<%-- edit.jsp mis_protocolKili Комиссия по летальным исходам --%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
<tiles:put name="style" type="string">
        </tiles:put>
  <tiles:put name="body" type="string">

  <msh:ifFormTypeIsNotView formName="mis_protocolKiliForm">		<!-- Начало формы, которая не является формой просмотра записей -->
  <!-- Запрос дефектов -->
  <ecom:webQuery name="defectList" nativeSql="select pkd.id as pkdId, pkd.defecttext, case when pkd.isdefectfound='1' then '1' else '0' end 
	, vkd.id as vkdId, vkd.name
 	from Vockilidefect vkd
 	left join protocolkilidefect pkd on vkd.id=pkd.defect_id and pkd.protocol_id=${param.id}"/>
	<ecom:webQuery name="GetIDProtocol" nativeSql="SELECT pk.id as protocolID
	FROM ProtocolKili pk WHERE pk.deathcase_id=${param.id}"/>
	<ecom:webQuery name="GetProfileByID" nameFldSql="GetProfileByID_sql" nativeSql="SELECT mlpu.name FROM deathcase dc 
LEFT JOIN medcase mc ON mc.id = dc.medcase_id 
LEFT JOIN mislpu mlpu ON  mlpu.id = mc.department_id 
WHERE dc.id = ${param.id}"/>
 
  </msh:ifFormTypeIsNotView>
  
  <!--
  List IDProtocol = (List)request.getAttribute("GetIDProtocol");

  out.print("<input type ='hidden' id='IDProtocol' value='"+ IDProtocol.get(0) +"'>");
  -->
<%
	
	 StringBuilder temp = new StringBuilder();
	 List listDefect = (List)request.getAttribute("defectList"); 
	 if (listDefect!= null && !listDefect.isEmpty()) {//аттрибутом является запрос (webQuery) с именем DefectFound
		temp.append("<TABLE>");
	for(int i=0;i<listDefect.size();i++) {	//в цикле выводим строки с элементами формы (в данном случае 3 строки)
		temp.append("<tr>");
		WebQueryResult row =(WebQueryResult) listDefect.get(i); 	//результаты запроса помещаются в элемент WebQueryResult row
		temp.append("<input type='hidden' id='defect"+i+"' name='defect"+i+"' value='"+(row.get1()!=null?row.get1():"")+"'>");
		temp.append("<input type='hidden' id='vocDefect"+i+"' name='vocDefect"+i+"' value='"+(row.get4()!=null?row.get4():"")+"'>");
		temp.append("<td>"+(row.get5()!=null?row.get5():"")+"</td>");
		temp.append("<td>Выявлен дефект<input type='checkBox' onclick='setDefectFound("+i+")' id='chkDefect"+i+"'"+(row.get3().toString().equals("1")?" checked='checked'":"")+">"+"</td>");
		temp.append("<td><input type='text' name='defectText"+i+"' id='defectText"+i+"' value='"+(row.get2()!=null?row.get2():"")+"' style='display:"+(row.get3().toString().equals("1")?"block":"none")+"'></td>");
		temp.append("</tr>");
	}  
	temp.append("</TABLE>");
	temp.append("<input type ='hidden' id='defectListSize' value='"+ listDefect.size()+"'>");
	  request.setAttribute("tableHTML", temp.toString());
	 }
%>
  
<msh:form action="entityParentSaveGoView-mis_protocolKili.do" defaultField="protocolDate">
	<msh:hidden property="id" />
	<msh:hidden property="deathCase" />
	<msh:hidden property="saveType" />
	<msh:hidden property="defectSaveList" />
	<msh:panel>
	
		<msh:row><msh:textField property="protocolNumber" label="Номер протокола"/></msh:row>
	<msh:row><msh:textField property="protocolDate"/></msh:row>
	<msh:row><msh:autoComplete property="conclusion" vocName="vocKiliConclusion" fieldColSpan="4" size="60"/></msh:row>
        
