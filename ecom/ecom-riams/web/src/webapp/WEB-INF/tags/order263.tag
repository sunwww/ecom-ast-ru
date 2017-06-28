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
    <h2>Информация согласно приказу 263</h2>
    <div class='rootPane'>
    
<form action="javascript:void(0) ;" id="formId">
<table width="100%" cellspacing="0" cellpadding="4">
    <tr> 
     <td align="right" width="100">ID:</td>
     <td><input type="text" id="idTxt" maxlength="50" size="20"></td>
    </tr>
    <tr> 
     <td align="right" width="100">Фамилия:</td>
     <td><input type="text" id="fnameTxt" maxlength="50" size="20"></td>
    </tr>
     <tr> 
     <td align="right" width="100">Имя:</td>
     <td><input type="text" id="nameTxt" maxlength="50" size="20"></td>
    </tr>
     <tr> 
     <td align="right" width="100">Отчество:</td>
     <td><input type="text" id="lnameTxt" maxlength="50" size="20"></td>
    </tr>
     <tr> 
     <td align="right" width="100">Дата рождения:</td>
     <td><input type="text" id="birthTxt" maxlength="50" size="20"></td>
    </tr>
     <tr> 
     <td align="right" width="100">Телефон:</td>
     <td><input type="text" id="phoneTxt" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">Пол:</td>
     <td><input type="text" id="sexTxt" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Тип полиса:</td>
     <td><input type="text" id="typepoliceTxt" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Серия:</td>
     <td><input type="text" id="serpolTxt" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Номер:</td>
     <td><input type="text" id="numpolTxt" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">№ напр-я ЛПУ:</td>
     <td><input type="text" id="numpolTxt" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">№ напр-я ФОНД:</td>
     <td><input type="text" id="numfondTxt" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">Дата напр-я:</td>
     <td><input type="text" id="directDateTxt" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Диагноз:</td>
     <td><input type="text" id="diagnosisTxt" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">Направляющее ЛПУ:</td>
     <td><input type="text" id="orderlpuTxt" maxlength="50" size="20"></td>
    </tr> 
    </tr> 
     <tr> 
     <td align="right" width="100">Плановая дата госпитализации:</td>
     <td><input type="text" id="preDateTxt" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <tr> 
     <td align="right" width="100">Принимающее ЛПУ:</td>
     <td><input type="text" id="directlpuTxt" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <tr> 
     <td align="right" width="100">Профиль ЛПУ:</td>
     <td><input type="text" id="profileTxt" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Госпитализирован (дата):</td>
     <td><input type="text" id="datehospTxt" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">с диагнозом</td>
     <td><input type="text" id="d2Txt" maxlength="50" size="20"></td>
    </tr>  
    <tr> 
     <td align="right" width="100">Выписан (дата):</td>
     <td><input type="text" id="disDateTxt" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">Причина отказа:</td>
     <td><input type="text" id="denied" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <tr> 
     <tr> 
     <td></td>
     <td><input type="button" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}CloseDocument()'/></td>
    </tr> 
 </table>     
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}CloseDisDocumentDialogInitialized = false ;
     var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
     // Показать
     
     function show${name}CloseDocument() {
    	 order263();
         theTableArrow = null ; 
         $("${name}Seria").focus() ;

     }
     function order263() {
    		HospitalMedCaseService.sqlorder263(
    				'${param.id}', {
    				callback: function(res) { 
    					if (res!="##") {
    						the${name}CloseDisDocumentDialog.show() ;
    						var aResult = res.split('#') ;  
    						$('idTxt').value=aResult[0];
    						if (aResult[1]!="null") $('fnameTxt').value=aResult[1];
    						if (aResult[2]!="null") $('nameTxt').value=aResult[2];
    						if (aResult[3]!="null") $('lnameTxt').value=aResult[3]; 
    						if (aResult[4]!="null") $('birthTxt').value=aResult[4];
    						if (aResult[5]!="null") $('phoneTxt').value=aResult[5];
    						if (aResult[6]!="null") $('sexTxt').value=aResult[6];
    						if (aResult[7]!="null") $('typepoliceTxt').value=aResult[7];
    						if (aResult[8]!="null") $('serpolTxt').value=aResult[8];
    						if (aResult[9]!="null") $('numpolTxt').value=aResult[9];
    						if (aResult[10]!="null") $('numdirectTxt').value=aResult[10];
    						if (aResult[11]!="null") $('numfondTxt').value=aResult[11];
    						if (aResult[12]!="null") $('directDateTxt').value=aResult[12];
    						if (aResult[13]!="null") $('diagnosisTxt').value=aResult[13];
    						if (aResult[14]!="null") $('orderlpuTxt').value=aResult[14];
    						if (aResult[15]!="null") $('preDateTxt').value=aResult[15];
    						if (aResult[16]!="null") $('directlpuTxt').value=aResult[16];
    						if (aResult[17]!="null") $('profileTxt').value=aResult[17];
    						if (aResult[18]!="null") $('datehospTxt').value=aResult[18];
    						if (aResult[13]!="null") $('d2Txt').value=aResult[13]; 
    						if (aResult[19]!="null") $('disDateTxt').value=aResult[19];  
    						if (aResult[20]!="null") $('denied').value=aResult[20];
    					}
    					else {
    						alert("Данных по 263 приказу по этой госпитализации не найдено!");
    						  //the${name}CloseDisDocumentDialog.hide() ;
    					//	$('formId').style.visibility = "hidden";
    					}
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