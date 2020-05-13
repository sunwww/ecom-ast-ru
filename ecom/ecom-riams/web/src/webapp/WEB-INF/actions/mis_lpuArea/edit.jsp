<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page = '/WEB-INF/tiles/mainLayout.jsp' flush = 'true'>

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-mis_lpuArea.do" defaultField="number">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpu" />
        <msh:ifNotInRole roles="/Policy/Mis/Patient/ViewInfoArea">
        <msh:hidden property="isViewInfoPatient"/>
        </msh:ifNotInRole>
      <msh:panel>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/EditParent">
         <msh:ifFormTypeIsNotView formName="mis_lpuAreaForm">
            <msh:ifFormTypeAreViewOrEdit formName="mis_lpuAreaForm">
              <msh:row>
                <msh:autoComplete property="lpu" vocName="lpu" label="Родительское ЛПУ" viewAction="entityEdit-mis_lpu.do" fieldColSpan="3" horizontalFill="true" />
              </msh:row>
            </msh:ifFormTypeAreViewOrEdit>
          </msh:ifFormTypeIsNotView>
        </msh:ifInRole>
        
        <msh:row>
          <msh:textField property="number" label="Номер участка" />
          <msh:textField property="codeDepartment" label="Код подразделения"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workFunction" label="Участковый" vocName="workFunction" horizontalFill="true" fieldColSpan="3" labelColSpan="1"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="type" label="Тип участка" vocName="vocAreaType" size="20" />
        </msh:row>
        <msh:row>
          <msh:textArea property="comment" label="Комментарий" horizontalFill="true" size="50" rows="4" />
        </msh:row>
        <msh:ifInRole roles="/Policy/Mis/Patient/ViewInfoArea">
        <msh:row>
        	<msh:checkBox property="isViewInfoPatient" label="Отображать информацию в персоне, если адрес персоны попадает по участку" fieldColSpan="3"/>
        </msh:row>
        </msh:ifInRole>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_lpuAreaForm">
      <msh:section>
        
        <msh:sectionContent>
          <ecom:webQuery name="countPopulation" nativeSql="select count(distinct patient_id) from lpuattachedbydepartment where area_id=${param.id} and dateTo is null" />
          <msh:table name="countPopulation" action="/javascript:void()" idField="1">
            <msh:tableColumn columnName="Количество прикрепленного населения на участке" property="1" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
      <msh:section>
        <msh:sectionTitle>Список прикрепленных домов</msh:sectionTitle>
        <msh:sectionContent>
          <ecom:parentEntityListAll formName="mis_lpuAreaAddressTextForm" attribute="areas" />
          <msh:table name="areas" action="entityParentView-mis_lpuAreaAddressText.do" idField="id">
            <msh:tableColumn columnName="Адрес" property="addressText" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
      <div id='changeArea' style='display:none'>
      <msh:separator label="Перекрепление населения" colSpan="10"></msh:separator>
      <msh:autoComplete label="ЛПУ для прикрепления" vocName="lpu" property="changeLpu" fieldColSpan="10" size="50" />
      <msh:autoComplete label="Участок для прикрепления" vocName='lpuAreaWithParent' property="changeLpuArea" size="50" parentAutocomplete="changeLpu" fieldColSpan="10"/>
      <input type='button' value="Перекрепить"  onclick="javascript:changeAttachmentArea()">
      </div>
     
    </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:ifFormTypeIsView formName="mis_lpuAreaForm">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/LpuArea/Edit" params="id" action="/entityEdit-mis_lpuArea" name="Изменить" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeAreViewOrEdit formName="mis_lpuAreaForm">
        <msh:sideLink key="ALT+3" roles="/Policy/Mis/LpuAreaAddressText/Create" params="id" action="/entityParentPrepareCreate-mis_lpuAreaAddressText" name="Добавить прикрепленные дома" />
        <msh:sideLink key="ALT+6" roles="/Policy/Mis/LpuArea/View" params="id" action="/javascript:window.location.href='mis_bypass_report.do?.do?area='+$('id').value+'&lpu='+$('lpu').value" name="Печать обходного листа" />
        <msh:sideLink key="ALT+DEL" roles="/Policy/Mis/LpuArea/Delete" params="id" action="/entityParentDeleteGoParentView-mis_lpuArea" name="Удалить" confirm="Удалить участок?" />
        <msh:sideLink roles="/Policy/Mis/LpuArea/View" action="/javascript:{$('changeArea').style.display='block';}" name="Перекрепить на другой участок" />
        <msh:sideLink roles="/Policy/Mis/Patient/ViewInfoArea" action="/js-mis_lpuArea-print_area_by_address.do?id=${param.id}" name="Печать по адресам пациентов для выборов"/>
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuAreaForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    
 <script type="text/javascript" src="./dwr/interface/AttachmentService.js"></script>
 <script type="text/javascript">
 function changeAttachmentArea() {
	 if ($('changeLpu').value==''||$('changeLpuArea').value=='') {
		 alert ('Укажите ЛПУ и участок!');
	 } else {
		 AttachmentService.changeArea($('id').value, $('changeLpu').value, $('changeLpuArea').value,{
			 callback: function (aResult) {
				 alert (aResult);
			 }
		 });
	 }
 }
 </script>
 </tiles:put>
</tiles:insert>

