<%@page import="java.util.StringTokenizer"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Contract" >Поиск контрактных лиц</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
		
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/Contract/ContractPerson/JuridicalPerson/Create" params="" action="/entityPrepareCreate-contract_juridicalPerson" title="Юридическое лицо" name="Юридическое лицо" />
		</msh:sideMenu>
		<tags:contractMenu currentAction="conPerson"/>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<%
	String typeContractPerson =ActionUtil.updateParameter("ReportContractFind","typeContractPerson","1", request) ;
	%>
	<msh:form action="contract_find_person.do" defaultField="contractNumber">
			<msh:panel>
				<msh:row>
					<msh:textField property="contractNumber" label="Наименование" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				        <msh:row>
	        <td class="label" title="Договорная персона (typeContractPerson)" colspan="1"><label for="typeContractPersonName" id="typeContractPersonLabel">Договорная персона:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeContractPerson" value="1"> физическое лицо
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeContractPerson" value="2"> юридическое лицо
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeContractPerson" value="3"> страховая компания
	        </td>
        </msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Найти" doNotDisableButtons="cancel" labelSaving="Поиск..." colSpan="4"/>
        </msh:row>
			</msh:panel>
		</msh:form>
		<script type='text/javascript'>
		checkFieldUpdate('typeContractPerson','${typeContractPerson}',1) ;
	    
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
   eventutil.addEventListener($('contractNumber'), eventutil.EVENT_KEY_UP, 
 		  	function() {$('contractNumber').value = latRus($('contractNumber').value) ;}
		) ;
	function latRus(aText) {
			aText=aText.toUpperCase() ;
			aText=replaceAll(aText,"Й", "Q" ,1) ;
			aText=replaceAll(aText,"Ц", "W" ,1) ;
	    	aText=replaceAll(aText,"У","E"  ,1) ;
	    	aText=replaceAll(aText, "К", "R" ,1) ;
	    	aText=replaceAll(aText,"Е", "T"  ,1) ;
	    	aText=replaceAll(aText, "Ф","A" ,1) ;
	    	aText=replaceAll(aText, "Ы", "S",1) ;
	    	aText=replaceAll(aText,"В", "D" ,1 ) ;
	    	aText=replaceAll(aText,"А","F" ,1) ;
	    	aText=replaceAll(aText,"П","G"  ,1) ;
	    	aText=replaceAll(aText,"Я","Z" ,1 ) ;
	    	aText=replaceAll(aText,"Ч","X"  ,1) ;
	    	aText=replaceAll(aText,"С","C" ,1 ) ;
	    	aText=replaceAll(aText, "М", "V" ,1) ;
	    	aText=replaceAll(aText,"И", "B",1 ) ;
	    	aText=replaceAll(aText,"Н", "Y" ,1 ) ;
	    	aText=replaceAll(aText,"Г", "U" ,1 ) ;
	    	aText=replaceAll(aText,"Ш", "I" ,1 ) ;
	    	aText=replaceAll(aText,"Щ", "O" ,1 ) ;
	    	aText=replaceAll(aText,"З","P",1 ) ;
	    	aText=replaceAll(aText, "Р","H",1 ) ;
	    	aText=replaceAll(aText,"О", "J" ,1 ) ;
	    	aText=replaceAll(aText,"Л","K" ,1 ) ;
	    	aText=replaceAll(aText,"Д", "L",1 ) ;
	    	aText=replaceAll(aText,"Т","N" ,1) ;
	    	aText=replaceAll(aText, "Ь","M",1 ) ;
	    	aText=replaceAll(aText, "Ю",".",1 ) ;
	    	aText=replaceAll(aText, "Б","," ,1) ;
	    	aText=replaceAll(aText, "Х","[" ,1) ;
	    	aText=replaceAll(aText, "Ъ","]",1 ) ;
	    	aText=replaceAll(aText, "Ж",";",1 ) ;
	    	aText=replaceAll(aText, "Э","'",1 ) ;
	    	return aText ;
		}
		function replaceAll(aText,aSymbIs,aSymbRep,aRedict) {
			var sym1=aSymbRep,sym2=aSymbIs;
			if (+aRedict<1) {
				sym2=aSymbRep,sym1=aSymbIs;
			} 
			while (aText.indexOf(sym1)>-1) {
				aText = aText.replace(sym1,sym2) ;
			}
			return aText.toUpperCase() ;
		}
			 
    </script>
	<% if (request.getParameter("contractNumber")!=null) { 
		StringBuilder fio = new StringBuilder() ;
		String contractNumber  = request.getParameter("contractNumber").toUpperCase() ;
		
		if (typeContractPerson.equals("1"))	{
			request.setAttribute("cpdtypeSql", "  cp.dtype='NaturalPerson'") ;
			StringTokenizer st = new StringTokenizer(contractNumber, " \t;,.");
			if (st.hasMoreTokens()) {
				fio.append(" p.lastname like '").append(st.nextToken()).append("%'");
			}
			if (st.hasMoreTokens()) {
				fio.append(" and p.firstname like '").append(st.nextToken()).append("%'");
			}
			if (st.hasMoreTokens()) {
				fio.append(" and p.middlename like '").append(st.nextToken()).append("%'");
			}
		} else if (typeContractPerson.equals("2")) {
			request.setAttribute("cpdtypeSql", "  cp.dtype='JuridicalPerson'") ;
			fio.append("cp.name like '%").append(contractNumber).append("%'") ;
		} else if (typeContractPerson.equals("3")) {
			request.setAttribute("cpdtypeSql", "  cp.dtype='JuridicalPerson'") ;
			fio.append("(cp.name like '%").append(contractNumber).append("%' or reg.name like '%").append(contractNumber).append("%')") ;
		}
			//request.setAttribute("cpdtypeSql", " and ") ;
			
		
		request.setAttribute("fiocp", fio.toString()) ;
	%>
			<ecom:webQuery name="childContract" nativeSql="
			select cp.id
			,CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') 
			when cp.dtype='JuridicalPerson' then 'Юрид.лицо: '||cp.name else 'Страховая компания'||reg.name END as persname
			from  ContractPerson cp
			left join REG_IC reg on reg.id=cp.regCompany_id
			left join Patient p on p.id=cp.patient_id
			where  ${cpdtypeSql} and ${fiocp} 
			
			"/>
				<msh:table name="childContract" viewUrl="entitySubclassView-contract_contractPerson.do?short=Short" action="entitySubclassView-contract_contractPerson.do" idField="1">
					<msh:tableColumn columnName="#" property="sn" />
					<msh:tableColumn columnName="Наименование" property="2" />
					<msh:tableColumn columnName="Страховая компания" property="3" />
				</msh:table>	
				<%} %>	
	</tiles:put>
</tiles:insert>
