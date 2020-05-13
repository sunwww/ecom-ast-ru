<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="style" type="string">
	  	<msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
	                    <style type="text/css">
	                    #sourceHospTypeLabel,#admissionOrderLabel, #whereAdmissionLabel,
	                    #orderMkbLabel,#orderDiagnosLabel,
	                    #admissionInHospitalLabel, #psychReasonLabel, #clinicalActuityLabel
	                    ,#orderLpuLabel
	                     {
	                        color: blue ;
	                    }
	                    #sourceHospTypeName,#admissionOrderName, #whereAdmissionName,
	                    #orderMkbName,#orderDiagnos,
	                    #admissionInHospitalName, #psychReasonName, #clinicalActuityName {
	                        background-color:#FFFFA0;
	                    }
	                </style>
	          	</msh:ifInRole>
  	<msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
        <style type="text/css">
            #sourceHospTypeLabel,#admissionOrderLabel, #whereAdmissionLabel,
            #admissionInHospitalLabel, #psychReasonLabel, #clinicalActuityLabel
            ,#judgment35Label,#lawCourtDesicionDate
             {
                color: blue ;
            }
            #sourceHospTypeName,#admissionOrderName, #whereAdmissionName,
            #admissionInHospitalName, #psychReasonName, #clinicalActuityName {
                background-color:#FFFFA0;
            }
        </style>
  	</msh:ifInRole>
  	<style type="text/css">


            #clinicalDiagnosLabel, #clinicalMkbLabel, #clinicalActuityLabel,#mkbAdcLabel {
                color: blue ;
            }
            #concomitantDiagnosLabel, #concomitantMkbLabel, .concomitantDiags {
                color: green ;
            }

            #concludingDiagnosLabel, #concludingMkbLabel, .concludingDiags {
                color: black ;
            }
            #complicationDiagnosLabel, #complicationMkbLabel, .complicationDiags {
                color: purple;
            }
            
            #pathanatomicalDiagnosLabel, #pathanatomicalMkbLabel {
                color: red ;
            }
            .otherTable {
            	width:99% ;
            }
            .otherTable tr {
            	border: 1px solid ;
            }
            
            .epicrisis {
				left:0px;  width:99%; 
				top:0px;  height:45em;
			}
			#epicriPanel {
			width:100%;
			}
			.dischargeEpicrisis {
			width:100%;
			}
        </style>

    </tiles:put>
      <tiles:put name="side" type="string">
    	  	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
    	  		<tags:template_new_diary name="newTemp" roles="/Policy/Diary/Template/Create" field="dischargeEpicrisis" title="Создание шаблона на основе выписки"/>
    	  	</msh:ifNotInRole>
    	  	<tags:stac_hospitalMenu currentAction="stac_sslDischarge"/>  
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:ifFormTypeIsView formName="stac_sslDischargePreForm">
  <script type="text/javascript">
  	window.location.href='entityParentView-stac_sslDischarge.do?id='+${param.id} ;
  </script>
  </msh:ifFormTypeIsView>
    <!-- 
    	  - Случай стационарного лечения (выписка предварительная)
    	  -->
     <msh:form action="/entityParentSaveGoView-stac_sslDischargePre.do" defaultField="" title="Случай стационарного лечения. ВЫПИСКА">
      <msh:hidden property="id" />
      <msh:hidden property="patient" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpu" />
      <msh:hidden property="emergency"/>
      <msh:hidden property="ambulanceTreatment"/>
      <msh:hidden property="ownerFunction"/>
      <msh:hidden property="bedType"/>
      <msh:hidden property="department"/>
      <msh:hidden property="hospType"/>
      <msh:hidden property="serviceStream"/>
      <msh:hidden property="intoxication"/>
      <msh:hidden property="medicalAid"/>
      <msh:hidden property="relativeMessage"/>
      <msh:hidden property="orderLpu"/>
      <msh:hidden property="sourceHospType"/>
      <msh:hidden property="orderNumber"/>
      <msh:hidden property="orderDate"/>
      <msh:hidden property="orderType"/>
      <msh:hidden property="intoxication"/>
      <msh:hidden property="preAdmissionTime"/>
      <msh:hidden property="pediculosis"/>
      <msh:hidden property="attendant"/>
      <msh:hidden property="supplyOrderNumber"/>
      <msh:hidden property="deniedHospitalizating"/>
      <msh:hidden property="ambulanceTreatment"/>
      <msh:hidden property="username"/>
        <msh:hidden property="judgment35"/>
        <msh:hidden property="admissionOrder"/>
        <msh:hidden property="lawCourtDesicionDate"/>
        <msh:hidden property="psychReason"/>
        <msh:hidden property="guarantee"/>
       <msh:ifNotInRole roles="/Policy/Mis/Patient/Newborn">
       	<msh:hidden property="hotelServices"/>
       </msh:ifNotInRole>
              <msh:ifFormTypeIsView formName="stac_sslDischargePreForm">
              	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
              		<msh:hidden property="dischargeEpicrisis"/>
              	</msh:ifNotInRole>
              </msh:ifFormTypeIsView>
      <msh:panel colsWidth="5%,10%,5%,80%">
        <msh:separator label="Приемное отделение" colSpan="8" />
        <msh:row>
          <msh:textField property="statCardNumber" label="Номер стат.карты" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateStart" label="Дата поступления" viewOnlyField="true" />
          <msh:textField property="entranceTime" label="время" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="transferDate" label="Выбыт. из приемника"  viewOnlyField="true"/>
          <msh:textField property="transferTime" label="время" fieldColSpan="3"  viewOnlyField="true"/>
        </msh:row>        
        <msh:row>
          <msh:checkBox property="relativeMessage" label="Сообщение родственникам" viewOnlyField="true" />
          <msh:autoComplete property="department" label="Отделение" vocName="vocLpuOtd" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <mis:ifPatientIsWoman classByObject="Patient" idObject="stac_sslDischargePreForm.patient" roles="/Policy/Mis/Pregnancy/History/View">
        	<msh:separator label="Беременность" colSpan="9"/>
	        <msh:row>
	        	<msh:autoComplete viewOnlyField="true" viewAction="entityParentView-preg_pregnancy.do" property="pregnancy" label="Беременность" fieldColSpan="3" parentId="stac_sslAdmissionForm.patient" vocName="pregnancyByPatient" horizontalFill="true"/>
	        </msh:row>
        </mis:ifPatientIsWoman>
        </msh:panel>
        <msh:panel colsWidth="5%,10%,5%,80%">
	        <msh:row>
	        	<msh:autoComplete vocName="vocIllnesPrimary" property="clinicalActuity" horizontalFill="true" label="Характер заболевания"
	        		fieldColSpan="3"
	        	/>
	        </msh:row>
	        <msh:row>
		        <msh:autoComplete vocName="vocIdc10" label="МКБ клин.диаг." property="clinicalMkb" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	    	    <msh:textField label="Клинический диагноз" property="clinicalDiagnos" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
        </msh:panel>
        <msh:panel styleId="epicriPanel" colsWidth="1%,1%,1%,1%">
        <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View">
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
	        <msh:row>
	        <msh:separator colSpan="8" label="Выписной эпикриз" />
	        </msh:row>
        	<msh:ifFormTypeIsNotView formName="stac_sslDischargePreForm">
	        <msh:row>
              <td colspan="4" align="center">
	                        <input type="button" value="Шаблон" onclick="showTextTemplateProtocol()"/>
	                        <input type="button" value="Доп. сведения" onclick="showTextEpicrisis()"/>
	                        <input type="button" value="Сохранить пред. выписку" onclick="savePreRecord()"/>
	                        <input type="button" value="Сохранить пред. выписку+диагноз" onclick="this.form.action='entityParentSaveGoView-stac_sslDischargePre.do';this.form.submit();"/>
	                        <input type="button" id="changeSizeEpicrisisButton1" value="Увеличить" onclick="changeSizeEpicrisis()">
	                        
               </td>
	        </msh:row>
	        </msh:ifFormTypeIsNotView>
	        <msh:row>
	        <msh:textArea property="dischargeEpicrisis" fieldColSpan="3" label="Текст" />
	        </msh:row>
        	<msh:ifFormTypeIsNotView formName="stac_sslDischargePreForm">
	        <msh:row>
              <td colspan="4" align="center">
	                        <input type="button" value="Шаблон" onclick="showTextTemplateProtocol()"/>
	                        <input type="button" value="Доп. сведения" onclick="showTextEpicrisis()"/>
	                        <input type="button" value="Сохранить пред. выписку" onclick="savePreRecord()"/>
	                        <input type="button" value="Сохранить пред. выписку+диагноз" onclick="this.form.action='entityParentSaveGoView-stac_sslDischargePre.do';this.form.submit();"/>
	                        <input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">
               </td>
	        </msh:row>
	        </msh:ifFormTypeIsNotView>
        </msh:ifNotInRole>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
        	<msh:hidden property="dischargeEpicrisis"/>
        </msh:ifInRole>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/View">
        	<msh:hidden property="dischargeEpicrisis"/>
        </msh:ifNotInRole>
        </msh:panel>
        <msh:panel colsWidth="5%,10%,5%,80%">
        <msh:separator colSpan="8" label="Выписка" />
        <msh:row>
        	<msh:autoComplete vocName="vocIllnesPrimary" property="concludingActuity" horizontalFill="true" label="Характер заболевания"
        		fieldColSpan="3"
        	/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 закл.диаг." property="concludingMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
	        <msh:textField label="Заключительный диагноз" property="concludingDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:hidden property="complicationDiags"/>
        	<msh:hidden property="concomitantDiags"/>
        <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">
                <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 клин.диаг.соп." property="concomitantMkb" fieldColSpan="6" horizontalFill="true"/>
        </msh:row>

        <msh:row>
    	    <msh:textField label="Клин. диаг. сопут" property="concomitantDiagnos" fieldColSpan="5" horizontalFill="true"/>
    	    <td><input type="button" value="+ диагноз" onclick="addDiag('concomitant')"/></td>
        </msh:row>
        </msh:ifFormTypeIsNotView>
        <tr><td colspan="7">
        <table class="otherTable" id='otherconcomitantDiagsTable'></table>
        </td></tr>   
        <msh:ifFormTypeIsNotView formName="stac_sslDischargeForm">     
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 клин.диаг.осл." property="complicationMkb" fieldColSpan="6" horizontalFill="true"/>
        </msh:row>
        <msh:row>
    	    <msh:textField label="Клин. диаг. осл." property="complicationDiagnos" fieldColSpan="5" horizontalFill="true"/>
    	    <td><input type="button" value="+ диагноз" onclick="addDiag('complication')"/></td>
        </msh:row>
        </msh:ifFormTypeIsNotView>
                <msh:row><td colspan="7">
        <table class="otherTable" id='othercomplicationDiagsTable'></table>
        </td></msh:row>        
        <msh:ifFormTypeIsView formName="stac_sslDischargePreForm">
	        <msh:row>
	    	    <msh:autoComplete vocName="vocIdc10" label="МКБ-10 патанат.диаг." property="pathanatomicalMkb" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	        	<msh:textField label="Патанатомический диагноз" property="pathanatomicalDiagnos" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
        	<msh:autoComplete property="kinsman" label="Представитель (иног.)" viewAction="entityParentView-mis_kinsman.do" 
        	parentId="stac_sslDischargePreForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/Patient/Newborn">
	        <msh:row>
	        	<msh:checkBox property="hotelServices" label="Находится в больнице по уходу за пациентом" fieldColSpan="3"/>
	        </msh:row>
        </msh:ifInRole>        
        <msh:row>
          <msh:autoComplete label="Исход" property="outcome" fieldColSpan="1" horizontalFill="true" vocName="vocHospitalizationOutcome" />
          <msh:autoComplete label="Результат госп." property="result" fieldColSpan="1" horizontalFill="true" vocName="vocHospitalizationResult" />
        </msh:row>
        <msh:row>
          <msh:autoComplete label="Причина выписки" property="reasonDischarge"  horizontalFill="true" vocName="vocReasonDischarge"/>
          <msh:autoComplete label="Дефекты догоспитального этапа" property="preAdmissionDefect"  horizontalFill="true" vocName="vocPreAdmissionDefect"/>
        </msh:row>
        <msh:row>
          <msh:textField label="Дата выписки" property="dateFinish" />
          <msh:textField label="Время выписки" property="dischargeTime" />
        </msh:row>
        <msh:row>
          <msh:autoComplete label="Перевод в др ЛПУ" property="moveToAnotherLPU" horizontalFill="true" vocName="mainLpu" fieldColSpan="3" />
        </msh:row>  
        <msh:row>
          <msh:autoComplete vocName="vocHospType" property="targetHospType" label="Куда выписан" horizontalFill="true" />
        	<msh:autoComplete label="Итог выписки" property="resultDischarge"  horizontalFill="true" vocName="vocResultDischarge" />
        </msh:row>

        <msh:row>
          <msh:checkBox property="rareCase" label="Редкий случай" />
        </msh:row>
        <msh:row>
          <msh:autoComplete labelColSpan="3" property="hospitalization" label="Госпитализация в данном году по данному заболевания" vocName="vocHospitalization" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/MedCase/IsPsychiatry">
	        <msh:row>
	        	<msh:autoComplete vocName="vocHospitalization" property="admissionInHospital" label="Поступление в стационар" horizontalFill="true" labelColSpan="1"/>
		        <msh:autoComplete label="Причина госпитализации" vocName="vocPsychHospitalReason" property="psychReason" labelColSpan="1" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	        	<msh:autoComplete property="admissionOrder" label="Порядок поступления" fieldColSpan="1" vocName="vocAdmissionOrder" horizontalFill="true"/>
		        <msh:autoComplete label="Откуда поступил" vocName="vocHospitalizationWhereAdmission" property="whereAdmission" labelColSpan="1" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	        	<msh:textField property="lawCourtDesicionDate" label="Дата решения суда"/>
	        	<msh:autoComplete property="judgment35" label="Решение судьи по ст. 35" horizontalFill="true" vocName="vocJudgment"/>
	        </msh:row>
        </msh:ifInRole>
        
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="Время"/>
            <msh:label property="username" label="Пользователь" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
        	<msh:label property="editTime" label="время"/>
          	<msh:label property="editUsername" label="Пользователь" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
    <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
    	<msh:ifFormTypeIsNotView formName="stac_sslDischargePreForm">
    	<tags:templateProtocol property="dischargeEpicrisis" name="Text" version="Visit" idSmo="stac_sslDischargePreForm.id" voc="protocolVisitByPatient" />
    	<tags:dischargeEpicrisis property="dischargeEpicrisis" name="Text" patient="patient" dateStart="dateStart" dateFinish="dateFinish"/>
    	</msh:ifFormTypeIsNotView>
    </msh:ifNotInRole>
    <tags:stac_infoBySls form="stac_sslDischargePreForm"/>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslDischargePreForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <script type="text/javascript">
  var slo_form_is_view = 0 ;
  </script>
  <msh:ifFormTypeIsView formName="stac_sslDischargePreForm">
  <script type="text/javascript">
  slo_form_is_view = 1 ;
  </script>
  </msh:ifFormTypeIsView>
      	<script type="text/javascript"> 
	    function trim(aStr) {
			return aStr.replace(/|\s+|\s+$/gm,'') ;
		    }

      	var old_action = document.forms["mainForm"].action ; 
      	document.forms["mainForm"].action="javascript:check_diags()" ; 
      	function check_diags() {
      		var list_diag = ["complication","concomitant"] ;
      		var isnext=true ;
      		for (var i=0;i<list_diag.length;i++) {
      			isnext=addDiag(list_diag[i],1);
      			if (!isnext) break ;
      			createOtherDiag(list_diag[i]);
      		}
      		if (isnext) {
	      		document.forms["mainForm"].action=old_action ;
	      		document.forms["mainForm"].submit() ;
      		} else {
      			$('submitButton').disabled=false ;
      		}
      	}
      	onload=function(){
      		
      		var list_diag = ["complication","concomitant"] ;
      		for (var j=0;j<list_diag.length;j++) {
      			
               		if ($(list_diag[j]+'Diags').value!='') {
                        var addRowF="";
                        var ind_f=0 ;
                  		for (var i=0;i<theFld.length;i++) {
                  			addRowF+="ar["+(ind_f++)+"],"
                  			if (theFld[i][2]==1) {
                  				addRowF+="ar["+(ind_f++)+"],"
                  			}
                  		}
                		addRowF=addRowF.length>0?trim(addRowF).substring(0,addRowF.length-1):"";
                        addRowF="addRowDiag('"+list_diag[j]+"',"+addRowF+",1);"
                        
                  		var arr = $(list_diag[j]+'Diags').value.split("#@#");
                  		for (var i=0;i<arr.length;i++) {
                  			var ar=arr[i].split("@#@") ;
                  			//alert(addRowF);
                              eval(addRowF) ;
                  		}
                  	}
                    
            }
	        	
        }
      	
      	function addDiag(aDiagType,aCheck) {
            var addRowF="";
            var isCheckReq =true ;
      		for (var i=0;i<theFld.length;i++) {
      			var fld_i = theFld[i] ;
  				eval("var "+fld_i[1]+"=$('"+aDiagType+fld_i[5]+"').value;");
      			var fld_i = theFld[i] ;addRowF+=fld_i[1]+","
      			
      			if (fld_i[2]==1) {
      				eval("var "+fld_i[1]+"Name=$('"+aDiagType+fld_i[5]+"Name').value;");
      				eval("if ("+fld_i[1]+">0) {} else {isCheckReq=false ;}") ;
          			addRowF+=fld_i[1]+"Name," ;
      			} else {
      				eval("if ("+fld_i[1]+".length>0) {} else {isCheckReq=false ;}") ;
      			}
      		}
    		addRowF=addRowF.length>0?trim(addRowF).substring(0,addRowF.length-1):"";
            addRowF="addRowDiag('"+aDiagType+"',"+addRowF+");"
      		
            if (isCheckReq) {
            	var servs = document.getElementById('other'+aDiagType+"DiagsTable").childNodes;
                  var l = servs.length;
                  for (var i=1; i<l;i++) {
                	  
                	  var isCheckDouble = (+$(aDiagType+theFld[0][5]).value 
                			  == +servs[i].childNodes[0].childNodes[theFld[0][3]].value)?false:true ;
                	 if (!isCheckDouble) {
                 		 if (+aCheck!=1) {
                 			 if (confirm("Такой диагноз уже зарегистрирован. Вы хотите его заменить?")) {
                 			var node=servs[i];node.parentNode.removeChild(node);
                 		 } else {
                  			return true;
                  		 } 
                 		} else {return true;}
                    }                 
                 }
            		
            
            eval(addRowF) ;
      		for (var i=0;i<theFld.length;i++) {
      			var fld_i = theFld[i] ;
      			if (fld_i[6]==1) {
	  				eval("$('"+aDiagType+fld_i[5]+"').value='';");
	      			if (fld_i[2]==1) {
	      				eval("$('"+aDiagType+fld_i[5]+"Name').value='';");
	      			}
      			}
      		}
            } else {
            	if (+aCheck!=1) {
            		alert("Заполнены не все поля диагноза!!");
            	} else {
            		if (+$(aDiagType+"Mkb").value>0&&$(aDiagType+"Diagnos").length>0&&!confirm('Диагнозы, где не заполнены все данные (МКБ и наименование) сохранены не будут!!! Продолжить сохранение?')) {
            			return false ;
            		} 
            	}
            }
            return true ;
         }
        //alert(document.getElementById('othercomplicationDiagsTable').childNodes.childNodes[0].childNodes[4].value);
      	function createOtherDiag(aDiagType) {
      		var servs = document.getElementById('other'+aDiagType+"DiagsTable").childNodes;
      		var str = ""; $(aDiagType+"Diags").value='';
      		for (var i=0;i<servs.length;i++) {
      			for (var ii=0;ii<theFld.length;ii++) {
      			str+=servs[i].childNodes[0].childNodes[theFld[ii][3]].value+"@#@";
  				if (theFld[ii][2]==1) {
  					str+=servs[i].childNodes[0].childNodes[theFld[ii][3]+1].value+"@#@";
  				}

      			}
      			str=str.length>0?trim(str).substring(0,str.length-3):"";
      			str+="#@#" ;
      		}
      		str=str.length>0?trim(str).substring(0,str.length-3):"";
      		$(aDiagType+"Diags").value=str;
      	}
      	// 0. наименование 1. Наим. поля в функции 2. autocomplete-1,textFld-2 
      	// 3. Номер node в добавленной услуге 4. Обяз.поля да-1 нет-2 
      	// 5. наим. поля в форме 6. очищать поле в форме при добавление да-1, нет-0 
  		var theFld = [['Код МКБ','Mkb',1,3,1,'Mkb',1],['Наименование','Diagnos',2,8,1,'Diagnos',1]] ;
      	function editMkbByDiag(aDiagType,aNode) {
      		if (+$(aDiagType+'Mkb').value==0 || confirm("Вы точно хотите продолжить? В этом случае Вы потеряете дааные еще недобавленного диагноза!")) {
   			for (var ii=0;ii<theFld.length;ii++) {
   				$(aDiagType+theFld[ii][5]).value=aNode.childNodes[0].childNodes[theFld[ii][3]].value;
				if (theFld[ii][2]==1) {
					$(aDiagType+theFld[ii][5]+'Name').value=aNode.childNodes[0].childNodes[theFld[ii][3]+1].value;
				}

   			}
   			aNode.parentNode.removeChild(aNode) ;
      		}
      	}
      	function addRowDiag(aDiagType,aMkb,aMkbName,aDiagnos,aIsLoad) {
      		var table = document.getElementById('other'+aDiagType+"DiagsTable");
      		var row = document.createElement('TR');
      		var td = document.createElement('TD');
      		var tdDel = document.createElement('TD');
      		table.appendChild(row);
      		row.appendChild(td);
      		var txt ="" ;addText="" ;
      		if (aDiagType=="complication") {addText="ослож."} else if (aDiagType=="concomitant") {addText="сопут." ;}
      		for (var i=0;i<theFld.length;i++) {
      			var fld_i = theFld[i] ;
      			if (fld_i[2]==1) {
      				txt+=" <label class='"+aDiagType+"Diags'>"+fld_i[0]+" "+addText+": </label>"+eval("a"+fld_i[1]+"Name")+" <input type='hidden' value='"+eval("a"+fld_i[1])+"'><input type='hidden' value='"+eval("a"+fld_i[1]+"Name")+"'>"
      			} else if (fld_i[2]==2) {
      				txt+=" <label class='"+aDiagType+"Diags'>"+fld_i[0]+" "+addText+":  </label><input type='text' style='width:85%' value='"+eval("a"+fld_i[1])+"'>"
      			}
      			if (i<theFld.length-1) txt+="<br>" ; 
      		}
      		td.innerHTML=txt ;
      		if (slo_form_is_view==0) {
	      		row.appendChild(tdDel);
	      		tdDel.style.width='2%' ;
	      		tdDel.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);createOtherDiag(\""+aDiagType+"\")' value='- диагноз' />"
	      		+ "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;editMkbByDiag(\""+aDiagType+"\",node);' value='редак.' />";
	      		if (+aIsLoad>0 && (+aMkb==0)) {
	      			if (+$(aDiagType+"Mkb").value==0) editMkbByDiag(aDiagType,row) ;  
	      		}
      		}
      	}
