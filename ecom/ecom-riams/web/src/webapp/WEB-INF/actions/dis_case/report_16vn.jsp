<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Форма 16ВН"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String noViewForm = request.getParameter("noViewForm") ;

  	String typeView=ActionUtil.updateParameter("report16vn","typeView","1", request) ;
  	
  	if (noViewForm!=null && noViewForm.equals("1")) {
  	} else {
  		
  %>
    <msh:form action="/mis_report_16vn.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printreport16vn" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Просмотр данных (typeAbort)" colspan="1"><label for="typeAbortName" id="typeAbortLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  диагнозы (01-95)
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2"  > 98,99,100,101,104 общее
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3"  > 98,99,100,101,104 по длительности
        </td>
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
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
           <script type='text/javascript'>
           checkFieldUpdate('typeView','${typeView}',1) ;

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
    function setPatient(aNumberDoc,aOrderDate, aPatient,aTypeList,aPeriod,aLpu,aSpec) {
    	if (confirm('Вы точно хотите установить соответствие с этим пациентом?')) HospitalMedCaseService.setPatientByExternalMedservice(aNumberDoc, aOrderDate, aPatient, {
    		callback: function(aResult) {
    			getDefinition('journal_doc_externalMedService.do?short=Short&typeList='+aTypeList+'&noViewForm=1&period='+aPeriod+'&lpuid='+aLpu+'&id='+aSpec); 
    		}
    	});

    }
   
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printHistology" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_histology.do' ;
    	$('id').value = $('dateBegin').value+":"
    		+$('dateBegin').value+":"
    		+$('department').value;
    }/*
    function printJournal() {
    	var frm = document.forms[0] ;
    	frm.m.value="printJournalByDay" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_journal001.do' ;
    	$('id').value = 
    		$('dateBegin').value+":"
    		
    		+$('department').value;
    }
    */
    /**/
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    
    String date = request.getParameter("dateBegin") ;
    String dateEnd = request.getParameter("dateEnd") ;
    //String id = (String)request.getParameter("id") ;
    String period = request.getParameter("period") ;
    String strcode =request.getParameter("strcode") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;

    if (typeView!=null && ((date!=null && !date.equals("")) || (period!=null && !period.equals("") 
    	    && strcode!=null && !strcode.equals("")))) {
    
        if (typeView!=null && typeView.equals("1")) {
        	

            if (date!=null && !date.equals("")) {
                request.setAttribute("isReportBase", ActionUtil.isReportBase(date,dateEnd,request));
                
            	%>
            
            <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
            </msh:section>
           
            <msh:section>
            <msh:sectionTitle>Свод по возрастам ${reportInfo}</msh:sectionTitle>
            <msh:sectionContent>
            <ecom:webQuery isReportBase="${isReportBase}" name="report16vnswod" nativeSql="
            
        select vrspt.id||'&strcode='||vrspt.id as vrsptid,vrspt.name ,vrspt.code as vrsptcode, vrspt.sexCode as vrsptsexCode
        ,vrspt.strCode
        ,count(distinct dd.id) as cntAll
        ,sum((select 
        case when 
        	count(distinct case when vddcr1.is16AtWork='1' then dd1.id else null end)=1 
        	then  (max(dr.dateto)-min(dr.datefrom))  
        	else case when max(dr.dateto)>max(case when dd.id=dd1.id then dr.dateto else null end) then
        max(case when dd.id=dd1.id then dr.dateto else null end) else 
        max(dr.dateto) end -
        case when min(dr.datefrom)<min(case when dd.id=dd1.id then dr.datefrom else null end) then
        min(case when dd.id=dd1.id then dr.datefrom else null end) else 
        min(dr.datefrom) end
        end+1
        from disabilityrecord dr 
        left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id 
        left join VocDisabilityDocumentCloseReason vddcr1 on vddcr1.id=dd1.closeReason_id 
        where dd.disabilityCase_id=dd1.disabilityCase_id 
        and (dd1.noActuality='0' or dd1.noActuality is null) 
        and dd1.anotherLpu_id is null)) as sumdays

        ,count(distinct case when (
        cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
        +(case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
        +(case when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
        <0) then -1 else 0 end))<=14 then dd.id else null end ) as age14
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
        -cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
        -cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
        - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
        then -1 else 0 end)) between 15 and 19
        then dd.id else null end) as age19
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
        -cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
        -cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
        - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
        <0) then -1 else 0 end)) between 15 and 17 then dd.id else null end) as age17
        ,count(distinct 
        case when (
        cast(to_char(dd.issuedate,'yyyy') as int)
        -cast(to_char(p.birthday,'yyyy') as int)
        +(case when (cast(to_char(dd.issuedate, 'mm') as int)
        -cast(to_char(p.birthday, 'mm') as int)
        +(case when (cast(to_char(dd.issuedate,'dd') as int) 
        - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
        <0) then -1 else 0 end)) between 20 and 24 then dd.id else null end) as age24
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
        -cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
        -cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
        - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
        then -1 else 0 end)) between 25 and 29 then dd.id else null end) as age29
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
        -cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
        -cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
        - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
        then -1 else 0 end)) between 30 and 34 then dd.id else null end) as age34
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
        -cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
        -cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
        - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
        then -1 else 0 end)) between 35 and 39 then dd.id else null end) as age39
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
        -cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
        -cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
        - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
        <0) then -1 else 0 end)) between 40 and 44 then dd.id else null end) as age44
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
        +case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
        when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
        ((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
        ) between 45 and 49 then dd.id else null end) as age49
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
        +case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
        when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
        ((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
        ) between 50 and 54 then dd.id else null end) as age54
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
        +case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
        when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
        ((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
        ) between 55 and 59 then dd.id else null end) as age59
        ,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
        +case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
        when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
        ((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
        ) >=60 then dd.id else null end) as age60
        from DisabilityCase dc
        left join DisabilityDocument dd on dd.disabilityCase_id=dc.id
        left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
        left join VocDisabilityReason vdr1 on vdr1.id=dd.disabilityReasonChange_id
        left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
        left join VocIdc10 mkb on mkb.id=dd.idc10Final_id
        left join patient p on p.id=dc.patient_id
        left join VocReportSetParameterType vrspt on vrspt.sex_id=p.sex_id
        left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
        where 
        dd.issueDate between to_date('${dateBegin}','dd.mm.yyyy') 
            and to_date('${dateEnd}','dd.mm.yyyy') and vddcr.is16AtWork='1'

            and (dd.noActuality='0' or dd.noActuality is null) 
            and (dd.mainWorkDocumentNumber='')
            and anotherLpu_id is null
        and vrspt.classname='16VN_DIAG' 
        and mkb.code between rspt.codefrom and rspt.codeto
        and (coalesce(vdr1.codeF,vdr.codeF)='05' and 

(select case when max(dr.dateto)-min(dr.datefrom)+1>139 then '1' else '0' end from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id  where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='')='0'
         or coalesce(vdr1.codeF,vdr.codeF)!='03'
        and coalesce(vdr1.codeF,vdr.codeF)!='09' and coalesce(vdr1.codeF,vdr.codeF)!='08'
        )
        group by vrspt.id,vrspt.name,vrspt.strCode,vrspt.code, vrspt.sexCode
        order by vrspt.strCode

        " />
            <msh:table name="report16vnswod"  printToExcelButton="Сохранить в excel"
            viewUrl="mis_report_16vn.do?typeView=${typeView}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
             action="mis_report_16vn.do?typeView=${typeView}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1">
              <msh:tableColumn columnName="Наименование" property="2" />
              <msh:tableColumn columnName="Код МКБ10" property="3" />
              <msh:tableColumn columnName="Пол" property="4" />
              <msh:tableColumn columnName="№ строки" property="5" />
              <msh:tableColumn columnName="Число дней временной нетрудоспособности" property="7"/>
              <msh:tableColumn columnName="Число случаев временной нетрудоспособности" property="6"/>
              <msh:tableColumn columnName="до 14 лет" property="8"/>
              <msh:tableColumn columnName="15-19" property="9"/>
              <msh:tableColumn columnName="15-17" property="10"/>
              <msh:tableColumn columnName="20-24" property="11"/>
              <msh:tableColumn columnName="25-29" property="12"/>
              <msh:tableColumn columnName="30-34" property="13"/>
              <msh:tableColumn columnName="35-39" property="14"/>
              <msh:tableColumn columnName="40-44" property="15"/>
              <msh:tableColumn columnName="45-49" property="16"/>
              <msh:tableColumn columnName="50-54" property="17"/>
              <msh:tableColumn columnName="55-59" property="18"/>
              <msh:tableColumn columnName="больше 60 лет" property="19"/>
            </msh:table>
            
            </msh:sectionContent>
            </msh:section>
            <%} else if (period!=null && !period.equals("") 
            && strcode!=null && !strcode.equals("")) {
            	
            	String[] obj = period.split("-") ;
            	String dateBegin=obj[0] ;
            	dateEnd=obj[1];
            	request.setAttribute("dateBegin", dateBegin);
            	request.setAttribute("dateEnd", dateEnd);
                request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
            		%>
            <msh:section>
            <msh:sectionTitle>Список пациентов</msh:sectionTitle>   
            <msh:sectionContent>
            <ecom:webQuery isReportBase="${isReportBase}" name="journal_surOperation" nativeSql="
            
            
            select 
        dd.id as soid

        ,p.lastname||' '||p.firstname||' '||p.middlename

        ,mkb.code as mkbcode
        ,cast(to_char(dd.issuedate,'yyyy') as int)
        -cast(to_char(p.birthday,'yyyy') as int)
        +(case when (cast(to_char(dd.issuedate, 'mm') as int)
        -cast(to_char(p.birthday, 'mm') as int)
        +(case when (cast(to_char(dd.issuedate,'dd') as int) 
        - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
        <0)
        then -1 else 0 end)
         as age
        ,
        ((select 
        case when 
        	count(distinct case when vddcr1.is16AtWork='1' then dd1.id else null end)=1 
        	then  (max(dr.dateto)-min(dr.datefrom))  
        	else case when max(dr.dateto)>max(case when dd.id=dd1.id then dr.dateto else null end) then
        max(case when dd.id=dd1.id then dr.dateto else null end) else 
        max(dr.dateto) end -
        case when min(dr.datefrom)<min(case when dd.id=dd1.id then dr.datefrom else null end) then
        min(case when dd.id=dd1.id then dr.datefrom else null end) else 
        min(dr.datefrom) end
        end+1
        from disabilityrecord dr 
        left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id 
        left join VocDisabilityDocumentCloseReason vddcr1 on vddcr1.id=dd1.closeReason_id 
        where dd.disabilityCase_id=dd1.disabilityCase_id 
        and (dd1.noActuality='0' or dd1.noActuality is null) 
        and dd1.anotherLpu_id is null)) as sumdays
        , list(ml.name) as deps
        from DisabilityCase dc
        left join DisabilityDocument dd on dd.disabilityCase_id=dc.id
        left join DisabilityRecord dr on dr.disabilityDocument_id=dd.id
        left join Workfunction wf on wf.id=dr.workfunction_id
        left join worker w on w.id=wf.worker_id
        left join mislpu ml on ml.id=w.lpu_id
        left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
        left join VocDisabilityReason vdr1 on vdr1.id=dd.disabilityReasonChange_id
        left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
        left join patient p on p.id=dc.patient_id
        left join VocReportSetParameterType vrspt on vrspt.sex_id=p.sex_id
        left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
        left join VocIdc10 mkb on mkb.id=dd.idc10Final_id
        where 
        dd.issueDate between 

        to_date('${dateBegin}','dd.mm.yyyy') 
            and to_date('${dateEnd}','dd.mm.yyyy') and vddcr.is16AtWork='1'
        and mkb.code between rspt.codefrom and rspt.codeto

            and (dd.noActuality='0' or dd.noActuality is null) 
            and (dd.mainWorkDocumentNumber='')
            and anotherLpu_id is null
        and (coalesce(vdr1.codeF,vdr.codeF)='05' and 

(select case when (max(dr.dateto)-min(dr.datefrom)+1)>139 then '1' else '0' end from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id  where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='')='0'
         or coalesce(vdr1.codeF,vdr.codeF)!='03'
        and coalesce(vdr1.codeF,vdr.codeF)!='09' and coalesce(vdr1.codeF,vdr.codeF)!='08'
        )

        and vrspt.id='${param.strcode}'
        group by dd.id
        ,p.lastname,p.firstname,p.middlename,p.birthday,dd.issuedate,dd.disabilityCase_id
        ,mkb.code
        order by p.lastname,p.firstname,p.middlename
            
            
        " />
            <msh:table name="journal_surOperation" printToExcelButton="Сохранить в excel"
            viewUrl="entityShortView-dis_document.do" 
             action="entityView-dis_document.do" idField="1">
              <msh:tableColumn columnName="##" property="sn" />
              <msh:tableColumn columnName="Пациент" property="2" />
              <msh:tableColumn columnName="Код МКБ" property="3" />
              <msh:tableColumn columnName="Возраст" property="4"/>
              <msh:tableColumn columnName="Длительность" property="5" />
              <msh:tableColumn columnName="Длительность" property="6" />
            </msh:table>
            </msh:sectionContent>
            </msh:section>  
              		
            		<%
            }
            }
    if (typeView!=null && (typeView.equals("2")||typeView.equals("3"))) {

    if (date!=null && !date.equals("")) {
    	if (typeView.equals("3")) {
    		request.setAttribute("sqlCntDays",",(select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='') ") ;
    		request.setAttribute("sqlCntDays1",",(select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.id=dd1.id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='') ") ;
    		request.setAttribute("sqlCntDaysUrl","||'&durat='||(select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='') ") ;
    		request.setAttribute("sqlCntDaysUrl1","||'&durat='||(select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.id=dd1.id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='') ") ;
    	} else {
    		request.setAttribute("sqlCntDays","") ;
    	}
        
    	%>
    
    <msh:section>
    <msh:sectionTitle>Свод</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="report16vnswod1" nativeSql="
select
'&strcode='||coalesce(vdr1.codeF,vdr.codeF) ${sqlCntDaysUrl1} as codeF,coalesce(vdr1.name,vdr.name) as namevdr,case when count(distinct dd.id)<10 then list(distinct ''||dc.id) else '' end as listDC
,count(distinct dc.id) as cntAll
,sum((select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.id=dd1.id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='')) as sumDays
,count(distinct case when (
cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end))<=14 then dc.id else null end ) as age14
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 15 and 19
then dc.id else null end) as age19
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 15 and 17 then dc.id else null end) as age17
,count(distinct 
case when (
cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 20 and 24 then dc.id else null end) as age24
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 25 and 29 then dc.id else null end) as age29
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 30 and 34 then dc.id else null end) as age34
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 35 and 39 then dc.id else null end) as age39
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 40 and 44 then dc.id else null end) as age44
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 45 and 49 then dc.id else null end) as age49
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 50 and 54 then dc.id else null end) as age54
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 55 and 59 then dc.id else null end) as age59
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) >=60 then dc.id else null end) as age60
${sqlCntDays1}
from DisabilityCase dc
left join DisabilityDocument dd on dd.disabilityCase_id=dc.id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
left join VocDisabilityReason vdr1 on vdr.id=dd.disabilityReasonChange_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocIdc10 mkb on mkb.id=dd.idc10Final_id
left join patient p on p.id=dc.patient_id
where 
dd.issueDate between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') and vddcr.is16AtWork='1'

    and (dd.noActuality='0' or dd.noActuality is null) 
    and (dd.mainWorkDocumentNumber='')
    and dd.anotherLpu_id is null
    and (coalesce(vdr1.codeF,vdr.codeF)='08') 
