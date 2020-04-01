<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:sideMenu title="Справочник видов динамических документов">
        <msh:ifFormTypeAreViewOrEdit formName="voc_dynamicDocumentForm">
        <msh:ifFormTypeIsView formName="voc_dynamicDocumentForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityEdit-voc_dynamicDocument" name="Изменить" roles="/Policy/Mis/UserDocument/Edit" />
      </msh:ifFormTypeIsView>
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-voc_dynamicDocument" name="Удалить" roles="/Policy/Mis/UserDocument/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>    
  </tiles:put>

  <tiles:put name="body" type="string">
    <msh:form action="/entitySaveGoView-voc_dynamicDocument.do" defaultField="code">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel >
      	<msh:row>
      		<msh:textField property="code"/>
      	</msh:row>
        <msh:row>
          <msh:textField property="name" label="Наименование"  horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textArea horizontalFill="true" rows="6" property="content" label="Содержимое справочника" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_dynamicDocumentForm" />
  </tiles:put>
  <tiles:put type="string" name="javascript">
  	<script type="text/javascript">

  	</script>
  </tiles:put>
  </tiles:insert>