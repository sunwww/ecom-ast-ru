<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-smo_short_ticket.do" defaultField="date" guid="77bf3d00-cfc6-49eb-9751-76e82d38751c">
      <msh:hidden property="id" guid="e862851f-7390-4fe6-9a37-3b22306138b4" />
      <msh:hidden property="saveType" guid="3e3fb7b5-258e-4194-9dbe-5093382cf627" />
      <msh:hidden property="medcard" guid="34911b70-6c2c-43f2-bbf4-c58fed9a296e" />
      <msh:panel>
        <msh:row guid="59560d9f-0765-4df0-bfb7-9a90b5eed824">
          <msh:textField label="Дата приема" property="dateFinish" fieldColSpan="1" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" />
        </msh:row>
        <msh:row guid="47073a0b-da87-49e0-9ff0-711dc597ce07">
          <msh:autoComplete parentId="smo_short_ticketForm.medcard" vocName="workFunctionByTicket" property="workFunctionExecute" label="Специалист" fieldColSpan="3" size="100" horizontalFill="true" guid="a8404201-1bae-467e-b3e9-5cef63411d01" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:ifFormTypeAreViewOrEdit formName="smo_short_ticketForm">
        <msh:row>
        	<msh:textField label="Дата выдачи" property="createDate" fieldColSpan="1" viewOnlyField="true"/>
        	<msh:textField label="Время выдачи" property="createTime" fieldColSpan="1" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Пользователь" property="username" viewOnlyField="true" />
        </msh:row>
        </msh:ifFormTypeAreViewOrEdit>
        <msh:submitCancelButtonsRow colSpan="3" guid="13aa4bce-1133-48d6-896b-eb588a046d59" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_short_ticketForm" guid="8f-4d80-856b-ce3095ca1d">
      <msh:sideMenu guid="e6c81315-888f-4d80-856b-ce3095ca1d55" title="Талон">
        <msh:sideLink roles="/Policy/Poly/ShortTicket/Edit" key="ALT+3" params="id" action="/entityParentEdit-smo_short_ticket" name="Изменить" guid="89585df8-aadb-4d59-abd9-c0d16a6170a9" title="Изменить талон" />
        <msh:ifFormTypeAreViewOrEdit formName="smo_short_ticketForm" guid="7f581b0a-a8b3-4d57-9cff-6dc6db1c85e3">
          <msh:sideLink roles="/Policy/Poly/ShortTicket/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-smo_short_ticket" name="Удалить" confirm="Вы действительно хотите удалить талон?" guid="8b9de89f-3b99-414e-b4af-778ccbb70edf" title="Удалить талон" />
        </msh:ifFormTypeAreViewOrEdit>
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="62fd4ce0-85b5-4661-87b2-fea2d4fb7339">
        <msh:sideLink roles="/Policy/Poly/ShortTicket/View" key="SHIFT+8" params="id" action="/print-ticketshort.do?s=PrintTicketService&amp;m=printInfo" name="Талон" guid="97e65138-f936-45d0-ac70-05e1ec87866c" title="Печатать талон" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="smo_short_ticketForm" guid="5c4f3682-e66b-4e0d-b448-4e6a2961a943" />
  </tiles:put>
</tiles:insert>
