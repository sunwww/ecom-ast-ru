<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient">Проба</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink key="ALT+N" action="/entityParentList-rec_time" name="Выбрать другое время" title="Выбрать другое время для записи на прием" params="id" />
      <msh:sideLink params="id" action="/entityParentPrepareCreate-rec_recording" name="Записаться" title="Записаться на прием" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-rec_date.do" idField="id">
      <msh:tableColumn columnName="Дата" property="id" />
      <msh:tableColumn columnName="Первое свободное время" property="hello" />
      <msh:tableColumn columnName="Выбор" property="parent" />
    </msh:table>
  </tiles:put>
</tiles:insert>

