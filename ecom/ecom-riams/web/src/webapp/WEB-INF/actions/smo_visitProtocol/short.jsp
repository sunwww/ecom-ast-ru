 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>



<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
    <msh:form action="entityParentSaveGoSubclassView-smo_visitProtocol.do" 
    defaultField="dateRegistration" >
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>
        <msh:hidden property="username"/>
        <msh:hidden property="date"/>
        <msh:hidden property="time"/>
        <msh:hidden property="printDate"/>
        <msh:hidden property="printTime"/>
        <msh:hidden property="medCase"/>
        <msh:hidden property="specialist"/>

            
            <msh:panel colsWidth="1%,1%,1%,1%,1%,1%,65%">
                <msh:row>
                    <msh:textField label="Дата" property="dateRegistration" fieldColSpan="1" />
                    <msh:textField label="Время" property="timeRegistration" fieldColSpan="1"  />
                </msh:row >
                <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/OwnerFunction">
                	<msh:row>
                		<msh:autoComplete property="type" fieldColSpan="3" label="Тип протокола" horizontalFill="true"
                		vocName="vocTypeProtocol"/>
	                </msh:row>
                </msh:ifInRole>
                <msh:row>
                    <msh:textArea property="record" label="Текст протокола:"
                                      size="100" rows="30" fieldColSpan="8"  />
                    <msh:ifFormTypeIsView formName="smo_visitProtocolForm">
                    <td></td>
                    </msh:ifFormTypeIsView>
                </msh:row>
                <msh:ifFormTypeIsView formName="smo_visitProtocolForm">
                
                <msh:row>
                	<msh:textField property="date" label="Дата создания" viewOnlyField="true"/>
                	<msh:textField property="time" label="Время" viewOnlyField="true"/>
                	<msh:textField property="username" label="Пользователь" viewOnlyField="true"/>
                </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Пользователь" />
        </msh:row>
                        <msh:row>
                	<msh:textField property="printDate" label="Дата печати" viewOnlyField="true"/>
                	<msh:textField property="printTime" label="Время" viewOnlyField="true"/>
                </msh:row>
                </msh:ifFormTypeIsView>
                <msh:row>
	                <msh:submitCancelButtonsRow colSpan="3"/>
                </msh:row>
            </msh:panel>
    </msh:form>
</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>

        <msh:ifFormTypeIsView formName="smo_visitProtocolForm">
            <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Edit" key="ALT+2" params="id" action="/entityParentEdit-smo_visitProtocol"
                          name="Редактировать"/>
        
        </msh:ifFormTypeIsView>

        <msh:ifFormTypeAreViewOrEdit formName="smo_visitProtocolForm">
            <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Delete" key='ALT+DEL' params="id"
                          action="/entityParentDeleteGoSubclassView-smo_visitProtocol" name="Удалить"
                          confirm="Вы действительно хотите удалить?"/>
         </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    
    <msh:ifFormTypeIsView formName="smo_visitProtocolForm">
    <msh:sideMenu title="Печать" >
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintProtocol" 
    	name="Печать дневника" params="id"  
    	action='/print-protocol.do?m=printProtocol&s=HospitalPrintService' title='Печать дневника' />
    
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
</tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="smo_visitProtocolForm" />
    </tiles:put>
</tiles:insert>
