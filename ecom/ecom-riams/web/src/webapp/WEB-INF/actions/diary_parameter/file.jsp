<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="diary_parameterGroup" />
    <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Параметр">
      <msh:ifFormTypeIsView formName="diary_parameterForm" guid="e2054544-85-a21c-3bb9b4569efc">
        <msh:sideLink key="ALT+1" params="id" action="/diary_parameterEdit" name="Изменить" roles="/Policy/Diary/ParameterGroup/Parameter/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="diary_parameterForm" guid="a6802286-1d60-46ea-b7f4-f588331a09f7">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-diary_parameter" name="Удалить" roles="/Policy/Diary/ParameterGroup/Parameter/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно" guid="9e0388c8-2666-4d66-b865-419c53ef9f89">
                  <tags:voc_menu currentAction="diary_param_list" />

    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Параметр
    	  -->
    <msh:form action="/diary_parameterSave.do" defaultField="name" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:hidden property="group" guid="bd32944-4587-a963-a09db2b93caf" />
      <msh:hidden property="type" guid="d604d1e6-ab84-4573-b527-216b1acc9116" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        ${paramform} ${shortName}
        <msh:submitCancelButtonsRow colSpan="3" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diary_parameterForm">
    <msh:section title="Список шаблонов, где присутствует данный параметер">
    <ecom:webQuery name="list_template"
    nativeSql="
    select p.template_id,tp.title as tptitle
,ms.id as msid,pms.name as pmsname
,ms.code||' ('||ms.additioncode||') '||ms.name||' ('||coalesce(ms.shortname,'НЕТ СОКРАЩЕННОГО НАИМЕНОВАНИЯ!!!!')||') ' as msname

from ParameterByForm p 
left join TemplateProtocol tp on tp.id=p.template_id
left join MedService ms on ms.id=tp.medService_id
left join MedService pms on pms.id=ms.parent_id
where p.parameter_id=${param.id} order by p.position
    "
    />
    <msh:table name="list_template" action="entityParentView-diary_template.do" idField="1">
    	<msh:tableButton property="1" buttonFunction="goNewOpen" buttonName="o" buttonShortName="o" addParam="'entityParentView-diary_template.do'"/>
    	<msh:tableColumn property="2" columnName="Шаблон"/>
    	<msh:tableColumn property="4" columnName="Категория"/>
    	<msh:tableColumn property="5" columnName="Наименование услуги"/>
    </msh:table>
    </msh:section>
    
          <ecom:webQuery nativeSql="
          select uv.id , uv.name, uv.cntBall from UserValue uv
          left join userdomain ud on ud.id=uv.domain_id
          left join parameter p on p.ValueDomain_id=ud.id
          where p.id=${param.id }
          " name="values" />
    
    <msh:tableNotEmpty name="values"><msh:section title="Значения справочника" >
          <msh:table name="values" action="entityParentView-diary_userValue.do" 
          idField="1" deleteUrl="entityParentDeleteGoParentView-diary_userValue.do" editUrl="entityParentEdit-diary_userValue.do" >
            <msh:tableColumn property="2" columnName="Значение" guid="2fd022ea-59b0-4cc9-a8ce-0ed4a3ddc91f" />
            <msh:tableColumn property="3" columnName="Балл" guid="2fd022ea-59b0-4cc9-a8ce-0ed4a3ddc91f" />
          </msh:table>
        </msh:section></msh:tableNotEmpty>
    </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_parameterForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="diary_parameterForm" guid="76f69ba0-a7b7-4cdb-8007-4de4ae2836ec">
      <script type="text/javascript">${paramscript}
      
      </script>
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView formName="diary_parameterForm">
    <script type="text/javascript">${paramscript}
      function goNewOpen(aId,aUrl) {
    	  window.open( aUrl+"?id="+aId) ;
      }
      </script>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

