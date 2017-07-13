<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
<tiles:put name="body" type="string">

<ecom:webQuery name="list" nativeSql="select e.id, e.comment,p.lastname||' '||p.firstname||' '||p.middlename, vwf.name from entry e
left join WorkFunction as wf on wf.id= e.person_id
left join Worker as w on w.id=wf.worker_id
left join Patient as p on p.id=w.person_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id" />
      
	<msh:table name="list" action="entityParentView-directory_entry.do" idField="1">
	<msh:tableColumn columnName="#" property="sn"/>
	<msh:tableColumn columnName="Наименование" property="2"/>
	<msh:tableColumn columnName="ФИО" property="3"/>
	<msh:tableColumn columnName="Рабочая функция" property="4"/>
	</msh:table>
</tiles:put>

 <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-directory_entry" name="Новый расчет" title="Добавить случай стационарного лечения в отделении" 
      roles="/Policy/Mis/Calc/Calculation/Create" key="ALT+2"/>
    </msh:sideMenu>
  </tiles:put>
  
  	<tiles:put name="side" type="string">
	<!-- <msh:ifFormTypeIsView formName="directory_entryForm"> -->
		<msh:sideMenu title="Добавить">
			<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-calc_calculations.do" name="функционал" title="функционал"/>
		</msh:sideMenu>
	<!--</msh:ifFormTypeIsView>-->
	</tiles:put>
</tiles:insert>
