<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoParentView-mis_medPolicyDmcForeign.do" defaultField="polNumber">
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
          <msh:textField property="series" label="Серия" horizontalFill="true" size="30" />
          <msh:textField property="polNumber" label="Номер" horizontalFill="true" size="30" />
        </msh:row>
        <msh:row>
          <msh:separator label="Страховая компания" colSpan="6" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="omcKodTer" property="insuranceCompanyArea" fieldColSpan="6" horizontalFill="true" label="Область нахождения СМО" />
       </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocInsuranceCompanyAll" property="company" label="Страховая&nbsp;компания" horizontalFill="true" fieldColSpan="5"/>
        </msh:row>
        <msh:row>
          <msh:textField property="insuranceCompanyCity" fieldColSpan="6" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="insuranceCompanyName" fieldColSpan="6" horizontalFill="true" />
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
    <msh:sideMenu>
      <msh:ifFormTypeIsView formName="mis_medPolicyDmcForeignForm">
        <msh:sideLink roles="/Policy/Mis/MedPolicy/DmcForeign/Edit" key="ALT+2" params="id" action="/entityParentEdit-mis_medPolicyDmcForeign" name="Изменить" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeAreViewOrEdit formName="mis_medPolicyDmcForeignForm">
        <msh:sideLink roles="/Policy/Mis/MedPolicy/DmcForeign/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-mis_medPolicyDmcForeign" name="Удалить" confirm="Удалить полис?" />
      <msh:ifFormTypeIsNotView formName="mis_medPolicyDmcForeignForm">
	    		<msh:sideLink roles="/Policy/Mis/MedPolicy/DmcForeign/Edit" action="/javascript:{$('changeType').style.display='block';}" name="Изменить тип полиса" />
      </msh:ifFormTypeIsNotView>
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_medPolicyDmcForeignForm" />
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

