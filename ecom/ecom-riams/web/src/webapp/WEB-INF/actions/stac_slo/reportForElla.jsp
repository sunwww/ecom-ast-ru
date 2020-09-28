<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Список всех CЛО по дате" />
    
  </tiles:put>
  <tiles:put name="side" type="string" >

  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:sectionTitle>Отчет для Эллы</msh:sectionTitle>
    <%

      String dateBegin = request.getParameter("dateBegin") ;
      if (dateBegin!=null && !dateBegin.equals("")) {
        request.setAttribute("dateBegin","'"+dateBegin+"'");
      }
      else
        request.setAttribute("dateBegin","current_date");
    %>
    <msh:form action="/stac_reportForElla.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
      <msh:panel>
        <msh:row>
          <msh:textField property="dateBegin" label="Период с" />
        </msh:row>
        <msh:row>
          <td>
            <input type="button" onclick="document.forms[0].submit();" value="Найти" />
          </td>
        </msh:row>
      </msh:panel>
    </msh:form>
    <ecom:webQuery name="datelist" nativeSql="select d.id, pat.patientinfo, d.dateregistration, d.timeregistration
      ,coalesce (substring(d.record,case when position('SP' in upper(d.record))>0 then position('SP' in upper(d.record))
      when position('SР' in upper(d.record))>0 then position('SР' in upper(d.record)) else position('САТУРАЦИ' in upper(d.record)) end,20),'') as f5
      ,dep.name
      ,d.record
      , sls.datestart as satestart
      from diary d
      left join medcase slo on slo.id=d.medcase_id
      left join medcase sls on sls.id=slo.parent_id
      left join mislpu  dep on dep.id=slo.department_id
      left join patient pat on pat.id=slo.patient_id
      where d.dateregistration =${dateBegin} and slo.department_id in (500,501,502,503,504,505,506,507,508,509,510,511)
order by pat.patientinfo, d.dateregistration desc , d.timeregistration  desc "
    />
    <msh:table printToExcelButton="Excel" name="datelist" action="entityParentView-smo_visitProtocol.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Пациент" property="2" />
      <msh:tableColumn columnName="Дата госпиталиция" property="8" />
      <msh:tableColumn columnName="Дата протокола" property="3" />
      <msh:tableColumn columnName="Время протокола" property="4" />
      <msh:tableColumn columnName="Отделение" property="6" />
      <msh:tableColumn columnName="Сатурация" property="5" />
      <msh:tableColumn columnName="Дневник целиком" property="7" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
	  <tags:stac_journal currentAction="${param.action}"/>
  </tiles:put>
</tiles:insert>

