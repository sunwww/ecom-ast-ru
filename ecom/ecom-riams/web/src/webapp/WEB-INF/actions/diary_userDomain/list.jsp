<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Пользовательские справочники" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
  <ecom:webQuery name="list" 
  nativeSql="
  select ud.id,ud.code,ud.name,list(ul.name) as listValue from UserDomain ud
left join UserValue ul on ul.domain_id=ud.id
group by ud.id,ud.code,ud.name
order by ud.name
  "
  />
    <msh:table name="list" action="entityView-diary_userDomain.do" idField="1"
	editUrl="entityEdit-diary_userDomain.do">
      <msh:tableColumn columnName="Код" property="2" />
      <msh:tableColumn columnName="Название" property="3" />
      <msh:tableColumn columnName="Значения" property="4" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="diary_userDomain" />
    <msh:sideMenu title="Добавить">
      <msh:sideLink action="/entityPrepareCreate-diary_userDomain" name="Пользовательский справочник" title="Добавить пользовательский справочник" roles="/Policy/Diary/User/Domain/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
                  <tags:voc_menu currentAction="diary_user_voc" />

    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

