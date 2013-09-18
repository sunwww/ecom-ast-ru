<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient" guid="4f98c442-7c90-4200-a59c-28f83747e1af" title="Поиск персон" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="5120ac2f-43a7-4204-a2e7-187cf4969bcc">
      <msh:sideLink roles="/Policy/Mis/Patient/Create" key="ALT+N" params="lastname" action="/entityPrepareCreate-pd_person" name="Добавить персону" guid="4cecc5e2-4e6b-4196-82ef-bf68124d90a5" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/searchPerson.do" defaultField="lastname" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
      <msh:panel colsWidth="10%, 10%, 70%" guid="354f9651-7a86-447b-9066-43af5b3bf277">
        <msh:row guid="6ebb763c-58d4-45f6-928e-2d03a5b55b5b">
          <msh:textField property="lastname" label="ФИО, полис или мед. карта" size="40" guid="56502d8a-33ae-463c-910b-59625f2d2778" />
          <td>
            <input type="submit" value="Найти" />
          </td>
        </msh:row>
        <msh:row guid="b729833a-a47b-437e-8d1c-b3362a03ce80">
          <msh:commentBox text="Фамилия Имя Отчество. &lt;i&gt;Например: ИВАНОВ ИВАН ИВАНОВИЧ или ИВАНОВ&lt;/i&gt;&lt;br/&gt;&#xA;Серия Номер Полиса. &lt;i&gt;Например: АА 1234567 или 1234567&lt;/i&gt;&lt;br/&gt;&#xA;Номер мед. карты." guid="5c197db1-df55-446f-ada6-da48c26f4a6c" colSpan="2" />
        </msh:row>
      </msh:panel>
    </msh:form>
    <%
    	String query = request.getParameter("lastname") ;
    
    	if (query!=null && !query.trim().equals("") && query.length()>0) {
    		StringBuilder querySql = new StringBuilder() ;
    		query=query.trim().toUpperCase() ;
   			String[] q = query.split(" ") ;
   			if (q.length>0) {querySql.append(" p.lastname like '").append(q[0]).append("%'") ;} else {querySql.append(" p.lastname like '").append(query).append("'") ;} 
   			if (q.length>1) {querySql.append(" and p.firstname like '").append(q[1]).append("%'") ;}
   			if (q.length>2) {querySql.append(" and p.patronymic like '").append(q[2]).append("%'") ;}
    		querySql.append(" or i.identificationNumber='").append(query).append("'") ;
    		request.setAttribute("querySql", querySql.toString()) ;
    	
    %>
    <ecom:webQuery name="list" nativeSql="select p.id,p.lastname,p.firstname,p.patronymic
    	,p.birthdate  
    from Person p
    left join Identifier i on p.id=i.person_id 
    where ${querySql}
    group by  p.id,p.lastname,p.firstname,p.patronymic
    	,p.birthdate  
    "
    />
      <msh:section title="Результат поиска" guid="8bc5fc1c-72bb-45c8-9eb2-58715c967b81">
        <msh:table viewUrl="entityShortView-pd_person.do" name="list" action="entityView-pd_person.do" idField="1" disableKeySupport="true" guid="7df98006-d2f7-4055-98a4-3b687377d9be" noDataMessage="Не найдено">
          <msh:tableColumn columnName="Фамилия" property="2" guid="87779c74-a164-4c5f-8fa9-5501c300bbf2" />
          <msh:tableColumn columnName="Имя" property="3" guid="88842354-b7d1-4c67-a43e-9837c179d5d1" />
          <msh:tableColumn columnName="Отчество" property="4" guid="4b8cb842-fcfb-4e91-b57f-ed881a1881c5" />
          <msh:tableColumn columnName="Дата рождения" property="5" guid="e63b0a34-7d09-4345-98c9-d9c0e37b69f4" />
        </msh:table>
      </msh:section>
     
    <% } %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript"></script>
  </tiles:put>
</tiles:insert>

