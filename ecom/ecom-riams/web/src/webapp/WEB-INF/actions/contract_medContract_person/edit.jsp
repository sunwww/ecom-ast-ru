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
                                var td5 = document.createElement("TD");
							    
							    row.appendChild(td1);
							    row.appendChild(td2);
							    row.appendChild(td3);
							    row.appendChild(td4);
                                row.appendChild(td5);
							
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
                                var attr=1;
                                td5.innerHTML = "<input  readonly='true' hidden id='attr"+cnt+"' name='attr"+cnt+"' value='"+attr+"' size='9'/ >";
				   			 checkSum() ;
							}
		                }
		    		});
				}
			   checkSum() ;
			}
            //milamesher добавление в договор услуг с направлениями
            function addRow(nameIdp,namep,cntp) {
                var nameId, name,cnt,count;
                cnt=+$('cnt').value+1 ;
                $('cnt').value = cnt;
                if (nameIdp==-1) {
                    nameId = document.getElementById('priceMedService').value;
                    name = document.getElementById('priceMedServiceName').value;
                    count = document.getElementById('Count').value;
                }
                else {
                    var nameId = nameIdp;
                    var name = namep;
                    count = cntp;
                }
                ContractService.getCostByPriceMedService(nameId, {
                        callback: function(aResult) {
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
                                var td5 = document.createElement("TD");

                                row.appendChild(td1);
                                row.appendChild(td2);
                                row.appendChild(td3);
                                row.appendChild(td4);
                                row.appendChild(td5);

                                // Наполняем ячейки
                                td1.innerHTML = name+"<input readonly='true' id='service"+cnt+"' name='service"+cnt+"' value='"+nameId+"' type='hidden' >"+"<input id='oldid"+cnt+"' name='oldid"+cnt+"' value='0' type='hidden' >";
                                if (+aResult>0)  {
                                    td2.innerHTML =  aResult +"<input  readonly='true' id='cost"+cnt+"' name='cost"+cnt+"' value='"+aResult+"' type='hidden' / >";
                                    td4.innerHTML = "<input  readonly='true' id='sum"+cnt+"' name='sum"+cnt+"' value='"+(+aResult)*(+count)+"' size='9' readonly='true' />" ;
                                    //alert(+(+aResult)*(+count));
                                } else {
                                    td2.innerHTML = "0" ;
                                    td4.innerHTML = "<input  readonly='true' id='sum"+cnt+"' name='sum"+cnt+"' value='0' size='9' readonly='true' />";
                                }
                                td3.innerHTML = "<input  readonly='true' id='count"+cnt+"' name='count"+cnt+"' value='"+count+"' size='9'/ >";
                                var attr=(nameIdp==-1)? 1:0;
                                td5.innerHTML = "<input  readonly='true' hidden id='attr"+cnt+"' name='attr"+cnt+"' value='"+attr+"' size='9'/ >";
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
                    if ($('count' + i) && $('attr' + i)) {
                        var attr = +$('attr' + i).value;
                            var count = +$('count' + i).value;
                            var cost = +$('cost' + i).value;
                            var sum = (cost * count);
                            costAll = costAll + sum;
                            $('sum' + i).value = sum;
                        if (attr != 0) {
                            if (medServAll != '') medServAll = medServAll + "#";
                            medServAll = medServAll + $('service' + i).value + ":" + count;
                        }
                    }
                }
                $('priceMedServicies').value=medServAll;
                $('divAllCount1').innerHTML = '<h1>Сумма: '+costAll+' руб. с учетом скидки: '+(costAll*(100-$('discountDefault').value)/100)+'руб.</h1>';
                $('divAllCount2').innerHTML = '<h1>Сумма: '+costAll+' руб. с учетом скидки: '+(costAll*(100-$('discountDefault').value)/100)+'руб.</h1>';
            }
	$('autoAccount')
</script>
	</tiles:put>
	
	<tiles:put name="body" type="string">
  <msh:ifFormTypeIsView formName="contract_medContract_personForm">
  <script type="text/javascript">
  	window.location.href='entityView-contract_medContract.do?id='+${param.id} ;
  </script>
  </msh:ifFormTypeIsView>	
		<msh:form action="/entitySaveGoView-contract_medContract_person.do" defaultField="contractNumber">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:hidden property="createDate" />
			<msh:hidden property="createTime" />
			<msh:hidden property="createUsername" />
			<msh:hidden property="editDate" />
			<msh:hidden property="editTime" />
			<msh:hidden property="editUsername" />
			<msh:hidden property="editUsername" />
			<msh:hidden property="customer" />
			<msh:hidden property="priceMedServicies" />
			<msh:hidden property="referralsInfo"/>
			<msh:hidden property="referralsInfoLab"/>
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="customer" label="Заказчик" viewOnlyField="true" vocName="contractPerson" size="100" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>			
				<msh:row>
					<msh:textField property="contractNumber" label="Номер договора"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="lpu" label="ЛПУ" vocName="lpu" horizontalFill="true" size="100" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="servedPerson" label="Обс.персона" vocName="contractPerson" size="100" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:ifInRole roles="/Policy/Mis/Person/Privilege">
				<msh:row>
					<msh:autoComplete property="privilege" label="Льгота" fieldColSpan="3" parentAutocomplete="servedPerson" vocName="actualPrivilege" horizontalFill="true" />
					<td colspan="9" title="добавить льготу" class="label">
						<a href=" javascript:showPrivilPrivilege('.do') ">Добавить льготу</a>
					</td>
					<tags:mis_privilegeTag name="Privil"/>
				</msh:row>
				</msh:ifInRole>
				<msh:row>
					<msh:autoComplete property="rulesProcessing" label="Обработка правил" fieldColSpan="3" vocName="vocContractRulesProcessing" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="discountDefault" label="Скидка"/>
					<msh:autoComplete property="priceList" label="Прейскурант" fieldColSpan="1"  vocName="actualPriceList" horizontalFill="true" />

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
			<msh:autoComplete property="priceMedService" parentAutocomplete="priceList" label="Медицинская услуга" vocName="priceMedServiceByPriceList" horizontalFill="true" size="90"/>
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
            <input type="button" name="subm" onclick="addRow(-1,'',-1);" value="+" tabindex="4" />
            <input type="button" name="subm" onclick="show1DirMedService();" value="++" tabindex="4" />
				<input type="button" name="subm" onclick="showAddEditSpecContractOne($('priceList').value);" value="Доб. направление к спец." tabindex="4" />
			<input type="button" name="subm" onclick="showAddEditPrescContract($('priceList').value);" value="Доб. направление в лаб." tabindex="4" />
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
				<msh:ifFormTypeIsView formName="contract_medContract_personForm">
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
		<tags:AddEditPrescContract name="AddEditPrescContract"/>
		<tags:AddEditSpecContractOne name="AddEditSpecContractOne"/>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_medContract_personForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<tags:contractMenu currentAction="medContract"/>		
	</tiles:put>
</tiles:insert>
