<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.mis.web.action.medcase.journal.RepeatCaseJournalForm"%>
<%@page import="java.text.SimpleDateFormat"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр повторных случаев по стационару</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalRepeatCaseByHospital"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/stac_journalRepeatCaseByHospital_list.do" defaultField="dateBegin" >
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
        <msh:row>
	        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
	        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        	<msh:textField property="cnt" label="искать более" />
        	<td>случаев</td>
        </msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow colSpan="3" labelCreate="Найти" labelSave="Найти" labelCreating="Поиск..." labelSaving="Поиск..."/>
      	</msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    RepeatCaseJournalForm frm = (RepeatCaseJournalForm) session.getAttribute("stac_repeatCaseForm") ;
    //String date = (String)request.getParameter("dateBegin") ;
    //out.println(frm) ;
    //out.println("-------------------") ;
    //out.println(frm) ;
    
    if (frm!=null && frm.getDateBegin()!=null 
    		&&!frm.getDateBegin().equals("")
    		&& frm.getDateEnd()!=null 
    		&&!frm.getDateEnd().equals("")
    		&& frm.getCnt()!=null
    		&&frm.getCnt().intValue()>1
    		
    )  {
    	try {
    	SimpleDateFormat FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd") ;
    	SimpleDateFormat FORMAT_2 = new SimpleDateFormat("dd.MM.yyyy") ;
    	//FORMAT_1.format( FORMAT_2.parse(frm.getDateBegin())) ;
    	request.setAttribute("startDate",FORMAT_1.format( FORMAT_2.parse(frm.getDateBegin()))) ;
    	request.setAttribute("finishDate",FORMAT_1.format( FORMAT_2.parse(frm.getDateEnd()))) ;
    	request.setAttribute("count",frm.getCnt()) ;
    	} catch (Exception e) {
    		out.print(e) ;
    	}
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результат поиска</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_repeatCase" nativeSql="select p.id||':${startDate}:${finishDate}:${count}',p.lastname||' '||p.middlename||' '||p.firstname||' '||to_char(p.birthday,'DD.MM.YYYY'),ss.code,(select count(*) from medcase m where m.patient_id=p.id and m.dtype='HospitalMedCase' and m.datestart between cast('${startDate}' as date) and cast('${finishDate}' as date))
    , (select count(*) from medcase m where m.patient_id=mm.patient_id and m.dtype='HospitalMedCase' and m.datestart between cast('${startDate}' as date) and cast('${finishDate}' as date) and m.deniedHospitalizating_id is not null)
    , (select count(*) from medcase m where m.patient_id=mm.patient_id and m.dtype='HospitalMedCase' and m.datestart between cast('${startDate}' as date) and cast('${finishDate}' as date) and m.ambulanceTreatment=1)
     from medcase mm 
left join patient p on mm.patient_id=p.id 
left join statisticstub ss on ss.id=mm.statisticstub_id
where mm.dtype='HospitalMedCase' and (select count(*) from medcase m where m.patient_id=mm.patient_id and m.dtype='HospitalMedCase' and m.datestart between '${startDate}' and '${finishDate}')>='${count}' group by mm.patient_id" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_repeatCase" action="stac_journalRepeatCaseByHospital_data.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="ФИО пациент" property="2"/>
            <msh:tableColumn columnName="Кол-во случаев" property="4"/>
            <msh:tableColumn columnName="из них отказы" property="5"/>
            <msh:tableColumn columnName="из них амб.случаи" property="6"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
  </tiles:put>
</tiles:insert>

