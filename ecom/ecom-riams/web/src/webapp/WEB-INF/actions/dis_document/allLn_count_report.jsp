<%@ page import="java.awt.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="body" type="string">
        <%
            String shor = request.getParameter("short");
            String ElnInfo = request.getParameter("ElnInfo");
            String lpuId = (String)request.getParameter("id");
            String beginDate = request.getParameter("dateBegin");
            if(lpuId!=null && !lpuId.equals(""))request.setAttribute("id", lpuId);
            request.setAttribute("lpuId", lpuId);
            if(ElnInfo!=null && !ElnInfo.equals(""))  request.setAttribute("ElnInfo", ElnInfo);
            if(shor==null || shor.equals(""))
            {%>
        <msh:form action="/allLn_count_report.do" method="GET" defaultField="hello">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7"/>
                </msh:row>
                <msh:row>
                    <msh:hidden property="department" />
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

                if(shor==null || shor.equals("")){
        %>
        <input id="getExcel2" class="button" name="submit" value="Печать" onclick="mshSaveTableToExcelById()" role="button" type="submit">
        <div id="eln">
            <ecom:webQuery isReportBase="false" name = "elnList" nameFldSql="listSQL"
                           nativeSql="select
                           (select lpu.name from disabilityrecord dr
                            left join workfunction wf on dr.workfunction_id = wf.id
                            left join worker w on w.id = wf.worker_id
                            left join mislpu lpu on lpu.id = w.lpu_id where
                            dr.id=(select max(dr2.id) from disabilityrecord dr2 where dr2.disabilitydocument_id=dd.id)) as lpuname,
                            count(dd.id) as lns,
                            count(dd.id) - count(edd.id) as ln,
                            ((count(dd.id) - count(edd.id))*100)/count(dd.id) as procetln,
                            count(edd.id) as eln,
                             100 - ((count(dd.id) - count(edd.id))*100)/count(dd.id) as procenteln
                             ,count(dd.id),
                             (select lpu.id from disabilityrecord dr
                            left join workfunction wf on dr.workfunction_id = wf.id
                            left join worker w on w.id = wf.worker_id
                            left join mislpu lpu on lpu.id = w.lpu_id where
                            dr.id=(select max(dr2.id) from disabilityrecord dr2 where dr2.disabilitydocument_id=dd.id)) as lpuid
                            from disabilitydocument dd
                            left join electronicdisabilitydocumentnumber edd on edd.disabilitydocument_id = dd.id
                            where dd.noactuality =false and dd.anotherlpu_id is null and
                            dd.issuedate  between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
                            group by lpuname,lpuid
                            order by lpuname"/>
            <msh:table name="elnList" cellFunction="true"                      action="allLn_count_report.do?dateBegin=${dateStart}&dateEnd=${dateFinish}&short=Short&lpuId=${lpuId}" idField="8">
                <msh:tableColumn columnName="№" identificator="false" property="sn" />
                <msh:tableColumn columnName="Отделение" property="1"/>
                <msh:tableColumn columnName="Всего ЛН" property="2" isCalcAmount="true"/>
                <msh:tableColumn columnName="Бумажных" property="3" isCalcAmount="true"/>
                <msh:tableColumn columnName="%" property="4"/>
                <msh:tableColumn columnName="Электронных" property="5" isCalcAmount="true"/>
                <msh:tableColumn columnName="%" property="6"/>
            </msh:table>
        </div>
        <%}else{ %>
        <ecom:webQuery isReportBase="false" name = "elnByDoc" nameFldSql="listSQL"
                       nativeSql="select doc.lastname||' '||doc.firstname||' '||doc.middlename as docfio,
                        count(dd.id) as lns,
                        count(dd.id) - count(edd.id) as ln,
                        ((count(dd.id) - count(edd.id))*100)/count(dd.id) as procetln,
                        count(edd.id) as eln,
                        100 - ((count(dd.id) - count(edd.id))*100)/count(dd.id) as procenteln
                        ,count(dd.id)
                        ,doc.id
                        from disabilitydocument dd
                        left join disabilityrecord dr on dr.id = (select max(id) from disabilityrecord where disabilitydocument_id =dd.id)
                        left join workfunction wf on dr.workfunction_id = wf.id
                        left join worker w on w.id = wf.worker_id
                        left join patient doc on doc.id = w.person_id
                        left join electronicdisabilitydocumentnumber edd on edd.disabilitydocument_id = dd.id
                        where dd.noactuality =false and dd.issuedate  between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
                        and dd.anotherlpu_id is null and w.lpu_id = ${id}
                        group by docfio,doc.id"/>
        <br> Период с ${dateStart} по ${dateFinish} по врачам <br>
        <msh:section>
            <msh:sectionContent>
                <msh:table cellFunction="true" name="elnByDoc" action="allLn_count_report.do?dateBegin=${dateStart}&dateEnd=${dateFinish}&short=Short&lpuId=${lpuId}&ElnInfo=true" idField="8">
                    <msh:tableColumn columnName="№" identificator="false" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="1"/>
                    <msh:tableColumn columnName="Всего ЛН" property="2" isCalcAmount="true"/>
                    <msh:tableColumn columnName="Бумажных" property="3" isCalcAmount="true"/>
                    <msh:tableColumn columnName="%" property="4"/>
                    <msh:tableColumn columnName="Электронных" property="5" isCalcAmount="true"/>
                    <msh:tableColumn columnName="%" property="6"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%}
            if(ElnInfo!=null && !ElnInfo.equals("")){
                String lpu = (String)request.getParameter("lpuId");
                if(lpu !=null && !lpu.equals(""))request.setAttribute("lpu", lpu);
        %>
        <ecom:webQuery isReportBase="false" name = "elnDoc" nameFldSql="listSQL"
                       nativeSql="select
					   dd.id,
					     p.lastname||' '||p.firstname||' '||p.middlename as p,
                        dd.number as number,
                        case when edd.id is not null then 'Электронный' else 'Бумажный' end as isEln
                        from disabilitydocument dd
                        left join disabilitydocument prev on prev.id = dd.prevdocument_id
                        left join disabilityrecord dr on dr.id = (select max(id) from disabilityrecord where disabilitydocument_id =dd.id)
                        left join patient p on p.id = dd.patient_id or p.id = prev.patient_id
                        left join workfunction wf on dr.workfunction_id = wf.id
                        left join worker w on w.id = wf.worker_id
                        left join patient doc on doc.id = w.person_id
                        left join electronicdisabilitydocumentnumber edd on edd.disabilitydocument_id = dd.id
                        where dd.issuedate  between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
                        and dd.anotherlpu_id is null and w.lpu_id = ${lpu} and doc.id = ${id}
                        order by isEln"/>
        <msh:section>
            <msh:sectionContent>
                <msh:table name="elnDoc" action="entityParentView-dis_document.do" idField="1">
                    <msh:tableColumn columnName="№" identificator="false" property="sn" />
                    <msh:tableColumn columnName="ФИО пациента" property="2"/>
                    <msh:tableColumn columnName="Номер ЛН" property="3"/>
                    <msh:tableColumn columnName="Тип ЛН" property="4"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%}}else { %>
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
