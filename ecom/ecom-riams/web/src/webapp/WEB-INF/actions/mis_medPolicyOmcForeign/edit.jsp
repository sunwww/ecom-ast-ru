<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_medPolicyOmcForeign.do" defaultField="typeName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
      <msh:panel>
        <msh:row>
          <msh:textField property="lastname" label="Фамилия" horizontalFill="true" size="30" />
          <msh:textField property="firstname" label="Имя" horizontalFill="true" size="30" />
          <msh:textField property="middlename" label="Отчество" horizontalFill="true" size="30" />
        </msh:row>
        <msh:row>
            <msh:autoComplete vocName="vocMedPolicyOmc" property="type" label="Тип полиса" horizontalFill="true" fieldColSpan="5"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="commonNumber" label="Единый номер" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
		  <msh:textField property="series" label="Серия" horizontalFill="true" size="30" />
          <msh:textField property="polNumber" label="Номер" horizontalFill="true" size="30" />
        </msh:row>
        <msh:row>
          <msh:separator label="Страховая компания" colSpan="5" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="omcKodTer" property="insuranceCompanyArea" fieldColSpan="5" horizontalFill="true" label="Область нахождения СМО" />
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
          <msh:textField  property="insuranceCompanyCity" fieldColSpan="5" horizontalFill="true" />
        </msh:row>

        <msh:row>
          <msh:textField property="insuranceCompanyName" viewOnlyField="true" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="actualDateFrom" label="Дата действия с" />
          <msh:textField property="actualDateTo" label="по" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
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
        		<ecom:webQuery name="list_sls" nativeSql="select m.id ,p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'DD.MM.YYYY')||'г.' ,
        		d.name as depname ,ss.code as sscode , m.dateStart as mdateStart,m.dateFinish as mdateFinish,
        		case when mp.datesync is not null then '&#9989;' else '&#x2716;'  end as actually
        		from medCase_medPolicy as mp
        		left join MedCase as m on m.id=mp.medcase_id
        		left join medcase as h on h.id=m.parent_id
        		left join statisticstub as ss on ss.id=m.statisticstub_id
        		left join bedfund as bf on bf.id=m.bedfund_id
        		left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id
        		left join vocbedtype as vbt on vbt.id=bf.bedtype_id
        		left join mislpu as d on d.id=m.department_id
        		left join patient as p on p.id=m.patient_id
        		where mp.policies_id='${param.id}' and m.DTYPE='HospitalMedCase'
        		order by m.dateStart"/>
        		<msh:tableNotEmpty name="list_sls">
	        		<msh:section title="Прикрепленные случаи лечения госпитализации">
		        		<msh:table name="list_sls" action="entityParentView-stac_ssl.do" idField="1">
		        			<msh:tableColumn property="2" columnName="Пациент" />
		        			<msh:tableColumn property="3" columnName="Отделение" />
		        			<msh:tableColumn property="4" columnName="Номер стат.карты" />
		        			<msh:tableColumn property="5" columnName="Дата начала" />
		        			<msh:tableColumn property="6" columnName="Дата окончания" />
                            <msh:tableColumn property="7" columnName="Проверено" />
                            <msh:tableButton property="1" buttonFunction="setPolisChecked" buttonShortName="Уст. 'проверено'" buttonName="проверено"/>
		        		</msh:table>
	        		</msh:section>
        		</msh:tableNotEmpty>
        	</msh:ifInRole>
        </msh:ifFormTypeIsView>  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:ifFormTypeIsView formName="mis_medPolicyOmcForeignForm">
        <msh:sideLink roles="/Policy/Mis/MedPolicy/OmcForeign/Edit" key="ALT+2" params="id" action="/entityParentEdit-mis_medPolicyOmcForeign" name="Изменить" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeAreViewOrEdit formName="mis_medPolicyOmcForeignForm">
        <msh:sideLink roles="/Policy/Mis/MedPolicy/OmcForeign/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-mis_medPolicyOmcForeign" name="Удалить" confirm="Удалить полис?" />
                <msh:sideLink
	    			roles="/Policy/Mis/MedCase/Stac/Journal/ReceivedWithoutPolicy" key="ALT+9"
	    			action="/stac_receivedWithoutPolicy_list" name="По госпитализациям без прикрепленных полисов"
	    			styleId="stac_receivedWithoutPolicy"
	    		/>
	    <msh:ifFormTypeIsNotView formName="mis_medPolicyOmcForeignForm">
	    	<msh:sideLink roles="/Policy/Mis/MedPolicy/OmcForeign/Edit" action="/javascript:{$('changeType').style.display='block';}" name="Изменить тип полиса" />
      </msh:ifFormTypeIsNotView>
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_medPolicyOmcForeignForm" />
  </tiles:put>
  
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="mis_medPolicyOmcForeignForm">
            <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
            <script type="text/javascript">
                function setPolisChecked(id) {
                    PatientService.polisIsChecked(id,{
                        callback: function(){
                            showToastMessage("Сохранено!",null,true);
                            jQuery("#listSLS").load("entityView-mis_medPolicyOmc.do?id=${param.id} #listSLS");
                        }});
                }
            </script>
        </msh:ifFormTypeIsView>
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

