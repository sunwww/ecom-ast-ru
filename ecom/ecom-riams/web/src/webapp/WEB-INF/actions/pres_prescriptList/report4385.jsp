<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="LaboratoryJournal" title="Журнал назначений" />
   
  </tiles:put>
  <tiles:put name="side" type="string">
     <msh:sideMenu>
                <tags:laboratory_menu currentAction="pres_journal"/>
        </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String typeState =ActionUtil.updateParameter("PrescriptJournal","typeState","3", request) ;
  String typeGroup =ActionUtil.updateParameter("PrescriptJournal","typeGroup","1", request) ;
  String typeReestr =request.getParameter("typeReestr") ;
  if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {
	 %>
	 
  <msh:form action="/pres_report4385.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
	
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="prescriptType" parentId="204" fieldColSpan="4" horizontalFill="true" label="Биологический материал" vocName="vocUserValueByDomain"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="diagnosis" fieldColSpan="4" horizontalFill="true" label="Диагноз" vocName="vocIdc10"/>
        </msh:row>
       
      <msh:row>
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    <%} %>
   
    <%
    String dateFrom = request.getParameter("beginDate");
    if (dateFrom!=null&&!dateFrom.equals("")) {
        %>
    <input type="button" onclick="mshSaveTableToExcelById('report4385ResultTable')" value="Сохранить в excel">
    <%
    	out.print(request.getAttribute("tableList"));
     } %>
    <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
    <script type='text/javascript'>
      checkFieldUpdate('typeGroup','${typeGroup}',1) ;
      checkFieldUpdate('typeState','${typeState}',1) ;
    </script>
  </tiles:put>
</tiles:insert>

