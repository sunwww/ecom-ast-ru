<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="" defaultField="dateStart" guid="77bf3d00-cfc6-49eb-9751-76e82d38751c">


      <msh:panel colsWidth="1%,1%,1%,97%">
     
          <msh:row>
              <msh:textField property="patientIds" label="ИД пациентов (через запятую)" fieldColSpan="4" size="100"/>
          </msh:row>
        <msh:row guid="59560d9f-0765-4df0-bfb7-9a90b5eed824">
          <msh:textField label="Дата начала" property="dateStart" fieldColSpan="1" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" />
          <msh:textField label="Дата окончания" property="dateFinish" fieldColSpan="1" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" />
        </msh:row>
        <msh:row>
          <msh:textField label="Возраст с" property="ageFrom" fieldColSpan="1" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" />
          <msh:textField label="Возраст по" property="ageTo" fieldColSpan="1" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" />
          <msh:row>
          <msh:autoComplete property="lpu" vocName="lpu" label="ЛПУ прикрепления"  fieldColSpan="4" size="100"/>
          </msh:row>
</msh:row>
        <msh:row>
          <msh:textField label="диагнозы (через запятую)" property="diagnosisList" fieldColSpan="4" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca"  size="100"/>
          
        </msh:row>
                  <msh:textField label="Количество визитов" property="recordCount" fieldColSpan="4" guid="9e3a8e0d-cd82-4158-b764-e15cb16b4fca" size="100"/>
	      <msh:row guid="47073a0b-da87-49e0-9ff0-711dc597ce07">
	        <msh:autoComplete vocName="workFunction" property="workFunctionExecute" label="Специалист" guid="a8404201-1bae-467e-b3e9-5cef63411d01"  fieldColSpan="4" size="100"/>
	      	
	      	  </msh:row>
          <msh:row><input type="button" onclick="addWorkFunction()" value="Добавить еще специалиста"></msh:row>
	      	  <msh:row>
	      	  <td>ИД специалистов</td><td colspan="5">
	      	<input type = 'text' title="Рабочие функции(ID, через запятую)"  name="wfList" id='wfList' placeholder="Рабочие функции (через запятую)" >
	      	</td>
	      </msh:row>
	        
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Вид оплаты" horizontalFill="true" guid="e5ac1267-bc69-44b2-8aba-b7455ac064c4" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="workPlaceType" label="Место обслуживания" horizontalFill="true" guid="18063077-15e8-4e4a-8679-ff79de589a72" />
          </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocSex" property="sex" label="Пол" horizontalFill="true" guid="18063077-15e8-4e4a-8679-ff79de589a72" />
        </msh:row>
     
        <msh:row guid="6d8642e8-756a-482f-a561-a9b474ef6c50">
          <msh:autoComplete vocName="vocReason" property="visitReason" label="Цель посещения" horizontalFill="true" guid="3632a2ed-6ecb-446f-8ae3-fe047f091076" />
          <msh:autoComplete vocName="vocVisitResult" property="visitResult" label="Результат обращения" horizontalFill="true" guid="4346bd08-5fe2-4f01-9065-93a66cdfc1dd" />
        </msh:row>
       
     
        <msh:row>
          <msh:autoComplete vocName="vocIllnesPrimary" property="concludingActuity" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>

      <input type = 'button' value='Генерировать' onclick="generate()">
        </msh:panel>
    </msh:form>
  
 


    </tiles:put>

  <tiles:put name="title" type="string">
   
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/TicketService.js"></script>
   <script type="text/javascript" >

       function addWorkFunction() {
           var val = $('workFunctionExecute').value;
           if (+val>0) {
             if ($('wfList').value!="") $('wfList').value+=",";
               $('wfList').value+=val;
               $('workFunctionExecute').value=0;
               $('workFunctionExecuteName').value="";
           }
       }

   function generate() {
       var val = $('workFunctionExecute').value;
	   if ($('wfList').value!=null&&$('wfList').value!='') {
		   val = $('wfList').value;
	   }
	   var isProfOsmotr = confirm("Формировать профосмотры (все визиты будут в рамках одного СПО)?");
	   TicketService.generateTalons (val,$('dateStart').value, $('dateFinish').value, "", $('serviceStream').value
			   ,$('workPlaceType').value, $('visitReason').value,$('visitResult').value
			   ,$('diagnosisList').value, $('concludingActuity').value, $('recordCount').value
			   ,$('ageFrom').value,$('ageTo').value, $('sex').value,$('lpu').value,$('patientIds').value, isProfOsmotr,{
		   callback: function (a) {
			   alert (a);
		   }
	   });
   }
   </script>
 
</tiles:put>
</tiles:insert>