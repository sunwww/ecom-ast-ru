<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Journals" >Диспансеризация</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
  	String typeGroup =ActionUtil.updateParameter("ExtDispAction","typeGroup","1", request) ;
  	String typePaid =ActionUtil.updateParameter("ExtDispAction","typePaid","3", request) ;
%>
		<msh:form action="extDisp_reestr_card.do" defaultField="beginDate">
			<msh:panel>
			<msh:row>
				<msh:textField property="beginDate" label="c"/>
				<msh:textField property="finishDate" label="по"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="dispType" label="Тип доп. диспансеризации" vocName="vocExtDisp" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="ageGroup" label="Возрастная группа" vocName="vocExtDispAgeGroupByDispType" parentAutocomplete="dispType" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="healthGroup" label="Группа здоровья" parentAutocomplete="dispType" vocName="vocExtDispHealthGroupByDispType" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="risk" label="Риск" vocName="vocExtDispRisk" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="service" label="Услуга" vocName="vocExtDispService" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="vocWorkFunction" label="Рабочая функция" vocName="vocWorkFunction" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="workFunction" label="Специалист" vocName="workFunction" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="lpu" label="ЛПУ" vocName="lpu" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>

        <msh:row>
	        <td class="label" title="Оплата (typePaid)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировки общие:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typePaid" value="1"> оплаченные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typePaid" value="2"> не оплаченные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typePaid" value="3"> все
	        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Группировка (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировки общие:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1"> реестр по типу доп.дисп.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> реестр по услугам
	        </td>
        </msh:row>
        
      <msh:row>
      		<msh:textArea property="listCards" label="Список кодов синх. пациента" rows="5" />
      </msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>
        
			</msh:panel>
			
		</msh:form>
<%
	String beginDate = request.getParameter("beginDate") ;
	if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("finishDate") ;
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		request.setAttribute("beginDate", beginDate) ;
		request.setAttribute("finishDate", finishDate) ;
		
		StringBuilder sqlAdd = new StringBuilder() ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("dispType","edc.dispType_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("workFunction","edc.workFunction_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("vocWorkFunction","wf.workFunction_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("lpu","lpu.id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("ageGroup","edc.ageGroup_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("healthGroup","edc.healthGroup_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("socialGroup","edc.socialGroup_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("risk","edr.dispRisk_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("service","eds.serviceType_id", request)) ;
		if (typePaid!=null &&typePaid.equals("1")) {
			sqlAdd.append(" and (edc.notPaid is null or edc.notPaid='0')") ;
		} else if (typePaid!=null &&typePaid.equals("2")) {
			sqlAdd.append(" and edc.notPaid='1'") ;
		}
		
		String medcard = request.getParameter("listCards") ;

		if (medcard!=null&&!medcard.equals("")&&medcard.length()>1) {
			
			String[] fs1=medcard.split(",") ;
			StringBuilder filtOr = new StringBuilder() ;
			
			for (int i=0;i<fs1.length;i++) {
				String filt1 = fs1[i].trim() ;
				
				if (filt1.length()>0) {
					if (filtOr.length()>0) filtOr.append(",") ;
		    		filtOr.append("'"+filt1+"'") ;

				}
			}
			request.setAttribute("medcardAddJoin", "left join  medcard medc on p.id=medc.person_id") ;
			
			sqlAdd.append(" and ( upper(medc.number) in ("
					+filtOr.toString()+") or upper(p.patientSync) in ("+filtOr.toString()+"))") ;
		}
		request.setAttribute("sqlAppend", sqlAdd.toString()) ;
		%>
		<% if (typeGroup!=null && typeGroup.equals("1") ) {%>
			<msh:section>
			<ecom:webQuery name="reestrExtDispCard" nameFldSql="reestrExtDispCard_sql" nativeSql="
select edc.id,p.lastname||' '||p.firstname||' '||
p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy') as birthday
,to_char(edc.startDate,'dd.mm.yyyy') as edcBeginDate,to_char(edc.finishDate,'dd.mm.yyyy') as edcFinishDate
,mkb.code as mkbcode
,vedag.name as vedagname
,vedsg.name as vedsgname
,vedhg.name as vedhgname
,list(distinct vedr.name) as vrisks
, edc.isObservation as cntDispM
,edc.isTreatment as cntLechM
,edc.isDiagnostics as cntDiagM
,edc.isSpecializedCare as cntSpecCareM
,edc.isSanatorium as cntSanatM
,edc.isServiceIndication as cntIsServiceIndication
,case
 when ved.code='CHILD_PROF_1' then 'ПРОФ'
when ved.code='CHILD_PROF_2' then 'ПРОФ'
when ved.code='CHILD_PERIOD_PRE_SCHOOL_1' then 'ПЕРИОД'
when ved.code='CHILD_PERIOD_SPEC_SCHOOL_1' then 'ПЕРИОД'
when ved.code='CHILD_PERIOD_SCHOOL_1' then 'ПЕРИОД'
when ved.code='CHILD_PERIOD' then 'ПЕРИОД'
when ved.code='CHILD_PRE_SCHOOL_1' then 'ПРЕДВАРИТ' 
 when ved.code='CHILD_SPEC_SCHOOL_1' then 'ПРЕДВАРИТ' 
 when ved.code='CHILD_SCHOOL_1' then 'ПРЕДВАРИТ' 
 when ved.code='ORPH_DISP_REAL_1' then 'СИРОТЫ' 
 when ved.code='ORPH_DISP_1' then 'СИРОТЫ' 
  else ved.name end as vedname
  ,list(veds.code||' '||veds.name||' - '||to_char(eds.serviceDate,'dd.mm.yyyy')) as service
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient p on p.id=edc.patient_id
${medcardAddJoin}
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by edc.id,p.lastname,p.firstname,
p.middlename,p.birthday,edc.startDate ,edc.finishDate 
,vedag.name,vedhg.name,vedsg.name
, edc.isObservation ,edc.isTreatment ,edc.isDiagnostics ,edc.isSpecializedCare,edc.isSanatorium 
,mkb.code,edc.isServiceIndication,ved.name,ved.code
order by p.lastname,p.firstname,p.middlename
			"/>
<msh:sectionTitle>
    <form action="print-extDisp_reestr_period_1.do" method="post" target="_blank">
Реестр карт по доп.диспансеризации за период ${param.beginDate}-${param.finishDate}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestrExtDispCard_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр карт по доп.диспансеризации за период с ${param.beginDate} по ${param.finishDate}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>

</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCard" 
				action="entityView-extDisp_card.do" viewUrl="entityView-extDisp_card.do?short=Short"
				idField="1">
				<msh:tableColumn columnName="Вид диспансеризации" property="15" />
					<msh:tableColumn columnName="ФИО пациента" property="2" />
					<msh:tableColumn columnName="Дата начала" property="3" />
					<msh:tableColumn columnName="Дата окончания" property="4" />
					<msh:tableColumn columnName="Код МКБ" property="5" />
					<msh:tableColumn columnName="Возрастная категория" property="6" />
					<msh:tableColumn columnName="Социальная группа" property="7" />
					<msh:tableColumn columnName="Группа здоровья" property="8" />
					<msh:tableColumn columnName="Факторы риска" property="9" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" property="10" />
					<msh:tableColumn columnName="Услуги" property="16" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
		<% } else if (typeGroup!=null && typeGroup.equals("2") ) {%>
			<msh:section>
			<ecom:webQuery name="reestrExtDispCard" nameFldSql="reestrExtDispCard_sql" nativeSql="
select edc.id,p.lastname||' '||p.firstname||' '||p.middlename as fio
, to_char(p.birthday,'dd.mm.yyyy') as birthday
,vedag.name as vedagname
,vedhg.name as vedhgname
,mkb.code as mkbcode
,list(distinct vedr.name) as vrisks
,coalesce(a.name)||' ' || case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end 
	 ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end 
	||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end 
	
	as address
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
,to_char(edc.startDate,'dd.mm.yyyy')||'-'||to_char(edc.finishDate,'dd.mm.yyyy') as edcDate
,case
 when ved.code='CHILD_PROF_1' then 'ПРОФ'
when ved.code='CHILD_PROF_2' then 'ПРОФ'
when ved.code='CHILD_PERIOD_PRE_SCHOOL_1' then 'ПЕРИОД'
when ved.code='CHILD_PERIOD_SPEC_SCHOOL_1' then 'ПЕРИОД'
when ved.code='CHILD_PERIOD_SCHOOL_1' then 'ПЕРИОД'
when ved.code='CHILD_PERIOD' then 'ПЕРИОД'
when ved.code='CHILD_PRE_SCHOOL_1' then 'ПРЕДВАРИТ' 
 when ved.code='CHILD_SPEC_SCHOOL_1' then 'ПРЕДВАРИТ' 
 when ved.code='CHILD_SCHOOL_1' then 'ПРЕДВАРИТ' 
 when ved.code='ORPH_DISP_REAL_1' then 'СИРОТЫ' 
 when ved.code='ORPH_DISP_1' then 'СИРОТЫ' 
  else ved.name end as vedname
,list(veds.code||' '||veds.name||' - '||to_char(eds.serviceDate,'dd.mm.yyyy')) as service
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient wp on wp.id=w.person_id
left join Patient p on p.id=edc.patient_id
left join Address2 a on a.addressid=p.address_addressid
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by edc.id,p.lastname,p.firstname,
p.middlename,p.birthday,edc.startDate ,edc.finishDate 
,vedag.name,vedhg.name,vedsg.name
, edc.isObservation ,edc.isTreatment ,edc.isDiagnostics ,edc.isSpecializedCare,edc.isSanatorium 
,mkb.code,edc.isServiceIndication,a.fullname,p.houseNumber,p.houseBuilding
,p.flatNumber,vwf.name,wp.lastname,wp.firstname,wp.middlename,ved.code,ved.name
order by p.lastname,p.firstname,p.middlename
			"/>
<msh:sectionTitle>
    <form action="print-extDisp_reestr_period_2.do" method="post" target="_blank">
Реестр карт по доп.диспансеризации за период ${param.beginDate}-${param.finishDate}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestrExtDispCard_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр карт по доп.диспансеризации за период с ${param.beginDate} по ${param.finishDate}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>

</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCard" 
				action="entityView-extDisp_card.do" viewUrl="entityView-extDisp_card.do?short=Short"
				idField="1">
				<msh:tableColumn columnName="Вид диспансеризации" property="11" />
					<msh:tableColumn columnName="ФИО пациента" property="2" />
					<msh:tableColumn columnName="Год рождения" property="3" />
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Группа здоровья" property="5" />
					<msh:tableColumn columnName="Диагноз" property="6" />
					<msh:tableColumn columnName="Факторы риска" property="7" />
					<msh:tableColumn columnName="Адрес" property="8" />
					<msh:tableColumn columnName="Фамилия врача" property="9" />
					<msh:tableColumn columnName="Дата дисп." property="10" />
					<msh:tableColumn columnName="Услуги" property="12" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
		
	<%}} %>

	</tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/ExtDispService.js"></script>
  	<script type="text/javascript">

    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typePaid','${typePaid}',1) ;
 //   checkFieldUpdate('typeDtype','${typeDtype}',3) ;
  //  checkFieldUpdate('typeDate','${typeDate}',2) ;
   var sqlAdd = ""; 
  
   function createSqlField (aField, aSqlField) {
	   if ($(aField)) {
		   if ($(aField).value!=null&&$(aField).value!='') {
			   return " and "+aSqlField + " = "+$(aField).value;
			   
		   }
	   }
	   return '';
   }
  function showForm() {
	
	  if ($('formOrphDiv').style.display=='block') {
		  $('formOrphDiv').style.display='none';
	  }else {
		  $('formOrphDiv').style.display='block';
	  }
  }  
  function test() {
	  alert ($('expZOJRecommend').value);
  }
  function showExpHelp() {
	  alert ("Выгружаются карты детей, которым на дату осмотра педиатра не исполнилось 18 лет. Выгружаются все исследования, в которых указана дат осмотра\n"
			  +"Если у ребенка не указан RZ, тип документа (также если тип документа НЕ паспорт или НЕ св-во о рождении), нет актуального полиса ОМС, карта выгружена не будет.\n"
			  +"Поле \"Группа для занятий физ. культурой\" - обязательное\nРезультат анализов - как пример \"Без патологий\"");
  }
  function prepareForm30() {
	  sqlAdd="";
	  sqlAdd += createSqlField ('workFunction', 'edc.workfunction_id');
	  sqlAdd += createSqlField ('dispType', 'edc.dispType_id');
	  sqlAdd += createSqlField ('ageGroup', 'edc.ageGroup_id');
	  sqlAdd += createSqlField ('healthGroup', 'edc.healthGroup_id');
	  
	  $('exportTable').style.display = 'none' ;
	 if ($('createFrom').value!=null&&$('createFrom').value!='') {
		 if ($('createTo').value!=null&&$('createTo').value!='') {
			 sqlAdd += " and edc.createdate between to_date('"+$('createFrom').value+ "','dd.MM.yyyy')"+
			 " and to_date('"+$('createTo').value+"','dd.MM.yyyy')";
		 } else {
			 sqlAdd +=" and edc.createdate >= to_date('"+$('createFrom').value+"','dd.MM.yyyy')"
		 }
	 }
	  for (var i=0; i<document.getElementsByName("typeExport").length;i++) {
		  if (document.getElementsByName("typeExport")[i].checked){
			  if (document.getElementsByName("typeExport")[i].value=="2") {
				  sqlAdd+=" and edc.exportDate is null ";
			  }  
		  }		
	  }
	  for (var i=0; i<document.getElementsByName("expDispAge").length;i++) {
		  if (document.getElementsByName("expDispAge")[i].checked){
			  if (document.getElementsByName("expDispAge")[i].value=="2") {
				  sqlAdd+=" and vedag.code not like '%.%' ";
			  }  
		  }		
	  }
	  var dispType="";
	  for (var i=0;i< document.getElementsByName("expDispType").length;i++) {
		  if (document.getElementsByName("expDispType")[i].checked) {
			  if (document.getElementsByName("expDispType")[i].value=="1") dispType+="'4',";
			  if (document.getElementsByName("expDispType")[i].value=="2") dispType+="'5',";
			  if (document.getElementsByName("expDispType")[i].value=="3") dispType+="'6',";
		  }
	  }
	  if (dispType=="") {
		  alert ("Укажите дип диспансеризации!");
		  return;
	  }
	  
	  
	  sqlAdd +=" and vedsg.code in ("+dispType.substring(0,dispType.length-1)+") ";
	  createForm30();
  }
  
  function createForm30() {
  	if ($('beginDate').value=='' || $('finishDate').value=='') {
    	alert ("Заполните дату начала и окончания!!") ;
    	return;
    }
	 	$('aView').innerHTML="Подождите...";
     ExtDispService.exportOrph($('beginDate').value, $('finishDate').value,"mis_",sqlAdd, 
    		$('expFizGroup').value,$('expHeight').value,$('expWeight').value,
    		$('expHeadsize').value,$('expResearchText').value,$('expZOJRecommend').value,$('expRecommend').value!=""?$('expRecommend').value:"_",$('expDivideNum').value,$('lpu').value, {
    	callback: function(aResult) {
    		
    	 	if (aResult==null)$('aView').innerHTML="Ошибка, обратитесь к разработчикам" ;
    		else {
    			var aData = aResult.split("@");
    			$('aView').innerHTML="<a href='../rtf/"+aData[0]+"''>"+aData[0]+"</a>" ;
    			if (aData[1]!="" && aData[1]!="null" && aData[1]!=null) {
    				$('exportTable').style.display = 'block' ;
	    			aData[1] = aData[1].substring(0,aData[1].length-1);
	    			var rows = aData[1].split("#");
	    			flushTable();
	    			for (var i=0;i<rows.length;i++) {
	    				addRow (rows[i]);
	    			}
    			}
    		}
    	}
    });	 
 
	
    }
  
  function flushTable() {
	  var table = document.getElementById("exportElements");
	  var aRows = table.childNodes;
	  if (aRows.length>1) {
		  var j=aRows.length;
		  for (var i=1;i<j;i++) {
			  table.deleteRow(0);
		  }
	  }
	  
  }
  var firstRow=1;
  function addRow (aRow) {
	  var aData = aRow.split(":"); // ID:fullname:Diagnosis:Comment 
 	
	  var tbody = document.getElementById('exportElements');
    var row = document.createElement("TR");
	//row.id = type+"Element"+num;
    tbody.appendChild(row);
    
    // Создаем ячейки в вышесозданной строке 
    // и добавляем тх 
    var td1 = document.createElement("TD");
 
    var td2 = document.createElement("TD");
    ///td2.colSpan="2";
    var td3 = document.createElement("TD");
    var td4 = document.createElement("TD");
    
	 row.appendChild(td1);
	 row.appendChild(td2);
	 row.appendChild(td3);
	 row.appendChild(td4);
   
    // Наполняем ячейки  
    td1.innerHTML = "<a href='/riams/entityView-extDisp_card.do?id="+aData[0]+"' target='_blank'><span>\t"+aData[0]+"</span></a>";
    td2.innerHTML = "<span> "+aData[1]+"</span>";
  	td3.innerHTML = "<span> "+aData[2]+"</span>";
   	td4.innerHTML = "<span> "+aData[3]+"</span>";
   
  }
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    </script>
    </tiles:put>
</tiles:insert>