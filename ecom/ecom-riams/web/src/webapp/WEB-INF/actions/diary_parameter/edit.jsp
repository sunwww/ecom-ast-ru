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
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-diary_parameter" name="Изменить" roles="/Policy/Diary/ParameterGroup/Parameter/Edit" />
          <msh:sideLink params="id" action="/entityParentPrepareCreate-diary_parameterRef" name="Создать реф. инт." roles="/Policy/Diary/ParameterGroup/Parameter/Create" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="diary_parameterForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-diary_parameter" name="Удалить" roles="/Policy/Diary/ParameterGroup/Parameter/Delete" />
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
    <msh:form action="/entityParentSaveGoView-diary_parameter.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="group" />
      <msh:panel>
       <msh:row>
          <msh:textField property="code" label="Код" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="shortName" label="Короткое название" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="name" label="Полное название" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="type" label="Тип параметра " vocName="parameterType" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="valueDomain" label="Справочник пользовательский" vocName="valueDomain" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="measureUnit" label="Единицы измерения" vocName="vocMeasureUnit" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Референтный интервал" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="normMinimum" label="Минимальное значение" viewOnlyField="true" />
          <msh:textField property="normMaximum" label="Максимальное значение" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Границы допустимых значений" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="minimum" label="Минимальное значение" viewOnlyField="true" />
          <msh:textField property="maximum" label="Максимальное значение" viewOnlyField="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
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
        </msh:ifFormTypeIsView>
    </msh:form>
    
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Config" beginForm="diary_parameterForm" />
  </tiles:put>
</tiles:insert>

