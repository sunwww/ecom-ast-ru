<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Journals" >Диспансеризация</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
  	String typeGroup =ActionUtil.updateParameter("ExtDispAction","typeGroup","1", request) ;
%>
		<msh:form action="extDisp_journal_card.do" defaultField="beginDate">
			<msh:panel>
			<msh:row>
				<msh:textField property="beginDate" label="c"/>
				<msh:textField property="finishDate" label="по"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="dispType" label="Тип доп. диспансеризации" vocName="vocExtDisp" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="ageGroup" label="Возрастная группа" vocName="vocExtDispAgeGroupByDispType" parentAutocomplete="dispType" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="healthGroup" label="Группа здоровья" parentAutocomplete="dispType" vocName="vocExtDispHealthGroupByDispType" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="risk" label="Риск" vocName="vocExtDispRisk" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="workFunction" label="Рабочая функция" vocName="workFunction" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
        <msh:row>
	        <td class="label" title="Группировка (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировки общие:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1"> реестр по типу доп.дисп.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> общий свод
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> свод по возрастной категории
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4"> свод по факторам риска
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="5"> свод по группам здоровья
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="6"> свод по услугам
	        </td>

        </msh:row>			
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="7"> свод по заболеваниям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="8"> свод по раб.функции
	        </td>        
        </msh:row>	
        <msh:row>
        	<td>Группировки по отчету</td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="9"> свод по возрастным категориям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="10"> свод по факторам риска
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="11"> свод по группам здоровья
	        </td>
        </msh:row>			
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="12"> свод по заболеваниям
	        </td>     
        </msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>
			</msh:panel>
		</msh:form>
