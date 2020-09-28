<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="diary_userDomain" />
    <msh:sideMenu title="Пользовательский справочник">
      <msh:ifFormTypeIsView formName="diary_userDomainForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityEdit-diary_userDomain" name="Изменить" roles="/Policy/Diary/User/Domain/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="diary_userDomainForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-diary_userDomain" name="Удалить" roles="/Policy/Diary/User/Domain/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="diary_userDomainForm">
      <msh:sideMenu title="Добавить">
        <msh:sideLink action="/entityParentPrepareCreate-diary_userValue" name="Значение справочника" params="id" roles="/Policy/Diary/User/Domain/Value/Create" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
                    <tags:voc_menu currentAction="diary_user_voc" />

      </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Медицинских услуг
    	  -->
    <msh:form action="/entitySaveGoView-diary_userDomain.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel >
        <msh:row>
          <msh:textField property="name" label="Название" size="50" horizontalFill="true" />
        </msh:row>
         <msh:row>
          <msh:textField property="code" label="Код" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
    <msh:ifInRole roles="/Policy/Mis/MedService/View">
      <msh:ifFormTypeIsView formName="diary_userDomainForm">
        <msh:section title="Значения справочника" createUrl="entityParentPrepareCreate-diary_userValue.do?id=${param.id}">
          <ecom:parentEntityListAll attribute="values" formName="diary_userValueForm" />
          <msh:table name="values" action="entityParentView-diary_userValue.do" 
          idField="id" deleteUrl="entityParentDeleteGoParentView-diary_userValue.do" editUrl="entityParentEdit-diary_userValue.do" >
            <msh:tableColumn property="name" columnName="Значение" />
            <msh:tableColumn property="cntBall" columnName="Балл" />
          </msh:table>
        </msh:section>
                  <ecom:webQuery name="parameters" nativeSql="select p.id,p.name
         ,case when p.type='1' then 'Числовой' when p.type='4' then 'Числовой с плавающей точкой зн.'||p.cntDecimal when p.type='2' then 'Пользовательский справочник: '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when p.type='7' then 'Пользовательский справочник (с текстом): '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when p.type='6' then 'Пользовательский справочник (множественный выбор): '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when p.type='3' then 'Текстовое поле' when p.type='5' then 'Текстовое поле с ограничением' else 'неизвестный' end as typeinfo
         ,vmu.name as vmuname
         ,p.code
          from parameter p 
          left join userDomain vd on vd.id=p.valueDomain_id 
          left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id
          where p.valueDomain_id=${param.id} order by p.name"/>
          <msh:tableNotEmpty name="parameters">
        <msh:section title="Список параметров, где используется справочник">
          <msh:table name="parameters" action="diary_parameterView.do" idField="1">
          	<msh:tableColumn property="5" columnName="Код"/>
            <msh:tableColumn property="2" columnName="Название" />
            <msh:tableColumn property="3" columnName="Тип" />
            <msh:tableColumn property="4" columnName="Ед.изм" />
          </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
        
      </msh:ifFormTypeIsView>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_userDomainForm" />
  </tiles:put>
</tiles:insert>

