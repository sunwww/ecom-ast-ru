<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_medPolicyDmcForeign.do" defaultField="polNumber" guid="aad9590d-27e9-4b68-b7f5-c726fd0d829d">
      <msh:hidden property="id" guid="920163df-050c-466d-ae73-b83efba98aea" />
      <msh:hidden property="saveType" guid="848ea0d1-b00b-4088-a1d9-b15cadbc1d68" />
      <msh:hidden property="patient" guid="2c705c5c-0161-4c0a-8f77-18684635dc5f" />
      <msh:panel guid="1cd8682e-dc17-4a34-9e1d-5cf81b847dfd">
        <msh:row guid="79b4d8e5-94f4-4ef6-a80c-671be776a288">
          <msh:textField property="lastname" label="Фамилия" horizontalFill="true" size="30" guid="accca226-eb8f-4157-923d-e06ab85d8c47" />
          <msh:textField property="firstname" label="Имя" horizontalFill="true" size="30" guid="3b504517-4871-47a4-a605-0695ca4b208c" />
          <msh:textField property="middlename" label="Отчество" horizontalFill="true" size="30" guid="3e2b1e65-fa0a-4966-8d72-d2ccbeddc723" />
        </msh:row>
        <msh:row guid="7f757215-5f77-4c54-991f-fba35828f795">
          <msh:textField property="series" label="Серия" horizontalFill="true" size="30" guid="0fd6-d6ed-4ddf-91b0-8cf8ee" />
          <msh:textField property="polNumber" label="Номер" horizontalFill="true" size="30" guid="68a52bc5-48f1-4df2-a235-310120f41e51" />
        </msh:row>
        <msh:row guid="c1482b00-d957-4473-9c52-bead5521af3b">
          <msh:separator label="Страховая компания" colSpan="6" guid="e3304cdc-4046-42d8-b8da-e3867c8f5f31" />
        </msh:row>
        <msh:row guid="e8c385bf-26c7-479e-b13d-0dd8922c6cb3">
          <msh:autoComplete vocName="omcKodTer" property="insuranceCompanyArea" fieldColSpan="6" horizontalFill="true" guid="84f44c-cc5d-4e9a-9daf-80f16e5" label="Область нахождения СМО" />
       </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocInsuranceCompanyAll" property="company" label="Страховая&nbsp;компания" horizontalFill="true" fieldColSpan="5"/>
        </msh:row>
        <msh:row guid="c95c097c-1243-413b-be05-d038a4d5af19">
          <msh:textField property="insuranceCompanyCity" fieldColSpan="6" horizontalFill="true" guid="515e0550-b6a0-4832-8975-844506ad1f1e" />
        </msh:row>
        <msh:row guid="a561f7e9-6634-4819-9a28-5d1b6bd9c901">
          <msh:textField property="insuranceCompanyName" fieldColSpan="6" horizontalFill="true" guid="e17fe888-eade-45b3-8745-bab8323a353c" />
        </msh:row>
        <msh:row guid="73f0a1a4-a679-43dd-b68e-5383be82a239">
          <msh:textField property="actualDateFrom" label="Дата действия с" guid="11433435-381d-4b2d-b433-982d16b7fc4a" />
          <msh:textField property="actualDateTo" label="по" guid="08e9c6b6-94a6-429d-bd78-d96907309765" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="fb7219ed-1b0d-4de6-98b2-844686275c71" />
      </msh:panel>
      <msh:section>
            <div id='changeType' style='display:none'>
      <msh:separator label="Изменить тип полиса" colSpan="10"></msh:separator>
      <msh:autoComplete label="Новый тип" vocName="vocMedPolicy" property="changePolicyType" fieldColSpan="10" size="50" />
      <input type='button' value="Изменить тип"  onclick="changePolicyTypef()">
      </div>
            </msh:section>
        <msh:ifFormTypeIsView formName="mis_medPolicyDmcForeignForm">
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
        </msh:ifFormTypeIsView>    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="8b1e458a-32e0-4e50-b8dc-813db9f06978">
      <msh:ifFormTypeIsView formName="mis_medPolicyDmcForeignForm" guid="f5670887-12f9-43e9-a803-a11a08bbfabe">
        <msh:sideLink roles="/Policy/Mis/MedPolicy/DmcForeign/Edit" key="ALT+2" params="id" action="/entityParentEdit-mis_medPolicyDmcForeign" name="Изменить" guid="9f4b4d6e-5d63-43b4-8738-365bcdd3354f" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeAreViewOrEdit formName="mis_medPolicyDmcForeignForm" guid="e768d0a9-7ac0-47b9-adfe-595effd37a2b">
        <msh:sideLink roles="/Policy/Mis/MedPolicy/DmcForeign/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-mis_medPolicyDmcForeign" name="Удалить" confirm="Удалить полис?" guid="a4553ac9-c47f-4c90-97b2-29fa2926f00b" />
      <msh:ifFormTypeIsNotView formName="mis_medPolicyDmcForeignForm">
	    		<msh:sideLink roles="/Policy/Mis/MedPolicy/DmcForeign/Edit" action="/javascript:{$('changeType').style.display='block';}" name="Изменить тип полиса" guid="d3d19781-f1b0-42b3-a314-f5e6a2b55584" />
      </msh:ifFormTypeIsNotView>
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_medPolicyDmcForeignForm" guid="2a10a36c-efe7-4d1d-be59-f608ba8cdf33" />
  </tiles:put>
    <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="mis_medPolicyDmcForeignForm">
        <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>	
    	<script type="text/javascript">
    		
    	companyAutocomplete.addOnChangeCallback(function() {
	      	 	update() ;
	      	 });
	      	 function update() {
	      	 	var text ;
	      	 	text = $('companyName').value ;
	      	 	//var cnt = text.indexOf(' ') ;
	      	 	//if (cnt>0) {
		      	 	//$('code').value=text.substring(0,cnt) ;
		      	 	$('insuranceCompanyName').value=text;
	      	 	//}
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
    	</script>
    </msh:ifFormTypeIsNotView>
  
  </tiles:put>
  </tiles:insert>

