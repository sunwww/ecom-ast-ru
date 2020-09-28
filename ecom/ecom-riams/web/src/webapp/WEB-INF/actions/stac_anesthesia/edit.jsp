<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_anesthesiaForm">
      <msh:sideMenu title="Анестезия">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_anesthesia" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-stac_anesthesia" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Анастезия
    	  -->
    <msh:form action="/entityParentSaveGoParentView-stac_anesthesia.do" defaultField="duration">
      <msh:hidden property="id" />
      <msh:hidden property="surgicalOperation" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <msh:textField property="duration" label="Длительность (мин)" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="method" label="Метод" horizontalFill="true" vocName="vocAnesthesiaMethod" fieldColSpan="3" />
        </msh:row>
	        <msh:row>
	          <msh:autoComplete property="type" label="Вид" horizontalFill="true" vocName="vocAnesthesia" fieldColSpan="3" />
	        </msh:row>
            <msh:row>
	          <msh:autoComplete property="medService" label="Услуга" horizontalFill="true" vocName="medServiceAnesthesia" fieldColSpan="3" size="100"/>
	        </msh:row>
        <msh:row>
          <msh:textArea property="description" label="Описание" horizontalFill="true" fieldColSpan="2" />
          <msh:ifFormTypeIsNotView formName="stac_anesthesiaForm">
            <td colspan="1">
              <input type="button" value="Шаблон" onClick="showdescriptionTempTemplateProtocol() " />
            </td>
          </msh:ifFormTypeIsNotView>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="anesthesist" label="Исполнитель" vocName="workFunction" size="100" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:ifFormTypeAreViewOrEdit formName="stac_anesthesiaForm">
          <msh:row>
            <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" />
            <msh:textField property="createUsername" label="Пользователь" viewOnlyField="true" />
          </msh:row>
        </msh:ifFormTypeAreViewOrEdit>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
      <msh:ifFormTypeIsNotView formName="stac_anesthesiaForm">
        <tags:templateProtocol property="description" name="descriptionTemp" />
      </msh:ifFormTypeIsNotView>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_anesthesiaForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
	  	var oldaction = document.forms[0].action ;
    	document.forms[0].action = 'javascript:checkCount()';
    	function checkCount() {
    		if ($('duration').value>4 || confirm('Продолжительность анестезии меньше 5 минут. Вы действительно хотите продолжить?')) {
	    		document.forms[0].action = oldaction ;
	    		document.forms[0].submit() ;
    		} else {
    			$('duration').focus() ;
    			$('duration').select() ;
    		}
    	}
    </script>
  </tiles:put>
</tiles:insert>

