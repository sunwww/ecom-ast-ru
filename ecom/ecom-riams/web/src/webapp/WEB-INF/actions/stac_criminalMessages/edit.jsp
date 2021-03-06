<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_criminalMessagesForm" guid="638ddd30-b48e-4058-b3ad-866c0c70ee1f" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_criminalMessagesForm" guid="e2054544-fdd1-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_criminalMessages" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_criminalMessages" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Delete" />
        <msh:sideLink key="ALT+3" params="id" action="/entityParentListRedirect-stac_criminalMessages" name="⇧Cписок сообщений в полицию" roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/View" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="5d2e6cab-8667-4344-9d02-b4a248383066">
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" guid="a5ab8aa6-8be1-43f4-8aae-f5024f6283ca" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Сообщение в полицию
    	  -->
    <msh:form  action="/entityParentSave-stac_criminalMessages.do?sslid=${param.sslid}" defaultField="phoneDate" guid="b37b5d4a-9687-45bf-b42c-613fd9b1739d" editRoles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Create">
      <msh:hidden property="id" guid="c5a9c42c-d593-4fae-a685-44586a81938b" />
      <msh:hidden property="saveType" guid="7cb7bcb7-2cb3-4e65-92ce-2a4a2cec809f" />
      <msh:hidden property="medCase" guid="a5b77824-a218-425d-b687-89c701cf512d" />
      <msh:hidden property="phoneMessageType" guid="039d827f-f42a-48e8-9bad-7882d2d08d91" />
      <msh:panel guid="6e8d827a-d32c-4a05-b4b0-5ff7eed6eedc" >
        <msh:row guid="a53d1f37-afe8-4779-9e63-0b2684e14828">
              <msh:textField property="number" label="Номер сообщения" guid="51e5754c-2356-4ef6-91b2-9634893cc329" />
        </msh:row>
        <msh:row guid="0e91a1ca-c366-435c-8f2c-274d23d87fd3">
          <msh:textField property="phoneDate" label="Дата регистрации" guid="e3fd4642-a532-4510-a528-c6e766328d61" />
          <msh:textField property="phoneTime" label="время" guid="f94ff57c-bbf9-44f8-9e8d-f21927edbcff" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="organization" fieldColSpan="5" horizontalFill="true" vocName="vocPhoneMessageOrganization" label="Прин. сооб. организация"/>
        </msh:row>
        <msh:row guid="cff159af-0e93-42de-b29d-b68c05e371d0">
       	  <msh:textField property="phone" label="Другой телефон" guid="c44b474f-6dba-4ba8-9af7-56a0dca363ad" horizontalFill="true" />
          <msh:textField property="recieverOrganization" label="организация" guid="7d35c85f-1f39-48c1-b48c-0b8fd4da28e3" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="f99e7379-1503-44ae-8d45-184ada064f84">
          <msh:autoComplete property="recieverEmploye" label="Фамилия принявшего" horizontalFill="true" fieldColSpan="5" vocName="vocPhoneMessageEmploye" parentAutocomplete="organization"/>
        </msh:row>
        <msh:row guid="f99e7379-1503-44ae-8d45-184ada064f84">
          <msh:textField property="recieverFio" label="Др. принявший" guid="ca12abf3-6c85-4bf8-a4f4-cbfbb8c184a9" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row guid="ffc77c31-8e93-48fc-b927-1c62e69cda44">
          <msh:autoComplete property="workFunction" label="Передавший сообщений" vocName="workFunction" guid="f1dab596-6c7d-4cb4-848b-71b62bd6bf3a" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocCriminalPhoneMessageType" property="phoneMessageType" label="Тип" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocPhoneMessageSubType" property="phoneMessageSubType" parentAutocomplete="phoneMessageType" label="Описание" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="whenDateEventOccurred" label="Когда дата:" fieldColSpan="1" />
        	<msh:textField property="whenTimeEventOccurred" label="время:" fieldColSpan="1" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocRayon" property="rayon" label="Район" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="place" label="Место" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row guid="5d9db3cf-010f-463e-a2e6-3bbec49fa646">
          <msh:textArea property="comment" label="Пояснение обстоятельств" rows="7" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocPhoneMessageState" property="state" label="Тяжесть состояния" fieldColSpan="5" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="idc10" fieldColSpan="5" label="МКБ диагноза" horizontalFill="true" vocName="vocIdc10"/>
        </msh:row>
        <msh:row>
          <msh:textArea property="diagnosis" label="Диагноз" rows="7" fieldColSpan="5" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="outcome" fieldColSpan="5" label="Исход" horizontalFill="true" vocName="vocPhoneMessageOutcome"/>
        </msh:row>
        <msh:row>
        	
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="6"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
            <msh:label property="createUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
        	<msh:label property="editTime" label="время"/>
          	<msh:label property="editUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="stac_criminalMessagesForm" guid="518fe547-aed9-be2229f04ba3">
      <script type="text/javascript">

       
  		try {
	    if (idc10Autocomplete) idc10Autocomplete.addOnChangeCallback(function() {
	      	 	setDiagnosisText('idc10','diagnosis');
	    });} catch(e) {}

  		function setDiagnosisText(aFieldMkb,aFieldText) {
  			var val = $(aFieldMkb+'Name').value ;
  			var ind = val.indexOf(' ') ;
  			//alert(ind+' '+val)
  			if (ind!=-1) {
  				if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
  			}
  		}
  		</script>
  		</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

