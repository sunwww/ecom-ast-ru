<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #AddServiceDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}AddServiceDialog' class='dialog'>
    <h2>Введите реквизиты счета</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formServiceAndDiagnosis">
            <table width="100%" cellspacing="0" cellpadding="4">
                <tr>
                    <td colspan="4" align="right" width="100"><b><u>Добавить диагноз</u></b></td>
                </tr>
                <msh:row>
                    <msh:autoComplete label="МКБ" property="${name}MkbId" vocName="vocIdc10"  size="50"   horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="${name}RegistrationType" label="Тип регистрации" horizontalFill="true" fieldColSpan="1" vocName="vocDiagnosisRegistrationType" />
                </msh:row><msh:row>

                </msh:row><msh:row>
                    <msh:autoComplete vocName="vocE2FondV027" property="${name}IllnesPrimary" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
                </msh:row><msh:row>
                    <msh:autoComplete vocName="vocPriorityDiagnosis" property="${name}Priority" label="Приоритет" horizontalFill="true" />
                </msh:row>
                <tr>
                    <td align="right" width="100">Доп. код МКБ</td>
                    <td><input type="text" id="${name}DopMkb" name="${name}DopMkb" maxlength="50" size="20"></td>
                </tr>
                <tr>
                    <td colspan="4"><label><input type="checkbox" name="${name}IsMainMkb" id="${name}IsMainMkb" />Диагноз является главным в случае?</label></td>
                </tr>
                <tr>
                    <td colspan="4" align="right" width="100"><b><u>Добавить услугу</u></b></td>
                </tr>
                <msh:autoComplete property="${name}MedService" vocName="vocMedService" size="50" label="Добавить услугу"  horizontalFill="true" fieldColSpan="3"/>
                <tr>
                  <msh:textField property="${name}MedServiceDate" label="Дата услуги"/>
                </tr>
                <tr>
                    <td colspan="4"><label><input type="checkbox" name="${name}IsMainService" id="${name}IsMainService" />Услуга является главной в случае?</label></td>
                </tr>

                <tr>
                    <td><input type="button" value='Создать' id="${name}Save" onclick='javascript:save${name}Service()'/></td>
                    <td><input type="button" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}Service()'/></td>
                </tr>
            </table>
        </form>

    </div>
</div>

<script type="text/javascript">

var the${name}AddServiceDialog = new msh.widget.Dialog($('${name}AddServiceDialog')) ;
var the${name}someData;
// Показать
function show${name}AddServiceDialog() {
    new dateutil.DateField($('${name}MedServiceDate'));
    the${name}AddServiceDialog.show() ;

}

// Отмена
function cancel${name}Service() {
    the${name}AddServiceDialog.hide() ;
}
//Сохранение
function save${name}Service() {
    var json = jQuery('#formServiceAndDiagnosis').serializeArray();
    var newJson={};
    for (var i in json) {
        newJson[json[i].name]=json[i].value;
    }
    if ($('${name}MkbId').value) {
        if (!$('${name}RegistrationType').value || !$('${name}IllnesPrimary').value || !$('${name}Priority').value) {
            alert("Необходимо заполнить тип регистрации, приоритет и характер заболевания!");
            return;
}
    }
    Expert2Service.addDiagnosisAndServiceToEntry (${param.id},JSON.stringify(newJson), {
        callback: function() {alert("Успешно добавлено!");cancel${name}Service();}
    });

}
</script>