<%@ tag pageEncoding="utf8" %>


<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="msh" uri="http://www.nuzmsh.ru/tags/msh" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="parentID" required="true" description="ИД родителя" %>

<style type="text/css">
    #${name}PrescTypesDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}PrescriptListDialog' class='dialog'>
    <h2>Выберите услугу для создания</h2>
	<div style="display: none" id="executeLabSurveyDiv">
		<table>
			<tr>
				<msh:autoComplete label="Лабораторная услуга" property="${name}NewService" vocName="labMedService" parentId="0" size="50"/>
			</tr>
			<tr><td colspan="2" style="alignment: center">
				<input type="button" value="Выбрать и внести данные" onclick="this.disabled=true;createNewPrescription()">
                <input type="button" value="Отмена" onclick="cancel${name}PrescriptList()">
			</td></tr>
		</table>
		<div id="BioIntakeRootPane"></div>
	</div>
    <div class='rootPane'>
    	<h3>Выбор вида назначения:</h3>
		<form action="javascript:">
			<table id = 'tablePrescriptList'>
			<tr>
				<td>
					<input name="add" type='button' onclick="javascript:goTo('service')" value='Добавить лабораторные назначение'>
				</td>
			</tr>
			<tr>
				<td>
					<input name="add" type='button' onclick="javascript:goTo('diagnostic')" value='Добавить диагностическое исследование'>
				</td>
			</tr>
			<tr>
				<td>
					<input name="add" type='button' onclick="javascript:goTo('operation')" value='Добавить назначение на операцию'>
				</td>
			</tr>
			<tr>
				<td>
					<input name="add" type='button' onclick="javascript:goTo('diet')" value='Добавить диету'>
				</td>
			</tr>
			<tr>
				<td>
					<input name="add" type='button' onclick="javascript:goTo('mode')" value='Добавить режим'>
				</td>
			</tr>
			<!--tr>
				<td>
					<input name="add" type='button' onclick="javascript:goTo('drug')" value='Добавить назначение лекарственного препарата'>
				</td>
			</tr-->
				<tr>
					<td>
						<input name="add" type='button' onclick="javascript:goTo('drugShort')" value='Добавить назначение наркотика'>
					</td>
				</tr>
			<tr>
				<td>
					<input name="add" type='button' onclick="javascript:goTo('cons')" value='Добавить назначение на консультацию'>
				</td>
			</tr>
			<tr>
				<td>
					<input name="view" type='button' onclick="javascript:goTo('view')" value='Просмотреть назначения'>
				</td>
			</tr>
		   <tr>
				<td>
					<input name="cancel" type='button' onclick="javascript:cancel${name}PrescriptList()" value='Ничего не надо, я передумал'>
				</td>
			</tr>
		<msh:ifInRole roles="/Policy/Mis/Prescription/ServicePrescription/CreateAndExecute">
		   <tr>
				<td>
					<input name="cancel" type='button' onclick="javascript:createAndExecutePrescription()" value='Ввести результат анализа'>
				</td>
			</tr>
		</msh:ifInRole>
			</table>

		</form>

	</div>
