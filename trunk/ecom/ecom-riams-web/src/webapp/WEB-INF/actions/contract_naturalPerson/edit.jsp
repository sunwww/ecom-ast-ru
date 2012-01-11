<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entitySaveGoView-contract_naturalPerson.do" defaultField="patientName" guid="6f65ff8f-8528-410f-96ac-cc08872b5901">
      <msh:hidden property="id" guid="e6bcfe59-6541-4e49-afb8-15a0f7059230" />
      <msh:hidden property="saveType" guid="81d410cd-ac37-4309-9fe7-a8d0943c5ae1" />
      <msh:panel guid="41b857b2-e843-401e-8a0f-33f2fbd66417">
        <msh:row guid="b18bbff8-d1ea-4681-bd11-5a712a9fac54">
          <msh:autoComplete property="patient" label="Пациент" vocName="patient" horizontalFill="true" size="150" guid="221d7df7-faf5-440d-916d-f1d486129027" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" guid="b57bcf78-314d-4821-a8d5-4606a69280dd" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Contract" beginForm="contract_naturalPersonForm" guid="e33fe6e9-a1e4-492c-ac29-f07b1db0efe0" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="dea2e8ca-c7db-4592-a9ec-727f6d330ad1">
      <msh:sideLink key="ALT+2" params="id" action="/entityEdit-contract_naturalPerson" name="Изменить" title="Изменить" roles="" guid="6050d629-20dd-4945-83f5-f0821ad67497" />
      <msh:sideLink key="ALT+DEL" params="id" action="/entityDelete-contract_naturalPerson" name="Удалить" title="Удалить" roles="" guid="91812d50-84d8-428a-a624-d1c59d868dfa" />
    </msh:sideMenu>
    <tags:contractMenu currentAction="naturalPerson" />
  </tiles:put>
</tiles:insert>

