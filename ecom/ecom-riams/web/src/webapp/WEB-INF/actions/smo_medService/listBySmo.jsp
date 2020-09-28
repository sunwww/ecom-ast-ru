<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список всех услуг по СМО" />
  </tiles:put>
  <tiles:put name="side" type="string">
        <msh:sideMenu title="Случай медицинского обслуживания">
            <msh:sideLink key='ALT+1' action="/entitySubclassView-mis_medCase.do?id=${param.medcase}"
                          name="Текущий СМО"/>
        </msh:sideMenu>
        <msh:sideMenu title="Добавить">
      			<msh:sideLink params="id" action="/entityParentPrepareCreate-smo_medService" name="Услугу" title="Добавить услугу" roles="/Policy/Mis/MedCase/MedService/Create" key="ALT+N" />
    	</msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="allInfo" nativeSql="select sm.id,sm.dateStart,sm.timeExecute, sm.medService_id,vms.name as vmsname, sm.medServiceAmount, sm.workFunctionExecute_id,vwf.name||' ' ||wp.lastname||' '||wp.firstname||' '||wp.middlename, p.record
    	from medcase as sm  
    	left join diary p on p.medCase_id=sm.id 
    	left join medservice vms on vms.id=sm.medService_id 
    	left join workfunction wf on wf.id=sm.workFunctionExecute_id 
    	left join vocworkfunction vwf on vwf.id=wf.workFunction_id 
    	left join worker w on w.id=wf.worker_id 
    	left join patient wp on wp.id=w.person_id 
    	left join Medcase parent on parent.id=sm.parent_id 
    	left join MisLpu d on d.id=parent.department_id 
    	where 
    	sm.parent_id=${param.id } and sm.DTYPE='ServiceMedCase'  "/>
	    	<msh:ifInRole roles="/Policy/Mis/MedCase/MedService/Print">
			    <msh:table selection="multiply" name="allInfo" action="entityParentView-smo_medService.do" idField="1">
		
		                    <msh:tableNotEmpty>
		                        <tr>
		                            <th colspan='4'>
		                                <msh:toolbar>
		                                    <a href='javascript:printMedServices()'>Печать оказанных мед.услуг</a>
		                                    <a href='entityParentPrepareCreate-smo_medService.do?id=${param.id }'>Добавить протокол</a>
		                                </msh:toolbar>
		                            </th>
		                        </tr>
		                    </msh:tableNotEmpty>
		  	   		<msh:tableColumn columnName="#" property="sn"/>
			   		<msh:tableColumn columnName="Дата" property="2"/>
			   		<msh:tableColumn columnName="Время" property="3"/>
			   		<msh:tableColumn columnName="Мед.услуга" property="5"/>
			   		<msh:tableColumn columnName="Кол-во" property="6"/>
			   		<msh:tableColumn columnName="Специалист" property="8"/>
			   		<msh:tableColumn columnName="Заключение" property="9" cssClass="preCell"/>
			    </msh:table>
	    </msh:ifInRole>
	    <msh:ifNotInRole roles="/Policy/Mis/MedCase/MedService/Print">
			    <msh:table name="allInfo" action="entityParentView-smo_medService.do" idField="1">
		  	   		<msh:tableColumn columnName="#" property="sn"/>
			   		<msh:tableColumn columnName="Дата" property="2"/>
			   		<msh:tableColumn columnName="Время" property="3"/>
			   		<msh:tableColumn columnName="Мед.услуга" property="5"/>
			   		<msh:tableColumn columnName="Кол-во" property="6"/>
			   		<msh:tableColumn columnName="Специалист" property="8"/>
			   		<msh:tableColumn columnName="Заключение" property="9" cssClass="preCell"/>
			    </msh:table>	    
	    </msh:ifNotInRole>
 
    

  </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function printMedServices() {
            	var ids = theTableArrow.getInsertedIdsAsParams("id","allInfo") ;
            	if(ids) {
            		//alert(ids) ;
            		window.location = 'print-medServicies.do?multy=1&m=printMedServicies&s=HospitalPrintService&'+ids ;
            	} else {
            		alert("Нет выделенных мед.услуг");
            	}
            }

        </script>
    </tiles:put>
</tiles:insert>

