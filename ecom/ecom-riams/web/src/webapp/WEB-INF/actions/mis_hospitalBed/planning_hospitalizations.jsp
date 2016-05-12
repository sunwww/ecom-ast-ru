<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  
  <tiles:put name="style" type="string">
  	<style type="text/css">

  	span.discharge {
  		list-style:none outside none;
  		background-color: #33cc66;
  	}
  	span.current {
  		list-style:none outside none;
  		
  	}
  	

  	</style>
  </tiles:put>
  <tiles:put name="body" type="string">
   <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/View">
    <msh:form action="/stac_planning_hospitalizations.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel>
    	<msh:row>
    		<msh:autoComplete property="department" vocName="vocLpuHospOtdAll" fieldColSpan="3" horizontalFill="true" size="200"/>
    	</msh:row>
    	<msh:row>
    		<msh:autoComplete property="roomType" vocName="vocRoomType" label="Уровень палаты"/>
    		<msh:autoComplete property="countBed" vocName="vocCountBedInHospitalRoom" label="Вместимость"/>
    	</msh:row>
    	<msh:row>
    		<msh:separator label="ПЕРИОД" colSpan="4"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="dateBegin" label="Дата начала"/>
    		<msh:textField property="dateEnd" label="Окончания"/>
    	</msh:row>
    	<msh:row>
    		<td colspan="4">
    			<input type="submit" onclick="find()" value="Поиск"/>
    			<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Create">
    			<input type="button" onclick="newP()" value="Добавить" >
    			</msh:ifInRole>
    		</td>
    	</msh:row>
    </msh:panel>
    </msh:form>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
  	
    <script type="text/javascript">
    	function find() {
    		
    	}
    	function newP() {
    		window.location = 'entityPrepareCreate-stac_planHospital.do?department='+$('department').value+"&roomType="+$('roomType').value+"&countBed="+$('countBed').value+"&dateEnd="+$('dateEnd').value+"&dateBegin="+$('dateBegin').value+"&tmp="+Math.random() ;
      	}
    	function editPlanning(aWp) {
    		window.location = 'entityEdit-stac_planHospital.do?id='+aWp+"&tmp="+Math.random() ;
    	}
    	function viewSlo(aSlo) {
    		getDefinition('entityShortView-stac_slo.do?id='+aSlo+"&tmp="+Math.random()) ;
    	}
    	
    	if (+$('department').value<1) {
    		HospitalMedCaseService.getDefaultDepartmentByUser (
					 {
     			callback: function(aResult) {
     				var res = aResult.split('#') ;
     				if (+res[0]!=0) {
     					$('department').value = res[0] ; 
     					$('departmentName').value = res[1] ; 
     				}
     			}
     		}) ;      		
    	} 
    </script>
    </msh:ifInRole>
        <%
    
    String date = (String)request.getParameter("dateBegin") ;
    String dateEnd = (String)request.getParameter("dateEnd") ;
    String id = (String)request.getParameter("id") ;
    String period = (String)request.getParameter("period") ;
    
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    
    String view = (String)request.getAttribute("typeView") ;
    String department = request.getParameter("department") ;
    boolean isgoing = false ;
	if (department!=null && !department.equals("") && !department.equals("0")) {
		request.setAttribute("department", " and wp.lpu_id='"+department+"'") ; 
		request.setAttribute("departmentPlanSql", " and wchb.department_id='"+department+"'") ; 
		request.setAttribute("department1", " slo.department_id='"+department+"' and") ;
		isgoing=true ;
	}
    if (date!=null && !date.equals("") && isgoing) {
    	
    	String roomType = request.getParameter("roomType") ;
    	if (roomType!=null && !roomType.equals("") && !roomType.equals("0")) {
    		request.setAttribute("roomType", " and wp.roomType_id='"+roomType+"'") ;
    	}
    	String countBed = request.getParameter("countBed") ;
    	if (countBed!=null && !countBed.equals("") && !countBed.equals("0")) {
    		request.setAttribute("countBed", " and wp.countBed_id='"+countBed+"'") ;
    	}
    %>
    <msh:section>
    <msh:sectionTitle>Список пациентов</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_pat" nativeSql="
    select wp.id as wpid,wp.name as wpnamw,vcbihr.name as vcbihr
,list(
'<br /><'||'span class=\"'||
case when slo.dateFinish is not null then 'discharge' else 'current' end
||'\">'||
case 
when ${department1} slo.dateStart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
or coalesce(slo.datefinish,slo.transferdate,current_date)  between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')

then p.lastname ||' '|| coalesce(substring(p.firstname,1,1),'') ||' '||coalesce(substring(p.middlename,1,1),'') 
||' '|| to_char(slo.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(slo.dateFinish,'dd.mm.yyyy'),to_char(slo.transferDate,'dd.mm.yyyy')
,'')||'<'||'/span>'
||' <a href='||'\"javascript:void(0)\" onclick=\"viewSlo('||slo.id||')\">Перейти</a>'

else null end

) as realPat
,list('<br />'||case 
when wchb.dateFrom between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
or wchb.dateTo  between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')

then pp.lastname ||' '|| coalesce(substring(pp.firstname,1,1),'') ||' '||coalesce(substring(pp.middlename,1,1),'') 
||' '|| to_char(wchb.dateFrom,'dd.mm.yyyy')||'-'||coalesce(to_char(wchb.dateTo,'dd.mm.yyyy'),'')
||' <a href='||'\"javascript:void(0)\" onclick=\"editPlanning('||wchb.id||')\">Изменить</a>'


else null end
) as planPat
 from workplace wp
left join medcase slo on slo.roomNumber_id=wp.id
left join patient p on p.id=slo.patient_id
left join VocCountBedInHospitalRoom vcbihr on vcbihr.id=wp.countBed_id
left join WorkCalendarHospitalBed wchb on wchb.hospitalRoom_id=wp.id
left join patient pp on pp.id=wchb.patient_id 

where wp.dtype='HospitalRoom' ${department}
${roomType} ${countBed}
group by wp.id,wp.name,vcbihr.name
order by case when length(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(wp.name,'0',''),'1',''),'2',''),'3',''),'4',''),'5',''),'6',''),'7',''),'8',''),'9',''))=0 then cast(wp.name as int) else 100 end
 " />
    <msh:table name="journal_pat" 
     action="javascript:void(0)" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Палата" property="2" />
      <msh:tableColumn columnName="Вместимость" property="3" />
      <msh:tableColumn columnName="Список пациентов, которые лежат" property="4" />
      <msh:tableColumn columnName="Список пациентов, которые планируются" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <msh:section>
    <msh:sectionTitle>Список пациентов, по которым не определены палаты</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_pat" nativeSql="
    select 1,list(
 '<br/>'||p.lastname ||' '|| coalesce(substring(p.firstname,1,1),'') ||' '||coalesce(substring(p.middlename,1,1),'') 
||' '|| to_char(slo.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(slo.dateFinish,'dd.mm.yyyy'),to_char(slo.transferDate,'dd.mm.yyyy')
,'')
||' <a href='||'\"javascript:void(0)\" onclick=\"viewSlo('||slo.id||')\">Перейти</a>'

) as realPat
 from  medcase slo
