<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoView-mis_licence.do" defaultField="numberDoc">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="lpu"/>

            <msh:panel>
                <msh:separator colSpan="4" label="Право на виды деятельности"/>
                <msh:row>
                    <msh:textField property="numberDoc" label="Номер"  horizontalFill="false" size="20" fieldColSpan="3"/>
                </msh:row>
                 <msh:row>
                    <msh:textField property="vidal" label='Кто выдал:' horizontalFill="true" fieldColSpan="3" size="50"/>
                </msh:row>
                 <msh:row>
                    <msh:autoComplete  property="typeWork" label="Вид Деятельности"  vocName="vocTypeWork" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>

                <msh:separator colSpan="4" label="Период действия"/>
                <msh:row>
                    <msh:textField property="dateStart" label="С" />
                    <msh:textField property="dateFinish" label="ПО"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateVidal" label="Дата выдачи" />
                     <msh:autoComplete property="status" label='Статус:' vocName="vocStatus"/>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="6"/>
            </msh:panel>
        </msh:form>

        <msh:ifFormTypeIsView formName="mis_licenceForm">
        <msh:section title = "Список приложений">
                      <ecom:parentEntityListAll formName="mis_prilogenieForm" attribute="pril"/>
                        <msh:table name="pril" action="entityParentView-mis_prilogenie.do" idField="id">
                            <msh:tableColumn columnName="Номер" property="numberDoc"/>
                            <msh:tableColumn columnName="Вид" property="nameTypeWork"/>
                            <msh:tableColumn columnName="Подразделение" property="nameLpu"/>
                        </msh:table>
         </msh:section>
         </msh:ifFormTypeIsView>

  </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_licenceForm">
                <%--<msh:sideLink key="ALT+2" params="id" roles="/Policy/Mis/MisLpu/View" action="/entityView-mis_lpu" name="⇧ К ЛПУ"/>--%>
                <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/Licence/Edit"  action="/entityEdit-mis_licence" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_licenceForm">

                <%--<msh:sideLink key='ALT+4' params="id" roles="/Policy/Mis/Document/Create" action="/entityParentPrepareCreate-mis_licence" name="Добавить лицензию" title="Добавить лицензию"/>--%>

                <msh:sideLink key='ALT+5' params="id" roles="/Policy/Mis/Prilogenie/Create" action="/entityParentPrepareCreate-mis_prilogenie" name="Добавить приложение" title="Добавить приложение"/>

                <msh:sideLink key='ALT+DEL' params="id" roles="/Policy/Mis/Licence/Delete" action="/entityParentDeleteGoParentView-mis_licence" name="Удалить" confirm="Удалить?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Lpu" beginForm="mis_licenceForm" />
    </tiles:put>

</tiles:insert>