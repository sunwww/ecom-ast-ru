<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по ИМТ пациентов со значением меньше 18.5 и больше 30</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/reportIMT.do" defaultField="department" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Результат" colSpan="7"/>
                </msh:row>
            </msh:panel>
        </msh:form>
        <ecom:webQuery name="patList" nativeSql="select p.lastname,p.firstname,p.middlename,st.height,st.weight,st.imt
        from statisticstub st
        left join medcase m on m.id=st.medcase_id
        left join patient p on m.patient_id=p.id
        GROUP BY p.lastname,p.firstname,p.middlename,st.height,st.weight,st.imt
        HAVING st.imt<>0 and (st.imt>30 or st.imt<18.5)"/>
        <msh:table hideTitle="false" styleRow="2" idField="1" name="patList" action="javascript:void(0)">
            <msh:tableColumn columnName="Фамилия" property="1"/>
            <msh:tableColumn columnName="Имя" property="2"/>
            <msh:tableColumn columnName="Отчество" property="3"/>
            <msh:tableColumn columnName="Рос" property="4"/>
            <msh:tableColumn columnName="Вес" property="5"/>
            <msh:tableColumn columnName="Индекс массы тела" property="6"/>
        </msh:table>
    </tiles:put>
</tiles:insert>