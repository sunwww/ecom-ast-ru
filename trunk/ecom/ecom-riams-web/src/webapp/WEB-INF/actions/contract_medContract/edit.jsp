<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entitySaveGoView-contract_medContract.do" defaultField="contractNumber">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:hidden property="createDate" />
			<msh:hidden property="createTime" />
			<msh:hidden property="createUsername" />
			<msh:hidden property="editDate" />
			<msh:hidden property="editTime" />
			<msh:hidden property="editUsername" />
			<msh:panel>	
				<msh:row>
					<msh:textField property="contractNumber" label="Номер договора"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="lpu" label="ЛПУ" vocName="lpu" horizontalFill="true" size="100" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="customer" label="Заказчик" vocName="contractPerson" size="100" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="rulesProcessing" label="Обработка правил" fieldColSpan="3" vocName="vocContractRulesProcessing" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="priceList" label="Прейскурант" fieldColSpan="3"  vocName="priceList" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала "/>
					<msh:textField property="dateTo" label="Дата окончания "/>
				</msh:row>
				<msh:row>
					<msh:textArea property="comment" label="Описание" fieldColSpan="3"/>
				</msh:row>
				<msh:ifFormTypeIsView formName="contract_medContractForm">
				<msh:row>
					<msh:separator label="Информация о создании" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:textField property="createDate" label="Дата"/>
					<msh:textField property="createTime" label="Время"/>
				</msh:row>
				<msh:row>
					<msh:textField property="createUsername" label="Пользователь"/>
				</msh:row>
				<msh:row>
					<msh:separator label="Информация о последней редакции" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:textField property="editDate" label="Дата"/>
					<msh:textField property="editTime" label="Время"/>
				</msh:row>
				<msh:row>
					<msh:textField property="editUsername" label="Пользователь"/>
				</msh:row>
				</msh:ifFormTypeIsView>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="contract_medContractForm">
			<msh:section title="Обслуживаемые персоны">
			<ecom:webQuery nativeSql="select sp.id,
			CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			,sp.dateFrom,sp.dateTo
			from ServedPerson sp
			left join ContractPerson cp on cp.id=sp.person_id left join patient p on p.id=cp.patient_id
			where sp.contract_id='${param.id}'
			" name="serverPerson"/>
				<msh:table name="serverPerson" action="entityParentView-contract_servedPerson.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Информация" property="2"/>
					<msh:tableColumn columnName="Дата начала обсл." property="3"/>
					<msh:tableColumn columnName="Дата окончания" property="4"/>
				</msh:table>
			</msh:section>
			<msh:section title="Поддоговор">
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
			where mc.parent_id='${param.id}'
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
			</msh:section>
			<%-- 
			<msh:section title="Гарантийные документы">
			<ecom:parentEntityListAll formName="contract_contractGuaranteeForm" attribute="contract" />
				<msh:table name="contract" action="entitySubclassView-contract_contractGuarantee.do" idField="id">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Договорная персона" property="contractPerson" />
				</msh:table>
			</msh:section>--%>
			<msh:section title="Договорные правила">
			<ecom:webQuery name="rules" nativeSql="select cr.id,cr.dateFrom,cr.dateTo
			,cr.medserviceAmount,cr.courseAmount,cr.medserviceCourseAmount
			,cng.name as cngname, cmsg.name as cmsgname,cgg.name as cggname
			,vcp.name as vcpname,vcrp.name as vcrpname
			,CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			
			from ContractRule cr 
			left join ContractNosologyGroup cng on cng.id=cr.nosologyGroup_id
			left join ContractMedServiceGroup cmsg on cmsg.id=cr.medServiceGroup_id
			left join ContractGuaranteeGroup cgg on cgg.id=cr.guaranteeGroup_id
			left join VocContractPermission vcp on vcp.id=cr.permission_id
			left join VocContractRulePeriod vcrp on vcrp.id=cr.period_id
			left join ServedPerson sp on sp.id=cr.servedPerson_id
			left join ContractPerson cp on cp.id=sp.person_id
			left join Patient p on p.id=cp.patient_id
			where cr.contract_id=${param.id}"/>
				<msh:table name="rules" action="entityParentView-contract_contractRule.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Период действия" property="11"/>
					<msh:tableColumn columnName="Дата начала" property="2"/>
					<msh:tableColumn columnName="Дата окончания" property="3"/>
					<msh:tableColumn columnName="Нозоологическая группа" property="7"/>
					<msh:tableColumn columnName="Группа мед. услуг" property="8"/>
					<msh:tableColumn columnName="Группа гарант. документов" property="9"/>
					<msh:tableColumn columnName="Разрешение" property="10"/>
					<msh:tableColumn columnName="Кол-во мед.услуг" property="4"/>
					<msh:tableColumn columnName="Кол-во курсов" property="5"/>
					<msh:tableColumn columnName="Кол-во мед.услуг на курс" property="6"/>
					<msh:tableColumn columnName="Обс. персона" property="12"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_medContractForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_medContract" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/Edit"/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_medContract" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/Delete"/>
		</msh:sideMenu>
		<msh:sideMenu title="Добавить" >
			<msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-contract_servedPerson" name="Обслуживаемые персоны" title="Обслуживаемые персоны" roles="/Policy/Mis/Contract/MedContract/ServedPerson/Create"/>
			<msh:sideLink key="ALT+4" params="id" action="/entityParentPrepareCreate-contract_medContract" name="Поддоговор" title="Поддоговор" roles="/Policy/Mis/Contract/MedContract/Create"/>
			<msh:sideLink key="ALT+5" params="id" action="/entityParentPrepareCreate-contract_contractRule" name="Договорные правила" title="Добавить договорные правила по договору" roles="/Policy/Mis/Contract/MedContract/ContractRule/Create"/>

			<msh:sideLink key="ALT+6" params="id" action="/entityParentPrepareCreate-contract_contractGuaranteeLetter" name="Гарантийное письмо" title="Гарантийное письмо" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/Create"/>
			<msh:sideLink key="ALT+7" params="id" action="/entityParentPrepareCreate-contract_contractPaymentOrder" name="Платежное поручение" title="Добавить платежное поручение по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractPaymentOrder/Create"/>
			<msh:sideLink key="ALT+8" params="id" action="/entityParentPrepareCreate-contract_contractMedPolicy" name="Медицинский полис" title="Добавить медицинский полис по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractMedPolicy/Create"/>
		</msh:sideMenu>
		
		<msh:sideMenu title="Показать по договору">
			<msh:sideLink params="id" action="/entityParentList-contract_contractGuaranteeLetter" name="Гарантийные письма" title="Просмотреть гарантийные письма по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View"/>
			<msh:sideLink params="id" action="/entityParentList-contract_contractPaymentOrder" name="Платежные поручения" title="Просмотреть платежные поручения по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractPaymentOrder/View"/>
			<msh:sideLink params="id" action="/entityParentList-contract_contractMedPolicy" name="Медицинские полиса" title="Просмотреть медицинские полиса по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractMedPolicy/View"/>
			<msh:sideLink params="id" action="/entityParentList-contract_contractRule" name="Договорные правила" title="Просмотреть договорные правила по договору" roles="/Policy/Mis/Contract/MedContract/ContractRule/View"/>
		</msh:sideMenu>
		<tags:contractMenu currentAction="medContract"/>		
	</tiles:put>
</tiles:insert>
