<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal" title="Информация о свободных местах в госпитале"/>
    </tiles:put>
    <tiles:put name="body" type="string">
        <form action="javascript:void(0) ;">
            <div>
                <table width="100%" cellspacing="0" cellpadding="4" id="table" border="1px">
                <tr>
                    <th align="center" width="300"><b>Отделение</b></th>
                    <td align="center" width="100"><b>Мужские, с кислородом</b></th>
                    <td align="center" width="100"><b>Мужские, без кислорода</b></th>
                    <td align="center" width="100"><b>Женские, с кислородом</b></th>
                    <td align="center" width="100"><b>Женские, без кислорода</b></th>
                </tr>
            </table>
            </div>
            <div>
                <table width="100%" cellspacing="10" cellpadding="10">
                    <tr>
                        <td align="center"><input type="button" value='Сохранить' id="saveBtn" onclick='javascript:saveTable()'/></td>
                    </tr>
                    <tr><td></td></tr>
                    <tr>
                        <td align="right"><input type="button"  style="font-weight:bold"  value='Закрыть' onclick='javascript:cancelTable()'/></td>
                    </tr>
                </table>
            </div>
        </form>
        <script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
        <script type='text/javascript'>

            jQuery(document).ready(function() {
                //load
                var table = document.getElementById('table');
                HospitalMedCaseService.getFreeHospBedInfo({
                    callback: function(res) {
                        if (res!=null && res!='{}') {
                            var all = JSON.parse(res);
                            for (var i=0; i<all.length; i++) {
                                var aResult=all[i];
                                if (aResult!=null && aResult!="") {
                                    var tr = document.createElement('tr');
                                    var td0 = document.createElement('td');
                                    td0.setAttribute("hidden",true);
                                    td0.innerHTML = aResult.id;
                                    td0.setAttribute("disabled",true);
                                    var td1 = document.createElement('td');
                                    td1.innerHTML = aResult.lpu;
                                    td1.setAttribute("disabled",true);
                                    var td2 = document.createElement('td');
                                    td2.innerHTML = '<input type="text" value="'+aResult.meno2+'">';
                                    var td3 = document.createElement('td');
                                    td3.innerHTML = '<input type="text" value="'+aResult.mennoo2+'">';
                                    var td4 = document.createElement('td');
                                    td4.innerHTML = '<input type="text" value="'+aResult.womeno2+'">';
                                    var td5 = document.createElement('td');
                                    td5.innerHTML = '<input type="text" value="'+aResult.womennoo2+'">';
                                    td1.align = "center";
                                    td2.align = "center";
                                    td3.align = "center";
                                    td4.align = "center";
                                    td5.align = "center";
                                    tr.appendChild(td0);
                                    tr.appendChild(td1);
                                    tr.appendChild(td2);
                                    tr.appendChild(td3);
                                    tr.appendChild(td4);
                                    tr.appendChild(td5);
                                    table.appendChild(tr);
                                }
                            }
                            //если просматривают все
                            <msh:ifInRole roles="/Policy/Mis/Journal/ShowHospitalFreeInfo/All">
                                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/DeleteAdmin"> //и не администратор
                                    var inputs = document.getElementsByTagName('input');
                                    jQuery("#saveBtn").prop("disabled",true);
                                    for (var i = 0; i < inputs.length; i++)
                                        if (inputs[i].type == 'text')
                                            inputs[i].disabled = true;
                                </msh:ifNotInRole>
                            </msh:ifInRole>
                        }
                        else
                            showToastMessage("Произошла ошибка, информация не найдена",null,true);
                    }});
            });

            function saveTable() {
                //save
                var table = document.getElementById('table');
                var rows = table.rows;
                var mas={
                    list:[]
                };
                for (var i=1; i<rows.length; i++) {
                    var r = rows[i];
                    var id = r.cells[0].innerHTML;
                    var meno2 = r.cells[2].childNodes[0].value;
                    var mennoo2 = r.cells[3].childNodes[0].value;
                    var womeno2 = r.cells[4].childNodes[0].value;
                    var womennoo2 = r.cells[5].childNodes[0].value;

                    if (isNaN(id) || isNaN(meno2) || isNaN(mennoo2)
                    || isNaN(womeno2) || isNaN(womennoo2)) {
                        showToastMessage("Введите только цифры!", null, true);
                        return;
                    }
                    else {
                        var obj = {
                            id: id,
                            meno2: meno2,
                            mennoo2: mennoo2,
                            womeno2: womeno2,
                            womennoo2: womennoo2
                        };
                        mas.list.push(obj);
                    }
                }
                var json = JSON.stringify(mas);
                HospitalMedCaseService.saveFreeHospBedInfo(json,{
                    callback: function() {
                        showToastMessage("Данные сохранены",null,false,2000);
                    }});
            }

            function cancelTable() {
                window.history.back();
            }
        </script>
    </tiles:put>
</tiles:insert>