<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Хирургическая операция
    	  -->

    <msh:form action="/entityParentSaveGoSubclassView-stac_surOperation.do" defaultField="" editRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create">
      <msh:panel colsWidth="15px,250px,15px">
        <msh:hidden property="id" />
        <msh:hidden property="patient" />
        <msh:hidden property="saveType" />
        <msh:hidden property="medCase" />
        <msh:hidden property="lpu" />
        <msh:row>
          <msh:textField property="numberInJournal" label="Номер протокола"  labelColSpan="2" fieldColSpan="2"/>
        </msh:row>
        <msh:separator label="Сведения до операции" colSpan="" />
        <msh:row>
          <msh:autoComplete property="idc10Before" label="МКБ до операции" fieldColSpan="3" horizontalFill="true" vocName="vocIdc10" />
        </msh:row>
        <msh:separator label="Сведения об операции" colSpan="" />
	        <msh:row>
	          <msh:textField property="operationDate" label="Начало дата" />
	          <msh:textField property="operationTime" label="время" />
	        </msh:row>
	        <msh:row>
	          <msh:textField property="operationDateTo" label="Окончание дата" />
	          <msh:textField property="operationTimeTo" label="время" />
	        </msh:row>
	        <msh:row>
	          <msh:textField property="operationDate" label="Начало дата" />
	        </msh:row>
        <msh:row>
          <msh:autoComplete property="department" label="Отделение" fieldColSpan="3" horizontalFill="true" vocName="vocLpuOtd" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="operation" label="Операция" size="60" fieldColSpan="3" horizontalFill="true" vocName="vocOperation" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="medService" label="Операция (услуга)" size="60" fieldColSpan="3" horizontalFill="true" vocName="medServiceOperation" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="profile" label="Профиль" horizontalFill="true" vocName="vocSurgicalProfile" />
          <msh:autoComplete property="method" label="Метод" horizontalFill="true" vocName="vocOperationMethod" />
        </msh:row>
        <msh:row>
          <msh:textArea rows="6" hideLabel="false" property="operationText" viewOnlyField="false" fieldColSpan="3" label="Протокол операции" />
        </msh:row>
        <msh:row>
          <msh:autoComplete horizontalFill="true" property="aspect" label="Показания" vocName="vocHospitalAspect" />
          <msh:autoComplete horizontalFill="true" vocName="vocOperationTechnology" property="technology" label="С испол. ВМТ"/>
        </msh:row>
        
         <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/HideCheckBox">
	         <msh:row>
	          <msh:checkBox property="base" label="Основная" fieldColSpan="1" />
	          <msh:checkBox property="minor" label="Малая операция" />
	        </msh:row>
        </msh:ifNotInRole>
        <msh:row>
        	<msh:autoComplete label="Хирург" property="surgeon" horizontalFill="true" fieldColSpan="3" vocName="workFunctionIsSurgical" />
        </msh:row>
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">          
        
        <msh:row>
          <ecom:oneToManyOneAutocomplete label="Ассистенты" property="surgeonFunctions" vocName="workFunction" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="operatingNurse" label="Операционная медсестра" fieldColSpan="3" horizontalFill="true" vocName="workFunction" />
        </msh:row>
        </msh:ifNotInRole>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
        	<msh:hidden property="surgeonFunctions"/>
        	<msh:hidden property="complications"/>
        </msh:ifInRole>
         <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/HideCheckBox">
        <msh:separator label="Использование специальной аппаратуры" colSpan="" />
	        <msh:row>
	          <msh:checkBox property="endoscopyUse" label="Эндоскопия" />
	          <msh:checkBox property="laserUse" label="Лазерная аппаратура" />
	        </msh:row>
	        <msh:row>
	          <msh:checkBox property="cryogenicUse" label="Криогенная аппаратура" />
	        </msh:row>
        <msh:separator label="Сведения после операции" colSpan="" />
        
        <msh:row>
          <ecom:oneToManyOneAutocomplete vocName="vocComplication" colSpan="3" label="Осложнения" property="complications" />
        </msh:row>
        <msh:row>
          <msh:textArea hideLabel="false" property="histologicalStudy" viewOnlyField="false" label="Гистологическое исследование" fieldColSpan="3" horizontalFill="true" rows="6" />
          <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
            <td colspan="1">
              <input type="button" value="Шаблон" onClick="showHistologicalStudyTempTemplateProtocol()" />
            </td>
          </msh:ifFormTypeIsNotView>
        </msh:row>

        <msh:row>
          <msh:autoComplete property="idc10After" label="МКБ после операции" fieldColSpan="3" horizontalFill="true" vocName="vocIdc10" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="outcome" label="Исход операции" fieldColSpan="3" horizontalFill="true" vocName="vocOperationOutcome" />
        </msh:row>
        </msh:ifNotInRole>
                <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="createUsername" label="Оператор" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Оператор" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата печати"/>
        </msh:row>
                <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
    </msh:ifFormTypeIsNotView>
    <msh:ifFormTypeIsView formName="stac_surOperationForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/View">
        <ecom:parentEntityListAll attribute="anesthesies" formName="stac_anesthesiaForm" />
        <msh:tableNotEmpty name="anesthesies">
          <msh:section title="Анестезия">
            <msh:table name="anesthesies" action="entityParentView-stac_anesthesia.do" idField="id">
              <msh:tableColumn columnName="Анестезиолог" property="anesthesistInfo" />
              <msh:tableColumn columnName="Длительность (мин)" property="duration" />
              <msh:tableColumn columnName="Метод" property="methodInfo" />
            </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsNotView formName="stac_surOperationForm">
    	<tags:mis_double name='MedService' title='Данная операция оказана:'/>
    </msh:ifFormTypeIsNotView>  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_surOperationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_surOperationForm">
      <msh:sideMenu title="Хир. операция">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_surOperation" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-stac_surOperation" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Delete" />
        <msh:sideLink key="ALT+3" params="id" action="/entityParentListRedirect-stac_surOperation" name="⇧Cписок операций" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View" title="Перейти к списку операций" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink key="CTRT+1" params="id" action="/entityParentPrepareCreate-stac_anesthesia" name="Анестезию" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/View" title="Добавить анестезию" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="CTRL+2" params="id" action="/print-surgicalOperation.do?m=printSurOperation&s=HospitalPrintService" name="Протокола операции"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>

</tiles:insert>

