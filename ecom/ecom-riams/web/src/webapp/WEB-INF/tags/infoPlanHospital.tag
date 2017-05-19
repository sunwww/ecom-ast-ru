<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #CloseDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}CloseDisDocumentDialog' class='dialog'>
    <h2>Информация о предварительных госпитализациях</h2>
    <div class='rootPane'>
    
<form action="javascript:void(0) ;" id="formId">
<table id="table" width="100%" cellspacing="0" cellpadding="4" border="1">
    <tr> 
     <td align="center" width="150"><b>Предварительная дата</b></td>
     <td align="center" width="350"><b>Диагноз</b></td>
     <td align="center" width="250"><b>Отделение</b></td> 
     <td align="center" width="180"><b>ФИО врача</b></td> 
    </tr> 
 </table>  
 <table><tr height="15"> <td></td><td></tr> </table>
 <div align="center"><input type="button" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}CloseDocument()'/></div>
 </form>
</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}CloseDisDocumentDialogInitialized = false ;
     var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
     // Показать
     
     function show${name}CloseDocument() {
    	 infoForPlanHosp();
         theTableArrow = null ; 
         $("${name}Seria").focus() ;

     }
     function infoForPlanHosp() { 
    	HospitalMedCaseService.prevPlanHospital(
				'${param.id}', {
				callback: function(res) { 
					var table = document.getElementById("table"); 
					for (var i=table.rows.length-1; i>=1; i--) {
						table.deleteRow(i);
					}
					if (res!="##") { 
						var aR = res.split('!') ;  
						for (var i=0; i<aR.length; i++) {
							var aResult = aR[i].split('#');
							if (aResult[0]!="" && aResult[1]!="" && aResult[2]!="") {  
							var row = document.createElement('tr');  
    						var td1 = document.createElement('td'); td1.appendChild(document.createTextNode(aResult[0]));
    						var td2 = document.createElement('td'); td2.appendChild(document.createTextNode(aResult[1]));
    						var td3 = document.createElement('td'); td3.appendChild(document.createTextNode(aResult[2]));
    						var td4 = document.createElement('td'); td4.appendChild(document.createTextNode(aResult[3]));
    						row.appendChild(td1);row.appendChild(td2);row.appendChild(td3);row.appendChild(td4);
    						table.appendChild(row);
							}  
						}
						the${name}CloseDisDocumentDialog.show() ;
					} 
					else alert('Актуальных предварительных госпитализаций нет!');
				}
			}
		);
    		}
     // Отмена
     function cancel${name}CloseDocument() {
         the${name}CloseDisDocumentDialog.hide() ;
     }
     function ${name}Docum() {
    	 
     }
</script>