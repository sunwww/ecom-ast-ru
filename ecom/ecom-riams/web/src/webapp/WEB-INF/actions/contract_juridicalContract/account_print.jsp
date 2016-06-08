<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="contract_accountForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
   
  </tiles:put>
  <tiles:put name="body" type="string">
<form action="print-contract_account_print.do" method="post">
<input type="submit" name="btnPrint" id="btnPrint" value="Печать реестра">
<input type='hidden' name='m' value="printNativeQuery"/>
<input type='hidden' name='s' value="PrintService"/>
<input type='hidden' name='clazz' value="Packages.ru.ecom.mis.ejb.domain.contract.ContractAccount:${param.id}"/>
<input type='hidden' name="groupField" id="groupField" value="2,3">
<ecom:setAttribute name="sqlText" value="
select cams.mainparent,
substring(cams.lastname,1,1)||lower(substring(cams.lastname,2)) as lastname
,substring(cams.firstname,1,1)||lower(substring(cams.firstname,2)) as firstname
,substring(cams.middlename,1,1)||lower(substring(cams.middlename,2)) as middlename

,to_char(cams.birthday,'dd.mm.yyyy') as birthday
,cams.polnumber
,to_char(cams.datefrom,'dd.mm.yyyy') as datefrom, to_char(cams.dateto,'dd.mm.yyyy') as dateto
,pp.code as ppcode
, pp.name as ppinfo
,mkb.code as mkbcode
,replace(''||round(round((cams.cost*(100-coalesce(mc.discountDefault,'0'))/100),0),2),'.',',') as cost
,cams.countMedService 
	, replace(''||cams.countMedService*round((cams.cost*(100-coalesce(mc.discountDefault,'0'))/100),0),'.',',') as sumNoAccraulMedService 
	, case when cams.isDeath='1' then 'Да' else '' end isdeath
,round(cams.countMedService*round((cams.cost*(100-coalesce(mc.discountDefault,'0'))/100),0),2) as sumNoAccraulMedService1f
,substring(cams.lastname,1,1)||lower(substring(cams.lastname,2)) 
||substring(cams.firstname,1,1)||lower(substring(cams.firstname,2)) 
||substring(cams.middlename,1,1)||lower(substring(cams.middlename,2)) 
||to_char(cams.birthday,'dd.mm.yyyy') as fiodrs

			from ContractAccountMedService cams
			left join medservice ms on ms.id=cams.serviceIn
			left join contractaccount ca on ca.id=cams.account_id
			left join medcontract mc on mc.id=ca.contract_id
			left join WorkFunction wf on wf.id=cams.doctor
			left join worker w on w.id=wf.worker_id
			left join patient wp on wp.id=w.person_id
			left join PriceMedService pms on pms.id=cams.medService_id
			left join PricePosition pp on pp.id=pms.pricePosition_id
			left join ContractPerson cp on cp.id=mc.customer_id 
			left join VocJuridicalPerson vjp on vjp.id=cp.juridicalPersonType_id
			left join patient p on p.id=cp.patient_id
    		left join MisLpu lpu on lpu.id=cp.lpu_id
			left join REG_IC reg on reg.id=cp.regCompany_id
			left join VocIdc10 mkb on mkb.id=cams.diagnosis
			where cams.account_id='${param.id}' and cams.medservice_id is not null
			and (cams.isDelete='0' or cams.isDelete is null) 
			order by cams.lastname,cams.firstname,cams.middlename,cams.birthday,pp.code,cams.datefrom
"/>
<input type='hidden' name='sqlText' value="${sqlText}"/>
<input type='hidden' name='fieldIdUniq' value="17" />
 </form>
<form action="print-contract_account_print_1.do" method="post">
<input type="submit" name="btnPrint" id="btnPrint" value="Печать акта и счета">
<input type='hidden' name='m' value="printNativeQuery"/>
<input type='hidden' name='s' value="PrintService"/>
<input type='hidden' name='clazz' value="Packages.ru.ecom.mis.ejb.domain.contract.ContractAccount:${param.id}"/>
<input type='hidden' name="groupField" id="groupField" value="2,3">
<ecom:setAttribute name="sqlText" value="
select pp.id as ppid
,pp.code as ppcode
, pp.name as ppinfo
,vpt.name
,round(round((cams.cost*(100-coalesce(mc.discountDefault,'0'))/100),0),2) as cost
,sum(cams.countMedService) 
,sum(round(cams.countMedService*round((cams.cost*(100-coalesce(mc.discountDefault,'0'))/100),0),2)) as sumNoAccraulMedService1f

			from ContractAccountMedService cams
			left join medservice ms on ms.id=cams.serviceIn
			left join contractaccount ca on ca.id=cams.account_id
			left join medcontract mc on mc.id=ca.contract_id
			left join WorkFunction wf on wf.id=cams.doctor
			left join worker w on w.id=wf.worker_id
			left join patient wp on wp.id=w.person_id
			left join PriceMedService pms on pms.id=cams.medService_id
			left join PricePosition pp on pp.id=pms.pricePosition_id
			left join VocPositionType vpt on vpt.id=pp.positionType_id
			left join ContractPerson cp on cp.id=mc.customer_id 
			left join VocJuridicalPerson vjp on vjp.id=cp.juridicalPersonType_id
			left join patient p on p.id=cp.patient_id
    		left join MisLpu lpu on lpu.id=cp.lpu_id
			left join REG_IC reg on reg.id=cp.regCompany_id
			left join VocIdc10 mkb on mkb.id=cams.diagnosis
			where cams.account_id='${param.id}' and cams.medservice_id is not null
			group by pp.id,pp.code , pp.name ,vpt.name,mc.discountDefault,cams.cost
"/>
<input type='hidden' name='sqlText' value="${sqlText}"/>
<input type='hidden' name='fieldIdUniq' value="17" />
 </form>
     <script type="text/javascript">
   
    </script>
 
  </tiles:put>
</tiles:insert>