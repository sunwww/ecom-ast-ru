<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_confinedCardForm">
      <msh:sideMenu title="Обменная карта">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_confinedCard" name="Изменить" roles="/Policy/Mis/Pregnancy/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-preg_confinedCard" name="Удалить" roles="/Policy/Mis/Pregnancy/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_newBornInformation" name="Сведения о новорожденном" roles="/Policy/Mis/Pregnancy/NewBornInformation/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Беременность
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_confinedCard.do" defaultField="birthDate">
      <msh:hidden property="id" />
      <msh:hidden property="pregnancy" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="hospital" label="Наименование" vocName="mainLpu" fieldColSpan="4" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="hospitalizationDate" label="Дата поступления" />
          <msh:textField property="birthDate" label="Дата родов" />
        </msh:row>
        <msh:row>
          <msh:textArea property="birthFeatures" label="Особенности течения родов" rows="8" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textArea property="birthOperations" label="Оперативные пособия в родах" rows="5" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textArea property="anesthetization" label="Обезболивание" rows="5" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textArea property="postNatalFeatures" label="Течение послеродового периода" rows="2" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthDischargeDays" label="Выписана на " labelColSpan="2" />
          <td>день после родов</td>
        </msh:row>
        <msh:row>
          <msh:textArea property="dischargeMotherCondition" label="Состояние матери при выписке" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="patronageNeeded" label="Нуждается в патронаже" />
        </msh:row>
        <msh:row>
          <msh:textArea property="patronageStatement" label="Показания к патронажу" rows="3" fieldColSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textArea property="notes" label="Особые замечания" rows="5" fieldColSpan="4" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
      <msh:ifFormTypeIsView formName="preg_confinedCardForm">
	<msh:ifInRole roles="/Policy/Mis/Pregnancy/NewBornInformation/View">
        <msh:section title="Сведения о новорожденном">
          <ecom:parentEntityListAll formName="preg_newBornInformationForm" attribute="newBorns" />
          <msh:table hideTitle="false" idField="id" name="newBorns" action="entityParentView-preg_newBornInformation.do">
            <msh:tableColumn columnName="Дата заполнения" property="fillingDate" />
            <msh:tableColumn columnName="Вес при рождении" identificator="false" property="birthWeight" />
            <msh:tableColumn columnName="Рост при рождении" identificator="false" property="birthHeight" />
            <msh:tableColumn columnName="Состояние при рождении" identificator="false" property="birthCondition" />
            <msh:tableColumn columnName="Вес при выписке" identificator="false" property="dischargeWeight" />
            <msh:tableColumn columnName="Состояние при выписке" identificator="false" property="dischargeCondition" />
          </msh:table>
        </msh:section>
    </msh:ifInRole>
      </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_confinedCardForm" />
  </tiles:put>
</tiles:insert>

