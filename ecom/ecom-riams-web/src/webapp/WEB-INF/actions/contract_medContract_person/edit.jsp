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
			
			
		
		
		
		</script>	
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
	</tiles:put>
	
	<tiles:put name="body" type="string">
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
				<msh:row>
					<msh:autoComplete property="rulesProcessing" label="Обработка правил" fieldColSpan="3" vocName="vocContractRulesProcessing" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:autoComplete property="priceList" label="Прейскурант" fieldColSpan="3"  vocName="priceList" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="dateFrom" label="Дата начала "/>
					<msh:textField property="dateTo" label="Дата окончания "/>
				</msh:row>
				<msh:row>
					<msh:checkBox property="autoAccount" label="Создать счет"/>
				</msh:row>
				
				</msh:panel>
								
		
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
            <input type="button" name="subm" onclick="addRow();" value="+" tabindex="4" />
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
		
		

		
		
		
		
		
		
		
		
		
		
		
		<msh:ifFormTypeIsView formName="contract_medContract_personForm">
			<msh:section title="Обслуживаемые персоны">
			<ecom:webQuery nativeSql="select sp.id,
			CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			,sp.dateFrom,sp.dateTo
			from ServedPerson sp
			left join ContractPerson cp on cp.id=sp.person_id left join patient p on p.id=cp.patient_id
			where sp.contract_id='${param.id}'
			" name="serverPerson"/>
				<msh:table name="serverPerson" action="entityParentView-contract_servedPerson.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Информация" property="2"/>
					<msh:tableColumn columnName="Дата начала обсл." property="3"/>
					<msh:tableColumn columnName="Дата окончания" property="4"/>
				</msh:table>
			</msh:section>
			<msh:section title="Поддоговор">
			<ecom:webQuery name="childContract" nativeSql="
			select mc.id 
			,CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			,ml.name as mlname,mc.contractNumber as mccontractNumber
			,mc.dateFrom as mcdateFrom,mc.dateTo as mcdateTo
			,vcrp.name as vcrpname,pl.name as plname
			from MedContract mc
			left join ContractPerson cp on cp.id=mc.customer_id
			left join Patient p on p.id=cp.patient_id
			left join MisLpu ml on ml.id=mc.lpu_id
			left join VocContractRulesProcessing vcrp on vcrp.id=mc.rulesProcessing_id
			left join PriceList pl on pl.id=mc.priceList_id
			where mc.parent_id='${param.id}'
			"/>
				<msh:table name="childContract" action="entityParentView-contract_medContract.do" idField="1">
					<msh:tableColumn columnName="#" property="sn" />
					<msh:tableColumn columnName="№ договора" property="4" />
					<msh:tableColumn columnName="ЛПУ" property="3" />
					<msh:tableColumn columnName="Заказчик" property="2" />
					<msh:tableColumn columnName="Дата начала" property="5" />
					<msh:tableColumn columnName="Дата окончания" property="6" />
					<msh:tableColumn columnName="Обработка правил" property="7" />
					<msh:tableColumn columnName="Прейскурант" property="8" />
				</msh:table>
			</msh:section>
			<msh:section title="Договорные правила">
			<ecom:webQuery name="rules" nativeSql="select cr.id,cr.dateFrom,cr.dateTo
			,cr.medserviceAmount,cr.courseAmount,cr.medserviceCourseAmount
			,cng.name as cngname, cmsg.name as cmsgname,cgg.name as cggname
			,vcp.name as vcpname,vcrp.name as vcrpname
			,CASE WHEN cp.dtype='NaturalPerson' THEN 'Физ.лицо: '||p.lastname ||' '|| p.firstname|| ' '|| p.middlename||' г.р. '|| to_char(p.birthday,'DD.MM.YYYY') ELSE 'Юрид.лицо: '||cp.name END
			
			from ContractRule cr 
			left join ContractNosologyGroup cng on cng.id=cr.nosologyGroup_id
			left join ContractMedServiceGroup cmsg on cmsg.id=cr.medServiceGroup_id
			left join ContractGuaranteeGroup cgg on cgg.id=cr.guaranteeGroup_id
			left join VocContractPermission vcp on vcp.id=cr.permission_id
			left join VocContractRulePeriod vcrp on vcrp.id=cr.period_id
			left join ServedPerson sp on sp.id=cr.servedPerson_id
			left join ContractPerson cp on cp.id=sp.person_id
			left join Patient p on p.id=cp.patient_id
			where cr.contract_id=${param.id}"/>
				<msh:table name="rules" action="entityParentView-contract_contractRule.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Период действия" property="11"/>
					<msh:tableColumn columnName="Дата начала" property="2"/>
					<msh:tableColumn columnName="Дата окончания" property="3"/>
					<msh:tableColumn columnName="Нозоологическая группа" property="7"/>
					<msh:tableColumn columnName="Группа мед. услуг" property="8"/>
					<msh:tableColumn columnName="Группа гарант. документов" property="9"/>
					<msh:tableColumn columnName="Разрешение" property="10"/>
					<msh:tableColumn columnName="Кол-во мед.услуг" property="4"/>
					<msh:tableColumn columnName="Кол-во курсов" property="5"/>
					<msh:tableColumn columnName="Кол-во мед.услуг на курс" property="6"/>
					<msh:tableColumn columnName="Обс. персона" property="12"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="contract_medContract_personForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
	<msh:ifFormTypeIsView formName="contract_medContract_personForm">
		<msh:sideMenu>
			<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_medContract" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/Edit"/>
			<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_medContract" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/Delete"/>
		</msh:sideMenu>
		<msh:sideMenu title="Добавить" >
			<msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-contract_servedPerson" name="Обслуживаемые персоны" title="Обслуживаемые персоны" roles="/Policy/Mis/Contract/MedContract/ServedPerson/Create"/>
			<msh:sideLink key="ALT+4" params="id" action="/entityParentPrepareCreate-contract_medContract" name="Поддоговор" title="Поддоговор" roles="/Policy/Mis/Contract/MedContract/Create"/>
			<msh:sideLink key="ALT+5" params="id" action="/entityParentPrepareCreate-contract_contractRule" name="Договорные правила" title="Добавить договорные правила по договору" roles="/Policy/Mis/Contract/MedContract/ContractRule/Create"/>

			<msh:sideLink key="ALT+6" params="id" action="/entityParentPrepareCreate-contract_contractGuaranteeLetter" name="Гарантийное письмо" title="Гарантийное письмо" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/Create"/>
			<msh:sideLink key="ALT+7" params="id" action="/entityParentPrepareCreate-contract_contractPaymentOrder" name="Платежное поручение" title="Добавить платежное поручение по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractPaymentOrder/Create"/>
			<msh:sideLink key="ALT+8" params="id" action="/entityParentPrepareCreate-contract_contractMedPolicy" name="Медицинский полис" title="Добавить медицинский полис по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractMedPolicy/Create"/>
		</msh:sideMenu>
		
		<msh:sideMenu title="Показать по договору">
			<msh:sideLink params="id" action="/entityParentList-contract_contractGuaranteeLetter" name="Гарантийные письма" title="Просмотреть гарантийные письма по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter/View"/>
			<msh:sideLink params="id" action="/entityParentList-contract_contractPaymentOrder" name="Платежные поручения" title="Просмотреть платежные поручения по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractPaymentOrder/View"/>
			<msh:sideLink params="id" action="/entityParentList-contract_contractMedPolicy" name="Медицинские полиса" title="Просмотреть медицинские полиса по договору" roles="/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractMedPolicy/View"/>
			<msh:sideLink params="id" action="/entityParentList-contract_contractRule" name="Договорные правила" title="Просмотреть договорные правила по договору" roles="/Policy/Mis/Contract/MedContract/ContractRule/View"/>
		</msh:sideMenu>
		</msh:ifFormTypeIsView>
		<tags:contractMenu currentAction="medContract"/>		
	</tiles:put>
</tiles:insert>
