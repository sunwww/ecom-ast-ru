<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entityParentSave-mis_birthReportDate" defaultField="reportDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpu" />
      <msh:panel colsWidth="10%,5%,1%,5%,60%">
        <msh:row>
          <msh:textField fieldColSpan="2" property="reportDate" label="Дата отчета" labelColSpan="1" />
        </msh:row>
        <msh:separator colSpan="4" label="Родилось" />
        <msh:row>
          <th colspan="1" />
          <th colspan="1" style="text-align: right; padding-right: 5px ;">Мальчики</th>
          <th colspan="2" style="text-align: right; padding-right: 5px ;">Девочки</th>
          <td />
        </msh:row>
        <msh:row>
          <msh:textField property="liveBornBoys" label="Родилось живыми:" />
          <msh:textField property="liveBornGirls" hideLabel="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="deadBornBoys" label="Родилось мертвыми" />
          <msh:textField property="deadBornGirls" hideLabel="true" />
        </msh:row>
        <msh:separator colSpan="4" label="Роды" />
        <msh:row>
          <th colspan="1" />
          <th colspan="1" style="text-align: right; padding-right: 5px ;">Роды&nbsp;живыми</th>
          <th colspan="2" style="text-align: right; padding-right: 5px ;">Роды&nbsp;мертвыми</th>
          <td />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="firstLiveBirth" label="1-е роды" horizontalFill="false" />
          <msh:textField passwordEnabled="false" hideLabel="true" property="firstDeadBirth" viewOnlyField="false" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="secondLiveBirth" label="2-е роды" horizontalFill="false" />
          <msh:textField passwordEnabled="false" hideLabel="true" property="secondDeadBirth" viewOnlyField="false" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="otherLiveBirth" label="3-и и последующие роды" horizontalFill="false" />
          <msh:textField passwordEnabled="false" hideLabel="true" property="otherDeadBirth" viewOnlyField="false" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="twinsBirth" label="Родов двойни" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="tripletsBirth" label="Родов тройни" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:checkBox hideLabel="false" property="editComplete" viewOnlyField="false" labelColSpan="1" fieldColSpan="3" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_birthReportDateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_birthReportDateForm" insideJavascript="false">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_birthReportDate" name="Изменить" roles="/Policy/Mis/Report/Birth/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-mis_birthReportDate" name="Удалить" roles="/Policy/Mis/Report/Birth/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

