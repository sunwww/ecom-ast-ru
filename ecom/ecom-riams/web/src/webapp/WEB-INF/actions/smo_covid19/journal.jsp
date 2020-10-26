<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name='title' type='string'>
    <msh:title mainMenu="Medcard">Просмотр данных по пациентам</msh:title>
  </tiles:put>

  <tiles:put name='side' type='string'>
    <tags:ticket_finds currentAction="ticketsByResident"/>
  </tiles:put>
<%
  String typeView = ActionUtil.updateParameter("Form039Action","typeView","3", request) ;
  String sqlAdd;
  switch (typeView) {
    case "noExport":
    sqlAdd=" where (c.noActual is null or c.noActual='0') and c.exportDate is null and ";
    break;
    case "actual":
      sqlAdd=" where (c.noActual is null or c.noActual='0')  and ";
      break;
    default:
      sqlAdd=" where ";
  }
  request.setAttribute("sqlAdd", sqlAdd);
%>
  <tiles:put name="body" type="string">
    <msh:form action="/covid19_journal.do" defaultField="department" disableFormDataConfirm="true" method="GET">
      <msh:panel>
        <msh:row>
          <msh:separator label="Параметры поиска" colSpan="7"/>
        </msh:row>
          <msh:row>
            <td></td>
            <td onclick="this.childNodes[1].checked='checked';">
              <input type="radio" name="typeView" checked value="noExport">  Актуальные невыгруженные
            </td>
            <td colspan="2" onclick="this.childNodes[1].checked='checked';">
              <input type="radio" name="typeView" value="actual">  Все актуальные
            </td>
            <td colspan="2" onclick="this.childNodes[1].checked='checked';">
              <input type="radio" name="typeView" value="all">  Все
            </td>
          </msh:row>
          <msh:row>
            <msh:textField property="dateBegin" label="Дата поступления с" />
            <msh:textField property="dateEnd" label="по" />
          </msh:row>
        <msh:row>
          <td>
            <input type="submit" value="Найти" />
          </td>
        </msh:row>

      </msh:panel>
    </msh:form>
    <%
      String date = request.getParameter("dateBegin") ;
      if (date!=null && !date.equals(""))  {
      String dateEnd = request.getParameter("dateEnd") ;

      if (dateEnd==null||dateEnd.equals("")) {
        dateEnd = date;
      }
      request.setAttribute("periodSql","sls.dateStart between to_date('"+date+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')") ;
    %>
    <msh:section>
      <msh:sectionTitle>Результаты поиска COVID 19</msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery name="list_covid" nativeSql="select  
        c.id, pat.patientinfo,  to_char(c.createdate,'dd.MM.yyyy')||' '|| cast(c.createtime as varchar(5))
        ,case when c.noActual='1' then 'background-color:#979090; color:black' when c.exportDate is not null then 'background-color:#8ee68e; color:black'
     when (c.labResult is not null and c.labResult!='') then 'background-color: #f0ba57; color:black' else '' end as f4_styleRow
     ,dep.name as f5_dep
     ,sls.datestart as f6_datestart
     ,to_char(c.exportfirstdate,'dd.MM.yyyy') as f7_expFirst
     ,to_char(c.exportdoubledate,'dd.MM.yyyy') as f8_expDouble
     ,to_char(c.exportdischargedate,'dd.MM.yyyy') as f9_expDisch
     ,vhr.name as f10_vhrName
     ,to_char(c.ishoddate,'dd.MM.yyyy') as f11_dateResHosp
    from Covid19 c
    left join Patient pat on pat.id=c.patient_id
    left join medcase sls on sls.id=c.medcase_id
    left join mislpu dep on dep.id=sls.department_id
    left join vochospitalizationresult vhr on vhr.id=c.hospresult_id
    ${sqlAdd}
    ${periodSql}
    order by c.createdate, c.createtime" />
        <msh:table name="list_covid" action="entityParentView-smo_covid19.do"
                   printToExcelButton="Сохранить в excel" idField="1" styleRow="4" noDataMessage="Не найдено">
          <msh:tableColumn columnName="#" property="sn"/>
          <msh:tableColumn columnName="Пациент" property="2"/>
          <msh:tableColumn columnName="Отделение поступления" property="5"/>
          <msh:tableColumn columnName="Дата поступления" property="6"/>
          <msh:tableColumn columnName="Дата и время создания карты" property="3"/>
          <msh:tableColumn columnName="Первичная выгрузка" property="7"/>
          <msh:tableColumn columnName="Повторная выгрузка" property="8"/>
          <msh:tableColumn columnName="Выгрузка при выписке" property="9"/>
          <msh:tableColumn columnName="Результат госп." property="10"/>
          <msh:tableColumn columnName="Дата выписки" property="11"/>
        </msh:table>
      </msh:sectionContent>
    </msh:section>
    <table>
      <tr style="background-color:#8ee68e; color:black"><td><p>Выгруженные на портал</p></td></tr>
      <tr style="background-color:#f0ba57; color:black"><td><p>С лаб. результатом, невыгруженные</p></td></tr>
      <tr style="background-color:#979090; color:black"><td><p>Неактуальные</p></td></tr>
    </table>

    <script type='text/javascript'>
      checkFieldUpdate('typeView','${typeView}','noExport') ;
     function checkFieldUpdate(aField,aValue,aDefaultValue) {
       if (jQuery(":radio[name="+aField+"][value='"+aValue+"']").val()!=undefined) {
         jQuery(":radio[name="+aField+"][value='"+aValue+"']").prop('checked',true);
       } else {
         jQuery(":radio[name="+aField+"][value='"+aDefaultValue+"']").prop('checked',true);
       }
     }

    </script>
    <%}else {%>
    <i>Выберите параметры поиска и нажмите "Найти" </i>
    <% }   %>
  </tiles:put>
</tiles:insert>