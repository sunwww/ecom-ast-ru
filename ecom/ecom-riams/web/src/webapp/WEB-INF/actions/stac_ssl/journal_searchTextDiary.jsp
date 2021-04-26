<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Поиск дневников по тексту</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String fldDate = "sls.dateFinish" ;
            String typeDate = ActionUtil.updateParameter("typeDate","typeDate","2", request) ;
            if (typeDate!=null) {
                if (typeDate.equals("1")) {fldDate="sls.dateStart" ;}
                else if (typeDate.equals("2")) {fldDate="sls.dateFinish" ;}
                else if (typeDate.equals("3")) {fldDate="di.dateregistration" ;}
            }

            String department = request.getParameter("department") ;
            if (department!=null && !department.equals("")) request.setAttribute("department"," and dep.id="+department);
            String date = request.getParameter("dateBegin") ;
            String dateEnd = request.getParameter("dateEnd") ;

            if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
            request.setAttribute("dateBegin", date) ;
            request.setAttribute("dateEnd", dateEnd) ;
            request.setAttribute("fldDate",fldDate);

            String workFunction = request.getParameter("workFunction") ;
            if (workFunction!=null && !workFunction.equals(""))
                request.setAttribute("workFunction"," and wf.id="+workFunction);

            String login = request.getParameter("filterAdd2") ;
            if (login!=null && !login.equals(""))
                request.setAttribute("login"," and su.id="+login);

            String bedType = request.getParameter("bedType") ;
            if (bedType!=null && !bedType.equals(""))
                request.setAttribute("bedType"," and vbt.id="+bedType);

            String hospitalizationResult = request.getParameter("hospitalizationResult") ;
            if (hospitalizationResult!=null && !hospitalizationResult.equals(""))
                request.setAttribute("hospitalizationResult"," and vhr.id="+hospitalizationResult);

            String hospitalizationOutcome = request.getParameter("hospitalizationOutcome") ;
            if (hospitalizationOutcome!=null && !hospitalizationOutcome.equals(""))
                request.setAttribute("hospitalizationOutcome"," and vho.id="+hospitalizationOutcome);

            String diaryLike = request.getParameter("filterAdd") ;
            if (diaryLike!=null && !diaryLike.equals(""))
                request.setAttribute("diaryLike"," and upper(di.record) like upper('%"+diaryLike + "%')");

            String diaryNotLike = request.getParameter("filterAdd1") ;
            if (diaryNotLike!=null && !diaryNotLike.equals(""))
                request.setAttribute("diaryNotLike"," and upper(di.record) not like upper('%"+diaryNotLike + "%')");
        %>
        <msh:form action="/journal_searchTextDiary.do" defaultField="department" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDate" value="1">  поступления
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDate" value="2">  выписки
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDate" value="3">  дате регистрации дневника
                    </td>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="workFunction" fieldColSpan="16" horizontalFill="true" label="Автор дневника" vocName="workFunction"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="secUser" property="filterAdd2"  fieldColSpan="16" label="Логин" horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocBedType" property="bedType" label="Профиль коек" horizontalFill="true" fieldColSpan="5" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="hospitalizationResult" fieldColSpan="16" horizontalFill="true" label="Результат госпитализации" vocName="vocHospitalizationResult"/>
                    <msh:autoComplete property="hospitalizationOutcome" fieldColSpan="16" horizontalFill="true" label="Исход" vocName="vocHospitalizationOutcome"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="filterAdd" label="Содержит текст" />
                </msh:row>
                <msh:row>
                    <msh:textField property="filterAdd1" label="Не содержит текст" />
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (date!=null && !date.equals("")) {
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery isReportBase="true" name="journal_DiaryText" nativeSql="
                select distinct slo.id
                , dep.name as depName
                ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename|| ' ' || to_char(pat.birthday,'dd.mm.yyyy') as patInfo
                ,to_char(di.dateregistration,'dd.mm.yyy') as dD
                from medCase slo
                left join medcase sls on slo.parent_id=sls.id and sls.dtype='HospitalMedCase'
                left join StatisticStub as sc on sc.medCase_id=sls.id
                left join diary di on di.medcase_id =slo.id
                left join bedfund as bf on bf.id=slo.bedfund_id
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join WorkFunction wf on wf.id=di.specialist_id
                left join Patient pat on sls.patient_id = pat.id
                left join Mislpu dep on dep.id=slo.department_id
                left join vochospitalizationoutcome vho on vho.id=sls.outcome_id
                left join vochospitalizationresult vhr on vhr.id=sls.result_id
                left join secuser su on su.login=di.username
                where ${fldDate} between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
                and slo.dtype='DepartmentMedCase'
                ${department}
                ${workFunction}
                ${login}
                ${bedType}
                ${hospitalizationResult}
                ${hospitalizationOutcome}
                ${diaryLike}
                ${diaryNotLike}
                order by dep.name" />
                <msh:table name="journal_DiaryText"  noDataMessage="Нет данных" printToExcelButton="Сохранить в excel" openNewWindow="true"
                           action="entityParentView-stac_slo.do" idField="1">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="2" columnName="Отделение"/>
                    <msh:tableColumn property="3" columnName="Пациент"/>
                    <msh:tableColumn property="4" columnName="Дата"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%    } %>
        <script type='text/javascript'>
        checkFieldUpdate('typeDate','${typeDate}',2) ;
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