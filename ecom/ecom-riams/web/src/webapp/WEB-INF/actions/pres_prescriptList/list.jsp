<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient" title="Лист назначений" />
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Лист назначений" />
    <script type="text/javascript">
    function printPrescriptionList(id) {
    window.document.location='print-prescriptList_1.do?s=HospitalPrintService&m=printPrescriptList&id='+id;
    }
    function printPrescriptionListTotal (id) {
        window.document.location='print-prescriptListTotal.do?s=HospitalPrintService&m=printPrescriptListTotal&id='+id;
    }
    </script>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Показать" />

  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section title="Список листов назначений  <b><u><a href='javascript:void(0)' onclick='printPrescriptionListTotal(${param.id});'> Печать сводного ЛН</a></u></b>">
        <ecom:webQuery name="allsvodlist" nativeSql="
            select pl.id,pl.createusername,pl.createdate, dep.name
            from prescriptionlist pl
            left join medcase mc on mc.id=pl.medcase_id
            left join mislpu dep on dep.id=mc.department_id
            where pl.medcase_id in (select id from medcase where id=${param.id} or parent_id=${param.id})
    "/>
	    <msh:table name="allsvodlist" action="entityParentView-pres_prescriptList.do" idField="1">
            <msh:tableColumn columnName="#" property="sn"/>
	        <msh:tableColumn columnName="Назначил" property="2" />
	        <msh:tableColumn columnName="Дата создания" property="3" />
            <msh:tableColumn columnName="Отделение" property="4" />
          <msh:tableButton property="1" buttonShortName="Печать ЛН" buttonFunction="printPrescriptionList" />
	    </msh:table>
    </msh:section>
    <tags:pres_prescriptByListTotal field="sls.id=${param.id}" />
  </tiles:put>
</tiles:insert>