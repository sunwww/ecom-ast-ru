<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех полисов" guid="3c259ba8-b962-4333-9aab-3316f984fdde" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table editUrl="entitySubclassEdit-mis_medPolicy.do" deleteUrl="entityParentDeleteGoParentView-mis_medPolicy.do"
    name="list" action="entitySubclassView-mis_medPolicy.do"
     idField="id" guid="86ef8e69-6d58-4e49-961a-f2f463e02f80">
        <msh:tableColumn property="text" guid="1a71dc2e-96a6-480d-8dd1-f944bee0344e" />
    </msh:table>
  </tiles:put>
</tiles:insert>

