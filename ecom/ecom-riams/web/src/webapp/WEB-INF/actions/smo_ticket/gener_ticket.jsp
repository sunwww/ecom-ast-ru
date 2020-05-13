<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="" defaultField="dateStart">


      <msh:panel colsWidth="1%,1%,1%,97%">
     
          <msh:row>
              <msh:textField property="patientIds" label="ИД пациентов (через запятую)" fieldColSpan="4" size="100"/>
          </msh:row>
        <msh:row>
          <msh:textField label="Дата начала" property="dateStart" fieldColSpan="1" />
          <msh:textField label="Дата окончания" property="dateFinish" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:textField label="Возраст с" property="ageFrom" fieldColSpan="1" />
          <msh:textField label="Возраст по" property="ageTo" fieldColSpan="1" />
          <msh:row>
          <msh:autoComplete property="lpu" vocName="lpu" label="ЛПУ прикрепления"  fieldColSpan="4" size="100"/>
          </msh:row>
</msh:row>
        <msh:row>
          <msh:textField label="диагнозы (через запятую)" property="diagnosisList" fieldColSpan="4"  size="100"/>
          
        </msh:row>
                  <msh:textField label="Количество визитов" property="recordCount" fieldColSpan="4" size="100"/>
	      <msh:row>
	        <msh:autoComplete vocName="workFunction" property="workFunctionExecute" label="Специалист"  fieldColSpan="4" size="100"/>
	      	
	      	  </msh:row>
          <msh:row><input type="button" onclick="addWorkFunction()" value="Добавить еще специалиста"></msh:row>
	      	  <msh:row>
	      	  <td>ИД специалистов</td><td colspan="5">
	      	<input type = 'text' title="Рабочие функции(ID, через запятую)"  name="wfList" id='wfList' placeholder="Рабочие функции (через запятую)" >
	      	</td>
	      </msh:row>
	        
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Вид оплаты" horizontalFill="true" />
          <msh:autoComplete vocName="vocWorkPlaceType" property="workPlaceType" label="Место обслуживания" horizontalFill="true" />
          </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocSex" property="sex" label="Пол" horizontalFill="true" />
        </msh:row>
     
        <msh:row>
          <msh:autoComplete vocName="vocReason" property="visitReason" label="Цель посещения" horizontalFill="true" />
          <msh:autoComplete vocName="vocVisitResult" property="visitResult" label="Результат обращения" horizontalFill="true" />
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