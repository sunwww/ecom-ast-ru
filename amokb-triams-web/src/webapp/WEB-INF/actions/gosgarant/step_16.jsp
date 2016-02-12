<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="16"/>  	
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
    
    <h2 align="right">Приложение
N 16<br/><br/>
к
<a href="step_gosgarant_0.do?infomat=${param.infomat}">Программе</a></h2>
<h4>Условия<br>размещения
пациентов в маломестных палатах (боксах)
по медицинским и (или) эпидемиологическим
показаниям, установленным Министерством
здравоохранения Российской Федерации</h4>

<p class="western" style="margin-bottom: 0cm;"><br>
</p>
<p class="western" style="margin-bottom: 0cm;">К
медицинским показаниям к размещению
пациентов в маломестных палатах (боксах)
относятся показания, утвержденные
приказом
Министерства здравоохранения и
социального развития Российской
Федерации от 15.05.2012 N 535н "Об утверждении
перечня медицинских и эпидемиологических
показаний к размещению пациентов в
маломестных палатах (боксах)":</p>
<p class="western" style="margin-bottom: 0cm;"><br>
</p>
<table border="1" >
	<col width="50">
	<col width="434">
	<col width="144">
	<tbody><tr>

		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">N
			п/п</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;" align="CENTER">Наименование
			показаний</p>
		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">Код
			диагноза по МКБ-Х</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">1.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Болезнь,
			вызванная вирусом иммунодефицита
			человека (ВИЧ)</p>

		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">В20 - В24</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">2.</p>

		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Кистозный
			фиброз (муковисцидоз)</p>
		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">Е
			84</p>
		</td>
	</tr>

	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">3.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Злокачественные
			новообразования лимфоидной, кроветворной
			и родственных тканей</p>
		</td>
		<td width="144">

			<p style="text-indent: 0cm;" align="CENTER">С
			81 - С 96</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">4.</p>
		</td>
		<td width="434">

			<p style="text-indent: 0cm;">Термические
			и химические ожоги</p>
		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">Т
			2 - Т 32</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER">5.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Заболевания,
			вызванные метициллин (оксациллин)-резистентным
			золотистым стафилококком или
			ванкомицинрезистентным энтерококком:</p>
		</td>
		<td width="144">
			<p class="western" style="text-indent: 0cm;" align="LEFT"><br>
			</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">5.1.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Пневмония</p>

		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">J
			15.2, J
			15.8</p>
		</td>
	</tr>
	<tr>
		<td width="50">

			<p style="text-indent: 0cm;" align="CENTER">5.2.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Менингит</p>
		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">G
			00.3, G
			00.8</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">5.3.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Остеомиелит</p>

		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">M
			86, В
			95.6, В
			96.8</p>
		</td>
	</tr>
	<tr>

		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">5.4.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Острый
			и подострый инфекционный эндокардит</p>
		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">I
			33.0</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">5.5.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Инфекционно-токсический
			шок</p>

		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">А
			48.3</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">5.6.</p>

		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Сепсис</p>
		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">А
			41.0, А
			41.8</p>
		</td>

	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">6.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Недержание
			кала (энкопрез)</p>
		</td>

		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">R
			15, F
			98.1</p>
		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">7.</p>

		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Недержание
			мочи</p>
		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">R
			32, N
			39.3, N
			39.4</p>

		</td>
	</tr>
	<tr>
		<td width="50">
			<p style="text-indent: 0cm;" align="CENTER">8.</p>
		</td>
		<td width="434">
			<p style="text-indent: 0cm;">Заболевания,
			сопровождающиеся тошнотой и рвотой</p>

		</td>
		<td width="144">
			<p style="text-indent: 0cm;" align="CENTER">R
			11</p>
		</td>
	</tr>
</tbody></table>
<p class="western" style="margin-bottom: 0cm;"><br>
</p>
<p class="western" style="margin-bottom: 0cm;">Эпидемиологическими
показаниями к размещению пациентов в
маломестных палатах (боксах) являются
инфекционные и паразитарные болезни
согласно кодам МКБ-Х: А00-А99,

В00-В19,
В25-В83,
В85-В99.</p>
<p class="western" style="margin-bottom: 0cm;">
    
    </div>
	   <tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>