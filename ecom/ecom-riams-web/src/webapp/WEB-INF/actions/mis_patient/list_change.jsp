<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <ecom:webQuery name="changePatient" nativeSql="
    select 
    jcp.id
    ,
    case when jcp.lastname != pat.lastname then ' фамилия<br>' else '' end
    ||
    case when jcp.firstname != pat.firstname then ' имя<br>' else '' end
    ||
    case when jcp.middlename != pat.middlename then ' отчество<br>' else '' end
    ||
    case when jcp.birthday != pat.birthday then ' дата рождения<br>' else '' end
    ||
    case when jcp.address_addressid != pat.address_addressid then ' адрес регистрации<br>' else '' end
    ||
    case when jcp.realaddress_addressid != pat.realaddress_addressid then ' адрес проживания<br>' else '' end
    
    ,jcp.lastname,jcp.firstname,jcp.middlename,jcp.birthday
    ,a1.fullname as a1fullname,a2.fullname as a2fullname
    ,jcp.changedate,jcp.changetime, jcp.changeusername
    from JournalChangePatient jcp
    left join Patient pat on pat.id=jcp.patient_id
    left join Address2 a1 on a1.addressid = jcp.address_addressid
    left join Address2 a2 on a2.addressid = jcp.realaddress_addressid
    where jcp.patient_id = ${param.id}
    "/>
    <msh:table name="changePatient" action=" javascript:void(0)" idField="1">
    	<msh:tableColumn property="2" columnName="Изменения"/>
    	<msh:tableColumn property="3" columnName="Фамилия"/>
    	<msh:tableColumn property="4" columnName="Имя"/>
    	<msh:tableColumn property="5" columnName="Отчество"/>
    	<msh:tableColumn property="6" columnName="Дата рождения"/>
    	<msh:tableColumn property="7" columnName="Адрес регистрации"/>
    	<msh:tableColumn property="8" columnName="Адрес проживания"/>
    	<msh:tableColumn property="9" columnName="Дата изменения"/>
    	<msh:tableColumn property="10" columnName="время"/>
    	<msh:tableColumn property="11" columnName="пользователь"/>
    </msh:table>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_patientForm"  />
  </tiles:put>
  <tiles:put name="style" type="string">
    
  </tiles:put>
</tiles:insert>

