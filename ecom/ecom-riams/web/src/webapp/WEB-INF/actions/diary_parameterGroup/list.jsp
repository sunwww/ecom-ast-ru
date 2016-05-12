<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Группа параметров" guid="dd8765db-fd78-4593-98ff-d87a7a48d574" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-diary_parameterGroup.do" idField="id" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn columnName="Название" property="name" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn identificator="false" property="parametersInfo" guid="eeebd620-971b-48a9-ba20-3db4703e1af8" columnName="Прикрепленная услуга" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="b33faf64-b72e-4845-bf32-5fda8e274fc3">
      <msh:sideLink action="/entityPrepareCreate-diary_parameterGroup" name="Создать" title="Создать" guid="dc488234-9da8-4290-9e71-3b4558d27ec7" roles="/Policy/Mis/MedService/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
            <tags:voc_menu currentAction="diary_param_list" />

    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

