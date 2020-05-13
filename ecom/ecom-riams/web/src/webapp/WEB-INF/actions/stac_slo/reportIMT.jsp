<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по ИМТ пациентов</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    <%
        String department = request.getParameter("department") ;
        if (department!=null && !department.equals("")) request.setAttribute("department"," and dep.id="+department);
        String typeDate = ActionUtil.updateParameter("reportIMT","typeDate", "1",request);
        String dateBegin="";
        String dateEnd="";
        if (typeDate!=null) {
            if (typeDate.equals("2")) typeDate = "m.dateFinish";
            else if (typeDate.equals("1")) typeDate = "m.datestart";

            dateBegin = request.getParameter("dateBegin");
            if (dateBegin != null && !dateBegin.equals(""))
                request.setAttribute("dateBegin", " and " + typeDate + " >= to_date('" + dateBegin + "','dd.mm.yyyy') ");
            dateEnd = request.getParameter("dateEnd");
            if (dateEnd != null && !dateEnd.equals(""))
                request.setAttribute("dateEnd", " and " + typeDate + " <= to_date('" + dateEnd + "','dd.mm.yyyy') ");
        }
        String typeInterval = ActionUtil.updateParameter("reportIMT","typeInterval","1",request);
        if (typeInterval!=null) {
            if (typeInterval.equals("1")) request.setAttribute("typeIntervalValue","HAVING st.imt<>0 and (st.imt<18.5 or st.imt>=30)");
            else if(typeInterval.equals("2")) request.setAttribute("typeIntervalValue","HAVING st.imt<>0 and st.imt<18.5");
            else if(typeInterval.equals("3")) request.setAttribute("typeIntervalValue","HAVING st.imt<>0 and st.imt>=30 and st.imt<=39.9");
            else if(typeInterval.equals("4")) request.setAttribute("typeIntervalValue","HAVING st.imt<>0 and st.imt>=40.0");
        }
        else request.setAttribute("typeIntervalValue","");
        String typeDone = ActionUtil.updateParameter("reportIMT","typeDone","1",request);
        if (typeDone!=null) {
            if (typeDone.equals("1")) request.setAttribute("typeDoneValue"," and st.dietdone=true");
            else if(typeDone.equals("2")) request.setAttribute("typeDoneValue"," and (st.dietdone=false or st.dietdone is null)");
            else if(typeDone.equals("3")) request.setAttribute("typeDoneValue"," ");
        }
        else request.setAttribute("typeDoneValue","");
    %>
    <tiles:put name="body" type="string">
        <msh:form action="/reportIMT.do" defaultField="department" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
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
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по промежутку  (typeInterval)" colspan="1"><label for="typeIntervalName" id="typeIntervalLabel">Искать по промежутку:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeInterval" value="1"> Все ИМТ, требующие консультации
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeInterval" value="2"> ИМТ < 18,5
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="4">
                        <input type="radio" name="typeInterval" value="3"> ИМТ от 30,0 до 39,9
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="5">
                        <input type="radio" name="typeInterval" value="4"> ИМТ свыше 40,0
                    </td>
                </msh:row>
                <msh:row>
                    <msh:row>
                        <td class="label" title="Поиск по консультациям  (typeDone)" colspan="1"><label for="typeDoneName" id="typeDoneLabel">Искать по консультациям:</label></td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                            <input type="radio" name="typeDone" value="1">  консультация оказана
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                            <input type="radio" name="typeDone" value="2">  консультация НЕ оказана
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
                            <input type="radio" name="typeDone" value="3">  все
                        </td>
                    </msh:row>
                    <td colspan="3">
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <script type='text/javascript'>
            function find() {
                var frm = document.forms[0];
                frm.target = '';
                frm.action = 'reportIMT.do';
            }
            checkFieldUpdate('typeDate','${typeDate}',1) ;
            checkFieldUpdate('typeInterval','${typeInterval}',1) ;
            checkFieldUpdate('typeDone','${typeDone}',1) ;
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
    <%
        if (request.getParameter("dateBegin")!=null && !dateBegin.equals("") && request.getParameter("typeInterval")!=null  && request.getParameter("typeDone")!=null) {
    %>
        <ecom:webQuery isReportBase="true" name="patList" nativeSql="select distinct m.id,p.lastname||' '||p.firstname||' '||p.middlename,cast(EXTRACT(YEAR FROM p.birthday) as integer),dep.name,st.imt,
 (select list (idc10.code||' '||idc10.name)
from diagnosis d2
 left join medcase m2 on d2.medcase_id=m2.id
        left join vocidc10 idc10 on idc10.id=d2.idc10_id
left join MedCase dmc2 on dmc2.parent_id=m2.id
        where m2.id=m.id  and dmc2.id=dmc.id
        ),
        case when st.dietdone=true then 'Да' else 'Нет' end
        from statisticstub st
        left join medcase m on m.id=st.medcase_id
        left join patient p on m.patient_id=p.id
        left join MedCase dmc on dmc.parent_id=m.id
        left join MisLpu dep on dep.id=m.department_id
        left join diagnosis d on d.medcase_id=m.id
        left join vocidc10 idc on idc.id=d.idc10_id
        where m.dtype='HospitalMedCase' and dmc.dateFinish is not null ${dateBegin} ${dateEnd} ${department}
         GROUP BY p.id,p.lastname,p.firstname,p.middlename,p.birthday,st.imt,dep.name,idc.id,m.id,dmc.id,st.dietdone
        ${typeIntervalValue} ${typeDoneValue}
        order by p.lastname||' '||p.firstname||' '||p.middlename
        "/>
        <msh:table hideTitle="false" styleRow="2" idField="1" name="patList" action="entityParentView-stac_ssl.do" >
            <msh:tableColumn columnName="#" property="sn" />
            <msh:tableColumn columnName="ФИО" property="2" />
            <msh:tableColumn columnName="Год рождения" property="3"/>
            <msh:tableColumn columnName="Отделение" property="4"/>
            <msh:tableColumn columnName="Диагноз" property="6"/>
            <msh:tableColumn columnName="ИМТ" property="5"/>
            <msh:tableColumn columnName="Выполнен?" property="7"/>
            <msh:tableButton property="1" addParam="this" buttonFunction="setDietVisitIsDoneReportIMT" buttonName="Выполнен?" buttonShortName="Вып."/>
        </msh:table>
        <%
            }
        %>
    </tiles:put>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
    <script type='text/javascript'>
        function setDietVisitIsDoneReportIMT(id) {
            HospitalMedCaseService.setDietVisitIsDoneReportIMT(id, {
                    callback: function () {
                        location.reload();
                    }
                }
            );
        }
    </script>
</tiles:insert>