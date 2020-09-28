<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник рабочих функций
    	  -->
    <msh:form action="/entitySaveGoView-work_attorney.do" defaultField="type">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
      <msh:row>
      <msh:autoComplete property="type" vocName="vocAttorneyType" fieldColSpan="4" size="50"/>
      </msh:row>
      <msh:row>
      <msh:autoComplete property="person" vocName="patient" fieldColSpan="4" size="50"/>
      </msh:row>
      <msh:row>
         <msh:textField property="altPersonInfo" label="ФИО в родительном падеже" fieldColSpan="4" size="50" />
      </msh:row>
        <msh:row>
          <msh:textField property="attorneyNumber" />
          <msh:textField property="attorneyStartDate" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isDefault"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isArchive"/>
        </msh:row>
       
       
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="work_attorneyForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
   <msh:sideMenu title="Перейти">
      <msh:sideLink roles="/Policy/Mis/Worker/Attorney/View" key="ALT+2" params="id" action="/entityList-work_attorney" name="Список доверенностей" title="Список доверенностей" />
    </msh:sideMenu>
    <msh:sideMenu title="Доверенность">
      <msh:sideLink roles="/Policy/Mis/Worker/Attorney/Edit" key="ALT+2" params="id" action="/entityEdit-work_attorney" name="Изменить" title="Изменить " />
      <msh:sideLink roles="/Policy/Mis/Worker/Attorney/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-work_attorney" name="Удалить" title="Удалить " />
    </msh:sideMenu>
         
  </tiles:put>
  
<tiles:put name="javascript" type="string">
  <script type='text/javascript'>
    personAutocomplete.addOnChangeCallback(function() {
    	$('altPersonInfo').value = $('personName').value;
    })
    

  </script>
</tiles:put>
</tiles:insert>

