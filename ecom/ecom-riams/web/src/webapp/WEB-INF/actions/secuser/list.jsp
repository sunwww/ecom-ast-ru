<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Список ${param.list==&quot;all&quot;? &quot;всех&quot; :param.list==&quot;disable&quot; ?&quot;заблокированных&quot;:&quot;активных&quot;} пользователей"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="${param.list==&quot;all&quot;? &quot;listall&quot; :param.list==&quot;disable&quot; ?&quot;listdisable&quot;:&quot;listactive&quot;}" />
    <msh:sideMenu>
      <msh:sideLink key="ALT+N" action="/entityPrepareCreate-secuser" name="Создать нового пользователя" roles="/Policy/Jaas/SecUser/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Списки пользователей">
      <msh:sideLink action="/entityList-secuser.do?list=all" name="Все пользователи" styleId="listall" roles="/Policy/Jaas/SecUser/View" />
      <msh:sideLink action="/entityList-secuser.do?list=disable" name="Заблокированные" styleId="listdisable" roles="/Policy/Jaas/SecUser/View" />
      <msh:sideLink action="/entityList-secuser.do" name="Активные" title="Активные" styleId="listactive" roles="/Policy/Jaas/SecUser/View" />
    </msh:sideMenu>
    <tags:menuJaas currentAction="users"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<msh:ifInRole roles="/Policy/Jaas/SecUser/EditSystem">
  		<ecom:webQuery name="listSort" hql="select id,login,fullname,comment,disable 
  		from SecUser &#xA;${param.list==&quot;all&quot;? &quot; &quot; :param.list==&quot;disable&quot; ?&quot;where disable='1'&quot;:&quot;where disable is null or disable='0'&quot;}&#xA;order by login" />
  	</msh:ifInRole>
  	<msh:ifNotInRole roles="/Policy/Jaas/SecUser/EditSystem">
  		<ecom:webQuery name="listSort" hql="select id,login,fullname,comment,disable 
  		from SecUser &#xA;${param.list==&quot;system&quot;?&quot; where isSystems='1'&quot;:param.list==&quot;all&quot;? &quot; where (isSystems is null or isSystems='0')&quot; :param.list==&quot;disable&quot; ?&quot;where disable='1' and (isSystems is null or isSystems='0')&quot;:&quot;where (disable is null or disable='0') and (isSystems is null or isSystems='0')&quot;}&#xA; order by login" />
  	</msh:ifNotInRole>
    <msh:table name="listSort" action="userView.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Пользователь" property="2" />
      <msh:tableColumn columnName="Полное имя" property="3" />
      <msh:tableColumn columnName="Комментарий" property="4" />
      <msh:tableColumn columnName="Заблокирован?" identificator="false" property="5" />
    </msh:table>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">Element.addClassName($('mainMenuUsers'), "selected") ;</script>
  </tiles:put>
</tiles:insert>

