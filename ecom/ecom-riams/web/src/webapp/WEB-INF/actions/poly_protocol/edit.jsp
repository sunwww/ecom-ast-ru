<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>



<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
    <tags:templateProtocol version="Ticket" voc="protocolTicketByPatient" idSmo="poly_protocolForm.ticket"  />
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
                    <msh:textField label="Время" property="timeRegistration" fieldColSpan="1"  />
                </msh:row>

                <msh:row>
                    <msh:textArea property="record" rows="50" size="100" label="Текст протокола"
                                      fieldColSpan="4"/>
                    <msh:ifFormTypeIsNotView formName="poly_protocolForm">
                    <td colspan="1">
                        <input type="button" value="Шаблон" onClick="showTemplateProtocol()"/>
                       
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
            <msh:ifFormTypeIsView formName="poly_protocolForm">
            <msh:section title="Лекарственное средство">
            <ecom:webQuery nativeSql="select p.id as pid,p.drug_id as drugis,vdc.name as vdcname
            from Prescription p 
            left join VocDrugClassify vdc on vdc.id=p.drug_id
            where p.diary_id='${param.id}'" name="drugs"/>
	    		<msh:table name="drugs" 
	    		action="entityParentView-poly_drugPrescription.do"
	    		viewUrl="entityShortView-poly_drugPrescription.do"
	    		 idField="1">
	    			<msh:tableColumn property="sn" columnName="#"/>
	    			<msh:tableColumn property="3" columnName="Лекарственное средство"/>
	    		</msh:table>
            </msh:section>
            
            </msh:ifFormTypeIsView>
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
            <msh:sideMenu title="Добавить">
            	        <msh:sideLink roles="/Policy/Poly/DrugPrescription/Create" key="CTRL+4" params="id" action="/entityParentPrepareCreate-poly_drugPrescription" name="Лекарственное средство" title="Добавить назначение лекарственных средств" />
            </msh:sideMenu>
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
