<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Выписки, данные о которых были удалены</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    <%
        String date = request.getParameter("dateBegin"), dateEnd="";
        if (date!=null && !date.equals("")) {
            dateEnd = request.getParameter("dateEnd");

            if (dateEnd == null || dateEnd.equals(""))
                dateEnd = date;
        }
        request.setAttribute("dateBegin",date) ;
        request.setAttribute("dateEnd", dateEnd) ;
    %>
    <tiles:put name="body" type="string">
        <msh:form action="/journal_deletedDisch.do" defaultField="department" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td colspan="3">
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (request.getParameter("dateBegin")!=null) {
        %>
        <ecom:webQuery isReportBase="true" name="journalList" nativeSql="
                select mc.id as f1_mcId
        ,pat.patientinfo as f2_patinfo
        ,to_char(ac.datefinishdisch,'dd.mm.yyyy')||' '||to_char(ac.timedisch,'HH24:MM') as f3_dischInfo
        ,ac.createusername as f4_cr
        ,to_char(ac.createdate,'dd.mm.yyyy')||' '||to_char(ac.createtime,'HH24:MM') as f5_crInfo
        ,to_char(mc.datefinish,'dd.mm.yyyy')||' '||to_char(mc.dischargetime,'HH24:MM') as f6_mcInfo
        ,case when mc.datefinish is null or mc.dischargetime is null then 'background:#F08080;color:black;' else '' end as f7_style
        from AdminChangeJournal ac
        left join medcase mc on ac.medcase=mc.id
        left join patient pat on pat.id=mc.patient_id
        where ac.ctype='HOSP_DELETE_DATA_DISCHARGE'
        and ac.createdate between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
        "/>
        <msh:table idField="1" name="journalList" action="entityParentView-stac_ssl.do" styleRow="7" openNewWindow="true">
            <msh:tableColumn columnName="#" property="sn" />
            <msh:tableColumn columnName="ФИО" property="2" />
            <msh:tableColumn columnName="Удалённые данные" property="3"/>
            <msh:tableColumn columnName="Кто удалил" property="4"/>
            <msh:tableColumn columnName="Когда удалил" property="5"/>
            <msh:tableColumn columnName="Данные о выписке на текущий момент" property="6"/>
        </msh:table>
        <%
            }
        %>
    </tiles:put>
</tiles:insert>