<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
    <msh:form action="entitySaveGoView-diary_templateProtocolType" defaultField="name">
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>


        <!--<input type="hidden" name="regionPk" value="" id="regionPk">-->
        <!--<input type="hidden" name="flatNumber" value="" id="flatNumber">-->
        <!--<input type="hidden" name="houseBuilding" value="" id="houseBuilding">-->
        <!--<input type="hidden" name="houseNumber" value="" id="houseNumber">-->


        <msh:panel>
            <msh:ifFormTypeIsNotView formName="diary_templateProtocolTypeForm" >
                <msh:row>
                    <msh:textField property="name" label="Наименование типа" horizontalFill="true"/>
                </msh:row>
            </msh:ifFormTypeIsNotView>

            <msh:submitCancelButtonsRow colSpan="6"/>
        </msh:panel>

    </msh:form>


</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>
        <msh:sideLink key="ALT+1" params="" action="/entityList-diary_templateProtocolType" name="⇧ Список типов протоколов"/>

        <msh:ifFormTypeIsView formName="diary_templateProtocolTypeForm">
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diary_templateProtocolType" name="Изменить"/>


        </msh:ifFormTypeIsView>

        <hr/>
        <msh:ifFormTypeAreViewOrEdit formName="diary_templateProtocolTypeForm">
            <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-diary_templateProtocolType" name="Удалить"
                          confirm="Удалить тип шаблона?"/>
        </msh:ifFormTypeAreViewOrEdit>

    </msh:sideMenu>
</tiles:put>

<tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsView formName="diary_templateProtocolTypeForm">

        <script type="text/javascript">
//            $('buttonShowAddress').style.display = 'none';
        </script>
    </msh:ifFormTypeIsView>

</tiles:put>


<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Diary" beginForm="diary_templateProtocolTypeForm"/>
</tiles:put>


</tiles:insert>