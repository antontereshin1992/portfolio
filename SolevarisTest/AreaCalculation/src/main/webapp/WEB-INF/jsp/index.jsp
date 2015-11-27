<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="resources/styles/bootstrap.css">
    <link rel="stylesheet" href="resources/styles/main.css">
    <link rel="stylesheet" href="resources/styles/bootstrap-dialog.css">
    <script type="text/javascript" src="resources/scripts/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/scripts/bootstrap.3.3.5.js"></script>
    <script type="text/javascript" src="resources/scripts/bootstrap-dialog.js"></script>
    <script type="text/javascript" src="resources/scripts/json2.js"></script>
    <script type="text/javascript" src="resources/scripts/areacalculation.js"></script>
    <script>
        $(document).ready(function () {
            $(".media-object").error(function () {
                $(this).attr('src', 'resources/images/128x128.gif');
            });
            WEBAPP.factory.viewMain();
        });
    </script>
</head>
<body class="text-center" style="margin-top: 30px">
<h4>Расчет площадей</h4>
<%-- Main page BEGIN --%>
    <div class="text-center">
        <c:forEach var="figure" items="${figures}">
            <div class="panel panel-default " >
                <div class="panel-body " >
                    <div class="form-group " >
                        <div class="media ">
                            <h4 class="media-heading">${figure.name}</h4>
                            <div class="media-body inline_group">
                                <div class="text-center" >
                                    <img class="media-object" src="resources/images/${figure.image}" style=" display: inline-block; height: 128px; width: 128px"/>
                                </div>
                            </div>
                            <div class="media-heading" >Площадь расчитывали: <span id="${figure.id}-times">${figure.times}</span> раз</div>
                        </div>
                        <button class="btn btn-default" mdl="areacalculation" id="${figure.id}">Рассчитать</button>
                    </div>
                </div>
            </div>
            <script>
                $(document).ready(function(){
                    WEBAPP.const.figures['${figure.id}'] = {
                        id                          : '${figure.id}',
                        name                        : '${figure.name}',
                        param                       : '${figure.parameters}',
                        area                        : ${figure.areaScript},
                        onSuccessUpdateStatistics   : function (data) {
                            $('#' + '${figure.id}' + '-times').text(data.amount);
                        },
                        onErrorUpdateStatistics     : function (thr, testStatus, errorCode) {
                            BootstrapDialog.show({
                                title: 'Error',
                                message: 'Произошла ошибка при получении статистики по фигуре ${figure.name}. Код ошибки: ' + errorCode,
                                type: BootstrapDialog.TYPE_DANGER,
                                size: BootstrapDialog.SIZE_SMALL,
                                closable: false,
                                buttons: [{
                                    id: 'btn-ok',
                                    label: 'OK',
                                    cssClass: 'btn-danger',
                                    action: function(dialogRef){
                                        dialogRef.close();
                                    }
                                }]
                            });
                        }
                    };
                });
            </script>
        </c:forEach>
    </div>
<%-- Main page END --%>

    <div class="modal fade" id="mdlAreaCalculation" style="">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                   <h4 class="modal-title">Расчет площади фигуры '<span id="title"></span>'</h4>
                </div>
                <div class="modal-body" >
                    <div class="form-group">
                        <div class="col-lg-12" id="fieldsWrapper" style="padding: 0;"> <%-- container --%>  </div>
                        <input id="inpArea" type="number" class="form-control" readonly>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="text-center" style="padding: 0">
                        <button type="button" class="btn btn-primary" id="btnCompute">Расчитать</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
