<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_audiologicScreeningForm" guid="55c9e18b-8454-4c29-a3d3-d0d4859b2e43">
      <msh:sideMenu title="Аудиологический скрининг" guid="4ee67cb9-118e-44ae-a994-10221e06b7bf">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_audiologicScreening" name="Изменить" roles="/Policy/Mis/Inspection/AudiologicScreening/Edit" guid="6fcac18e-3b14-4a42-8a2d-5fa46e9b0435" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_audiologicScreening" name="Удалить" roles="/Policy/Mis/Inspection/AudiologicScreening/Delete" guid="6a91ad1d-2a8d-4221-a1ac-8a0df12dbae4" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Аудиологический скрининг
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_audiologicScreening.do" defaultField="inspectionDate" guid="ce659606-d5c4-4dad-9953-3d7f7678514d">
      <msh:hidden property="id" guid="56e43f19-1b88-48b2-b442-92f5165c3117" />
      <msh:hidden property="medCase" guid="932d2570-0309-4663-b06f-606ab27178cb" />
      <msh:hidden property="saveType" guid="9815b7c2-9ace-4cf2-a1a0-497f7e610b59" />
      <msh:panel guid="f7d4ef9a-39fb-4a5f-9d43-bf5d83778203">
        <msh:row guid="87873415-13cd-4d90-8263-a596e518661c">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row guid="76580e17-545d-403b-b1d4-dec5c8e68b5e">
          <msh:textField property="inspectionDate" label="Дата осмотра" guid="f705d4b2-6477-419a-adf9-8ec642542f27" />
          <msh:textField property="inspectionTime" label="Время осмотра" guid="e093c5c2-f6b4-497d-b84e-6f8653295cf5" />
        </msh:row>
        <msh:row guid="99f8341f-e873-4014-bd1a-a5d8e9cbf722">
          <msh:textArea property="notes" label="Описание" guid="0aca3827-aec8-40d9-b8da-641f2d993af4" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="8db04efa-d5b9-4524-9f16-6886d83852a3">
          <msh:autoComplete showId="false" vocName="workFunction" hideLabel="false" property="workFunction" viewOnlyField="false" label="Специалист" guid="7922e01d-3977-4b26-b652-260cdc1390a1" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="d6c696c6-1534-4ed1-8d33-2089a6d78d37" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_audiologicScreeningForm" guid="ba61975b-cf27-4369-810a-0acb51dd5966" />
  </tiles:put>
</tiles:insert>

