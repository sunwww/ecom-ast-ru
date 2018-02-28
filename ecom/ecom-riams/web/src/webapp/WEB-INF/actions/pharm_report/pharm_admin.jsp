<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="body" type="string">
    </tiles:put>


    <tiles:put name="side" type="string">
    <msh:sideMenu title="Администрирование">
        <msh:sideLink styleId="viewShort" action="/entityList-pharmnet_complect" name="Комплекты" roles="/Policy/Mis/Directory/Department" title="Перейти к списку отделений"/>
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='Инвентаризация' title="Просмотр визитов по пациенту" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='Склады' title="Просмотр визитов по пациенту" params="" roles="/Policy/Mis/MedCase/Visit/View" />
        <msh:sideLink styleId="viewShort" action="/javascript:viewOtherVisitsByPatient('.do')" name='Комплекты' title="Просмотр визитов по пациенту" params="" roles="/Policy/Mis/MedCase/Visit/View" />
    </msh:sideMenu>
    </tiles:put>

    <tiles:put name="body" type="string">

        <ecom:webQuery name="operations" nativeSql="
                select idoc.medcaseid
                ,vdt.name
                ,vb.branch
                ,vg.drug||' ('||vg.form||')' as drugname
                ,cast(idoc.amount as numeric)
                ,to_char(dateoperation,'dd.MM.yyyy')||' '||to_char(idoc.timeoperation,'HH24:MI:SS') as dt
                ,idoc.username
                from internaldocument idoc
                left join vocdocumenttype vdt on vdt.code = cast(idoc.typedocument as text)
                left join vocgoods vg on vg.regid = idoc.goodid
                left join pharmnetstorage ps on ps.id = idoc.storageid
                left join vocbranch vb on vb.branchid = ps.branchid"/>
        <msh:tableNotEmpty name="operations" >
            <msh:table  name="operations" action="entityParentView-smo_visit.do" idField="1" >
                <msh:tableColumn columnName="#" property="sn" />
                <msh:tableColumn columnName="Операция" property="2" />
                <msh:tableColumn columnName="Кабинет" property="3" />
                <msh:tableColumn columnName="Наименование" property="4" />
                <msh:tableColumn columnName="Кол-во" property="5" />
                <msh:tableColumn columnName="Дата операции" property="6" />
                <msh:tableColumn columnName="Пользователь" property="7" />
            </msh:table>
        </msh:tableNotEmpty>

    </tiles:put>
    <script type="text/javascript" src="./dwr/interface/PharmnetService.js"></script>


    <script language="javascript" type="text/javascript">
    </script>
</tiles:insert>

