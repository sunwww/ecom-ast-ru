<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Журнал подсчёта времени пациентов на ИВЛ</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeDate = ActionUtil.updateParameter("IVLReport", "typeDate", "2", request);

            String date = request.getParameter("dateBegin"), dateEnd = "";
            if (date != null && !date.equals("")) {
                dateEnd = request.getParameter("dateEnd");

                if (dateEnd == null || dateEnd.equals("")) {
                    dateEnd = date;
                }
            }
            request.setAttribute("dateBegin", date);
            request.setAttribute("dateEnd", dateEnd);
            if ("1".equals(typeDate))
                request.setAttribute("typeD", "sls.datestart");
            else
                request.setAttribute("typeD", "sls.datefinish");
            request.setAttribute("typeDate", typeDate);

            String filterAdd = request.getParameter("filterAdd");
            if (filterAdd != null && !filterAdd.equals(""))
                request.setAttribute("vhrSql", " and vhr.id=" + filterAdd);

            String filterAdd2 = request.getParameter("filterAdd2");
            if (filterAdd2 != null && !filterAdd2.equals(""))
                request.setAttribute("ivlSql", " and ms.id=" + filterAdd2);

            String bedType = request.getParameter("bedType") ;
            if (bedType!=null && !bedType.equals(""))
                request.setAttribute("vbtSql"," and vbt.id="+bedType);
            ActionUtil.setParameterFilterSql("bedType", "bf.bedType_id", request);
        %>
        <msh:form action="/journal_ivlTimeCount.do" defaultField="dateBegin" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7"/>
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по  (typeDate)" colspan="1"><label for="typeDateName"
                                                                                      id="typeDateLabel">Поиск по
                        дате:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeDate" value="1"> поступления
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeDate" value="2"> выписки
                    </td>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с"/>
                    <msh:textField property="dateEnd" label="по"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="filterAdd" fieldColSpan="16" horizontalFill="true"
                                      label="Результат госп." vocName="vocHospitalizationResult"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocBedType" property="bedType" label="Профиль коек" horizontalFill="true"
                                      fieldColSpan="5"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="filterAdd2" fieldColSpan="16" horizontalFill="true"
                                      label="Тип ИВЛ" vocName="vocIVL"/>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти"/>
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (request.getParameter("dateBegin") != null && !request.getParameter("dateBegin").equals("")) {

        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_ivlTimeCount" isReportBase="true" nativeSql="
                select so.id as f1_so
                ,st.code as f2_hist
                ,pat.patientinfo as f3_pfio
                ,to_char(sls.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(sls.dateFinish,'dd.mm.yyyy'),'по наст.время') as f4_periodsls
                ,vhr.name as f5_vhtN
                ,ms.name as f6_so
               ,coalesce(to_char(so.operationDate,'DD.MM.YYYY')||' '||to_char(so.operationTime,'HH24:MI')
               ||' - '||to_char(so.operationDateTo,'DD.MM.YYYY')||' '||to_char(so.operationTimeTo,'HH24:MI')
               ,to_char(so.operationDate,'DD.MM.YYYY')||' '||to_char(so.operationTime,'HH24:MI')) as f7_operDate
                ,interval_days(cast((to_char(so.operationDateTo,'dd.mm.yyyy')||' '||to_char(so.operationTimeTo,'HH24:MI')) as timestamp )
                - cast((to_char(so.operationDate,'dd.mm.yyyy')||' '||to_char(so.operationTime,'HH24:MI')) as timestamp)) as f8_period
                from medcase sls
                left join medcase slo on slo.parent_id=sls.id
                left join surgicaloperation so on so.medcase_id=sls.id or so.medcase_id=slo.id
                left join bedfund as bf on bf.id=slo.bedfund_id
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join vocHospitalizationResult vhr on vhr.id=sls.result_id
                left join statisticstub st on st.medcase_id=sls.id
                left join patient pat on pat.id=sls.patient_id
                left join medservice ms on ms.id=so.medService_id
                left join voccoloridentitypatient vip on vip.id=ms.vocColorIdentity_id
                where sls.dtype='HospitalMedCase' and slo.dtype='DepartmentMedCase'
                and  ${typeD} between to_date('${dateBegin}','dd.mm.yyyy')
                and to_date('${dateEnd}','dd.mm.yyyy')
                and vip.code='ИВЛ'
                ${vhrSql}
                ${vbtSql}
                ${ivlSql}
                 "/>
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_ivlTimeCount" noDataMessage="Нет данных"
                           action="entityParentView-stac_surOperation.do"
                           openNewWindow="true"
                           idField="1">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn property="2" columnName="Номер ИБ"/>
                    <msh:tableColumn property="3" columnName="Пациент"/>
                    <msh:tableColumn property="4" columnName="Период госп."/>
                    <msh:tableColumn property="5" columnName="Результат госп."/>
                    <msh:tableColumn property="6" columnName="Тип ИВЛ"/>
                    <msh:tableColumn property="7" columnName="Период ИВЛ"/>
                    <msh:tableColumn property="8" columnName="Общее кол-во времени на ИВЛ"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <% }%>
        <script type='text/javascript'>
            checkFieldUpdate('typeDate', '${typeDate}', 2);

            function checkFieldUpdate(aField, aValue, aDefaultValue) {
                eval('var chk =  document.forms[0].' + aField);
                var aMax = chk.length;
                if ((+aValue) == 0 || (+aValue) > (+aMax)) {
                    chk[+aDefaultValue - 1].checked = 'checked';
                } else {
                    chk[+aValue - 1].checked = 'checked';
                }
            }
        </script>
    </tiles:put>
</tiles:insert>