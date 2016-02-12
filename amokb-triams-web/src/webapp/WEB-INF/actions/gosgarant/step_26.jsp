<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="26"/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Программа гос. гарантий бесплатного оказания гражданам мед. помощи на территории АО на 2015 год</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <h2 align="right">
    Приложение N 22<br/>
к
<a href="step_gosgarant_0.do?infomat=${param.infomat}">Программе</a></h2>

<h4>Раздел 	II. Перечень видов высокотехнологичной 	медицинской помощи, не включенных в
	территориальную программу обязательного 	медицинского страхования, финансовое
	обеспечение которых осуществляется	за счет средств, предоставляемых
	федеральному бюджету из бюджета	Федерального фонда обязательного
	медицинского страхования в виде иных	межбюджетных трансфертов в соответствии
	с федеральным законом о бюджете	Федерального фонда обязательного
	медицинского страхования на очередной 	финансовый год и на плановый период</h4>

	<p class="western"><br>
	</p>
	<table border="1" bordercolor="#000000" cellpadding="7" cellspacing="0">


		<tbody><tr valign="TOP">
			<td>
				<p align="CENTER">N
				группы ВМП</p>
			</td>
			<td>
				<p align="CENTER">Наименование
				вида ВМП</p>
			</td>
			<td>

				<p align="CENTER">Коды
				по МКБ-10</p>
			</td>
			<td>
				<p align="CENTER">Модель
				пациента</p>
			</td>
			<td>
				<p align="CENTER">Вид
				лечения</p>

			</td>
			<td>
				<p align="CENTER">Метод
				лечения</p>
			</td>
		</tr>
		<tr>
			<td colspan="6" valign="TOP" width="1076">
				<p align="CENTER">Онкология продолжение</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p align="CENTER">7</p>
			</td>
			<td>
				<p>Реконструктивно-пластические,
				микрохирургические, обширные
				циторедуктивные, расширенно-комбинированные
				хирургические вмешательства, в том
				числе с применением физических
				факторов при злокачественных
				новообразованиях</p>
			</td>

			<td>
				<p align="CENTER">C00.0, C00.1, C00.2, C00.3, C00.4, C00.5, C00.6, C00.8, C00.9, C01.0, C01.9, C02, C03.1, C03.9, C04.0, C04.1, C04.8, C04.9, C05, C06.0, C06.1, C06.2, C06.8, C06.9, C07.0, C07.9, C08.0, C08.1, C08.8, C08.9, C09.0, C09.1, C09.8, C09.9, C10.0, C10.1, C10.2, C10.3, C10.4, C10.8, C10.9, C11.0, C11.1, C11.2, C11.3, C11.8, C11.9, C12.0, C12.9, C13.0, C13.1, C13.2, C13.8, C13.9, C14.0, C14.1, C14.2, C14.8, C15.0, C30.0, C30.1, C31.0, C31.1, C31.2, C31.3, C31.8, C31.9, C32.0, C32.1, C32.2, C32.3, C32.8, C32.9, C33.0, C43.0 - C43.9, C44.0 - C44.9, C49.0, C69, C73.0, C73.1, C73.2, C73.3, C73.8, C73.9</p>

			</td>
			<td>
				<p>Опухоли
				головы и шеи, первичные и рецидивные,
				метастатические опухоли центральной
				нервной системы</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">поднакостничная
				экзентерация орбиты</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">поднакостничная
				экзентерация орбиты с сохранением
				век</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">орбитосинуальная
				экзентерация</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				опухоли орбиты темпоральным доступом</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				опухоли орбиты транзигоматозным
				доступом</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">транскраниальная
				верхняя орбитотомия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">орбитотомия
				с ревизией носовых пазух</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">органосохраняющее
				удаление опухоли орбиты</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">реконструкция
				стенок глазницы</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">пластика
				верхнего неба</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">глосэктомия
				с реконструктивно-пластическим
				компонентом</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				ротоглотки комбинированная с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">фарингэктомия
				комбинированная с реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">иссечение
				новообразования мягких тканей с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">резекция
				верхней или нижней челюсти с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">резекция
				губы с реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">резекция
				черепно-лицевого комплекса с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">паротидэктомия
				радикальная с реконструктивно-пластическим
				компонентом</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				твердого неба с реконструктивно-пластическим
				компонентом</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				глотки с реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">ларингофарингэктомия
				с реконструкцией перемещенным лоскутом</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				ротоглотки комбинированная с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">резекция
				дна полости рта комбинированная с
				микрохирургической пластикой</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">ларингофарингоэзофагэктомия
				с реконструкцией висцеральными
				лоскутами</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">резекция
				твердого неба с микрохирургической
				пластикой</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				гортани с реконструкцией посредством
				имплантата или биоинженерной
				реконструкцией</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">ларингофарингэктомия
				с биоинженерной реконструкцией</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">ларингофарингэктомия
				с микрососудистой реконструкцией</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				нижней челюсти с микрохирургической
				пластикой</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				ротоглотки комбинированная с
				микрохирургической реконструкцией</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">тиреоидэктомия
				с микрохирургической пластикой</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">резекция
				верхней челюсти с микрохирургической
				пластикой</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">лимфаденэктомия
				шейная расширенная с ангиопластикой</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				черепно-глазнично-лицевого комплекса
				с микрохирургической пластикой</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">иссечение
				новообразования мягких тканей с
				микрохирургической пластикой</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				черепно-лицевого комплекса с
				микрохирургической пластикой</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				внеорганной опухоли с комбинированной
				резекцией соседних органов</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				внеорганной опухоли с ангиопластикой</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">удаление
				внеорганной опухоли с пластикой
				нервов</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">резекция
				грушевидного синуса с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">фарингэктомия
				комбинированная с микрососудистой
				реконструкцией</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				глотки с микрососудистой реконструкцией</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">пластика
				трахеи биоинженерным лоскутом</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">реконструкция
				и пластика трахеостомы и фарингостомы
				с отсроченным трахеопищеводным
				шунтированием и голосовым протезированием</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">расширенная
				ларингофарингэктомия с
				реконструктивно-пластическим
				компонентом и одномоментным
				трахеопищеводным шунтированием и
				голосовым протезированием</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">ларингэктомия
				с пластическим оформлением трахеостомы</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">отсроченная
				микрохирургическая пластика (все
				виды)</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">резекция
				ротоглотки комбинированная</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">удаление
				опухоли головного мозга с
				краниоорбитофациальным ростом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				опухоли головы и шеи с интракраниальным
				ростом</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C15</p>
			</td>
			<td>
				<p>Начальные,
				локализованные и местнораспространенные
				формы злокачественных новообразований
				пищевода</p>
			</td>

			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">отсроченная
				пластика пищевода желудочным стеблем</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">отсроченная
				пластика пищевода сегментом толстой
				кишки</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">отсроченная
				пластика пищевода сегментом тонкой
				кишки</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">отсроченная
				пластика пищевода с микрохирургической
				реваскуляризацией трансплантата</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">одномоментная
				эзофагэктомия или субтотальная
				резекция пищевода с лимфаденэктомией,
				интраоперационной фотодинамической
				терапией и пластикой пищевода</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C17</p>
			</td>
			<td>

				<p>Местнораспространенные
				и диссеминированные формы злокачественных
				новообразований двенадцатиперстной
				и тонкой кишки</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">панкреатодуоденальная
				резекция с интраоперационной
				фотодинамической терапией</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">циторедуктивная
				резекция тонкой кишки с интраоперационной
				фотодинамической терапией или
				внутрибрюшной гипертермической
				химиотерапией</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Местнораспространенные
				и метастатические формы первичных и
				рецидивных злокачественных
				новообразований ободочной, сигмовидной,
				прямой кишки и ректосигмоидного
				соединения II - IV стадии</p>
			</td>

			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">левосторонняя
				гемиколэктомия с резекцией печени</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">левосторонняя
				гемиколэктомия с резекцией легкого</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">резекция
				сигмовидной кишки с резекцией печени</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">резекция
				сигмовидной кишки с резекцией легкого</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">тотальная
				экзентерация малого таза</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">задняя
				экзентерация малого таза</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">расширенная,
				комбинированная брюшно-анальная
				резекция прямой кишки</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				прямой кишки с резекцией легкого</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">расширенно-комбинированная
				брюшно-промежностная экстирпация
				прямой кишки</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">брюшно-промежностная
				экстирпация прямой кишки с формированием
				неосфинктера и толстокишечного
				резервуара</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">тотальная
				экзентерация малого таза с реконструкцией
				сфинктерного аппарата прямой кишки
				и толстокишечного резервуара, а также
				пластикой мочевого пузыря</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C20</p>

			</td>
			<td>
				<p>Локализованные
				опухоли средне- и нижнеампулярного
				отдела прямой кишки</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">сфинктеросохраняющие
				низкие внутрибрюшные резекции прямой
				кишки с реконструкцией сфинктерного
				аппарата и (или) формированием
				толстокишечных резервуаров</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C22,
				C23,
				C24</p>

			</td>
			<td>
				<p>Местнораспространенные
				первичные и метастатические опухоли
				печени</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">анатомическая
				резекция печени</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">правосторонняя
				гемигепатэктомия</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">левосторонняя
				гемигепатэктомия</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">медианная
				резекция печени</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">двухэтапная
				резекция печени</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="CENTER">C25</p>
			</td>
			<td>
				<p>Резектабельные
				опухоли поджелудочной железы</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">панкреатодуоденальная
				резекция</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">пилоруссберегающая
				панкреато-дуоденальная резекция</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">срединная
				резекция поджелудочной железы</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">тотальная
				дуоденопанкреатэктомия</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">расширенно-комбинированная
				панкреатодуоденальная резекция</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">расширенно-комбинированная
				пилоруссберегающая панкреатодуоденальная
				резекция</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">расширенно-комбинированная
				срединная резекция поджелудочной
				железы</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">расширенно-комбинированная
				тотальная дуоденопанкреатэктомия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">расширенно-комбинированная
				дистальная гемипанкреатэктомия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C33</p>

			</td>
			<td>
				<p align="LEFT">Опухоль
				трахеи</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">расширенная,
				комбинированная циркулярная резекция
				трахеи с формированием межтрахеального
				или трахеогортанного анастомозов</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">расширенная,
				комбинированная циркулярная резекция
				трахеи с формированием концевой
				трахеостомы</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">пластика
				трахеи (ауто-аллопластика, использование
				свободных микрохирургических,
				перемещенных и биоинженерных лоскутов)</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="CENTER">C34</p>
			</td>
			<td>
				<p align="LEFT">Опухоли
				легкого I - III стадии</p>
			</td>
			<td>

				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">изолированная
				(циркулярная) резекция бронха
				(формирование межбронхиального
				анастомоза)</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">комбинированная
				пневмонэктомия с циркулярной резекцией
				бифуркации трахеи (формирование
				трахео-бронхиального анастомоза)</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">комбинированная
				лобэктомия (билобэктомия, пневмонэктомия)
				с резекцией, пластикой
				(алло-аутотрасплантатом, перемещенным
				биоинженерным лоскутом) грудной
				стенки</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">расширенные
				лоб-билобэктомии, пневмонэктомия,
				включая билатеральную медиастинальную
				лимфаденэктомию</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">лоб-,
				билоб-, пневмонэктомия с медиастинальной
				лимфаденэктомией и интраоперационной
				фотодинамической терапией</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C37,
				C08.1,
				C38.2,
				C38.3,
				C78.1</p>

			</td>
			<td>
				<p>Опухоль
				вилочковой железы III стадии, опухоль
				переднего, заднего средостения,
				местнораспространенные формы,
				метастатическое поражение средостения</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">удаление
				опухоли средостения с интраоперационной
				фотодинамической терапией</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C38.4,
				C38.8,
				C45,
				C78.2</p>

			</td>
			<td>
				<p>Опухоль
				плевры. Распространенное поражение
				плевры. Мезотелиома плевры.
				Метастатическое поражение плевры</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">плевропневмонэктомия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">тотальная
				плеврэктомия с гемиперикардэктомией,
				резекцией диафрагмы</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">тотальная
				плеврэктомия или плевропневмонэктомия
				с интраоперационной фотодинамической
				терапией, гипертермической хемоперфузией</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="CENTER">C39.8,
				C41.3,
				C49.3</p>
			</td>
			<td>
				<p>Опухоли
				грудной стенки (мягких тканей, ребер,
				грудины, ключицы)</p>

			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">удаление
				опухоли грудной стенки с экзартикуляцией
				ребер, ключицы и пластикой дефекта
				грудной стенки местными тканями</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				опухоли грудной стенки с экзартикуляцией
				ребер, ключицы и пластикой обширного
				дефекта мягких тканей, каркаса грудной
				стенки ауто-, алломатериалами,
				перемещенными, биоинженерными
				лоскутами</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				опухоли грудной стенки с экзартикуляцией
				ребер, ключицы и резекцией соседних
				органов и структур (легкого, мышечной
				стенки пищевода, диафрагмы, перикарда,
				верхней полой вены, адвентиции аорты
				и др.)</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C40.0,
				C40.1,
				C40.2,
				C40.3,
				C40.8,
				C40.9,
				C41.2,
				C41.3,
				C41.4,
				C41.8,
				C41.9,
				C79.5,
				C43.5</p>

			</td>
			<td>
				<p>Первичные
				злокачественные новообразования
				костей и суставных хрящей туловища
				и конечностей Ia-b, IIa-b, IVa-b стадии.
				Метастатические новообразования
				костей, суставных хрящей туловища и
				конечностей</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">резекция
				кости с микрохирургической реконструкцией</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				грудной стенки с микрохирургической
				реконструкцией</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				злокачественного новообразования
				кости с микрохирургической реконструкцией
				нерва</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">стабилизирующие
				операции на позвоночнике передним
				доступом</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				кости с реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				лопатки с реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">экстирпация
				ребра с реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">экстирпация
				лопатки с реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">экстирпация
				ключицы с реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				костей таза комбинированная с
				реконструктивно-пластическим
				компонентом</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">ампутация
				межподвздошно-брюшная с пластикой</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				позвонка с эндопротезированием и
				фиксацией</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				лонной и седалищной костей с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">резекция
				костей верхнего плечевого пояса с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">экстирпация
				костей верхнего плечевого пояса с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">резекция
				костей таза комбинированная с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">удаление
				злокачественного новообразования
				кости с протезированием артерии</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p>Местнораспространенные
				формы первичных и метастатических
				злокачественных опухолей длинных
				трубчатых костей</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">изолированная
				гипертермическая регионарная
				химиоперфузия конечностей</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C43,
				C43.5,
				C43.6,
				C43.7,
				C43.8,
				C43.9,
				C44,
				C44.5,
				C44.6,
				C44.7,
				C44.8,
				C44.9</p>

			</td>
			<td>
				<p>Злокачественные
				новообразования кожи</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">широкое
				иссечение меланомы кожи с пластикой
				дефекта кожно-мышечным лоскутом на
				сосудистой ножке</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">широкое
				иссечение опухоли кожи с
				реконструктивно-пластическим
				компонентом комбинированное (местные
				ткани и эспандер)</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Местнораспространенные
				формы первичных и метастатических
				меланом кожи конечностей</p>

			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">изолированная
				гипертермическая регионарная
				химиоперфузия конечностей</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="CENTER">C48</p>
			</td>
			<td>
				<p>Местнораспространенные
				и диссеминированные формы первичных
				и рецидивных неорганных опухолей
				забрюшинного пространства</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">удаление
				первичных и рецидивных неорганных
				забрюшинных опухолей с ангиопластикой</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">удаление
				первичных и рецидивных неорганных
				забрюшинных опухолей с
				реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p>Местнораспространенные
				формы первичных и метастатических
				опухолей брюшной стенки</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">удаление
				первичных, рецидивных и метастатических
				опухолей брюшной стенки с
				реконструктивно-пластическим
				компонентом</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C49.1,
				C49.2,
				C49.3,
				C49.5,
				C49.6,
				C47.1,
				C47.2,
				C47.3,
				C47.5,
				C43.5</p>

			</td>
			<td>
				<p>Первичные
				злокачественные новообразования
				мягких тканей туловища и конечностей,
				злокачественные новообразования
				периферической нервной системы
				туловища, нижних и верхних конечностей
				Ia-b, IIa-b, III, IVa-b стадии</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">иссечение
				новообразования мягких тканей с
				микрохирургической пластикой</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p>Местнораспространенные
				формы первичных и метастатических
				сарком мягких тканей конечностей</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">изолированная
				гипертермическая регионарная
				химиоперфузия конечностей</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C50,
				C50.1,
				C50.2,
				C50.3,
				C50.4,
				C50.5,
				C50.6,
				C50.8,
				C50.9</p>

			</td>
			<td>
				<p>Злокачественные
				новообразования молочной железы 0 -
				IV стадии</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">радикальная
				мастэктомия по Маддену, Пейти - Дайсену,
				Холстеду - Майеру с пластикой
				подмышечно-подключично-подлопаточной
				области композитным мышечным
				трансплантатом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">радикальная
				мастэктомия по Маддену, Пейти - Дайсену,
				Холстеду - Майеру с перевязкой
				лимфатических сосудов
				подмышечно-подключично-подлопаточной
				области с использованием микрохирургической
				техники</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">радикальная
				мастэктомия по Маддену, Пейти - Дайсену,
				Холстеду - Майеру с пластикой
				кожно-мышечным лоскутом прямой мышцы
				живота и использованием микрохирургической
				техники</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">подкожная
				мастэктомия (или субтотальная
				радикальная резекция молочной железы)
				с одномоментной маммопластикой
				широчайшей мышцей спины или широчайшей
				мышцей спины в комбинации с эндопротезом</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">подкожная
				мастэктомия (или субтотальная
				радикальная резекция молочной железы)
				с одномоментной маммопластикой
				широчайшей мышцей спины и (или) большой
				грудной мышцей в комбинации с
				эндопротезом</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">подкожная
				мастэктомия (или субтотальная
				радикальная резекция молочной железы)
				с одномоментной маммопластикой
				кожно-мышечным лоскутом прямой мышцы
				живота или кожно-мышечным лоскутом
				прямой мышцы живота в комбинации с
				эндопротезом, в том числе с применением
				микрохирургической техники</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">радикальная
				расширенная модифицированная
				мастэктомия с закрытием дефекта
				кожно-мышечным лоскутом прямой мышцы
				живота, в том числе с применением
				микрохирургической техники</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">подкожная
				радикальная мастэктомия с одномоментной
				пластикой эндопротезом и сетчатым
				имплантатом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">мастэктомия
				радикальная расширенная модифицированная
				с пластическим закрытием дефекта
				грудной стенки различными вариантами
				кожно-мышечных лоскутов</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C51</p>
			</td>
			<td>

				<p>Злокачественные
				новообразования вульвы I - III стадии</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">расширенная
				вульвэктомия с реконструктивно-пластическим
				компонентом</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">вульвэктомия
				с двухсторонней расширенной
				подвздошно-паховой лимфаденэктомией
				и интраоперационной фотодинамической
				терапией</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">вульвэктомия
				с определением сторожевых лимфоузлов
				и расширенной лимфаденэктомией</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">вульвэктомия
				с двухсторонней подвздошно-паховой
				лимфаденэктомией</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="CENTER">C52</p>
			</td>
			<td>
				<p>Злокачественные
				новообразования влагалища II - III стадии</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">удаление
				опухоли влагалища с реконструктивно-пластическим
				компонентом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">удаление
				опухоли влагалища с резекцией смежных
				органов, пахово-бедренной лимфаденэктомией</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C53</p>
			</td>

			<td>
				<p>Злокачественные
				новообразования шейки матки</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">радикальная
				абдоминальная трахелэктомия</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">радикальная
				влагалищная трахелэктомия с
				видеоэндоскопической тазовой
				лимфаденэктомией</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">расширенная
				экстирпация матки с парааортальной
				лимфаденэктомией, резекцией смежных
				органов</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">нервосберегающая
				расширенная экстирпация матки с
				придатками и тазовой лимфаденэктомией</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">нервосберегающая
				расширенная экстирпация матки с
				транспозицией яичников и тазовой
				лимфаденэктомией</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">расширенная
				экстирпация матки с придатками или
				с транспозицией яичников после
				предоперационной лучевой терапии</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C54</p>

			</td>
			<td>
				<p>Злокачественные
				новообразования тела матки
				(местнораспространенные формы).
				Злокачественные новообразования
				эндометрия IA - III</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">расширенная
				экстирпация матки с парааортальной
				лимфаденэктомией и субтотальной
				резекцией большого сальника</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p>стадии
				с осложненным соматическим статусом
				(тяжелая степень ожирения, тяжелая
				степень сахарного диабета и т.д.)</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">нервосберегающая
				экстирпация матки с придатками, с
				верхней третью влагалища и тазовой
				лимфаденкэтомией</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">экстирпация
				матки с транспозицией яичников и
				тазовой лимфаденэктомией</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">экстирпация
				матки с придатками, верхней третью
				влагалища, тазовой лимфаденэктомией
				и интраоперационной лучевой терапией</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="CENTER">C56</p>
			</td>
			<td>
				<p>Злокачественные
				новообразования яичников I - IV стадии.
				Рецидивы злокачественных новообразований
				яичников</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">экстирпация
				матки с придатками, субтотальная
				резекция большого сальника с
				интраоперационной флюоресцентной
				диагностикой и фотодинамической
				терапией</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C53,
				C54,
				C56,
				C57.8</p>

			</td>
			<td>
				<p>Рецидивы
				злокачественных новообразований
				тела матки, шейки матки и яичников</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">тазовые
				эвисцерации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C60</p>
			</td>
			<td>

				<p>Рак
				полового члена I - IV стадии</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">резекция
				полового члена с пластикой</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C61</p>
			</td>
			<td>
				<p>Рак
				предстательной железы II стадии
				(T1c-2bN 0M0), уровень ПСА менее 10 нг/мл,
				сумма баллов по Глисону менее 7</p>
			</td>

			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">радикальная
				простатэктомия промежностным доступом</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p>Рак
				предстательной железы II стадии,
				T1b-T2cN xMo</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT"><br>
				</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p>Рак
				предстательной железы II - III стадии
				(T1c-2bN 0M0) с высоким риском регионарного
				метастазирования</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p class="western" align="LEFT"><br>
				</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p>Локализованный
				рак предстательной железы I - II стадии,
				T1-2cN 0M0</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p class="western" align="LEFT"><br>
				</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C64</p>
			</td>

			<td>
				<p>Рак
				единственной почки с инвазией в
				лоханку почки</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">резекция
				почечной лоханки с пиелопластикой</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Злокачественные
				новообразования почки III - IV стадии</p>

			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">радикальная
				нефрэктомия с расширенной забрюшинной
				лимфаденэктомией</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Рак
				почки
				I - III стадии

				T1a-T3aN xMo</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">радикальная
				нефрэктомия с резекцией соседних
				органов</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				рецидивной опухоли почки с расширенной
				лимфаденэктомией</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				рецидивной опухоли почки с резекцией
				соседних органов</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="CENTER">C67</p>
			</td>
			<td>
				<p>Рак
				мочевого пузыря I - IV стадии</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">цистпростатвезикулэктомия
				с пластикой мочевого резервуара
				сегментом тонкой кишки</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">передняя
				экзентерация таза</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C74</p>
			</td>

			<td>
				<p>Рак
				надпочечника I - III стадии (T1a-T3aN xMo)</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">лапароскопическое
				удаление рецидивной опухоли надпочечника
				с расширенной лимфаденэктомией</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">удаление
				рецидивной опухоли надпочечника с
				резекцией соседних органов</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Рак
				надпочечника III - IV стадии</p>
			</td>

			<td>
				<p align="LEFT">хирургическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">лапароскопическая
				расширенная адреналэктомия или
				адреналэктомия с резекцией соседних
				органов</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p align="CENTER">8</p>
			</td>
			<td>
				<p>Комбинированное
				лечение злокачественных новообразований,
				сочетающее обширные хирургические
				вмешательства и лекарственное
				противоопухолевое лечение, требующее
				интенсивной поддерживающей и
				корригирующей терапии</p>
			</td>
			<td>
				<p align="CENTER">C00, C01, C02, C03, C04, C05.0, C05, C06, C07, C08, C09, C10, C11, C12, C13, C14, C15.0, C30, C31, C32, C33, C43, C44, C49.0, C69, C73</p>

			</td>
			<td>
				<p>Злокачественные
				новообразования головы и шеи T3-4,
				рецидив</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>

				<p align="LEFT">внутриартериальная
				или системная предоперационная
				полихимиотерапия с последующей
				операцией в течение одной госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C16</p>
			</td>
			<td>

				<p>Местнораспространенный
				рак желудка (T2N 2M0, T3N 1M0, T4N 0M0, T3N 2M0, T4N
				1-3M0-1) после операций в объеме R0</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение с последующим курсом
				химиотерапии в течение одной
				госпитализации</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C18,
