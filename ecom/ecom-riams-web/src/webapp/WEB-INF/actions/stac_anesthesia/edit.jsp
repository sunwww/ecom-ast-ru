<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_anesthesiaForm" guid="e20545-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Анестезия">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_anesthesia" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-stac_anesthesia" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Анастезия
    	  -->
    <msh:form action="/entityParentSaveGoParentView-stac_anesthesia.do" defaultField="duration" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="surgicalOperation" guid="9d90-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:row guid="d758c040-4a94-4574-9f0b-4a1600a616fa">
          <msh:textField property="duration" label="Длительность (мин)" guid="aae4294a-21c3-4b10-830d-3c4e0c011a8c" />
        </msh:row>
        <msh:row guid="6f8494e5-f641-4e50-905e-9df8b3cf7f8e">
          <msh:autoComplete property="method" label="Метод" guid="a1f275e2-6780-4c50-a4e9-b1da5b3e15a9" horizontalFill="true" vocName="vocAnesthesiaMethod" fieldColSpan="3" />
        </msh:row>
	        <msh:row>
	          <msh:autoComplete property="type" label="Вид" horizontalFill="true" vocName="vocAnesthesia" fieldColSpan="3" />
	        </msh:row>
            <msh:row>
	          <msh:autoComplete property="medService" label="Услуга" horizontalFill="true" vocName="medServiceAnesthesia" fieldColSpan="3" />
	        </msh:row>
        <msh:row guid="5edd4c78-6105-4726-acba-cc999e71ed78">
          <msh:textArea property="description" label="Описание" guid="fd59275f-0cb5-4959-b7d7-b49a701eb2ee" horizontalFill="true" fieldColSpan="2" />
          <msh:ifFormTypeIsNotView formName="stac_anesthesiaForm" guid="367dc712-da06-4520-a295-57680ba1a56a">
            <td colspan="1">
              <input type="button" value="Шаблон" onClick="showdescriptionTempTemplateProtocol() " />
            </td>
          </msh:ifFormTypeIsNotView>
        </msh:row>
        <msh:row guid="8cf0863d-991d-4e6c-bb0a-ead66299a21c">
          <msh:autoComplete property="anesthesist" label="Исполнитель" vocName="workFunction" guid="56067cb3-f8bd-4c07-9330-ad6ffee3e83a" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:ifFormTypeAreViewOrEdit formName="stac_anesthesiaForm" guid="77f35713-df7e-40b1-a2d8-f045bc17d390">
          <msh:row guid="d1744869-a80c-4e09-ada3-22a87b8e40b3">
            <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" guid="76b0f8ed-caa9-496b-a1bd-9a0081c69198" />
            <msh:textField property="createUsername" label="Пользователь" viewOnlyField="true" guid="4eac72aa-0de9-427e-b2db-15635bb16fe0" />
          </msh:row>
        </msh:ifFormTypeAreViewOrEdit>
        <msh:submitCancelButtonsRow colSpan="2" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
      <msh:ifFormTypeIsNotView formName="stac_anesthesiaForm" guid="6ea7dcbb-d32c-4230-b6b0-a662dcc9f568">
        <tags:templateProtocol property="description" name="descriptionTemp" />
      </msh:ifFormTypeIsNotView>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_anesthesiaForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
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

