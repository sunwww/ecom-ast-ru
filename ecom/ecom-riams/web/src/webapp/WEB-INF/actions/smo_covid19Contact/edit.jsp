<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title title="Covid 2019" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-smo_covid19Contact.do" defaultField="lastname">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="card" />
      <msh:panel>
        <msh:row>
         <msh:textField property="lastname" size="50" />
        </msh:row><msh:row>
         <msh:textField property="firstname" horizontalFill="true" />
      </msh:row><msh:row>
         <msh:textField property="middlename" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:label property="birthDate" />
      </msh:row><msh:row>
          <msh:textField property="phone" horizontalFill="true" />
      </msh:row><msh:row>
          <msh:textField property="address" horizontalFill="true" />
      </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>


  </tiles:put>
  <tiles:put name="side" type="string">

 </tiles:put>
</tiles:insert>

