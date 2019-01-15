<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="documentId" required="true" description="Поле Id документа" %>

<div id='${name}moveLN' class='dialog'>
        <h2>Перенести ЛН в другой СНТ</h2>
        <div class='rootPane'>
            <h3>Выберите СНТ:</h3>
            <table width="100%" cellspacing="10" cellpadding="10" id="${name}Table" border="1" >
                <tr>
                    <th align="center" width="150">C</th>
                    <th align="center" width="150">По</th>
                    <th align="center" width="150">Кол-во дней</th>
                </tr>
            </table>
            <div>
        </div>
        <br>
        <div>
            <table width="100%" cellspacing="10" cellpadding="10">
                <tr>
                    <td align="center"><input type="button" value='Перенести' id="${name}Add" onclick='javascript:move${name}()'/></td>
                    <td align="center"><input type="button" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
                </tr>
            </table>
        </div>
    </div>
</div>


<script type='text/javascript' src='./dwr/interface/DisabilityService.js'></script>
<script type="text/javascript">
    var theIs${name}moveLNInitialized = false ;
    var the${name}moveLN = new msh.widget.Dialog($('${name}moveLN')) ;

    // Показать
    function show${name}() {
        var table = document.getElementById('${name}Table');
        table.innerHTML="<tr>\n" +
            "                    <th align=\"center\" width=\"150\">C</th>\n" +
            "                    <th align=\"center\" width=\"150\">По</th>\n" +
            "                    <th align=\"center\" width=\"150\">Кол-во дней</th>\n" +
            "                </tr>";
        DisabilityService.getDisCasesInJson('${documentId}', {
            callback: function (res) {
                if (res!=null) {
                    var cases=JSON.parse(res);
                    for (var ind1 = 0; ind1 < cases.length; ind1++) {
                        var case1 = cases[ind1];
                        var tr = document.createElement('tr');
                        var td1 = document.createElement('td');
                        td1.innerHTML = "<div class=\"radio\"> <label id="+case1.id+"><input type=\"radio\" name=\"'${name}case\">"+case1.mindatefrom+"</label> </div>";
                        var td2 = document.createElement('td');
                        td2.innerHTML = case1.maxdateto;
                        var td3 = document.createElement('td');
                        td3.innerHTML = case1.case;
                        td1.align="center";td2.align="center";td3.align="center";
                        tr.appendChild(td1); tr.appendChild(td2); tr.appendChild(td3);
                        table.appendChild(tr);
                    }
                    //если СНТ только один и текущий - закрыть
                    if (cases.length==0) {
                        showToastMessage("Нет других СНТ кроме текущего!",null, true);
                        cancel${name}();
                    }
                }
            }
        });
        the${name}moveLN.show() ;
    }

    // Отмена
    function cancel${name}() {
        the${name}moveLN.hide() ;
        msh.effect.FadeEffect.pushFadeAll();
    }
    // Перенести
    function move${name}() {
        var id=getValueVocRadiooncoT('${name}case');
        if (id!=-1) {
            DisabilityService.moveToNewDisCase('${documentId}',id, {
                callback: function (res) {
                    if (res!=null) {
                        window.location.href = 'entityParentView-dis_case.do?id=' + id;
                        showToastMessage(res,null, true);
                    }
                }
            });
        }
        else alert('Выберите СНТ!');
        the${name}moveLN.hide() ;
    }
    //получить значения группы радиобаттонов
    function getValueVocRadiooncoT(name) {
        var res=-1;
        var inputs = document.getElementsByTagName('input');
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].type == 'radio' && inputs[i].checked==true && inputs[i].name.indexOf('${name}')!=-1) {
                res=inputs[i].parentNode.id;
            }
        }
        return res;
    }
</script>