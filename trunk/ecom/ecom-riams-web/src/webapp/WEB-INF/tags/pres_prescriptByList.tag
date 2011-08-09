<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@  attribute name="field" required="true" description="" %>

    <msh:section>
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid,pl.id as plid,dr.name as drname,p.planStartDate,p.planStartTime,p.planEndDate,p.planEndTime,vdm.name as vdmname , p.frequency ||' '||ifnull(p.frequencyUnit_id,'',vfu.name), p.orderTime ||' '||ifnull(p.orderType_id,'',vpot.name), p.amount ||' '||ifnull(p.amountUnit_id,'',vdau.name), p.duration ||' '||ifnull(p.durationUnit_id,'',vdu.name) from Prescription p left join PrescriptionList pl on pl.id=p.prescriptionList_id left join vocdrugclassify as dr on dr.id=p.drug_id left join vocdrugmethod as vdm on vdm.id=p.method_id left join vocfrequencyunit as vfu on vfu.id=p.frequencyunit_id left join vocPrescriptOrderType as vpot on vpot.id=p.orderType_id left join vocDrugAmountUnit as vdau on vdau.id=p.amountUnit_id left join vocDurationUnit as vdu on vdu.id=p.durationUnit_id where ${field } and p.DTYPE='DrugPrescription' order by p.planStartDate"/>
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
    	<ecom:webQuery name="pres" nativeSql="select p.id as pid,pl.id as plid,d.name as dname,p.planStartDate,p.planStartTime,p.planEndDate,p.planEndTime from Prescription p left join PrescriptionList pl on pl.id=p.prescriptionList_id left join diet as d on d.id=p.diet_id where ${field } and p.DTYPE='DietPrescription' order by p.planStartDate"/>
    	<msh:sectionTitle>Список назначенных диет</msh:sectionTitle>
    	<msh:sectionContent>
    		<msh:table name="pres" action="entitySubclassView-pres_prescription.do" idField="1">
    			<msh:tableColumn property="3" columnName="Диета"/>
    			<msh:tableColumn property="4" columnName="Дата начала"/>
    			<msh:tableColumn property="6" columnName="Дата окончания"/>
    		</msh:table>
    	</msh:sectionContent>
    </msh:section>
