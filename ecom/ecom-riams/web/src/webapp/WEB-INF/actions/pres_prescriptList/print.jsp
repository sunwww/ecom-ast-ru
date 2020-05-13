<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >


  <tiles:put name="body" type="string">
          <ecom:parentEntityListAll formName="pres_prescriptionForm" attribute="prescription" />
          <msh:table name="prescription" action="entitySubclassView-pres_prescription.do" idField="id">
            <msh:tableColumn columnName="Назначение" property="descriptionInfo" />
            <msh:tableColumn columnName="Дата и время назначения" property="prescriptTimeStamp"  />
            <msh:tableColumn columnName="Роспись врача" property="signature"  />
            <msh:tableColumn columnName="Роспись мед.сестры" property="signature"  />
            <msh:tableColumn columnName="Дата и время отмены" property="prescriptCancelTimeStamp" />
            <msh:tableColumn columnName="Роспись врача" property="signature" />
            <msh:tableColumn columnName="Роспись мед.сестры" property="signature" />
          </msh:table>
  </tiles:put>
</tiles:insert>