</div>
<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
<script type="text/javascript">
var plId ;
var parentId;
var viewWay;
var isSLSClosed = true;
var fldJson = {};
     var theIs${name}PrescriptListDialogInitialized = false ;
     var the${name}PolicyDialog = new msh.widget.Dialog($('${name}PrescriptListDialog')) ;
     var the${name}PrescriptListDialog = new msh.widget.Dialog($('${name}PrescriptListDialog')) ;

     function goTo(aValue) {
		 if (aValue=='view') window.location=viewWay;
		 else if (aValue=="cons") window.location='entityParentPrepareCreate-pres_wfConsultation.do?id='+plId;
		 else window.location = 'entityParentPrepareCreate-pres_'+aValue+'Prescription.do?id='+plId;
     }

     //получаем шаблоны по анализу
     function getTemplateByService(presId, labId) {
		 PrescriptionService.getTemplateByService(null,presId,labId,"enter${name}Result","save${name}Result", {
			 callback: function (templateStr) {
				 var n = +templateStr.substring(0,1) ;
				 if (n==0) { // не должно быть
					 $('${name}IntakeInfoTitle').innerHTML = "ВЫБОР ШАБЛОНА" ;
					 $('${name}List').value=aSmoId;
					 $('${name}IntakeRootPane').innerHTML = templateStr.substring(2) +"<br><input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}IntakeInfo(); this.disabled='true';\">";
				 } else if (n==1) {
					 var templateId =  templateStr.substring(2).split("##") ;
					 enter${name}Result(null,presId,labId,templateId[1],0,templateId[0],"save${name}Result") ;
				 } else if (n==2) {
					 alert("Нельзя так! К программистам: "+templateStr);
				 }
			 }
		 });
	 }

	 //сохранение дневника
function save${name}Result(aButton, aSmoId,aPrescriptId,aProtocolId, aTemplateId) {
	if (aButton ) aButton.disabled=true;
	var isError = false ;
	for (var ind=0;ind<fldJson.params.length;ind++) {
		var val = $('param'+fldJson.params[ind].id).value ;
		var par = fldJson.params[ind] ;
		errorutil.HideError($('param'+par.idEnter)) ;
		if (+par.type==2) {
			if (+val<1) {val='0' ;} else {
				par.valueVoc = $('param'+fldJson.params[ind].id+'Name').value ;
			}
		}
		if (par.type=='4') {
			val = val.replace(",",".") ;
			val = val.replace("-",".") ;
			var v = val.split(".") ;
			var decimalRegexp=/^(-?\d+(\.?\d+)?|\d+)$/g;
			var cntdecimal = +par.cntdecimal
			if (val!="") {
				if (decimalRegexp.test(val)) {
					if (v.length==2 && v[1].length!=cntdecimal) {
						errorutil.ShowFieldError($('param'+par.idEnter),"Необходимо ввести "+cntdecimal+" знаков после запятой") ;
						isError= true ;
					}
					if (cntdecimal>0 ) {
						if (v.length==1) {
							errorutil.ShowFieldError($('param'+par.idEnter),"Должна быть 1 точка или запятая") ;
							isError= true ;
						} else if (v.length>1 && !isNaN(v[2])) {
							errorutil.ShowFieldError($('param'+par.idEnter),"Должна быть 1 точка или запятая") ;
							isError= true ;
						}
					}
				} else {
					errorutil.ShowFieldError($('param' + par.idEnter), "Указанное значение не является числом!");
					isError = true;
				}
			}
		}
		par.value = val ;
	}
	if (+fldJson.isdoctoredit==0) {
		if (+$('paramWF').value==0) {
			isError=true ;
			errorutil.ShowFieldError($('paramWFName'),"Обязательное поле") ;
		} else {
			fldJson.workFunction=$('paramWF').value
		}
	}
	var str = JSON.stringify(fldJson);
	if (!isError) {
		PrescriptionService.saveParameterByProtocol(aSmoId,aPrescriptId,aProtocolId,str, aTemplateId, {
			callback: function () {
				checkLabControl(aPrescriptId);
				//делаем подтверждение

			}}) ;
	} else {
		if (aButton ) aButton.disabled=false;
	}
}

//подтверждение результата
function checkLabControl(aPrescriptId) {
	PrescriptionService.checkLabontrolByPrescriptionId(aPrescriptId, {
		callback: function() {alert("Данные об анализе добавлены!");window.document.location.reload();}

	}) ;
}

