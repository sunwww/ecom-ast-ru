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
Если не началось автоматическое скачаивание документа нажмите кнопку <input type="submit" name="btnPrint" id="btnPrint" value="Печать">
<input type='hidden' name='m' value="printNativeQuery"/>
<input type='hidden' name='s' value="PrintService"/>
<input type='hidden' name="groupField" id="groupField" value="2,3">
<ecom:setAttribute name="sqlText" value="
select cams.mainparent,
cams.lastname,cams.firstname,cams.middlename
,to_char(cams.birthday,'dd.mm.yyyy') as birthday
,cams.polnumber
,to_char(cams.datefrom,'dd.mm.yyyy') as datefrom, to_char(cams.dateto,'dd.mm.yyyy') as dateto
,pp.code as ppcode
, pp.name as ppinfo
,mkb.code as mkbcode
,replace(''||cams.cost,'.',',') as cost
,cams.countMedService 
	, replace(''||cams.countMedService*cams.cost,'.',',') as sumNoAccraulMedService 
	, case when cams.isDeath='1' then 'Да' else '' end isdeath
,cams.countMedService*cams.cost as sumNoAccraulMedService1f
			from ContractAccountMedService cams
			left join medservice ms on ms.id=cams.serviceIn
			left join contractaccount ca on ca.id=cams.account_id
			left join WorkFunction wf on wf.id=cams.doctor
			left join worker w on w.id=wf.worker_id
			left join patient wp on wp.id=w.person_id
			left join PriceMedService pms on pms.id=cams.medService_id
			left join PricePosition pp on pp.id=pms.pricePosition_id
			left join MedContract mc on mc.id=ca.contract_id
			left join ContractPerson cp on cp.id=mc.customer_id 
			left join VocJuridicalPerson vjp on vjp.id=cp.juridicalPersonType_id
			left join patient p on p.id=cp.patient_id
    		left join MisLpu lpu on lpu.id=cp.lpu_id
			left join REG_IC reg on reg.id=cp.regCompany_id
			left join VocIdc10 mkb on mkb.id=cams.diagnosis
			where cams.account_id='${param.id}'
			order by cams.datefrom,wp.lastname,pp.code
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