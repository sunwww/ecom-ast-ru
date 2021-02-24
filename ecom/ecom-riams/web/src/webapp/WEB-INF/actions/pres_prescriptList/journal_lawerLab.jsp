<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Лаборатория для юридического отдела</msh:title>
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

            String login = request.getParameter("filterAdd2Name");
            if (login != null && !login.equals(""))
                request.setAttribute("login", " and  mc.username=regexp_replace('" + login + "', '\\s', '', 'g')");

            String serviceStream = request.getParameter("serviceStream");
            if (serviceStream != null && !serviceStream.equals(""))
                request.setAttribute("serviceStream", " and sstr.id=" + serviceStream);

            String bedType = request.getParameter("bedType");
            if (bedType != null && !bedType.equals(""))
                request.setAttribute("bedType", " and vbt.id=" + bedType);

            String typeVMP = ActionUtil.updateParameter("typeVMP","typeVMP","1", request) ;
            if (typeVMP!=null && typeVMP.equals("2"))
                request.setAttribute("typeVMPSql", "  and himc.id is not null ");
            else
                request.setAttribute("typeVMPSql", "");

        %>
        <msh:form action="/journal_lawerLab.do" defaultField="dateBegin" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с"/>
                    <msh:textField property="dateEnd" label="по"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="secUserLogin" property="filterAdd2" fieldColSpan="16" label="Логин"
                                      horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocBedType" property="bedType" label="Профиль коек" horizontalFill="true"
                                      fieldColSpan="5"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="serviceStream" fieldColSpan="4" horizontalFill="true" label="Поток обслуживания" vocName="vocServiceStream"/>
                </msh:row>
                <msh:row>
                    <td class="label" title="ВМП  (typeVMP)" colspan="1"><label for="typeVMPName" id="typeVMPLabel">ВМП:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeVMP" value="1">  всё
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeVMP" value="2">  только ВМП
                    </td>
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
                <ecom:webQuery isReportBase="true" name="journal_lawerLab" nativeSql="
                select ms.code,ms.name,count (ms.id) from prescription p
left join medcase mc on mc.id = p.medcase_id
left join prescriptionlist pl on pl.id=p.prescriptionlist_id
left join medcase slo on slo.id = pl.medcase_id
left join  bedfund as bf on bf.id=slo.bedfund_id
left join vocbedtype vbt on vbt.id=bf.bedType_id
left join medservice ms on p.medservice_id=ms.id
left join vocservicestream sstr on sstr.id=slo.servicestream_id
left join HitechMedicalCase himc on  himc.medCase_id = slo.id
 where mc.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
    ${login}
    ${bedType}
    ${serviceStream}
    ${typeVMPSql}
group by ms.code,ms.name"/>
                <msh:table name="journal_lawerLab" noDataMessage="Нет данных" printToExcelButton="Сохранить в excel"
                           action="javascript:void(0) ;" idField="1">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn property="2" columnName="Услуга"/>
                    <msh:tableColumn property="3" columnName="Количество"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <% } %>

        <script type='text/javascript'>
            checkFieldUpdate('typeVMP','${typeVMP}',1) ;
            function checkFieldUpdate(aField,aValue,aDefaultValue) {
                eval('var chk =  document.forms[0].' + aField);
                var max = chk.length;
                aValue = +aValue;
                if (aValue == 0 || (aValue > max)) {
                    aValue = +aDefaultValue
                }
                if (aValue == 0 || (aValue > max)) {
                    chk[max - 1].checked = 'checked';
                } else {
                    chk[aValue - 1].checked = 'checked';
                }
            }
        </script>
    </tiles:put>
</tiles:insert>