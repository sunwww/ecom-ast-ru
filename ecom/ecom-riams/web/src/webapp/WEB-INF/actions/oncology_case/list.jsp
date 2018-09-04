<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title mainMenu="Patient">случаи ЗНО</msh:title>
    <%--    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="ily_MedCaseForm" />--%>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:sideMenu>
            <msh:sideLink roles="/Policy/Mis/Oncology/Direction/Create" key="ALT+N" action="/entityParentPrepareCreate-oncology_case.do" name="Заполнить случай ЗНО" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <ecom:webQuery name="onccase" nativeSql="
        select o.id,
        n002.name as stad,
        n003.name as tumor,
        n004.name as nodus,
        n005.name as metas
        from oncologycase o
        left join voconcologyn002 n002 on n002.id= o.stad_id
        left join voconcologyn003 n003 on n003.id=o.tumor_id
        left join voconcologyn004 n004 on n004.id =o.nodus_id
        left join voconcologyn005 n005 on n005.id = o.metastasis_id"/>
        <msh:tableNotEmpty name="onccase" >
            <msh:table  name="onccase" action="entityParentView-oncology_case.do" idField="1" >
                <msh:tableColumn columnName="#" property="sn" />
                <msh:tableColumn columnName="Стадия" property="2" />
                <msh:tableColumn columnName="Tumor" property="3" />
                <msh:tableColumn columnName="Nodus" property="4" />
                <msh:tableColumn columnName="Metastasis" property="5" />
            </msh:table>
        </msh:tableNotEmpty>
    </tiles:put>
</tiles:insert>

