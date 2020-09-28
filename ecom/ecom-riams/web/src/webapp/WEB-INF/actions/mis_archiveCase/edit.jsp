<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    
   
    <tiles:put name='body' type='string'>
    
        <msh:form action="entitySaveGoView-mis_claim.do" defaultField="id">
        
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:panel colsWidth="20% 20% 15%">
       		<input type='hidden' id='statusState'>
        
        <msh:ifFormTypeAreViewOrEdit formName="mis_archiveCaseForm">
         <msh:row>
         <msh:textField property="patient" label="Пациент" viewOnlyField="true"/>
        </msh:row>
          <msh:row>
	         <msh:textField property="statNumber" label="Стат. карта" fieldColSpan="3" horizontalFill="true" viewOnlyField="true"/>
          </msh:row> 
          
	        <msh:separator label="Информация о создании заявки" colSpan="10"></msh:separator>
     	<msh:row>
     	<msh:row>
	         <msh:autoComplete vocName="workFunction" size="50"  property="workfunction" label="Создал" fieldColSpan="3" horizontalFill="true" viewOnlyField="true"/>
          </msh:row> 
        	<msh:label property="createDate" label="Дата создания"/>
        	</msh:row> <msh:row>
        	<msh:label property="createTime" label="Время создания"/>
        	</msh:row> <msh:row>
          	<msh:label property="createUsername" label="Пользователь" />
        </msh:row>   
             
        </msh:ifFormTypeAreViewOrEdit>
      </msh:panel>
      </msh:form>
    
    </tiles:put>

    <tiles:put name='side' type='string'>
      <msh:ifFormTypeAreViewOrEdit formName="mis_archiveCaseForm">
        <msh:sideMenu>
	        <msh:sideLink key="ALT+2" params="id" action="/entityPrepareDelete-mis_archiveCase" name="Удалить" roles="/Policy/Mis/Claim/Delete" />
	   </msh:sideMenu>
      </msh:ifFormTypeAreViewOrEdit>
       
    </tiles:put>
    
  

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_archiveCaseForm" />
    </tiles:put>


</tiles:insert>