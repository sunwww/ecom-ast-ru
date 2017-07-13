<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
<tiles:put name="body" type="string">
<ecom:webQuery name="list" nativeSql="select e.id, e.insidenumber,
case when tn.typenumber_id=3 then list(tn.telnumber) else '' end as sot,
case when tn.typenumber_id=2 then list(tn.telnumber) else '' end as gor,
e.comment,
p.lastname||' '||p.firstname||' '||p.middlename as names,vb.name,vbl.name,m.name as dep
from entry e  
left join WorkFunction as wf on wf.id= e.person_id
left join Worker as w on w.id=wf.worker_id
left join Patient as p on p.id=w.person_id
left join department d on d.id=e.department_id
left join mislpu m on m.id=d.department_id
left join vocbuildinglevel vbl on vbl.id = d.buildinglevel_id
left join vocbuilding vb on vb.id = d.building_id
left join telephonenumber tn on tn.entry_id = e.id
left join voctypenumber vtn on vtn.id = tn.typenumber_id
group by e.id,names,vb.name,vbl.name,dep,tn.typenumber_id,e.comment" />
      
	<msh:table name="list" action="entityParentView-directory_entry.do" idField="1">
	<msh:tableColumn columnName="#" property="sn"/>
	<msh:tableColumn columnName="Внутренний номер" property="2"/>
	<msh:tableColumn columnName="Сотовый" property="3"/>
	<msh:tableColumn columnName="Городской" property="4"/>
	<msh:tableColumn columnName="Наименование" property="5"/>
	<msh:tableColumn columnName="ФИО" property="6"/>
	<msh:tableColumn columnName="Корпус" property="7"/>
	<msh:tableColumn columnName="Этаж" property="8"/>
	<msh:tableColumn columnName="Отделение" property="9"/>
	</msh:table>
</tiles:put>
    <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink guid="helloSideLinkNew" roles="/Policy/Mis/Directory/Department" key="ALT+N" action="/entityPrepareCreate-directory_telephonenumber" name="Создать"  />
    </msh:sideMenu>
  </tiles:put>
  	
</tiles:insert>
