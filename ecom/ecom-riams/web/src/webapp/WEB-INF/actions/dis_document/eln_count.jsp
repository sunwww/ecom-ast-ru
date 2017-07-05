<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

 <tiles:put name="body" type="string">
 
  <msh:form guid="formHello" action="/eln_count.do" defaultField="hello">
      
      <msh:panel guid="panel">
      <msh:row>
       <td>
            Период
          </td>
			<msh:textField property="dateBegin" label="c"/>
			<msh:textField property="dateEnd" label="по"/>
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
     </msh:panel>
    </msh:form>
    
     <%
    /* String beginDate = request.getParameter("dateBegin");
     String finishDate = request.getParameter("dateEnd") ;
     
     request.setAttribute("dateStart", beginDate);
     request.setAttribute("dateFinish", finishDate) ;*/
     
     String beginDate = request.getParameter("dateBegin") ;
	if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("dateEnd") ;		
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		 request.setAttribute("dateStart", beginDate);
		 request.setAttribute("dateFinish", finishDate) ;

%>

<ecom:webQuery name = "elnList" nativeSql="
select count(dd.id) as count1, count(elec.id) as count2, count(exp.id) as count3
from disabilitydocument dd
left join electronicdisabilitydocumentnumber elec on elec.number = dd.number
left join exportfsslog exp on exp.disabilitynumber = elec.number and result= 'OK'
where 
dd.createdate between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')" />
    <msh:section>
    <msh:sectionContent>
				<msh:table name="elnList" action="" idField="1">
					<msh:tableColumn columnName="Общее кол-во ЛН" property="1" />
					<msh:tableColumn columnName="Из них ЭЛН" property="2" />
					<msh:tableColumn columnName="Отправлено в ФСС" property="3" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
			 <%} else { %>
			    	<p>Укажите период!</p>
			    	<%}%>
			
     </tiles:put>
</tiles:insert>
    