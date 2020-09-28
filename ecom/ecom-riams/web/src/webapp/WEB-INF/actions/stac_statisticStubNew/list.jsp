<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Config">Список следующих новых номеров стат.карты по годам и ЛПУ</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string" >
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="list" nativeSql="select ss.id as ssid,ss.year as ssyear,ss.code as sscode
  	,ml.name as mlname,vph.name as vphname, ss.isPlan,ss.isEmergency 
  	from StatisticStub ss 
  	left join MisLpu ml on ml.id=ss.lpu_id
  	left join VocPigeonHole vph on vph.id=ss.pigeonHole_id
  	where ss.dtype='StatisticStubNew' order by ss.year"/>
    <msh:table name="list" action="entityView-stac_statisticStubNew.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Год" property="2" />
      <msh:tableColumn columnName="Следующий номер" property="3" />
      <msh:tableColumn columnName="ЛПУ" property="4" />
      <msh:tableColumn columnName="Приемник" property="5" />
      <msh:tableColumn columnName="Плановые СЛС" property="6" />
      <msh:tableColumn columnName="Экстрен. СЛС" property="7" />
      
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="" confirm="Создать следующий номер стат. карты за новый год?"
      	 	action="/entityPrepareCreate-stac_statisticStubNew" name="Создать" 
      	 	title="Создать следующий номер стат. карты за новый год по ЛПУ"
      	 	roles="/Policy/Config/Hospital/StatisticStubNew/Create" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

