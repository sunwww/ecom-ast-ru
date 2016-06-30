<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-temp_protocolPrint" defaultField="name" guid="34dc6e2d-dfa9-41a2-b8e1-3a0bdbb24d36">
      <msh:hidden property="id" guid="b13af088-2be6-4450-8c05-aaa9971111bf" />
      <msh:hidden property="saveType" guid="4f251d39-a1c1-46cc-b8a8-6c641aadad7d" />
      <msh:hidden property="template" guid="ea9befb6-d884-4f43-b90e-f45eb4a310f4" />
      <msh:panel guid="4b44ca53-2bd6-4bdf-9e54-0ea38d8fb582">
        <msh:row guid="d40cb3bf-d3e7-4544-a6ca-9db18a786f47">
          <msh:textField property="name" label="Заголовок шаблона для печати" horizontalFill="true" guid="83e6d295-6ea7-4010-8845-c1f694f8fc2d" fieldColSpan="3" />
        </msh:row>
         <msh:row guid="432f3c8f-a13a-477b-86ac-c2eaaff4fa0e">
                 <msh:textField property="fileName" label="Название файла для печати" horizontalFill="true" guid="83e6d295-6ea7-4010-8845-c1f694f8fc2d" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="fdcf0100-ab1c-4900-b7d6-cb08c77924b0">
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="4a71ff37-bc99-4d73-a6a4-80c15f1c29e8" />
      </msh:panel>
    </msh:form>   
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="5db0db09-9993-44cb-8477-a3fee5037b42" title="Шаблон протокола">
      
      <msh:ifFormTypeIsView formName="temp_protocolPrintForm" guid="dd63e5e4-f81c-43f2-b50a-f12b1d8e026b">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-temp_protocolPrint" name="Изменить" roles="/Policy/Diary/Template/Edit" guid="05503c33-989a-45dc-ab6f-8d1be735e97e" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="temp_protocolPrintForm" guid="4399c99f-8801-4a73-b168-c25c23f8b0ba" />
  </tiles:put>
</tiles:insert>