C19,
C20</p>
			</td>
			<td>

				<p>Местнораспространенный
				колоректальный рак T1-2N 1M0, T3-4N 1M0, T1-4N
				2M0</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение с последующим курсом
				химиотерапии в течение одной
				госпитализации</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Метастатический
				колоректальный рак, предполагающий
				использование на одном из этапов
				лечения хирургического метода</p>

			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				химиотерапия с применением таргетных
				лекарственных препаратов после
				проведения генетических исследований
				(определение мутаций) с последующим
				хирургическим лечением в течение
				одной госпитализации</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">хирургическое
				лечение с последующим курсом
				химиотерапии с применением таргетных
				лекарственных препаратов после
				проведения генетических исследований
				(определение мутаций) в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="CENTER">C34</p>
			</td>
			<td>
				<p>Местнораспространенный
				рак легкого T3N 1M0, T1-3N 2M0, T4N 0-2M0, T1-4N 3M0</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>

			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C40,
C41.2,
C41.3,
C41.4,
C41.8,
C41.9</p>

			</td>
			<td>
				<p>Первичные
				злокачественные новообразования
				костей и суставных хрящей туловища
				и конечностей IIb - IVa,b стадии. Первичные
				злокачественные новообразования
				мягких тканей туловища и конечностей
				IIa-b, III, IV стадии</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>

				<p align="LEFT">внутриартериальная
				химиотерапия с последующим хирургическим
				вмешательством</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C48</p>
			</td>
			<td>
				<p>Местнораспространенные
				и метастатические формы первичных и
				рецидивных неорганных опухолей
				забрюшинного пространства</p>
			</td>

			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p>Местнораспространенные
				формы опухолей брюшной стенки</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>

			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C50</p>

			</td>
			<td>
				<p>Первичный
				рак молочной железы T1-3N 0-1M0</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>

				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p>Первичный
				рак молочной железы T1N 2-3M0; T2-3N 1-3M0</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				химиотерапия, в том числе в сочетании
				с таргетными лекарственными препаратами,
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Метастатический
				и рецидивный рак молочной железы,
				предполагающий использование на
				одном из этапов лечения хирургического
				метода</p>

			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="CENTER">C53</p>
			</td>
			<td>
				<p>Местнораспространенные
				формы рака шейки матки</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>

			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C54</p>

			</td>
			<td>
				<p>Злокачественные
				новообразования эндометрия II - III
				стадии</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>

				<p align="LEFT">послеоперационная
				химиотерапия с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C56</p>
			</td>
			<td>

				<p>Злокачественные
				новообразования яичников I - IV стадии</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Рецидивы
				рака яичников</p>

			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="CENTER">C62</p>
			</td>
			<td>
				<p>Местнораспространенный,
				метастатический и рецидивный рак
				яичка</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>

			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p>Рак
				яичка I - III стадии, T1-4N 1-3M0-1</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>

				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C64</p>
			</td>
			<td>

				<p>Рак
				почки IV стадии, T3b-3c4,N 0-1M1</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">послеоперационная
				лекарственная терапия с проведением
				хирургического вмешательства в
				течение одной госпитализации</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C65,
