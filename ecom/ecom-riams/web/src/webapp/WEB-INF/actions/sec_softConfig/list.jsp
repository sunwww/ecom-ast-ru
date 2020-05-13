<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Настройки приложения" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
      <msh:ifNotInRole roles="/Policy/Config/ShowAllConfig">
      <ecom:webQuery name="list" nativeSql="
              select id,key,keyvalue,description from softconfig where invisible is null or invisible=false
      "/>
      </msh:ifNotInRole>
      <msh:ifInRole roles="/Policy/Config/ShowAllConfig">
        <ecom:webQuery name="list" nativeSql="
              select id,key,keyvalue,description from softconfig
      "/>
      </msh:ifInRole>
    <msh:table name="list" action="entityView-sec_softConfig.do" idField="1">
      <msh:tableColumn columnName="Ключ" property="2"/>
      <msh:tableColumn columnName="Значение" property="3" />
      <msh:tableColumn columnName="Описание" property="4"/>
    </msh:table>
    </msh:section>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="" action="/entityPrepareCreate-sec_softConfig" name="Новую настройку" title="Добавить новую настройку" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

