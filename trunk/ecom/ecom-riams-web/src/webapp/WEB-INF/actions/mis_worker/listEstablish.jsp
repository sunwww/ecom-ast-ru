<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Список сотрудников" guid="e51b1bad-82ba-4906-9829-7d9148b1174a" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="360e85c3-7aa1-4a04-8c1d-a9c0a6739efa" title="Добавить">
      <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-mis_worker" name="Добавить сотрудника" guid="0fd18715-d91c-422d-87e7-aafe9a3c0ca8" roles="/Policy/Mis/Worker/Worker/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти" guid="720b3f9b-02cd-4235-a1ba-dc12a6dc356b">
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/Create" key="ALT+2" params="id" action="/entityParentList-work_groupWorkFunction" name="К списку рабочих групп" title="Перейти к списку рабочих групп" guid="712b4156-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+3" params="id" action="/js-mis_worker-archives" name="К списку архивных рабочих функций" title="Перейти к списку архивных рабочих групп" guid="712b4156-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+4" params="id" action="/js-mis_worker-running" name="К списку действующих рабочих функций" title="Перейти к списку действующих рабочих групп" guid="712b4156-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+5" params="id" action="/js-mis_worker-empty" name="К списку сотрудников без раб.функции" title="Перейти к списку сотрудников без раб.функции" guid="712b4156-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+6" params="id" action="/js-mis_worker-all" name="К полному списку сотрудников" title="Перейти к полному списку сотрудников" guid="712b-54e2-4e7b-b0da-a46821eba3de" />
      <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+7" params="id" action="/js-mis_worker-pattern" name="Шаблоны расписания сотрудников" title="Перейти к установке шаблонов календарей по специалистам" guid="712b-54e2-4e7b-b0da-a46821eba3de" styleId="selected"/>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:section title="Рабочие функции">
  	<ecom:webQuery name="listPerson" nativeSql="
  	select wf.id
  	,coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename as fio
  	,wf.groupName,'нет данных') as infowf
  	, vwf.name as vwfname
  	,wc.id as wcid ,wf.code
  	from workfunction wf 
  	left join worker w on w.id=wf.worker_id 
  	left join Patient wp on wp.id=w.person_id 
  	left join WorkFunction gr on gr.id=wf.group_id
  	 left join WorkCalendar wc on wc.workFunction_id=wf.id  
  	 left join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
  	 where ((w.lpu_id=${param.id} and wf.DTYPE='PersonalWorkFunction') or
  	 (wf.lpu_id=${param.id} and wf.DTYPE='GroupWorkFunction') ) 
  	 and (wf.archival is null or cast(wf.archival as integer)=0) 
  	 and wf.group_id is null
  	 and wc.id is not null
  	 order by wf.groupName, wp.lastname,wp.middlename,wp.firstname
  	 "/>
  	 <msh:tableNotEmpty name="listPerson">
		  	<msh:toolbar >
			                	<tbody>
			                		<msh:toolbar>
			                		<form >
			                			<table>
			                				
				                		<msh:row>
			                					<msh:textField property="beginDate" label="Дата начала"/>
			                					<msh:textField property="finishDate" label="Дата окончания"/>
				                		</msh:row>
				                		<msh:row>
				                			<th class='linkButtons' colspan="6">
			                					<input type='button' value='Генерировать календарь' onclick="javascript:generateWorkFunction('add','listPerson')" />
			                					<input type='button' value='Очистить незанятые времена' onclick="javascript:deleteGenerate('add','listPerson')" />
			                					<input type='button' value='Оформить неявки на прием' onclick="javascript:deleteNoAppearance('add','listPerson')" />
			                				</th>
				                		</msh:row>
				                		<msh:row>
				                			<th class='linkButtons' colspan="6">
			                					<input type='button' value='Перенести сгенерированные времена с даты начала на дату окончания' onclick="javascript:moveDate('add','listPerson')" />
			                					<font color="blue">если нет сгенерированных времен на дате окончания</font>>
			                				</th>
				                		</msh:row>
		                					<msh:autoComplete parentId="${param.id}" property="pattern" horizontalFill="true"
			                					 label="Шаблон" vocName="workCalendarPatternByLpu"  fieldColSpan="3"/>
					                		<th class='linkButtons' colspan="2">
			                					<input type='button' value='Добавить шаблон календаря' onclick="javascript:addJournalPattern('add','listPerson')" />
		                					</th>
				                		<msh:row>
		                					 <msh:autoComplete property="busy" horizontalFill="true"
			                					 label="Причина" vocName="vocWorkBusyIsNot" fieldColSpan="3"/>
					                		<th class='linkButtons' colspan="2">
			                					 <input type='button' value='Добавить в нерабочее время' onclick="javascript:addNotWorking('delete','listPerson')" />
			                				</th>				                		
				                		</msh:row>
				                		</table>
				                		</form>
			                		</msh:toolbar>
			                	</tbody>
		  	</msh:toolbar>
  	</msh:tableNotEmpty>
    <msh:table selection="true" viewUrl="entitySubclassShortView-work_workFunction.do" name="listPerson" action="entitySubclassView-work_workFunction.do" idField="1" guid="d20ae6f6-f534-4d56-affe-ff02d3034d32">
      <msh:tableColumn columnName="#" property="sn" guid="4797" />
      <msh:tableColumn columnName="Код" property="5" guid="4ceb96e" />
      <msh:tableColumn columnName="ФИО (Название группы)" property="2" guid="4ceb96e" />
      <msh:tableColumn columnName="Должностные обязанности" property="3"/>
      <msh:tableColumn columnName="Рабочий календарь" property="4"/>
    </msh:table>
  </msh:section>
  
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/WorkCalendarService.js"></script>
  	<script type="text/javascript">
  		if ($('beginDate')) new dateutil.DateField($('beginDate')) ;
  		if ($('finishDate')) new dateutil.DateField($('finishDate')) ;
  		function addNotWorking(add,list) {
  			var ids = theTableArrow.getInsertedIdsAsParams("id",list) ;
  			alert('Установить не рабочее время') ;
  			if (ids) {
  				
                window.location = 'cal_workCalendar-journal.do?functionJournal=addNotWorking&lpuId=${param.id}&beginDate='+$('beginDate').value+'&busy='+$('busy').value+'&finishDate=' +$('finishDate').value+"&"+ ids;
            } else {
                alert("Нет выделенных функций");
            }  			
  		}
  		function moveDate(add,list) {
  			var ids = theTableArrow.getInsertedIdsAsParams("id",list) ;
  			if (ids) {
  				
                window.location = 'cal_workCalendar-journal.do?functionJournal=moveDate&lpuId=${param.id}&beginDate='+$('beginDate').value+'&finishDate=' +$('finishDate').value+"&"+ ids;
            } else {
                alert("Нет выделенных функций");
            }  			
  		}
  		function generateWorkFunction(add,list) {
  			alert('Генерирование расписания по рабочим функциям') ;
  			var ids = theTableArrow.getInsertedIdsAsParams("id",list) ;
               if (ids) {
                   window.location = 'cal_workCalendar-journal.do?functionJournal=generate&lpuId=${param.id}&beginDate='+$('beginDate').value+'&finishDate=' +$('finishDate').value+"&"+ ids;
                   //alert( 'cal_workCalendar-generate.do?lpuId=${param.id}&beginDate='+$('beginDate').value+'&finishDate=' +$('finishDate').value+"&"+ ids);
               } else {
                   alert("Нет выделенных функций");
               }
  		}
  		function addJournalPattern(add,list) {
  			var ids = theTableArrow.getInsertedIdsAsParams("id",list) ;
  			alert('Добавить шаблон') ;
  			if (ids) {
                window.location = 'cal_workCalendar-journal.do?functionJournal=addBusyPattern&lpuId=${param.id}&beginDate='+$('beginDate').value+'&pattern='+$('pattern').value+'&finishDate=' +$('finishDate').value+"&"+ ids;
            } else {
                alert("Нет выделенных функций");
            }
  			
  		}
  		function deleteGenerate(add,list) {
  			
  			alert('Удалить не использованное время') ;
  			var ids = theTableArrow.getInsertedIdsAsParams("id",list) ;
            if (ids) {
                window.location = 'cal_workCalendar-journal.do?functionJournal=deleteFreeTime&lpuId=${param.id}&beginDate='+$('beginDate').value+'&finishDate=' +$('finishDate').value+"&"+ ids;
                //alert( 'cal_workCalendar-generate.do?lpuId=${param.id}&beginDate='+$('beginDate').value+'&finishDate=' +$('finishDate').value+"&"+ ids);
            } else {
                alert("Нет выделенных функций");
            }
  			
  		}
  		function deleteNoAppearance(add,list) {
  			alert('Удалить все не якви:)') ;
  			var ids = theTableArrow.getInsertedIdsAsParams("id",list) ;
            if (ids) {
                window.location = 'cal_workCalendar-journal.do?functionJournal=deleteNoAppearance&lpuId=${param.id}&beginDate='+$('beginDate').value+'&finishDate=' +$('finishDate').value+"&"+ ids;
                //alert( 'cal_workCalendar-generate.do?lpuId=${param.id}&beginDate='+$('beginDate').value+'&finishDate=' +$('finishDate').value+"&"+ ids);
            } else {
                alert("Нет выделенных функций");
            }
  		}
  		if ('${param.beginDate}'!='') {
  			$('beginDate').value='${param.beginDate}' ;
  		}
  		if ('${param.finishDate}'!='') {
  			$('finishDate').value='${param.finishDate}' ;
  		}
  	</script>
  </tiles:put>
</tiles:insert>

