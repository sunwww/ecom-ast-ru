<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<%@ attribute name="title" required="true" description="Заголовок" %>

<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="field" required="true" description="Название поля" %>

<msh:ifInRole roles="${roles}">
    <div id='${name}NewCalculationDialog' class='dialog'>
        <h2>ИМТ</h2>
        <div class="formula" id="formula${name}"> </div>
        <msh:panel>
            <label><input  disabled type="checkbox" name="body-mass-index-more-than-25${name}" value="1">
                Избыточный вес и ожирение (ИМТ &gt; 25 кг/м²)</label>
            <div id="imt-calc${name}">
                <h4>Расчет ИМТ (Индекс Массы Тела):</h4>
                <label for="imt-calc-height${name}">рост, см</label>
                <input type="number" name="height${name}" id="imt-calc-height${name}">
                <label for="imt-calc-weight${name}">вес, кг</label>
                <input type="number" name="weight${name}" id="imt-calc-weight${name}">
                <p id="imt-calc-result${name}"></p>
            </div>
        </msh:panel>
        <br>
        <tr>
            <td>
                <input class="cancel" id="cancel" value="Отмена" onclick="cancel${name}NewCalculation()" type="button">
            </td>
            <td>
                <input value="Сохранить" onclick="save${name}NewCalculation();" type="button">
            </td>
        </tr>
    </div>
    <style type="text/css">
        #${name}NewCalculation {
            visibility: hidden ;
            display: none ;
            position: absolute ;
        }
    </style>
    <script type="text/javascript" src="./dwr/interface/CalculateService.js"></script>
    <script type="text/javascript">
        var theIs${name}NewCalculationDialogInitialized = false ;
        var the${name}NewCalculationDialog = new msh.widget.Dialog($('${name}NewCalculationDialog')) ;
        var departmentId${name}=0;

        // инициализация диалогового окна
        function init${name}NewCalculationDialog() {
            theIs${name}NewCalculationDialogInitialized = true ;
        }

        // Показать
        function show${name}NewCalculation(id,create) {
            if (!theIs${name}NewCalculationDialogInitialized) {
                init${name}NewCalculationDialog() ;
            }
            the${name}NewCalculationDialog.show() ;
            getIMT${name}(id);
            departmentId${name}=id;
            document.getElementById("cancel").style.display ="";
        }

        // Отмена
        function cancel${name}NewCalculation() {
            the${name}NewCalculationDialog.hide() ;

        }
        // Сохранение данных
        function save${name}NewCalculation() {
            var prop ;
            if ("${property}"=="") prop = "record" ;
            var record = window.parent.document.getElementById(prop);
            for (var i=0; i<100; i++) {
                if (window.parent.document.getElementById('allCalc')!=null) window.parent.document.getElementById('allCalc').hide();
                if (window.parent.document.getElementById('fadeEffect')!=null) window.parent.document.getElementById('fadeEffect').hide();
            }
            if (record!=null) record.value+='Индекс массы тела: '+document.getElementById('imt-calc-result${name}').innerHTML.replace(' кг/м<sup>2</sup>','')+"\n";
            the${name}NewCalculationDialog.hide();
        }

        //Получить ИМТ (если есть)
        function getIMT${name}(medcaseId) {
            CalculateService.getIMT(medcaseId, {
                callback : function(aResult) {
                    if (aResult!='') {
                        var mas=aResult.split('#');
                        jQuery('#imt-calc-height${name}').prop( "value", mas[0]);
                        jQuery('#imt-calc-weight${name}').prop( "value", mas[1]);
                        imt${name}();
                    }
                }
            });
        }
        /* Расчет ИМТ */
        jQuery('#imt-calc-height${name}, #imt-calc-weight${name}').on('change paste keyup', function () {
            imt${name}();
        });
        function imt${name}() {
            var imtHeight = parseFloat(jQuery('#imt-calc-height${name}').val());
            var imtWeight = parseFloat(jQuery('#imt-calc-weight${name}').val());
            if (imtHeight > 0 && imtWeight > 0) {
                var imt = imtWeight / ((imtHeight*imtHeight) / 10000);
                jQuery('#imt-calc-result${name}').html(imt.toFixed(2) + ' кг/м<sup>2</sup>');
                if (imt > 25) {
                    jQuery('input:checkbox[name="body-mass-index-more-than-25${name}"]').prop( "checked", true );
                } else {
                    jQuery('input:checkbox[name="body-mass-index-more-than-25${name}"]').prop( "checked", false );
                }
            }
        }
    </script>
</msh:ifInRole>