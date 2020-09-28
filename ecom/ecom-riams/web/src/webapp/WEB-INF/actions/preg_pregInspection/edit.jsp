<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_pregInspectionForm">
      <msh:sideMenu title="Осмотр беременной">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_pregInspection" name="Изменить" roles="/Policy/Mis/Inspection/Pregnancy/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_pregInspection" name="Удалить" roles="/Policy/Mis/Inspection/Pregnancy/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Осмотр беременной
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_pregInspection.do" defaultField="inspectionDate">
      <msh:hidden property="id" />
      <msh:hidden property="pregnancy"  />
      <msh:hidden property="medCase"  />
      <msh:hidden property="saveType"  />
      <msh:panel  colsWidth="5%,20%,5%,20%">
        <msh:row >
          <msh:textField label="Дата осмотра" property="inspectionDate" />
          <msh:textField label="Время осмотра" property="inspectionTime" />
        </msh:row>
        <msh:row>
          <msh:separator label="Осмотр таза" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField label="Таз: D.Sp" property="pelvisDSp" size="20" horizontalFill="true" />
          <msh:textField label="D.Cr." property="pelvisDCr" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="D.Tr." property="pelvisDTr" horizontalFill="true" />
          <msh:textField label="C.ext." property="pelvisCExt" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="C.diag." property="pelvisCDiag" horizontalFill="true" />
          <msh:textField label="C.later" property="pelvisCLater" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="Ромб Михаэлиса прод." property="rhombLongitudinal" horizontalFill="true" />
          <msh:textField label="попер" property="rhombTransversal" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Наружное акушерское исследование" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField label="Индекс Соловьева" property="solovievIndex" horizontalFill="true" />
          <msh:textField label="Окружн. живота" property="abdomenCircle" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField label="Высота дна матки над лоном" property="uterusHeight" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocFetusLocation" label="Положение плода" property="fetusLocation" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocPreviusPart" label="Предлежащая часть" property="previusPart" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField label="Высота ее стояния" property="previusPartHeight" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocFetusPalpitationPlace" label="Сердцебиение плода место" property="fetusPalpitationPlace" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocFetusPalpitationNature" label="характер" property="fetusPalpitationNature" horizontalFill="true" />
          <msh:textField label="число уд." property="fetusPalpitationRate" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:separator label="Влагалищное исследование" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocPregnancyActivity" label="Родовая деятельность" property="pregnancyActivity" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocVaginalDischarge" label="Влагалищные выделения" property="vaginalDischarge" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:separator label="Осмотр производил" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="workFunction" property="workFunction" label="Специалист" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_pregInspectionForm" />
  </tiles:put>
</tiles:insert>

