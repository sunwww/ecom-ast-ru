<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@  attribute name="field" required="true" description="" %>

    <msh:section>
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid
    	,pl.id as plid,vmp.name as vmpname,to_char(p.planStartDate,'dd.mm.yyyy')||cast(p.planStartTime as varchar(5)) as startDate
    	,to_char(p.planEndDate,'dd.mm.yyyy')||' '||cast(p.planEndTime as varchar(5)) as endDate from Prescription p 
    	left join PrescriptionList pl on pl.id=p.prescriptionList_id 
    	left join VocModePrescription as vmp on vmp.id=p.modePrescription_id where ${field } and p.DTYPE='ModePrescription' 
    	order by p.planStartDate"/>
    	<msh:sectionTitle>Список режимов</msh:sectionTitle>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entitySubclassView-pres_prescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Режим"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="6" columnName="Дата окончания"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>
    <msh:section>
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid
    	,pl.id as plid,d.name as dname,to_char(p.planStartDate,'dd.mm.yyyy')||cast(p.planStartTime as varchar(5)) as startDate
    	,to_char(p.planEndDate,'dd.mm.yyyy')||' '||cast(p.planEndTime as varchar(5)) as endDate from Prescription p 
    	left join PrescriptionList pl on pl.id=p.prescriptionList_id 
    	left join diet as d on d.id=p.diet_id where ${field } and p.DTYPE='DietPrescription' 
    	order by p.planStartDate"/>
    	<msh:sectionTitle>Список назначенных диет</msh:sectionTitle>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entitySubclassView-pres_prescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Диета"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="6" columnName="Дата окончания"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>
    <msh:section>
    	<ecom:webQuery name="pres" nativeSql="select 
    	p.id as pid,pl.id as plid,dr.name as drname
 ,p.planStartDate,p.planStartTime,p.planEndDate,p.planEndTime,vdm.name as vdmname 
 , p.frequency ||' '||coalesce(vfu.name,'') as vfuname
 , p.orderTime ||' '||coalesce(vpot.name,'') as vpotname
 , p.amount ||' '||coalesce(vdau.name,'') as vdauname
 , p.duration ||' '||coalesce(vdu.name,'') as vduname
 from Prescription p left join PrescriptionList pl on pl.id=p.prescriptionList_id left join vocdrugclassify as dr on dr.id=p.drug_id left join vocdrugmethod as vdm on vdm.id=p.method_id left join vocfrequencyunit as vfu on vfu.id=p.frequencyunit_id left join vocPrescriptOrderType as vpot on vpot.id=p.orderType_id left join vocDrugAmountUnit as vdau on vdau.id=p.amountUnit_id left join vocDurationUnit as vdu on vdu.id=p.durationUnit_id where ${field } and p.DTYPE='DrugPrescription' order by p.planStartDate"/>
    	<msh:sectionTitle>Список лекарственных назначений</msh:sectionTitle>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entitySubclassView-pres_prescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Лек.средство"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="6" columnName="Дата окончания"/>
    			<msh:tableColumn property="8" columnName="Способ введения"/>
    			<msh:tableColumn property="9" columnName="Частота"/>
    			<msh:tableColumn property="10" columnName="Время приема"/>
    			<msh:tableColumn property="11" columnName="Кол-во на один прием"/>
    			<msh:tableColumn property="12" columnName="Продолжительность"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>
    <msh:section>
    	<ecom:webQuery name="pres" nativeSql="select 
    	p.id as pid,pl.id as plid,ms.name as drname
 ,p.planStartDate,p.planEndDate
 from Prescription p 
 left join PrescriptionList pl on pl.id=p.prescriptionList_id 
 left join medservice ms on ms.id=p.medService_id
 left join vocservicetype as vms on vms.id=ms.serviceType_id 
 where ${field } and p.DTYPE='ServicePrescription' 
 and (vms.code='DIAGNOSTIC' or vms.code='SERVICE' or (vms.id is null and ms.id is not null)) 
 order by p.planStartDate"/>
    	<msh:sectionTitle>Список назначений на диагностические исследования</msh:sectionTitle>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entityView-pres_diagnosticPrescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Исследование"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="5" columnName="Дата окончания"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>
    <msh:section>
    	<ecom:webQuery name="pres" nativeSql="select 
    	p.id as pid,pl.id as plid,ms.name as drname
    	
 ,p.planStartDate,p.planEndDate,p.materialId,vpt.name as vptname
 ,ml.name as mlname,vpcr.name||' '||coalesce(p.cancelReasonText,'') as fldCancel
 from Prescription p 
 left join PrescriptionList pl on pl.id=p.prescriptionList_id 
 left join mislpu ml on ml.id=p.department_id
 left join medservice ms on ms.id=p.medService_id
 left join vocservicetype as vms on vms.id=ms.serviceType_id 
 left join vocprescripttype vpt on vpt.id=p.prescriptType_id
 left join VocPrescriptCancelReason vpcr on vpcr.id=p.cancelReason_id
 where ${field } and p.DTYPE='ServicePrescription' 
 and vms.code='LABSURVEY'
 order by p.planStartDate"/>
    	<msh:sectionTitle>Список назначений на лабораторные исследования</msh:sectionTitle>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entityView-pres_servicePrescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Исследование"/>
    			<msh:tableColumn property="7" columnName="Тип назначения"/>
    			<msh:tableColumn property="8" columnName="Место забора"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="5" columnName="Дата окончания"/>
    			<msh:tableColumn property="6" columnName="ИД биоматериала"/>
    			<msh:tableColumn property="8" columnName="Причина отмены"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>
    <msh:section>
    	<ecom:webQuery name="pres" nativeSql="select 
    	p.id as pid,pl.id as plid,ms.name as drname
,to_char(p.planStartDate,'dd.MM.yyyy')
,cast (wct.timefrom as varchar(5))
,wf.groupname
 
 from Prescription p 
 left join PrescriptionList pl on pl.id=p.prescriptionList_id
 left join workfunction wf on wf.id=p.prescriptcabinet_id
 left join workcalendartime wct on wct.id=p.calendartime_id
 left join mislpu ml on ml.id=p.department_id
 left join medservice ms on ms.id=p.medService_id
 left join vocservicetype as vms on vms.id=ms.serviceType_id 
 left join vocprescripttype vpt on vpt.id=p.prescriptType_id
 where ${field } and p.DTYPE='ServicePrescription' 
 and vms.code='OPERATION'
 order by p.planStartDate"/>
    	<msh:sectionTitle>Список назначений на операции</msh:sectionTitle>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entityView-pres_operationPrescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Операция"/>
    			<msh:tableColumn property="6" columnName="Операционная"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="5" columnName="Время назначения"/>
    			
    			
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>
    <msh:section>
    	<ecom:webQuery name="pres" nativeSql="select 
    	p.id as pid,pl.id as plid,ms.name as drname
 ,p.planStartDate,p.planEndDate
 from Prescription p 
 left join PrescriptionList pl on pl.id=p.prescriptionList_id 
 left join mislpu ml on ml.id=p.department_id
 left join medservice ms on ms.id=p.medService_id
 left join vocservicetype as vms on vms.id=ms.serviceType_id 
 left join vocprescripttype vpt on vpt.id=p.prescriptType_id
 where ${field } and p.DTYPE='ServicePrescription' 
 and vms.code='к/д'
 order by p.planStartDate"/>
    	<msh:sectionTitle>Список койко-дней</msh:sectionTitle>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entityView-pres_servicePrescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Тип услуги"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="5" columnName="Дата окончания"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>

