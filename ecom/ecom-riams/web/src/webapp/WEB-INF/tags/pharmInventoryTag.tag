<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>

<msh:ifInRole roles="${roles}">

    <style type="text/css">
        #${name}PharmInventory {
            visibility: hidden;
            display: none;
            position: absolute;
        }
    </style>

    <div id='${name}PharmInventoryDialog' class='dialog'>

        <div id='${name}PharmInventoryDataDiv'>

        </div>

        <input id="cancel" value="Отмена" onclick="cancel${name}PharmInventory()" type="button">
        <input id="save" value="Сохранить" onclick="save${name}PharmInventory()" type="button">
    </div>
    <script type="text/javascript" src="./dwr/interface/PharmnaetService.js"></script>
    <script type="text/javascript">
        var the${name}PharmInventoryDialog = new msh.widget.Dialog($('${name}PharmInventoryDialog'));

        var goodleaveId;

        // Показать
        function show${name}PharmInventory(id) {
            goodleaveId = id;
            getInfo(id);
        }

        function save${name}PharmInventory() {

            var div = document.getElementById('inventar');
            var vocStorage = document.getElementById('vocStorage');

            var ucount = document.getElementById('ucount');
            var qcount = document.getElementById('qcount');
            var userNames = "${username}";

            PharmnetService.saveInvent(goodleaveId, qcount.value, ucount.value, userNames, {
                callback: function (aResult) {

                    PharmnetService.getInventTable(vocStorage.value, {
                        callback: function (aResult) {
                            div.innerHTML = "";
                            div.innerHTML = aResult;
                        }
                    });
                }
            });


            the${name}PharmInventoryDialog.hide();
        }

        // Отмена
        function cancel${name}PharmInventory() {
            the${name}PharmInventoryDialog.hide();
        }


        function getInfo(aGoodleaveId) {
            PharmnetService.getGoodsleave(aGoodleaveId, {
                callback: function (aResult) {

                    $('${name}PharmInventoryDataDiv').innerHTML = aResult;
                    the${name}PharmInventoryDialog.show();
                }
            });
        }


        var timer = setInterval(function () {
            if (document.getElementById('ucount') == null) {
            } else {
                var input = document.getElementById('countinpack');
                var ucount = document.getElementById('ucount');
                var qcount = document.getElementById('qcount');

                ucount.oninput = function () {
                    var t = (ucount.value * input.value);
                    document.getElementById('qcount').value = t;
                };

                qcount.oninput = function () {
                    var t = (qcount.value / input.value);
                    document.getElementById('ucount').value = t;
                };
            }
        }, 100);
    </script>
</msh:ifInRole>