<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="style" type="string">
    <style type="text/css">tr.deniedHospitalizating td {
            color: red ;}
        tr.otherLpu td {
            color: blue ;
        }</style>
  </tiles:put>
  <tiles:put name="title" type="string">
    <h1>Список всех случаев лечения в стационаре. <msh:sideLink params="id" action="/entityView-mis_patient" name="⇧ Пациент" guid="f07e71b2-bfbe-4137-8bba-b347b8056561" /></h1>
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entitySubclassView-mis_medCase.do" guid="d579127c-69a0-4eca-b3e3-950381d1585c" decorator="decorator">
      <msh:tableColumn columnName="Номер" property="id" guid="ce16c32c-9459-4673-9ce8-d6e646f969ff" />
      <msh:tableColumn columnName="Дата открытия" property="dateStart" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Экстренность" property="emergency" guid="e98f6bbc96" />
      <msh:tableColumn columnName="Кем открыт" property="username" guid="35347247-b552-4154-a82a-ee484a1714ad" />
      <msh:tableColumn columnName="Дата закрытия" property="dateFinish" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
      <msh:tableColumn columnName="Кем закрыт" property="finishWorkerText" guid="6b562107-5017-4559-9b94-ab525b579202" />
      <msh:tableColumn columnName="Стат.карта" property="statCardNumber" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Количество дней" property="daysCount" guid="8b496fc7-80e9-4beb-878b-5bfb20e98f31" />
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
        <tr class="otherLpu">
          <td />
          <td>Госпитализация в другом ЛПУ</td>
        </tr>
      </table>
    </msh:tableNotEmpty>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="6372e109-9e1b-49dc-840b-9b38f524efeb">
      <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Create" params="id" action="/entityParentPrepareCreate-stac_sslAdmission" name="Госпитализацию" title="Добавить случай лечения в стационаре" key="ALT+2" guid="436bbb7b-497c-4b10-ba0e-c5601675a713" />
      <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Ext/Create" params="id" action="/entityParentPrepareCreate-stac_extssl" name="Госпитализацию в другом стационаре" title="Зарегистрировать госпитализацию в другом стационаре" key="ALT+3"/>
    </msh:sideMenu>
    <msh:sideMenu title="Перейти" guid="b43f7427-60be-4539-8b79-38a6882a8512">
      <msh:sideLink key="ALT+2" params="id" action="/entityView-mis_patient" name="⇧ Пациент" guid="f07e71b2-bfbe-4137-8bba-b347b8056561" />
      <msh:sideLink roles="/Policy/XZ" params="id" action="/entityParentListRedirect-pres_prescriptList" name="⇧К списку назначений" guid="b1195713-54a1-49f3-9dbf-31751203b6b0" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

