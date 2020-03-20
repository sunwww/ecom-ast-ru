<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
<% request.setAttribute("elmedUrl",ActionUtil.getDefaultParameterByConfig("ELMED_URL","",request)); %>

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Config">Настройки</msh:title>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <form id="mainForm" name="mainForm">
            <input type="file" id="file" name="file">
            <input type="button" name="btnClick" onclick="importFile()" value="Загрузить файл">
            <input type="button" name="btnClick" onclick="showAllImportCases()" value="Список импортов">
            <div>
                <div id="fileInfo"></div>
            </div>
        </form>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
        <script type='text/javascript' src='./dwr/interface/DispensaryService.js'></script>
        <script type="text/javascript">

            const elmedUrl = "${elmedUrl}";
            console.log("elmedUrl="+elmedUrl);
            function showAllImportCases() {
                getData("/listAllImport","GET",null,showAllImportCasesHandler);
            }
            function showAllImportCasesHandler(dta) {
                var txt = "<table>";
                for (var i=0;i<dta.length;i++) {
                    var js = dta[i];
                    txt+="<tr><td><p>Дата импорта: "+js.importDate+", тип ="+js.importType+", записей = "+js.count+"</p><input type='button' onclick='getEntitiesByCase("+js.id+")' value='Просмотр записей'></td></tr>";
                }
                txt+="</table>";
                jQuery('#fileInfo').html(txt);
            }

            function getEntitiesByCase(entityId) {
                getData("/listImportById","GET",{"id":entityId},getEntitiesByCaseHandler);
            }
            function getEntitiesByCaseHandler(dta) {
                var txt = "<table>";
                for (var i=0;i<dta.length;i++) {
                    var js = dta[i];
                    txt+="<tr style='background-color : "+(js.error ? "red" : "green")+"'><td><p>Пациент: "+js.name+(js.error ? " Ошибка: "+js.error+"<input type='button' onclick='importById("+js.id+ ",this)' value='Импортировать еще раз'></p> " : "")+"</td></tr>";
                }
                txt+="</table>";
                jQuery('#fileInfo').html(txt);
            }

            function importById(entityId, btn) {
                btn.disabled=true;
                getData("/importById","GET",{"entityId":entityId}, importByIdHandler);
            }
            function importByIdHandler(dta){
                if (dta.error) {
                    console.log("Error import="+dta.error);
                } else {
                    console.log("res = "+dta.message);
                }
            }

            function importFile() {
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
                    jQuery('#fileInfo').html("Файл загружен. <input type='button' value='Синхронизировать' onclick='this.disabled=true;makeImport(\""+htm.fileName+"\")'><br>");
                }).fail( function (err) {
                    console.log("ERROR "+JSON.stringify(err));
                });
            }

            function getData(urlAppend,typePost, data, handler) {
                jQuery.ajax({ //создаем сущность
                    type: typePost
                    ,url:elmedUrl+urlAppend
                    ,data: data
                }).done (function(htm) {
                    console.log(htm);
                    handler(JSON.parse(htm.result));
                }).fail( function (err) {
                    console.log("ERROR "+JSON.stringify(err));
                    handler(JSON.parse(err));
                });
            }

            function makeImport(fileName) {
                jQuery.ajax({
                    url:elmedUrl+"/importFile?file="+fileName
                }).done(function(res) {
                    var dt = JSON.parse(res.result);
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
