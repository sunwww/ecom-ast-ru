<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Суицид
    	  -->
    <msh:form action="/entityParentSaveGoParentView-psych_suicide.do" defaultField="registrationDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="suiMessage" />
      
      <msh:panel>
      	<msh:row>
      		<msh:textField property="registrationDate" label="Дата осмотра"/>
      	</msh:row>
        <msh:row>
        	<msh:autoComplete property="careCard" label="Псих. карта" horizontalFill="true" fieldColSpan="3" vocName="psychiatricCareCardBySuiMes" parentId="psych_suicideForm.suiMessage"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="diagnosMkb" label="Код по МКБ-10" horizontalFill="true" fieldColSpan="3" vocName="vocIdc10"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="diagnosText" label="Психиатрический диагноз" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="psychotropicHelp" label="Получает психотропную терапию?" horizontalFill="true" fieldColSpan="3" vocName="vocYesNo"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="psychotropicHelpDesc" label="Указать, что именно" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="otherSuicide" label="Суицидальные попытки ранее" horizontalFill="true" fieldColSpan="3" vocName="vocYesNo"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="otherSuicide" label="Какие, когда" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="alcoholism" label="Нарк. анамнез: алкоголизм" horizontalFill="true" fieldColSpan="1" vocName="vocYesNo"/>
        	<msh:textField property="alcoholismExperience" label="Стаж"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="narcomania" label="Нарк. анамнез: наркомания" horizontalFill="true" fieldColSpan="1" vocName="vocYesNo"/>
        	<msh:textField property="narcomaniaExperience" label="Стаж"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="motive" label="Мотивы суиц. попытки" horizontalFill="true" fieldColSpan="3" vocName="vocSuicidalMotive"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="motiveOther" label="Другие мотивы" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="psychoTherapeuticHelp" label="Получал ли психотерапевтическую помощь после попытки" horizontalFill="true" labelColSpan="3" fieldColSpan="1" vocName="vocYesNo"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="psychoTherapeuticHelpWhere" label="Где" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="regret" label="О соверш. суиц. попытке" horizontalFill="true" fieldColSpan="3" vocName="vocSuicideRegret"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="suicidalAttitude" label="Сохр. ли суиц. настроенность" horizontalFill="true" fieldColSpan="3" vocName="vocSuicidalAttitude"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="suicidalActivity" label="Характер суиц. акт. в наст. время" horizontalFill="true" fieldColSpan="3" vocName="vocSuicidalActivity"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="marriageStatus" label="Семейное положение" horizontalFill="true" fieldColSpan="3" vocName="vocMarriageStatus"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="finSituation" label="Отнош. к своему материал. положению" horizontalFill="true" fieldColSpan="3" vocName="vocFinancialSituation"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="somaticDisease" label="Наличие соматического заболевания" horizontalFill="true" fieldColSpan="3" vocName="vocPsychSomaticDisease"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="recommendation" label="Рекомендации (результат осмотра)" horizontalFill="true" fieldColSpan="3" vocName="vocPsychRecommendation"/>
        </msh:row>
        <msh:row>
        	<msh:textArea fieldColSpan="3" property="notes" label="Описание"/>
        </msh:row>
        </msh:panel>
			<msh:panel>
		 <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="6"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>   
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_suicideForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Осмотр">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_suicide" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/SuicideMessage/Suicide/Edit" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-psych_suicide" name="Удалить" confirm="Вы точно хотите удалить?"  roles="/Policy/Mis/Psychiatry/CareCard/SuicideMessage/Suicide/Delete"  />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>