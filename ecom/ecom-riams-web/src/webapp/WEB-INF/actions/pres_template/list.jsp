<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Template" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Шаблоны листов назначений" />
  </tiles:put>
  <tiles:put name="side" type="string">
    
    <msh:sideMenu title="Добавить" guid="60616958-11ef-48b0-bec7-f6b1d0b8463f">
      <msh:sideLink roles="/Policy/Mis/Prescription/Template/Create" key="ALT+N" action="/entityPrepareCreate-pres_template" name="Шаблон листа назначений" guid="1faa5477-419b-4f77-8379-232e33a61922" />
      <msh:sideLink action=" javascript:shownewTemplatePrescription(1,&quot;.do&quot;)" name="Шаблон ЛН на основе существующего" title="Создать шаблон лист назначения на основе существующего шаблона" guid="c6e48b9d-d1cf-4731-af04-3f8fe356717e" />
    </msh:sideMenu>
        <tags:template_menu currentAction="prescriptions"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-pres_template.do" idField="id" guid="3c4adc65-cfce-4205-a2dd-91ba8ba87543" navigationAction="js-pres_template-listTemplate.do">
      <msh:tableColumn columnName="Название" property="name" />
      <msh:tableColumn columnName="Комментарии" property="comments" guid="5c893448-9084-4b1a-b301-d7aca8f6307c" cssClass="preCell"/>
      <msh:tableColumn columnName="Владелец" property="workFunctionInfo" guid="44482100-2200-4c8b-9df5-4f5cc0e3fe68" />
      <msh:tableColumn columnName="Дата создания" property="date" guid="dbe4fc52-03f7-42af-9555-a4bee397a800" />
      <msh:tableColumn columnName="Создан" property="username" guid="715694de-3af4-4395-b777-2cb19bdbcf62" />
    </msh:table>
    <tags:templatePrescription record="2" parentId="0" name="new" />
  </tiles:put>
</tiles:insert>