group by coalesce(vdr1.codeF,vdr.codeF),coalesce(vdr1.name,vdr.name)
${sqlCntDays1}

" />
    <msh:table name="report16vnswod1" printToExcelButton="Сохранить в excel"
    viewUrl="mis_report_16vn.do?typeView=${typeView}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="mis_report_16vn.do?typeView=${typeView}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1">
      <msh:tableColumn columnName="Наименование" property="2" />
       <msh:tableColumn columnName="Длительность" property="18" />
      <msh:tableColumn columnName="Число дней временной нетрудоспособности" property="5"/>
      <msh:tableColumn columnName="Число случаев временной нетрудоспособности" property="4"/>
      <msh:tableColumn columnName="до 14 лет" property="6"/>
      <msh:tableColumn columnName="15-19" property="7"/>
      <msh:tableColumn columnName="15-17" property="8"/>
      <msh:tableColumn columnName="20-24" property="9"/>
      <msh:tableColumn columnName="25-29" property="10"/>
      <msh:tableColumn columnName="30-34" property="11"/>
      <msh:tableColumn columnName="35-39" property="12"/>
      <msh:tableColumn columnName="40-44" property="13"/>
      <msh:tableColumn columnName="45-49" property="14"/>
      <msh:tableColumn columnName="50-54" property="15"/>
      <msh:tableColumn columnName="55-59" property="16"/>
      <msh:tableColumn columnName="больше 60 лет" property="17"/>
    </msh:table>
    
    <ecom:webQuery isReportBase="${isReportBase}" name="report16vnswod" nativeSql="


