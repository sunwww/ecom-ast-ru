<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_pregnancyForm" guid="e20545-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Беременность">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_pregnancy" name="Изменить" roles="/Policy/Mis/Pregnancy/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-preg_pregnancy" name="Удалить" roles="/Policy/Mis/Pregnancy/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
      <msh:sideMenu title="Обменная карта" guid="5605a96c-130c-4e37-ba9b-bbbe3c38420b">
        <msh:sideLink key="ALT+3" action="/javascript:urlPregnanExchangeCard('.do')" title="Сведения женской консультации о беременной (заполняется на каждую беременную и выдается на руки в 35 недель)" roles="/Policy/Mis/Pregnancy/PregnanExchangeCard/View" name="Сведения жен. консультации" guid="4b4743bd-f910-47d0-9b14-332e75276a5c" />
        <msh:sideLink key="ALT+4" action="/javascript:urlConfinedExchangeCard('.do')" title="Сведения родильного дома, родильного отделения больницы о родильнице (направляется в женскую консультацию в первый день по выписке женщины)" roles="/Policy/Mis/Pregnancy/ConfinedExchangeCard/View" name="Сведения о родильнице" guid="c8284dee-65a1-4d2a-9d35-9f54d0e94152" />
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
    <msh:form action="/entityParentSave-preg_pregnancy.do" defaultField="transferDate" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="patient" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="medCase" guid="9d90-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:row guid="d274f18a-5e28-4288-b06b-519f101d2a32">
          <msh:textField property="orderNumber" label="Которые по счету: беременность" guid="38272f23-93ef-4fb9-8403-bc871f78be74" />
          <msh:textField property="childbirthAmount" label="роды" guid="38272f23-93ef-4fb9-8403-bc871f78be74" />
        </msh:row>
        <msh:row guid="6210df91-b6f7-4965-94b3-c908995f36d1">
          <msh:textField property="username" viewOnlyField="true" label="Пользователь" guid="9e12c210-4aed-4884-9278-8c789dcc4e9b" />
          <msh:textField property="dateCreate" label="Дата создания" viewOnlyField="true" guid="0708cff4-bd1b-4477-ae5a-c5c26a49147a" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="preg_pregnancyForm" guid="f5d10596-1638-4f7a-b088-737b060bbfd7">
      <msh:ifInRole roles="/Policy/Mis/MedCase/View" guid="0f111ff1-d51f-4817-964c-71c2d197bd67">
        <msh:section guid="89cf3bcc-1f99-40d3-94d7-8110b95aa2b3">
          <ecom:webQuery name="smoByPregnancy" nativeSql="select ph.id,mc.dateStart from PregnancyHistory ph&#xA;left join medcase mc on ph.medcase_id = mc.id&#xA; where ph.pregnancy_id='${param.id}'" guid="9db603df-a489-41a8-a7f6-926084ad458a" />
          <msh:sectionTitle guid="050c4364-c63c-4aee-90cd-bc91f616b1d7">Истории родов данной беременности</msh:sectionTitle>
          <msh:sectionContent guid="048d9c8a-3a72-4943-ba37-cba39ec1478d">
            <msh:table name="smoByPregnancy" action="entityParentView-preg_pregHistory.do" idField="1" guid="4f5506e1-b49f-4725-a6e3-43f4fc5ec8a4">
              <msh:tableColumn property="2" columnName="Дата начала" guid="b4473fa2-67da-4b78-83d4-a1598a9cce0a" />
              <msh:tableColumn identificator="false" property="3" guid="b59614db-54c6-4fa1-9020-c8bbadc17804" columnName="Дата выписки" />
            </msh:table>
          </msh:sectionContent>
        </msh:section>
        <msh:section title="Описание родов">
        	<ecom:webQuery name="childBirthForPregnancy" nativeSql="select id,birthFinishDate from ChildBirth where medCase_id in(select id from medcase where parent_id in (select medCase_id from pregnancyhistory where pregnancy_id =${param.id}) and DTYPE='DepartmentMedCase')"/>
            <msh:table name="childBirthForPregnancy" action="entityParentView-preg_childBirth.do" idField="1" guid="4f5506e1-b49f-4725-a6e3-43f4fc5ec8a4">
              <msh:tableColumn property="2" columnName="Дата окончания родов" guid="b4473fa2-67da-4b78-83d4-a1598a9cce0a" />
             
            </msh:table>
        </msh:section>
        <msh:section>
        <msh:ifInRole roles="/Policy/Mis/Certificate/View">
        <ecom:parentEntityListAll attribute="certificates" formName="stac_certificateForm"/>
        <msh:table name="certificates" action="entitySubclassView-stac_certificate" >
        	<msh:tableColumn property="information" columnName="Описание"/>
        </msh:table>
        </msh:ifInRole>
        </msh:section>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_pregnancyForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
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

