<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_patientForm"  title="Родственники (представители)" />
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' roles="/Policy/Mis/Patient/Kinsman/Create" params="id" action="/entityParentPrepareCreate-mis_kinsman" name="Добавить родственника (представителя)" />

        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityParentView-mis_kinsman.do" idField="id">
            <msh:tableColumn columnName="Родственник (представитель)" property="kinsmanInfo" />
            <msh:tableColumn columnName="Статус" property="kinsmanRoleInfo" />
        </msh:table>
    </tiles:put>

</tiles:insert>