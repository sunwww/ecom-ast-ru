<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form guid="formHello" action="/entitySaveGoView-rec_recording.do" defaultField="hello">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenParent" property="parent" />
      <msh:panel guid="panel">
        <msh:row guid="row1">
          <msh:textField guid="textFieldHello" property="hello" label="Фамилия" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Имя" guid="2910219b-15ca-4c3b-9f50-cb4fd3849c8c" horizontalFill="false" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Отчество" guid="0c239696-64e4-4685-9863-006a767d6f16" horizontalFill="false" />
        </msh:row>
        <msh:row guid="a91f6f79-ba2b-4a2a-a7b1-f807019e093a">
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Дата рождения" guid="083fb75f-4b11-43cb-b718-673c1d979f53" horizontalFill="false" />
        </msh:row>
        <msh:row guid="fc47fb98-5021-44e3-8bec-6f15739af95b">
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="СНИЛС" guid="a9988bfd-bc6d-4e6c-83c0-b931b3561fe0" horizontalFill="false" />
        </msh:row>
        <msh:row guid="22d48931-e080-4c32-a746-91c86d712723">
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Серия полиса ОМС" guid="34dea214-3ce9-4574-9f04-27d6be9db4da" horizontalFill="false" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Номер полиса ОМС" guid="62ad96c2-c9d9-4155-b2ef-e559c83b8e33" horizontalFill="false" />
        </msh:row>
        <msh:row guid="3499010b-f6a3-44da-ad3e-194dd0cd5b4f">
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Дата действия с" guid="b3b19609-25cc-449b-8ae4-0ef422564d22" horizontalFill="false" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="по" guid="a57bed40-ed64-445b-84cb-16021988fd04" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="rec_recordingForm" title="Запись на прием" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="sideMenu-123" title="Запись на прием">
      <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentPrepareCreate-rec_recording" name="Записаться" title="Записаться на прием" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8" />
  </tiles:put>
</tiles:insert>

