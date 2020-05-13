<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Психиатрическая экспертиза
    	  -->
    <msh:form action="/entityParentSaveGoParentView-psych_examination.do" defaultField="actNumber">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="careCard" />
      <msh:panel>
        <msh:row>
        	<msh:textField property="actNumber" label="Номер акта"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="examinationDate" label="Дата экспертизы"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" property="criminalCase" label="Вид уголовного дела" vocName="vocCriminalCase"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" property="criminalCodeArtical" label="Статья уголовного кодекса" vocName="vocCriminalCodeArticle"/>
        </msh:row>
        <msh:row>
        	<msh:textField horizontalFill="true" property="expertDecision" label="Экспертное заключение"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" property="kind" label="Вид экспертизы" vocName="vocPsychExamination"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" property="paticipation" label="Вид участия в экспертизе" vocName="vocPsychExamPaticipation"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="actNotes" label="Описание акта"/>
        </msh:row>
        <msh:row>
        	<msh:textField horizontalFill="true" property="reporter" label="Докладчик"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_examinationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Психиатрическая экспертиза">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_examination" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/Edit" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-psych_examination" name="Удалить" confirm="Вы точно хотите удалить?"  roles="/Policy/Mis/Psychiatry/CareCard/PsychiatricExamination/Delete"  />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

