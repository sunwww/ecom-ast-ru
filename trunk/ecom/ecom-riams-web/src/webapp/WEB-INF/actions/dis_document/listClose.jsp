<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
        <msh:title mainMenu="Disability">Закрытые документы за период</msh:title>
  </tiles:put>
    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="closeDNT"/>
    </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/dis_documentClose.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
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
        	<input type="radio" name="typeDate" value="2">  посл.продл.нетруд.
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="3">  выдачи
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Статус документа  (typeDocument)" colspan="1"><label for="typeDateName" id="typeDateLabel">Статус документа:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDocument" value="1">  открытые
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDocument" value="2">  закрытые
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDocument" value="3">  все
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="ЛПУ выдачи  (typeLpu)" colspan="1"><label for="typeLpuName" id="typeLpuLabel">ЛПУ выдачи:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeLpu" value="1">  текущее
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeLpu" value="2">  из других ЛПУ
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeLpu" value="3">  все
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Искать в документах  (noActualityt)" colspan="1"><label for="noActualityName" id="noActualityLabel">Искать в документах:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="noActuality" value="1">  испорченных
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="noActuality" value="2">  неиспорченных
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="noActuality" value="3">  во всех
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
      <msh:row>
      	<msh:autoComplete property="disabilityReason" fieldColSpan="3" size="6" horizontalFill="true"
      		label="Причина нетруд." vocName="vocDisabilityReason"
      	/>
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="closeReason" fieldColSpan="3" horizontalFill="true"
      		label="Причина закрытия" vocName="vocDisabilityDocumentCloseReason"
      	/>
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="primarity" fieldColSpan="3" horizontalFill="true"
      		label="Первичность" vocName="vocDisabilityDocumentPrimarity"
      	/>
      </msh:row>
      <msh:row>
      	<msh:textField property="sn" label="Порядковый номер для печати" labelColSpan="2" fieldColSpan="4"/>
      </msh:row>
      <msh:row >
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
           <td>
            <input type="submit" onclick="print()" value="Печать" />
            <input type="submit" onclick="printNoActuality()" value="Печать испорченных" />
          </td>
      </msh:row>
      <msh:row>
           <td>
            <input type="submit" onclick="create16vn()" value="Сформировать 16-ВН" />
          </td>
           <td>
            <input type="button" onclick="showForm()" value="Экспорт в ФСС" />
          </td>
           <td>
            <input type="button" onclick="clickForm()" value="click" />
          </td>
          </msh:row>
      <input type="hidden" value="DisabilityService" name="s"/>
      <input type="hidden" value="printJournal" name="m"/>
    </msh:panel>
    <div id="formExportDiv" style="display: none">
	<msh:panel styleId="formExport">
	<table>
		<tr><td></td><td></td></tr>
		<tr>
			<msh:autoComplete vocName="mainLpu" property="lpu" label="ЛПУ" size="30" />
		</tr>
		<tr>
			<msh:autoComplete vocName="workFunction" property="workFunction" label="Исполнитель" size="30" />
		</tr>
		<tr>
			<msh:textField property="packetNumber" label="Порядковый номер пакета" />
		</tr>
	</table>
	<table>
	  <tr>	
        <td>
            <input type="button" onclick="exportLNByDate()" value="Экспортировать" />
          </td></tr><tr>
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
    <msh:sectionTitle>Результаты поиска обращений за  ${period}. ${infoSearch} ${anotherlpuinfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select dd.id,dd.issueDate as date1
    ,(select min(dr2.dateFrom) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id) as date2
    ,(select max(dr2.dateTo) from disabilityrecord as dr2 where dr2.disabilitydocument_id=dd.id) as date3  
    ,dd.number
    , vds.name||' '||case when dd.duplicate_id is not null then ' заменен на '||dupl.number else '' end as vdsname
    ,vddp.name as vddpname,vdr.name as vdrname
    ,vddcr.name as vddcrname,case when cast(dd.isclose as int)=1 then 'Да' else 'НЕТ' end
    
    	,p.lastname||' '||p.firstname||' '||p.middlename as pat 
     	from disabilitydocument as dd 
	   	left join disabilitycase dc on dc.id=dd.disabilityCase_id
	   	left join disabilitydocument dupl on dupl.id=dd.duplicate_id
	   	left join VocDisabilityStatus vds on vds.id=dd.status_id
	   	left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
	   	left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id 
	   	left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
    	left join patient p on p.id=dc.patient_id
     	where ${status} ${statusNoActuality} ${dateGroup } between cast('${beginDate}' as date) and cast('${endDate}' as date) ${disReason} ${closeReason} ${primarity} ${anotherlpu} order by ${orderBystatus} "/>
    <msh:table viewUrl="entityShortView-dis_document.do" name="journal_priem" action="entityParentView-dis_document.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Дата выдачи" property="2"/>
      <msh:tableColumn columnName="Номер" property="5"/>
      <msh:tableColumn columnName="Пациент" property="11"/>
      <msh:tableColumn columnName="Статус" property="6"/>
      <msh:tableColumn columnName="Первичность" property="7"/>
      <msh:tableColumn columnName="Причина нетруд." property="8"/>
      <msh:tableColumn columnName="Дата начала" property="3"/>
      <msh:tableColumn columnName="Дата послед. продл." property="4"/>
      <msh:tableColumn columnName="Причина закрытия" property="9"/>
      <msh:tableColumn columnName="Закрыт?" property="10"/>
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
    
    checkFieldUpdate('typeDate','${typeDate}',3) ;
    checkFieldUpdate('noActuality','${noActuality}',3) ;
    checkFieldUpdate('typeDocument','${typeDocument}',3) ;
    checkFieldUpdate('orderBy','${orderBy}',1) ;
    checkFieldUpdate('typeLpu','${typeLpu}',1) ;
    
   	function clickForm() {
   		alert($('socCode').value);
   	}
    
    function showForm() {
    	
    	if ($('formExportDiv').style.display=='none') {
    		$('formExportDiv').style.display='block';
    	} else {
    		$('formExportDiv').style.display='none';
    	}
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
    	frm.action='dis_documentClose.do' ;
    }
    function create16vn() {
    	var frm = document.forms[0] ;
    	//alert('ewqe') ;
    	frm.target='' ;
    	frm.action='dis_create16vn.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.m.value='printJournal' ;
    	frm.action='print-disabilityJournal.do' ;
    }
    function printNoActuality() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.m.value='printNoActuality' ;
    	frm.action='print-disabilityJournalByNo.do' ;
    }
    
    function exportLNByDate() {
    	if ($('beginDate').value=='' ) {
    		alert ('Заполните дату начала!');
    		return;
    	}
    	if ($('socCode').value=='' ) {
    		alert ('Заполните рег. номер в ФСС!');
    		return;
    	}
		$('aView').innerHTML="Подождите..." ;
		$('aViewTD').style.display="block";
    	DisabilityService.exportLNByDate($('beginDate').value, $('endDate').value, $('socCode').value, $('lpu').value, $('packetNumber').value, {
    		callback: function (aResult) {
    			if (aResult!=null) {
    				var aData = aResult.split("@");
    				$('aView').innerHTML="<a href='../rtf/"+aData[0]+"' target='_blank'>"+aData[0]+"</a>" ;
    				if (aData[1].length>0){
    					
        				aData[1] = aData[1].substring(0,aData[1].length-1);
    	    			var rows = aData[1].split("#");
    	    			flushTable();
    	    			for (var i=0;i<rows.length;i++) {
    	    				addRow (rows[i]);
    	    			}
    	    			$('exportTable').style.display = 'block' ;
    				}
    			} else {
    				alert ("Ошибка: "+aResult);
    			}
    			
    		//	var res = aResult.split("@");
    			
    		//	alert (res[0]);
    		}
    	});	
    }
    function flushTable() {
  	  var table = document.getElementById("exportElements");
  	  var aRows = table.childNodes;
  	  if (aRows.length>1) {
  		  for (var i=0;i<aRows.length;i++) {
  			  table.deleteRow(0);
  		  }
  	  }
  	  
    }
    var firstRow=1;
    function addRow (aRow) {
  	  var aData = aRow.split(":"); // ID:fullname:Diagnosis:Comment 
   	
  	  var tbody = document.getElementById('exportElements');
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

