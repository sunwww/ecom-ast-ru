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
    <msh:form action="/dis_swod.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row >
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
           <td>
            <input type="submit" onclick="print()" value="Печать" />
          </td>
      </msh:row>
      <input type="hidden" value="DisabilityService" name="s"/>
      <input type="hidden" value="printJournal" name="m"/>
    </msh:panel>
    </msh:form>
    
    <%
    DisabilitySearchForm form 
    	= (DisabilitySearchForm)request.getAttribute("dis_searchForm");
    String date = form.getBeginDate() ;
    String date1 = form.getEndDate() ;
    
    if (date!=null && !date.equals("")) {
    	if (date1==null || date1.equals("")) {
    		date1=date ;
    	}
    	request.setAttribute("beginDate", date) ;
    	request.setAttribute("endDate", date1) ;
    	%>
    
    
    <msh:section>
    <ecom:webQuery  name="journal_swod" nativeSql="select dd.issuedate,count(*) 
    ,count(case when dup.id is  null then 1 else null end) as aANew 
, count(case when vddcr.code='2' then 1 else null end) cntcr2
, count(case when vddcr.code='2' and dup.id is  null then 1 else null end) cntcr2new
, count(case when vddp.code='1' then 1 else null end) as cntpr
, count(case when vddp.code='1' and dup.id is  null then 1 else null end) as cntprnew
, count(case when vdr.code='11' then 1 else null end) cntr11
, count(case when vdr.code='11' and dup.id is  null then 1 else null end) as cntr11new
, count(case when vdr.code='4' then 1 else null end) cntr4
, count(case when vdr.code='4' and dup.id is  null then 1 else null end) as cntr4new
, count(case when vdr.code='2' then 1 else null end) cntr2
, count(case when vdr.code='2' and dup.id is  null then 1 else null end) as cntr2new

from DisabilityDocument dd 
left join DisabilityDocument dup on dup.duplicate_id=dd.id 
left join VocDisabilityStatus vds on vds.id=dup.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id

where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null
group by dd.issueDate order by dd.issueDate "/>
    <msh:sectionTitle>Сводная таблица по документам нетрудоспособности (общая)</msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_swod" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th />
                <th colspan=2>Всего</th>
                <th colspan=2>Кол-во амб.</th>
                <th colspan=2>Кол-во первичных</th>
                <th colspan=2>Кол-во травм</th>
                <th colspan=2>Кол-во произ.травм</th>
                <th colspan=2>Кол-во проф. заб.</th>
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="Дата выдачи" property="1"/>
      <msh:tableColumn columnName="общ. кол-во" property="2"/>
      <msh:tableColumn columnName="из них новых" property="3"/>
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
    </msh:table>
    </msh:sectionContent>

    <ecom:webQuery  name="journal_itog" nativeSql="select 
    count(*) as aA
    ,count(case when dup.id is  null then 1 else null end) as aANew 
, count(case when vddcr.code='2' then 1 else null end) cntcr2
, count(case when vddcr.code='2' and dup.id is  null then 1 else null end) cntcr2new
, count(case when vddp.code='1' then 1 else null end) as cntpr
, count(case when vddp.code='1' and dup.id is  null then 1 else null end) as cntprnew
, count(case when vdr.code='11' then 1 else null end) cntr11
, count(case when vdr.code='11' and dup.id is  null then 1 else null end) as cntr11new
, count(case when vdr.code='4' then 1 else null end) cntr4
, count(case when vdr.code='4' and dup.id is  null then 1 else null end) as cntr4new
, count(case when vdr.code='2' then 1 else null end) cntr2
, count(case when vdr.code='2' and dup.id is  null then 1 else null end) as cntr2new

from DisabilityDocument dd 
left join DisabilityDocument dup on dup.duplicate_id=dd.id 
left join VocDisabilityStatus vds on vds.id=dup.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
left join VocDisabilityReason vdr on vdr.id=dd.disabilityReason_id
    
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null
"/>
    <msh:sectionTitle>ИТОГ по документам нетрудоспособности (общая)</msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_itog" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
              <tr>
                <th colspan=2>Всего</th>
                <th colspan=2>Кол-во амб.</th>
                <th colspan=2>Кол-во первичных</th>
                <th colspan=2>Кол-во травм</th>
                <th colspan=2>Кол-во произ.травм</th>
                <th colspan=2>Кол-во проф. заб.</th>
              </tr>
            </msh:tableNotEmpty>
      <msh:tableColumn columnName="общ. кол-во" property="1"/>
      <msh:tableColumn columnName="из них новых" property="2"/>
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
left join DisabilityDocument dup on dup.duplicate_id=dd.id 
left join VocDisabilityStatus vds on vds.id=dup.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null
group by dd.issueDate order by dd.issueDate"
    />
    <msh:table name="journal_issueDuplicate" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
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
left join DisabilityDocument dup on dup.duplicate_id=dd.id 
left join VocDisabilityStatus vds on vds.id=dup.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null
"
    />
    <msh:table name="journal_issueDuplicate_itog" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
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
left join DisabilityDocument dup on dup.id=dd.duplicate_id 
left join VocDisabilityStatus vds on vds.id=dd.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null
group by dd.issueDate order by dd.issueDate"
    />
    <msh:table name="journal_duplicate" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
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
left join DisabilityDocument dup on dup.id=dd.duplicate_id 
left join VocDisabilityStatus vds on vds.id=dd.status_id
left join VocDisabilityDocumentPrimarity vddp on vddp.id=dd.primarity_id
left join VocDisabilityDocumentCloseReason vddcr on vddcr.id=dd.closeReason_id
where dd.issueDate between to_date('${beginDate}','DD.MM.YYYY') and to_date('${endDate}','DD.MM.YYYY')
and dd.anotherlpu_id is null
"
    />
    <msh:table name="journal_duplicate_itog" action="dis_documentClose.do" idField="1">
            <msh:tableNotEmpty guid="a6284e48-9209-412d-8436-c1e8e37eb8aa">
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
    <% } else {%>
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