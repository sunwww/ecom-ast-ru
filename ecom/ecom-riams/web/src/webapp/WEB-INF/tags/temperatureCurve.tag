<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<%@ attribute name="name" required="true" description="Название поля Address" %>






<style type="text/css">
    #${name}CurveDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }

</style>

<div id='${name}CurveDialog' class='dialog'>
    <h2>Создание показателя температурного листа</h2>
    <div class='rootPane'>


<form>
<msh:panel guid="panel">
<msh:row>
	<span id="resultText"> </span>
</msh:row>
        <msh:row guid="b76eb-b971-441e-9a90-51707">
          <msh:textField property="takingDate" label="Дата" guid="50t19c-de41-4b70-b68f-0ft3a" />
        </msh:row>
        <msh:row guid="bffdcf-151a-46b2-ae9b-8s47d2">
          <msh:textField property="illnessDayNumber" label="День болезни " guid="034f8-dcd5-4574-9dc6-d8fef" />
          <msh:textField property="hospDayNumber" label="День пребывания в стационаре " guid="0f34f8-dcd5-4574-9dc6-dd8fef" />
        </msh:row>
        <msh:row guid="bgffdcf-151a-46b2-ae9b-8sg47d2">
          <msh:autoComplete vocName="vocDayTime" property="dayTime" guid="3td1b-8802-467d-b205-71t18" label="Время суток" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row guid="bfcf-151a-46b2-ae9b-f8s47d2">
          <msh:textField property="bloodPressureUp" label="Артериальное давление, систолическое " guid="0f8-dcd5-4574-9dc6-d8fef" />
          <msh:textField property="bloodPressureDown" label="диастолическое " guid="0ff8-dcd5-4574-9dc6-ddef" />
          <msh:textField property="pulse" label="Пульс " guid="0ff8-dcd5-4574-9dc6-dd8fef" />
        </msh:row>
        <msh:row guid="bff4fdcf-151a-46b2-ae9b-f8s47d2">
          <msh:textField property="degree" label="Температура, в градусах" guid="0e34f8-dcd5-4574-9dc6-d8fef" />
          <msh:textField property="respirationRate" label="ЧДД" guid="0f34rf8-dcd5-4574-9dc6-dd8fef" />
          <msh:textField property="weight" label="Вес " guid="0f3r4f8-dcd5-4574-9dc6-drd8fef" />
        </msh:row>
        <msh:row guid="bgffdcf5-151a-46b2-ae9b-8sg47d2">
          <msh:autoComplete vocName="vocStoolType" property="stool" guid="3btd1b-8802-467d-b205-71t18" horizontalFill="true" label="Стул" fieldColSpan="5" />
        </msh:row>
      </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" id='${name}buttonCurveOk' value='Сохранить' onclick='save${name}Curve(0)'/>
                <input type="button" id='${name}buttonCurveOk' value='Сохранить и создать новый' onclick='save${name}Curve(1)'/>
                <input type="button" value='Отменить' onclick='cancel${name}Curve()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript">
var theIs${name}CurveDialogInitialized = false ;
new dateutil.DateField($('takingDate')) ;
var the${name}CurveDialog = new msh.widget.Dialog($('${name}CurveDialog')) ;


function checkFields (aFields) {
	
	for (var i=0; i<aFields.length;i++) {
		if ($(aFields[i][0]).value == '') {
			alert ('Поле \"'+aFields[i][1]+'\" обязательно к заполнению');
			return false;
		} 
	}
	return true;
	
}
function save${name}Curve(next) {
	var requiredFields = [['takingDate','Дата'],['dayTime','Время суток'],['degree','Температура']];
	if (checkFields(requiredFields)) {
	var data = '';
	data +=$('takingDate').value +' :';
	data +=$('illnessDayNumber').value+' :';
	data +=$('hospDayNumber').value +' :';
	data +=$('dayTime').value +' :';
	data +=$('bloodPressureUp').value +' :';
	data +=$('bloodPressureDown').value +' :';
	data +=$('pulse').value +' :';
	data +=$('degree').value +' :';
	data +=$('respirationRate').value +' :';
	data +=$('weight').value +' : ';
	data +=$('stool').value ; 
	$('resultText').innerHTML = "...";
	HospitalMedCaseService.createTemperatureCurve($('id').value, data, {
		callback: function (a) {
			if (a=='1') {
				$('resultText').innerHTML = "Успешно создано";
			} else {
				$('resultText').innerHTML = "Ошибка: "+a;
			}
			if (next==1) {
				$('takingDate').value='';
				$('dayTime').value=''; $('dayTimeName').value='';
			} else {
				cancel${name}Curve();
			}
		}
	});
	
	}
}

    function show${name}Curve() {
     	   
        the${name}CurveDialog.show() ;

    }

    function cancel${name}Curve() {
        the${name}CurveDialog.hide() ;
    }




</script>



