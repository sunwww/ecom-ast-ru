<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="java.util.List"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.ejb.services.query.IWebQueryService"%>
<%@page import="java.util.Collection"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Diet" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Журнал диет" />
   
  </tiles:put>
  <tiles:put name="side" type="string">
     <msh:sideMenu>
  
        </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String typeState =ActionUtil.updateParameter("PrescriptJournal","typeState","3", request) ;
  String typeGroup =ActionUtil.updateParameter("PrescriptJournal","typeGroup","1", request) ;
  String typeReestr =request.getParameter("typeReestr") ;
  if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {
	 %>
	 
  <msh:form action="/diet_dietJournal.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
	
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="diet"  fieldColSpan="4" horizontalFill="true" label="Диета" vocName="Diet"/>
        </msh:row>
        
      <msh:row>
        <msh:textField property="beginDate" label="Период" />
        
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
    	out.print(request.getAttribute("tableList"));
     } %>
    <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/
           <script type='text/javascript'>
       //    checkFieldUpdate('typeIntake','${typeIntake}',1) ;
         //  checkFieldUpdate('typeGroup','${typeGroup}',1) ;
//           checkFieldUpdate('typeState','${typeState}',1) ;
        //   checkFieldUpdate('typeTransfer','${typeTransfer}',1) ;
       //    checkFieldUpdate('typeService','${typeService}',1) ;

  </script>
  </tiles:put>
</tiles:insert>

