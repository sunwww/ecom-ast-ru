<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc" title="Классификатор лекарственных средств" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-drug_classificator.do" idField="id">
      <msh:tableColumn columnName="Название" property="name" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="" action="/entityPrepareCreate-drug_classificator" name="Категорию" title="Добавить категорию" roles="/Policy/Mis/MedService/Create" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="drugClassif"/>
  </tiles:put>
</tiles:insert>

