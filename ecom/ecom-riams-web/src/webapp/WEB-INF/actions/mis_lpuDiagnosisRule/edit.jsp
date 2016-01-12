<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page = '/WEB-INF/tiles/mainLayout.jsp' flush = 'true'>

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-mis_lpuDiagnosisRule.do" defaultField="department" guid="10826cd9-7e71-480c-9d53-c96e6805ce24">
      <msh:hidden property="id" guid="332d968f-182e-4108-b1de-df71738d7b8a" />
      <msh:hidden property="saveType" guid="23740389-cb85-4e5c-b736-0343bbc49d15" />
      <msh:panel guid="070b9d1e-c50f-4423-9d72-274f6b1dc045">
        <msh:row guid="numberRow123">
          <msh:autoComplete property="department" label="Отделение" vocName="lpu" fieldColSpan="3" size="50"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" label="Поток обслуживания" vocName="vocServiceStream" horizontalFill="true" fieldColSpan="3" labelColSpan="1"/>
        </msh:row>
        <msh:row guid="c3c9de7b-88e7-42f5-a7e9-2e1a8d5651b8">
          <msh:autoComplete property="sex" label="Пол" vocName="vocSex" size="20" />
        </msh:row>
        <msh:row guid="669f7589-58e7-4974-ac96-c9cbe4fb9eaa">
          <msh:autoComplete property="diagnosisRegistrationType" label="Тип регистрации диагноза" vocName="vocDiagnosisRegistrationType" size="20" />
          <msh:autoComplete property="diagnosisPriority" label="Приоритет диагноза" vocName="vocPriorityDiagnosis" size="20" />
        </msh:row>
        <msh:row>
        <msh:checkBox property="permissionRule"  label="Разрешены только эти диагнозы"/>
        </msh:row>
        <msh:ifFormTypeIsView formName="mis_lpuDiagnosisRuleForm">
        <msh:row> <td colspan="3"> 
        <ecom:webQuery name="ruleList" nativeSql="select ng.id, ng.name from lpucontractnosologygroup lcng 
        left join contractnosologygroup ng on ng.id=lcng.nosologygroup
         where lcng.lpudiagnosisrule=${param.id}"/>        
           <msh:section guid="b67c5be3-5330-4589-8da8-7888453aaeb8">
        <msh:sectionTitle guid="3ae2d769-07ed-4109-b6cc-75bf939a86d2">Список правил</msh:sectionTitle>
        <msh:sectionContent guid="6dd31b9f-5068-4961-9033-417c6e01ff13">
        <a href='entityPrepareCreate-contract_nosologyGroup.do?diagnosisRule=${param.id}'>+ДОБАВИТЬ</a>
          <msh:table name="ruleList" action="entityView-contract_nosologyGroup.do" idField="1">
            <msh:tableColumn columnName="Название" property="2" guid="e7ee8550-c34b-40bb-9aac-fcdd4da970e0" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
      </td>
        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:submitCancelButtonsRow colSpan="2" guid="a332e241-83f4-4e61-ad6f-d0f69cc6076e" />
      </msh:panel>
    </msh:form>
    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="b08ac525-aee5-493c-bd30-a245e7c80200">
      <msh:ifFormTypeIsView formName="mis_lpuDiagnosisRuleForm" guid="24576531-bcea-4103-9c80-fe9e95c4dfaf">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/LpuArea/Edit" params="id" action="/entityEdit-mis_lpuDiagnosisRule" name="Изменить" guid="dc8c6883-5faf-41b0-b068-296214839e40" />
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/LpuArea/Edit" params="id" action="/entityParentDeleteGoParentView-mis_lpuDiagnosisRule" name="Удалить" guid="dc8c6883-5faf-41b0-b068-296214839e40" />
         </msh:ifFormTypeIsView>
      </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuDiagnosisRuleForm" guid="04eb4fb1-03b4-4011-9e85-30cd955d2c41" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    
 <script type="text/javascript" src="./dwr/interface/AttachmentService.js"></script>
 
 </tiles:put>
</tiles:insert>

