<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
    <div style='float:right'>
        <a id='helpAddressLink' href='javascript:showHideHelp()'>Показать помощь</a>
    </div>
    <msh:form action="entityParentSaveGoParentView-mis_lpuAreaAddressText.do" defaultField="addressString">
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>
        <msh:hidden property="area"/>

        <msh:hidden property="address"/>
        <msh:hidden property="flatNumber"/>
        <msh:hidden property="houseBuilding"/>
        <msh:hidden property="houseNumber"/>

        <msh:panel>

            <msh:row>
                <td colspan='1' title='Адрес (addressField)' class='label'><label id='addressFieldLabel'
                                                                                  for='addressField'>Адрес:</label>
                </td>
                <td colspan='5' class='addressField'><input title='АдресNoneField' class=' horizontalFill'
                                                            id='addressField' name='addressField' size='10'
                                                            value='Адрес... ' type='text'/></td>

            </msh:row>

            <msh:row>
                <msh:textArea property="addressString" label="Номер дома, владения" horizontalFill="true"
                              size="50"/>
            </msh:row>

            <msh:ifFormTypeIsView formName="mis_lpuAreaAddressTextForm">
                <msh:row>
                    <msh:label property="addressText" label="Адрес" horizontalFill="true" size="50"/>
                </msh:row>
            </msh:ifFormTypeIsView>

            <msh:submitCancelButtonsRow colSpan="2"/>
        </msh:panel>

    </msh:form>

    <msh:ifFormTypeIsNotView formName="mis_lpuAreaAddressTextForm">
        <msh:section>
            <msh:sectionTitle>Результат проверки поля "Номер дома, владения"</msh:sectionTitle>
            <msh:sectionContent>
                <p id='addressPoints'>Проверка адреса...</p>
            </msh:sectionContent>
        </msh:section>
    </msh:ifFormTypeIsNotView>

    <div id='helpAddress' style='display:none'>
        <msh:section>
            <msh:sectionTitle>Помощь</msh:sectionTitle>
            <msh:sectionContent>
                <jsp:include page="help.jsp"/>
            </msh:sectionContent>
        </msh:section>
    </div>

    <tags:addressTag/>

</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>
        <msh:ifFormTypeIsView formName="mis_lpuAreaAddressTextForm">
            <msh:sideLink key="ALT+2" roles="/Policy/Mis/LpuAreaAddressText/Edit" params="id" action="/entityEdit-mis_lpuAreaAddressText" name="Изменить"/>

            <msh:sideLink key="ALT+6" roles="/Policy/Mis/LpuAreaAddressText/View" params="id" action="/mis_bypassAreaExcelExport.do?type=addressText" name="Печать обходного листа"/>

        </msh:ifFormTypeIsView>
        <hr/>
        <msh:ifFormTypeAreViewOrEdit formName="mis_lpuAreaAddressTextForm">
            <msh:sideLink key='ALT+DEL' params="id" roles="/Policy/Mis/LpuAreaAddressText/Delete" action="/entityParentDeleteGoParentView-mis_lpuAreaAddressText"
                          name="Удалить" confirm="Удалить адрес?"/>
        </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
</tiles:put>

<tiles:put name='title' type='string'>
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuAreaAddressTextForm"/>
</tiles:put>

<tiles:put name="style" type="string">
    <style type="text/css">
        #addressString {
            width: 100%;
        }

        li p, li, p {
            margin-top: 1em;
            margin-bottom: 1em;
        }

        li {
            margin-left: 1em;
        }
    </style>
</tiles:put>

<tiles:put name="javascript" type="string">
    <script type="text/javascript">
        <msh:ifFormTypeIsView formName="mis_lpuAreaAddressTextForm">
        $('buttonShowAddress').style.display = 'none';
        </msh:ifFormTypeIsView>

        function showHideHelp() {
            var div = $('helpAddress') ;
            var a = $('helpAddressLink') ;
            var isShowed = div.style.display == 'block' ;

            div.style.display = isShowed ? 'none' : 'block';
            a.innerHTML = isShowed ? "Показать помощь" : "Скрыть помощь";

        }

        $('addressHouseNumber').style.display = 'none';
        $('addressHouseNumberLabel').style.display = 'none';
        $('addressHouseBuilding').style.display = 'none';
        $('addressHouseBuildingLabel').style.display = 'none';
        $('addressFlatNumber1').style.display = 'none';
        $('addressFlatNumber1Label').style.display = 'none';

        <msh:ifFormTypeIsNotView formName="mis_lpuAreaAddressTextForm">

        function parsePoints() {
            parsePointsWithSubmitCheck(false) ;
        }

        function parsePointsWithSubmitCheck(aSubmit) {
            var p = $('addressPoints') ;
            p.innerHTML = "Проверка адреса..." ;
            AddressService.parseAddressPoints($('area').value, $('id').value, $('address').value, $('addressString').value, {
                callback: function(aStr) {
                    p.innerHTML = aStr;
                    p.style.color = "black";
                    $('submitButton').disabled=false ;
                    if(aSubmit) {
                        $('mainForm').submit() ;
                    }
                },
                errorHandler: function(aMessage) {
                    p.innerHTML = "ОШИБКА: " + aMessage;
                    p.style.color = "red";
                    $('submitButton').disabled=true ;
                }
            });
        }
                
        function onAddressSave() {
            parsePoints(false);
        }

        function parsePointsSubmit() {
            parsePointsWithSubmitCheck(true) ;
        }
        eventutil.addEventListener($('addressString'), eventutil.EVENT_KEY_UP, parsePoints);
        parsePoints(false);
        $('submitButton').onclick = parsePointsSubmit ;
        </msh:ifFormTypeIsNotView>
    </script>


</tiles:put>
</tiles:insert>

