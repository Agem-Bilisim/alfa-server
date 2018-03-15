// Make sure jQuery has been loaded
if (typeof jQuery === 'undefined') {
	throw new Error('AdminLTE requires jQuery');
}

/*
 * We try to load all other js files before this one. 
 * So it is fairly safe to use (e.g. jQuery or Bootstrap) related functions here.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 * 
 */
// These ensure AlertifyJS uses Bootstrap3 styling
alertify.defaults.transition = "slide";
alertify.defaults.theme.ok = "btn btn-primary";
alertify.defaults.theme.cancel = "btn btn-danger";
alertify.defaults.theme.input = "form-control";
alertify.defaults.maximizable = false;

/**
 * Hook serializeObject() function to jQuery object to easily convert forms to JSON objects.
 * 
 * ignoreList: Array of element names to ignore.
 * 
 */
$.fn.serializeObject = function(ignoreList) {
	var o = {};
	var a = this.serializeArray();
	// We cannot retrieve 'checked' status since iCheck does not hold value, instead we should use checked property...
	var checkboxNames = $('input[type="checkbox"]', this).map(function(){return $(this).attr("name");}).get();
	$.each(a, function() {
		if (ignoreList && ignoreList.indexOf(this.name) >= 0) {
			return;
		}
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push((checkboxNames.indexOf(this.name) > -1 ? $("#" + this.name).prop('checked') : this.value) || '');
		} else {
			o[this.name] = (checkboxNames.indexOf(this.name) > -1 ? $("#" + this.name).prop('checked') : this.value) || '';
		}
	});
	return o;
};

/**
 * Hook paginatedTable() function tu jQuery object to easily generate DataTables with server-side processing...
 * 
 * url: Ajax URL for GET request
 * resultingProp: Property name for the resulting list
 * cols: Columns in the format of DataTable columns array. See https://datatables.net/reference/option/columns.data
 * drawCallback: Callback function to trigger after table is drawn.
 * 
 */
$.fn.paginatedTable = function(url, resultingProp, cols, drawCallback) {
	if (!$.prototype.DataTable) {
		console.log('DataTable function does not exist. Ensure that its js file is included in the HTML page.');
		return;
	}
	if (!url) {
		console.log('URL parameter was null.');
		return;
	}
	if (!resultingProp) {
		console.log('Resulting prop parameter was null.');
		return;
	}
	if (!cols) {
		console.log('Cols parameter was null.');
		return;
	}
	this.DataTable({
		"processing": true,
		"serverSide": true,
		"ajax": function(data, callback, settings) {
			var finalUrl = url + "?pageNumber=" + (data.start / data.length);
			if (data.length) {
				finalUrl += "&pageSize=" + data.length;
			}
			if (data.order) {
				for (index in data.order) {
					var o = data.order[index];
					var prop = cols[o.column]["property"];
					var dir = o.dir.toUpperCase();
					
					finalUrl += "&sort.orders[" + index + "].property=" + prop;
					finalUrl += "&sort.orders[" + index + "].direction=" + dir;
				}
			}
			var drawCount = data.draw;
			$.ajax({
				type: "GET",
				contentType: "application/json",
				url: finalUrl,
				cache: false,
				timeout: 600000,
				beforeSend: function(xhr) {
					var token = $('#_csrf').attr('content');
					var header = $('#_csrf_header').attr('content');
					xhr.setRequestHeader(header, token);
				},
				success: function(result) {
					if (result && result.data) {
						callback({
							"draw": drawCount,
							"recordsTotal": result.data[resultingProp].totalElements,
							"recordsFiltered": result.data[resultingProp].totalElements,
							"data": result.data[resultingProp].content
						});
					}
				},
				error: function(result) {
					callback({
						"error": "Unexpected error occurred."
					});
				} 
			});
		},
		"columns": cols,
		"deferRender": true,
		"responsive": true,
		"drawCallback": drawCallback
	});
};

// Pace (a simple loading module) is triggered for each ajax call.
$(document).ajaxStart(function() {
	Pace.restart();
});

