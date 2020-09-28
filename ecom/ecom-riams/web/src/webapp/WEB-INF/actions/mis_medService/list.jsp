<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc" title="Кодификатор" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-mis_medService.do" idField="id">
      <msh:tableColumn columnName="Название" property="name" />
      <msh:tableColumn identificator="false" property="vocMedServiceInfo" columnName="Прикрепленная услуга" />
      <msh:tableColumn columnName="Дата начала" property="startDate"/>
      <msh:tableColumn columnName="Дата окончания" property="finishDate"/>
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="" action="/entityPrepareCreate-mis_medService" name="Создать" title="Создать" roles="/Policy/Mis/MedService/Create" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="medService"/>
  </tiles:put>
</tiles:insert>

