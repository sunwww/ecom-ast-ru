<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page = '/WEB-INF/tiles/mainLayout.jsp' flush = 'true'>

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-mis_lpuArea.do" defaultField="number" guid="10826cd9-7e71-480c-9d53-c96e6805ce24">
      <msh:hidden property="id" guid="332d968f-182e-4108-b1de-df71738d7b8a" />
      <msh:hidden property="saveType" guid="23740389-cb85-4e5c-b736-0343bbc49d15" />
      <msh:hidden property="lpu" guid="18c8ab60-7a63-4ab2-a829-a2105879ec4f" />
      <msh:panel guid="070b9d1e-c50f-4423-9d72-274f6b1dc045">
        <msh:ifInRole roles="/Policy/Mis/MisLpu/EditParent" guid="96b64dbe-1907-48e8-b2e2-e39a570c185a">
         <msh:ifFormTypeIsNotView formName="mis_lpuAreaForm" guid="78609968-ca83-4a72-86a9-1ae6cb7fdaaa">
            <msh:ifFormTypeAreViewOrEdit formName="mis_lpuAreaForm" guid="12393d17-3068-40c2-8dc7-9b9ee4252715">
              <msh:row guid="a7a62505-2bfe-41b6-a54f-217b970dc0c3">
                <msh:autoComplete property="lpu" vocName="lpu" label="Родительское ЛПУ" viewAction="entityEdit-mis_lpu.do" fieldColSpan="3" guid="67d2a4af-71bc-4a19-8844-4a59b97fabda" horizontalFill="true" />
              </msh:row>
            </msh:ifFormTypeAreViewOrEdit>
          </msh:ifFormTypeIsNotView>
        </msh:ifInRole>
        
        <msh:row guid="numberRow123">
          <msh:textField property="number" label="Номер участка" guid="9f8be45a-773a-4ca4-a12c-8f50d63e3ffc" />
        </msh:row>
        <msh:row guid="c3c9de7b-88e7-42f5-a7e9-2e1a8d5651b8">
          <msh:autoComplete guid="type123" property="type" label="Тип участка" vocName="vocAreaType" size="20" />
        </msh:row>
        <msh:row guid="669f7589-58e7-4974-ac96-c9cbe4fb9eaa">
          <msh:textArea property="comment" label="Комментарий" horizontalFill="true" size="50" guid="9e8e72e3-5b37-4fef-ad93-a2cb2c36fb9b" rows="4" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="a332e241-83f4-4e61-ad6f-d0f69cc6076e" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_lpuAreaForm" guid="8c94403e-3dda-408e-85f8-78081f8c8ea1">
      <msh:section guid="b67c5be3-5330-4589-8da8-7888453aaeb8">
        <msh:sectionTitle guid="3ae2d769-07ed-4109-b6cc-75bf939a86d2">Список прикрепленных домов</msh:sectionTitle>
        <msh:sectionContent guid="6dd31b9f-5068-4961-9033-417c6e01ff13">
          <ecom:parentEntityListAll formName="mis_lpuAreaAddressTextForm" attribute="areas" guid="2afdc402-f549-49b1-b9c8-a0c5cb012785" />
          <msh:table name="areas" action="entityParentView-mis_lpuAreaAddressText.do" idField="id">
            <msh:tableColumn columnName="Адрес" property="addressText" guid="e7ee8550-c34b-40bb-9aac-fcdd4da970e0" />
          </msh:table>
        </msh:sectionContent>
      </msh:section>
      <%--
      <msh:section>
      <table width="100%" >
      	<tbody>
      		<tr>
      			<td valign="top">
      				<msh:section title="Список карт обратившихся за психиатрической помощью по участку">
      					<msh:sectionContent>
      						<ecom:webQuery name="listOpenCard" nativeSql="select pcc.id as pccid,pcc.cardNumber as pcccardNumber,p.lastname||' '||p.firstname|| ' '||p.middlename as lfm,area.startDate,area.finishDate,p.birthday as pbirthday from PsychiatricCareCard pcc 
      						left join Patient p on p.id=pcc.patient_id left join LpuAreaPsychCareCard area on area.lpuArea_id=${param.id} 
      						where pcc.id=area.careCard_id and area.finishDate is null"/>
      						<msh:table name="listOpenCard" action="entityParentView-psych_careCard.do" idField="1">
      							<msh:tableColumn columnName="#" property="sn"/>
      							<msh:tableColumn columnName="№карты" property="2"/>
      							<msh:tableColumn columnName="ФИО пациента" property="3"/>
      							<msh:tableColumn columnName="Год рождения" property="6"/>
      							<msh:tableColumn columnName="Дата прибытия" property="4"/>
      							<msh:tableColumn columnName="Дата убытия" property="5"/>
      						</msh:table>
      					</msh:sectionContent>
      				</msh:section>
      			</td>
      			<td valign="top">
      				<msh:section title="Список закрытых карт обратившихся за психиатрической помощью по участку">
      						<ecom:webQuery name="listCloseCard" nativeSql="select pcc.id as pccid,pcc.cardNumber,p.lastname||' '||p.firstname|| ' '||p.middlename as lfm,area.startDate,area.finishDate,p.birthday from PsychiatricCareCard pcc left join Patient p on p.id=pcc.patient_id 
      						left join LpuAreaPsychCareCard area on area.lpuArea_id=${param.id} where pcc.id=area.careCard_id and area.finishDate is not null"/>
      						<msh:table name="listCloseCard" action="entityParentView-psych_careCard.do" idField="1">
      							<msh:tableColumn columnName="#" property="sn"/>
      							<msh:tableColumn columnName="№карты" property="2"/>
      							<msh:tableColumn columnName="ФИО пациента" property="3"/>
      							<msh:tableColumn columnName="Год рождения" property="6"/>
      							<msh:tableColumn columnName="Дата прибытия" property="4"/>
      							<msh:tableColumn columnName="Дата убытия" property="5"/>
      						</msh:table>
      				</msh:section>
      			</td>
      		</tr>
      	</tbody>
      </table>
      </msh:section>
      --%>
    </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="b08ac525-aee5-493c-bd30-a245e7c80200">
      <msh:ifFormTypeIsView formName="mis_lpuAreaForm" guid="24576531-bcea-4103-9c80-fe9e95c4dfaf">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/LpuArea/Edit" params="id" action="/entityEdit-mis_lpuArea" name="Изменить" guid="dc8c6883-5faf-41b0-b068-296214839e40" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeAreViewOrEdit formName="mis_lpuAreaForm" guid="b0b9ca4d-8f54-4f15-87a7-34b467ed10d5">
        <msh:sideLink key="ALT+3" roles="/Policy/Mis/LpuAreaAddressText/Create" params="id" action="/entityParentPrepareCreate-mis_lpuAreaAddressText" name="Добавить прикрепленные дома" guid="84c60b19-bebb-4109-9e22-8a20e6dcc0b0" />
        <msh:sideLink key="ALT+5" roles="/Policy/Jaas/SecPolicy/Create" params="id" action="/mis_lpuAreaUpdateJaasPolicy" name="Перекрепить" title="Перекрепить население" guid="525ebc5e-181a-4ded-84ec-228d01869605" confirm="Перекрепить все население?" />
        <msh:sideLink key="ALT+6" roles="/Policy/Mis/LpuArea/View" params="id" action="/mis_bypassAreaExcelExport.do?type=area" name="Печать обходного листа" guid="b2a729f4-c83e-45af-ae91-a905d22ec44f" />
        <msh:sideLink key="ALT+DEL" roles="/Policy/Mis/LpuArea/Delete" params="id" action="/entityParentDeleteGoParentView-mis_lpuArea" name="Удалить" confirm="Удалить участок?" guid="d3d19781-f1b0-42b3-a314-f5e6a2b55584" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuAreaForm" guid="04eb4fb1-03b4-4011-9e85-30cd955d2c41" />
  </tiles:put>
</tiles:insert>

