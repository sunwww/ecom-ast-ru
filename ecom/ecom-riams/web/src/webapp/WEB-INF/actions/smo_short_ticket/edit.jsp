<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-smo_short_ticket.do" defaultField="dateFinish">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medcard" />
      <msh:hidden property="patient" />
      <msh:hidden property="datePlan" />
      <msh:hidden property="timePlan" />
      <msh:hidden property="workFunctionPlan" />

      <msh:panel>
        <msh:row>
          <msh:textField label="Дата приема" property="dateFinish" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:autoComplete parentId="smo_short_ticketForm.medcard" vocName="workFunctionByTicket" property="workFunctionExecute" label="Специалист" fieldColSpan="3" size="100" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<td colspan="4"><div id='infoDirect'/></td>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Вид оплаты" horizontalFill="true" />
        </msh:row>   
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocWorkPlaceType" property="workPlaceType" viewOnlyField="false" label="Рабочее место" fieldColSpan="3" horizontalFill="true" />
        </msh:row>     
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocReason" property="visitReason" viewOnlyField="false" label="Цель визита" horizontalFill="true" fieldColSpan="3" />
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
        <msh:submitCancelButtonsRow colSpan="3"  />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsNotView formName="smo_short_ticketForm">
    	<tags:mis_double name='Ticket' title='Существующие направления в базе:' cmdAdd="document.forms[0].submitButton.disabled = false "/>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_short_ticketForm">
      <msh:sideMenu title="Талон">
        <msh:sideLink roles="/Policy/Poly/ShortTicket/Edit" key="ALT+3" params="id" action="/entityParentEdit-smo_short_ticket" name="Изменить" title="Изменить талон" />
        <msh:ifFormTypeAreViewOrEdit formName="smo_short_ticketForm">
          <msh:sideLink roles="/Policy/Poly/ShortTicket/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-smo_short_ticket" name="Удалить" confirm="Вы действительно хотите удалить талон?" title="Удалить талон" />
        </msh:ifFormTypeAreViewOrEdit>
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
        <msh:sideLink roles="/Policy/Poly/ShortTicket/View" key="SHIFT+8" params="id" action="/print-ticketshort.do?s=PrintTicketService&amp;m=printInfo&amp;next=entityParentView-smo_short_ticket.do__id=${param.id}&noView=1t&id=${param.id}" name="Талон" title="Печатать талон" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="smo_short_ticketForm" />
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
