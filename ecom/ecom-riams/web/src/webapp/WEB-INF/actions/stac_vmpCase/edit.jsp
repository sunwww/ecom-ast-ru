<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <tiles:put name="body" type="string">
   
    <msh:form action="/entityParentSaveGoSubclassView-stac_vmpCase.do" defaultField="" editRoles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Create">
      <msh:panel colsWidth="15px,250px,15px">
        <msh:hidden property="id" />
        <msh:hidden property="saveType" />
        <msh:hidden property="medCase" />
        <msh:hidden property="financeSource" />

        <msh:separator label="Сведения об ВМП" colSpan="5" />
        <msh:row>
          <msh:textField property="ticketNumber" label="Номер талона на ВМП" size="100"/>
        </msh:row>
        <msh:row>
 	       <msh:textField property="ticketDate" label="Дата выдачи талона на ВМП"/>
 	       </msh:row>
 	       <msh:row>
 	       <msh:textField property="planHospDate" label="Дата планируемой госпитализации"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="kind" label="Вид ВМП" fieldColSpan="3" horizontalFill="true" vocName="vocKindHighCare" size="50" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="method" label="Метод ВМП"  horizontalFill="true" fieldColSpan="3" parentAutocomplete="kind" vocName="vocMethodHighCareByKind" size="50"/>
        </msh:row>
        <msh:row>
          <msh:textField property="stantAmount" label="Количество установленных стентов" />
        </msh:row>
        <msh:row>
          <msh:textField property="diagnosis" size="50"/>
        </msh:row>
        <msh:row>
          <msh:textArea property="patientModel" rows="2"/>
      </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="createUsername" label="пользователь" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="пользователь" />
        </msh:row>
        
                <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
 
    
    </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_vmpCaseForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_vmpCaseForm">
      <msh:sideMenu title="Случай ВМП">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_vmpCase" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-stac_vmpCase" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Delete" />
     </msh:sideMenu>
   </msh:ifFormTypeIsView>
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
   
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
        <script type="text/javascript" >
    kindAutocomplete.setParentId(+$('financeSource').value);
    methodAutocomplete.addOnChangeCallback(function() {if ($('method').value>0) {
        HospitalMedCaseService.getDiagnosisAndModelByVMPMethod($('method').value, {
            callback: function (ret) {
                if (ret != null) {
                    ret = JSON.parse(ret);
                    $('diagnosis').value = ret[0].diagnosis;
                    $('patientModel').value = ret[0].patientModel;
                }
            }
        });
    } else {$('diagnosis').value = ""; $('patientModel').value = "";}
    });
    </script>
   
  </tiles:put>
</tiles:insert>

