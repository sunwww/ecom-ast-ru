<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
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
					<msh:autoComplete viewAction="entitySubclassView-contract_contractPerson.do" 
					property="customer" label="Заказчик" vocName="contractPerson" size="100" horizontalFill="true" fieldColSpan="3"/>
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
			<msh:section title="Счета оплату" createRoles="" createUrl="entityParentPrepareCreate-contract_account_contract.do?id=${param.id}">
			<ecom:webQuery nativeSql="select ca.id,
			CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			,sp.dateFrom,sp.dateTo
			, count(distinct case when cao.id is null then cams.id else null end) as cntMedService 
			, sum(case when cao.id is null then cams.countMedService*cams.cost else 0 end) as sumNoAccraulMedService 
			, round(sum((case when cao.id is null then cams.countMedService*cams.cost else 0 end)*(100-ca.discountDefault)/100),2) as sumNoAccraulMedServiceWithDiscount 
			from ContractAccount ca
			left join ServedPerson sp on ca.id = sp.account_id
			left join ContractAccountMedService cams on cams.account_id=ca.id
			left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id
			left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'
			left join ContractPerson cp on cp.id=sp.person_id left join patient p on p.id=cp.patient_id
			where ca.contract_id='${param.id}' and cao.id is null  and caos.id is null
			group by  sp.id,cp.dtype,p.lastname,p.firstname,p.middlename,p.birthday,cp.name
			,sp.dateFrom,sp.dateTo,ca.id,ca.balanceSum, ca.reservationSum,ca.discountdefault
			" name="serverPerson"/>
				<msh:table name="serverPerson" viewUrl="entityParentView-contract_account.do?short=Short" 
				
				printUrl="print-dogovor572.do?s=CertificatePersonPrintService&m=printDogovogByNoPrePaidServicesMedServise"
				action="entityParentPrepareCreate-contract_accountOperationAccrual.do"
				
				idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Информация" property="2"/>
					<msh:tableColumn columnName="Дата начала обсл." property="3"/>
					<msh:tableColumn columnName="Дата окончания" property="4"/>
					<msh:tableColumn columnName="кол-во неопл. услуг" property="5"/>
					<msh:tableColumn columnName="сумма к оплате" property="6"/>
					<msh:tableColumn columnName="сумма к оплате с учетом скидки" property="7"/>
				</msh:table>
			</msh:section>
			<msh:section>
			<msh:sectionTitle>Оплаченные счета 
			<a onclick="getDefinition(&quot;js-contract_medContract-list_accrual_service.do?short=Short&amp;id=${param.id}&quot;,event); " href="javascript:void(0);"><img width="14" height="14" title="Просмотр списка оплаченных учлуг" alt="Просмотр списка" src="/skin/images/main/view1.png">Просмотр списка оплаченных услуг</a>
			</msh:sectionTitle>
			<msh:sectionContent>
			<ecom:webQuery nativeSql="select ca.id,
			CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			,sp.dateFrom,sp.dateTo,ca.balanceSum, ca.reservationSum
			from ContractAccount ca
			left join ServedPerson sp on ca.id = sp.account_id
			left join ContractAccountMedService cams on cams.account_id=ca.id
			left join ContractAccountOperationByService caos on caos.accountMedService_id=cams.id
			left join ContractAccountOperation cao on cao.id=caos.accountOperation_id and cao.dtype='OperationAccrual'
			left join ContractPerson cp on cp.id=sp.person_id left join patient p on p.id=cp.patient_id
			where ca.contract_id='${param.id}' and cao.id is not null
			group by  sp.id,cp.dtype,p.lastname,p.firstname,p.middlename,p.birthday,cp.name
			,sp.dateFrom,sp.dateTo,ca.id,ca.balanceSum, ca.reservationSum
			" name="serverPerson"/>
				<msh:table name="serverPerson" action="entityParentView-contract_account.do" idField="1"
				viewUrl="entityParentView-contract_account.do?short=Short" >
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Счет" property="1"/>
					<msh:tableColumn columnName="Информация" property="2"/>
					<msh:tableColumn columnName="Дата начала обсл." property="3"/>
					<msh:tableColumn columnName="Дата окончания" property="4"/>
					<msh:tableColumn columnName="Сумма баланса" property="5"/>
					<msh:tableColumn columnName="из них зарезер." property="6"/>
					
				</msh:table>
				</msh:sectionContent>
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
      			<ecom:webQuery name="lastVisit1" nativeSql="select 
    	m.id,m.dateStart as dateFrom
    	,coalesce(vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename
    	,vwf1.name||' '||wp1.lastname||' '||wp1.firstname||' '||wp1.middlename) as worker
    	from medCase m
    	
    	left join VocServiceStream vss on vss.id=m.serviceStream_id
    	left join ContractPerson cp on cp.patient_id=m.patient_id
    	left join MedContract mc on cp.id=mc.customer_id
    	left join workfunction wf on wf.id=m.workFunctionExecute_id
    	left join vocworkfunction vwf on vwf.id=wf.workFunction_id
    	left join workfunction wf1 on wf1.id=m.workFunctionPlan_id
    	left join vocworkfunction vwf1 on vwf1.id=wf1.workFunction_id
    	left join WorkCalendarDay wcd on wcd.id=m.datePlan_id
    	left join worker w on w.id=wf.worker_id
    	left join patient wp on wp.id=w.person_id
    	left join worker w1 on w1.id=wf1.worker_id
    	left join patient wp1 on wp1.id=w1.person_id
    	where mc.id=${param.id} and (m.DTYPE='Visit' or m.dtype='ShortMedCase')
    	and m.dateStart is not null
    	and vss.code='CHARGED'
    	order by m.dateStart desc
    	" maxResult="5" />
     <msh:section title="Последнее посещение <a href='print-begunok.do?s=SmoVisitService&amp;m=printDirectionByPatient&patientId=${param.id}' target='_blank'>бегунок</a>" 
     viewRoles="/Policy/Mis/MedCase/Direction/View" shortList="js-mis_patient-viewDirection.do?id=${param.id}">
    	<msh:table name="lastVisit1" action="entitySubclassView-mis_medCase.do" idField="1">
	    	<msh:tableColumn property="2" columnName="Дата"/>
    		<msh:tableColumn property="3" columnName="Специалист"/>
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
			<msh:sideLink key="ALT+DEL" confirm="Вы точно хотите удалить контракт?" params="id" action="/entityParentDeleteGoSubclassView-contract_medContract_person" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/Delete"/>
		</msh:sideMenu>
		<msh:sideMenu title="Добавить" >
			<msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-contract_servedPerson" name="Обслуживаемые персоны" title="Обслуживаемые персоны" roles="/Policy/Mis/Contract/MedContract/ServedPerson/Create"/>
			
			<msh:sideLink key="ALT+4" params="id" action="/entityParentPrepareCreate-contract_account_person" name="Счет (услуги)" title="Счет" roles="/Policy/Mis/Contract/MedContract/Create"/>
			<msh:sideLink params="id" action="/entityParentPrepareCreate-contract_medContract" name="Поддоговор" title="Поддоговор" roles="/Policy/Mis/Contract/MedContract/Create"/>
			
			
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
