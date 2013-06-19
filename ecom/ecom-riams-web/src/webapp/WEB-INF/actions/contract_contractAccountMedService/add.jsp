<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="javascript" type="string">
		<script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
		<script>
			var d = document;
			
			var name;
			var nameId;
			var cnt ;
			var cost;
			var count;

			function addRow() {
			    // Считываем значения с формы
				
			    //td3.innerHTML = "<input name='count"+nameId+"' value='"+count+"' size='9'>";
			    var nameId = document.getElementById('medService').value;
			    ContractService.getCostByPriceMedService(nameId, {
		               callback: function(aResult) {
		            	   var cnt=+$('cnt').value+1 ;
		   					$('cnt').value = cnt;
		                   //alert(aPosition) ;
						    var name = document.getElementById('medServiceName').value;
						    nameId = document.getElementById('medService').value;
						    var count = document.getElementById('Count').value;
						   	var costName="cost"+cnt;
						    //cost = document.getElementsByName("cost"+nameId)[0].value;
						    
						    // Находим нужную таблицу
						    var tbody = document.getElementById('tab1').getElementsByTagName('TBODY')[0];
							if(nameId!="")if(nameId!=null){
							    // Создаем строку таблицы и добавляем ее
							    var row = document.createElement("TR");
							    tbody.appendChild(row);
							
							    // Создаем ячейки в вышесозданной строке
							    // и добавляем тх
							    var td1 = document.createElement("TD");
							    var td2 = document.createElement("TD");
							    var td3 = document.createElement("TD");
							    var td4 = document.createElement("TD");
							    
							    row.appendChild(td1);
							    row.appendChild(td2);
							    row.appendChild(td3);
							    row.appendChild(td4);
							
							    // Наполняем ячейки
							    td1.innerHTML = name+"<input id='service"+cnt+"' name='service"+cnt+"' value='"+nameId+"' type='hidden' >"+"<input id='oldid"+cnt+"' name='oldid"+cnt+"' value='0' type='hidden' >";
				                   if (+aResult>0)  {
				                   	td2.innerHTML =  aResult +"<input id='cost"+cnt+"' name='cost"+cnt+"' value='"+aResult+"' type='hidden' / >";
				                   	td4.innerHTML = "<input id='sum"+cnt+"' name='sum"+cnt+"' value='"+(+aResult)*(+count)+"' size='9' readonly='true' />" ;
				                   	//alert(+(+aResult)*(+count));
				                   } else {
				                   	td2.innerHTML = "0" ;
				                   	td4.innerHTML = "<input id='sum"+cnt+"' name='sum"+cnt+"' value='0' size='9' readonly='true' />";
				                   }
				   			    td3.innerHTML = "<input id='count"+cnt+"' name='count"+cnt+"' value='"+count+"' size='9'/ >";
				   			    //$('sum'+cnt).readOnly=true ;
				   			    eval("eventutil.addEventListener($('count"+cnt+"'),'change',function(){checkSum() ;})");
				                eval("eventutil.addEventListener($('count"+cnt+"'),'keyup',function(){checkSum() ;})");

				   			 checkSum() ;
							}
		                }
		    		}
               );
			
			}
			
			
		
		
		
		</script>	
	</tiles:put>
	<tiles:put name="body" type="string">
	<script type="text/javascript">
	function addRowOld(aName, aNameId, aCount, aPrice, aContractAMS)
	{
	    // Считываем значения с формы
		//alert(aNameId) ;
	    //td3.innerHTML = "<input name='count"+nameId+"' value='"+count+"' size='9'>";
	    var nameId = aNameId;
	   

   	    var cnt=+$('cnt').value+1 ;
		$('cnt').value = cnt;
        //alert(cnt) ;
	    var name = aName;
	    //alert('aa') ;
	    //nameId = aNameID;
	    var count = aCount;
	   	var costName="cost"+cnt;
	    //cost = document.getElementsByName("cost"+nameId)[0].value;
	    
	    // Находим нужную таблицу
	    var tbody = document.getElementById('tab1').getElementsByTagName('TBODY')[0];
		if(nameId!="")if(nameId!=null){
		    // Создаем строку таблицы и добавляем ее
		    var row = document.createElement("TR");
		    tbody.appendChild(row);
		
		    // Создаем ячейки в вышесозданной строке
		    // и добавляем тх
		    var td1 = document.createElement("TD");
		    var td2 = document.createElement("TD");
		    var td3 = document.createElement("TD");
		    var td4 = document.createElement("TD");
		    
		    row.appendChild(td1);
		    row.appendChild(td2);
		    row.appendChild(td3);
		    row.appendChild(td4);
		
		    // Наполняем ячейки
		    td1.innerHTML = name+"<input id='service"+cnt+"' name='service"+cnt+"' value='"+nameId+"' type='hidden' >"+"<input id='oldid"+cnt+"' name='oldid"+cnt+"' value='"+aContractAMS+"' type='hidden' >";
               if (+aPrice>0)  {
               	td2.innerHTML =  aPrice +"<input id='cost"+cnt+"' name='cost"+cnt+"' value='"+aPrice+"' type='hidden' / >";
               	td4.innerHTML = "<input id='sum"+cnt+"' name='sum"+cnt+"' value='"+(+aPrice)*(+count)+"' size='9' readonly='true' />" ;
               } else {
               	td2.innerHTML = "0" ;
               	td4.innerHTML = "<input id='sum"+cnt+"' name='sum"+cnt+"' value='0' size='9' readonly='true' />";
               }
			    td3.innerHTML = "<input id='count"+cnt+"' name='count"+cnt+"' value='"+count+"' size='9'/ >";
			    //$('sum'+cnt).readOnly=true ;
			    eval("eventutil.addEventListener($('count"+cnt+"'),'change',function(){checkSum() ;})");
            eval("eventutil.addEventListener($('count"+cnt+"'),'keyup',function(){checkSum() ;})");

			 
			
        }
    		
	}
	function checkSum() {
		var costAll = 0;
		var medServAll = "";
		var cnt = +$('cnt').value ;
		for (var i=1; i<=cnt;i++) {
			if ($('count'+i)) {
				var count=+$('count'+i).value
				var cost = +$('cost'+i).value ;
				var sum = (cost*count) ;
				costAll = costAll + sum ;
				$('sum'+i).value=sum ;
				//medServAll = medServAll+$('service'+i).value+":"+cost+":"+count+"#";
			}
			
		}
		//$('cost'+i).value
		//if(medServAll!=null)$('additionMedService').value=medServAll;
		$('divAllCount1').innerHTML = '<h1>Сумма: '+costAll+' руб.</h1>' 
		$('divAllCount2').innerHTML = '<h1>Сумма: '+costAll+' руб.</h1>' 
	}
	</script>
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
			<msh:autoComplete property="medService" parentId="${param.id}" label="Медицинская услуга" vocName="priceMedServiceByContractAccout" horizontalFill="true" size="90"/>
			</td>
			<td colspan='1' title='кол-во' class='label'>
				<label id='CountName' for='Count'>Кол-во:</label>
			</td>
			<td colspan='1'>
			<div>
			<input title='Count' type='text' name='count' value='1' id='Count' size='9'/>
			</div>
			</td>
			<td>        	
            <input type="submit" name="subm" class="submit" value="+" tabindex="4" />
            </td>
            </tr>
            </table>
         <br/>
	</ul>
