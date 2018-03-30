<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">V015 - Классификатор медицинских специальностей</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" guid="fdcda21a-c1c6-4e0e-a74e-1bf843a8c1c8">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocFondV015" name="Создать" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="listAll" nativeSql="select voc.id, voc.code, voc.name, voc.parent, list(vp.code||' '||vp.name) from voce2fondv015 voc
  left join e2fondmedspeclink link on link.medspec_id=voc.id left join vocpost vp on vp.code=link.medosworkfunction group by voc.id, voc.code, voc.name"/>
                <msh:table  name="listAll" action="entityView-e2_vocFondV015.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="Название" property="3" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Код"  property="2" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Привязки"  property="5" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>