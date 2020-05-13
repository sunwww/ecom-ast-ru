<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/poly" prefix="poly" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-ecom_deleteJournal.do" defaultField="number">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />

      <msh:panel>
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
        <msh:submitCancelButtonsRow colSpan="6" />
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
    <ecom:titleTrail mainMenu="Config" beginForm="ecom_deleteJournalForm" />
  </tiles:put>
  
</tiles:insert>

