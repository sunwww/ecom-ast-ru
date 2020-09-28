<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_obstetricNewBornForm">
      <msh:sideMenu title="Новорожденный">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-preg_obstetricNewBorn" name="Изменить" roles="/Policy/Mis/NewBorn/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_obstetricNewBorn" name="Удалить" roles="/Policy/Mis/NewBorn/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать">
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" name="Отделения по новорожденному" 
    	params="id"  action='/entityParentList-stac_newBornSlo'  key='Alt+6' title='Лечение в отделениях по новорожденному' 
    	styleId="stac_newBornSlo"
    	/>      
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
        <msh:sideLink params="id" action="/print-newbornhistory.do?s=PrintNewBornHistoryService&amp;m=printNewBornHistory" name="Историю развития новорожденного" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Новорожденный
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_obstetricNewBorn.do" defaultField="lastname">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="childBirth" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:hidden property="firstname"/>
      <msh:panel colsWidth="7%,7%,4%">
        <msh:ifFormTypeIsNotView formName="preg_obstetricNewBornForm">
          <msh:row>
            <msh:textField property="lastname" label="Фамилия"horizontalFill="true" />
          	<msh:autoComplete property="child" vocName="vocNewBorn" label="Ребенок" horizontalFill="true" size="25"/>
          </msh:row>
          <msh:row>
            <msh:textField property="middlename" label="Отчество" horizontalFill="true" />
          </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsView formName="preg_obstetricNewBornForm">
          <msh:row>
            <msh:autoComplete property="patient" vocName="patient" label="ФИО" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" viewAction="entityView-mis_patient.do" />
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete property="sex" vocName="vocSex" label="Пол" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthDate" label="Дата рождения" />
          <msh:textField property="birthTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthWeight" label="Вес при рождении (гр.)" />
          <msh:textField property="birthHeight" label="Рост при рождении (см)" />
        </msh:row>
        <msh:row>
          <msh:textField property="headCircle" label="Окружность головы" />
          <msh:textField property="shouldersCircle" label="Окружность плеч" />
        </msh:row>
        <msh:row>
          <msh:textField property="livingPart" label="Вышел (часть тела)" />
          <msh:checkBox property="liveBorn" label="Родился живым" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="maturity" vocName="vocNewBornMaturity" label="Зрелость" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthState" label="Состояние при рождении" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="setPart" label="Вставленная часть тела" />
          <msh:textField property="setView" label="Вид вставки" />
        </msh:row>
        <msh:row>
          <msh:separator label="Размеры" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="directSize" label="Прямой (см)" />
          <msh:textField property="directCircleSize" label="Прямой окружности (см)" />
        </msh:row>
        <msh:row>
          <msh:textField property="shortSkewSize" label="Малый косой" />
          <msh:textField property="shortSkewCircleSize" label="Малой косой окружности (см)" />
        </msh:row>
        <msh:row>
          <msh:textField property="longSkewSize" label="Большой косой (см)" />
          <msh:textField property="longSkewCircleSize" label="Большой косой окружности (см)" />
        </msh:row>
        <msh:row>
          <msh:textField property="shortTransversalSize" label="Малый поперечный (см)" />
          <msh:textField property="longTransversalSize" label="Большой поперечный (см)" />
        </msh:row>
        <msh:row>
          <msh:separator label="Пуповина" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="cordLength" label="Длина пуповины (см)" />
          <msh:textField property="loopCordAmount" label="Кол-во обвитий пуповиной" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="cordAttaching" horizontalFill="true" vocName="vocNewBornCordAttaching" label="Прикрепление пуповины" />
          <msh:textField property="hemorrhageVolume" label="Кровопотеря (мл)" />
        </msh:row>
        <msh:row>
          <msh:textField property="loopCordPart" label="Часть тела, обвитая пуповиной" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Дополнительно" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="oesophagusPermeability" label="Проходимость пищевода" horizontalFill="true" />
          <msh:checkBox property="anus" label="Наличие ануса" />
        </msh:row>
        <msh:row>
          <msh:textField property="malformations" label="Пороки развития" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="childDeliverer" label="Кто принимал ребенка" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textArea property="mothersHarmfulEffect" label="Вредное влияние матери" rows="2" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textArea property="fathersHarmfulEffect" label="Вредное влияние отца" rows="2" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" />
          <msh:textField property="createTime" label="Время" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_obstetricNewBornForm" />
  </tiles:put>
</tiles:insert>

