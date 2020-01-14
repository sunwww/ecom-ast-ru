<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Journals">Журнал отказов от госпитализаций пациентов без диагноза</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
         <msh:sideMenu>
			<msh:sideLink name="Список отчетов" 
				action="/javascript:getDefinition('riams_journals.do?short=Short',null)" styleId="viewShort" />
         </msh:sideMenu>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typeView =ActionUtil.updateParameter("Report_CloseSpo","typeView","1", request) ;
	String typeMode =ActionUtil.updateParameter("journal_denied_without_diagnosis","typeMode","1", request) ;
	String typeDiag =ActionUtil.updateParameter("journal_denied_without_diagnosis","typeDiag","2", request) ;


  %>
    <msh:form action="/stac_journal_denied_without_diagnosis.do" defaultField="beginDate" disableFormDataConfirm="true" method="POST" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel>
		<msh:row>
        	<msh:separator label="Выбор режима" colSpan="7" />
		</msh:row>
		<msh:row>
			<td class="label" title="Режим отображения (typeMode)" colspan="1"><label for="typeModeName" id="typeModeLabel">Режим:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode() ;">
	        	<input type="radio" name="typeMode" value="1333"> по отделению(ям)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode() ;" colspan="2">
	        	<input type="radio" name="typeMode" value="2" > по дневникам
	        </td>

		</msh:row>

		</msh:panel>
	<msh:panel styleId="pnlDiary">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      		<msh:row>
			<td class="label" title="Поиск по параметрам (typeDiag)" colspan="1"><label for="typeModeName" id="typeModeLabel">Диагнозы (для реестра):</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDiag" value="1"> есть
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDiag" value="2" > нет
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDiag" value="3" > все
	        </td>
		</msh:row>
				<msh:row>
			<td class="label" title="Отобразить (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Поиск:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1"> свод
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2" > реестр
	        </td>
	        
		</msh:row>
		<msh:row>
			<%--<ecom:oneToManyOneAutocomplete label="Раб.функции для выборки" vocName="vocWorkFunction" property="vocWorkFunctions" colSpan="5"/>--%>
			<msh:autoComplete label="Раб.функция для выборки" vocName="vocWorkFunction" property="vocWorkFunctions" size="100"/>
		</msh:row>
		<msh:row>
			<msh:autoComplete property="vocWorkFunction" horizontalFill="true" vocName="vocWorkFunction" label="Раб.функция для генерации" fieldColSpan="5"/>
		</msh:row>
		<msh:row>
			<msh:textArea property="filterMkb" label="Фильтр МКБ" fieldColSpan="5"/>
		</msh:row>
	</msh:panel>
    <msh:panel styleId="pnlDepartment">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>

        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="5"
        	label="ЛПУ" horizontalFill="true" vocName="vocLpuHospOtdAll" size="100"/>
        </msh:row>
        </msh:ifInRole>
    </msh:panel>
    <msh:panel styleId="pnlDefault">
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="5"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" />
	        <msh:textField property="dateEnd" label="по" />
			<td>
	            <input type="submit" onclick="find()" value="Найти" />
	            <msh:ifInRole roles="/Policy/Poly/Ticket/GenerateByDenied">
	            <input type="button" onclick="createNewVisitByDenied()" value="Генерировать" />
	            </msh:ifInRole>
	          </td>
        </msh:row>
    </msh:panel>
    <msh:panel styleId="pnlGeneration">
		<msh:autoComplete property="department1" fieldColSpan="5"
        	label="ЛПУ" horizontalFill="true" vocName="vocLpuHospOtdAll" size="100"/>
        <msh:row>
			<msh:autoComplete property="group" horizontalFill="true" vocName="funcMedServiceRoom" label="ЭКСТР. ПУНКТ" fieldColSpan="5"/>
		</msh:row>
		<msh:row>
			<input type="submit" onclick="find()" value="Найти" />
	        <td>
	          <msh:ifInRole roles="/Policy/Poly/Ticket/GenerateByDenied">
	            <input type="button" onclick="createNewSpecialist()" value="Генерировать" />
	          </msh:ifInRole>
	          </td>
        </msh:row>
    </msh:panel>
    
    </msh:form>
    <script type="text/javascript">
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeMode','${typeMode}',2) ;
    checkFieldUpdate('typeDiag','${typeDiag}',1) ;
    function checkFieldUpdate(aField,aValue,aDefaultValue) {
       	eval('var chk =  document.forms[0].'+aField) ;
       	var max = chk.length ;
       	aValue=+aValue ;
       	if (aValue==0 || (aValue>max)) {aValue=+aDefaultValue}
       	if (aValue==0 || (aValue>max)) {
       		chk[max-1].checked='checked' ;
       	} else {
       		chk[aValue-1].checked='checked' ;
       	}
    }
    function checkMode() {
    	var chk =  document.forms[0].typeMode ;
 	   if (chk[0].checked) {
		   showTable("pnlDepartment", true ) ;
		   showTable("pnlDiary", false ) ;
		   showTable("pnlDefault", true ) ;
		   showTable("pnlGeneration", false ) ;
    	} else if (chk[1].checked){
 		   showTable("pnlDepartment", false ) ;
		   showTable("pnlDiary", true ) ;
		   showTable("pnlDefault", true ) ;
		   showTable("pnlGeneration", false ) ;
    	} else {
    		showTable("pnlDepartment", false ) ;
 		   showTable("pnlDiary", false ) ;
 		   showTable("pnlDefault", false ) ;
 		   showTable("pnlGeneration", true ) ;
    	}
    }
    function showTable(aTableId, aCanShow ) {
		try {
			$(aTableId).style.display = aCanShow ? 'table' : 'none' ;
		} catch (e) {
			try{
			$(aTableId).style.display = aCanShow ? 'block' : 'none' ;
			}catch(e) {}
		}	
	}
	checkMode() ;
    </script>
    <%
    if (typeMode.equals("3") && request.getParameter("department1")!=null && !request.getParameter("department1").equals("0")  && !request.getParameter("department1").equals("")) {
    %>
    <msh:section>
    <msh:sectionTitle>Список соответствий сотрудников по экстренным пунктам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="true" name="datelist" nativeSql="
    select wf.id,vwf.name, wp.lastname, list(case when wfg1.emergency='1' then wfg1.groupname else null end) from workfunction wf
left join worker w on w.id=wf.worker_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
left join patient wp on wp.id=w.person_id
left join worker wg on wg.person_id=w.person_id
left join workfunction wfg on wfg.worker_id=wg.id 
left join workfunction wfg1 on wfg1.id=wfg.group_id 
where w.lpu_id='${param.department1}'
group by wf.id,vwf.name,wp.lastname
" 
	/>
    <msh:table name="datelist" printToExcelButton="сохранить в Excel"
    action="entityParentView-work_personalWorkFunction.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Должность" property="2" />
      <msh:tableColumn columnName="Фамилия специалиста" property="3" />
      <msh:tableColumn columnName="Кабинет экст. помощи" property="4" />
    </msh:table>
    </msh:sectionContent>
    </msh:section> 
    <%
    }
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String finishDate = request.getParameter("dateEnd") ;
    	if (finishDate==null||finishDate.equals("")) {finishDate=date;}
    		request.setAttribute("finishDate", finishDate) ;
    	request.setAttribute("beginDate", date) ;
        request.setAttribute("isReportBase", ActionUtil.isReportBase(date,finishDate,request));
    	/*
    	if (typeView!=null && typeView.equals("1")) {
    		request.setAttribute("additionSpoSql", " and (select count(spo1.id) from medcase spo1 left join WorkFunction owf1 on owf1.id=spo1.ownerFunction_id left join Worker ow1 on ow1.id=owf1.worker_id where spo.patient_id=spo1.patient_id and spo1.dtype='PolyclinicMedCase' and owf.workFunction_id=owf1.workFunction_id and spo1.dateFinish between to_date('"+date+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy') and ow.lpu_id=ow1.lpu_id and spo1.id!=spo.id)>1") ;
    	} else if (typeView!=null && typeView.equals("2")) {
    		request.setAttribute("additionSpoSql", " and (select count(spo1.id) from medcase spo1 left join WorkFunction owf1 on owf1.id=spo1.ownerFunction_id left join Worker ow1 on ow1.id=owf1.worker_id where spo.patient_id=spo1.patient_id and spo1.dtype='PolyclinicMedCase' and owf.workFunction_id=owf1.workFunction_id and spo1.dateStart between to_date('"+date+"','dd.mm.yyyy') and to_date('"+finishDate+"','dd.mm.yyyy') and ow.lpu_id=ow1.lpu_id and spo1.dateFinish is null)>1") ;
    	} else {
    		
    	}*/
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.trim().equals("") && !dep.equals("0")) {
    		request.setAttribute("departmentSql", " and sls.department_id="+dep) ;
    		request.setAttribute("department",dep) ;
    	} else {
    		request.setAttribute("department","0") ;
    	}
    	/*String spec = (String)request.getParameter("spec") ;
    	if (spec!=null && !spec.equals("") && !spec.equals("0")) {
    		request.setAttribute("workFunctionSql", " and vis.workFunctionExecute_id="+spec) ;
    		request.setAttribute("workFunction",spec) ;
    	} else {
    		request.setAttribute("workFunction","0") ;
    	}*/
    	String servStream = request.getParameter("serviceStream") ;
    	if (servStream!=null && !servStream.equals("") && !servStream.equals("0")) {
    		request.setAttribute("serviceStreamSql", " and sls.serviceStream_id="+servStream) ;
    		request.setAttribute("serviceStream", servStream) ;
    	} else {
    		request.setAttribute("serviceStream", "0") ;
    	}
		String vwfid = request.getParameter("vwfid") ;
		if (vwfid!=null && !vwfid.equals("") && !vwfid.equals("0")) {
			request.setAttribute("vocWorkFunctionsSql", " and vwf.id="+vwfid) ;
			request.setAttribute("vocWorkFunctionsSqlId", vwfid) ;
		} else {
			request.setAttribute("vocWorkFunctionsSqlId", "0") ;
		}
    	%>
    	
    	
    	<%
        	String login = LoginInfo.find(request.getSession(true)).getUsername() ;
        	request.setAttribute("login", login) ;
        %>
        <ecom:webQuery name="infoByLogin"
        maxResult="1" nativeSql="
        select wf.id,w.lpu_id,case when wf.isAdministrator='1' then '1' else null end as isAdmin
        from SecUser su
        left join WorkFunction wf on su.id=wf.secUSer_id
        left join Worker w on wf.worker_id=w.id
        where su.login='${login}'
        "
        />
        <%
        if (typeMode.equals("1")) {
        	boolean isViewAllDepartment=RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments",request) ;
        	List list= (List)request.getAttribute("infoByLogin");
	    	WebQueryResult wqr = list.size()>0?(WebQueryResult)list.get(0):null ;
	    	//WebQueryResult wqr = null ;
        	String department = request.getParameter("department") ;
        	String curator = request.getParameter("specialist") ;
        	String workFunc = wqr!=null?""+wqr.get1():"0" ;
        	boolean isBossDepartment=(wqr!=null&&wqr.get3()!=null)?true:false ;

 
        	int type=0 ;
        	if (department!=null && !department.equals("0")&& !department.equals("") && (isViewAllDepartment || isBossDepartment)) {
        		type=2 ;
       		} else if (isViewAllDepartment) {
       			type=1 ;
       		} else if (wqr!=null) {
       		
       			if (isBossDepartment) {
       				type=2 ;
       				if ((wqr.get2()!=null&&!wqr.get2().equals("0")&&!wqr.get2().equals(""))) {
       					department=""+wqr.get2() ;
       				} else {
       					department="0" ;
       				}
       				
       			} else {
       				type=2 ;
       				curator=workFunc ;
       				department=""+wqr.get2() ;
       			}
       		} 
       		request.setAttribute("department", department) ;
       		//request.setAttribute("curator", curator) ;
       		//out.print("Type="+type) ;
       		//out.print(" isViewAllDepartment="+isViewAllDepartment) ;
       		//out.print(" isBossDepartment="+isBossDepartment) ;
       		//out.print(" curator="+curator) ;
       		//out.print(" department="+department) ;
       	%>
        	    
    <%if (type==1) { %>
    <msh:section>
    <msh:sectionTitle>Свод по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="datelist" nativeSql="
    select ml.id||'&department='||ml.id,ml.name ,count(distinct sls.id) as cntSls,count(distinct case when diag.id is null then sls.id else null end) as notdiag
	 from MedCase sls
left join mislpu ml on ml.id=sls.department_id
left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)
left join vocidc10 mkb on mkb.id=diag.idc10_id
where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is not null
and sls.medicalAid='1'
 ${serviceStreamSql}
	and ml.emergencyCabinet>0
	group by ml.id,ml.name 
	order by ml.name" 
	/>
    <msh:table name="datelist" printToExcelButton="сохранить в Excel"
    viewUrl="stac_journal_denied_without_diagnosis.do?short=Short&typeView=${typeView}&serviceStream=${param.serviceStream}&dateBegin=${beginDate}&dateEnd=${finishDate}"
    action="stac_journal_denied_without_diagnosis.do?typeView=${typeView}&serviceStream=${param.serviceStream}&dateBegin=${beginDate}&dateEnd=${finishDate}" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во отказов" property="3" isCalcAmount="true" />
      <msh:tableColumn columnName="из них без диагноза" property="4" isCalcAmount="true" />
    </msh:table>
    </msh:sectionContent>
    </msh:section> 
    <% } %>

         <%if (type==2 )  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр пациентов</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="datelist" nativeSql="
