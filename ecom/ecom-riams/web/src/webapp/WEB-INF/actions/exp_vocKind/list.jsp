<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Список видов экспертиз</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    <msh:sideMenu title="Добавить" >
      <msh:sideLink key="ALT+N" action="/entityPrepareCreate-exp_vocKind" name="Вид экспертизы" title="Добавить вид экспертизы"/>
    </msh:sideMenu>
    <tags:voc_menu currentAction="exp_voc_kind"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:section title='Список видов экспертиз'>
                <msh:table name="list" action="entityView-exp_vocKind.do" idField="id">
                    <msh:tableColumn columnName="Код" property="code"/>
                    <msh:tableColumn columnName="Наименование" property="name"/>
                </msh:table>
            </msh:section>
    </tiles:put>

</tiles:insert>