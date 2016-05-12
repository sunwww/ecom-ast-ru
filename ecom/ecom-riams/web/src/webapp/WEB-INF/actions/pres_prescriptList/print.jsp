<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >


  <tiles:put name="body" type="string">
          <ecom:parentEntityListAll formName="pres_prescriptionForm" attribute="prescription" guid="8399625f-d21d-4cf3-b87f-2f48b2f43626" />
          <msh:table name="prescription" action="entitySubclassView-pres_prescription.do" idField="id" guid="95f63378-5b89-4f15-ad12-ba3f87976c52">
            <msh:tableColumn columnName="Назначение" property="descriptionInfo" guid="e2842846-7904-4038-8209-5a296f56f0fe" />
            <msh:tableColumn columnName="Дата и время назначения" property="prescriptTimeStamp"  />
            <msh:tableColumn columnName="Роспись врача" property="signature"  />
            <msh:tableColumn columnName="Роспись мед.сестры" property="signature"  />
            <msh:tableColumn columnName="Дата и время отмены" property="prescriptCancelTimeStamp" />
            <msh:tableColumn columnName="Роспись врача" property="signature" />
            <msh:tableColumn columnName="Роспись мед.сестры" property="signature" />
          </msh:table>
  </tiles:put>
</tiles:insert>

