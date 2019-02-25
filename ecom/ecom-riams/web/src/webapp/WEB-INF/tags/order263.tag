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
     <td><input type="text" id="idTxt${name}" maxlength="50" size="20"></td>
    </tr>
    <tr> 
     <td align="right" width="100">Фамилия:</td>
     <td><input type="text" id="fnameTxt${name}" maxlength="50" size="20"></td>
    </tr>
     <tr> 
     <td align="right" width="100">Имя:</td>
     <td><input type="text" id="nameTxt${name}" maxlength="50" size="20"></td>
    </tr>
     <tr> 
     <td align="right" width="100">Отчество:</td>
     <td><input type="text" id="lnameTxt${name}" maxlength="50" size="20"></td>
    </tr>
     <tr> 
     <td align="right" width="100">Дата рождения:</td>
     <td><input type="text" id="birthTxt${name}" maxlength="50" size="20"></td>
    </tr>
     <tr> 
     <td align="right" width="100">Телефон:</td>
     <td><input type="text" id="phoneTxt${name}" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">Пол:</td>
     <td><input type="text" id="sexTxt${name}" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Тип полиса:</td>
     <td><input type="text" id="typepoliceTxt${name}" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Серия:</td>
     <td><input type="text" id="serpolTxt${name}" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Номер:</td>
     <td><input type="text" id="numpolTxt${name}" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">№ напр-я ЛПУ:</td>
     <td><input type="text" id="numdirectTxt${name}" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">№ напр-я ФОНД:</td>
     <td><input type="text" id="numfondTxt${name}" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">Дата напр-я:</td>
     <td><input type="text" id="directDateTxt${name}" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Диагноз:</td>
     <td><input type="text" id="diagnosisTxt${name}" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">Направляющее ЛПУ:</td>
     <td><input type="text" id="orderlpuTxt${name}" maxlength="50" size="20"></td>
    </tr> 
    </tr> 
     <tr> 
     <td align="right" width="100">Плановая дата госпитализации:</td>
     <td><input type="text" id="preDateTxt${name}" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <tr> 
     <td align="right" width="100">Принимающее ЛПУ:</td>
     <td><input type="text" id="directlpuTxt${name}" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <tr> 
     <td align="right" width="100">Профиль ЛПУ:</td>
     <td><input type="text" id="profileTxt${name}" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">Госпитализирован (дата):</td>
     <td><input type="text" id="datehospTxt${name}" maxlength="50" size="20"></td>
    </tr> 
    <tr> 
     <td align="right" width="100">с диагнозом</td>
     <td><input type="text" id="d2Txt${name}" maxlength="50" size="20"></td>
    </tr>  
    <tr> 
     <td align="right" width="100">Выписан (дата):</td>
     <td><input type="text" id="disDateTxt${name}" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <td align="right" width="100">Причина отказа:</td>
     <td><input type="text" id="denied${name}" maxlength="50" size="20"></td>
    </tr> 
     <tr> 
     <tr> 
     <tr> 
     <td></td>
     <td><input type="button" value='Закрыть' id="${name}Cancel" onclick='javascript:the${name}CloseDisDocumentDialog.hide() ;'/></td>
    </tr> 
 </table>     
</form>
</div>
</div>

<script type="text/javascript">
     var theIs${name}CloseDisDocumentDialogInitialized = false ;
     var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
     
     function show${name}CloseDocument() {
         HospitalMedCaseService.sqlorder263('${param.id}', {
             callback: function(res) {
                 if (res!=null) {
                     the${name}CloseDisDocumentDialog.show() ;
                     var aResult = JSON.parse(res);
                     $('idTxt${name}').value=(typeof aResult.idTxt==='undefined')? '': aResult.idTxt;
                     $('fnameTxt${name}').value=(typeof aResult.fnameTxt==='undefined')? '': aResult.fnameTxt;
                     $('nameTxt${name}').value=(typeof aResult.nameTxt==='undefined')? '': aResult.nameTxt;
                     $('lnameTxt${name}').value=(typeof aResult.lnameTxt==='undefined')? '': aResult.lnameTxt;
                     $('birthTxt${name}').value=(typeof aResult.birthTxt==='undefined')? '': aResult.birthTxt;
                     $('phoneTxt${name}').value=(typeof aResult.phoneTxt==='undefined')? '': aResult.phoneTxt;
                     $('sexTxt${name}').value=(typeof aResult.sexTxt==='undefined')? '': aResult.sexTxt;
                     $('typepoliceTxt${name}').value=(typeof aResult.typepoliceTxt==='undefined')? '': aResult.typepoliceTxt;
                     $('serpolTxt${name}').value=(typeof aResult.serpolTxt==='undefined')? '': aResult.serpolTxt;
                     $('numpolTxt${name}').value=(typeof aResult.numpolTxt==='undefined')? '': aResult.numpolTxt;
                     $('numdirectTxt${name}').value=(typeof aResult.numdirectTxt==='undefined')? '': aResult.numdirectTxt;
                     $('numfondTxt${name}').value=(typeof aResult.numfondTxt==='undefined')? '': aResult.numfondTxt;
                     $('directDateTxt${name}').value=(typeof aResult.directDateTxt==='undefined')? '': aResult.directDateTxt;
                     $('d2Txt${name}').value=$('diagnosisTxt${name}').value=(typeof aResult.diagnosisTxt==='undefined')? '': aResult.diagnosisTxt;
                     $('orderlpuTxt${name}').value=(typeof aResult.orderlpuTxt==='undefined')? '': aResult.orderlpuTxt;
                     $('preDateTxt${name}').value=(typeof aResult.preDateTxt==='undefined')? '': aResult.preDateTxt;
                     $('directlpuTxt${name}').value=(typeof aResult.directlpuTxt==='undefined')? '': aResult.directlpuTxt;
                     $('profileTxt${name}').value=(typeof aResult.profileTxt==='undefined')? '': aResult.profileTxt;
                     $('datehospTxt${name}').value=(typeof aResult.datehospTxt==='undefined')? '': aResult.datehospTxt;
                     $('disDateTxt${name}').value=(typeof aResult.disDateTxt==='undefined')? '': aResult.disDateTxt;
                     $('denied${name}').value=(typeof aResult.denied==='undefined')? '': aResult.denied;
                     var inputs = document.getElementsByTagName('input');
                     for (var i = 0; i < inputs.length; i++) {
                         if (inputs[i].type == 'text' && inputs[i].id.indexOf('${name}')!=-1) {
                             inputs[i].disabled = true;
                         }
                     }
                 }
                 else
                     alert("Данных по 263 приказу по этой госпитализации не найдено!");
             }});
     }
</script>