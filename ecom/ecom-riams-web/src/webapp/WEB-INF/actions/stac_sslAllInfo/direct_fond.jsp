<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал для отправки данных в фонд"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  
	String typeView=ActionUtil.updateParameter("HospitalDirectDataInFond","typeView","1", request) ;
	String typeDate=ActionUtil.updateParameter("HospitalDirectDataInFond","typeDate","1", request) ;
	String typeMode=ActionUtil.updateParameter("HospitalDirectDataInFond","typeMode","1", request) ;
  %>
  
    <msh:form action="/stac_direct_in_fond.do" defaultField="lpu" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel>
   
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
      	<msh:textField property="lpu"/>
      </msh:row>
     <msh:row>
        <msh:textField fieldColSpan="2" property="numberPackage" label="№пакета" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="numberReestr" label="Реестровый номер" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
      <msh:row>
        <msh:textField  property="period" label="Период с" />
         <msh:textField  property="periodTo" label="по" /> 
      </msh:row>
      <msh:row>
      	<td class="label" title="Режим  (typeMode)" colspan="1"><label for="typeModeName" id="typeModeLabel">Режим:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkMode()">
        	<input type="radio" name="typeMode" value="1"> Формирования Xml
        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeMode" value="2"> Импорт результата
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeMode" value="3"> Работа с данными
	        </td>
      </msh:row>
      </msh:panel>
      <msh:panel styleId="exportXml">
      <msh:row>
        <td class="label" title="Тип xml  (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Тип xml:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1"> (N1) направления на госп.
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2"> (N2) госпитализации по направлению
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="3"> (N3) экстр. госпитализация
	        </td>
       </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="4"> (N4) аннулирование направ. на госп.
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="5"> (N5) выбывшие из стац.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="6"> (N6) наличие своб. мест
	        </td>
       </msh:row>
       <msh:row>
        <td></td>
        
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="7">  список неопред. по N1
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="8">  N1 + N3 таблицы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="9">  N4 + N5 таблицы
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
            <input type="submit" value="Сформировать файлы" />
          </td>
      </msh:row>
          </msh:panel>
          <msh:panel styleId="workData">
           <msh:row>
               <td class="label" title="Список  (typeDate)" colspan="1"><label for="typeDateName" 
               id="typeDateLabel">Список:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDate" value="1"> проверять по базе импортированных из фонда
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDate" value="2">  брать данные из базы напрямую
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDate" value="3">  отобразить не вошедших в импортированную базу
	        </td>
       </msh:row>
             <msh:row styleId="exportXml4">
           <td colspan="11">
            <input type="submit" value="Отобразить данные" />
          </td>
      </msh:row>
          </msh:panel>
    </msh:form>
       <msh:form action="stac_direct_in_fond_import.do"  defaultField="isClear" fileTransferSupports="true">
			            <msh:hidden property="saveType"/>
			 			<msh:panel styleId="importXml">
			                <msh:row>
			                    <td>Файл *.xml</td>
			                    <td colspan="1">
			                        <input type="file" name="file" id="file" size="50" value="" onchange="">
			                        <input type="button" name="run_import" value="Импорт"  onclick="this.form.submit()" />
			                    </td>
			                </msh:row>
			                	<msh:row>
			                	<td colspan="4" align="center">
			                		
			                	</td>
			                	</msh:row>
			                	
			            </msh:panel>
			        </msh:form>
      <script type="text/javascript">
      checkFieldUpdate('typeView','${typeView}',1) ;
      checkFieldUpdate('typeDate','${typeDate}',1) ;
      checkFieldUpdate('typeMode','${typeMode}',1) ;
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
     
  $('aView').innerHTML=$('filename').value ;
  
   function checkMode() {
	   var chk =  document.forms[0].typeMode ;
	   if (chk[0].checked) {
		   //alert("exportXml") ;
		   showTable("exportXml", true ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", false ) ; 
	   } else if (chk[1].checked) {
		   //alert("importXml") ;
		   showTable("exportXml", false ) ; 
		   showTable("importXml", true ) ; 
		   showTable("workData", false ) ; 
	   } else {
		   //alert("workData") ;
		   showTable("exportXml", false ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", true ) ; 
	   }
   }
   function showTable(aTableId, aCanShow ) {
		//alert(aTableId) ;
		try {
			//alert( aCanShow ? 'table' : 'none') ;
			$(aTableId).style.display = aCanShow ? 'table' : 'none' ;
		} catch (e) {
			// for IE
			//alert(aCanShow ? 'block' : 'none') ;
			try{
			$(aTableId).style.display = aCanShow ? 'block' : 'none' ;
			}catch(e) {}
		}	
	}
	checkMode() ;
    </script>

  </tiles:put>
</tiles:insert>