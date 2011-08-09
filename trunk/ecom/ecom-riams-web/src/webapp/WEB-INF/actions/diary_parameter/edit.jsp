<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="diary_parameterGroup" />
    <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Параметр">
      <msh:ifFormTypeIsView formName="diary_parameterForm" guid="e2054544-85-a21c-3bb9b4569efc">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-diary_parameter" name="Изменить" roles="/Policy/Diary/ParameterGroup/Parameter/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="diary_parameterForm" guid="a6802286-1d60-46ea-b7f4-f588331a09f7">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-diary_parameter" name="Удалить" roles="/Policy/Diary/ParameterGroup/Parameter/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно" guid="9e0388c8-2666-4d66-b865-419c53ef9f89">
      <tags:diary_additionMenu />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Параметр
    	  -->
    <msh:form action="/entityParentSaveGoView-diary_parameter.do" defaultField="name" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:hidden property="group" guid="bd32944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:row guid="bb6f7393-5e65-498c-82799f6b4">
          <msh:textField property="shortName" label="Короткое название" guid="b87e9cee-cf5d-43bc-b50d-1911d5e87e40" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="bb6f7393-5e65-498c-8279-b849d7e9f6b4">
          <msh:textField property="name" label="Полное название" guid="b87e9ce5d-43bc-b50d-1911d5e87e40" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="bb6f7393-5e65-498c-8f6b4">
          <msh:autoComplete property="type" label="Тип параметра " vocName="parameterType" guid="b87ed-43bc-b50d-1911d5e87e40" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="0f9d3ef3-5d4f-42da-ac3b-cad11ab98887">
          <msh:autoComplete property="valueDomain" label="Справочник пользовательский" vocName="valueDomain" guid="ee7d465d-6477-4c2b-9f7a-1eafb05f4f10" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="097bfac3-f5a7-418b-8980-c076584c8aad">
          <msh:autoComplete property="measureUnit" label="Единицы измерения" vocName="vocMeasureUnit" guid="48f51575-3ece-490b-a36d-32041db31bc4" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="ef378ec5-743a-4eda-acc3-db57081148aa">
          <msh:separator label="Референтный интервал" colSpan="4" guid="8a5d7b52-f7c9-435b-a0a6-0f0ee96ff98a" />
        </msh:row>
        <msh:row guid="24a99174-6e3c-4a2b-aee8-170d17f75823">
          <msh:textField property="normMinimum" label="Минимальное значение" guid="c6bcea98-7d63-4c4f-b6ca-8c21f8e6afb2" />
          <msh:textField property="normMaximum" label="Максимальное значение" guid="d9c99474-04f7-4e21-b236-9407f1340bb2" />
        </msh:row>
        <msh:row guid="751f466a-e4b1-4734-8366-78404e3116a5">
          <msh:separator label="Границы допустимых значений" colSpan="4" guid="ab667acc-fbdc-487f-90bc-97a8bd689e13" />
        </msh:row>
        <msh:row guid="9f396407-75b9-4804-955a-96677a5c21af">
          <msh:textField property="minimum" label="Минимальное значение" guid="c3a6b3c6-989b-454d-ac72-67e35aaf8313" />
          <msh:textField property="maximum" label="Максимальное значение" guid="e4db521f-0810-4728-829b-72392c0c8814" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_parameterForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
</tiles:insert>

