<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Patient" title="Список картотек" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-voc_cardIndex.do" idField="id" >
      <msh:tableColumn columnName="Код" property="code"/>
      <msh:tableColumn columnName="Наименование" property="name"/>
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" >
      <msh:sideLink params="id" action="/entityParentPrepareCreate-voc_cardIndex" 
      name="Картотеку" title="Добавить картотеку" 
      />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

