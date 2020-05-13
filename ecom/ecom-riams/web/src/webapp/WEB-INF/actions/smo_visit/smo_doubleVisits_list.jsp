<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly" property="worker" title="Повторные посещения">Просмотр дублей по специалистам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="journalDouble"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_doubleTickets_list.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>

      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    
    <msh:section>
    <msh:sectionContent>
    <ecom:webQuery name="journal_double_visit" nativeSql="
    select 
    t.patient_id||':'||to_char(t.dateStart,'yyyy-mm-dd')||':'||t.workFunctionExecute_id
,t.dateStart
    				,count(distinct t1.id) 
    				,vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename,pm.lastname||' '||pm.firstname||' '||pm.middlename 
    				from Medcase t 
    				left join Medcase t1 on  t.patient_id =t1.patient_id 
    				left join WorkFunction as wf on wf.id=t.workFunctionExecute_id 
    				left join Worker as w on w.id=wf.worker_id 
    				left join Patient as p on p.id=w.person_id 
    				
    				 left join Patient as pm on pm.id=t.patient_id 
    				 inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
    				 where t.DTYPE='Visit' and t.dateStart  
    				 between to_date('${param.dateBegin}','dd.mm.yyyy')  
    				 and to_date('${param.dateEnd}','dd.mm.yyyy') and t.id>t1.id 
    				 and t1.dateStart=t.dateStart and t1.workFunctionExecute_id=t.workFunctionExecute_id and t1.DTYPE='Visit'
 group by t.patient_id,t.dateStart,t.workFunctionExecute_id ,vwf.name, p.lastname,p.firstname,p.middlename,pm.lastname,pm.firstname,pm.middlename
 having count(t1.id)>1
 "/>
        <msh:table name="journal_double_visit" action="smo_doubleVisits_data.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="2"/>
            <msh:tableColumn columnName="Пациент" property="5"/>
            <msh:tableColumn columnName="Специалист" property="4"/>
            <msh:tableColumn columnName="Кол-во" property="3"/>
        </msh:table>
    </msh:sectionContent>


    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
    
    <script type='text/javascript'>
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='smo_doubleVisits_list.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='smo_doubleVisits_print.do' ;
    }
     </script>
  </tiles:put>
</tiles:insert>