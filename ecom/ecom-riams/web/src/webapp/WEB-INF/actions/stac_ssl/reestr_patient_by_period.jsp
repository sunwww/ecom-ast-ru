<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true">
    <tiles:put name="body" type="string">
        <%
            String date = request.getParameter("dateBegin");
            if (date != null && !date.equals("")) {
                String date1 = request.getParameter("dateEnd");
                if (date1 == null || date1.equals("")) {
                    request.setAttribute("dateEnd", date);
                } else {
                    request.setAttribute("dateEnd", date1);
                }
        %>

        <ecom:webQuery name="journal_ticket" nativeSql="select
ldep.name as NAM_OTD
,pat.lastname||' '||pat.firstname||' '||pat.middlename as FIO
,vr.name  as K_ADRESA
,list(adr.fullname
               ||case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end
               ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end
	       ||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end) as f4_NAS_PUNKT
,vs.name as f5_POL
,to_char(pat.birthday,'dd.mm.yyyy') as D_ROGD
,to_char(hosp.dateStart,'dd.mm.yyyy') as D_POST
,to_char(hosp.dateFinish,'dd.mm.yyyy') as D_VIP
,case when (hosp.dateFinish-hosp.dateStart)=0 then 1
when (vht.code='DAYTIMEHOSP') then hosp.dateFinish-hosp.dateStart+1
else hosp.dateFinish-hosp.dateStart
end as f9_KK_DEN
,list(pat.phone) as f10_PHONE
,case when hosp.emergency='1' then 1 else 0 end as f11_EKSTR
,vho.code as ISHOD
,vho.name as ISHOD1
,ss.code as N_KARTA
,ldep.id as f15_OTDEL
,lwfpat.lastname||' '||lwfpat.firstname||' '||lwfpat.middlename as f16_FIO_WR1
,list(vss.name) as f18_SERVICE_STREAM
,list(vbt.name) as f19_BED_FUND_OLD_DOST
,vof.name as DOSTAV
,olpu.name as NAPRAVLEN
,pat.snils as f22_SNILS
,list(mkb.code) as DIAG
,pat.commonNumber||'_' as f24_ENP
from medcase hosp
left join diagnosis diag on diag.medcase_id=hosp.id and diag.priority_id=1 and diag.registrationtype_id=4
left join vocidc10 mkb on diag.idc10_id=mkb.id
left join VocHospType vht on vht.id=hosp.hospType_id
left join statisticstub ss on ss.id=hosp.statisticstub_id
left join patient pat on pat.id=hosp.patient_id
left join vocrayon vr on vr.id=pat.rayon_id
left join medcase md on md.parent_id=hosp.id
left join medcase lmd on lmd.parent_id=hosp.id and lmd.dateFinish is not null 
left join workfunction lwf on lwf.id=lmd.ownerfunction_id
left join worker lw on lw.id=lwf.worker_id
left join patient lwfpat on lwfpat.id=lw.person_id
left join mislpu ldep on ldep.id=lmd.department_id
left join address2 adr on adr.addressid=pat.address_addressid
left join vocsex vs on vs.id=pat.sex_id
left join vochospitalizationoutcome vho on vho.id = hosp.outcome_id
left join omc_frm vof on vof.id=hosp.ordertype_id
left join mislpu olpu on olpu.id=hosp.orderlpu_id
left join bedfund bf on bf.id=md.bedfund_id
left join vocbedtype vbt on vbt.id=bf.bedtype_id
left join vocservicestream vss on vss.id= hosp.servicestream_id
where hosp.dtype='HospitalMedCase'
and hosp.dateFinish between to_date('${param.dateBegin}','dd.mm.yyyy') 
    	and to_date('${dateEnd}','dd.mm.yyyy')
and hosp.dischargeTime is not null
and hosp.deniedHospitalizating_id is null
and hosp.result_id !=6
group by
hosp.id
,ldep.name 
,pat.lastname,pat.firstname,pat.middlename,pat.commonNumber,pat.snils
,vr.name
,adr.fullname
,vs.name
,pat.birthday
,hosp.dateStart
,hosp.dateFinish
,ss.code
,hosp.emergency
,vho.code,vho.name,ldep.id
,lwf.code
,lwfpat.lastname,lwfpat.firstname,lwfpat.middlename
,vof.voc_code
,vof.name
,olpu.name,olpu.omcCode ,vht.code
 "/>
        <msh:table name="journal_ticket" action="stac_groupByBedFundData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="nam_otd" property="1"/>
            <msh:tableColumn columnName="fio" property="2"/>
            <msh:tableColumn columnName="Район" property="3"/>
            <msh:tableColumn columnName="Адрес" property="4"/>
            <msh:tableColumn columnName="Пол" property="5"/>
            <msh:tableColumn columnName="d_rogd" property="6"/>
            <msh:tableColumn columnName="d_post" property="7"/>
            <msh:tableColumn columnName="d_vip" property="8"/>
            <msh:tableColumn columnName="kk_den" property="9"/>
            <msh:tableColumn columnName="Телефон" property="10"/>
            <msh:tableColumn columnName="ekstr" property="11"/>
            <msh:tableColumn columnName="ishod" property="12"/>
            <msh:tableColumn columnName="ishod1" property="13"/>
            <msh:tableColumn columnName="n_karta" property="14"/>
            <msh:tableColumn columnName="otdel" property="15"/>
            <msh:tableColumn columnName="vrach1" property="16"/>
            <msh:tableColumn columnName="Поток обслуживания" property="17"/>
            <msh:tableColumn columnName="bedfund" property="18"/>
            <msh:tableColumn columnName="dostav" property="19"/>
            <msh:tableColumn columnName="napravlen" property="20"/>
            <msh:tableColumn columnName="diag" property="22"/>
            <msh:tableColumn columnName="ЕНП" property="23"/>
            <msh:tableColumn columnName="СНИЛС" property="21"/>
        </msh:table>
        <% } else {%>

        <% } %>
    </tiles:put>
</tiles:insert>

