<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Категории классификатора шаблонов
    	  -->
    <msh:form action="/entityParentSaveGoView-temp_category.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:textField property="name" label="Название категории" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textArea property="comments" label="Комментарии" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" />
          <msh:textField property="dateCreate" label="Дата создания" viewOnlyField="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="temp_categoryForm">
      <msh:section title="Категории">
        <ecom:parentEntityListAll attribute="child" formName="temp_categoryForm" />
        <msh:table name="child" action="entityParentView-temp_category.do" idField="id" noDataMessage="Нет данных">
          <msh:tableColumn property="name" columnName="Название" />
          <msh:tableColumn property="comments" columnName="Комментарии" />
          <msh:tableColumn property="dateCreate" columnName="Дата создания" />
          <msh:tableColumn property="username" columnName="Пользователь" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="temp_categoryForm">
      <msh:section title="Протоколы">
        <ecom:webQuery name="protocols" nativeSql="select p.id,p.title,p.text,p.username from TemplateProtocol_TemplateCategory pc left join TemplateProtocol p on p.id=pc.protocols_id where pc.categories_id='${param.id}' group by p.id,p.title,p.text,p.username order by p.title" />
	    <msh:table name="protocols" action="entityView-temp_protocol.do" idField="1">
          <msh:tableColumn property="2" columnName="Название" />
          <msh:tableColumn property="3" columnName="Комментарии" />
	      <msh:tableColumn columnName="Пользователь" property="4" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>    
    <msh:ifFormTypeIsView formName="temp_categoryForm">
      <msh:section title="Листы назначений">
        <ecom:webQuery name="prescriptionLists" nativeSql="select pl.id,pl.name,pl.createusername from PrescriptionList_templatecategory pltc 
        left join PrescriptionList pl on pl.id=pltc.prescriptlists_id where pltc.categories_id='${param.id}' group by pl.id,pl.name,pl.createusername order by pl.name" />
	    <msh:table name="prescriptionLists" action="entityView-pres_template.do" idField="1">
          <msh:tableColumn property="2" columnName="Название" />
          <msh:tableColumn columnName="Пользователь" property="3" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="temp_categoryForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="temp_categoryForm">
      <msh:sideMenu title="Категория">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-temp_category" name="Изменить" roles="/Policy/Mis/Template/Category/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-temp_category" name="Удалить" roles="/Policy/Mis/Template/Category/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink name="Категорию" key="ALT+2" params="id" action="/entityParentPrepareCreate-temp_category" roles="/Policy/Mis/Template/Category/Create" />
      </msh:sideMenu>
      <msh:sideMenu title="Перейти">
        <msh:sideLink key="ALT+9" action="/entityParentList-temp_category.do?id=0" name="⇧К началу классификатора" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

