<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #NosCard {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<script type="text/javascript" src="./dwr/interface/PregnancyService.js">/**/</script>
<div id='${name}NosCardDialog' class='dialog'>
<h2>Нозологии</h2>
<div class='rootPane'>
    <form action="javascript:void(0) ;" id="formId">
        <table width="100%" cellspacing="0" cellpadding="4" id="table${name}" border="1">
        </table>
    </form>
</div>
</div>

<script type="text/javascript">
    var ID;
    var theIs${name}NosCardDialogInitialized = false ;
    var the${name}NosCardDialog = new msh.widget.Dialog($('${name}NosCardDialog')) ;
    var goIfNOtSave${name};
    var disable${name};
    var aPrefix${name};
    var Form${name};
    // Показать
    function show${name}(id,goIfNOtSave,disable,form,aPrefix) {
        ID=id;
        goIfNOtSave${name}=goIfNOtSave;
        disable${name}=disable;
        Form${name}=form;
        aPrefix${name}=aPrefix;
        PregnancyService.getBirthNosologyCard(
            ID, {
                callback: function(aResult) {
                    if (aResult!=null && aResult!='[]') {
                        var dsbl=false;
                        var result = JSON.parse(aResult);
                        var table = document.getElementById('table${name}');
                        table.innerHTML="";
                        for (var i=0; i<result.length; i++) {
                            var res=result[i];
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');
                            td1.setAttribute("colspan",2);
                            var html = '<label height="100%" width="100%" for="${name}'+res.vocID+'"><input type="checkbox" id="${name}'+res.vocID+'" name="${name}Chb"';
                            if ( disable${name}!=null && (res.disabled || disable${name})) {
                                html+=' disabled';
                                dsbl=true;
                            }
                            if (res.checked) html+=' checked';
                            html+= '>'+res.vocName+'</label>';
                            td1.innerHTML =  html;
                            td1.setAttribute("align","center");
                            tr.id='${name}row'+i;
                            tr.appendChild(td1);
                            table.appendChild(tr);
                        }
                        table.innerHTML+=" <table width=\"100%\" cellspacing=\"0\" cellpadding=\"4\" id=\"table${name}\" border=\"1\">" +
                            "        <tr>" +
                            "        <tr>" +
                            "        <tr>" +
                            "            <td><input type=\"button\" value='Сохранить' id=\"${name}Save\" onclick='save${name}();'/></td>" +
                            "            <td><input type=\"button\" value='Закрыть' id=\"${name}Cancel\" onclick='javascript:cancel${name}NosCardDialog()'/></td>" +
                            "        </tr>" +
                            "        </table>";
                        if (dsbl) {
                            jQuery("#${name}Save").prop("disabled", true);
                            if (!disable${name})
                                showToastMessage("Пациент выписан, либо уже есть СЛО в обсервационном отделении (при выписке можно будет уточнить нозологии)! Сейчас доступно только для просмотра.",null,true,false);
                        }
                        /*jQuery('#table${name} > tbody  > tr').each(function(index, tr) {
                            tr.onclick = function() {
                                if (tr.childNodes.length>0 && tr.childNodes[0].childNodes.length>0)
                                    tr.childNodes[0].childNodes[0].click();
                            }
                        });*/
                        the${name}NosCardDialog.show() ;
                    }
                    else showToastMessage("Нозологии не найдены!",null,true,true);
                }
            }
        );
    }

    //Сохранить
    function save${name}() {
        var mas = getCheckBoxes${name}();
        if (mas.length>0) {
            PregnancyService.saveBirthNosologyCard(ID,mas+'', {
                callback: function (aResult) {
                    showToastMessage(aResult,null,true,false,2000);
                    the${name}NosCardDialog.hide() ;
                    if (Form${name}) {
                        //Form${name}.submit();
                        showCreateDiagnoseCriteriaCloseDocument($('clinicalMkb').value,null,null, document.forms[0],${param.id},true);
                    }
                    if (aPrefix${name} || aPrefix${name}=='')
                        saveNext(aPrefix${name});
                }
                }
            );
        }
        else
            showToastMessage("Обязательно должна быть отмечена хотя бы одна нозология!",null,true,false);
    }

    //Получить все отмеченные чекбоксы
    function getCheckBoxes${name}(){
        var mas=[];
        var inputs = document.getElementsByTagName('input');
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].type == 'checkbox' && inputs[i].checked==true && inputs[i].name.indexOf('${name}')!=-1) {
                mas.push(inputs[i].id.replace('${name}',''));
            }
        }
        return mas;
    }

    //Закрыть
    function cancel${name}NosCardDialog() {
        the${name}NosCardDialog.hide() ;
        if (goIfNOtSave${name})
            window.location = goIfNOtSave${name};
        if (aPrefix${name} || aPrefix${name}=='')
            $('submitButton').disabled=false ;
    }
</script>