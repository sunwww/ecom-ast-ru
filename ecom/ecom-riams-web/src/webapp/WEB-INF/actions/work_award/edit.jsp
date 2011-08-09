<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entityParentSaveGoParentView-work_award.do" defaultField="rewardingDate">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="person" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="rewardingDate" label="Дата награждения" />
          <msh:textField property="awardNumber" label="Номер награды" guid="e71fa83a-c6c2-4221-bb72-77067f879971" />
        </msh:row>
        <msh:row guid="72b4d97f-aec0-4993-ab44-5635ffc0f5b8">
          <msh:textField passwordEnabled="false" property="awardOrganization" viewOnlyField="false" label="Наградившая организация" guid="78658304-d2f5-4de0-a72f-6d1739392515" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocAwardGroup" property="awardGroup" guid="3a3e4d1b-8802-467d-b205-715fb379b018" horizontalFill="true" fieldColSpan="3" label="Группа наград" />
        </msh:row>
        <msh:row guid="4c5e31a9-4505-47be-96e1-8fd2f43bd3ef">
          <msh:autoComplete showId="false" vocName="vocAwardType" property="awardType" viewOnlyField="false" label="Тип награды" guid="d025a17b-80e7-4d00-9e44-613add4cd02a" fieldColSpan="3" horizontalFill="true" parentAutocomplete="awardGroup" />
        </msh:row>
        <msh:row guid="fa7207e-2a6e-4bda-855c-077d85bf">
          <msh:textArea rows="2" property="comments" viewOnlyField="false" label="Комментарий" guid="228a25f7-0d1a-49b0-acdb-4bda26c9041f" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="work_awardForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="work_awardForm" insideJavascript="false" guid="3007f4fa-c608-453b-a951-b7482653c750">
      <msh:sideMenu guid="sideMenu-123" title="Проба">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-work_award" name="Изменить" roles="/Policy/IdeMode/Hello/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-work_award" name="Удалить" roles="/Policy/IdeMode/Hello/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

