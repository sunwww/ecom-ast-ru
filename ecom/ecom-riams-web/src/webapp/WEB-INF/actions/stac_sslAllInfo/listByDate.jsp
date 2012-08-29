<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="style" type="string">
    <style type="text/css">tr.deniedHospitalizating td {
            color: red ;
        }</style>
  </tiles:put>
  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" guid="f6e72e89-0ba7-4f9e-97f6-0a1ecaf5b162">Список всех ССЛ</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="677746d8-d63e-44a3-8e8b-e227dea8decb">
      <msh:sideLink roles="/Policy/MedCase" key="ALT+N" action="/" name="По отделению" guid="b6f99225-3f13-4e39-91a4-3b371f8dce53" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="datelist" nativeSql="
    select m.id,m.dateStart,m.dateFinish,m.username,stat.code
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday,dep.name,m.emergency,m.noActuality  from MedCase m 
    left join MisLpu dep on m.department_id = dep.id
    left join Patient pat on m.patient_id = pat.id  
    left join StatisticStub stat on m.statisticstub_id=stat.id 
    left join MisLpu lpu on m.department_id = lpu.id 
    where m.DTYPE='HospitalMedCase' ${paramPeriod} ${addParam} ${emergency} ${department} ${pigeonHole}" guid="ac83420f-43a0-4ede-b576-394b4395a23a" />
    <msh:table viewUrl="entityShortView-stac_ssl.do" name="datelist" idField="1" action="entityView-stac_ssl.do" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="6" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Год рождения" property="7" guid="fc223a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Отделение" property="8" guid="e9g8f5-8b9e-4a3e-966f-4d435g76bbc96" />
      <msh:tableColumn columnName="Экстренность" property="9" guid="e9g8f5-8b9e-4a3e-966f-4d435g76bbc96" />
      <msh:tableColumn columnName="Дата открытия" property="2" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Кем открыт" property="4" guid="35347247-b552-4154-a82a-ee484a1714ad" />
      <msh:tableColumn columnName="Стат.карта" property="5" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Дата закрытия" property="3" guid="e98f5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Недействителен" property="10" guid="e98f5-8b9e-4a3e-966f-4d43576bbc96" />
    </msh:table>
    <msh:tableNotEmpty name="list" guid="189caa95-f200-4b88-ae0f-5669effa19ce">
      <div class="h3">
        <h3>Легенда</h3>
      </div>
      <table class="tabview">
        <tr class="deniedHospitalizating">
          <td>―</td>
          <td>Отказ в госпитализации</td>
        </tr>
        <tr class="current">
          <td />
          <td>Текущая госпитализация</td>
        </tr>
      </table>
    </msh:tableNotEmpty>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Перейти" guid="b43f7427-60be-4539-8b79-38a6882a8512">
      <msh:sideLink key="ALT+2" params="id" action="/entityView-mis_patient" name="⇧ Пациент" guid="f07e71b2-bfbe-4137-8bba-b347b8056561" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

