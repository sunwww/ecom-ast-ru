<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-extDispCom_card.do" defaultField="lpuName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="patient" />
			<msh:panel colsWidth="1%,1%,1%,97%">
				<msh:row>
					<msh:autoComplete property="lpu" label="ЛПУ" vocName="lpu" horizontalFill="true" fieldColSpan="3" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="serviceStream" label="Источник финансирования" vocName="vocServiceStream" horizontalFill="true" fieldColSpan="3" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="dispType" label="Тип доп. диспансеризации" vocName="extDispCom" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				 <msh:row>
					<msh:autoComplete property="idcMain" label="МКБ основного диагноза" vocName="vocIdc10" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="ageGroup" label="Возрастная группа" vocName="vocExtDispAgeGroupByDispType" parentAutocomplete="dispType" horizontalFill="true" fieldColSpan="1"/>
				</msh:row>
        		<msh:row>
					<msh:textField property="startDate" label="Дата начала"/>
					<msh:textField property="finishDate" label="окончания"/>
				</msh:row>
				<msh:row>
		        	<msh:autoComplete vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" 
		          		label="Раб.функция (терапевт)" fieldColSpan="3" horizontalFill="true" size="150" />
		        </msh:row>
				</msh:panel>
			<msh:panel>
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
        <msh:row>
        	<msh:label property="editDateRender" label="Дата ред. услуги"/>
        	<msh:label property="editTimeRender" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsernameRender" label="пользователь"/>
        </msh:row>   
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="extDispCom_cardForm">
			<msh:section title="Обследования" >
			<ecom:webQuery name="examQuery" nativeSql="
select eds.card_id as adscard,coalesce(veds.code,'') ||' '||coalesce(veds.name,'') as vedsname
,eds.servicedate,eds.isPathology as edsIsPathology
from ExtDispService eds 
left join VocExtDispService veds on veds.id=eds.serviceType_id
where eds.card_id=${param.id} and eds.dtype='ExtDispExam'
			"/>
			
				<msh:table name="examQuery" action="js-extDispCom_service-edit.do" idField="1">
					<msh:tableColumn columnName="Услуга" property="2"/>
					<msh:tableColumn columnName="Дата" property="3"/>
					<msh:tableColumn columnName="Выявлена патология" property="4"/>
				</msh:table>
			</msh:section>
			<msh:section title="Посещения">
			<ecom:webQuery name="examQuery" nativeSql="
select eds.card_id as edsid
,coalesce(veds.code,'') ||' '||coalesce(veds.name,'') as vedsname 
, to_char(eds.serviceDate,'dd.mm.yyyy') as servicedate
,eds.recommendation as edsRecommendation
,eds.isEtdccSuspicion as edsIsaf
,vwf.name||' '|| pw.lastname||' '||pw.firstname||' '||pw.middlename as worker
,mkb.code || ' ' || mkb.name as diagnosis
from extdispservice eds
left join workfunction wf on wf.id=eds.workfunction_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
left join worker wrk on wrk.id=wf.worker_id
left join patient pw on pw.id=wrk.person_id
left join vocIdc10 mkb on mkb.id=eds.idc10_id
left join VocExtDispService veds on veds.id=eds.servicetype_id
where eds.card_id='${param.id}' and eds.dtype='ExtDispVisit'
			"/>
				<msh:table  name="examQuery" action="js-extDispCom_service-edit.do" idField="1">
					<msh:tableColumn columnName="Услуга" property="2"/>
					<msh:tableColumn columnName="Дата" property="3"/>
					<msh:tableColumn columnName="Рабочая функция" property="6"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
		
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="extDispCom_cardForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="extDispCom_cardForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-extDispCom_card" name="Изменить" title="Изменить" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
				<msh:sideLink confirm="Удалить медосмотр?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-extDispCom_card" name="Удалить" title="Удалить" roles="/Policy/Mis/ExtDisp/Card/Delete"/>
			 
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/js-extDispCom_service-edit" name="Услуги" title="Услуги" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
			</msh:sideMenu>
			<msh:sideMenu title="Печать" >
	 <msh:sideLink action="/javascript:window.location.href='print-immig.do?s=PatientPrintService&m=printInfo&id='+$('patient').value" 
        name="Карты осмотра (иммигранта)" title="Печать карты осмотра (иммигранта)" 
        roles="/Policy/Mis/Patient/View" />
        </msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
		
	</tiles:put>
</tiles:insert>