left join patient p on p.id=slo.patient_id

where  ${department1} slo.dateStart<=to_date('${dateEnd}','dd.mm.yyyy')
and 
(coalesce(slo.datefinish,slo.transferdate) is null
or coalesce(slo.datefinish,slo.transferdate)>= to_date('${dateBegin}','dd.mm.yyyy')
)
and slo.dtype='DepartmentMedCase'

and slo.roomNumber_id is null
 " />
    <msh:table name="journal_pat" 
     action="javascript:void(0)" idField="1" noDataMessage="Нет данных" hideTitle="true">
      <msh:tableColumn columnName="Список пациентов, которые лежат" property="2" />
     </msh:table>
    </msh:sectionContent>
    </msh:section>
    <msh:section title="Список направлений на госпитализацию из поликлиники">
    <ecom:webQuery name="stac_planHospital"
    nativeSql="select wchb.id,ml.name as mlname,p.id,p.lastname||' '||p.firstname||' '||p.middlename as fio,p.birthday
as birthday,mkb.code,wchb.diagnosis
 ,wchb.dateFrom,mc.dateStart,mc.dateFinish,list(mkbF.code),wchb.phone
 ,wchb.createDate as wchbcreatedate
from WorkCalendarHospitalBed wchb
left join Patient p on p.id=wchb.patient_id
left join MedCase mc on mc.id=wchb.medcase_id
left join VocIdc10 mkb on mkb.id=wchb.idc10_id
left join MisLpu ml on ml.id=wchb.department_id
left join Diagnosis diag on diag.medcase_id=mc.id
left join VocIdc10 mkbF on mkbF.id=diag.idc10_id
where wchb.visit_id is not null
and wchb.dateFrom between to_date('${dateBegin}','dd.mm.yyyy')
	 and to_date('${dateEnd}','dd.mm.yyyy')
 ${departmentPlanSql}
group by wchb.id,wchb.createDate,ml.name,p.id,p.lastname,p.firstname,p.middlename,p.birthday
,mkb.code,wchb.diagnosis,wchb.dateFrom,mc.dateStart,mc.dateFinish,wchb.phone
order by wchb.dateFrom,p.lastname,p.firstname,p.middlename
    "
    />
    <msh:table name="stac_planHospital" action="entityParentEdit-stac_planHospital.do"
    idField="1" >
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата пред.госпитализации" property="8"/>
            <msh:tableColumn columnName="Направлен в отделение" property="2"/>
            <msh:tableColumn columnName="Телефон пациента" property="12"/>
            <msh:tableColumn columnName="ФИО пациента" property="4"/>
            <msh:tableColumn columnName="Дата рождения" property="5"/>
            <msh:tableColumn columnName="Код МКБ" property="6"/>
            <msh:tableColumn columnName="Диагноз" property="7"/>
            <msh:tableColumn columnName="Дата создания" property="13"/>
    	
    </msh:table>
    </msh:section>
    <%} %>
    <div id="divViewBed">
    </div>
    
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_planning_hospitalizations"/>
  </tiles:put>

  <tiles:put name="title" type="string">
  </tiles:put>

</tiles:insert>