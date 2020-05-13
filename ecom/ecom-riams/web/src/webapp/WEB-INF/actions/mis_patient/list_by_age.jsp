<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Разбивка пациентов по возрастам</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_everyday"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <%
    String typeReestr = request.getParameter("typeReestr") ;
    String typeHour =ActionUtil.updateParameter("mis_patient_by_age","typeHour","2", request) ;
    
    String typeDisp=ActionUtil.updateParameter("mis_patient_by_age","typeDisp","1", request) ;
    String typeRange=ActionUtil.updateParameter("mis_patient_by_age","typeRange","1", request) ;
    String typeGroup=ActionUtil.updateParameter("mis_patient_by_age","typeGroup","1", request) ;
    String typeSex=ActionUtil.updateParameter("mis_patient_by_age","typeSex","1", request) ;
    
    if (typeReestr==null) {
	  	String noViewForm = request.getParameter("noViewForm") ;
		String typeDate =ActionUtil.updateParameter("mis_patient_by_age","typeDate","1", request) ;
		String typeEmergency =ActionUtil.updateParameter("mis_patient_by_age","typeEmergency","3", request) ;
		
		String typeView =ActionUtil.updateParameter("mis_patient_by_age","typeView","2", request) ;
    
  	%>
    <msh:form action="/mis_patient_by_age.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
      <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <msh:textField fieldColSpan="2" property="dateBeginYear" label="Год" />
      </msh:row>

      <msh:row><td><br></td></msh:row>
      <msh:row>
        <td class="label" title="Население" colspan="1"><label for="typeDispName" id="typeDispLabel">Население: </label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDisp" value="1"> всё	
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDisp" value="2"> непрошедшее диспансеризацию
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDisp" value="3"> прошедшее диспансеризацию
        </td>
      </msh:row>
      <msh:row>
       <td class="label" title="Пол" colspan="1"><label for="typeSexName" id="typeSexLabel">Пол: </label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeSex" value="1"> все	
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeSex" value="2"> М
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeSex" value="3"> Ж
        </td>
      </msh:row>
      <msh:row><td><br></td></msh:row>
      <msh:row>
        <td class="label" title="Охват" colspan="1"><label for="typeRangeName" id="typeRangeLabel">Охват: </label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeRange" value="1"> вся база персон	
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeRange" value="2"> только приписное население
        </td>
	  </msh:row>
	   <msh:row>
        <td class="label" title="Группировка" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка: </label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="1"> Группировать по году рождения
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="2"> Не группировать
        </td>
	  </msh:row>
	 <msh:row>
		  <msh:autoComplete fieldColSpan="5" property="lpu" label="ЛПУ" horizontalFill="true"
		                              vocName="lpu" size="50"/>
	 </msh:row>
	 <msh:row styleId='rowLpuArea'>
	       <msh:autoComplete fieldColSpan="5" property="area" label="Участок" horizontalFill="true"
	                      parentAutocomplete="lpu" vocName="lpuAreaWithParent"/>
	</msh:row>	
	  

        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>
    </msh:panel>
    </msh:form>
       <script type='text/javascript'>
    
    //checkFieldUpdate('typeDate','${typeDate}',1) ;
    //checkFieldUpdate('typePatient','${typePatient}',4) ;
    //checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    //checkFieldUpdate('typeView','${typeView}',1) ;
    //checkFieldUpdate('typeHour','${typeHour}',3) ;
    //checkFieldUpdate('typeDepartment','${typeDepartment}',1) ;typeDisp
    
    checkFieldUpdate('typeDisp','${typeDisp}',1) ;
    checkFieldUpdate('typeRange','${typeRange}',2) ;
    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typeSex','${typeSex}',1) ;
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

    if ($('dateBegin')&&$('dateBegin').value=="") {
    	var dt = new Date() ;
    	dt.setDate(dt.getDate()-1);
    	$('dateBegin').value=format2day(dt.getDate())+"."+format2day(dt.getMonth()+1)+"."+dt.getFullYear() ;
    }

			 
    </script>
    <%
    }
    String date = request.getParameter("dateBeginYear") ;

    if (date!=null && !date.equals(""))  {
    	String view = (String)request.getAttribute("typeView") ;
    	
    	String pigeonHole="" ;
    	String pigeonHole1="" ;
    	StringBuilder paramHref = new StringBuilder();
    	paramHref.append("typeReestr=1");
    	paramHref.append("&typeView=").append(view);
    	
    	ActionUtil.setParameterFilterSql("serviceStream", "m.serviceStream_id", request);
    	ActionUtil.setParameterFilterSql("department", "ml.id", request);
    	ActionUtil.setParameterFilterSql("pigeonHole", "ml.pigeonHole_id", request);
    	String serviceStream=request.getParameter("serviceStream");
    	paramHref.append("&serviceStream=").append(serviceStream!=null?serviceStream:"");
    	paramHref.append("&dateBegin=").append(date);
    	paramHref.append("&typeSex=").append(typeSex);
    	request.setAttribute("paramHref", paramHref.toString()) ;
    	
    	request.setAttribute("dispSql", "");
    	StringBuilder sbDisp=new StringBuilder();
    	if(typeDisp!=null) {
    		
    		if(typeDisp.equals("2")) {
    			sbDisp.append(" and edc.id is null ");
    		} else if(typeDisp.equals("3")) {
    			sbDisp.append(" and edc.id is not null and to_char(edc.FinishDate,'yyyy')='"+date+"' ");
    		}
    		
    	}
    	
    	
    	if(typeRange!=null&&typeRange.equals("2")) {
   			String lpu = request.getParameter("lpu"); 
  			String area = request.getParameter("area");
   			if (lpu!=null&&!lpu.equals("")&&!lpu.equals("0")) {
   				sbDisp.append(" and la.lpu_id="+lpu);
   				}
   			if (area!=null&&!area.equals("")&&!area.equals("0")) {
   				sbDisp.append(" and la.id="+area);
   			}
   			sbDisp.append(" and (lad.id is not null and lad.dateto is null) ");
    	}

    	if (typeSex!=null&&typeSex.equals("2")) {
    		sbDisp.append(" and vs.omccode='1'");
    	} else if (typeSex!=null&&typeSex.equals("3")){
    		sbDisp.append(" and vs.omccode='2'");
    	}
    	//request.setAttribute("rangeSQL", range);
//</AOI 14.06.2016
    		request.setAttribute("dispSql", sbDisp.toString());
    	if (typeReestr!=null && typeReestr.equals("1")) {
    			String year=request.getParameter("year") ; String month=request.getParameter("month" );
    			StringBuilder where = new StringBuilder() ;
    			String dtype="Patient" ;
    			if (year!=null) {
    				where.append(" and to_char(pat.birthday,'yyyy')='").append(year).append("'") ;	
    			}
    			if (month!=null) {
    				where.append(" and to_char(pat.birthday,'mm')='").append(month).append("'") ;
    			}
    			
    				request.setAttribute("whereSql", where.toString()) ;
    				//request.setAttribute("groupBy", groupBy) ;
    				if (dtype.equals("Patient")) {
						String defaultLpu = ActionUtil.getDefaultParameterByConfig("DEFAULT_LPU_OMCCODE","300026",request);
    				    String leftJoinSql = "  left join patientfond pf on pf.lastname=pat.lastname and pf.firstname=pat.firstname and pf.middlename=pat.middlename and pf.birthday=pat.birthday and pf.id=(select max(id) from patientfond where lastname=pat.lastname and firstname=pat.firstname and middlename=pat.middlename and birthday=pat.birthday)";

    				    String selectSql = " ,'По состоянию на '||to_char(pf.checkDate,'dd.MM.yyyy')||' пациент прикреплен к ЛПУ '||pf.lpuattached||' от '||to_char(pf.attachedDate,'dd.MM.yyyy')||' способом '||pf.attachedtype as fondAttached" +
								" ,case when pf.lpuattached!='"+defaultLpu+"' then 'color:red' else 'color:green' end as color";
						request.setAttribute("leftJoinSql",leftJoinSql);
						request.setAttribute("selectSql",selectSql);
    			%>
    			
    <ecom:webQuery isReportBase="true" name="reestr" nameFldSql="reestr_sql" nativeSql="
    select distinct pat.id
    ,pat.lastname,pat.firstname,pat.middlename,to_char(pat.birthday,'dd.mm.yyyy') as birthday
    ,coalesce(a.fullname)||' ' || case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end 
	 ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end 
	||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end as address
	,(select list(ved.name||' '||to_char(edc.startDate,'dd.mm.yyyy')||' '||to_char(edc.finishDate,'dd.mm.yyyy')||case when isServiceIndication='1' then ', направлен на 2 этап' else '' end) from ExtDispCard edc left join VocExtDisp ved on ved.id=edc.dispType_id where edc.patient_id=pat.id and to_char(edc.FinishDate,'yyyy')='${param.dateBeginYear}') as edclist
     ,la.number as f8_laNumber
         ,coalesce(a2.fullname)||' ' || case when pat.houseNumber is not null and pat.realhouseNumber!='' then ' д.'||pat.realhouseNumber else '' end 
	 ||case when pat.realhouseBuilding is not null and pat.realhouseBuilding!='' then ' корп.'|| pat.realhouseBuilding else '' end 
	||case when pat.realflatNumber is not null and pat.realflatNumber!='' then ' кв. '|| pat.realflatNumber else '' end as f9_address_real
	${selectSql}
	,pat.patientSync as f12_patSync
     from  Patient pat
     
     left join Address2 a on a.addressid=pat.address_addressid
     left join Address2 a2 on a2.addressid=pat.realaddress_addressid
     left join extdispcard edc on edc.patient_id=pat.id and to_char(edc.FinishDate,'yyyy')='${param.dateBeginYear}' 
     left join lpuattachedbydepartment lad on lad.patient_id=pat.id
     left join lpuarea la on la.id=lad.area_id
     left join vocSex vs on vs.id=pat.sex_id
	${leftJoinSql}
	where pat.deathDate is null ${whereSql} ${dispSql} 
	order by pat.lastname,pat.firstname,pat.middlename
    "/>
   <msh:section>
       <msh:sectionTitle>
    
    	<form action="print-mis_patient_by_age.do" method="post" target="_blank">
	    Реестр пациентов
	    <input type='hidden' name="sqlText" id="sqlText" value="${reestr_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Список пациентов, рожденных ${param.month}.${param.year}  на ${param.dateBeginYear} год">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table action="entityView-mis_patient.do" printToExcelButton="Сохранить в excel"
    viewUrl="entityShortView-mis_patient.do" styleRow="11"
     name="reestr" idField="1">
    	<msh:tableColumn property="sn" columnName="#"/>
    	<msh:tableColumn property="12" columnName="Код синхр"/>
    	<msh:tableColumn property="2" columnName="Фамилия"/>
    	<msh:tableColumn property="3" columnName="Имя"/>
    	<msh:tableColumn property="4" columnName="Отчество"/>
    	<msh:tableColumn property="5" columnName="Дата рождения"/>
    	<msh:tableColumn property="6" columnName="Адрес"/>
    	<msh:tableColumn property="7" columnName="Доп. диспансеризации за ${param.dateBeginYear}"/>
    	<msh:tableColumn property="8" columnName="Номер участка"/>
    	<msh:tableColumn property="9" columnName="Адрес проживания"/>
    	<msh:tableColumn property="10" columnName="Сведения о прикреплении ФОМС"/>

    </msh:table>
    </msh:sectionContent>
   </msh:section> 			
    				    			<%    					
    				}
    				} else {
						if (typeGroup!=null&&typeGroup.equals("1")) {
							request.setAttribute("groupBySql", "group by to_char(pat.birthday,'yyyy') order by to_char(pat.birthday,'yyyy') desc ");
							request.setAttribute("selectSql", "'&dateBeginYear="+date+"&year='||to_char(pat.birthday,'yyyy') as id,"+date+"-cast(to_char(pat.birthday,'yyyy') as int) as year");
						} else {
							request.setAttribute("groupBySql", "");
							request.setAttribute("selectSql", "cast('&dateBeginYear="+date+"' as varchar) as id,cast('Все возраста' as varchar) as year");
						}
%>
<ecom:webQuery isReportBase="true" name="swod" nameFldSql="swod_sql" nativeSql="
select 
${selectSql} 
,count(distinct case when to_char(pat.birthday,'mm')='01' then pat.id else null end) cnt01
,count(distinct case when to_char(pat.birthday,'mm')='02' then pat.id else null end) cnt02
,count(distinct case when to_char(pat.birthday,'mm')='03' then pat.id else null end) cnt03
,count(distinct case when to_char(pat.birthday,'mm')='04' then pat.id else null end) cnt04
,count(distinct case when to_char(pat.birthday,'mm')='05' then pat.id else null end) cnt05
,count(distinct case when to_char(pat.birthday,'mm')='06' then pat.id else null end) cnt06
,count(distinct case when to_char(pat.birthday,'mm')='07' then pat.id else null end) cnt07
,count(distinct case when to_char(pat.birthday,'mm')='08' then pat.id else null end) cnt08
,count(distinct case when to_char(pat.birthday,'mm')='09' then pat.id else null end) cnt09
,count(distinct case when to_char(pat.birthday,'mm')='10' then pat.id else null end) cnt10
,count(distinct case when to_char(pat.birthday,'mm')='11' then pat.id else null end) cnt11
,count(distinct case when to_char(pat.birthday,'mm')='12' then pat.id else null end) cnt12
,count(distinct pat.id) as cntAll
from patient pat
left join extdispcard edc on edc.patient_id=pat.id and to_char(edc.FinishDate,'yyyy')='${param.dateBeginYear}'
left join lpuattachedbydepartment lad on lad.patient_id=pat.id
left join lpuarea la on la.id=lad.area_id
left join vocsex vs on vs.id=pat.sex_id
where pat.deathdate is null ${dispSql} 
${groupBySql}

"/> 
<msh:table name="swod" printToExcelButton="Сохранить в excel" action="mis_patient_by_age.do?typeReestr=1&lpu=${param.lpu}&area=${param.area}" idField="1" cellFunction="true">
	<msh:tableColumn property="2" columnName="Возраст" addParam=""/>
	<msh:tableColumn property="3" columnName="Январь" addParam="&month=01"/>
	<msh:tableColumn property="4" columnName="Февраль" addParam="&month=02"/>
	<msh:tableColumn property="5" columnName="Март" addParam="&month=03"/>
	<msh:tableColumn property="6" columnName="Апрель" addParam="&month=04"/>
	<msh:tableColumn property="7" columnName="Май" addParam="&month=05"/>
	<msh:tableColumn property="8" columnName="Июнь" addParam="&month=06"/>
	<msh:tableColumn property="9" columnName="Июль" addParam="&month=07"/>
	<msh:tableColumn property="10" columnName="Август" addParam="&month=08"/>
	<msh:tableColumn property="11" columnName="Сентябрь" addParam="&month=09"/>
	<msh:tableColumn property="12" columnName="Октябрь" addParam="&month=10"/>
	<msh:tableColumn property="13" columnName="Ноябрь" addParam="&month=11"/>
	<msh:tableColumn property="14" columnName="Декабрь" addParam="&month=12"/>
	<msh:tableColumn property="15" columnName="Общее кол-во" addParam=""/>
</msh:table>
<%
    				}
    	} else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
		<script type="text/javascript">
		
		</script>
  </tiles:put>
</tiles:insert>

