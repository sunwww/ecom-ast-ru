 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">            
            .protocols {
				left:0px;  width:99%; 
				top:0px;  height:55em;
			}
			#epicriPanel {
			width:100%;
			}
			.record {
			width:100%;
			}
        </style>

    </tiles:put>
  <tiles:put name="side" type="string">
	<msh:ifFormTypeIsNotView formName="preg_childBirthForm">
    	<tags:templateProtocol idSmo="preg_childBirthForm.medCase" version="Visit" name="tmp" property="histology" voc="protocolVisitByPatient"/>
    </msh:ifFormTypeIsNotView>  
    <msh:ifFormTypeIsView formName="preg_childBirthForm" guid="0908a638-fd02-4b94-978b-18ab86829e08">
      <msh:sideMenu title="Роды" guid="bc6ceef3-4709-47d9-ba37-d68540cffc61">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_childBirth" name="Изменить" roles="/Policy/Mis/Pregnancy/ChildBirth/Edit" guid="a8d1a1fa-aa31-408a-b1f6-6b9ba1ff18e8" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_childBirth" name="Удалить" roles="/Policy/Mis/Pregnancy/ChildBirth/Delete" guid="91460b8b-80a7-46b3-bc95-a53cd320f687" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="3f5cf55a-2ae6-4367-b9b9-1ce75e0938c4">
         <mis:sideLinkForWoman roles="/Policy/Mis/Pregnancy/History/View" classByObject="MedCase" id="${param.medcase}"
     	action="/javascript:printPregHistory()" name="Истории родов" title="Печать истории родов"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Течение родов
    	  -->
   <ecom:webQuery isReportBase="${isReportBase}" name="dead_born_sql" nativeSql="select id,name from VocLiveBorn where code='2'"/>
   <%
   ActionUtil.getValueByList("dead_born_sql", "dead_born", request) ;
 	String dead_born = (String)request.getAttribute("dead_born") ;
 	request.setAttribute("dead_born", dead_born);
   %>
    <msh:form action="/entityParentSaveGoView-preg_childBirth.do" defaultField="durationPregnancy" guid="93666922-7bed-42a7-be5e-b2d52e41d39b">
      <msh:hidden property="id" guid="2821496c-bc8e-4cbe-ba14-ac9a7f019ead" />
      <msh:hidden property="medCase" guid="2104232f-62fa-4f0b-84de-7ec4b5f306b3" />
      <msh:hidden property="saveType" guid="3ec5c007-f4b1-443c-83b0-b6d93f55c6f2" />
      <msh:hidden property="newBornsInfo"  guid="3ec5c007-f4b1-443c-83b0-b6d93f55c6f2" />
      <input type='hidden' name='deadBorn' id="deadBorn" value='${dead_born}' guid="3ec5c007-f4b1-443c-83b0-b6d93f55c6f2" />
      <msh:panel guid="0a4989f1-a793-45e4-905f-4ac4f46d7815">
        <msh:row guid="4bbea36b-255f-441f-8617-35cb54eaf9d0">
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Первый период родовой деятельности" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="durationPregnancy" label="Срок беременности (нед.)"/>
        	<msh:autoComplete property="whereBirthOccurred" label="Роды произошли" horizontalFill="true" vocName="vocWhereBirthOccurred" fieldColSpan="1"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="whereBirthOccurredOther" label="Где" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="childBirthType" label="Роды" vocName="vocChildBirth" horizontalFill="true"/>
        	<msh:autoComplete property="emergency" parentAutocomplete="childBirthType" label="Показания" vocName="vocChildEmergency" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="pangsStartDate" label="Дата начала родов" guid="eec0820e-6990-40f8-aa50-f48b8034d7f3" />
          <msh:textField property="pangsStartTime" label="Время" guid="360cc928-1178-46ee-b5c0-5710ff873e3c" />
        </msh:row>
        <msh:row guid="84d7a910-53ad-48b2-8582-294aef3497e6">
          <msh:autoComplete property="watersPrematurity" vocName="vocBirthWatesPrematurity" label="Преждевременность отхождения вод" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="1e548b4d-8b56-42e2-9b51-c116784c92a3">
          <msh:textField property="watersDate" label="Дата отхождения вод" guid="34fc13d6-0618-42ac-96e8-d8717302f0bd" />
          <msh:textField property="watersTime" label="Время" guid="ffcc4f9d-05fc-4b65-8def-78adc1c0b908" />
        </msh:row>
        <msh:row>
          <msh:textField property="upperWatersAmount" label="Кол-во передних вод (мл)"  />
          <msh:textField property="downWatersAmount" label="Кол-во задних вод (мл)"  />
        </msh:row>
        <msh:row>
          <msh:textField property="upperWatersQuality" label="Качество передних вод"  />
          <msh:textField property="downWatersQuality" label="Качество задних вод"  />
        </msh:row>
        <msh:row styleId="rwSam1"><msh:separator label="Второй период родовой деятельности" colSpan="4"/>
        </msh:row>
        <msh:row styleId="rwSam6">
          <msh:textField property="fullOpenDate" label="Дата полного открытия" guid="10d048cc-6dd3-4e6c-8ef2-3b580a02ec7e" />
          <msh:textField property="fullOpenTime" label="Время" guid="683fa920-9a2c-4f8a-a223-4e59c6f55f85" />
        </msh:row>
        <msh:row styleId="rwSam2">
          <msh:textField property="travailStartDate" label="Дата начала потуг" guid="10d048cc-6dd3-4e6c-8ef2-3b580a02ec7e" />
          <msh:textField property="travailStartTime" label="Время" guid="683fa920-9a2c-4f8a-a223-4e59c6f55f85" />
        </msh:row>

       <msh:row>
        	<msh:separator label="Новорожденные" colSpan="10"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="newBornAmount" fieldColSpan="1" label="Кол-во плодов" />
        </msh:row>
        <msh:row>
        	<td colspan='10'>
        <table id="otherNewBorns">
        </table>
        </td>
        </msh:row>
        

        <msh:row styleId="rwSam7">
        	<msh:separator label="Третий период родовой деятельности" colSpan="9" guid="32379f0e-c12d-43ea-b5b7-8e29e524c0ff" />
        </msh:row>
        <msh:row styleId="rwKes1">
        	<msh:separator label="Окончание родовой деятельности" colSpan="9" guid="32379f0e-c12d-43ea-b5b7-8e29e524c0ff" />
        </msh:row>
         <msh:row>
          <msh:textField property="birthFinishDate" label="Дата окончания родов" guid="4ca102af-a7eb-4b61-95f3-8eba8ae25ac6" />
          <msh:textField property="birthFinishTime" label="Время" guid="bd37fba9-cb02-4966-9301-1a03d62e551e" />
        </msh:row>
        <msh:row  styleId="rwSam8">
          <msh:autoComplete label="Послед выделился" property="placentaSeparation" vocName="vocPlacentaSeparation" />
          <msh:textField property="placentaMinute" label="Через (мин)"/>
        </msh:row>
        <msh:row  styleId="rwSam9">
          <msh:textField property="placentaWeight" label="Вес плаценты (гр.)" guid="0b31bed6-d06e-48c0-aaac-b16e54959b53" />
          <msh:textField property="placentaSize" label="Размеры плаценты" guid="d245643c-f2ba-4e98-a764-0a3c51792866" />
        </msh:row>
        <msh:row styleId="rwSam10">
          <msh:autoComplete fieldColSpan="3" vocName="vocPlacentaIntegrity" property="placentaIntegrity" label="Детское место" guid="bcac0c74-7fb5-49f8-beaa-917a584a812e" horizontalFill="true" />
        </msh:row>
        <msh:row  styleId="rwSam11">
          <msh:autoComplete property="membranesIntegrity" label="Целостность оболочек" vocName="vocFetalMembranesIntegrity" fieldColSpan="3" horizontalFill="true" guid="308af9c9-080f-486f-af92-d2be2d0b6e0d" />
        </msh:row>
        <msh:row>
        	<msh:textField property="cordLength" label="Длина пуповины"/>
        </msh:row>
        <msh:row  styleId="rwSam13">
          <msh:textArea rows="3" property="placentaFeatures" label="Особенности плаценты" guid="f817f39f-82a1-4dca-b2e8-136f15069658" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
         <msh:row  styleId="rwSam14">
          <msh:textField property="hemorrhageVolume" label="Объем кровопотери (мл)" guid="e613e17e-0fad-4dcb-bca5-0261d03cd28c" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="anesthesiaMedication" label="Медикаментозное обезболивание" vocName="vocChildAnesthesiaMedication" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="anesthesiaMedicationEffect" label="Эффект" vocName="vocChildAnesthesiaMedicationEffect" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="placentaHistologyOrder" label="Направление плаценты на гистологию" guid="bfc88e8a-d54c-48f9-87e9-6740779e3287" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="histology" fieldColSpan="3" horizontalFill="true" label="Гистология плацента" vocName="vocHistologyResult"/>
        </msh:row>
        <msh:row styleId="rwSam3">
        <msh:separator label="Общая продолжительность родов" colSpan="9"/>
        </msh:row>
        <msh:row  styleId="rwSam4">
          <msh:textField property="period1Duration" label="1 периода (час)" guid="1fc59e7b-1423-4249-9bec-e7131f2221e0" />
          <msh:textField property="period2Duration" label="2 периода (час)" guid="b8f27f6b-bed2-43a0-b0a3-e3eed5f58544" />
        </msh:row>
        <msh:row  styleId="rwSam5">
          <msh:textField property="period3Duration" label="3 периода (мин)" guid="79931dee-fefe-480e-b976-f39afd0d003c" />
        </msh:row>
       
        <msh:row guid="21aa5b55-367d-404d-b8c5-03ce63e32329">
          <msh:autoComplete property="childTook" label="Ребенка принял" vocName="workFunction" fieldColSpan="3" horizontalFill="true" guid="9429d5cb-cf05-42e1-8df8-ec5545f3a198" />
        </msh:row>
        <msh:row guid="21aa5b55-367d-404d-b8c5-03ce63e32329">
          <msh:autoComplete property="placentaInspector" label="Послед осматривал" vocName="workFunction" fieldColSpan="3" horizontalFill="true" guid="9429d5cb-cf05-42e1-8df8-ec5545f3a198" />
        </msh:row>
        

        <msh:ifFormTypeIsView formName="preg_childBirthForm">
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>
        </msh:ifFormTypeIsView>
        <msh:submitCancelButtonsRow colSpan="3"  guid="bd5bf27d-bcd4-4779-9b5d-1de22f1ddc68" functionSubmit="this.form.action='entityParentSaveGoView-preg_childBirth.do';checkForm();"/>
      </msh:panel>
    </msh:form>
     <tags:preg_childBirthYesNo name="DeadBorn" field="DeadBeforeLabors"/>
    <msh:ifFormTypeIsView formName="preg_childBirthForm" guid="07462ced-904f-4485-895c-0107f05b5d8d">
   
      <msh:ifInRole roles="/Policy/Mis/NewBorn/View" guid="187f5083-94a7-42fd-a428-7f9d4720bfd1">
        <%-- <ecom:parentEntityListAll attribute="newBorns" formName="preg_newBornForm" guid="35b71f42-e1fc-40f2-93e5-0908ea385878" /> --%>
        <ecom:webQuery name = "newBorns" nameFldSql="newBorns_sql" nativeSql="select nb.id as id, nb.birthDate as bDate, nb.birthTime as bTime, vs.name as sexName, pat.firstname as patName
         from newBorn nb left join patient pat on pat.id=nb.patient_id
         left join vocSex vs on vs.id=nb.sex_id
         where nb.childBirth_id='${param.id}'" />
        <msh:tableNotEmpty name="newBorns" guid="bd28e321-5e07-4e52-95dc-9851c96a0007">
          <msh:section title="Данные о новорожденных" guid="7aee16b5-d063-4868-8891-313de24ca013">
            <msh:table name="newBorns" action="entityParentView-preg_newBorn.do" idField="1" guid="e5ff27a4-e8ae-44ef-a20f-7a6f71f42f3a">
              <msh:tableColumn columnName="Дата рождения" property="2" guid="724d2c83-221e-4a44-9144-5346fa8fefd2" />
              <msh:tableColumn columnName="Время рождения" property="3" guid="17991748-77de-4b16-8c94-740bbfa10e7a" />
              <msh:tableColumn columnName="Пол" property="4" guid="17991748-77de-4b16-8c94-740bbfa10e7a" />
              <msh:tableColumn columnName="Имя" property="5" guid="17991748-77de-4b16-8c94-740bbfa10e7a" />
            </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_childBirthForm" guid="d16befe8-59da-47d9-9c54-ee0d13e97be2" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <script type="text/javascript">
  function printPregHistory() {
	  document.location='print-preghistory.do?s=HospitalPrintService&m=printPregHistoryByMC&id='+$('medCase').value;
  }
  </script>
  <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
  <script type="text/javascript">
  	var isSaveNewBorns ;
  	var old_action = document.forms["mainForm"].action ; 
  	
  	//document.forms["mainForm"].action="javascript:checkForm()" ; 
  	
  	
  </script>
  <msh:ifFormTypeAreViewOrEdit formName="preg_childBirthForm">
  <script type="text/javascript">
  isSaveNewBorns=0;
  </script>
  </msh:ifFormTypeAreViewOrEdit>
  <msh:ifFormTypeIsCreate formName="preg_childBirthForm">
  <script type="text/javascript">
  document.forms["mainForm"].action="javascript:checkDeadBorn()" ;
  isSaveNewBorns=1 ;
  </script>
  </msh:ifFormTypeIsCreate>
  <script type="text/javascript">
   
  function checkForm() {
		if (isSaveNewBorns) {
			try {
			createOtherNewBorns() ;
			document.forms["mainForm"].action=old_action ;
			document.forms["mainForm"].submit() ;
			
			} catch(e) {
				alert(e) ;
				$('submitButton').disabled=false ;
			}
		} else {
			document.forms["mainForm"].action=old_action ; 
			document.forms["mainForm"].submit() ;
		}
	}
  childBirthTypeAutocomplete.addOnChangeCallback(function() {
	  checkChildBirth() ;
	  $('emergency').value='' ;
	  $('emergencyName').value='' ;
	 });
  function checkChildBirth() {
	  if (+$('childBirthType').value>0) {VocService.getCodeById("vocChildBirth",$("childBirthType").value,"code",{
			
			callback: function(aResult) {
				//alert(aResult) ;
				var trViewK = true ;
				var trViewS = true ;
				if (aResult!=null && aResult=="KES") {trViewS=false ;}else{trViewK=false ;}
				for (var i=1;i<15;i++) {
					showRow("rwSam"+i,trViewS) ;
				}
				for (var i=1;i<2;i++) {
					showRow("rwKes"+i,trViewK) ;
				}
				
			}
		} ) ;}else{
			for (var i=1;i<15;i++) {
				showRow("rwSam"+i,false) ;
			}
			for (var i=1;i<2;i++) {
				showRow("rwKes"+i,false) ;
			}
		}
  }
  function showRow(aRowId, aCanShow ) {
		
		try {
			$(aRowId).style.display = aCanShow ? 'table-row' : 'none' ;
		} catch (e) {
			try{
			$(aRowId).style.display = aCanShow ? 'block' : 'none' ;
			}catch(e) {}
		}	
	}
  checkChildBirth() ;
  </script>
  </msh:ifFormTypeIsNotView>
  <msh:ifFormTypeIsCreate formName="preg_childBirthForm">
  	<script type="text/javascript">
	var theFld = [['Дата рождения','BirthDate',3,1,1,'birthDate',0,'',0,'pangsStartDate']
	,['Время','BirthTime',4,3,1,'birthTime',0,'',0]
	,['Родился','LiveBorn',1,7,1,'liveBorn',0,'VocLiveBorn',1]
	,['Родился','PartBodyBorn',1,5,1,'partBodyBorn',0,'VocPartBodyBorn',1]
	,['Пол','Sex',1,5,1,'sex',0,'VocSex',1]
	,['Масса','BirthWeight',2,7,1,'birthWeight',0,'',1]
	,['Рост','BirthHeight',2,9,1,'birthHeight',0,'',0]
	,['Окружность головы','HeadCircle',2,11,1,'headCircle',0,'',0] 
	,['Плеч','ShouldersCircle',2,11,0,'shouldersCircle',0,'',0] 
	,['Груди','BreastCircle',2,11,0,'breastCircle',0,'',0] 
	,['Обвитие','Entanglement',1,11,1,'entanglement',0,'VocBirthEntanglement',1] 
	,['Кол-во обвитий','EntanglementMultiplicity',1,11,0,'entanglementMultiplicity',0,'VocBirthEntanglementMultiplicity',1] 
	,['Где','WhereEntanglement',1,11,0,'whereEntanglement',0,'VocBirthWhereEntanglement',1]
	,['Отделение','Department',1,11,1,'department',0,"MisLpu where IsNewBornDep='1'",1]
	,['Зрелость','Maturity',1,2,1,'maturity',0,'VocNewBornMaturity',1]
    //,['Акушерский диагноз','Idc10',1,11,2,'idc10',1,'VocIdc10',1]
	,['','DeadBeforeLabors',6,2,0,'deadBeforeLabors',0,'',1]
	] ;
	var vocList = "" ;
	var vocValueList  ;


	for (var i=0; i<theFld.length;i ++) {
		if (theFld[i][7]!="") {
			if (vocList!="") vocList+="," ;
			vocList+=theFld[i][7] ;
		}
	}
	onload=function(){
	VocService.getAllValueByVocs(vocList,{
	
	callback: function(aResult) {
        vocValueList=JSON.parse(aResult) ;
        //$('whereBirthOccurredOther').value=aResult;
	  		if (+$('newBornAmount').value<1) {
	  			$('newBornAmount').value="1";
	  			
	  		}
	  		changeBorn(+$('newBornAmount').value) ;
	 		if ($('newBornsInfo').value!='') {
	 			//alert ("Info = "+$('newBornsInfo').value);
	               var addRowF="";
	               var ind_f=0 ;
	         		for (var i=0;i<theFld.length;i++) {
	         			addRowF+="ar["+(ind_f++)+"],";
	         			if (theFld[i][2]==1) {
	         				addRowF+="ar["+(ind_f++)+"],"
	         			}
	         		}
	       		//alert ("addRF = "+addRowF);
	         	    fillRows();
	         	}

	    
	  	var oldValueAmount =+$('newBornAmount').value;
	  	eventutil.addEventListener($('newBornAmount'),'blur',function(){
	  		if (+oldValueAmount!=$('newBornAmount').value) {
	  			changeBorn(+$('newBornAmount').value) ;
	  			oldValueAmount=$('newBornAmount').value ;
	  		}
	  	}) ;

	}} ) }
  	
	
  	function changeBorn(aNewValue) {
  		var borns = document.getElementById('otherNewBorns').childNodes.length-1;
  		if (borns<aNewValue) {
				addRow() ;
				borns++ ;
				//alert(borns+"---"+aNewValue+"add")
				changeBorn(aNewValue) ;
		} else if (borns>aNewValue) {
			//alert(borns+"---"+aNewValue+"del")
				deleteLastNewBorn() ;
			borns--;
				//changeBorn(aNewValue) ;
		}
		if (borns!=aNewValue) changeBorn(aNewValue);
	}
  	
  	/*function addNewBorn(check) {
       /* var addRowF="";
  		for (var i=0;i<theFld.length;i++) {
  			var fld_i = theFld[i] ;
				eval("var "+fld_i[1]+"=$('"+fld_i[5]+"').value;");
  			var fld_i = theFld[i] ;addRowF+=fld_i[1]+","
  			if (fld_i[2]==1) {
  				eval("var "+fld_i[1]+"Name=$('"+fld_i[5]+"Name').value;");
      			addRowF+=fld_i[1]+"Name," ;
  			}
  		}
		addRowF=addRow.length>0?addRowF.trim().substring(0,addRowF.length-1):"";
       addRow ();
  		//var isCheckReq=true ;
        /*if (isCheckReq) {
        	var servs = document.getElementById('otherNewBorns').childNodes;
              var l = servs.length;
              for (var i=1; i<l;i++) {
            	  var isCheckDouble = false;
            	 if (isCheckDouble) {
             		 if (+check!=1) {
             			 if (confirm("Такая услуга уже зарегистрирована. Вы хотите ее заменить?")) {
             			var node=servs[i];node.parentNode.removeChild(node);
             		 } else {
              			return;
              		 } 
             		} else {return;}
                }                 
             }
        		for (var i=0;i<theFld.length;i++) {
        		}
        }
        eval(addRowF) ;
  		for (var i=0;i<theFld.length;i++) {
  			var fld_i = theFld[i] ;
  			if (fld_i[6]==1) {
  				eval("$('"+fld_i[5]+"').value='';");
      			if (fld_i[2]==1) {
      				eval("$('"+fld_i[5]+"Name').value='';");
      			}
  			}
  		}
     }
    */
  	function checkDeadBorn() {
    	var borns = document.getElementById('otherNewBorns').childNodes;
    	var ret = 0;
  		for (var i=1;i<borns.length;i++) {
  			for (var ii=0;ii<theFld.length;ii++) {
  				
  				var val
	  			if (+theFld[ii][2]==1) {
	  				val = getCheckedRadio(document.forms["mainForm"],theFld[ii][1]+(i)) ;
	  				
	  				if ((theFld[ii][1]=='LiveBorn') &&($('DeadBeforeLabors'+i).value==null || $('DeadBeforeLabors'+i).value=='')) {
	  					//alert ("chDB "+theFld[ii][1] +" <==> "+$('DeadBeforeLabors'+i).value);
	  					if (val==$('deadBorn').value) {
	  						ret=1;
	  						showDeadBornCreateType(i, "Новорожденный "+i+" умер до начала родовой деятельности?");
	  					}	  					
	  				}
	  			}
  			}
  			
	  		
  		}
  		if (ret==0) {
  		checkForm ();	
  		}
    }
    // Заполняем данные по новорожденному, если сохранение не удалось 

    function fillRows() {
    	if ($('newBornsInfo').value !=null && $('newBornsInfo').value !='') {
    		var borns = $('newBornsInfo').value.split('@');
    		for (var i=0;i<borns.length;i++) {
    			var born = borns[i].split('#');
    			for (var ii=0;ii<theFld.length;ii++) {
    				if (+theFld[ii][2]==1) {
    					setCheckedRadio(theFld[ii][1]+(i+1),born[ii]);
    				} else {    					
    					$(theFld[ii][1]+(i+1)).value = born[ii];
    				}
    			}
    			var index=i+1;
                var t = new msh_autocomplete.Autocomplete();
                t.setUrl('simpleVocAutocomplete/vocIdc10');
                t.setIdFieldId('Idc10' + index);
                t.setNameFieldId('Idc10' + index + 'Name');
                t.setDivId('Idc10' + index + 'Div');
                t.build();
                //$('currentIndexOdNewBorn').value=i;
                t.addOnChangeCallback(closure(index));
                if (((document.getElementById('LiveBorn' + index)).checked)==false) {
                    var idc10div = document.getElementById('divIdc10' + index);
                    if (idc10div != null) {
                        idc10div.removeAttribute('hidden');
                    }
                }
    		}
    	}
    }
    
    function setCheckedRadio (aField, aValue) {    	
    	    var chk =  document.getElementsByName(aField) ;
    	    for (var i=0;i<chk.length;i++) {
    	    	if (chk[i].value ==+aValue) {
    	    		chk[i].checked='checked';
    	    	}
    	    }
    }
    function createOtherNewBorns() {
  		var borns = document.getElementById('otherNewBorns').childNodes;
  		var str = ""; $('newBornsInfo').value='';
  		for (var i=1;i<borns.length;i++) {
  			for (var ii=0;ii<theFld.length;ii++) {
  				var val
	  			if (+theFld[ii][2]==1) {
	  				val = getCheckedRadio(document.forms["mainForm"],theFld[ii][1]+(i)) ;
	  			} else {
	  				val = $(theFld[ii][1]+(i)).value ;
	  			}
  				if (theFld[ii][4]&&val=="") throw "Поле обязательное для заполнения: "+theFld[ii][0] ;
	  			str+=val+"#";
  			}
  			//alert(currentValue[i - 1]);
            //alert((document.getElementById('LiveBorn' + i)).checked);  //false if 2!!
  			if (((document.getElementById('LiveBorn' + i)).checked)==false) {  //если мёртвый, то диагноз+наименование
                var idc10Field = document.getElementById('Idc10' + i/* + 'Name'*/);
                var textField = document.getElementById('2IdcText' + i);
                if (textField != null && idc10Field != null) {
                    str += idc10Field.value + "#";
                    str += textField.value + "#";
                }
            }
            /*else {
                str += "#";
                str += "#";
            }*/
            str=str.length>0?str.trim().substring(0,str.length-1):"";
	  		str+="@" ;
  		}
  		str=str.length>0?str.trim().substring(0,str.length-1):"";
  		//alert(str);
  		$('newBornsInfo').value=str;
  	}
  	// 0. наименование 1. Наим. поля в функции 2. autocomplete-1,textFld-2 ,dateFld-3,timeFld-4
  	// 3. Номер node в добавленной услуге 4. Обяз.поля да-1 нет-2 
  	// 5. наим. поля в форме 6. очищать поле в форме при добавление да-1, нет-0 ,7-справочник
  	var fldIndexRow = 0;
  	function deleteLastNewBorn() {
  		var node = document.getElementById('otherNewBorns').lastChild ;
  		node.parentNode.removeChild(node);
  	}
  	function addRow () {
  		var cmd = "" ;
  		for (var i=0;i<theFld.length;i++) {
  			cmd += "a"+theFld[i][1]+"=" ;  
  			if (theFld[i].length>9 &&theFld[i][9]!="") {
  				
  				cmd += "$('"+theFld[i][9]+"').value" ;
  				if (theFld[i][2]==1) {
  					cmd += ";a"+theFld[i][1]+"Name=" ; 
  					cmd += "$('"+theFld[i][9]+"').value" ;
  				}
  			} else {
  				cmd += "''" ;
  				if (theFld[i][2]==1) {
  					cmd += ";a"+theFld[i][1]+"Name=''" ;
  				}
  			}
  			cmd+=";" ;
  		}
  		eval(cmd) ;
  		var table = document.getElementById('otherNewBorns');
  		var row = document.createElement('TR');
  		var td = document.createElement('TD');
  		var tdDel = document.createElement('TD');
  		table.appendChild(row);
  		row.appendChild(td);
  		var txt ="" ;
  		
  		var js="" ;
  		var idChild=document.getElementById('otherNewBorns').children.length ;
  		txt+="<h3>Новорожденный " +idChild+"</h3>";
  		for (var i=0;i<theFld.length;i++) {

  			var fld_i = theFld[i] ;//alert(fld_i);
  			if (fld_i[8]==1) {txt+="<br/>";}
  			if (fld_i[2]==1) {
  			    txt+=fld_i[0]+": ";
                for (var ind=0;ind<vocValueList.vocs.length;ind++) {
                    var voc = vocValueList.vocs[ind] ;
                    if (voc.name==fld_i[7]) { //если справочник
                        //if (fld_i[5]!='idc10') {
                            if (fld_i[4] == 0) txt += " <input type='radio' name='" + fld_i[1] + idChild + "' id='" + fld_i[1] + idChild + "' value=''>не выбрано";
                            for (var ind1 = 0; ind1 < voc.values.length; ind1++) {
                                var vocVal = voc.values[ind1];
                                txt += " <input type='radio' name='" + fld_i[1] + idChild + "' id='" + fld_i[1] + idChild + "' value='" + vocVal.id + "' onclick=handleClick(this,'"+idChild+"')>" + vocVal.name;
                            }
                        break ;
                    }
                }
  			} else if (fld_i[2]==2) {
  				txt+=" "+fld_i[0]+": <input type='text' size='5' name='"+fld_i[1]+idChild+"' id='"+fld_i[1]+idChild+"' value='"+eval("a"+fld_i[1])+"'>"
  			} else if (fld_i[2]==3) {
  				txt+=" "+fld_i[0]+": <input type='text' size='10' name='"+fld_i[1]+idChild+"' id='"+fld_i[1]+idChild+"' value='"+eval("a"+fld_i[1])+"'>"
  				js += "new dateutil.DateField($('"+fld_i[1]+idChild+"')) ;"
  			} else if (fld_i[2]==4) {
  				txt+=" "+fld_i[0]+": <input type='text' size='5' name='"+fld_i[1]+idChild+"' id='"+fld_i[1]+idChild+"' value='"+eval("a"+fld_i[1])+"'>"
  				js += "new timeutil.TimeField($('"+fld_i[1]+idChild+"')) ;"
  			} else if (fld_i[2]==6) {
  				txt+=" "+fld_i[0]+": <input type='hidden'  name='"+fld_i[1]+idChild+"' id='"+fld_i[1]+idChild+"' value='"+eval("a"+fld_i[1])+"'>"
  			}
  		}
  		txt+="<div hidden id='divIdc10"+idChild+"'><label id='IdcLabel"+idChild+"' for='IdcName'>Акушерский диагноз:</label></td>"+
            "<div colspan='1' class='Idc10'><div><input size='1' name='Idc10"+idChild+"' "+
            "id='Idc10"+idChild+"' type='hidden'><input autocomplete='off' title='Idc10' name='Idc10"+idChild+"Name' id='Idc10"+idChild+"Name'"+
            " size='10' class='autocomplete horizontalFill' type='text'><div style='visibility: hidden; display: none;'"+
            " id='Idc10"+idChild+"Div'></div></div>" +
            "<label id='2IdcLabel"+idChild+"' for='IdcName'>Наименование:</label>" +
            "<input type='text' size='10' style='width: 400px;' name='2IdcLabel"+idChild+"' id='2IdcText"+idChild+"'></div>";
  		td.innerHTML=txt;
  		row.appendChild(tdDel);
  		eval(js) ;
  	}
    var closure = function(i) {
        return function() {
            var idc10Field=document.getElementById('Idc10'+i+'Name');
            var textField=document.getElementById('2IdcText'+i);
            if (textField!=null && idc10Field!=null) {
                textField.value=idc10Field.value;
            }
        };
    };
    var currentValue = [$('newBornAmount').value];
    function handleClick(myRadio,i) {
       // alert(myRadio.getAttribute("id").indexOf("LiveBorn"));
        if (myRadio.getAttribute("id").indexOf("LiveBorn") != -1) {
            if (myRadio.value == 2 && myRadio.value != currentValue[i - 1]) {
                var t = new msh_autocomplete.Autocomplete();
                t.setUrl('simpleVocAutocomplete/vocIdc10');
                t.setIdFieldId('Idc10' + i);
                t.setNameFieldId('Idc10' + i + 'Name');
                t.setDivId('Idc10' + i + 'Div');
                t.build();
                //$('currentIndexOdNewBorn').value=i;
                t.addOnChangeCallback(closure(i));
                var idc10div = document.getElementById('divIdc10' + i);
                if (idc10div != null) {
                    idc10div.removeAttribute('hidden');
                }
                currentValue[i - 1] = myRadio.value;
            }
            else if (myRadio.value == 1 && myRadio.value != currentValue[i - 1]) {
                var idc10div = document.getElementById('divIdc10' + i);
                if (idc10div != null) {
                    idc10div.setAttribute('hidden', true);
                }
                currentValue[i - 1] = myRadio.value;
            }
        }
    }
  	</script>
  </msh:ifFormTypeIsCreate>
  <msh:ifFormTypeIsNotView formName="preg_childBirthForm">
    	<script type="text/javascript">
    	var isChangeSizeEpicrisis=1 ;
		function changeSizeEpicrisis() {
			if (isChangeSizeEpicrisis==1) {
				Element.addClassName($('histology'), "protocols") ;
				if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Уменьшить' ;
				isChangeSizeEpicrisis=0 ;
			} else {
				Element.removeClassName($('histology'), "protocols") ;
				if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Увеличить' ;
				isChangeSizeEpicrisis=1;
			}
		}
		eventutil.addEventListener($('histology'), "dblclick", 
	  		  	function() {
					changeSizeEpicrisis() ;
	  		  	}) ;
    	</script>
    	</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>