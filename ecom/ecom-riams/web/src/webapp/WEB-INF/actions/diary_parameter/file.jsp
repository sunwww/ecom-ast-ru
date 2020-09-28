<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="diary_parameterGroup" />
    <msh:sideMenu title="Параметр">
      <msh:ifFormTypeIsView formName="diary_parameterForm">
        <msh:sideLink key="ALT+1" params="id" action="/diary_parameterEdit" name="Изменить" roles="/Policy/Diary/ParameterGroup/Parameter/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="diary_parameterForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-diary_parameter" name="Удалить" roles="/Policy/Diary/ParameterGroup/Parameter/Delete" />
        <msh:sideLink params="id" action="/entityParentPrepareCreate-diary_parameterRef" name="Создать реф инт" roles="/Policy/Diary/ParameterGroup/Parameter/Create" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
                  <tags:voc_menu currentAction="diary_param_list" />

    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Параметр
    	  -->
    <msh:form action="/diary_parameterSave.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="group" />
      <msh:hidden property="type" />
      <msh:panel>
        ${paramform} ${shortName}
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="diary_parameterForm">
      <msh:section title="Перечень референтных значений">
        <ecom:webQuery name="refValues"
                       nativeSql="
    select rv.id, rv.normaMin, rv.normaMax, rv.superMin, rv.superMax, rv.ageFrom, rv.ageTo, vs.name
from ParameterReferenceValue rv
left join vocSex vs on vs.id=rv.sex_id
where rv.parameter_id=${param.id} order by rv.ageFrom
    "
        />
        <msh:table name="refValues" action="entityParentEdit-diary_parameterRef.do" idField="1">
          <msh:tableColumn property="6" columnName="Возраст от"/>
          <msh:tableColumn property="7" columnName="Возраст до"/>
          <msh:tableColumn property="8" columnName="Пол"/>
          <msh:tableColumn property="2" columnName="Норма от" />
          <msh:tableColumn property="3" columnName="Норма до" />
          <msh:tableColumn property="5" columnName="Мин значение" />
          <msh:tableColumn property="4" columnName="Макс значение" />
        </msh:table>
      </msh:section>

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
            <msh:tableColumn property="2" columnName="Значение" />
            <msh:tableColumn property="3" columnName="Балл" />
          </msh:table>
        </msh:section></msh:tableNotEmpty>
    </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_parameterForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="diary_parameterForm">
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

