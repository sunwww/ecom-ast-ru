<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_guaranteeForm" title="Список Гарантийный документ по договору"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/Create" params="id" action="/entityParentPrepareCreate-contract_guarantee" title="Гарантийное письмо по договору" name="Гарантийное письмо по договору" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:section title="Гарантийные документы"  
				createUrl="entityParentPrepareCreate-contract_guaranteeLetter.do?id=${param.id}"
				createRoles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/Create">
				<ecom:webQuery name="contractGuaranteeList" nativeSql="
				select cg.id as cgid
				,CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') when cp.dtype='JuridicalPerson' then 'Юрид.лицо: '||cp.name else 'Страховая компания'||reg.name  END as cpid
				,cg.limitMoney as cglimitMoney
				,cg.numberdoc||' от '||to_char(cg.issueDate,'dd.mm.yyyy') as issueinfo
				,cg.actiondate
				 from ContractGuarantee cg
				 left join ContractPerson cp on cp.id=cg.contractPerson_id
				left join Patient p on p.id=cp.patient_id
				left join reg_ic reg on reg.id=cp.regcompany_id
				 where cg.contract_id='${param.id}'
				 order by cg.issueDate desc
				"/>
				<msh:table name="contractGuaranteeList" action="entitySubclassView-contract_guarantee.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Номер" property="4" />
					<msh:tableColumn columnName="Дата действия" property="4" />
					<msh:tableColumn columnName="Лимит" property="3" />
					<msh:tableColumn columnName="Договорная персона" property="2" />
				</msh:table>
			</msh:section>
	</tiles:put>
</tiles:insert>
