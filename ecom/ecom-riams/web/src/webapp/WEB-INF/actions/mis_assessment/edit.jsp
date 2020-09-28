<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name='body' type='string'>
        <msh:form action="/entitySaveGoView-mis_assessment.do" defaultField="name">
        <msh:hidden property="assessmentCard"/>
        <msh:hidden property="saveType" />
         <msh:textField property="name" label="Название" size="200"/>
       	 <br>
          <msh:textField property="minBall" label="Минимальный балл" size="10"/>
         <br>
            <msh:textField property="maxBall" label="Максимальный балл" size="10"/>
            <br>
        <msh:row>
      <msh:submitCancelButtonsRow colSpan="4"/>
         </msh:row>
      <msh:ifFormTypeAreViewOrEdit formName="mis_assessmentForm">
       <msh:hidden property="id" />
      <tiles:put name='side' type='string'>
	  <msh:sideMenu> 
	 <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_assessment.do" name="Изменить" title="Изменить"/>
	  <msh:sideLink confirm="Удалить?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-mis_assessment.do" name="Удалить" title="Удалить"/>
	   
	  </msh:sideMenu>
	   </tiles:put>
      </msh:ifFormTypeAreViewOrEdit>
       </msh:form>
    </tiles:put>

     <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_assessmentForm" />
    </tiles:put>

</tiles:insert>