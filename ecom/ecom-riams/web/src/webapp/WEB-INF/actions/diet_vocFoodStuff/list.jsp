<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Diet">Список продуктов питания</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/Create" key="ALT+N" action="/entityPrepareCreate-diet_vocFoodStuff" name="Добавить продукт питания" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/diet_foodStuffs.do" defaultField="name" disableFormDataConfirm="true" method="GET">
      <msh:panel colsWidth="10%, 10%, 70%">
        <msh:row>
          <msh:textField property="name" label="Наименование" size="40" fieldColSpan="2" horizontalFill="true" />
          <td>
            <input type="submit" value="Найти" />
          </td>
        </msh:row>
        <msh:row>
          <msh:commentBox text="Наименование продукта питания." colSpan="2" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <msh:hideException>
    <msh:section title="Результат поиска">
      <msh:table name="list" action="entityView-diet_vocFoodStuff.do" idField="id" disableKeySupport="true" noDataMessage="Не найдено">
          <msh:tableColumn columnName="Наименование" property="name" />
          <msh:tableColumn columnName="Белки" property="proteins" />
          <msh:tableColumn columnName="Жиры" property="lipids" />
          <msh:tableColumn columnName="Углеводы" property="carbohydrates" />
          <msh:tableColumn columnName="Энергоценность" property="calorieContent" />
        </msh:table>
    </msh:section>
    </msh:hideException>
  </tiles:put>
</tiles:insert>