select 
'&strcode='||coalesce(vdr1.codeF,vdr.codeF) ${sqlCntDaysUrl} as codeF,coalesce(vdr1.name,vdr.name) as namevdr,case when count(distinct dd.id)<10 then list(distinct ''||dc.id) else '' end as listDC
,count(distinct dc.id) as cntAll
,sum((select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='')) as sumDays
,count(distinct case when (
cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end))<=14 then dc.id else null end ) as age14
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 15 and 19
then dc.id else null end) as age19
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 15 and 17 then dc.id else null end) as age17
,count(distinct 
case when (
cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 20 and 24 then dc.id else null end) as age24
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 25 and 29 then dc.id else null end) as age29
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 30 and 34 then dc.id else null end) as age34
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 35 and 39 then dc.id else null end) as age39
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 40 and 44 then dc.id else null end) as age44
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 45 and 49 then dc.id else null end) as age49
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 50 and 54 then dc.id else null end) as age54
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 55 and 59 then dc.id else null end) as age59
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) >=60 then dc.id else null end) as age60
${sqlCntDays}
from DisabilityCase dc
left join DisabilityDocument dd on dd.disabilityCase_id=dc.id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
left join VocDisabilityReason vdr1 on vdr.id=dd.disabilityReasonChange_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocIdc10 mkb on mkb.id=dd.idc10Final_id
left join patient p on p.id=dc.patient_id
where 
dd.issueDate between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') and vddcr.is16AtWork='1'

    and (dd.noActuality='0' or dd.noActuality is null) 
    and (dd.mainWorkDocumentNumber='')
    and dd.anotherLpu_id is null
    and (coalesce(vdr1.codeF,vdr.codeF)='05' and 

(select case when (max(dr.dateto)-min(dr.datefrom)+1)>139 then '1' else '0' end from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id  where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='')='1'

or coalesce(vdr1.codeF,vdr.codeF)='09' or coalesce(vdr1.codeF,vdr.codeF)='03'
    ) 
