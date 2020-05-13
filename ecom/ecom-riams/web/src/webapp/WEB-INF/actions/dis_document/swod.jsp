<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.mis.web.action.disability.DisabilitySearchForm"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
        <msh:title mainMenu="Disability">Свод по документам нетрудоспособности за период</msh:title>
  </tiles:put>
    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="swodNT"/>
    </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	
  	String typeReport =ActionUtil.updateParameter("DIS_DOCUMENT","typeReport","1", request) ;
  	%>
    <msh:form action="/dis_swod.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      
      	<msh:row>
        <td class="label" title="Поиск по дате  (typeReport)" colspan="1"><label for="typeReportName" id="typeReportLabel">Поиск по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeReport" value="1">  сводная информация
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeReport" value="2">  информация о случаях ВН (кроме 31, 37 кода)
        </td>
      </msh:row>
      <msh:row>
          <msh:autoComplete vocName="vocDisabilityDocumentType" property="documentType" label="Документ" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
      <msh:row>
      	<msh:autoComplete property="sex" fieldColSpan="3" size="6" horizontalFill="true"
      		label="Пол" vocName="vocSex"
      	/>
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="disabilityReason" fieldColSpan="3" size="6" horizontalFill="true"
      		label="Причина нетруд." vocName="vocDisabilityReason"
      	/>
      </msh:row>
      <msh:row>
        <msh:autoComplete property="disabilityReason2" fieldColSpan="3" size="6" horizontalFill="true"
                          label="Доп. причина нетруд." vocName="vocDisabilityReason2"
        />
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="closeReason" fieldColSpan="3" horizontalFill="true"
      		label="Причина закрытия" vocName="vocDisabilityDocumentCloseReason"
      	/>
      </msh:row>
      <msh:row >
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
           <td>
            <input type="submit" onclick="print()" value="Печать свода" />
          </td>
      </msh:row>
      <input type="hidden" value="DisabilityService" name="s"/>
      <input type="hidden" value="printJournal" name="m"/>
    </msh:panel>
    </msh:form>
    <script type="text/javascript">

    checkFieldUpdate('typeReport','${typeReport}',1) ;
    
    
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    
  	
  	function getCheckedValue(radioGrp) {
  		var radioValue ;
  		for(i=0; i < radioGrp.length; i++) {
  		  if (radioGrp[i].checked == true){
  		    radioValue = radioGrp[i].value;
  		    break ;
  		  }
  		}
  		return radioValue ;
  	}
  	function openPatientNewTab(aPat) {
  		window.open("entitySubclassView-mis_medCase.do?id="+aPat) ;
  	}
  		
  	</script>
    <%
    DisabilitySearchForm form 
    	= (DisabilitySearchForm)request.getAttribute("dis_searchForm");
    String date = form.getBeginDate() ;
    String date1 = form.getEndDate() ;


    StringBuilder addSql = new StringBuilder() ;
    addSql.append(ActionUtil.setParameterFilterSql("disabilityReason","vdr.id", request)) ;
      addSql.append(ActionUtil.setParameterFilterSql("disabilityReason2","vdr2.id", request)) ;
    addSql.append(ActionUtil.setParameterFilterSql("closeReason","vddcr.id", request)) ;
    addSql.append(ActionUtil.setParameterFilterSql("sex","pat.sex_id", request)) ;
    addSql.append(ActionUtil.setParameterFilterSql("documentType","vddt.id", request)) ;
    request.setAttribute("addSql", addSql.toString()) ;
    if (date!=null && !date.equals("")) {
    	if (date1==null || date1.equals("")) {
    		date1=date ;
    	}
    	request.setAttribute("beginDate", date) ;
    	request.setAttribute("endDate", date1) ;
    	
    	if (typeReport!=null && typeReport.equals("2")) {
    		%>
    		<msh:section title="Список закрытых СНТ">
    		<msh:sectionContent>
    	<ecom:webQuery name="inf_svn"
    	nameFldSql="inf_svn_sql"
    	nativeSql="
    	select dc.id as dcid,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio,pat.birthday as birthday
    	
    	,max(
    	cast(to_char(dr.dateto,'yyyy') as int)-cast(to_char(pat.birthday,'yyyy') as int)
+(case when (cast(to_char(dr.dateto, 'mm') as int)-cast(to_char(pat.birthday, 'mm') as int)
+(case when (cast(to_char(dr.dateto,'dd') as int) - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)
    	
    	
    	
    	
    	) as age
,vs.name,adr.fullname,list(distinct dd.hospitalizednumber) as statcard
,
(select list(distinct mkb.code)
from medcase mc
left join statisticstub ss on ss.id=mc.statisticstub_id
left join diagnosis d on d.medcase_id=mc.id
left join vocdiagnosisregistrationtype vdrt on vdrt.id=d.registrationtype_id
left join vocprioritydiagnosis vpd on vpd.id=d.priority_id
left join vocidc10 mkb on mkb.id=d.idc10_id
where mc.patient_id=dc.patient_id and ss.code=trim(dd.hospitalizednumber)
and vpd.code='1' and vdrt.code='3'
)
, min(dr.datefrom) as mind,max(dr.dateto) as maxd
,max(dr.dateto)-min(dr.datefrom)+1 as cntdays
,vddcr.name as reason

 from disabilitydocument ddM
left join vocdisabilitydocumentclosereason vddcr on vddcr.id=ddM.closereason_id
 left join VocDisabilityReason vdr on vdr.id=ddM.disabilityReason_id
 left join VocDisabilityReason vdr2 on vdr2.id=ddM.disabilityReason2_id
left join disabilitycase dc on ddM.disabilitycase_id=dc.id
left join disabilitydocument dd on dd.disabilitycase_id=dc.id
left join disabilityrecord dr on dr.disabilitydocument_id=dd.id
left join patient pat on pat.id=dc.patient_id
left join vocsex vs on vs.id=pat.sex_id
left join address2 adr on adr.addressid=pat.address_addressid
left join vocdisabilitydocumenttype vddt on vddt.id=ddM.documentType_id
where (select max(drM.dateTo) from disabilityrecord drM where drM.disabilitydocument_id=ddM.id) 
between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and case when vddcr.codeF='31' then '1' when vddcr.codeF='37' then '1' else '2' end='2'
and dd.isclose='1' ${addSql}
and ddM.workComboType_id is null
and ddm.isclose='1' and dd.isclose='1' 
and ddM.workComboType_id is null and dd.workComboType_id is null
and (dd.noactuality='0' or dd.noactuality is null) and (ddm.noactuality='0' or ddm.noactuality is null) 

group by dc.id ,pat.lastname,pat.firstname,pat.middlename ,pat.birthday 
,vs.name,adr.fullname,vddcr.name,dd.hospitalizednumber
order by pat.lastname,pat.firstname,pat.middlename
    	
    	"
    	/>	
    		<msh:table printToExcelButton="Сохранить в excel" viewUrl="entityView-dis_case.do?short=Short" name="inf_svn" action="entityParentView-dis_case.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="ФИО" property="2"/>
      <msh:tableColumn columnName="Дата рождения" property="3"/>
      <msh:tableColumn columnName="Возраст (на дату закрытия БЛ)" property="4"/>
      <msh:tableColumn columnName="Пол" property="5"/>
      <msh:tableColumn columnName="Место жительства (по регистрации)" property="6"/>
      <msh:tableColumn columnName="№ИБ" property="7"/>
      <msh:tableColumn columnName="Шифр по МКБ выписной" property="8"/>
      <msh:tableColumn columnName="Дата открытия случая" property="9"/>
      <msh:tableColumn columnName="Дата закрытия случая" property="10"/>
      <msh:tableColumn columnName="Длительность" property="11"/>
      <msh:tableColumn columnName="Причина закрытия" property="12"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    		<%
    	} else {
    	%>
    
    
    <msh:section>
    <ecom:webQuery  name="journal_swod" nativeSql="select dd.issuedate,count(*) 
    ,count(distinct case when dup.id is  null then dd.id else null end) as aANew 
, count(distinct case when vddcr.code='2' then dd.id else null end) cntcr2
, count(distinct case when vddcr.code='2' and dup.id is  null then dd.id else null end) cntcr2new
, count(distinct case when vddp.code='1' then dd.id else null end) as cntpr
, count(distinct case when vddp.code='1' and dup.id is  null then dd.id else null end) as cntprnew
, count(distinct case when vdr.code='11' then dd.id else null end) cntr11
, count(distinct case when vdr.code='11' and dup.id is  null then dd.id else null end) as cntr11new
, count(distinct case when vdr.code='4' then dd.id else null end) cntr4
, count(distinct case when vdr.code='4' and dup.id is  null then dd.id else null end) as cntr4new
, count(distinct case when vdr.code='2' then dd.id else null end) cntr2
, count(distinct case when vdr.code='2' and dup.id is  null then dd.id else null end) as cntr2new
, count(distinct case when vdr.code='1' then dc.id else null end) cntr1
, count(distinct case when vdr.code='1' and dup.id is  null then dc.id else null end) as cntr1new
, count(distinct case when dd.isClose='1' and vdr.code='1' and dup.id is  null then dc.id else null end) as cntr1newc
, sum(case when dd.isClose='1' and vdr.code='1' and dup.id is  null then (select max(dr.dateTo)-min(dr.dateFrom) from disabilityrecord dr where dr.disabilityDocument_id=dd.id) else 0 end) as cntr1newcdays
, count(distinct case when dd.isClose='1' and dup.id is  null then dc.id else null end) as aANewClose
, sum(case when dd.isClose='1' and dup.id is  null then (select max(dr.dateTo)-min(dr.dateFrom) from disabilityrecord dr where dr.disabilityDocument_id=dd.id) else 0 end) as aANewCloseDays
from DisabilityDocument dd 
left join DisabilityCase dc on dc.id=dd.disabilityCase_id
left join patient pat on pat.id=dc.patient_id
left join DisabilityDocument dup on dup.duplicate_id=dd.id 
left join VocDisabilityStatus vds on vds.id=dup.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
 left join VocDisabilityReason vdr2 on vdr2.id=dd.disabilityReason2_id
left join vocdisabilitydocumenttype vddt on vddt.id=dd.documentType_id
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null ${addSql}
group by dd.issueDate order by dd.issueDate "/>
    <msh:sectionTitle>Сводная таблица по документам нетрудоспособности (общая)</msh:sectionTitle>
    <msh:sectionContent>
    <msh:table printToExcelButton="Сохранить в excel" name="journal_swod" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th />
                <th colspan=4>Всего</th>
                <th colspan=2>Кол-во амб.</th>
                <th colspan=2>Кол-во первичных</th>
                <th colspan=2>Кол-во травм</th>
                <th colspan=2>Кол-во произ.травм</th>
                <th colspan=2>Кол-во проф. заб.</th>
                <th colspan=4>Кол-во заб.</th>
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="Дата выдачи" property="1"/>
      <msh:tableColumn columnName="общ. кол-во" property="2"/>
      <msh:tableColumn columnName="из них новых" property="3"/>
       <msh:tableColumn columnName="из них новых закрытых" property="18"/>
      <msh:tableColumn columnName="кол-во дней по новым закрытым ДНТ" property="19"/>
      <msh:tableColumn columnName="общ. кол-во" property="4"/>
      <msh:tableColumn columnName="из них новых" property="5"/>
      <msh:tableColumn columnName="общ. кол-во" property="6"/>
      <msh:tableColumn columnName="из них новых" property="7"/>
      <msh:tableColumn columnName="общ. кол-во" property="8"/>
      <msh:tableColumn columnName="из них новых" property="9"/>
      <msh:tableColumn columnName="общ. кол-во" property="10"/>
      <msh:tableColumn columnName="из них новых" property="11"/>
      <msh:tableColumn columnName="общ. кол-во" property="12"/>
      <msh:tableColumn columnName="из них новых" property="13"/>
      <msh:tableColumn columnName="общ. кол-во" property="14"/>
      <msh:tableColumn columnName="из них новых" property="15"/>
      <msh:tableColumn columnName="из них новых закрытых" property="16"/>
      <msh:tableColumn columnName="кол-во дней по новым закрытым ДНТ" property="17"/>
    </msh:table>
    </msh:sectionContent>

    <ecom:webQuery  name="journal_itog" nativeSql="select 
    count(*) as aA
   ,count(distinct case when dup.id is  null then dd.id else null end) as aANew 
, count(distinct case when vddcr.code='2' then dd.id else null end) cntcr2
, count(distinct case when vddcr.code='2' and dup.id is  null then dd.id else null end) cntcr2new
, count(distinct case when vddp.code='1' then dd.id else null end) as cntpr
, count(distinct case when vddp.code='1' and dup.id is  null then dd.id else null end) as cntprnew
, count(distinct case when vdr.code='11' then dd.id else null end) cntr11
, count(distinct case when vdr.code='11' and dup.id is  null then dd.id else null end) as cntr11new
, count(distinct case when vdr.code='4' then dd.id else null end) cntr4
, count(distinct case when vdr.code='4' and dup.id is  null then dd.id else null end) as cntr4new
, count(distinct case when vdr.code='2' then dd.id else null end) cntr2
, count(distinct case when vdr.code='2' and dup.id is  null then dd.id else null end) as cntr2new
, count(distinct case when vdr.code='1' then dc.id else null end) cntr1
, count(distinct case when vdr.code='1' and dup.id is  null then dd.id else null end) as cntr1new
, count(distinct case when dd.isClose='1' and vdr.code='1' and dup.id is  null then dc.id else null end) as cntr1newc
, sum(case when dd.isClose='1' and vdr.code='1' and dup.id is  null then (select max(dr.dateTo)-min(dr.dateFrom) from disabilityrecord dr where dr.disabilityDocument_id=dd.id) else 0 end) as cntr1newcdays
, count(distinct case when dd.isClose='1' and dup.id is  null then dc.id else null end) as aANewClose
, sum(case when dd.isClose='1' and dup.id is  null then (select max(dr.dateTo)-min(dr.dateFrom) from disabilityrecord dr where dr.disabilityDocument_id=dd.id) else 0 end) as aANewCloseDays
from DisabilityDocument dd 
left join DisabilityCase dc on dc.id=dd.disabilityCase_id
left join patient pat on pat.id=dc.patient_id
left join DisabilityDocument dup on dup.duplicate_id=dd.id 
left join VocDisabilityStatus vds on vds.id=dup.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
 left join VocDisabilityReason vdr2 on vdr2.id=dd.disabilityReason2_id
left join vocdisabilitydocumenttype vddt on vddt.id=dd.documentType_id
    
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY') 
and dd.anotherlpu_id is null ${addSql}
"/>
    <msh:sectionTitle>ИТОГ по документам нетрудоспособности (общая)</msh:sectionTitle>
    <msh:sectionContent>
    <msh:table printToExcelButton="Сохранить в excel" name="journal_itog" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th colspan=4>Всего</th>
                <th colspan=2>Кол-во амб.</th>
                <th colspan=2>Кол-во первичных</th>
                <th colspan=2>Кол-во травм</th>
                <th colspan=2>Кол-во произ.травм</th>
                <th colspan=2>Кол-во проф. заб.</th>
                <th colspan=4>Кол-во заб.</th>
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="общ. кол-во" property="1"/>
      <msh:tableColumn columnName="из них новых" property="2"/>
       <msh:tableColumn columnName="из них новых закрытых СНТ" property="17"/>
      <msh:tableColumn columnName="кол-во дней по новым закрытым СНТ" property="18"/>
      <msh:tableColumn columnName="общ. кол-во" property="3"/>
      <msh:tableColumn columnName="из них новых" property="4"/>
      <msh:tableColumn columnName="общ. кол-во" property="5"/>
      <msh:tableColumn columnName="из них новых" property="6"/>
      <msh:tableColumn columnName="общ. кол-во" property="7"/>
      <msh:tableColumn columnName="из них новых" property="8"/>
      <msh:tableColumn columnName="общ. кол-во" property="9"/>
      <msh:tableColumn columnName="из них новых" property="10"/>
      <msh:tableColumn columnName="общ. кол-во" property="11"/>
      <msh:tableColumn columnName="из них новых" property="12"/>
      <msh:tableColumn columnName="общ. кол-во" property="13"/>
      <msh:tableColumn columnName="из них новых" property="14"/>
      <msh:tableColumn columnName="из них новых закрытых СНТ" property="15"/>
      <msh:tableColumn columnName="кол-во дней по новым закрытым СНТ" property="16"/>
    </msh:table>
    </msh:sectionContent>


    <msh:sectionTitle>Сводная таблица по документам нетрудоспособности выданных взамен испорченных документов и дубликатам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_issueDuplicate"
    nativeSql="select dd.issueDate, count(*) as cnt
, count(case when dup.id is not null then 1 else null end) as cntA
, count(case when dup.id is not null and vds.code='1' then 1 else null end) as cntB
, count(case when dup.id is not null and vds.code='2' then 1 else null end) as cntD
, count(case when dup.id is not null and vddp.code='1' 
	then 1 else null end) pr1a
, count(case when dup.id is not null and vddp.code='1'  and vds.code='1'
	then 1 else null end) pr1B
, count(case when dup.id is not null and vddp.code='1'  and vds.code='2'
	then 1 else null end) pr1D
, count(case when dup.id is not null and vddp.code='2' 
	then 1 else null end) pr2a
, count(case when dup.id is not null and vddp.code='2'  and vds.code='1'
	then 1 else null end) pr2B
, count(case when dup.id is not null and vddp.code='2'  and vds.code='2'
	then 1 else null end) pr2D

from DisabilityDocument dd 
left join disabilitycase dc on dd.disabilitycase_id=dc.id
left join patient pat on pat.id=dc.patient_id
left join DisabilityDocument dup on dup.duplicate_id=dd.id 
left join VocDisabilityStatus vds on vds.id=dup.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
 left join VocDisabilityReason vdr2 on vdr2.id=dd.disabilityReason2_id
left join vocdisabilitydocumenttype vddt on vddt.id=dd.documentType_id
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null ${addSql}
group by dd.issueDate order by dd.issueDate"
    />
    <msh:table printToExcelButton="Сохранить в excel" name="journal_issueDuplicate" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th />
                <th />
                <th colspan=3>Всего выданных взамен</th>
                <th colspan=3>из них кол-во первичных</th>
                <th colspan=3>из них кол-во повторных</th>
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="Дата выдачи" property="1"/>
      <msh:tableColumn columnName="все" property="2"/>
      <msh:tableColumn columnName="общ. кол-во" property="3"/>
      <msh:tableColumn columnName="испор." property="4"/>
      <msh:tableColumn columnName="дублик." property="5"/>
      <msh:tableColumn columnName="общ. кол-во" property="6"/>
      <msh:tableColumn columnName="испор." property="7"/>
      <msh:tableColumn columnName="дублик." property="8"/>
      <msh:tableColumn columnName="общ. кол-во" property="9"/>
      <msh:tableColumn columnName="испор." property="10"/>
      <msh:tableColumn columnName="дублик." property="11"/>
    </msh:table>
    </msh:sectionContent>


    <msh:sectionTitle>ИТОГ по документам нетрудоспособности выданных взамен испорченных документов и дубликатам</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_issueDuplicate_itog"
    nativeSql="select count(*) as cnt
, count(case when dup.id is not null then 1 else null end) as cntA
, count(case when vds.code='1' then 1 else null end) as cntB
, count(case when vds.code='2' then 1 else null end) as cntD
, count(case when dup.id is not null and vddp.code='1' 
	then 1 else null end) pr1a
, count(case when dup.id is not null and vddp.code='1'  and vds.code='1'
	then 1 else null end) pr1B
, count(case when dup.id is not null and vddp.code='1'  and vds.code='2'
	then 1 else null end) pr1D
, count(case when dup.id is not null and vddp.code='2' 
	then 1 else null end) pr2a
, count(case when dup.id is not null and vddp.code='2'  and vds.code='1'
	then 1 else null end) pr2B
, count(case when dup.id is not null and vddp.code='2'  and vds.code='2'
	then 1 else null end) pr2D

from DisabilityDocument dd 
left join disabilitycase dc on dd.disabilitycase_id=dc.id
left join patient pat on pat.id=dc.patient_id
left join DisabilityDocument dup on dup.duplicate_id=dd.id 
left join VocDisabilityStatus vds on vds.id=dup.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
 left join VocDisabilityReason vdr2 on vdr2.id=dd.disabilityReason2_id
left join vocdisabilitydocumenttype vddt on vddt.id=dd.documentType_id
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY') 
and dd.anotherlpu_id is null ${addSql}
"
    />
    <msh:table printToExcelButton="Сохранить в excel" name="journal_issueDuplicate_itog" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th />
                <th colspan=3>Всего выданных взамен</th>
                <th colspan=3>из них кол-во первичных</th>
                <th colspan=3>из них кол-во повторных</th>
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="все" property="1"/>
      <msh:tableColumn columnName="общ. кол-во" property="2"/>
      <msh:tableColumn columnName="испор." property="3"/>
      <msh:tableColumn columnName="дублик." property="4"/>
      <msh:tableColumn columnName="общ. кол-во" property="5"/>
      <msh:tableColumn columnName="испор." property="6"/>
      <msh:tableColumn columnName="дублик." property="7"/>
      <msh:tableColumn columnName="общ. кол-во" property="8"/>
      <msh:tableColumn columnName="испор." property="9"/>
      <msh:tableColumn columnName="дублик." property="10"/>
    </msh:table>
    </msh:sectionContent>

    <msh:sectionTitle>Сводная таблица по документам нетрудоспособности испорченным документам и дубликатам за период</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_duplicate"
    nativeSql="select dd.issueDate, count(*) 
, count(case when dup.id is not null then 1 else null end) as aA
, count(case when dup.id is not null and vds.code='1' then 1 else null end) as aB
, count(case when dup.id is not null and vds.code='2' then 1 else null end) as aD
, count(case when dup.id is not null and vddp.code='1' 
	then 1 else null end) pr1a
, count(case when dup.id is not null and vddp.code='1'  and vds.code='1'
	then 1 else null end) pr1B
, count(case when dup.id is not null and vddp.code='1'  and vds.code='2'
	then 1 else null end) pr1D
, count(case when dup.id is not null and vddp.code='2' 
	then 1 else null end) pr2a
, count(case when dup.id is not null and vddp.code='2'  and vds.code='1'
	then 1 else null end) pr2B
, count(case when dup.id is not null and vddp.code='2'  and vds.code='2'
	then 1 else null end) pr2D

from DisabilityDocument dd 
left join disabilitycase dc on dd.disabilitycase_id=dc.id
left join patient pat on pat.id=dc.patient_id
left join DisabilityDocument dup on dup.id=dd.duplicate_id 
left join VocDisabilityStatus vds on vds.id=dd.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
 left join VocDisabilityReason vdr2 on vdr2.id=dd.disabilityReason2_id
left join vocdisabilitydocumenttype vddt on vddt.id=dd.documentType_id
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null ${addSql}
group by dd.issueDate order by dd.issueDate"
    />
    <msh:table printToExcelButton="Сохранить в excel" name="journal_duplicate" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th />
                <th />
                <th colspan=3>Всего испор(дубл)</th>
                <th colspan=3>из них кол-во первичных</th>
                <th colspan=3>из них кол-во повторных</th>
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="Дата выдачи" property="1"/>
      <msh:tableColumn columnName="все" property="2"/>
      <msh:tableColumn columnName="общ. кол-во" property="3"/>
      <msh:tableColumn columnName="испор." property="4"/>
      <msh:tableColumn columnName="дублик." property="5"/>
      <msh:tableColumn columnName="общ. кол-во" property="6"/>
      <msh:tableColumn columnName="испор." property="7"/>
      <msh:tableColumn columnName="дублик." property="8"/>
      <msh:tableColumn columnName="общ. кол-во" property="9"/>
      <msh:tableColumn columnName="испор." property="10"/>
      <msh:tableColumn columnName="дублик." property="11"/>
    </msh:table>
    </msh:sectionContent>

    <msh:sectionTitle>ИТОГ по документам нетрудоспособности испорченным документам и дубликатам за период</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_duplicate_itog"
    nativeSql="select count(*) as cnt
, count(case when dup.id is not null then 1 else null end) as cntA
, count(case when dup.id is not null and vds.code='1' then 1 else null end) as cntB
, count(case when dup.id is not null and vds.code='2' then 1 else null end) as cntD
, count(case when dup.id is not null and vddp.code='1' 
	then 1 else null end) pr1a
, count(case when dup.id is not null and vddp.code='1'  and vds.code='1'
	then 1 else null end) pr1B
, count(case when dup.id is not null and vddp.code='1'  and vds.code='2'
	then 1 else null end) pr1D
, count(case when dup.id is not null and vddp.code='2' 
	then 1 else null end) pr2a
, count(case when dup.id is not null and vddp.code='2'  and vds.code='1'
	then 1 else null end) pr2B
, count(case when dup.id is not null and vddp.code='2'  and vds.code='2'
	then 1 else null end) pr2D

from DisabilityDocument dd 
left join disabilitycase dc on dd.disabilitycase_id=dc.id
left join patient pat on pat.id=dc.patient_id
left join DisabilityDocument dup on dup.id=dd.duplicate_id 
left join VocDisabilityStatus vds on vds.id=dd.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
 left join VocDisabilityReason vdr2 on vdr2.id=dd.disabilityReason2_id
left join vocdisabilitydocumenttype vddt on vddt.id=dd.documentType_id
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null ${addSql}
" 
    />
    <msh:table printToExcelButton="Сохранить в excel" name="journal_duplicate_itog" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty>
              <tr>
                <th />
                <th colspan=3>Всего испор(дубл)</th>
                <th colspan=3>из них кол-во первичных</th>
                <th colspan=3>из них кол-во повторных</th>
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="все" property="1"/>
      <msh:tableColumn columnName="общ. кол-во" property="2"/>
      <msh:tableColumn columnName="испор." property="3"/>
      <msh:tableColumn columnName="дублик." property="4"/>
      <msh:tableColumn columnName="общ. кол-во" property="5"/>
      <msh:tableColumn columnName="испор." property="6"/>
      <msh:tableColumn columnName="дублик." property="7"/>
      <msh:tableColumn columnName="общ. кол-во" property="8"/>
      <msh:tableColumn columnName="испор." property="9"/>
      <msh:tableColumn columnName="дублик." property="10"/>
    </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% }} else {%>
    	<i>Введите заполните период</i>
    	<% }   %>
  </tiles:put>
  <tiles:put type="string" name="javascript">
    <script type='text/javascript'>
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='dis_swod.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.m.value='printJournal' ;
    	frm.action='print-disabilityJournal.do' ;
    }
    function printNoActuality() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.m.value='printNoActuality' ;
    	frm.action='print-disabilityJournalByNo.do' ;
    }
    </script>
  </tiles:put>
  
</tiles:insert>