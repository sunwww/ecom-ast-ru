<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Report">Просмотр отчета по гражданству(Приказ №47р от 19.01.2017) </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typeDate =ActionUtil.updateParameter("Report_nationality","typeDate","1", request) ;
	String typePatient =ActionUtil.updateParameter("Report_nationality","typePatient","1", request) ;
	//String typeGroup =ActionUtil.updateParameter("Report_nationality","typeGroup","2", request) ;
	String typeDepartment =ActionUtil.updateParameter("Report_nationality","typeDepartment","2", request) ;
	//String typeView =ActionUtil.updateParameter("Report_nationality","typeView","3", request) ;
  %>
    <msh:form action="/journal_nationality_new.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="m" id="m" value="categoryForeignNationals"/>
    <input type="hidden" name="s" id="s" value="VisitPrintService"/>
    <input type="hidden" name="id" id="id"/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
              <msh:row>
	        <td class="label" title="Поиск по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Дата поиска:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="1">  поступления
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDate" value="2"  >  выписки 
	        </td>

        </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  иногородние
        </td>
      </msh:row>
        <msh:row>
            <td class="label" title="Поиск по пациентам (typeDepartment)" colspan="1"><label for="typePatientName" id="typeDepartmentLabel">Группировать по отделению:</label></td>
            <td onclick="this.childNodes[1].checked='checked';">
                <input type="radio" name="typeDepartment" value="1">  да
            </td>
            <td onclick="this.childNodes[1].checked='checked';">
                <input type="radio" name="typeDepartment" value="2">  нет
            </td>
        </msh:row>
        <msh:autoComplete property="serviceStream" vocName="vocServiceStream" label="Поток обслуживания" fieldColSpan="5"/>
   
      <msh:row>
        	<msh:textField property="beginDate"  label="Период с" />
        	<msh:textField property="finishDate" fieldColSpan="7" label="по" />
      </msh:row>
        <msh:row>
        <td colspan="1" class="buttons">
			<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;journal_nationality_new.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
 		</td>
        </msh:row>

    </msh:panel>
    </msh:form>
    <% 

	String date = (String)request.getParameter("beginDate");
	String dateEnd = (String)request.getParameter("finishDate");
	String serviceStream = request.getParameter("serviceStream");

	if (date!=null && !date.equals("")){
	    
	if(dateEnd==null || dateEnd.equals("")){ 
	    dateEnd = date;
	}
	  
	  
	 request.setAttribute("beginDate",date);
	 request.setAttribute("finishDate",dateEnd);
String groupSql="",change="",address="",names="", selectDepSql=",cast('' as varchar) as f16", whereSql="";
if (serviceStream!=null&& !serviceStream.trim().equals("") && !serviceStream.equals("0")) {
    whereSql=" and m.serviceStream_id="+serviceStream;
}
    if (typePatient.equals("1")) {
    	
    	groupSql= " and (oo.id is not null and oo.voc_code!='643') group by ##GROUP oo.name order by oo.name";
		change= "oo.name as vnname";
		address= "";
    	names= "Страна, где зарегистрирован гражданин";
    	
	} else if (typePatient.equals("2")) {
	    groupSql= " and ar.kladr not like '30%' and (oo.id is null or oo.voc_code='643') group by ##GROUP ar.name order by ar.name";
		change= "ar.name as vnname";
		address= " left join address2 a on a.addressid=p.address_addressid left join Address2 ar on ar.addressid=a.region_addressid";
		names= "Cубъект РФ, где зарегистрирован гражданин";

	}
    if (typeDate.equals("1")) {
    	request.setAttribute("dateFld", "dateStart") ;
    } else {
    	request.setAttribute("dateFld", "dateFinish") ;
    }

    if (typeDepartment.equals("1")) { //группировка по отделению
        address+=" left join mislpu dep on dep.id=smo.department_id";
        selectDepSql=",coalesce(dep.name,'Поликлиника') as f_16 ";
        groupSql=groupSql.replace("##GROUP","dep.name, ");
    }
        groupSql=groupSql.replace("##GROUP","");
        request.setAttribute("groupSql",groupSql);
        request.setAttribute("change",change);
        request.setAttribute("address",address);
        request.setAttribute("names",names);
        request.setAttribute("selectDepSql",selectDepSql);
        request.setAttribute("whereSql",whereSql);

    if (date!=null && dateEnd!=null) {
    	%>
    
    <msh:section>
<ecom:webQuery isReportBase="true" nameFldSql="sql_journal_swod" name="journal_swod" nativeSql="
select ${change}
,count(distinct case when m.dtype='PolyclinicMedCase' and m.dateStart=m.dateFinish and vss.code='OBLIGATORYINSURANCE'  then m.id end) as visitOMC
,count(distinct case when m.dtype='PolyclinicMedCase' and m.dateStart=m.dateFinish and vss.code='BUDGET' then m.id else null end) as visitBUDGET
,count(distinct case when m.dtype='PolyclinicMedCase' and m.dateStart=m.dateFinish and (vss.code='CHARGED' or vss.code='PRIVATEINSURANCE' or vss.code='DOGOVOR')  then m.id else null end) as visitPATIENT
,count(distinct case when m.dtype='PolyclinicMedCase' and (m.dateStart!=m.dateFinish or m.dateFinish is null) and vss.code='OBLIGATORYINSURANCE' then m.id else null end) as treatmentOMC
,count(distinct case when m.dtype='PolyclinicMedCase' and (m.dateStart!=m.dateFinish or m.dateFinish is null) and vss.code='BUDGET' then m.id else null end) as treatmentBUDGET
,count(distinct case when m.dtype='PolyclinicMedCase' and (m.dateStart!=m.dateFinish or m.dateFinish is null) and (vss.code='CHARGED' or vss.code='PRIVATEINSURANCE' or vss.code='DOGOVOR') then m.id else null end) as treatmentPATIENT
,count(distinct case when m.dtype='HospitalMedCase' and vss.code='OBLIGATORYINSURANCE' and vbs.code='1'  then m.id else null end) as statOMC
,count(distinct case when m.dtype='HospitalMedCase' and vss.code='BUDGET' and vbs.code='1' then m.id else null end) as statBudget
,count(distinct case when m.dtype='HospitalMedCase' and (vss.code='CHARGED' or vss.code='PRIVATEINSURANCE' or vss.code='DOGOVOR') and vbs.code='1' then m.id else null end) as statPatient
,count(distinct case when m.dtype='HospitalMedCase' and vss.code='OBLIGATORYINSURANCE' and vbs.code='2'  then m.id else null end) as daystatOMC
,count(distinct case when m.dtype='HospitalMedCase' and vss.code='BUDGET' and vbs.code='2' then m.id else null end) as daystatBudget
,count(distinct case when m.dtype='HospitalMedCase' and (vss.code='CHARGED' or vss.code='PRIVATEINSURANCE' or vss.code='DOGOVOR') and vbs.code='2' then  m.id else null end) as daystatPatient
,0,0 ${selectDepSql}
from medcase m
left join medcase smo on smo.parent_id = m.id
left join VocServiceStream vss on vss.id=m.serviceStream_id
left join Patient p on p.id=m.patient_id
${address}
left join Omc_Oksm oo on oo.id=p.nationality_id 
left join bedfund bf on bf.id = smo.bedfund_id
left join vocbedsubtype vbs on vbs.id = bf.bedsubtype_id 

where m.dateStart between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')

AND case when m.dtype = 'HospitalMedCase' then case when m.deniedHospitalizating_id is not null then '0' else '1' end else '1' end = '1'
and (smo.dtype in ('Visit', 'ShortMedCase') or smo.dtype='DepartmentMedCase' and smo.transferDate is null)
and m.dtype in ('HospitalMedCase', 'PolyclinicMedCase')
and vss.code in ('OBLIGATORYINSURANCE', 'BUDGET', 'CHARGED', 'PRIVATEINSURANCE','DOGOVOR') ${whereSql}
${groupSql}"
/> 

    <msh:sectionContent>
        <msh:table printToExcelButton="Сохранить в excel"
         name="journal_swod" action="journal_nationality_new.do?beginDate=${beginDate}&finishDate=${finishDate}&typeView=1&typeGroup=${typeGroup}&typePatient=${typePatient}&typeEmergency=${typeEmergency}&typeDate=${typeDate}" idField="1" noDataMessage="Не найдено">

            <msh:tableNotEmpty>
              <tr>
                <th colspan="3" rowspan="2" />
                <th colspan="6" class="rightBold">Количество пациентов, получивших медицинскую помощь в амбулаторных условиях</th>
                <th colspan="3" rowspan="2" class="rightBold">Количество пациентов, получивших медицинскую помощь в стационарных условиях</th>
                <th colspan="3" rowspan="2" class="rightBold">Количество пациентов, получивших медицинскую помощь в условиях дневного стационара</th>
                <th colspan="2" rowspan="2" class="rightBold">Количество па-циентов, полу-чивших скорую медицинскую помощь</th>
              </tr>
              <tr>
              <th colspan="3" class="rightBold">Количество посещений</th>
              <th colspan="3" class="rightBold">Количество обращений</th>
              </tr>
            </msh:tableNotEmpty>            
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="${names}" property="1"/>            
            <msh:tableColumn columnName="Отделение" property="16"/>
            <msh:tableColumn columnName="за счет ОМС" property="2" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет личных средств граждан, договору и ДМС" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет ОМС" property="5" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="6" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет личных средств граждан, договору и ДМС" property="7" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет ОМС" property="8" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="9" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет личных средств граждан, договору и ДМС" property="10" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет ОМС" property="11" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="12" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет личных средств граждан, договору и ДМС" property="13" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет ОМС" property="14" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="15" isCalcAmount="true"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	

    	<% } } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>

  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	
    checkFieldUpdate('typePatient','${typePatient}',1) ;
    checkFieldUpdate('typeDate','${typeDate}',1) ;
    checkFieldUpdate('typeDepartment','${typeDepartment}',2) ;

  	function checkFieldUpdate(aField,aValue,aDefault) {
  		aValue=+aValue ;
    	eval('var chk =  document.forms[0].'+aField) ;
    	max = chk.length ;
    	if (aValue<1) aValue=+aDefault ;
    	if (aValue>max) {
    		if (aDefault>max) {
    			chk[max-1].checked='checked' ;
    		} else {
    			chk[aDefault-1].checked='checked' ;
    		}
    	} else {
    		chk[aValue-1].checked='checked' ;
    	}
    }
    
  	function getId(aBis) {
		 
		
	}
  	function getCheckedValue(radioGrp) {
  		var radioValue ;
  		for(var i=0; i < radioGrp.length; i++){
  		  if (radioGrp[i].checked == true){
  		    radioValue = radioGrp[i].value;
  		    break ;
  		  }
  		} 
  		return radioValue ;
  	}
  		
  	</script>
  </tiles:put>

</tiles:insert>