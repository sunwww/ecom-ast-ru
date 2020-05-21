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
    <h3>Если указан пароль, он будет сброшен на выбранное значение.</h3>
    <h3>Если пароль равен 1, то пользователь должен будет сменить пароль после входа.</h3>
    <table width="100%" cellspacing="10" cellpadding="10" border="1" >
    </table>
    <div>
        <form id="${name}">
            <msh:panel>
                <msh:row>
                    <br><label><input name="${name}isCovid" id="${name}isCovid" type="checkbox" onclick='javascript:setCovidOrNot${name}();'/>Инфекционное?</label><br>
                </msh:row>
                <msh:row>
                    <msh:comboBox size='300' horizontalFill="true" property='${name}vocLpuHospOtdAll' vocName="vocLpuHospOtdAll" label='Отделение:'/>
                </msh:row>
                <msh:row>
                    <msh:comboBox size='300' horizontalFill="true" property='${name}vocWorkFunction' vocName="vocWorkFunction" label='Рабочая функция:'/>
                </msh:row>
                <msh:row>
                    <msh:textField property="${name}Psw" label="Пароль" fieldColSpan="3"/>
                    <table width="100%" cellspacing="10" cellpadding="10">
                        <tr>
                            <td align="center"><label><input name="${name}rad" type="radio" onclick='javascript:setText${name}("1");'/>1</label></td>
                            <td align="center"><label><input name="${name}rad" type="radio" onclick='javascript:setText${name}("Covid-19");'/>Covid-19</label></td>
                        </tr>
                        <tr><td></td></tr>
                    </table>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Выполнить' id="${name}Add" onclick='javascript:make${name}()'/></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td align="right"><input type="button"  style="font-weight:bold" id="${name}203" value='Отменить' id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
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
        setCovidOrNot${name}();
        the${name}CloseDocumentDialog.show();
    }

    // Отменить консультацию
    function make${name}() {
        var lpu=+document.getElementById('${name}vocLpuHospOtdAll').value;
        var vwf=+document.getElementById('${name}vocWorkFunction').value;

        if (!isNaN(lpu) && !isNaN(vwf) && (lpu!=0 && vwf!=0 || $('${name}Psw').value!='')) {
            RolePoliciesService.addUserToCovidHosp(ID, lpu, vwf,$('${name}Psw').value, {
                callback:function (msg) {
                    showToastMessage(msg,null,true,false,6000);
                    cancel${name}();
                }
            }) ;
        }
        else
            showToastMessage('Введите корректные данные. Нужно выбрать отделение и должность и/или ввести новый пароль!',null,true,true,5000);
    }

    //Проставить текст в пароль
    function setText${name}(text) {
        $('${name}Psw').value=text;
    }

    //очистить и закрыть
    function cancel${name}() {
        //госпиталь и должность удобно не убирать
        $('${name}Psw').value="";
        the${name}CloseDocumentDialog.hide() ;
        jQuery("input[name='${name}rad']")[0].checked=jQuery("input[name='${name}rad']")[1].checked=false;
    }

    //если инфекционное, то проставить parentId
    function setCovidOrNot${name}() {
        $('${name}vocLpuHospOtdAll').value='';
        $('${name}vocLpuHospOtdAllName').value='';
        $('${name}vocWorkFunction').value='';
        $('${name}vocWorkFunctionName').value='';

        var vocLpu = jQuery("#${name}isCovid").prop("checked") ?
            'vocCovidLpu' : 'vocLpuHospOtdAll';
        var vocWf = jQuery("#${name}isCovid").prop("checked") ?
            'vocCovidWf' : 'vocWorkFunction';
        ${name}vocLpuHospOtdAllAutocomplete.setUrl('simpleVocAutocomplete\\'+vocLpu);
        ${name}vocWorkFunctionAutocomplete.setUrl('simpleVocAutocomplete\\'+vocWf);
    }
</script>