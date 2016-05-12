<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSave-mis_birthReportDate" defaultField="reportDate">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="lpu" />
      <msh:panel guid="panel" colsWidth="10%,5%,1%,5%,60%">
        <msh:row guid="row-1">
          <msh:textField guid="e71fa83a-c6c2-4221-0-1" fieldColSpan="2" property="reportDate" label="Дата отчета" labelColSpan="1" />
        </msh:row>
        <msh:separator colSpan="4" label="Родилось" guid="6c3c92a5-25f0-4324-83c1-c5e7f8ee89a9" />
        <msh:row guid="row0">
          <th colspan="1" />
          <th colspan="1" style="text-align: right; padding-right: 5px ;">Мальчики</th>
          <th colspan="2" style="text-align: right; padding-right: 5px ;">Девочки</th>
          <td />
        </msh:row>
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="liveBornBoys" label="Родилось живыми:" />
          <msh:textField property="liveBornGirls" guid="e71fa83a-c6c2-4221-bb72-77067f879971" hideLabel="true" />
        </msh:row>
        <msh:row guid="row2">
          <msh:textField guid="e71fa83a-c6c2-4221-2" property="deadBornBoys" label="Родилось мертвыми" />
          <msh:textField property="deadBornGirls" guid="e71fa83a-c6c2-4221-bb72-2" hideLabel="true" />
        </msh:row>
        <msh:separator colSpan="4" label="Роды" guid="72d0494f-4519-4150-ac7a-a0465134bbfa" />
        <msh:row guid="row0">
          <th colspan="1" />
          <th colspan="1" style="text-align: right; padding-right: 5px ;">Роды&nbsp;живыми</th>
          <th colspan="2" style="text-align: right; padding-right: 5px ;">Роды&nbsp;мертвыми</th>
          <td />
        </msh:row>
        <msh:row guid="3cf4a743-7ed0-43be-8d63-9ae588dea3bf">
          <msh:textField passwordEnabled="false" property="firstLiveBirth" label="1-е роды" guid="24b87983-0d73-4eee-85f8-e3678b2791e3" horizontalFill="false" />
          <msh:textField passwordEnabled="false" hideLabel="true" property="firstDeadBirth" viewOnlyField="false" guid="473d7a82-3784-4e67-b7ea-86a76d010c60" horizontalFill="false" />
        </msh:row>
        <msh:row guid="7fca8e3e-8bdf-41c4-88ec-ee731184e1dc">
          <msh:textField passwordEnabled="false" property="secondLiveBirth" label="2-е роды" guid="b8457235-a0d0-4492-a898-4eee1670a7ab" horizontalFill="false" />
          <msh:textField passwordEnabled="false" hideLabel="true" property="secondDeadBirth" viewOnlyField="false" guid="a2b8a039-5e81-4df8-a88d-54488428aee0" horizontalFill="false" />
        </msh:row>
        <msh:row guid="b6d82fe1-27bc-4f05-bd93-b900b261895f">
          <msh:textField passwordEnabled="false" property="otherLiveBirth" label="3-и и последующие роды" guid="12551dff-476e-4784-8318-72cb7900d18f" horizontalFill="false" />
          <msh:textField passwordEnabled="false" hideLabel="true" property="otherDeadBirth" viewOnlyField="false" guid="17143079-3fbb-4f31-8274-89062945ae78" horizontalFill="false" />
        </msh:row>
        <msh:row guid="b0fa5b62-1826-4aee-8a57-8c78e05d4247">
          <msh:textField passwordEnabled="false" property="twinsBirth" label="Родов двойни" guid="8ee76e20-e41c-4fa6-afb4-00f10c9873a3" horizontalFill="false" />
        </msh:row>
        <msh:row guid="2c8715b5-6b3d-405a-b410-cdda35333c97">
          <msh:textField passwordEnabled="false" property="tripletsBirth" label="Родов тройни" guid="e9e71d22-67ba-424f-a4a5-2368f983bf09" horizontalFill="false" />
        </msh:row>
        <msh:row guid="3852267a-15ab-4f44-988b-2d7e5d5d3e8d">
          <msh:checkBox hideLabel="false" property="editComplete" viewOnlyField="false" guid="dcd9bda7-6464-4d67-a3bb-ec9971f3f174" labelColSpan="1" fieldColSpan="3" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Lpu" beginForm="mis_birthReportDateForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_birthReportDateForm" insideJavascript="false" guid="87ba4256-5b3e-43ac-ba3b-edf6bfb49237">
      <msh:sideMenu guid="sideMenu-123">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-mis_birthReportDate" name="Изменить" roles="/Policy/Mis/Report/Birth/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-mis_birthReportDate" name="Удалить" roles="/Policy/Mis/Report/Birth/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

