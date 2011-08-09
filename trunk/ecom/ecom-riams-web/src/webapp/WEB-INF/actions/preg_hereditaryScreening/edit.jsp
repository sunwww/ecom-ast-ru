<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_hereditaryScreeningForm" guid="9c32ca33-ee2d-4242-8d06-897e619bb334">
      <msh:sideMenu title="Скрининг на наследственные заболевания" guid="0adf78a9-9a70-4044-a522-a4aa059b0ddd">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_hereditaryScreening" name="Изменить" roles="/Policy/Mis/Inspection/HereditaryScreening/Edit" guid="646bba8c-0cd8-4c54-96a2-f19176c317bd" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_hereditaryScreening" name="Удалить" roles="/Policy/Mis/Inspection/HereditaryScreening/Delete" guid="dcac18fb-fd5f-4f4e-a4a1-830bd819d1db" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Скрининг на наследственные заболевания 
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_hereditaryScreening.do" defaultField="inspectionDate" guid="40d6b873-a237-4d9d-9df1-18de10db821d">
      <msh:hidden property="id" guid="b4d822ce-a4bc-4501-831f-7b2f47eded2f" />
      <msh:hidden property="medCase" guid="47d57f79-d463-4769-bd78-b6eada682901" />
      <msh:hidden property="saveType" guid="8d45fda9-7c72-4f7d-928c-e537c89757c8" />
      <msh:panel guid="f1c33bfe-9e0b-42fe-adff-11a3e7cfc501">
        <msh:row guid="fb1f21e8-2169-483c-9e7b-0f60476459a8">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row guid="283c02be-3ad1-4fe4-8b5d-2a3360323c05">
          <msh:textField property="inspectionDate" label="Дата" guid="b285c433-1a7b-4cc1-a1d0-64d60365a2fb" />
          <msh:textField property="inspectionTime" label="Время" guid="4112791f-9399-46dd-ba5c-b0838bc9e159" />
        </msh:row>
        <msh:row guid="02aac1bb-0846-410e-9a9a-df68d7e846ec">
          <msh:textArea property="notes" label="Описание" rows="3" fieldColSpan="4" guid="229537a6-b34b-421b-a601-389c16dacf2a" />
        </msh:row>
        <msh:row guid="c727920e-0d60-4118-9d20-da32d07f7dcc">
          <msh:autoComplete property="workFunction" label="Специалист" horizontalFill="true" fieldColSpan="3" guid="8aae4afd-510c-4036-b4c3-010f1ff69978" vocName="workFunction" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="62d233fa-d5cf-4c9a-846e-eafdbd7ac0ac" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_hereditaryScreeningForm" guid="64aada64-bf54-4b22-90cf-f4e820742059" />
  </tiles:put>
</tiles:insert>

