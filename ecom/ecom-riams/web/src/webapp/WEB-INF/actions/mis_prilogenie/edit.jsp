<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

     <tiles:put name='body' type='string'>
        <msh:form action="entitySaveGoView-mis_prilogenie.do" defaultField="numberDoc">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="licence"/>

             <msh:panel>

                 <msh:row>
                    <msh:textField property="numberDoc" label="Номер приложения" horizontalFill="false" size="20"/>
                </msh:row>
                 <msh:row>
                    <msh:autoComplete property="lpu" label='Подразделение:' vocName="lpu" horizontalFill="true" size='50'/>
                </msh:row>
                 <msh:row>
	                 <ecom:oneToManyOneAutocomplete vocName="vocTypeWork" label="Вид Деятельности" property="typeWorks" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="2"/>
          </msh:panel>
        </msh:form>

         <%--<msh:ifFormTypeIsView formName="mis_prilogenieForm">--%>
        <%--<msh:section title = "Список видов деятельности">--%>
                      <%--<ecom:parentEntityListAll formName="mis_prilogenieForm" attribute="pril"/>--%>
                        <%--<msh:table name="pril" action="entityParentView-mis_prilogenie.do" idField="id">--%>
                            <%--<msh:tableColumn columnName="Вид Деятельности" property="nameTypeWork"/>--%>
                        <%--</msh:table>--%>
         <%--</msh:section>--%>
         <%--</msh:ifFormTypeIsView>--%>

  </tiles:put>

         <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_prilogenieForm">
                <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/Prilogenie/Edit" action="/entityEdit-mis_prilogenie" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_prilogenieForm">
                <msh:sideLink key='ALT+DEL' params="id" roles="/Policy/Mis/Plirogenie/Delete" action="/entityParentDelete-mis_prilogenie" name="Удалить приложение" confirm="Удалить приложение?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_prilogenieForm" />
    </tiles:put>

</tiles:insert>