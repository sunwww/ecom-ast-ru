<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
        <msh:title mainMenu="Disability">Журнал экспорта за период</msh:title>
  </tiles:put>
    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="exportDNT"/>
    </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/dis_documentExport.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Поиск по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1">  начала нетруд.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="2">  выдачи
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Статус документа  (typeDocument)" colspan="1"><label for="typeDateName" id="typeDateLabel">Статус документа:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDocument" value="1">  все экспортированные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDocument" value="2">  экспортированные без ошибок
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDocument" value="3">  дефекты
        </td>
      </msh:row>
            <msh:row>
        <td class="label" title="Сортировать  (orderBy)" colspan="1"><label for="ordeByName" id="orderByLabel">Сортировка:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="orderBy" value="1">  по номеру
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="orderBy" value="2">  по дате
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="orderBy" value="3">  по ФИО
        </td>
      </msh:row>
      <msh:row >
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
      </msh:row>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Импорт данных" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
             <msh:row>
          	 <td colspan="3"> 
          <label>Импорт файла с ФСС:</label>     	
            <input type="file"  name="filenameDefect" id="filenameDefect" size="50" value="Импорт дефектов" onchange="importDefects(event)">
          </td>
          </msh:row>
      <form action="/dis_documentExport.do" name="uploadForm" target="hiddenframe" enctype="multipart/form-data" method="post">
  <%--      <msh:row>
          	 <td colspan="3"> 
          <label>Импорт файла с ФСС(IFRAME):</label>     	
            <input type="file"  name="filenameImport" id="filenameImport" size="50" value="Импорт " onchange="document.uploadForm.submit()">
          </td>
          </msh:row>
      <msh:row>
           <td>
            <input type="button" onclick="textAjax()" value="textAjax" />
          </td>
          </msh:row>  --%>
        </form> 
      <input type="hidden" value="DisabilityService" name="s"/>
      <input type="hidden" value="printJournal" name="m"/>
       <table id="importTable" border="1" style="padding: 15px; display: none">
       <tr>
        	<td colspan="4">Протокол импорта:</td>
       </tr>
       <tr>
        	<td>#</td>
        	<td>Номер ЛН</td>
        	<td>Результат</td>
        	<td>Текст дефекта</td>
        </tr>
         <tbody id="importElements" >
        </tbody>
        </table>
    </msh:panel>
    <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Экспорт данных" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
    <div id="formExportDiv" style="display: block">
	<msh:panel styleId="formExport">
	<table>
		<tr><td></td><td></td></tr>
		<tr>
			<msh:autoComplete vocName="mainLpu" property="lpu" label="ЛПУ" size="50" />
		</tr>
		<tr>
			<msh:autoComplete vocName="workFunction" property="workFunction" label="Исполнитель" size="50" />
		</tr>
		<tr>
			<msh:textField property="packetNumber" label="Порядковый номер пакета" />
		</tr>
	</table>
	<table>
	  <tr>	
        <td colspan="3"><label>Экспорт данных в ФСС:</label>
            <input type="button" onclick="exportLNByDate()" value="Экспорт" />
          </td>
         
       	 </tr><tr>
          <td id='aViewTD' style="display: none">
       		Файл <span id='aView'></span>
       	</td></tr></table>
      <table id="exportTable" border="1" style="padding: 15px; display: none">
       <tr style="color: red">
        	<td colspan="2">Внимание! Следующие записи не выгружены!</td>
       </tr>
       <tr>
        	<td>Номер ЛН</td>
        	<td>Ошибка</td>
        </tr>
         <tbody id="exportElements" >
        </tbody>
        </table>
     
		</msh:panel>
	</div>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("beginDate") ;
    String date1 = (String)request.getAttribute("endDate") ;
    String valid = (String) request.getAttribute("valid") ;
    if (valid!=null && !valid.equals("0")) {
    	
    	if (date1==null || date1.equals("")) {
    		date1=date ;
    	}
    	request.setAttribute("endDate", date1) ;
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений за период: с ${beginDate} по ${endDate} . ${infoSearch} </msh:sectionTitle>
    
    <msh:sectionContent>
    <ecom:webQuery nameFldSql="journal_priemSQL" name="journal_priem" nativeSql="select dd.id,dd.issueDate as date1
    ,(select min(dr2.dateFrom) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id) as date2
    ,(select max(dr2.dateTo) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id) as date3  
    ,dd.number
    , vds.name||' '||case when dd.duplicate_id is not null then ' заменен на '||dupl.number else '' end as vdsname
    ,vddp.name as vddpname,vdr.name as vdrname
    ,vddcr.name as vddcrname,case when cast(dd.isclose as int)=1 then 'Да' else 'НЕТ' end
    
    	,p.lastname||' '||p.firstname||' '||p.middlename as pat
    	,dd.exportdate
		,dd.exportdefect 
     	from disabilitydocument as dd 
	   	left join disabilitycase dc on dc.id=dd.disabilityCase_id
	   	left join disabilitydocument dupl on dupl.id=dd.duplicate_id
	   	left join VocDisabilityStatus vds on vds.id=dd.status_id
	   	left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
	   	left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id 
	   	left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
    	left join patient p on p.id=dc.patient_id
     	where ${status}  ${dateGroup } between cast('${beginDate}' as date) and cast('${endDate}' as date) ${disReason} ${closeReason} ${primarity} ${anotherlpu} order by ${orderBystatus} "/>
    <msh:table viewUrl="entityShortView-dis_document.do" name="journal_priem" action="entityParentView-dis_document.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Дата выдачи" property="2"/>
      <msh:tableColumn columnName="Номер" property="5"/>
      <msh:tableColumn columnName="Пациент" property="11"/>
      <msh:tableColumn columnName="Статус" property="6"/>
      <msh:tableColumn columnName="Причина нетруд." property="8"/>
      <msh:tableColumn columnName="Дата начала" property="3"/>
      <msh:tableColumn columnName="Дата послед. продл." property="4"/>
      <msh:tableColumn columnName="Дата экспорта" property="12"/>
      <msh:tableColumn columnName="Текст дефекта" property="13"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
  </tiles:put>
  <tiles:put type="string" name="javascript">
   	<script type="text/javascript" src="./dwr/interface/DisabilityService.js"></script>
    <script type='text/javascript'>
    var typeDocument = document.forms[0].typeDocument ;
    var typeDate = document.forms[0].typeDate ;
    var noActuality = document.forms[0].noActuality ;
    
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typeDocument','${typeDocument}',3) ;
    checkFieldUpdate('orderBy','${orderBy}',1) ;

	
    function getXmlHttp() {
    	var xmlHttp;
    	try {
    		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
    	} catch (E) {
    		try{
    		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    		}catch (E) {
        		xmlHttp = false;
        	}
    	}
    	if (!xmlHttp && typeof XMLHttpRequest!='undefined') {
    		xmlHttp = new XMLHttpRequest();
    	}
    	return xmlHttp;
    }
    function textAjax() {
    	var xmlHttp = getXmlHttp();
    	xmlHttp.open('GET', '/riams/mis_patients.do',true);
    	xmlHttp.onreadystatechange=function () {
    		if (xmlHttp.readyState==4) {
    			if (xmlHttp.status==200) {
    				alert (xmlHttp.responseText);
    			}
    		}
    	};
    	xmlHttp.send(null);
    }
    function checkFieldUpdate(aField,aValue,aDefault) {
    	eval('var chk =  document.forms[0].'+aField) ;
    	var aMax = chk.length ;
    	if (aValue==null || aValue=="") aValue=aDefault ;
    	if ((+aValue)>aMax) {
    		chk[+aMax-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }

    

    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='dis_documentExport.do' ;
    }

    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.m.value='printJournal' ;
    	frm.action='print-disabilityJournal.do' ;
    }

	var text="";
    var importDefects = function(event) {
  	  var input = event.target;
  	  var reader = new FileReader();
  	  reader.onload = function() {
  		  text = reader.result;
  		  DisabilityService.analyseExportLN (text, {
  			  callback: function(aResult) {
  				  if (aResult!=null & aResult!="") {
    				var aData = aResult.split("#");
    				flushTable("importElements");
    				if (aData.length>0){
    					for (var i=0;i<aData.length;i++) {
    						addImportRow (aData[i],"importElements");
    	    			}
    	    			$('exportTable').style.display = 'none' ;
    	    			$('importTable').style.display = 'block' ;
    				}
    			}
  			  }
  		  });
  	  }
  	  reader.readAsText(input.files[0],'UTF8');
  	  
    }
    function exportLNByDate() {
    	if ($('beginDate').value=='' ) {
    		alert ('Заполните дату начала!');
    		return;
    	}
    	if ($('lpu').value=='') {
    		alert ('Выберите ЛПУ!');
    		return;    		
    	}
    	if ($('workFunction').value=='') {
    		alert ('Выберите исполнителя!');
    		return;
    	}
		$('aView').innerHTML="Подождите..." ;
		$('aViewTD').style.display="block";
    	DisabilityService.exportLNByDate($('beginDate').value, $('endDate').value, $('lpu').value, $('workFunction').value, $('packetNumber').value, {
    		callback: function (aResult) {
    			if (aResult!=null) {
    				$('exportTable').style.display = 'none' ;
    				var aData = aResult.split("@");
    				$('aView').innerHTML="<a href='../rtf/"+aData[0]+"' target='_blank'>"+aData[0]+"</a>" ;
    				flushTable("exportElements");
    				if (aData[1].length>0){
    					
        				aData[1] = aData[1].substring(0,aData[1].length-1);
    	    			var rows = aData[1].split("#");
    	    			
    	    			for (var i=0;i<rows.length;i++) {
    	    				addRow (rows[i], "exportElements");
    	    			}
    	    			$('exportTable').style.display = 'block' ;
    				}
    			} else {
    				alert ("Ошибка: "+aResult);
    			}
    		}
    	});	
    }
    function flushTable(name) {
  	  var table = document.getElementById(name);
  	  var aRows = table.childNodes;
  	  if (aRows.length>1) {
  		  var j=aRows.length;
  		  for (var i=1;i<j;i++) {
  			  table.deleteRow(0);
  		  }
  	  }
  	  
    }
    var firstRow=1;
    function addImportRow (aRow, aName) {
    	  var aData = aRow.split(":"); // ID:fullname:Diagnosis:Comment 
     	
    	  var tbody = document.getElementById(aName);
        var row = document.createElement("TR");
    	tbody.appendChild(row);
        
        var td1 = document.createElement("TD");
        var td2 = document.createElement("TD");
        var td3 = document.createElement("TD");
        var td4 = document.createElement("TD");
        row.appendChild(td1);
    	row.appendChild(td2);
    	row.appendChild(td3);
    	row.appendChild(td4);
    	 
       // Наполняем ячейки  
        td1.innerHTML = "<span> "+aData[0]+"</span>"; 
        td2.innerHTML = "<a href='/riams/entityParentView-dis_document.do?id="+aData[1]+"' target='_blank'><span>\t"+aData[2]+"</span></a>";
        td3.innerHTML = "<span> "+aData[3]+"</span>";
        td4.innerHTML = "<span> "+aData[4]+"</span>";

        
       
      }
    function addRow (aRow, aName) {
  	  var aData = aRow.split(":"); // ID:fullname:Diagnosis:Comment 
   	
  	  var tbody = document.getElementById(aName);
      var row = document.createElement("TR");
  	//row.id = type+"Element"+num;
      tbody.appendChild(row);
      
      // Создаем ячейки в вышесозданной строке 
      // и добавляем тх 

      var td1 = document.createElement("TD");
      var td2 = document.createElement("TD");
           ///td2.colSpan="2";
      
  	 row.appendChild(td1);
  	 row.appendChild(td2);
  	 
     
      // Наполняем ячейки  
      td1.innerHTML = "<a href='/riams/entityParentView-dis_document.do?id="+aData[1]+"' target='_blank'><span>\t"+aData[0]+"</span></a>";
      td2.innerHTML = "<span> "+aData[2]+"</span>";

      
     
    }
    </script>
  </tiles:put>
  
</tiles:insert>

