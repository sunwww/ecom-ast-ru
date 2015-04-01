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
    <msh:title mainMenu="Journals" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Забор биоматериала для лабораторных исследований" />
   
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  	<%
  	
  	//String typeIntake =ActionUtil.updateParameter("PrescriptJournal","typeIntake","2", request) ;
  	String typeTransfer =ActionUtil.updateParameter("PrescriptJournal","typeTransfer","2", request) ;
    
  	%>
  	<tags:pres_intakeDate name="Biomat"/>
  	  <msh:form action="/pres_journal_intake_transfer.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" />
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
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="prescriptType" fieldColSpan="4" horizontalFill="true" label="Тип наначения" vocName="vocPrescriptType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceSubType" fieldColSpan="4" horizontalFill="true" label="Тип услуги" vocName="vocServiceSubTypeLab"/>
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
    
    <script type='text/javascript'>
    //checkFieldUpdate('typeIntake','${typeIntake}',1) ;
    //checkFieldUpdate('typeMaterial','${typeMaterial}',1) ;
    checkFieldUpdate('typeTransfer','${typeTransfer}',1) ;

   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[1].'+aField) ;
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
    String department = request.getParameter("department") ;
  	if (department!=null && !department.equals("")) {
  		String beginDate = request.getParameter("beginDate") ;
  		if (beginDate==null || beginDate.equals("")) {
  			beginDate=DateFormat.formatToDate(new Date()) ;
  		}
  		String endDate = request.getParameter("endDate") ;
  	  	if (endDate==null|| endDate.equals("")) {endDate=beginDate;}
  		request.setAttribute("beginDate", beginDate) ;
  		request.setAttribute("endDate", endDate) ;
    StringBuilder sqlAdd = new StringBuilder() ;
    
    if (typeTransfer!=null && typeTransfer.equals("1")) {
		sqlAdd.append(" and p.transferDate is not null ") ;
	} else if (typeTransfer!=null && typeTransfer.equals("2")) {
		sqlAdd.append(" and p.transferDate is null ") ;
	}
    request.setAttribute("sqlAdd", sqlAdd.toString()) ;
    %>
    <msh:section>
    <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
    select 
    case when p.intakeDate is not null then replace(list(''||p.id),' ','') else null end as js1
    ,case when p.intakeDate is null then null else list(''||p.id) end as js2
      , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
    , coalesce(case when list(p.materialId)='' then coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id)
    
    ||'#'||pat.lastname else list(p.materialId) end) as f4material
    ,coalesce(vsst.name,'---') as f5vsstname
    ,pat.lastname as f6lastname,pat.firstname as f7firstname,pat.middlename as f8middlename
    ,to_char(pat.birthday,'dd.mm.yyyy') as f9birthday
   ,list(case when vst.code='LABSURVEY' then ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name else null end) as f10medServicies
   ,list(wp.lastname||' '||wp.firstname||' '||wp.middlename) as f11fioworker
   ,list(distinct iwp.lastname||' '||iwp.firstname||' '||iwp.middlename) as f12intakefioworker
       ,to_char(p.intakeDate,'dd.mm.yyyy')||' '||cast(p.intakeTime as varchar(5)) as f13dtintake
   
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
    left join Patient wp on wp.id=w.person_id
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    left join WorkFunction iwf on iwf.id=p.intakeSpecial_id
    left join Worker iw on iw.id=iwf.worker_id
    left join Patient iwp on iwp.id=iw.person_id
    left join MisLpu ml on ml.id=w.lpu_id
    where p.dtype='ServicePrescription'
    and p.planStartDate between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
    and coalesce(p.department_id,w.lpu_id)='${param.department}' 
    and p.cancelDate is null 
    and p.intakeDate is not null
    ${sqlAdd}
    group by pat.id,pat.lastname,pat.firstname,pat.middlename
    ,vsst.name  , ssSls.code,ssslo.code,pl.medCase_id,pl.id
    ,p.intakedate,pat.birthday,iwp.lastname,iwp.firstname,iwp.middlename,p.intakeTime
    order by pat.lastname,pat.firstname,pat.middlename
    "/>
    
    <msh:sectionTitle>

    </msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="list" action="javascript:void(0)" idField="1" selection="multiply">
	     <msh:tableNotEmpty>
            <tr>
              <th colspan="4">
                <msh:toolbar>
                  <a href="javascript:transferInLab()">Переданы в лабораторию</a>
                </msh:toolbar>
              </th>
            </tr>
          </msh:tableNotEmpty>
	      <msh:tableColumn columnName="Стат.карта" property="3"  />
	      <msh:tableColumn columnName="Код биоматериала" property="4"/>
	      <msh:tableColumn columnName="Метка биоматериала" property="5"/>
	      <msh:tableColumn columnName="Фамилия пациента" property="6"  />
	      <msh:tableColumn columnName="Имя" property="7" />
	      <msh:tableColumn columnName="Отчетство" property="8"/>
	      <msh:tableColumn columnName="Дата рождения" property="9"/>
	      <msh:tableColumn columnName="Список услуг" property="10"/>

	    </msh:table>
	    <script type="text/javascript">
	    
	    </script>
    </msh:sectionContent>
    </msh:section>
  	
  	<%	
  	} else {
  	  	%>
<H1>Выберите параметры!!!!</H1>  	  	
  	  	<%	
  	}
  	
	%>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
  	<script type="text/javascript">
  		/*function getMaterialId(aMaterialId) {
			aMaterialId=prompt('Введите наименование пробирки:', aMaterialId) ;
			if (aMaterialId==null || aMaterialId=="") {
				if (confirm('Неопределено наименование пробирки. Хотите ввести еще раз?')) {
					return getMaterialId(aMaterialId) ;
				}
			} else {
				return aMaterialId ;
			}
			return null ;
  		}*/
  		
  	    function transferInLab() {
  	    	var ids = theTableArrow.getInsertedIdsAsParams("","list") ;
  	        if (ids) {
  	        	PrescriptionService.checkTransferService( ids, { 
  		            callback: function(aResult) {
  		            	window.document.location.reload();
  		            }
  				});
  	        } else {
  	            alert("Нет выделенных пациентов");
  	        }
  	        
  				
  		}
  	</script>
  </tiles:put>
</tiles:insert>

