<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал обращений по отделению</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_journalCurrentByUserDepartment"/>
  </tiles:put>
  <tiles:put name="body" type="string">    
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
		    <msh:form action="/stac_journalCurrentByUserDepartment.do" defaultField="department" disableFormDataConfirm="true" method="POST" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
		    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff"  colsWidth="10%,89%">
		      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9" >
		        <msh:separator label="Параметры поиска" colSpan="6" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
		      </msh:row>
			      <msh:row>
			      	<msh:autoComplete property="department" vocName="lpu" label="Отделение" fieldColSpan="4" horizontalFill="true"/>
			      	<msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoByDate">
				           <td>
				            <input type="submit" value="Найти" />
				          </td>
			      	</msh:ifNotInRole>
			      </msh:row>
			      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoByDate">
			      	<msh:row>
			      		<msh:textField property="dateStart" label="Дата"/>
				           <td>
				            <input type="submit" value="Найти" />
				          </td>
			      	</msh:row>
			      </msh:ifInRole>
		    </msh:panel>
		    </msh:form>
      </msh:ifInRole>
        <%
		    Long department = (Long)request.getAttribute("department") ;
		    if (department!=null && department.intValue()>0 )  {
    	%>
    <msh:section>
    <msh:sectionTitle>Журнал состоящих пациентов в отделение  ${departmentInfo} на текущее момент</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="
    select m.id,m.dateStart,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    	,pat.birthday,sc.code  
    from medCase m 
    left join MedCase as sls on sls.id = m.parent_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left outer join Patient pat on m.patient_id = pat.id 
    where m.DTYPE='DepartmentMedCase' and m.department_id='${department}' 
    and m.transferDate is null and m.dateFinish is null"
     guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table name="datelist" action="entityParentView-stac_slo.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } %>

  </tiles:put>
</tiles:insert>

