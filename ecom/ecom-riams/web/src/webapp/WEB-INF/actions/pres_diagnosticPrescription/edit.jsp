 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name="javascript" type="string">
	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
	<script type="text/javascript" src="./dwr/interface/WorkCalendarService.js"></script>
	<msh:ifFormTypeIsNotView formName="pres_diagnosticPrescriptionForm">
	<script type="text/javascript">
	
	
	var currentDate = new Date;
	var textDay = currentDate.getDate()<10?'0'+currentDate.getDate():currentDate.getDate();
	var textMonth = currentDate.getMonth()+1;
	textMonth = textMonth<10?'0'+textMonth:textMonth;
	var textYear =currentDate.getFullYear();
	var textDate = textDay+'.'+textMonth+'.'+textYear;
	
	
	function checkRecord(aId,aValue,aIdService,aService) {
    	$('surgCalTime').value = aId; 
    	$('surgCalTimeName').value = aValue ;
    
    }
	
	//Заполняем ЛН данными из шаблона (не удаляя существующие назначения). 
	function fillFormFromTemplate(aData) {
		var aRow = aData.split("#");
		if (aRow.length>0) {
			for (var i=0;i<aRow.length;i++) {
				var research = aRow[i].split("@");
				if (research[0]!=null && research[0]!="" ){
					var prescType = research[0];
					if (prescType=="SERVICE") {
						addRows(research[1],1);
					}
				}
			}
		}
	}

	var oldaction = document.forms['pres_diagnosticPrescriptionForm'].action ;
	document.forms['pres_diagnosticPrescriptionForm'].action="javascript:cancell()";
	
	var num=0;
	var labNum=0;
	var funcNum=0;
	var surgNum=0;
	var labList="";
	
	function cancell () {
		window.location='entityParentView-pres_prescriptList.do?id='+$('prescriptionList').value;	
	}
	
	function preShowDir() {
		$('1IsViewButton').value=$('prescriptType').value ;
		var list = +$('labServicies').value;
		clear1DirMedServiceDialog() ;
		var typeNum = 0;
		type='lab';
		typeNum = labNum;
		while (typeNum>0) {
			if (document.getElementById(type+"Element"+typeNum)) {
				var ar = $(type+"Service"+typeNum).value ;
				list+="," ;
				list+=ar ;
			}
	       	typeNum-=1;
		 }			
		 $('1ListIds').value=list;
	 }

	function checkDoubles () {
		labList="";
		var str="";
		var aList =labList;
		aList = aList.substring(0,aList.length-1);
		var aListArr = aList.split("#");
		if (aListArr.length>0) {
			for (var i=0; i<aListArr.length;i++) {
				var id = aListArr[i].split(":");
			str+=id[0]+":";
			}
		}
		str=str.substring(0,str.length-1);
        PrescriptionService.getDuplicatePrescriptions($('medcaseId').value, str,{
            callback: function(aResult) {
                if (aResult.length>0){
                    var aText = "Данные назначения\n "+aResult+"\nуже назначены пациенту в этой истории болезни, все равно назначить?";
                        if (!confirm (aText)) {
                            document.getElementById('submitButton').disabled=false;
                            document.getElementById('submitButton').value='Создать';
                            return;
                            }
                }
                checkLabs();
            }
        });
	}

	function removeRows(type) {
		var rType;
		if (type=='lab') {rType=labNum; labNum=0;}
		else if (type=='func') {rType=funcNum;funcNum=0;}
		else if (type=='drug') {rType=drugNum;drugNum=0;}
		else return;
		 
		if (rType>0) {
			for (var i=1; i<=rType;i++) {
				if (document.getElementById(type+'Element'+i)){
					var node = document.getElementById(type+'Element'+i);
					node.parentNode.removeChild(node);
				}				
			}
		}
		
	}
	function checkError(aErrorList) {
		for (var i=0;i<aErrorList.length;i++) {
			if (aErrorList[i][3]=="isEmptyUnit") {
				if ($(aErrorList[i][0]).value==""){
					alert("Обязательное поле: "+aErrorList[i][2]) ;
					//alert(aErrorList[i][0]+aErrorList[i][1]);
					$(aErrorList[i][0]+aErrorList[i][1]).focus() ;
					return true;
				}
			}
		}
		return false ;
	}
