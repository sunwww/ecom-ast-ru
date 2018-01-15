<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

     <tiles:put name='body' type='string'>
         <% String shorts = (String)request.getParameter("short");%>
		<!-- Льгота -->
        <msh:form action="entityParentSaveGoParentView-mis_privilege.do" defaultField="beginDate">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="person"/>
             <msh:panel>
                 <msh:row>
                    <msh:textField property="beginDate" label="Дата включения" horizontalFill="false" />
                    <msh:textField property="endDate" label="Дата исключения" horizontalFill="false" />
                </msh:row>
                <msh:row>
                	<msh:autoComplete property="category" label="Категория льготника" horizontalFill="true" fieldColSpan="3" vocName="vocPrivilegeCategory"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="numberDoc" label="Номер" horizontalFill="false" size="20"/>
                    <msh:textField property="serialDoc" label="Серия" horizontalFill="false" size="20"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4"/>
          </msh:panel>
        </msh:form>
  </tiles:put>
        <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="mis_privilegeForm">
                <msh:sideLink key="ALT+2" params="id" roles="/Policy/Mis/Person/Privilege/Edit" action="/entityEdit-mis_privilege" name="Изменить"/>
                <msh:sideLink key='ALT+DEL' params="id" roles="/Policy/Mis/Person/Privilege/Delete" action="/entityParentDeleteGoParentView-mis_privilege" name="Удалить льготу" confirm="Удалить льготу?"/>
            </msh:ifFormTypeIsView>
        </msh:sideMenu>
  </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_privilegeForm" />
    </tiles:put>
</tiles:insert>