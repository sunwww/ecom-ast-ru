<%@page import="ru.ecom.mis.ejb.form.licence.ExternalDocumentForm"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
  <%
  ExternalDocumentForm form = (ExternalDocumentForm) request.getAttribute("doc_externalDocumentForm");
  request.setAttribute("referenceTo", form.getReferenceTo()) ;
  request.setAttribute("referenceCompTo", form.getReferenceCompTo()) ;
  String[] arr = form.getReferenceTo().split("\\.");
  String ext = arr[arr.length-1];
  //System.out.println("File="+form.getReferenceTo()+" EXT="+ext);
  %>
    <msh:form action="/entityParentSaveGoView-doc_externalDocument.do" defaultField="dateFrom">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:hidden property="medCase" />
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
        
        
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
     <%if (ext!=null&&ext.equals("pdf")) {%>
        <msh:row>
       
        	<td colspan="6">
        	<a href='/docmis/${referenceTo}' target="_blank" >Сохранить файл</a><br>
        	<object><embed src="/docmis/${referenceTo}" width="700" height="500" /></object>
        	
        	</td>
        	</msh:row>
        	<%} else { %>
        	<msh:row>
        	<td colspan="6">
        	<img alt="Загрузка изображения" src="/docmis/${referenceCompTo}" id="imgDocument" width="100%" ondblclick="this.src='/docmis/${referenceTo}'">
        	</td>
        	</msh:row>	
        	<% } %>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Poly" beginForm="doc_externalDocumentForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="doc_externalDocumentForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+5" action="/javascript:gotoMedCase()" name="Перейти к случаю лечения" />
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-doc_externalDocument" name="Изменить" roles="/Policy/Mis/MedCase/Document/External/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-doc_externalDocument" name="Удалить" roles="/Policy/Mis/MedCase/Document/External/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
 
  </tiles:put>
 <tiles:put name="javascript" type="string">
        <script type="text/javascript">
        function gotoMedCase() {
        	window.location = 'entitySubclassView-mis_medCase.do?id='+$('medCase').value;
        }
        </script>
        </tiles:put>
  
</tiles:insert>

