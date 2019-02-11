<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>



<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp"
	flush="true">
	<tiles:put name="style" type="string">
		<style type="text/css">
		.hide {
    display: none;
}
.protocols {
	left: 0px;
	width: 99%;
	top: 0px;
	height: 55em;
}

#epicriPanel {
	width: 100%;
}

.record {
	width: 100%;
}
</style>


	</tiles:put>

	<tiles:put name='body' type='string'>
		<msh:form action="entityParentSaveGoSubclassView-smo_visitProtocol.do"
			defaultField="dateRegistration" guid="b55hb-b971-441e-9a90-5155c07"
			fileTransferSupports="true">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="username" />
			<msh:hidden property="date" />
			<msh:hidden property="time" />
			<msh:hidden property="printDate" />
			<msh:hidden property="printTime" />
			<msh:hidden property="templateProtocol" />
			<msh:hidden property="medCase" />
			<msh:hidden property="params" />
			<msh:hidden property="specialist" />

			<msh:ifFormTypeIsView formName="smo_visitProtocolForm">
				<msh:hidden property="record" />
			</msh:ifFormTypeIsView>
			<msh:panel colsWidth="1%,1%,1%,1%,1%,1%,65%">
				<msh:row>
					<msh:textField label="Дата" property="dateRegistration" fieldColSpan="1"/>
					<msh:textField label="Время" property="timeRegistration" fieldColSpan="1"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="type" fieldColSpan="3"
						label="Тип протокола" horizontalFill="true"
						vocName="vocTypeProtocol" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="mode" fieldColSpan="3" label="Режим"
						horizontalFill="true" vocName="vocProtocolMode" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="state" fieldColSpan="3"
						label="Состояние больного" horizontalFill="true"
						vocName="vocPhoneMessageState" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService" fieldColSpan="3"
						horizontalFill="true" vocName="medServiceForSpec" />

				</msh:row>
				<msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
				<msh:row>
					<msh:checkBox property="isCreateDiagnosis" fieldColSpan="2" label="Добавить диагноз"/>
				</msh:row>
				
				<msh:row  styleId="rwCreateDiagnosis1">
					<msh:separator  colSpan="6" label="Сведения о диагнозе"/>
				</msh:row>
					<msh:row  styleId="rwCreateDiagnosis2">
						<msh:autoComplete property="diagnosisRegistrationType" label="Тип регистрации" horizontalFill="true" fieldColSpan="1" 

vocName="vocDiagnosisRegistrationType" guid="1ecf26b7-d071-4abc-93ae-c52af4ae368b" />
						<msh:autoComplete vocName="vocPriorityDiagnosis" property="diagnosisPriority" label="Приоритет" guid="e28f35fc-fe25-4968-
bf2f-d1fe4661349e" horizontalFill="true" />
					</msh:row>
				<msh:row styleId="rwCreateDiagnosis3">
					<msh:autoComplete vocName="vocIdc10" property="diagnosisIdc10" label="Код МКБ-10" guid="e36df3bf-fe77-4096-a082-51016fc2baad" 
fieldColSpan="3" horizontalFill="true" />
				</msh:row>
				<msh:row styleId="rwCreateDiagnosis4">
					<msh:autoComplete vocName="vocIllnesPrimary" property="diagnosisIllnessPrimary" label="Характер заболевания" horizontalFill="true" 
fieldColSpan="3"/>
				</msh:row>
				<msh:row  styleId="rwCreateDiagnosis5">
					<msh:textArea property="diagnosisText" label="Наименование" guid="c0a86a5e-34ff-46f3-984b-5ecbd2749760" fieldColSpan="5" rows="2" 

