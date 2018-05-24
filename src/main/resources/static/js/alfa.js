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
	if ($.fn.DataTable.isDataTable(this)) return;
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
	return this.DataTable({
		"processing": true,
		"serverSide": true,
		"ajax": function(data, callback, settings) {
			var finalUrl = url + (url.indexOf('?') > -1 ? "&" : "?") + "pageNumber=" + (data.start / data.length);
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
		"language": {
		    "sDecimal":        ",",
		    "sEmptyTable":     "Tabloda herhangi bir veri mevcut değil",
		    "sInfo":           "_TOTAL_ kayıttan _START_ - _END_ arasındaki kayıtlar gösteriliyor",
		    "sInfoEmpty":      "Kayıt yok",
		    "sInfoFiltered":   "(_MAX_ kayıt içerisinden bulunan)",
		    "sInfoPostFix":    "",
		    "sInfoThousands":  ".",
		    "sLengthMenu":     "Sayfada _MENU_ kayıt göster",
		    "sLoadingRecords": "Yükleniyor...",
		    "sProcessing":     "İşleniyor...",
		    "sSearch":         "Ara:",
		    "sZeroRecords":    "Eşleşen kayıt bulunamadı",
		    "oPaginate": {
		        "sFirst":    "İlk",
		        "sLast":     "Son",
		        "sNext":     "Sonraki",
		        "sPrevious": "Önceki"
		    },
		    "oAria": {
		        "sSortAscending":  ": artan sütun sıralamasını aktifleştir",
		        "sSortDescending": ": azalan sütun sıralamasını aktifleştir"
		    }
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
	alertify.error('Beklenmedik hata oluştu!');
});

alfaDefaultOptions = {
};

Alfa = window.Lider || {};
window.Alfa = Alfa;
Alfa.options = alfaDefaultOptions;

Alfa.ajax = function(opts, successHandler, errorHandler) {
	if (!opts) return;
	if (!opts.url) { console.log("URL must not be empty!"); return; }
	var _opts = {
		type : opts.type ? opts.type : "POST",
		contentType : "application/json",
		url : opts.url,
		dataType : 'json',
		cache : false,
		timeout : 600000,
		beforeSend: function(xhr) {
			var token = $('#_csrf').attr('content');
			var header = $('#_csrf_header').attr('content');
			xhr.setRequestHeader(header, token);
		},
		success: function(data, textStatus, jqXHR) {
			if (successHandler) successHandler(data, textStatus, jqXHR);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			if (errorHandler) errorHandler(jqXHR, textStatus, errorThrown);
		}
	};
	$.ajax(_opts);
};
