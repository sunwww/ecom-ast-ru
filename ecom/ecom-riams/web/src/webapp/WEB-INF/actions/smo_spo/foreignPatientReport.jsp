<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Стоимость поликлиническго обслуживания (СПО)" />
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
      <input type="button" id="submitButton" value="Сформировать файл" onclick="javascript:makeReport()">
    </msh:row>
    <msh:row>
      <div style="font-size: 40px; color: #00aa00" id="resultDiv"></div>
    </msh:row>
    </msh:panel>
    </msh:form>

  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
  <script type="text/javascript">
    function makeReport() {
        //jQuery("input:button").prop("disabled",true);
        $('submitButton').disabled=true;
        //var lpu = jQuery('#lpuDirect').val();
        //var dateFrom = jQuery("#dateBegin").val();
        //var dateTo = jQuery("#dateEnd").val();
        var lpu = $('lpuDirect').value;
        var dateFrom = $("dateBegin").value;
        var dateTo = $("dateEnd").value;
        if (lpu=="") {alert ("Укажите код ЛПУ в справочнике МИАЦа!");$('submitButton').disabled=false;return;}
        if (dateFrom==""||dateTo=="") {alert ("Укажите период!");$('submitButton').disabled=false;return;}
        var type = jQuery("input[name=typeGroup]:radio:checked").val();
        /*var list = document.getElementsByName('typeGroup');
        for (var i=0;i<list.length;i++) {
            if (list[i].checked) {
                type=list[i].value;
                break;
            }
        }*/
        var reportType = jQuery("input[name=typeReportGroup]:radio:checked").val();
        alert(reportType);
       // alert (dateFrom+"<>"+dateTo+"<>"+type);
        HospitalMedCaseService.getMedcaseCost(dateFrom,dateTo,type,lpu, reportType, {
            callback: function (fileUrl) {
                $('resultDiv').innerHTML="<a target='_blank' href='" + fileUrl + "'>Сохранить файл</a>";
                $('submitButton').disabled=false;
            }
        });
        $('resultDiv').innerHTML="Подождите, идет формирование файла...";
    }
  </script>
  </tiles:put>
</tiles:insert>