function enter${name}Result(aSmoId,aPrescriptId,aServiceId,aServiceName,aProtocolId,aTempId,aFunctionSave) {
	//alert(aTempId) ;
	PrescriptionService.getParameterByTemplate(aSmoId,aPrescriptId,aServiceId,aProtocolId,aTempId, {
		callback: function (aResult) {
			fldJson = JSON.parse(aResult) ;
			var cnt = +fldJson.params.length ;
			var txt = "<form><table id='tblParamProtocol'>" ;
			if (+fldJson.isdoctoredit==0) {

				var p = "WF" ;var pid=fldJson.workFunction;var pn =fldJson.workFunctionName ;
				var param = {"id":p,"idEnter":p+"Name","valueVoc":pn,"value":pid
					,"vocname":"workFunction","vocid":""
					,"shortname":"Специалист","name":"Специалист","type":"2" ,"unitname":""
				};
				txt += ${name}getFieldTxtByParam(param) ;

			}
			for (var ind=0;ind<cnt;ind++) {
				var param = fldJson.params[ind] ;
				txt += ${name}getFieldTxtByParam(param) ;
			}
			txt += "</table></form>" ;

			$('BioIntakeRootPane').innerHTML =txt
					+ "<br><input type=\"button\" id=\"paramOK\" name=\"paramOK\" value=\"Сохранить\" onclick=\" "+aFunctionSave+"(this,"+(+aSmoId)+","+(+aPrescriptId)+","+(+aProtocolId)+","+(+aTempId)+")\">"
					+ "<input type=\"button\" value=\"Отмена\" onclick=\"the${name}PrescriptListDialog.hide() ;\">";
					if (eval${name}String) {
						eval(eval${name}String);
					}
			the${name}PrescriptListDialog.hide() ;
			the${name}PrescriptListDialog.show() ;

			if (fldJson.errors.length==0) {
				if (+fldJson.isdoctoredit==0) {
					var p = "WF" ;var pid=fldJson.workFunction;var pn =fldJson.workFunctionName ;
					eval("param"+p+"Autocomlete = new msh_autocomplete.Autocomplete() ;");
					eval("param"+p+"Autocomlete.setUrl('simpleVocAutocomplete/workFunction') ;");
					eval("param"+p+"Autocomlete.setIdFieldId('param"+p+"') ;");
					eval("param"+p+"Autocomlete.setNameFieldId('param"+p+"Name') ;");
					eval("param"+p+"Autocomlete.setDivId('param"+p+"Div') ;");
					eval("param"+p+"Autocomlete.setVocKey('workFunction') ;");
					eval("param"+p+"Autocomlete.setVocTitle('"+pn+"') ;");
					eval("param"+p+"Autocomlete.build() ;");
					$("param"+p+"Name").select() ;
					$("param"+p+"Name").focus() ;
					eventutil.addEnterSupport("param"+p+"Name","param"+fldJson.params[0].idEnter) ;

				}
				for (var ind=0;ind<fldJson.params.length;ind++) {
					var param1 = fldJson.params[ind] ;

					if (ind<cnt-1) {
						var param2 = fldJson.params[ind+1] ;
						eventutil.addEnterSupport("param"+param1.idEnter,"param"+param2.idEnter) ;
					} else {
						eventutil.addEnterSupport("param"+param1.idEnter,"paramOK") ;

					}
					if (+param1.type==2) {
						eval("param"+param1.id+"Autocomlete = new msh_autocomplete.Autocomplete() ;");
						eval("param"+param1.id+"Autocomlete.setUrl('simpleVocAutocomplete/userValue') ;");
						eval("param"+param1.id+"Autocomlete.setIdFieldId('param"+param1.id+"') ;");
						eval("param"+param1.id+"Autocomlete.setNameFieldId('param"+param1.idEnter+"') ;");
						eval("param"+param1.id+"Autocomlete.setDivId('param"+param1.id+"Div') ;");
						eval("param"+param1.id+"Autocomlete.setVocKey('userValue') ;");
						eval("param"+param1.id+"Autocomlete.setVocTitle('"+param1.vocname+"') ;");
						eval("param"+param1.id+"Autocomlete.build() ;");
						eval("param"+param1.id+"Autocomlete.setParentId('"+param1.vocid+"') ;");
					}
				}
			} else {
				var txt ="<form name='frmTemplate' id='frmTemplate'>" ;
				txt += "<a target='_blank' href='diary_templateView.do?id="+fldJson.template+"'>НЕПРАВИЛЬНО ЗАПОЛНЕНЫ ДАННЫЕ ПАРАМЕНТОВ ШАБЛОНА</a>" ;
				txt +="</form>" ;
				$('BioIntakeRootPane').innerHTML =txt
						+ "<input type=\"button\" value=\"ОК\" onclick=\"cancelBioIntakeInfo()\">";
			}

			if (+fldJson.isdoctoredit>0) {

				$("param"+fldJson.params[0].idEnter).select() ;
				$("param"+fldJson.params[0].idEnter).focus() ;
			} else {
				$("param"+p+"Name").select() ;
				$("param"+p+"Name").focus() ;
				$('param'+p+'Name').className="autocomplete horizontalFill required" ;
				eventutil.addEnterSupport("param"+p+"Name","param"+fldJson.params[0].idEnter) ;

			}
		}
	}) ;
}

