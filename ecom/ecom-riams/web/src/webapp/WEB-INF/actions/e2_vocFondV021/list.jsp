<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Медицинская специальность V021 </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocFondV021" name="Создать (не надо нажимать)" roles="/Policy/E2/Create" />
            <msh:sideLink key="ALT+2"  action="/entityList-e2_vocFondV021.do?typeFilter=all&" name="Показать все" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>
<%
    String typeFilter = request.getParameter("typeFilter");
    if ("all".equals(typeFilter)) {
        typeFilter=" ";
    } else {
        typeFilter=" where voc.isNoActual='0'";
    }
    request.setAttribute("filterSql",typeFilter);
%>
    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="listAll" nativeSql="select voc.id, voc.code, voc.name
                ,vms1.code||' '||vms1.name as defaultCode1 ,vms2.code||' '||vms2.name as defaultCode2
                 from voce2fondv021 voc
                 left join vocmedservice vms1 on vms1.id=voc.defaultMedService_id
                 left join vocmedservice vms2 on vms2.id=voc.repeatMedService_id
                 ${filterSql} order by cast(voc.code as int)"/>
                <msh:table  name="listAll" action="entityView-e2_vocFondV021.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="Код"  property="2" />
                    <msh:tableColumn columnName="Название" property="3" />
                    <msh:tableColumn columnName="Услуга первичная" property="4" />
                    <msh:tableColumn columnName="Услуга повторная" property="5" />

                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>