<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
    	<div class="button ${stepinfo}" onclick="${stepclickinfo}">
    	<p class="label">Согласие</p>
    	<p class="info"></p>
    	</div>
    	<div class="button ${step0}"  onclick="${stepclick0}">
    	<p class="label">Шаг 1.</p>
    	<p class="info ">Анкетные данные</p>
    	</div>
    	<div class="button ${step1}" onclick="${stepclick1}">
    	<p class="label">Шаг 2.</p>
    	<p class="info ">Подразделение</p>
    	</div>
    	<div class="button ${step2}" onclick="${stepclick2}">
    	<p class="label">Шаг 3.</p>
    	<p class="info">Специализация</p>
    	</div>
    	<div class="button ${step3}" onclick="${stepclick3}">
    	<p class="label">Шаг 4.</p>
    	<p class="info">Специалист</p>
    	</div>
    	<div class="button ${step4}" onclick="${stepclick4}">
    	<p class="label">Шаг 5.</p>
    	<p class="info">Выбор даты</p>
    	</div>
    	<div class="button ${step5}" onclick="${stepclick5}">
    	<p class="label">Шаг 6.</p>
    	<p class="info">Выбор времени</p>
    	</div>
    	<br>
    	<div class="button delete" style="margin-top:40px;" onclick="window.location='start.do'">
    	<p class="label">Отмена</p>
    	<p class="info"></p>
    	</div>
    	
    	