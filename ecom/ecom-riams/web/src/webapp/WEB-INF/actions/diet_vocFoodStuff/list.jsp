<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Diet">Список продуктов питания</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="352799f4-0a5e-475a-a3e1-e204d">
      <msh:sideLink roles="/Policy/Mis/InvalidFood/VocFoodStuff/Create" key="ALT+N" action="/entityPrepareCreate-diet_vocFoodStuff" name="Добавить продукт питания" guid="a9fa1-4b2d-4762-8ee9-9a412" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/diet_foodStuffs.do" defaultField="name" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
      <msh:panel colsWidth="10%, 10%, 70%" guid="354f9651-7a86-447b-9066-43af5b3bf277">
        <msh:row guid="68523c-58d4-45f6-928e-2741b5b">
          <msh:textField property="name" label="Наименование" size="40" guid="56502d8a-33ae-463c-910b-51238" fieldColSpan="2" horizontalFill="true" />
          <td>
            <input type="submit" value="Найти" />
          </td>
        </msh:row>
        <msh:row guid="b7853a-a47b-437e-8d1c-b6380">
          <msh:commentBox text="Наименование продукта питания." guid="5c136b1-df55-446f-ada6-da14a6c" colSpan="2" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <msh:hideException>
    <msh:section title="Результат поиска" guid="9c3571f6-7d65-4c2d-958b-26b6d9ec06a7">
      <msh:table name="list" action="entityView-diet_vocFoodStuff.do" idField="id" disableKeySupport="true" guid="719506-d2f7-4055-98a4-3b687377d9be" noDataMessage="Не найдено">
          <msh:tableColumn columnName="Наименование" property="name" guid="8725-a164-4c5f-8fa9-5501c300bbf2" />
          <msh:tableColumn columnName="Белки" property="proteins" guid="88842354-b7d1-4c67-a43e-988525d1" />
          <msh:tableColumn columnName="Жиры" property="lipids" guid="4b8cb842-fcfb-4e91-b57f-ed4565" />
          <msh:tableColumn columnName="Углеводы" property="carbohydrates" guid="e63b0a34-7d09-4345-98c9-d3254" />
          <msh:tableColumn columnName="Энергоценность" property="calorieContent" guid="210f1c10-2013-4a05-8ceb-a9852e" />
        </msh:table>
    </msh:section>
    </msh:hideException>
  </tiles:put>
</tiles:insert>

