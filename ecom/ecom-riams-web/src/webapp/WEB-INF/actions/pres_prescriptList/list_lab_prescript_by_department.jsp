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
  	String username = LoginInfo.find(request.getSession(true)).getUsername() ;
  	ActionUtil.getValueBySql("select lpu.id,lpu.name from mislpu lpu left join worker w on w.lpu_id=lpu.id left join workfunction wf on wf.worker_id=w.id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'", "lpu_id","lpu_name",request) ;
  	Object lpu = request.getAttribute("lpu_id") ;
  	request.setAttribute("beginDate", "01.01.2014") ;
  	request.setAttribute("endDate", "01.12.2014") ;
  	if (lpu!=null && !lpu.equals("")) {
  	%>
  	  <msh:form action="/pres_journal_by_department.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
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
        	<input type="radio" name="typeIntake" value="2"> отобразить все данные
        </td>

       </msh:row>
      
        
      <msh:row>
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" value="Отобразить данные" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
           <script type='text/javascript'>
           checkFieldUpdate('typeIntake','${typeIntake}',1) ;
           checkFieldUpdate('typeGroup','${typeGroup}',1) ;

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

			 
    </script>
    <msh:section>
    <ecom:webQuery name="list" nativeSql="
    select pat.id, coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id)
    ,pat.lastname,pat.firstname,pat.middlename
    ,coalesce(vsst.name,'---') as vsstname
    , coalesce(case when list(p.materialId)='' then max(p.id)||'|'||coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id)
    ||'#'||pat.lastname else list(p.materialId) end) as material
    ,list(ms.code||' '||ms.name) as medServicies
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
    where p.dtype='ServicePrescription'
    and p.planStartDate between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
    and w.lpu_id='${lpu_id}'
    group by pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id
    
    order by pat.lastname,pat.firstname,pat.middlename
    "/>
    <msh:sectionTitle>Список пациентов по отделению ${lpu_name}</msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="list" action="entityParentView-pres_prescriptList.do" idField="1">
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
  	} else {
  	  	%>
<H1>НЕПРАВИЛЬНО НАСТРОЕН ПРОФИЛЬ ПОЛЬЗОВАТЕЛЯ!!! ОБРАТИТЕСЬ К АДМИНИСТРАТОРУ!!!!</H1>  	  	
  	  	<%	
  	}
  	
	%>
  </tiles:put>
</tiles:insert>

