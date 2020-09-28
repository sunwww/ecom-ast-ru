<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
  
	<ecom:webQuery name="medService" nativeSql="
							select MS.name as name, CAMS.id, PP.cost  from ContractAccountMedService CAMS
							left join PriceMedService PMS ON PMS.pricePosition_id = CAMS.medservice_id
							left join MedService MS ON MS.id = PMS.medService_id
							left join PricePosition pp on pp.id=pms.pricePosition_id
							where CAMS.account_id = '${param.id}'
			"/>

	<%

	
		//Query aQuery;
		//EntityManager aManager = null;
		
		//IWebQueryService test = null;
		//test.executeNativeSql("select MS.name as name, CAMS.id, PP.cost  from ContractAccountMedService CAMS "+
		//		"left join PriceMedService PMS ON PMS.pricePosition_id = CAMS.medservice_id "+
		//		"left join MedService MS ON MS.id = PMS.medService_id "+
		//		"left join PricePosition pp on pp.id=pms.pricePosition_id "+
		//		"where CAMS.account_id = "+request.getParameter("id"));
		
		String query1="select MS.name as name, CAMS.id, PP.cost  from ContractAccountMedService CAMS "+
				"left join PriceMedService PMS ON PMS.pricePosition_id = CAMS.medservice_id "+
				"left join MedService MS ON MS.id = PMS.medService_id "+
				"left join PricePosition pp on pp.id=pms.pricePosition_id "+
				"where CAMS.account_id = "+request.getParameter("id");
				request.setAttribute("t1", request.getAttribute("medService"));
	%>
		
	
    ldfjkdgdjkgdkgh
    =
    ${t1};
	<msh:table name="medService" action="entityParentView-contract_contractAccountMedService.do" idField="2">
			<msh:tableColumn columnName="Медицинския услуга" property="1" />
			<msh:tableColumn columnName="Стоимость" property="3" />
	</msh:table>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="ContractAccount" beginForm="smo_contract_directionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_contract_directionForm" >
      <msh:sideMenu title="Направление">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-smo_contract_direction" name="Изменить" roles="/Policy/Mis/MedCase/Direction/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-smo_contract_direction" name="Удалить" roles="/Policy/Mis/MedCase/Direction/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="smo_contract_directionForm" >
      <script type="text/javascript">workFunctionPlanAutocomplete.addOnChangeCallback(function(){
  			updateDefaultDate() ;
  		}) ;
  		function updateDefaultDate() {
  			WorkCalendarService.getDefaultDate($('workFunctionPlan').value,
  			{
  				callback:function(aDateDefault) {
  					if (aDateDefault!=null) {
  						//alert(aDateDefault) ;
	  					var calDayId, calDayInfo,ind1 ;
	  					ind1 = aDateDefault.indexOf("#") ;
  						calDayInfo = aDateDefault.substring(0,ind1) ;
  						calDayId = aDateDefault.substring(ind1+1) ;
  						
	  					$('datePlan').value=calDayId ;
				        $('datePlanName').value = calDayInfo;
  					}
	  				else {
	  					$('datePlan').value=0 ;
				        $('datePlanName').value = "";
	  				}
	  			}
  			}
  			) ;
  			$('timePlan').value="0" ;
			$('timePlanName').value = "";
  			
  		}</script>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

