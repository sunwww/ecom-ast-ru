<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Config">Настройки</msh:title>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <form id="mainForm" name="mainForm">
            <div>
                <div id="fileInfo"></div>
            </div>
            <input type="file" id="file" name="file">
            <input type="button" name="btnClick" onclick="testMe()" value="Загрузить файл">

        </form>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
        <script type='text/javascript' src='./dwr/interface/DispensaryService.js'></script>
        <script type="text/javascript">

            const elmedUrl = "http://127.0.0.1:8090/";

            function testMe() {
                let frm = new FormData();
                frm.append("file",$('file').files[0]);
                jQuery.ajax({ //создаем сущность
                    type: "POST"
                    ,url:elmedUrl+"uploadFile"
                    ,data: frm
                    ,processData: false
                    ,dataType    : 'json'
                    ,contentType : false,
                }).done (function(htm) {
                    console.log(htm);
                    jQuery('#fileInfo').html("Файл загружен. <input type='button' value='Синхронизировать' onclick='makeImport(\""+htm.fileName+"\")'><br>");
                }).fail( function (err) {
                    console.log("ERROR "+JSON.stringify(err));
                });
            }

            function makeImport(fileName) {
                jQuery.ajax({
                    url:elmedUrl+"/importFile?file="+fileName
                }).done(function(res) {
                    console.log("res1="+res);
                    console.log("res11="+res.code);
                    console.log("res12="+res.result);
                    var dt = JSON.parse(res.result);
                    console.log("res12="+dt.data);
                 //   res = JSON.parse(res);
            //        if (res.code=="good") {
                        let str = "";
                        jQuery.each(dt.data, function (ind, el) {
                            str+="<br><p style=\"color: "+(el.status=="ok" ?"green\">"+el.recordId +" УСПЕШНО": "red\">"+el.recordId+" "+el.statusName)+"</p>";
                        });
                        jQuery('#fileInfo').html(str);


              //      }
                }).fail(function (err) {
                    console.log("ERR = "+JSON.stringify(err));
                });
            }




        </script>
    </tiles:put>
</tiles:insert>
