<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Стоимость поликлиническго обслуживания (СПО)" />
  </tiles:put>
  <tiles:put name="side" type="string">
   
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/journal_foreignPatients.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="m" id="m" value="f039"/>
    <input type="hidden" name="s" id="s" value="TicketService"/>
    <input type="hidden" name="id" id="id"/>
    <input type="hidden" name="ticketIs" id="ticketIs" value="0"/>
    <msh:panel colsWidth="1%,1%,1%">
    <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
      <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
    </msh:row>
      <msh:row>
        <msh:textField label="Код ЛПУ" property="lpuDirect"/>
              </msh:row>
    <msh:row>
      <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
      <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
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
        var type ;//= jQuery("input[name=typeGroup]:radio:checked").val();
        var list = document.getElementsByName('typeGroup');
        for (var i=0;i<list.length;i++) {
            if (list[i].checked) {
                type=list[i].value;
                break;
            }
        }
       // alert (dateFrom+"<>"+dateTo+"<>"+type);
        HospitalMedCaseService.getMedcaseCost(dateFrom,dateTo,type,lpu, {
            callback: function (fileUrl) {
                $('resultDiv').innerHTML="<a target='_blank' href='" + fileUrl + "'>Сохранить файл</a>";
                $('submitButton').disabled=false;
            }
        });
        //jQuery('#resultDiv').html("Подождите, идет формирование файла...");
        $('resultDiv').innerHTML="Подождите, идет формирование файла...";
    }
  </script>
  </tiles:put>
</tiles:insert>
