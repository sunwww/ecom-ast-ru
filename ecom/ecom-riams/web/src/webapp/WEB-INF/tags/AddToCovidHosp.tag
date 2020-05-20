<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>

<div id='${name}CloseDocumentDialog' class='dialog'>
    <h2>Выберите параметры</h2>
    <h3>Если в выбранном отделении нет работника, он будет создан.</h3>
    <h3>Если работник есть, но нет рабочей функции, она будет создана.</h3>
    <h3>Если указано, пароль будет сброшен.</h3>
    <table width="100%" cellspacing="10" cellpadding="10" border="1" >
    </table>
    <div>
        <form id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:comboBox size='300' horizontalFill="true" property='${name}vocCovidLpu' vocName="vocCovidLpu" label='Отделение:'/>
                </msh:row>
                <msh:row>
                    <msh:comboBox size='300' horizontalFill="true" property='${name}vocCovidWf' vocName="vocCovidWf" label='Рабочая функция:'/>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Создать' id="${name}Add" onclick='javascript:make${name}()'/></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td align="right"><input type="button"  style="font-weight:bold" id="${name}203" value='Отменить' id="${name}Cancel" onclick='javascript:the${name}CloseDocumentDialog.hide() ;'/></td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    var ID;
    var theIs${name}CloseDocumentDialogInitialized = false ;
    var the${name}CloseDocumentDialog = new msh.widget.Dialog($('${name}CloseDocumentDialog')) ;
    // Показать
    function show${name}(id) {
        ID=id;
        theTableArrow = null ;
        the${name}CloseDocumentDialog.show();
    }

    // Отменить консультацию
    function make${name}() {
        var lpu=document.getElementById('${name}vocCovidLpu').value;
        if (isNaN(lpu)) {
            alert('Выберите госпиталь!');
            return;
        }
        var vwf=document.getElementById('${name}vocCovidWf').value;
        if (isNaN(vwf)) {
            alert('Выберите рабочую функцию!');
            return;
        }
        else {
            RolePoliciesService.addUserToCovidHosp(ID, lpu, vwf, {
                callback:function (msg) {
                    showToastMessage(msg,null,true,false,6000);
                    the${name}CloseDocumentDialog.hide() ;
                }
            }) ;
        }
    }
</script>