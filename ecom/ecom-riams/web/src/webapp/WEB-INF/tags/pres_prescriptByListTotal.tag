<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@  attribute name="field" required="true" description="" %>

    <msh:section title="Список режимов">
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid ,pl.id as plid,vmp.name as vmpname,to_char(p.planStartDate,'dd.mm.yyyy')||' '||cast(p.planStartTime as varchar(5)) as startDate
    	,to_char(p.planEndDate,'dd.mm.yyyy')||' '||cast(p.planEndTime as varchar(5)) as endDate
 from Medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join PrescriptionList pl on pl.medcase_id=sls.id or pl.medcase_id=slo.id
left join Prescription p on p.prescriptionList_id =pl.id
 left join VocModePrescription as vmp on vmp.id=p.modePrescription_id
 where ${field}
   and p.DTYPE='ModePrescription' order by p.planStartDate
"/>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entitySubclassView-pres_prescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Режим"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="5" columnName="Дата окончания"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>

    <msh:section title="Список назначенных диет">
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid ,pl.id as plid,d.name as dname,to_char(p.planStartDate,'dd.mm.yyyy')||' '||cast(p.planStartTime as varchar(5)) as startDate
,to_char(p.planEndDate,'dd.mm.yyyy')||' '||cast(p.planEndTime as varchar(5)) as endDate
 from Medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join PrescriptionList pl on pl.medcase_id=sls.id or pl.medcase_id=slo.id
left join Prescription p on p.prescriptionList_id =pl.id
left join diet as d on d.id=p.diet_id
where ${field}
 and p.DTYPE='DietPrescription' order by p.planStartDate"/>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entitySubclassView-pres_prescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Диета"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="5" columnName="Дата окончания"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>

<msh:ifInRole roles="/Policy/Mis/Pharmacy/CreateDrugPrescription">
    <msh:section title="Список лекарственных назначений">
    	<ecom:webQuery name="pres" nativeSql=" select p.id,vd.name as nm,
to_char(p.planstartdate,'dd.MM.yyyy') as start,
to_char(p.planenddate,'dd.MM.yyyy') as end,
vdm.name as method,
p.frequency||' раз в день' as freq,
'по '||p.amount as count
,(p.planenddate-p.planstartdate)||' дней/день' as longtime
from Medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join PrescriptionList pl on pl.medcase_id=sls.id or pl.medcase_id=slo.id
left join Prescription p on p.prescriptionList_id =pl.id
left join pharmdrug pd on pd.id = p.drug_id
left join vocdrug vd on vd.id = pd.drug_id
left join vocdrugmethod vdm on vdm.id = p.method_id
where ${field}  and p.dtype = 'DrugPrescription'"/>
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

    <msh:section title="Список назначений на диагностические исследования">
    	<ecom:webQuery name="pres" nativeSql="
 select p.id as pid,pl.id as plid,ms.code||' '|| ms.name as f3_drname ,p.planStartDate ,m.datestart ||' '||cast(m.timeexecute as varchar(5)) as f5_timeexecute
  , p.canceldate as f6_canceldate ,coalesce(p.cancelreasontext,'') as f7_cancelText
  ,case when canceldate is not null then 'color:red;' else null end as f8_styleCancel
  from Medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join PrescriptionList pl on pl.medcase_id=sls.id or pl.medcase_id=slo.id
left join Prescription p on p.prescriptionList_id =pl.id
left join medservice ms on ms.id=p.medService_id
left join vocservicetype as vms on vms.id=ms.serviceType_id
left join workcalendartime wct on wct.id=p.calendartime_id
left join medcase m on m.id=wct.medcase_id
where ${field}
  and p.DTYPE='ServicePrescription' and (vms.code='DIAGNOSTIC' or vms.code='SERVICE' or (vms.id is null and ms.id is not null)) order by p.planStartDate"/>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entityView-pres_diagnosticPrescription.do" idField="1" styleRow="8">
    			<msh:tableColumn property="3" columnName="Исследование"/>
    			<msh:tableColumn property="4" columnName="Дата назначения"/>
    			<msh:tableColumn property="5" columnName="Дата исполнения услуги"/>
    			<msh:tableColumn property="6" columnName="Дата отмены"/>
    			<msh:tableColumn property="7" columnName="Причина отмены"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>

    <msh:section title="Список назначений на лабораторные исследования">
    	<ecom:webQuery name="pres" nativeSql="  select p.id as pid
  ,ms.name as f2_drname ,p.planStartDate as f3,p.materialId as f4,vpt.shortname as f5_vptname
