<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    
   
    <tiles:put name='body' type='string'>
    
          
      <msh:form action="/mis_assessment_risk_report.do" defaultField="dateBegin">
      <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">

      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
      <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      
      <msh:row>
      <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
      <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
      <msh:row>
      <msh:autoComplete vocName="vocAssessmentCard" property="assessmentCardTemplate" label="Тип оценочной карты"  fieldColSpan="4" size="30" />
	  </msh:row>
      <msh:row>
      <msh:autoComplete vocName="VocAssessmentsByTemplate" property="assessment" label="Оценка"  parentAutocomplete="assessmentCardTemplate" fieldColSpan="4" size="30" />
	  </msh:row>
      <msh:row>
      <input type="submit" value="Найти" />
      </msh:row>
      </msh:panel>
      </msh:form>
      
      
       <%
    String date = (String)request.getParameter("dateBegin") ;
    String dateEnd = (String)request.getParameter("dateEnd") ;
    String assessmentCardTemplate = (String)request.getParameter("assessmentCardTemplate") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    request.setAttribute("assessmentCardTemplate", assessmentCardTemplate);
    String view = (String)request.getParameter("typeView");
    
    if (date!=null && !date.equals("") && assessmentCardTemplate!=null && !assessmentCardTemplate.equals("")) {
        
    	String assessment = (String)request.getParameter("assessment") ;
    	String sqlAdd = "";
    	if (assessment!=null&&!assessment.equals("")) {
    		sqlAdd += " and ass.id = "+assessment;        		
    	}
    	if (assessmentCardTemplate!=null&&!assessmentCardTemplate.equals("")) {
    		sqlAdd += " and act.id = "+assessmentCardTemplate;
    	}
    	request.setAttribute("sqlAdd", sqlAdd);
    	
        if (view==null||view.equals("")) {
        %>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
        <msh:section>
    <msh:sectionTitle>Разбивка по дням</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_assessment_risk" nativeSql="select '&assessment='||ass.id, ass.name, count(ac.id)
			from assessmentcard ac 
			left join assessmentcardtemplate act on act.id = ac.template
			left join assessment ass on ass.assessmentcard = act.id 
			where ac.ballsum between ass.minball and ass.maxball
			and ac.startdate between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') 
    ${sqlAdd}
			group by ass.id, ass.name" />
			
    <msh:table name="journal_assessment_risk" 
    action="mis_assessment_risk_report.do?typeView=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&assessmentCardTemplate=${assessmentCardTemplate}" idField="1"
    >
    <msh:tableColumn property="sn" columnName="#"/>
    <msh:tableColumn property="2" columnName="Тип оценки"/>
    <msh:tableColumn property="3" columnName="Количество"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
       
        <%  } else {
        	
        	%>
        	
        	<msh:section>
            <msh:sectionTitle>Хто?</msh:sectionTitle>
            <msh:sectionContent>
            <ecom:webQuery name="whoisit" nativeSql="select ac.id ,ass.name, p.patientinfo
        from assessmentcard ac
        left join assessmentcardtemplate act on act.id = ac.template
        left join assessment ass on ass.assessmentcard = act.id 
        left join patient p on ac.patient = p.id
        where ac.ballsum between ass.minball and ass.maxball 
        and ac.startdate between to_date('${dateBegin}','dd.mm.yyyy') 
        and to_date('${dateEnd}','dd.mm.yyyy') 
        
        ${sqlAdd}
        " />
        	
            <msh:table name="whoisit" 
            action="entityParentView-mis_assessmentCard.do" idField="1"
            >
            <msh:tableColumn property="1" columnName="id"/>
            <msh:tableColumn property="2" columnName="Тип оценки"/>
            <msh:tableColumn property="3" columnName="Пациент"/>
            </msh:table>
            </msh:sectionContent>
            </msh:section>
   <%   }  } else {%>
    	<i>Заполните параметры!</i>
    	<% }   %>
       
    </tiles:put>
</tiles:insert>