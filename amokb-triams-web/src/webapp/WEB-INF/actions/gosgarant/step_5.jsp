<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="5"/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Программа гос. гарантий бесплатного оказания гражданам мед. помощи на территории АО на 2015 год</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <div id='step_5_text'>
    
    <FONT COLOR="#26282f"><B>Приложение N 5<BR>к </B></FONT><FONT COLOR="#106bbe"><B>Программе</B></FONT></P>
<P CLASS="western" STYLE="margin-bottom: 0cm"><BR>
</P>
<H1 CLASS="western">Объем<BR>медицинской помощи, оказываемой в рамках
Программы в соответствии с законодательством Российской Федерации об
обязательном медицинском страховании</H1>
<P CLASS="western" STYLE="margin-bottom: 0cm"><BR>
</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">Объемы медицинской
помощи по видам, условиям, формам ее оказания составляют:</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- для скорой
медицинской помощи вне медицинской организации, включая медицинскую
эвакуацию, - 303 140 вызовов;</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- для медицинской
помощи в амбулаторных условиях, оказываемой с профилактическими и
иными целями (включая посещения центров здоровья, посещения в связи с
диспансеризацией, посещения среднего медицинского персонала), - 2 376
097 посещения;</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- для медицинской
помощи в амбулаторных условиях, оказываемой в неотложной форме, - 565
862 посещения;</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- для медицинской
помощи в амбулаторных условиях, оказываемой в связи с заболеваниями,
- 2 002 175 обращений;</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- для медицинской
помощи в условиях дневных стационаров - 60 818 случаев лечения;</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- для
специализированной медицинской помощи в стационарных условиях - 173
940 случаев госпитализации (в том числе 1697 случаев оказания
высокотехнологичной медицинской помощи, финансируемых за счет средств
ОМС, и 39 440 койко-дней по профилю &quot;Медицинская реабилитация&quot;).</P>
<P CLASS="western" STYLE="margin-bottom: 0cm"><BR>
</P>
    
    </div>
<tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>