<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/move_to_archive.do" defaultField="hello">
      
      <msh:panel>
        <msh:row><td></td>
          <msh:autoComplete vocName="vocLpuHospOtdAll" property="department" size="50" fieldColSpan="3" label="Отделение выписки" />
        </msh:row>
        <msh:row><td>Дата выписки:</td>
			<msh:textField property="dateBegin" label="c"/>
			<msh:textField property="dateEnd" label="по"/>
		</msh:row>
        <msh:submitCancelButtonsRow labelSave="Поиск" labelSaving="Поиск..." colSpan="4" />
      </msh:panel>
    </msh:form>
    <%
    String beginDate = request.getParameter("dateBegin") ;
	if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("dateEnd") ;		
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		request.setAttribute("dateStart", beginDate) ;
		request.setAttribute("dateFinish", finishDate) ;
		
    String dep = request.getParameter("department");
    String orderBySql  = "pat.lastname, sls.datefinish desc"; 
    String depSql = "";
    if (dep!=null&&!dep.equals("")&&!dep.equals("0")) {
    	depSql+=" and slo.department_id='"+dep+"'";
    
    request.setAttribute("depSql",depSql);
    request.setAttribute("orderBySql",orderBySql);
    %>
    
    <ecom:webQuery name = "hospitalList" nativeSql="
    select ss.id as ssid, sls.id as slsid, ss.code as code, pat.patientinfo as pat
    , to_char (sls.dateStart,'dd.MM.yyyy') as dateStart, to_char (sls.dateFinish,'dd.MM.yyyy') as dateFinish,
    case when sls.result_id='6' then '+' else '' end
from medcase sls
left join statisticstub ss on ss.medcase_id=sls.id
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' and slo.datefinish is not null
left join patient pat on pat.id=sls.patient_id
left join vochospitalizationresult vhr on sls.result_id = vhr.id 
where sls.dtype='HospitalMedCase' and sls.deniedhospitalizating_id is null and sls. datefinish between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy') and sls.dischargetime is not null and ss.archiveCase is null
${depSql}
order by ${orderBySql}
    " />
    <msh:section>
    <msh:sectionContent>
				<msh:table name="hospitalList" action="javascript:void()" idField="1">
					
					<msh:tableButton role="/Policy/Mis/ArchiveCase/Create" property="1" buttonFunction="Test" buttonName="Передать в архив" addParam="0,this);javascript:void(0" buttonShortName="Передать в архив"/>
					<msh:tableColumn columnName="Номер ИБ" property="3" />
					<msh:tableColumn columnName="ФИО пациента" property="4" />
					<msh:tableColumn columnName="Дата начала госпитализации" property="5" />
					<msh:tableColumn columnName="Дата окончания госпитализации" property="6" />
					<msh:tableColumn columnName="Умер" property="7" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
    <%} else {  
    	%>
    	<p>Укажите отделение!</p>
    <%}}%>
  </tiles:put>
  <tiles:put name='side' type='string'> 
  
  <msh:sideLink  params="" roles="/Policy/Mis/ArchiveCase/View" action="/mis_archive_journal" name="Перейти к журналу переданных карт" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/ArchiveService.js"></script>
  	<script type="text/javascript">
  	function createArchiveCase (aStatCardId,o,btn) {
  		
  		
  				
  		ArchiveService.putCardInArchive (aStatCardId, {
  			callback: function (a) {
  					var arr = a.split("@");
  					if (""+arr[0]!=''){
  					alert ("Ошибка: "+a);
  					}
  					arr[1]
  					alert (btn);
  				
  			}
  		})
  	}
  	</script> 
  <script type="text/javascript">
  	function Test (aStatCardId,o,btn) {
  		var a =btn.value 
  		switch (a) {
  		  case 'Передать в архив':
  		    btn.value = 'Убрать из архива'
  		    	ArchiveService.putCardInArchive (aStatCardId, {
  		  			callback: function (a) {
  		  					var arr = a.split("@");
  		  					if (""+arr[0]!=''){
  		  					alert ("Ошибка: "+a);
  		  					}
  		  			}
  		  		})
  		    break
  		  case 'Убрать из архива':
  			  
  		    btn.value = 'Передать в архив'
  		    	ArchiveService.getCardFromArchive(aStatCardId)
  		    break
  		 
  		}

  		
  	}
  	</script>
  
  </tiles:put>
</tiles:insert>

