<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <style type="text/css">
        .recordButton {
            font-size: 20px;
            margin-bottom: 5px;
            margin-left:5px;
            padding: 4px 5px;
        }
        .recordButtonDisabled {
            border: 1px solid #999999;
            background-color: #cccccc;
            color: #666666;
        }
        .recordButtonUnClicked {
            background-color:green
        }
        .recordButtonClicked {
            background-color:green
        }
    </style>
	<tiles:put name="javascript" type="string">
	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
	<script type="text/javascript" src="./dwr/interface/WorkCalendarService.js"></script>
	<msh:ifFormTypeIsNotView formName="pres_operationPrescriptionForm">
	<script type="text/javascript">

	function checkDoubles () {
		labList="";
        var i = 0;
        // Id from aList
		var str="";
		var aList =labList;
		aList = aList.substring(0,aList.length-1);
		var aListArr = aList.split("#");
		if (aListArr.length>0) {
			for (i = 0; i<aListArr.length; i++) {
				var id = aListArr[i].split(":");
			str+=id[0]+":";
			}
		}
		str=str.substring(0,str.length-1);
				PrescriptionService.getDuplicatePrescriptions($("medcaseId").value, str,{
					callback: function(aResult) {
						if (aResult.length>0){
							var aText = "Данные назначения\n "+aResult+"\nуже назначены пациенту в этой истории болезни, все равно назначить?";
								if (!confirm (aText)) {							
									document.getElementById('submitButton').disabled=false;
									document.getElementById('submitButton').value='Создать';
									}
						}
					}
				});		

	

	}

	getPreRecord();

	surgCalDateAutocomplete.addOnChangeCallback(function(){
	  	  getPreRecord() ;
        updateOperationParent();
    });
	prescriptCabinetAutocomplete.addOnChangeCallback(function(){
		updateDefaultDate() ;
        $('labDepartment').value=0;
        if ($('prescriptCabinet').value>0) {
            PrescriptionService.getDepartmentFromWorkfunction(+$('prescriptCabinet').value, {
                callback: function (depId) {
                    $('labDepartment').value = +depId;
                    updateOperationParent();
                }
            });
        } else {
            $('labDepartment').value=0;
        }
	}) ;

    intakeSpecialAutocomplete.addOnChangeCallback(function() {updateOperationParent();});
	/*обновляем парент у справочника операций*/
	function updateOperationParent() {
	    //date#wfId#depId#serviceStreamId
        var str = $('planStartDate').value.trim()+'#'+$('intakeSpecial').value+'#'+$('labDepartment').value+"#"+$('serviceStream').value;
        medServiceAutocomplete.setParentId(str);

    }

	function getPreRecord() {
		if ($('tdPreRecord')) {
			if ($('surgCalDate') && +$('surgCalDate').value>0) {
	  			WorkCalendarService.getTimesByCalendarDay($('surgCalDate').value,$('duration').value, {
	  		  				callback:function(arr) {
	  		  				    console.log(arr);
	  		  				    arr = JSON.parse(arr);
	  		  				    var last = '00';
	  		  				    var txt="";
	  		  				    var newLineNums=['00','03','06','09','12','15','18','21'];
	  		  				    for (var i=0;i<arr.length;i++) {
	  		  				        var el = arr[i];
	  		  				        var hour =el.timefrom.substring(0,2);
	  		  				        if (last!=hour && newLineNums.indexOf(hour)!=-1) {
	  		  				            last=hour;
	  		  				            txt+='<br>';
                                    }
	  		  				        if (el.isrest===true) { //нельзя назначить
                                        txt+= "<input name='recordButton' class='recordButton recordButtonDisabled' type='button' alt='Занято' value='"+el.timefrom+"'>";
                                    } else {
                                        txt+= "<input name='recordButton' class='recordButton recordButtonNoClicked' type='button' onclick='checkRecord(this, "+el.id+",\""+el.timefrom+"\")' value='"+el.timefrom+"'>";
                                    }

                                }
                                $('tdPreRecord').innerHTML=txt;
	  			  			}
	  		  			}) ;
	  			$('planStartDate').value=$('surgCalDateName').value;
	  			} else {
	  				$('tdPreRecord').innerHTML="";
	  			}
		}
	}

    //нужно, не убираем
    function checkRecord(aBtn, aId, aValue) {
	    jQuery("input[name='recordButton']").removeClass('recordButtonClicked', false);
	    jQuery(aBtn).removeClass('recordButtonNoClicked', false);
	    jQuery(aBtn).addClass('recordButtonClicked');

        $('calendarTime').value = aId;
        $('planStartTime').value = aValue;
        $('planStartTimeReadOnly').value = aValue;
    }

	function updateDefaultDate() {
	    if ($('prescriptCabinet').value>0) {
            WorkCalendarService.getDefaultDate($('prescriptCabinet').value, {
                    callback:function(aDateDefault) {
                        if (aDateDefault!=null) {
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
                }
            ) ;
        }
			$('calendarTime').value="0" ;
		}

			</script>
			</msh:ifFormTypeIsNotView>
			</tiles:put>

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-pres_operationPrescription.do" defaultField="id" title="Назначение операции">
      <msh:hidden property="id" />
      <msh:hidden property="prescriptionList" />
      <msh:hidden property="saveType" />
      <msh:hidden property="labList" />
      <msh:hidden property="medcaseId" />
      <msh:hidden property="serviceStream" />
      <msh:hidden property="labDepartment" />
        <msh:hidden property="calendarTime" />
        <msh:hidden property="planStartDate" />
      <msh:panel colsWidth="3">
         <msh:row>
          <msh:autoComplete property="prescriptSpecial" label="Назначил" size="100" vocName="workFunction" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
        </msh:row>
         <msh:ifFormTypeIsView formName="pres_operationPrescriptionForm">
             <msh:row>
                 <msh:autoComplete property="prescriptCabinet" label="Операционная"  viewOnlyField="true" vocName="operationRoom" size='20' horizontalFill="true" />
             </msh:row>
             <msh:row>
    			<msh:autoComplete property="medService" label="Исследование" vocName="surgicalOperations" horizontalFill="true" size="90" viewOnlyField="true" />
    		 </msh:row>

			<msh:row>
				 <msh:textField property="planStartDate" size="10" fieldColSpan="1" />
    		</msh:row>
			<msh:row>
				 <msh:textArea property="comments" label="Примечание" size="50" fieldColSpan="4" />
			</msh:row>
      
    </msh:ifFormTypeIsView>
         <msh:ifFormTypeIsCreate formName="pres_operationPrescriptionForm">
        <msh:panel styleId="tblSurgOperation">
       	 <msh:row>
        	<msh:separator label="Операции" colSpan="10"/>
        </msh:row>
        <msh:row>
        <tr><td>
        <table id="surgTable">
        <tbody id="addsurgElements">
    		<msh:row>
				 <msh:autoComplete property="prescriptCabinet" label="Операционная"  fieldColSpan="5" vocName="operationRoom" size='20' horizontalFill="true" />
			</msh:row>
            <msh:row>
                <msh:autoComplete property="surgCalDate" parentAutocomplete="prescriptCabinet" vocName="vocWorkCalendarDayByWorkFunction" label="Дата" size="10" fieldColSpan="1" />
                <msh:textField property="planStartTime" label="Начало операции" viewOnlyField="true"/>
            </msh:row><msh:row>
                <msh:autoComplete property="duration" vocName="durationInMinute" size="40" fieldColSpan="5"/>
            </msh:row>
            <msh:row>
                <msh:autoComplete property="intakeSpecial" vocName="workFunctionIsSurgical" label="Хирург" size="100" fieldColSpan="5" horizontalFill="true" />
            </msh:row>
            <msh:row>
                <msh:autoComplete property="medService" label="Операция" vocName="medServiceOperation" horizontalFill="true" size="90" fieldColSpan="5" />
            </msh:row>
    		<msh:row>
                <msh:autoComplete property="anesthesiaType" vocName="vocAnesthesia" label="Анестезия" size="100" fieldColSpan="5" horizontalFill="true" />
            </msh:row>
        <msh:row>
            <msh:autoComplete property="bloodGroup" fieldColSpan="1" label="Группа крови" vocName="vocBloodGroup" />
            </msh:row><msh:row>
            <msh:autoComplete property="rhesusFactor" label="Резус-фактор" vocName="vocRhesusFactor"  />
        </msh:row>
			<msh:row>
				 <msh:textArea property="comments" label="Примечание" size="50" fieldColSpan="5" />
			</msh:row>
	         <tr><td colspan="10"><table><tr><td valign="top"><table>
        <msh:row>
          <msh:separator label="Выбор времени операции" colSpan="4" />
        </msh:row>
        <msh:row>
        	<td colspan="4" id="tdPreRecord"></td>
        </msh:row>
        </table>
        </td></tr></table></td></tr>
           </tbody>
    		</table>
    		</td></tr></msh:row>
        </msh:panel>
        </msh:ifFormTypeIsCreate>
        <msh:ifFormTypeAreViewOrEdit formName="pres_operationPrescriptionForm">
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
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <tags:enter_date name="2" functionSave="prepare1RowByDate"/>
   
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="pres_operationPrescriptionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_operationPrescriptionForm">
      <msh:sideMenu title="Назначения">
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/Edit" params="id" action="/entityParentEdit-pres_operationPrescription" name="Изменить" key="ALT+2"/>
        <msh:sideLink confirm="Удалить?" roles="/Policy/Mis/Prescription/ServicePrescription/Delete" params="id" action="/entityParentDelete-pres_operationPrescription" name="Удалить" key="ALT+DEL"/>
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Create" params="id" action="/entityParentPrepareCreate-pres_prescriptionFulfilment" name="Исполнение назначения" key="ALT+3"/>
      </msh:sideMenu>
      <msh:sideMenu title="Показать" >
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentListRedirect-pres_operationPrescription" name="К списку назначений на операцию" key="ALT+4"/>
        <msh:sideLink roles="/Policy/Mis/Prescription/View" params="id" action="/entityParentListRedirect-pres_prescription" name="К списку назначений" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsCreate formName="pres_operationPrescriptionForm">
    <tags:templatePrescription record="2" parentId="${param.prescriptionList}" name="add" />
    <tags:pres_addFromDirections parentID='${param.prescriptionList}' name='Show'/>

    </msh:ifFormTypeIsCreate>
  </tiles:put>
</tiles:insert>