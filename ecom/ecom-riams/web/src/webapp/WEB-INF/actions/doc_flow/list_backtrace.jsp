<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="java.util.Date"%>
<%@page import="ru.nuzmsh.util.format.DateFormat"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="PatientJournal" title="Отслеживание документов" />
  </tiles:put>
  <tiles:put name="side" type="string">
 		<msh:sideMenu title="Журналы">
      	<msh:sideLink name="Журнал документов для отправления" action="/doc_flow_sending.do"/>
      	<msh:sideLink name="Журнал документов для получения" action="/doc_flow_receit.do"/>
      	<msh:sideLink name="Журнал отслеж. документов" action="/doc_flow_backtrace.do"/>
      	
      </msh:sideMenu>
      <msh:sideMenu title="Передача документов">
      <msh:sideLink key="ALT+2" action="/entityPrepareCreate-doc_flow" name="Регистрация" roles="/Policy/Mis/Document/Flow/Create" />
      </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<%
  	String username = LoginInfo.find(request.getSession(true)).getUsername() ;
  	ActionUtil.getValueBySql("select lpu.id,lpu.name from mislpu lpu left join worker w on w.lpu_id=lpu.id left join workfunction wf on wf.worker_id=w.id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'", "lpu_id","lpu_name",request) ;
  	Object lpu = request.getAttribute("lpu_id") ;
  	String typeMoveFrom =ActionUtil.updateParameter("FLOW_RECEIT_Journal","typeMoveFrom","2", request) ;
  	String typeMoveTo =ActionUtil.updateParameter("FLOW_RECEIT_Journal","typeMoveTo","1", request) ;
    String typeDocument =ActionUtil.updateParameter("FLOW_RECEIT_Journal","typeDocument","4", request) ;
    String typeIsReceiting =ActionUtil.updateParameter("FLOW_RECEIT_Journal","typeIsReceiting","2", request) ;
    String typeOrderBy =ActionUtil.updateParameter("FLOW_RECEIT_Journal","typeOrderBy","1", request) ;

  	
  		String beginDate = request.getParameter("beginDate") ;
  		if (beginDate==null || beginDate.equals("")) {
  			beginDate=DateFormat.formatToDate(new Date()) ;
  		}
  		String finishDate = request.getParameter("finishDate") ;
  	  	if (finishDate==null|| finishDate.equals("")) {finishDate=beginDate;}
  		request.setAttribute("beginDate", beginDate) ;
  		request.setAttribute("finishDate", finishDate) ;
  	%>
  	
  	  <msh:form action="/doc_flow_backtrace.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Группировка (typeMoveFrom)" colspan="1"><label for="typeMoveFromName" id="typeMoveFromLabel">Отправлено из:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;">
        	<input type="radio" name="typeMoveFrom" value="1"> стационара
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;" >
        	<input type="radio" name="typeMoveFrom" value="2"> поликлиники
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;" >
        	<input type="radio" name="typeMoveFrom" value="3"> АСПЭ
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;" >
        	<input type="radio" name="typeMoveFrom" value="4"> отобразить все данные
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Группировка (typeMoveTo)" colspan="1"><label for="typeMoveToName" id="typeMoveToLabel">Отправлено в:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;">
        	<input type="radio" name="typeMoveTo" value="1"> стационар
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;" >
        	<input type="radio" name="typeMoveTo" value="2"> поликлинику
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;" >
        	<input type="radio" name="typeMoveTo" value="3"> АСПЭ
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;" >
        	<input type="radio" name="typeMoveTo" value="4"> отобразить все данные
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Группировка (typeDocument)" colspan="1"><label for="typeDocumentName" id="typeDocumentLabel">Тип документов:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;">
        	<input type="radio" name="typeDocument" value="1"> стационар.
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;" >
        	<input type="radio" name="typeDocument" value="2"> поликлин.
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;" >
        	<input type="radio" name="typeDocument" value="3"> другие
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();this.form.submit() ;" >
        	<input type="radio" name="typeDocument" value="4"> отобразить все данные
        </td>
      </msh:row>
      
      
      <msh:row>
        <td class="label" title="Статус получения (typeIsReceiting)"><label for="typeIsReceitingName" id="typeIsReceitingLabel">Передача:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="1">
        	<input type="radio" name="typeIsReceiting" value="1"> осуществлена
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeIsReceiting" value="2"> не была произведена
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeIsReceiting" value="3"> отобразить все данные
        </td>

       </msh:row>
     <msh:row>
        <td class="label" title="Сортировка (typeOrderBy)"><label for="typeOrderByName" id="typeOrderByLabel">Сортировка:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="1">
        	<input type="radio" name="typeOrderBy" value="1"> ФИО пациента
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeOrderBy" value="2"> отправлен из
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeOrderBy" value="3"> отправлен в
        </td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
        	<input type="radio" name="typeOrderBy" value="4"> вид документа
        </td>

       </msh:row>
        <msh:row>
        	<msh:autoComplete property="type"  fieldColSpan="4" horizontalFill="true" label="Тип" vocName="vocFlowDocumentType"/>
        </msh:row>
      <msh:row>
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="finishDate" label="по" />
           <td>
            <input type="submit" value="Отобразить данные" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <script type='text/javascript'>
    checkFieldUpdate('typeDocument','${typeDocument}',1) ;
    //checkFieldUpdate('typeIsSending','${typeIsSending}',1) ;
    checkFieldUpdate('typeIsReceiting','${typeIsReceiting}',1) ;
    checkFieldUpdate('typeMoveTo','${typeMoveTo}',1) ;
    checkFieldUpdate('typeMoveFrom','${typeMoveFrom}',1) ;
    checkFieldUpdate('typeOrderBy','${typeOrderBy}',1) ;
    function checkfrm() {
    	document.forms[0].submit() ;
    }
  	function viewPatient(aPat) {
  		window.open("entityView-mis_patient.do?id="+aPat) ;
  	}

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
    <%
    //if (typeGroup.equals("2")) {request.setAttribute("addByGroup", "p.id,");}
    StringBuilder sqlAdd = new StringBuilder() ;
    StringBuilder title = new StringBuilder() ;
    if (typeMoveFrom!=null && typeMoveFrom.equals("1")) {
		sqlAdd.append(" and vfdp1.type='HOSP' ") ;
	} else if (typeMoveFrom!=null && typeMoveFrom.equals("2")) {
		sqlAdd.append(" and vfdp1.type='POLYC' ") ;
	} else if (typeMoveFrom!=null && typeMoveFrom.equals("3")) {
		sqlAdd.append(" and vfdp1.type='ASPE' ") ;
	}
    if (typeMoveTo!=null && typeMoveTo.equals("1")) {
		sqlAdd.append(" and vfdp2.type='HOSP' ") ;
	} else if (typeMoveTo!=null && typeMoveTo.equals("2")) {
		sqlAdd.append(" and vfdp2.type='POLYC' ") ;
	} else if (typeMoveTo!=null && typeMoveTo.equals("3")) {
		sqlAdd.append(" and vfdp2.type='ASPE' ") ;
	}
    if (typeDocument!=null && typeDocument.equals("1")) {
		sqlAdd.append(" and vfdt.type='HOSP' ") ;
	} else if (typeDocument!=null && typeDocument.equals("2")) {
		sqlAdd.append(" and vfdt.type='POLYC' ") ;
	} else if (typeDocument!=null && typeDocument.equals("3")) {
		sqlAdd.append(" and vfdt.type='OTHER' ") ;
	}/*
    if (typeIsSending!=null && typeIsSending.equals("1")) {
		sqlAdd.append(" and fd.postedDate is not null ") ;
	} else if (typeIsSending!=null && typeIsSending.equals("2")) {
		sqlAdd.append(" and fd.postedDate is null ") ;
	} */
    if (typeIsReceiting!=null && typeIsReceiting.equals("1")) {
		sqlAdd.append(" and fd.receiptDate is not null ") ;
	} else if (typeIsReceiting!=null && typeIsReceiting.equals("2")) {
		sqlAdd.append(" and fd.receiptDate is null ") ;
	} 
	if (typeOrderBy!=null && typeOrderBy.equals("1")) {
		request.setAttribute("orderBySql"," pat.lastname,pat.firstname,pat.middlename ") ;
	} else if (typeOrderBy!=null && typeOrderBy.equals("2")) {
		request.setAttribute("orderBySql"," vfdp1.name,pat.lastname,pat.firstname,pat.middlename ") ;
	} else if (typeOrderBy!=null && typeOrderBy.equals("3")) {
		request.setAttribute("orderBySql"," vfdp2.name,pat.lastname,pat.firstname,pat.middlename ") ;
	} else if (typeOrderBy!=null && typeOrderBy.equals("4")) {
		request.setAttribute("orderBySql"," vfdt.name,pat.lastname,pat.firstname,pat.middlename ") ;
	}
		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocFlowDocumentType where id=:id"
  				, "тип","type","vfdt.id", request)) ;
  		title.append(" ").append(request.getAttribute("typeInfo")) 
  			//.append(" ").append(request.getAttribute("prescriptTypeInfo")) 
  			//.append(" ").append(request.getAttribute("serviceInfo"))
  			;
    request.setAttribute("sqlAdd", sqlAdd.toString()) ;
    %>
    <msh:section>
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
    select fd.id as f1fdid