<msh:ifFormTypeIsView formName="mis_protocolKiliForm">

	<msh:section title="Обследования" >
	<ecom:webQuery name="defectList" nativeSql="select pkd.id as pkdId, pkd.defecttext, case when pkd.isdefectfound='1' then '+' else '-' end 
	, vkd.id as vkdId, vkd.name
		from Vockilidefect vkd
		left join protocolkilidefect pkd on vkd.id=pkd.defect_id and pkd.protocol_id=${param.id}"/>
	<!-- Вывод информации о дефектах в протоколе КИЛИ -->
	<msh:table name="defectList" action="javascript:void(0)" idField="1">
		<msh:tableColumn columnName="Тип дефекта" property="5"/>
		<msh:tableColumn columnName="Выявлено?" property="3"/>
		<msh:tableColumn columnName="Дефект" property="2"/>
	</msh:table>
 	</msh:section>
</msh:ifFormTypeIsView>
  	${tableHTML}
  	
  	<msh:row>
<msh:textField property="protocolComment" size="50"/>
	</msh:row>
  	
	<msh:row><msh:separator label="Дополнительная информация" colSpan="4"/>
	</msh:row>
	<table>
	<msh:row>
	<msh:label property="createDate" label="Дата создания"/>
	<msh:label property="createTime" label="Время" labelColSpan="2"/>
	</msh:row><msh:row>
	<msh:label property="createUsername" label="Пользователь"/>
	</msh:row><msh:row>
	<msh:label property="editDate" label="Дата редактирования" />
	<msh:label property="editTime" label="Время" labelColSpan="2"/>
	</msh:row><msh:row>
	<msh:label property="editUsername" label="Пользователь"/></msh:row>
	</table>
	
	
        
	<msh:submitCancelButtonsRow colSpan="2" functionSubmit="saveData() ;"/> 
    </msh:panel>
</msh:form>
    
<!-- <msh:ifFormTypeIsView formName="mis_protocolKiliForm" /> -->
	</tiles:put>
	
	
<tiles:put name="side" type="string">
	<msh:sideMenu title="Протокол КИЛИ">
	<msh:ifFormTypeIsView formName="mis_protocolKiliForm">
	<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_protocolKili" name="Изменить" roles="/Policy/Mis/MedCase/ProtocolKili/Edit" />
	<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-mis_protocolKili" name="Удалить" confirm="Удалить протокол?" roles="/Policy/Mis/MedCase/ProtocolKili/Delete" />
	</msh:ifFormTypeIsView>
	</msh:sideMenu> 
		
</tiles:put>

<tiles:put name="title" type="string">
	<ecom:titleTrail mainMenu="Lpu" beginForm="mis_protocolKiliForm" />
</tiles:put>

<tiles:put name="javascript" type="string">
	<!-- Скрипт отображения текстового поля соседнего с чек-боксом -->
	<script type="text/javascript">
	function setDefectFound(i){
	

	if ($('chkDefect'+i).checked){
		$('defectText'+i).style = "display: block";
		}
	  else {
		  $('defectText'+i).style = "display: none";
		}
	};
	</script>
	<!-- Скрипт передачи информации в edit.jsp -->
	<script type="text/javascript">
	function saveData(){
		//$('protocolID').value = ${param.id};
		//alert($('protocolID').value);
		//alert($('deathCase').value);
		var str = "";
		var ret = 1;
		if ($('defectListSize')) {
			  
			  for (var i=0;i<$('defectListSize').value;i++) {
				  str +="" +$('defect'+i).value;
				  str +="@@"+ $('vocDefect'+i).value;
				  if ($('chkDefect'+i).checked && ($('defectText'+i)).value.trim()== ""){
					  alert("Заполните поле отмеченного дефекта!");
					  ret = 0;
					  break;
				  }
				  if (!$('chkDefect'+i).checked){
					  str +="@@0";
					  str +="@@''";
				  }
				  else {
					  str +="@@1";
					  str +="@@"+$('defectText'+i).value;
				  }
				  //str +0="@@" $('vocDefect'+i).value;
				  
				  str+="##";
			  }
		  }
		  //var str = "1:" + $('def_type') + "#2:" + $('def_detected') + "#3:" + $('def_text');
		  if (ret == 1){
			  $('defectSaveList').value = str!=''?str.substring(0, str.length-2):'';
			  document.forms["mainForm"].action="entityParentSaveGoView-mis_protocolKili.do" ;
			  document.forms["mainForm"].submit() ;  
		  }
		  else {
			  document.forms["mainForm"].submitButton.disabled = false ;
		  }
		  
	}
	</script>
</tiles:put>
</tiles:insert>