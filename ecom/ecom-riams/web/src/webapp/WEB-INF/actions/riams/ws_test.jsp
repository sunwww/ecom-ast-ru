<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Config">Настройки</msh:title>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <div>
            <form name="hello" action="/someTest.ss">
        <input type='text' id='msgText' name="msgText">
        <input type='button' onclick='ws_sendTextMessage()' id='btc' value="Send message">
        <input type='button' onclick='createQrCode()' id='bt1c' value="CreateQrCode">
        <input type='button' onclick='ws_stopWork()' id='btnStopWork' value="stopWork">
        <input type='button' onclick='ws_getNewTicket()' id='btnGetNewPatient' value="getNewPatient">

            </form>
            <img id="image_123"/>
        </div>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type='text/javascript' src='./dwr/interface/HospitalMedCaseService.js'></script>
        <script type='text/javascript' src='./dwr/interface/DispensaryService.js'></script>
        <script type="text/javascript">

            function createQrCode() {
                DispensaryService.hello(jQuery('#msgText').val(), {
                    callback: function(a) {
                        jQuery('#image_123').attr("src","data:image/png;base64,"+a);
                    }
                })
            }




        </script>
    </tiles:put>
</tiles:insert>
