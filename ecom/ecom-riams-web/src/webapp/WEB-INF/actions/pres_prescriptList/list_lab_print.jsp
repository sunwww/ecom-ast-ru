<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="LaboratoryJournal">Поиск лабораторных назначений</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    

        <msh:sideMenu>
                <tags:laboratory_menu currentAction="pres_print"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
    String typePrint=ActionUtil.updateParameter("Report_labor","typePrint","3", request) ; 
    String typeSmo=ActionUtil.updateParameter("Report_labor","typeSmo","3", request) ; 
    
    %>
    
            <msh:form action="/pres_lab_print.do" defaultField="number" method="GET">
                <msh:panel>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="prescriptType" fieldColSpan="4" horizontalFill="true" label="Тип назначения" vocName="vocPrescriptType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceSubType"  parentId="LABSURVEY" fieldColSpan="4" horizontalFill="true" label="Тип биоматериала" vocName="vocServiceSubTypeByCode"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="patient"  fieldColSpan="4" horizontalFill="true" label="Пациент" vocName="patient"/>
        </msh:row>
              <msh:row>
        <td class="label" title="Поиск по типу печати (typePrint)" colspan="1"><label for="typePrintName" id="typePrintLabel">Назначения:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typePrint" value="1">  распечатанные
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  >
        	<input type="radio" name="typePrint" value="2" >  нераспечатанные
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  >
        	<input type="radio" name="typePrint" value="3">  все
        </td>
      </msh:row>
              <msh:row>
        <td class="label" title="Поиск по типу печати (typeSmo)" colspan="1"><label for="typeSmoName" id="typeSmoLabel">Поиск назначений:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeSmo" value="1">  по дате назначения
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  >
        	<input type="radio" name="typeSmo" value="2" >  по дате выписки
        </td>
        
      </msh:row>
        				    <msh:row>
                        <msh:textField property="number" label="№ назначения" />
                        <msh:textField property="beginDate" label="от" />
                        <td><input type='submit' value='Реестр'>
                        
                        </td>
                    </msh:row>
                </msh:panel>
                <input type="hidden" name="sql_info" id="sql_info" value="
    select 
    d.id as pid
    ,  d.record as f2drecord
   , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
   , p.materialId||' ('||vsst.code||')' as f4material
   ,coalesce(vsst.name,'---') as f5vsstname
   ,pat.lastname ||' '||pat.firstname||' '||pat.middlename ||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as f6birthday
   , ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name as f7medServicies
   ,wp.lastname||' '||wp.firstname||' '||wp.middlename as f8fioworker
   ,iwp.lastname||' '||iwp.firstname||' '||iwp.middlename as f9intakefioworker
   ,to_char(mc.datestart,'dd.mm.yyyy')||' '||cast(mc.timeexecute as varchar(5)) as f10dtintake
   ,to_char(p.createdate,'dd.mm.yyyy')||' '||cast(p.createtime as varchar(5)) as f11planStartDate
   ,mc.id as f12mcid
   ,ml.name as m13lname
   ,ml.name||' '||pat.lastname ||' '||pat.firstname||' '||pat.middlename ||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as f14birthday
    from prescription p
    left join MedCase mc on mc.id=p.medcase_id
    left join Diary d on d.medcase_id=mc.id
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
    left join WorkFunction iwf on iwf.id=mc.workfunctionexecute_id
    left join Worker iw on iw.id=iwf.worker_id
    left join Patient iwp on iwp.id=iw.person_id
    left join MisLpu ml on ml.id=w.lpu_id
   where p.transferDate = to_date('{param.beginDate}','dd.mm.yyyy')
    and vst.code='LABSURVEY' 
    and mc.workFunctionExecute_id is not null and mc.dateStart is not null
    {sqlAdd}
    order by ml.name,pat.lastname,pat.firstname,pat.middlename
    ">
            </msh:form>
                <form action="print-pres_lab_prescript_print.do" method="post" target="_blank">
                <input type="hidden" name="sqlText" id="sqlText" value=""/> 
                
    	    <input type='hidden' name="sqlInfo" id="sqlInfo" value='Список пациентов за ${beginDate}-${endDate} по отделению ${lpu_name}'>
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService">
	    <input type='hidden' name="m" id="m" value="printGroupColumnNativeQuery">
	    <input type='hidden' name="groupField" id="groupField" value="14">
	    <input type='hidden' name="cntColumn" id="cntColumn" value="3">
	    <input type='hidden' name="updateId" id="updateId" value="1">
	    <input type='hidden' name="updateSql" id="updateSql" value="update diary set printdate=current_date,printtime=current_time where id=':id'">
	    <input type='hidden' name='next' id="next" value="pres_lab_print.do">
	    <input type="submit" value="Печать текстовый" onclick="print();this.form.action='print-pres_lab_print_txt.do'"> 
	    <input type="submit" value="Печать файл" onclick="print();this.form.action='print-pres_lab_print_odt.do';"> 
                           
    </form>
            <script type="text/javascript">
            checkFieldUpdate('typePrint','${typePrint}',1) ;
            checkFieldUpdate('typeSmo','${typeSmo}',1) ;
            function checkfrm() {
            	document.forms[1].submit() ;
            }
            function printId(aId,aDate,aFilePrefix) {
            	var addParam = " and p.id="+aId;
            	$('sqlText').value = $('sql_info').value.replace('{param.beginDate}',aDate)
            	$('sqlText').value=$('sqlText').value.replace('{sqlAdd}',addParam) ;
            	document.forms[1].action='print-pres_lab_print_'+aFilePrefix+'.do'
            	document.forms[1].submit() ;
            }
            function print() {
            	if ($('beginDate').value=="") {
                	$('beginDate').value=getCurrentDate() ;
                }
            	//$('sqlText').value =  ;
            	var addParam = "";
            	if (+$("department").value>0) addParam+=" and ml.id="+$("department").value ;
            	if (+$("prescriptType").value>0) addParam+=" and vpt.id="+$("prescriptType").value ;
            	if (+$("serviceSubType").value>0) addParam+=" and ms.serviceSubType_id="+$("serviceSubType").value ;
            	if (+$("patient").value>0) addParam+=" and pat.id="+$("department").value ;
            	if ($("number").value!="") {
            		addParam+=" and p.materialId='"+$("number").value+"'" ;
            		
            	}
            	var print = +getCheckedRadio(document.forms[0],"typePrint");
            	var smo = +getCheckedRadio(document.forms[0],"typeSmo");
            	if (print==1) {
            		addParam +=" and d.printDate is null" ;
            	} else if (print==2){
            		addParam +=" and d.printDate is not null" ;
            	}
            	$('sqlText').value = $('sql_info').value.replace('{param.beginDate}',$('beginDate').value)
            	$('sqlText').value=$('sqlText').value.replace('{sqlAdd}',addParam) ;
            	//checkfrm() ;
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
            if (request.getParameter("beginDate")!=null ) {
            	 StringBuilder sqlAdd = new StringBuilder() ;
            	
        		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocPrescriptType where id=:id"
        				, "тип назначения","prescriptType","vpt.id", request)) ;
        		sqlAdd.append(ActionUtil.getValueInfoById("select id, name from vocServiceSubType where id=:id"
        				, "биоматериал","serviceSubType","ms.serviceSubType_id", request)) ;
        		sqlAdd.append(ActionUtil.getValueInfoById("select id, lastname||' '||firstname||' '||middlename from patient where id=:id"
        				, "пациент","patient","pat.id", request)) ;
        		String number=request.getParameter("number") ;
        		if (number!=null &&!number.equals("")) {
            		sqlAdd.append(" and p.materialId='").append(number).append("'") ;
            		
            	}
        		 StringBuilder title = new StringBuilder() ;
        		title.append(request.getAttribute("departmentInfo"))
        			.append(" ").append(request.getAttribute("serviceSubTypeInfo")) 
        			.append(" ").append(request.getAttribute("prescriptTypeInfo")) 
        			.append(" ").append(request.getAttribute("patientInfo")) ;
        		if (number!=null &&!number.equals("")) {
            		title.append(" номер назначения: ").append(number).append("") ;
            		
            	}
        		if (typePrint!=null) {
        			if (typePrint.equals("1")) {
        				title.append(" назначения: распечатанные") ;
        				sqlAdd.append(" and d.printDate is null") ;
        			} else if (typePrint.equals("2")) {
        				title.append(" назначения: нераспечатанные") ;
        				sqlAdd.append(" and d.printDate is not null") ;
        			}
        		}
        		if (typePrint!=null) {
        			if (typePrint.equals("1")) {
        				title.append(" назначения: распечатанные") ;
        				sqlAdd.append(" and d.printDate is null") ;
        			} else if (typePrint.equals("2")) {
        				title.append(" назначения: нераспечатанные") ;
        				sqlAdd.append(" and d.printDate is not null") ;
        			}
        		}
        		if (typeSmo!=null && typeSmo.equals("1")) {
        			title.append(" по дате назначения") ;
        			request.setAttribute("dateInfo"," p.transferDate = to_date('"+request.getParameter("beginDate")+"','dd.mm.yyyy')") ;
        			sqlAdd.append(ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
            				, "отделение","department","ml.id", request));
                	if (request.getParameter("department")!=null) ;
	       		} else {
	       			ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
	        				, "отделение","department","ml.id", request);
	            	if (request.getParameter("department")!=null) {
	            		sqlAdd.append("select max(sloall.department_id) from medcase sloall where sloall.parent_id=(case when slo.dtype='HospitalMedCase' then slo.id ")
	            				.append(request.getParameter("beginDate"))
	            				.append(" when sls.dtype='HospitalMedCase' then sls.id else slo.id end)='")
	            				.append(request.getParameter("department"))
	            				.append("'");
	            	}
        			title.append(" по дате выписки") ;
        			request.setAttribute("dateInfo"," (slo.dtype='HospitalMedCase' and slo.dateFinish = to_date('"+request.getParameter("beginDate")+"','dd.mm.yyyy' or sls.dtype='HospitalMedCase' and sls.dateFinish = to_date('"+request.getParameter("beginDate")+"','dd.mm.yyyy')") ;

        		}
            request.setAttribute("sqlAdd", sqlAdd.toString()) ;
            request.setAttribute("titleInfo", title.toString()) ;
            %>
            <msh:section title='Результат поиска: ${titleInfo }'>
            	<ecom:webQuery name="listPres" nativeSql="
            	
   select 
    p.id||''',''${param.beginDate}' as pid
    ,  d.record as f2drecord
   , coalesce(ssSls.code,ssslo.code,'POL'||pl.medCase_id) as f3codemed
   , p.materialId||' ('||vsst.code||')' as f4material
   ,coalesce(vsst.name,'---') as f5vsstname
   ,pat.lastname ||' '||pat.firstname||' '||pat.middlename ||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as f6birthday
   , ms.code||coalesce('('||ms.additionCode||')','')||' '||ms.name as f7medServicies
   ,wp.lastname||' '||wp.firstname||' '||wp.middlename as f8fioworker
   ,iwp.lastname||' '||iwp.firstname||' '||iwp.middlename as f9intakefioworker
   ,to_char(mc.datestart,'dd.mm.yyyy')||' '||cast(mc.timeexecute as varchar(5)) as f10dtintake
   ,to_char(p.createdate,'dd.mm.yyyy')||' '||cast(p.createtime as varchar(5)) as f11planStartDate
   ,mc.id as f12mcid
   ,ml.name as m13lname
   
    from prescription p
    left join MedCase mc on mc.id=p.medcase_id
    left join Diary d on d.medcase_id=mc.id
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
    left join WorkFunction iwf on iwf.id=mc.workfunctionexecute_id
    left join Worker iw on iw.id=iwf.worker_id
    left join Patient iwp on iwp.id=iw.person_id
    left join MisLpu ml on ml.id=w.lpu_id
    
    where ${dateInfo}
    and vst.code='LABSURVEY' 
    and mc.workFunctionExecute_id is not null and mc.dateStart is not null
    ${sqlAdd}
    order by pat.lastname,pat.firstname,pat.middlename
    
"/>
   <msh:table name="listPres" action="javascript:void(0)" idField="1">
   <msh:tableButton property="1" addParam="'txt'" buttonFunction="printId" buttonName="Печать в txt" buttonShortName="txt"/>
   <msh:tableButton property="1" addParam="'odt'" buttonFunction="printId" buttonName="Печать в odt" buttonShortName="odt"/>
	      <msh:tableColumn columnName="#" property="sn"  />
	      <msh:tableColumn columnName="ИБ" property="3"  />
	      <msh:tableColumn columnName="Дата напр." property="11"/>
	      <msh:tableColumn columnName="Отделение" property="13"  />
	      <msh:tableColumn columnName="Забор" property="10"/>
	      <msh:tableColumn columnName="Код" property="4"/>
	      <msh:tableColumn columnName="ФИО пациента" property="6"  />
	      <msh:tableColumn columnName="Услуга" property="7"/>
	      <msh:tableColumn columnName="Результат" property="2" cssClass="preCell"/>
	    </msh:table>
            </msh:section>
            <%} %>
            <tags:pres_intake_biomaterial name="Bio" role="/Policy/Mis/Journal/Prescription/LabSurvey/LaborantRegistrator"/>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
  	
    <script type="text/javascript">// <![CDATA[//
    	
    	$('number').focus() ;
    	$('number').select() ;
    	if ($('beginDate').value=="") {
	    	var currentDate = new Date;
			var textDay = currentDate.getDate()<10?'0'+currentDate.getDate():currentDate.getDate();
			var textMonth = currentDate.getMonth()+1;
			var textMonth = textMonth<10?'0'+textMonth:textMonth;
			var textYear =currentDate.getFullYear();
			$('beginDate').value=textDay+'.'+textMonth+'.'+textYear;
    	}
  	</script>
  
     </tiles:put>

</tiles:insert>