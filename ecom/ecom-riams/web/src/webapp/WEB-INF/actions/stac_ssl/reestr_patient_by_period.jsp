<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >
  <tiles:put name="body" type="string">
    <%
    String date = (String)request.getParameter("dateBegin") ;
    
    
    if (date!=null && !date.equals(""))  {
    	String date1 = (String)request.getParameter("dateEnd") ;
    	if (date1==null || date1.equals("")) {
    		request.setAttribute("dateEnd",date) ;
    	} else {
    		request.setAttribute("dateEnd",date1) ;
    	}
    	%>
    
    <ecom:webQuery name="journal_ticket" nativeSql="select 
ldep.name as NAM_OTD
,pat.lastname||' '||pat.firstname||' '||pat.middlename as FIO
,vr.name  as K_ADRESA
,adr.fullname as NAS_PUNKT
,vs.omcCode as POL
,to_char(pat.birthday,'dd.mm.yyyy') as D_ROGD
,to_char(hosp.dateStart,'dd.mm.yyyy') as D_POST
,to_char(hosp.dateFinish,'dd.mm.yyyy') as D_VIP
,case when (hosp.dateFinish-hosp.dateStart)=0 then 1
when (vht.code='DAYTIMEHOSP') then hosp.dateFinish-hosp.dateStart+1
else hosp.dateFinish-hosp.dateStart
end as KK_DEN
,to_char(so.operationdate,'dd.mm.yyyy') as D_OPER
,vo.code as K_OPER
,count(*) as KOL_OPER
,case when hosp.emergency='1' then 1 else 0 end as EKSTR
,vho.code as ISHOD
,vho.name as ISHOD1
,ss.code as N_KARTA
,ldep.id as OTDEL
,sowf.code as VRACH
,sowfpat.lastname||' '||sowfpat.firstname||' '||sowfpat.middlename as FIO_WR
,lwf.code as VRACH1
,lwfpat.lastname||' '||lwfpat.firstname||' '||lwfpat.middlename as FIO_WR1
,vof.voc_code as DOST
,vof.name as DOSTAV
,olpu.name as NAPRAVLEN
,olpu.omcCode as NAPR
,list(mkb.code) as DIAG
from medcase hosp
left join diagnosis diag on diag.medcase_id=hosp.id and diag.priority_id=1 and diag.registrationtype_id=4
left join vocidc10 mkb on diag.idc10_id=mkb.id
left join VocHospType vht on vht.id=hosp.hospType_id
left join statisticstub ss on ss.id=hosp.statisticstub_id
left join patient pat on pat.id=hosp.patient_id
left join vocrayon vr on vr.id=pat.rayon_id
left join medcase md on md.parent_id=hosp.id
left join surgicaloperation so on (so.medcase_id=hosp.id or so.medcase_id=md.id)
left join workfunction sowf on sowf.id=so.surgeon_id
left join worker sow on sow.id=sowf.worker_id
left join patient sowfpat on sowfpat.id=sow.person_id
left join medService vo on vo.id=so.medService_id
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
where hosp.dtype='HospitalMedCase'
and hosp.dateFinish between to_date('${param.dateBegin}','dd.mm.yyyy') 
    	and to_date('${dateEnd}','dd.mm.yyyy')
and hosp.dischargeTime is not null
and hosp.deniedHospitalizating_id is null
group by 
hosp.id
,ldep.name 
,pat.lastname,pat.firstname,pat.middlename 
,vr.name  
,adr.fullname 
,vs.omcCode
,pat.birthday
,hosp.dateStart 
,hosp.dateFinish 
,so.operationdate 
,vo.code
,ss.code
,hosp.emergency
,vho.code,vho.name,ldep.id
,sowf.code
,sowfpat.lastname,sowfpat.firstname,sowfpat.middlename 
,lwf.code 
,lwfpat.lastname,lwfpat.firstname,lwfpat.middlename 
,vof.voc_code
,vof.name
,olpu.name,olpu.omcCode ,vht.code
 " />
        <msh:table name="journal_ticket" action="stac_groupByBedFundData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="nam_otd" property="1"/>
            <msh:tableColumn columnName="fio" property="2"/>
            <msh:tableColumn columnName="k_adresa" property="3"/>
            <msh:tableColumn columnName="nas_punkt" property="4"/>
            <msh:tableColumn columnName="pol" property="5"/>
            <msh:tableColumn columnName="d_rogd" property="6"/>
            <msh:tableColumn columnName="d_post" property="7"/>
            <msh:tableColumn columnName="d_vip" property="8"/>
            <msh:tableColumn columnName="kk_den" property="9"/>
            <msh:tableColumn columnName="d_oper" property="10"/>
            <msh:tableColumn columnName="k_oper" property="11"/>
            <msh:tableColumn columnName="kol_oper" property="12"/>
            <msh:tableColumn columnName="ekstr" property="13"/>
            <msh:tableColumn columnName="ishod" property="14"/>
            <msh:tableColumn columnName="ishod1" property="15"/>
            <msh:tableColumn columnName="n_karta" property="16"/>
            <msh:tableColumn columnName="otdel" property="17"/>
            <msh:tableColumn columnName="vrach" property="18"/>
            <msh:tableColumn columnName="fio_wr" property="19"/>
            <msh:tableColumn columnName="vrach1" property="20"/>
            <msh:tableColumn columnName="fio_wr1" property="21"/>
            <msh:tableColumn columnName="dost" property="22"/>
            <msh:tableColumn columnName="dostav" property="23"/>
            <msh:tableColumn columnName="napravlen" property="24"/>
            <msh:tableColumn columnName="napr" property="25"/>
            <msh:tableColumn columnName="diag" property="26"/>
        </msh:table>
    <% } else {%>

    	<% }   %>
  </tiles:put>
</tiles:insert>