// Notification is triggered for each ajax call which ended with an error
$(document).ajaxError(function(event, jqxhr, settings, thrownError) {
	console.log('E:' + event + ' jqxhr: ' + jqxhr + ' settings:' + settings 
			+ ' thrownError:' + thrownError);
	alertify.error('Unexpected error! URL:' + settings.url + ' Cause:' + thrownError);
});

liderDefaultOptions = {
	taskQueueSize: 50	
};

Lider = window.Lider || {};
window.Lider = Lider;
Lider.options = liderDefaultOptions;

/**
 * Adds task id to the task queue. 
 * 
 * This will be used in SSE connection in order to notify user when related task status message received.
 */
Lider.addTaskQueue = function(taskId) {
	if (typeof taskId === 'undefined') {
		console.log('Task ID cannot be null.');
		return;
	}
	var taskIdQueue = localStorage.getItem('task-id-queue');
	// Init array
	if (typeof taskIdQueue === 'undefined' || taskIdQueue === null || taskIdQueue.length == 0) {
		taskIdQueue = [];
	} else {
		// Be aware that this is an array of strings, not numbers.
		taskIdQueue = taskIdQueue.split(',');
	}
	
	// Limit size!
	if (taskIdQueue.length > Lider.options.taskQueueSize) {
		taskIdQueue.splice(0, taskIdQueue.length - Lider.options.taskQueueSize);
	}
	// Append task id
	taskIdQueue.push(taskId.toString());

	// Save the queue back to the storage
	localStorage.setItem('task-id-queue', taskIdQueue.toString());
};

Lider.checkTaskQueue = function(taskId) {
	if (typeof taskId === 'undefined') {
		console.log('Task ID cannot be null.');
		return false;
	}
	var taskIdQueue = localStorage.getItem('task-id-queue');
	if (typeof taskIdQueue === 'undefined' || taskIdQueue === null || taskIdQueue.length == 0) {
		return false;
	}
	return taskIdQueue.indexOf(taskId) > -1;
};

Lider.taskQueueHandlers = [];
Lider.addTaskQueueHandler = function(fn) {
	Lider.taskQueueHandlers.push(fn);
};

/**
 * SSE event listener/handler.
 * 
 * This is used to notify user about task/policy status messages in real time.
 */
(function(){
	if (window.location.pathname == '/lider/login') {
		return;
	}
	// SSE event source
	const eventSource = new EventSource('/lider/task/status');
	eventSource.onmessage = function(event) {
		// simple event (not interested!)
		console.log('SSE message received: ' + event);
	};
	eventSource.addEventListener('task-status', function(event) {
		if (event && event.data && event.lastEventId) {
			var taskId = event.lastEventId.split('-')[0];
			if (Lider.checkTaskQueue(taskId)) {
				const result = JSON.parse(event.data);
				// This is the expected type (TASK_STATUS) which is received when a task status message received from an agent.
				if (result.type == 'TASK_STATUS') {
					switch(result.status) {
					case 'TASK_SUCCESS':
						alertify.success(result.message);
						// Trigger task queue handlers
						for (var index in Lider.taskQueueHandlers) {
							try {
								Lider.taskQueueHandlers[index](result);
							} catch(err) {
								console.log(err);
							}
						}
						break;
					case 'TASK_WARNING':
						alertify.warning(result.message);
						break;
					case 'TASK_ERROR':
						alertify.error(result.message);
						break;
					default:
						alertify.notify(result.message);
					}
				} else if (result.type == 'EXECUTE_TASK') {
					// This is an echoed-back message that is thrown when an internal server error occurred.
					alertify.error("Unexpected error occurred. Agent could not be connected or script could not be run. Check your SSH config and username/password.");
				}
			}
		}
	}, false);
	eventSource.onopen = function(event) {
		console.log('SSE connection established.');
	};
	eventSource.onerror = function(event) {
		// We do not need to do anything. It will automatically try to reconnect.
		if (event.readyState == EventSource.CLOSED) {
			console.log('SSE connection closed.');
		}
		else {
			console.log(event);
		}
	};
})();
