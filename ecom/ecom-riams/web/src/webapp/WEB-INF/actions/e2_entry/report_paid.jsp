<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Отчет по оплаченным случаям</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>

    </tiles:put>

    <tiles:put name="body" type="string">
        <%
            String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","POLYCLINIC", request) ;
            String typeView =ActionUtil.updateParameter("Form039Action","typeView","1", request) ;
            String typeDefect =ActionUtil.updateParameter("Form039Action","typeDefect","NO", request) ;
            String typeInog =ActionUtil.updateParameter("Form039Action","typeInog","ALL", request) ;

        %>
        <msh:form action="/e2_report_paid.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">

            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="9" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>

                <msh:row>
                    <td class="label" title="Группировака (typePatient)" colspan="1">
                        <label for="typeInogName" id="typeInogLabel">Группировка по:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeInog" value="NO" checked>Без иногородних
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeInog" value="YES">Только иногородние
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeInog" value="ALL">Всё вместе
                    </td>
                </msh:row>
                <msh:row>
                    <td class="label" title="Группировака (typePatient)" colspan="1">
                        <label for="typeDefectName" id="typeDefectLabel">Группировка по:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDefect" value="NO" checked>Без дефектов
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDefect" value="YES">Только дефекты
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDefect" value="ALL">Всё вместе
                    </td>
                </msh:row>

                <msh:row>
                    <td class="label" title="Группировака (typePatient)" colspan="1">
                        <label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeView" value="1">  профилю помощи
                    </td>

                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeView" value="2"> профиль+отделение
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeView" value="3"> профиль+отделение+врач
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeView" value="4">  профиль+врач
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeView" value="5">  профиль+отделение+врач+услуга
                    </td>
                </msh:row>
                <msh:row>
                    <td></td>
                    <td  onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeGroup" value="POLYCLINIC">поликлиника
                    </td>
                    <td colspan="2" onclick="this.childNodes[1].checked='checked';" >
                        <input type="radio" name="typeGroup" value="HOSPITAL">стационар
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeGroup" value="VMP">ВМП
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeGroup" value="EXTDISP">диспансеризация
                    </td>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="3" submitLabel="Поиск" notDisplayCancel="true"/>

            </msh:panel>
        </msh:form>

        <%
            String dateBegin = request.getParameter("dateBegin");
            String dateEnd = request.getParameter("dateEnd");
            if (dateBegin!=null && dateEnd!=null) {
                boolean isReestr = "1".equals(request.getParameter("typeReestr"));
                request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
                String sumCostSql=" ,count(e.id) as f6_cnt, sum(e.cost) as f7_cost";
                String selectSql , groupBySql,orderBySql, whereSql;
                switch (typeView) {
                    case "1": // профилю помощи
                        selectSql = ",case when e.bedsubtype ='1' then 'КРУГЛОСУТОЧНЫЙ СТАЦ' when e.bedsubtype ='2' then 'ДНЕВНОЙ СТАЦ' else cast('' as varchar) end as f2_stacType" +
                                ",cast('' as varchar) as f3_depname, vmhp.name as f4_helpKind, cast('' as varchar) as f5_doctorName ";
                        groupBySql = "e.bedsubtype, vmhp.name";
                        orderBySql = "e.bedsubtype, vmhp.name";
                        break;
                    case "2": // отделение+профиль
                        selectSql = ",case when e.bedsubtype ='1' then 'КРУГЛОСУТОЧНЫЙ СТАЦ' when e.bedsubtype ='2' then 'ДНЕВНОЙ СТАЦ' else cast('' as varchar) end as f2_stacType" +
                                ",e.departmentname as f3_depname, vmhp.name as f4_helpKind, cast('' as varchar) as f5_doctorName ";
                        groupBySql = "e.bedsubtype, e.departmentname, vmhp.name";
                        orderBySql = "e.bedsubtype, e.departmentname, vmhp.name";
                        break;
                    case "3": // профиль+отделение+врач
                        selectSql = ",case when e.bedsubtype ='1' then 'КРУГЛОСУТОЧНЫЙ СТАЦ' when e.bedsubtype ='2' then 'ДНЕВНОЙ СТАЦ' else cast('' as varchar) end as f2_stacType" +
                                ",e.departmentname as f3_depname, vmhp.name as f4_helpKind, case when coalesce(e.doctorname,'')!='' then e.doctorname else coalesce(e.doctorsnils ,'') end as f5_doctorName ";
                        groupBySql = "e.bedsubtype, e.departmentname, vmhp.name, e.doctorname, e.doctorsnils";
                        orderBySql = "e.bedsubtype, e.departmentname, vmhp.name, e.doctorname, e.doctorsnils";
                        break;
                    case "4": // профиль+врач
                        selectSql = ",case when e.bedsubtype ='1' then 'КРУГЛОСУТОЧНЫЙ СТАЦ' when e.bedsubtype ='2' then 'ДНЕВНОЙ СТАЦ' else cast('' as varchar) end as f2_stacType" +
                                ",cast('' as varchar) as f3_depname, vmhp.name as f4_helpKind, case when coalesce(e.doctorname,'')!='' then e.doctorname else coalesce(e.doctorsnils ,'') end as f5_doctorName ";
                        groupBySql = "e.bedsubtype, vmhp.name, e.doctorname, e.doctorsnils";
                        orderBySql = "e.bedsubtype, vmhp.name, e.doctorname, e.doctorsnils";
                        break;
                    case "5": //для ДД: отделение-врач-услуга
                        selectSql = ",case when e.bedsubtype ='1' then 'КРУГЛОСУТОЧНЫЙ СТАЦ' when e.bedsubtype ='2' then 'ДНЕВНОЙ СТАЦ' else cast('' as varchar) end as f2_stacType" +
                                ",vms.name as f3_depname, vmhp.name as f4_helpKind, case when coalesce(e.doctorname,'')!='' then e.doctorname else coalesce(e.doctorsnils ,'') end as f5_doctorName ";
                        groupBySql = "e.bedsubtype, vmhp.name, e.doctorname, e.doctorsnils,  vms.id, vms.code, vms.name";
                        sumCostSql=" ,count(distinct ems.id) as f6_cnt, sum(e.cost) as f7_cost";
                        orderBySql = "e.bedsubtype, vmhp.name, e.doctorname, e.doctorsnils";
                        break;
                    default:
                        selectSql = ",case when e.bedsubtype ='1' then 'КРУГЛОСУТОЧНЫЙ СТАЦ' when e.bedsubtype ='2' then 'ДНЕВНОЙ СТАЦ' else cast('' as varchar) end as f2_stacType" +
                                ",cast('' as varchar) as f3_depname, cast('' as varchar)  as f4_helpKind, cast('' as varchar)  as f5_doctorName ";
                        groupBySql = "e.bedsubtype";
                        orderBySql = "e.bedsubtype";
                }
                StringBuilder sql = new StringBuilder();
                String defectSql = ("NO".equals(typeDefect) ? " and (e.isDefect is null or e.isDefect='0')" : ("YES".equals(typeDefect) ? " and e.isDefect='1'" : ""))+
                        ("NO".equals(typeInog) ? " and (e.isForeign is null or e.isForeign='0')" : ("YES".equals(typeInog) ? " and e.isForeign='1'" : ""));
                switch (typeGroup) {
                    case "POLYCLINIC": //
                        sql.append("select cast('' as varchar) as id").append(selectSql).append(sumCostSql).append(
                                ", v025.name as f8_vidSl"+
                                " from e2entry e" +
                                        " left join e2bill  bill on bill.id=e.bill_id " +
                                        " left join voce2medhelpprofile vmhp on vmhp.id=e.medhelpprofile_id" +
                                        " LEFT JOIN VocE2FondV025 v025 on v025.id=e.visitPurpose_id" +
                                        " where bill.status_id =3 and e.entryType in ('SERVICE','POLYCLINIC') and e.finishDate between to_date('").append(dateBegin).append("','dd.MM.yyyy')" +
                                " and to_date('").append(dateEnd).append("','dd.MM.yyyy') and (e.isDeleted is null or e.isDeleted='0') and (e.doNotSend is null or e.doNotSend='0')").append(defectSql)
                                .append(" group by ").append(groupBySql).append(",v025.id, v025.name order by ").append(orderBySql).append(", v025.name ");
                        break;
                    case "HOSPITAL": //
                    case "VMP": //
                        sql.append("select cast('' as varchar) as id").append(selectSql).append(sumCostSql).append(
                                ("VMP".equals(typeGroup) ? ", e.vmpkind as f8_vmp" : "")).append(
                                " from e2entry e" +
                                " left join e2bill  bill on bill.id=e.bill_id " +
                                " left join voce2medhelpprofile vmhp on vmhp.id=e.medhelpprofile_id" +
                                " where bill.status_id =3 and e.entryType='").append(typeGroup).append("' and e.finishDate between to_date('").append(dateBegin).append("','dd.MM.yyyy')" +
                                " and to_date('").append(dateEnd).append("','dd.MM.yyyy') and (e.isDeleted is null or e.isDeleted='0') and (e.doNotSend is null or e.doNotSend='0')").append(defectSql)
                        .append(" group by ").append(groupBySql).append(("VMP".equals(typeGroup) ? ", e.vmpkind " : "")).append(" order by ").append(orderBySql);

                        break;
                    case "EXTDISP": //
                        sql.append("select cast('' as varchar) as id").append(selectSql).append(sumCostSql).append(
                                ", st.name as f8_dispType"+
                                " from e2entry e" +
                                        " left join e2bill  bill on bill.id=e.bill_id " +
                                        " left join VocE2EntrySubType st on st.id=e.subtype_id").append(
                                        "5".equals(typeView) ? " left join entrymedservice ems on ems.entry_id = e.id left join vocmedservice vms on vms.id=ems.medservice_id" : "")
                                .append(" left join voce2medhelpprofile vmhp on vmhp.id=e.medhelpprofile_id" +
                                        " where bill.status_id =3 and e.entryType='").append(typeGroup).append("' and e.finishDate between to_date('").append(dateBegin).append("','dd.MM.yyyy')" +
                                " and to_date('").append(dateEnd).append("','dd.MM.yyyy') and (e.isDeleted is null or e.isDeleted='0') and (e.doNotSend is null or e.doNotSend='0')").append(defectSql)
                                .append(" group by ").append(groupBySql).append( ", st.id, st.name " ).append(" order by ").append(orderBySql);
                }
                request.setAttribute("mainSql",sql.toString());
              //  out.print("ssssl = "+sql.toString());

                if (isReestr) {
        %>

        <msh:section>
            <msh:sectionTitle>Период с ${beginDate} по ${finishDate}</msh:sectionTitle>
            <msh:sectionContent>
                <ecom:webQuery isReportBase="${isReportBase}" maxResult="1500"  name="journal_ticket" nativeSql="
select smo.id as name
,smo.dateStart as nameFld
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,p.birthday as birthday
,	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end) as age
,case when (ad1.addressisvillage='1') then 'сел' else null end as cntVil
,vr.name as vrname
,vwpt.name as vwptname
,vss.name ||' '|| case when vss.code='HOSPITAL' or vss.code='OTHER' then
coalesce(' - '||(select
list(case when sls.deniedHospitalizating_id is null then slsml.name||' '||
to_char(sls.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(sls.dateFinish,'dd.mm.yyyy'),
'по тек.мом.') else ' ОТКАЗ '||slsml.name ||' '||to_char(sls.dateStart,'dd.mm.yyyy') end)
from medcase sls
left join mislpu slsml on slsml.id=sls.department_id
where sls.patient_id=smo.patient_id and sls.dtype='HospitalMedCase'
and (
sls.deniedHospitalizating_id is null and smo.datestart between sls.dateStart and coalesce(sls.datefinish,current_date)
or
sls.deniedHospitalizating_id is not null and smo.datestart between sls.dateStart and sls.datestart+1
)
),'нет данных')
else '' end as vssname
,list(mkb.code) as mkblist,list(ms.code||' '||ms.name) as servecilist
,olpu.name as olpuname
,ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename||' ('||owflpu.name||')' as owfinfo

FROM MedCase smo
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id
left join VocAdditionStatus vas on vas.id=p.additionStatus_id
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id
LEFT JOIN Worker w on w.id=wf.worker_id
LEFT JOIN Patient wp on wp.id=w.person_id
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id
left join diagnosis diag on diag.medcase_id=smo.id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join medcase mssmo on mssmo.parent_id=smo.id and mssmo.dtype='ServiceMedCase'
left join medservice ms on ms.id=mssmo.medservice_id
left join mislpu olpu on olpu.id=smo.orderLpu_id
LEFT JOIN WorkFunction owf on owf.id=smo.orderWorkFunction_id
LEFT JOIN VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
LEFT JOIN Worker ow on ow.id=owf.worker_id
left join mislpu owflpu on owflpu.id=ow.lpu_id
LEFT JOIN Patient owp on owp.id=ow.person_id
WHERE  ${dtypeSql}
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy')
and (smo.noActuality is null or smo.noActuality='0')
${specialistSql} ${workFunctionSql} ${workFunctionGroupSql} ${lpuSql} ${serviceStreamSql} ${medServiceSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql} ${is039Sql}
group by ${groupOrder},smo.id,smo.dateStart,p.lastname,p.middlename,p.firstname,p.birthday,ad1.addressisvillage,vr.name,vwpt.name,vss.name
,olpu.name,ovwf.name,owp.lastname,owp.firstname,owp.middlename,smo.patient_id,vss.code,owflpu.name

ORDER BY ${groupOrder},p.lastname,p.firstname,p.middlename
" />
                <msh:table printToExcelButton="Сохранить в excel"
                           name="journal_ticket" action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата посещения" property="2"/>
                    <msh:tableColumn columnName="ФИО пациента" property="3"/>
                    <msh:tableColumn columnName="Дата рождения" property="4"/>
                    <msh:tableColumn columnName="Возраст" property="5"/>
                    <msh:tableColumn columnName="Житель" property="6"/>
                    <msh:tableColumn columnName="цель визита" property="7"/>
                    <msh:tableColumn columnName="место" property="8"/>
                    <msh:tableColumn columnName="поток обсл." property="9"/>
                    <msh:tableColumn columnName="диагноз" property="10"/>
                    <msh:tableColumn columnName="услуга" property="11"/>
                    <msh:tableColumn columnName="напр. ЛПУ" property="12"/>
                    <msh:tableColumn columnName="напр. внутр." property="13"/>
                    <msh:tableColumn columnName="направившее ЛПУ" property="14"/>
                </msh:table>
            </msh:sectionContent>

        </msh:section>
        <% } else { // свод
        %>
        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="${mainSql}" />
            <msh:sectionTitle>${journal_ticket_sql}

            </msh:sectionTitle>
            <msh:sectionContent>

                <msh:table printToExcelButton="Сохранить в excel"
                           name="journal_ticket" action="visit_report_service.do?typeReestr=1&typeDiag=${typeDiag}&typeView=${typeView}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}&typeGroup=${typeGroup}"
                           idField="1" noDataMessage="Не найдено">

                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Тип стац" property="2"/>
                    <msh:tableColumn columnName="Отделение" property="3"/>
                    <msh:tableColumn columnName="Профиль помощи" property="4"/>
                    <msh:tableColumn columnName="Доктор" property="5"/>
                    <msh:tableColumn columnName="" property="8"/>
                    <msh:tableColumn columnName="Кол-во случаев" isCalcAmount="true" property="6"/>
                    <msh:tableColumn columnName="Сумма денег" isCalcAmount="true" property="7"/>
                </msh:table>
            </msh:sectionContent>

        </msh:section>

        <% }
        } else {%>
        <i>Выберите параметры поиска и нажмите "Найти" </i>
        <% }   %>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">

            checkFieldUpdate('typeGroup','${typeGroup}',"") ;
            checkFieldUpdate('typeView','${typeView}',1) ;
            checkFieldUpdate('typeDefect','${typeDefect}',"NO") ;
            checkFieldUpdate('typeInog','${typeInog}',"ALL") ;


            function checkFieldUpdate(aField,aValue,aDefaultValue) {
                if (jQuery(":radio[name="+aField+"][value='"+aValue+"']").val()!=undefined) {
                    jQuery(":radio[name="+aField+"][value='"+aValue+"']").prop('checked',true);
                } else {
                    jQuery(":radio[name="+aField+"][value='"+aDefaultValue+"']").prop('checked',true);
                }
            }

        </script>
    </tiles:put>
</tiles:insert>