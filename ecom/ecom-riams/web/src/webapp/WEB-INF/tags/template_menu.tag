<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>
<tags:style_currentMenu currentAction="${currentAction }"/>
	<msh:sideMenu></msh:sideMenu>
    <msh:sideMenu title="Перейти к шаблонам" >
      <msh:sideLink key="ALT+1" action="/js-temp_protocol-listTemplate.do" name="Протоколов" roles="/Policy/Diary/Template/View" title="Перейти к просмотру списка шаблонов протоколов" styleId="protocols"/>
      <msh:sideLink action="/js-pres_template-listTemplate.do" name="Листов назначений" title="Назначений" roles="/Policy/Mis/Prescription/Template/View" styleId="prescriptions"/>
      <msh:sideLink action="/entityParentList-diet_mealMenuMainTemplate.do?id=0" name="Меню раскладок" title="Просмотр меню раскладки" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/View" styleId="mealMenu"/>
      <msh:sideLink action="/js-cal_pattern-listTemplate.do?id=0" name="Календарей" title="Просмотр существующий шаблонов календарей" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/View" styleId="workCalendar"/>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <msh:sideLink key="ALT+2" params="" action="/entityList-diary_templateword" name="Ключевые слова" roles="/Policy/Diary/KeyWord/View" styleId="keyWord"/>
      <msh:sideLink roles="/Policy/Mis/Template/Category/View" action="/entityParentList-temp_category.do?id=0" name="Классификатор" title="Классфикатор шаблонов" styleId="category"/>
    </msh:sideMenu>