var eval${name}String = "";
function ${name}getFieldTxtByParam(aParam) {
	var txt = "<tr>" ;
	var type=+aParam.type;
	txt += "<td class=\"label\"><label id=\"param"+aParam.id+"Label\" for=\"param"+aParam.idEnter+"\" title='"+aParam.name ;
	if (type==1) {
		txt += '(число)' ;
	} else if (type==3) {
		txt += '(текст)' ;
	} else if (type==4) {
		txt += '(число зн. '+aParam.cntdecimal+')' ;
	} else if (type==5) {
		txt += '(текст с огр. '+aParam.cntdecimal+')' ;
	} else if (type==8) {
		txt+='(дата)';
	}
	txt += "'>" ;
	txt += aParam.shortname ;
	txt += "</label></td>" ;
	txt += "<td>" ;
	if (type==2) {
		txt += "<input id=\"param"+aParam.id+"\" name=\"param"+aParam.id+"\" type=\"hidden\" value=\""+aParam.value+"\" title=\"\" autocomplete=\"\">";
		txt += "<div>";
		txt += "<input id=\"param"+aParam.idEnter+"\" name=\"param"+aParam.idEnter+"\" type=\"text\" value=\""+aParam.valueVoc+"\" title=\""+aParam.vocname+"\" class=\"autocomplete horizontalFill\" autocomplete=\"on\">";
		txt += "<div id=\"param"+aParam.id+"Div\">";
		txt += "<span></span></div></div>";
	} else {
		txt += "<input onblur='checkFieldValue(this, "+aParam.id+")' id=\"param"+aParam.id+"\" name=\"param"+aParam.id+"\" type=\"text\" value=\""+aParam.value+"\" title=\""+aParam.name+"\" autocomplete=\"off\">";
		if (type==8) {
			eval${name}String +=" new dateutil.DateField($('param"+aParam.id+"'));"
		}

	}
	txt += "</td>" ;
	txt += "<td>" ;
	if (+aParam.type==4) {txt += " ("+aParam.cntdecimal+") ";}
	txt += aParam.unitname ;
	txt += "</td>" ;
	txt += "</tr>" ;
	return txt ;
}