group by coalesce(vdr1.codeF,vdr.codeF),coalesce(vdr1.name,vdr.name)
${sqlCntDays}
" />
    <msh:table name="report16vnswod" printToExcelButton="Сохранить в excel"
    viewUrl="mis_report_16vn.do?typeView=${typeView}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="mis_report_16vn.do?typeView=${typeView}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1">
      <msh:tableColumn columnName="Наименование" property="2" />
       <msh:tableColumn columnName="Длительность" property="18" />
      <msh:tableColumn columnName="Число дней временной нетрудоспособности" property="5"/>
      <msh:tableColumn columnName="Число случаев временной нетрудоспособности" property="4"/>
      <msh:tableColumn columnName="до 14 лет" property="6"/>
      <msh:tableColumn columnName="15-19" property="7"/>
      <msh:tableColumn columnName="15-17" property="8"/>
      <msh:tableColumn columnName="20-24" property="9"/>
      <msh:tableColumn columnName="25-29" property="10"/>
      <msh:tableColumn columnName="30-34" property="11"/>
      <msh:tableColumn columnName="35-39" property="12"/>
      <msh:tableColumn columnName="40-44" property="13"/>
      <msh:tableColumn columnName="45-49" property="14"/>
      <msh:tableColumn columnName="50-54" property="15"/>
      <msh:tableColumn columnName="55-59" property="16"/>
      <msh:tableColumn columnName="больше 60 лет" property="17"/>
    </msh:table>

        <ecom:webQuery isReportBase="${isReportBase}" name="report16_just16" nativeSql="
