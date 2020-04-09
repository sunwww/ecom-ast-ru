<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Справочник видов динамических документов</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    <msh:sideMenu title="Добавить" >
      <msh:sideLink  params="name" key="ALT+N" action="/entityPrepareCreate-voc_dynamicDocument" name="Справочник видов динамических документов" />
    </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:section title='Список'>
            	<ecom:webQuery name="dynamicDocuments" nativeSql="select voc.id as dtid, voc.code as dtcode, voc.name as dtname
            	 from VocDynamicDocument voc"/>
                <msh:table name="dynamicDocuments" action="entityView-voc_dynamicDocument.do" idField="1" disableKeySupport="false">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Код" property="2"/>
                    <msh:tableColumn columnName="Наименование" property="3"/>
                </msh:table>
            </msh:section>
    </tiles:put>
</tiles:insert>