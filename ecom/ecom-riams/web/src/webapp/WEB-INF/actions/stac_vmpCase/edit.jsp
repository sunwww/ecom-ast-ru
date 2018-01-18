<%@page import="java.util.Collection"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.awt.print.Printable"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <tiles:put name="body" type="string">
   
    <msh:form action="/entityParentSaveGoSubclassView-stac_vmpCase.do" defaultField="" guid="137f576d-2283-4edd-9978-74290e04b873" editRoles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Create">
      <msh:panel guid="80209fa0-fbd4-45d0-be90-26ca4219af2e" colsWidth="15px,250px,15px">
        <msh:hidden property="id" />
        <msh:hidden property="saveType" />
        <msh:hidden property="medCase" />
        <msh:hidden property="financeSource" />
        <msh:hidden property="stantAmount" />

        <msh:separator label="Сведения об ВМП" colSpan="5" guid="a7a51c304-335b4ade6f66" />
        <msh:row>
          <msh:textField property="ticketNumber" label="Номер талона на ВМП" size="100"/>
        </msh:row>
        <msh:row guid="a03a1e02-5a44-4403-bb71-fb8e5afcec43">
 	       <msh:textField property="ticketDate" label="Дата выдачи талона на ВМП"/>
 	       </msh:row>
 	       <msh:row guid="a03a1e02-5a44-4403-bb71-fb8e5afcec43">
 	       <msh:textField property="planHospDate" label="Дата планируемой госпитализации"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="kind" label="Вид ВМП" fieldColSpan="3" horizontalFill="true" vocName="vocKindHighCare" size="50" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="method" label="Метод ВМП"  horizontalFill="true" fieldColSpan="3" parentAutocomplete="kind" vocName="vocMethodHighCare" size="50"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="createUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        
                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
      </msh:panel>
    </msh:form>
 
    
    </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_vmpCaseForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_vmpCaseForm" guid="c7cae1b4-31ca-4b76-ab51-7f75b52d11b6">
      <msh:sideMenu title="Случай ВМП" guid="edd9bfa6-e6e7-4998-b4c2-08754057b0aa">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_vmpCase" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-stac_vmpCase" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
     </msh:sideMenu>
   </msh:ifFormTypeIsView>
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
   
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
        <script type="text/javascript" >
    kindAutocomplete.setParentId(+$('financeSource').value);
    </script>
   
  </tiles:put>
</tiles:insert>

