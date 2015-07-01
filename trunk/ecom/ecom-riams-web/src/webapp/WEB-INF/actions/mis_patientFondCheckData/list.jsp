<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	
 <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Список проверок по базе ФОМС</msh:title>
 </tiles:put>
 <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/entityView-mis_lpu" name="⇧ К ЛПУ"/>
            <msh:sideLink key='ALT+N' params="id" action="/entityParentPrepareCreate-mis_stateList" name="Добавить должность в штатное расписание" />
            <msh:link action="javascript:checkAllPatients()">Запустить новую проверку всех пациентов</msh:link>
        </msh:sideMenu>
    </tiles:put>

  <tiles:put name="body" type="string">
  
  <msh:section>
     
		       <msh:sectionTitle>Список проверок:</msh:sectionTitle>
		       <msh:sectionContent>
		       <ecom:webQuery name="fondCheck" 
		        nativeSql="select pfcd.id, pfcd.comment, pfcd.startDate
		        from PatientFondCheckData pfcd order by startDate desc"	nameFldSql="fondCheckSQL"/>
		        <msh:tableNotEmpty name="fondCheck">
			        <msh:table viewUrl="entityView-mis_patientFondCheckData.do" hideTitle="false"  idField="1" name="fondCheck" action="entityView-mis_patientFondCheckData.do" >
			            <msh:tableColumn  columnName="Номер" property="1" />
			            <msh:tableColumn columnName="Дата проверки" identificator="false" property="3" />
			        </msh:table>
		        </msh:tableNotEmpty>
		        </msh:sectionContent>
    </msh:section>
  </tiles:put>
   <tiles:put name="javascript" type="string">
      <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
       <script type="text/javascript">
  function checkAllPatients() {
    	PatientService.checkAllPatients();
    	alert ('Запущена новая проверка');
    }
  </script>
    </tiles:put>
</tiles:insert>