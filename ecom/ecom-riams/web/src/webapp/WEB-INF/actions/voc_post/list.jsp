<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Просмотр справочника должностей</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    <msh:sideMenu title="Добавить" >
      <msh:sideLink key="ALT+N" roles="/Policy/Voc/VocPost/Create" action="/entityPrepareCreate-voc_post" name="Рабочую функцию" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="post"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
       <msh:section>
	       	<msh:sectionTitle>Справочник должностей</msh:sectionTitle>
	       	<msh:sectionContent>
	       		<ecom:webQuery nativeSql="
	       			select vp.id,vp.name as vpname,vp.code as vpcode, vodp.code as vodpcode,vodt.code as vodtcode
	       			from VocPost vp 
	       			left join VocOmcDepType vodt on vodt.id=vp.omcDepType_id
	       			left join VocOmcDoctorPost vodp on vodp.id=vp.omcDoctorPost_id 
	       		" name="list"/>
	            <msh:table name="list" action="entityView-voc_post.do" idField="1" disableKeySupport="true">
	                <msh:tableColumn columnName="Код" property="3"/>
	                <msh:tableColumn columnName="Название" property="2"/>
	                <msh:tableColumn columnName="Врачебная должность по ОМС" property="4"/>
	                <msh:tableColumn columnName="Код профиля отделения для стационара или специалиста для поликлиники" property="5"/>
	            </msh:table>
	       	</msh:sectionContent>
       </msh:section>

    </tiles:put>

</tiles:insert>