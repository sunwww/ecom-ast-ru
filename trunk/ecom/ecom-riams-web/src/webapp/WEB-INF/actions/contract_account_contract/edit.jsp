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
			function addRowWithDir(aId,aName) {
				//alert(aId) ;
				var isNew = true ;
				var cnt = +$('cnt').value ;
			   for (var i=1; i<=cnt;i++) {
					if ($('count'+i)) {
						
						if ($('service'+i).value==aId) {
							var counti=+$('count'+i).value ;
							$('count'+i).value=1+counti ;
							isNew = false ;
							checkSum() ;
							break ;
						}
						
					}
					
				}
			   if (isNew) {
			    ContractService.getCostByPriceMedService(aId, {
		               callback: function(aResult) {
		            	   var cnt=+$('cnt').value+1 ;
		   					$('cnt').value = cnt;
		                   
						    var name = aName;
						    nameId=aId ;
						    var count = 1;
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
				                	td2.innerHTML =  aResult +"<input id='cost"+cnt+"' name='cost"+cnt+"' value='"+aResult+"' type='hidden' / >";
				                   	td4.innerHTML = "<input id='sum"+cnt+"' name='sum"+cnt+"' value='0' size='9' readonly='true' />";
				                   }
				   			    td3.innerHTML = "<input id='count"+cnt+"' name='count"+cnt+"' value='"+count+"' size='9'/ >";
				   			    //$('sum'+cnt).readOnly=true ;
				   			    eval("eventutil.addEventListener($('count"+cnt+"'),'change',function(){checkSum() ;})");
				                eval("eventutil.addEventListener($('count"+cnt+"'),'keyup',function(){checkSum() ;})");

				   			 checkSum() ;
							}
		                }
		    		});
				}
			   checkSum() ;
			}
			function addRow() {
			    // Считываем значения с формы
				
			    //td3.innerHTML = "<input name='count"+nameId+"' value='"+count+"' size='9'>";
			    var nameId = document.getElementById('priceMedService').value;
			    ContractService.getCostByPriceMedService(nameId, {
		               callback: function(aResult) {
		            	   var cnt=+$('cnt').value+1 ;
		   					$('cnt').value = cnt;
		                   //alert(aPosition) ;
						    var name = document.getElementById('priceMedServiceName').value;
						    nameId = document.getElementById('priceMedService').value;
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
				if (medServAll!='') medServAll=medServAll+"#" ;
				medServAll = medServAll+$('service'+i).value+":"+count;
			}
			
		}
		$('priceMedServicies').value=medServAll;
		$('divAllCount1').innerHTML = '<h1>Сумма: '+costAll+' руб. с учетом скидки: '+(costAll*(100-$('discountDefault').value)/100)+'руб.</h1>' 
		$('divAllCount2').innerHTML = '<h1>Сумма: '+costAll+' руб. с учетом скидки: '+(costAll*(100-$('discountDefault').value)/100)+'руб.</h1>' 
		//$('divAllCount2').innerHTML = '<h1>Сумма: '+costAll+' руб.</h1>' 
		
	}
	$('autoAccount')
	</script>
	</tiles:put>
	
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-contract_account_contract.do" defaultField="servedPerson">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="contract" />
  <msh:ifFormTypeIsView formName="contract_account_contractForm">
  <script type="text/javascript">
  	window.location.href='entityView-contract_medContract.do?id='+$('contract').value ;
  </script>
  </msh:ifFormTypeIsView>	
			<msh:hidden property="createDate" />
			<msh:hidden property="createTime" />
			<msh:hidden property="createUsername" />
			<msh:hidden property="editDate" />
			<msh:hidden property="editTime" />
			<msh:hidden property="editUsername" />
			<msh:hidden property="editUsername" />
			<msh:hidden property="priceList" />
			<msh:hidden property="priceMedServicies" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="servedPerson" label="Обс.персона" vocName="contractPerson" size="100" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textField property="discountDefault" label="Скидка"/>
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала "/>
					<msh:textField property="dateTo" label="Дата окончания "/>
				</msh:row>				
				</msh:panel>
								<script type="text/javascript">
				   			    eventutil.addEventListener($('discountDefault'),'change',function(){checkSum() ;});
				                eventutil.addEventListener($('discountDefault'),'keyup',function(){checkSum() ;});

								</script>
		
<fieldset>
<legend>Добавить медицинскую услугу</legend>
    		<table>
    		<tr>
    		<td>
			<msh:autoComplete property="priceMedService" parentId="contract_account_contractForm.priceList" label="Медицинская услуга" vocName="priceMedServiceByPriceList" horizontalFill="true" size="90"/>
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
            <input type="button" name="subm" onclick="addRow();" value="+" tabindex="4" />
            <input type="button" name="subm" onclick="show1DirMedService();" value="++" tabindex="4" />
            </td>
            </tr>
            </table>

</fieldset>


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
				<msh:ifFormTypeIsView formName="contract_account_contractForm">
				<msh:panel>
				<msh:row>
					<msh:separator label="Информация о создании" colSpan="4"/>
				</msh:row>
				<msh:row>
					<msh:textField property="createDate" label="Дата"/>
					<msh:textField property="createTime" label="Время"/>
					<msh:textField property="createUsername" label="Пользователь"/>
				</msh:row>
				<msh:row>
					<msh:separator label="Информация о последней редакции" colSpan="4"/>
					<msh:textField property="editDate" label="Дата"/>
					<msh:textField property="editTime" label="Время"/>
				</msh:row>
				<msh:row>
					<msh:textField property="editUsername" label="Пользователь"/>
				</msh:row>
				</msh:panel>
				</msh:ifFormTypeIsView>
				<msh:panel>
					<msh:submitCancelButtonsRow colSpan="4" />
				</msh:panel>	
		</msh:form>
		<tags:dir_medService name="1" table="PRICEMEDSERVICE" title="Прейскурант" functionAdd="addRowWithDir" addParam="priceList"/>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_account_contractForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<tags:contractMenu currentAction="medContract"/>		
	</tiles:put>
</tiles:insert>
