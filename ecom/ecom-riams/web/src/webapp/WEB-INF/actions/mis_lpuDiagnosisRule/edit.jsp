<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page = '/WEB-INF/tiles/mainLayout.jsp' flush = 'true'>

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-mis_lpuDiagnosisRule.do" defaultField="department">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <msh:autoComplete property="department" label="Отделение" vocName="lpu" fieldColSpan="3" size="50"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" label="Поток обслуживания" vocName="vocServiceStream" horizontalFill="true" fieldColSpan="3" labelColSpan="1"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="sex" label="Пол" vocName="vocSex" size="20" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="diagnosisRegistrationType" label="Тип регистрации диагноза" vocName="vocDiagnosisRegistrationType" size="20" />
          <msh:autoComplete property="diagnosisPriority" label="Приоритет диагноза" vocName="vocPriorityDiagnosis" size="20" />
        </msh:row>
        <msh:row>
        <msh:checkBox property="permissionRule"  label="Разрешены только эти диагнозы"/>
        </msh:row>
        <msh:ifFormTypeIsView formName="mis_lpuDiagnosisRuleForm">
        <msh:row> 
        <td colspan='3'>
        <ecom:webQuery name="ruleList" nativeSql="select ng.id as ngid, ng.name, lcng.id as lcngid from lpucontractnosologygroup lcng 
        left join contractnosologygroup ng on ng.id=lcng.nosologygroup
         where lcng.lpudiagnosisrule=${param.id}"/>        
        <msh:section>
        <msh:sectionTitle>Список правил</msh:sectionTitle>
        <msh:sectionContent>
        <a href='entityPrepareCreate-contract_nosologyGroup.do?diagnosisRule=${param.id}'>+ДОБАВИТЬ</a>
          <msh:table name="ruleList" action="/javascript:void()" idField="1">
            <msh:tableColumn columnName="Название" property="2" />
            <msh:tableButton property="3" buttonShortName="Удалить" buttonFunction="deleteLpuContractGroup" buttonName="Удалить"/>
          </msh:table>
        </msh:sectionContent>
      </msh:section>
      </td>
        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:ifFormTypeIsView formName="mis_lpuDiagnosisRuleForm">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/LpuArea/Edit" params="id" action="/entityEdit-mis_lpuDiagnosisRule" name="Изменить" />
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/LpuArea/Edit" params="id" action="/entityParentDeleteGoParentView-mis_lpuDiagnosisRule" name="Удалить" />
         </msh:ifFormTypeIsView>
      </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuDiagnosisRuleForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    
 <script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
 <script type='text/javascript'>
 function deleteLpuContractGroup(aId){
	 ContractService.deleteLpuContractGroup (aId, {
		 callback: function (){
			 window.location.reload();
		 }
	 });
 }
 </script>
 </tiles:put>
</tiles:insert>

