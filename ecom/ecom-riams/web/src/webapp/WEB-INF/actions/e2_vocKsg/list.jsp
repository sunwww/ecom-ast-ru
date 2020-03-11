<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Справочник КСГ</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:expertvoc_menu currentAction="e2_vocKsg_st"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <label><input type="text" id="year" name="year"/>Указать год</label><button onclick="showAll()" value="Фильтр"></button>
        <%
        String year = request.getParameter("year");
        request.setAttribute("yearSql","where ksg.year="+(year!=null && !"".equals(year) ? "'"+year+"'" : "cast(to_char(current_date,'yyyy') as int)"));
            %>
        <ecom:webQuery name="entryList" nativeSql="select ksg.id
            ,ksg.code||' '||ksg.name as ksg
            ,ksg.kz as kz
            , ksg.profile
            ,vbst.name
            ,ksg.isoperation
            ,ksg.longKsg
            ,ksg.isFullPayment
             from VocKsg ksg
             left join vocBedSubType vbst on vbst.id=ksg.bedsubtype_id
             ${yearSql}
              order by ksg.year desc, vbst.id, ksg.code "/>
        <msh:section title='Результат поиска'>
            <msh:table  name="entryList" action="entityView-e2_vocKsg.do" idField="1" disableKeySupport="true" styleRow="6">
                <msh:tableColumn columnName="КСГ" property="2" />
                <msh:tableColumn columnName="KZ" property="3" />
                <msh:tableColumn columnName="Профиль" property="4" />
                <msh:tableColumn columnName="Тип коек" property="5" />
                <msh:tableColumn columnName="Операционное КСГ" property="8" />
                <msh:tableColumn columnName="Сверхдлительное КСГ" property="6" />
                <msh:tableColumn columnName="ПРизнак полной оплаты" property="7" />
            </msh:table>
        </msh:section>

    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function showAll() {
                window.location.href+="?year="+$('year').value;
            }
        </script>
            </tiles:put>
</tiles:insert>