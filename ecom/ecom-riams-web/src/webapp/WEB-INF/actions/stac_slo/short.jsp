<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения в отделении
    	  -->
    	  <style type="text/css">


            #clinicalDiagnosLabel, #clinicalMkbLabel, #clinicalActuityLabel {
                color: blue ;
            }
            #concomitantDiagnosLabel, #concomitantMkbLabel {
                color: green ;
            }

            #concludingDiagnosLabel, #concludingMkbLabel {
                color: black ;
            }
            #pathanatomicalDiagnosLabel, #pathanatomicalMkbLabel {
                color: red ;
            }

        </style>
    <msh:form action="/entityParentSaveGoView-stac_slo.do" title="<a href='entityParentView-stac_slo.do?id=${param.id}'>Случай лечения в отделении</a>" defaultField="transferDate" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="prevMedCase" guid="710eb92b-fc3f-4390-b32df6837280" />
      <msh:hidden property="parent" guid="710eb92b-fc3f-4b44-9390-b32df6837280" />
      <msh:hidden property="patient" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:hidden property="lpuAndDate" guid="9cc5ff9f-b68c-423a-be34-50ebeecf4b18" />
      <msh:hidden property="lpu" guid="756525c0-3c91-41da-b2ba-27ebdbdc001b" />
      <msh:hidden property="dateFinish"/>
      <msh:hidden property="dischargeTime"/>
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="5%,5%,5%">
      <msh:ifFormTypeAreViewOrEdit formName="stac_sloForm">
      	<msh:row >
      		<msh:label property="statCardBySLS" label="Номер стат.карты" labelColSpan="1"/>
      		<td colspan="2">
      		<msh:link action="entityParentListRedirect-stac_slo.do?id=${param.id}" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View">⇧Cписок СЛО</msh:link>
      		</td>
      	</msh:row>
      	</msh:ifFormTypeAreViewOrEdit>
        <msh:separator label="Поступление в отделение" colSpan="6" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/NotEditAdmissionTime">
	        <msh:row guid="d6321f29-4e95-42a5-9063-96df480e55a8">
	          <msh:textField property="dateStart" label="Дата поступления" viewOnlyField="true" />
	          <msh:textField property="entranceTime" label="время" viewOnlyField="true" />
	        </msh:row>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/NotEditAdmissionTime">
	        <msh:row guid="d6321f29-4e95-42a5-9063-96df480e55a8">
	          <msh:textField  property="dateStart" label="Дата поступления" viewOnlyField="false" />
	          <msh:textField property="entranceTime" label="время" viewOnlyField="false" />
	        </msh:row>
        </msh:ifNotInRole>
        <msh:row guid="f244aba5-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete  viewOnlyField="true"  vocName="lpu" property="department" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:row guid="f2-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete vocName="serviceStreamByDepAndDate" property="serviceStream" label="Поток обслуживания" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b216c" />
        </msh:row>
        <msh:row guid="f2aba5-68fb-4ccc-9982-7b4480cmca147">
          <msh:autoComplete vocName="bedFundByDepAndStreamAndDate" property="bedFund" label="Профиль коек" fieldColSpan="6" horizontalFill="true" guid="1064-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:row guid="9b781235-66ad-4f9d-991b-afb9aedfb7a8">
          <msh:textField label="№палаты" property="roomNumber" guid="fff1dd1d-b7a5-4fe2-899b-3292ec9f3fad" />
          <msh:autoComplete property="roomType" vocName="vocRoomType" label="Тип палаты" horizontalFill="true"/>
         </msh:row>
         <msh:row>
          <msh:textField label="№ койки" property="bedNumber" guid="ed0d86e6-71b9-44f6-9c3a-213f5e8465c8" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Провизорность" property="provisional" guid="dh88d59-3adb-4485-af94-cahb04f82b" />
        	<msh:checkBox label="Экстренно" property="emergency" guid="dhcahb04f82b" />
        </msh:row>
        <msh:row guid="1d32ce64-883b-4be9-8db1-a421709f4470">
          <msh:autoComplete vocName="workFunction" property="ownerFunction" label="Лечащий врач" fieldColSpan="6" horizontalFill="true" guid="968469ce-dd95-40f4-af14-deef6cd3e4f3" viewAction="entitySubclassView-work_workFunction.do" size="30" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocAcuityDiagnosis" property="clinicalActuity" horizontalFill="true" label="Характер заболевания"/>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ клин.диаг." property="clinicalMkb" fieldColSpan="1" horizontalFill="true"/>
        </msh:row>
        <msh:row>
    	    <msh:textField label="Клинический диагноз" property="clinicalDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
	        <msh:autoComplete vocName="vocIdc10" label="МКБ-10 клин.диаг.соп." property="concomitantMkb" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
    	    <msh:textField label="Клин. диаг. сопут" property="concomitantDiagnos" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>      
        <msh:separator label="Перевод в другое отделение" colSpan="" guid="dd7185d0-e499-4307-9e58-6ef41d83c2b0" />
        <msh:row guid="a3509d1f-9324-4997-a7c3-6ca8f12a9347">
          <msh:textField property="transferDate" label="Дата" guid="f8f5c912-00b8-4fd8-87b9-abe417212d78" />
          <msh:textField property="transferTime" label="Время" guid="c04ab410-42df-4f5b-b365-b4acf17a2616" />
        </msh:row>
        <msh:row guid="72adfc11-ef9b-47c0-8eb4-a23ee9e84ed8">
          <msh:autoComplete vocName="vocLpuOtd" property="transferDepartment" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="f793944a-6afe-4c26-82f3-50532049a8bc" />
        </msh:row>
        <msh:row guid="f2a5-68fb-4ccc-9982-7b4447">
          <msh:autoComplete vocName="vocHospType" property="targetHospType" label="Куда переведен" fieldColSpan="6" horizontalFill="true" guid="10964-23b2-42c0-ba47-6547816c" />
        </msh:row>
        <msh:separator label="Выписка" colSpan=""/>
        <msh:row guid="21b4ac48-1773-410d-b85f-537680420aa4">
          <msh:textField property="dateFinish" label="Дата" viewOnlyField="true" guid="bb7b87a8-c542-47ef-93b6-91106abf9f19" />
          <msh:textField property="dischargeTime" label="Время" viewOnlyField="true" guid="a8bfc5ac-8d19-4656-a30b-bd87da1918df" />
        </msh:row>


      </msh:panel>
    </msh:form>
  </tiles:put>
</tiles:insert>