,coalesce(vpcr.name,'')||' '||coalesce(p.cancelReasonText,'') as а6_fldCancel
,case when p.canceldate is not null then 'color:red;' else null end as а7_stylCancel
,presV.datestart||' '||cast(presV.timeExecute as varchar(5)) as f8_execute
from Medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join PrescriptionList pl on pl.medcase_id=sls.id or pl.medcase_id=slo.id
left join Prescription p on p.prescriptionList_id =pl.id
left join medservice ms on ms.id=p.medService_id
left join vocservicetype as vms on vms.id=ms.serviceType_id
left join vocprescripttype vpt on vpt.id=p.prescriptType_id
left join VocPrescriptCancelReason vpcr on vpcr.id=p.cancelReason_id
left join medcase presV on p.medcase_id=presV.id
where ${field}
 and p.DTYPE='ServicePrescription' and vms.code='LABSURVEY'  order by p.planStartDate"/>
    	<msh:sectionContent>
    		<msh:table styleRow="7" name="pres" action="entityView-pres_servicePrescription.do" idField="1">
    			<msh:tableColumn property="2" columnName="Исследование"/>
    			<msh:tableColumn property="5" columnName="Тип назначения"/>
    			<msh:tableColumn property="3" columnName="Дата назначения"/>
    			<msh:tableColumn property="8" columnName="Дата выполнения"/>
    			<msh:tableColumn property="4" columnName="ИД биоматериала"/>
    			<msh:tableColumn property="6" columnName="Причина брака"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>

    <msh:section title="Список назначений на операции">
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid,pl.id as plid,ms.name as drname ,to_char(p.planStartDate,'dd.MM.yyyy')
,cast (wct.timefrom as varchar(5)) ,wf.groupname
from Medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join PrescriptionList pl on pl.medcase_id=sls.id or pl.medcase_id=slo.id
left join Prescription p on p.prescriptionList_id =pl.id
left join workfunction wf on wf.id=p.prescriptcabinet_id
left join workcalendartime wct on wct.id=p.calendartime_id
left join medservice ms on ms.id=p.medService_id
left join vocservicetype as vms on vms.id=ms.serviceType_id
 where ${field}
  and p.DTYPE='ServicePrescription' and vms.code='OPERATION' order by p.planStartDate"/>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entityView-pres_operationPrescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Операция"/>
    			<msh:tableColumn property="6" columnName="Операционная"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="5" columnName="Время назначения"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>

<msh:ifInRole roles="/NOTEXISTROLE/Policy/Mis/Pharmacy/CreateDrugPrescription" guid="*Не назначают койко дни">
    <msh:section title="Список койко-дней">
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid,pl.id as plid,ms.name as drname ,p.planStartDate,p.planEndDate
from Medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join PrescriptionList pl on pl.medcase_id=sls.id or pl.medcase_id=slo.id
left join Prescription p on p.prescriptionList_id =pl.id
left join mislpu ml on ml.id=p.department_id
left join medservice ms on ms.id=p.medService_id
left join vocservicetype as vms on vms.id=ms.serviceType_id
left join vocprescripttype vpt on vpt.id=p.prescriptType_id
where ${field}
 and p.DTYPE='ServicePrescription' and vms.code='к/д' order by p.planStartDate"/>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entityView-pres_servicePrescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Тип услуги"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="5" columnName="Дата окончания"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>
</msh:ifInRole>

<msh:section title="Список консультаций">
	<ecom:webQuery name="pres" nativeSql="select scg.id,vtype.code||' '||vtype.name as f00,
wf.groupname as f01,scg.createusername as f1
,to_char(scg.createdate,'dd.mm.yyyy')||' '||scg.createtime as f2,scg.editusername as f3,to_char(scg.editdate,'dd.mm.yyyy')||' '||scg.edittime as f4,
scg.transferusername as f5 ,to_char(scg.transferdate,'dd.mm.yyyy')||' '||to_char(scg.transfertime,'HH24:MI:SS') as f6,
vwf2.name||' '||wp2.lastname||' '||wp2.firstname||' '||wp2.middlename as f7,to_char(scg.intakedate,'dd.mm.yyyy')||' '||to_char(scg.intaketime,'HH24:MI:SS') as f8
from medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join PrescriptionList pl on pl.medcase_id = sls.id or pl.medcase_id = slo.id
left join Prescription scg on scg.prescriptionList_id=pl.id
left join workfunction wf on wf.id=scg.prescriptcabinet_id
left join vocworkFunction vwf on vwf.id=wf.workFunction_id
left join workfunction wf2 on wf2.id=scg.intakespecial_id
left join vocworkFunction vwf2 on vwf2.id=wf2.workFunction_id
left join worker w2 on w2.id = wf2.worker_id
left join patient wp2 on wp2.id=w2.person_id
left join vocconsultingtype vtype on vtype.id=scg.vocconsultingtype_id
where ${field} and scg.canceldate is null and scg.dtype='WfConsultation'"/>
	<msh:sectionContent>
		<msh:table name="pres" action="entityParentView-pres_wfConsultation.do" idField="1">
			<msh:tableColumn columnName="#" property="sn"/>
			<msh:tableColumn columnName="Тип" property="2"/>
			<msh:tableColumn columnName="Специалист" property="3"/>
			<msh:tableColumn columnName="Пользователь, который создал" property="4" cssClass="preCell"/>
			<msh:tableColumn columnName="Дата и время создания" property="5"/>
			<msh:tableColumn columnName="Пользователь, который отредактировал" property="6" cssClass="preCell"/>
			<msh:tableColumn columnName="Дата и время редактирования" property="7"/>
			<msh:tableColumn columnName="Пользователь, который передал" property="8" cssClass="preCell"/>
			<msh:tableColumn columnName="Дата и время передачи" property="9"/>
			<msh:tableColumn columnName="Пользователь, который выполнил" property="10" cssClass="preCell"/>
			<msh:tableColumn columnName="Дата и время выполнения" property="11"/>
		</msh:table>
	</msh:sectionContent>
</msh:section>
