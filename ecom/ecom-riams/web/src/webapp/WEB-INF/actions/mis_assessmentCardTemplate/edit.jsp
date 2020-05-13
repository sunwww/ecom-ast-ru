<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    
   
    <tiles:put name='body' type='string'>
    
        <msh:form action="entitySaveGoView-mis_assessmentCardTemplate.do" defaultField="id">
        
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:panel colsWidth="10% 20% 15%">
        
        
         <msh:row>
         <msh:textField property="name" label="Название"  size="50"/>
        </msh:row>
          <msh:row>
	         <msh:checkBox property="isGroupParameters" label="Группировать параметры?"/>
          </msh:row> 
         <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        </msh:row>
        <msh:row>
          	<msh:label property="createUsername" label="Пользователь" />
        </msh:row>
         <msh:submitCancelButtonsRow colSpan="1"></msh:submitCancelButtonsRow>
         <msh:ifFormTypeIsView formName="mis_assessmentCardTemplateForm">
        <msh:section title="Список параметров у карты" viewRoles="/Policy/Diary/AssessmentCard/Edit" listUrl="mis_assessmentParamsEdit.do?id=${param.id}">
            <msh:sectionContent>
            <table><tr><td>
                <div id="treeDiv1"></div>
                </td> <td style="vertical-align: top;">
                <ecom:webQuery name="list_par_temp" nativeSql="select p.parameter_id
,gr.name as grname
,par.name||' ('||case when par.type='1' then 'Числовой' when par.type='4' then 'Числовой с плавающей точкой зн.'||par.cntDecimal when par.type='2' then 'Пользовательский справочник: '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when par.type='3' then 'Текстовое поле' when par.type='5' then 'Текстовое поле с ограничением' else 'неизвестный' end||') - '||coalesce(vmu.name,'')
,par.valueTextDefault 
from ParameterByForm p 
left join Parameter par on par.id=p.parameter_id 
left join ParameterGroup gr on gr.id=par.group_id
left join userDomain vd on vd.id=par.valueDomain_id left join vocMeasureUnit vmu on vmu.id=par.measureUnit_id
where p.assessmentCard=${param.id} order by p.position
                "/>
                    <msh:section title="Порядок">
   
    <msh:table name="list_par_temp" action="diary_parameterView.do" idField="1">
    	<msh:tableColumn property="sn" columnName="#"/>
    	<msh:tableColumn property="2" columnName="Категория"/>
    	<msh:tableColumn property="3" columnName="Параметр"/>
    	<msh:tableColumn property="4" columnName="Значение по умолчанию"/>
    </msh:table>
    </msh:section>
                </td>
            </tr></table>
            </msh:sectionContent>
        </msh:section>
        
                <msh:section title="Критерии оценки" viewRoles="/Policy/Diary/AssessmentCard/Edit" listUrl="mis_assessmentParamsEdit.do?id=${param.id}">
            <msh:sectionContent>
           
                <ecom:webQuery name="list_assessments" nativeSql="select ass.id, ass.name, ass.minball, ass.maxball 
					from assessment ass
					where ass.assessmentcard = ${param.id} "/>
                    
   
    <msh:table name="list_assessments" action="entityView-mis_assessment.do" idField="1">
    	<msh:tableColumn property="sn" columnName="#"/>
    	<msh:tableColumn property="2" columnName="Название"/>
    	<msh:tableColumn property="3" columnName="Минимальный балл"/>
    	<msh:tableColumn property="4" columnName="Максимальный балл"/>
    </msh:table>
              
            </msh:sectionContent>
        </msh:section>
        
    
         </msh:ifFormTypeIsView>
      </msh:panel>
      </msh:form>
    
    </tiles:put>

    <tiles:put name='side' type='string'>
      <msh:ifFormTypeAreViewOrEdit formName="mis_assessmentCardTemplateForm">
        <msh:sideMenu>
	        <msh:sideLink key="ALT+2" params="id" action="/entityPrepareDelete-mis_assessmentCardTemplate" name="Удалить" roles="/Policy/Mis/AssessmentCard/Delete" />
	        <msh:sideLink key="ALT+3" params="id" action="/mis_assessmentParamsEdit" name="Работа с параметрами" roles="/Policy/Mis/AssessmentCard" />
	         <msh:sideLink key="ALT+M" params="id" action='/entityParentPrepareCreate-mis_assessment.do' name="Создать оценку" />
	   </msh:sideMenu>
      </msh:ifFormTypeAreViewOrEdit>
       
    </tiles:put>
    
  

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_assessmentCardTemplateForm" />
    </tiles:put>


</tiles:insert>