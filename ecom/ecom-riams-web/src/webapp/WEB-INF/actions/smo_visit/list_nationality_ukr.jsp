<%@page import="java.util.List"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.Collection"%>
<%@page import="ru.ecom.mis.web.dwr.medcase.HospitalMedCaseServiceJs"%>
<%@page import="ru.ecom.mis.ejb.service.patient.HospitalLibrary"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Report">Просмотр отчета по гражданству </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typeEmergency =ActionUtil.updateParameter("Report_nationality","typeEmergency","3", request) ;
	String typePatient =ActionUtil.updateParameter("Report_nationality","typePatient","1", request) ;
	String typeAge =ActionUtil.updateParameter("Report_nationality","typeAge","3", request) ;
	String typeGroup =ActionUtil.updateParameter("Report_nationality","typeGroup","2", request) ;
	String typeView =ActionUtil.updateParameter("Report_nationality","typeView","3", request) ;

  %>
    <msh:form action="/journal_nationality_ukraine.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="m" id="m" value="categoryForeignNationals"/>
    <input type="hidden" name="s" id="s" value="VisitPrintService"/>
    <input type="hidden" name="id" id="id"/>
    <msh:panel>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
        <msh:row styleId="noswod">
	        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="1">  экстренные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="2"  >  плановые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="3">  все
	        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="1">  по отделениям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="2">  по гражданству
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="3">  по потоку обслуживания
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Отображать (typeView)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Отображать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  реестр обращений
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2">  реестр пациентов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3">  свод
        </td>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по возрастам (typeAge)" colspan="1"><label for="typeAgeName" id="typeAgeLabel">Возраст:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="1">  До 18 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="2">  Старше 18 лет
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="3">  Все
        </td>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  соотечественники
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  иногородние
        </td>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="4">  без адреса
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typePatient" value="5">  иностранцы+соотечественники
        </td>
      </msh:row>
       <msh:row>
       	<msh:autoComplete property="department" fieldColSpan="4"
       	label="Отделение" horizontalFill="true" vocName="lpu"/>
       </msh:row>
        <msh:row>
        	<msh:autoComplete property="nationality" fieldColSpan="4"
        	label="Гражданство" horizontalFill="true" vocName="omcOksm"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4"
        	label="Поток облуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
      <msh:row>
        	<msh:textField property="beginDate"  label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" fieldColSpan="7" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        <td colspan="3" class="buttons">
			<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;journal_nationality_ukraine.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
 		</td>
        
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
    	if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null
    	 || request.getParameter("id")!=null && !request.getParameter("id").equals("")
    	) {
    		
        	if (typeEmergency!=null && typeEmergency.equals("1")) {
        		request.setAttribute("emergencySql", " and m.emergency='1' ") ;
        		request.setAttribute("emergencyInfo", ", поступивших по экстренным показаниям") ;
        		request.setAttribute("emergencyTicketSql", " and t.emergency='1' ") ;
        	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
        		request.setAttribute("emergencySql", " and (m.emergency is null or m.emergency='0') ") ;
        		request.setAttribute("emergencyInfo", ", поступивших по плановым показаниям") ;
        		request.setAttribute("emergencyTicketSql", " and (t.emergency is null or t.emergency='0') ") ;
        	} 
        	if (typePatient.equals("1")) {
    			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
    			request.setAttribute("patientSql", HospitalLibrary.getSqlGringo(true, "vn")) ;
    			request.setAttribute("infoTypePat", "Поиск по иностранцам") ;
        	} else if (typePatient.equals("2")) {
    			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
    			request.setAttribute("patientSql", " and (p.isCompatriot='1' or vn.isCompatriot='1')") ;
    			request.setAttribute("infoTypePat", "Поиск по соотечественникам") ;
        	} else if (typePatient.equals("3")) {
    			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
    			request.setAttribute("patientSql", " and a.kladr not like '30%' and (vn.id is null or vn.voc_code='643')") ;
    			request.setAttribute("infoTypePat", "Поиск по иногородним") ;
        	} else if (typePatient.equals("4")) {
    			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
    			request.setAttribute("patientSql", " and a.kladr is null and (vn.id is null or vn.voc_code='643')") ;
    			request.setAttribute("infoTypePat", "Поиск по иногородним") ;
    		} else {
    			request.setAttribute("patientSql", "") ;
    			request.setAttribute("infoTypePat", "Поиск по всем") ;
    		}
        	
        	if (typeGroup!=null&&typeGroup.equals("1")) {
    			request.setAttribute("groupSql", "coalesce(mlV.name,ml.name)") ;
    			request.setAttribute("groupSqlId", "coalesce(mlV.id,ml.id)") ;
    			request.setAttribute("groupId", "'&department='||coalesce(mlV.id,ml.id)") ;
    			request.setAttribute("groupName", "Наименование отделения") ;
    			request.setAttribute("group1Sql", "ml.name") ;
    			request.setAttribute("group1SqlId", "ml.id") ;
    			request.setAttribute("group1Id", "'&department='||ml.id") ;
        	} else if (typeGroup!=null&&typeGroup.equals("3")) {
    			request.setAttribute("groupSql", "vss.name") ;
    			request.setAttribute("groupSqlId", "vss.id") ;
    			request.setAttribute("groupId", "'&serviceStream='||vss.id") ;
    			request.setAttribute("groupName", "Поток обслуживания") ;
    			request.setAttribute("group1Sql", "vss.name") ;
    			request.setAttribute("group1SqlId", "vss.id") ;
    			request.setAttribute("group1Id", "'&serviceStream='||vss.id") ;
        	} else {
        		if (!typePatient.equals("3")) {
	    			request.setAttribute("groupSql", "vn.name") ;
	    			request.setAttribute("groupSqlId", "coalesce(p.nationality_id,0)") ;
	    			request.setAttribute("groupId", "'&nationality='||coalesce(p.nationality_id,0)") ;
	    			request.setAttribute("group1Sql", "vn.name") ;
	    			request.setAttribute("group1SqlId", "coalesce(p.nationality_id,0)") ;
	    			request.setAttribute("group1Id", "'&nationality='||coalesce(p.nationality_id,0)") ;
	    			request.setAttribute("groupName", "Гражданство") ;
        		} else {
	    			request.setAttribute("groupSql", "ar.name") ;
	    			request.setAttribute("groupSqlId", "coalesce(a.region_addressid,0)") ;
	    			request.setAttribute("groupId", "'&region='||coalesce(a.region_addressid,0)") ;
	    			request.setAttribute("group1Sql", "ar.name") ;
	    			request.setAttribute("group1SqlId", "coalesce(ap.region_addressid,0)") ;
	    			request.setAttribute("group1Id", "'&region='||coalesce(a.region_addressid,0)") ;
	    			request.setAttribute("groupName", "Район") ;
	    			request.setAttribute("groupSqlAdd", "left join Address2 ar on ar.addressid=a.region_addressid") ;
        		}
        	}
        	if (typeAge!=null) {
        		StringBuilder age = new StringBuilder();
        		age.append(" and cast(to_char(m.dateStart,'yyyy') as int)")
        				.append(" -cast(to_char(p.birthday,'yyyy') as int)")
        				.append(" +(case when (cast(to_char(m.dateStart, 'mm') as int)")
        				.append(" -cast(to_char(p.birthday, 'mm') as int)")
        				.append(" +(case when (cast(to_char(m.dateStart,'dd') as int)") 
        				.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
        				.append(" <0)")
        				.append(" then -1 else 0 end)") ;
        		if (typeAge.equals("1")) {
        			age.append("< 18 ");
        			request.setAttribute("ageSql", age);
            	} else if (typeAge.equals("2")) {
            		age.append(">= 18 ");
            		request.setAttribute("ageSql", age);
            	} else {
            		request.setAttribute("ageSql", "");
            	}
        	}
        	
        	ActionUtil.setParameterFilterSql("serviceStream","vss.id", request) ;
        	ActionUtil.setParameterFilterSql("department","m.department_id", request) ;
        	ActionUtil.setParameterFilterSql("department","departmentD","m.department_id", request) ;
        	ActionUtil.setParameterFilterSql("department","departmentWF","we.lpu_id", request) ;
        	ActionUtil.setParameterFilterSql("nationality","p.nationality_id", request) ;
        	ActionUtil.setParameterFilterSql("region","a.region_addressid", request) ;
    		%>
   
    <% 
    //начало реестра
    
    if (typeView.equals("1")) {
    	
    
    %>


  	<msh:section title="Поликлиника">

  	
	    <ecom:webQuery nameFldSql="list_yes_sql" name="list_yes" maxResult="1000" nativeSql="select m.id
	    
	    ,to_char(m.dateStart,'DD.MM.YYYY') as dateStart

	    ,p.lastname||' '||p.firstname||' '||p.middlename as fio,to_char(p.birthday,'DD.MM.YYYY') as birthday
	    ,vwfe.name||' '||pe.lastname as pefio
	    ,list(mkb.code) as mkbcode
	    ,vss.name, res.name as resname
	    ,case when vsst.omccode='66' then '+' else '-' end 
from medcase m 
left join vocvisitresult res on res.id=m.visitresult_id 
left join patient p on p.id=m.patient_id
left join address2 a on a.addressid=p.address_addressid
left join Omc_Oksm vn on vn.id=p.nationality_id
left join WorkFunction wfe on wfe.id=m.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join MisLpu ml on ml.id=we.lpu_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join VocVisitResult vvr on vvr.id=m.visitResult_id
left join VocServiceStream vss on vss.id=m.serviceStream_id
left join Diagnosis d on d.medcase_id=m.id
left join Vocidc10 mkb on mkb.id=d.idc10_id
left join vocsocialstatus vsst on vsst.id=p.socialstatus_id 
${groupSqlAdd}
where  m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and (m.DTYPE='Visit' or m.DTYPE='ShortMedCase')  
and (m.noActuality is null or m.noActuality='0')
and vss.code!='HOSPITAL' 
${emergencySql} ${departmentWFSql}
${serviceStreamSql}
 ${nationalitySql} ${regionSql} ${patientSql} ${ageSql}
group by m.id,m.dateStart,m.timeExecute
	    ,p.lastname,p.firstname,p.middlename,p.birthday
	    ,vwfe.name,pe.lastname,vss.name,res.name,vsst.omccode  
order by p.lastname,p.firstname,p.middlename"/>

  	</msh:section>
  	<%
  	List listPol = (List)request.getAttribute("list_yes") ;
  	int sizePol = listPol.size();
  	if (sizePol>0) {
/*   	out.println("<tr><td>") ;
	out.println("<table border=1>") ;
	out.println("<tr>") ;
	out.print("<th>№</th>") ;
	out.print("<th>Пациент</th>") ;
	out.print("<th>Дата рождения</th>") ;
	out.print("<th>Беженец</th>") ;
	out.print("<th>Дата обращения</th>") ;
	out.print("<th>Диагноз</th>") ;
	out.print("<th>Специалист</th>") ;
	out.print("<th>Результат лечения</th>") ;
	out.print("<th>Поток обслуживания</th>") ;
	out.print("<th>Объем финансирования</th>") ;
	out.println("</tr>") ; */
	for (int i=0; i<sizePol; i++) {
		WebQueryResult wqr = (WebQueryResult) listPol.get(i) ;
		/* out.println("<tr>") ; 
		out.print("<td>") ;	//1
		out.println(i+1) ;out.print("</td>") ;
		out.print("<td>") ;	//2
		out.print("<b>"+wqr.get3()!=null?wqr.get3():"") ;out.print("</b></td>") ;		
		//out.println(wqr.get1()!=null?wqr.get1():"0") ; //IDMEDCASE
		out.print("<td>") ;	//3
		out.print(wqr.get4()) ;out.print("</td>") ;
		out.print("<td>") ;	//Беженец
		out.print(wqr.get9());
		out.print("</td>") ;
		out.print("<td>") ;	//4
		out.println(wqr.get2()) ;out.print("</td>") ;
		out.print("<td>") ;out.println(wqr.get6()) ;out.print("</td>") ;	//5
		out.print("<td>") ;out.println(wqr.get5()) ;out.print("</td>") ;	//6
		out.print("<td>") ;out.print(wqr.get8()!=null?wqr.get8():"");out.print("</td>") ;	//7
		out.print("<td>") ;out.print(wqr.get7()!=null?wqr.get7():"");out.print("</td>") ;
		out.print("<td>") ; */
		try {
			String[] arr =HospitalMedCaseServiceJs.getDataByReferencePrintNotOnlyOMS(Long.valueOf(wqr.get1().toString()),"SPO",false,"OTHER", request).split("&");
			for (int j=0;j<arr.length;j++)
			{
				if (arr[j].startsWith("render")) {
					String[] arrPrice =arr[j].split("%23"); 
					String price = arrPrice[0].substring(7,arrPrice[0].length());
					//wqr.set1(price);
					wqr.set10(price.length()>0?price+" руб.":"---");
					listPol.set(i,wqr);
					/* out.print(" ");out.print(price);out.print(" руб"); */
					break;
				}
			}
		} catch (Exception e) {
			/* out.print("---"); */
			wqr.set10("---");
			listPol.set(i,wqr);
			
		}
		/* out.print("</td>") ;
		out.println("</tr>") ; */
	}
	request.setAttribute("list_yes", listPol);
/* 	out.println("</table>") ;
	out.println("</td></tr>") ; */
  	} else {
  		/* out.print("Нет данных"); */
  	}
  	%>
   <msh:table name="list_yes" action="entitySubclassView-mis_medCase.do"
    	viewUrl="entityView-mis_medCase.do?short=Short" idField="1">
    	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
    	      <msh:tableColumn columnName="Пациент" property="3" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
    	      <msh:tableColumn columnName="Дата рождения" property="4" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
    	      <msh:tableColumn columnName="Беженец" property="9" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
    	      <msh:tableColumn columnName="Дата обращения" property="2" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
    	      <msh:tableColumn columnName="Диагноз" property="6" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
    	      <msh:tableColumn columnName="Специалист" identificator="false" property="5" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
    	      <msh:tableColumn columnName="Результат лечения" identificator="false" property="8" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
    	      <msh:tableColumn columnName="Поток обслуживания" identificator="false" property="7" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
    	      <msh:tableColumn columnName="Стоимость случая" identificator="false" property="10" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
    	     </msh:table>
  	<msh:section title="Стационар">

  	
	    <ecom:webQuery nameFldSql="list_stac_sql" name="list_stac" maxResult="1000" nativeSql="select smo.id
	    
	    ,to_char(smo.dateStart,'DD.MM.YYYY') as dateStart
	    ,to_char(smo.dateFinish,'DD.MM.YYYY') as dateFinish
	    ,p.lastname||' '||p.firstname||' '||p.middlename as fio
	    ,ss.code as sscode 
	    ,ml.name as mlname,vss.name as vssname, res.name as resname
	    ,to_char(p.birthday,'DD.MM.YYYY') as birthday
	    ,mkb.code || ' ' ||mkb.name as diagnosis
	    ,case when vsst.omccode='66' then '+' else '-' end 
	    
from medcase m 
left join medcase smo on smo.id=m.parent_id
left join vochospitalizationresult res on res.id=smo.result_id
left join patient p on p.id=m.patient_id
left join address2 a on a.addressid=p.address_addressid
${groupSqlAdd}
left join Omc_Oksm vn on vn.id=p.nationality_id
left join statisticstub ss on ss.id=smo.statisticStub_id
left join mislpu ml on ml.id=m.department_id
left join VocServiceStream vss on vss.id=smo.serviceStream_id
left join diagnosis ds on ds.medcase_id=smo.id and ds.registrationtype_id='3' and ds.priority_id='1'
left join vocidc10 mkb on mkb.id=ds.idc10_id
left join vocsocialstatus vsst on vsst.id=p.socialstatus_id 
where  
m.DTYPE='DepartmentMedCase' and m.dateFinish between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and (m.noActuality is null or m.noActuality='0')
and smo.deniedHospitalizating_id is null
${emergencySql} ${departmentSql} 
${serviceStreamSql}
${nationalitySql} ${regionSql} ${patientSql}  ${ageSql}
order by p.lastname,p.firstname,p.middlename"/>
</msh:section>  	
  <%
  	List listStac = (List)request.getAttribute("list_stac") ;
  	int sizeStac = listStac.size();
  	if (sizeStac>0) {
/*    	out.println("<tr><td>") ;
	out.println("<table border=1>") ;
	out.println("<tr>") ;
	out.print("<th>№</th>") ;
	out.print("<th>Пациент</th>") ;
	out.print("<th>Дата рождения</th>") ;
	out.print("<th>Беженец</th>") ;
	out.print("<th>Диагноз</th>") ;
	out.print("<th>Дата поступления</th>") ;
	out.print("<th>Дата выписки</th>") ;
	out.print("<th>Результат лечения</th>") ;
	out.print("<th>Поток обслуживания</th>") ;
	out.print("<th>Объем финансирования</th>") ;
	out.println("</tr>") ; */ 
	for (int i=0; i<sizeStac; i++) {
		WebQueryResult wqr = (WebQueryResult) listStac.get(i) ;
/* 	 out.println("<tr>") ; 
		out.print("<td>") ;	//1
		out.println(i+1) ;out.print("</td>") ;
		out.print("<td>") ;	//2
		out.print("<b>"+wqr.get4()!=null?wqr.get4():"") ;out.print("</b></td>") ;		
		//out.println(wqr.get1()!=null?wqr.get1():"0") ; //IDMEDCASE
		out.print("<td>") ;	//Беженец
		out.print(wqr.get11());
		out.print("</td>") ;
		out.print("<td>") ;	//3
		out.print(wqr.get9()) ;out.print("</td>") ;
		
		out.print("<td>") ;	//4
		out.print(wqr.get10()) ;out.print("</td>") ;
		out.print("<td>") ;out.println(wqr.get2()) ;out.print("</td>") ;	//5
		out.print("<td>") ;out.println(wqr.get3()) ;out.print("</td>") ;	//6
		out.print("<td>") ;out.print(wqr.get8()!=null?wqr.get8():"");out.print("</td>") ;	//7
		out.print("<td>") ;out.print(wqr.get7()!=null?wqr.get7():"");out.print("</td>") ;	//8
		out.print("<td>") ;	//4 */
	 	try {
			String[] arr =HospitalMedCaseServiceJs.getDataByReferencePrintNotOnlyOMS(Long.valueOf(wqr.get1().toString()),"HOSP",false,"OTHER", request).split("&");
			for (int j=0;j<arr.length;j++)
			{
				if (arr[j].startsWith("render")) {
					String[] arrPrice =arr[j].split("%23"); 
					String price = arrPrice[0].substring(7,arrPrice[0].length());
					wqr.set12(price+" руб.");
					listStac.set(i,wqr);
					 /* out.print(" ");out.print(price);out.print(" руб"); */ 
				}
			}
		} catch (Exception e) {
			wqr.set12("---");
			listStac.set(i,wqr);	
		}
/* 		 out.print("</td>") ;	//4
		out.println("</tr>") ; */ 
	}
	request.setAttribute("list_stac", listStac);
/*  	out.println("</table>") ;
	out.println("</td></tr>") ;  */
  	} else {
  		 /* out.print("Нет данных");  */
  	}
  	%>
  	  <msh:table viewUrl="entityShortView-stac_ssl.do" 
 name="list_stac"
 action="entityView-stac_ssl.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Пациент" property="4" />
	      <msh:tableColumn property="9" columnName="Дата рождения"/>
	      <msh:tableColumn property="11" columnName="Беженец"/>
	      <msh:tableColumn columnName="Дата поступления" property="2" />
	      <msh:tableColumn columnName="Дата выписки" identificator="false" property="3" />
	      <msh:tableColumn property="6" columnName="Отделение"/>
	      <msh:tableColumn property="7" columnName="Поток обслуживания"/>
	      <msh:tableColumn property="8" columnName="Результат лечения"/>
	      <msh:tableColumn property="10" columnName="Диагноз"/>
	      <msh:tableColumn property="12" columnName="Объем финансирования"/>
	    </msh:table>	
  	<msh:section title="Отказы от госпитализаций">

  	
	    <ecom:webQuery name="list_stac1" maxResult="1000" nativeSql="select m.id
	    
	    ,to_char(m.dateStart,'DD.MM.YYYY') as dateStart
	    ,to_char(m.dateFinish,'DD.MM.YYYY') as dateFinish
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,ss.code as sscode 

from medcase m 
left join patient p on p.id=m.patient_id
left join address2 a on a.addressid=p.address_addressid
${groupSqlAdd}
left join Omc_Oksm vn on vn.id=p.nationality_id
left join statisticstub ss on ss.id=m.statisticStub_id
left join MisLpu ml on ml.id=m.department_id
left join VocServiceStream vss on vss.id=m.serviceStream_id
where  m.DTYPE='HospitalMedCase'
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and (m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is not null
${emergencySql} ${departmentSql} 
${serviceStreamSql}
${nationalitySql} ${regionSql} ${patientSql}  ${ageSql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-stac_ssl.do" 
 name="list_stac1"
 action="entityView-stac_ssl.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Пациент" property="4" />
	      <msh:tableColumn columnName="Дата обращения" property="2" />
	    </msh:table>
  	</msh:section>  	



    <% 
    //окончание реестра
    } else if (typeView.equals("2")) {
	%>

      	<msh:section title="Поликлиника">

      	
    	    <ecom:webQuery name="list_yes" maxResult="1000" nativeSql="select 
    	    p.id as pid
    	    ,count(distinct m.id)

    	    ,p.lastname||' '||p.firstname||' '||p.middlename as fio,to_char(p.birthday,'DD.MM.YYYY') as birthday
    	    ,vwfe.name||' '||pe.lastname as pefio
    	    ,vn.name as vnname
    	    ,a.fullname
    	    ,list(distinct mkb.code) as mkbcode
    	    ,vss.name
    from medcase m 
    left join patient p on p.id=m.patient_id
    left join address2 a on a.addressid=p.address_addressid
${groupSqlAdd}
    left join Omc_Oksm vn on vn.id=p.nationality_id
    left join WorkFunction wfe on wfe.id=m.workFunctionExecute_id
    left join Worker we on we.id=wfe.worker_id
    left join MisLpu ml on ml.id=we.lpu_id
    left join Patient pe on pe.id=we.person_id
    left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
    left join VocVisitResult vvr on vvr.id=m.visitResult_id
    left join VocServiceStream vss on vss.id=m.serviceStream_id
    left join Diagnosis d on d.medcase_id=m.id
    left join Vocidc10 mkb on mkb.id=d.idc10_id
    where  m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
    and (m.DTYPE='Visit' or m.DTYPE='ShortMedCase')  
    and (m.noActuality is null or m.noActuality='0')
    ${emergencySql} ${departmentWFSql}
    ${serviceStreamSql}
     ${nationalitySql} ${regionSql} ${patientSql}
    group by p.id,p.lastname,p.firstname,p.middlename,p.birthday
    	    ,vwfe.name,pe.lastname , vn.name,a.fullname,vss.name
    order by p.lastname,p.firstname,p.middlename"/>
    <msh:table name="list_yes" action="entityView-mis_patient.do"
    	viewUrl="entityShortView-mis_patient.do" 
    	idField="1">
    	      <msh:tableColumn columnName="№" identificator="false" property="sn" guid="270ae0dc-e1c6-45c5-b8b8-26d034ec3878" />
    	      <msh:tableColumn columnName="Пациент" property="3" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
    	      <msh:tableColumn columnName="Дата рождения" property="4" guid="315cb6eb-3db8-4de5-8b0c-a49e3cacf382" />
    	      <msh:tableColumn columnName="Кол-во обращения" property="2" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
    	      <msh:tableColumn columnName="Диагноз" property="8" guid="b3e2fb6e-53b6-4e69-8427-2534cf1edcca" />
    	      <msh:tableColumn columnName="Гражданство" identificator="false" property="6" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
    	      <msh:tableColumn columnName="Адрес проживания" identificator="false" property="7" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
    	      <msh:tableColumn columnName="Специалист" identificator="false" property="5" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
    	      <msh:tableColumn columnName="Поток обслуживания" property="9"/>
    	    </msh:table>
      	</msh:section>
      	<msh:section title="Стационар">

      	
    	    <ecom:webQuery name="list_stac" maxResult="1000" nativeSql="select
    	    p.id as pid
    	    ,p.lastname||' '||p.firstname||' '||p.middlename as fio
    	    ,to_char(p.birthday,'DD.MM.YYYY') as birthday
    	    
    	    ,list(ml.name) as mlname
    	    ,list(ss.code) as sscode
    	    ,list(vss.name) as vssname
    	    ,vn.name as vnname
    	    ,a.fullname as afullname
    from medcase m 
    left join medcase smo on smo.id=m.parent_id
    left join patient p on p.id=m.patient_id
    left join address2 a on a.addressid=p.address_addressid
    ${groupSqlAdd}
    left join Omc_Oksm vn on vn.id=p.nationality_id
    left join statisticstub ss on ss.id=smo.statisticStub_id
    left join mislpu ml on ml.id=m.department_id
    left join VocServiceStream vss on vss.id=smo.serviceStream_id
    where  
    m.DTYPE='DepartmentMedCase' and m.dateFinish between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
    and (m.noActuality is null or m.noActuality='0')
    and smo.deniedHospitalizating_id is null
    ${emergencySql} ${departmentSql} 
    ${serviceStreamSql}
    ${nationalitySql} ${regionSql} ${patientSql}
    group by p.id,p.lastname,p.firstname,p.middlename 
    ,p.birthday,vn.name ,a.fullname
    order by p.lastname,p.firstname,p.middlename"/>
    <msh:table viewUrl="entityShortView-stac_ssl.do" 
     name="list_stac"
     action="entityView-mis_patient.do" idField="1" >
    	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
    	      <msh:tableColumn columnName="№№ стат.карт" property="5" />
    	      <msh:tableColumn columnName="Пациент" property="2" />
    	      <msh:tableColumn columnName="Дата рождения" property="3" />
    	      <msh:tableColumn columnName="Гражданство" identificator="false" property="7" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
    	      <msh:tableColumn columnName="Адрес проживания" identificator="false" property="8" guid="3145e72a-cce5-4994-a507-b1a81efefdfe" />
    	      <msh:tableColumn columnName="Отделения" identificator="false" property="4" />
    	      <msh:tableColumn property="6" columnName="Потоки обслуживания"/>
    	    </msh:table>
      	</msh:section>
      	<msh:section title="Отказы от госпитализаций">

      	
    	    <ecom:webQuery name="list_stac1" maxResult="1000" nativeSql="select p.id
    	    ,p.lastname||' '||p.firstname||' '||p.middlename as fio
    	    ,to_char(p.birthday,'DD.MM.YYYY') as birthday
    	    ,vn.name as vnname
    	    ,a.fullname as afullname
    	    ,list(to_char(m.datestart,'dd.mm.yyyy')||vdh.name) as denhosp
    	    
    from medcase m 
    left join patient p on p.id=m.patient_id
    left join address2 a on a.addressid=p.address_addressid
    ${groupSqlAdd}
    left join Omc_Oksm vn on vn.id=p.nationality_id
    left join statisticstub ss on ss.id=m.statisticStub_id
    left join MisLpu ml on ml.id=m.department_id
    left join VocServiceStream vss on vss.id=m.serviceStream_id
    left join VocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id
    where  m.DTYPE='HospitalMedCase'
    and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
    and (m.noActuality is null or m.noActuality='0')
    and m.deniedHospitalizating_id is not null
    ${emergencySql} ${departmentSql} 
    ${serviceStreamSql}
    ${nationalitySql} ${regionSql} ${patientSql}
    group by p.id,p.lastname,p.firstname,p.middlename
    	    ,p.birthday ,vn.name ,a.fullname
    order by p.lastname,p.firstname,p.middlename"/>
    <msh:table viewUrl="entityShortView-mis_patient.do" 
     name="list_stac1" 
     action="entityView-mis_patient.do" idField="1" >
    	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
    	      <msh:tableColumn columnName="Пациент" property="2" />
    	      <msh:tableColumn columnName="Дата рождения" property="3" />
    	      <msh:tableColumn columnName="Гражданство" property="4" />
    	      <msh:tableColumn columnName="Адрес" property="5" />
    	      <msh:tableColumn columnName="Дата и причина отказов" property="6" />
    	    </msh:table>
      	</msh:section>
      	  	
	<%  
	
    } else {
    	// начало свода
    	%>
    	
    <msh:section>
<ecom:webQuery nameFldSql="sql_journal_swod" name="journal_swod" nativeSql="
select ${groupId}||${departmentSqlId}||${nationalitySqlId}||${serviceStreamSqlId} as idparam,${groupSql} as vnname
,count(*) as cntAll
,count(distinct case when (m.dtype='Visit' or m.dtype='ShortMedCase') then m.id else null end) as polic
,count(distinct case when (m.dtype='Visit' or m.dtype='ShortMedCase') and vss.code='CHARGED' then m.id else null end) as policCh
,count(distinct case when (m.dtype='Visit' or m.dtype='ShortMedCase') and vss.code!='CHARGED' then m.id else null end) as policOther
,list(distinct case when (m.dtype='Visit' or m.dtype='ShortMedCase') and vss.code!='CHARGED' then vss.name else null end) as policOtherServiceStream

,count(distinct case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') then m.id else null end) as hospitAll
,sum(case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') then case when smo.dateFinish=smo.dateStart then 1 else smo.dateFinish-smo.dateStart end else null end) as hospitDaysAll
,count(distinct case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code='CHARGED' then m.id else null end) as hospitAllCh
,sum( case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code='CHARGED' then case when smo.dateFinish=smo.dateStart then 1 else smo.dateFinish-smo.dateStart end else null end) as hospitAllDaysCh
,count(distinct case when m.dtype='DepartmentMedCase' 
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then m.id else null end) as cntNoChan
,list(distinct case when m.dtype='DepartmentMedCase'  
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then vss.name else null end) as listVssNoChan
,case when 
count(distinct case when m.dtype='DepartmentMedCase' 
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then m.id else null end)>0
then 
cast(round(1.0*sum(case when m.dtype='DepartmentMedCase' 
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then smo.dateFinish-smo.dateStart+1 else null end)
/
count(distinct case when m.dtype='DepartmentMedCase'  
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then m.id else null end)
,2) as numeric)
else null 
end as srDaysNoCh
,count(distinct case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP' then m.id else null end) as hospitDn
,sum(case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP' then smo.dateFinish-smo.dateStart+1 else null end) as hospitDaysDn
,count(distinct case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP'and vss.code='CHARGED' then m.id else null end) as hospitDnCh
,sum(case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP'and vss.code='CHARGED' then smo.dateFinish-smo.dateStart+1 else null end) as hospitDnDaysCh
,count(distinct case when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is not null then m.id else null end) as hospitDenied
from medcase m
left join medcase smo on smo.id=m.parent_id
left join patient p on p.id=m.patient_id
left join address2 a on a.addressid=p.address_addressid
left join Omc_Oksm vn on vn.id=p.nationality_id
left join VocHospType vht on vht.id=m.hospType_id
left join VocServiceStream vss on vss.id=m.serviceStream_id
${groupSqlAdd}
left join WorkFunction wfe on wfe.id=m.workFunctionExecute_id 
left join Worker we on we.id=wfe.worker_id
left join MisLpu mlV on mlV.id=we.lpu_id
left join MisLpu ml on ml.id=m.department_id

where ((m.dtype='Visit' or m.dtype='ShortMedCase') 
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
${departmentWFSql}

or m.dtype='DepartmentMedCase' ${departmentSql} 
and m.dateFinish between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')

or m.dtype='HospitalMedCase' ${departmentSql} 
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and m.deniedHospitalizating_id is not null
) 
and (m.noActuality is null or m.noActuality='0') ${emergencySql}
 ${serviceStreamSql} ${patientSql} ${nationalitySql} ${regionSql} 
group by ${groupSqlId},${groupSql}

" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 


    <msh:sectionTitle>Период с ${param.beginDate} по ${param.finishDate}${emergencyInfo}
    <form action="print-report_categoryForeignNationals.do" method="post" target="_blank">
    <input type='hidden' name="sqlText1" id="sqlText1" value="${sql_journal_swod}"> 
    <input type='hidden' name="sqlText2" id="sqlText2" value="${sql_journal_swod1}">
    <input type='hidden' name="sqlCount" id="sqlCount" value="1">
    <input type='hidden' name="sqlInfo1" id="sqlInfo1" value="${param.beginDate}-${param.finishDate}${emergencyInfo}.">
    <input type='hidden' name="sqlInfo2" id="sqlInfo2" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printManyNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table
         name="journal_swod" action="journal_nationality_ukraine.do?beginDate=${param.beginDate}&finishDate=${param.finishDate}&typeView=1&typeGroup=${typeGroup}&typePatient=${typePatient}&typeEmergency=${typeEmergency}" idField="1" noDataMessage="Не найдено">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="4" class="rightBold">Амбулаторно-поликлиническая помощь</th>
                <th colspan="7" class="rightBold">Стационарная медицинская помощь</th>
                <th colspan="4" class="rightBold">Стационарно-замещающая медицинская помощь</th>
                <th colspan="1" />
              </tr>
            </msh:tableNotEmpty>            
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn columnName="Общее кол-во" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="всего" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="5" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. др. потоки" property="6" isCalcAmount="true"/>
            <msh:tableColumn columnName="потоки обс." property="7"/>
            <msh:tableColumn columnName="всего" property="8" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="9" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="10" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="11" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. др. потоки" property="12" isCalcAmount="true"/>
            <msh:tableColumn columnName="потоки обс." property="13"/>
            <msh:tableColumn columnName="сред. к.дней" property="14"/>
            <msh:tableColumn columnName="всего" property="15" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="16" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="17" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="18" isCalcAmount="true"/>
            <msh:tableColumn columnName="отказы от госп." property="19" isCalcAmount="true"/>
        </msh:table>
    </msh:sectionContent>
    
    </msh:section>    	
    	<%
    	//окончание свода
    }
    	} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
   <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
  	<script type="text/javascript">
  	checkFieldUpdate('typeView','${typeView}',2) ;
  	checkFieldUpdate('typeGroup','${typeGroup}',2) ;
  	//checkFieldUpdate('typeDate','${typeDate}',2) ;
  	//checkFieldUpdate('typeUser','${typeUser}',3) ;
    checkFieldUpdate('typePatient','${typePatient}',1) ;
    checkFieldUpdate('typeAge','${typeAge}',3) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',4) ;
    /* function fillPrice () {
    	var tables = document.getElementsByTagName
    } */
    function printReference() {
    	HospitalMedCaseService.getDataByReference(
    		'${param.id}','SPO',{
    			callback: function(aResult) {
    				if (aResult!=null) {
    					alert("aResult="+aResult);
    				//	window.location.href = "print-doc_reference.do?medCase=${param.id}&m=refenceSMO&s=VisitPrintService"+aResult;
    					
    				}
    			}, errorHandler: function(aMessage) {
    				if (aMessage!=null) {
    					alert("ERROR="+aMessage);
    				} else{
    			    	alert("СПРАВКА РАСПЕЧАТЫВАЕТСЯ ТОЛЬКО ПО ВЫПИСАННЫМ ОМС БОЛЬНЫМ!!!") ;
    				}
    			}
    		
    		}
    	);
    	//print-discharge_reference.do?m=printReference&s=HospitalPrintService
    }
    
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
  		for(i=0; i < radioGrp.length; i++){
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