//TODO !! 
	function prepareLabRow(type) {
 	var fldList,reqList =[];
	 if (type=='surg') {
		var error = [
						[type+'Servicies','Name', 'Услуга!','isEmptyUnit']
				      	,[type+'CalTime','', 'Дата и время услуги!','isEmptyUnit']
					  
					];
		num = surgNum;
		fldList = [['Servicies',1],['ServiciesName',1],['CalDateName',1],['Cabinet',1]
		,['CabinetName',1],['',1],['',1],['CalTime',1],['CalTimeName',1],['',1]
	] ;
	}
	
	if (checkError(error)) return ;
	var typeDate = new Date();
	var l = $(type+'CalDateName').value;
	var t = $(type+'CalTimeName').value;
	l=l.substr(6,4)+'-'+l.substr(3,2)+'-'+l.substr(0,2)+'T'+t+':00';
	typeDate.setTime (Date.parse(l));
	
	if (typeDate<currentDate) {
		if (!confirm ('Дата назначения меньше текущей даты. Создать назначение прошедшим днем?')) {
			return;
		}
	} 
	
	// Проверим на дубли 
	var checkNum = 1;
	while (checkNum<=num) {
		if (document.getElementById(type+'Service'+checkNum)) {
			if ($(type+'CalTime')) {
				if ($(type+'CalTime').value == $(type+'CalTime'+checkNum).value) {
					alert ("На это время уже назначена запись!");
					return;
				}
			}
			if ($(type+'Servicies').value==document.getElementById(type+'Service'+checkNum).value){
				if (confirm("В этом листе назначений уже назначено это исследование, ВЫ УВЕРЕНЫ, что хотите назначить его еще раз?")) {
					break; 
				} else {
					return;
				}
			}
			
		}
		checkNum+=1;
	}
	var ar = getArrayByFld(type,"", fldList, reqList, "", -1) ;
	if ($('tdPreRecord')) { $('tdPreRecord').innerHTML=""; }
	if ($('divReserve')) { $('divReserve').innerHTML=""; }
	
	
	
	//alert("ALERT!!"+$('Servicies').value);
	addPrescription($(type+'Servicies').value,'',$(type+'Cabinet').value,$(type+'CalDateName').value,$(type+'CalTime').value,$('comments').value, type+":"+ar[0],1);
	
}
	
function addPrescription(aLabID, aLabDepartment, aLabCabinet, aDateStart, aWCT, comments,addRowType, addRowNum) {
	$('subm').value = 'Создание...';
	$('subm').disabled = true;
	PrescriptionService.addPrescriptionToListWCT($('prescriptionList').value, aLabID, aLabDepartment, aLabCabinet,"ServicePrescription",aDateStart, aWCT, comments, {
	    callback: function (ret) {
	        if (ret!=null) {
	            createVisitByPrescription(addRowType,addRowNum);
            } else {
	            alert("Ошибка при создании назначения. Время уже занято");
                $('subm').value = 'Создать назначение';
                $('subm').disabled = false;
            }
        }
	});
}

function createVisitByPrescription(addRowType,addRowNum) {
    PrescriptionService.createVisitByPrescription($('prescriptionList').value, $('surgCabinet').value, $('surgCalDate').value, $('surgCalTime').value
        ,$('surgServicies').value, $('countDays').value, $('guaranteeId').value, {
            callback: function(a) {
                if (a==null) {
                    alert("Ошибка при назначении услуги!!! Выбранное время уже занято!");

                } else {
                    addRows(addRowType, addRowNum);
                }
                getPreRecord();
                updateTime();
            }
        });
}
function deletePrescription(aMedService, aWCT) {
	PrescriptionService.removePrescriptionFromList($('prescriptionList').value,aMedService,aWCT);
}

