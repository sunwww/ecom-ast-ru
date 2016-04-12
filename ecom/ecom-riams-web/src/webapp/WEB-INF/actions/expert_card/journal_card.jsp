<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Disability">Журнал внутреннего контроля качества</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="journalOpenKER"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
	String estimationType = request.getParameter("estimationKind");
	String dateEnd = request.getParameter("dateEnd");
	String dateStart = request.getParameter("dateBegin");
    String typeOrder = ActionUtil.updateParameter("QualityEstimationCard","typeOrder","1", request) ;
   %>
    <msh:form action="quality_card_journal.do" defaultField="1" >
    <msh:panel>
          <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        <msh:autoComplete property="estimationKind" vocName="vocQualityEstimationKind" label="Тип оценки качества" fieldColSpan="50" />
      </msh:row>
      <msh:row>
        <msh:textField property="dateBegin" />
        <msh:textField property="dateEnd" />
      </msh:row>
      
      <msh:row>
      <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeOrder" value="1">  по ФИО пациента
        </td>
        <td onclick="this.childNodes[1].checked='checked'"  colspan="2">
        	<input type="radio" name="typeOrder" value="2" >  по дате экспертизы
        </td>
      </msh:row>
       <msh:row>
           <td colspan="11">
            <input type="submit"  value="Найти" />
          </td>
      </msh:row>
      </msh:panel>
      </msh:form>
          <script type='text/javascript'>
    
    checkFieldUpdate('typeOrder','${typeOrder}',1) ;
    //checkFieldUpdate('typeLpu','${typeLpu}',3) ;
  
   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }
      </script>
    	<% 
    	String sqlAdd = "";
    	String orderBySql = "pat.patientinfo";
    	if (dateEnd!=null&&!dateEnd.equals("")) {
    		
    	} else {
    		dateEnd = dateStart;
    	}
    	if (estimationType!=null&&!estimationType.equals("")) {
    		sqlAdd +="and qec.kind_id="+estimationType;
    	}
    	if (typeOrder!=null&&typeOrder.equals("2")) {
    		orderBySql="qec.createdate";
    	}
    	
    	request.setAttribute("finishDate", dateEnd);
    	request.setAttribute("sqlAdd", sqlAdd);
    	request.setAttribute("orderBySql",orderBySql);
    	
    	%>
    	<ecom:webQuery name="card_list" nameFldSql="card_list_sql"
    	nativeSql="select qec.id
,to_char(qec.createdate,'dd.MM.yyyy') as f1_createDate
,wml.name ||' '||vwf.name ||' '||wpat.lastname ||' ' || wpat.firstname||' '||wpat.middlename as f2_dep_doctor
,'('||ss.code||') ' ||pat.patientinfo as f3_patient
,mkb.code as f4_diagnosis

,max(case when vqec.code = '01' then (vqem.mark) else 0 end) as f5_def1
,max(case when vqec.code = '02' then (vqem.mark) else 0 end) as f6_def2
,max(case when vqec.code = '03' then (vqem.mark) else 0 end) as f7_def3
,max(case when vqec.code = '04' then (vqem.mark) else 0 end) as f8_def4
,max(case when vqec.code = '05' then (vqem.mark) else 0 end) as f9_def5
,max(case when vqec.code = '06' then (vqem.mark) else 0 end) as f10_def6
,max(case when vqec.code = '07' then (vqem.mark) else 0 end) as f11_def7
,max(case when vqec.code = '08' then (vqem.mark) else 0 end) as f12_def8
,max(case when vqec.code = '09' then (vqem.mark) else 0 end) as f13_def9
,max(case when vqec.code = '10' then (vqem.mark) else 0 end) as f14_def10
,sum (vqem.mark)/count(vqec.id) as f15_average
from qualityestimationcard qec
left join vocidc10 mkb on mkb.id=qec.idc10_id
left join workfunction wf on wf.id=qec.doctorcase_id
left join worker w on w.id=wf.worker_id
left join mislpu wml on wml.id=w.lpu_id
left join patient wpat on wpat.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
left join qualityestimation qe on qe.card_id=qec.id
left join qualityestimationcrit qecr on qecr.estimation_id=qe.id
left join vocqualityestimationmark vqem on vqem.id=qecr.mark_id
left join vocqualityestimationcrit vqec on vqec.kind_id = qec.kind_id and vqec.id=qecr.criterion_id  
left join medcase sls on sls.id=qec.medcase_id
left join patient pat on pat.id=sls.patient_id
left join statisticstub ss on ss.medcase_id=sls.id

where  qec.createDate between to_date('${param.dateBegin}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy')
${sqlAdd}
group by qec.id,qec.createdate ,wml.name ,vwf.name,wpat.lastname, wpat.firstname,wpat.middlename ,ss.code,pat.patientinfo ,mkb.code 
order by ${orderBySql}
"
    	/>
        <msh:table name="card_list" viewUrl="expert_kersopen.do?typeJournal=${typeJournal}&short=Short" action="expert_kersopen.do?typeJournal=${typeJournal}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата проведения контроля" property="2"/>
            <msh:tableColumn columnName="ФИО врача/отделение" property="3"/>
            <msh:tableColumn columnName="ФИО пациента, номер карты" property="4"/>
            <msh:tableColumn columnName="Диагноз по МКБ-10" property="5"/>
            <msh:tableColumn columnName="Ведение мед. документации (01)" property="6"/>
            <msh:tableColumn columnName="Сбор жалоб, анамнеза, описание объективного статуса (02)" property="7"/>
            <msh:tableColumn columnName="Оформление диагноза (03)" property="8"/>
            <msh:tableColumn columnName="Диагностические мероприятия и их интерпретация, консультации (04)" property="9"/>
            <msh:tableColumn columnName="Лечебно-профилактичекие мероприятия, медицинская реабилитация (05)" property="10"/>
            <msh:tableColumn columnName="Осуществление динамического наблюдения в установленном порядке (06)" property="11"/>
            <msh:tableColumn columnName="Экспертиза временной нетрудоспособности (07)" property="12"/>
            <msh:tableColumn columnName="Результаты оказания мед. помощи (08)" property="13"/>
            <msh:tableColumn columnName="Сроки оказания мед. помощи (09)" property="14"/>
            <msh:tableColumn columnName="Преемственность, этапность (10)" property="15"/>
            <msh:tableColumn columnName="Интегрированная оценка качества оказания мед. помощи" property="16"/>
        </msh:table>
    
    </tiles:put>

</tiles:insert>