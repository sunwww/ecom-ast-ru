<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="divForButton" required="true" description="div" %>


<%@ attribute name="autoCompliteContractPerson" required="true" description="Дата" %>


<style type="text/css">
    #${name}ContractPersonDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
    #${name}Lastname {
    	 
    }
</style>

<div id='${name}ContractPersonDialog' class='dialog'>
    <h2>Создание новой персоны</h2>
    <div class='rootPane'>
    
<form action="javascript:save${name}ContractPerson()">
    <msh:panel styleId="panel${name}" >
    	<msh:row>
    		<msh:textField property="${name}Fio" label="ФИО" fieldColSpan="3" horizontalFill="true"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="${name}Lastname" label="Фамилия" fieldColSpan="3" horizontalFill="true"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="${name}Firstname" label="Имя" fieldColSpan="3" horizontalFill="true"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="${name}Middlename" label="Отчество" fieldColSpan="3" horizontalFill="true"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="${name}Birthday" label="Дата рождения"/>

    	</msh:row>
    	
    </msh:panel>
    	<msh:row>
    		<td colspan="6"> 
    			<div id="${name}errorDiv" style="background-color: #FFF8E7;"></div>
    		</td>
    	</msh:row>
        <div id='div${name}Button'>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Поиск' id="${name}Ok"  onclick="javascript:save${name}ContractPerson()"/>
                <input type="button" value='Отменить' id="${name}Cancel" onclick='javascript:cancel${name}ContractPerson()'/>
            </td>
        </msh:row>
        </div>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/ContractService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}ContractPersonDialogInitialized = false ;
     var the${name}ContractPersonDialog = new msh.widget.Dialog($('${name}ContractPersonDialog')) ;

     // Показать
     function show${name}ContractPerson() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}ContractPersonDialogInitialized) {
         	init${name}ContractPersonDialog() ;
          }
         the${name}ContractPersonDialog.show() ;

         $("${name}Lastname").focus() ;
         $("${name}Lastname").select() ;
         

     }

     // Отмена
     function cancel${name}ContractPerson() {
         the${name}ContractPersonDialog.hide() ;
     }

     // Сохранение данных
     function save${name}ContractPerson() {
     	if (checkRequered('${name}Lastname') 
     		&& checkRequered('${name}Firstname')
     		)
     		 {
		     	check${name}ContractPerson($('${name}Lastname').value,$('${name}Firstname').value,$('${name}Middlename').value,$('${name}Birthday').value) ;
             }
     }
     function edit${name}ContractPerson() {
		     		 		$('panel${name}').style.display="" ;
		     		 		$('div${name}Button').style.display="" ;
		     		 		$('${name}errorDiv').innerHTML=""
     
     }
     function check${name}ContractPerson(aLastname,aFirstname,aMiddlename,aBirthday) {
     	ContractService.findPerson(
		     		aLastname,aFirstname,aMiddlename,aBirthday
		     			,'javascript:updateContractPerson'
		     		 ,{
		     		 callback: function(aString) {
		     		 	
		     		 	if (aString==null || aString=="") {
		     		 		alert("<h2>НЕТ ДАННЫХ!!!</h2>") ;
		     		 		edit${name}ContractPerson() ;
		     		 	} else {
		     		 		$('panel${name}').style.display="none" ;
		     		 		$('div${name}Button').style.display="none" ;
		     		 		$('${name}errorDiv').innerHTML = "<h2>СПИСОК ПЕРСОН!!!</h2>"
		     		 			+aString
		     		 			+'<br/><i>Выберите необходимую персону</i><br/>'
		     		 			+'<button onclick="edit${name}ContractPerson()">Изменить данные</button>' ;

		     		 		
		     		 		return false ;
		     		 	}
		     		 }
		     		 }) ;
     }
     function add${name}ContractPerson(aIdPat,aField) {
     	///alert(aLastname+" "+aFirstname+" "+aMiddlename+" "+aBirthday+" "+ aSex+" "+aSocialStatus);
	     ContractService.addContractPerson(
	    		 aIdPat,aField
		     		 ,{
			               callback: function(aString) {
			            	   var str = aString.split("#") ;
			     				updateContractPerson(str[0],str[1],str[2]) ;
		                        
		                    }
		                }
		         ) ;
     }
     function updateContractPerson(aIdPat,aIdPerCont,aField) {
    	 	if (+aIdPerCont>0) {
    	 		$('${autoCompliteContractPerson}').value =  aIdPerCont;
    			$('${autoCompliteContractPerson}Name').value = "Физ.лицо: "+aField ;
    			the${name}ContractPersonDialog.hide() ;
    	 	} else {
    	 		add${name}ContractPerson(aIdPat,aField) ;
    	 	}
     		
     }
     function checkRequered(aField,aFieldView) {
     	if ($(aField).value=="" || $(aField).value==0) {
	     	alert("Поле дата является обязательным") ;
	     	if (aFieldView==null || aFieldView=="") {
		     	$(aField).focus() ;
		     	$(aField).select() ;
	     	} else {
		     	$(aFieldView).focus() ;
		     	$(aFieldView).select() ;
	     	}
     		return false ;
     	} 
     		return true
     	
     }

     // инициализация диалогового окна
     function init${name}ContractPersonDialog() {
     	$('${divForButton}').innerHTML="<input type='button' onclick='javascript:show${name}ContractPerson()' value='${title}'/>"
     	new dateutil.DateField($('${name}Birthday')) ;
     	//new snilsutil.SnilsField($('${name}Snils'));
     	
         $("${name}Fio").className=$("${name}Fio").className+" upperCase " ;
         $("${name}Lastname").className=$("${name}Lastname").className+" required upperCase " ;
         $("${name}Middlename").className=$("${name}Middlename").className+" required upperCase" ;
         $("${name}Firstname").className=$("${name}Firstname").className+" required upperCase" ;     	
         $("${name}Birthday").className=$("${name}Birthday").className+" required" ;     	
         eventutil.addEnterSupport('${name}Birthday', '${name}Ok') ;
         eventutil.addEventListener($('${name}Fio'),'blur',function(){
		  		${name}UpdateFio();
		  	}) ;
     	theIs${name}ContractPersonDialogInitialized = true ;
     }
     function ${name}UpdateFio() {
     	var lfm=$('${name}Fio').value ;
  		var lfm = ${name}Subvalue(lfm,' ','${name}Lastname');
  		var lfm = ${name}Subvalue(lfm,' ','${name}Firstname');
  		var lfm = ${name}Subvalue(lfm,' ','${name}Middlename');
     }
     function ${name}Subvalue(aValue,aDel,aField) {
  		var ind = aValue.indexOf(aDel) ;
  		if (ind==-1) {
  			if ($(aField).value=="") $(aField).value=aValue ;
  			return "" ;
  		} else {
  			if ($(aField).value=="") $(aField).value=aValue.substring(0,ind) ;
  			return aValue.substring(ind+1).trim() ;
  		}
  	}
</script>