<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
    	<div class="button ${stepinfo}" onclick="${stepclickinfo}">
    	<p class="label">Согласие</p>
    	<p class="info"></p>
    	</div>
    	<div class="button ${step1}" onclick="${stepclick1}">
    	<p class="label">Шаг 2.</p>
    	<p class="info ">Подразделение</p>
    	</div>
    	<div class="button ${step2}" onclick="${stepclick2}">
    	<p class="label">Шаг 3.</p>
    	<p class="info">Специализация</p>
    	</div>

    	<br>
    	<div class="button delete" style="margin-top:40px;" onclick="window.location='start.do'">
    	<p class="label">Отмена</p>
    	<p class="info"></p>
    	</div>
    	
    	