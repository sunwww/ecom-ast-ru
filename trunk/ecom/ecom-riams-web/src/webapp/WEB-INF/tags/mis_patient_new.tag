<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="divForButton" required="true" description="div" %>


<%@ attribute name="autoComplitePatient" required="true" description="Дата" %>


<style type="text/css">
    #${name}PatientDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
    #${name}Lastname {
    	 
    }
</style>

<div id='${name}PatientDialog' class='dialog'>
    <h2>Создание новой персоны</h2>
    <div class='rootPane'>
    
<form action="javascript:save${name}Patient()">
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
    		<msh:autoComplete property="${name}Sex"  label="Пол"  vocName="vocSex"/>
    	</msh:row>
    	<msh:row>
    		<msh:autoComplete property="${name}SocialStatus" label="Соц.статус" vocName="vocSocialStatus" fieldColSpan="3" horizontalFill="true"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="${name}Snils" label="СНИЛС" fieldColSpan="3" horizontalFill="true"/>
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
                <input type="button" value='Сохранить' id="${name}Ok"  onclick="javascript:save${name}Patient()"/>
                <input type="button" value='Отменить' id="${name}Cancel" onclick='javascript:cancel${name}Patient()'/>
            </td>
        </msh:row>
        </div>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}PatientDialogInitialized = false ;
     var the${name}PatientDialog = new msh.widget.Dialog($('${name}PatientDialog')) ;

     // Показать
     function show${name}Patient() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}PatientDialogInitialized) {
         	init${name}PatientDialog() ;
          }
         the${name}PatientDialog.show() ;

         $("${name}Lastname").focus() ;
         $("${name}Lastname").select() ;
         

     }

     // Отмена
     function cancel${name}Patient() {
         the${name}PatientDialog.hide() ;
     }

     // Сохранение данных
     function save${name}Patient() {
     	if (checkRequered('${name}Lastname') 
     		&& checkRequered('${name}Firstname')
     		&& checkRequered('${name}Middlename')
     		&& checkRequered('${name}Birthday')
     		&& checkRequered('${name}SocialStatus','${name}SocialStatusName')
     		&& checkRequered('${name}Sex','${name}SexName')
     		)
     		 {
     		
		     	check${name}Patient($('${name}Lastname').value,$('${name}Firstname').value,$('${name}Middlename').value,$('${name}Birthday').value,$('${name}Sex').value,$('${name}SocialStatus').value,$('${name}Snils').value) ;
         }
     }
     function edit${name}Patient() {
     ///alert(2121) ;
		     		 		$('panel${name}').style.display="" ;
		     		 		$('div${name}Button').style.display="" ;
		     		 		$('${name}errorDiv').innerHTML=""
     
     }
     function check${name}Patient(aLastname,aFirstname,aMiddlename,aBirthday, aSex, aSocialStatus,aSnils) {
     	PatientService.getDoubleByFio(
		     		null,aLastname,aFirstname,aMiddlename,aSnils,aBirthday
		     			,'','','javascript:updatePatient'
		     		 ,{
		     		 callback: function(aString) {
		     		 	
		     		 	if (aString==null || aString=="") {
		     		 		add${name}Patient(aLastname,aFirstname,aMiddlename,aBirthday, aSex, aSocialStatus,aSnils) ;
		     		 	} else {
		     		 		$('panel${name}').style.display="none" ;
		     		 		$('div${name}Button').style.display="none" ;
		     		 		$('${name}errorDiv').innerHTML = "<h2>ВОЗМОЖНЫ ДУБЛИ!!!</h2>"
		     		 			+aString
		     		 			+'<br/><i>Выберите необходимую персону или продолжите сохранение новой, нажав кнопку ВСЕ РАВНО СОХРАНИТЬ</i><br/>'
		     		 			+'<button onClick="'
		     		 			+"add${name}Patient('"+aLastname+"','"+aFirstname+"','"+aMiddlename+"','"+aBirthday+"','"+ aSex+"','"+aSocialStatus+"','"+aSnils
		     		 			+"'"+')">Все равно сохранить</button><button onclick="edit${name}Patient()">Изменить данные</button>' ;

		     		 		
		     		 		return false ;
		     		 	}
		     		 }
		     		 }) ;
		     		 //return false ;
     }
     function add${name}Patient(aLastname,aFirstname,aMiddlename,aBirthday, aSex, aSocialStatus, aSnils) {
     	///alert(aLastname+" "+aFirstname+" "+aMiddlename+" "+aBirthday+" "+ aSex+" "+aSocialStatus);
	     PatientService.addPatient(
		     		aLastname,aFirstname,aMiddlename,aBirthday, aSex, aSocialStatus, aSnils
		     		 ,{
			               callback: function(aString) {
			                   	var fio = $('${name}Lastname').value+" "+$('${name}Firstname').value
			     					+" "+ $('${name}Middlename').value+" "+ $('${name}Birthday').value ;
			     				var ind = aString.indexOf("#") ;
			     				updatePatient(aString.substring(0,ind),aString.substring(ind+1)) ;
			     				
		                        cancel${name}Patient() ;
		                    }
		                }
		         ) ;
     }
     function updatePatient(aId,aField) {
     		$('${autoComplitePatient}').value =  aId;
			$('${autoComplitePatient}Name').value = aField ;
			the${name}PatientDialog.hide() ;
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
     function init${name}PatientDialog() {
     	$('${divForButton}').innerHTML="<input type='button' onclick='javascript:show${name}Patient()' value='${title}'/>"
     	new dateutil.DateField($('${name}Birthday')) ;
     	new snilsutil.SnilsField($('${name}Snils'));
     	
         $("${name}Fio").className=$("${name}Fio").className+" upperCase " ;
         $("${name}Lastname").className=$("${name}Lastname").className+" required upperCase " ;
         $("${name}Middlename").className=$("${name}Middlename").className+" required upperCase" ;
         $("${name}Firstname").className=$("${name}Firstname").className+" required upperCase" ;     	
         $("${name}Birthday").className=$("${name}Birthday").className+" required" ;     	
         $("${name}SocialStatusName").className=$("${name}SocialStatusName").className+" required" ;     	
         $("${name}SexName").className=$("${name}SexName").className+" required" ; 
         eventutil.addEnterSupport('${name}Snils', '${name}Ok') ;
         eventutil.addEventListener($('${name}Fio'),'blur',function(){
		  		${name}UpdateFio();
		  	}) ;
     	theIs${name}PatientDialogInitialized = true ;
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
  			return aValue.substring(ind+1) ;
  		}
  	}
</script>