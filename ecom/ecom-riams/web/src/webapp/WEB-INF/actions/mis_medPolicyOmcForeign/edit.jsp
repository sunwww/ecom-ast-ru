<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_medPolicyOmcForeign.do" defaultField="typeName" guid="f8ccb30e-95c8-463b-a15a-b09362ef0bd1">
      <msh:hidden property="id" guid="5baf6c78-1239-4a89-9c94-8f623e19a2ab" />
      <msh:hidden property="saveType" guid="dba00ec3-b59b-4a5b-9ca0-b20e244e5c68" />
      <msh:hidden property="patient" guid="704091c6-c582-4866-9918-973cbda50492" />
      <msh:panel guid="5ac7e026-6e6c-45be-8dc8-0b7edc6f157e">
        <msh:row guid="0573f75f-dcb2-4640-9fc2-e69e7f463d34">
          <msh:textField property="lastname" label="Фамилия" horizontalFill="true" size="30" guid="82ed7c52-cfb3-4153-bc8a-4cdc62d2cf8c" />
          <msh:textField property="firstname" label="Имя" horizontalFill="true" size="30" guid="23fbced6-f48c-4f43-8c85-4935fa681d89" />
          <msh:textField property="middlename" label="Отчество" horizontalFill="true" size="30" guid="063fa4b8-ff94-41a1-8ef7-3703a879014f" />
        </msh:row>
        <msh:row>
            <msh:autoComplete vocName="vocMedPolicyOmc" property="type" label="Тип полиса" horizontalFill="true" fieldColSpan="5"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="commonNumber" label="Единый номер" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row guid="80606a0a-0d51-4571-92c2-a164554431de">
		  <msh:textField property="series" label="Серия" horizontalFill="true" size="30" guid="0hd6-d6ed-4ddf-91b0-hf8ee" />
          <msh:textField property="polNumber" label="Номер" horizontalFill="true" size="30" guid="07400ed6-d6ed-4ddf-91b0-8c8cdb5df8ee" />
        </msh:row>
        <msh:row guid="911564dd-d4ee-445c-bf76-cdbda804c9f3">
          <msh:separator label="Страховая компания" colSpan="5" guid="0dd1e796-9671-4bb7-971c-f6a0b636ed9e" />
        </msh:row>
        <msh:row guid="8fd1df42-4b30-4943-93a9-3ae66c03a3e1">
          <msh:autoComplete vocName="omcKodTer" property="insuranceCompanyArea" fieldColSpan="5" horizontalFill="true" guid="8467544c-cc5d-4e9a-9daf-805b20f316e5" label="Область нахождения СМО" />
        </msh:row>
       <msh:row>
          <msh:autoComplete parentAutocomplete="insuranceCompanyArea" vocName="omcSprSmo" property='insuranceCompanyCode' fieldColSpan="5" horizontalFill="true" label="ОГРН СМО"/>
        </msh:row>
        <msh:ifFormTypeIsView formName="mis_medPolicyOmcForeignForm">
        	<msh:row>
        		<msh:textField property="ogrn" label="ОГРН" fieldColSpan="5" horizontalFill="true"/>
        	</msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete vocName="vocInsuranceCompanyAll" property="company" label="Страховая&nbsp;компания" horizontalFill="true" fieldColSpan="5"/>
        </msh:row>
        <msh:row>
          <msh:textField  property="insuranceCompanyCity" fieldColSpan="5" horizontalFill="true" guid="410bf207-c461-4be4-bbf5-a5d5f8a21f5d" />
        </msh:row>

        <msh:row>
          <msh:textField property="insuranceCompanyName" viewOnlyField="true" fieldColSpan="5" horizontalFill="true" guid="44682fdf-001f-4e02-a532-06f3008b950f" />
        </msh:row>
        <msh:row guid="2cbb416c-2610-4eea-ba68-fca5916565d5">
          <msh:textField property="actualDateFrom" label="Дата действия с" guid="d7c90d82-baa2-4e68-823b-25cec7f1a3d6" />
          <msh:textField property="actualDateTo" label="по" guid="d08295a1-ee40-40c0-a646-81a2e25266d0" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="2d879919-003c-4065-b06b-50f09ff8157f" />
      </msh:panel>
      	<msh:section>
    	<div id='changeType' style='display:none'>
     		<msh:separator label="Изменить тип полиса" colSpan="10"></msh:separator>
      		<msh:autoComplete label="Новый тип" vocName="vocMedPolicy" property="changePolicyType" fieldColSpan="10" size="50" />
      		<input type='button' value="Изменить тип"  onclick="changePolicyTypef()">
      	</div>
	</msh:section>
    </msh:form>
        <msh:ifFormTypeIsView formName="mis_medPolicyOmcForeignForm">
        	<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/View">
        		<ecom:webQuery name="list_sls" nativeSql="select m.id ,p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'DD.MM.YYYY')||'г.' ,d.name as depname ,ss.code as sscode , m.dateStart as mdateStart,m.dateFinish as mdateFinish from medCase_medPolicy as mp left join MedCase as m on m.id=mp.medcase_id left join medcase as h on h.id=m.parent_id  left join statisticstub as ss on ss.id=m.statisticstub_id  left join bedfund as bf on bf.id=m.bedfund_id  left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id  left join vocbedtype as vbt on vbt.id=bf.bedtype_id  left join mislpu as d on d.id=m.department_id  left join patient as p on p.id=m.patient_id  where mp.policies_id='${param.id}' and m.DTYPE='HospitalMedCase'  order by m.dateStart"/>
        		<msh:tableNotEmpty name="list_sls">
	        		<msh:section title="Прикрепленные случаи лечения госпитализации">
		        		<msh:table name="list_sls" action="entityParentView-stac_ssl.do" idField="1">
		        			<msh:tableColumn property="2" columnName="Пациент" />
		        			<msh:tableColumn property="3" columnName="Отделение" />
		        			<msh:tableColumn property="4" columnName="Номер стат.карты" />
		        			<msh:tableColumn property="5" columnName="Дата начала" />
		        			<msh:tableColumn property="6" columnName="Дата окончания" />
		        		</msh:table>
	        		</msh:section>
        		</msh:tableNotEmpty>
        	</msh:ifInRole>
        </msh:ifFormTypeIsView>  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="f81fcf11-00d1-4d02-b665-7caaf3b77a1c">
      <msh:ifFormTypeIsView formName="mis_medPolicyOmcForeignForm" guid="3012c1ab-ec8f-45e8-aa70-d1bcec1c2467">
        <msh:sideLink roles="/Policy/Mis/MedPolicy/OmcForeign/Edit" key="ALT+2" params="id" action="/entityParentEdit-mis_medPolicyOmcForeign" name="Изменить" guid="fa2cd07b-3ed5-45a7-9671-d7719ccf846d" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeAreViewOrEdit formName="mis_medPolicyOmcForeignForm" guid="d5749c79-5a21-44da-b2de-7775f32b23ff">
        <msh:sideLink roles="/Policy/Mis/MedPolicy/OmcForeign/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-mis_medPolicyOmcForeign" name="Удалить" confirm="Удалить полис?" guid="5b21852d-2e5a-47d9-99ad-f2e5a8b623a3" />
                <msh:sideLink
	    			roles="/Policy/Mis/MedCase/Stac/Journal/ReceivedWithoutPolicy" key="ALT+9"
	    			action="/stac_receivedWithoutPolicy_list" name="По госпитализациям без прикрепленных полисов"
	    			styleId="stac_receivedWithoutPolicy"
	    		/>
	    <msh:ifFormTypeIsNotView formName="mis_medPolicyOmcForeignForm">
	    	<msh:sideLink roles="/Policy/Mis/MedPolicy/OmcForeign/Edit" action="/javascript:{$('changeType').style.display='block';}" name="Изменить тип полиса" guid="d3d19781-f1b0-42b3-a314-f5e6a2b55584" />
      </msh:ifFormTypeIsNotView>
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_medPolicyOmcForeignForm" guid="45adb273-615e-482f-b598-0beb37a2bac0" />
  </tiles:put>
  
    <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="mis_medPolicyOmcForeignForm">
    	<script type="text/javascript">
    		
    		insuranceCompanyCodeAutocomplete.addOnChangeCallback(function() {
	      	 	update() ;
	      	 	updateCompany() ;
	      	 });
    		companyAutocomplete.addOnChangeCallback(function(){
    			updateOgrn() ;
    		});
	      	 function update() {
	      	 	var text ;
	      	 	if ($('insuranceCompanyCodeName').value!='') {
		      	 	text = $('insuranceCompanyCodeName').value ;
	      	 	} else {
	      	 		
	      	 		var ind=$('companyName').value.indexOf(' ') ;
	      	 		if (ind!=-1) {
	      	 				text = $('companyName').value.substring(ind+1) ;
	      	 		} else {
	      	 			text = $('companyName').value ;
	      	 		}
	      	 		
	      	 	}
	      	 	$('insuranceCompanyName').value=text;
   				$('insuranceCompanyNameReadOnly').value=text;
		      	
	      	 }
	      	 function updateOgrn() {
	      		PatientService.getCodefByRegIcForeign(
	      				$('insuranceCompanyArea').value
	      				,$('company').value
	      				,{
	      			callback:function(aResult) {
	      				if (aResult!='') {
	      					var sp = aResult.split('#') ;
		      				$('insuranceCompanyCode').value = sp[0] ; 
			      			$('insuranceCompanyCodeName').value= sp[1] ;
	      				} else {
		      				$('insuranceCompanyCode').value = '0' ; 
			      			$('insuranceCompanyCodeName').value= '' ;
	      				}
	      				update() ;
	      			}
	      		}) ;
	      	 }
	      	 function updateCompany() {
	      		PatientService.getRegIcForeignByCodef(
	      				$('insuranceCompanyCode').value
	      				,{
	      			callback:function(aResult) {
	      				if (aResult!='') {
	      					var sp = aResult.split('#') ;
		      				$('company').value = sp[0] ; 
			      			$('companyName').value= sp[1] ;
	      				} else {
		      				$('company').value = '0' ; 
			      			$('companyName').value= '' ;	      					
	      				}
	      				update() ;
	      			}
	      		}) ;
	      	 }
    	</script>

	    	<script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
	    	<script type="text/javascript">
	    		function isRequeredRZ() {
	    			PatientService.getCodeByMedPolicyOmc($('type').value, {
	                    callback: function(aResult) {
	                 	  if (+aResult==3) {
	                 		 $('commonNumber').className=" horizontalFill required";
	                 	  } else {
	                 		 $('commonNumber').className=" horizontalFill ";
	                 	  }
	                    }
	 	        	});
	    		}
	    		
	    		 function changePolicyTypef() {
		    		 if ($('changePolicyType').value!=null&&$('changePolicyType').value!=''){
		    		PatientService.changeMedPolicyType($('id').value, $('changePolicyType').value,{
		    			callback: function(){
		    				alert ('Тип полиса изменен!');
		    				document.location='entityView-mis_patient.do?id='+$('patient').value;
		    			}});
		    		} else {
		    			alert ('Укажите тип полиса');
		    		} 
		    		
		    		
		    	}
	    		 
	    		isRequeredRZ() ;
	    		typeAutocomplete.addOnChangeCallback(function() {isRequeredRZ() ;}) ;
	    	</script>
	    </msh:ifFormTypeIsNotView>
    </tiles:put>
    
  

</tiles:insert>

