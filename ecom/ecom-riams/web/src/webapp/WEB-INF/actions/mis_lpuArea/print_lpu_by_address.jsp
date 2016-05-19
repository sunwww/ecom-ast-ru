<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuAreaForm" title="Печать пациентов по участку по адресам"/>
  </tiles:put>
  <tiles:put name="side" type="string">
   
  </tiles:put>
  <tiles:put name="body" type="string">
<form action="print-mis_area_patient_address.do" method="post" target="_blank">
Если не началось автоматическое скачаивание документа нажмите кнопку <input type="submit" name="btnPrint" id="btnPrint" value="Печать">
<input type='hidden' name='m' value="printGroup3NativeQuery"/>
<input type='hidden' name='s' value="PrintService"/>
<input type='hidden' name="groupField" id="groupField" value="2,3">
<ecom:setAttribute name="sqlText" value="select
ml.id as mlid
,ml.name as mlname
,pat.id as patid

,coalesce(vr.name,vrr.name) as vrname
,lastname
,pat.firstname , pat.middlename as fio
,

case when


cast(to_char(to_date('22.05.2016','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(pat.birthday,'yyyy') as int)
 +(case when (cast(to_char(to_date('22.05.2016','dd.mm.yyyy'), 'mm') as int)
 -cast(to_char(pat.birthday, 'mm') as int)+(case when (cast(to_char(to_date('22.05.2016','dd.mm.yyyy'),'dd') as int)
 - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) = 18
 then to_char(pat.birthday,'dd.mm.yyyy')  else to_char(pat.birthday,'yyyy') end
as birthday

,case when vat.shortname='ул' then ''
when vat.shortname='пр-кт' then  vat.shortname
when vat.shortname='б-р' then  vat.shortname
 else vat.shortname||'. ' end||

adr.name ||' '||case when pat.houseNumber is not null and pat.houseNumber!='' then ' д. '||pat.houseNumber else '' end ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп. '|| pat.houseBuilding else '' end||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end
,pat.passportseries||' '||pat.passportnumber
,(
select list(la.number) from lpuareaaddresspoint p
left join lpuareaaddresstext t on t.id=p.lpuareaaddresstext_id
left join lpuarea la on la.id=t.area_id
where pat.address_addressid=p.address_addressid
and la.lpu_id = ${param.id}
and (p.id is null or (p.houseNumber='' and p.HouseBuilding='') or p.houseNumber is null or (p.houseNumber=pat.houseNumber and (p.houseBuilding is null or p.HouseBuilding='' or p.houseBuilding=pat.houseBuilding) ) )
)
 from medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.transferdepartment_id is null
left join mislpu ml on ml.id=slo.department_id
left join patient pat on pat.id=sls.patient_id
left join address2 adr on adr.addressid=pat.address_addressid
left join vocrayon vr on vr.id=pat.rayon_id
left join vocrayon vrr on vrr.id=pat.realrayon_id
left join addresstype vat on vat.id=adr.type_Id

where sls.datefinish is null and sls.dtype='HospitalMedCase'
and sls.deniedhospitalizating_id is null
and slo.dtype='DepartmentMedCase'
and (adr.kladr is null or adr.kladr like '300000%')
and cast(to_char(to_date('22.05.2016','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(pat.birthday,'yyyy') as int)
 +(case when (cast(to_char(to_date('22.05.2016','dd.mm.yyyy'), 'mm') as int)
 -cast(to_char(pat.birthday, 'mm') as int)+(case when (cast(to_char(to_date('22.05.2016','dd.mm.yyyy'),'dd') as int)
 - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) >= 18


and (select count(*) from lpuareaaddresspoint p
left join lpuareaaddresstext t on t.id=p.lpuareaaddresstext_id
left join lpuarea la on la.id=t.area_id
where 
pat.address_addressid=p.address_addressid
and la.lpu_id = ${param.id}
and (p.id is null or p.houseNumber='' or p.houseNumber is null or (p.houseNumber=pat.houseNumber and (p.houseBuilding is null or p.HouseBuilding='' or p.houseBuilding=pat.houseBuilding) ) )
)>0


 order by ml.name,pat.lastname,pat.firstname,pat.middlename,pat.birthday
"/>
<input type='hidden' name='sqlText' value="${sqlText}"/>
 </form>
     <script type="text/javascript">
    function print() {
    	//alert("Печать") ;
    	var frm = document.forms[0] ;
    	frm.submit() ;
    }
    print() ;
    
    </script>
 
  </tiles:put>
</tiles:insert>