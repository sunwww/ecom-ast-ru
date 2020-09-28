<%@ page contentType="text/html;charset=UTF-8" %>
<style type='text/css'>
    td, th {
        border: 1px solid black;
        padding: 4px;
        font-size: 2em
    }
    table {
       text-align:center;
        border:1px;
        width:100%;
        height:100%
    }
   /* tbody {
        line-height: 2.5em;
    }*/
</style>
<html>
<table id="sheduleTable">
    <tbody id="sheduleTableBody" name="sheduleTableBody"></tbody>
</table>
</html>
<script type="text/javascript" src="/skin/ac.js"></script>
<script type="text/javascript">
    var tbl =jQuery('#sheduleTableBody');
    var NUM=0; //кол-во выводимых строк
    var nowNum=1;
    var defRows='<tr>' +
        '           <td width="10%" id="now"><b>00:00 01.01.1991</b></td>' +
        '           <td width="90%" colspan="4"><b>РАСПИСАНИЕ СПЕЦИАЛИСТОВ КОНСУЛЬТАТИВНОЙ ПОЛИКЛИНИКИ</b></td>' +
        '       </tr>' +
        '       <tr>' +
        '           <td width="5%"><b>№</b></td>' +
        '           <td width="28%"><b>Специальность врача</b></td>' +
        '           <td width="42%"><b>ФИО врача</b></td>' +
        '           <td width="10%"><b>№ кабинета</b></td>' +
        '           <td width="15%"><b>Время приёма</b></td>' +
        '       </tr>';  //2 строки в таблице по умолчанию
    //проставялем дату-время
    function setCurrentDateTime() {
        if (typeof $('now')!=='undefined' && $('now')!=null)
            $('now').innerHTML=new Date(jQuery.now()).toLocaleTimeString()+' '+getCurrentDate();
        setTimeout(setCurrentDateTime,1000);
    }
    //получаем кол-во строк
    function getNumOfRowsAndLoad() {
        jQuery.ajax({
            url: "api/polySchedule/getNumConfig"
            ,data:{}
            ,error: function(jqXHR,ex){console.log(ex);setTimeout(loadAll,60000);}
            ,success: function(res) {
                if (!res || typeof res==='indefined' || res=='') tbl.html("Нет настройки с количеством строк!");
                else {
                    NUM=res;
                    loadAll();
                }
            }
        }) ;
    }
    //перезагрузка страницы
    function reloadPage() {
        tbl.html("Подождите...");
        location.reload();
    }
    //загружаем содержимое таблицы
    function loadAll() {
        tbl.html("Подождите...");
        jQuery.ajax({
            url: "api/polySchedule/getAll"
            ,data:{}
            ,error: function(jqXHR,ex){console.log(ex);setTimeout(loadAll,60000);}
            ,success: function(array) {
                if (!array||array.length==0) tbl.html("Нет расписания");
                else {
                    if (nowNum>array.length)
                        reloadPage();
                    else
                        tbl.html(defRows);
                    for (var i=nowNum-1;i<array.length && i+1<nowNum+NUM;i++) {
                        var el = array[i];
                        tbl.append("<tr><td align='left'>"+el.num+"</td><td align='left'>"+el.wfname+"</td><td align='left'>"+el.fio+"</td><td align='left'>"+((typeof el.cab==='undefined')? '-' : el.cab)+"</td><td align='left'>"+el.per+"</td></tr>");
                        if (i+1==NUM) break;
                    }
                    nowNum+=NUM;
                    setCurrentDateTime();
                }
            }
        });
        setTimeout(setCurrentDateTime,1000);
        setTimeout(loadAll,15000);
    }
    jQuery(document).ready(function() {getNumOfRowsAndLoad();});
</script>