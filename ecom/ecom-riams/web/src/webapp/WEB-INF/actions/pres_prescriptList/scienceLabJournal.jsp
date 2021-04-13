<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Наука для лаборатории</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String date = request.getParameter("dateBegin");
            String dateEnd = request.getParameter("dateEnd");

            if (dateEnd == null || dateEnd.equals("")) dateEnd = date;
            request.setAttribute("dateBegin", date);
            request.setAttribute("dateEnd", dateEnd);

        %>
        <msh:form action="/scienceLabJournal.do" defaultField="dateBegin" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с"/>
                    <msh:textField property="dateEnd" label="по"/>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти"/>
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (date != null && !date.equals("")) {
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery isReportBase="true" name="scienceLabJournal" nativeSql="
select distinct sls.id as f1_id,pat.lastname||' ' ||pat.firstname||' '||pat.middlename as f2_fio
,to_char(pat.birthday,'dd.mm.yyyy') as f3_birth
,st.code as f4_hist
, getAllParamsByMedService(sls.id,'A09.05.076') as f5_ferr
, getAllParamsByMedService(sls.id,'A09.05.009') as f6_srb
, getAllParamsByMedService(sls.id,'A09.05.042') as f7_alt
, getAllParamsByMedService(sls.id,'A09.05.041') as f8_ast
, getAllParamsByMedService(sls.id,'A09.05.039') as f9_ldg
, getAllParamsByMedService(sls.id,'A09.05.044') as f10_kfk
, getAllParamsByMedService(sls.id,'A09.05.209') as f11_prok
, getAllParamsByMedService(sls.id,'A09.05.206') as f12_ca
, getAllParamsByMedService(sls.id,'A09.05.031') as f13_k
, getAllParamsByMedService(sls.id,'A09.05.020') as f14_kreat
, getAllParamsByMedService(sls.id,'B03.016.002') as f15_oak
, getAllParamsByMedService(sls.id,'B03.016.003') as f16_oak_razv
from prescription p
left join medcase mc on mc.id = p.medcase_id
left join prescriptionlist pl on pl.id=p.prescriptionlist_id
left join medcase slo on slo.id = pl.medcase_id
left join medcase sls on sls.id =slo.parent_id
left join statisticstub st on st.medcase_id = sls.id
left join medservice ms on p.medservice_id=ms.id
left join patient pat on pat.id=sls.patient_id
 where mc.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
 and ms.code='A09.05.076' and sls.dtype='HospitalMedCase'
group by sls.id,pat.lastname||' ' ||pat.firstname||' '||pat.middlename
,to_char(pat.birthday,'dd.mm.yyyy')
,st.code
      "/>
                <msh:table name="scienceLabJournal" noDataMessage="Нет данных"
                           printToExcelButton="Сохранить в excel"
                           action="entityParentView-stac_ssl.do" idField="1"
                           openNewWindow="true">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn property="2" columnName="ФИО"/>
                    <msh:tableColumn property="3" columnName="Дата рождения"/>
                    <msh:tableColumn property="4" columnName="№ истории"/>
                    <msh:tableColumn property="5" columnName="ферритин"/>
                    <msh:tableColumn property="6" columnName="срб"/>
                    <msh:tableColumn property="7" columnName="алт"/>
                    <msh:tableColumn property="8" columnName="аст"/>
                    <msh:tableColumn property="9" columnName="лдг"/>
                    <msh:tableColumn property="10" columnName="кфк"/>
                    <msh:tableColumn property="11" columnName="прокальцит"/>
                    <msh:tableColumn property="12" columnName="Ca2+"/>
                    <msh:tableColumn property="13" columnName="K+"/>
                    <msh:tableColumn property="14" columnName="креат."/>
                    <msh:tableColumn property="15" columnName="оак"/>
                    <msh:tableColumn property="16" columnName="оак (разв.)"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <% } %>
    </tiles:put>
</tiles:insert>