<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
<tiles:put name="body" type="string">
<ecom:webQuery name="list" nativeSql="select d.id,vb.name,vbl.name, m.name as dep from department d
left join mislpu m on m.id = d.department_id
left join vocbuildinglevel vbl on vbl.id = d.buildinglevel_id
left join vocbuilding vb on vb.id = d.building_id" />
      
	<msh:table name="list" action="entityParentView-directory_department.do" idField="1">
	<msh:tableColumn columnName="#" property="sn"/>
	<msh:tableColumn columnName="Корпус" property="2"/>
	<msh:tableColumn columnName="Этаж" property="3"/>
	<msh:tableColumn columnName="Отделение" property="4"/>
	</msh:table>
</tiles:put>

 <tiles:put name="side" type="string">
   <msh:sideMenu title="Добавить">
     <msh:sideLink params="id" 
     action="/entityPrepareCreate-directory_department.do" name="Новое отделение" title="Добавить новое отделение" roles="/Policy/Mis/Directory/Department" key="ALT+2"/>
   </msh:sideMenu>
 </tiles:put>
</tiles:insert>
