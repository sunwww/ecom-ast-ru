<%@page import="java.util.Collection"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал прикрепленного населения"/>
  </tiles:put>

  <tiles:put name="body" type="string">
  <%
  	String typeRead="2"; 	
	String typeAge=ActionUtil.updateParameter("PatientAttachment","typeAge","3", request) ;
	String typeAttachment=ActionUtil.updateParameter("PatientAttachment","typeAttachment","3", request) ;
	String typeCompany=ActionUtil.updateParameter("PatientAttachment","typeCompany","3", request) ;
	String typeGroup=ActionUtil.updateParameter("PatientAttachment","typeGroup","1", request) ;
	String typeSex=ActionUtil.updateParameter("PatientAttachment","typeSex","3",request);

  %>
  
    <msh:form action="/mis_bypass_report.do" defaultField="lpuName" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
   
  
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
       <input type='hidden' name="typeRead" id="typeRead" value="2">
      <a href='mis_attachment.do'> <input type="button" value="Выгрузка населения в ФОМС" /> </a>
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
       <msh:row guid="a7a62505-2bfe-41b6-a54f-217b970dc0c3">
        <msh:autoComplete property="lpu" vocName="lpu" label="ЛПУ" viewAction="entityEdit-mis_lpu.do" fieldColSpan="7" guid="67d2a4af-71bc-4a19-8844-4a59b97fabda" horizontalFill="true" />
      </msh:row>
		 <msh:row styleId='rowCompany'>
		 	<msh:autoComplete fieldColSpan="3" property="company" label="Страховая&nbsp;компания" horizontalFill="true"
		                              vocName="vocInsuranceCompany"/>
         </msh:row>		
        <msh:row styleId='rowLpuArea'>
            <msh:autoComplete fieldColSpan="3" property="area" label="Участок" horizontalFill="true"
                              parentAutocomplete="lpu" vocName="lpuAreaWithParent"/>
        </msh:row>	
      <msh:row>
      <msh:row>
        <msh:checkBox property="noCheckLpu" label="Не учитывать ЛПУ"/>
       </msh:row>
        <msh:textField  property="period" label="Дата прикрепления с " />
        <msh:textField  property="periodTo" label="поs" />
      </msh:row>
      <msh:row>
        <td class="label" title="Возраст  (typeAge)" colspan="1"><label for="typeAgeName" id="typeAgeLabel">Возраст:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="1"  >  до 18 лет
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeAge" value="2">  от 18 лет 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeAge" value="3">  все 
	        </td>
	        
       </msh:row>
      <msh:row>
        <td class="label" title="Прикрепление  (typeAttachment)" colspan="1"><label for="typeAttachmentName" id="typeAttachmentLabel">Прикрепление:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAttachment" value="1">  по территории
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeAttachment" value="2">  по заявлению 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeAttachment" value="3">  все 
	        </td>
	        
       </msh:row>
       </msh:panel>
       <msh:panel colsWidth="systemTable">
      <msh:row>
        <td class="label" title="Пол  (typeSex)" colspan="1"><label for="typeSexName" id="typeSexLabel">Пол:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeSex" value="1">  М
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeSex" value="2">  Ж
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeSex" value="3">  все
        </td>
       </msh:row>
      <msh:row>
        <td class="label" title="Страх. компания  (typeCompany)" colspan="1"><label for="typeCompanyName" id="typeCompanyLabel">Страховая компания:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeCompany" value="1">  указана
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeCompany" value="2">  не указана
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeCompany" value="3">  все
        </td>
       </msh:row>
      <msh:row>
        <td class="label" title="Отображать  (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Отображать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="1">  Реестр прикрепленных
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeGroup" value="2">  Общий свод
        </td>
        
       </msh:row>
  
       <msh:row>
       
	   </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" value="Найти" />
           
          </td>
      </msh:row>
      <script type="text/javascript" src="./dwr/interface/AttachmentService.js"></script>
      <script type="text/javascript">
      checkFieldUpdate('typeAttachment','${typeAttachment}',3) ;
      checkFieldUpdate('typeAge','${typeAge}',3) ;
      checkFieldUpdate('typeCompany','${typeCompany}',3) ;
      checkFieldUpdate('typeGroup','${typeGroup}',0) ;
      checkFieldUpdate('typeSex','${typeSex}',3) ;
     
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
      </script>
    </msh:panel>
    
    </msh:form>
    
   

    
    <%
    String date = (String)request.getParameter("period") ;
    String date1 = (String)request.getParameter("periodTo") ;
    String sqlAdd = (String)request.getAttribute("sqlAdd");
	

   if (sqlAdd!=null &&date!=null && !date.equals(""))  {
	   
    	if (date1==null ||date1.equals("")) {
    		request.setAttribute("periodTo", date);
    	} else {
    		request.setAttribute("periodTo", date1) ;
    	}
    	

    %>
<%if (typeGroup!=null && typeGroup.equals("1")) {%>
    
	   
   <ecom:webQuery nameFldSql="journal_ticket_sql" name="journal_ticket" maxResult="240" nativeSql="
		select lp.id,p.lastname,p.firstname,case when p.middlename='' or p.middlename='Х' or p.middlename is null then 'НЕТ' else p.middlename end as middlename,to_char(p.birthday,'dd.MM.yyyy') as birthday
    	 , case when lp.id is null then 'Территориально' else coalesce(vat.name,'2') end as f5_spprik
    	 , case when lp.id is null then '01.01.2013' else coalesce(to_char(lp.dateFrom,'dd.MM.yyyy'),'01.01.2013') end as f6_tprik
    	 ,coalesce(a.fullname)||' ' || case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end 
    	 ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end  	||case 
when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end as f7_address
    	  from LpuAttachedByDepartment lp
    	 left join Patient p on lp.patient_id=p.id
    	 left join vocsex vs on vs.id=p.sex_id
    	 left join address2 a on a.addressid=p.address_addressid
    	 left join MisLpu ml1 on ml1.id=p.lpu_id
    	 left join MisLpu ml2 on ml2.id=lp.lpu_id
         left join VocAttachedType vat on lp.attachedType_id=vat.id

   		where (p.noActuality='0' or p.noActuality is null) and p.deathDate is null and lp.dateto is null ${sqlAdd} 
   		group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,lp.id,lp.dateFrom,vat.name
   		,a.fullname,p.houseNumber,p.houseBuilding,p.flatNumber
    	 order by p.lastname,p.firstname,p.middlename,p.birthday  " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    
    <form action="print-bypass_journal1.do" method="post" target="_blank">
Реестр прикрепленного населения за период ${param.period}-${param.periodTo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр прикрепленного населения за период с ${param.period} по ${param.periodTo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
        <msh:table name="journal_ticket" action="/javascript:void(0)" idField="1" noDataMessage="Не найдено">
			<msh:tableColumn columnName="#" property="sn"/>
			<msh:tableColumn columnName="Фамилия" property="2"/>
			<msh:tableColumn columnName="Имя" property="3"/>
			<msh:tableColumn columnName="Отчетство" property="4"/>
			<msh:tableColumn columnName="Дата рождения" property="5"/>
			<msh:tableColumn columnName="Способ прикрепления" property="6"/>
			<msh:tableColumn columnName="Дата прикрепления" property="7"/>
			<msh:tableColumn columnName="Адрес регистрации" property="8"/>
        </msh:table>
    <%} else if (typeGroup!=null&& typeGroup.equals("2")) {%>
<ecom:webQuery nameFldSql="journal_ticket_sql" name="journal_ticket" maxResult="240" nativeSql="
		select to_char(p.birthday,'yyyy')
		,count(lp.id)
		,sum(case when vs.omccode='1' then 1 else null end )as sexM
		,sum(case when vs.omccode='2' then 1 else null end )as sexF
    	 from LpuAttachedByDepartment lp
    	 left join Patient p on lp.patient_id=p.id
    	 left join vocsex vs on vs.id=p.sex_id
    	 left join MisLpu ml1 on ml1.id=p.lpu_id
    	 left join MisLpu ml2 on ml2.id=lp.lpu_id
         left join VocAttachedType vat on lp.attachedType_id=vat.id
         left join reg_ic smo on smo.id=lp.company_id

   		where (p.noActuality='0' or p.noActuality is null) and p.deathDate is null and lp.dateto is null ${sqlAdd} group by to_char(p.birthday,'yyyy')
    	 order by to_char(p.birthday,'yyyy')  " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
   
        <msh:table name="journal_ticket" action="entityView-mis_lpuAttachedByDepartment.do" idField="1" noDataMessage="Не найдено">
			<msh:tableColumn columnName="#" property="sn"/>
			<msh:tableColumn  columnName="Год рождения" property="1"/>
			<msh:tableColumn isCalcAmount="true" columnName="Количество человек" property="2"/>
			<msh:tableColumn isCalcAmount="true" columnName="из них - М" property="3"/>
			<msh:tableColumn isCalcAmount="true" columnName="из них - Ж" property="4"/>
			</msh:table>
    	<%}  else {%>
    	Группировка какая?
    	<%} }%>
   
     
    <script type="text/javascript">
   
   </script> 
  </tiles:put>
</tiles:insert>