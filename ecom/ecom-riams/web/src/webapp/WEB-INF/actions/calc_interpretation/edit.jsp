<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>



<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="calc_calculator" beginForm="calc_interpretationForm"/>
    </tiles:put>

    <tiles:put name="style" type="string">
        <style>
            .border {
                display: inline-block;
                padding: 3px;
                border: 1px solid rgba(255,0,0,0);
                transition: 3s;
                border-color: rgb(59,92,105) ;
                position: relative;
                padding-top: 0.4em;
                min-height: 0em;
            }


            .colortext {
                background-color: red; /* Цвет фона */
                color: #ffe; /* Цвет текста */
                width: 97%;
            }

        </style>
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-calc_interpretation.do"
                  defaultField="name">

            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="calculator" />



            <div class="information"> </div>
            <div id="copyMe">
                <msh:panel>
                    <msh:row>
                        <msh:textField property="value" label="Значение" horizontalFill="true" fieldColSpan="3" />
                    </msh:row>
                    <msh:row>
                        <msh:textField property="result" label="Интерпретация результата" horizontalFill="true" fieldColSpan="3" />
                    </msh:row>
                </msh:panel>
            </div>

            <input id="btn" type="button" value="Отмена" onclick="goBack()"/>
            <input id="btn" type="button" value="Перейти к следующему элементу" onclick="copyElements()"/>
            <input type="button" value="Предпросмотр" onclick="preview()"/>
            <input id="bt" type="button" value="Сохранить все" onclick="Create()"/>
        </msh:form>



    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/CalculateService.js"></script>
        <script type="text/javascript">



            var global = 1;
            var information = document.querySelector('.information');
            var calculatorId = $('calculator');

            function preview() {
                var i = 1;
                information.innerHTML = '<br> <font> Финальная формула будет выглядеть: <b>';
                for (i; i < global; i++) {
                    var SpanElement = document.querySelector('#content')
                        .querySelector('#id' + i);
                    var Value = SpanElement.querySelector('#value.horizontalFill');

                    information.innerHTML += '<b>' + Value.value + '</b>';
                }
                information.innerHTML += '</font></b><br>';
            }
            function isEmpty(field) {
                if (field.value != "") {
                    return false;
                }
                return true;
            }
            function copyElements() {

                var value = document.querySelector('#value.horizontalFill');
                var result = document.querySelector('#result.horizontalFill');

                if (!isEmpty(value) && !isEmpty(result)) {

                    information.innerHTML = '';
                    var clonedNode = document.getElementById("copyMe").cloneNode(
                        true); // где копируем

                    var content = document.querySelector('#content');
                    var span = document.createElement('span');
                    span.className = "border";
                    span.id = "id" + global;
                    content.appendChild(span);
                    span.appendChild(clonedNode);
                    var butn = document.createElement('div');
                    butn.innerHTML = '<button type="button"  class="btn0" id="id'
                        + global
                        + '" onclick="delthis(this.id)">Удалить</button>';
                    butn.innerHTML += '<button type="button" class="btn1" id="id'
                        + global + '" onclick="DoDown(this.id)">↑</button>'; //up
                    butn.innerHTML += '<button type="button" class="btn2" id="id'
                        + global + '" onclick="DoUp(this.id)">↓</button>'; //down
                    span.appendChild(butn);

                    global++;

                    var clear = document.querySelector('#value.horizontalFill');
                    clear.value = "";
                    clear = document.querySelector('#result.horizontalFill');
                    clear.value = "";
                    preview();
                } else {
                    information.innerHTML = 'надо<font color="red"><b>Заполните все поля!</b></font>';
                }
            }

            function DoDown(id) {
                var intIdArr = id.split('id');
                var intId = parseInt(intIdArr[1]);
                var UpSpanElement = document.querySelector('#content')
                    .querySelector('#' + id);

                var UpValue = UpSpanElement.querySelector('#value.horizontalFill');
                var UpComment = UpSpanElement
                    .querySelector('#result.horizontalFill');

                var DownId = intId - 1;
                if (DownId > 0) {

                    var DownSpanElement = document.querySelector('#content')
                        .querySelector('#id' + DownId);

                    var DownValue = DownSpanElement
                        .querySelector('#value.horizontalFill');
                    var DownComment = DownSpanElement
                        .querySelector('#result.horizontalFill');

                    var t = DownValue.value;
                    DownValue.value = UpValue.value;
                    UpValue.value = t;

                    t = DownComment.value;
                    DownComment.value = UpComment.value;
                    UpComment.value = t;
                }
            }
            function DoUp(id) {

                var intIdArr = id.split('id');
                var intId = parseInt(intIdArr[1]);
                var UpSpanElement = document.querySelector('#content')
                    .querySelector('#' + id);

                var UpValue = UpSpanElement.querySelector('#value.horizontalFill');
                var UpComment = UpSpanElement
                    .querySelector('#result.horizontalFill');

                var DownId = intId + 1;
                if (DownId < global) {

                    var DownSpanElement = document.querySelector('#content')
                        .querySelector('#id' + DownId);

                    var DownValue = DownSpanElement
                        .querySelector('#value.horizontalFill');
                    var DownComment = DownSpanElement
                        .querySelector('#result.horizontalFill');

                    var t = DownValue.value;
                    DownValue.value = UpValue.value;
                    UpValue.value = t;

                    t = DownComment.value;
                    DownComment.value = UpComment.value;
                    UpComment.value = t;
                }
            }
            function delthis(id) {
                var intIdArr = id.split('id');
                var intId = parseInt(intIdArr[1]);
                var del = document.querySelector('#' + id);
                del.remove();

                for ( var i = intId; i < global; i++) {
                    var id2 = (i + 1);
                    if (id2 < global) {
                        var span = document.querySelector('#id' + id2);
                        var orderus = span.querySelector('#copyMe').querySelector(
                            '#orderusReadOnly.viewOnly');
                        var btn = document.querySelector('#id' + id2 + '.btn0');
                        orderus.value = i;
                        btn.id = "id" + i;
                        btn = document.querySelector('#id' + id2 + '.btn1');
                        btn.id = "id" + i;
                        btn = document.querySelector('#id' + id2 + '.btn2');
                        btn.id = "id" + i;
                        span.id = "id" + i;
                    }
                }
                global--;
            }

            function goBack() // возврат на родительскую страницу.
            {
                location.href = "entityParentView-calc_calculator.do?id="
                    + calculatorId.value;
            }

            function Create() {

                var i = 1;
                var interpretations = new Array(); //struct-like!
                for (i; i < global; i++) {
                    var span = document.querySelector('#id' + i + '.border')
                        .querySelector('#copyMe');

                    interpretations[i] = {
                        value : span.querySelector('#value.horizontalFill').value,
                        result : span.querySelector('#result.horizontalFill').value
                    };
                }

                var Json = "{\"calculatorId\":\"" + calculatorId.value
                    + "\",\"params\":" + JSON.stringify(interpretations) + "}";
                CalculateService.SetInterpretationsSettings(Json, {
                    callback : function(aResult) {
                    }
                });
                goBack();
            }
        </script>
    </tiles:put>
</tiles:insert>