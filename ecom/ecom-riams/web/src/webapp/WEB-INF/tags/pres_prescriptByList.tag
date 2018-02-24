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


<msh:ifInRole roles="/Policy/Mis/Pharmacy/CreateDrugPrescription">
	<msh:section>
		<ecom:webQuery name="pres" nativeSql="select pres.id,vd.name as nm,
to_char(pres.planstartdate,'dd.MM.yyyy') as start,
to_char(pres.planenddate,'dd.MM.yyyy') as end,
vdm.name as method,
pres.frequency||' раз в день' as freq,
'по '||pres.amount as count
,(pres.planenddate-pres.planstartdate)||' дней/день' as longtime
from prescription pres
left join prescriptionList pl on pl.id = pres.prescriptionlist_id
left join pharmdrug pd on pd.id = pres.drug_id
left join vocdrug vd on vd.id = pd.drug_id
left join vocdrugmethod vdm on vdm.id = pres.method_id
where ${field} and pres.dtype = 'DrugPrescription'"/>
		<msh:sectionTitle>Список лекарственных назначений</msh:sectionTitle>
		<msh:sectionContent>
			<msh:table name="pres" action="entitySubclassView-pres_prescription.do" idField="1">
				<msh:tableColumn property="2" columnName="Лек.средство"/>
				<msh:tableColumn property="3" columnName="Дата начала"/>
				<msh:tableColumn property="4" columnName="Дата окончания"/>
				<msh:tableColumn property="5" columnName="Способ введения"/>
				<msh:tableColumn property="6" columnName="Частота"/>
				<msh:tableColumn property="7" columnName="Кол-во на один прием"/>
				<msh:tableColumn property="8" columnName="Продолжительность"/>
			</msh:table>
		</msh:sectionContent>
	</msh:section>
</msh:ifInRole>
<msh:section>
	<ecom:webQuery name="pres" nativeSql="select
    	p.id as pid,pl.id as plid,ms.name as drname
 ,p.planStartDate,p.planEndDate
 ,m.datestart
,coalesce(d.record,'')
, p.canceldate as canceldate
, coalesce(p.cancelreasontext,'') as cancelText
,case when canceldate is not null then 'color:red;' else null end as styleCancel
 from Prescription p

 left join PrescriptionList pl on pl.id=p.prescriptionList_id
 left join medservice ms on ms.id=p.medService_id
 left join vocservicetype as vms on vms.id=ms.serviceType_id
 left join workcalendartime wct on wct.id=p.calendartime_id
 left join medcase m on m.id=wct.medcase_id
 left join diary d on d.medcase_id=m.id
 where ${field } and p.DTYPE='ServicePrescription'
 and (vms.code='DIAGNOSTIC' or vms.code='SERVICE' or (vms.id is null and ms.id is not null))
 order by p.planStartDate"/>
	<msh:sectionTitle>Список назначений на диагностические исследования</msh:sectionTitle>
	<msh:sectionContent>
		<msh:table name="pres" action="entityView-pres_diagnosticPrescription.do" idField="1" styleRow="10">
			<msh:tableColumn property="3" columnName="Исследование"/>
			<msh:tableColumn property="4" columnName="Дата начала"/>
			<msh:tableColumn property="6" columnName="Дата исполнения услуги"/>

			<msh:tableColumn property="7" columnName="Результат выполнения"/>
			<msh:tableColumn property="8" columnName="Дата отмены"/>
			<msh:tableColumn property="9" columnName="Причина отмены"/>
		</msh:table>
	</msh:sectionContent>
</msh:section>
<msh:section>
	<ecom:webQuery name="pres" nativeSql="select
    	p.id as pid,pl.id as plid,ms.name as drname

 ,p.planStartDate,p.planEndDate,p.materialId,vpt.name as vptname
 ,ml.name as mlname,coalesce(vpcr.name,'')||' '||coalesce(p.cancelReasonText,'') as fldCancel
 ,case when p.canceldate is not null then 'color:red;' else null end as stylCancel
 , case when presV.datestart is not null then coalesce(d.record, '') else '' end as lab_rests
 ,presV.datestart
 from Prescription p
 left join PrescriptionList pl on pl.id=p.prescriptionList_id
 left join mislpu ml on ml.id=p.department_id
 left join medservice ms on ms.id=p.medService_id
 left join vocservicetype as vms on vms.id=ms.serviceType_id
 left join vocprescripttype vpt on vpt.id=p.prescriptType_id
 left join VocPrescriptCancelReason vpcr on vpcr.id=p.cancelReason_id
 left join medcase presV on presV.id=p.medcase_id
 left join diary d on d.medcase_id=p.medcase_id
 where ${field } and p.DTYPE='ServicePrescription'
 and vms.code='LABSURVEY'
 order by p.planStartDate"/>
	<msh:sectionTitle>Список назначений на лабораторные исследования</msh:sectionTitle>
	<msh:sectionContent>
		<msh:table styleRow="10" name="pres" action="entityView-pres_servicePrescription.do" idField="1">
			<msh:tableColumn property="3" columnName="Исследование"/>
			<msh:tableColumn property="7" columnName="Тип назначения"/>
			<msh:tableColumn property="8" columnName="Место забора"/>
			<msh:tableColumn property="4" columnName="Дата начала"/>
			<msh:tableColumn property="12" columnName="Дата выполнения"/>
			<msh:tableColumn property="5" columnName="Дата окончания"/>
			<msh:tableColumn property="6" columnName="ИД биоматериала"/>
			<msh:tableColumn property="9" columnName="Причина брака"/>
			<msh:tableColumn property="11" columnName="Результат"/>

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