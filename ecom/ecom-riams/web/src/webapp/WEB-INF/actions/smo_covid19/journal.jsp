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

  <tiles:put name="body" type="string">
    <msh:form action="/covid19_journal.do" defaultField="department" disableFormDataConfirm="true" method="GET">
      <msh:panel>
        <msh:row>
          <msh:separator label="Параметры поиска" colSpan="7"/>
        </msh:row>

      </msh:panel>
    </msh:form>

    <msh:section>
      <msh:sectionTitle>Результаты поиска COVID 19</msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery name="list_covid" nativeSql="select  
        cv.id, pat.patientinfo,  cv.createdate||' '|| cast(cv.createtime as varchar(5))
        ,case when cv.exportDate is not null then 'color:green' when cv.noActual='1' then 'color:gray' else '' end as f9_styleRow
    from Covid19 cv
    left join Patient pat on pat.id=cv.patient_id
    order by cv.createdate, cv.createtime" />
        <msh:table name="list_covid" action="entityParentView-smo_covid19.do" idField="1" styleRow="4" noDataMessage="Не найдено">
          <msh:tableColumn columnName="#" property="sn"/>
          <msh:tableColumn columnName="Пациент" property="2"/>
          <msh:tableColumn columnName="Дата и время создания карты" property="3"/>
        </msh:table>
      </msh:sectionContent>
    </msh:section>

    <script type='text/javascript'>
     // checkFieldUpdate('typePatient','${typePatient}',4) ;
    //  checkFieldUpdate('typeDtype','${typeDtype}',3) ;
      function checkFieldUpdate(aField,aValue,aDefault) {
        eval('var chk =  document.forms[0].'+aField) ;
        eval('var aMax =  chk.length') ;
        if (aMax>aDefault) {aDefault=aMax}
        if ((+aValue)>aMax) {
          chk[+aDefault-1].checked='checked' ;
        } else {
          chk[+aValue-1].checked='checked' ;
        }
      }

    </script>
  </tiles:put>
</tiles:insert>