select codef,cast('ОТПУСК ПО БЕРЕМЕННОСТИ И РОДАМ' as varchar(30)),max(listdc),sum(cntall) as cntall,sum(sumdays) as sumdays,sum(age14) as age14,sum(age19) as age19,sum(age17) as age17
,sum(age24) as age24,sum(age29) as age29,sum(age34) as age34,sum(age39) as age39,sum(age44) as age44,sum(age49) as age49
,sum(age54) as age54,sum(age59) as age59,sum(age60) as age60,16 from (
select
'&strcode=05' ||'&durat='||(select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='')  as codeF
,case when count(distinct dd.id)<10 then list(distinct ''||dc.id) else '' end as listDC
,count(distinct dc.id) as cntAll
,sum((select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='')) as sumDays
,count(distinct case when (
cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end))<=14 then dc.id else null end ) as age14
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int)
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 15 and 19
then dc.id else null end) as age19
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int)
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 15 and 17 then dc.id else null end) as age17
,count(distinct
case when (
cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(dd.issuedate,'dd') as int)
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 20 and 24 then dc.id else null end) as age24
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int)
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 25 and 29 then dc.id else null end) as age29
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int)
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 30 and 34 then dc.id else null end) as age34
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int)
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 35 and 39 then dc.id else null end) as age39
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(dd.issuedate,'dd') as int)
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 40 and 44 then dc.id else null end) as age44
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 45 and 49 then dc.id else null end) as age49
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 50 and 54 then dc.id else null end) as age54
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 55 and 59 then dc.id else null end) as age59
,count(distinct case when (cast(to_char(dd.issuedate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1
when (cast(to_char(dd.issuedate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and
((cast(to_char(dd.issuedate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) >=60 then dc.id else null end) as age60
,(select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='')
as v
from disabilityrecord dr
left join disabilitydocument dd on dd.id=dr.disabilitydocument_id
left join DisabilityCase dc on dd.disabilityCase_id=dc.id
left join disabilitydocument ddprev on dd.prevdocument_id=ddprev.id
left join mislpu ourlpu on dd.anotherlpu_id=ourlpu.id
left join mislpu lpu on ddprev.anotherlpu_id=lpu.id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
left join VocDisabilityReason vdr1 on vdr.id=dd.disabilityReasonChange_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocIdc10 mkb on mkb.id=dd.idc10Final_id
left join patient p on p.id=dc.patient_id
where dd.disabilityReason2_id=4 and  lpu.id=462 and  ourlpu.id is null
 and (dd.noActuality='0' or dd.noActuality is null)
    and (dd.mainWorkDocumentNumber='')
    and dd.anotherLpu_id is null
    or coalesce(vdr1.codeF,vdr.codeF)='09' or coalesce(vdr1.codeF,vdr.codeF)='03'
group by  dd.id having max(dr.dateto)-min(dr.datefrom)+1=16 and dd.issueDate between to_date('${dateBegin}','dd.mm.yyyy')
    and to_date('${dateEnd}','dd.mm.yyyy')) as t
    group by t.codef
" />
        <msh:table name="report16_just16" printToExcelButton="Сохранить в excel"
                   viewUrl="mis_report_16vn.do?typeView=3&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}"
                   action="mis_report_16vn.do?typeView=3&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1">
            <msh:tableColumn columnName="Наименование" property="2" />
            <msh:tableColumn columnName="Длительность" property="18" />
            <msh:tableColumn columnName="Число дней временной нетрудоспособности" property="5"/>
            <msh:tableColumn columnName="Число случаев временной нетрудоспособности" property="4"/>
            <msh:tableColumn columnName="до 14 лет" property="6"/>
            <msh:tableColumn columnName="15-19" property="7"/>
            <msh:tableColumn columnName="15-17" property="8"/>
            <msh:tableColumn columnName="20-24" property="9"/>
            <msh:tableColumn columnName="25-29" property="10"/>
            <msh:tableColumn columnName="30-34" property="11"/>
            <msh:tableColumn columnName="35-39" property="12"/>
            <msh:tableColumn columnName="40-44" property="13"/>
            <msh:tableColumn columnName="45-49" property="14"/>
            <msh:tableColumn columnName="50-54" property="15"/>
            <msh:tableColumn columnName="55-59" property="16"/>
            <msh:tableColumn columnName="больше 60 лет" property="17"/>
        </msh:table>

    </msh:sectionContent>
    </msh:section>
    <%} else if (period!=null && !period.equals("")
    && strcode!=null && !strcode.equals("")) {
    	if (typeView.equals("3")) {
    		request.setAttribute("sqlCntDays"," and (select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.disabilityCase_id=dd1.disabilityCase_id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='') ="+request.getParameter("durat")) ;
    		request.setAttribute("sqlCntDays1"," and (select max(dr.dateto)-min(dr.datefrom)+1 from disabilityrecord dr left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id where dd.id=dd1.id and (dd1.noActuality='0' or dd1.noActuality is null) and dd1.anotherLpu_id is null and dd1.mainWorkDocumentNumber='') ="+request.getParameter("durat")) ;
    	} else {
    		request.setAttribute("sqlCntDays","") ;
    		request.setAttribute("sqlCntDays","") ;
    	}
    	
    	String[] obj = period.split("-") ;
    	String dateBegin=obj[0] ;
    	dateEnd=obj[1];
    	request.setAttribute("dateBegin", dateBegin);
    	request.setAttribute("dateEnd", dateEnd);
    	
    		%>
    <msh:section>
    <msh:sectionTitle>Список пациентов</msh:sectionTitle>   
    <msh:sectionContent>
    <ecom:webQuery isReportBase="${isReportBase}" name="journal_surOperation" nativeSql="
    select
dd.id as soid

,p.lastname||' '||p.firstname||' '||p.middlename

,mkb.code as mkbcode
,cast(to_char(dd.issuedate,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(dd.issuedate, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(dd.issuedate,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end)
 as age
,
((select 
case when 
	count(distinct case when vddcr1.is16AtWork='1' then dd1.id else null end)=1 
	then  (max(dr.dateto)-min(dr.datefrom))  
	else case when max(dr.dateto)>max(case when dd.id=dd1.id then dr.dateto else null end) then
max(case when dd.id=dd1.id then dr.dateto else null end) else 
max(dr.dateto) end -
case when min(dr.datefrom)<min(case when dd.id=dd1.id then dr.datefrom else null end) then
min(case when dd.id=dd1.id then dr.datefrom else null end) else 
min(dr.datefrom) end
end+1
from disabilityrecord dr 
left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id 
left join VocDisabilityDocumentCloseReason vddcr1 on vddcr1.id=dd1.closeReason_id 
where dd.disabilityCase_id=dd1.disabilityCase_id 
and (dd1.noActuality='0' or dd1.noActuality is null) 
and dd1.anotherLpu_id is null)) as sumdays
,
((select 
case when 
	count(distinct case when vddcr1.is16AtWork='1' then dd1.id else null end)=1 
	then  (max(dr.dateto)-min(dr.datefrom))  
	else case when max(dr.dateto)>max(case when dd.id=dd1.id then dr.dateto else null end) then
max(case when dd.id=dd1.id then dr.dateto else null end) else 
max(dr.dateto) end -
case when min(dr.datefrom)<min(case when dd.id=dd1.id then dr.datefrom else null end) then
min(case when dd.id=dd1.id then dr.datefrom else null end) else 
min(dr.datefrom) end
end+1
from disabilityrecord dr 
left join disabilitydocument dd1 on dd1.id=dr.disabilityDocument_id 
left join VocDisabilityDocumentCloseReason vddcr1 on vddcr1.id=dd1.closeReason_id 
where dd.id=dd1.id 
and (dd1.noActuality='0' or dd1.noActuality is null) 
and dd1.anotherLpu_id is null)) as sumdays1
from DisabilityCase dc
left join DisabilityDocument dd on dd.disabilityCase_id=dc.id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
left join VocDisabilityReason vdr1 on vdr1.id=dd.disabilityReasonChange_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join patient p on p.id=dc.patient_id
left join VocReportSetParameterType vrspt on vrspt.sex_id=p.sex_id
left join ReportSetTYpeParameterType rspt on rspt.parameterType_id=vrspt.id
left join VocIdc10 mkb on mkb.id=dd.idc10Final_id
where 
dd.issueDate between 

to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') and vddcr.is16AtWork='1'
and mkb.code between rspt.codefrom and rspt.codeto

    and (dd.noActuality='0' or dd.noActuality is null) 
    and (dd.mainWorkDocumentNumber='')
    and anotherLpu_id is null
and coalesce(vdr1.codeF,vdr.codeF)='${param.strcode}' and ('${param.strcode}'='08'
 ${sqlCntDays1} or '${param.strcode}'!='08' ${sqlCntDays})
group by dd.id
,p.lastname,p.firstname,p.middlename,p.birthday,dd.issuedate,dd.disabilityCase_id
,mkb.code
order by p.lastname,p.firstname,p.middlename
    
    
" />
    <msh:table name="journal_surOperation" printToExcelButton="Сохранить в excel" openNewWindow="true"
    viewUrl="entityShortView-dis_document.do" 
     action="entityView-dis_document.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
      <msh:tableColumn columnName="Пациент" property="2" />
      <msh:tableColumn columnName="Код МКБ" property="3" />
      <msh:tableColumn columnName="Возраст" property="4"/>
      <msh:tableColumn columnName="Длительность случая" property="5" />
      <msh:tableColumn columnName="Длительность документа" property="6" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>  
    		<%
    }
    }
    } else {%>
    	<i>Нет данных </i>
    	<% } %>
    
  </tiles:put>
</tiles:insert>