<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал операций" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="stac_surOperation" />
    <tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String[] ids = request.getParameter("id").split(":") ;
  	request.setAttribute("addParamSql", ids[0]) ;
  	request.setAttribute("beginDate", ids[1]) ;
  	request.setAttribute("endDate", ids[2]) ;
  	if (ids.length>=4 && ids[3]!=null && !ids[3].equals("") && !ids[3].equals("0")) {
  	  	request.setAttribute("spec", " and so.surgeon_id='"+ids[3]+"'") ;
  	}
  	if (ids.length>=5 && ids[4]!=null && !ids[4].equals("") && !ids[4].equals("0")) {
	  	request.setAttribute("medService", " and so.medService_id='"+ids[4]+"'") ;
  	}
  	if (ids.length>=6 && ids[5]!=null && !ids[5].equals("") && !ids[5].equals("0")) {
	  	request.setAttribute("department", " and so.department_id='"+ids[5]+"'") ;
  	}
  	
    
  %>
    <msh:section guid="863b6d75-fded-49ba-8eab-108bec8e092a">
      <msh:sectionTitle guid="1dcd4d93-235d-4141-a7ee-eca528858925">
        Результаты поиска за дату с ${beginDate} по ${endDate}.
        <msh:link action="journal_surOperation.do">Выбрать другие параметры</msh:link>
      </msh:sectionTitle>
      <msh:sectionContent>
		<ecom:webQuery name="journal_surOperation1" nativeSql="select so.id as id
	    ,coalesce(to_char(so.operationDate,'DD.MM.YYYY')||' '||to_char(so.operationTime,'HH24:MI')||' - '||to_char(so.operationDateTo,'DD.MM.YYYY')||' '||to_char(so.operationTimeTo,'HH24:MI'),to_char(so.operationDate,'DD.MM.YYYY')) as operDate
	    , vo.name as voname
	    ,(select list(' '||vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename) from SurgicalOperation_WorkFunction sowf left join WorkFunction wf on wf.id=sowf.surgeonFunctions_id left join Worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id left join vocworkFunction vwf on vwf.id=wf.workFunction_id where sowf.SurgicalOperation_id=so.id ) as surgOper 
	    ,p.lastname||' '||p.firstname||' '||p.middlename ||' гр '||to_char(p.birthday,'DD.MM.YYYY') as patientInfo,
	    (select list(' '||vam.name|| ' '|| a.duration||' мин '||vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename) as aneth 
	    from Anesthesia a
	    left join VocAnesthesiaMethod vam on vam.id=a.method_id
	    left join WorkFunction wf on wf.id=a.anesthesist_id
	    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
	    left join Worker w on w.id=wf.worker_id
	    left join Patient wp on wp.id=w.person_id
	    
	    where a.surgicalOperation_id=so.id
	    )
	     ,vas.name as vasname
	     ,svwf.name||' '||swp.lastname||' '||swp.firstname||' '||swp.middlename as surinfo
	     from SurgicalOperation so

	    left join WorkFunction swf on swf.id=so.surgeon_id
	    left join VocWorkFunction svwf on svwf.id=swf.workFunction_id
	    left join Worker sw on sw.id=swf.worker_id
	    left join Patient swp on swp.id=sw.person_id

	      left join Patient p on p.id=so.patient_id
	      left join VocAdditionStatus vas on vas.id=p.additionStatus_id
	      left join MedService vo on vo.id=so.medService_id
	       where operationDate 
	        between to_date('${beginDate}','dd.mm.yyyy')
	          and to_date('${endDate}','dd.mm.yyyy') 
	          ${department} ${spec} ${medService}
	           ${addParamSql}
	          order by p.lastname,p.firstname,p.middlename
	        " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
	    <msh:table name="journal_surOperation1" 
	    action="entityView-stac_surOperation.do" idField="1" 
	    viewUrl="entityShortView-stac_surOperation.do"
	    >
	      <msh:tableColumn columnName="#" property="sn" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Статус пациента" property="7"/>
	      <msh:tableColumn columnName="Пациент" property="5"/>
	      <msh:tableColumn columnName="Период операции" property="2" guid="de1f591c-02b8-4875-969f-d2698689db5d" />
	      <msh:tableColumn columnName="Хирург" property="8"/>
	      <msh:tableColumn columnName="Ассистенты" property="4"/>
	      <msh:tableColumn columnName="Операции" property="3"/>
	      <msh:tableColumn columnName="Анестезия" property="6"/>
	    </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>