function getArrayByFld(aType, aTypeNum, aFldList, aReqFldId, aCheckFld, aCheckId) {
    var next = true ;
    var l="",lAll="",isDoubble=0 ;
    for (var i=0;i<aReqFldId.length;i++) {
        if ($(aType+aFldList[aReqFldId[i]][0]+aTypeNum).value=="") {next=false ; break;}
    }
    if (next) {
        //Формат строки - name:date:method:freq:freqU:amount:amountU:duration:durationU#
        for (var i=0;i<aFldList.length;i++) {
            var val = aFldList[i][0]==""?"":$(aType+aFldList[i][0]+aTypeNum).value ;
            val = val.replace(":","-");
            if (i!=0) {
                l += ":" ;lAll+=":";
            }
            if (aCheckId==i) {
                if ($(aCheckFld).value==val) {
                    isDoubble=1;
                }
            }
            if (aFldList[i][1]==1) { l += val ; }
            lAll+=val ;
            if (i==(aFldList.length-1)) {l += "#" ;lAll+="#" ;}
        }
    }
    return [l,lAll,isDoubble] ;
}
		

		function addRows(aResult,aFocus) {
			$('subm').value = 'Создать назначение';
			$('subm').disabled = false;
		var resultRow = aResult.split(":");
		/*
		0 - ms.type
		1 - ms.ID 2 - ms. code+name
		3 - date
		4 - cabinetcode           5 - cabinetname
		6 - departmentintakecode  7 - departmentintakename (for lab)
		8 - timecode              9 - timename (for func)
		*/
		var type = resultRow[0];
		var id = resultRow[1]; 
		var name = resultRow[2];
		var date = resultRow[3]!=""?resultRow[3]:textDate;
		var cabinet = resultRow[4]?resultRow[4]:"";
		var cabinetName = resultRow[5]?resultRow[5]:"";
		
		if (type=='LABSURVEY' || type=='lab') {
			type='lab'; num = labNum;
		} else if (type=='DIAGNOSTIC' || type=='func') {
			type='func'; num = funcNum; 
		} else if (type=='surg') {
			num = surgNum;
		} else if (type='hosp') {
			num = hospNum;
		}
		num+=1;
	    
 		var tbody = document.getElementById('add'+type+'Elements');
	    var row = document.createElement("TR");
		row.id = type+"Element"+num;
	    tbody.appendChild(row);
	
	    // Создаем ячейки в вышесозданной строке и добавляем тх 
	    var td1 = document.createElement("TD"); td1.colSpan="1"; td1.align="right";
	    var td2 = document.createElement("TD"); td2.colSpan="3";
	    var td3 = document.createElement("TD");
	    row.appendChild(td1); row.appendChild(td2); row.appendChild(td3);
	   
	    // Наполняем ячейки 
	    //var dt2="<input id='"+type+"Cabinet"+num+"' name='"+type+"Cabinet"+num+"' value='"+cabinet+"' type='hidden'  />";
	    
	  	td1.innerHTML = "";//textInput("Дата",type,"Date",num,resultRow[3],date,10) ;
	    td2.innerHTML = hiddenInput(type,"Service",num,resultRow[1],"")+spanTag(" ",resultRow[2],"");
	   	if (type=="lab") {
	   		td2.innerHTML += hiddenInput(type,"Department",num,resultRow[6],"")+spanTag("Место забора",resultRow[7],"") ;
	   		td2.innerHTML += hiddenInput(type,"Cabinet",num,cabinet,"");
	   		labNum = num;
	   	} else if (type=="surg"){
		   	td2.innerHTML += hiddenInput(type,"Cabinet",num,resultRow[4],"")+spanTag("Кабинет",resultRow[5],"");
	   		td2.innerHTML += hiddenInput(type,"CalTime",num,resultRow[8],"")+spanTag("Время",resultRow[3]+" "+resultRow[9].replace("-",":"),"") ;
	   		funcNum = num;
	   		//$(type+'Cabinet').value='';
			//$(type+'CabinetName').value='';
	   	} 
	   	td3.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);deletePrescription("+resultRow[1]+","+resultRow[8]+")' value='Удалить' />";
	   	//new dateutil.DateField($(type+'Date'+num));
		
		$(type+'Servicies').value='';
		$(type+'ServiciesName').value='';
		$(type+'CalTime').value='';
		$(type+'CalTimeName').value='';
		
		if (aFocus) $(type+'ServiciesName').focus() ;
}
	function hiddenInput(aType,aFld,aNum,aValue,aDefaultValue) {
		return "<input id='"+aType+aFld+aNum+"' name='"+aType+aFld+aNum+"' value='"+(aValue==null||aValue==""?aDefaultValue:aValue)+"' type='hidden' />"
	}
	function textInput(aLabel,aType,aFld,aNum,aValue,aDefaultValue,aSize) {
		return "<span>"+aLabel+"</span><input "+(+aSize>0?"size="+aSize:"")+" id='"+aType+aFld+aNum+"' name='"+aType+aFld+aNum+"' value='"+(aValue==null||aValue==""?aDefaultValue:aValue)+"' type='text' />"
	}
	function spanTag(aText,aValue,aDefaultValue) {
		return "<span>"+aText+": <b>"+(aValue!=null&&aValue!=""?aValue.trim():aDefaultValue.trim())+"</b></span>. " ;
	}
	surgCalDateAutocomplete.addOnChangeCallback(function(){
		  $('surgCalTime').value="" ;
		  $('surgCalTimeName').value="" ;
  	  	  getPreRecord() ;
  	 });
	surgCabinetAutocomplete.addOnChangeCallback(function(){
		updateDefaultDate() ;
		surgServiciesAutocomplete.setParentId($('surgCabinet').value+"#"+$('serviceStream').value);
	}) ;

	function fillServiceListVocId() {
	    if ($('medcaseType').value==='POLYCLINIC') {
	        surgServiciesAutocomplete.setUrl('simpleVocAutocomplete/funcMedServicePolByServiceStream');
        }
    }
    fillServiceListVocId();
    function getPreRecord() {
  		if ($('tdPreRecord')) {
  			if ($('surgCalDate') && +$('surgCalDate').value>0) {
  	  			WorkCalendarService.getPreRecord($('surgCalDate').value, {
  	  			    callback:function(aResult) {
  	  			        $('tdPreRecord').innerHTML= aResult!=null ? aResult : "";
  	  		  			updateTime() ;
  	  			  	}
  	  		  	}) ;
  	  		} else {
                $('tdPreRecord').innerHTML="";
            }
  		} else {
  			updateTime() ;
  		}
	}
	
	function updateTime() {
        $('surgCalTime').value="" ;
        $('surgCalTimeName').value = "";
   		if (+$('surgCalDate').value>0 ) {
   			surgCalTimeAutocomplete.setParentId($('surgCalDate').value+"#"+$('patient').value);
   			WorkCalendarService.getReserveByDateAndServiceByPrescriptionList($('surgCalDate').value,$('prescriptionList').value, {
   			    callback: function(aResult) {
                     $('divReserve').innerHTML = aResult ;
                }
            });
        }
   	}

	function updateDefaultDate() {
        WorkCalendarService.getDefaultDate($('surgCabinet').value,{
            callback:function(aDateDefault) {
                if (aDateDefault!=null) {
                    //alert(aDateDefault) ;
                    var calDayId, calDayInfo,ind1 ;
                    ind1 = aDateDefault.indexOf("#") ;
                    calDayInfo = aDateDefault.substring(0,ind1) ;
                    calDayId = aDateDefault.substring(ind1+1) ;
                    $('surgCalDate').value=calDayId ;
                    $('surgCalDateName').value = calDayInfo;
                    getPreRecord();
                } else {
                    $('surgCalDate').value=0 ;
                    $('surgCalDateName').value = "";
                    getPreRecord();
                }
            }
        }) ;
        $('surgCalTime').value="" ;
        $('surgCalTimeName').value = "";
	}
	function changeToPolyclinicDoctor() {
        jQuery('#surgCabinet').val("");
        jQuery('#surgServices').val("");
        jQuery('#surgCabinetName').val("");
        jQuery('#surgServicesName').val("");
        surgCabinetAutocomplete.setUrl('simpleVocAutocomplete/workFunctionByDirect');
    }
