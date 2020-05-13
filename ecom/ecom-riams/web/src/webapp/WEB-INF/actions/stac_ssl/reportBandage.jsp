<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по перевязкам</msh:title>
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
            String patId=request.getParameter("id");
            request.setAttribute("patId",patId);
            if (request.getParameter("short")==null || request.getParameter("short").equals("")) {
        %>
        <msh:form action="/reportBandage.do" defaultField="department" disableFormDataConfirm="true" method="GET">
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
            if (dateBegin!=null && !dateBegin.equals("")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="true" name="total" nameFldSql="total_sql" nativeSql="
                 select pat.id as pid,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio
                 ,count(mm.id) as cnt
                from medicalmanipulation mm
                left join Patient pat on mm.patient_id=pat.id
                where  mm.startdate between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
                and mm.dtype='Bandage'
                group by pat.id
                order by fio
"/>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="total"
                           action="reportBandage.do?short=Short" idField="1" cellFunction="true">
                    <msh:tableColumn columnName="#" property="sn" addParam="&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"/>
                    <msh:tableColumn columnName="ФИО пациента" property="2" addParam="&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"/>
                    <msh:tableColumn columnName="Количество перевязок" property="3" addParam="&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
        } else {
                    %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="true" name="allBandage" nameFldSql="total_sql" nativeSql="
                select so.id,
            to_char(so.startdate,'dd.mm.yyyy')||' '||coalesce(cast(so.starttime as varchar(5)),'') as datetime,
            a.duration,vam.name as vamname,va.name as vaname,substring(so.text,1,100)||' ...' as text,pat.lastname||' '||pat.firstname||' '||pat.middlename as fioan from medicalmanipulation so
            left join MedService ms on ms.id=so.medService_id
            left join medcase parent on parent.id=so.medcase_id
            left join MisLpu d on d.id=so.thedepartment_id
            left join anesthesia a on a.manipulation_id=so.id
            left join vocanesthesiamethod vam on vam.id=a.method_id
            left join vocanesthesia va on va.id=a.type_id
            left join workfunction wfan on wfan.id=a.anesthesist_id
            left join worker wan on wan.id=wfan.worker_id
            left join Patient pat on pat.id=wan.person_id
            where so.startdate between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
            and so.patient_id=${patId} and so.dtype='Bandage'
            order by so.startdate
"/>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel"
                           name="allBandage" action="entityParentView-stac_bandage.do" idField="1">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата и время" property="2"/>
                    <msh:tableColumn columnName="Длительность" property="3"/>
                    <msh:tableColumn columnName="Метод" property="4"/>
                    <msh:tableColumn columnName="Тип" property="5"/>
                    <msh:tableColumn columnName="Протокол перевязки" property="6"/>
                    <msh:tableColumn columnName="Анестезиолог" property="7"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
        %>
        <script type="text/javascript">
        function find() {
        var frm = document.forms[0];
        frm.submit();
        }
        </script>
    </tiles:put>
</tiles:insert>
<!-- lastrelease milamesher 13.03.2018 #93-->