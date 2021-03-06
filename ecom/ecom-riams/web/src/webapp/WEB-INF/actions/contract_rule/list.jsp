<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_medContractForm" title="Список договорных правил"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/ContractRule/Create" params="id" action="/entityParentPrepareCreate-contract_rule" title="Договорное правило" name="Договорное правило" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
			<msh:section title="Договорные правила">
			<ecom:webQuery name="rules" nativeSql="select cr.id,cr.name as crname
			,coalesce(vcrp.name,cr.dateFrom||'-'||coalesce(to_char(cr.dateTo,'dd.mm.yyyy'),'неограничено')) as p3eriod
			,cr.medserviceAmount,cr.courseAmount,cr.medserviceCourseAmount
			,cng.name as c7ngname, cmsg.name as c8msgname,cgg.name as c9ggname
			,vcp.name as v10cpname
			,CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') 
			when cp.dtype='JuridicalPerson' then 'Юрид.лицо: '||cp.name 
			else 'Страховая компания'||reg.name  END as personinfo
			
			from ContractRule cr 
			left join ContractNosologyGroup cng on cng.id=cr.nosologyGroup_id
			left join ContractMedServiceGroup cmsg on cmsg.id=cr.medServiceGroup_id
			left join ContractGuaranteeGroup cgg on cgg.id=cr.guaranteeGroup_id
			left join VocContractPermission vcp on vcp.id=cr.permission_id
			left join VocContractRulePeriod vcrp on vcrp.id=cr.period_id
			left join ServedPerson sp on sp.id=cr.servedPerson_id
			left join ContractPerson cp on cp.id=sp.person_id
			left join Patient p on p.id=cp.patient_id
			left join reg_ic reg on reg.id=cp.regcompany_id
			where cr.contract_id=${param.id}"/>
				<msh:table name="rules" action="entityParentView-contract_rule.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Название" property="2"/>
					<msh:tableColumn columnName="Период действия" property="3"/>
					<msh:tableColumn columnName="Дата начала" property="2"/>
					<msh:tableColumn columnName="Дата окончания" property="3"/>
					<msh:tableColumn columnName="Нозоологическая группа" property="7"/>
					<msh:tableColumn columnName="Группа мед. услуг" property="8"/>
					<msh:tableColumn columnName="Группа гарант. документов" property="9"/>
					<msh:tableColumn columnName="Разрешение" property="10"/>
					<msh:tableColumn columnName="Кол-во мед.услуг" property="4"/>
					<msh:tableColumn columnName="Кол-во курсов" property="5"/>
					<msh:tableColumn columnName="Кол-во мед.услуг на курс" property="6"/>
					<msh:tableColumn columnName="Обс. персона" property="11"/>
				</msh:table>
			</msh:section>
	</tiles:put>
</tiles:insert>
