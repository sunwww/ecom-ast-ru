<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="mis_patientForm" title="Список карт медосмотров"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/ExtDisp/Card/Create" params="id" action="/entityParentPrepareCreate-extdisp_extDispCardCom" title="Карта медосмотра" name="Карта медосмотра" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<msh:section createRoles="/Policy/Mis/ExtDisp/Card/Create" createUrl="entityParentPrepareCreate-extDispCom_card.do?id=${param.id}"
		title="Список карт медосмотров">
		<ecom:webQuery name="list" nativeSql="
		select edc.id as edcid,lpu.name as lpuname
		,edc.hospitalized ,  vedsg.name as vedsgname
		, ved.name as vedname, edc.startDate as edcStartDate,edc.finishDate as edcFinishDate
		,vedhg.name as vedhgname,vi.name as viname
		,edc.onDeparture,edc.isObservation,edc.isTreatment,edc.isDiagnostics
		,edc.isSpecializedCare,edc.isSanatorium,edc.isSmallNation
		from ExtDispCard edc
		left join MisLpu lpu on lpu.id=edc.lpu_id
		left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
		left join VocExtDisp ved on ved.id=edc.dispType_id
		left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
		left join VocIdc10 vi on vi.id=edc.idcMain_id
		where edc.patient_id=${param.id} and (ved.isComission='1')
		"/>
		<msh:table name="list" action="entityView-extDispCom_card.do" idField="1" >
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ЛПУ" property="2" />
			<msh:tableColumn columnName="Дата начала" property="6" />
			<msh:tableColumn columnName="Дата окончания" property="7" />
			<msh:tableColumn columnName="Госпитализирован" property="3" />
			<msh:tableColumn columnName="Социальная группа" property="4" />
			<msh:tableColumn columnName="Тип дополнительной диспансеризации" property="5" />
			<msh:tableColumn columnName="Группа здоровья дополнительной диспансеризации" property="8" />
			<msh:tableColumn columnName="МКБ основного диагноза" property="9" />
		</msh:table>
	</msh:section>
</tiles:put>
</tiles:insert>
