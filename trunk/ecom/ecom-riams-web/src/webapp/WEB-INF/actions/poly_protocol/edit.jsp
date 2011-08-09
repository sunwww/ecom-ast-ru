<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>



<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
    <tags:templateProtocol  />
    <tags:prevTemplate  ticket="poly_protocolForm.ticket"/>
    <msh:form action="entityParentSaveGoView-poly_protocol.do" defaultField="dateRegistration">
      <msh:panel>
        <msh:hidden property="id"/>
        <msh:hidden property="saveType"/>
        <%--<msh:hidden property="medcard"/>--%>
        <msh:hidden property="ticket"/>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Protocol/EditSpecialist">
	        <msh:hidden property="specialist"/>
        </msh:ifNotInRole>
        
                <msh:row>
                    <msh:textField label="Дата" property="dateRegistration"/>
                    <msh:textField label="Время" property="timeRegistration" fieldColSpan="1"  guid="b3hb-b971-441e-9a90-8019c07" />
                </msh:row>

                <msh:row>
                    <msh:textArea property="record" rows="50" label="Текст протокола"
                                      horizontalFill="true" fieldColSpan="4"/>
                    <msh:ifFormTypeIsNotView formName="poly_protocolForm">
                    <td colspan="1">
                        <input type="button" value="Шаблон" onClick="showTemplateProtocol()"/>
                        <br/>
                        <input type="button" value="Пред. протоколы по пациенту" onClick="showPrevProtocol()"/>
                    </td>
                    <tags:keyWord name="record" service="KeyWordService" methodService="getDecryption"/>
                    </msh:ifFormTypeIsNotView>
                </msh:row>
                <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/EditSpecialist">
                <msh:row>
                	<msh:autoComplete fieldColSpan="3" horizontalFill="true" property="specialist" vocName="workFunction" label="Специалист"/>
                </msh:row>
                </msh:ifInRole>


                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
            
    </msh:form>
</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>

        <msh:ifFormTypeIsView formName="poly_protocolForm">
            <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Edit" key="ALT+2" params="id" action="/entityParentEdit-poly_protocol"
                          name="Редактировать"/>
            <!--begin протокол-->
             <%--<msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Create" key="ALT+5" params="id"--%>
                          <!--action="/entityParentPrepareCreate-poly_protocol"-->
                          <!--name="Добавить протокол"/>-->
            <!--end протокол-->

        </msh:ifFormTypeIsView>

        <msh:ifFormTypeAreViewOrEdit formName="poly_protocolForm">
            <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Delete" key='ALT+DEL' params="id"
                          action="/entityParentDeleteGoParentView-poly_protocol" name="Удалить"
                          confirm="Вы действительно хотите удалить?"/>
            <msh:sideMenu title="Печать">
	            <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/View" key='ALT+3' params="id"
	                          action="/print-protocol.do?m=printProtocol&s=PrintTicketService" name="Печать протокола"
	                         />
             </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>

    </msh:sideMenu>
</tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="poly_protocolForm"/>
    </tiles:put>

</tiles:insert>