C66,
C67</p>
			</td>
			<td>

				<p>Местнораспространенный
				уротелиальный рак T3-4N 0M0 при планировании
				органосохраняющей операции</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Местнораспространенный
				уротелиальный рак T1-4N 1-3M0</p>

			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="CENTER">C00, C01, C02, C03, C04, C05, C09, C10, C11, C30, C31, C41.0, C41.1, C49.0, C69.2, C69.4, C69.6</p>

			</td>
			<td>
				<p>Опухоли
				головы и шеи у детей: остеосаркома,
				опухоли семейства саркомы Юинга,
				саркомы мягких тканей, хондросаркома,
				злокачественная фиброзная гистиоцитома,
				ретинобластома</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>

				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">комплексное
				лечение с применением высокотоксичных
				противоопухолевых лекарственных
				препаратов, включая таргетные
				лекарственные препараты, при развитии
				выраженных токсических реакций с
				применением сопроводительной терапии,
				требующей постоянного мониторирования
				в стационарных условиях</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C71</p>
			</td>
			<td>
				<p>Опухоли
				центральной нервной системы у детей</p>
			</td>

			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">комплексное
				лечение с применением высокотоксичных
				противоопухолевых лекарственных
				препаратов, включая таргетные
				лекарственные препараты, при развитии
				выраженных токсических реакций с
				применением сопроводительной терапии,
				требующей постоянного мониторирования
				в стационарных условиях</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">
	

