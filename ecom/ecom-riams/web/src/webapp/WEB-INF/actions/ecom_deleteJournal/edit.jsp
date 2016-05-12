<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/poly" prefix="poly" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-ecom_deleteJournal.do" defaultField="number" guid="bc67abba-e254-42ec-93f5-05fc22642f08">
      <msh:hidden property="id" guid="ca6184cf-53af-4505-bcf1-e5968005c218" />
      <msh:hidden property="saveType" guid="dbe70ad4-8e55-4c4a-9d5c-c1f7c98baeab" />

      <msh:panel guid="493743b7-5c99-4908-8def-f46fc983c447">
      <msh:row>
      	<msh:textField property="deleteDate" label="Дата удаления"/>
      	<msh:textField property="deleteTime" label="Время"/>
      </msh:row>
      <msh:row>
      	<msh:textField property="loginName" label="Логин удалившего"/>
      </msh:row>
      <msh:row>
      	<msh:textArea property="serialization" label="Описание" fieldColSpan="2"/>
      </msh:row>
        <msh:submitCancelButtonsRow colSpan="6" guid="38b40884-2beb-4370-b8c6-9f3e9b1b1183" />
      </msh:panel>
    </msh:form>

  </tiles:put>
  <tiles:put name="side" type="string">
    
    <msh:ifFormTypeAreViewOrEdit formName="ecom_deleteJournalForm">
    <msh:sideMenu title="Показать все">
        <msh:sideLink action="/js-ecom_deleteJournal-listNext.do?next=50" name="Перейти к списку" title="Перейти к списку" params="id"/>
    </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>

    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="ecom_deleteJournalForm" guid="8d54b767-f785-4513-8a11-3dd5d2112e48" />
  </tiles:put>
  
</tiles:insert>

