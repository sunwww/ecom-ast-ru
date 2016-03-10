<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Категории классификатора шаблонов
    	  -->
    <msh:form action="/entityParentSaveGoView-temp_category.do" defaultField="name" guid="ea411ae6-6822-4cbd-a7f3-b8f6cfa1beba">
      <msh:hidden property="id" guid="ba8ca3c4-0044-44ab-bb12-a75e3441fae2" />
      <msh:hidden property="saveType" guid="efb8a9d9-e3c6-4f03-87bc-f0cccb820e89" />
      <msh:hidden property="parent" guid="a896bb6b-4615-466f-b2eb-a29f942abedd" />
      <msh:panel guid="8f2d40f7-6118-4da3-b209-3529333490ec">
        <msh:row guid="5918647f-c1fd-439b-86d8-61afa1aeacf4">
          <msh:textField property="name" label="Название категории" guid="b090cdd7-a4ff-45da-ada0-9d27ef61c323" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="154fb2a0-a3ac-4034-9cbb-087444dbe299">
          <msh:textArea property="comments" label="Комментарии" fieldColSpan="3" horizontalFill="true" guid="f5338dbf-03ae-4c9c-a2ee-e6a3cc240dff" />
        </msh:row>
        <msh:row guid="fdabbac6-97a9-4171-af88-2506106b38a3">
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" guid="d4f27875-9c0e-4b2d-807a-e4b1d002ec30" />
          <msh:textField property="dateCreate" label="Дата создания" viewOnlyField="true" guid="4f0598d7-0dfd-414a-acef-274fb76dda94" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="temp_categoryForm" guid="770fc32b-aee3-426b-9aba-6f6af9de6c9d">
      <msh:section title="Категории" guid="712b744d-be86-4dc1-9d3a-0ab52eb1bed9">
        <ecom:parentEntityListAll attribute="child" formName="temp_categoryForm" guid="5d68c148-4501-4a12-84a1-6f13708a3c4b" />
        <msh:table name="child" action="entityParentView-temp_category.do" idField="id" noDataMessage="Нет данных" guid="123c019a-f668-4454-af88-4897d27728ab">
          <msh:tableColumn property="name" columnName="Название" guid="4d4c6566-75c9-4ef5-931c-723e88d4efbb" />
          <msh:tableColumn property="comments" columnName="Комментарии" guid="f6c2e5ba-4045-4dbe-b8fb-bb5d8386e9c4" />
          <msh:tableColumn property="dateCreate" columnName="Дата создания" guid="1102c0d7-1116-481a-b10b-34d6806d79f2" />
          <msh:tableColumn property="username" columnName="Пользователь" guid="6ca58b37-99f2-41e1-82ba-344e0bd7b767" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsView formName="temp_categoryForm" guid="770fc32b-aee3-426b-9aba-6f6af9de6c9d">
      <msh:section title="Протоколы" guid="712b744d-be86-4dc1-9d3a-0ab52eb1bed9">
        <ecom:webQuery name="protocols" nativeSql="select p.id,p.title,p.text,p.username from TemplateProtocol_TemplateCategory pc left join TemplateProtocol p on p.id=pc.protocols_id where pc.categories_id='${param.id}' group by p.id,p.title,p.text,p.username order by p.title" />
	    <msh:table name="protocols" action="entityView-temp_protocol.do" idField="1" guid="c0ba5c22-fda6-4d4f-89c4-aa58abb1e9d8">
          <msh:tableColumn property="2" columnName="Название" guid="4d4c6566-75c9-4ef5-931c-723e88d4efbb" />
          <msh:tableColumn property="3" columnName="Комментарии" guid="f6c2e5ba-4045-4dbe-b8fb-bb5d8386e9c4" />
	      <msh:tableColumn columnName="Пользователь" property="4" guid="c28f06f0-c64a-4cdd-b84f-b1e081186496" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>    
    <msh:ifFormTypeIsView formName="temp_categoryForm" guid="770fc32b-aee3-426b-9aba-6f6af9de6c9d">
      <msh:section title="Листы назначений" guid="712b744d-be86-4dc1-9d3a-0ab52eb1bed9">
        <ecom:webQuery name="prescriptionLists" nativeSql="select pl.id,pl.name,pl.createusername from PrescriptionList_templatecategory pltc 
        left join PrescriptionList pl on pl.id=pltc.prescriptlists_id where pltc.categories_id='${param.id}' group by pl.id,pl.name,pl.createusername order by pl.name" />
	    <msh:table name="prescriptionLists" action="entityView-pres_template.do" idField="1" guid="c0ba5c22-fda6-4d4f-89c4-aa58abb1e9d8">
          <msh:tableColumn property="2" columnName="Название" guid="4d4c6566-75c9-4ef5-931c-723e88d4efbb" />
          <msh:tableColumn columnName="Пользователь" property="3" guid="c28f06f0-c64a-4cdd-b84f-b1e081186496" />
        </msh:table>
      </msh:section>
    </msh:ifFormTypeIsView>    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Template" beginForm="temp_categoryForm" guid="af3d88cd-60b5-4bba-a85d-ac2334f43161" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="temp_categoryForm" guid="d4c560e9-6ddb-4cf2-9375-4caf7f0d3fb8">
      <msh:sideMenu title="Категория" guid="2742309d-41bf-4fbe-9238-2f895b5f79a9">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-temp_category" name="Изменить" roles="/Policy/Mis/Template/Category/Edit" guid="0f0781cd-81dd-4da2-aba5-67eab700b161" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-temp_category" name="Удалить" roles="/Policy/Mis/Template/Category/Delete" guid="99bf20e3-4292-4554-bd60-051aa4338ee1" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="1c73d7e7-8cad-49ef-9c02-af573d2dd929">
        <msh:sideLink name="Категорию" key="ALT+2" params="id" action="/entityParentPrepareCreate-temp_category" roles="/Policy/Mis/Template/Category/Create" guid="d31fca56-2793-4068-a7a5-f62a43a050ce" />
      </msh:sideMenu>
      <msh:sideMenu title="Перейти" guid="4943cb98-adb2-4c2d-9668-e973ee0ed67f">
        <msh:sideLink key="ALT+9" action="/entityParentList-temp_category.do?id=0" name="⇧К началу классификатора" guid="07f2bb72-26b3-4609-a19a-7dffebdd0171" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

