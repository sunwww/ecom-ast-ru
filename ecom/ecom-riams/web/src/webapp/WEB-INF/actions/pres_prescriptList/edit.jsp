<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="javascript" type="string">
	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>

		<msh:ifFormTypeIsView formName="pres_prescriptListForm">
			<script type="text/javascript">
				function createTemplate() {
			if (confirm("Создать шаблон на основе текущего листа назначений?")) {
				var name = prompt("Введите имя шаблона");
				PrescriptionService.createTemplateFromList($('id').value, name, {
					callback: function(aResult) {
						if (+aResult>0 && confirm("Шаблон успешно создан, хотите в него перейти?")) {
				    			window.location.href = "entityParentView-pres_template.do?id="+aResult  ;
						} else {
                            alert("Ошибка при создании шаблона "+aResult);
                        }
					}
				});
			}
		}
			</script>
		</msh:ifFormTypeIsView>
			</tiles:put>
		
  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="StacJournal" />
  </tiles:put>
  <tiles:put name="body" type="string">
   <msh:form action="/entityParentSaveGoView-pres_prescriptList.do" defaultField="workFunctionName" >
      <msh:hidden property="id"  />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:hidden property="labList" />
      <msh:hidden property="drugList" />
      <msh:hidden property="allDrugList" />
      <msh:hidden property="labCabinet" />
      <msh:hidden property="surgDate" />
      <msh:panel colsWidth="1%,1%,1%,97%">
      <msh:row>
       	<msh:autoComplete vocName="vocPrescriptType" property="prescriptType" label="Тип назначения" horizontalFill="true" fieldColSpan="3" size="30" viewOnlyField="true" />
      </msh:row>
		<msh:row>
          <msh:textArea rows="2" property="comments" label="Комментарии" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="workFunction" label="Назначил" vocName="workFunction" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        </msh:panel>

          <msh:panel>
          <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        	<msh:label property="createUsername" label="пол-ль"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редакт."/>
        	<msh:label property="editTime" label="время"/>
        	<msh:label property="editUsername" label="пол-ль"/>
        </msh:row>                
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <tags:stac_selectPrinter name="Select" roles="/Policy/Config/SelectPrinter" />
    <tags:dir_medService name="1" table="MEDSERVICE" title="Услуги" functionAdd="prepare1Row" addParam="id" />
    <tags:enter_date name="2" functionSave="prepare1RowByDate"/>
    <msh:ifFormTypeIsView formName="pres_prescriptListForm">
    	<tags:pres_prescriptByList field="p.prescriptionlist_id='${param.id}'" />
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pres_prescriptListForm">
      <msh:sideMenu title="Лист назначений">
      	<msh:sideLink action=" javascript:createTemplate()" name="Создать шаблон на основе текущего ЛН" title="Добавить назначения из шаблона" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-pres_prescriptList" name="Удалить" roles="/Policy/Mis/Prescription/Prescript/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-pres_drugPrescription" name="Лекарственное средство" roles="/Policy/Mis/Prescription/DrugPrescription/View"/>
        <msh:sideLink params="id" action="/entityParentPrepareCreate-pres_dietPrescription" name="Диету" roles="/Policy/Mis/Prescription/DietPrescription/View" />
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentPrepareCreate-pres_servicePrescription" name="Лабораторное исследование" />
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentPrepareCreate-pres_operationPrescription" name="Назначение на операцию" />
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentPrepareCreate-pres_diagnosticPrescription" name="Назначение на диаг. услугу" />
        <msh:sideLink roles="/Policy/Mis/Prescription/ModePrescription/View" params="id" action="/entityParentPrepareCreate-pres_modePrescription" name="Режим" />
        <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentPrepareCreate-pres_wfConsultation" name="Консультацию" />
          <msh:sideLink roles="/Policy/Mis/Prescription/ServicePrescription/View" params="id" action="/entityParentPrepareCreate-pres_drugPrescription" name="Лекарственный препарат" />
      </msh:sideMenu>
      <msh:sideMenu title="Перейти">
        <msh:sideLink key="ALT+9" action="/entityParentListRedirect-pres_prescriptList" name="⇧К списку сводных листов назначений" params="id" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
        <msh:sideLink action='/javascript:initSelectPrinter("print-prescriptList_1.do?s=HospitalPrintService&m=printPrescriptList&id=${param.id}",1)' name="Листа назначений" />
        <msh:sideLink action='/javascript:initSelectPrinter("print-prescriptListDrug.do?s=HospitalPrintService&m=printDrugPrescriptList&id=${param.id}",1)' name="Листа назначений нарКотиков" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