,pat.lastname||' '||pat.firstname||' '||pat.middlename as f2fio,to_char(pat.birthday,'dd.mm.yyyy') as f3birthday
,vr.name as f4vrname,coalesce('ИБ№'||ss.code,'МК№'||mc.number,'') as f5infodoc
 ,vfdt.name as f6vfdtname
 ,vfdp1.name as f7vfdp1name,vfdp2.name as f8vfdp2name
 ,to_char(fd.posteddate,'dd.mm.yyyy') as f9createdate ,fd.createusername as f10createusername,su.fullname as f11fullname
 ,to_char(fd.posteddate,'dd.mm.yyyy') as f12posteddate
 , case when fd.postedDate is null then fd.id else null end as f12fdid
 ,fd.patient as f13patient
from FlowDocument fd 
left join secuser su on su.login=fd.createusername
left join medcase sls on sls.id=fd.medcase 
left join statisticstub ss on ss.id=sls.statisticstub_id 
left join medcard mc on mc.id=fd.medcard 
left join patient pat on pat.id=fd.patient 
left join vocrayon as vr on vr.id=pat.rayon_id
left join VocFlowDocumentType vfdt on vfdt.id=fd.type_id 
left join VocFlowDocmentPlace vfdp1 on vfdp1.id=fd.placeFrom_id
left join VocFlowDocmentPlace vfdp2 on vfdp2.id=fd.placeTo_id
left join FlowDocument fd1 on fd1.trackingDoc_id=fd.id
    where fd.posteddate between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${finishDate}','dd.mm.yyyy')
    
    ${sqlAdd}
    and fd.isTracking ='1' and fd1.id is null
    order by ${orderBySql}
    "/>
    
    <msh:sectionTitle>
    
    
    <form  id="printForm" name="printForm" action="print-doc_flow_backtrace.do" method="post" target="_blank">
	 Список пациентов за ${beginDate}-${finishDate}   
	    <input type='hidden' name="sqlText" id="sqlText" value="${list_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value='Список пациентов за ${beginDate}-${finishDate}'>
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="button" value="Печать" title="Печать всего списка (либо тех, что отмечены галочками)" onclick="printSomePrescriptions()">
	     <script type="text/javascript">
	    function printSomePrescriptions() {
	    	var text = theTableArrow.getInsertedIdsAsParams("","list") ;
	    	var oldSqlText=$('sqlText').value;
	    	if (text) {$('sqlText').value=$('sqlText').value.replace("order by"," and fd.id in ("+text+")"+"order by") ;}
	    	document.getElementById('printForm').action='print-pres_lab_prescript_by_department.do';
    		document.getElementById('printForm').submit();
    		$('sqlText').value=oldSqlText;
	    }
	    </script>
	    </form>    
    </msh:sectionTitle>
    <msh:sectionContent>
    
        <msh:table name="list" idField="1" action="javascript:void(0)" selection="multiply">
	    
              <msh:tableButton hideIfEmpty="true" property="13" buttonFunction="viewPatient" buttonName="Пациент" buttonShortName="Пациент" role="/Policy/Mis/Patient/View"/>
     
	     <msh:tableColumn columnName="#" property="sn"  />
	      <msh:tableColumn columnName="ФИО пациента" property="2"  />
	      <msh:tableColumn columnName="Дата рождения" property="3"/>
	      <msh:tableColumn columnName="Район" property="4"/>
	      <msh:tableColumn columnName="Документ" property="5"/>
	      <msh:tableColumn columnName="Вид документа" property="6"/>
	      <msh:tableColumn columnName="Откуда" property="7"/>
	      <msh:tableColumn columnName="Куда" property="8"  />
	      <msh:tableColumn columnName="ФИО сотрудника" property="11" />
	      <msh:tableColumn columnName="Дата передачи" property="12" />
	    </msh:table>

    </msh:sectionContent>
    </msh:section>
  	
  	<%	
  	
  	
	%>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
  	<script type="text/javascript">
  	function transferIn() {
	    	
	    		var ids = theTableArrow.getInsertedIdsAsParams("","list") ;
  	        if (ids) {
  	        	showBiomatIntakeInfo(ids) ;
  	        } else {
  	            alert("Нет выделенных пациентов");
  	        }
	    	 
		}
  		function removeService(aListPrescript, aMaterialId) {
  				PrescriptionService.intakeServiceRemove(aListPrescript, { 
		            callback: function(aResult) {
		            	window.document.location.reload();
		            }
				}); 
  		}
  		typeAutocomplete.addOnChangeCallback(function() {checkfrm()}) ;
  	</script>
  </tiles:put>
</tiles:insert>

