<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Отчёт по записанным на дату</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String dateBegin = request.getParameter("dateBegin") ;
            if (dateBegin!=null && !dateBegin.equals(""))
                request.setAttribute("dateBegin",dateBegin);
            String serviceStream = request.getParameter("serviceStream") ;
            if (serviceStream==null || serviceStream.equals(""))
                request.setAttribute("serviceStream",0);
            else
                request.setAttribute("serviceStream",serviceStream);
            String filterAdd = request.getParameter("filterAdd") ;
            if (filterAdd==null || filterAdd.equals(""))
                request.setAttribute("filterAdd",0);
            else
                request.setAttribute("filterAdd",filterAdd);
            if (request.getParameter("short")==null) {
        %>
        <msh:form action="/recordReport.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateBegin" label="Дата" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="serviceStream"
                                      label="Поток облуживания" horizontalFill="true" vocName="vocServiceStream"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="filterAdd" label="Специалист" size="40" vocName="workFunctionByDirect"/>
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
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="totalinfo" nameFldSql="totalinfo_sql" nativeSql="
                select * from selectinfoforkdcrecordreport('${dateBegin}','${filterAdd}','${serviceStream}')"/>
                <form action="print-recordReport.do" method="post" target="_blank">
                    <input type='hidden' name="sqlText" id="sqlText" value="${totalinfo_sql}">
                    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Дата ${param.dateBegin}.">
                    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
                    <input type='hidden' name="s" id="s" value="PrintService">
                    <input type='hidden' name="m" id="m" value="printNativeQuery">
                    <input type="submit" value="Печать">
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="totalinfo" openNewWindow="true"
                           action="entityView-mis_patient.do" idField="10">
                    <msh:tableColumn property="1" columnName="#" />
                    <msh:tableColumn columnName="Врач" property="3" />
                    <msh:tableColumn columnName="#" property="4" />
                    <msh:tableColumn columnName="Время" property="5" />
                    <msh:tableColumn columnName="Пациент" property="6" />
                    <msh:tableColumn columnName="Район" property="7" />
                    <msh:tableColumn columnName="Факт. адрес" property="8" />
                    <msh:tableColumn columnName="Дата последнего визита" property="9" />
                    <msh:tableColumn columnName="Другие визиты в этот день" property="11" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
        %>
        <script type='text/javascript'>
            function find() {
                var frm = document.forms[0];
                frm.target = '';
                frm.action = 'recordReport.do';
                frm.submit();
            }
        </script>
        <%
        }
        %>
    </tiles:put>
</tiles:insert>