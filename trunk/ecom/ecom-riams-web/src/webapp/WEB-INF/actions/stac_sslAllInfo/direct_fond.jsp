<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал для отправки данных в фонд"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  
	String typeView=ActionUtil.updateParameter("HospitalDirectDataInFond","typeView","1", request) ;
  %>
  
    <msh:form action="/stac_direct_in_fond.do" defaultField="lpuName" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
   
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
      	<msh:textField property="lpu"/>
      </msh:row>
      <!-- 
       <msh:row guid="a7a62505-2bfe-41b6-a54f-217b970dc0c3">
        <msh:autoComplete property="lpu" vocName="lpu" label="ЛПУ" viewAction="entityEdit-mis_lpu.do" fieldColSpan="7" guid="67d2a4af-71bc-4a19-8844-4a59b97fabda" horizontalFill="true" />
      </msh:row>
       -->
     <msh:row>
        <msh:textField fieldColSpan="2" property="numberPackage" label="№пакета" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="numberReestr" label="Реестровый номер" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
      <msh:row>
        <msh:textField  property="period" label="Период с" />
<!--         <msh:textField  property="periodTo" label="до" /> -->
      </msh:row>
      <%--
      <msh:row>
        <msh:checkBox property="noCheckLpu" label="Не учитывать ЛПУ"/>
       </msh:row>
        --%>
      <msh:row>
        <td class="label" title="Список  (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Список:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  направления на госп.
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2">  госпитализации по направлению
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="3">  экстр. госпитализация
	        </td>
       </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="4">  аннулирование направ. на госп.
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="5">  выбывшие из стац.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="6">  наличие своб. мест
	        </td>
       </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="7">  все
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
      </script>
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
    //checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    //checkFieldUpdate('typeView','${typeView}',1) ;
  $('aView').innerHTML=$('filename').value ;
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

  </tiles:put>
</tiles:insert>