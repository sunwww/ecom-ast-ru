<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title  mainMenu="StacJournal">Журнал обращений по отделению</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_journalByUserDepartmentOnly"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/stac_journalByUserDepartmentOnly.do" defaultField="department" disableFormDataConfirm="true" method="GET" >
    <msh:panel >
      <msh:row >
        <msh:separator label="Параметры поиска" colSpan="7"  />
      </msh:row>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
	      <msh:row>
	      	<msh:autoComplete property="department" vocName="lpu" label="Отделение" fieldColSpan="6" horizontalFill="true"/>
	      </msh:row>
      </msh:ifInRole>
      <msh:row >
        <msh:checkBox property="dischargeIs" label="Искать по дате выписки"  />
        <msh:textField property="dateBegin" label="Период с"  />
        <msh:textField property="dateEnd" label="по"  />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    Long department = (Long)request.getAttribute("department") ;
    if (date!=null && !date.equals("") && department!=null && department.intValue()>0 )  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений в отделение  ${departmentInfo}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select to_char(${dateSearch},'dd.mm.yyyy'), count(*) as all from medcase where dtype='DepartmentMedCase' and department_id='${department}'  and ${dateSearch}  between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${param.dateEnd}','dd.mm.yyyy')  group by ${dateSearch} "  />
    <msh:table name="journal_priem" action="js-stac_slo-findByDate.do?action=stac_journalByUserDepartmentOnly&dateSearch=${dateSearch}&department=${department}&departmentInfo=${departmentInfo}" idField="1" >
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Дата" property="1"  />
      <msh:tableColumn columnName="Кол-во" identificator="false" property="2"  />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
  </tiles:put>
</tiles:insert>