</script>
  
  
  
  
  
  
  
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
        	<script type="text/javascript">
        		$('outcomeName').select() ;
        		$('outcomeName').focus() ;
        	</script>
        </msh:ifInRole>
     <msh:ifFormTypeIsNotView formName="stac_sslDischargePreForm">
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/NotViewDischargeEpicrisis">
        	<script type="text/javascript">
        		$('dischargeEpicrisis').select() ;
        		$('dischargeEpicrisis').focus() ;
        		var isChangeSizeEpicrisis = 1 ;
        		function changeSizeEpicrisis() {
        			if (isChangeSizeEpicrisis==1) {
        				Element.addClassName($('dischargeEpicrisis'), "epicrisis") ;
        				if ($('changeSizeEpicrisisButton')) {
        					$('changeSizeEpicrisisButton').value='Уменьшить'
        					$('changeSizeEpicrisisButton1').value='Уменьшить'
        				} ;
        				isChangeSizeEpicrisis=0 ;
        			} else {
        				Element.removeClassName($('dischargeEpicrisis'), "epicrisis") ;
        				if ($('changeSizeEpicrisisButton')) {
        					$('changeSizeEpicrisisButton').value='Увеличить' ;
        					$('changeSizeEpicrisisButton1').value='Увеличить' ;
        				}
        				isChangeSizeEpicrisis=1;
        			}
        		}
        		eventutil.addEventListener($('dischargeEpicrisis'), "dblclick", 
        	  		  	function() {
        					changeSizeEpicrisis() ;
        	  		  	}) ;
        	</script>
        </msh:ifNotInRole>
        
     <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
  	<script type="text/javascript">
  		try {
  		if (concludingMkbAutocomplete) concludingMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('concludingMkb','concludingDiagnos');
	    });} catch(e) {
	    }
  		try {
	    if (clinicalMkbAutocomplete) clinicalMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('clinicalMkb','clinicalDiagnos');
	    });} catch(e) {}
  		try {
	    if (pathanatomicalMkbAutocomplete) pathanatomicalMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('pathanatomicalMkb','pathanatomicalDiagnos');
	    });} catch(e) {}
  		try {
	    if (concomitantMkbAutocomplete) concomitantMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('concomitantMkb','concomitantDiagnos');
	    });} catch(e) {}
  		try {
	    if (complicationMkbAutocomplete) complicationMkbAutocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('complicationMkb','complicationDiagnos');
	    });} catch(e) {}
  		function setDiagnosisText(aFieldMkb,aFieldText) {
  			var val = $(aFieldMkb+'Name').value ;
  			var ind = val.indexOf(' ') ;
  			//alert(ind+' '+val)
  			if (ind!=-1) {
  				if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
  			}
  		}
  		function savePreRecord() {
  			HospitalMedCaseService.preRecordDischarge(
  					$('id').value,$('dischargeEpicrisis').value, {
	                    callback: function(aResult) {
	                        alert("Сохранено") ;
	                    }
  					}
  			) ;
  		}
  	</script>
  	</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>