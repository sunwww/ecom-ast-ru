<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
     <tiles:put name='body' type='string'>
		<!-- 
		   -	Лекарственное вещество 
		  -->
        <msh:form action="entityParentSaveGoParentView-mis_drugNeed.do" defaultField="drugClassifyName">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="privilege"/>

             <msh:panel>

                <msh:row>
                	<msh:autoComplete property="drugClassify" label="Лекарственный препарат" horizontalFill="true" fieldColSpan="3" vocName="vocDrugClassify" size='50'/>
                </msh:row>
                <msh:row>
                    <msh:textField property="tradename" label="Торговое наименование" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                	<msh:textField property='middleMonthDoze' label='Среднемесячная доза (мг)' horizontalFill="false" />
                </msh:row>

                <msh:row>
                	<msh:textField property='issuedDate' label='Дата назначения' />
                	<msh:textField property='cancelDate' label='Дата отмены' />
                </msh:row>
                <msh:row>
                	<msh:autoComplete property='dloDoctor' label='Код врача' horizontalFill="true" vocName="vocDloDoctor" fieldColSpan="3"/>
                </msh:row>
                
                <msh:submitCancelButtonsRow colSpan="4"/>
          </msh:panel>
        </msh:form>
        
        
  </tiles:put>

        <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_drugNeedForm">
                <msh:sideLink key="ALT+2" params="id" roles="/Policy/Mis/Person/Privilege/DrugNeed/Edit" action="/entityEdit-mis_drugNeed" name="Изменить"/>
            </msh:ifFormTypeIsView>
            <hr/>
            <msh:ifFormTypeAreViewOrEdit formName="mis_drugNeedForm">
                <msh:sideLink key='ALT+DEL' params="id" roles="/Policy/Mis/Person/Privilege/DrugNeed/Delete" action="/entityParentDeleteGoParentView-mis_drugNeed" 
                	name="Удалить лекарственное вещество" confirm="Удалить лекарственное вещество?"/>
            </msh:ifFormTypeAreViewOrEdit>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_drugNeedForm" />
    </tiles:put>

</tiles:insert>