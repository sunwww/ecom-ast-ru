<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Отчет по направленным"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String noViewForm = request.getParameter("noViewForm") ;

  	String typeView=ActionUtil.updateParameter("ReportDirectByHospital","typeView","1", request) ;
  	String typeEmergency=ActionUtil.updateParameter("ReportDirectByHospital","typeEmergency","3", request) ;
  	String typeDepartment=ActionUtil.updateParameter("ReportDirectByHospital","typeDepartment","2", request) ;
  	String typeDate=ActionUtil.updateParameter("ReportDirectByHospital","typeDate","2", request) ;
  	String typeGroup=ActionUtil.updateParameter("ReportDirectByHospital","typeGroup","2", request) ;
  	
  	
  	if ("1".equals(noViewForm)) {
  	} else {
  		
  %>
    <msh:form action="/stac_report_direct_in_hospital.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport13" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Искать по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="1"> поступления  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
      </msh:row>      
      <msh:row>
        <td class="label" title="Искать по отделению (typeDepartment)" colspan="1"><label for="typeDepartmentName" id="typeDepartmentLabel">Отделение:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDepartment" value="1"> поступления  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDepartment" value="2">  выписки
        </td>
      </msh:row>      
      <msh:row>
        <td class="label" title="Группировать (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировать:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeGroup" value="1"> отделению  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeGroup" value="2">  кем направлен
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="4">
        	<input type="radio" name="typeGroup" value="3">  тип ЛПУ кем направлен
        </td>
      </msh:row>      
      <msh:row>
        <td class="label" title="Доставлен по показаниям (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания к поступлению:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="1">  экстренно
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="2"  >  планово
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeEmergency" value="3"  >  все
        </td>
       </msh:row>
      <msh:row>
        <td class="label" title="Отображение (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отображение:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  разбивка по экст. план.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2"  >  по типам доставки
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3"  >  реестр по всем направившим ЛПУ
        </td>
       </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4" horizontalFill="true" label="Поток обслуживания" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="lpuDirect" fieldColSpan="4" horizontalFill="true" label="ЛПУ направителя" vocName="mainLpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="lpuFunctionDirect" fieldColSpan="4" horizontalFill="true" label="Тип напр ЛПУ" vocName="vocLpuFunction"/>
        </msh:row>
      <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%} %>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
           <script type='text/javascript'>
           checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
           checkFieldUpdate('typeGroup','${typeGroup}',2) ;
           checkFieldUpdate('typeDate','${typeDate}',2) ;
           checkFieldUpdate('typeDepartment','${typeDepartment}',2) ;
           checkFieldUpdate('typeView','${typeView}',2) ;

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
			 
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='journal_doc_externalMedService.do' ;
    }
   
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printHistology" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_histology.do' ;
    	$('id').value = $('dateBegin').value+":"
    		+$('dateBegin').value+":"
    		+$('department').value;
    }
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    
    String date = request.getParameter("dateBegin") ;
    String dateEnd = request.getParameter("dateEnd") ;
    String period = request.getParameter("period") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
  	String dep = request.getParameter("department") ; 
  	if (dep!=null&&!dep.equals("")&&!dep.equals("0")) {
  		if ("1".equals(typeDepartment)) {
  			request.setAttribute("department", " and sls.department_id='"+dep+"'") ;
  		} else {
  			request.setAttribute("department", " and slo.department_id='"+dep+"'") ;
  		}
  	}
  	
	ActionUtil.setParameterFilterSql("lpuDirect","sls.orderLpu_id", request) ;
	ActionUtil.setParameterFilterSql("lpuFunctionDirect","ml.lpuFunction_id", request) ;

  	if ("1".equals(typeGroup)) {
  		request.setAttribute("typeGroupId", "dep.id") ;
  		request.setAttribute("typeGroupName", "dep.name") ;
  		request.setAttribute("typeGroupColumn", "department") ;
  	} else if ("3".equals(typeGroup)) {
  		request.setAttribute("typeGroupId", "coalesce(ml.lpuFunction_id,'-1')") ;
  		request.setAttribute("typeGroupName", "vlf.name") ;
  		request.setAttribute("typeGroupColumn", "lpuFunctionDirect") ;
  	} else {
  		request.setAttribute("typeGroupId", "coalesce(ml.id,'-1')") ;
  		request.setAttribute("typeGroupName", "ml.name") ;
  		request.setAttribute("typeGroupColumn", "lpuDirect") ;
  	}
  	String serviceStream = request.getParameter("serviceStream") ; 
  	if (serviceStream!=null&&!serviceStream.equals("")&&!serviceStream.equals("0")) {
		request.setAttribute("serviceStreamSql", " and sls.serviceStream_id='"+serviceStream+"'") ;
  	}
    request.setAttribute("dateSql", "1".equals(typeDate) ? "sls.dateStart" : "slo.dateFinish") ;

  	if ("1".equals(typeEmergency)) {
    	request.setAttribute("emergencySql", " and sls.emergency='1'") ;
    } else if ("2".equals(typeEmergency)) {
    	request.setAttribute("emergencySql", " and (sls.emergency is null or sls.emergency='0')") ;
    }

    if (date!=null && !date.equals("") && !"3".equals(typeView)) {
        request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
        if (typeView.equals("1")) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Свод ${reportInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="report_direct_swod" nativeSql="
select '&department=${param.department}&serviceStream=${param.serviceStream}&${typeGroupColumn}='||${typeGroupId},coalesce(${typeGroupName},'Без направления (самообращение)'),
count(distinct sls.id) 
,count(distinct case when sls.emergency='1' then sls.id else null end) as cntEmergency
,count(distinct case when sls.emergency='1' and of_.voc_code='А' then sls.id else null end) as cntEmerVrAmb
,count(distinct case when sls.emergency='1' and of_.voc_code='В' then sls.id else null end) as cntEmerRVK
,count(distinct case when sls.emergency='1' and of_.voc_code='К' then sls.id else null end) as cntEmerAmb
,count(distinct case when sls.emergency='1' and of_.voc_code='О' then sls.id else null end) as cntEmerSam 
,count(distinct case when sls.emergency='1' and of_.voc_code='П' then sls.id else null end) as cntEmerPoly
,count(distinct case when sls.emergency='1' and of_.voc_code='С' then sls.id else null end) as cntEmerStac
,count(distinct case when (sls.emergency is null or sls.emergency='0') then sls.id else null end) as cntPlan
,count(distinct case when (sls.emergency is null or sls.emergency='0') and vht.code='POLYCLINIC' then sls.id else null end) as cntPlanPoly
,count(distinct case when sls.emergency='1' then sls.id else null end)
-count(distinct case when sls.emergency='1' and of_.voc_code='В' then sls.id else null end)
-count(distinct case when sls.emergency='1' and of_.voc_code='К' then sls.id else null end)
 as cntEmergencyNoRVK

from medcase sls
left join medcase slo on slo.parent_id=sls.id  and slo.dtype='DepartmentMedCase'
left join misLpu ml on ml.id=sls.orderLpu_id
left join misLpu dep on dep.id=sls.department_id
left join omc_frm of_ on of_.id=sls.orderType_id
left join vocServiceStream vss on vss.id=sls.serviceStream_id
left join VocHospType vht on sls.sourceHospType_id=vht.id
left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id
where ${dateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
    and    upper(sls.dtype)='HOSPITALMEDCASE'
${department} ${emergencySql} ${lpuDirectSql} ${serviceStreamSql}
${lpuFunctionDirectSql}
and sls.deniedhospitalizating_id is null
group by ${typeGroupId},${typeGroupName}
order by ${typeGroupName}
" />
    <msh:table name="report_direct_swod" printToExcelButton="Сохранить в excel"
    viewUrl="stac_report_direct_in_hospital.do?typeView=1&typeDate=${typeDate}&typeEmergency=${typeEmergency}&typeDepartment=${typeDepartment}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_direct_in_hospital.do?typeView=1&typeDate=${typeDate}&typeEmergency=${typeEmergency}&typeDepartment=${typeDepartment}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1">
     <msh:tableNotEmpty>
     	<tr>
     		<th></th>
     		<th></th>
     		<th></th>
     		<th></th>
     		<th></th>
     		<th colspan="2">в т.ч. (из экстр. общ.)</th>
     		<th></th>
     		<th class="rigthBold">из них</th>
     	</tr>
     </msh:tableNotEmpty>
      <msh:tableColumn columnName="Наименование ЛПУ" property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" isCalcAmount="true" />
      <msh:tableColumn property="4" columnName="Кол-во экстренных (общее кол-во)" isCalcAmount="true"/>
      <msh:tableColumn property="13" columnName="Кол-во экстренных (без КСП и РВК)" isCalcAmount="true"/>
      <msh:tableColumn columnName="коммисий РВК" property="6" isCalcAmount="true"/>
      <msh:tableColumn columnName="карета скорой помощи" property="7" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во плановых" property="11" isCalcAmount="true"/>
      <msh:tableColumn columnName="пол-ка" property="12" isCalcAmount="true"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    		<%
    } else if (typeView.equals("2")) {
    	%>
    	    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Свод ${reportInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="report_direct_swod" nativeSql="
select '&department=${param.department}&serviceStream=${param.serviceStream}&${typeGroupColumn}='||${typeGroupId},coalesce(${typeGroupName},'Без направления'),
count(distinct sls.id) 
,count(distinct case when sls.emergency='1' then sls.id else null end) as cntEmergency
,count(distinct case when sls.emergency='1' and of_.voc_code='А' then sls.id else null end) as cntEmerVrAmb
,count(distinct case when sls.emergency='1' and of_.voc_code='В' then sls.id else null end) as cntEmerRVK
,count(distinct case when sls.emergency='1' and of_.voc_code='К' then sls.id else null end) as cntEmerAmb
,count(distinct case when sls.emergency='1' and of_.voc_code='О' then sls.id else null end) as cntEmerSam 
,count(distinct case when sls.emergency='1' and of_.voc_code='П' then sls.id else null end) as cntEmerPoly
,count(distinct case when sls.emergency='1' and of_.voc_code='С' then sls.id else null end) as cntEmerStac
,count(distinct case when (sls.emergency is null or sls.emergency='0') then sls.id else null end) as cntPlan
,count(distinct case when (sls.emergency is null or sls.emergency='0') and vht.code='POLYCLINIC' then sls.id else null end) as cntPlanPoly

from medcase sls
left join medcase slo on slo.parent_id=sls.id  and slo.dtype='DepartmentMedCase'
left join misLpu ml on ml.id=sls.orderLpu_id
left join misLpu dep on dep.id=sls.department_id
left join omc_frm of_ on of_.id=sls.orderType_id
left join vocServiceStream vss on vss.id=sls.serviceStream_id
left join VocHospType vht on sls.sourceHospType_id=vht.id
left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id
where ${dateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
    and    upper(sls.dtype)='HOSPITALMEDCASE'
${department} ${emergencySql} ${lpuDirectSql} ${serviceStreamSql}
${lpuFunctionDirectSql}
and sls.deniedhospitalizating_id is null
group by ${typeGroupId},${typeGroupName}
order by ${typeGroupName}
" />
    <msh:table name="report_direct_swod"  printToExcelButton="Сохранить в excel"
    viewUrl="stac_report_direct_in_hospital.do?typeView=1&typeDate=${typeDate}&typeEmergency=${typeEmergency}&typeDepartment=${typeDepartment}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_direct_in_hospital.do?typeView=1&typeDate=${typeDate}&typeEmergency=${typeEmergency}&typeDepartment=${typeDepartment}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1">
     <msh:tableNotEmpty>
     	<tr>
     		<th></th>
     		<th></th>
     		<th></th>
     		<th></th>
     		<th colspan="6" class="rigthBold">из них доставлено</th>
     		<th></th>
     		<th class="rigthBold">из них</th>
     	</tr>
     </msh:tableNotEmpty>
      <msh:tableColumn columnName="Наименование ЛПУ" property="2" />
      <msh:tableColumn columnName="Кол-во" property="3" isCalcAmount="true" />
      <msh:tableColumn property="4" columnName="Кол-во экстренных" isCalcAmount="true"/>
      <msh:tableColumn columnName="врач. амбулаторий" property="5" isCalcAmount="true"/>
      <msh:tableColumn columnName="коммисий РВК" property="6" isCalcAmount="true"/>
      <msh:tableColumn columnName="карета скорой помощи" property="7" isCalcAmount="true"/>
      <msh:tableColumn columnName="самообращение" property="8" isCalcAmount="true"/>
      <msh:tableColumn columnName="пол-ка" property="9" isCalcAmount="true"/>
      <msh:tableColumn columnName="стационар" property="10" isCalcAmount="true"/>
      <msh:tableColumn columnName="Кол-во плановых" property="11" isCalcAmount="true"/>
      <msh:tableColumn columnName="пол-ка" property="12" isCalcAmount="true"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <%}
        } else if ("3".equals(typeView) || period!=null && !period.equals("")) {
            if (period!=null && !period.equals("")) {
                String[] obj = period.split("-") ;
                request.setAttribute("dateBegin", obj[0]);
                request.setAttribute("dateEnd", obj[1]);
            }
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="true" name="journal_surOperation" nativeSql="
    select 
sls.id as slsid
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename ||' ' ||to_char(p.birthday,'dd.MM.yyyy') as pat
,cast(to_char(${dateSql},'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(${dateSql}, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(${dateSql},'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end)
 as age,sls.dateStart,sls.dateFinish
, ml.name as mlname, dep.name as depname
 ,of_.name as ofname, vss.name as vssname
 ,vht.name as vhtname,case when sls.emergency='1' then 'Экстр.' else 'План.' end as f12_emerg
 , vlf.name as vlfname
 ,vr.name as vrname
 ,list(mkb.code||' '||mkb.name) as f15_diag
 ,${dateSql}-p.birthday as f16_days
 ,p.newbornWeight as f17_w
from MedCase sls
left join VocHospType vht on sls.sourceHospType_id=vht.id
left join patient p on p.id=sls.patient_id
left join medcase slo on slo.parent_id=sls.id  and slo.dtype='DepartmentMedCase'
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join misLpu ml on ml.id=sls.orderLpu_id
left join misLpu dep on dep.id=sls.department_id
left join omc_frm of_ on of_.id=sls.orderType_id
left join vocServiceStream vss on vss.id=sls.serviceStream_id
left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id
left join VocRayon vr on vr.id=p.rayon_id
left join diagnosis d on d.medcase_id=sls.id and d.registrationType_id=3 and d.priority_id=1
left join vocidc10 mkb on mkb.id=d.idc10_id
where ${dateSql} between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
and    upper(sls.dtype)='HOSPITALMEDCASE'
 
${department} ${emergencySql} ${lpuDirectSql} ${serviceStreamSql}
${lpuFunctionDirectSql}
and sls.deniedhospitalizating_id is null
group by sls.id ,ss.code ,p.id
,p.birthday,sls.dateStart,sls.dateFinish,${dateSql}
, ml.name , dep.name ,of_.name , vss.name 
 ,vht.name ,sls.emergency , vlf.name ,vr.name
order by p.lastname,p.firstname,p.middlename " />
    <msh:table name="journal_surOperation"  printToExcelButton="Сохранить в excel"
    viewUrl="entityShortView-stac_ssl.do" 
     action="entityView-stac_ssl.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
      <msh:tableColumn columnName="№стат. карт" property="2" />
      <msh:tableColumn columnName="ФИО пациента" property="3" />
      <msh:tableColumn columnName="Возраст" property="4" />
      <msh:tableColumn columnName="Район" property="14" />
      <msh:tableColumn columnName="Дата поступления" property="5"/>
      <msh:tableColumn columnName="Дата выписки" property="6"/>
      <msh:tableColumn columnName="Порядок пост." property="12"/>
      <msh:tableColumn columnName="Тип нарп. ЛПУ" property="11"/>
      <msh:tableColumn columnName="Кем направлен" property="7"/>
      <msh:tableColumn columnName="Функция ЛПУ" property="13"/>
      <msh:tableColumn columnName="Отделение" property="8"/>
      <msh:tableColumn columnName="Кем доставлен" property="9"/>
      <msh:tableColumn columnName="Поток обслуживания" property="10"/>
      <msh:tableColumn columnName="Выписной диагноз" property="15"/>
      <msh:tableColumn columnName="возраст (дней)" property="16"/>
      <msh:tableColumn columnName="Вес" property="17"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
    	<%
    	} else {%>
    	<i>Нет данных </i>
    	<% } %>
    
  </tiles:put>
</tiles:insert>