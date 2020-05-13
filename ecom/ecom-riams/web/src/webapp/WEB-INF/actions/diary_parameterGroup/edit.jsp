<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="diary_parameterGroup" />
    <msh:sideMenu title="Группа">
      <msh:ifFormTypeIsView formName="diary_parameterGroupForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-diary_parameterGroup" name="Изменить" roles="/Policy/Diary/ParameterGroup/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="diary_parameterGroupForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-diary_parameterGroup" name="Удалить" roles="/Policy/Diary/ParameterGroup/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="diary_parameterGroupForm">
      <msh:sideMenu title="Добавить">
        <msh:sideLink action="/entityParentPrepareCreate-diary_parameterGroup" name="Категорию" params="id" roles="/Policy/Diary/ParameterGroup/Create" />
        <tags:diary_parameterCreate vocName="parameterType" document="параметра" roles="/Policy/Diary/ParameterGroup/Parameter/Create" action="diary_parameterPrepareCreate.do?id=${param.id}" name="type" title="Параметр" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
                    <tags:voc_menu currentAction="diary_param_list" />
      </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Группы параметров
    	  -->
    <msh:form action="/entityParentSaveGoView-diary_parameterGroup.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel colsWidth="20% 30%">
        <msh:row styleId="аа">
          <msh:textField property="name" label="Наименование" size="30" horizontalFill="true" />
        </msh:row>
         <msh:row>
          <ecom:oneToManyOneAutocomplete  viewAction="entityView-secgroup.do" label="Довер. группы" vocName="secGroup" property="secGroups" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:submitCancelButtonsRow colSpan="2" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <msh:ifInRole roles="/Policy/Diary/ParameterGroup/View">
      <msh:ifFormTypeIsView formName="diary_parameterGroupForm">
        <msh:section title="Список категорий" createRoles="/Policy/Diary/ParameterGroup/Create" createUrl="entityParentPrepareCreate-diary_parameterGroup.do?id=${param.id}">
        <ecom:webQuery name="childGroups" nativeSql="select id,name 
        from parametergroup 
        where parent_id=${param.id} order by name"/>
          <msh:table name="childGroups" action="entityParentView-diary_parameterGroup.do" idField="1">
            <msh:tableColumn property="2" columnName="Название" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Diary/ParameterGroup/Parameter/View">
      <msh:ifFormTypeIsView formName="diary_parameterGroupForm">
        <msh:section title="Список параметров" createRoles="/Policy/Diary/ParameterGroup/Parameter/Create" createUrl="javascript:showtypeCreateType()">
         <ecom:webQuery name="parameters" nativeSql="select p.id,p.name
         ,case when p.type='1' then 'Числовой' when p.type='4' then 'Числовой с плавающей точкой зн.'||p.cntDecimal when p.type='2' then 'Пользовательский справочник: '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when p.type='7' then 'Пользовательский справочник (с текстом): '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when p.type='6' then 'Пользовательский справочник (множественный выбор): '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!')
          when p.type='3' then 'Текстовое поле' when p.type='5' then 'Текстовое поле с ограничением' when p.type='8' then 'Дата' else 'неизвестный' end as typeinfo
         ,vmu.name as vmuname
         ,case when p.type='2' or p.type='6' or p.type='7' then vd.id else null end as vdid
         ,p.code
          from parameter p 
          left join userDomain vd on vd.id=p.valueDomain_id 
          left join vocMeasureUnit vmu on vmu.id=p.measureUnit_id
          where p.group_id=${param.id} order by p.name"/>
          <msh:table name="parameters" action="diary_parameterView.do"
           idField="1" editUrl="diary_parameterEdit.do">
           <msh:tableButton property="1" buttonFunction="editType" buttonName="Изменить тип" buttonShortName="ИТ" role="/Policy/Diary/ParameterGroup/Parameter/EditType"/>
          	<msh:tableColumn property="6" columnName="Код"/>
            <msh:tableColumn property="2" columnName="Название" />
          	<msh:tableButton property="5" buttonFunction="viewDomain" buttonShortName="ПН" buttonName="Просмотр справочника в новом окне (для редактирования)" hideIfEmpty="true"/>
          	<msh:tableButton property="5" buttonFunction="viewShortDomain" buttonShortName="П" buttonName="Просмотр справочника" hideIfEmpty="true"/>
            <msh:tableColumn property="3" columnName="Тип" />
            <msh:tableColumn property="4" columnName="Ед.изм" />
          </msh:table>
        </msh:section>
      </msh:ifFormTypeIsView>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <script type="text/javascript">
  function editType(aId) {
	  showtypeCreateType(aId) ;
  }
  function viewDomain(aId) {
  		window.open("entityView-diary_userDomain.do?id="+aId) ;
  	}
  function viewShortDomain(aId) {
	  getDefinition("entityView-diary_userDomain.do?short=Short&id="+aId)
  }
  </script>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_parameterGroupForm" />
  </tiles:put>
</tiles:insert>

