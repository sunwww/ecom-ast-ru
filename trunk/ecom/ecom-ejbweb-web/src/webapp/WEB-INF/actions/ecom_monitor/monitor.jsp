<%@ page import="ru.nuzmsh.web.util.StringSafeEncode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">


<tiles:put name='body' type='string'>
    <div id='monitor'>
        <h1 id='name'>Загрузка ...</h1>

        <h2 id='text'>Загрузка ...</h2>
        <table border='1'>
            <td id='left'></td>
            <td id='right'></td>
        </table>
    </div>

    <form>
        <input id='cancelButton' type='button' value='Прервать' onclick="cancel()"/>
    </form>
    <p id='updating'>Updating...</p>
    <!--<a href='javascript:update()'>Обновить</a>-->

</tiles:put>

<tiles:put name='side' type='string'>
</tiles:put>

<tiles:put name="javascript" type="string">
    <script type="text/javascript">
        var theMonitorId = <%=request.getParameter("id")%> ;
        var theNextUrl = '<%=new StringSafeEncode().decode(request.getParameter("nextUrl"))%>' ;


        function update() {
            var theUpdatingElement = $('updating') ;
            try { //IEBUG
                theUpdatingElement.innerHTML = "<p>...</p>"
            } catch (e) {}
            RemoteMonitorService.getMonitorStatus(theMonitorId, {
                callback: function(aStatus) {
                    updateView(aStatus);
                    theUpdatingElement.innerHTML = "";
                    if (aStatus.finish) {
                        if (theNextUrl != null && theNextUrl != "null") {
                            window.location = theNextUrl + aStatus.finishedParameters;
                        } else {
                            $('name').innerHTML = "ПРЕРВАНО ПОЛЬЗОВАТЕЛЕМ"
                        }
                    } else {
                        setTimeout(update,4000) ;
                    }
                },
                errorHandler:function(message) {
                    theUpdatingElement.innerHTML = "<p class='error'>"+message+"</p>" ;
                    setTimeout(update,4000) ;
                },
                warningHandler:function(message) {
                    theUpdatingElement.innerHTML = "<p class='error'>"+message+"</p>" ;
                    setTimeout(update,4000) ;
                }
            });
        }

        function updateView(aStatus) {
            $('name').innerHTML = aStatus.name;
            $('text').innerHTML = aStatus.text;

            var percent = aStatus.current / aStatus.max ;
            document.title = Math.round(percent * 100) + "% " + aStatus.name;

            var leftWidth = Math.round(300 * percent) ;
            $('left').style.width = leftWidth + "px";
            $('right').style.width = 300 - leftWidth + "px";
        }

        function cancel() {
            $('cancelButton').value = 'Операция прерывается ...';
            RemoteMonitorService.cancel(theMonitorId);
        }

        function addText(aElement, aText) {
            aElement.appendChild(document.createTextNode(aText));
        }

//         setInterval(update,4000) ;
         setTimeout(update,4000) ;
         update() ;

    </script>
</tiles:put>

<tiles:put name="style" type="string">
    <style type="text/css">
        #monitor table {
            width: 300px;
            height: 20px;
        }

        #left {
            width: 0px;
            background-color: blue;
        }

        #right {
            width: 300px;
            background-color: white;
        }

        p.error {
            color: red ;
        }
    </style>
</tiles:put>


</tiles:insert>