C22, C34, C38, C48.0, C52, C53.9, C56, C61, C62, C64, C67.8, C74</p>

			</td>
			<td>
				<p>Злокачественные
				новообразования торако-абдоминальной
				локализации у детей (опухоли средостения,
				опухоли надпочечника, опухоли печени,
				яичка, яичников, неорганные забрюшинные
				опухоли, опухоли почки, мочевыводящей
				системы и другие). Программное лечение</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>

				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">комплексное
				лечение с применением высокотоксичных
				противоопухолевых лекарственных
				препаратов, включая таргетные
				лекарственные препараты, при развитии
				выраженных токсических реакций с
				применением сопроводительной терапии,
				требующей постоянного мониторирования
				в стационарных условиях</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C40,
				C41,
				C49</p>
			</td>
			<td>

				<p>Опухоли
				опорно-двигательного аппарата у
				детей. Остеосаркома, опухоли семейства
				саркомы Юинга, злокачественная
				фиброзная гистиоцитома, саркомы
				мягких тканей</p>
			</td>
			<td>
				<p align="LEFT">комбинированное
				лечение</p>
			</td>
			<td>
				<p align="LEFT">предоперационная
				или послеоперационная химиотерапия
				с проведением хирургического
				вмешательства в течение одной
				госпитализации</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">комплексное
				лечение с применением высокотоксичных
				противоопухолевых препаратов, включая
				таргетные лекарственные препараты,
				при развитии выраженных токсических
				реакций с применением сопроводительной
				терапии, требующей постоянного
				мониторирования в стационарных
				условиях</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p align="CENTER">9</p>
			</td>
			<td>
				<p>Комплексное
				лечение с применением стандартной
				химио- и (или) иммунотерапии (включая
				таргетные лекарственные препараты),
				лучевой и афферентной терапии при
				первичных острых и хронических
				лейкозах и лимфомах (за исключением
				высокозлокачественных лимфом,
				хронического миелолейкоза в стадии
				бластного криза и фазе акселерации),
				рецидивах и рефрактерных формах
				солидных опухолей</p>
			</td>

			<td>
				<p align="CENTER">C81 - C90, C91.1 - 91.9, C92.1, C93.1, C94.1, C95.1</p>
			</td>

			<td>
				<p>Первичные
				хронические лейкозы и лимфомы (кроме
				высокозлокачественных лимфом,
				хронического миелолейкоза в фазе
				бластного криза и фазе акселерации)</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">комплексная
				иммунохимиотерапия с поддержкой
				ростовыми факторами и использованием
				антибактериальной, противогрибковой,
				противовирусной терапии, методов
				афферентной терапии и лучевой терапии</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">комплексное
				лечение с использованием таргетных
				лекарственных препаратов, факторов
				роста, биопрепаратов, поддержкой
				стволовыми клетками</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">комплексная
				химиотерапия с поддержкой ростовыми
				факторами и использованием
				антибактериальных, противогрибковых,
				противовирусных лекарственных
				препаратов, методов афферентной
				терапии и лучевой терапии</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p align="CENTER">10</p>
			</td>
			<td>
				<p>Дистанционная,
				внутритканевая, внутриполостная,
				стереотаксическая, радионуклидная
				лучевая терапия в радиотерапевтических
				отделениях 3-го уровня оснащенности,
				высокоинтенсивная фокусированная
				ультразвуковая терапия при
				злокачественных новообразованиях</p>
			</td>
			<td>

				<p align="CENTER">C00 - C14, C30, C31, C32, C77.0</p>
			</td>
			<td>
				<p>Злокачественные
				новообразования головы и шеи (T1-4N
				любая M0), локализованные и
				местнораспространенные формы</p>

			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">интраоперационная
				лучевая терапия, конформная дистанционная
				лучевая терапия, в том числе IMRT, IGRT,
				ViMAT, стереотаксическая. Радиомодификация.
				Компьютерная томография и (или)
				магнитно-резонансная топометрия. 3D
				- 4D планирование. Фиксирующие устройства.
				Плоскостная и (или) объемная визуализация
				мишени. Интраоперационная лучевая
				терапия</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">внутритканевая,
				аппликационная лучевая терапия. 3D -
				4D планирование. Внутриполостная
				лучевая терапия. Рентгенологический
				контроль установки эндостата</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">внутритканевая,
				аппликационная лучевая терапия. 3D -
				4D планирование. Внутриполостная
				лучевая терапия. Рентгенологический
				контроль установки эндостата</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C15</p>

			</td>
			<td>
				<p>Рак
				пищевода (T1-4N любая M0), локализованные
				и местнораспространенные формы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания. Интраоперационная
				лучевая терапия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуации</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">внутриполостная
				лучевая терапия. Рентгенологический
				контроль установки эндостата. 3D - 4D
				планирование</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="CENTER">C16</p>
			</td>
			<td>
				<p>Рак
				желудка (T2b-4aN0-3M0), локализованные и
				местнораспространенные формы</p>
			</td>
			<td>

				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания. Интраоперационная
				лучевая терапия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C17,
				C77.2</p>

			</td>
			<td>
				<p>Рак
				тонкого кишечника, локализованные и
				местнораспространенные формы с
				метастазами во внутрибрюшные
				лимфатические узлы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая
				фиксация. Компьютерная томография и
				(или) магнитно-резонансная топометрия.
				3D - 4D планирование. Фиксирующие
				устройства. Плоскостная и (или) объемная
				визуализация мишени, синхронизация
				дыхания. Интраоперационная лучевая
				терапия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C18,
				C19</p>
			</td>

			<td>
				<p>Рак
				ободочной кишки и ректосигмоидного
				угла (T2b-4aN0- 3M0), локализованные и
				местнораспространенные формы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания. Интраоперационная
				лучевая терапия</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C20,
				C77.5</p>
			</td>
			<td>
				<p>Рак
				прямой кишки (T1-4N любая M0), локализованные
				и местнораспространенные формы с
				метастазами во внутритазовые
				лимфатические узлы</p>

			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания. Интраоперационная
				лучевая терапия</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">внутриполостная
				лучевая терапия. Рентгенологический
				контроль установки эндостата. 3D - 4D
				планирование</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C21</p>

			</td>
			<td>
				<p>Рак
				анального канала (T1-3N любая M0),
				локализованные и местнораспространенные
				формы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">внутриполостная,
				внутритканевая, аппликационная
				лучевая терапия. Рентгенологический
				контроль установки эндостата. 3D - 4D
				планирование</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="CENTER">C22,
				C23</p>
			</td>
			<td>
				<p>Рак
				печени и желчного пузыря (T1-4N любая
				M0), локализованные и местнораспространенные
				формы</p>
			</td>

			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания. Интраоперационная
				лучевая терапия</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="CENTER">C24,
				C25</p>
			</td>
			<td>
				<p>Рак
				других частей желчных путей и
				поджелудочной железы (T1-4NxM0),
				локализованные и местнораспространенные
				формы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания. Интраоперационная
				лучевая терапия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C33,
				C34</p>

			</td>
			<td>
				<p>Рак
				трахеи, бронхов и легкого (T1-3N0-3M0),
				локализованные и местнораспространенные
				формы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени,
				синхронизация дыхания. Интраоперационная
				лучевая терапия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени.
				Синхронизация дыхания</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">внутриполостная
				лучевая терапия. Рентгеновский
				контроль установки эндостата. 3D - 4D
				планирование</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">внутриполостная
				лучевая терапия. Рентгеновский
				контроль установки эндостата. 3D - 4D
				планирование</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="CENTER">C37,
				C39,
				C77.1</p>
			</td>
			<td>
				<p>Злокачественные
				новообразования плевры и средостения
				(T1-3N0-3M0), локализованные и
				местнораспространенные формы с
				метастазами во внутригрудные
				лимфатические узлы</p>
			</td>

			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени.
				Синхронизация дыхания</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="CENTER">C40,
				C41</p>
			</td>
			<td>
				<p>Злокачественные
				новообразования костей и суставных
				хрящей (T любая, N любая, M0), локализованные
				и местнораспространенные формы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT. Радиомодификация.
				Компьютерная томография и (или)
				магнитно-резонансная топометрия. 3D
				- 4D планирование. Фиксирующие устройства.
				Плоскостная и (или) объемная визуализация
				мишени. Интраоперационная лучевая
				терапия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C44</p>

			</td>
			<td>
				<p>Злокачественные
				новообразования кожи (T1-4N0M0),
				локализованные и местнораспространенные
				формы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">конформная
				дистанционная лучевая терапия.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">аппликационная
				лучевая терапия с изготовлением и
				применением индивидуальных аппликаторов.
				3D - 4D планирование</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C48, C49, C50, C67, C74, C73</p>

			</td>
			<td>
				<p>Злокачественные
				новообразования мягких тканей (T
				любая, N любая, M0), локализованные и
				местнораспространенные формы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">внутритканевая
				интраоперационная лучевая терапия.
				Рентгенологический контроль установки
				эндостата. 3D - 4D планирование</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p>Рак
				молочной железы, мочевого пузыря и
				надпочечника, рак щитовидной железы
				(T1-3N0M0), локализованные и
				местнораспространенные формы</p>

			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе в комбинации с лекарственной
				терапией (IMRT, IGRT, ViMAT). Радиомодификация.
				Компьютерная томография и (или)
				магнитно-резонансная топометрия. 3D
				- 4D планирование. Фиксирующие устройства.
				Плоскостная и (или) объемная визуализация
				мишени. Интраоперационная лучевая
				терапия</p>
			</td>
		</tr>

		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="CENTER">C51</p>
			</td>
			<td>
				<p>Рак
				вульвы интраэпителиальный,
				микроинвазивный, местнораспространенный</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">дистанционная
				конформная лучевая терапия.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени.
				Интраоперационная лучевая терапия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">аппликационная
				и (или) внутритканевая лучевая терапия
				на брахитерапевтических аппаратах.
				Рентгеновский контроль установки
				эндостата. 3D - 4D планирование.
				Радиомодификация</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">дистанционная
				конформная лучевая терапия.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">аппликационная
				и (или) внутритканевая лучевая терапия
				на брахитерапевтических аппаратах.
				Рентгеновский контроль установки
				эндостата. 3D - 4D планирование</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C52</p>
			</td>
			<td>
				<p>Рак
				влагалища интраэпителиальный,
				микроинвазивный, местнораспространенный</p>
			</td>

			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">внутриполостная,
				аппликационная, внутритканевая
				лучевая терапия на брахитерапевтических
				аппаратах. Рентгеновский контроль
				установки эндостата. 3D - 4D планирование.
				Радиомодификация</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">дистанционная
				конформная лучевая терапия.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени.
				Внутриполостная, внутритканевая
				лучевая терапия на брахитерапевтических
				аппаратах. Рентгеновский контроль
				установки эндостата. 3D - 4D планирование.
				Радиомодификация</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C53</p>

			</td>
			<td>
				<p>Рак
				шейки матки T1-3N0-1M0-1 (M1 - метастазы в
				парааортальные или паховые лимфоузлы),
				интраэпителиальный и микроинвазивный</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">внутриполостная
				лучевая терапия на брахитерапевтических
				аппаратах. Рентгеновский и ультразвуковой
				контроль установки эндостата. 3D - 4D
				планирование. Радиомодификация</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">дистанционная
				конформная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT. Радиомодификация.
				Компьютерная томография и (или)
				магнитно-резонансная топометрия. 3D
				- 4D планирование. Фиксирующие устройства.
				Плоскостная и (или) объемная визуализация
				мишени. Внутриполостная лучевая
				терапия на брахитерапевтических
				аппаратах. Рентгеновский и ультразвуковой
				контроль установки эндостата. 3D - 4D
				планирование. Радиомодификация.
				Интраоперационная лучевая терапия</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C54</p>
			</td>
			<td>
				<p>Рак
				тела матки локализованный и
				местнораспространенный</p>
			</td>

			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">внутриполостная
				лучевая терапия на брахитерапевтических
				аппаратах. Рентгеновский и ультразвуковой
				контроль установки эндостата. 3D -4D
				планирование. Радиомодификация</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="LEFT">дистанционная
				конформная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT. Радиомодификация.
				Компьютерная томография и (или)
				магнитно-резонансная топометрия. 3D
				- 4D планирование. Фиксирующие устройства.
				Плоскостная и (или) объемная визуализация
				мишени</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="LEFT">дистанционная
				конформная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT. Радиомодификация.</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">внутриполостная
				лучевая терапия на брахитерапевтических
				аппаратах. Рентгеновский и ультразвуковой
				контроль установки эндостата. 3D - 4D
				планирование. Радиомодификация.
				Интраоперационная лучевая терапия</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C56</p>
			</td>
			<td>

				<p>Злокачественные
				новообразования яичников. Локальный
				рецидив, поражение лимфатических
				узлов после неоднократных курсов
				полихимиотерапии и невозможности
				выполнить хирургическое вмешательство</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">дистанционная
				конформная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT. Радиомодификация.
				Компьютерная томография и (или)
				магнитно-резонансная топометрия. 3D
				- 4D планирование. Фиксирующие устройства.
				Плоскостная и (или) объемная визуализация
				мишени</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C57</p>
			</td>
			<td>
				<p>Рак
				маточных труб. Локальный рецидив
				после неоднократных курсов
				полихимиотерапии и невозможности
				выполнить хирургическое вмешательство</p>
			</td>

			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">дистанционная
				конформная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT. Радиомодификация.
				Компьютерная томография и (или)
				магнитно-резонансная топометрия. 3D
				- 4D планирование. Фиксирующие устройства.
				Плоскостная и (или) объемная визуализация
				мишени</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="CENTER">C60</p>
			</td>
			<td>
				<p>Рак
				полового члена T1N0-M0</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">аппликационная
				лучевая терапия с изготовлением и
				применением индивидуальных аппликаторов.
				3D - 4D планирование</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C61</p>

			</td>
			<td>
				<p>Рак
				предстательной железы (T1-3N0M0),
				локализованные и местнораспространенные
				формы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Радиомодификация. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование.
				Фиксирующие устройства. Плоскостная
				и (или) объемная визуализация мишени</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">внутритканевая
				лучевая терапия. Рентгенологический
				контроль установки эндостата. 3D - 4D
				планирование</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C64</p>
			</td>
			<td>
				<p>Рак
				почки (T1-3N0M0), локализованные и
				местнораспространенные формы</p>
			</td>

			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">интраоперационная
				лучевая терапия. Компьютерная
				томография и (или) магнитно-резонансная
				топометрия. 3D - 4D планирование</p>
			</td>
		</tr>
		<tr valign="TOP">

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="CENTER">C73</p>
			</td>
			<td>
				<p>Рак
				щитовидной железы</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>

			</td>
			<td>
				<p align="LEFT">радиойодабляция
				остаточной тиреоидной ткани</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p align="LEFT">радиойодтерапия
				отдаленных метастазов дифференцированного
				рака щитовидной железы (в легкие, в
				кости и другие органы), радиойодтерапия
				в сочетании с локальной лучевой
				терапией при метастазах рака щитовидной
				железы в кости</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p align="LEFT">радиойодтерапия
				в сочетании с радионуклидной терапией
				самарием-оксабифором, Sm-153 при
				множественных метастазах рака
				щитовидной железы с болевым синдромом</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="CENTER">C50, C61, C34, C73, C64, C89</p>

			</td>
			<td>
				<p>Множественные
				метастазы в кости рака молочной
				железы, предстательной железы, рака
				легкого, рака почки, рака щитовидной
				железы (радиойоднегативный вариант)
				и других опухолей, сопровождающиеся
				болевым синдромом</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">системная
				радионуклидная терапия амарием-оксабифором,
				Sm-153</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>

				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">сочетание
				системной радионуклидной терапии
				самарием-оксабифором, Sm-153 и локальной
				лучевой терапии</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p align="LEFT">системная
				радионуклидная терапия стронцием-89-хлоридом</p>
			</td>

		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>
				</p>

			</td>
			<td>
				<p align="CENTER"><a name="sub_810"></a>C70, C71, C72, C75.1</p>
			</td>
			<td>

				<p>Злокачественные
				новообразования оболочек головного
				мозга, спинного мозга, головного мозга</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT, стереотаксическая.
				Компьютерная томография и (или)
				магнитно-резонансная топометрия. 3D
				- 4D планирование. Фиксирующие устройства.
				Плоскостная и (или) объемная визуализация
				мишени</p>

			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>
			<td>
				<p><br>

				</p>
			</td>
			<td>
				<p align="CENTER">C81, C82, C83, C84, C85</p>

			</td>
			<td>
				<p>Злокачественные
				новообразования лимфоидной ткани</p>
			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>

				<p align="LEFT">конформная
				дистанционная лучевая терапия, в том
				числе IMRT, IGRT, ViMAT. Компьютерная томография
				и (или) магнитно-резонансная топометрия.
				3D - 4D планирование. Фиксирующие
				устройства. Плоскостная и (или) объемная
				визуализация мишени. Синхронизация
				дыхания</p>
			</td>
		</tr>
		<tr valign="TOP">
			<td>
				<p><br>
				</p>
			</td>

			<td>
				<p>Контактная
				лучевая терапия при раке предстательной
				железы с использованием I125</p>
			</td>
			<td>
				<p align="CENTER">C61</p>
			</td>
			<td>
				<p>Рак
				предстательной железы (T1- 2N0M0),
				локализованные формы</p>

			</td>
			<td>
				<p align="LEFT">терапевтическое
				лечение</p>
			</td>
			<td>
				<p align="LEFT">внутритканевая
				лучевая терапия с использованием
				I125</p>
			</td>
		</tr>

		</tbody></table>

	   <tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>