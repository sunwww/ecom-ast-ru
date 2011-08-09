<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSave-mis_mortalityReportDate.do" defaultField="reportDate">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden property="lpu" guid="8cc5cfd7-ecd4-4573-bc99-62c548cd763e" />
      <msh:row guid="e510fd94-9a7d-4245-970b-b2d670d20c2c" />
      <msh:panel guid="panel" colsWidth="10%,5%,1%,5%">
        <msh:row guid="row-1">
          <msh:textField guid="e71fa83a-c6c2-4221-0-1" property="reportDate" label="Дата отчета" labelColSpan="2" />
        </msh:row>
        <msh:row guid="7e0172b7-1351-483d-b32a-71997b631ed3">
          <th colspan="1">Возраст</th>
          <th colspan="1" style="text-align: right; padding-right: 5px ;">Мужчины</th>
          <th colspan="2" style="text-align: right; padding-right: 5px ;">Женщины</th>
          <td />
        </msh:row>
        <msh:row guid="row0">
          <msh:textField guid="e71fa83a-c6c2-4221-0" property="menAge0" label="до 1 года" />
          <msh:textField property="womenAge0" guid="e71fa83a-c6c2-4221-bb72-77067f879971" hideLabel="true" />
        </msh:row>
        <msh:row guid="row1">
          <msh:textField guid="e71fa83a-c6c2-4221-1" property="menAge1" label="1-4 года" />
          <msh:textField property="womenAge1" guid="e71fa83a-c6c2-4221-bb72-1" hideLabel="true" />
        </msh:row>
        <msh:row guid="row2">
          <msh:textField guid="e71fa83a-c6c2-4221-2" property="menAge5" label="5-9 года" />
          <msh:textField property="womenAge5" guid="e71fa83a-c6c2-4221-bb72-2" hideLabel="true" />
        </msh:row>
        <msh:row guid="row3">
          <msh:textField guid="e71fa83a-c6c2-4221-3" property="menAge10" label="10-14 лет" />
          <msh:textField property="womenAge10" guid="e71fa83a-c6c2-4221-bb72-3" hideLabel="true" />
        </msh:row>
        <msh:row guid="row4">
          <msh:textField guid="e71fa83a-c6c2-4221-4" property="menAge15" label="15-17 лет" />
          <msh:textField property="womenAge15" guid="e71fa83a-c6c2-4221-bb72-4" hideLabel="true" />
        </msh:row>
        <msh:row guid="row5">
          <msh:textField guid="e71fa83a-c6c2-4221-5" property="menAge18" label="18-24 лет" />
          <msh:textField property="womenAge18" guid="e71fa83a-c6c2-4221-bb72-5" hideLabel="true" />
        </msh:row>
        <msh:row guid="row6">
          <msh:textField guid="e71fa83a-c6c2-4221-6" property="menAge25" label="25-34 лет" />
          <msh:textField property="womenAge25" guid="e71fa83a-c6c2-4221-bb72-6" hideLabel="true" />
        </msh:row>
        <msh:row guid="row7">
          <msh:textField guid="e71fa83a-c6c2-4221-7" property="menAge35" label="35-44 лет" />
          <msh:textField property="womenAge35" guid="e71fa83a-c6c2-4221-bb72-7" hideLabel="true" />
        </msh:row>
        <msh:row guid="row8">
          <msh:textField guid="e71fa83a-c6c2-4221-8" property="menAge45" label="45-54 лет" />
          <msh:textField property="womenAge45" guid="e71fa83a-c6c2-4221-bb72-8" hideLabel="true" />
        </msh:row>
        <msh:row guid="row9">
          <msh:textField guid="e71fa83a-c6c2-4221-9" property="menAge55" label="55-64 лет" />
          <msh:textField property="womenAge55" guid="e71fa83a-c6c2-4221-bb72-9" hideLabel="true" />
        </msh:row>
        <msh:row guid="row10">
          <msh:textField guid="e71fa83a-c6c2-4221-10" property="menAge65" label="65 и старше лет" />
          <msh:textField property="womenAge65" guid="e71fa83a-c6c2-4221-bb72-10" hideLabel="true" />
        </msh:row>
        <msh:row guid="f512b669-5459-4fa6-a03b-e7977bf984dd">
          <msh:checkBox hideLabel="false" property="editComplete" viewOnlyField="false" guid="c50cc973-8f7f-4284-93f8-a59cc355385b" horizontalFill="false" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" labelSave="Сохранить" labelSaving="Сохранение" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Lpu" beginForm="mis_mortalityReportDateForm" />
  </tiles:put>
  <tiles:put name="side" type="string" />
</tiles:insert>

