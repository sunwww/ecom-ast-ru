<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
    String ksCode="A16.20.005";
    request.setAttribute("ksCode",ksCode);
    String nul = request.getParameter("nul") ;
    if (nul==null) {

%>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Классификация Робсона</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String dateBegin = request.getParameter("dateBegin") ;
            if (dateBegin!=null && !dateBegin.equals("")) {
                request.setAttribute("dateBegin",dateBegin);
            }
            String dateEnd = request.getParameter("dateEnd") ;
            if (dateEnd==null || dateEnd.equals("")) dateEnd=dateBegin;
            if (dateEnd!=null && !dateEnd.equals("")) {
                request.setAttribute("dateEnd",dateEnd);
            }
            if (request.getParameter("short")==null) {
        %>
        <msh:form action="/journal_robson.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td colspan="3">
                        <input type="button" onclick="find()" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {
                request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
                ActionUtil.getValueBySql("select getTotalChildBirthOrKs('"+dateBegin+"','"+dateEnd+"',"+false+",'"+ksCode+"') ", "r","rName",request) ;
                ActionUtil.getValueBySql("select getTotalChildBirthOrKs('"+dateBegin+"','"+dateEnd+"',"+true+",'"+ksCode+"') ", "ks","ksName",request) ;
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="totalinfo" nameFldSql="totalinfo_sql" nativeSql="
                select * from selectRobson('${ksCode}','${dateBegin}','${dateEnd}',${r},${ks})"/>
                <form action="print-journal_robson.do" method="post" target="_blank">
                    <input type='hidden' name="sqlText" id="sqlText" value="${totalinfo_sql}">
                    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
                    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
                    <input type='hidden' name="s" id="s" value="PrintService">
                    <input type='hidden' name="m" id="m" value="printNativeQuery">
                    <input type="submit" value="Печать">
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="totalinfo"
                           action="journal_robson.do" idField="2" cellFunction="true">
                    <msh:tableColumn property="1" columnName="#" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Группа" property="3" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Кол-во КС в группе" property="4" addParam="&short=Short&ks=true&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&viewname=(КС)"/>
                    <msh:tableColumn columnName="Кол-во женщин в группе" property="5"  addParam="&short=Short&ks=false&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&viewname=(роды)"/>
                    <msh:tableColumn columnName="Размер группы (%)" property="6" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Частота КС в группе (%)" property="7" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Абсолютный вклад группы в общую частоту КС (%)" property="9" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Относительный вклад группы в общую частоту КС (%)" property="8" addParam="&nul=nul"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
        %>
        <script type='text/javascript'>
            function setCssClassName() {
                var num = document.getElementsByTagName('table').length > 0 ? 1 : 0;
                var rows = document.getElementsByTagName('table')[num].rows;
                var r = rows[rows.length - 1];
                for (var j = 0; j < r.cells.length; j++)
                    r.cells[j].className += ' sumTd ';
            }
            setCssClassName();
        function find() {
            var frm = document.forms[0];
            frm.target = '';
            frm.action = 'journal_robson.do';
            frm.submit();
        }
        </script>
        <%
        } else {
            if (dateEnd!=null && !dateEnd.equals("")) request.setAttribute("dateTo"," по "+dateEnd);
                request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
                //строчки "итого" считаются в хранимке (и не должны генерить запрос на ФИО), т.к. есть разбивка по подкатегориям => нельзя calcAmount
                //чтобы не было ошибки в выполнении запроса
           if (request.getParameter("vrid")!=null && !request.getParameter("vrid").toString().equals("")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="patList" nameFldSql="patList_sql" nativeSql="
                select distinct sls.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
                from medcase slo
                left join childbirth chb on chb.medcase_id=slo.id
                left join robsonclass r on r.medcase_id=slo.id
                left join vocrobsonclass vr on vr.id=r.robsontype_id
                left join vocsubrobson vsr on vsr.id=r.robsonsub_id
                left join MedCase sls on sls.id=slo.parent_id and sls.dtype='HospitalMedCase'
                left join SurgicalOperation so on so.medCase_id=slo.id
                left join MedService vo on vo.id=so.medService_id
                left join patient pat on slo.patient_id=pat.id
                where chb.birthfinishdate between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')  and slo.dtype='DepartmentMedCase'
                and case when ${param.vrid}<>-1 then vr.id=${param.vrid} else vr.id is not null end
                and case when ${param.ks}=true then vo.code='${ksCode}' else 1=1 end
                and case when ${param.vsrid}<>0 then vsr.id=${param.vsrid} else 1=1 end"/>

                <form action="journal_robson.do" method="post" target="_blank">
                    ${param.vrname} (подгруппа: ${param.vsrname}) за период с ${param.dateBegin} ${dateTo} ${param.viewname}
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="patList" openNewWindow="true"
                           action="entityView-stac_ssl.do" idField="1" printToExcelButton="Сохранить в excel">
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }}
        %>
    </tiles:put>
</tiles:insert>
<%
    }
%>