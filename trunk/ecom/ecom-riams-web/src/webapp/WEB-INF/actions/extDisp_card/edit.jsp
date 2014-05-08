<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-extDisp_card.do" defaultField="lpuName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="patient" />
			<msh:panel colsWidth="1%,1%,1%,97%">
				<msh:row>
					<msh:autoComplete property="lpu" label="ЛПУ" vocName="lpu" horizontalFill="true" fieldColSpan="3" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="socialGroup" label="Социальная группа" vocName="vocExtDispSocialGroup" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="dispType" label="Тип доп. диспансеризации" vocName="vocExtDisp" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
        		<msh:row>
					<msh:textField property="startDate" label="Дата начала"/>
					<msh:textField property="finishDate" label="окончания"/>
				</msh:row>
				<msh:row>
					<msh:textField property="age" label="Возраст" horizontalFill="true" viewOnlyField="true"  />
					<msh:autoComplete property="ageGroup" label="Возрастная группа" vocName="vocExtDispAgeGroupByDispType" parentAutocomplete="dispType" horizontalFill="true" fieldColSpan="1"/>
				</msh:row>
		        <msh:row>
		        	<msh:autoComplete property="kinsman" label="Представитель" 
		        		parentId="extDisp_cardForm.patient" vocName="kinsmanBySMO" horizontalFill="true" fieldColSpan="3"/>
		        </msh:row>				
		        <msh:row>
		        	<msh:autoComplete vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" 
		          		label="Раб.функция (терапевт)" fieldColSpan="3" horizontalFill="true" size="150" />
		        </msh:row>
				<msh:row>
					<msh:autoComplete property="idcMain" label="МКБ основного диагноза" vocName="vocIdc10" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="onDeparture" label="На выезде"/>
					<msh:checkBox property="hospitalized" label="Госпитализирован"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="healthGroup" label="Группа здоровья" parentAutocomplete="dispType" vocName="vocExtDispHealthGroupByDispType" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isServiceIndication" label="Направлен на след. этап" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isSmallNation" label="Принадлежность к коренным малочисленным народам Севера, Сибири и Дальнего Востока РФ" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isTreatment" label="Назначено лечение" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isSpecializedCare" label="Дано направление  для  получения  специализированной,  в  том  числе ВМП" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isObservation" label="Взят на диспансерное наблюдение" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isSanatorium" label="Дано направление на санаторно-курортное лечение" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="isDiagnostics" label="Назначена дополнительное диагностическое исследование" fieldColSpan="3"/>
				</msh:row>
			</msh:panel>
			<msh:panel>
				<msh:row>
					<ecom:checkGroup label="Риски" tableName="VocExtDispRisk" tableField="name" tableId="id" property="risks"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="extDisp_cardForm">
			<msh:section title="Обследования" >
			<ecom:webQuery name="examQuery" nativeSql="
select eds.card_id as adscard,coalesce(veds.code,'') ||' '||coalesce(veds.name,'') as vedsname
,eds.servicedate,eds.isPathology as edsIsPathology
from ExtDispService eds 
left join VocExtDispService veds on veds.id=eds.serviceType_id
where eds.card_id=${param.id} and eds.dtype='ExtDispExam'
			"/>
			
				<msh:table name="examQuery" action="js-extDisp_service-edit.do" idField="1">
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
from extdispservice eds
left join VocExtDispService veds on veds.id=eds.servicetype_id
where eds.card_id='${param.id}' and eds.dtype='ExtDispVisit'
			"/>
				<msh:table  name="examQuery" action="js-extDisp_service-edit.do" idField="1">
					<msh:tableColumn columnName="Услуга" property="2"/>
					<msh:tableColumn columnName="Дата" property="3"/>
					<msh:tableColumn columnName="Рекомендации" property="4"/>
					<msh:tableColumn columnName="Подозрение на ранее перенесенное нарушение мозгового кровообращения" property="5"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
		<script type="text/javascript">
		eventutil.addEventListener($('finishDate'),'change',function(){updateAge() ;}) ;
		eventutil.addEventListener($('finishDate'),'blur',function(){updateAge() ;}) ;
    	function updateAge() {
    		PatientService.getAgeForDisp($('patient').value, $('finishDate').value, {
        		callback: function(aResult) {
       				$('ageReadOnly').value=aResult ;
        		}
        	});
    	}
    	updateAge() ;
		</script>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="extDisp_cardForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="extDisp_cardForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-extDisp_card" name="Изменить" title="Изменить" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
				<msh:sideLink confirm="Удалить доп.диспансеризацию?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-extDisp_card" name="Удалить" title="Удалить" roles="/Policy/Mis/ExtDisp/Card/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/js-extDisp_service-edit" name="Услуги" title="Услуги" roles="/Policy/Mis/ExtDisp/Card/Edit"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
