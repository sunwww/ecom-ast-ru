<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config" title="Группа параметров" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-diary_parameterGroup.do" idField="id">
      <msh:tableColumn columnName="Название" property="name" />
      <msh:tableColumn identificator="false" property="parametersInfo" columnName="Прикрепленная услуга" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink action="/entityPrepareCreate-diary_parameterGroup" name="Создать" title="Создать" roles="/Policy/Mis/MedService/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
            <tags:voc_menu currentAction="diary_param_list" />

    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

