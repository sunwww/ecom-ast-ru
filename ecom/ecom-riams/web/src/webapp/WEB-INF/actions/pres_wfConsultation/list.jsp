<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="Patient" title="Консультация" />
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu title="Добавить">
            <msh:sideLink params="id" action="/entityParentPrepareCreate-pres_wfConsultation" name="Консультация специалиста" title="Добавить консультацию специалиста" roles="/Policy/Mis/Prescription/ServicePrescription/Create" key="ALT+N" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:section title="Консультации специалистов. 
        <a href='entityParentPrepareCreate-pres_wfConsultation.do?id=${param.id }'> Добавить новую консультацию специалиста</a>
        ">
            <ecom:webQuery name="consultations"  nativeSql="select scg.id,vtype.code||' '||vtype.name as f00,
wf.groupname as f01,scg.createusername as f1
,to_char(scg.createdate,'dd.mm.yyyy')||' '||scg.createtime as f2,scg.editusername as f3,to_char(scg.editdate,'dd.mm.yyyy')||' '||scg.edittime as f4,
scg.transferusername as f5 ,to_char(scg.transferdate,'dd.mm.yyyy')||' '||to_char(scg.transfertime,'HH24:MI:SS') as f6,
vwf2.name||' '||wp2.lastname||' '||wp2.firstname||' '||wp2.middlename as f7,to_char(scg.intakedate,'dd.mm.yyyy')||' '||to_char(scg.intaketime,'HH24:MI:SS') as f8
from prescription scg left join PrescriptionList pl on pl.id=scg.prescriptionList_id
left join workfunction wf on wf.id=scg.prescriptcabinet_id
left join vocworkFunction vwf on vwf.id=wf.workFunction_id
left join workfunction wf2 on wf2.id=scg.intakespecial_id
left join vocworkFunction vwf2 on vwf2.id=wf2.workFunction_id
left join worker w2 on w2.id = wf2.worker_id
left join patient wp2 on wp2.id=w2.person_id
left join vocconsultingtype vtype on vtype.id=scg.vocconsultingtype_id
where scg.dtype='WfConsultation' and scg.canceldate is null and scg.prescriptionlist_id='${param.id}'"/>

            <msh:table hideTitle="false" idField="1" name="consultations" action="entityParentView-pres_wfConsultation.do">
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

        </msh:section>
    </tiles:put>
</tiles:insert>
