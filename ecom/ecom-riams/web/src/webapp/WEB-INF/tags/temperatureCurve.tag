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
<msh:panel>
<msh:row>
	<span id="resultText"> </span>
</msh:row>
        <msh:row>
          <msh:textField property="takingDate" label="Дата" />
        </msh:row>
        <msh:row>
          <msh:textField property="illnessDayNumber" label="День болезни " />
          <msh:textField property="hospDayNumber" label="День пребывания в стационаре " />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocDayTime" property="dayTime" label="Время суток" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="bloodPressureUp" label="Артериальное давление, систолическое " />
          <msh:textField property="bloodPressureDown" label="диастолическое " />
          <msh:textField property="pulse" label="Пульс " />
        </msh:row>
        <msh:row>
          <msh:textField property="degree" label="Температура, в градусах" />
          <msh:textField property="respirationRate" label="ЧДД" />
          <msh:textField property="weight" label="Вес " />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocStoolType" property="stool" horizontalFill="true" label="Стул" fieldColSpan="5" />
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



