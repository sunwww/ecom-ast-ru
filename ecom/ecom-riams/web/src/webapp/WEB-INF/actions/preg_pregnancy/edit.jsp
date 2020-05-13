<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_pregnancyForm">
      <msh:sideMenu title="Беременность">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_pregnancy" name="Изменить" roles="/Policy/Mis/Pregnancy/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-preg_pregnancy" name="Удалить" roles="/Policy/Mis/Pregnancy/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Обменная карта">
        <msh:sideLink key="ALT+3" action="/javascript:urlPregnanExchangeCard('.do')" title="Сведения женской консультации о беременной (заполняется на каждую беременную и выдается на руки в 35 недель)" roles="/Policy/Mis/Pregnancy/PregnanExchangeCard/View" name="Сведения жен. консультации" />
        <msh:sideLink key="ALT+4" action="/javascript:urlConfinedExchangeCard('.do')" title="Сведения родильного дома, родильного отделения больницы о родильнице (направляется в женскую консультацию в первый день по выписке женщины)" roles="/Policy/Mis/Pregnancy/ConfinedExchangeCard/View" name="Сведения о родильнице" />
      </msh:sideMenu>
      <%-- 
      <msh:sideMenu title="Роды">
      	<msh:sideLink params="id" action="/entityParentPrepareCreate-preg_childBirth" name="История родов" roles="/Policy/Mis/Pregnancy/ChildBirth/Edit" title="Добавить историю родов" />
      </msh:sideMenu>
      --%>
      <msh:sideMenu title="Сертификаты">
      	<msh:sideLink key="ALT+5" action="/javascript:urlConfCertificate('.do')" title="Родовый сертификат" name="Родовый сертификат" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Беременность
    	  -->
    <msh:form action="/entityParentSave-preg_pregnancy.do" defaultField="transferDate">
      <msh:hidden property="id" />
      <msh:hidden property="patient" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <msh:textField property="orderNumber" label="Которые по счету: беременность" />
          <msh:textField property="childbirthAmount" label="роды" />
        </msh:row>
        <msh:row>
          <msh:textField property="username" viewOnlyField="true" label="Пользователь" />
          <msh:textField property="dateCreate" label="Дата создания" viewOnlyField="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="preg_pregnancyForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/View">
        <msh:section>
          <ecom:webQuery name="smoByPregnancy" nativeSql="select ph.id,mc.dateStart from PregnancyHistory ph&#xA;left join medcase mc on ph.medcase_id = mc.id&#xA; where ph.pregnancy_id='${param.id}'" />
          <msh:sectionTitle>Истории родов данной беременности</msh:sectionTitle>
          <msh:sectionContent>
            <msh:table name="smoByPregnancy" action="entityParentView-preg_pregHistory.do" idField="1">
              <msh:tableColumn property="2" columnName="Дата начала" />
              <msh:tableColumn identificator="false" property="3" columnName="Дата выписки" />
            </msh:table>
          </msh:sectionContent>
        </msh:section>
        <msh:section>
        	<ecom:webQuery name="pregnanExchangeCard" nativeSql="select 
        	pec.id,pec.firstVisitDate,ml.name,pec.pregnancyFeatures
        	,pec.notes 
        	from PregnanExchangeCard pec
        	left join mislpu ml on ml.id=pec.consultation_id
        	where pregnancy_id='${param.id}'
        	"/>
        	<msh:table idField="1" 
        	viewUrl="entityParentView-preg_pregnanCard.do?short=Short" name="pregnanExchangeCard" action="entityParentView-preg_pregnanCard.do">
        		<msh:tableColumn property="2" columnName="Дата 1 посещ. консул."/>
        		<msh:tableColumn property="3" columnName="Консультация"/>
        		<msh:tableColumn property="4" columnName="Особенности течения беременности"/>
        		<msh:tableColumn property="5" columnName="Особые замечания"/>
        	</msh:table>
        </msh:section>
        <msh:section title="Описание родов" createRoles="/Policy/Mis/Pregnancy/ChildBirth/Create" createUrl="entityParentPrepareCreate-preg_childBirthForPregnancy.do?id=${param.id}">
        	<ecom:webQuery name="childBirthForPregnancy" nativeSql="
        	select cb.id
        	,to_char(cb.birthFinishDate,'dd.mm.yyyy')||' '||cast(cb.birthFinishTime as varchar(5)) as datetimebirthday
        	,vof.name as ofname, list(case 
        	when vdrt.code='1' and vpd.code='1' and (mkb.code='O82.1' or mkb.code='O82.0') then 'кесарево' 
        	when vdrt.code='1' and vpd.code='1' and (mkb.code = 'O80.0' or mkb.code='O80.1') then 'естествен.'
        	else '' end)
        	,case when cb.placentaHistologyOrder='1' then 'Направлена плацента на гистологию.'||coalesce(cb.histology,'') else '' end
        	from ChildBirth cb
        	left join MedCase slo on slo.id=cb.medCase_id
        	left join MedCase sls on sls.id=slo.parent_id
         	left join Pregnancy preg on preg.id=sls.pregnancy_id 
        	left join Diagnosis d on d.medCase_id=slo.id
        	left join VocDiagnosisRegistrationType vdrt on vdrt.id=d.registrationType_id
        	left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
        	left join VocIdc10 mkb on mkb.id=d.idc10_id
        	left join Omc_Frm vof on vof.id=sls.orderType_id
          	where preg.id =${param.id}
          	group by cb.id,cb.birthFinishDate,cb.birthFinishTime,
          	vof.name,cb.placentaHistologyOrder,cb.histology
          	"/>
            <msh:table name="childBirthForPregnancy" 
            viewUrl="entityParentView-preg_childBirth.do?short=Short&" 
            action="entityParentView-preg_childBirth.do" idField="1">
              <msh:tableColumn property="2" columnName="Окончания родов" />
              <msh:tableColumn property="3" columnName="Кем доставлен" />
              <msh:tableColumn property="4" columnName="Роды" />
              <msh:tableColumn property="5" columnName="Гистология плаценты" cssClass="preCell" />
             
            </msh:table>
        </msh:section>
        <msh:section title="Сертификаты">
        <msh:ifInRole roles="/Policy/Mis/Certificate/View">
        <ecom:webQuery name="certificates" 
        nativeSql="select c.id,c.series,c.number,to_char(c.dateIssue,'dd.mm.yyyy') as dateIssue
        ,case when c.dtype='BirthCertificate' then 'Свидетельство о рождении' 
        		when c.dtype='ConfinementCertificate' then 'Родовый сертификат'
        		when c.dtype='DeathCertificate' then 'Свидетельство о смерти' else '-' end as dtype from Certificate c where c.pregnancy_id=${param.id}"/>
        <msh:table name="certificates" action="entitySubclassView-stac_certificate" idField="1" >
        	<msh:tableColumn property="2" columnName="Серия"/>
        	<msh:tableColumn property="3" columnName="Номер"/>
        	<msh:tableColumn property="4" columnName="Дата выдачи"/>
        </msh:table>
        </msh:ifInRole>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_pregnancyForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/PregnancyService.js" >
    /* Вроде того*/
    </script>
    <script type="text/javascript">
    $('medCase').value = '${param.medCase}' ;
    
    function urlConfCertificate() {
 		PregnancyService.getConfinementCertificate(
			     		'${param.id}' , {
			 callback: function(aId) {
			     if (aId!=null) {
			          window.location.href = "entityParentView-preg_confCertificate.do?id="+aId ;
			     } else {
			     
			    PregnancyService.able2createByConfinementCertificate ({
			    	callback: function(aBool) {
			    		createUrl(aBool
			    			,"Родовый сертификат не заполнен. Хотите завести его сейчас?"
				    		,"entityParentPrepareCreate-preg_confCertificate.do?id=${param.id}"
				    		,"Родовый сертификат не заполнен."
			    		) } } );
			     }
			  }
			}) ;
  }

    function urlPregnanExchangeCard() {
 		PregnancyService.getPregnanExchangeCard(
			     		'${param.id}' , {
			 callback: function(aId) {
			     if (aId!=null) {
			          window.location.href = "entityParentView-preg_pregnanCard.do?id="+aId ;
			     } else {
			     
			    PregnancyService.able2createByPregnanExchangeCard ({
			    	callback: function(aBool) {
			    		createUrl(aBool
			    			,"Сведения жен. консультации не заполнены. Хотите завести их сейчас?"
				    		,"entityParentPrepareCreate-preg_pregnanCard.do?id=${param.id}"
				    		,"Сведения жен. консультации не заполнены."
			    		) } } );
			     }
			  }
			}) ;
  }
  function urlConfinedExchangeCard() {
 	PregnancyService.getConfinedExchangeCard(
			     		'${param.id}' , {
			 callback: function(aId) {
			     if (aId!=null) {
			          window.location.href = "entityParentView-preg_confinedCard.do?id="+aId ;
			     } else {
			     
			    PregnancyService.able2createByConfinedExchangeCard ({
			    	callback: function(aBool) {
			    		createUrl(aBool
			    			,"Сведения родильного дома, родильного отделения о родильнице не заполнена. Хотите завести их сейчас?"
			    			,"entityParentPrepareCreate-preg_confinedCard.do?id=${param.id}"
			    			,"Сведения родильного дома, родильного отделения о родильнице не заполнена."
			    			) ;
				     }
				   }
				   );
			     }
			  }
			}) ;
  }
 
 function urlChildBirthHistory() {
 	PregnancyService.getConfinedExchangeCard(
			     		'${param.id}' , {
			 callback: function(aId) {
			     if (aId!=null) {
			          window.location.href = "entityParentView-preg_confinedCard.do?id="+aId ;
			     } else {
			     
			    PregnancyService.able2createByConfinedExchangeCard ({
			    	callback: function(aBool) {
			    		createUrl(aBool
			    			,"Сведения родильного дома, родильного отделения о родильнице не заполнена. Хотите завести их сейчас?"
			    			,"entityParentPrepareCreate-preg_confinedCard.do?id=${param.id}"
			    			,"Сведения родильного дома, родильного отделения о родильнице не заполнена."
			    			) ;
				     }
				   }
				   );
			     }
			  }
			}) ;
  }
 
 
  function createUrl(aBool, aTitleConfirm,aUrlCreate,aMessageNoRight) {
   		if (bool(aBool)) {
	     	if (confirm(aTitleConfirm)) {
				window.location.href = aUrlCreate  ;
			} else {
				//alert("Ну и не надо");
			}
		} else {
			alert(aMessageNoRight) ;
		}
  }
  function bool(aBool) {
  	if (aBool!=null) if (aBool==true) return true ;
  	return false ;
  }</script>
  </tiles:put>
</tiles:insert>

