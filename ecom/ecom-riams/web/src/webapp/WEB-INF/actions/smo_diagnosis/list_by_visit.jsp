<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по диагнозам по визитам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalDiagByvisit"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_ticketsByNonredidentPatientList.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
     <msh:row>
        <td class="label" title="Поиск по МКБ (typeMKB)" colspan="1"><label for="typeMKbName" id="typeMkbLabel">МКБ:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="1">  перв. симв.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="2">  два симв.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="3">  три симв.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeMKB" value="4">  полностью
        </td>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="5"
        	label="Отделение" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="2"
        	label="Поток обслуживания" horizontalFill="true" vocName="vocServiceStream"/>
          <msh:autoComplete vocName="vocBedSubType" property="bedSubType" label="Тип коек" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row>
        	<msh:textField property="filterAdd" label="Фильтр МКБ" fieldColSpan="2"/>
        	<td><i>Пример: <b>A0</b>, <b>N</b>, <b>A00-B87</b></i></td>
        </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" />
	        <msh:textField property="dateEnd" label="по" />
			<td>
	            <input type="submit" onclick="find()" value="Найти" />
	          </td>
        </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String dateEnd = (String)request.getParameter("dateEnd") ;
    	if (dateEnd==null||dateEnd.equals("")) {
    		request.setAttribute("dateEnd", date) ;
    	} else {
    		request.setAttribute("dateEnd", dateEnd) ;
    	}
    	
    	String stat = (String)request.getParameter("typeStatus") ;
    	String typeMKB = (String)request.getAttribute("typeMKB") ;
    	//String typeDate = (String)request.getAttribute("typeDate") ;
    	String mkbCode = "mkb.code" ;
    	String mkbName = "mkb.name" ;
    	String mkbLike = "0" ;
    	if (typeMKB!=null) {
    		if (typeMKB.equals("1")) {mkbName="substring(mkb.code,1,1)";mkbCode="substring(mkb.code,1,1)" ;mkbLike="1";} 
    		else if (typeMKB.equals("2")) {mkbName="substring(mkb.code,1,2)";mkbCode="substring(mkb.code,1,2)" ;mkbLike="1";}
    		else if (typeMKB.equals("3")) {mkbName="substring(mkb.code,1,3)";mkbCode="substring(mkb.code,1,3)" ;mkbLike="1";}
    	} 
    	request.setAttribute("mkbName",mkbName) ;
    	request.setAttribute("mkbNameGroup",mkbName.equals("mkb.name")?",mkb.name":"") ;
    	request.setAttribute("mkbCode",mkbCode) ;
    	request.setAttribute("mkbLike",mkbLike) ;
    	String fldDate = "visit.dateFinish" ;
    	//if (typeDate!=null) {
    	//	if (typeDate.equals("1")) {fldDate="visit.dateStart" ;} 
    	//	else if (typeDate.equals("3")) {fldDate="visit.transferDate" ;}
    	//} 
    	request.setAttribute("fldDate","visit.dateStart") ;
    	boolean isStat = true ;
    	if (stat!=null && stat.equals("2")) {
    		isStat = false ;
    	}

    	String dep = (String)request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		request.setAttribute("dep", " and visit.department_id="+dep) ;
    		request.setAttribute("department",dep) ;
    	} else {
    		request.setAttribute("department","0") ;
    	}
    	String servStream = (String)request.getParameter("serviceStream") ;
    	if (servStream!=null && !servStream.equals("") && !servStream.equals("0")) {
    		request.setAttribute("servStream", " and vss.id="+servStream) ;
    		request.setAttribute("serviceStream", servStream) ;
    	} else {
    		request.setAttribute("serviceStream", "0") ;
    	}
    	//String regType = (String)request.getParameter("registrationType") ;
    	//if (regType!=null && !regType.equals("") && !regType.equals("0")) {
    	//	request.setAttribute("regType", " and diag.registrationType_id="+regType) ;
    	//} 
    	String priority = (String)request.getParameter("priority") ;
    	if (priority!=null && !priority.equals("") && !priority.equals("0")) {
    		request.setAttribute("prioritySql", " and diag.priority_id="+priority) ;
    	} 
    	//String bedSubType = (String)request.getParameter("bedSubType") ;
    	//if (bedSubType!=null && !bedSubType.equals("") && !bedSubType.equals("0")) {
    	//	request.setAttribute("bedSubTypeSql", " and bf.bedSubType_id="+bedSubType) ;
    	//} 
    	String filter = (String)request.getParameter("filterAdd") ;
    	if (filter!=null && !filter.equals("")) {
    		filter = filter.toUpperCase() ;
    		request.setAttribute("filterAdd",filter) ;
    		//filter = filter.replaceAll(" ", "") ;
    		String[] fs1=filter.split(",") ;
    		StringBuilder filtOr = new StringBuilder() ;
    		
    		for (int i=0;i<fs1.length;i++) {
    			String filt1 = fs1[i].trim() ;
    			String[] fs=filt1.split("-") ;
    			if (filt1.length()>0) {
	    			if (filtOr.length()>0) filtOr.append(" or ") ;
		    		if (fs.length>1) {
		    			String fsE1 = fs[1].trim() ;
		    			int ind = fsE1.indexOf(".");
		    			if (ind>-1) {fsE1=fsE1+"999";} else if (fsE1.length()<2) {
		    				for (int ij=0;ij<2-fsE1.length();ij++) {fsE1=fsE1+"9" ;}
		    				fsE1=fsE1+".999";
		    			} else {
		    				fsE1=fsE1+".999";}
		    			filtOr.append(" mkb.code between '"+fs[0].trim()+"' and '"+fsE1+"'") ;
		    		} else {
		    			filtOr.append(" substring(mkb.code,1,"+filt1.length()+")='"+filt1+"'") ;
		    		}
    			}
     		}
    		request.setAttribute("filterSql", filtOr.insert(0, " and (").append(")").toString()) ;
    		
    	} 
    	
    	%>
		<ecom:webQuery name="diag_list" nativeSql="
		select ${mkbCode}||':${mkbLike}:${dep}:${servStream}:${fldDate}:${param.dateBegin}:${dateEnd}:'||vpd.id||':'||vdrt.id||':${bedSubTypeSql}:${filterAdd}' as id
		,${mkbCode} as mkb,count(*),vpd.name as vpdname,vdrt.name as vdrtname 
		,${mkbName} as mkbname
		,count(distinct visit.id)
		from Diagnosis diag
		left join VocIdc10 mkb on mkb.id=diag.idc10_id
		left join MedCase visit on visit.id=diag.medCase_id
		left join MedCase sls on sls.id=visit.parent_id
		left join VocHospitalizationResult vhr on vhr.id=sls.result_id
		left join BedFund bf on bf.id=visit.bedFund_id
		left join VocServiceStream vss on vss.id=visit.serviceStream_id
		left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
		where ${fldDate} between to_date('${param.dateBegin}','dd.mm.yyyy')
			and to_date('${dateEnd}','dd.mm.yyyy') and visit.dtype='Visit'
			${servStream} ${dep} ${prioritySql} ${regType} ${bedSubTypeSql}
			${filterSql}
		group by ${mkbCode},vpd.id,vpd.name,vdrt.id,vdrt.name ${mkbNameGroup}
		order by ${mkbCode},vpd.id,vdrt.id
		"/>
		<msh:table name="diag_list" idField="1" action="js-smo_diagnosis-by_visit_data.do">
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="5" columnName="Тип регистрации"/>
			<msh:tableColumn property="4" columnName="Приоритет"/>
			<msh:tableColumn property="2" columnName="Код МКБ10"/>
			<msh:tableColumn property="6" columnName="Наименование болезни"/>
			<msh:tableColumn property="3" columnName="Кол-во визитов"/>
		</msh:table>
    <% } else { %>
    	<i>Выберите параметры и нажмите найти </i>
    <% }   %>
    
    <script type='text/javascript'>
    
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typeMKB','${typeMKB}',4) ;
    
    
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
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='smo_diagnosis_by_visit_list.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	
    }
    </script>
  </tiles:put>
</tiles:insert>