<%
		String beginDate = request.getParameter("beginDate") ;
		if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("finishDate") ;
		String dispType = request.getParameter("dispType") ;
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		request.setAttribute("beginDate", beginDate) ;
		request.setAttribute("finishDate", finishDate) ;
		if (!typeGroup.equals("2") && (dispType==null || dispType.equals("")||dispType.equals("0"))) {
			typeGroup = "2" ;
		}
		/*
		if (typeGroup.equals("1")) {
			// Группировка по дате
			request.setAttribute("groupSql", "to_char(CAo.operationdate,'dd.mm.yyyy')") ;
       		request.setAttribute("groupSqlId", "'&beginDate='||to_char(CAo.operationdate,'dd.mm.yyyy')||'&finishDate='||to_char(CAo.operationdate,'dd.mm.yyyy')") ;
       		request.setAttribute("groupName", "Дате операции") ;
       		request.setAttribute("groupGroup", "CAo.operationdate") ;
       		request.setAttribute("groupGroupNext", "2") ;
       		request.setAttribute("groupOrder", "CAo.operationdate") ;
		} else if (typeGroup.equals("2")) {
			// Группировка по операторам
   			request.setAttribute("groupSql", "wp.lastname||' '||wp.firstname||' '||wp.middlename") ;
   			request.setAttribute("groupSqlId", "'&operator='||wf.id") ;
   			request.setAttribute("groupName", "Оператор") ;
   			request.setAttribute("groupGroup", "wf.id,vwf.name,wp.lastname,wp.firstname,wp.middlename") ;
       		request.setAttribute("groupGroupNext", "4") ;
   			request.setAttribute("groupOrder", "wp.lastname") ;
		} else if (typeGroup.equals("3")) {
			// Группировка по услугам 
   			request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
   			request.setAttribute("groupSqlId", "'&medService='||pms.id") ;
   			request.setAttribute("groupName", "Услуга") ;
       		request.setAttribute("groupGroupNext", "4") ;
   			request.setAttribute("groupGroup", "pms.id,pp.code,pp.name") ;
   			request.setAttribute("groupOrder", "pp.code") ;
		} else {
			//Реестр
   			request.setAttribute("groupSql", "pms.name") ;
   			request.setAttribute("groupSqlId", "'&medService='||pms.id") ;
   			request.setAttribute("groupName", "Сотрудник") ;
   			request.setAttribute("groupGroup", "pms.id,pms.code,pms.name") ;
   			request.setAttribute("groupOrder", "pms.code") ;
		}
		ActionUtil.setParameterFilterSql("operator","cao.workFunction_id", request) ;
		ActionUtil.setParameterFilterSql("medService","pms.id", request) ;
		*/
		StringBuilder sqlAdd = new StringBuilder() ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("dispType","edc.dispType_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("workFunction","edc.workFunction_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("ageGroup","edc.ageGroup_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("healthGroup","edc.healthGroup_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("socialGroup","edc.socialGroup_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("risk","edr.dispRisk_id", request)) ;
		request.setAttribute("sqlAppend", sqlAdd.toString()) ;
		%>
		<% if (typeGroup!=null && typeGroup.equals("1") ) {%>
			<msh:section title="Реестр карт по доп.диспансеризации за период ${param.beginDate}-${param.finishDate} ">
			<ecom:webQuery name="reestrExtDispCard" nativeSql="
select edc.id,p.lastname||' '||p.firstname||' '||
p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy') as birthday
,edc.startDate as edcBeginDate,edc.finishDate as edcFinishDate
,vedag.name as vedagname
,vedsg.name as vedsgname
,vedhg.name as vedhgname
,list(distinct vedr.name) as vrisks
, edc.isObservation as cntDispM
,edc.isTreatment as cntLechM
,edc.isDiagnostics as cntDiagM
,edc.isSpecializedCare as cntSpecCareM
,edc.isSanatorium as cntSanatM

from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Patient p on p.id=edc.patient_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by edc.id,p.lastname,p.firstname,
p.middlename,p.birthday,edc.startDate ,edc.finishDate 
,vedag.name,vedhg.name,vedsg.name
, edc.isObservation ,edc.isTreatment ,edc.isDiagnostics ,edc.isSpecializedCare,edc.isSanatorium 

			"/>

				<msh:table name="reestrExtDispCard" 
				action="entityView-extDisp_card.do" viewUrl="entityView-extDisp_card.do?short=Short"
				idField="1">
					<msh:tableColumn columnName="ФИО пациента" property="2" />
					<msh:tableColumn columnName="Дата начала" property="3" />
					<msh:tableColumn columnName="Дата окончания" property="4" />
					<msh:tableColumn columnName="Возрасная категория" property="5" />
					<msh:tableColumn columnName="Социальная группа" property="6" />
					<msh:tableColumn columnName="Группа здоровья" property="7" />
					<msh:tableColumn columnName="Факторы риска" property="8" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" property="9" />
					<msh:tableColumn columnName="Назначено лечение"  property="10" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование"  property="11" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ"  property="12" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" property="13" />
				</msh:table>
			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("2")) {%>
			<msh:section title="Свод за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id,ved.name,ved.code,count(distinct edc.id) from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code
order by ved.code
			"/>

				<msh:table name="extDispSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Тип доп.диспансеризации" property="2" />
					<msh:tableColumn columnName="Код" property="3" />
					<msh:tableColumn columnName="Кол-во оформленных карт" property="4" />
				</msh:table>

			</msh:section>


	<%} else if (typeGroup!=null&& typeGroup.equals("3")) {%>
			<msh:section title="Свод по возрастным категориям за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispAgeSwod" nativeSql="
select '&dispType='||ved.id||'&ageGroup='||vedag.id as id
,ved.name as vedname
,ved.code as vedcode,vedag.name as vedagname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedag.id,vedag.name
order by vedag.name
			"/>

				<msh:table name="extDispAgeSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Возрасная группа" property="4" />
					<msh:tableColumn columnName="Прошли диспансеризацию мужчин" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Прошли диспансеризацию женщин" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="7" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("4")) {%>
			<msh:section title="Свод по факторам риска за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageGroup='||vedag.id||'&dispRisk='||vedr.id as id,ved.name as vedname
,ved.code as vedcode,vedag.name as vedagname
,vedr.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispRisk edr
left join ExtDispCard edc on edr.card_id=edc.id
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} and vedr.id is not null
group by ved.id,ved.name,ved.code,vedag.id,vedag.name,vedr.id,vedr.name
order by vedr.id,vedag.name
			"/>

				<msh:table name="extDispSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Фактор риска" property="5" />
					<msh:tableColumn columnName="Возрасная группа" property="4" />
					<msh:tableColumn columnName="Выявлено у мужчин"  property="6" />
					<msh:tableColumn columnName="Выявлено у женщин"  property="7" />
					<msh:tableColumn columnName="Выявлено всего"  property="8" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("5")) {%>
			<msh:section title="Свод по группам здоровья за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageGroup='||vedag.id||'&healthGroup='||coalesce(vedhg.id,'-1') as id,ved.name as vedname
,ved.code as vedcode,vedag.name as vedagname
,vedhg.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='1' and edc.isObservation='1' then edc.id else null end) as cntDispM
,count(distinct case when vs.omcCode='1' and edc.isTreatment='1' then edc.id else null end) as cntLechM
,count(distinct case when vs.omcCode='1' and edc.isDiagnostics='1' then edc.id else null end) as cntDiagM
,count(distinct case when vs.omcCode='1' and edc.isSpecializedCare='1' then edc.id else null end) as cntSpecCareM
,count(distinct case when vs.omcCode='1' and edc.isSanatorium='1' then edc.id else null end) as cntSanatM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct case when vs.omcCode='2' and edc.isObservation='1' then edc.id else null end) as cntDispW
,count(distinct case when vs.omcCode='2' and edc.isTreatment='1' then edc.id else null end) as cntLechW
,count(distinct case when vs.omcCode='2' and edc.isDiagnostics='1' then edc.id else null end) as cntDiagW
,count(distinct case when vs.omcCode='2' and edc.isSpecializedCare='1' then edc.id else null end) as cntSpecCareW
,count(distinct case when vs.omcCode='2' and edc.isSanatorium='1' then edc.id else null end) as cntSanatW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedag.id,vedag.name,vedhg.id,vedhg.name
order by vedhg.name,vedag.name
			"/>

				<msh:table name="extDispSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
				<msh:tableNotEmpty>
					<tr>
						<th></th>
						<th></th>
						<th colspan="6">Мужчины</th>
						<th colspan="6">Женщины</th>
						<th></th>
					</tr>
				</msh:tableNotEmpty>
					<msh:tableColumn columnName="Группа здоровья" property="5" />
					<msh:tableColumn columnName="Возрасная группа" property="4" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Назначено лечение" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ" isCalcAmount="true" property="10" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" isCalcAmount="true" property="11" />

					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="12" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" isCalcAmount="true" property="13" />
					<msh:tableColumn columnName="Назначено лечение" isCalcAmount="true" property="14" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование" isCalcAmount="true" property="15" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ" isCalcAmount="true" property="16" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" isCalcAmount="true" property="17" />
					
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="18" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("6")) {%>
			<msh:section title="Свод по услугам за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id as id
,ved.name as vedname
,ved.code as vedcode
,veds.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM1
,count(distinct case when vs.omcCode='1' and (eds.dtype='ExtDispExam' and eds.isPathology='1' or eds.dtype='ExtDispVisit' and eds.recommendation!='')  then edc.id else null end) as cntM2
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW1
,count(distinct case when vs.omcCode='1' and (eds.dtype='ExtDispExam' and eds.isPathology='1' or eds.dtype='ExtDispVisit' and eds.recommendation!='')  then edc.id else null end) as cntW2
,count(distinct edc.id) as cntAll1
,count(distinct case when (eds.dtype='ExtDispExam' and eds.isPathology='1' or eds.dtype='ExtDispVisit' and eds.recommendation!='')  then edc.id else null end) as cntAll2
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispService eds on eds.card_id=edc.id
left join VocExtDispService veds on eds.serviceType_id=veds.id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend}  and eds.serviceDate is not null
group by ved.id,ved.name,ved.code,veds.id,veds.name,veds.code
order by veds.id
			"/>

				<msh:table name="extDispSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
				<msh:tableNotEmpty>
					<tr>
						<th></th>
						<th colspan="2">Мужчины</th>
						<th colspan="2">Женщины</th>
						<th colspan="2">Всего</th>
						<th></th>
					</tr>
				</msh:tableNotEmpty>
					<msh:tableColumn columnName="Услуга" property="4" />
					<msh:tableColumn columnName="Прошли" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Выявлено заболевания" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Прошли" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Выявлено заболевания" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Прошли" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Выявлено заболевания" isCalcAmount="true" property="10" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("7")) {%>
			<msh:section title="Свод по заболеваниям за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageGroup='||vedag.id||'&mkb='||substring(mkb.code,1,3) as id,ved.name as vedname