horizontalFill="true" />
				</msh:row>
				
					<msh:row>
						<td colspan="3" align="right">
						<input type="button" style="display: none" name="btnEditProt2" id="btnEditProt2"
							value="Редактировать параметры" onClick="showTemplateForm($('templateProtocol').value);" />

							<input id="usualCalcs" class="hide" type="button" value="Калькуляторы и шкалы риска" onClick="showallCalc(medCaseId,1)"/>

							<!--input id="SKNF" class="hide" type="button" value="Вычисление СКФ" onClick="showMyNewCalculation(medCaseId,1)"/-->


							<input type="button" value="Шаблон" onClick="showtmpTemplateProtocol()"/>
							<input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">


						</td>
					</msh:row>
				</msh:ifFormTypeIsNotView>
				<msh:row>
					<msh:textArea property="record" label="Текст:" size="100" rows="25"
						fieldColSpan="8" guid="b6ehb-b971-441e-9a90-519c07"/>

				</msh:row>

				<msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
					<msh:row>
						<td colspan="3" align="right"><input type="button" style="display: none" name="btnEditProt1" id="btnEditProt1"
						value="Редактировать параметры" onClick="showTemplateForm($('templateProtocol').value);" />
              			<input type="button" value="Шаблон" onClick="showtmpTemplateProtocol()"/>
						<input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()"></td>
						<tags:keyWord name="record" service="KeyWordService" methodService="getDecryption" />
					</msh:row>
				</msh:ifFormTypeIsNotView>
				<msh:row>
					<msh:textArea property="journalText" fieldColSpan="8" rows="2"
						size="100" label="Принятые меры (для журнала)" />
				</msh:row>
				<msh:ifFormTypeIsView formName="smo_visitProtocolForm">

					<msh:row>
						<msh:textField property="date" label="Дата создания"
							viewOnlyField="true" />
						<msh:textField property="time" label="Время" viewOnlyField="true" />
						<msh:textField property="username" label="Пользователь"
							viewOnlyField="true" />
					</msh:row>
					<msh:row>
						<msh:label property="editDate" label="Дата редак." />
						<msh:label property="editTime" label="Время редак." />
						<msh:label property="editUsername" label="Пользователь" />
					</msh:row>
					<msh:row>
						<msh:textField property="printDate" label="Дата печати"
							viewOnlyField="true" />
						<msh:textField property="printTime" label="Время"
							viewOnlyField="true" />
					</msh:row>
				</msh:ifFormTypeIsView>
				<msh:ifFormTypeIsCreate formName="smo_visitProtocolForm">
					<msh:row>
						<td><input type="button"
							onclick="this.form.action='entityParentSaveGoSubclassView-smo_draftProtocol.do';removeFromStorage();this.form.submit();"
							value="Сохранить как черновик" /></td>

					</msh:row>

				</msh:ifFormTypeIsCreate>
        <msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
                 <msh:hidden property="editUsername"/>
        </msh:ifFormTypeIsNotView>
                <msh:row>
	                <msh:submitCancelButtonsRow colSpan="3"  functionSubmit="" />
                </msh:row>
			</msh:panel>
		</msh:form>
		<tags:mis_login name="Login" />
		<tags:stac_selectPrinter name="Select"
								 roles="/Policy/Config/SelectPrinter" />

		<msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
			<tags:templateProtocol idSmo="smo_visitProtocolForm.medCase"
								   version="Visit" name="tmp" property="record"
								   voc="protocolVisitByPatient" />
		</msh:ifFormTypeIsNotView>
	</tiles:put>

	<tiles:put name='side' type='string'>
		<msh:sideMenu>
			<tags:template_new_diary name="newTemp"
									 roles="/Policy/Diary/Template/Create" field="record" title="Создание шаблона"/>
			<tags:calculation name="My" roles="/Policy/Mis/Calc/Calculation/Create" field="record" title=""/>
			<msh:ifFormTypeIsCreate formName="smo_visitProtocolForm">
				<tags:allCalcs name="all" roles="/Policy/Mis/Calc/Calculation/Create" medCaseId="${param.id}"/>
			</msh:ifFormTypeIsCreate>

			<msh:ifFormTypeIsView formName="smo_visitProtocolForm">
				<tags:mis_protocolTemplateDocumentList name="Print" />
				<msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Edit" key="ALT+2"
							  params="id" action="/entityParentEdit-smo_visitProtocol"
							  name="Редактировать" />

			</msh:ifFormTypeIsView>

			<msh:ifFormTypeAreViewOrEdit formName="smo_visitProtocolForm">
				<msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Delete"
							  key='ALT+DEL' params="id"
							  action="/entityParentDeleteGoSubclassView-smo_visitProtocol"
							  name="Удалить" confirm="Вы действительно хотите удалить?" />

				<msh:sideLink action="/entityPrepareCreate-sec_userPermission.do?type=1&ido=${param.id}"
							  name="Добавить разрешение на редактирование протокола"
							  title="Добавить разрешение на редактирование протокола"
							  roles="/Policy/Jaas/Permission/User/Create" />
			</msh:ifFormTypeAreViewOrEdit>
		</msh:sideMenu>

		<msh:ifFormTypeIsView formName="smo_visitProtocolForm">
			<msh:sideMenu title="Печать">
				<%--     <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintProtocol"
    	name="Печать дневника"
    	action='/javascript:printProtocol(".do")' title='Печать дневника' /> --%>
				<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintProtocol"
							  name="Печать дневника"
							  action='/javascript:showPrintProtocolTemplate()'
							  title='Печать дневника' />

				<msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintProtocol"
							  name="Добавить внешний документ"
							  action='/javascript:createExternalDocument()'
							  title='Добавить внешний документ' />

			</msh:sideMenu>
		</msh:ifFormTypeIsView>
	</tiles:put>

	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Patient" beginForm="smo_visitProtocolForm"/>
	</tiles:put>

	<tiles:put name='javascript' type='string'>



		<script type="text/javascript">
            var isDMS=false;
            function printProtocol() {
                HospitalMedCaseService.getPrefixByProtocol(${param.id},
                    {
                        callback: function(prefix) {
                            if (prefix==null) prefix="" ;
                            initSelectPrinter("print-protocol"+prefix+".do?m=printProtocol&s=HospitalPrintService&id=${param.id}",1)
                            //window.location.href="print-protocol"+prefix+".do?m=printProtocol&s=HospitalPrintService&id=${param.id}" ;

                        }
                    }
                )
            }

		</script>

		<msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
			<script type="text/javascript">
                var oldaction = document.smo_visitProtocolForm.action;
                var medCaseId = $('medCase').value;
                var currentUsername = jQuery('#current_username_li').html();
                document.smo_visitProtocolForm.action="javascript:save_form()";

                function getFromStorage() {
                    try{return localStorage.getItem("smo_visitProtocolForm"+";"+medCaseId+";"+currentUsername, $('record').value);} catch (e) { alert(e);}
                }

                function saveToStorage() {
                    try {localStorage.setItem("smo_visitProtocolForm"+";"+medCaseId+";"+currentUsername, $('record').value);}catch(e) {}
                }
                function removeFromStorage() {
                    try {localStorage.removeItem("smo_visitProtocolForm"+";"+medCaseId+";"+currentUsername);}catch (e) {alert("remove ex"+e);}
                }

                eventutil.addEventListener($('record'), "keyup", function(){saveToStorage();}) ;
                eventutil.addEventListener($('record'), "input", function(){saveToStorage();}) ;
                eventutil.addEventListener($('record'), "blur", function(){saveToStorage();}) ;
                eventutil.addEventListener($('record'), "paste", function(){saveToStorage();}) ;

          		onCreateDiagnosis(true) ;
				eventutil.addEventListener($('isCreateDiagnosis'), 'click', onCreateDiagnosis) ;
		        eventutil.addEventListener($('isCreateDiagnosis'), 'focus', onCheckBoxFocus) ;
		        eventutil.addEventListener($('isCreateDiagnosis'), 'blur', onCheckBoxBlur) ;
		        eventutil.addEnterSupport('isCreateDiagnosis', 'record') ;

		        function onCreateDiagnosis(aFocus) {
		        	var isCreateDiagnosis = $('isCreateDiagnosis').checked ;
					if (aFocus) {} else {aFocus=false;}
					for (var i=1;i<6;i++){
						showRow('rwCreateDiagnosis'+i, isCreateDiagnosis) ;
					}
					if (aFocus) {
						try {
							if (isCreateDiagnosis) {
								$('diagnosisRegistrationTypeName').select() ;
								$('diagnosisRegistrationTypeName').focus() ;
								eventutil.addEnterSupport('isCreateDiagnosis', 'diagnosisRegistrationTypeName')

							} else {
								$('record').select() ;
								$('record').focus() ;
								eventutil.addEnterSupport('isCreateDiagnosis', 'record') ;
							}
						} catch (e) {}
					}
		        }

		        function onCheckBoxFocus() {
					try {
						$(this.id+'Label').style.border='1px dotted black' ;
					} catch(e) {}
				}

				function onCheckBoxBlur() {
					try {
						$(this.id+'Label').style.border='none' ;
					} catch(e) {}
				}
    	
				function showRow(aRowId, aCanShow, aField ) {
		    		//alert(aRowId) ;
					try {
						//alert( aCanShow ? 'table-row' : 'none') ;
						$(aRowId).style.display = aCanShow ? 'table-row' : 'none' ;
					} catch (e) {
						// for IE
						//alert(aCanShow ? 'block' : 'none') ;
						try{
						$(aRowId).style.display = aCanShow ? 'block' : 'none' ;
						}catch(e) {}
					}	
				}
    			var flag=0;

				function save_form() {
                    if (typeof isDMS  == 'undefined') {
                        HospitalMedCaseService.getIfPrivateInsurance(${param.id},true,{
                                callback: function(res) {
                                    isDMS=(res=="1");
                                    checkAndSave();
                            }}
                        );
                    }
					else checkAndSave();
                }
                function checkAndSave() {
                    var flag=false;
                    <msh:ifFormTypeIsCreate formName="smo_visitProtocolForm">
                    flag=(!isDMS || (isDMS && document.getElementById("medServiceName").value != ""
                        && document.getElementById("typeName").value != ""
                        && document.getElementById("diagnosisPriorityName").value != ""
                        && document.getElementById("diagnosisIdc10Name").value != ""
                        && document.getElementById("diagnosisRegistrationTypeName").value != ""
                        && document.getElementById("diagnosisIllnessPrimaryName").value != ""));
                    </msh:ifFormTypeIsCreate>
                    <msh:ifFormTypeAreViewOrEdit formName="smo_visitProtocolForm">
                    flag=(!isDMS || (isDMS && document.getElementById("medServiceName").value != ""
                        && document.getElementById("typeName").value != ""));
                    </msh:ifFormTypeAreViewOrEdit>
                    if (flag) {
                        TemplateProtocolService.getUsername({
                                callback: function (aValue) {
                                    if (aValue != "") {
                                        removeFromStorage();
                                        var frm = document.smo_visitProtocolForm;
                                        frm.action = oldaction;
                                        frm.submit();
                                    } else {
                                        $('submitButton').disabled = false;
                                        if (confirm("Возникли проблемы с авторизацией. Вы хотите ввести логин и пароль в новом окне?")) {
                                            showLoginAutorization();
                                        }
                                        ;
                                    }
                                }
                            }
                        );
                    }
                    else {
                        <msh:ifFormTypeIsCreate formName="smo_visitProtocolForm">
                        alert("Поток обслуживания ДМС - при создании проотокола необходимо указать мед. услугу, тип протокола и данные по диагнозу!");
                        </msh:ifFormTypeIsCreate>
                        <msh:ifFormTypeAreViewOrEdit formName="smo_visitProtocolForm">
                        alert("Поток обслуживания ДМС - при редактировании протокола необходимо указать мед. услугу и тип протокола!");
                        </msh:ifFormTypeAreViewOrEdit>
                        $('submitButton').disabled = false;
                        $('submitButton').value = (location.href.indexOf('entityParentPrepareCreate')==-1)? 'Сохранить изменения':'Создать';
                    }
				}

   if ($('templateProtocol').value>0) {
	   $('btnEditProt1').style.display='inline' ;
	   $('btnEditProt2').style.display='inline' ;
   }

    function checktime() {
    	if (confirm('Вы хотите сохранить дневник?')) {
    		if (thetmpIntakeInfoDialogInit) {
    			savetmpIntakeInfo();
    		}
    		oldaction='entityParentSaveGoEdit-smo_visitProtocol.do';
    		document.smo_visitProtocolForm.submit() ;
    	}else {setTimeout(checktime,600000); }
    }

	try {
		var text = localStorage.getItem("smo_visitProtocolForm" + ";" + medCaseId + ";" + currentUsername);
		if (text != null) {
			if (confirm('Обнаружен несохранённый протокол. Восстановить?')) {
				$('record').value = text;
			}
			removeFromStorage();
		}
	} catch (e) {}

	function saveCookie() {
		if (($('record').value.replace(/|\s+|\s+$/gm,''))!="") setCookie("protocol", $('record').value) ;
	}

	function getCookie(name) {
		var matches = document.cookie.match(new RegExp(
			"(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
		));
		return matches ? decodeURIComponent(matches[1]) : undefined;
	}

	function setCookie(name, value, options) {
		options = options || {};

		var expires = options.expires;

		if (typeof expires == "number" && expires) {
			var d = new Date();
			d.setTime(d.getTime() + expires * 1000);
			expires = options.expires = d;
		}
		if (expires && expires.toUTCString) {
			options.expires = expires.toUTCString();
		}

		value = encodeURIComponent(value);

		var updatedCookie = name + "=" + value;

		for (var propName in options) {
			updatedCookie += "; " + propName;
			var propValue = options[propName];
			if (propValue !== true) {
				updatedCookie += "=" + propValue;
			}
		}

		document.cookie = updatedCookie;
	}

	function deleteCookie(name) {
		setCookie(name, "", {
			expires: -1
		})
	}

	function setMedServiceParent() {
		medServiceAutocomplete.setParentId($('specialist').value+"#"+$('dateRegistration').value);
	}
	eventutil.addEventListener($('dateRegistration'),"change", function (){setMedServiceParent();});
	setMedServiceParent();

	var isChangeSizeEpicrisis=1 ;
	function changeSizeEpicrisis() {
		if (isChangeSizeEpicrisis==1) {
			Element.addClassName($('record'), "protocols") ;
			if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Уменьшить' ;
			isChangeSizeEpicrisis=0 ;
		} else {
			Element.removeClassName($('record'), "protocols") ;
			if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Увеличить' ;
			isChangeSizeEpicrisis=1;
		}
	}
	eventutil.addEventListener($('record'), "dblclick",
		function() {
			changeSizeEpicrisis() ;
		}) ;

                var ishosp=0;

                function getDtype(){
                    TemplateProtocolService.getDtypeMedCase($('medCase').value,{
                        callback: function(res) {
                            var aResult = res.split('#') ;
                            aDtype=aResult[0];
                            ifCharged=aResult[1];
                            if (aDtype != null && aDtype == "HospitalMedCase") {
                                ishosp = 1;
                                $('stateName').className = "autocomplete horizontalFill required";
                                $('typeName').className = "autocomplete horizontalFill required";
                                $('journalText').className = "required maxHorizontalSize";
                                $('diagnosisRegistrationTypeName').className = "autocomplete horizontalFill required";
                                $('diagnosisPriorityName').className = "autocomplete horizontalFill required";
                                $('diagnosisIdc10Name').className = "autocomplete horizontalFill required";
                                $('diagnosisIllnessPrimaryName').className = "autocomplete horizontalFill required";
                                if (ifCharged != null && ifCharged == "CHARGED") {
                                    medServiceAutocomplete.setUrl('simpleVocAutocomplete/medServiceForSpecStacCharged');
                                }
                                else {
                                    medServiceAutocomplete.setUrl('simpleVocAutocomplete/medServiceForSpecStac');
                                }
                            } else if (aDtype != null && aDtype == "DepartmentMedCase") {
                                ishosp = 1;
                                $('typeName').className = "autocomplete horizontalFill required";
                                $('stateName').className = "autocomplete horizontalFill required";
                                if (ifCharged != null && ifCharged == "CHARGED") {
                                    medServiceAutocomplete.setUrl('simpleVocAutocomplete/medServiceForSpecStacCharged');
                                }
                                else  {
                                    medServiceAutocomplete.setUrl('simpleVocAutocomplete/medServiceForSpecStac');
                                }
                            } else {
                                if (ifCharged != null && ifCharged == "CHARGED") {
                                    medServiceAutocomplete.setUrl('simpleVocAutocomplete/medServiceForSpecCharged');
                                }
                            }
                        }
                    });
                }
                getDtype();


			</script>


			<msh:ifFormTypeAreViewOrEdit formName="smo_visitProtocolForm">
				<script type="text/javascript">
						if ($('dateRegistration').value!="") {setFocusOnField('record') ;

							onload=function (){
								$('record').blur()
								$('record').focus();
								$('record').selectionStart=$('record').value.length ;
							}
						}
						isEditable($('id').value);

                        TemplateProtocolService.isCanEditProtocol($('id').value,$('username').value,
                            {
                                callback: function(aString) {
                                    //alert(aString);
                                    if (+aString>0) {} else {
                                        alert('У Вас стоит ограничение на редактрование данного протокола!');
                                        window.location.href= "entityParentView-smo_visitProtocol.do?id=${param.id}";
                                    }
                                }
                            });
				</script>
            <msh:ifFormTypeIsNotView formName="smo_visitProtocolForm">
                <script type="text/javascript">
                    //Milamesher 18102018 #122
                    HospitalMedCaseService.getIfPrivateInsurance(${param.id},false,{
                            callback: function(res) {
                                if (res=="1") {
                                    isDMS=true;
                                    document.getElementById("medServiceName").className = "autocomplete horizontalFill required";
                                    document.getElementById("typeName").className = "autocomplete horizontalFill required";
                                    document.getElementById("isCreateDiagnosis").setAttribute("disabled",true);
                                }
                            }
                        }
                    );
                </script>
            </msh:ifFormTypeIsNotView>
			</msh:ifFormTypeAreViewOrEdit>

			<msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/NoCheckTime">
				<script type="text/javascript">
                    setTimeout(checktime,600000) ;
				</script>
			</msh:ifNotInRole>


		</msh:ifFormTypeIsNotView>

		<msh:ifFormTypeAreViewOrEdit formName="smo_visitProtocolForm">
			<script type="text/javascript">
   	 			function createExternalDocument() {
	    			window.location.href = 'medcaseExternalDocument-import.do?id='+$('medCase').value;
	    		}
    	</script>
		</msh:ifFormTypeAreViewOrEdit>

		<msh:ifFormTypeIsCreate formName="smo_visitProtocolForm">
            <script type="text/javascript">
				//Milamesher 27122018 #135 - вывод суток
				<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShowDaysInDiary">
				if ($('record').value=='') {
                HospitalMedCaseService.getMedcaseDtypeById(${param.id},{
                        callback: function(res) {
                            if (res=="0") {
                                HospitalMedCaseService.getSlsCountDays(${param.id}, {
                                    callback: function (res2) {
                                        if (res2!="") {
                                            if (res2==0) res2=1;
                                            $('record').value+="С начала СЛС: " + res2+" сутки\n";
                                        }
                                    }});
							}
							else if (res=="1") {
                                HospitalMedCaseService.getSloCountDays(${param.id}, {
                                    callback: function (res) {
                                        if (res!="##") {
                                            var cnts=res.split('#');
                                            if (cnts.length==2) {
                                                var rescnt1=cnts[0];
                                                var rescnt2=cnts[1];
                                                if (rescnt1==0) rescnt1=1;
                                                if (rescnt2==0) rescnt2=1;
                                                $('record').value += "С начала СЛС: " + rescnt1 + " сутки\n";
                                                $('record').value += "С начала СЛО: " + rescnt2 + " сутки\n";
                                            }
                                        }
                                    }});
                            }
                        }});
				}
                </msh:ifInRole>
			   //Milamesher 18102018 #122
                HospitalMedCaseService.getIfPrivateInsurance(${param.id},true,{
                        callback: function(res) {
                            if (res=="1") {
                                isDMS=true;
                                document.getElementById("medServiceName").className = "autocomplete horizontalFill required";
                                document.getElementById("typeName").className = "autocomplete horizontalFill required";
                                document.getElementById("isCreateDiagnosis").click();
                                document.getElementById("isCreateDiagnosis").setAttribute("disabled",true);
                                $('diagnosisRegistrationTypeName').className = "autocomplete horizontalFill required";
                                $('diagnosisPriorityName').className = "autocomplete horizontalFill required";
                                $('diagnosisIdc10Name').className = "autocomplete horizontalFill required";
                                $('diagnosisIllnessPrimaryName').className = "autocomplete horizontalFill required";
                            }
                        }
                    }
                );
            </script>
			<msh:ifInRole roles="/Policy/Mis/Calc/Calculation/Create">
				<script type="text/javascript">
				   /*var btn = document.querySelector('#SKNF');
				   btn.className = "";*/
				   var btn = document.querySelector('#usualCalcs');
                   btn.className = "";
				   flag=1;
				   //Milamesher 10122018 - больше не нужна при загрузке
				   /*function CalcService(){
						CalculateService.getCountDiary(medCaseId, {
							callback : function(aResult) {
							if(parseInt(aResult)==0 && parseInt(ishosp)==1) {
							showMyNewCalculation(medCaseId,0);
							}
						}});
				   }*/
				   getDtype();
				   //CalcService();
			   </script>
		</msh:ifInRole>

		<script type="text/javascript">
			if(flag==0){
    			if ($('record').value=="" && confirm("Вы хотите создать дневник на основе шаблона?")) {
    				showtmpTemplateProtocol() ;
    			} else {
    				if ($('dateRegistration').value!="") setFocusOnField('record') ;
    			}
    		}
        	diagnosisIdc10Autocomplete.addOnChangeCallback(function() {
            	setDiagnosisText('diagnosisIdc10','diagnosisText');
        	});
			function setDiagnosisText(aFieldMkb,aFieldText) {
				var val = $(aFieldMkb+'Name').value ;
				var ind = val.indexOf(' ') ;
				//alert(ind+' '+val)
				if (ind!=-1) {
					if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
				}
			}
    	</script>
		</msh:ifFormTypeIsCreate>
	</tiles:put>
</tiles:insert>