/*
    onload = function() {
        if ($('allowOnlyPaid').value=="true") {
            showToastMessage("ВНИМАНИЕ! Возможно создание только оплаченных назначений!");
            PrescriptionService.getAllowedLabServiciesByPatient($('patient').value, null, {
                callback: function (servList) {
                    console.log(servList);
                    paidServiceList = JSON.parse(servList);
                    if (paidServiceList.length===0) {
                        showToastMessage("У пациента нет оплаченных услуг!");
                    }
                }
            });
        }
    };
*/
    //fillServiceListVocId();
				</script>
			</msh:ifFormTypeIsNotView>
			
			<!--  скрипт отмены -->
	<msh:ifFormTypeIsView formName="pres_diagnosticPrescriptionForm">
	<script type="text/javascript">
	function cancelDiagnostic() {
		var reason = ''+ prompt('Введите причину отмены');
		if (""+reason.trim()!="") {
            PrescriptionService.cancelPrescription($('id').value, reason, {
                callback:function (a) {
                    alert(a);
                }
            }) ;
        }

	}
	</script>
	</msh:ifFormTypeIsView>
		</tiles:put>

  <tiles:put name="body" type="string">
    <!-- 
    	  - Назначение медицинской услуги
    	  -->
    <msh:form action="/entityParentSaveGoView-pres_diagnosticPrescription.do" defaultField="surgCabinetName" title="Назначение диагностического исследования/консультации">
      <msh:hidden property="id" />
      <msh:hidden property="prescriptionList" />
      <msh:hidden property="serviceStream" />
      <msh:hidden property="medcaseType" />
        <msh:hidden property="medcaseId"/>
        <msh:hidden property="patient"/>
        <msh:hidden property="allowOnlyPaid"/>
        <msh:hidden property="unpaidConfirmation"/>
        <msh:hidden property="guaranteeId"/>
        <input type="hidden" id = "caosId">
      <msh:hidden property="saveType" />
        <msh:hidden property="comments"  />
      <msh:hidden property="labList" />
       <msh:ifNotInRole roles="/Policy/Mis/MedCase/Direction/CreateDirectionOnCourseTreatment">
        <msh:hidden property="countDays"/>
      </msh:ifNotInRole>
		
      <msh:panel colsWidth="3">  
         <msh:ifFormTypeIsView formName="pres_diagnosticPrescriptionForm">
             <msh:row>
                 <msh:autoComplete property="prescriptSpecial" label="Назначил" size="100" vocName="workFunction" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
             </msh:row>
             <msh:row>
    			<msh:autoComplete property="medService" label="Исследование" vocName="funcMedService" horizontalFill="true" size="90"  />
    		 </msh:row>
			 <msh:row>
				 <msh:autoComplete property="prescriptCabinet" label="Кабинет"  viewOnlyField="true" vocName="funcMedServiceRoom" size='20'  />
			</msh:row>
			<msh:row>
				 <msh:textField property="planStartDate" size="10" fieldColSpan="1" label="Дата назначения"/>
    		</msh:row>

    </msh:ifFormTypeIsView>
         <msh:ifFormTypeIsCreate formName="pres_diagnosticPrescriptionForm">
        <msh:panel>
            <msh:hidden property="prescriptSpecial"/>
        <msh:row>
        <tr><td> <input type="button" onclick="changeToPolyclinicDoctor()" value="Выбрать из списка врачей поликлиники">
        </td></tr>
        <tr><td>
        <table id="surgTable">
            <msh:row>
        	 	<msh:separator label="Диагностическое исследование" colSpan="10"/>
        </msh:row>
        <tbody id="addsurgElements">
			 <msh:row>
				 <msh:autoComplete property="surgCabinet" label="Кабинет"  fieldColSpan="4" vocName="funcMedServiceRoom"  horizontalFill="true" />
			</msh:row>
			<msh:row>
				 <msh:autoComplete property="surgCalDate" parentAutocomplete="surgCabinet" vocName="vocWorkCalendarDayByWorkFunction" label="Дата" size="10" fieldColSpan="1" />
    			 <msh:autoComplete property="surgCalTime"  label="Время" vocName="vocWorkCalendarTimeWorkCalendarDayAndPatient" fieldColSpan="1" />
    		</msh:row>
    	<msh:ifInRole roles="/Policy/Mis/MedCase/Direction/CreateDirectionOnCourseTreatment">
        <msh:row> 
        <msh:textField property="countDays"  label="Кол-во дней записи" />
        </msh:row>
        </msh:ifInRole>
          <msh:row>
    			<msh:autoComplete property="surgServicies" label="Исследование" vocName="funcMedServiceStacByServiceStream"  horizontalFill="true" size="90" fieldColSpan="4" />
    		 </msh:row>

			<msh:row>
				<td colspan="4" align="center">        	
	            	<input type="button" name="subm" id="subm"  onclick="prepareLabRow('surg');" value="Создать назначение" tabindex="4" />
	            </td>
	        </msh:row>
	        
        <tr><td colspan="10"><table><tr><td valign="top"><table>
        <msh:row>
          <msh:separator label="Резервы" colSpan="4" />
        </msh:row>
        <msh:row>
        	<td colspan="4">
        	<div id="divReserve"></div>
        	</td>
        </msh:row></table>
        </td><td valign="top"><table>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Direction/PreRecord">
        <msh:row>
          <msh:separator label="Предварительная запись" colSpan="4" />
        </msh:row>
        <msh:row>
        	<td colspan="4" id="tdPreRecord"></td>
        </msh:row>
        </msh:ifInRole></table>
        </td></tr></table></td></tr>

       		 </tbody>
    		</table>
    		</td></tr></msh:row>
            <msh:row><td><input type='button' onclick='cancell()' value='Закрыть'></td></msh:row>
        </msh:panel>
        </msh:ifFormTypeIsCreate>
        <msh:ifFormTypeAreViewOrEdit formName="pres_diagnosticPrescriptionForm">
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>  
        </msh:ifFormTypeAreViewOrEdit>
        <msh:row><td>
       
        </td></msh:row>              
      </msh:panel>
    </msh:form>
  </tiles:put>

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="pres_diagnosticPrescriptionForm" />
  </tiles:put>

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_diagnosticPrescriptionForm">
      <msh:sideMenu title="Назначения">
      <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Edit" params="id" action="/javascript:cancelDiagnostic()" name="Отменить" key="ALT+1"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Edit" params="id" action="/entityParentEdit-pres_diagnosticPrescription" name="Изменить" key="ALT+2"/>
        <msh:sideLink confirm="Удалить?" roles="/Policy/Mis/Prescription/ServicePrescription/Delete" params="id" action="/entityParentDelete-pres_diagnosticPrescription" name="Удалить" key="ALT+DEL"/>
      </msh:sideMenu>
      <msh:sideMenu title="Показать" >
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentListRedirect-pres_servicePrescription" name="К списку назначений на услугу" key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" key="ALT+4"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsCreate formName="pres_diagnosticPrescriptionForm">
    <tags:templatePrescription record="2" parentId="${param.prescriptionList}" name="add" />
    </msh:ifFormTypeIsCreate>
  </tiles:put>
</tiles:insert>