<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">
        <tags:sideGosgarant curUrl="info"/>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Перечень жизненно необходимых и важнейших лекарственныъ препаратов для медицинского применения на 2018 г.</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
        <style type="text/css">
        </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <div>
            <iframe src="progrm.pdf" width="800" height="800" alt="Попробуйте в другом браузере"></iframe>

        </div>
        <tags:timerGoMain interval="600000"/>
    </tiles:put>
</tiles:insert>