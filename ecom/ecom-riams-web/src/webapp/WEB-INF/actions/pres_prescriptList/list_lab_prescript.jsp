<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Забор биоматериала для лабораторных исследований" />
   
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Показать" guid="a47dfc0b-97d1-4cb5-b904-4ff717e612a7" />
    <msh:sideMenu title="Добавить" guid="60616958-11ef-48b0-bec7-f6b1d0b8463f">
      <msh:sideLink roles="/Policy/Mis/Prescription/Create" key="ALT+N" action="/entityParentPrepareCreate-pres_prescriptList" name="Сводный  лист назначений" guid="1faa5477-419b-4f77-8379-232e33a61922" params="id" />
      <msh:sideLink roles="/Policy/Mis/Prescription/Create" key="ALT+4" action=".javascript:shownewTemplatePrescription(1,&quot;.do&quot;)" name="ЛН из шаблона" guid="2a2c0ab6-4a46-41f7-8221-264de893815c" title="Добавить лист назначений из шаблона" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String typeCancel =ActionUtil.updateParameter("PrescriptJournal","typeCancel","3", request) ;
  String typeGroup =ActionUtil.updateParameter("PrescriptJournal","typeGroup","1", request) ;
  String typeIntake =ActionUtil.updateParameter("PrescriptJournal","typeIntake","3", request) ;
  String typeReestr =request.getParameter("typeReestr") ;
	 %>
  <msh:form action="/pres_journal.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
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
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="prescriptType" fieldColSpan="4" horizontalFill="true" label="Тип наначения" vocName="vocPrescriptType"/>
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
	            callback: function(aResult) {
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
  		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
  				, "отделение","deparment","ml.id", request)) ;
  		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocPrescriptType where id=:id"
  				, "тип назначения","prescriptType","vpt.id", request)) ;
  		sqlAdd.append(ActionUtil.getValueInfoById("select id, code||' '||name from medservice where id=:id"
  				, "исследование","service","ms.id", request)) ;
  		title.append(request.getAttribute("departmentInfo"))
  			.append(" ").append(request.getAttribute("prescriptTypeInfo")) 
  			.append(" ").append(request.getAttribute("serviceInfo")) ;
  		if (typeCancel!=null && typeCancel.equals("1")) {
  			sqlAdd.append(" and p.cancelDate is not null ") ;
  		} else if (typeCancel!=null && typeCancel.equals("2")) {
  			sqlAdd.append(" and p.cancelDate is null ") ;
  		}
  		
		href.append("&typeCancel=").append(typeCancel) ;
		href.append("&typeIntake=").append(typeIntake) ;
		request.setAttribute("sqlAdd", sqlAdd.toString()) ;
		request.setAttribute("href", href.toString()) ;
		request.setAttribute("title", title.toString()) ;
		if (typeGroup!=null && (typeReestr==null || typeReestr.equals("0"))) {
			if (typeGroup.equals("1")) {
				request.setAttribute("groupSql", "ml.name as mlname,ms.name as msname") ;
       			request.setAttribute("groupSqlId", "'&department='||ml.id||'&service='||ms.id") ;
       			request.setAttribute("groupName1", "Отделение") ;
       			request.setAttribute("groupName2", "Услуга") ;
       			request.setAttribute("groupGroup", "ml.id,ml.name,ms.id,ms.name") ;
       			request.setAttribute("groupOrder", "ml.name,ms.name") ;
			} else if (typeGroup.equals("2")) {
				request.setAttribute("groupSql", "ms.name as msname,ml.name as mlname") ;
       			request.setAttribute("groupSqlId", "'&department='||ml.id||'&service='||ms.id") ;
       			request.setAttribute("groupName1", "Услуга") ;
       			request.setAttribute("groupName2", "Отделение") ;
       			request.setAttribute("groupGroup", "ms.id,ms.name,ml.id,ml.name") ;
       			request.setAttribute("groupOrder", "ms.name,ml.name") ;
			} else if (typeGroup.equals("3")) {
				request.setAttribute("groupSql", "vpt.name as vptname,ml.name as mlname") ;
       			request.setAttribute("groupSqlId", "'&department='||ml.id||'&prescriptType='||vpt.id") ;
       			request.setAttribute("groupName1", "Тип назначения") ;
       			request.setAttribute("groupName2", "Отделение") ;
       			request.setAttribute("groupGroup", "vpt.id,vpt.name,ml.id,ml.name") ;
       			request.setAttribute("groupOrder", "vpt.name,ml.name") ;
			} else if (typeGroup.equals("4")) {
				request.setAttribute("groupSql", "vpt.name as vptname,ms.name as msname") ;
       			request.setAttribute("groupSqlId", "'&service='||ms.id||'&prescriptType='||vpt.id") ;
       			request.setAttribute("groupName1", "Тип назначения") ;
       			request.setAttribute("groupName2", "Услуга") ;
       			request.setAttribute("groupGroup", "vpt.id,vpt.name,ms.id,ms.name") ;
       			request.setAttribute("groupOrder", "vpt.name,ms.name") ;
			}
		
  		if (typeGroup!=null &&
  				(typeGroup.equals("1")|| typeGroup.equals("2")
  						|| typeGroup.equals("3")|| typeGroup.equals("4"))
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
    left join VocServiceType vst on vst.id=ms.serviceType_id
    left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id
    left join WorkFunction wf on wf.id=p.prescriptSpecial_id
    left join Worker w on w.id=wf.worker_id
    left join MisLpu ml on ml.id=w.lpu_id
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    left join VocPrescriptType vpt on vpt.id=p.prescriptType_id
    where p.dtype='ServicePrescription'
    and p.planStartDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${endDate}','dd.mm.yyyy')
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
    ,'${j}'
    from prescription p
    left join PrescriptionList pl on pl.id=p.prescriptionList_id
    left join MedCase slo on slo.id=pl.medCase_id
    left join MedCase sls on sls.id=slo.parent_id
    left join StatisticStub ssSls on ssSls.id=sls.statisticstub_id
    left join StatisticStub ssSlo on ssSlo.id=slo.statisticstub_id
    left join Patient pat on pat.id=slo.patient_id
    left join MedService ms on ms.id=p.medService_id
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
    ,p.id,ms.id,ms.name,ms.code,p.intakedate,p.materialId
    order by pat.lastname,pat.firstname,pat.middlename"/>
        <msh:sectionTitle>Реестр пациентов ${title}</msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="reestr" action="javascript:void(0)" idField="1">
	      <msh:tableColumn columnName="Управление" property="9"  />
	      <msh:tableColumn columnName="Стат.карта" property="2"  />
	      <msh:tableColumn columnName="Фамилия пациента" property="3"  />
	      <msh:tableColumn columnName="Имя" property="4" />
	      <msh:tableColumn columnName="Отчетство" property="5"/>
	      <msh:tableColumn columnName="Метка биоматериала" property="6"/>
	      <msh:tableColumn columnName="Код биоматериала" property="7"/>
	      <msh:tableColumn columnName="Список услуг" property="8"/>
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

