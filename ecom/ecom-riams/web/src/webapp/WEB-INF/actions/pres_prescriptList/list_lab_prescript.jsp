<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

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
  String typeCancel =ActionUtil.updateParameter("PrescriptJournal","typeCancel","3", request) ;
  String typeGroup =ActionUtil.updateParameter("PrescriptJournal","typeGroup","1", request) ;
  String typeIntake =ActionUtil.updateParameter("PrescriptJournal","typeIntake","3", request) ;
  String typeReestr =request.getParameter("typeReestr") ;
  String typeTransfer =ActionUtil.updateParameter("PrescriptJournal","typeTransfer","2", request) ;
  String typeService =ActionUtil.updateParameter("PrescriptJournal","typeService","2", request) ;

	 %>
  <msh:form action="/pres_journal.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Забор материала (typeIntake)" colspan="1"><label for="typeIntakeame" id="typeIntakeLabel">Забор:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeIntake" value="1"> был
        </td>
        <td onclick="this.childNodes[1].checked='checked';" >
        	<input type="radio" name="typeIntake" value="2"> не был
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeIntake" value="3"> отобразить все данные
        </td>

       </msh:row>
      <msh:row>
        <td class="label" title="Передача в лабораторию (typeTransfer)"><label for="typeTransferName" id="typeTransferLabel">Передача в лабораторию:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeTransfer" value="1"> осуществлена
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeTransfer" value="2"> не была произведена
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeTransfer" value="3"> все
        </td>

       </msh:row>
      <msh:row>
        <td class="label" title="Назначения (typeCancel)"><label for="typeCancelName" id="typeCancelLabel">Назначения:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeCancel" value="1"> отмененные
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeCancel" value="2"> действующие
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeCancel" value="3"> все
        </td>

       </msh:row>
      <msh:row>
        <td class="label" title="Группировать (typeService)"><label for="typeServiceName" id="typeServiceLabel">Группировать услуги:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeService" value="1"> без группировки
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeService" value="2"> группировать
        </td>

       </msh:row>
      <msh:row>
        <td class="label" title="Просмотр данных (typeGroup)"><label for="typeGroupName" id="typeGroupLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeGroup" value="1"> по отделениям и услугам
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeGroup" value="2"> по услугам и отделениям
        </td>

       </msh:row>
        <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeGroup" value="3"> по типу назначения и отделению
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeGroup" value="4"> по типу назначения и услугам
        </td>
       </msh:row>
        <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeGroup" value="5"> по отделению
        </td>
       </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="prescriptType" fieldColSpan="4" horizontalFill="true" label="Тип назначения" vocName="vocPrescriptType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceSubType"  parentId="LABSURVEY" fieldColSpan="4" horizontalFill="true" label="Тип биоматериала" vocName="vocServiceSubTypeByCode"/>
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
    <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
           <script type='text/javascript'>
           checkFieldUpdate('typeIntake','${typeIntake}',1) ;
           checkFieldUpdate('typeGroup','${typeGroup}',1) ;
           checkFieldUpdate('typeCancel','${typeCancel}',1) ;
           checkFieldUpdate('typeTransfer','${typeTransfer}',1) ;
           checkFieldUpdate('typeService','${typeService}',1) ;

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
  	if (endDate==null|| endDate.equals("")) {endDate=beginDate;}
  	if (beginDate!=null && !beginDate.equals("")) {
  		request.setAttribute("beginDate", beginDate) ;
  		request.setAttribute("endDate", endDate) ;
  		StringBuilder title = new StringBuilder() ;
  		StringBuilder sqlAdd = new StringBuilder() ;
  		StringBuilder href = new StringBuilder() ;
  		
  		if (typeIntake!=null && typeIntake.equals("1")) {
  			sqlAdd.append(" and p.intakeDate is not null ") ;
  		} else if (typeIntake!=null && typeIntake.equals("2")) {
  			sqlAdd.append(" and p.intakeDate is null ") ;
  		}
  		if (typeTransfer!=null && typeTransfer.equals("1")) {
  			sqlAdd.append(" and p.transferDate is not null ") ;
  		} else if (typeTransfer!=null && typeTransfer.equals("2")) {
  			sqlAdd.append(" and p.transferDate is null ") ;
  		}
  		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
  				, "отделение","deparment","ml.id", request)) ;
  		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocPrescriptType where id=:id"
  				, "тип назначения","prescriptType","vpt.id", request)) ;
  		if (typeService!=null &&typeService.equals("1")) {
  	  		sqlAdd.append(ActionUtil.getValueInfoById("select id, code||' '||name from medservice where id=:id"
  	  				, "исследование","service","ms.id", request)) ;
  		} else {
  			sqlAdd.append(ActionUtil.getValueInfoById("select id, code||' '||name from medservice where id=:id"
  	  				, "категория исследований","service","pms.id", request)) ;
  		}
  		title.append(request.getAttribute("departmentInfo"))
  			.append(" ").append(request.getAttribute("prescriptTypeInfo")) 
  			.append(" ").append(request.getAttribute("serviceInfo")) ;
  		if (typeCancel!=null && typeCancel.equals("1")) {
  			sqlAdd.append(" and p.cancelDate is not null ") ;
  		} else if (typeCancel!=null && typeCancel.equals("2")) {
  			sqlAdd.append(" and p.cancelDate is null ") ;
  		}
  		if (department!=null&&!department.equals("")) {
  			sqlAdd.append("and ml.id = "+department);
  		}
		href.append("&typeCancel=").append(typeCancel) ;
		href.append("&typeIntake=").append(typeIntake) ;
		request.setAttribute("sqlAdd", sqlAdd.toString()) ;
		request.setAttribute("href", href.toString()) ;
		request.setAttribute("title", title.toString()) ;
		String ms = "ms.id,ms.additionCode,ms.code,ms.name";
		String ms_i = "ms.id";
		String ms_o = "ms.code" ;
		String ms_n = "ms.code||' ('||ms.additionCode||')'||ms.name" ;
		if (typeService!=null&&typeService.equals("2")) {
			ms = "pms.id,pms.additionCode,pms.code,pms.name";
			ms_i = "pms.id";
			ms_o = "pms.code" ;
			ms_n = "pms.code||' ('||pms.additionCode||')'||pms.name" ;
		}
		if (typeGroup!=null && (typeReestr==null || typeReestr.equals("0"))) {
			if (typeGroup.equals("1")) {
				request.setAttribute("groupSql", "ml.name as mlname,"+ms_n+" as msname") ;
       			request.setAttribute("groupSqlId", "'&department='||ml.id||'&service='||"+ms_i+"") ;
       			request.setAttribute("groupName1", "Отделение") ;
       			request.setAttribute("groupName2", "Услуга") ;
       			request.setAttribute("groupGroup", "ml.id,ml.name,"+ms) ;
       			request.setAttribute("groupOrder", "ml.name,"+ms_o) ;
			} else if (typeGroup.equals("2")) {
				request.setAttribute("groupSql", ""+ms_n+" as msname,ml.name as mlname") ;
       			request.setAttribute("groupSqlId", "'&department='||ml.id||'&service='||"+ms_i+"") ;
       			request.setAttribute("groupName1", "Услуга") ;
       			request.setAttribute("groupName2", "Отделение") ;
       			request.setAttribute("groupGroup", ""+ms+",ml.id,ml.name") ;
       			request.setAttribute("groupOrder", ms_o+",ml.name") ;
			} else if (typeGroup.equals("3")) {
				request.setAttribute("groupSql", "vpt.name as vptname,ml.name as mlname") ;
       			request.setAttribute("groupSqlId", "'&department='||ml.id||'&prescriptType='||vpt.id") ;
       			request.setAttribute("groupName1", "Тип назначения") ;
       			request.setAttribute("groupName2", "Отделение") ;
       			request.setAttribute("groupGroup", "vpt.id,vpt.name,ml.id,ml.name") ;
       			request.setAttribute("groupOrder", "vpt.name,ml.name") ;
			} else if (typeGroup.equals("4")) {
				request.setAttribute("groupSql", "vpt.name as vptname,"+ms_n+" as msname") ;
       			request.setAttribute("groupSqlId", "'&service='||"+ms_i+"||'&prescriptType='||vpt.id") ;
       			request.setAttribute("groupName1", "Тип назначения") ;
       			request.setAttribute("groupName2", "Услуга") ;
       			request.setAttribute("groupGroup", "vpt.id,vpt.name,"+ms+"") ;
       			request.setAttribute("groupOrder", "vpt.name,"+ms_o+"") ;
			} else if (typeGroup.equals("5")) {
				request.setAttribute("groupSql", "ml.name as mlname, cast('' as varchar(1))") ;
       			request.setAttribute("groupSqlId", "'&department='||ml.id") ;
       			request.setAttribute("groupName1", "Отделение") ;
       			request.setAttribute("groupName2", "") ;
       			request.setAttribute("groupGroup", "ml.id,ml.name") ;
       			request.setAttribute("groupOrder", "ml.name") ;
			}
		
  		if (typeGroup!=null &&
  				(typeGroup.equals("1")|| typeGroup.equals("2")
  						|| typeGroup.equals("3")|| typeGroup.equals("4")|| typeGroup.equals("5"))
  				) {
  	//and w.lpu_id='${lpu_id}'
  	%>
    <msh:section>
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
    select ${groupSqlId} as id
    ,${groupSql},count(p.id) as cntService
    
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
    where p.dtype='ServicePrescription'
    and p.planStartDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${endDate}','dd.mm.yyyy')
    and vst.code='LABSURVEY' 
    ${sqlAdd}
    group by ${groupGroup}
    
    order by ${groupOrder}
    "/>
    <msh:sectionTitle>Свод. ${departmentInfo}
    
    </msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="list" action="pres_journal.do?typeReestr=1&beginDate=${beginDate}&endDate=${endDate}${href}" idField="1" >
	      <msh:tableColumn columnName="${groupName1}" property="2"  />
	      <msh:tableColumn columnName="${groupName2}" property="3"  />
	      <msh:tableColumn columnName="Кол-во" property="4"  />
	    </msh:table>
    </msh:sectionContent>
    </msh:section>
  	
  	<%	
  		}
  		
	} else if (typeReestr!=null && typeReestr.equals("1")) {
	%>
	<msh:section>
	<ecom:webQuery name="reestr" nativeSql="select pat.id, coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id)
    ,pat.lastname,pat.firstname,pat.middlename
    ,coalesce(vsst.name,'---') as vsstname
    , p.materialId as material
    ,ms.code||' '||ms.name as medServicies
    ,case when p.canceldate is null then '${j}' else 'Услуга отменена' end as isCancel
    ,pms.name as pmsname
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
    where p.dtype='ServicePrescription'
    and p.planStartDate between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
     ${sqlAdd}
    group by pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id
    ,p.id,ms.id,ms.name,ms.code,p.intakedate,p.materialId,isCancel,pms.name
    order by pat.lastname,pat.firstname,pat.middlename"/>
        <msh:sectionTitle>Реестр пациентов ${title}</msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="reestr" action="javascript:void(0)" idField="1" escapeSymbols="false">
	      <msh:tableColumn columnName="Управление" property="9"  />
	      <msh:tableColumn columnName="Стат.карта" property="2"  />
	      <msh:tableColumn columnName="Фамилия пациента" property="3"  />
	      <msh:tableColumn columnName="Имя" property="4" />
	      <msh:tableColumn columnName="Отчетство" property="5"/>
	      <msh:tableColumn columnName="Метка биоматериала" property="6"/>
	      <msh:tableColumn columnName="Код биоматериала" property="7"/>
	      <msh:tableColumn columnName="Услуга" property="8"/>
	      <msh:tableColumn columnName="Категория услуги" property="10"/>
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

