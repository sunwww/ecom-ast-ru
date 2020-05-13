<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_criminalMessagesForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_criminalMessagesForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_criminalMessages" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_criminalMessages" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Delete" />
        <msh:sideLink key="ALT+3" params="id" action="/entityParentListRedirect-stac_criminalMessages" name="⇧Cписок сообщений в полицию" roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/View" />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Сообщение в полицию
    	  -->
    <msh:form  action="/entityParentSave-stac_criminalMessages.do?sslid=${param.sslid}" defaultField="phoneDate" editRoles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Create">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:hidden property="phoneMessageType" />
      <msh:panel >
        <msh:row>
              <msh:textField property="number" label="Номер сообщения" />
        </msh:row>
        <msh:row>
          <msh:textField property="phoneDate" label="Дата регистрации" />
          <msh:textField property="phoneTime" label="время" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="organization" fieldColSpan="5" horizontalFill="true" vocName="vocPhoneMessageOrganization" label="Прин. сооб. организация"/>
        </msh:row>
        <msh:row>
       	  <msh:textField property="phone" label="Другой телефон" horizontalFill="true" />
          <msh:textField property="recieverOrganization" label="организация" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="recieverEmploye" label="Фамилия принявшего" horizontalFill="true" fieldColSpan="5" vocName="vocPhoneMessageEmploye" parentAutocomplete="organization"/>
        </msh:row>
        <msh:row>
          <msh:textField property="recieverFio" label="Др. принявший" horizontalFill="true" fieldColSpan="5" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="workFunction" label="Передавший сообщений" vocName="workFunction" horizontalFill="true" fieldColSpan="5" />
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
        <msh:row>
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
            <msh:label property="createUsername" label="пользователь" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
        	<msh:label property="editTime" label="время"/>
          	<msh:label property="editUsername" label="пользователь" />
        </msh:row>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsNotView formName="stac_criminalMessagesForm">
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

