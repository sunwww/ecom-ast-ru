<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="11"/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Программа гос. гарантий бесплатного оказания гражданам мед. помощи на территории АО на 2016 год</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <div id='step_11_text'>
    
    <FONT COLOR="#26282f"><B>Приложение N 11<BR>к </B></FONT><FONT COLOR="#106bbe"><B>Программе</B></FONT></P>
<P CLASS="western" STYLE="margin-bottom: 0cm"><BR>
</P>
<H1 CLASS="western">Условия<BR>предоставления детям-сиротам и детям,
оставшимся без попечения родителей, в случае выявления у них
заболеваний медицинской помощи всех видов, включая
специализированную, в том числе высокотехнологичную, медицинскую
помощь</H1>
<P CLASS="western" STYLE="margin-bottom: 0cm"><BR>
</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">Оказание медицинской
помощи детям-сиротам и детям, оставшимся без попечения родителей
(далее - дети-сироты), в случае выявления у них заболеваний
осуществляется на территории Астраханской области в медицинских
организациях, участвующих в Программе.</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">В рамках Программы
детям-сиротам бесплатно предоставляются:</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- первичная
медико-санитарная помощь, в том числе первичная доврачебная,
первичная врачебная и первичная специализированная (в плановой и
неотложной формах);</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- специализированная, в
том числе высокотехнологичная, медицинская помощь (в плановой форме,
экстренной и неотложной);</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- скорая, в том числе
скорая специализированная, медицинская помощь;</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">- паллиативная
медицинская помощь.</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">Первичная
медико-санитарная помощь оказывается в поликлиниках и других
медицинских организациях, к которым прикреплены дети-сироты.</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">В случае необходимости
оказания детям-сиротам медицинской помощи в условиях стационара или
дневного стационара врач амбулаторно-поликлинического подразделения
медицинской организации выдает направление на госпитализацию.
Медицинская организация, оказывающая стационарную медицинскую помощь,
на основании предъявленного направления обеспечивает плановую
госпитализацию.</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">В случае отсутствия
необходимой медицинской помощи в медицинских организациях,
находящихся на территории Астраханской области, министерство
здравоохранения Астраханской области на основании решения врачебных
комиссий медицинских организаций направляет детей-сирот в медицинские
организации других субъектов Российской Федерации.</P>
       
    </div>
	   <tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>