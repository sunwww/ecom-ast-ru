<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ attribute name="name" description="название" required="true"%>
<%@ attribute name="property" description="свойство, куда записывать данные"  required="true" %>
<%@ attribute name="patient" description="свойство пациента"  required="true" %>
<%@ attribute name="dateFinish" description="свойство даты выписки из стационара СЛС" %>
<%@ attribute name="dateStart" description="свойство даты поступления СЛС"  required="true"%>

<style type="text/css">
    #${name}EpicrisisDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}EpicrisisDialog' class='dialog'>
    <h2>Выбор параметров</h2>
    <div class='rootPane'>
		<form name="${name}EpicrisisParameterForm">
		    <msh:panel>
		    	<msh:ifInRole roles="/Policy/Mis/MedCase/Document/External/Medservice/View">
					<input type="hidden" name="${name}ExtLabsDep" id="${name}ExtLabsDep" value="0" />
					<tr onclick="show${name}DiariesDiv()"> <td></td>
			        	<td>
							<label><input type="radio" name="${name}showTypeService" value="">Все протоколы</label>
						</td><td>
							<label><input type="radio" name="${name}showTypeService" value="LAB">Лабораторные исследования</label>
						</td><td>
							<label><input type="radio" name="${name}showTypeService" value="DIAG">Диагностические исследования</label>
						</td>
					</tr>
				<msh:row>
		            	<td></td>
				        <td onclick="format${name}Services();">
				        	<label> <input type="checkbox" id="${name}ExtLabsReg" /> Перевести в ниж.регистр</label>
				        </td>
					<td onclick="format${name}Services();">
						<label> <input type="checkbox" id="${name}ExtLabsOrder" /> Обратный порядок (от старых к новым)</label>
					</td>
			        </msh:row>
		            <msh:row>
		            	<td></td>
				        <td onclick="format${name}Services();">
							<label><input type="checkbox" name="${name}ExtLabsStr" id="${name}ExtLabsStr" checked="checked" > Преобразовать в одну строку</label>
				        </td>
			        </msh:row>
		            <msh:row>
		                <td></td>
				        <td onclick="this.childNodes[1].checked='checked';format${name}Services();">
				        	<input type="radio" name="${name}ExtLabsCode" value="0"> Показывать код и название услуги
				        </td>
				        <td onclick="this.childNodes[1].checked='checked';format${name}Services();">
				        	<input type="radio" name="${name}ExtLabsCode" checked="checked" value="1"> Показывать название услуги
				        </td>
				        <td onclick="this.childNodes[1].checked='checked';format${name}Services();">
				        	<input type="radio" name="${name}ExtLabsCode"  value="2"> Не показывать услугу
				        </td>
					</msh:row>
		            <msh:row>
		                <td></td>
				        <td onclick=" format${name}Services();">
				        	<label><input type="checkbox" name="${name}ExtLabsNoIntake" id="${name}ExtLabsNoIntake" checked="checked"> Убрать инф. о заборе</label>
				        </td>
					</msh:row>
					<msh:row>
						<td></td>
						<td onclick=" format${name}Services();">
							<label><input type="checkbox" name="${name}NoLabTechnik" id="${name}NoLabTechnik" checked="checked"> Убрать инф. о лаборанте</label>
						</td>
					</msh:row>
					<msh:row>
						<td></td>
						<td onclick=" format${name}Services();">
							<label><input type="checkbox" name="${name}NoRefValues" id="${name}NoRefValues" checked="checked"> Убрать инф. о реф. инт.</label>
						</td>
					</msh:row>
		            <msh:row>
		                <td></td>
				        <td onclick=" format${name}Services();">
				        	<label><input type="checkbox" name="${name}ExtLabsDate" id="${name}ExtLabsDate" > Добавить дату выполнения услуги</label>
				        </td>
					</msh:row>
		    	</msh:ifInRole>

		        <msh:row>
		            <msh:checkBox property="${name}DefaultInfo" label="Общие сведения о пациенте" fieldColSpan="2"/>
		        </msh:row>
		        <msh:row>
		            <msh:checkBox property="${name}Operations" label="Операции" fieldColSpan="2"/>
		        </msh:row>
				<msh:row>
					<td colspan="6">
						<input type="button" name="${name}EpicrisisOk" id='${name}EpicrisisOk' value='OK' onclick='save${name}Epicrisis()'/>
						<input type="button" value='Отменить' onclick='cancel${name}Epicrisis()'/>
					</td>
				</msh:row>
		        <msh:row><td colspan="5">
		        <div id='${name}diariesDiv' style='display:none'>
		        </div></td>
		        </msh:row>
		    </msh:panel>
		        <msh:row>
		            <td colspan="6">
		                <input type="button" name="${name}EpicrisisOk" id='${name}EpicrisisOk' value='OK' onclick='save${name}Epicrisis()'/>
		                <input type="button" value='Отменить' onclick='cancel${name}Epicrisis()'/>
		            </td>
		        </msh:row>
		</form>
		
		</div>
