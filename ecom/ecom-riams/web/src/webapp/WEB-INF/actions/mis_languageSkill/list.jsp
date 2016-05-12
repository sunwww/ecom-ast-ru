<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Patient">Проба</msh:title>
    <msh:form disableFormDataConfirm="true" method="GET" fileTransferSupports="false" action="entityView-mis_qualification.do" defaultField="id" guid="f76f0c34-fcc2-425c-bdd9-b9f9d84f667f">
      <msh:panel guid="2132e38c-8f0a-45cb-aca7-e07b7c040f84">
        <msh:row guid="04a63f08-e401-4731-87a8-d8cfb4901f15">
          <msh:autoComplete showId="false" vocName="vocHello" property="hello" viewOnlyField="false" label="Персона" guid="7ae456fc-a4fb-49be-b44a-b4e62708a37c" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="a00c6c68-fe76-49b4-80a9-1421cdcd7aa6">
          <msh:autoComplete showId="false" vocName="vocHello" property="hello" viewOnlyField="false" label="Язык" guid="f6a400b2-1e6c-4c8e-ade8-8253b6bc7b6b" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="d36ec05d-e659-42c9-a6d9-7874b8f59352">
          <msh:autoComplete showId="false" vocName="vocHello" property="hello" viewOnlyField="false" label="Уровнень знания" guid="785f135a-e7ee-4b9c-920b-a943e6b7d6e3" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/IdeMode/Hello/Create" key="ALT+N" action="/entityPrepareCreate-mis_languageSkill" name="Создать новое" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string" />
</tiles:insert>

