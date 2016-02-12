<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="11"/>  	
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
    
    <h2 align="right">
    Приложение N 11<br/>
к
<a href="step_gosgarant_0.do?infomat=${param.infomat}">Программе</a></h2>
<h4>Перечень<br/> лекарственных
препаратов, отпускаемых населению в
соответствии с перечнем групп населения
и категорий заболеваний, при амбулаторном
лечении которых лекарственные средства
и изделия медицинского назначения
отпускаются по рецептам врачей бесплатно</h4>

<table border="1" bordercolor="#000000" cellpadding="7" cellspacing="0" >
	<col >
	<col >
	<tbody><tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;" align="CENTER">Международное
			непатентованное наименование</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="CENTER">Форма
			выпуска</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;" align="CENTER">1</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="center">2</p>
		</td>
	</tr>
	<tr>

		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Анальгетики,
			нестероидные противовоспалительные
			препараты, средства для лечения
			ревматических заболеваний</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Ненаркотические
			анальгетики и нестероидные
			противовоспалительные средства</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ацетилсалициловая
			кислота</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			таблетки, покрытые кишечно-растворимой
			оболочкой</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Диклофенак-натрий</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			таблетки, покрытые кишечно-растворимой
			оболочкой</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ибупрофен</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			суспензия для приема внутрь</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Кетопрофен</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			таблетки, покрытые кишечно-растворимой
			оболочкой</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Кеторолак</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			раствор для инъекций</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Нимесулид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">суспензия
			для приема внутрь</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Парацетамол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">сироп
			для приема внутрь, суппозитории</p>
		</td>

	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Прочие
			противовоспалительные средства</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Пеницилламин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Сульфасалазин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >

			<p style="text-indent: 0cm;" align="CENTER">Средства,
			применяемые для лечения аллергических
			реакций</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Антигистаминные
			средства</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Кетотифен</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			сироп для приема внутрь</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Клемастин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Лоратадин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Мебгидролин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">драже,
			таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Цетиризин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			капли для приема внутрь</p>
		</td>
	</tr>

	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства,
			влияющие на центральную нервную
			систему</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Противосудорожные
			средства и средства для лечения
			паркинсонизма</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Амантадин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Вальпроевая
			кислота</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			капсулы, сироп, капли для приема внутрь,
			гранулы</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Карбамазепин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Клоназепам</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Леводопа
			+ бенсеразид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы,
			таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Леветирацетам</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ламотриджин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Леводопа
			+ карбидопа</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Пирибедил</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Топирамат</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы,
			таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Тригексифенидил</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Фенитоин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Седативные
			и анксиолитические средства, средства
			для лечения психотических расстройств</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Галоперидол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Диазепам</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Клозапин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Оксазепам</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Перфеназин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			покрытые оболочкой</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Сульпирид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Тиоридазин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Тофизопам</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Феназепам</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Хлорпромазин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			драже</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Хлорпротиксен</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>

		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Антидепрессанты
			и средства нормотимического действия</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Амитриптилин</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Пипофезин</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="LEFT">Средства
			для лечения нарушений сна</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Нитразепам</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>

	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Прочие
			средства, влияющие на центральную
			нервную систему</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Винпоцетин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Гопантеновая
			кислота</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Пирацетам</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Пирацетам
			+ циннаризин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы,
			таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >

			<p style="text-indent: 0cm;" align="CENTER">Средства,
			применяемые для профилактики и лечения
			инфекций</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Антибактериальные
			средства</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Амоксициллин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			капсулы</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Азитромицин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			для приготовления суспензий, капсулы</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Кларитромицин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ко-тримоксазол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Нитроксолин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Нитрофурантоин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Пипемидовая
			кислота</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Рокситромицин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Сульфациламид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">глазные
			капли</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Тетрациклин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">мазь
			глазная</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Фуразидин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Фуразолидон</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Цефазолин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			для раствора для в/в и в/м введения</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ципрофлоксацин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Противовирусные
			средства</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ацикловир</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			мазь</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Арбидол</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			покрытые оболочкой, капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Интерферон
			альфа-2a</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">суппозитории</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Противогрибковые
			средства</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Клотримазол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">крем,
			мазь</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Нистатин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="LEFT">Противопротозойные
			средства</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Метронидазол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>

		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Прочие
			средства профилактики и лечения
			инфекций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Бактисубтил</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Бифидумбактерии
			бифидум</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">сухой
			порошок, таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Противоопухолевые,
			иммунодепрессивные и сопутствующие
			средства</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Меркаптопурин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Метотрексат</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>

	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства,
			влияющие на кровь</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >

			<p style="text-indent: 0cm;" align="CENTER">Противоанемические
			средства</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Железа
			сульфат + аскорбиновая кислота</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Фолиевая
			кислота</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Железа
			(III) полиизомальтозат</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">раствор
			для приема внутрь, сироп, таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства,
			влияющие на сердечно-сосудистую
			систему</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Верапамил</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Дигоксин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Индапамид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Амлодипин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Лизиноприл</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Каптоприл
			+ гидрохлоротиазид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Карведилол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Метопролол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Молсидомин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Нитроглицерин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки
			(капсулы)</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Нифедипин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			таблетки пролонгированного действия</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Периндоприл</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Эналаприл</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства
			для лечения заболеваний желудочно-кишечного
			тракта</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства,
			используемые для лечения заболеваний,
			сопровождающихся эрозивно-язвенными
			процессами в пищеводе, желудке и
			двенадцатиперстной кишке</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Алгедрат
			+ магния гидроксид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">суспензия</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Метоклопрамид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Омепразол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ранитидин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Спазмолитические
			средства</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;" align="CENTER">Дротаверин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>

		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Панкреатические
			энзимы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;" align="CENTER">Панкреатин</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			покрытые оболочкой, капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Панкреатин
			+ желчи компоненты + гемицеллюлаза</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">драже</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Антидиарейные
			средства</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Смектит
			диоктаэдрический</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			для приготовления суспензии для приема
			внутрь в пакетах</p>
		</td>

	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства,
			используемые для лечения заболеваний
			печени и желчевыводящих путей</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Силибинин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">драже</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Урсодеоксихолевая
			кислота</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Фосфолипиды</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >

			<p style="text-indent: 0cm;" align="CENTER">Гормоны
			и средства, влияющие на эндокринную
			систему</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Неполовые
			гормоны, синтетические субстанции и
			антигормоны</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Дексаметазон</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Метилпреднизолон</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Преднизолон</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Триамцинолон</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>

	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Инсулин
			и средства, используемые при сахарном
			диабете</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Глибенкламид</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Глибенкламид
			+ метформин</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Гликвидон</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Гликлазид</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Глимепирид</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Метформин</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Инсулин
			двухфазный (человеческий генноинженерный)</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">суспензия
			для подкожного введения</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Инсулин-изофан
			(человеческий генноинженерный)</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">суспензия
			для подкожного введения</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Инсулин
			растворимый (человеческий генноинженерный</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для подкожного введения</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Инсулин
			лизпро</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для подкожного введения</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Инсулин
			аспарт</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Инсулин
			аспарт двухфазный</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Инсулин
			гларгин</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Инсулин
			глулизин</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Инсулин
			детемир</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Тиоктовая
			кислота</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Росиглитазон</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства
			для лечения заболеваний почек и
			мочевыводящих путей</p>

		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Диуретики</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Ацетазоламид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Гидрохлоротиазид
			+ триамтерен</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Гидрохлоротиазид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Спиронолактон</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Фуросемид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>

		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства,
			использующиеся при офтальмологических
			заболеваниях</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Пилокарпин</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			(глазные капли)</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Пилокарпин
			+ тимолол</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			(глазные капли)</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Таурин</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			(глазные капли)</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Тимолол</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			(глазные капли)</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Метилэтилпиридинол</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			(глазные капли)</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства,
			влияющие на органы дыхания</p>
		</td>

	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Противоастматические
			средства</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Беклометазон</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">аэрозоль
			для ингаляций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Ипратропия
			бромид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">аэрозоль
			для ингаляций дозированный</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Ипратропия
			бромид + фенотерол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">аэрозоль
			для ингаляций, раствор</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Будесонид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">аэрозоль,
			суспензия для ингаляций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Будесонид
			+ формотерол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			для ингаляций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Сальбутамол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">аэрозоль
			для ингаляций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Сальметерол
			+ флутиказон</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">аэрозоль,
			порошок для ингаляций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Тиотропия
			бромид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы
			с порошком для ингаляций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Формотерол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			для ингаляций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Фенотерол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">аэрозоль
			для ингаляций, раствор</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >

			<p style="text-indent: 0cm;" align="CENTER">Прочие
			препараты для лечения заболеваний
			органов дыхания, не обозначенные в
			других рубриках</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Амброксол</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">раствор,
			сироп для приема внутрь, таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Аминофиллин</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ацетилцистеин</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки
			шипучие для приема внутрь</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Бромгексин</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">сироп</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Теофиллин</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			капсулы</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Витамины</p>
		</td>
	</tr>

	<tr valign="BOTTOM">
		<td >
			<p style="text-indent: 0cm;" align="LEFT">Железа
			[III] гидроксид полимальтозат</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капли
			для приема внутрь</p>
		</td>
	</tr>

	<tr valign="BOTTOM">
		<td >
			<p style="text-indent: 0cm;" align="LEFT">Железа
			[III] гидроксид полимальтозат</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">сироп</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;" align="LEFT">Калия
			и магния аспарагинат</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			покрытые оболочкой</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;" align="LEFT">Поливитамины</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">сироп</p>
		</td>
	</tr>

	<tr valign="BOTTOM">
		<td >
			<p style="text-indent: 0cm;" align="LEFT">Левокарнитин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для приема внутрь</p>
		</td>
	</tr>

	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Прочие
			лекарственные средства</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Аминоуксусная
			кислота (глицин)</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Кальция
			глюконат</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Метамизол
			в комбинации с другими препаратами</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Мяты
			перечной масло + фенобарбитал +
			этилбромизовалерианат</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капли
			для приема внутрь</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Парацетамол
			+ метамизол натрия + кодеин + кофеин +
			фенобарбитал</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Пентоксифиллин</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			покрытые оболочкой</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Троксерутин</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">гель,
			капсулы</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Изделия
			медицинского назначения</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Шприц
			инсулиновый</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT"><br>

			</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Иглы
			для шприц-ручек</p>
		</td>
		<td >

			<p class="western" style="text-indent: 0cm;" align="LEFT"><br>
			</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Тест-полоски
			для определения сахара крови с помощью
			глюкометра</p>
		</td>

		<td >
			<p class="western" style="text-indent: 0cm;" align="LEFT"><br>
			</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Антихолинэстеразные
			средства</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Пиридостигмина
			бромид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Ненаркотические
			анальгетики и нестероидные
			противовоспалительные средства</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Мелоксикам</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>

		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Опиоидные
			анальгетики и анальгетик смешанного
			действия</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Морфин</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Трамадол</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для инъекций, таблетки, капсулы,
			суппозитории</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Тримеперидин</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Фентанил</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">трансдермальная
			терапевтическая система</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Прочие
			противовоспалительные средства</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Лефлуномид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			покрытые оболочкой</p>
		</td>

	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Седативные
			и анксиолитические средства, средства
			для лечения психотических расстройств</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Рисперидон</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Сульпирид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >

			<p style="text-indent: 0cm;" align="CENTER">Антидепрессанты
			и средства нормотимического действия</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Сертралин</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Флуоксетин</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Прамипексол</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства
			для лечения рассеянного склероза</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Интерферон
			бета-1a</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			лиофилизированный для приготовления
			раствора для инъекций 30 мкг/мл во
			флаконе в комплекте с растворителем</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Интерферон
			бета-1b</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			лиофилизированный для приготовления
			раствора для инъекций 9,6 млн МЕ во
			флаконе в комплекте с растворителем</p>
		</td>
	</tr>

	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Прочие
			средства, влияющие на центральную
			нервную систему</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Бетагистина
			дигидрохлорид</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Полипептиды
			коры головного мозга скота</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			лиофилизированный для приготовления
			для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Церебролизин</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для инъекций</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Антибактериальные
			средства</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Бензатина
			бензилпенициллина</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			лиофилизированный для приготовления
			суспензий для в/м введения</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Амоксициллин
			+ клавуланат</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			лиофилизированный для приготовления
			суспензий для приема внутрь, таблетки</p>

		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Противотуберкулезные
			средства</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Изониазид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Канамицин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			для приготовления раствора для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Пиразинамид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Протионамид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки,
			покрытые оболочкой</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Рифампицин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Стрептомицин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			для приготовления раствора для инъекций</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Этамбутол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>

		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Противогрибковые
			средства</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Флуконазол</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Прочие
			средства профилактики и лечения
			инфекций</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Хилак
			форте</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для приема внутрь</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Лизатов
			бактерий смесь</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">аэрозоль
			назальный, капсулы</p>
		</td>

	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Цитостатические
			средства</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Азатиоприн</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Гидроксикарбамид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Капецитабин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Темозоломид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Хлорамбуцил</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Сандимун
			неорал</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >

			<p style="text-indent: 0cm;">Циклофосфамид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">порошок
			для приготовления раствора для в/м и
			в/в введения</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >

			<p style="text-indent: 0cm;" align="CENTER">Гормоны
			и антигормоны</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Анастрозол</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Бикалутамид</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Гозерелин</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">капсулы-депо
			для инъекций в шприц-ампулах</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Летрозол</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Медроксипрогестерон</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Тамоксифен</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Флутамид</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ципротерон</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Эксиместан</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Тоцилизумаб</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">раствор</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Эверолимус</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Такролимус</p>
		</td>
		<td >

			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Антисептики</p>
		</td>
	</tr>

	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Этанол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			70%</p>
		</td>
	</tr>

	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Неполовые
			гормоны, синтетические субстанции и
			антигормоны</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Десмопрессин</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Кломифен</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Левотироксин
			натрия</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Триамцинолон</p>

		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Эстрогены
			и гестагены</p>

		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Этинилэстрадиол</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>

		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства
			для лечения аденомы простаты</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Тамсулозин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капсулы</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Теразозин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">

		<td >
			<p style="text-indent: 0cm;">Финастерид</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>

		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Средства,
			влияющие на сердечно-сосудистую
			систему</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Розувастатин</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Клопидогрел</p>
		</td>

		<td >
			<p style="text-indent: 0cm;" align="LEFT">таблетки</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" valign="TOP" >
			<p style="text-indent: 0cm;" align="CENTER">Прочие
			лекарственные средства</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Ксилометазолин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капли
			назальные</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Левокарнитин</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">раствор
			для внутреннего применения</p>
		</td>

	</tr>
	<tr valign="TOP">
		<td >
			<p style="text-indent: 0cm;">Эхинацеи
			сок</p>
		</td>
		<td >
			<p style="text-indent: 0cm;" align="LEFT">капли</p>
		</td>

	</tr>
</tbody></table>
<p class="western" style="margin-bottom: 0cm;">
    
    
    </div>
	   <tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>