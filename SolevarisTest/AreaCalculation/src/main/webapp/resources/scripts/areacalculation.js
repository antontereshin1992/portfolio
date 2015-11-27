/**
 * Created by Anton on 05.11.2015.
 */

var WEBAPP = {};

WEBAPP.const = {};

WEBAPP.const.figures = {};

WEBAPP.const.url = {
    AJAX_GET_STATISTICS: 'http://127.0.0.1:8080/areacalculation/statistics/get'
};

WEBAPP.factory = {};

WEBAPP.factory.viewMain = function () {

    var that = {};

    var SEL_BTN_CALCULATOR = "button[mdl=areacalculation]";

    var calculators;
    var mdlAreaCalculation = WEBAPP.factory.mdlAreaCalculator();

    function init (){
        initComponents();
        bindComponentEvent();
    }

    function initComponents() {
        calculators = $(SEL_BTN_CALCULATOR);
    }

    function bindComponentEvent() {
        calculators.click(function(){
            mdlAreaCalculation.show($(this)[0].id);
        });
    }

    init();

    return that;
};

WEBAPP.factory.mdlAreaCalculator = function () {

    var that = {};

    const SELECTOR_MDL_AREA_CALCULATION = "#mdlAreaCalculation";
    const SELECTOR_BTN_COMPUTE          = "#btnCompute";
    const SELECTOR_FIELDS_WRAPPER       = "#fieldsWrapper";
    const SELECTOR_AREA                 = "#inpArea";
    const SELECTOR_TITLE                = "#title";

    var mdlAreaCalculation;
    var btnCompute;
    var fieldsWrapper;
    var inpArea;
    var title;

    var figure;

    function init (){
        initComponents();
        bindComponentEvent();
    }

    function initComponents() {
        mdlAreaCalculation = $(SELECTOR_MDL_AREA_CALCULATION);
        btnCompute         = $(SELECTOR_MDL_AREA_CALCULATION + ' ' + SELECTOR_BTN_COMPUTE);
        fieldsWrapper      = $(SELECTOR_MDL_AREA_CALCULATION + ' ' + SELECTOR_FIELDS_WRAPPER);
        inpArea            = $(SELECTOR_MDL_AREA_CALCULATION + ' ' + SELECTOR_AREA);
        title              = $(SELECTOR_MDL_AREA_CALCULATION + ' ' + SELECTOR_TITLE);
    }

    function bindComponentEvent() {
        btnCompute.click(function(){
            var hasError = false;
            var args = [];
            fieldsWrapper.find('input[type=number]').each(function(idx,element){
                element = $(element);
                if(element.val().trim() === '') {
                    element.parents('.form-group[type=wrapper]').addClass('has-error');
                    hasError = true;
                } else {
                    element.parents('.form-group[type=wrapper]').removeClass('has-error');
                    args.push(parseInt(element.val(),10));
                }
            });
            if (hasError) return;
            inpArea.val(figure.area(args));
            WEBAPP.api.StatisticsApi.updateStatistics(
                {figureId: figure.id},
                figure.onSuccessUpdateStatistics,
                figure.onErrorUpdateStatistics
            );
        });
    }

    that.show = function (figureId) {
        figure = WEBAPP.const.figures[figureId];
        title.text(figure.name);
        generateFields();
        reset();
        mdlAreaCalculation.modal('show');
    };

    function reset() {
        inpArea.val('');
    }

    function generateFields(){
        fieldsWrapper.empty();
        var params = figure.param;
        params = params.substring(1,params.length-1);
        var fields = params.split(',');
        fields.forEach(function(e){
            fieldsWrapper.append(generate(e));
        });
        fieldsWrapper.find('input[type=number]').keypress(function(event) {
            // Backspace, tab, enter, end, home, left, right
            // We don't support the del key in Opera because del == . == 46.
            var controlKeys = [8, 9, 13, 35, 36, 37, 39];
            // IE doesn't support indexOf
            var isControlKey = controlKeys.join(",").match(new RegExp(event.which));
            // Some browsers just don't raise events for control keys. Easy.
            // e.g. Safari backspace.
            if (!event.which || // Control keys in most browsers. e.g. Firefox tab is 0
                (49 <= event.which && event.which <= 57) || // Always 1 through 9
                (48 == event.which && $(this).attr("value")) || // No 0 first digit
                isControlKey) { // Opera assigns values for control keys.
            } else {
                event.preventDefault();
            }
        });
    }

    function generate(label){
        return '<div class="form-group input_panel" type="wrapper">' +
                    '<div class="row">' +
                        '<div class="col-lg-4 text-left" style=";padding: 5px">' +
                            '<label class="control-label">' + label + ':</label>' +
                        '</div> ' +
                        '<div class="col-lg-8" style=";padding: 0">' +
                            '<input type="number" class="form-control">' +
                        '</div>' +
                    '</div>' +
               '</div>';
    }

    init();

    return that;
};

WEBAPP.api = {};

WEBAPP.api.StatisticsApi = {

    updateStatistics: function (request, successCallback, errorCallback) {

        console.log('StatisticsApi.updateStatistics. Init:');
        console.log('request');
        console.log(request);

        $.ajax({
            type: 'POST',
            url: WEBAPP.const.url.AJAX_GET_STATISTICS,
            contentType: 'application/json',
            data: JSON.stringify(request),
            dataType: 'json',
            mimeType: 'application/json',
            crossDomain: true,
            processData: false,
            cache: false,
            headers: {
                'Access-Control-Allow-Headers': "x-requested-with",
                'Access-Control-Allow-Methods': 'POST, GET, OPTIONS, DELETE',
                'Access-Control-Max-Age': 3600,
                'Access-Control-Allow-Origin': '*'
            },
            success: function (data) {
                console.log('StatisticsApi.updateStatistics. Success:');
                if (successCallback !== undefined) {
                    successCallback(data);
                } else {
                    throw 'Success Callback function is undefined'
                }
            },
            error: function (thr, testStatus, errorCode) {
                if (errorCallback !== undefined) {
                    errorCallback(thr, testStatus, errorCode);
                } else {
                    throw 'Error Callback function is undefined'
                }
            }
        });
    }

};