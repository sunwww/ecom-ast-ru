<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:sideMenu title="Общие услуги по специальности">
      <msh:ifFormTypeIsView formName="mis_medServiceBySpecialityForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-mis_medServiceBySpeciality" name="Изменить" roles="/Policy/Mis/MedService/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_medServiceBySpecialityForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_medServiceBySpeciality" name="Удалить" roles="/Policy/Mis/MedService/Edit" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/entitySaveGoView-mis_medServiceBySpeciality.do" defaultField="innerMedService">
      <msh:hidden property="id"/>
      <msh:hidden property="saveType"/>
      <msh:hidden property="speciality"/>
      <msh:panel>
          <msh:row>
              <msh:autoComplete vocName="medServiceAll" property="innerMedService" viewAction="entityParentView-mis_medService.do"  label="Услуга в программе:" horizontalFill="true" fieldColSpan="2" size="100"/>
          </msh:row>
        <msh:row>
          <msh:textField property="countInnerMedService" label="Количество услуг в программе" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="isDefault" />
        </msh:row><msh:row>
          <msh:textField property="weight"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="mis_medServiceBySpecialityForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">
      if (+$('speciality').value>0) {
        $('complexMedServiceName').disabled=true;
      }
        eventutil.addEventListener($('countInnerMedService'), "change",function(){
            $('countInnerMedService').value=parseInt($('countInnerMedService').value);
            if ($('countInnerMedService').value=="NaN") $('countInnerMedService').value="1";
        }) ;
        <msh:ifFormTypeIsCreate formName="mis_medServiceBySpecialityForm">
        $('countInnerMedService').value=1;
        </msh:ifFormTypeIsCreate>
    </script>
  </tiles:put>
</tiles:insert>