/*Проверяем на минимальные / максимальные значения*/
function checkFieldValue(txtField, id) {
	var val = +jQuery(txtField).val().replace(",",".").trim();
	if (val){
		var el = getParameterById(id);
		if (el.nmin || el.nmax) {
			if (val < el.min || val > el.max) { //выходит вообще за всякие рамки
				if (! confirm ("Значение "+val +(val <el.nmin ? " меньше минимального значения "+(+el.min) : " превышает максимальное значение "+(+el.max))+", вы уверены? ")) {
					jQuery(txtField).val("");
					return;
				}
			}
			if (val < el.nmin || val > el.nmax) { //выход за пределы реф. значений
				jQuery(jQuery(txtField).parent().parent().children()[2]).html((val < el.nmin ? "<img src='/skin/images/arrow/green-down.png'>" : "<img src='/skin/images/arrow/red-up.png'>")+"" +
						" (норм "+(+el.nmin)+" - "+(+el.nmax)+")");
			} else { //значение в норме
				jQuery(jQuery(txtField).parent().parent().children()[2]).html("("+el.cntdecimal+") "+el.unitname);
			}
		}
	}
}
function getParameterById(id) {
	var ret = jQuery.grep(fldJson.params, function(el) {return el.id==id;});
	return ret ? ret[0] : {} ;
}


	 //отображаем окно в выбором услуги для создания и выполнения назначения
	 function createAndExecutePrescription () {
		the${name}PrescriptListDialog.hide() ;
		$('${name}NewService').value=22347;
		$('${name}NewServiceName').value='A26.08.027.999 МОЛЕКУЛЯРНО-БИОЛОГИЧЕСКОЕ ИССЛЕДОВАНИЕ МАЗКОВ СО СЛИЗИСТОЙ ОБОЛОЧКИ НОСОГЛОТКИ НА КОРОНАВИРУС ТОРС (SARS-COV)';
     	jQuery('.rootPane').hide();
     	jQuery('#executeLabSurveyDiv').show();
		the${name}PrescriptListDialog.show() ;

	}

	function createNewPrescription() {
     	var labId = $('${name}NewService').value ;
     	if (labId && plId) {
			PrescriptionService.createServicePrescription(labId, plId, {
     			callback: function(presId) { //создали назначение, получаем данные шаблона для него.
     				if (presId) getTemplateByService(presId, labId);

				}
			})
     		//Создаем назначение, получаем его ИД. по ИД
		} else {
     		alert('Услуга не выбрана');
		}
	}
   
     
     // Отмена 
     function cancel${name}PrescriptList() {
        the${name}PrescriptListDialog.hide() ;
     }
     
	
     function disableButtons() {
    	 var b = document.getElementsByName('add');
		 for (var i=0; i<b.length;i++) {
			 b[i].disabled=true;
		 }
     }
     // инициализация диалогового окна 
     function show${name}PrescriptList() {
         parentId=${parentID};
         PrescriptionService.isPrescriptListExists(+'${parentID}', {
             callback: function (aPresID) {
                 if (aPresID==null||aPresID=='null') {
                     showToastMessage ('Пациент выписан, назначения отсутствуют, добавление назначений невозможно!',false);
                     cancel${name}PrescriptList();
                     return;
                 } else {
                     var isMedcaseClosed = aPresID.substring(0,1);
                     if (isMedcaseClosed=='0') {
                         disableButtons();
                         showToastMessage('Пациент выписан, добавление назначений невоможно! Можно просмотреть назначения в приёмнике и сводный лист назначений.',false);
                     } else if (isMedcaseClosed=='2') {
                         showToastMessage("В визите можно создавать лист назначений в закрытом СПО текущим числом.",false);
                     }
                     plId = aPresID.substring(1);
                     PrescriptionService.isPrescriptListCanBeChangedFromSLS('${parentID}', {
                         callback: function (aRes) {
                             if (aRes==true && isMedcaseClosed!='0') {
                                 disableButtons();
                                 showToastMessage('Пациент уже в отделении, делать назначения из госпитализации запрещено! Можно просмотреть назначения в приёмнике и сводный лист назначений.',false);
                             }
                             PrescriptionService.isPrescriptListfromSLO('${parentID}', {
                                 callback: function (aMCaseType) {
                                     if (aMCaseType==true) {
                                         viewWay='entityParentList-pres_prescriptList.do?id='+parentId;
                                     }
                                     else {
                                         viewWay='entityParentView-pres_prescriptList.do?id='+plId;
                                     }

                                 }
                             });
                             the${name}PrescriptListDialog.show() ;
                         }
                     });
                 }
             }
         });
		 theIs${name}PrescriptListDialogInitialized=true;
     }
</script>