<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name='title' type='string'>
    <msh:title mainMenu="Medcard">Журнал covid по иногородним</msh:title>
  </tiles:put>

  <tiles:put name='side' type='string'>
  </tiles:put>

  <tiles:put name="body" type="string">
    <msh:form action="/covid19_reg_journal.do" defaultField="department" disableFormDataConfirm="true" method="GET">
      <msh:panel>
        <msh:row>
          <msh:separator label="Параметры поиска" colSpan="7"/>
        </msh:row>
        <msh:row>
          <td></td>
          <td onclick="this.childNodes[1].checked='checked';">
            <input type="radio" name="typeRegion" checked value="dag">  Дагестанцы
          </td>
          <td colspan="2" onclick="this.childNodes[1].checked='checked';">
            <input type="radio" name="typeRegion" value="foreign">  Иностранцы
          </td>

        </msh:row>
          <msh:row>
            <msh:textField property="beginDate" label="Начало СЛС с "/>
            <msh:textField property="finishDate" label="Начало СЛС по"/>
          </msh:row>
        <msh:row>
          <td>
            <input type="submit" value="Найти" />
          </td>
        </msh:row>

      </msh:panel>
    </msh:form>
<%
  String startDate = request.getParameter("beginDate");
  String finishDate = request.getParameter("finishDate");
  request.setAttribute("startDate", startDate);
  request.setAttribute("finishDate", finishDate!=null && !finishDate.equals("") ? finishDate : startDate);
  String typeRegion = ActionUtil.updateParameter("journal_cardiacScreening","typeRegion", "dag",request); // request.getParameter("typeRegion");
  if ("foreign".equals(typeRegion)) {
    request.setAttribute("typeRegionSql"," and pat.nationality_id is not null and nat.voc_code!='643' ");
    request.setAttribute("selectRegionSql"," ,nat.name as region");
  } else {
    request.setAttribute("typeRegionSql"," and adr.kladr like '05%'");
    request.setAttribute("selectRegionSql"," ,cast('' as varchar) as region");
  }


%>
    <msh:section>
      <msh:sectionTitle>Результаты поиска COVID 19 по иногородним</msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery name="list_covid" nativeSql="select sls.id,
          pat.lastname ||' '||pat.firstname ||' '||pat.middlename as f2_fio
          , adr.fullname ||case when coalesce(pat.housenumber,'')!='' then ' д.'||pat.housenumber else '' end ||
              case when coalesce(pat.housebuilding ,'')!='' then ' корп.'||pat.housebuilding else '' end ||
              case when coalesce(pat.flatnumber ,'')!='' then ' кв.'||pat.flatnumber else '' end as f3_regaddress
          , rea.fullname ||case when coalesce(pat.realhousenumber,'')!='' then ' д.'||pat.realhousenumber else '' end ||
              case when coalesce(pat.realhousebuilding ,'')!='' then ' корп.'||pat.realhousebuilding else '' end ||
              case when coalesce(pat.realflatnumber ,'')!='' then ' кв.'||pat.realflatnumber else '' end as f4_realaddress
          ,to_char(sls.datestart,'dd.MM.yyyy') ||' - '|| coalesce(to_char(sls.dateFinish,'dd.MM.yyyy'),' по текущий момент') as f5_datestart
          , case when sls.deniedhospitalizating_id is not null then '-'
            when sls.datefinish is null then '+' else '-' end as f6_provodista
            , case when sls.deniedhospitalizating_id is not null then '-'
            when sls.datefinish is null then '-' else '+' end as f7_zakoncheno
          ,case when sls.deniedhospitalizating_id is not null then (select list(d.record) from diary d where d.medcase_id =sls.id) end as f7_diaries
              ${selectRegionSql}
              from medcase sls
          left join patient pat on pat.id=sls.patient_id
          left join address2 adr on adr.addressid  = pat.address_addressid
          left join OMC_OKSM nat on nat.id=pat.nationality_id
          left join address2 rea on rea.addressid  = pat.realaddress_addressid
          where sls.datestart between to_date('${startDate}','dd.MM.yyyy') and  to_date('${finishDate}','dd.MM.yyyy')
          and sls.dtype ='HospitalMedCase' and sls.department_id in (499,500,501,502,503,504,505,506,507,508,509)
           ${typeRegionSql}" />
        <msh:table printToExcelButton="Сохранить в excel" name="list_covid" action="entityParentView-stac_ssl.do" idField="1" noDataMessage="Не найдено">
          <msh:tableColumn columnName="#" property="sn"/>
          <msh:tableColumn columnName="Пациент" property="2"/>
          <msh:tableColumn columnName="Регион" property="9"/>
          <msh:tableColumn columnName="Адрес регистрации" property="3"/>
          <msh:tableColumn columnName="Адрес проживания" property="4"/>
          <msh:tableColumn columnName="Начало случая" property="5"/>
          <msh:tableColumn columnName="Проводится лечение" property="6"/>
          <msh:tableColumn columnName="Закончено лечение" property="7"/>
          <msh:tableColumn columnName="Дневники у отказных" property="8" width="30"/>
        </msh:table>
      </msh:sectionContent>
    </msh:section>


    <script type='text/javascript'>
      $('startDate').value = getCurrentDate();
      checkFieldUpdate('typeRegion','${typeRegion}','dag') ;
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

