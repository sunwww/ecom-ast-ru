<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
  <tiles:put name="side" type="string">
  </tiles:put>

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-stac_dataFond.do" defaultField="transfusionDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
	    <msh:panel>
	    <msh:row>
	    	<msh:textField property="numberFond" label="Номер фонда"/>
	    	<msh:textField property="directDate" label="Дата направл."/>
	    	<msh:textField property="emergency" label="Порядок поступления"/>
	    </msh:row>
	    <msh:row>
	    	<msh:textField property="orderLpuCode" label="Направилеть ЛПУ"/>
	    	<msh:textField property="directLpuCode" label="Куда направлен"/>
	    	<msh:textField property="formHelp" label="Форма помощи"/>
	    </msh:row>
	    <msh:row>
	    	<msh:autoComplete property="directMedCase" label="Визит" vocName="allSMO" shortViewAction="entitySubclassShortView-mis_medCase.do" viewAction="entitySubclassView-mis_medCase.do"/>
	    	<msh:autoComplete property="hospitalMedCase" label="Госпитализация" vocName="allSMO" shortViewAction="entitySubclassShortView-mis_medCase.do" viewAction="entitySubclassView-mis_medCase.do"/>
	    	<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/HospitalDataFond/DeleteSls">
	    	<A href="javascript:void(0)" onclick="if (confirm('Вы точно хотите открепить госпитализацию?')) getDefinition('js-stac_dataFond-deleteSlsInDirectFond.do?short=Short&id=${param.id}')"> ОТКРЕПИТЬ СЛУЧАЙ ОТ ГОСПИТАЛИЗАЦИИ</A>
	    	</msh:ifInRole>
	    </msh:row>
	      <msh:row>
	      	<msh:textField property="lastname" label="Фамилия"/>
	      	<msh:textField property="firstname" label="Имя"/>
	      	<msh:textField property="middlename" label="Отчество"/>
	      </msh:row> 
	      <msh:row>
	      	<msh:textField property="birthday" label="Дата рождения"/>
	      	<msh:autoComplete property="sex" label="Пол" vocName="vocSex"/>
	      	<msh:textField property="phone" label="Телефон"/>
	      </msh:row>
	      <msh:row>
	      	<msh:textField property="snils" label="СНИЛС"/>
	      </msh:row> 
	      <msh:row>
	      	<msh:textField property="typePolicy" label="Тип полиса"/>
	      	<msh:textField property="seriesPolicy" label="Серия полиса"/>
	      	<msh:textField property="numberPolicy" label="Номер полиса"/>
	      </msh:row>
	      <msh:row>
	      	<msh:textField property="smoName" label="Наим. СМО"/>
	      </msh:row>
	      <msh:row>
	      	<msh:textField property="smo" label="Код СМО"/>
	      	<msh:textField property="smoOgrn" label="ОГРН СМО"/>
	      	<msh:textField property="smoOkato" label="ОКАТО СМО"/>
	      </msh:row>
	      
	      <msh:row>
	      	<msh:textField property="statCard" label="Стат.карта"/>
	      	<msh:textField property="preHospDate" label="Дата пред. госп."/>
	      	<msh:textField property="forChild" label="Для детей"/>
	      </msh:row>
	      <msh:row>
	      	<msh:textField property="hospDate" label="Дата госп."/>
	      	<msh:textField property="hospTime" label="Время госп."/>
	      	<msh:textField property="hospDischargeDate" label="Дата выписки"/>
	      </msh:row>
	      <msh:row>
	      	<msh:textField property="diagnosis" label="Диагноз"/>
	      	<msh:textField property="profile" label="Профиль"/>
	      	<msh:textField property="bedSubType" label="Тип помощи"/>
	      </msh:row>
	      <msh:row>
	      	<msh:checkBox property="isTable1" label="Подан по N1"/>
	      	<msh:textField property="dateImport1" label="Дата импорта"/>
	      </msh:row>
	      <msh:row>
	      	<msh:checkBox property="isTable2" label="Подан по N2"/>
	      	<msh:textField property="dateImport2" label="Дата импорта"/>
	      </msh:row>
	      <msh:row>
	      	<msh:checkBox property="isTable3" label="Подан по N3"/>
	      	<msh:textField property="dateImport3" label="Дата импорта"/>
	      </msh:row>
	      <msh:row>
	      	<msh:checkBox property="isTable4" label="Подан по N4"/>
	      	<msh:textField property="dateImport4" label="Дата импорта"/>
	      </msh:row>
	      <msh:row>
	      	<msh:checkBox property="isTable5" label="Подан по N5"/>
	      	<msh:textField property="dateImport5" label="Дата импорта"/>
	      </msh:row>
	    </msh:panel>
        <msh:submitCancelButtonsRow colSpan="" />
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string"> 
    <msh:title mainMenu="Journals" title="Данные фонда по 263 приказу"> </msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_dataFondForm">
      <msh:sideMenu title="СCО">
        <msh:sideLink key="" params="id" action="/entityEdit-stac_dataFond" name="Изменить " title="Изменить информацию о переливании крови" roles="/Policy/Mis/MedCase/Stac/Ssl/Blood/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-stac_dataFond" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Blood/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

 