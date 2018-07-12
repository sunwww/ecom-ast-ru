<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<%@ attribute name="name" required="true" description="Название" %>

<style type="text/css">
    #CloseDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<script type="text/javascript" src="./dwr/interface/ContractService.js">/**/</script>
<div id='${name}CloseDisDocumentDialog' class='dialog'>
    <h2>Лабораторные направления</h2>
    <div>
        <form  id="${name}">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete vocName="vocPrescriptType" property="${name}prescriptType" label="Тип назначения" horizontalFill="true" size='1000' />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="${name}labDepartment" label="Место забора" vocName="departmentIntake" size='1000' horizontalFill="true" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete size='1000' horizontalFill="true" property='${name}priceMedServicies' vocName="medServiceForLabPriceList" label='Услуга :'/>
                </msh:row>
            </msh:panel>
            <msh:panel>
                <msh:row>
                    <td><input style="border: 0;padding:0; text-align: right;" type="text" value="Дата:" maxlength="13" size="13" readonly="true"/></td>
                    <td><input type="text" maxlength="10" size="10" id="${name}date" placeholder="dd.mm.yyyy"/></td>
                </msh:row>
                <msh:row>
                    <td><input style="border: 0;padding:0; text-align: right;" type="text" value="Кол-во:" maxlength="13" size="13" readonly="true"/></td>
                    <td><input type="text" maxlength="10" size="10" id="${name}num" pattern="/[0-9]+/"/></td>
                </msh:row>
            </msh:panel>
        </form>
    </div>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td align="center"><input type="button" value='Добавить' id="${name}Add" onclick='javascript:addMedServ${name}()'/></td>
            </tr>
        </table>
    </div>
    <h3 align="center">Добавленные направления</h3>
    <table width="100%" cellspacing="10" cellpadding="10" id="medServTable${name}" border="1"></table>
    <div>
        <table width="100%" cellspacing="10" cellpadding="10">
            <tr>
                <td></td>
            </tr>
            <tr>
                <td align="center"><input type="button" value='В ДОГОВОР' id="${name}Add" onclick='javascript:putToContract${name}()'/></td>
                <td align="right"><input type="button"  style="font-weight:bold" value='Отмена' id="${name}Cancel" onclick='javascript:cancel${name}()'/></td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    var ${name}prLID;
    var theIs${name}CloseDisDocumentDialogInitialized = false ;
    var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
    var labs = new Array();
    // Показать
    function show${name}(id) {
        if (id!=null && id!='') {
            ${name}prLID=id;
            theTableArrow = null ;
            reload${name}();
            //обнуление
            $('${name}num').value=1;
            $('${name}prescriptType').value=1;
            $('${name}prescriptTypeName').value='ПЛАНОВЫЕ ИССЛЕДОВАНИЯ (срок выполнения 24 часа с момента доставки биологического материала) ';
            $('${name}labDepartment').value=180;
            $('${name}labDepartmentName').value='ЦЕНТР КОНСУЛЬТАТИВНО - ПОЛИКЛИНИЧЕСКОЙ ПОМОЩИ ';
            setCurrentDate();
            ${name}priceMedServiciesAutocomplete.setParentId($("${name}date").value+"#"+${name}prLID) ;
            //заливка
            document.getElementById("${name}labDepartmentName").style.backgroundColor = '#fcffa7';
            document.getElementById("${name}priceMedServiciesName").style.backgroundColor = '#fcffa7';
            document.getElementById("${name}prescriptTypeName").style.backgroundColor = '#fcffa7';
            document.getElementById("${name}date").style.backgroundColor = '#fcffa7';
        }
        else alert("Выберите прейскурант!");
    }
    // Отмена
    function cancel${name}() {
        labs = new Array();
        the${name}CloseDisDocumentDialog.hide() ;
    }
    //Перезагрузка
    function reload${name}() {
        if ($('${name}priceMedServicies')) $('${name}priceMedServicies').value='';
        if ($('${name}priceMedServiciesName')) $('${name}priceMedServiciesName').value='';
        var table = document.getElementById('medServTable${name}');
        table.innerHTML=" <tr><th align=\"center\" width=\"450\">Услуга</th><th align=\"center\" width=\"150\">Дата</th><th align=\"center\" width=\"300\">Удалить?</th></tr><tr>";
        for (var i=0; i<labs.length; i++) {
            var l = labs[i];
            var tr = document.createElement('tr');
            var td1 = document.createElement('td');td1.align = "center";
            var td2 = document.createElement('td');td2.align = "center";
            var td3 = document.createElement('td');td3.align = "center";
            var td4 = document.createElement('td');td4.align = "center";
            var td5 = document.createElement('td');td5.align = "center";
            td1.innerHTML = l.name+" - " + l.num;
            td2.innerHTML = l.date;
            td3.innerHTML = "<input type=\"button\" value='Удалить' onclick='javascript:deleteMedServ${name}(\""+i+"\")'/>";
            tr.appendChild(td1);tr.appendChild(td2);tr.appendChild(td3);
            table.appendChild(tr);
        }
        the${name}CloseDisDocumentDialog.show();
    }
    //Удаление направления
    function deleteMedServ${name}(i) {
        labs.splice(i, 1);
        the${name}CloseDisDocumentDialog.hide() ;
        reload${name}();
    }
    //Добавление направления
    function addMedServ${name}() {
        if (document.getElementById("${name}priceMedServicies").value!=null && document.getElementById("${name}priceMedServicies").value!=""
            && document.getElementById("${name}priceMedServiciesName").value!==""
            && $(${name}date).value!=null && $(${name}date).value!=="" && (+$('${name}num').value>=1) && $('${name}labDepartmentName').value!==""
            && $('${name}prescriptTypeName').value!==""  && $('${name}prescriptType').value!=="" &&  $('${name}labDepartment').value!=="") {
            if (checkDate()) {
                var l= {
                    msid: document.getElementById("${name}priceMedServicies").value,
                    date: $(${name}date).value,
                    name: document.getElementById("${name}priceMedServiciesName").value,
                    prescriptType: document.getElementById("${name}prescriptTypeName").value,
                    prescriptTypeId: document.getElementById("${name}prescriptType").value,
                    depIntakeId: document.getElementById("${name}labDepartment").value,
                    num: document.getElementById("${name}num").value
                };
                if (isUnique(l,labs)) {
                    labs.push(l);
                    the${name}CloseDisDocumentDialog.hide() ;
                    reload${name}();
                } else alert("Вы уже добавили точно такую же услугу!");
            }
            else alert("Некорректная дата!");
        }
        else alert("Необходимо выбрать тип назначения, место забора, услугу, ввести дату и количество!");
    }
    //проверка даты
    function checkDate() {
        var inputDate = document.getElementById("${name}date").value;
        var dateParts = inputDate.split(".");
        var date = new Date(dateParts[2], (dateParts[1] - 1), dateParts[0]);

        var dd=date.getDate();
        var mm=date.getMonth() + 1;
        var yyyy=date.getFullYear();

        var inputDate2=(dd>9 ? '' : '0') +  dd+ "." + (mm>9 ? '' : '0')  + mm+ "." + yyyy;
        return (inputDate==inputDate2);
    }
    //Сегодняшняя дата по умолчанию
    function setCurrentDate() {
        var date=new Date();
        var dd=date.getDate();
        var mm=date.getMonth() + 1;
        var yyyy=date.getFullYear();
        $(${name}date).value=(dd>9 ? '' : '0') +  dd+ "." + (mm>9 ? '' : '0')  + mm+ "." + yyyy;
    }
    //Добавить в договор
    function putToContract${name}() {
        for (var i=0; i<labs.length; i++)
                addRow(labs[i].msid,labs[i].name,labs[i].num);
        if (labs.length==0) alert("Список лаб. услуг с направлениями пуст!");
        else {
            var t={
                labs:labs
            };
            $('referralsInfoLab').value=JSON.stringify(t);
            cancel${name}();
        }
    }
    //проверка на уникальность
    function isUnique(element,array) {
        for(var i=0; i<array.length; i++)
            if((JSON.stringify(element)+'') === (JSON.stringify(array[i])+'')) return false;
        return true;
    }
    eventutil.addEventListener($('${name}date'), "input", function(){${name}priceMedServiciesAutocomplete.setParentId($("${name}date").value+"#"+${name}prLID) ;}) ;
    eventutil.addEventListener($('${name}date'), "keyup", function(){${name}priceMedServiciesAutocomplete.setParentId($("${name}date").value+"#"+${name}prLID) ;}) ;
    eventutil.addEventListener($('${name}date'), "blur",	 function(){${name}priceMedServiciesAutocomplete.setParentId($("${name}date").value+"#"+${name}prLID) ;}) ;
    eventutil.addEventListener($('${name}date'), "paste", function(){${name}priceMedServiciesAutocomplete.setParentId($("${name}date").value+"#"+${name}prLID) ;}) ;
</script>
<!--lastrelease milameser 1505 #99-->