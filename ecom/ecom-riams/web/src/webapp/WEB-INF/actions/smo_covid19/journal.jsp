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
            <msh:textField property="dateBegin" label="Дата с" />
            <msh:textField property="dateEnd" label="по" />
          </msh:row>
        <div>
          <h3>Примечание</h3>
          <p>для отчёта: <b>дата поступления</b></p>
          <p>для реестра первичных: <b>дата поступления</b></p>
          <p>для реестра повторных: <b>дата повторной выгрузки</b></p>
          <p>для реестра выписных: <b>дата выгрузки при выписке</b></p>
          <p>для журнала: <b>1 день (дата первичной выгрузки)</b></p>
          <p>в реестр попадают только актуальные карты</p>
        </div>
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
      <a href='javascript:printReestr(1)'>Печать реестра ПЕРВИЧНЫХ</a>
      <a href='javascript:printReestr(2)'>Печать реестра ПОВТОРНЫХ</a>
      <a href='javascript:printReestr(3)'>Печать реестра ПРИ ВЫПИСКЕ</a>
      <a href='javascript:printJournal()'>Печать журнала</a>
       <msh:sectionContent>
        <ecom:webQuery name="list_covid" nativeSql="select  
        c.id, pat.patientinfo,  c.epidnumber as f3_enumb
        ,case when c.noActual='1' then 'background-color:#979090; color:black' when c.exportDate is not null then 'background-color:#8ee68e; color:black'
     when (c.labResult is not null and c.labResult!='') then 'background-color: #f0ba57; color:black' else '' end as f4_styleRow
     ,dep.name as f5_dep
     ,sls.datestart as f6_datestart
     ,to_char(c.exportfirstdate,'dd.MM.yyyy') as f7_expFirst
     ,to_char(c.exportdoubledate,'dd.MM.yyyy') as f8_expDouble
     ,to_char(c.exportdischargedate,'dd.MM.yyyy') as f9_expDisch
     ,vhr.name as f10_vhrName
     ,to_char(c.ishoddate,'dd.MM.yyyy') as f11_dateResHosp
        ,st.code as f12_hist
        ,(select idc.code from covid19 where medcase_id=sls.id and id=(select max(id) from covid19 where medcase_id=sls.id)) as f13_mkb
        ,vct.name as f14_ct
    from Covid19 c
    left join Patient pat on pat.id=c.patient_id
    left join medcase sls on sls.id=c.medcase_id
    left join mislpu dep on dep.id=sls.department_id
    left join vochospitalizationresult vhr on vhr.id=c.hospresult_id
    left join statisticstub st on st.medcase_id=sls.id
    left join vocidc10 idc on idc.id=c.mkb_id
    left join vocct vct on vct.id=c.ct_id
    ${sqlAdd}
    ${periodSql}
    order by c.createdate, c.createtime" />
        <msh:table name="list_covid" action="entityParentView-smo_covid19.do" openNewWindow="true"
                   printToExcelButton="Сохранить в excel" idField="1" styleRow="4" noDataMessage="Не найдено">
          <msh:tableColumn columnName="#" property="sn"/>
          <msh:tableColumn columnName="Пациент" property="2" width="15"/>
          <msh:tableColumn columnName="Отделение поступления" property="5" width="15"/>
          <msh:tableColumn columnName="Дата поступления" property="6" width="8"/>
          <msh:tableColumn columnName="Номер истории" property="12" width="8"/>
          <msh:tableColumn columnName="Эпид. номер" property="3" width="8"/>
          <msh:tableColumn columnName="МКБ" property="13" width="6"/>
          <msh:tableColumn columnName="КТ" property="14" width="6"/>
          <msh:tableColumn columnName="Первичная выгрузка" property="7" width="8"/>
          <msh:tableColumn columnName="Повторная выгрузка" property="8" width="8"/>
          <msh:tableColumn columnName="Выгрузка при выписке" property="9" width="8"/>
          <msh:tableColumn columnName="Результат госп." property="10" width="8"/>
          <msh:tableColumn columnName="Дата выписки" property="11" width="8"/>
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

     //печать реестра
     function printReestr(num) {
       var file = 'stac_print_covid19_reestr';
       if (num == 3)
         file += "_disch";
       window.location.href = 'print-' + file + '.do?s=PatientPrintService&m=printCovidReestr&dateBegin='
               + $('dateBegin').value + '&dateEnd=' + $('dateEnd').value + '&type=' + num;
     }

      //печать журнала
      function printJournal() {
        window.location.href = 'print-stac_print_covid19_journal.do?s=PatientPrintService&m=printCovidJournal&dateBegin='
                + $('dateBegin').value + '&dateEnd=' + $('dateEnd').value;
      }

    </script>
    <%}else {%>
    <i>Выберите параметры поиска и нажмите "Найти" </i>
    <% }   %>
  </tiles:put>
</tiles:insert>