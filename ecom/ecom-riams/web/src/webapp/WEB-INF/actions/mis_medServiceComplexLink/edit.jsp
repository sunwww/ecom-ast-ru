<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Связь с комплексной услугой">
      <msh:ifFormTypeIsView formName="mis_medServiceComplexLinkForm" guid="e2054544-85-a21c-3bb9b4569efc">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-mis_medServiceComplexLink" name="Изменить" roles="/Policy/Mis/MedService/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_medServiceComplexLinkForm" guid="a6802286-1d60-46ea-b7f4-f588331a09f7">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_medServiceComplexLink" name="Удалить" roles="/Policy/Mis/MedService/Edit" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoParentView-mis_medServiceComplexLink.do" defaultField="complexMedService" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id"/>
      <msh:hidden property="saveType"/>
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:row>
          <msh:autoComplete vocName="medServiceAll" property="complexMedService" viewAction="entityParentView-mis_medService.do" label="Комплексная услуга" horizontalFill="true" fieldColSpan="2" viewOnlyField="true" />
        </msh:row>
          <msh:row>
              <msh:autoComplete vocName="medServiceAll" property="innerMedService" viewAction="entityParentView-mis_medService.do"  label="Услуга в программе:" horizontalFill="true" fieldColSpan="2" />
          </msh:row>
        <msh:row>
          <msh:textField property="countInnerMedService" label="Количество услуг в программе" guid="b326c2f9-7b10-4370-8141-5afe5a31b104" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="mis_medServiceComplexLinkForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">
        eventutil.addEventListener($('countInnerMedService'), "change",function(){
            $('countInnerMedService').value=parseInt($('countInnerMedService').value);
            if ($('countInnerMedService').value=="NaN") $('countInnerMedService').value="";
        }) ;
        <msh:ifFormTypeIsCreate formName="mis_medServiceComplexLinkForm" guid="e2054544-85-a21c-3bb9b4569efc">
        $('countInnerMedService').value=1;
        </msh:ifFormTypeIsCreate>
    </script>
  </tiles:put>
</tiles:insert>