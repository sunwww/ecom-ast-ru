<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="14"/>  	
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
N 14<br/>
к
<a href="step_gosgarant_0.do?infomat=${param.infomat}">Программе</a></h2>
<h4>Перечень<br/>медицинских
организаций, участвующих в реализации
Программы, в том числе территориальной
программы обязательного медицинского
страхования</h4>
<p class="western" style="margin-bottom: 0cm;"><br>
</p>
<table border="1" bordercolor="#000000" cellpadding="7" cellspacing="0" width="672">
	<col width="50">

	<col width="462">
	<col width="116">
	<tbody><tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">N
			п/п</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">Наименование
			медицинской организации</font></p>

		</td>
		<td valign="TOP" width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">Осуществляющие
			деятельность в сфере ОМС </font><a href="#sub_14001"><font color="#106bbe"><font face="Times New Roman, serif">(+)</font></font></a></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">1</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">2</font></p>
		</td>
		<td valign="TOP" width="116">
			<p style="text-indent: 0cm;" align="LEFT"><font face="Times New Roman, serif">3</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">1</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО Александро-Мариинская областная
			клиническая больница</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">2</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областная детская клиническая
			больница им. Н.Н. Силищевой"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">3</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областной кардиологический
			диспансер"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">4</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областная инфекционная клиническая
			больница им. А.М. Ничоги"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">5</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областной онкологический
			диспансер"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">6</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областной кожно-венерологический
			диспансер"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">7</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областной клинический
			противотуберкулезный диспансер"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">8</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областной наркологический
			диспансер"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">9</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областная клиническая
			психиатрическая больница"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">10</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областной клинический
			стоматологический центр"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">11</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областной центр по профилактике
			и борьбе со СПИД и инфекционными
			заболеваниями"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">12</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областной врачебно-физкультурный
			диспансер"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">13</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Областной центр крови"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">14</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			"Территориальный центр медицины
			катастроф Астраханской области"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">15</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Бюро судебно-медицинской
			экспертизы"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">16</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Патологоанатомическое бюро"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">17</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Центр медицинской профилактики"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">18</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Медицинский центр "Пластическая
			хирургия и косметология"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">19</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУ
			АО "Управление по экспертизе, учету
			и анализу обращения средств медицинского
			применения"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">20</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Медицинский информационно-аналитический
			центр"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">21</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГКУЗ
			АО "Медицинский центр мобилизационных
			резервов "Резерв"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">22</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ОГБОУ
			СПО "Астраханский базовый медицинский
			колледж"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">-</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">23</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБОУ
			ВПО "Астраханский государственный
			медицинский университет Минздрава
			России"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">24</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская клиническая больница
			N 2 им. братьев Губиных"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">25</font></p>

		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская клиническая больница
			N 3 им. С.М. Кирова"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">26</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская клиническая больница
			N 4 им. В.И. Ленина"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">27</font></p>
		</td>
		<td valign="TOP" width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская клиническая больница
			N 5"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">28</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Детская городская клиническая
			больница N 2"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">29</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская поликлиника N 1"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">30</font></p>

		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская поликлиника N 2"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">31</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская поликлиника N 3"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">32</font></p>
		</td>
		<td valign="TOP" width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская поликлиника N 5"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">33</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская поликлиника N 8 им.
			Н.И. Пирогова"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">34</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская поликлиника N 10"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">35</font></p>

		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Детская городская поликлиника
			N 1"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">36</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Детская городская поликлиника
			N 3"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">37</font></p>
		</td>
		<td valign="TOP" width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Детская городская поликлиника
			N 4"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">38</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Детская городская поликлиника
			N 5"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">39</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Стоматологическая поликлиника
			N 1"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">40</font></p>

		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Стоматологическая поликлиника
			N 2"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">41</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Стоматологическая поликлиника
			N 3"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">42</font></p>
		</td>
		<td valign="TOP" width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Стоматологическая поликлиника
			N 4"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">43</font></p>
		</td>
		<td valign="TOP" width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Клинический родильный дом"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">44</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Центр охраны здоровья семьи и
			репродукции"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">45</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Станция скорой медицинской
			помощи"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">46</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Ахтубинская РБ"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">47</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Володарская РБ"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">48</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Енотаевская РБ"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">49</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Икрянинская РБ"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">50</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Камызякская РБ"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">51</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Красноярская РБ"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">52</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Лиманская РБ"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">53</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Наримановская РБ"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">54</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Приволжская РБ"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">55</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Харабалинская РБ им. Г.В. Храповой"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">56</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Черноярская РБ"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">57</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ГБУЗ
			АО "Городская больница ЗАТО Знаменск"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">58</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Астраханская
			клиническая больница ФГБУЗ ЮОМЦ ФМБА
			России</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">59</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Негосударственное
			учреждение здравоохранения
			"Медико-санитарная часть"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">60</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Негосударственное
			учреждение здравоохранения "Отделенческая
			больница на станции Астрахань-1 ОАО
			"РЖД"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">61</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Госпиталь
			(на 150 коек, г. Ахтубинск) ФГКУ "1602
			ВКГ" Минобороны России</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">62</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Филиал
			N 8 ФГКУ "1602 ВКГ" Минобороны России</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">63</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Филиал
			N 13 ФГКУ "1602 ВКГ" Минобороны России</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">64</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Филиал
			N 12 ФГКУ "1602 ВКГ" Минобороны России</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">65</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФКУЗ
			"МСЧ МВД России по Астраханской
			области"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">66</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФБУ
			Центр реабилитации Фонда социального
			страхования Российской Федерации
			"Тинаки"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">67</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Открытое
			акционерное общество "Новая
			Поликлиника-Астрахань"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">68</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Медицинский центр Альтернатива"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">69</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Стоматология XXI век"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">70</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Специализированное протезно-ортопедическое
			предприятие "Протезист"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">71</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Медиал"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">72</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Мэтр-Дент"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">73</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Аполлония +"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">74</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ИП
			У.С. Уразова</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">75</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФГБУ
			"ФМИЦ им. В.А. Алмазова" Минздрава
			России</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">76</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФГБУ
			"Федеральный центр сердечно-сосудистой
			хирургии" МЗ РФ (г. Астрахань)</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">77</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Астраханский
			филиал ФГБУ "Научно-клинический
			центр оториноларингологии ФМБА России"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">78</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Центр диализа Астрахань"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">79</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФГБУЗ
			"Центральная детская клиническая
			больница ФМБА России"</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">80</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФГБУ
			"РОСНИИ гематологии и трансфузиологии
			ФМБА России"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">81</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФГБУЗ
			"Клиническая больница N 122 им. Л.Г.
			Соколова ФМБА России"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">82</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Травматологический центр "Локохелп"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">83</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Медицинский центр "Локохелп"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">84</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФГБУ
			"Новосибирский НИИ травматологии
			и ортопедии им. Я.Л. Цивьяна" МЗ РФ</font></p>

		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">85</font></p>

		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФГБУ
			"Научный центр акушерства, гинекологии
			и перинатологии им. В.И. Кулакова"
			МЗ РФ</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">86</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"МедЭкс-Астрахань"</font></p>
		</td>
		<td width="116">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">87</font></p>
		</td>
		<td width="462">

			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">Общество
			с ограниченной ответственностью
			"Окулист А"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">88</font></p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">ФКУЗ
			"Медико-санитарная часть N 30 ФСИН
			России"</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">+</font></p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font color="#26282f"><b><font face="Times New Roman, serif">Итого</font></b></font><font face="Times New Roman, serif">

			медицинских организаций, участвующих
			Программе</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">88</font></p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;"><br>
			</p>
		</td>
		<td width="462">
			<p style="text-indent: 0cm;"><font face="Times New Roman, serif">из
			них медицинских организаций,
			осуществляющих деятельность в сфере
			ОМС</font></p>
		</td>
		<td width="116">
			<p style="text-indent: 0cm;" align="CENTER"><font face="Times New Roman, serif">76</font></p>

		</td>
	</tr>
</tbody></table>
<p class="western" style="margin-bottom: 0cm;"><br>
</p>
<p class="western" style="margin-bottom: 0cm;"><a name="sub_14001"></a>
<font color="#26282f"><b><font face="Times New Roman, serif">(+)</font></b></font><font face="Times New Roman, serif">
знак отличия об участии в сфере ОМС.</font>
    
    </div>
	   <tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>