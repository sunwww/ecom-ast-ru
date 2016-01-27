<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
    
        <msh:form action="entitySaveGoView-mis_claim.do" defaultField="id">
        
            <msh:hidden property="id"/>
            <msh:hidden property="workfunction"/>
            <msh:hidden property="saveType"/>
            <msh:panel guid="panel" colsWidth="20% 20% 15%">
       
        <msh:ifFormTypeIsCreate formName="mis_claimForm">
         <msh:row guid="row1">
          <msh:textArea  property="description" label="Текст заявки" />
          
        </msh:row>
        <msh:row>
      	  <msh:textField property="phone" label="Контактный телефон"/>
        </msh:row>
        <msh:row guid="cfba9b91-b2af-4867-aab3-29a1f39833fd">
          <msh:autoComplete vocName="vocClaimType" property="claimType" label="Тип заявки" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        </msh:ifFormTypeIsCreate>
        
        <msh:ifFormTypeAreViewOrEdit formName="mis_claimForm">
          <msh:row>
          	<msh:textArea  property="description" label="Текст заявки" viewOnlyField="true" />
          </msh:row>
           <msh:row>
      	  <msh:textField property="phone" label="Контактный телефон" viewOnlyField="true"/>
        </msh:row>
          <msh:row>
	         <msh:autoComplete vocName="vocClaimType" property="claimType" label="Тип заявки" fieldColSpan="3" horizontalFill="true" viewOnlyField="true"/>
          </msh:row> 
          <msh:row>
	         <msh:autoComplete vocName="workFunction" property="workfunction" label="Создал" fieldColSpan="3" horizontalFill="true" viewOnlyField="true"/>
          </msh:row> 
	        <msh:separator label="Информация о создании заявки" colSpan="10"></msh:separator>
     	<msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	</msh:row> <msh:row>
        	<msh:label property="createTime" label="Время создания"/>
        	</msh:row> <msh:row>
          	<msh:label property="username" label="Пользователь" />
        </msh:row>   
        <msh:separator label="Информация о просмотре заявки оператором" colSpan="10"></msh:separator>
        <msh:row>
        	<msh:label property="viewDate" label="Дата просмотра оператором"/>
        	</msh:row> <msh:row>
        	<msh:label property="viewTime" label="Время просмотра оператором"/>
        	</msh:row> <msh:row>
          	<msh:label property="viewUsername" label="Оператор" />
        </msh:row>  
        <msh:separator label="Информация об исполнении заявки" colSpan="10"></msh:separator>
        <msh:row>
        	<msh:label property="startWorkDate" label="Дата начала исполнения"/>
        	</msh:row> <msh:row>
        	<msh:label property="startWorkTime" label="Время начала исполнения"/>
        	</msh:row> <msh:row>
          	<msh:label property="startWorkUsername" label="Исполнитель" />
        </msh:row>  
        <msh:separator label="Информация о выполнении заявки" colSpan="10"></msh:separator>
        <msh:row>
        	<msh:label property="finishDate" label="Дата исполнения"/>
        	</msh:row> <msh:row>
        	<msh:label property="finishTime" label="Время исполнения"/>
        	</msh:row> <msh:row>
          	<msh:label property="finishUsername" label="Пользователь, подтвердивший исполнение" />
        </msh:row>  
        
        <msh:separator label="Информация об отмене заявки" colSpan="10"></msh:separator>
        <msh:row>
        	<msh:label property="cancelDate" label="Дата отмены"/>
        	</msh:row> <msh:row>
        	<msh:label property="cancelTime" label="Время отмены"/>
        	</msh:row> <msh:row>
          	<msh:label property="cancelUsername" label="Пользователь, отменивший заявку" />
        </msh:row>  
        
        <msh:ifInRole roles="/Policy/Mis/Claim/Operator">
        
        </msh:ifInRole>
        
        
        </msh:ifFormTypeAreViewOrEdit>
        <msh:ifFormTypeIsCreate formName="mis_claimForm">
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
        </msh:ifFormTypeIsCreate>
      </msh:panel>
      </msh:form>
    </tiles:put>

    <tiles:put name='side' type='string'>
      <msh:ifFormTypeAreViewOrEdit formName="mis_claimForm">
        <msh:sideMenu>
	        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-mis_claim" name="Изменить" roles="/Policy/Mis/Claim/Edit" />
	   </msh:sideMenu>
      </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
<tiles:put name="javascript" type="string">
	  	<script type="text/javascript">
		  	</script>
	  </tiles:put>  

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_claimForm" />
    </tiles:put>


</tiles:insert>