<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
    <tags:templateProtocol voc="protocolTicketByPatient"/>

    <msh:form action="entitySaveGoView-diary_protocol" defaultField="record">
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>


        <input type="hidden" name="slsId" value="1" id="slsId">
        <input type="hidden" name="sloId" value="1" id="sloId">
        <input type="hidden" name="tepmProt" value="" id="flatNumber">
        <!--<input type="hidden" name="houseBuilding" value="" id="houseBuilding">-->
        <!--<input type="hidden" name="houseNumber" value="" id="houseNumber">-->


        <msh:panel>
            <msh:ifFormTypeIsNotView formName="diary_protocolForm" >
                <msh:row>
                    <msh:textArea property="record" label="Запись врача" horizontalFill="true"  />
                </msh:row>
                <msh:row>
                    <tags:keyWord name="record" service="KeyWordService" methodService="getDecryption" saveKeyWord="saveKeyWord"/>
                    <td colspan="2">
                        <input type="button" value="Добавить шаблон" onClick="showTemplateProtocol()"/>
                        <textarea id="saveKeyWord" name="saveKeyWord" ></textarea>
                    </td>

                </msh:row>
            </msh:ifFormTypeIsNotView>

            <msh:submitCancelButtonsRow colSpan="6"/>
        </msh:panel>

    </msh:form>


</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>
        <msh:sideLink key="ALT+1" params="" action="/entityList-diary_protocol" name="⇧ Список протоколов"/>

        <msh:ifFormTypeIsView formName="diary_protocolForm">
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-diary_protocol" name="Изменить"/>


        </msh:ifFormTypeIsView>

        <hr/>
        <msh:ifFormTypeAreViewOrEdit formName="diary_protocolForm">
            <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-diary_protocol" name="Удалить"
                          confirm="Удалить протокол?"/>
        </msh:ifFormTypeAreViewOrEdit>

    </msh:sideMenu>
</tiles:put>

<tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsView formName="diary_protocolForm">

        <script type="text/javascript">
//            $('buttonShowTemplate').style.display = 'none';
        </script>
    </msh:ifFormTypeIsView>

</tiles:put>


<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Diary" beginForm="diary_protocolForm"/>
</tiles:put>


</tiles:insert>