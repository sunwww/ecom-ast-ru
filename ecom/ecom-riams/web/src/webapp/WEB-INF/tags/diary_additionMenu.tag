<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
 
        <msh:sideLink 
        	action="/entityParentList-mis_medServiceGroup.do?id=-1" 
        	name="⇧Медицинские услуги" 
        	title="Перейти к списку основному списку медицинских услуг" 
        	styleId="mis_medService"
        	roles="/Policy/Mis/MedService/View"
        	/>
        <msh:sideLink 
        	action="/entityList-diary_userDomain.do" 
        	name="⇧Пользов. справочники" 
        	title="Просмотр пользовательских справочников" 
        	styleId="diary_userDomain"
        	roles="/Policy/Diary/User/Domain/View"
        	/>
        <msh:sideLink 
        	action="/entityParentList-diary_parameterGroup.do?id=-1" 
        	name="⇧Группы параметров" 
        	title="Просмотр групп доступных параметров" 
        	styleId="diary_parameterGroup"
        	roles="/Policy/Diary/ParameterGroup/View"
        	/>
