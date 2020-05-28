<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="alreadyUser" required="true" description="Пользователь или персона" %>  <!-- true - пользователь, false - персона -->

<div id='${name}CloseDocumentDialog' class='dialog'>
    <h2>Выберите параметры</h2>
    <h3>Если пользователя ещё нет, он будет создан.</h3>
    <h3>Роли можно скопировать у другого пользователя.</h3>
    <h3>Если в выбранном отделении нет работника, он будет создан.</h3>
    <h3>Если работник есть, но нет рабочей функции, она будет создана.</h3>
    <h3>Если указан пароль, он будет сброшен на выбранное значение.</h3>
    <h3>Если пароль равен 1, то пользователь должен будет сменить пароль после входа.</h3>
    <h4>При создании через <i>Настройки\Пользователи</i> обязательные поля <u>ЛПУ, рабочая функция</u> и/или <u>пароль</u>.
    <h4>(можно просто сбросить пароль/создать рабочую функцию/всё вместе)</h4>
    <h4>При создании через <i>Персону</i> обязательные поля <u>Логин, ЛПУ, рабочая функция</u> и/или <u>пароль</u> и/или <u>роли</u>.</h4>
    <h4>(можно просто сбросить пароль/скопировать роли/создать пользователя/создать рабочую функцию/всё вместе)</h4>
    <h4>Экспорт ролей может занять некоторое время</h4>
    <table width="100%" cellspacing="10" cellpadding="10" border="1" >
    </table>
    <div>
        <form id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:textField property="${name}Username" label="Логин" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="${name}userCopy" vocName="secUser" label="Копировать роли у пользователя" fieldColSpan="3"/>
                </msh:row>
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
<script type='text/javascript' src='./dwr/interface/RolePoliciesService.js'></script>
<script type="text/javascript">
    var ID;
    var theIs${name}CloseDocumentDialogInitialized = false ;
    var the${name}CloseDocumentDialog = new msh.widget.Dialog($('${name}CloseDocumentDialog')) ;
    // Показать
    function show${name}(id) {
        $('${name}Add').disabled=false;
        ID=id;
        theTableArrow = null ;
        setCovidOrNot${name}();
        $('${name}Username').disabled=(+'${alreadyUser}');
        if (!(+'${alreadyUser}')) { //если создание через Персону
            RolePoliciesService.checkUserExistsAndGenLogin(ID, {
                callback:function (res) {
                   if (res)
                       $('${name}Username').value = res;
                   else {
                       $('${name}Username').disabled=true;
                       showToastMessage('Пользователь у персоны уже есть, можно добавить рабочую функцию/скопировать роли/изменить пароль',null,true,false,6000);
                   }
                    the${name}CloseDocumentDialog.show();
                }
            }) ;
        }
        else
            the${name}CloseDocumentDialog.show();
    }

    // Выполнить
    function make${name}() {
        $('${name}Add').disabled=true;
        var lpu=+document.getElementById('${name}vocLpuHospOtdAll').value;
        var vwf=+document.getElementById('${name}vocWorkFunction').value;
        var login = document.getElementById('${name}Username').value;
        var psw = $('${name}Psw').value;
        var userCopy = $('${name}userCopy').value;

        if (+'${alreadyUser}') {  //создание через пользователя
            if (!isNaN(lpu) && !isNaN(vwf) && (lpu!=0 && vwf!=0 || psw!='' || userCopy!='')) {
                RolePoliciesService.getPatientBySecUser(ID, {
                    callback:function (patientId) {
                        if (patientId) {
                            RolePoliciesService.addUserToHospShort(patientId, lpu, vwf,psw,userCopy,'',ID, {//пустой логин, т.к. он тут не нужен
                                callback:function (msg) {
                                    showToastMessage(msg,null,true,false,6000);
                                    cancel${name}();
                                }
                            }) ;
                        }
                    }
                }) ;
            }
            else {
                $('${name}Add').disabled=false;
                showToastMessage('Введите корректные данные. Нужно выбрать отделение и должность и/или ввести новый пароль!',null,true,true,5000);
                return;
            }
        }
            else if (login!='' && (isNaN(lpu) || lpu==0 || isNaN(vwf) || vwf==0)) {
                showToastMessage('Если введён логин, то необходимо выбрать отделение и должность!',null,true,true,5000);
                $('${name}Add').disabled=false;
                return;
            }
            else if (!isNaN(lpu) && lpu!=0 && (isNaN(vwf) || vwf==0)
                || !isNaN(vwf) && vwf!=0 && (isNaN(lpu) || lpu==0)) {
                showToastMessage('Нельзя выбрать только отделение или только должность!',null,true,true,5000);
                $('${name}Add').disabled=false;
                return;
            }
            else if (login!='' || lpu!=0 || psw!='' || userCopy!=0) {
                RolePoliciesService.addUserToHospShort(ID, lpu, vwf,psw,userCopy,login,null, {
                callback:function (msg) {
                    showToastMessage(msg,null,true,false,6000);
                    cancel${name}();
                    }
                }) ;
            }
            else {
                showToastMessage('Заполните данные!',null,true,true,2000);
                $('${name}Add').disabled=false;
            }
    }

    //Проставить текст в пароль
    function setText${name}(text) {
        $('${name}Psw').value=text;
    }

    //Очистить и закрыть
    function cancel${name}() {
        $('${name}Add').disabled=false;
        //госпиталь и должность удобно не убирать
        $('${name}Psw').value="";
        $('${name}Username').value="";
        $('${name}userCopy').value='';
        $('${name}userCopyName').value='';
        the${name}CloseDocumentDialog.hide() ;
        jQuery("input[name='${name}rad']")[0].checked=jQuery("input[name='${name}rad']")[1].checked=false;
    }

    //Если инфекционное, то проставить parentId
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