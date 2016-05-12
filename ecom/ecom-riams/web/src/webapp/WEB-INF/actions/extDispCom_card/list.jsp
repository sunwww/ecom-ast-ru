<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="mis_patientForm" title="Список карт медосмотров"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<msh:section createRoles="/Policy/Mis/ExtDisp/Card/Create" createUrl="entityParentPrepareCreate-extDispCom_card.do?id=${param.id}"
		title="Список карт медосмотров">
		<ecom:webQuery name="list" nativeSql="
		select edc.id as edcid,lpu.name as lpuname,
		ved.name as vedname, edc.startDate as edcStartDate,edc.finishDate as edcFinishDate
		from ExtDispCard edc
		left join MisLpu lpu on lpu.id=edc.lpu_id 
		left join VocExtDisp ved on ved.id=edc.dispType_id
		left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
		where edc.patient_id=${param.id} and (ved.isComission='1')
		"/>
		<msh:table name="list" action="entityView-extDispCom_card.do" idField="1" >
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ЛПУ" property="2" />
			<msh:tableColumn columnName="Дата начала" property="4" />
			<msh:tableColumn columnName="Дата окончания" property="5" />
			<msh:tableColumn columnName="Тип дополнительной диспансеризации" property="3" /></msh:table>
	</msh:section>
</tiles:put>
</tiles:insert>
