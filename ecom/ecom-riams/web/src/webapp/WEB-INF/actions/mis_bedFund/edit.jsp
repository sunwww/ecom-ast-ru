<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entityParentSaveGoView-mis_bedFund.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpu" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete vocName="vocBedType" property="bedType" label="Профиль коек" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="amount" label="Количество коек" size="20" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="forChild" label="Для детей" />
          <msh:checkBox property="noFood" label="Без питания" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="isRehab" label="Реабилитационные койки" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        
        	<msh:checkBox property="addCaseDuration" fieldColSpan="3" label="День выписки и день поступления считать разными днями"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocBedSubType" property="bedSubType" label="Тип коек" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocBedReductionType" property="reductionType" label="" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateStart" label="Дата начала актуальности" />
          <msh:textField property="timeStart" label="Время начала актуальности" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateFinish" label="Дата окончания актуальности" />
          <msh:textField property="timeFinish" label="Время окончания актуальности" />
        </msh:row>
        <msh:row>
        	<msh:textField fieldColSpan="3" property="snilsDoctorDirect263"/>
        </msh:row>
        <msh:row />
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_bedFundForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <msh:ifFormTypeIsView formName="mis_bedFundForm">
    <msh:sideMenu>
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_bedFund" name="Изменить" roles="/Policy/Mis/BedFund/Edit" />
      <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-mis_bedFund" name="Удалить" roles="/Policy/Mis/BedFund/Delete" />
    </msh:sideMenu>
    
    <msh:sideMenu title="Перейти">
      <msh:sideLink action="/entityParentListRedirect-mis_bedFund" name="К списку" params="id" />
      <msh:sideLink roles="/Policy/Mis/Journal/263injunction" action="/stac_direct_in_fond.do" name="263 приказ" params="id" />
    </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

