<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Принудительное лечение
    	  -->
    <msh:form action="/entityParentSaveGoParentView-psych_compulsoryTreatment.do" defaultField="orderNumber">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="careCard" />
      <msh:panel>
        <msh:row>
        	<msh:textField property="orderNumber" label="Поряд. номер лечения"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="decisionDate" label="Дата решения суда"/>
        	<msh:textField property="registrationDate" label="Дата регистрации"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" fieldColSpan="3" property="kind" label="Решение суда" vocName="vocPsychCompulsoryTreatment"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" fieldColSpan="3" property="lawCourt" label="Суд, принявший решение" vocName="vocLawCourt"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" fieldColSpan="3" property="crimainalCodeArticle" label="Статья уголовного кодекса" vocName="vocCriminalCodeArticle"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="decisionNotes" fieldColSpan="3"  label="Описание решения"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="dateReplace" label="Дата замены"/>
        	<msh:textField property="registrationReplaceDate" label="Дата регист. замены"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="lawCourtReplace" vocName="vocLawCourt" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="courtDecisionReplace" vocName="vocPsychCourtTreatmentDecision" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <!-- 
        TODO Добавить заключение 
        -->
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
    <ecom:titleTrail mainMenu="Patient" beginForm="psych_compulsoryTreatmentForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Принудительное лечение">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-psych_compulsoryTreatment" name="Изменить" roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/Edit" />
      <msh:sideLink key="ALT+DEL"  params="id" action="/entityParentDelete-psych_compulsoryTreatment" name="Удалить" confirm="Вы точно хотите удалить?"  roles="/Policy/Mis/Psychiatry/CareCard/CompulsoryTreatment/Delete"  />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

