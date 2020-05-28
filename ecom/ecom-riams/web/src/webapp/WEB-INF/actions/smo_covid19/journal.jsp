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
    sqlAdd=" where (c.noActual is null or c.noActual='0') and c.exportDate is null ";
    break;
    case "actual":
      sqlAdd=" where (c.noActual is null or c.noActual='0') ";
      break;
    default:
      sqlAdd="";
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
          <td>
            <input type="submit" value="Найти" />
          </td>
        </msh:row>

      </msh:panel>
    </msh:form>

    <msh:section>
      <msh:sectionTitle>Результаты поиска COVID 19</msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery name="list_covid" nativeSql="select  
        c.id, pat.patientinfo,  to_char(c.createdate,'dd.MM.yyyy')||' '|| cast(c.createtime as varchar(5))
        ,case when c.noActual='1' then 'background-color:#979090; color:black' when c.exportDate is not null then 'background-color:#8ee68e; color:black'
     when (c.labResult is not null and c.labResult!='') then 'background-color: #f0ba57; color:black' else '' end as f4_styleRow
     ,dep.name as f5_dep
     ,sls.datestart as f5_datestart
    from Covid19 c
    left join Patient pat on pat.id=c.patient_id
    left join medcase sls on sls.id=c.medcase_id
    left join mislpu dep on dep.id=sls.department_id
    ${sqlAdd}
    order by c.createdate, c.createtime" />
        <msh:table name="list_covid" action="entityParentView-smo_covid19.do" idField="1" styleRow="4" noDataMessage="Не найдено">
          <msh:tableColumn columnName="#" property="sn"/>
          <msh:tableColumn columnName="Пациент" property="2"/>
          <msh:tableColumn columnName="Отделение поступления" property="5"/>
          <msh:tableColumn columnName="Дата поступления" property="6"/>
          <msh:tableColumn columnName="Дата и время создания карты" property="3"/>
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
  </tiles:put>
</tiles:insert>

