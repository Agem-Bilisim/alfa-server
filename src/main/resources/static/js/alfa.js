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
 * Hook paginatedTable() function to jQuery object to easily generate DataTables with server-side processing...
 * 
 * url: Ajax URL for GET request
 * resultingProp: Property name for the resulting list
 * cols: Columns in the format of DataTable columns array.
 * (Column options:
 *	cellType,
 *	className,
 *	contentPadding,
 *	data,
 *	defaultContent,
 *	name,
 *	orderable,
 *	orderData,
 *	orderDataType,
 *	render,
 *	searchable,
 *	title,
 *	type,
 *	visible,
 *	width 
 * )
 * See https://datatables.net/reference/option/columns.data for option definitions
 * drawCallback: Callback function to trigger after table is drawn.
 * 
 */
$.fn.paginatedTable = function(url, resultingProp, cols, drawCallback, order) {
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
	console.log(order);
	return this.DataTable({
		"processing": true,
		"serverSide": true,
		"ajax": function(data, callback, settings) {
			var finalUrl = url + (url.indexOf('?') > -1 ? "&" : "?") + "page=" + (data.start / data.length);
			if (data.length) {
				finalUrl += "&size=" + data.length;
			}
			if (data.order) {
				for (index in data.order) {
					var o = data.order[index];
					var prop = cols[o.column].hasOwnProperty("property") ? cols[o.column]["property"] : cols[o.column]["data"];
					var dir = o.dir.toUpperCase();
					
					finalUrl += "&sort.orders[" + index + "].property=" + prop;
					finalUrl += "&sort.orders[" + index + "].direction=" + dir;
				}
			}
			if (data.search) {
				finalUrl += "&search=" + data.search.value;
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
						console.log("GET Response [" + finalUrl + "]");
						console.log(JSON.stringify(result))
						callback({
							"draw": drawCount,
							"recordsTotal": result.data[resultingProp].hasOwnProperty('totalElements') ? result.data[resultingProp].totalElements : result.data[resultingProp].length,
							"recordsFiltered": result.data[resultingProp].hasOwnProperty('totalElements') ? result.data[resultingProp].totalElements : result.data[resultingProp].length,
							"data": result.data[resultingProp].hasOwnProperty('content') ? result.data[resultingProp].content : result.data[resultingProp]
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
		"drawCallback": drawCallback,
		"order": order ? order : [ [0, 'desc'] ],
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
	eventQueueSize: 50	
};

Alfa = window.Alfa || {};
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
		async: opts.async ? opts.async : true,
		data: JSON.stringify(opts.data),
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

var trStrings = {
  //survey templates
  survey: {
    edit: "Düzenle",
    dropQuestion: "Lütfen soldaki araç kutusundan buraya soru sürükleyip bırakın.",
    copy: "Kopyala",
    addToToolbox: "Araç kutusuna ekle",
    deletePanel: "Paneli Sil",
    deleteQuestion: "Soruyu Sil",
    convertTo: "Dönüştür",
    drag: "Sürükle"
  },
  //questionTypes
  qt: {
    'default': "Varsayılan",
    checkbox: "Onay kutusu",
    comment: "Yorum",
    dropdown: "Seçimli kutu",
    file: "Dosya",
    html: "Html",
    matrix: "Matris (tek seçenek)",
    matrixdropdown: "Matris (çoklu seçenek)",
    matrixdynamic: "Matris (dinamik satırlar)",
    multipletext: "Çoklu Metin",
    panel: "Panel",
    paneldynamic: "Panel (dinamik paneller)",
    radiogroup: "Radyo kutu",
    rating: "Reyting",
    text: "Metin",
    boolean: "Doğru-Yanlış",
    expression: "İfade"
  },
  //Strings in Editor
  ed: {
    survey: "Anket",
    settings: "Anket Ayarları",
    editSurvey: "Anket Düzenle",
    addNewPage: "Yeni Sayfa Ekle",
    moveRight: "Sağa Kaydır",
    moveLeft: "Sola Kaydır",
    deletePage: "Sayfayı Sil",
    editPage: "Sayfa Düzenle",
    edit: "Düzenle",
    newPageName: "sayfa",
    newQuestionName: "soru",
    newPanelName: "panel",
    testSurvey: "Anketi Dene",
    testSurveyAgain: "Anketi Tekrar Dene",
    testSurveyWidth: "Anket genişliği: ",
    embedSurvey: "Gömülü Anket",
    saveSurvey: "Anketi Kaydet",
    designer: "Anket Tasarımcısı",
    jsonEditor: "JSON Editörü",
    undo: "Geri Al",
    redo: "İleri Al",
    options: "Seçenekler",
    generateValidJSON: "Geçerli JSON Üret",
    generateReadableJSON: "Kolay-okunur JSON Üret",
    toolbox: "Araç Kutusu",
    toolboxGeneralCategory: "Genel",
    delSelObject: "Seçili nesneyi sil",
    editSelObject: "Seçili nesneyi düzenle",
    correctJSON: "Lütfen JSON'ı düzeltin.",
    surveyResults: "Anket Sonucu: ",
    modified: "Düzenlendi",
    saving: "Kaydediliyor",
    saved: "Kaydedildi"
  },
  //Property names in table headers
  pel: {
    isRequired: "Zorunlu?"
  },
  //Property Editors
  pe: {
    apply: "Uygula",
    ok: "Tamam",
    cancel: "İptal",
    reset: "Vazgeç",
    close: "Kapat",
    delete: "Sil",
    addNew: "Yeni Ekle",
    addItem: "Eklemek için tıklayın...",
    removeAll: "Hepsini Kaldır",
    edit: "Düzenle",
    itemValueEdit: "Koşullu Görünürlük",
    editChoices: "Edit Choices",
    move: "Move",
    empty: "<boş>",
    notEmpty: "<değer düzenle>",
    fastEntry: "Hızlı Girdi",
    formEntry: "Form Girdisi",
    testService: "Servisi dene",
    conditionSelectQuestion: "Soru seçin...",
    conditionValueQuestionTitle: "Lütfen değer girin/seçin",
    conditionButtonAdd: "Ekle",
    conditionButtonReplace: "Kaldır",
    conditionHelp:
      "Please enter a boolean expression. It should return true to keep the question/page visible. For example: {question1} = 'value1' or ({question2} * {question4}  > 20 and {question3} < 5)",
    expressionHelp:
      "Please enter an expression. You may use curly brackets to get access to the question values: '{question1} + {question2}', '({price}*{quantity}) * (100 - {discount})'",
    aceEditorHelp: "Press ctrl+space to get expression completion hint",
    aceEditorRowTitle: "Current row",
    aceEditorPanelTitle: "Current panel",
    showMore: "For more details please check the documentation",
    conditionShowMoreUrl:
      "https://surveyjs.io/Documentation/LibraryParameter?id=QuestionBase&parameter=visibleIf",
    assistantTitle: "Available questions:",
    cellsEmptyRowsColumns: "There is should be at least one column or row",

    propertyIsEmpty: "Please enter a value",
    value: "Value",
    text: "Text",
    columnEdit: "Edit column: {0}",
    itemEdit: "Edit item: {0}",

    url: "URL",
    path: "Dizin",
    valueName: "Değer adı",
    titleName: "Başlık adı",

    hasOther: "Has other item",
    otherText: "Other item text",
    name: "Name",
    title: "Title",
    cellType: "Cell type",
    colCount: "Column count",
    choicesOrder: "Select choices order",
    visible: "Is visible?",
    isRequired: "Is required?",
    startWithNewLine: "Is start with new line?",
    rows: "Row count",
    placeHolder: "Input place holder",
    showPreview: "Is image preview shown?",
    storeDataAsText: "Store file content in JSON result as text",
    maxSize: "Maximum file size in bytes",
    imageHeight: "Image height",
    imageWidth: "Image width",
    rowCount: "Row count",
    addRowText: "Add row button text",
    removeRowText: "Remove row button text",
    minRateDescription: "Minimum rate description",
    maxRateDescription: "Maximum rate description",
    inputType: "Input type",
    optionsCaption: "Options caption",
    defaultValue: "Default value",
    cellsDefaultRow: "Default cells texts",

    surveyEditorTitle: "Edit survey settings",
    qEditorTitle: "Edit: {0}",

    //survey
    showTitle: "Başlık göster/gizle",
    locale: "Varsayılan dil",
    mode: "Mod (Düzenle/salt-okunur)",
    clearInvisibleValues: "Görünür olmayan değerleri temizle",
    cookieName: "Cookie name (to disable run survey two times locally)",
    sendResultOnPageNext: "Send survey results on page next",
    storeOthersAsComment: "Store 'others' value in separate field",
    showPageTitles: "Sayfa başlıklarını göster",
    showPageNumbers: "Sayfa numaralarını göster",
    pagePrevText: "Page previous button text",
    pageNextText: "Page next button text",
    completeText: "Tamamla butonu metni",
    startSurveyText: "Başlat butonu metni",
    showNavigationButtons: "Show navigation buttons (default navigation)",
    showPrevButton: "Show previous button (user may return on previous page)",
    firstPageIsStarted: "The first page in the survey is a started page.",
    showCompletedPage: "Show the completed page at the end (completedHtml)",
    goNextPageAutomatic:
      "On answering all questions, go to the next page automatically",
    showProgressBar: "İlerleme barı göster",
    questionTitleLocation: "Soru başlığı konumu",
    requiredText: "The question required symbol(s)",
    questionStartIndex: "Soru başlangıç indisi (1, 2 or 'A', 'a')",
    showQuestionNumbers: "Show question numbers",
    questionTitleTemplate:
      "Question title template, default is: '{no}. {require} {title}'",
    questionErrorLocation: "Soru hata konumu",
    focusFirstQuestionAutomatic: "Focus first question on changing the page",
    questionsOrder: "Soru sıralaması",
    maxTimeToFinish: "Maximum time to finish the survey",
    maxTimeToFinishPage: "Maximum time to finish a page in the survey",
    showTimerPanel: "Show timer panel",
    showTimerPanelMode: "Show timer panel mode",
    renderMode: "Render mode",
    allowAddPanel: "Allow adding a panel",
    allowRemovePanel: "Allow removing the panel",
    panelAddText: "Adding panel text",
    panelRemoveText: "Removing panel text",
    isSinglePage: "Show all elements on one page",

    tabs: {
      general: "Genel",
      fileOptions: "Seçenekler",
      html: "Html Editör",
      columns: "Sütunlar",
      rows: "Satılar",
      choices: "Seçimler",
      items: "Items",
      visibleIf: "Koşullu Görünürlük",
      enableIf: "Enable If",
      rateValues: "Rate Values",
      choicesByUrl: "Choices from Web",
      matrixChoices: "Default Choices",
      multipleTextItems: "Text Inputs",
      validators: "Validators",
      navigation: "Navigation",
      question: "Question",
      completedHtml: "Completed Html",
      loadingHtml: "Loading Html",
      timer: "Timer/Quiz",
      triggers: "Triggers",
      templateTitle: "Template title"
    },
    editProperty: "Nitelik Düzenle '{0}'",
    items: "[ Items: {0} ]",

    enterNewValue: "Lütfen, değer giriniz.",
    noquestions: "Ankette herhangi bir soru mevcut değil.",
    createtrigger: "Lütfen tetikleyici oluşturun",
    triggerOn: "Açık ",
    triggerMakePagesVisible: "Sayfaları görünür yap:",
    triggerMakeQuestionsVisible: "Make elements visible:",
    triggerCompleteText: "Complete the survey if succeed.",
    triggerNotSet: "The trigger is not set",
    triggerRunIf: "Run if",
    triggerSetToName: "Change value of: ",
    triggerSetValue: "to: ",
    triggerIsVariable: "Do not put the variable into the survey result."
  },
  //Property values
  pv: {
    true: "doğru",
    false: "yanlış",
    inherit: "inherit",
    show: "göster",
    hide: "gizle",
    default: "varsayılan",
    initial: "initial",
    random: "rastgele",
    collapsed: "collapsed",
    expanded: "expanded",
    none: "none",
    asc: "artan sırada",
    desc: "azalan sırada",
    indeterminate: "belirsiz",
    decimal: "ondalık",
    currency: "currency",
    percent: "oran",
    firstExpanded: "firstExpanded",
    off: "off",
    onPanel: "onPanel",
    onSurvey: "onSurvey",
    list: "list",
    progressTop: "progressTop",
    progressBottom: "progressBottom",
    progressTopBottom: "progressTopBottom",
    top: "üst",
    bottom: "alt",
    left: "sol",
    color: "renk",
    date: "tarih",
    datetime: "tarih-zaman",
    "datetime-local": "datetime-local",
    email: "eposta",
    month: "ay",
    number: "sayı",
    password: "parola",
    range: "range",
    tel: "tel",
    text: "metin",
    time: "zaman",
    url: "url",
    week: "hafta",
    hidden: "gizli",
    on: "on",
    onPage: "onPage",
    edit: "düzenle",
    display: "göster",
    onComplete: "onComplete",
    onHidden: "onHidden",
    all: "hepsi",
    page: "sayfa",
    survey: "anket",
    onNextPage: "onNextPage",
    onValueChanged: "onValueChanged"
  },
  //Operators
  op: {
    empty: "boş",
    notempty: "boş değil",
    equal: "eşit",
    notequal: "eşit değil",
    contains: "içerir",
    notcontains: "içermez",
    greater: "büyük",
    less: "küçük",
    greaterorequal: "büyük eşit",
    lessorequal: "küçük eşit"
  },
  //Embed window
  ew: {
    angular: "Angular sürümü kullan",
    jquery: "jQuery sürümü kullan",
    knockout: "Knockout sürümü kullan",
    react: "React sürümü kullan",
    vue: "Vue sürümü kullan",
    bootstrap: "Bootstrap için",
    standard: "Bootstrap olmadan",
    showOnPage: "Anketi bir sayfada göster",
    showInWindow: "Anketi bir pencerede göster",
    loadFromServer: "Sunucudan anket JSON'ı yükle",
    titleScript: "Betikler ve stiller",
    titleHtml: "HTML",
    titleJavaScript: "JavaScript"
  },
  //Test Survey
  ts: {
    selectPage: "Test etmek için sayfayı seçin:"
  },
  validators: {
    answercountvalidator: "Cevap sayısı",
    emailvalidator: "e-posta",
    expressionvalidator: "ifade",
    numericvalidator: "sayısal",
    regexvalidator: "regex",
    textvalidator: "metin"
  },
  triggers: {
    completetrigger: "anketi tamamla",
    setvaluetrigger: "değer gir",
    visibletrigger: "görünürlüğü değiştir"
  },
  //Properties
  p: {
    name: "isim",
    title: {
      name: "başlık",
      title: "Eğer 'İsim' ile aynı ise, boş bırak"
    },

    navigationButtonsVisibility: "navigationButtonsVisibility",
    questionsOrder: "questionsOrder",
    maxTimeToFinish: "maxTimeToFinish",

    visible: "visible",
    visibleIf: "visibleIf",
    questionTitleLocation: "questionTitleLocation",
    description: "description",
    state: "state",
    isRequired: "isRequired",
    indent: "indent",
    requiredErrorText: "requiredErrorText",
    startWithNewLine: "startWithNewLine",
    innerIndent: "innerIndent",
    page: "Sayfa",

    width: "Genişlik",

    commentText: "Yorum metni",
    valueName: "valueName",
    enableIf: "enableIf",
    defaultValue: "defaultValue",
    correctAnswer: "correctAnswer",
    readOnly: "Salt-okunur",
    validators: "validators",
    titleLocation: "titleLocation",

    hasComment: "hasComment",
    hasOther: "hasOther",
    choices: "choices",
    choicesOrder: "choicesOrder",
    choicesByUrl: "choicesByUrl",
    otherText: "otherText",
    otherErrorText: "otherErrorText",
    storeOthersAsComment: "storeOthersAsComment",

    label: "label",
    showTitle: "showTitle",
    valueTrue: "valueTrue",
    valueFalse: "valueFalse",

    cols: "cols",
    rows: "rows",
    placeHolder: "placeHolder",

    optionsCaption: "optionsCaption",

    expression: "expression",
    format: "format",
    displayStyle: "displayStyle",
    currency: "currency",
    useGrouping: "useGrouping",

    showPreview: "Önizleme göster",
    allowMultiple: "allowMultiple",
    imageHeight: "imageHeight",
    imageWidth: "imageWidth",
    storeDataAsText: "storeDataAsText",
    maxSize: "maxSize",

    html: "html",

    columns: "columns",
    cells: "cells",
    isAllRowRequired: "isAllRowRequired",

    horizontalScroll: "horizontalScroll",
    cellType: "cellType",
    columnColCount: "columnColCount",
    columnMinWidth: "columnMinWidth",

    rowCount: "rowCount",
    minRowCount: "minRowCount",
    maxRowCount: "maxRowCount",
    keyName: "keyName",
    keyDuplicationError: "keyDuplicationError",
    confirmDelete: "confirmDelete",
    confirmDeleteText: "confirmDeleteText",
    addRowText: "addRowText",
    removeRowText: "removeRowText",

    items: "items",
    itemSize: "itemSize",
    colCount: "colCount",

    templateTitle: "templateTitle",
    templateDescription: "templateDescription",
    allowAddPanel: "allowAddPanel",
    allowRemovePanel: "allowRemovePanel",
    panelCount: "panelCount",
    minPanelCount: "minPanelCount",
    maxPanelCount: "maxPanelCount",
    panelsState: "panelsState",
    panelAddText: "panelAddText",
    panelRemoveText: "panelRemoveText",
    panelPrevText: "panelPrevText",
    panelNextText: "panelNextText",
    showQuestionNumbers: "showQuestionNumbers",
    showRangeInProgress: "showRangeInProgress",
    renderMode: "renderMode",
    templateTitleLocation: "templateTitleLocation",

    rateValues: "rateValues",
    rateMin: "rateMin",
    rateMax: "rateMax",
    rateStep: "rateStep",
    minRateDescription: "minRateDescription",
    maxRateDescription: "maxRateDescription",

    inputType: "inputType",
    size: "size",

    locale: "dil",
    focusFirstQuestionAutomatic: "İlk soruya otomatik odaklan",
    completedHtml: "completedHtml",
    completedBeforeHtml: "completedBeforeHtml",
    loadingHtml: "loadingHtml",
    triggers: "Tetikleyiciler",
    cookieName: "cookieName",
    sendResultOnPageNext: "sendResultOnPageNext",
    showNavigationButtons: "showNavigationButtons",
    showPrevButton: "Önceki butonu göster",
    showPageTitles: "Sayfa başlıklarını göster",
    showCompletedPage: "showCompletedPage",
    showPageNumbers: "Sayfa numaralarını göster",
    questionErrorLocation: "Soru hata konumu",
    showProgressBar: "İlerleme barı göster",
    mode: "Mod",
    goNextPageAutomatic: "Sonraki sayfaya otomatik geç",
    checkErrorsMode: "checkErrorsMode",
    clearInvisibleValues: "Görünür olmayan değerleri temizle",
    startSurveyText: "Anket başlangıç metni",
    pagePrevText: "Önceki sayfa metni",
    pageNextText: "Sonraki sayfa metni",
    completeText: "completeText",
    requiredText: "Zorunlu alan metni",
    questionStartIndex: "Soru başlangıç indisi",
    questionTitleTemplate: "Soru başlık şablonu",
    firstPageIsStarted: "firstPageIsStarted",
    isSinglePage: "Tek sayfalı",
    maxTimeToFinishPage: "maxTimeToFinishPage",
    showTimerPanel: "showTimerPanel",
    showTimerPanelMode: "showTimerPanelMode",

    text: "metin",
    minValue: "minimum değer",
    maxValue: "maksimum değer",
    minLength: "minimum uzunluk",
    maxLength: "maksimum uzunluk",
    allowDigits: "allow digits",
    minCount: "minumum count",
    maxCount: "maximum count",
    regex: "regular expression"
  }
};
SurveyEditor.editorLocalization.locales["tr"] = trStrings;
SurveyEditor.editorLocalization.currentLocale = "tr";

/**
 * Adds event id to the event queue. 
 * 
 * This will be used in SSE connection in order to notify user when related message received.
 */
Alfa.addEventQueue = function(eventId) {
	if (typeof eventId === 'undefined') {
		console.log('Event ID cannot be null.');
		return;
	}
	var eventIdQueue = localStorage.getItem('event-id-queue');
	// Init array
	if (typeof eventIdQueue === 'undefined' || eventIdQueue === null || eventIdQueue.length == 0) {
		eventIdQueue = [];
	} else {
		// Be aware that this is an array of strings, not numbers.
		eventIdQueue = eventIdQueue.split(',');
	}
	
	// Limit size!
	if (eventIdQueue.length > Alfa.options.eventQueueSize) {
		eventIdQueue.splice(0, eventIdQueue.length - Alfa.options.eventQueueSize);
	}
	// Append task id
	eventIdQueue.push(eventId.toString());

	// Save the queue back to the storage
	localStorage.setItem('event-id-queue', eventIdQueue.toString());
};

Alfa.checkEventQueue = function(eventId) {
	if (typeof eventId === 'undefined') {
		console.log('Event ID cannot be null.');
		return false;
	}
	var eventIdQueue = localStorage.getItem('event-id-queue');
	if (typeof eventIdQueue === 'undefined' || eventIdQueue === null || eventIdQueue.length == 0) {
		return false;
	}
	return eventIdQueue.indexOf(eventId) > -1;
};

/**
 * SSE event listener/handler.
 * 
 * This is used to notify user about LDAP sync messages in real time.
 */
(function(){
	if (window.location.pathname == '/alfa/login') {
		return;
	}
	// SSE event source
	const eventSource = new EventSource('/alfa/ldap/sync/status');
	eventSource.addEventListener('ldap-sync-status', function(event) {
		if (event && event.data && event.lastEventId) {
			var integrationId = event.lastEventId.split('-')[0];
			console.log(JSON.parse(event.data));
			if (Alfa.checkEventQueue(integrationId)) {
				const result = JSON.parse(event.data);
				var success = result.data.success;
				if (success) {
					alertify.success(result.message);
				} else {
					aletfiy.error(result.message);
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
		} else {
			console.log(event);
		}
	};
})();