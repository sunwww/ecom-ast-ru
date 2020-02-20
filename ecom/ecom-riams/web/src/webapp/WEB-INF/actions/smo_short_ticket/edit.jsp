<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-smo_short_ticket.do" defaultField="dateFinish" guid="77bf3d00-cfc6-49eb-9751-76e82d38751c">
      <msh:hidden property="id" guid="e862851f-7390-4fe6-9a37-3b22306138b4" />
      <msh:hidden property="saveType" guid="3e3fb7b5-258e-4194-9dbe-5093382cf627" />
      <msh:hidden property="medcard" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:hidden property="patient" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:hidden property="datePlan" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:hidden property="timePlan" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:hidden property="workFunctionPlan" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />

      <msh:panel>
        <msh:row guid="59560d9f-0765-4df0-bfb7-9a90b5eed824">
          <msh:textField label="Дата приема" property="dateFinish" fieldColSpan="1" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" />
        </msh:row>
        <msh:row guid="47073a0b-da87-49e0-9ff0-711dc597ce07">
          <msh:autoComplete parentId="smo_short_ticketForm.medcard" vocName="workFunctionByTicket" property="workFunctionExecute" label="Специалист" fieldColSpan="3" size="100" horizontalFill="true" guid="a8404201-1bae-467e-b3e9-5cef63411d01" />
        </msh:row>
        <msh:row>
        	<td colspan="4"><div id='infoDirect'/></td>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Вид оплаты" horizontalFill="true" guid="e5ac1267-bc69-44b2-8aba-b7455ac064c4" />
        </msh:row>   
        <msh:row guid="a970fa32-6038-4e0b-a0a8-c57b5ebd3a04">
          <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="workPlaceType" viewOnlyField="false" label="Рабочее место" guid="c61023b1-bf59-432f-8764-8a571b1eddf8" fieldColSpan="3" horizontalFill="true" />
        </msh:row>     
        <msh:row guid="16db3ed2-4536-4a64-9cac-b73390bf65d6">
          <msh:autoComplete showId="false" vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" guid="5f53c276-d7de-423a-86f5-b523ed37e75d" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:ifFormTypeAreViewOrEdit formName="smo_short_ticketForm">
        <msh:row>
        	<msh:textField label="Дата выдачи" property="createDate" fieldColSpan="1" viewOnlyField="true"/>
        	<msh:textField label="Время выдачи" property="createTime" fieldColSpan="1" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Пользователь" property="username" viewOnlyField="true" />
        </msh:row>
        </msh:ifFormTypeAreViewOrEdit>
        <msh:submitCancelButtonsRow colSpan="3" guid="13aa4bce-1133-48d6-896b-eb588a046d59"  />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsNotView formName="smo_short_ticketForm">
    	<tags:mis_double name='Ticket' title='Существующие направления в базе:' cmdAdd="document.forms[0].submitButton.disabled = false "/>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_short_ticketForm" guid="8f-4d80-856b-ce3095ca1d">
      <msh:sideMenu guid="e6c81315-888f-4d80-856b-ce3095ca1d55" title="Талон">
        <msh:sideLink roles="/Policy/Poly/ShortTicket/Edit" key="ALT+3" params="id" action="/entityParentEdit-smo_short_ticket" name="Изменить" guid="89585df8-aadb-4d59-abd9-c0d16a6170a9" title="Изменить талон" />
        <msh:ifFormTypeAreViewOrEdit formName="smo_short_ticketForm" guid="7f581b0a-a8b3-4d57-9cff-6dc6db1c85e3">
          <msh:sideLink roles="/Policy/Poly/ShortTicket/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-smo_short_ticket" name="Удалить" confirm="Вы действительно хотите удалить талон?" guid="8b9de89f-3b99-414e-b4af-778ccbb70edf" title="Удалить талон" />
        </msh:ifFormTypeAreViewOrEdit>
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="62fd4ce0-85b5-4661-87b2-fea2d4fb7339">
        <msh:sideLink roles="/Policy/Poly/ShortTicket/View" key="SHIFT+8" params="id" action="/print-ticketshort.do?s=PrintTicketService&amp;m=printInfo&amp;next=entityParentView-smo_short_ticket.do__id=${param.id}&noView=1t&id=${param.id}" name="Талон" title="Печатать талон" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="smo_short_ticketForm" guid="5c4f3682-e66b-4e0d-b448-4e6a2961a943" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="smo_short_ticketForm">
    <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
    <script type="text/javascript" src="./dwr/interface/WorkCalendarService.js"></script>
    <script type="text/javascript">
    var oldaction = document.forms[0].action ;
    document.forms[0].action = 'javascript:isExistTicket(this)';
    function getFreeTimeForWorkfunction() {
        var wf = +$("workFunctionExecute").value ;
        var ds = $("dateFinish").value ;
        if (wf>0 && ds.length==10) {
            WorkCalendarService.getFreeCalendarTimeForWorkFunction(wf, ds, {
                callback:function(ret) {
                    ret = JSON.parse(ret);
                    if (ret.status=="ok") {
                        $('timePlan').value=ret.timeId;
                        $('datePlan').value=ret.calendarDateId;
                        $('mainForm').name="smo_directionForm";
                        $('workFunctionPlan').value=$('workFunctionExecute').value;
                        oldaction="entitySaveGoView-smo_direction.do";
                    }
                    document.forms[0].action = oldaction ;
                    document.forms[0].submit() ;
                }
            });
        }
    }

    workFunctionExecuteAutocomplete.addOnChangeCallback(function() {
    	getInfoByWorker();
    //	getFreeTimeForWorkfunction();
	});

    function isExistTicket() {
		 if (!$('emergency').checked) {
			 TicketService.checkPolicyByMedcard($('medcard').value,$('dateFinish').value,$('serviceStream').value
					 , {
				 callback: function(aResult) {
					 if (+aResult<1) {
							checkDouble();
				  	  } else {
				  		  alert('У Вас стоит запрет на запись пациентов по ОМС без полиса!!!');
				  		  document.forms[0].submitButton.disabled = false ;
				  	  } 
					 }
				 });
		  } else {
			 checkDouble();
		  }
		 
	}
    function checkDouble() {
  	  TicketService.findDoubleBySpecAndDate($('id').value,$('medcard').value
	    			,$('workFunctionExecute').value  , $('dateFinish').value
	  		, {
	                 callback: function(aResult) {
	                    if (aResult) {
					    		showTicketDouble(aResult) ;
	                     } else {
                            getFreeTimeForWorkfunction();

	                     }
	                 }
		        	}
		  ); 
    }
    function getInfoByWorker() {
    	var wf = +$("workFunctionExecute").value ;
    	var ds = $("dateFinish").value ;
    	
    	if (wf>0 && ds.length==10) {
		  	TicketService.getInfoByWorkFunctionAndDate($('medcard').value,wf,ds,{
				callback: function(aResult) {
					if (aResult!="") {
			  				$('infoDirect').innerHTML = aResult;
					} else {
		  				$('infoDirect').innerHTML = "";
					}
				}
			  	
			}) ;
    		
		} else {
			$('infoDirect').innerHTML = "Указаны не все данные";
		}
    }
    </script>
    
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>
