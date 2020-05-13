<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_infectiousMessagesForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_infectiousMessagesForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_infectiousMessages" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_infectiousMessages" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/Delete" />
        <msh:sideLink key="ALT+3" params="id" action="/entityParentListRedirect-stac_infectiousMessages" name="⇧Cписок сообщений об инфекции" roles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/View" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Сообщения об инфекции
    	  -->
    <msh:form action="/entityParentSave-stac_infectiousMessages.do?sslid=${param.sslid}" defaultField="dateStart" editRoles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/Create">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:hidden property="phoneMessageType" />
      <msh:panel>
        <msh:separator colSpan="8" label="Сообщение об инфекции" />
        <msh:row>
          <msh:textField property="phoneDate" label="Дата регистрации" />
          <msh:textField property="phoneTime" label="время" />
        </msh:row>
        <msh:row>
          <msh:textField property="phone" label="Телефон" horizontalFill="true" />
          <msh:textField property="number" label="Номер сообщения" />
        </msh:row>
        <msh:row>
          <msh:textField property="recieverOrganization" label="Принявшая сообщение организация" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="recieverFio" label="Фамилия принявшего" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="workFunction" label="Передавший сообщение специалист" vocName="workFunction" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" labelSave="Сохранить изменения" labelCreating="Создание" labelCreate="Создать новый случай" labelSaving="Сохранение данных" />
      </msh:panel>
    </msh:form>
  </tiles:put>
</tiles:insert>

