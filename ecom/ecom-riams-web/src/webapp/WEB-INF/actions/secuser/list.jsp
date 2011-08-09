<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="14b68ab0-5301-4aa8-83c8-56b92eb6a75d" mainMenu="Config" title="Список ${param.list==&quot;all&quot;? &quot;всех&quot; :param.list==&quot;disable&quot; ?&quot;заблокированных&quot;:&quot;активных&quot;} пользователей"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="${param.list==&quot;all&quot;? &quot;listall&quot; :param.list==&quot;disable&quot; ?&quot;listdisable&quot;:&quot;listactive&quot;}" />
    <msh:sideMenu guid="6acc4c6e-0d0e-465b-a669-4598c8cf6307">
      <msh:sideLink key="ALT+N" action="/entityPrepareCreate-secuser" name="Создать нового пользователя" guid="230cc639-2d0f-4fb4-81d4-11b4edaea7db" roles="/Policy/Jaas/SecUser/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Списки пользователей" guid="8a39450d-3f79-4741-9555-c379c457a830">
      <msh:sideLink action="/entityList-secuser.do?list=all" name="Все пользователи" guid="cdeac23a-ab60-49b9-a53f-d7fcbbd6bd86" styleId="listall" roles="/Policy/Jaas/SecUser/View" />
      <msh:sideLink action="/entityList-secuser.do?list=disable" name="Заблокированные" guid="58c9d4ef-772c-4370-81f4-5b8cf2f8947b" styleId="listdisable" roles="/Policy/Jaas/SecUser/View" />
      <msh:sideLink action="/entityList-secuser.do" name="Активные" title="Активные" guid="c78bd172-c888-410e-a775-a5917326cf3c" styleId="listactive" roles="/Policy/Jaas/SecUser/View" />
    </msh:sideMenu>
    <tags:menuJaas currentAction="users"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="listSort" hql="select id,login,fullname,comment,disable from SecUser &#xA;${param.list==&quot;all&quot;? &quot; &quot; :param.list==&quot;disable&quot; ?&quot;where cast(disable as integer)=1&quot;:&quot;where disable is null or cast(disable as integer)=0&quot;}&#xA;order by login" guid="4961a776-a9d5-499f-9b4f-8aeb3620d24e" />
    <msh:table name="listSort" action="userView.do" idField="1" guid="cf6b8963-1a46-4142-8503-cc36fce098f7">
      <msh:tableColumn columnName="#" property="sn" guid="597cacc1-1445c8-98b0-5ff0d70752b9" />
      <msh:tableColumn columnName="Пользователь" property="2" guid="597cacc1-1408-45c8-98b0-5ff0d70752b9" />
      <msh:tableColumn columnName="Полное имя" property="3" guid="c2d94189-cb9a-4119-b012-4993b512f7ac" />
      <msh:tableColumn columnName="Комментарий" property="4" guid="8a8b3afb-523f-4d2c-92c0-6bf52b6ced31" />
      <msh:tableColumn columnName="Заблокирован?" identificator="false" property="5" guid="009b46d8-1b6b-4bf6-b7c5-24c24fcfc7ef" />
    </msh:table>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">Element.addClassName($('mainMenuUsers'), "selected") ;</script>
  </tiles:put>
</tiles:insert>

