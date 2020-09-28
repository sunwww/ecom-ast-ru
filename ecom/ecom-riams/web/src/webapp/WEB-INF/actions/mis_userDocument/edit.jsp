<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-mis_userDocument.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
      	<msh:row>
      	  <msh:textField property="name" label="Наименование" fieldColSpan="3"  horizontalFill="true" size="100"/>
      	</msh:row>
        <msh:row>
          <msh:textField property="fileName" label='Имя файла' fieldColSpan="3"  horizontalFill="true" size="100"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="groupType" vocName="userDocumentGroup" label="Группа" size="100" />
        </msh:row>
        
        <msh:submitCancelButtonsRow colSpan="2"/>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="">
      <msh:ifFormTypeIsView formName="mis_userDocumentForm">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/UserDocument/Edit" params="id" action="/entityEdit-mis_userDocument" name="Изменить" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_userDocumentForm">
        <msh:sideLink key="ALT+DEL" params="id" roles="/Policy/Mis/UserDocument/Delete" action="/entityDeleteGoList-mis_userDocument" name="Удалить" confirm="Удалить?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
   
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_userDocumentForm" />
  </tiles:put>

</tiles:insert>