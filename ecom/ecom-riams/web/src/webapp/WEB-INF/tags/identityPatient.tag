<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="title" description="заголовок" %>

<style type="text/css">
    #CloseDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
<div id='${name}Dialog' class='dialog'>
    <h2>Цветные браслеты пациента ${title}</h2>
    <table width="100%" cellspacing="10" cellpadding="10" id="braceletTable${name}" border="1" >
    </table>
    <div>
        <form  id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:comboBox size='300' horizontalFill="true" property='${name}vocColorIdentityPatient' vocName="vocColorIdentityPatient" label='Выберите:'/>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Добавить' id="${name}Add" onclick='javascript:addIdentityPatient${name}()'/></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td align="right"><input type="button"  style="font-weight:bold" id="${name}203" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
            </tr>
        </table>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
            <tr>

            <tr><td></td></tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    var ID;
    var slsOrPat; //1-СЛС, 0 - пациент
    var theIs${name}DialogInitialized = false ;
    var the${name}Dialog = new msh.widget.Dialog($('${name}Dialog')) ;
    // Показать

    function show${name}(id,aSlsOrPat) {
        ID=id;
        slsOrPat=aSlsOrPat;
        theTableArrow = null ;
        reload${name}();
    }
    // Отмена
    function cancel${name}() {
        the${name}Dialog.hide() ;
        //перезагрузка
        loadBracelets();
    }
    //Перезагрузка
    function reload${name}() {
        var table = document.getElementById('braceletTable${name}');
        table.innerHTML=" <tr><th align=\"center\" width=\"450\">Браслет</th><th align=\"center\" width=\"300\">Снять?</th></tr><tr>";
        HospitalMedCaseService.selectIdentityPatient(
            ID,slsOrPat, {
                callback: function(res) {
                    if (res!=null && res!='[]') {
                        var aResult = JSON.parse(res);
                        for (var i=0; i<aResult.length; i++) {
                            if (aResult[i].isforpat=='0') { //только цветные браслеты, не патология
                                var tr = document.createElement('tr');
                                var td = document.createElement('td');
                                var td2 = document.createElement('td');
                                td.innerHTML=aResult[i].vsipName;
                                td2.innerHTML = "<input type=\"button\" value='Снять' id="+aResult[i].vcipId+" onclick='javascript:deleteIdentityPatient${name}(\""+aResult[i].vcipId+"\")'/>";
                                td.setAttribute("align","center");
                                td2.setAttribute("align","center");
                                tr.appendChild(td);
                                tr.appendChild(td2);
                                table.appendChild(tr);
                            }
                        }
                    }
                    the${name}Dialog.show();
                }
            }
        );
    }
    //Удаление
    function deleteIdentityPatient${name}(identityPatientId) {
        HospitalMedCaseService.deleteIdentityPatient(identityPatientId, {
                callback: function() {
                    the${name}Dialog.hide() ;
                    reload${name}();
                }
            }
        );
    }
    //Добавление
    function addIdentityPatient${name}() {
        var identityPatientId=$(${name}vocColorIdentityPatient).value;
        if (identityPatientId!=null && identityPatientId!="") {
            HospitalMedCaseService.addIdentityPatient(
                ID,slsOrPat,identityPatientId, {
                    callback: function() {
                        the${name}Dialog.hide() ;
                        reload${name}();
                    }
                }
            );
        }
    }
</script>