<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения
    	  -->
    	  
    	  		<ecom:webQuery name="naprfond" nativeSql="select 
'entityView-stac_dataFond.do?short=Short&id='||hdf.id as id,hdf.numberfond,hdf.lastname,hdf.firstname,hdf.middlename,to_char(hdf.birthday,'dd.mm.yyyy') as birthday
,case when hdf.bedSubType='2' then  'DS' 
 when hdf.bedSubType='1' then 'KS' 
else null end as vbst
,hdf.OrderLpuCode
,hdf.DirectDate
,case when hdf.FormHelp='1' then  'Плановая' 
 when hdf.FormHelp='2' then'Экстренная'
 when hdf.FormHelp='3' then'Неотложная'
else null end as vbst
,hdf.Diagnosis
 from hospitaldatafond hdf
where hdf.hospitalmedcase_id =${param.id}
    	"/>
    	
    	<msh:table  action="javascript:void(0)" name="naprfond" idField="1">
    	<msh:tableButton property="1" buttonFunction="getDefinition" buttonName="Информация" buttonShortName="Информация"/>
    		<msh:tableColumn property="2" columnName="№напр. фонда" />
    		<msh:tableColumn property="3" columnName="Фамилия" />
    		<msh:tableColumn property="4" columnName="Имя" />
    		<msh:tableColumn property="5" columnName="Отчество" />
    		<msh:tableColumn property="6" columnName="Дата рождения" />
    		<msh:tableColumn property="7" columnName="Вид" />
    		<msh:tableColumn property="8" columnName="Напр. ЛПУ" />
    		<msh:tableColumn property="9" columnName="Дата напр." />
    		<msh:tableColumn property="10" columnName="Форма помощи" />
    		<msh:tableColumn property="11" columnName="Диаг." />
    	</msh:table>

    	  
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sslForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    	  	<tags:stac_hospitalMenu currentAction="stac_sslAllInfo"/>  
  </tiles:put>
</tiles:insert>

