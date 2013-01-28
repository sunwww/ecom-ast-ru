<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-mis_customMessage.do" defaultField="name" guid="17f720e4-3690-4ae5-961b-6d69348757b6">
      <msh:hidden property="id" guid="df46ed12-bbf3-4f90-9046-dcf1b595541c" />
      <msh:hidden property="saveType" guid="12fee02b-f848-4039-b3f6-35cbf5f3a629" />
      <msh:panel guid="eb62e08a-d34a-4af0-9f13-d23197a33fef">
        <msh:row guid="2df6f0d2-a60d-44a5-b64b-3aeb3f298d04">
          <msh:textField property="messageTitle" label="Название" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textArea property="messageText" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="messageUrl" label="Ссылка" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:ifFormTypeIsCreate formName="mis_customMessageForm">
	        <msh:row>
	        	<msh:autoComplete property="secUser" label="Пользователю" fieldColSpan="3" vocName="secUser" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	        	<msh:checkBox property="isAllUsers" label="Отправить всем пользователям" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:row>
        	<msh:textField property="validityDate" label="Срок действия"/>
        	<msh:ifFormTypeAreViewOrEdit formName="mis_customMessageForm">
        		<msh:textField property="recipient"/>
        	</msh:ifFormTypeAreViewOrEdit>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="fe0172d0-16e6-490d-9bf2-ab6ac96e7402" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_customMessageForm" guid="441c731d-212d-45e8-9964-dde5c8db0a4b">
      <msh:sideMenu title="Добавить" guid="f692ef30-e3cb-4cb7-9f0f-1e0a38e4b08d">
        <msh:sideLink key="ALT+2" params="id" roles="/Policy/Mis/CustomMessage/Create" action="/entityPrepareCreate-mis_customMessage" name="Сообщение" title="Добавить сообщение" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
    <msh:sideMenu title="Сообщение">
      <msh:sideLink key="ALT+3" action="/js-mis_customMessage-sendMessages.do?id=-1" name="Список отправленных сообщений" />
      <msh:sideLink key="ALT+4" action="/js-mis_customMessage-getMessages.do?id=-1" name="Список полученных сообщений" />
      <msh:sideLink key="ALT+5" action="/js-mis_customMessage-notReceivedMessages.do?id=-1" name="Список не полученных сообщений" />
      <msh:sideLink key="ALT+6" action="/js-mis_customMessage-overdueMessages.do?id=-1" name="Список просроченных сообщений" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="mis_customMessageForm" />
  </tiles:put>

</tiles:insert>