,ved.code as vedcode,vedag.name as vedagname
,substring(mkb.code,1,3) as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedag.id,vedag.name,substring(mkb.code,1,3)
order by substring(mkb.code,1,3),vedag.name
			"/>

				<msh:table name="extDispSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Класс МКБ" property="5" />
					<msh:tableColumn columnName="Возрасная группа" property="4" />
					<msh:tableColumn columnName="Мужчины" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Женщины" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="8" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("8")) {%>
			<msh:section title="Свод по раб.функциям за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&workFunction='||wf.id as id
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||coalesce(wp.middlename) as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join ExtDispRisk edr on edr.card_id=edc.id
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend}
group by ved.id,wf.id,vwf.name,wp.lastname,wp.firstname,wp.middlename
order by wp.lastname
			"/>

				<msh:table name="extDispSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Раб.функция" property="2" />
					<msh:tableColumn columnName="Кол-во мужчин" isCalcAmount="true" property="3" />
					<msh:tableColumn columnName="Кол-во женщин" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="5" />
				</msh:table>

			</msh:section>







	<%} else if (typeGroup!=null&& typeGroup.equals("9")) {%>
			<msh:section title="Свод по группам возрастов за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispAgeSwod" nativeSql="
select '&dispType='||ved.id||'&ageReportGroup='||vedarg.id as id
,ved.name as vedname
,ved.code as vedcode,vedarg.name as vedagname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispAgeReportGroup vedarg on vedarg.id=vedag.reportGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedarg.id,vedarg.code,vedarg.name
order by vedarg.code
			"/>

				<msh:table name="extDispAgeSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Возрасная группа" property="4" />
					<msh:tableColumn columnName="Прошли диспансеризацию мужчин" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Прошли диспансеризацию женщин" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="7" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("10")) {%>
			<msh:section title="Свод по факторам риска за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageReportGroup='||vedarg.id||'&dispRisk='||vedr.id as id,ved.name as vedname
,ved.code as vedcode,vedarg.name as vedagname
,vedr.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispRisk edr
left join ExtDispCard edc on edr.card_id=edc.id
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispAgeReportGroup vedarg on vedarg.id=vedag.reportGroup_id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} and vedr.id is not null
group by ved.id,ved.name,ved.code,vedarg.id,vedarg.name,vedarg.code,vedr.id,vedr.name
order by vedr.id,vedarg.code
			"/>

				<msh:table name="extDispSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Фактор риска" property="5" />
					<msh:tableColumn columnName="Возрасная группа" property="4" />
					<msh:tableColumn columnName="Выявлено у мужчин" property="6" />
					<msh:tableColumn columnName="Выявлено у женщин"  property="7" />
					<msh:tableColumn columnName="Выявлено всего" property="8" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("11")) {%>
			<msh:section title="Свод по группам здоровья за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageReportGroup='||vedarg.id||'&healthGroup='||coalesce(vedhg.id,'-1') as id,ved.name as vedname
,ved.code as vedcode,vedarg.name as vedagname
,vedhg.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='1' and edc.isObservation='1' then edc.id else null end) as cntDispM
,count(distinct case when vs.omcCode='1' and edc.isTreatment='1' then edc.id else null end) as cntLechM
,count(distinct case when vs.omcCode='1' and edc.isDiagnostics='1' then edc.id else null end) as cntDiagM
,count(distinct case when vs.omcCode='1' and edc.isSpecializedCare='1' then edc.id else null end) as cntSpecCareM
,count(distinct case when vs.omcCode='1' and edc.isSanatorium='1' then edc.id else null end) as cntSanatM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct case when vs.omcCode='2' and edc.isObservation='1' then edc.id else null end) as cntDispW
,count(distinct case when vs.omcCode='2' and edc.isTreatment='1' then edc.id else null end) as cntLechW
,count(distinct case when vs.omcCode='2' and edc.isDiagnostics='1' then edc.id else null end) as cntDiagW
,count(distinct case when vs.omcCode='2' and edc.isSpecializedCare='1' then edc.id else null end) as cntSpecCareW
,count(distinct case when vs.omcCode='2' and edc.isSanatorium='1' then edc.id else null end) as cntSanatW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispAgeReportGroup vedarg on vedarg.id=vedag.reportGroup_id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedarg.id,vedarg.name,vedarg.code,vedhg.id,vedhg.name
order by vedhg.name,vedarg.code
			"/>

				<msh:table name="extDispSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
				<msh:tableNotEmpty>
					<tr>
						<th></th>
						<th></th>
						<th colspan="6">Мужчины</th>
						<th colspan="6">Женщины</th>
						<th></th>
					</tr>
				</msh:tableNotEmpty>
					<msh:tableColumn columnName="Группа здоровья" property="5" />
					<msh:tableColumn columnName="Возрасная группа" property="4" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Назначено лечение" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ" isCalcAmount="true" property="10" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" isCalcAmount="true" property="11" />

					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="12" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" isCalcAmount="true" property="13" />
					<msh:tableColumn columnName="Назначено лечение" isCalcAmount="true" property="14" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование" isCalcAmount="true" property="15" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ" isCalcAmount="true" property="16" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" isCalcAmount="true" property="17" />
					
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="18" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("12")) {%>
			<msh:section title="Свод по заболеваниям за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageReportGroup='||vedarg.id||'&mkb='||substring(mkb.code,1,3) as id,ved.name as vedname
,ved.code as vedcode,vedarg.name as vedagname
,substring(mkb.code,1,3) as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispAgeReportGroup vedarg on vedarg.id=vedag.reportGroup_id
left join VocExtDispRisk vedr on vedag.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedarg.id,vedarg.name,vedarg.code,substring(mkb.code,1,3)
order by substring(mkb.code,1,3),vedarg.name
			"/>

				<msh:table name="extDispSwod" 
				action="extDisp_journal_card.do?beginDate=${beginDate}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Класс МКБ" property="5" />
					<msh:tableColumn columnName="Возрасная группа" property="4" />
					<msh:tableColumn columnName="Мужчины" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Женщины" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="8" />
				</msh:table>

			</msh:section>

	<%} %>
	<%} %>
	</tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    </script>
    </tiles:put>
</tiles:insert>