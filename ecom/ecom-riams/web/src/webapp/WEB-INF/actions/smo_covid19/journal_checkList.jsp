<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Чек-листы по степеням тяжести</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeType = ActionUtil.updateParameter("typeType","typeType","1", request) ;

            String selectSql = " cast('Все' as varchar), ",depNSql = "", depidSql = " cast('' as varchar) as str "
                    ,groupSql = "", orderSql = "", tgroupSql = " group by t.str ", torderSql = "";
            if ("1".equals(typeType)) {
                selectSql = " distinct t.depname as depname,";
                depNSql = " dep.name as depname, ";
                depidSql = "'&depId='||coalesce(dep.id,0)||'&depname='||coalesce(dep.name,'') as str ";
                groupSql = " dep.id,dep.name, ";
                orderSql = " order by dep.name ";
                tgroupSql = " group by t.depname,t.str ";
                torderSql = " order by t.depname ";
            }
            request.setAttribute("selectSql",selectSql);
            request.setAttribute("depNSql",depNSql);
            request.setAttribute("depidSql",depidSql);
            request.setAttribute("groupSql",groupSql);
            request.setAttribute("orderSql",orderSql);
            request.setAttribute("tgroupSql",tgroupSql);
            request.setAttribute("torderSql",torderSql);
            String date = request.getParameter("dateBegin"), dateEnd="";
            if (date!=null && !date.equals("")) {
                dateEnd = request.getParameter("dateEnd");

                if (dateEnd == null || dateEnd.equals("")) {
                    dateEnd = date;
                }
            }
            request.setAttribute("dateBegin",date) ;
            request.setAttribute("dateEnd", dateEnd) ;
            request.setAttribute("typeType", typeType) ;
            if (request.getParameter("short")==null) {
        %>
        <msh:form action="/journal_checkList.do" defaultField="department" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по  (typeType)" colspan="1"><label for="typeTypeName" id="typeTypeLabel">Группировать:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeType" value="1">  по отделению
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeType" value="2">  без группировки
                    </td>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            }
            if (request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {
                if (request.getParameter("short")==null) {

        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_checkList" nativeSql="
                 select ${selectSql}
                 sum(t.total) as total
                ,sum(t.cntCard) as cntCard
                ,sum(t.cntNotCard) as cntNotCard
                ,sum(t.cntIT00) as cntIT00
                ,sum(t.cntIT00_10) as cntIT00_10
                ,case when sum(t.cntIT00)=0 then '0.00' else round(100*(cast(sum(t.cntIT00_10) as numeric)/cast(sum(t.cntIT00) as numeric)),2) end as perIT00_10
                ,sum(t.cntIT00_11) as cntIT00_11
                ,case when sum(t.cntIT00)=0 then '0.00' else round(100*(cast(sum(t.cntIT00_11) as numeric)/cast(sum(t.cntIT00) as numeric)),2) end as perIT00_11
                ,sum(t.cntIT23) as cntIT23
                ,sum(t.cntIT23_10) as cntIT23_10
                ,case when sum(t.cntIT23)=0 then '0.00' else round(100*(cast(sum(t.cntIT23_10) as numeric)/cast(sum(t.cntIT23) as numeric)),2) end as perIT23_10
                ,sum(t.cntIT23_11) as cntIT23_11
                ,case when sum(t.cntIT23)=0 then '0.00' else round(100*(cast(sum(t.cntIT23_11) as numeric)/cast(sum(t.cntIT23) as numeric)),2) end as perIT23_11
                ,sum(t.cntIT24) as cntIT24
                ,sum(t.cntIT24_10) as cntIT24_10
                ,case when sum(t.cntIT24)=0 then '0.00' else round(100*(cast(sum(t.cntIT24_10) as numeric)/cast(sum(t.cntIT24) as numeric)),2) end as perIT24_10
                ,sum(t.cntIT24_11) as cntIT24_11
                ,case when sum(t.cntIT24)=0 then '0.00' else round(100*(cast(sum(t.cntIT24_11) as numeric)/cast(sum(t.cntIT24) as numeric)),2) end as perIT24_11
                ,sum(t.cntIT25) as cntIT25
                ,sum(t.cntIT25_10) as cntIT25_10
                ,case when sum(t.cntIT25)=0 then '0.00' else round(100*(cast(sum(t.cntIT25_10) as numeric)/cast(sum(t.cntIT25) as numeric)),2) end as perIT25_10
                ,sum(t.cntIT25_11) as cntIT25_11
                ,case when sum(t.cntIT25)=0 then '0.00' else round(100*(cast(sum(t.cntIT25_11) as numeric)/cast(sum(t.cntIT25) as numeric)),2) end as perIT25_11
                ,t.str as str from (
                select ${depNSql} count (distinct sls.id) as total
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                where slsinner.id=sls.id
                and c.id is not null
                ) as cntCard
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                where slsinner.id=sls.id
                and c.id is null
                ) as cntNotCard,
                ${depidSql}
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                where slsinner.id=sls.id and vs.code='IT00'
                and c.id is not null
                ) as cntIT00
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                left join assessmentcard a on a.medcase_id=slsinner.id
                where slsinner.id=sls.id and vs.code='IT00'
                and c.id is not null and a.template='10'
                ) as cntIT00_10
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                left join assessmentcard a on a.medcase_id=slsinner.id
                where slsinner.id=sls.id and vs.code='IT00'
                and c.id is not null and a.template='11'
                ) as cntIT00_11
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                where slsinner.id=sls.id and vs.code='IT23'
                and c.id is not null
                ) as cntIT23
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                left join assessmentcard a on a.medcase_id=slsinner.id
                where slsinner.id=sls.id and vs.code='IT23'
                and c.id is not null and a.template='10'
                ) as cntIT23_10
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                left join assessmentcard a on a.medcase_id=slsinner.id
                where slsinner.id=sls.id and vs.code='IT23'
                and c.id is not null and a.template='11'
                ) as cntIT23_11
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                where slsinner.id=sls.id and vs.code='IT24'
                and c.id is not null
                ) as cntIT24
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                left join assessmentcard a on a.medcase_id=slsinner.id
                where slsinner.id=sls.id and vs.code='IT24'
                and c.id is not null and a.template='10'
                ) as cntIT24_10
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                left join assessmentcard a on a.medcase_id=slsinner.id
                where slsinner.id=sls.id and vs.code='IT24'
                and c.id is not null and a.template='11'
                ) as cntIT24_11
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                where slsinner.id=sls.id and vs.code='IT25'
                and c.id is not null
                ) as cntIT25
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                left join assessmentcard a on a.medcase_id=slsinner.id
                where slsinner.id=sls.id and vs.code='IT25'
                and c.id is not null and a.template='10'
                ) as cntIT25_10
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                left join vocsost vs on vs.id=c.sost_id
                left join assessmentcard a on a.medcase_id=slsinner.id
                where slsinner.id=sls.id and vs.code='IT25'
                and c.id is not null and a.template='11'
                ) as cntIT25_11
                from medCase sls
                left join CovidMark c on sls.id=c.medcase_id
                left join MedCase sloa on sloa.parent_id=sls.id
                left join Medcase prevmc on prevmc.id=sloa.prevmedcase_id
                left join mislpu dep on case when sloa.department_id=501 then dep.id=prevmc.department_id else dep.id=sloa.department_id end
                left join MedCase as m on sls.id = m.parent_id
                left join diagnosis diag on diag.medcase_id=sls.id or diag.medcase_id=m.id
                left join vocidc10 mkb on mkb.id=diag.idc10_id
                left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
                left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
                left join ReportSetTYpeParameterType rspt on mkb.code between rspt.codefrom and rspt.codeto
                left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
                left join ReportSetTYpeParameterType rspt1 on mkb.code between rspt1.codefrom and rspt1.codeto
                left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id
                left join vocsost vs on vs.id=c.sost_id
                where m.DTYPE='DepartmentMedCase'
                and vrspt.id='523' and vdrt.id='3' and vpd.id='1'
                and sls.datefinish between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
           		and sloa.datefinish is not null and sloa.DTYPE='DepartmentMedCase'
                group by ${groupSql}
                sls.id
                ${orderSql}) as t
                ${tgroupSql}
                ${torderSql}" />
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_checkList"  noDataMessage="Нет данных"
                           action="journal_checkList.do?&short=Short&typeType=${param.typeType}&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"
                           idField="25" cellFunction="true">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="1" columnName="Подразделение" addParam="&type=total"/>
                    <msh:tableColumn property="2" isCalcAmount="true" columnName="Всего пациентов" addParam="&type=total"/>
                    <msh:tableColumn property="3" isCalcAmount="true" columnName="Форм создано" addParam="&type=create"/>
                    <msh:tableColumn property="4" isCalcAmount="true" columnName="Форм не создано" addParam="&type=notCreate"/>
                    <msh:tableColumn property="5" isCalcAmount="true" columnName="Лёгкое" addParam="&type=IT00"/>
                    <msh:tableColumn property="6" isCalcAmount="true" columnName="I" addParam="&type=IT00_10"/>
                    <msh:tableColumn property="7" columnName="I%" addParam="&type=IT00_10"/>
                    <msh:tableColumn property="8" isCalcAmount="true" columnName="II" addParam="&type=IT00_11"/>
                    <msh:tableColumn property="9" columnName="II%" addParam="&type=IT00_11"/>
                    <msh:tableColumn property="10" isCalcAmount="true" columnName="Среднетяжёлое" addParam="&type=IT23"/>
                    <msh:tableColumn property="11" isCalcAmount="true" columnName="I" addParam="&type=IT23_10"/>
                    <msh:tableColumn property="12" columnName="I%" addParam="&type=IT23_10"/>
                    <msh:tableColumn property="13" isCalcAmount="true" columnName="II" addParam="&type=IT23_11"/>
                    <msh:tableColumn property="14" columnName="II%" addParam="&type=IT23_11"/>
                    <msh:tableColumn property="15" isCalcAmount="true" columnName="Тяжёлое" addParam="&type=IT24"/>
                    <msh:tableColumn property="16" isCalcAmount="true" columnName="I" addParam="&type=IT24_10"/>
                    <msh:tableColumn property="17" columnName="I%" addParam="&type=IT24_10"/>
                    <msh:tableColumn property="18" isCalcAmount="true" columnName="II" addParam="&type=IT24_11"/>
                    <msh:tableColumn property="19" columnName="II%" addParam="&type=IT24_11"/>
                    <msh:tableColumn property="20" isCalcAmount="true" columnName="Крайнетяжёлое" addParam="&type=IT25"/>
                    <msh:tableColumn property="21" isCalcAmount="true" columnName="I" addParam="&type=IT25_10"/>
                    <msh:tableColumn property="22" columnName="I%" addParam="&type=IT25_10"/>
                    <msh:tableColumn property="23" isCalcAmount="true" columnName="II" addParam="&type=IT25_11"/>
                    <msh:tableColumn property="24" columnName="II%" addParam="&type=IT25_11"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%    }
        else if (request.getParameter("short")==null || "2".equals(typeType)) {
            String type = request.getParameter("type");
            String sqlAdd="";
            if (type!=null) {
                if ("create".equals(type))
                    sqlAdd = " and c.id is not null";
                else if ("notCreate".equals(type))
                    sqlAdd = " and c.id is null";
                else if ("IT00".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT00'";
                else if ("IT23".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT23'";
                else if ("IT24".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT24'";
                else if ("IT25".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT25'";
                else if ("IT00_10".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT00' and a.template='10'";
                else if ("IT00_11".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT00' and a.template='11'";
                else if ("IT23_10".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT23' and a.template='10'";
                else if ("IT23_11".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT23' and a.template='11'";
                else if ("IT24_10".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT24' and a.template='10'";
                else if ("IT24_11".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT24' and a.template='11'";
                else if ("IT25_10".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT25' and a.template='10'";
                else if ("IT25_11".equals(type))
                    sqlAdd = " and c.id is not null and vs.code='IT25' and a.template='11'";
                request.setAttribute("sqlAdd",sqlAdd);
                String depId = request.getParameter("depId");
                String depSql = depId!=null?
                        "and dep.id = " + depId : "";
                request.setAttribute("depSql",depSql);
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_checkListPat" nativeSql="
                select distinct sls.id,dep.name as depname, st.code as stc,pat.patientinfo as info
                , vs.name as vsname, vhr.name as vhrname
                ,getChosenCovidFormPars(c.id) as pars
                 from medCase m
                left join MedCase as sls on sls.id = m.parent_id
                left join Patient pat on m.patient_id = pat.id
                left join CovidMark c on sls.id=c.medcase_id
                left join MedCase sloa on sloa.parent_id=sls.id
                left join Medcase prevmc on prevmc.id=sloa.prevmedcase_id
                left join mislpu dep on case when sloa.department_id=501 then dep.id=prevmc.department_id else dep.id=sloa.department_id end
                left join diagnosis diag on diag.medcase_id=sls.id or diag.medcase_id=m.id
                left join vocidc10 mkb on mkb.id=diag.idc10_id
                left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
                left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
                left join ReportSetTYpeParameterType rspt on mkb.code between rspt.codefrom and rspt.codeto
                left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
                left join ReportSetTYpeParameterType rspt1 on mkb.code between rspt1.codefrom and rspt1.codeto
                left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id
                left join statisticstub st on st.medcase_id=sls.id
                left join vocsost vs on vs.id=c.sost_id
                left join vochospitalizationresult vhr on vhr.id=sls.result_id
                left join assessmentcard a on a.medcase_id=sls.id
                where m.DTYPE='DepartmentMedCase'
                and vrspt.id='523' and vdrt.id='3' and vpd.id='1'
                and sls.datefinish between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                ${sqlAdd}
                ${depSql}
                and sloa.datefinish is not null and sloa.DTYPE='DepartmentMedCase'
                group by sls.id,dep.name, st.code,pat.patientinfo, vs.name, mkb.code, vhr.name, c.id
                order by dep.name,pat.patientinfo" />
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_checkListPat"  noDataMessage="Нет данных"
                           action="entityParentView-stac_ssl.do" idField="1" openNewWindow="true">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="4" columnName="Пациент"/>
                    <msh:tableColumn property="3" columnName="ИБ"/>
                    <msh:tableColumn property="2" columnName="Отделение"/>
                    <msh:tableColumn property="5" columnName="Степень тяжести COVID-19"/>
                    <msh:tableColumn property="6" columnName="Результат госпитализации"/>
                    <msh:tableColumn property="7" columnName="Выбранные параметры"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%} } }%>
        <script type='text/javascript'>
            checkFieldUpdate('typeType','${typeType}',1) ;
            function checkFieldUpdate(aField,aValue,aDefaultValue) {
                eval('var chk =  document.forms[0].'+aField) ;
                var aMax=chk.length ;
                if ((+aValue)==0 || (+aValue)>(+aMax)) {
                    chk[+aDefaultValue-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
        </script>
    </tiles:put>
</tiles:insert>