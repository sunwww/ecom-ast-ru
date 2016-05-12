<%@ page import="ru.ecom.expomc.ejb.services.form.check.CheckPropertyForm"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:form action="exp_checkPropertySave.do" defaultField="valueName">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="check"/>
            <msh:hidden property="property"/>
            <msh:hidden property="vocName"/>

            <msh:panel>
                <msh:row>
                    <msh:label property="property" label="Свойство" size='30'/>
                </msh:row>
                <msh:row>

                    <logic:notEmpty name='exp_checkpropertyForm' property="vocName">
                        <msh:autoComplete property="value" label='Значение' size='60' />
                    </logic:notEmpty>

                    <logic:empty name='exp_checkpropertyForm' property="vocName">
                        <msh:textArea property="value" label="Значение" />
                    </logic:empty>

                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>

    </tiles:put>

<tiles:put name="style" type="string">
    <style type="text/css">
    	textarea {
            width: 700px;
            height: 20em ;
    	}
    </style>
</tiles:put>
	

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            <logic:notEmpty name='exp_checkpropertyForm' property="vocName">
                valueAutocomplete.setVocKey($('vocName').value);
                valueAutocomplete.setUrl('simpleVocAutocomplete/'+$('vocName').value);
                valueAutocomplete.setParentId($('check').value);
                valueAutocomplete.setShowIdInName(true);
            </logic:notEmpty>

            <logic:empty name='exp_checkpropertyForm' property="vocName">
                $('value').focus() ;
            </logic:empty>
        </script>
    </tiles:put>


    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Document">Свойство проверки</msh:title>
    </tiles:put>

</tiles:insert>