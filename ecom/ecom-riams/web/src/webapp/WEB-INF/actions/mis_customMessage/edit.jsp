<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-mis_customMessage.do" defaultField="name">
      <msh:hidden property="id"/>
      <msh:hidden property="saveType"/>
      <msh:panel>
        <msh:row>
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
	        	<msh:autoComplete property="secRole" label="Пол-лям, у которых есть роль" fieldColSpan="3" vocName="role" horizontalFill="true"/>
	        </msh:row>
            <msh:row>
                <ecom:oneToManyOneAutocomplete viewAction="mis_lpu.do" vocName="vocLpuHospOtdAll" colSpan="3" label="Сотрудникам отделений" property="lpus" />
            </msh:row>
	        <msh:row>
	        	<msh:checkBox property="isAllUsers" label="Отправить всем пользователям" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
	        <msh:row>
	        	<msh:checkBox property="isEmergency" label="Экстренное сообщение?" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:row>
        	<msh:textField property="validityDate" label="Срок действия"/>
        	<msh:ifFormTypeAreViewOrEdit formName="mis_customMessageForm">
        		<msh:textField property="recipient"/>
        	</msh:ifFormTypeAreViewOrEdit>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2"/>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_customMessageForm">
      <msh:sideMenu title="Добавить">
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