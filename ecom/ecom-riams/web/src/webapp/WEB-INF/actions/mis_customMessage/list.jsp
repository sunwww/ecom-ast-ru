<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/CustomMessage/Create" key="ALT+N" action="/entityPrepareCreate-mis_customMessage.do" name="Создать новое" />
    </msh:sideMenu>
  	<msh:sideMenu title="Список сообщений">
  	  <msh:sideLink action="/js-mis_customMessage-sendMessages.do?id=-1&short=${param.short}" name="Список отправленных сообщений" />
      <msh:sideLink action="/js-mis_customMessage-getMessages.do?id=-1&short=${param.short}" name="Список полученных сообщений" />
      <msh:sideLink action="/js-mis_customMessage-getSystemMessages.do?id=-1&short=${param.short}" name="Список полученных системных сообщений" />
      <msh:sideLink action="/js-mis_customMessage-notReceivedMessages.do?id=-1&short=${param.short}" name="Список не полученных сообщений" />
      <msh:sideLink action="/js-mis_customMessage-overdueMessages.do?id=-1&short=${param.short}" name="Список просроченных сообщений" />
    </msh:sideMenu>

  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list"
  	nativeSql="select cm.messageTitle,cm.messageText,count(distinct cm.id) as cntMes,list(cm.recipient||'<br/>') as addr,to_char(cm.validityDate,'dd.mm.yyyy') as validDate,to_char(cm.dateReceipt,'dd.mm.yyyy')||' '||cast(cm.timeReceipt as varchar(5))
  	from CustomMessage cm where ${addParam} and (cm.isHidden='0' or cm.isHidden is null)
  	  	group by cm.messageTitle,cm.messageText,cm.recipient,cm.validityDate,cm.dateReceipt,cm.timeReceipt order by cm.dateReceipt desc
  	" maxResult="20"
  	/>
  	<msh:section createUrl="entityPrepareCreate-mis_customMessage.do" 
  	createRoles="/Policy/Mis/CustomMessage/Create" title="${title}">
  	<msh:sectionTitle><msh:sideLink roles="/Policy/Mis/CustomMessage/Emergency/Create" action="/default_message_update.do" name="Отправить Экстренное сообщение об обновлении через 5 минут" />
  	</msh:sectionTitle>
  	<msh:sectionContent >
  	<table>
  	<tr><td valign="top">
  	  <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('js-mis_customMessage-sendMessages.do?short=Short',null)" name="  отправленных сообщений" />
      <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('js-mis_customMessage-receivedMessages.do?short=Short',null)" name="  доставленных сообщений" />
      <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('js-mis_customMessage-notReceivedMessages.do?short=Short',null)" name="  не доставленных сообщений" />
      <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('js-mis_customMessage-overdueMessages.do?short=Short',null)" name="  просроченных сообщений" />
      <hr>
      <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('js-mis_customMessage-getMessages.do?short=Short',null)" name="  полученных сообщений" />
      <msh:sideLink styleId="viewShort"  action="/javascript:getDefinition('js-mis_customMessage-getSystemMessages.do?short=Short',null)" name="  полученных системных сообщений" />

  	</td><td valign="top">
    <msh:table name="list" action="javascript:void(0)" idField="1">
      <msh:tableColumn columnName="Заголовок" property="1" />
      <msh:tableColumn columnName="Сообщение" property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" />
      <msh:tableColumn columnName="Дата получения" property="6" />
      
      <msh:tableColumn columnName="Срок действия" property="5" />
      <msh:tableColumn columnName="Адресат(ы)" property="4" />
    </msh:table>
    </td></tr>
  	</table>
  	</msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

