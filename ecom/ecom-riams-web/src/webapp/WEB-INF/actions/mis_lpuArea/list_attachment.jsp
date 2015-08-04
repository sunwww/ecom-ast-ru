
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал прикрепленного населения"/>
  </tiles:put>
<%--     <tiles:put name='side' type='string'>
        <msh:sideMenu>
        	<msh:sideLink action="/mis_attachmentUpload.do" name="Импорт прик. населения"/>
         </msh:sideMenu>
    </tiles:put> --%>
  <tiles:put name="body" type="string">
  <%
    String typeRead =ActionUtil.updateParameter("PatientAttachment","typeRead","1", request) ;
	String typeView=ActionUtil.updateParameter("PatientAttachment","typeView","1", request) ;
	String typeAge=ActionUtil.updateParameter("PatientAttachment","typeAge","3", request) ;
	String typeAttachment=ActionUtil.updateParameter("PatientAttachment","typeAttachment","3", request) ;
	String typeChange=ActionUtil.updateParameter("PatientAttachment","typeChange","1", request) ;
	String typeDefect=ActionUtil.updateParameter("PatientAttachment","typeDefect","3", request) ;
	String typeCompany=ActionUtil.updateParameter("PatientAttachment","typeCompany","3", request) ;
	String typeAreaCheck=ActionUtil.updateParameter("PatientAttachment","typeAreaCheck","3", request) ;
	String typeWork=ActionUtil.updateParameter("PatientAttachment","typeWork","1", request) ;
	String typeDivide=ActionUtil.updateParameter("PatientAttachemnt","typeDivide","1",request) ;
	//String typeXmlFormat = ActionUtil.updateParameter("PatientAttachment", "typeXmlFormat", "2", request);

  %>
  
    <msh:form  action="/mis_attachment.do" defaultField="lpuName" disableFormDataConfirm="true" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f" >
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
       <a href='mis_bypass_report.do'> 
       		<input type="button" value="Работа с прик. населением" /> 
       </a>
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
        <msh:textField fieldColSpan="2" property="numberPackage" label="№пакета" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="numberReestr" label="Реестровый номер" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
      <msh:row>
        <msh:textField  property="period" label="Период с" />
        <msh:textField  property="periodTo" label="до" />
      </msh:row>
      <msh:row>
        <msh:checkBox property="noCheckLpu" label="Не учитывать ЛПУ" />
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
       <td class="label" title="База  (typeWork)" colspan="1"><label for="typeWorkName" id="typeWorkLabel">База:</label></td>
         <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeWork" value="1"> системная
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeWork" value="2"> фонда 
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
       <msh:panel colsWidth="fondTable">
       
      <msh:row>
        <td class="label" title="Пациенты  (typePatientFond)" colspan="1"><label for="typePatientFondName" id="typePatientFondLabel">Пациенты (ДЛЯ БАЗЫ ПО ФОНДУ!):</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatientFond" value="1">  есть в базе
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typePatientFond" value="2">  нет в базе 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typePatientFond" value="3">  все без ограничения
	        </td>
	        
       </msh:row>
      <msh:row>
        <td class="label" title="Участок  (typeAreaCheck)" colspan="1"><label for="typeAreaCheckName" id="typeAreaCheckLabel">Прикрепление к участку:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAreaCheck" value="1">  существует
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeAreaCheck" value="2">  нет прикрепления к участку 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeAreaCheck" value="3">  все без ограничения
	        </td>
	        
       </msh:row>
              
       </msh:panel>
       <msh:panel colsWidth="systemTable">

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
        <td class="label" title="Разбивать по СК  (typeDivide)" colspan="1"><label for="typeDivideName" id="typeDivideLabel">Разбивать по СК:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDivide" value="1">  Разбивать
        </td>
	    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	    	<input type="radio" name="typeDivide" value="2">  Не разбивать
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
        <msh:textField  property="changedDateFrom" label="Измененные с" />
      </msh:row>
      <msh:row>
        <td class="label" title="Пациенты  (typeRead)" colspan="1"><label for="typeReadName" id="typeReadLabel">Отображать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeRead" value="1">  xml-файл
        </td>
	    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	    	<input type="radio" name="typeRead" value="2">  на экране (первые 250 записей)
	    </td>
       </msh:row>
  
       <msh:row>
       
	   </msh:row>
       <msh:row>
      	 <td colspan="11">  <label>Импорт дефектов: </label>    	
            <input type="file"  name="filenameDefect" id="filenameDefect" size="50" value="Импорт дефектов" onchange="importDefects(event)">
          
           <!--  <input type="button" name="run_import" value="Импорт дефектов"  onclick="this.form.submit()" /> -->
       	 </td>
       	
       </msh:row>
     <%--   <msh:row>
       <td>
         <html:file property="attachmentFile" />
         </td>
       </msh:row> --%>
       <msh:row>
       	<msh:hidden property="filename" />
       	<td colspan="4">
       		Файл <span id='aView'></span>
       	</td>
       </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" value="Найти" />
           
          </td>
      </msh:row>
        
      <msh:row>
           <td colspan="11" align="right">
            <input type="button" onclick="document.getElementById('attachmentDiv').style.display='block'"  value="X" />
          </td>
          </msh:row><msh:row>
          <td align="left">
       <div id="attachmentDiv" style="display: none">
       <p style="color: red">Внимание! Не стоит нажимать эти кнопки без необходимости</p>
	     <input type="button" name="create_att" value="Создать прикрепления"  onclick="createAttachments()" /> 
	     <input type="button" name="update_IC" value="Обновить страх. компании"  onclick="updateInsCompany()" /> 
	    </div>
	    </td>
      </msh:row>
      <table id="defectTable" border="1" style="padding: 15px; display: none">
       <tr style="color: black">
        	<td colspan="4">Протокол импорта дефектов:</td>
       </tr>
       <tr>
        	<td>#</td>
        	<td>Пациент</td>
        	<td>Прикрепление</td>
        	<td>Описание</td>
        </tr>
         <tbody id="defectElements" >
        </tbody>
        </table>
      <script type="text/javascript" src="./dwr/interface/AttachmentService.js"></script>
      <script type="text/javascript">
      checkFieldUpdate('typeRead','${typeRead}',1) ;
      checkFieldUpdate('typeAttachment','${typeAttachment}',3) ;
      checkFieldUpdate('typeView','${typeView}',1) ;
      checkFieldUpdate('typeAge','${typeAge}',3) ;
      checkFieldUpdate('typeDefect','${typeDefect}',3) ;
      checkFieldUpdate('typeChange','${typeChange}',1) ;
      checkFieldUpdate('typeCompany','${typeCompany}',3) ;
      checkFieldUpdate('typePatientFond','${typePatientFond}',3) ;
      checkFieldUpdate('typeDivide','${typeDivide}',1) ;
      checkFieldUpdate('typeWork','${typeWork}',1) ;
      checkFieldUpdate('typeAreaCheck','${typeAreaCheck}',3) ;
      //checkFieldUpdate('typeXmlFormat', '${typeXmlFormat}',1);
      $('aView').innerHTML=$('filename').value ;
     
    	var text="";
      function createAttachments() {
    	  if (confirm("Процедуру необходимо проделывать ТОЛЬКО ОДИН РАЗ, вы уверены?")){
    		  AttachmentService.createAttachmentFromPatient('NO', {
        		  callback: function (aResult) {
        			  alert(""+aResult);
        		  }
        	  });  
    	  }
    	  
    	  
      }
      
      function updateInsCompany () {
    	  if (confirm("Будут обновлены данные страх. компаний в прикреплениях, вы уверены?")){
    	  AttachmentService.setInsuranceCompany('NO', {
    		  callback: function (aResult) {
    			  alert(""+aResult);
    		  }
    	  });
      }
      }
    	var importDefects = function(event) {
    	  var input = event.target;
    	  var reader = new FileReader();
    	  reader.onload = function() {
    		  text = reader.result;
    	//	  alert(text);
    		  AttachmentService.importDefectsFromXML (text, {
    			  callback: function(aResult) {
    				  if (aResult!=null & aResult!="") {
    					  flushTable();
    					  aResult=aResult.substring(0,aResult.length-1);
    					  var arr = aResult.split("#");
    					  for (var i=0;i<arr.length;i++) {
    						  addRow(arr[i]);
    					  }
    					  $('defectTable').style.display='block';
    					  
    				  }
    			  }
    		  });
    	  }
    	  reader.readAsText(input.files[0],'CP1251');
    	  
    	//  AttachmentService.importDefectsFromXML($'file').value;
      }

      function flushTable() {
    	  var table = document.getElementById("defectElements");
    	  var aRows = table.childNodes;
    	  if (aRows.length>1) {
    		  var j=aRows.length;
    		  for (var i=1;i<j;i++) {
    			  table.deleteRow(0);
    		  }
    	  }
    	  
      }
      function addRow (aRow) {
    	  var aData = aRow.split(":"); // #:pat_id:att_id:Comment 
     	
    	  var tbody = document.getElementById('defectElements');
        var row = document.createElement("TR");
        row.style.color=""+aData[0];
        tbody.appendChild(row);
        
        // Создаем ячейки в вышесозданной строке 
        // и добавляем тх 
        var td1 = document.createElement("TD");
     
        var td2 = document.createElement("TD");
        ///td2.colSpan="2";
        var td3 = document.createElement("TD");
        var td4 = document.createElement("TD");
        
    	 row.appendChild(td1);
    	 row.appendChild(td2);
    	 row.appendChild(td3);
    	 row.appendChild(td4);
       
        // Наполняем ячейки  
        td1.innerHTML = "<span> "+aData[1]+"</span>";
        if (aData[2]!=null &&aData[2]!="null"){
        	td2.innerHTML = "<a href='/riams/entityView-mis_patient.do?id="+aData[2]+"' target='_blank'><span>\t"+aData[2]+"</span></a>";
        }
        td3.innerHTML = "<a href='/riams/entityView-mis_lpuAttachedByDepartment.do?id="+aData[3]+"' target='_blank'><span>\t"+aData[3]+"</span></a>";
        td4.innerHTML = "<span> "+aData[4]+"</span>";
       	//td4.innerHTML = "<span> "+aData[3]+"</span>";
       
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
      </script>
    </msh:panel>
     <%if (request.getAttribute("defectWQR")!=null){ %>
     <p style="color:red">Внимание! Следующие записи не выгружены!</p>
         <msh:table   viewUrl="entityParentView-mis_lpuAttachedByDepartment.do" editUrl="entityParentView-mis_lpuAttachedByDepartment.do" deleteUrl="entityParentDeleteGoParentView-mis_lpuAttachedByDepartment.do" name="defectWQR" action="entityView-mis_lpuAttachedByDepartment.do" idField="2" noDataMessage="Не найдено">
			<msh:tableColumn columnName="Ошибка" property="3"/>
        </msh:table>
            <%} %>
    </msh:form>
    
   

    
    <%
    String date = (String)request.getParameter("period") ;
    String date1 = (String)request.getParameter("periodTo") ;
    String sqlAdd = (String)request.getAttribute("sqlAdd");
    String exportDefects = (String)request.getAttribute("exportDefects");

    if (sqlAdd!=null &&date!=null && !date.equals("") && typeRead!=null)  {
    	if (date1==null ||date1.equals("")) {
    		request.setAttribute("periodTo", date);
    	} else {
    		request.setAttribute("periodTo", date1) ;
    	}
    	

    %>

    
	 <%    if (typeRead!=null && (typeRead.equals("2"))) {%>
   <ecom:webQuery nameFldSql="journal_ticket_sql" name="journal_ticket" maxResult="250" nativeSql="
		select lp.id,p.lastname,p.firstname,case when p.middlename='' or p.middlename='Х' or p.middlename is null then 'НЕТ' else p.middlename end as middlename,to_char(p.birthday,'dd.MM.yyyy') as birthday
    	 , p.commonNumber
    	 , case when lp.id is null then '1' else coalesce(vat.code,'2') end as spprik
    	 , case when lp.id is null then '01.01.2013' else coalesce(to_char(lp.dateFrom,'dd.MM.yyyy'),'01.01.2014') end as tprik
    	 , to_char(lp.dateTo,'dd.MM.yyyy') as otkprikdate
         , case when lp.dateTo is null then 'Прикреплен' else 'Откреплен' end as otkorprik
         , lp.defectperiod
         , lp.defecttext
         ,smo.name
    	 from LpuAttachedByDepartment lp
    	 left join Patient p on lp.patient_id=p.id
    	 left join MisLpu ml1 on ml1.id=p.lpu_id
    	 left join MisLpu ml2 on ml2.id=lp.lpu_id
         left join VocAttachedType vat on lp.attachedType_id=vat.id
         left join reg_ic smo on smo.id=lp.company_id

   		where (p.noActuality='0' or p.noActuality is null) and p.deathDate is null ${sqlAdd} group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.snils,p.commonNumber,lp.id,lp.dateFrom,lp.dateTo,vat.code, lp.defectperiod
         , lp.defecttext, smo.name
    	 order by p.lastname,p.firstname,p.middlename,p.birthday  " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    
        <msh:table   viewUrl="entityParentView-mis_lpuAttachedByDepartment.do" editUrl="entityParentView-mis_lpuAttachedByDepartment.do" deleteUrl="entityParentDeleteGoParentView-mis_lpuAttachedByDepartment.do" name="journal_ticket" action="entityView-mis_lpuAttachedByDepartment.do" idField="1" noDataMessage="Не найдено">
			<msh:tableColumn columnName="#" property="sn"/>
			<msh:tableColumn columnName="Фамилия" property="2"/>
			<msh:tableColumn columnName="Имя" property="3"/>
			<msh:tableColumn columnName="Отчетство" property="4"/>
			<msh:tableColumn columnName="Дата рождения" property="5"/>
			<msh:tableColumn columnName="RZ" property="6"/>
			<msh:tableColumn columnName="Способ прикрепления" property="7"/>
			<msh:tableColumn columnName="Дата прикрепления" property="8"/>
			<msh:tableColumn columnName="Дата открепления" property="9"/>
			<msh:tableColumn columnName="Статус" property="10"/>
			<msh:tableColumn columnName="Дата импорта" property="11"/>
			<msh:tableColumn columnName="Дефект" property="12"/>
			<msh:tableColumn columnName="Страх. компания" property="13"/>
        </msh:table>
    <% 
    }} else {%>
    	<i>Введите данные </i>
   <%}%>
     
    <script type="text/javascript">
   
   </script> 
  </tiles:put>
</tiles:insert>