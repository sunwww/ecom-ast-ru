<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Отчёт для МИАЦа и ЦБРФ" />
  </tiles:put>
  <tiles:put name="side" type="string">
   
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/journal_foreignPatients.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">

    <msh:panel colsWidth="1%,1%,1%">
    <msh:row>
      <msh:separator label="Параметры поиска" colSpan="7" />
    </msh:row>
      <msh:row>
        <msh:textField label="Код ЛПУ" property="lpuDirect"/>
              </msh:row>
    <msh:row>
      <msh:textField property="dateBegin" label="Период с" />
      <msh:textField property="dateEnd" label="по" />
    </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
          <input type="radio" name="typeGroup" checked value="inos">  Иностранные граждане
        </td>
        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
          <input type="radio" name="typeGroup" value="inog">  Иногородние граждане
        </td>
      </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
          <input type="radio" name="typeReportGroup" checked value="MIAC">  Отчет для МИАЦа
        </td>
        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
          <input type="radio" name="typeReportGroup" value="BANK">  Отчет для ЦБРФ
        </td>
      </msh:row>
    <msh:row>
      <input type="button" id="submitButton" value="Сформировать" onclick="javascript:makeReport()">
    </msh:row>
    <msh:row>
      <div style="font-size: 40px; color: #00aa00" id="resultDiv"></div>
    </msh:row>
      <table hidden id="resultTable" border="1px">
        <th width="5%">Номер строки</th>
        <th width="6%">Код территории</th>
        <th width="5%">Месяц отчетного квартала</th>
        <th width="6%">Код страны регистрации нерезидентов</th>
        <th width="6%">Код возрастной регистрации нерезидентов</th>
        <th width="6%">Код вида МП</th>
        <th width="6%">Код условия оказания МП</th>
        <th width="6%">Код формы оказания МП</th>
        <th width="6%">Код продолжительности осуществления МП</th>
        <th width="6%">Код профиля МП</th>
        <th width="6%">Число обращений нерезидентов</th>
        <th width="6%">Код истоничка финансирования МД</th>
        <th width="6%">Код формы оплаты МД</th>
        <th width="12%">Стоимость МД</th>
        <th width="12%">Примечание</th>
        <tbody id="resultTableBody" name="resultTableBody"></tbody>
      </table>
    </msh:panel>
    </msh:form>
<% request.setAttribute("numCBRF", ActionUtil.getDefaultParameterByConfig("numCBRF","",request));%>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
  <script type="text/javascript">
    const numCBRF = "${numCBRF}";
    function makeReport() {
        $('submitButton').disabled=true;
        var lpu = $('lpuDirect').value;
        var dateFrom = $("dateBegin").value;
        var dateTo = $("dateEnd").value;
        if (lpu=="") {alert ("Укажите код ЛПУ в справочнике МИАЦа!");$('submitButton').disabled=false;return;}
        if (dateFrom==""||dateTo=="") {alert ("Укажите период!");$('submitButton').disabled=false;return;}
        var type = jQuery("input[name=typeGroup]:radio:checked").val();

        var reportType = jQuery("input[name=typeReportGroup]:radio:checked").val();

        var tbl =jQuery('#resultTableBody');

        if (reportType=='MIAC')
          jQuery('#resultTable').hide();
        else if (reportType=='BANK')
          jQuery('#resultTable').show();
        HospitalMedCaseService.getMedcaseCost(dateFrom,dateTo,type,lpu, reportType, {
            callback: function (result) {
                if (reportType=='MIAC')
                  $('resultDiv').innerHTML = "<a target='_blank' href='" + result + "'>Сохранить файл</a>";
                else {
                  if (!result || result.length==0)
                    tbl.html("Нет случаев");
                  else {
                    tbl.html("");
                    result=JSON.parse(result);
                    for (var i=0;i<result.length;i++) {
                      var el = result[i];

                      tbl.append("<tr><td align='center'><b>"+(i+1)+"</b></td>" +
                              "<td align='center'>"+'12'+"</td>" +
                              "<td align='center'>"+$('dateBegin').value.substring(3,5)+"</td>" +
                              "<td align='center'>"+el.region+"</td>" +
                              "<td align='center'>"+el.age+"</td>" +
                              "<td align='center'>"+el.code_vid_mp+"</td>" +
                              "<td align='center'>"+el.code_usl_mp+"</td>" +
                              "<td align='center'>"+el.code_form_ok+"</td>" +
                              "<td align='center'>"+el.code_prod+"</td>" +
                              "<td align='center'>"+el.profile+"</td>" +
                              "<td align='center'>"+el.patientCount+"</td>" +
                              "<td align='center'>"+el.code_fin_md+"</td>" +
                              "<td align='center'>"+el.pay+"</td>" +
                              "<td align='center'>"+(''+(el.sum*numCBRF).toFixed(2)).replace('.',",")+"</td>" +
                              "<td align='center'>"+el.note+"</td>"
                      );
                    }
                    if (reportType=='BANK')
                      $('resultDiv').innerHTML = '<input type="button" value="Сохранить в excel" onclick="mshSaveTableToExcelById(\'resultTable\')">';
                  }
                }
              $('submitButton').disabled = false;
            }
        });
      if (reportType=='MIAC')
        $('resultDiv').innerHTML="Подождите, идет формирование файла...";
      else {
        tbl.html("Подождите...");
        $('resultDiv').innerHTML="Подождите, идет формирование результата...";
      }
    }
  </script>
  </tiles:put>
</tiles:insert>
