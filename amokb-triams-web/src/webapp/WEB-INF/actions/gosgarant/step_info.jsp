<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="info"/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Программа гос. гарантий бесплатного оказания гражданам мед. помощи на территории АО на 2015 год</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <div>
<H1 CLASS="western"><FONT ><SPAN STYLE="align: center; font-weight: normal">Постановление
Правительства Астраханской области<BR>от 24 декабря 2015 г. N
655-П<BR>&quot;О Программе государственных гарантий бесплатного
оказания гражданам медицинской помощи на территории Астраханской
области на 2016 год&quot;</SPAN></FONT></H1>
<P CLASS="western" STYLE="margin-bottom: 0cm"><BR>
</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">В целях реализации
основных принципов охраны здоровья граждан в Российской Федерации, в
соответствии с федеральными законами <B>от
29.11.2010 N 326-ФЗ</B> &quot;Об обязательном медицинском
страховании в Российской Федерации&quot;, <B>от
21.11.2011 N 323-ФЗ</B> &quot;Об основах охраны здоровья
граждан в Российской Федерации&quot;, во исполнение <B>постановления</B>
Правительства Российской Федерации от 19.12.2015 N 1382 &quot;О
Программе государственных гарантий бесплатного оказания гражданам
медицинской помощи на 2016 год&quot; Правительство Астраханской
области постановляет:</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">1.
Утвердить прилагаемую ><B>Программу</B>
государственных гарантий бесплатного оказания гражданам медицинской
помощи на территории Астраханской области на 2016 год.</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">2.
Агентству связи и массовых коммуникаций Астраханской области (Зайцева
М.А.) <FONT COLOR="#106bbe"><B>опубликовать</B></FONT>
настоящее постановление в средствах массовой информации.</P>
<P CLASS="western" STYLE="margin-bottom: 0cm">Постановление вступает в силу с 01.01.2016.</P>
<P CLASS="western" STYLE="margin-bottom: 0cm"><BR>
</P>
<TABLE BORDER=0 CELLPADDING=7 CELLSPACING=0>
	<COL >
	<COL >
	<TR >
		<TD>
		<img src="img/jilkin.jpg" height="250px" />
			<P ALIGN=LEFT STYLE="text-indent: 0cm">Губернатор Астраханской
			области</P>
		</TD>
		<TD>
		
			<P ALIGN=RIGHT STYLE="text-indent: 0cm">А.А. Жилкин</P>
		</TD>
	</TR>
</TABLE>
    </div>
	   <tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>