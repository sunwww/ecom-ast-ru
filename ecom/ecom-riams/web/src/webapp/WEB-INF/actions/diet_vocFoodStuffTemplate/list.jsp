<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Diet">Список шаблонов продуктов питания</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuffTemplate/Create" key="ALT+N" action="/entityPrepareCreate-diet_vocFoodStuffTemplate" name="Добавить шаблон продукта питания" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityView-diet_vocFoodStuffTemplate.do" idField="id">
      <msh:tableColumn columnName="ИД" property="foodStuff" />
      <msh:tableColumn columnName="Наименование" property="gross" />
      <msh:tableColumn columnName="Месяц начала действия" identificator="false" property="monthStartText" />
      <msh:tableColumn columnName="Месяц окончания действия" identificator="false" property="monthEndText" />
    </msh:table>
  </tiles:put>
</tiles:insert>

