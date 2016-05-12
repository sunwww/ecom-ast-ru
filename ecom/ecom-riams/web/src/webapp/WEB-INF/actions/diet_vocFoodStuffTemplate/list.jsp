<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Diet">Список шаблонов продуктов питания</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="352ff4-0a5e-475a-a3e1-e204d">
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuffTemplate/Create" key="ALT+N" action="/entityPrepareCreate-diet_vocFoodStuffTemplate" name="Добавить шаблон продукта питания" guid="a954fa1-4b2d-4762-8ee9-9a412" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_vocFoodStuffTemplate.do" idField="id" guid="0d2l5-1d31-4e70-8174-294l">
      <msh:tableColumn columnName="ИД" property="foodStuff" guid="9cc3214-3929-4b9a-9892-00" />
      <msh:tableColumn columnName="Наименование" property="gross" guid="32588-3122-425f-9f66-0c51c" />
      <msh:tableColumn columnName="Месяц начала действия" identificator="false" property="monthStartText" guid="b6321d-19e1-4e97-a096-893eeda23f53" />
      <msh:tableColumn columnName="Месяц окончания действия" identificator="false" property="monthEndText" guid="89884b-5a07-4f97-9e10-f69fabda91c3" />
    </msh:table>
  </tiles:put>
</tiles:insert>

