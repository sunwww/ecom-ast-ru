<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="ru.ecom.web.login.LoginInfo"%>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Template">Шаблоны</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:template_menu currentAction="all"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:ifInRole roles="/Policy/Diary/Template/View">
      <msh:section createRoles="/Policy/Diary/Template/Create" createUrl="entityPrepareCreate-temp_protocol.do" listUrl="js-temp_protocol-listTemplate.do" title="Шаблоны протоколов">
       Шаблон протокола нужен для удобства заполнения дневников специалистов однотипными данными.
      </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/Pattern/View">
      <msh:section>
      	<msh:sectionTitle> <msh:link action="js-cal_pattern-listTemplate.do?id=0">Шаблоны календарей</msh:link> </msh:sectionTitle>
        <msh:sectionContent>Необходим для создания шаблона рабочего времени специалиста.</msh:sectionContent>
      </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/Prescription/Template/View">
      <msh:section title="Шаблоны назначений"
      	listUrl="js-pres_template-listTemplate.do" createRoles="/Policy/Mis/Prescription/Template/Create"
      	createUrl="entityPrepareCreate-pres_template.do"
      >Дает возможность создавать шаблоны назначений</msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Diary/KeyWord/View">
      <msh:section >
      	<msh:sectionTitle> <msh:link action="entityList-diary_templateword.do">Ключевые слова</msh:link> </msh:sectionTitle>
        <msh:sectionContent>Дает возможность быстрого набора заключений специалиста</msh:sectionContent>
      </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/Template/Category/View">
      <msh:section>
      	<msh:sectionTitle> <msh:link action="entityParentList-temp_category.do?id=0">Классификатор</msh:link> </msh:sectionTitle>
        <msh:sectionContent>Создан для группировки шаблонов</msh:sectionContent>
      </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Voc/VocDocumentParameter/View">
    	<msh:section listUrl="entityList-voc_documentParameter.do" title="Параметры для внешней лаборатории">
    	</msh:section>
    </msh:ifInRole>
 
    <%
    String username = LoginInfo.find(request.getSession()).getUsername();
    request.setAttribute("username", username);
	%>
    <msh:ifInRole roles="/Policy/Mis/Calc/Calculator"> 
    <ecom:webQuery name="calcs" nativeSql="select id, name,comment from calculator where username= '${username}' "/>
      	<msh:section  createRoles="/Policy/Mis/Calc/Calculator" createUrl="entityPrepareCreate-calc_calculator.do" title="Калькуляторы">
      		<msh:sectionContent>
		      	<msh:table name="calcs" action="entityParentView-calc_calculator.do" idField="1">
		      		<msh:tableColumn property="sn" columnName="##"/>
		      		<msh:tableColumn property="2" columnName="Название" />
		      		<msh:tableColumn property="3" columnName="Назначение" />
		      	</msh:table>
      		</msh:sectionContent>
      	</msh:section>
    </msh:ifInRole>
    
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
 <script type="text/javascript">
 //LoginInfo.find(request.getSession()).getUsername() ;
 onPreCreate(aForm, aCtx)
 function onPreCreate(aForm, aCtx) {
		aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	}	
 </script>
 </tiles:put>
  
</tiles:insert>

