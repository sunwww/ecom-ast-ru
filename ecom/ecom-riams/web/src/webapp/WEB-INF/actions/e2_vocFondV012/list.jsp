<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">V012 - Исход случая</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocFondV012" name="Создать" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="e2_vocFondV012_st"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="listAll" nativeSql="select voc.id, voc.code, voc.name, voc.usl, list('Результат:'||vp.code||' '||vp.name) from voce2fondv012 voc
  left join E2FondIshodLink link on link.ishod_id=voc.id left join VocHospitalizationResult vp on vp.code=link.MedosHospResult group by voc.id, voc.code, voc.name, voc.usl
    order by voc.code "/>
                <msh:table  name="listAll" action="entityView-e2_vocFondV012.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="Название" property="3" />
                    <msh:tableColumn columnName="Код"  property="2" />
                    <msh:tableColumn columnName="Тип коек (дн, кругл)"  property="4" />
                    <msh:tableColumn columnName="Привязки"  property="5" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>