<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="6"/>  	
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
    <h2>Приложение N 6<br/>
к
<a href="step_gosgarant_0.do?infomat=${param.infomat}">Программе</a></h2><h4>
Средние нормативы объема медицинской помощи</h4>
<p class="western" style="margin-bottom: 0cm;"><br>
</p>
<p class="western" style="margin-bottom: 0cm;">Средние
нормативы объема медицинской помощи
по ее видам в целом по Программе
рассчитываются в единицах объема в
расчете на 1 жителя в год (по ТП ОМС - в
расчете на 1 застрахованное лицо) и
составляют:</p>
<p class="western" style="margin-bottom: 0cm;"><br>
</p>
<table border="1">

	<tbody><tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;" align="CENTER">Виды
			и условия медицинской помощи</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">Ед.
			изм.</p>
		</td>
		<td colspan="6" width="406">
			<p style="text-indent: 0cm;" align="CENTER">Нормативы
			объема</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td >
			<p style="text-indent: 0cm;"><br>

			</p>
		</td>
		<td colspan="2" width="126">
			<p style="text-indent: 0cm;" align="CENTER">2015
			год</p>
		</td>
		<td colspan="2" width="126">
			<p style="text-indent: 0cm;" align="CENTER">2016
			год</p>
		</td>

		<td colspan="2" width="125">
			<p style="text-indent: 0cm;" align="CENTER">2017
			год</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;"><br>
			</p>

		</td>
		<td >
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">Программа</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="CENTER">в
			т.ч. по ТП ОМС</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">Программа</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">в
			т.ч. по ТП ОМС</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">Программа</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">в
			т.ч. по ТП ОМС</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Скорая
			медицинская помощь вне медицинской
			организации, включая медицинскую
			эвакуацию, в том числе:</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">вызов</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,318</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,318</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,318</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,318</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,318</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,318</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь первого уровня</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">вызов</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,300</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,300</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,300</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,300</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,300</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,300</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">медицинская
			помощь второго уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">вызов</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,014</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,014</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,014</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,014</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,014</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,014</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь третьего уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">вызов</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,004</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,004</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,004</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,004</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,004</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,004</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Медицинская
			помощь в амбулаторных условиях,
			оказываемая с профилактической и
			иными целями (включая посещения центров
			здоровья, посещения в связи с
			диспансеризацией, посещения среднего
			медицинского персонала), включая
			медицинскую помощь с использованием
			передвижных форм ее оказания, в том
			числе:</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">посещение</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">2,9</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">2,3</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">2,95</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">2,35</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">2,98</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">2,38</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">посещение</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,044</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,044</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,044</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,044</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,044</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,044</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь первого уровня</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">посещение</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,53</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,37</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,56</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,4</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">1,58</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,42</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">медицинская
			помощь второго уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">посещение</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,07</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,63</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,08</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,64</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,09</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,65</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь третьего уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">посещение</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,3</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,3</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,31</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,31</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,31</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,31</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Медицинская
			помощь в амбулаторных условиях,
			оказываемая в связи с заболеваниями,
			включая медицинскую помощь с
			использованием передвижных форм ее
			оказания, в том числе:</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">обращение</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">2,15</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">1,95</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">2,18</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,98</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">2,18</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,98</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">обращение</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,005</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,005</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,005</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,005</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,005</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,005</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь первого уровня</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">обращение</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,56</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,39</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,58</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,41</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">1,58</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">1,41</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">медицинская
			помощь второго уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">обращение</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,55</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,45</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,56</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,46</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,56</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,46</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь третьего уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">обращение</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,04</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,11</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,04</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,11</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,04</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,11</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Медицинская
			помощь в амбулаторных условиях,
			оказываемая в неотложной форме, в том
			числе:</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">посещение</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,5</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,5</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,56</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,56</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,6</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,6</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь первого уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">посещение</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,3</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,3</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,3</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,34</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,3</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">0,36</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь второго уровня</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">посещение</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,12</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,12</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,12</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,13</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,12</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,14</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">медицинская
			помощь третьего уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">посещение</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,08</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,08</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,08</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,09</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,08</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,1</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Медицинская
			помощь в условиях дневных стационаров,
			в том числе:</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">пациенто-день</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,675</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,56</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,675</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,56</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,675</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,56</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь первого уровня</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">пациенто-день</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,33</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,33</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,33</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,33</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,33</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,33</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь второго уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">пациенто-день</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,255</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,16</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,255</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,16</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,255</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">0,16</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь третьего уровня</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">пациенто-день</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,08</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,08</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,08</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,08</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,08</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,08</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Медицинская
			помощь в стационарных условиях, в том
			числе:</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">случай</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,193</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,172</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,193</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,172</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,193</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,172</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь первого уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">случай</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,041</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,04</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,041</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,04</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,041</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,04</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь второго уровня</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">случай</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,061</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,044</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,061</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,044</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,061</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,044</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">медицинская
			помощь третьего уровня</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">случай</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,090</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,089</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,090</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,089</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,090</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">0,089</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">для
			медицинской реабилитации в стационарных
			условиях</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">койко-день</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,033</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,033</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,039</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,039</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">0,039</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">0,039</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">для
			паллиативной медицинской помощи</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">койко-день</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,092</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">Х</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,092</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">Х
			</p></td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">0,092</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="CENTER">Х
			</p></td>
			
			
			</tr></tbody></table>
    </div>
	   <tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>