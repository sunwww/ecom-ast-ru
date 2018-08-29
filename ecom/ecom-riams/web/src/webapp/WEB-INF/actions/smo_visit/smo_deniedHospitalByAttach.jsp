<%@ page import="java.awt.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="body" type="string">
        <%  String shortForm = request.getParameter("short");
            String beginDate = request.getParameter("dateBegin");
            if(shortForm==null || shortForm.equals(""))
            {%>
        <msh:form action="/smo_deniedHospitelByAttach.do" method="GET" defaultField="hello">
            <msh:panel guid="panel">
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7"/>
                </msh:row>
                <msh:row>
                    <td>
                        Период
                    </td>
                    <msh:textField property="dateBegin" label="c"/>
                    <msh:textField property="dateEnd" label="по"/>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%}
            if (beginDate!=null && !beginDate.equals("")) {
                String finishDate = request.getParameter("dateEnd");
                if (finishDate==null || finishDate.equals("")) {
                    finishDate=beginDate ;
                }
                request.setAttribute("dateStart", beginDate);
                request.setAttribute("dateFinish", finishDate);
                if(shortForm==null || shortForm.equals("")){
        %>
        <input id="getExcel2" class="button" name="submit" value="Печать" onclick="mshSaveTableToExcelById()" role="button" type="submit">
        <div id="eln">
            <ecom:webQuery isReportBase="false" name = "allByLpu" nameFldSql="listSQL"
                           nativeSql="select lpu.id, lpu.name as lpuname,
                           count(pf.id)  as countdeni
                            from medcase m
                            left join patient p on p.id = m.patient_id
                            left join patientfond pf
                            on pf.lastname = p.lastname
                            and pf.firstname = p.firstname
                            and pf.middlename = p.middlename
                            and pf.birthday = p.birthday
                            left join mislpu lpu on lpu.codef = pf.lpuattached
                            where m.dtype = 'HospitalMedCase'
                            and m.deniedhospitalizating_id is not null
                            and m.datestart between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
                            and pf.id=(select max(id) from patientfond
                            where lastname = p.lastname
                            and firstname = p.firstname
                            and middlename = pf.middlename
                            and birthday = pf.birthday)
                            and lpu.id = (select max(id) from mislpu where codef = pf.lpuattached)
                            group by lpu.name,lpu.id
                            order by countdeni desc "/>

            <msh:table name="allByLpu" cellFunction="true" action="smo_deniedHospitelByAttach.do?dateBegin=${dateStart}&dateEnd=${dateFinish}&short=Short" idField="1">
                <msh:tableColumn columnName="№" identificator="false" property="sn" />
                <msh:tableColumn columnName="ЛПУ прикрепления" property="2"/>
                <msh:tableColumn columnName="Отказов" property="3" isCalcAmount="true"/>
            </msh:table>
        </div>
        <%}else {
            String lpuId = (String)request.getParameter("id");
            if(lpuId!=null && !lpuId.equals(""))request.setAttribute("id", lpuId); %>
        <input id="getExcel2" class="button" name="submit" value="Печать" onclick="mshSaveTableToExcelById()" role="button" type="submit">
        <div id="eln">
            <ecom:webQuery isReportBase="false" name = "allByLpu" nameFldSql="listSQL"
                           nativeSql="select dep.id,dep.name as depname, count(m.id)
                            from medcase m
                            left join patient p on p.id = m.patient_id
                            left join patientfond pf
                            on pf.lastname = p.lastname
                            and pf.firstname = p.firstname
                            and pf.middlename = p.middlename
                            and pf.birthday = p.birthday
                            left join mislpu lpu on lpu.codef = pf.lpuattached
                            left join diary d on d.medcase_id = m.id
                            left join workfunction wf on wf.id = d.specialist_id
                            left join vocworkfunction vwf on vwf.id = wf.workfunction_id
                            left join mislpu dep on dep.id = m.department_id
                            where
                            m.dtype = 'HospitalMedCase'
                            and m.deniedhospitalizating_id is not null
                            and m.datestart between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
                            and lpu.id = ${id}
                            and pf.id=(select max(id) from patientfond
                            where lastname = p.lastname
                            and firstname = p.firstname
                            and middlename = pf.middlename
                            and birthday = pf.birthday)
                            group by depname,dep.id"/>

            <msh:table name="allByLpu" cellFunction="true" action="smo_deniedHospitelByAttach.do?dateBegin=${dateStart}&dateEnd=${dateFinish}&short=Short&lpuId=${lpuId}" idField="1">
                <msh:tableColumn columnName="№" identificator="false" property="sn" />
                <msh:tableColumn columnName="Отделение" property="2"/>
                <msh:tableColumn columnName="Кол-во отказов" property="3" isCalcAmount="true"/>
            </msh:table>
        </div>
        <%}
            }else{ %>
        <i>Выберите параметры поиска и нажмите "Найти" </i>
        <%}%>

    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function mshPrintTextToExcelTable (html) {
                window.location.href='data:application/vnd.ms-excel,'+'\uFEFF'+encodeURIComponent(html); }
            function mshSaveTableToExcelById() {
                mshPrintTextToExcelTable(document.getElementById("eln").outerHTML);}
        </script>
    </tiles:put>
</tiles:insert>
