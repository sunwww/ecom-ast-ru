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
<form action="print-mis_area_patient_address_primer.do" method="post" target="_blank">
 <input type="button" onclick="goPrintRegion('dao_mis_area_patient_address')" value="Печать список ДАО">
 <input type="button" onclick="goPrint('gd_mis_area_patient_address')" value="Печать список ГДРФ">
 <input type="button" onclick="goPrintNative('main_big_report')" value="Большая важная таблица">
<input type='hidden' name='m' id='m' value="printGroup3NativeQuery"/>
<input type='hidden' name='s' id='s' value="PrintService"/>
<%
request.setAttribute("fldDate", "18.09.2016"); //Дата выборов
%>
<input type='hidden' name="groupField" id="groupField" value="11,2,3">
<input type='hidden' name='sqlText' id='sqlText' value=''>
<input type='hidden' name='originText' id='originText' value="
select
  case when (ml.isnoomc is null or ml.isnoomc='0') then case when ml.id !=203 then ml.id else 182 end else case when ml.id !=203 then prevMl.id else 182 end end as mlid
 ,case when (ml.isnoomc is null or ml.isnoomc='0') then case when ml.id !=203 then ml.name else 'АКУШЕРСКОЕ ОТДЕЛЕНИЕ ПАТОЛОГИИ БЕРЕМЕННОСТИ' end else case when prevml.id !=203 then prevml.name else 'АКУШЕРСКОЕ ОТДЕЛЕНИЕ ПАТОЛОГИИ БЕРЕМЕННОСТИ' end end as mlname
 ,pat.id as patid

 ,coalesce(vr.name,vrr.name) as vrname
 ,lastname
 ,pat.firstname , pat.middlename as fio
 ,

 case when


 cast(to_char(to_date('${fldDate}','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(pat.birthday,'yyyy') as int)
 +(case when (cast(to_char(to_date('${fldDate}','dd.mm.yyyy'), 'mm') as int)
 -cast(to_char(pat.birthday, 'mm') as int)+(case when (cast(to_char(to_date('${fldDate}','dd.mm.yyyy'),'dd') as int)
 - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) = 18
 then to_char(pat.birthday,'dd.mm.yyyy')  else to_char(pat.birthday,'yyyy') end
as birthday

,case when vat.shortname='ул' then ''
when vat.shortname='пр-кт' then  vat.shortname
when vat.shortname='б-р' then  vat.shortname
 else vat.shortname||'. ' end||

adr.name ||' '||case when pat.houseNumber is not null and pat.houseNumber!='' then ' д. '||pat.houseNumber else '' end ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп. '|| pat.houseBuilding else '' end||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end
,pat.passportseries||' '||pat.passportnumber as fld10
,coalesce(case when (ml.isnoomc is null or ml.isnoomc='0') then ml.booknumer else prevMl.booknumer end ,''||ml.name) as fld_11_BookNumber  
,row_number() over (order by {orderNumber} ) as f12_num

,case when adrP3.domen=2 then case when atP3.id=3 then lower(atP3.name)||' '||adrP3.name else adrP3.name||' '||lower(atP3.name) end 
	when adrP2.domen=2 then case when atP2.id=3 then lower(atP2.name)||' '||adrP2.name else adrP2.name||' '||lower(atP2.name) end 
	when adrP1.domen=2 then case when atP1.id=3 then lower(atP1.name)||' '||adrP1.name else adrP1.name||' '||lower(atP1.name) end 
	when adr.domen=2 then case when at.id=3 then lower(at.name)||' '||adr.name else adr.name||' '||lower(at.name) end  
	else adr.domen||'_'||adr.fullname end as f13_adr_Region
	
,case when adrP1.addressid=14 and coalesce(vr.id, vrr.id) is not null and coalesce(vr.name,vrr.name) not like '%ИНОГОРОДНИЕ%' then 'г. Астрахань ('||initcap(coalesce(vr.name,vrr.name)) ||')' else 
case when adrP3.domen=3 then adrP3.name||' '||atP3.name  when adrP2.domen=3 then adrP2.name ||' '||atP2.name  when adrP1.domen=3 then adrP1.name||' '||atP1.name when adr.domen=3 then adr.name||' '||at.name when adrP1.domen=4 then atP1.shortname ||'. '|| adrP1.name end end as f14_adr_Rayon
,case when adrP3.domen in (4,5) then atP3.shortname||' '||adrP3.name when adrP2.domen in (4,5) then atP2.shortname||' '||adrP2.name when adrP1.domen in (4,5) then atP1.shortname||' '|| adrP1.name when adr.domen in (4,5) then at.shortname ||' '|| adr.name else '' end as f15_adr_CityVillage
,case when adr.domen=6 then adr.name else '' end as f16_adrStreet
,pat.housenumber as f17_adrHouse
,pat.housebuilding as f18_adrCorpus
,pat.flatnumber as f18_adrKvartira
 from medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.transferdepartment_id is null
left join mislpu ml on ml.id=slo.department_id
left join medcase prevSlo on prevSlo.id=slo.prevmedcase_id
left join mislpu prevMl on prevMl.id=prevSlo.department_id
left join patient pat on pat.id=sls.patient_id
left join address2 adr on adr.addressid=pat.address_addressid

left join addresstype at on at.id=adr.type_id
left join address2 adrP1 on adrP1.addressid=adr.parent_addressid
left join addresstype atP1 on atP1.id=adrP1.type_id
left join address2 adrP2 on adrP2.addressid=adrP1.parent_addressid
left join addresstype atP2 on atP2.id=adrP2.type_id
left join address2 adrP3 on adrP3.addressid=adrP2.parent_addressid
left join addresstype atP3 on atP3.id=adrP3.type_id

left join vocrayon vr on vr.id=pat.rayon_id
left join vocrayon vrr on vrr.id=pat.realrayon_id
left join addresstype vat on vat.id=adr.type_Id

where sls.datefinish is null and sls.dtype='HospitalMedCase'
and sls.deniedhospitalizating_id is null
and (pat.nationality_id is null or pat.nationality_id=171)
and ml.id!=382
{onlyAstrakhan}
and slo.dtype='DepartmentMedCase'
and cast(to_char(to_date('${fldDate}','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(pat.birthday,'yyyy') as int)
 +(case when (cast(to_char(to_date('${fldDate}','dd.mm.yyyy'), 'mm') as int)
 -cast(to_char(pat.birthday, 'mm') as int)+(case when (cast(to_char(to_date('${fldDate}','dd.mm.yyyy'),'dd') as int)
 - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) >= 18
and sls.datestart<to_date('15.09.2016','dd.MM.yyyy')
and (pat.notvote is null or pat.notvote='0')
 order by {orderNumber}
 "/>
 </form>
     <script type="text/javascript">
     var orderByDAO = "case when (ml.isnoomc is null or ml.isnoomc='0') then ml.booknumer else prevMl.booknumer end,case when (ml.isnoomc is null or ml.isnoomc='0') then case when ml.id !=203 then ml.name else 'АКУШЕРСКОЕ ОТДЕЛЕНИЕ ПАТОЛОГИИ БЕРЕМЕННОСТИ' end else case when prevml.id !=203 then prevml.name else 'АКУШЕРСКОЕ ОТДЕЛЕНИЕ ПАТОЛОГИИ БЕРЕМЕННОСТИ' end end ,pat.lastname,pat.firstname,pat.middlename,pat.birthday";
     var orderByExample = "pat.lastname,pat.firstname,pat.middlename,pat.birthday"
     function goPrint(name) {
    	 if ($('sqlText').value=='') {
    		 $('sqlText').value = $('originText').value;
    	 }
    	 	 $('sqlText').value = $('sqlText').value.replace("{orderNumber}",orderByDAO).replace("{orderNumber}",orderByDAO);
    	 	 $('sqlText').value=$('sqlText').value.replace("{onlyAstrakhan}",""); 
    	 
    	 var frm = document.forms[0] ;
    	 alert ("M= "+$('m').value);
    	 alert ("Group= "+$('groupField').value);
    	 alert ($('sqlText').value);
    	 frm.action="print-"+name+".do";
    	 frm.submit();
    	 $('sqlText').value = "";
    	 $('m').value="printGroup3NativeQuery";
    	 
    	 
     }
     function goPrintRegion(name) {
    	 $('sqlText').value = $('originText').value;
    	 $('sqlText').value=$('sqlText').value.replace("{onlyAstrakhan}"," and (adr.kladr is null or adr.kladr like '300%')");
    	 goPrint(name);
     }
     function goPrintNative(name) {
    	 $('sqlText').value = $('originText').value;
    	 $('m').value = "printNativeQuery";
    	 $('sqlText').value = $('sqlText').value.replace("{orderNumber}",orderByExample).replace("{orderNumber}",orderByExample);
    	 goPrint(name);
     }
  
    </script>
 
  </tiles:put>
</tiles:insert>