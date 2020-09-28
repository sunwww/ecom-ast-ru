<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entitySaveGoView-voc_workFunction.do" defaultField="code">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <msh:textField label="Код" property="code" />
          <msh:textField label="Наименование" property="name" size="80" />
        </msh:row>
        <msh:row>
        	<msh:textField property="shortName"  label="Короткое название"/>
          <msh:checkBox property="isNoOmc" />

        </msh:row>
        <msh:row>
          <msh:checkBox property="isRadiationDiagnosis" label="Лучевая диагностика"/>
        	<msh:checkBox property="isFuncDiag" label="Функциональная диагностика"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="isLab" label="Лаборатория"/>
        	<msh:checkBox property="isNoDiagnosis" label="Не заполняется диагноз"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isNo039" label="Не включать в 039 форму"/>
        	<msh:checkBox property="isDiaryTitle" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="medHelpProfile" label="Профиль мед. помощи" vocName="vocE2MedHelpProfile" size="50"/>

        </msh:row>
        <msh:row>
          <msh:autoComplete horizontalFill="true" property="vocPost" vocName="vocPost" label="Должность" fieldColSpan="3" viewAction="entityView-voc_post.do" />
        </msh:row>
        <msh:row>
          <msh:autoComplete horizontalFill="true" property="fondSpeciality" vocName="vocE2FondV021" label="Профиль V021" fieldColSpan="3" viewAction="entityView-e2_vocFondV021.do"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="isSuitForCovid" label="Можно назначать в инфекционном?"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="voc_workFunctionForm">
      <msh:section>
        <msh:sectionTitle>Рабочие функции специалистов</msh:sectionTitle>
        <msh:sectionContent>
          <ecom:webQuery name="workFuncs" nativeSql="select wf.id, wp.lastname||' '||wp.firstname||' '||wp.middlename,ml.name,gr.groupname from WorkFunction wf left join Worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id left join MisLpu ml on ml.id=w.lpu_id        left join WorkFunction gr on gr.id=wf.group_id       where wf.DTYPE='PersonalWorkFunction' and wf.workFunction_id='${param.id}'" />
          <msh:table name="workFuncs" action="entitySubclassView-work_workFunction.do" idField="1">
            <msh:tableColumn property="sn" columnName="#" />
            <msh:tableColumn columnName="Сотрудник" property="2" />
            <msh:tableColumn columnName="Подразделение" property="3" />
            <msh:tableColumn columnName="Рабочая группа" property="4" />
          </msh:table>
        </msh:sectionContent>
        <msh:sectionTitle>Групповые рабочие функции</msh:sectionTitle>
        <msh:sectionContent>
          <ecom:webQuery name="workFuncGrs" nativeSql="select wf.id,wf.groupname,ml.name as name from WorkFunction wf left join MisLpu ml on ml.id=wf.lpu_id        where wf.DTYPE='GroupWorkFunction' and wf.workFunction_id='${param.id}'" />
          <msh:table name="workFuncGrs" action="entitySubclassView-work_workFunction.do" idField="1">
            <msh:tableColumn property="sn" columnName="#" />
            <msh:tableColumn columnName="Название" property="2" />
            <msh:tableColumn columnName="Подразделение" property="3" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_workFunctionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Справочник рабочих функций">
      <msh:sideLink roles="/Policy/Voc/VocWorkFunction/Edit" key="ALT+2" params="id" action="/entityEdit-voc_workFunction" name="Изменить" title="Изменить данные по рабочей функции" />
      <msh:sideLink roles="/Policy/Voc/VocWorkFunction/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-voc_workFunction" name="Удалить" title="Удалить данные рабочей функции" />
    </msh:sideMenu>
        <tags:voc_menu currentAction="mnWorkFunction"/>
  </tiles:put>
</tiles:insert>

