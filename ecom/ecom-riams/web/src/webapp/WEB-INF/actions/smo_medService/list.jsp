<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список всех услуг" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/js-smo_medService-add.do" name="Добавить услугу" title="Добавить услугу" roles="/Policy/Mis/MedCase/MedService/Create" key="ALT+N" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="allInfo" nativeSql="select sm.id,sm.dateStart,sm.timeExecute
    , sm.medService_id,vms.code||' '||vms.name as vmsname, sm.medServiceAmount, sm.workFunctionExecute_id
    ,vwf.name||' ' ||wp.lastname||' '||wp.firstname||' '||wp.middlename as workinfo
    , mkb.code||' '||mkb.name as mk10b
    , case when parent.DTYPE='HospitalMedCase' then 'Приемное отделение' when parent.DTYPE='DepartmentMedCase' then d.name else '' end 
    from medcase as sm  
    left join diary p on p.medCase_id=sm.id 
    left join vocidc10 mkb on mkb.id=sm.idc10_id 
    left join medservice vms on vms.id=sm.medService_id 
    left join workfunction wf on wf.id=sm.workFunctionExecute_id 
    left join vocworkfunction vwf on vwf.id=wf.workFunction_id 
    left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join Medcase parent on parent.id=sm.parent_id left join MisLpu d on d.id=parent.department_id where ('DepartmentMedCase'=(select DTYPE from medcase where id=${param.id }) and  (sm.parent_id in (select id from medcase  where  parent_id=(select parent_id from medcase where id=${param.id } )) or sm.parent_id=(select parent_id from medcase where id=${param.id } )) and sm.DTYPE='ServiceMedCase' ) or ('HospitalMedCase'=(select DTYPE from medcase where id=${param.id }) and  (sm.parent_id in (select id from medcase  where  parent_id=${param.id}) or sm.parent_id=${param.id }) and sm.DTYPE='ServiceMedCase' ) "/>
	    <msh:section title="Просмотр всех услуг по СЛС, включая все СЛО" createUrl="js-smo_medService-add.do?id=${param.id}" createRoles="/Policy/Mis/MedCase/MedService/Create">
		    	<msh:table name="allInfo" action="entityParentView-smo_medService.do" idField="1">
		    		<msh:tableColumn columnName="#" property="sn"/>
		    		<msh:tableColumn columnName="Отделение" property="10"/>
		    		<msh:tableColumn columnName="Дата" property="2"/>
		    		<msh:tableColumn columnName="Время" property="3"/>
		    		<msh:tableColumn columnName="Мед.услуга" property="5"/>
		    		<msh:tableColumn columnName="Кол-во" property="6"/>
		    		<msh:tableColumn columnName="Специалист" property="8"/>
		    		<msh:tableColumn columnName="МКБ 10" property="9" />
		    	</msh:table>
	    </msh:section>
    
  </tiles:put>
</tiles:insert>