</div>
<%--<script type='text/javascript' src='./dwr/interface/EpicrisisService.js'></script> --%>
<script type="text/javascript">
     var theIs${name}EpicrisisDialogInitialized = false ;
     var the${name}EpicrisisDialog = new msh.widget.Dialog($('${name}EpicrisisDialog')) ;
	function escapeHtml(aText, lowerCase, oneString, noIntake, noLabTechnik,NoRefValues) {
	    if (true==lowerCase) aText = aText.toLowerCase();
	    if (true==noIntake) aText = aText.replace(/([Зз]абор)[абор имтелпзвдн:]*\d{2}.\d{2}.\d{4} \d{2}:\d{2}/g,'');
	    if (true==noLabTechnik) {
	        aText = aText.replace(/(Лаборант ).*/g,'');
            aText = aText.replace(/(лаборант ).*/g,'');
        }
        if (true==NoRefValues) {
            aText = aText.replace(/▲/g,'');
            aText = aText.replace(/▼/g,'');
            aText = aText.replace(/\((реф. инт: .*)/g,'');
        }
		aText = aText.replace(/&/g, '&amp;')
            .replace(/>/g, '&gt;')
            .replace(/</g, '&lt;')
            .replace(/"/g, '&quot;');
		aText = aText.replace(/[\r\n]/g,false==oneString ? '<br>' :' ');
		return aText;

	}
     function unEscapeHtml(aText) {
		aText = aText.replace(/<br>/g,'\r');
         return aText.replace('&amp;',/&/g)
             .replace( '&gt;',/>/g)
             .replace('&lt;',/</g)
             .replace('&quot;',/"/g);
     }
     //Отображаем лаб. исследования в нужном виде
	 var servicesList =[]; // будем хранить дневники здесь

	 function format${name}Services() {
	 	if (jQuery('[name=${name}showTypeService]:checked')) {
			var p ='<table border=\'1\' ><tr> <td></td><td>Дата</td><td>Дневник</td></tr><tbody id=\'diariesTable\'>';
			//формирование дневников лаборатории
			var showService = jQuery('[name=${name}ExtLabsCode]:checked').val()=='0';
			var showServiceName = jQuery('[name=${name}ExtLabsCode]:checked').val()!='2';
			var isOldFirst = jQuery('#${name}ExtLabsOrder').is(':checked');
			var makeOneString = jQuery('#${name}ExtLabsStr').is(':checked');
			var lowerCase = jQuery('#${name}ExtLabsReg').is(':checked');
			var noIntake = jQuery('#${name}ExtLabsNoIntake').is(':checked');
            var noLabTechnik = jQuery('#${name}NoLabTechnik').is(':checked');
            var NoRefValues = jQuery('#${name}NoRefValues').is(':checked');
			var addDate = jQuery('#${name}ExtLabsDate').is(':checked');
			p+="<tr><td colspan='3'><label><input type='checkbox' onclick='checkAllServices(this.checked)'>Отметить все</label></td></tr>";
			for (var i=0;i<servicesList.length;i++){
				var diary = isOldFirst ? servicesList[servicesList.length-(i+1)] : servicesList[i];
				p+='<tr>';
				p+='<td><input type=\'checkbox\' class=\'diary${name}Checkbox\' name=\'diary'+diary.id+'\'></td>';
				p+='<td>'+diary.recordDate+" "+diary.recordTime+'</td>';
				p+='<td id='+diary.id+'>'+escapeHtml(
						(true===addDate ? diary.recordDate+" " : "") +
						(true===showService ? diary.serviceCode+" " : "")
						+(true===showServiceName ? diary.serviceName : "")+" "+diary.recordText
						,lowerCase,makeOneString, noIntake, noLabTechnik, NoRefValues)+'</td>';
				p+='</tr>';
			}
			p+='</tbody></table>';

			$('${name}diariesDiv').style.display='block';
			$('${name}diariesDiv').innerHTML=p;
		}
	 }
	 function checkAllServices(isShow) {
	 	jQuery('.diary${name}Checkbox').prop('checked',isShow );

	 }

     function show${name}DiariesDiv() {
		HospitalMedCaseService.getDiariesByHospital($('id').value, jQuery('[name=${name}showTypeService]:checked').val() ,{
			callback: function (aResult) {
				servicesList = JSON.parse(aResult);
				the${name}EpicrisisDialog.hide() ;
					format${name}Services();
				the${name}EpicrisisDialog.show() ;
			}});
     }
     // Показать
     function show${name}Epicrisis() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}EpicrisisDialogInitialized) {
         	init${name}Epicrisis() ;

         } 
         
         the${name}EpicrisisDialog.show() ;
		if ($('${name}Labs')) {
			$("${name}Labs").focus() ;
			$("${name}Labs").select() ;
		} else {
			$("${name}Operations").focus() ;
			$("${name}Operations").select() ;
		}
         //setFocusOnField('${name}tempProtCategoryName' );

     }

     // Отмена
     function cancel${name}Epicrisis() {
         the${name}EpicrisisDialog.hide() ;
     }

     // Сохранение данных
     function save${name}Epicrisis() {
		 if (document.getElementsByName("stac_sslDischargeForm") !=null) {
			 try {
				 localStorage.setItem("stac_sslDischargeForm"+";"+medCaseId.value+";"+document.getElementById('current_username_li').innerHTML, $('dischargeEpicrisis').value);
			 }
			 catch (e) {}
		 }
		 get${name}OperationsInfo() ;
     }

     function get${name}OperationsInfo() {
     	if ($('${name}Operations').checked) {
			HospitalMedCaseService.getOperations($('${patient}').value,$('${dateStart}').value
				,$('${dateFinish}').value, {
					callback: function(aString) {
						$('${property}').value += "\n" ;
						$('${property}').value += aString ;
						get${name}DiariesInfo() ;
					}
			} ) ;
		} else {
			get${name}DiariesInfo() ;
		}
     }

     function get${name}DiariesInfo() {
    	 if ($('diariesTable')) {
    		 var res = '';
    		 var rows = $('diariesTable').childNodes;
    		 for (var i=0;i<rows.length;i++){
    			 var td = rows[i].childNodes;
    			// alert ('tDD = '+td[2].innerHTML)
    			 if (td[0].childNodes[0].checked){
    			 	res+=unEscapeHtml(td[2].innerHTML)+'\n\n';
    			 }
    		 }
    		 $('${property}').value+=res;
    	 }
    		 get${name}DefaultInfo() ;
     }
     
     function get${name}DefaultInfo() {
       	if ($('${name}DefaultInfo').checked) {
      		var frm = document.forms["${name}EpicrisisParameterForm"];
      		var reg = getCheckedRadio(frm,"${name}DefaultInfo") ;
 			HospitalMedCaseService.getPatientDefaultInfo($('${patient}').value,$('${dateStart}').value
 				,$('${dateFinish}').value, {
 					callback: function(aString) {
 						$('${property}').value = aString +"\n"+$('${property}').value;
 						the${name}EpicrisisDialog.hide() ;
 						$('${property}').focus() ;
 						
 					}
 			} ) ;
 		} else {
 			the${name}EpicrisisDialog.hide() ;
 			$('${property}').focus() ;
 		}
      }
     // инициализация диалогового окна выбора шаблона протокола
     function init${name}Epicrisis() {
         theIs${name}EpicrisisDialogInitialized = true ;
     }
</script>
