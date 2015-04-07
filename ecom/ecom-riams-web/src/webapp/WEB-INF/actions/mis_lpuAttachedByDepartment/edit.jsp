<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoParentView-mis_lpuAttachedByDepartment.do" defaultField="attachedTypeName">
        	<msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="patient"/>

            <msh:panel>
            	<msh:row>
            		<msh:autoComplete property="attachedType" label="Тип прикрепления" fieldColSpan="5" horizontalFill="true" vocName="vocAttachedType"/>
            	</msh:row>
				<msh:row styleId='rowLpu'>
		            <msh:autoComplete fieldColSpan="5" property="lpu" label="ЛПУ" horizontalFill="true"
		                              vocName="lpu" size="50"/>
		        </msh:row>
		        <msh:row styleId='rowLpuArea'>
		            <msh:autoComplete fieldColSpan="5" property="area" label="Участок" horizontalFill="true"
		                              parentAutocomplete="lpu" vocName="lpuAreaWithParent"/>
		        </msh:row>	
		                        <msh:row>
                    <msh:autoComplete vocName="vocInsuranceCompany" property="company" label="Страховая&nbsp;компания" horizontalFill="true" fieldColSpan="5"/>
                </msh:row>	
		        <msh:row>
		        	<msh:textField property="dateFrom" label="Дата прикрепления"/>
		        	<msh:textField property="dateTo" label="Дата открепления"/>
		        	<msh:textField property="lpuTo" label="ЛПУ открепления"/>
		        </msh:row>
		        <msh:row>
		        	<msh:checkBox property="noActualPolicy" fieldColSpan="5" label="Подача производилась по неактуальному полису"/>
		        </msh:row>
		        
			</msh:panel>
			<msh:panel>
		 <msh:row>
        	<msh:separator label="Информация о дефекте" colSpan="6"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="defectPeriod"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="defectText" fieldColSpan="200"/>
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
                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>
            
            

        </msh:form>


    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:ifFormTypeIsView formName="mis_lpuAttachedByDepartmentForm">
                <msh:sideLink roles="/Policy/Mis/Patient/AttachedByDepartment/Edit" key="ALT+2" params="id" action="/entityParentEdit-mis_lpuAttachedByDepartment" name="Изменить"/>

	            <hr/>
                <msh:sideLink roles="/Policy/Mis/Patient/AttachedByDepartment/Delete" key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_lpuAttachedByDepartment" name="Удалить"
                              confirm="Удалить прикрепление?"/>
                <msh:sideLink roles="/Policy/Mis/Patient/AttachedByDepartment/Edit" params="id" action="/javascript:cleanDefect();" name="Очистить дефект"/>
            </msh:ifFormTypeIsView>
               
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_lpuAttachedByDepartmentForm"/>
    </tiles:put>
    <script type="text/javascript" src="./dwr/interface/AttachmentService.js"></script>
  <script type="text/javascript">
  function cleanDefect () {
	  if (confirm("Очистить данные о дефекте?")){
	  AttachmentService.cleanDefect($('id').value, {
		  callback: function (aResult) {
			  alert(""+aResult);
		  }
	  });
  }
  }
  </script>
</tiles:insert>