</fieldset>
</form>
<br>
<form action='contract_contractAccountMedServiciesSave.do'>
<input id='id' name='id' type='hidden' value="${param.id}">
<input id='cnt' name='cnt' type='hidden' value="0">
<div id="divAllCount1"><h1>Сумма: 0 руб</h1></div>
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
<div id="divAllCount2"><h1>Сумма: 0 руб</h1></div>
<%/**<input id="additionMedService" name="additionMedService" value="0"/> */%>
<input type='submit' value='сохранить' >
</form>
</br>
		<ecom:webQuery name="operation" nativeSql="
			SELECT 
  				MS.name as msname,
  				MS.code as mscode, 
  				PP.cost as ppcost, 
  				PP.id as ppid,
  				CA.id as caid 
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
  				MS.name as msname, PP.cost as ppcost, CAMS.countMedService as camscountmedservice
  				, PMS.id as pmsid, CAMS.id as camsid
			FROM 
  				contractAccountMedService AS CAMS
			LEFT JOIN PricePosition AS PP ON CAMS.medservice_id = PP.id
			left join pricemedservice PMS on PMS.priceposition_id = PP.id
			left join medservice MS on MS.id = PMS.medservice_id
			where CAMS.account_id=  ${param.id}
			"/>
			<%
			List list= (List)request.getAttribute("meservc");
			out.println("<script>");
			for (int i=0 ; i<list.size();i++) {
				WebQueryResult res = (WebQueryResult)list.get(i) ;
				out.println("addRowOld('"+res.get1()+" ("+res.get2()+")'"+", "+res.get4()+", "+res.get3()+",'"+res.get2()+"',"+res.get5()+");");				
				
			}	
			out.println("   checkSum() ;");
			out.println("</script>");		
			%>
			</form>

	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="contract_contractAccountForm" title="Услуги"/>
	</tiles:put>
	<tiles:put name="side" type="string">
		<tags:contractMenu currentAction="medContract"/>
	</tiles:put>
</tiles:insert>