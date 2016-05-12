<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="sec_softConfigForm" guid="9d27df65-9af0-4398-8a7d-57059cd29743">
      <msh:sideMenu title="Настройка приложения" guid="58caa1eb-0a6c-4e93-9575-fde48488ebe5">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-sec_softConfig" name="Изменить" roles="/Policy/Config/Soft/Edit" guid="c714bd8f-4fef-44dc-9d0b-cbaf455258dc" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-sec_softConfig" name="Удалить" roles="/Policy/Config/Soft/Delete" guid="48146ed8-f1f6-431c-8d29-2ba385d21cb7" />
        <msh:sideLink key="ALT+3"  action="/entityList-sec_softConfig" name="⇧К списку настроек" roles="/Policy/Config/Soft/View" guid="48146ed8--431c-8d29-2ba385d21cb7" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Настройка приложения
    	  -->
    <msh:form action="/entitySaveGoView-sec_softConfig.do" defaultField="key" guid="63c31f7d-c99c-44f1-9236-aab59a65cc4f">
      <msh:hidden property="id" guid="bd0acac0-10f3-47a8-9b15-0d42a6369dbf" />
      <msh:hidden property="saveType" guid="ff2056d2-edb4-4b48-beda-cfb01a60b964" />
      <msh:panel guid="f71a2cac-9676-4672-ac98-0e9e305cd9ea">
        <msh:separator label="Основные сведения" colSpan="" guid="be299385-b3b1-4c0c-80d6-7033ca9a9c5f" />
        <msh:row guid="2822bfda-b649-402e-b21d-23332f6f988b">
          <msh:textField property="key" label="Ключ" guid="623d03fd-f2ea-4da5-85b4-593a65996c1c" size="25" />
          <msh:textField property="keyValue" label="Значение" guid="76dd3e83-867c-4361-a103-db3d6f671d69" size="40" />
        </msh:row>
        <msh:row guid="c5cc0909-1c67-4e7f-81b7-4b88c1e320f8">
          <msh:textArea property="description" label="Описание" rows="4" fieldColSpan="3" guid="5c0f3467-cf3e-4f3e-ac24-11cfe2bb072e" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="8515167d-96cf-48ad-8294-1f3f60a105ec" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="sec_softConfigForm" guid="6602cbc1-6a25-4fe2-a951-b297a9d9b990" />
  </tiles:put>
  <tiles:put name="javascript" type="string" />
</tiles:insert>

