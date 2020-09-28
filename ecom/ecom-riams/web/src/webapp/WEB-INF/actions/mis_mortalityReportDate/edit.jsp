<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entityParentSave-mis_mortalityReportDate.do" defaultField="reportDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpu" />
      <msh:row />
      <msh:panel colsWidth="10%,5%,1%,5%">
        <msh:row>
          <msh:textField property="reportDate" label="Дата отчета" labelColSpan="2" />
        </msh:row>
        <msh:row>
          <th colspan="1">Возраст</th>
          <th colspan="1" style="text-align: right; padding-right: 5px ;">Мужчины</th>
          <th colspan="2" style="text-align: right; padding-right: 5px ;">Женщины</th>
          <td />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge0" label="до 1 года" />
          <msh:textField property="womenAge0" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge1" label="1-4 года" />
          <msh:textField property="womenAge1" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge5" label="5-9 года" />
          <msh:textField property="womenAge5" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge10" label="10-14 лет" />
          <msh:textField property="womenAge10" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge15" label="15-17 лет" />
          <msh:textField property="womenAge15" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge18" label="18-24 лет" />
          <msh:textField property="womenAge18" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge25" label="25-34 лет" />
          <msh:textField property="womenAge25" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge35" label="35-44 лет" />
          <msh:textField property="womenAge35" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge45" label="45-54 лет" />
          <msh:textField property="womenAge45" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge55" label="55-64 лет" />
          <msh:textField property="womenAge55" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="menAge65" label="65 и старше лет" />
          <msh:textField property="womenAge65" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:checkBox hideLabel="false" property="editComplete" viewOnlyField="false" horizontalFill="false" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" labelSave="Сохранить" labelSaving="Сохранение" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_mortalityReportDateForm" />
  </tiles:put>
  <tiles:put name="side" type="string" />
</tiles:insert>

