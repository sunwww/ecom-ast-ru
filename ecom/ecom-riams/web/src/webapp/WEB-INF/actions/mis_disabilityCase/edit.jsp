<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

<tiles:put name='body' type='string'>
<msh:form action="entitySaveGoView-mis_disabilityCase.do" defaultField="dateFrom">
   <msh:hidden property="id"/>
   <msh:section title="Случай нетрудоспособности"> 
     <msh:panel colsWidth='5%, 20%, 10%'>
        <msh:row>
            <msh:textField property="dateFrom" label="C"/>
            <msh:textField property="dateTo" label="По"/>
        </msh:row>
	    <ecom:parentEntityListAll formName="mis_disabilityDocumentForm" attribute="documents"/>
		<msh:tableNotEmpty name="documents">
			<msh:section title='Документы нетрудоспособности'>
		            <msh:table  hideTitle='true' name="disabilityDocumentForm" action="entitySubclassView-mis_disabilityDocument.do" idField="id">
						<msh:tableColumn columnName="Номер" property="number" />
						<msh:tableColumn columnName="Серия" property="series" />
		            </msh:table>
			</msh:section>
		</msh:tableNotEmpty>
    </msh:panel>        
  </msh:section>
</msh:form>
</tiles:put>

<tiles:put name='side' type='string'>
    <msh:sideMenu>
        <msh:sideLink key="ALT+1" params="" action="/mis_patients" name="⇧ Список персон"/>

        <msh:ifFormTypeIsView formName="mis_disablityDocumentForm">
            <msh:sideLink roles="/Policy/Mis/DisabilityDocument/Create" key="ALT+2" params="id" action="/entityEdit-mis_disabilityDocument"
                          name="Создать новый документ нетрудоспособности"/>
        </msh:ifFormTypeIsView>
    </msh:sideMenu>
</tiles:put>

</tiles:insert>