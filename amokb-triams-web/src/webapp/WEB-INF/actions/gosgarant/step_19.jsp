<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="19"/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Программа гос. гарантий бесплатного оказания гражданам мед. помощи на территории АО на 2015 год</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
    
    <h2 align="right">Приложение
N 19<br/>
к
<a href="step_gosgarant_0.do?infomat=${param.infomat}">Программе</a></h2>
<h4>Целевые
значения критериев доступности и
качества медицинской помощи, оказываемой
в рамках Программы</h4>

<table border="1" bordercolor="#000000" cellpadding="7" cellspacing="0" width="672" >
	<col width="50">
	<col width="313">
	<col width="89">
	<col width="79">
	<col width="69">

	<tbody><tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">N
			п/п</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;" align="CENTER">Наименование
			критерия</p>
		</td>
		<td colspan="3" width="265">

			<p style="text-indent: 0cm;" align="CENTER">Целевые
			значения критерия</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>

		<td width="313">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">на
			2015 год</p>
		</td>
		<td width="79">

			<p style="text-indent: 0cm;" align="CENTER">на
			2016 год</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">на
			2017 год</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER">1</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;" align="CENTER">2</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">3</p>

		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">4</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">5</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">1.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Удовлетворенность
			населения медицинской помощью
			(процентов от числа опрошенных)</p>
		</td>
		<td width="89">

			<p style="text-indent: 0cm;" align="CENTER">не
			менее 89,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">не
			менее 90,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">не
			менее 90,0</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">городского
			населения</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">не
			менее 90,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">не
			менее 91,0</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="CENTER">не
			менее 91,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>

		<td width="313">
			<p style="text-indent: 0cm;">сельского
			населения</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">не
			менее 88,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">не
			менее 89,0</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">не
			менее 89,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">2.</p>

		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Смертность
			населения (число умерших лиц на 1000
			населения)</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">12,2</p>
		</td>
		<td width="79">

			<p style="text-indent: 0cm;" align="CENTER">12,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">11,9</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">городского
			населения</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">11,2</p>

		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">11,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">10,9</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">сельского
			населения</p>
		</td>

		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">14,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">13,8</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">13,7</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">3.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Смертность
			населения от болезней системы
			кровообращения (число умерших от
			болезней системы кровообращения на
			100 тыс. человек населения)</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">672,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">662,6</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="CENTER">649,6</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>

		<td width="313">
			<p style="text-indent: 0cm;">городского
			населения</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">678,5</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">678,0</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">677,5</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>

			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">сельского
			населения</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">690,8</p>
		</td>

		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">690,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">689,6</p>
		</td>
	</tr>
	<tr>

		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">4.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Смертность
			населения от новообразований, в том
			числе от злокачественных (число умерших
			от новообразований, в том числе от
			злокачественных на 100 тыс. человек
			населения)</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">191,2</p>

		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">191,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">190,6</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">городского
			населения</p>
		</td>

		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">198,5</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">198,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">197,5</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">сельского
			населения</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">186,7</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">186,0</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="LEFT">185,3</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">5.</p>
		</td>
		<td width="313">

			<p style="text-indent: 0cm;">Смертность
			населения от туберкулеза (случаев на
			100 тыс. человек населения)</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">21,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">18,0</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">16,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>

			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">городского
			населения</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">19,1</p>
		</td>

		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">19,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">18,9</p>
		</td>
	</tr>
	<tr>

		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">сельского
			населения</p>
		</td>
		<td width="89">

			<p style="text-indent: 0cm;" align="CENTER">25,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">24,8</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">24,5</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">6.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Смертность
			населения в трудоспособном возрасте
			(число умерших в трудоспособном
			возрасте на 100 тыс. человек населения)</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">632,2</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">631,5</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="CENTER">631,4</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">7.</p>
		</td>
		<td width="313">

			<p style="text-indent: 0cm;">Смертность
			населения трудоспособного возраста
			от болезней системы кровообращения
			(число умерших от болезней системы
			кровообращения в трудоспособном
			возрасте на 100 тыс. человек населения)</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">202,5</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">201,5</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">201,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">8.</p>

		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			умерших в трудоспособном возрасте на
			дому в общем количестве умерших в
			трудоспособном возрасте</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">33,0</p>
		</td>
		<td width="79">

			<p style="text-indent: 0cm;" align="CENTER">31,5</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">30,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER">9.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Материнская
			смертность (на 100 тыс. родившихся
			живыми)</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">7,0</p>

		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">7,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">7,0</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">10.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Младенческая
			смертность (на 1000 детей, родившихся
			живыми, в том числе в городской и
			сельской местности)</p>
		</td>
		<td width="89">

			<p style="text-indent: 0cm;" align="CENTER">8,6</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">8,3</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">7,5</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">городского
			населения</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">8,2</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">8,0</p>
		</td>
		<td valign="TOP" width="69">

			<p style="text-indent: 0cm;" align="CENTER">7,3</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>

		<td width="313">
			<p style="text-indent: 0cm;">сельского
			населения</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">8,9</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">8,6</p>

		</td>
		<td valign="TOP" width="69">
			<p style="text-indent: 0cm;" align="LEFT">7,7</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">11.</p>

		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			умерших в возрасте до 1 года на дому в
			общем количестве умерших в возрасте
			до 1 года</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">13,9</p>
		</td>
		<td width="79">

			<p style="text-indent: 0cm;" align="CENTER">12,2</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">10,7</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER">12.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Смертность
			детей в возрасте 0 - 4 лет (на 100 тыс.
			человек населения соответствующего
			возраста)</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">207,2</p>

		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">206,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">204,8</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">13.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			умерших в возрасте 0 - 4 лет на дому в
			общем количестве умерших в возрасте
			0 - 4 лет</p>
		</td>
		<td width="89">

			<p style="text-indent: 0cm;" align="CENTER">16,9</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">15,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">13,0</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">14.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Смертность
			детей в возрасте 0 - 17 лет (на 100 тыс.
			человек населения соответствующего
			возраста)</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">91,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">88,0</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="LEFT">83,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">15.</p>
		</td>
		<td width="313">

			<p style="text-indent: 0cm;">Доля
			умерших в возрасте 0 - 17 лет на дому в
			общем количестве умерших в возрасте
			0 - 17 лет</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">19,9</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">18,0</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">16,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">16.</p>

		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			пациентов со злокачественными
			новообразованиями, состоящих на учете
			с момента установления диагноза 5 лет
			и более, в общем числе пациентов со
			злокачественными новообразованиями,
			состоящими на учете</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">44,5</p>
		</td>
		<td width="79">

			<p style="text-indent: 0cm;" align="CENTER">44,6</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">44,7</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER">17.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Обеспеченность
			населения врачами (на 10 тыс. человек
			населения, включая городское и сельское
			население), в том числе</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">49,0</p>

		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">48,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">46,0</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">оказывающими
			медицинскую помощь в амбулаторных
			условиях</p>
		</td>

		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">27,2</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">26,4</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">25,6</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">оказывающими
			медицинскую помощь в стационарных
			условиях</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">22,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">21,4</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="LEFT">20,8</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">18.</p>
		</td>
		<td width="313">

			<p style="text-indent: 0cm;">Обеспеченность
			населения средним медицинским
			персоналом (на 10 тыс. человек населения,
			включая городское и сельское население),
			в том числе</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">110,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">113,0</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">116,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>

			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">оказывающим
			медицинскую помощь в амбулаторных
			условиях</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">56,0</p>
		</td>

		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">57,6</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">59,2</p>
		</td>
	</tr>
	<tr>

		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">оказывающим
			медицинскую помощь в стационарных
			условиях</p>
		</td>
		<td width="89">

			<p style="text-indent: 0cm;" align="CENTER">54,6</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">56,2</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">57,8</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">19.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Средняя
			длительность лечения в медицинских
			организациях, оказывающих медицинскую
			помощь в стационарных условиях (в
			среднем по субъекту Российской
			Федерации)</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">11,7</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">11,7</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="CENTER">11,7</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">20.</p>
		</td>
		<td width="313">

			<p style="text-indent: 0cm;">Эффективность
			деятельности медицинских организаций,
			в том числе расположенных в городской
			и сельской местности (на основе оценки
			выполнения функции врачебной должности,
			показателей рационального и целевого
			использования коечного фонда)</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">1,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">1,0</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">1,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>

			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">городского
			населения</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">1,0</p>
		</td>

		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">1,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">1,0</p>
		</td>
	</tr>
	<tr>

		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">сельского
			населения</p>
		</td>
		<td width="89">

			<p style="text-indent: 0cm;" align="CENTER">1,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">1,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">1,0</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">21.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			расходов на оказание медицинской
			помощи в условиях дневных стационаров
			в общих расходах на Программу</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">7,8</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">7,9</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="CENTER">8,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">22.</p>
		</td>
		<td width="313">

			<p style="text-indent: 0cm;">Доля
			расходов на оказание медицинской
			помощи в амбулаторных условиях в
			неотложной форме в общих расходах на
			Программу</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">2,4</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">2,5</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">2,5</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">23.</p>

		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			впервые выявленных случаев туберкулеза
			в ранней стадии в общем количестве
			случаев выявленного туберкулеза в
			течение года</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">32,0</p>
		</td>
		<td width="79">

			<p style="text-indent: 0cm;" align="CENTER">31,5</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">30,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER">24.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			впервые выявленных случаев онкологических
			заболеваний на ранних стадиях (I и II
			стадии) в общем количестве выявленных
			случаев онкологических заболеваний
			в течение года</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">46,4</p>

		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">46,8</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">46,8</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">25.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Полнота
			охвата профилактическими медицинскими
			осмотрами детей</p>
		</td>
		<td width="89">

			<p style="text-indent: 0cm;" align="CENTER">85,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">85,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">85,0</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">городского
			населения</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">95,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">95,0</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="CENTER">95,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>

		<td width="313">
			<p style="text-indent: 0cm;">сельского
			населения</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">75,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">75,0</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">75,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">26.</p>

		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			пациентов, получивших специализированную
			медицинскую помощь в стационарных
			условиях в медицинских организациях,
			подведомственных федеральным органам
			исполнительной власти, в общем числе
			пациентов, которым была оказана
			медицинская помощь в стационарных
			условиях в рамках ТП ОМС</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">1,3</p>
		</td>
		<td width="79">

			<p style="text-indent: 0cm;" align="CENTER">1,35</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="CENTER">1,35</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER">27.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Число
			лиц, проживающих в сельской местности,
			которым оказана скорая медицинская
			помощь (на 1000 чел. сельского населения)</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">318,0</p>

		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">318,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">318,0</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">28.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			фельдшерско-акушерских пунктов и
			фельдшерских пунктов, находящихся в
			аварийном состоянии и требующих
			капитального ремонта, в общем количестве
			фельдшерско-акушерских пунктов и
			фельдшерских пунктов</p>
		</td>
		<td width="89">

			<p style="text-indent: 0cm;" align="CENTER">14,8</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">14,3</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">14,0</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">29</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			вызовов бригад скорой медицинской
			помощи со временем доезда до пациента
			менее 20 минут с момента вызова в общем
			количестве вызовов</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">85,0</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">86,0</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="LEFT">87,5</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">30.</p>
		</td>
		<td width="313">

			<p style="text-indent: 0cm;">Доля
			пациентов с инфарктом миокарда,
			госпитализированных в первые 6 часов
			от начала заболевания, в общем количестве
			госпитализированных пациентов с
			инфарктом миокарда</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">45,6</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">45,7</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">45,9</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">31.</p>

		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			пациентов с острым инфарктом миокарда,
			которым проведена тромболитическая
			терапия, в общем количестве пациентов
			с острым инфарктом миокарда</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">5,0</p>
		</td>
		<td width="79">

			<p style="text-indent: 0cm;" align="CENTER">5,2</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">5,5</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER">32.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			пациентов с острым инфарктом миокарда,
			которым проведено стентирование
			коронарных артерий, в общем количестве
			пациентов с острым инфарктом миокарда</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">9,5</p>

		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">10,0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">10,5</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">33.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Количество
			проведенных выездной бригадой скорой
			медицинской помощи тромболизисов у
			пациентов с острым и повторным инфарктом
			миокарда в расчете на 100 пациентов с
			острым и повторным инфарктом миокарда,
			которым оказана медицинская помощь
			выездными бригадами скорой медицинской
			помощи</p>
		</td>
		<td width="89">

			<p style="text-indent: 0cm;" align="CENTER">0,12</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">0,14</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">0,16</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">34.</p>
		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Доля
			пациентов с острыми цереброваскулярными
			болезнями, госпитализированных в
			первые 6 часов от начала заболевания,
			в общем количестве госпитализированных
			пациентов с острыми цереброваскулярными
			болезнями</p>

		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">23,7</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">24,0</p>
		</td>
		<td width="69">

			<p style="text-indent: 0cm;" align="LEFT">25,0</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">35</p>
		</td>
		<td width="313">

			<p style="text-indent: 0cm;">Удельный
			вес числа пациентов с острым ишемическим
			инсультом, которым проведена
			тромболитическая терапия в первые 6
			часов госпитализации, в общем количестве
			пациентов с острым ишемическим
			инсультом</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">1,7</p>
		</td>
		<td width="79">
			<p style="text-indent: 0cm;" align="CENTER">2,0</p>

		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">2,4</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">36.</p>

		</td>
		<td width="313">
			<p style="text-indent: 0cm;">Количество
			обоснованных жалоб, в том числе на
			отказ в оказании медицинской помощи,
			предоставляемой в рамках Программы</p>
		</td>
		<td width="89">
			<p style="text-indent: 0cm;" align="CENTER">0</p>
		</td>
		<td width="79">

			<p style="text-indent: 0cm;" align="CENTER">0</p>
		</td>
		<td width="69">
			<p style="text-indent: 0cm;" align="LEFT">0</p>
		</td>
	</tr>
</tbody></table>
<p class="western" style="margin-bottom: 0cm;"><br>
</p>

<p class="western" style="margin-bottom: 0cm;">Целевые
значения критериев доступности и
качества медицинской помощи, оказываемой
в рамках ТП ОМС, могут использоваться
при установлении территориальным фондом
ОМС Астраханской области целевых
значений доступности и качества
медицинской помощи для выплат
стимулирующего характера медицинским
организациям из средств нормированного
страхового запаса территориального
фонда ОМС Астраханской области.
   
	   <tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>