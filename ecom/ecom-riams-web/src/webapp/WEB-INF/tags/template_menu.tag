<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>
<tags:style_currentMenu currentAction="${currentAction }"/>
	<msh:sideMenu></msh:sideMenu>
    <msh:sideMenu guid="c8896942-33fb-4800-a00b-61d7750e8100" title="Перейти к шаблонам" >
      <msh:sideLink key="ALT+1" action="/js-temp_protocol-listTemplate.do" name="Протоколов" roles="/Policy/Diary/Template/View" guid="b7609d1a-4b0a-4ec1-adc1-66630068d379" title="Перейти к просмотру списка шаблонов протоколов" styleId="protocols"/>
      <msh:sideLink action="/js-pres_template-listTemplate.do" name="Листов назначений" title="Назначений" guid="6c84c427-7169-441e-b45b-243e351bd425" roles="/Policy/Mis/Prescription/Template/View" styleId="prescriptions"/>
      <msh:sideLink action="/entityParentList-diet_mealMenuMainTemplate.do?id=0" name="Меню раскладок" title="Просмотр меню раскладки" guid="823b925a-522c-4cc3-888e-dc72ade56076" roles="/Policy/Mis/InvalidFood/MealMenuTemplate/View" styleId="mealMenu"/>
      <msh:sideLink action="/js-cal_pattern-listTemplate.do?id=0" name="Календарей" title="Просмотр существующий шаблонов календарей" guid="25a-522c-4cc3-888e-dc72ade56076" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/View" styleId="workCalendar"/>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно" guid="41e922d8-a2f8-4b4a-be9a-20d7a8abb4f4">
      <msh:sideLink key="ALT+2" params="" action="/entityList-diary_templateword" name="Ключевые слова" roles="/Policy/Diary/KeyWord/View" guid="23518fb5-0a31-4bf2-9cb7-c7f4539e81d9" styleId="keyWord"/>
      <msh:sideLink roles="/Policy/Mis/Template/Category/View" action="/entityParentList-temp_category.do?id=0" name="Классификатор" title="Классфикатор шаблонов" guid="76c0bbc0-c792-4c5c-88de-c6973bebf8f7" styleId="category"/>
    </msh:sideMenu>
