<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">            

        </style>

    </tiles:put>
  <tiles:put name="body" type="string">
      <msh:section title="Направления на госпитализацию">
          <ecom:webQuery name="list" nativeSql="
  	select d.id , ml.name ,d.createDate
  	from WorkCalendarHospitalBed d
  	left join MisLpu ml on ml.id=d.directLpu_id
  	where d.visit_id='${param.id}'
  	"/>
          <msh:table name="list" action="entityView-smo_planHospitalByHosp.do" idField="1"
                     viewUrl="entityShortView-smo_planHospitalByHosp.do"
                     deleteUrl="entityParentDeleteGoParentView-smo_planHospitalByHosp.do"
                     editUrl="entityParentEdit-smo_planHospitalByHosp.do"
          >
              <msh:tableColumn columnName="ЛПУ направления" property="2" />
              <msh:tableColumn columnName="Дата создания" property="3" />
          </msh:table>
      </msh:section>
</tiles:put>

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslForm" />
  </tiles:put>

  <tiles:put name="side" type="string">
      <msh:sideMenu title="Планирование госпитализаций">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-smo_planHospitalByHosp" name="Создать" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Create" />
      </msh:sideMenu>
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
<script type="text/javascript" src="./dwr/interface/WorkCalendarService.js"></script>
  	
  	<script type="text/javascript">
	
	</script>
  </tiles:put>

</tiles:insert>

