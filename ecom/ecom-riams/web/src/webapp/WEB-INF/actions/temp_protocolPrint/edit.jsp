<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-temp_protocolPrint" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="template" />
      <msh:panel>
        <msh:row>
          <msh:textField property="name" label="Заголовок шаблона для печати" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
         <msh:row>
                 <msh:textField property="fileName" label="Название файла для печати" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>   
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Шаблон протокола">
      
      <msh:ifFormTypeIsView formName="temp_protocolPrintForm">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-temp_protocolPrint"  name="Изменить" roles="/Policy/Diary/Template/Edit" />
        <msh:sideLink key="ALT+D" params="id" action="/entityParentDeleteGoParentView-temp_protocolPrint" name="Удалить" roles="/Policy/Diary/Template/Delete" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="temp_protocolPrintForm" />
  </tiles:put>
</tiles:insert>

