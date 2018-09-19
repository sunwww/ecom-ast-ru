<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Консультации в стационаре</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/wfConsultationsAll.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                    <td class="label" title="Показать  (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Показать:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup" value="1" checked> все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="2"> выполненные
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup" value="3"> НЕ выполненные
                    </td>
                </msh:row>
                <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                    <td class="label" title="Показать  (typeGroup2)" colspan="1"><label for="typeGroup2Name" id="typeGroup2Label"></label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup2" value="1" checked> все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup2" value="2"> переданные
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup2" value="3"> НЕ переданные
                    </td>
                </msh:row>
                <msh:row>
                    <td colspan="3">
                        <input type="button" onclick="this.disabled=true;find();" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            StringBuilder typeSql=new StringBuilder();
            String type1 = (String)request.getParameter("typeGroup");
            if (type1!=null && !type1.equals("")) {
                if (type1.equals("2")) typeSql.append(" and scg.diary_id is not null");
                if (type1.equals("3")) typeSql.append(" and scg.diary_id is null");
            }
            String type2 = (String)request.getParameter("typeGroup2");
            if (type2!=null && !type2.equals("")) {
                if (type2.equals("2")) typeSql.append(" and scg.transferdate is not null");
                if (type2.equals("3")) typeSql.append(" and scg.transferdate is null");
            }
            request.setAttribute("typeSql",typeSql.toString());
            request.setAttribute("typeGroup",type1);
            request.setAttribute("typeGroup2",type2);
        %>
        <msh:section>
            <msh:sectionTitle>Зелёные - выполненные, жёлтые - переданные, красные - не переданные
                <ecom:webQuery isReportBase="true" name="totalName" nameFldSql="totalName_sql" nativeSql="
select scg.id,vtype.code||' '||vtype.name as f00,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as f01,
pat.lastname||' '||pat.firstname||' '||pat.middlename||' '||to_char(pat.birthday,'dd.mm.yyyy') as fpat,
scg.createusername as f1,scg.createdate as f2,scg.editusername as f3,scg.editdate as f4, scg.transferusername as f5,scg.transferdate as f6
, case
    when scg.diary_id is not null then 'background:#90EE90;color:black'
    else case when scg.transferdate is not null  then 'background:yellow;color:black'
    else case when scg.transferdate is null  then 'background:#CD5C5C;color:black'
    else '' end end end as f10style
from prescription scg
left join PrescriptionList pl on pl.id=scg.prescriptionList_id
left join workfunction wf on wf.id=scg.prescriptcabinet_id
left join vocworkFunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id = wf.worker_id
left join patient wp on wp.id=w.person_id
left join vocconsultingtype vtype on vtype.id=scg.vocconsultingtype_id
left join medcase slo on slo.id=pl.medcase_id
left join patient pat on slo.patient_id=pat.id
where scg.dtype='WfConsultation' ${typeSql}
"/>
                <form action="javascript:void(0)" method="post" target="_blank"></form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="totalName" viewUrl="wfConsultationsAll.do" action="entityView-pres_wfConsultation.do" idField="1" styleRow="11">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Тип" property="2"/>
                    <msh:tableColumn columnName="Специалист" property="3"/>
                    <msh:tableColumn columnName="Пациент" property="4"/>
                    <msh:tableColumn columnName="Создал" property="5"/>
                    <msh:tableColumn columnName="Дата создания" property="6"/>
                    <msh:tableColumn columnName="Отредактировал" property="7"/>
                    <msh:tableColumn columnName="Дата редактирования" property="8"/>
                    <msh:tableColumn columnName="Передал" property="9"/>
                    <msh:tableColumn columnName="Дата передачи" property="10"/>
                    <msh:tableButton property="1" addParam="this" buttonFunction="setWfConsultingIsTransfered" buttonName="Передать?" buttonShortName="Передать"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
        <script type="text/javascript">
            checkFieldUpdate('typeGroup','${typeGroup}',3) ;
            checkFieldUpdate('typeGroup2','${typeGroup2}',1) ;
            function checkFieldUpdate(aField,aValue,aDefault) {
                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)==0 || (+aValue)>(+max)) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
            function find() {
                var frm = document.forms[0];
                frm.submit();
            }
            function setWfConsultingIsTransfered(id) {
                HospitalMedCaseService.setWfConsultingIsTransfered(
                    id, {
                        callback: function (res) {
                            if (res=="1") location.reload(); else alert("Консультация уже была отмечена, как переданная врачу! Повторно сделать это нельзя.")
                        }
                    }
                );
            }
        </script>
    </tiles:put>
</tiles:insert>