select sls.id as slsid, to_char(sls.datestart,'dd.mm.yyyy') as deniedDate
,p.lastname||' '||p.firstname||' '||p.middlename as fiopatient
,to_char(p.birthday,'dd.mm.yyyy') as birthday
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as worker
,case when mcmp.policies_id is not null then 'Да' else '' end  as policies
, ml.name as f7_depname
 from MedCase sls
left join patient p on p.id=sls.patient_id
left join workfunction wf on wf.id=sls.ownerFunction_id
left join worker w on w.id=wf.worker_id
left join patient wp on wp.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id
left join workfunction dwf on dwf.id=diag.medicalWorker_id
left join worker dw on dw.id=dwf.worker_id
left join patient dwp on dwp.id=dw.person_id
left join vocworkfunction dvwf on dvwf.id=dwf.workFunction_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join mislpu ml on ml.id=sls.department_id
where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is not null
and sls.department_id='${department}' and sls.medicalAid='1'
 ${serviceStreamSql}
and diag.id is null
order by sls.dateStart,p.lastname,p.firstname,p.middlename
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" printToExcelButton="сохранить в Excel"
    viewUrl="entityParentView-stac_sslAdmission.do?short=Short"
    action="entityParentView-stac_sslAdmission.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Дата обращения" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Деж. врач" property="5" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Наличие страх. документов" property="6"/>
      <msh:tableColumn columnName="Отделение" property="7"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }
        } else if (typeMode.equals("2")) {
        	StringBuilder paramSql= new StringBuilder() ;
       //   	StringBuilder paramHref= new StringBuilder() ;
          	//--old---paramSql.append(" ").append(ActionUtil.setParameterFilterSql("department", "sloa.department_id", request)) ;
          	//paramSql.append(" ").append(ActionUtil.setParameterManyFilterSql("vocWorkFunctions","vocWorkFunctions", "vwf.id", request)) ;
          	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("vocWorkFunctions","vocWorkFunctions", "vwf.id", request)) ;

          	//paramSql.append(" ").append(ActionUtil.setParameterFilterSql("vocWorkFunction", "vwf.id", request)) ;
          	paramSql.append(" ").append(ActionUtil.setParameterFilterMkb("filterMkb","filterMkb", "mkb.code", request)) ;
          	 if (typeDiag.equals("1")) {
          		request.setAttribute("diagSql", " and diag.id is not null") ;
          	 } else if (typeDiag.equals("2")) {
           		request.setAttribute("diagSql", " and diag.id is null") ;
          		 
          	 }
         %>
         
    <%if (typeView.equals("1")) { %>
    <msh:section>
    <msh:sectionTitle>Свод по дневникам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="datelist" nameFldSql="datelist_sql" nativeSql="
    select '&vwfid='||coalesce(vwf.id,0),vwf.name as vwfname
    ,count(distinct sls.id) as cntSls
    ,count(distinct case when diag.id is null then sls.id else null end) as notdiag
    ,count(distinct case when diag.id is not null ${filterMkbSql} then sls.id else null end) as diagFilter
	 from MedCase sls
	 left join diary d on d.medcase_id=sls.id
left join mislpu ml on ml.id=sls.department_id
left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join workFunction wf on wf.id=d.specialist_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is not null ${filterMkbSql}
and sls.medicalAid='1'
 ${vocWorkFunctionsSql}
 ${serviceStreamSql}
	${diagSql}
	group by vwf.id,vwf.name
	order by vwf.name"
	/>
    <msh:table name="datelist" printToExcelButton="сохранить в Excel"
    viewUrl="stac_journal_denied_without_diagnosis.do?short=Short&typeView=2&typeMode=${typeMode}&serviceStream=${param.serviceStream}&dateBegin=${beginDate}&dateEnd=${finishDate}"
    action="stac_journal_denied_without_diagnosis.do?typeView=2&typeMode=${typeMode}&serviceStream=${param.serviceStream}&dateBegin=${beginDate}&dateEnd=${finishDate}" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во отказов" property="3" isCalcAmount="true" />
      <msh:tableColumn columnName="из них без диагноза" property="4" isCalcAmount="true" />
      <msh:tableColumn columnName="С подходящим диагнозом" property="5" isCalcAmount="true" />
    </msh:table>${datelist_sql}
    </msh:sectionContent>
    </msh:section>
    <% } %>

         <%if (typeView.equals("2"))  {	%>
    <msh:section>
    <msh:sectionTitle>Реестр пациентов</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="datelist" nameFldSql="datelist_sql" nativeSql="
select distinct sls.id as slsid, to_char(sls.datestart,'dd.mm.yyyy') as deniedDate
,p.lastname||' '||p.firstname||' '||p.middlename as fiopatient
,to_char(p.birthday,'dd.mm.yyyy') as birthday
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as worker
,case when mcmp.policies_id is not null then 'Да' else '' end  as policies
,case when diag.id is not null ${filterMkbSql} then mkb.code || ' ' ||dvwf.name||' '||dwp.lastname||' '||dwp.firstname||' '||dwp.middlename else null end as diag
,ml.name as f8_depName
 from MedCase sls
left join patient p on p.id=sls.patient_id
left join diary d on d.medcase_id=sls.id
left join workfunction wf on wf.id=d.specialist_id
left join worker w on w.id=wf.worker_id
left join patient wp on wp.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
left join diagnosis diag on diag.medcase_id=sls.id and diag.registrationType_id in (1,4)
left join medcase_medpolicy mcmp on mcmp.medcase_id=sls.id
left join workfunction dwf on dwf.id=diag.medicalWorker_id
left join worker dw on dw.id=dwf.worker_id
left join patient dwp on dwp.id=dw.person_id
left join vocworkfunction dvwf on dvwf.id=dwf.workFunction_id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join mislpu ml on ml.id=sls.department_id
where sls.dtype='HospitalMedCase' and sls.dateStart between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is not null
 and sls.medicalAid='1'
 ${vocWorkFunctionsSql}
 ${serviceStreamSql}
${diagSql}
order by to_char(sls.datestart,'dd.mm.yyyy'),p.lastname||' '||p.firstname||' '||p.middlename
    " guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" printToExcelButton="сохранить в Excel"
    viewUrl="entityParentView-stac_sslAdmission.do?short=Short"
    action="entityParentView-stac_sslAdmission.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Дата обращения" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Деж. врач (создал дневник)" property="5" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Наличие страх. документов" property="6"/>
      <msh:tableColumn columnName="Диагноз" property="7"/>
		<msh:tableColumn columnName="Отделение" property="8"/>
    </msh:table>${datelist_sql}
    </msh:sectionContent>
    </msh:section>
    <% } %>         
         <% 	
        }
        //}
    }else { %>
    	<i>Выберите параметры и нажмите найти </i>
    <% }   %>
    
    <script type='text/javascript'>
    //var typePatient = document.forms[0].typePatient ;
    //var typeDate = document.forms[0].typeDate ;
    //checkFieldUpdate('typeSwod','${typeSwod}',2,1) ;
    //checkFieldUpdate('typeDate','${typeDate}',2) ;
    
    //checkFieldUpdate('typePatient','${typePatient}',3,3) ;
    //checkFieldUpdate('typeStatus','${typeStatus}',2,2) ;

    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_journal_denied_without_diagnosis.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	//frm.action='stac_groupByBedFundList.do' ;
    }
    function createNewSpecialist() {
    	window.location = 'js-smo_spo-createNewEmergencySpec.do?group='+$('group').value
			 +"&department1="+$('department1').value ;
    }
    function createNewVisitByDenied() {
  		var ids = true ;
        if (ids) {
        	var chk =  document.forms[0].typeMode ;
      	   if (chk[0].checked) {
      	   	alert ("Генерация дневников по отделению отключена");
            //	window.location = 'js-smo_spo-createNewVisitByDenied.do?dateBegin='+$('dateBegin').value +'&dateEnd='+$('dateEnd').value+"&department="+$('department').value ;
      	   } else {
      		   
      		/* var obj = JSON.parse($('vocWorkFunctions').value) ;
      		
      		var sb ="" ;
      		for (var i = 0; i < obj.childs.length; i++) {
      			var child = obj.childs[i];
      			if (sb.length!=0) {
      				sb+="," ;
      			}
      			sb += child.value ;
      		} */
      		//alert(sb) ;
      		 window.location = 'js-smo_spo-createNewVisitByDeniedDiary.do?dateBegin='+$('dateBegin').value +'&dateEnd='+$('dateEnd').value
      				 +"&vocWorkFunction="+$('vocWorkFunction').value
      				 +"&vocWorkFunctions="+$('vocWorkFunctions').value
      				 +"&filterMkb="+$('filterMkb').value 
      				 ;
      	   }
        } else {
            alert("Не заданы все параметры");
        }
  	}
    </script>
  </tiles:put>
</tiles:insert>