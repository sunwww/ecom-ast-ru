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
    String typeRead =ActionUtil.updateParameter("PatientAttachment","typeRead","1", request) ;
	String typeView=ActionUtil.updateParameter("PatientAttachment","typeView","1", request) ;
	String typeAge=ActionUtil.updateParameter("PatientAttachment","typeAge","3", request) ;
	String typeAttachment=ActionUtil.updateParameter("PatientAttachment","typeAttachment","3", request) ;
	String typeChange=ActionUtil.updateParameter("PatientAttachment","typeChange","1", request) ;
	String typeDefect=ActionUtil.updateParameter("PatientAttachment","typeDefect","3", request) ;
  %>
  
    <msh:form action="/mis_attachment.do" defaultField="lpuName" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
   
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
       <msh:row guid="a7a62505-2bfe-41b6-a54f-217b970dc0c3">
        <msh:autoComplete property="lpu" vocName="lpu" label="ЛПУ" viewAction="entityEdit-mis_lpu.do" fieldColSpan="7" guid="67d2a4af-71bc-4a19-8844-4a59b97fabda" horizontalFill="true" />
      </msh:row>
		        <msh:row styleId='rowLpuArea'>
		            <msh:autoComplete fieldColSpan="3" property="area" label="Участок" horizontalFill="true"
		                              parentAutocomplete="lpu" vocName="lpuAreaWithParent"/>
		        </msh:row>		
     <msh:row>
        <msh:textField fieldColSpan="2" property="numberPackage" label="№пакета" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="numberReestr" label="Реестровый номер" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
      <msh:row>
        <msh:textField  property="period" label="Период с" />
        <msh:textField  property="periodTo" label="до" />
      </msh:row>
      <msh:row>
        <msh:checkBox property="noCheckLpu" label="Не учитывать ЛПУ"/>
       </msh:row>
      <msh:row>
        <td class="label" title="Возраст  (typeAge)" colspan="1"><label for="typeAgeName" id="typeAgeLabel">Возраст:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAge" value="1">  до 18 лет
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
      <msh:row>
        <td class="label" title="Отобразить  (typeDefect)" colspan="1"><label for="typeDefectName" id="typeDefectLabel">Отображать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDefect" value="1">  дефектные
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDefect" value="2">  без дефектов 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDefect" value="3">  все прикрепления
	        </td>
	        
       </msh:row>
      <msh:row>
        <td class="label" title="Список  (typeChange)" colspan="1"><label for="typeChangeName" id="typeChangeLabel">Список:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeChange" value="1">  изменения за период
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeChange" value="2">  без ограничения нач.периода
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeChange" value="3">  полностью база пациентов
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Пациенты  (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отображать пациентов:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  прикрепленных (все)
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2">  пациентов без адресов
	        </td>
       </msh:row>
       <msh:row>
        <msh:textField  property="changedDateFrom" label="Измененные с" />
      </msh:row>
      <msh:row>
        <td class="label" title="Пациенты  (typeRead)" colspan="1"><label for="typeReadName" id="typeReadLabel">Отображать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeRead" value="1">  xml-файл
        </td>
	    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	    	<input type="radio" name="typeRead" value="2">  на экране
	    </td>
       </msh:row>
       
       
       <msh:row>
       	<msh:hidden property="filename"/>
       	<td colspan="4">
       		Файл <span id='aView'></span>
       	</td>
       </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
      <script type="text/javascript">
      checkFieldUpdate('typeRead','${typeRead}',1) ;
      checkFieldUpdate('typeAttachment','${typeAttachment}',3) ;
      checkFieldUpdate('typeView','${typeView}',1) ;
      checkFieldUpdate('typeAge','${typeAge}',3) ;
      checkFieldUpdate('typeDefect','${typeDefect}',3) ;
      checkFieldUpdate('typeChange','${typeChange}',1) ;
      
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
    if (sqlAdd!=null &&date!=null && !date.equals("") && typeRead!=null)  {
    	if (date1==null ||date1.equals("")) {
    		request.setAttribute("periodTo", date);
    	} else {
    		request.setAttribute("periodTo", date1) ;
    	}
    	

    %>

    
	 <%    if (typeRead!=null && (typeRead.equals("2"))) {%>
   <ecom:webQuery nameFldSql="journal_ticket_sql" name="journal_ticket" maxResult="100" nativeSql="
		select lp.id,p.lastname,p.firstname,case when p.middlename='' or p.middlename='Х' or p.middlename is null then 'НЕТ' else p.middlename end as middlename,to_char(p.birthday,'yyyy-mm-dd') as birthday
    	 , p.commonNumber
    	 , case when lp.id is null then '1' else coalesce(vat.code,'2') end as spprik
    	 , case when lp.id is null then '2013-01-01' else coalesce(to_char(lp.dateFrom,'yyyy-mm-dd'),'2013-04-01') end as tprik
    	 , to_char(lp.dateTo,'yyyy-mm-dd') as otkprikdate
         , case when lp.dateTo is null then 'Прикреплен' else 'Откреплен' end as otkorprik
         , lp.defectperiod
         , lp.defecttext
    	 from LpuAttachedByDepartment lp
    	 left join Patient p on lp.patient_id=p.id
    	 left join MisLpu ml1 on ml1.id=p.lpu_id
    	 left join MisLpu ml2 on ml2.id=lp.lpu_id
         left join VocAttachedType vat on lp.attachedType_id=vat.id

   		where (p.noActuality='0' or p.noActuality is null) and p.deathDate is null ${sqlAdd} group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.snils,p.commonNumber,lp.id,lp.dateFrom,lp.dateTo,vat.code, lp.defectperiod
         , lp.defecttext
    	 order by p.lastname,p.firstname,p.middlename,p.birthday  " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    
        <msh:table viewUrl="entityParentView-mis_lpuAttachedByDepartment.do" editUrl="entityParentView-mis_lpuAttachedByDepartment.do" deleteUrl="entityParentDeleteGoParentView-mis_lpuAttachedByDepartment.do" name="journal_ticket" action="entityView-mis_lpuAttachedByDepartment.do" idField="1" noDataMessage="Не найдено">
			<msh:tableColumn columnName="#" property="sn"/>
			<msh:tableColumn columnName="Фамилия" property="2"/>
			<msh:tableColumn columnName="Имя" property="3"/>
			<msh:tableColumn columnName="Отчетство" property="4"/>
			<msh:tableColumn columnName="Дата рождения" property="5"/>
			<msh:tableColumn columnName="RZ" property="6"/>
			<msh:tableColumn columnName="Способ прикрепления" property="7"/>
			<msh:tableColumn columnName="Дата прикрепления" property="8"/>
			<msh:tableColumn columnName="Дата открепления" property="9"/>
			<msh:tableColumn columnName="Прикреплен\откреплен" property="10"/>
			<msh:tableColumn columnName="Период дефекта" property="11"/>
			<msh:tableColumn columnName="Код дефекта" property="12"/>
        </msh:table>
    <% 
    }} else {%>
    	<i>Введите данные </i>
   <%}%>
     
  </tiles:put>
</tiles:insert>