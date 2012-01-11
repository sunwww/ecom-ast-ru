<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="javascript" type="string">
		<script>
			var d = document;
			
			var name;
			var nameId;
			var cost;
			var count;
			
			function save() {
				String query, id ;
				id = request.getParameter("id");
				int i;
				query = "select 1, ";
				for(i=1; i<=5000;i++){
					if(request.getParameter("count"+i)!=null){
					query = query + "insertmedser("+id+","+
							i+","+
							request.getParameter("count"+i)+"), ";}
				}
				query = query + "2";
				request.setAttribute("query",query);
			}
			function addRow() {
			    // Считываем значения с формы
			
			    name = d.getElementById('medServiceName').value;
			    nameId = d.getElementById('medService').value;
			    count = d.getElementById('Count').value;
			   	var costName="cost"+nameId;
			    //cost = d.getElementsByName("cost"+nameId)[0].value;
			    
			    // Находим нужную таблицу
			    var tbody = d.getElementById('tab1').getElementsByTagName('TBODY')[0];
				if(nameId!="")if(nameId!=null){
			    // Создаем строку таблицы и добавляем ее
			    var row = d.createElement("TR");
			    tbody.appendChild(row);
			
			    // Создаем ячейки в вышесозданной строке
			    // и добавляем тх
			    var td1 = d.createElement("TD");
			    var td2 = d.createElement("TD");
			    var td3 = d.createElement("TD");
			    var td4 = d.createElement("TD");
			    
			    row.appendChild(td1);
			    row.appendChild(td2);
			    row.appendChild(td3);
			    row.appendChild(td4);
			
			    // Наполняем ячейки
			    td1.innerHTML = name;
			    //td3.innerHTML = "<input name='count"+nameId+"' value='"+count+"' size='9'>";
			    //td2.innerHTML = cost;
			    td3.innerHTML = "<input name='count"+nameId+"' value='"+count+"' size='9' ><"+"/"+"input>";
			    //td4.innerHTML = (cost*1)*(1*count);
				}
			}
			
			
			function addRowOld(name, nameId, count)
			{
			    // Считываем значения с формы
			
			   	var costName="cost"+nameId;
			    //cost = d.getElementsByName("cost"+nameId)[0].value;
			    
			    // Находим нужную таблицу
			    var tbody = d.getElementById('tab1').getElementsByTagName('TBODY')[0];
				if(nameId!="")if(nameId!=null){
			    // Создаем строку таблицы и добавляем ее
			    var row = d.createElement("TR");
			    tbody.appendChild(row);
			
			    // Создаем ячейки в вышесозданной строке
			    // и добавляем тх
			    var td1 = d.createElement("TD");
			    var td2 = d.createElement("TD");
			    var td3 = d.createElement("TD");
			    var td4 = d.createElement("TD");
			    
			    row.appendChild(td1);
			    row.appendChild(td2);
			    row.appendChild(td3);
			    row.appendChild(td4);
			
			    // Наполняем ячейки
			    td1.innerHTML = name;
			    //td3.innerHTML = "<input name='count"+nameId+"' value='"+count+"' size='9'>";
			    //td2.innerHTML = cost;
			    td3.innerHTML = "<input name='count"+nameId+"' value='"+count+"' size='9' ><"+"/"+"input>";
			    //td4.innerHTML = (cost*1)*(1*count);
				}
			}
		
		
		
		</script>	
	</tiles:put>
	<tiles:put name="body" type="string">
		<input type="hidden" id="" name=""/>
		<%--<msh:form action="/entityParentSaveGoView-contract_contractAccountMedService.do" defaultField="typeName">
		 	<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="account" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="medService" parentId="${param.id}" label="Медицинская услуга" vocName="PriceMedService" horizontalFill="true" />
					
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>--%>
<form action="" id="add_persons" method="post" onsubmit="addRow();return false;">
<fieldset>
<legend>Добавить медицинскую услугу</legend>
    <ul>
    	<br>
    		<table>
    		<tr>
    		<td>
			<msh:autoComplete property="medService" parentId="${param.id}" label="Медицинская услуга" vocName="PriceMedService" horizontalFill="true" size="90"/>
			</td>
			<td colspan='1' title='кол-во' class='label'>
			<label id='CountName' for='Count'>Кол-во:</label>
			</td>
			<td colspan='1'>
			<div>
			<input title='Count' type='text' name='count' value='' id='Count' size='9'/>
			</div>
			</td>
			<td>        	
            <input type="submit" name="subm" class="submit" value="+" tabindex="4" />
            </td>
            </tr>
            </table>
         <br>
	</ul>
</fieldset>
</form>
<br>

<form action='contract_contractAccountSave.do?'>
<input name='id' type='hidden' value="${param.id}">
<table id="tab1" border='1' class='tabview sel tableArrow'>
    <thead>
        <tr>
            <th>Наименование услуги</th>
            <th>Стоимость</th>
            <th>Кол-во услуг</th>
            <th>Общая стоимость</th>
        </tr>
    </thead>
    <tbody>
    
    </tbody>
</table>
<input type='submit' value='сохранить' >
</form>
</br>
		<ecom:webQuery name="operation" nativeSql="
			SELECT 
  				MS.name,
  				MS.code, 
  				PP.cost, 
  				PP.id,
  				CA.id 
			FROM 
  				contractaccount AS CA
			LEFT JOIN servedperson AS SP ON CA.servedperson_id = SP.id
			LEFT JOIN medcontract AS MC ON SP.contract_id = MC.id
			LEFT JOIN priceposition AS PP ON MC.pricelist_id = PP.pricelist_id
			LEFT JOIN pricemedservice AS PMS ON PMS.priceposition_id = PP.id
			LEFT JOIN medservice AS MS ON   MS.id = PMS.medservice_id
			WHERE 
  				CA.id =  ${param.id}
			"/>
			<ecom:webQuery name="meservc" nativeSql="
			SELECT 
  				MS.name, PP.cost, CAMS.count, PMS.id
			FROM 
  				contractaccount AS CA
			LEFT JOIN contractAccountMedService AS CAMS ON CAMS.account_id = CA.id
			LEFT JOIN PriceMedService AS PMS ON  CAMS.medservice_id = PMS.id
			LEFT JOIN MedService AS MS ON PMS.medService_id =MS.id
			LEFT JOIN PricePosition AS PP ON PMS.pricePosition_id = PP.id
			where CA.id=  ${param.id}
			"/>
			<%
			List list= (List)request.getAttribute("meservc");
			out.println("<script>");
			for (int i=0 ; i<list.size();i++) {
				WebQueryResult res = (WebQueryResult)list.get(i) ;
				
				out.println("addRowOld("+res.get1()+" ("+res.get2()+")"+", "+res.get4()+", "+res.get3()+");");				
				
			}	
			out.println("</script>");		
			%>
			</form>
		<msh:ifFormTypeIsView formName="contract_contractAccountMedServiceForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_contractAccountMedServiceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="contract_contractAccountMedServiceForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractAccountMedService" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountMedService/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractAccountMedservice" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountMedService/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
