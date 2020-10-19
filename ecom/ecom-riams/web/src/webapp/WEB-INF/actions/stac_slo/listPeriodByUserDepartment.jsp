<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal">Журнал COVID для Иванова за период</msh:title>
    </tiles:put>
    <tiles:put name="side" type="string">
        <tags:stac_journal currentAction="stac_journalPeriodByUserDepartment"/>
    </tiles:put>
    <%
        String date = request.getParameter("dateBegin"), dateEnd="";
        if (date!=null && !date.equals("")) {
            dateEnd = request.getParameter("dateEnd");

            if (dateEnd == null || dateEnd.equals("")) {
                dateEnd = date;
            }
        }
        request.setAttribute("dateBegin",date) ;
        request.setAttribute("dateEnd", dateEnd) ;
    %>
    <tiles:put name="body" type="string">
        <msh:form action="/stac_journalPeriodByUserDepartment.do" defaultField="dateBegin" disableFormDataConfirm="true" method="POST">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска (по дате поступления)" colSpan="6"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с"/>
                    <msh:textField property="dateEnd" label="по"/>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="button" value="Найти" onclick="jQuery('#a').show();"/>
                        <a hidden id="a" href='print-stac_all_department_covid_lab.do?s=HospitalPrintService&m=printCovidAllDepartments&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&'>
                            Журнал COVID для Иванова (пациенты по всем инфекционным отделениям)</a>
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
       </tiles:put>
</tiles:insert>