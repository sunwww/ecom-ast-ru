<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Отчёт по нозологиям в акушерстве</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/preg_nosology.do" defaultField="dateBegin" disableFormDataConfirm="true" method="POST">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с"/>
                    <msh:textField property="dateEnd" label="по"/>
                </msh:row>
                <msh:row>
                    <td class="label" title="Показать  (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel"></label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup" value="1" checked> все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="2"> выписанные
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup" value="3"> состоящие
                    </td>
                </msh:row>
                <msh:row>
                    <td colspan="3">
                        <input type="button" onclick="this.disabled=true;find();" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            StringBuilder typeSql=new StringBuilder();
            String type = (String)request.getParameter("typeGroup");
            if (type==null || type.equals("")) type="1";
            if (type!=null && !type.equals("")) {
                if (type.equals("2")) typeSql.append(" and hmc.datefinish is not null ");
                if (type.equals("3")) typeSql.append(" and hmc.datefinish is null ");
            }
            String dateBegin = request.getParameter("dateBegin") ;
            if (dateBegin!=null && !dateBegin.equals("")) {
                String dateEnd = request.getParameter("dateEnd") ;
                if (dateEnd==null || dateEnd.equals("")) dateEnd=dateBegin;
                if (dateEnd!=null && !dateEnd.equals("")) {
                    typeSql.append(" and hmc.dateStart between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('" + dateEnd+"','dd.mm.yyyy') ");
                }
            }
            String department = request.getParameter("department") ;
            if (department!=null && !department.equals("")) request.setAttribute("department"," and dep.id="+department);
            request.setAttribute("typeSql", typeSql.toString());
            request.setAttribute("typeGroup",type);
            if (dateBegin!=null) {
        %>
        <msh:section>
            <msh:sectionTitle>Сведения о нозологиях в акушерстве
                <ecom:webQuery isReportBase="false" name="totalName" nameFldSql="totalName_sql" nativeSql="
select hmc.id
,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as fio
,to_char(pat.birthday,'dd.mm.yyyy') as bd
,to_char(c.createdate,'dd.mm.yyyy') as cd
,vwf.name||' '||wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as w
,to_char(c.editdate,'dd.mm.yyyy') as ed
,vwf2.name||' '||wpat2.lastname||' '||wpat2.firstname||' '||wpat2.middlename as w2
from birthnosologycard c
left join medcase hmc on hmc.id=c.medcase_id
left join patient pat on pat.id=hmc.patient_id
left join workfunction wf on wf.id=c.creator_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
left join worker w on w.id=wf.worker_id
left join patient wpat on wpat.id=w.person_id
left join workfunction wf2 on wf2.id=c.editor_id
left join vocworkfunction vwf2 on vwf2.id=wf2.workfunction_id
left join worker w2 on w2.id=wf2.worker_id
left join patient wpat2 on wpat2.id=w2.person_id
where 1=1  ${typeSql} "/>
                <form action="javascript:void(0)" method="post" target="_blank"></form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="totalName" action="entityParentView-stac_ssl.do" idField="1">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="ФИО пацииента" property="2"/>
                    <msh:tableColumn columnName="Число, месяц, год рождения" property="3"/>
                    <msh:tableColumn columnName="Дата создания" property="4"/>
                    <msh:tableColumn columnName="Создал" property="5"/>
                    <msh:tableColumn columnName="Дата редактирования" property="6"/>
                    <msh:tableColumn columnName="Отредактировал" property="7"/>
                    <msh:tableButton buttonFunction="showbirthNosology" property="1" buttonShortName="Нозология"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%}%>
        <tags:birthNosologyCard  name="birthNosologyCard"  />
        <script type="text/javascript">
            checkFieldUpdate('typeGroup','${typeGroup}',1) ;
            function checkFieldUpdate(aField,aValue,aDefault) {
                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)==0 || (+aValue)>(+max)) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }

            function find() {
                var frm = document.forms[0];
                frm.submit();
            }

            function showbirthNosology(aSlsId) {
                showbirthNosologyCard(aSlsId,null,true);
            }
        </script>
    </tiles:put>
</tiles:insert>