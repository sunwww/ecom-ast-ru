<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract" >Список медицинских договоров</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/MedContract/Create" params="" action="/entityPrepareCreate-contract_medContract" title="Добавить медицинский договор" name="Медицинский договор" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<%--
		<msh:table name="list" action="entityView-contract_medContract.do" idField="id">
			<msh:tableColumn columnName="ЛПУ" property="lpu" />
			<msh:tableColumn columnName="Заказчик" property="customer" />
			<msh:tableColumn columnName="Номер договора" property="contractNumber" />
			<msh:tableColumn columnName="Дата начала действия" property="dateFrom" />
			<msh:tableColumn columnName="Дата окончания действия" property="dateTo" />
			<msh:tableColumn columnName="Описание" property="comment" />
			<msh:tableColumn columnName="Обработка правил" property="rulesProcessing" />
			<msh:tableColumn columnName="Прейскурант" property="priceList" />
		</msh:table>
		--%>
		<%
		String id = request.getParameter("id") ;
		String ids = "" ;
		if (id==null ||id.equals("") ) {
			ids=" is null " ;
		} else {
			ids = "='"+id+"'" ;
		}
		request.setAttribute("ids",ids) ;
		%>
			<ecom:webQuery name="childContract" nativeSql="
			select mc.id
			,CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			,ml.name as mlname,mc.contractNumber as mccontractNumber
			,mc.dateFrom as mcdateFrom,mc.dateTo as mcdateTo
			,vcrp.name as vcrpname,pl.name as plname
			from MedContract mc
			left join ContractPerson cp on cp.id=mc.customer_id
			left join Patient p on p.id=cp.patient_id
			left join MisLpu ml on ml.id=mc.lpu_id
			left join VocContractRulesProcessing vcrp on vcrp.id=mc.rulesProcessing_id
			left join PriceList pl on pl.id=mc.priceList_id
			where  mc.parent_id ${ids}
			"/>
				<msh:table name="childContract" action="entityParentView-contract_medContract.do" idField="1">
					<msh:tableColumn columnName="#" property="sn" />
					<msh:tableColumn columnName="№ договора" property="4" />
					<msh:tableColumn columnName="ЛПУ" property="3" />
					<msh:tableColumn columnName="Заказчик" property="2" />
					<msh:tableColumn columnName="Дата начала" property="5" />
					<msh:tableColumn columnName="Дата окончания" property="6" />
					<msh:tableColumn columnName="Обработка правил" property="7" />
					<msh:tableColumn columnName="Прейскурант" property="8" />
				</msh:table>		
	</tiles:put>
</tiles:insert>
