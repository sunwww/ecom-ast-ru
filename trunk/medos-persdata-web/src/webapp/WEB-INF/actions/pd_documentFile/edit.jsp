<%@page import="ru.medos.ejb.persdata.form.DocumentFileForm"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
  <%
  DocumentFileForm form = (DocumentFileForm) request.getAttribute("pd_documentFileForm");
  request.setAttribute("referenceTo", form.getReferenceTo()) ;
  request.setAttribute("referenceCompTo", form.getReferenceCompTo()) ;
  %>
    <msh:form action="/entityParentSaveGoView-pd_documentFile.do" defaultField="comment" >
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="document" />
      <msh:panel>
        <msh:row>
          <msh:textArea property="comment" label="Комментарий:"
               size="100" rows="5" fieldColSpan="5" />
               
        </msh:row>
        <msh:row>
          <msh:textField property="referenceTo" label="Ссылка:"
               horizontalFill="true" viewOnlyField="true"/>
          <msh:textField property="referenceCompTo" label="Ссылка сжатого файла:"
               horizontalFill="true" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
          <msh:textField viewOnlyField="true" property="createUsername" label="Создав. польз:"/>
          <msh:textField viewOnlyField="true" property="createDate" label="дата:"/>
          <msh:textField viewOnlyField="true" property="createTime" label="время:"/>
        </msh:row>
        <msh:row>
          <msh:textField viewOnlyField="true" property="editUsername" label="Польз. посл. редак.:"/>
          <msh:textField viewOnlyField="true" property="editDate" label="дата:"/>
        </msh:row>
        <msh:row>
        	<td colspan="6">
        	<img alt="Загрузка изображения" src="/persdoc/${referenceCompTo}" id="imgDocument" width="100%" ondblclick="this.src='/docmis/${referenceTo}'">
        	</td>
        	
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Poly" beginForm="pd_documentFileForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="pd_documentFileForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
      <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-pd_documentFile" name="Изменить" roles="/Policy/Mis/MedCase/Document/External/Edit" guid="609c81cf-05e5-4e07-90b7-87b38863114c" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-pd_documentFile" name="Удалить" roles="/Policy/Mis/MedCase/Document/External/Delete" guid="1a3265b4-cebb-4536-a471-c79003ccf548" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
 
  </tiles:put>
  
</tiles:insert>
