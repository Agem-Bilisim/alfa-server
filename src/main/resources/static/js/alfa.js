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
    default: "Varsayılan",
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
    text: "Tekli Girdi",
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
    jsonEditor: "JSON Editor",
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
    addItem: "Kalem eklemek için tıklayın...",
    removeAll: "Hepsini Kaldır",
    edit: "Düzenle",
    itemValueEdit: "Visible If",
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
    showTitle: "Show/hide title",
    locale: "Default language",
    mode: "Mode (edit/read only)",
    clearInvisibleValues: "Clear invisible values",
    cookieName: "Cookie name (to disable run survey two times locally)",
    sendResultOnPageNext: "Send survey results on page next",
    storeOthersAsComment: "Store 'others' value in separate field",
    showPageTitles: "Show page titles",
    showPageNumbers: "Show page numbers",
    pagePrevText: "Page previous button text",
    pageNextText: "Page next button text",
    completeText: "Complete button text",
    startSurveyText: "Start button text",
    showNavigationButtons: "Show navigation buttons (default navigation)",
    showPrevButton: "Show previous button (user may return on previous page)",
    firstPageIsStarted: "The first page in the survey is a started page.",
    showCompletedPage: "Show the completed page at the end (completedHtml)",
    goNextPageAutomatic:
      "On answering all questions, go to the next page automatically",
    showProgressBar: "Show progress bar",
    questionTitleLocation: "Question title location",
    requiredText: "The question required symbol(s)",
    questionStartIndex: "Question start index (1, 2 or 'A', 'a')",
    showQuestionNumbers: "Show question numbers",
    questionTitleTemplate:
      "Question title template, default is: '{no}. {require} {title}'",
    questionErrorLocation: "Question error location",
    focusFirstQuestionAutomatic: "Focus first question on changing the page",
    questionsOrder: "Elements order on the page",
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
      general: "General",
      fileOptions: "Options",
      html: "Html Editor",
      columns: "Columns",
      rows: "Rows",
      choices: "Choices",
      items: "Items",
      visibleIf: "Visible If",
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
    editProperty: "Edit property '{0}'",
    items: "[ Items: {0} ]",

    enterNewValue: "Please, enter the value.",
    noquestions: "There is no any question in the survey.",
    createtrigger: "Please create a trigger",
    triggerOn: "On ",
    triggerMakePagesVisible: "Make pages visible:",
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
    true: "true",
    false: "false",
    inherit: "inherit",
    show: "show",
    hide: "hide",
    default: "default",
    initial: "initial",
    random: "random",
    collapsed: "collapsed",
    expanded: "expanded",
    none: "none",
    asc: "ascending",
    desc: "descending",
    indeterminate: "indeterminate",
    decimal: "decimal",
    currency: "currency",
    percent: "percent",
    firstExpanded: "firstExpanded",
    off: "off",
    onPanel: "onPanel",
    onSurvey: "onSurvey",
    list: "list",
    progressTop: "progressTop",
    progressBottom: "progressBottom",
    progressTopBottom: "progressTopBottom",
    top: "top",
    bottom: "bottom",
    left: "left",
    color: "color",
    date: "date",
    datetime: "datetime",
    "datetime-local": "datetime-local",
    email: "email",
    month: "month",
    number: "number",
    password: "password",
    range: "range",
    tel: "tel",
    text: "text",
    time: "time",
    url: "url",
    week: "week",
    hidden: "hidden",
    on: "on",
    onPage: "onPage",
    edit: "edit",
    display: "display",
    onComplete: "onComplete",
    onHidden: "onHidden",
    all: "all",
    page: "page",
    survey: "survey",
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
    answercountvalidator: "answer count",
    emailvalidator: "e-mail",
    expressionvalidator: "expression",
    numericvalidator: "numeric",
    regexvalidator: "regex",
    textvalidator: "text"
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
    page: "page",

    width: "width",

    commentText: "commentText",
    valueName: "valueName",
    enableIf: "enableIf",
    defaultValue: "defaultValue",
    correctAnswer: "correctAnswer",
    readOnly: "readOnly",
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

    showPreview: "showPreview",
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

    locale: "locale",
    focusFirstQuestionAutomatic: "focusFirstQuestionAutomatic",
    completedHtml: "completedHtml",
    completedBeforeHtml: "completedBeforeHtml",
    loadingHtml: "loadingHtml",
    triggers: "triggers",
    cookieName: "cookieName",
    sendResultOnPageNext: "sendResultOnPageNext",
    showNavigationButtons: "showNavigationButtons",
    showPrevButton: "showPrevButton",
    showPageTitles: "showPageTitles",
    showCompletedPage: "showCompletedPage",
    showPageNumbers: "showPageNumbers",
    questionErrorLocation: "questionErrorLocation",
    showProgressBar: "showProgressBar",
    mode: "mode",
    goNextPageAutomatic: "goNextPageAutomatic",
    checkErrorsMode: "checkErrorsMode",
    clearInvisibleValues: "clearInvisibleValues",
    startSurveyText: "startSurveyText",
    pagePrevText: "pagePrevText",
    pageNextText: "pageNextText",
    completeText: "completeText",
    requiredText: "requiredText",
    questionStartIndex: "questionStartIndex",
    questionTitleTemplate: "questionTitleTemplate",
    firstPageIsStarted: "firstPageIsStarted",
    isSinglePage: "isSinglePage",
    maxTimeToFinishPage: "maxTimeToFinishPage",
    showTimerPanel: "showTimerPanel",
    showTimerPanelMode: "showTimerPanelMode",

    text: "text",
    minValue: "minimum value",
    maxValue: "maximum value",
    minLength: "minumum length",
    maxLength: "maximum length",
    allowDigits: "allow digits",
    minCount: "minumum count",
    maxCount: "maximum count",
    regex: "regular expression"
  }
};
SurveyEditor.editorLocalization.locales["tr"] = trStrings;
SurveyEditor.editorLocalization.currentLocale = "tr";