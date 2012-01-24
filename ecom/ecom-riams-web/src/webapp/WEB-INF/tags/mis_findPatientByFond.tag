<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>

<style type="text/css">
    #${name}FindPatientByFond {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}FindPatientByFond' class='dialog'>
    <h2>Поиск по базе фонда</h2>
    <div class='rootPane'>
    <h3>Найденные данные по базе фонда</h3>
<form action="javascript:void(0)" id="frmFond" name="frmFond">
    <msh:panel>
    
    	<msh:row>
    		<span id='${name}FindPatientByFondText'/>
    	</msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6" align="center">
          <!--       <input type="button" value='Создать нового пациента' onclick='javascript:next${name}FindPatientByFond()'/> 
          -->
                <input type="button" value='Выделить все' 
                onclick='javascript:checkedAll${name}FindPatientByFond(true)'/>
                <input type="button" value='Отменить выделение' 
                onclick='javascript:checkedAll${name}FindPatientByFond(false)'/>
                <input type="button" value='Обновить данные пациента' 
                onclick='javascript:update${name}FindPatientByFond()'/>
                <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}FindPatientByFond()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}FindPatientByFondInitialized = false ;
     var the${name}FindPatientByFond = new msh.widget.Dialog($('${name}FindPatientByFond')) ;
     // Показать
     function show${name}FindPatientByFond(aText) {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}FindPatientByFondInitialized) {
         	init${name}FindPatientByFond() ;
          }
         $('${name}FindPatientByFondText').innerHTML = aText ;
         the${name}FindPatientByFond.show() ;

     }

     // Отмена
     function update${name}FindPatientByFond() {
        var frm = document.forms['frmFond'] ;
		var fondFiodr = getCheckedRadio(frm,"fondFiodr") ;
		var fondDocument = getCheckedRadio(frm,"fondDocument") ;
		var fondPolicy = getCheckedCheckBox(frm,"fondPolicy","&") ;
		var fondAdr = getCheckedRadio(frm,"fondAdr") ;
		if(+$('id').value>0) {
			PatientService.updateDataByFond(
		     		$('id').value,fondFiodr,fondDocument,fondPolicy,fondAdr
		     		 ,{
		                   callback: function(aString) {
		                      alert("Данные обновлены") ;
		                       window.document.location.reload();
		                    }
		                }
		         ) ;
		} else {
			if (fondFiodr!="") {
				var fiodr = fondFiodr.split("#") ;
				$('lastname').value=fiodr[0] ;
				$('firstname').value=fiodr[1] ;
				$('middlename').value=fiodr[2] ;
				$('birthday').value=fiodr[3] ;
				if (fiodr[4]!=null) $('snils').value=fiodr[4] ;
			}
			var passType = "" ;
			var address = "" ;
			if (fondDocument!="") {
				var doc = fondDocument.split("#") ;
				passType = doc[0] ;
				//$('passportType').value=doc[0] ;//
				$('passportSeries').value=doc[1] ;
				$('passportNumber').value=doc[2] ;
				$('passportDateIssued').value=doc[3] ;
				$('passportWhomIssued').value=doc[4] ;
			}
			if (fondAdr!="") {
				var adr = fondAdr.split("#") ;
				//$('address').value=adr[0] ;//
				$('houseNumber').value=adr[1] ;
				$('houseBuilding').value=adr[2] ;
				$('flatNumber').value=adr[3] ;
				//$('rayon').value=adr[4] ;//
				address=adr[0]+"#"+adr[4]+"#"+adr[5]+"#"+adr[6] ;
				//alert(address);
				PatientService.getInfoVocForFond(
					passType,address,{
						callback: function(aResult) {
							var res = aResult.split('#') ;
							if (res[0]!="") {
								$('passportType').value=res[0] ;
								$('passportTypeName').value=res[1] ;
							}
							if (res[3]&&res[3]!="") {
								$('rayon').value=res[3] ;
								$('rayonName').value=res[4] ;
							}							
							if (address!="") {
								if (res[2]&&res[2]!="") {
									$('address').value=res[2] ;
									reloadAddress() ;
								}
								
							}
							cancel${name}FindPatientByFond() ;
						}
					}
				);
			} else {
				cancel${name}FindPatientByFond() ;
			}
			
			
		}
     }
     // Отмена
     function cancel${name}FindPatientByFond() {
        the${name}FindPatientByFond.hide() ;
     }
     function checkedAll${name}FindPatientByFond(aValue) {
    	 var frm = document.forms['frmFond'] ;
    	setCheckedAllRadio(frm,"fondFiodr",aValue) ;
    	setCheckedAllRadio(frm,"fondDocument",aValue) ;
    	setCheckedAllCheckBox(frm,"fondPolicy",aValue) ;
    	setCheckedAllRadio(frm,"fondAdr",aValue) ;

     }
     

     

     // инициализация диалогового окна
     function init${name}FindPatientByFond() {
     	theIs${name}FindPatientByFondInitialized = true ;
     	
     }
</script>