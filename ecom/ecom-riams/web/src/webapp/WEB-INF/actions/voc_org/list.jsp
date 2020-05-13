<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Поиск организаций</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    <msh:sideMenu title="Добавить" >
      <msh:sideLink key="ALT+N" action="/entityPrepareCreate-voc_org" name="Организацию" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="org"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/voc_orgs.do" defaultField="fondNumber" method="GET">
                <msh:panel>
                    <msh:row>
                        <msh:textField property="fondNumber" label="Новый код ФОНДА" size="30"/>
                        <msh:textField property="oldFondNumber" label="Старый код ФОНДА" size="30"/>
                   </msh:row><msh:row>
                        <msh:textField property="name" label="Наименование" fieldColSpan="3" horizontalFill="true"/>
                    </msh:row>
                    <msh:row>
                         <td colspan=3 align="right"><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            
        <msh:hideException>
            <msh:section title='Результат поиска'>
                <msh:table name="list" action="entityView-voc_org.do" idField="id" disableKeySupport="true">
                    <msh:tableColumn columnName="Новый код ФОНДА" property="fondNumber"/>
                    <msh:tableColumn columnName="Старый код ФОНДА" property="oldFondNumber"/>
                    <msh:tableColumn columnName="Наименование" property="name"/>
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>

</tiles:insert>