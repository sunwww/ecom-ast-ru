<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="LaboratoryJournal" title="Журнал назначений" />
   
  </tiles:put>
  <tiles:put name="side" type="string">
     <msh:sideMenu>
                <tags:laboratory_menu currentAction="pres_journal"/>
        </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String typeState =ActionUtil.updateParameter("PrescriptJournal","typeState","3", request) ;
  String typeGroup =ActionUtil.updateParameter("PrescriptJournal","typeGroup","1", request) ;
  String typeReestr =request.getParameter("typeReestr") ;
  if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {
	 %>
	 
  <msh:form action="/lab_chief_report.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
	
	    <msh:row>
	    <td class="label" title="Тип журнала (typeGroup)"><label for="typeGroupName" id="typeGroupLabel">Тип журнала:</label></td>
	    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeGroup" value="1"> Контроль выполнения исследований
        </td>
	    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeGroup" value="2"> Отчет по выполнению иссделований
        </td>
	    </msh:row>
      <msh:row>
        <td class="label" title="Назначения (typeState)"><label for="typeStateName" id="typeStateLabel">Контроль выполнения исследований: состояние назначения:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeState" value="1"> бракованные
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeState" value="2"> невыполненные
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeState" value="3"> неподтверженные
        </td>

       </msh:row>
     
     
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="prescriptType" fieldColSpan="4" horizontalFill="true" label="Тип назначения" vocName="vocPrescriptType"/>
        </msh:row>
       
      <msh:row>
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    <%} %>
    <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
           <script type='text/javascript'>
       //    checkFieldUpdate('typeIntake','${typeIntake}',1) ;
           checkFieldUpdate('typeGroup','${typeGroup}',1) ;
           checkFieldUpdate('typeState','${typeState}',1) ;
        //   checkFieldUpdate('typeTransfer','${typeTransfer}',1) ;
       //    checkFieldUpdate('typeService','${typeService}',1) ;

   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }
    if ($('beginDate').value=="") {
    	$('beginDate').value=getCurrentDate() ;
    }
    function getCancelReason(aMaterialId) {
		aMaterialId=prompt('Введите причину отмены:', aMaterialId) ;
		if (aMaterialId==null || aMaterialId=="") {
			if (confirm('Неопределена причина отмены. Хотите ввести еще раз?')) {
				return getMaterialId(aMaterialId) ;
			}
		} else {
			return aMaterialId ;
		}
		return null ;
	}
    function cancelService(aListPrescript, aReason) {
			var aReason = getCancelReason() ;
			if (aReason!=null) {
				PrescriptionService.cancelService(aListPrescript, aReason, { 
	            callback: function() {
	            	window.document.location.reload();
	            }
			}); 
			}
		}
			 
    </script>
  	<%
  	request.setAttribute("j", "<input type=\"button\" value=\"Отмена услуги\" onclick=\""
  		    +"cancelService('||p.id||')\"/>") ;
  		    
  	String username = LoginInfo.find(request.getSession(true)).getUsername() ;
  	//ActionUtil.getValueBySql("select lpu.id,lpu.name from mislpu lpu left join worker w on w.lpu_id=lpu.id left join workfunction wf on wf.worker_id=w.id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'", "lpu_id","lpu_name",request) ;
  	//Object lpu = request.getAttribute("lpu_id") ;
  	String beginDate = request.getParameter("beginDate") ;
  	String endDate = request.getParameter("endDate") ;
  	String department = request.getParameter("department");
  	String prescriptType = request.getParameter("prescriptType");
  	String state = request.getParameter("state");
  	
  	if (endDate==null|| endDate.equals("")) {endDate=beginDate;}
  	if (beginDate!=null && !beginDate.equals("")) {
  		request.setAttribute("beginDate", beginDate) ;
  		request.setAttribute("endDate", endDate) ;
  		StringBuilder title = new StringBuilder() ;
  		StringBuilder sqlAdd = new StringBuilder() ;
  		StringBuilder href = new StringBuilder() ;
  		
  		
  		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
  				, "отделение","department","ml.id", request)) ;
  		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocPrescriptType where id=:id"
  				, "тип назначения","prescriptType","vpt.id", request)) ;
  		
  		title.append(request.getAttribute("departmentInfo"))
  			.append(" ").append(request.getAttribute("prescriptTypeInfo")) 
  			.append(" ").append(request.getAttribute("serviceInfo")) ;
  		if (typeGroup!=null&&typeGroup.equals("1")){
	  		if (typeState!=null && typeState.equals("1")) {
	  			sqlAdd.append(" and p.canceldate is not null  ") ;
	  		} else if (typeState!=null && typeState.equals("2")) {
	  			sqlAdd.append(" and p.transferdate is not null and p.canceldate is null and p.medcase_id is null ") ;
	  		} else if (typeState!=null&&typeState.equals("3")) {
	  			sqlAdd.append(" and p.transferdate is not null and p.canceldate is null and p.medcase_id is not null and mc.datestart is null ") ;
	  		}
  		} 
  	//	if (typeGroup!=null&&typeGroup.equals("1")) {
	  		if (state!=null&&state.equals("0")) {
	  			sqlAdd.append(" and p.medcase_id is null and p.canceldate is null");
	  		} else if (state!=null&&state.equals("1")) {
	  			sqlAdd.append(" and p.medcase_id is not null and mc.datestart is null and p.canceldate is null");
	  		} else if (state!=null&&state.equals("2")) {
	  			sqlAdd.append(" and mc.datestart is not null and p.canceldate is null");
	  		}
  		//} 
		if (typeState!=null&&!typeState.equals("")){href.append("&typeState=").append(typeState) ;}
		if (prescriptType!=null&&!prescriptType.equals("")){href.append("&prescriptType=").append(prescriptType);}
		if (department!=null&&!department.equals("")){href.append("&department=").append(department);}
		href.append("&");
		request.setAttribute("sqlAdd", sqlAdd.toString()) ;
		request.setAttribute("href", href.toString()) ;
		request.setAttribute("title", title.toString()) ;
		
		if (typeGroup!=null && (typeReestr==null || typeReestr.equals("0"))) {
			
		
  		if (typeGroup!=null &&typeGroup.equals("1"))
  		{
  	%>
    <msh:section>
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
select '&department='||ml.id,ml.name, count(p.id)
from prescription p 
left join WorkFunction wf on wf.id=p.prescriptSpecial_id
left join Worker w on w.id=wf.worker_id
left join MisLpu ml on ml.id=w.lpu_id
left join medcase mc on mc.id=p.medcase_id
left join vocprescripttype vpt on vpt.id=p.prescripttype_id
where p.dtype='ServicePrescription' and p.intakedate between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
${sqlAdd}

group by ml.id, ml.name
order by ml.name
    "/>
    <msh:sectionTitle>Свод ${departmentInfo}
    
    </msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="list" action="lab_chief_report.do?typeReestr=1&beginDate=${beginDate}&endDate=${endDate}${href}" idField="1" >
	      <msh:tableColumn columnName="Отделение" property="2"  />
	      <msh:tableColumn columnName="Количество назначений" property="3"  />
	    </msh:table>
    </msh:sectionContent>
    </msh:section>
  	
  	<%	
  		} else if (typeGroup!=null &&typeGroup.equals("2")) {
  			%>
  			 <msh:section>
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
    
    select '&prescriptType='||vpt.id as fldId ,vpt.name as f2_name
,count (case when p.medcase_id is null then p.id else null end) as cntNotReady
,count (case when p.medcase_id is not null and mc.datestart is null then p.id else null end) as cntReady
,count (case when mc.datestart is not null then p.id else null end) as cntConfirmed

from prescription p 
left join medcase mc on mc.id=p.medcase_id
left join vocprescripttype vpt on vpt.id=p.prescripttype_id
left join WorkFunction wf on wf.id=p.prescriptSpecial_id
left join Worker w on w.id=wf.worker_id
left join MisLpu ml on ml.id=w.lpu_id
where p.dtype='ServicePrescription' and p.transferdate between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
    ${sqlAdd}
and p.canceldate is null	
group by vpt.id, vpt.name

    "/>
    <msh:sectionTitle>Свод ${departmentInfo}
    
    </msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table cellFunction="true" name="list" action="lab_chief_report.do?typeReestr=1&beginDate=${beginDate}&endDate=${endDate}${href}" idField="1" >
	      <msh:tableColumn columnName="Отделение" property="2"  />
	      <msh:tableColumn columnName="Не выполнено" property="3" addParam="&state=0"  />
	      <msh:tableColumn columnName="Не подтверждено" property="4" addParam="&state=1" />
	      <msh:tableColumn columnName="Выполнено" property="5" addParam="&state=2" />
	    </msh:table>
    </msh:sectionContent>
    </msh:section>
  			<%
  		}
  		
	} else if (typeReestr!=null && typeReestr.equals("1")) {
	%>
	<msh:section>
	<ecom:webQuery name="reestr" nameFldSql="reestr_sql" nativeSql="select pat.id, coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id)
    ,pat.lastname,pat.firstname,pat.middlename
    ,coalesce(vsst.name,'---') as vsstname
    , p.materialId as material
    ,ms.code||' '||ms.name as f8_medServicies
    ,list(coalesce (vpcr.name,'')||' '||coalesce(p.cancelreasontext,'') )as f9_cancelReason
    from prescription p
    left join PrescriptionList pl on pl.id=p.prescriptionList_id
    left join MedCase slo on slo.id=pl.medCase_id
    left join MedCase sls on sls.id=slo.parent_id
    left join StatisticStub ssSls on ssSls.id=sls.statisticstub_id
    left join StatisticStub ssSlo on ssSlo.id=slo.statisticstub_id
    left join Patient pat on pat.id=slo.patient_id
    left join MedService ms on ms.id=p.medService_id
    left join MedService pms on pms.id=ms.parent_id
    left join VocServiceType vst on vst.id=ms.serviceType_id
    left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id
    left join WorkFunction wf on wf.id=p.prescriptSpecial_id
    left join Worker w on w.id=wf.worker_id
    left join MisLpu ml on ml.id=w.lpu_id
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    left join VocPrescriptType vpt on vpt.id=p.prescriptType_id
    left join vocprescriptcancelreason  vpcr on vpcr.id=p.cancelreason_id
    left join medcase mc on mc.id=p.medcase_id
    where p.dtype='ServicePrescription'
    and  p.intakedate between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
     ${sqlAdd}
    group by pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id
    ,p.id,ms.id,ms.name,ms.code,p.intakedate,p.materialId,pms.name
    order by pat.lastname,pat.firstname,pat.middlename"/>
        <msh:sectionTitle>Реестр пациентов ${title}</msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="reestr" action="javascript:void(0)" idField="1">
	      <msh:tableColumn columnName="Стат.карта" property="2"  />
	      <msh:tableColumn columnName="Фамилия пациента" property="3"  />
	      <msh:tableColumn columnName="Имя" property="4" />
	      <msh:tableColumn columnName="Отчество" property="5"/>
	      <msh:tableColumn columnName="Метка биоматериала" property="6"/>
	      <msh:tableColumn columnName="Код биоматериала" property="7"/>
	      <msh:tableColumn columnName="Услуга" property="8"/>
	      <msh:tableColumn columnName="Причина брака" property="9"/>
	    </msh:table>
    </msh:sectionContent>
    </msh:section>
	<%
	}
  	} else {
  	  	%>
<H1>Выберите параметры отчета</H1>  	  	
  	  	<%	
  	}
  	
	%>
  </tiles:put>
</tiles:insert>

