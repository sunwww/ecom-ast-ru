<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Отчёт о проведении кардиоскрининга новорождённых</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
    <%
        String typeDate = ActionUtil.updateParameter("journal_cardiacScreening","typeDate", "1",request);
        String dateBegin="";
        String dateEnd="";
        if (typeDate!=null) {
            if (typeDate.equals("2")) typeDate = "sls.dateFinish";
            else if (typeDate.equals("1")) typeDate = "sls.datestart";

            dateBegin = request.getParameter("dateBegin");
            if (dateBegin != null && !dateBegin.equals(""))
                request.setAttribute("dateBegin", " and " + typeDate + " >= to_date('" + dateBegin + "','dd.mm.yyyy') ");
            dateEnd = request.getParameter("dateEnd");
            if (dateEnd==null || dateEnd.equals("")) dateEnd=dateBegin;
            if (dateEnd != null && !dateEnd.equals(""))
                request.setAttribute("dateEnd", " and " + typeDate + " <= to_date('" + dateEnd + "','dd.mm.yyyy') ");
            request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
            request.setAttribute("period", "с " + dateBegin + " по " + dateEnd);
        }
        if (request.getParameter("short")==null) {
    %>
        <msh:form action="/journal_cardiacScreening.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeDate" value="1">  поступления
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeDate" value="2">  выписки
                    </td>
                </msh:row>
                <msh:row>
                    <td colspan="3">
                        <input type="button" onclick="find()" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (request.getParameter("dateBegin")!=null && !dateBegin.equals("")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="true" name="patList" nativeSql="
        select sum(t.slsid) as slsid,
        sum(t.i) as i,sum(t.ii) as ii, sum(t.iii) as iii from
        (
        select count(distinct sls.id) as slsid
        ,case when scrI.medcase_id is not null then 1 else 0 end as i
        ,case when scrII.medcase_id is not null then 1 else 0 end as ii
        ,case when scrI.medcase_id is not null and scrII.medcase_id is not null then 1 else 0 end as iii
        from medcase sls
        left join mislpu lpu on lpu.id=sls.department_id
        left join medcase slo on slo.parent_id=sls.id
        left join medcase allslo on allslo.parent_id=sls.id
        left join mislpu lpuslo on lpuslo.id=slo.department_id
        left join screeningcardiac scrI on scrI.medcase_id=slo.id and scrI.dtype='ScreeningCardiacFirst'
        left join screeningcardiac scrII on scrII.medcase_id=slo.id and scrII.dtype='ScreeningCardiacSecond'
        where lpu.IsCreateCardiacScreening=true  and lpuslo.IsCreateCardiacScreening=true
        and sls.dtype='HospitalMedCase'  and slo.dtype='DepartmentMedCase'  and allslo.dtype='DepartmentMedCase'
        ${dateBegin} ${dateEnd}
        group by sls.id,scrI.medcase_id,scrII.medcase_id
        having count(distinct allslo.id)=1
        ) as t"/>
                <form action="journal_cardiacScreening.do" method="post" target="_blank">
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
        <msh:table hideTitle="false" idField="1" name="patList" cellFunction="true" action="journal_cardiacScreening.do">
            <msh:tableColumn columnName="Всего в отделении новорождённых" property="1" addParam="&short=Short&view=total&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&period=${param.period}"/>
            <msh:tableColumn columnName="Сделан I этап" property="2" addParam="&short=Short&view=I&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&period=${param.period}"/>
            <msh:tableColumn columnName="Сделан II этап" property="3" addParam="&short=Short&view=II&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&period=${param.period}"/>
            <msh:tableColumn columnName="Сделаны оба этапа" property="4" addParam="&short=Short&view=все&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&period=${param.period}"/>
        </msh:table>
        </msh:sectionContent>
        </msh:section>
        <%
            }
        %>
        <script type='text/javascript'>
            function find() {
                var frm = document.forms[0];
                frm.target = '';
                frm.action = 'journal_cardiacScreening.do';
                frm.submit();
            }
            checkFieldUpdate('typeDate','${typeDate}',1) ;
            function checkFieldUpdate(aField,aValue,aDefaultValue) {
                eval('var chk =  document.forms[0].'+aField) ;
                var aMax=chk.length ;
                //alert(aField+" "+aValue+" "+aMax+" "+chk) ;
                if ((+aValue)==0 || (+aValue)>(+aMax)) {
                    chk[+aDefaultValue-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
        </script>
        <%
        } else {
            String view = (String)request.getParameter("view") ;
            request.setAttribute("view", view);
            if (view.equals("total")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="total" nameFldSql="total_sql" nativeSql="
                select distinct slo.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
                from medcase sls
                left join mislpu lpu on lpu.id=sls.department_id
                left join medcase slo on slo.parent_id=sls.id
                left join medcase allslo on allslo.parent_id=sls.id
                left join mislpu lpuslo on lpuslo.id=slo.department_id
                left join patient pat on sls.patient_id=pat.id
                where lpu.IsCreateCardiacScreening=true and lpuslo.IsCreateCardiacScreening=true
                 and sls.dtype='HospitalMedCase' and slo.dtype='DepartmentMedCase' and allslo.dtype='DepartmentMedCase'
                ${dateBegin} ${dateEnd}
                group by slo.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
                having count(distinct allslo.id)=1"/>
                <form action="journal_cardiacScreening.do" method="post" target="_blank">
                    Всего пациентов за период ${period} в ОТДЕЛЕНИИ НОВОРОЖДЁННЫХ
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="total"
                           viewUrl="journal_cardiacScreening.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            else  if (view.equals("I") || view.equals("II") || view.equals("все")) {
                String whereScr=view.equals("I")? " and scrI.medcase_id is not null" :
                        view.equals("II")? " and scrII.medcase_id is not null":
                                view.equals("все")? " and scrI.medcase_id is not null and scrII.medcase_id is not null ":"";
                request.setAttribute("whereScr", whereScr);
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="${isReportBase}" name="i" nameFldSql="i_sql" nativeSql="
                select distinct slo.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
                from medcase sls
                left join mislpu lpu on lpu.id=sls.department_id
                left join medcase slo on slo.parent_id=sls.id
                left join medcase allslo on allslo.parent_id=sls.id
                left join mislpu lpuslo on lpuslo.id=slo.department_id
                left join patient pat on sls.patient_id=pat.id
                left join screeningcardiac scrI on scrI.medcase_id=slo.id and scrI.dtype='ScreeningCardiacFirst'
                left join screeningcardiac scrII on scrII.medcase_id=slo.id and scrII.dtype='ScreeningCardiacSecond'
                where lpu.IsCreateCardiacScreening=true and lpuslo.IsCreateCardiacScreening=true
                and sls.dtype='HospitalMedCase' and slo.dtype='DepartmentMedCase' and allslo.dtype='DepartmentMedCase'
                ${dateBegin} ${dateEnd}
                ${whereScr}
                group by slo.id,pat.lastname||' '||pat.firstname||' '||pat.middlename
                having count(distinct allslo.id)=1"/>
                <form action="journal_cardiacScreening.do" method="post" target="_blank">
                    Пациенты за период ${period} в ОТДЕЛЕНИИ НОВОРОЖДЁННЫХ (этап скрининга: ${view})
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="i"
                           viewUrl="journal_cardiacScreening.do" openNewWindow="true"
                           action="entityView-stac_slo.do" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="ФИО" property="2" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
        %>
        <%
            }
        %>
    </tiles:put>
</tiles:insert>