<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_importtimeForm"/>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:sideLink key="ALT+1" params="id" action="/entityParentListRedirect-exp_importtime" name="⇧ Список по дате" />

            <msh:sideLink key="ALT+2" params="id" action="/exp_importDataList" name="Просмотр данных" />
            <msh:sideLink key="ALT+3" params="id" action="/exp_messageList" name="Список сообщений о проверке" />

            <msh:sideLink key="ALT+4" params="id" action="/exp_importtimeCheck" name="Проверить" />
            <msh:sideLink key="ALT+6" params="id" action="/exp_sync" name="Синхронизировать данные" />

            <msh:sideLink params="id" confirm="Удалить импортированные данные?" action="/entityParentDelete-exp_importtime" name="Удалить" />
        </msh:sideMenu>
        
        <msh:sideMenu title="ОМС">
        	<msh:sideLink action="/exp_messagesOther" params="id" name="Сводка"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:form action="entityParentSave-exp_importtime.do" defaultField="keyName">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="document" />

            <msh:panel>
                <msh:row>
                    <msh:textField property="comment" label="Комментарий" fieldColSpan="3" horizontalFill='true'/>
                </msh:row>
                <msh:row>
                    <msh:textField property="actualDateFrom" label="Дата действия с" />
                    <msh:textField property="actualDateTo" label="Дата действия по" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
            <msh:section title='По виду проверки'>
	            <ecom:webQuery name='test' 
	              hql='select m.check.id, m.check.name, count(*), m.check.checkType, m.check.sn from Message m where importTime=${param.id} 
	              	group by m.check.name, m.check.id, m.check.checkType,m.check.sn order by m.check.sn' />
	            <msh:table name="test" action="exp_messagesByCheck.do?importTime=${param.id}" 
	            	decorator="ru.ecom.expomc.web.actions.importtime.MessageByVidTableDecorator"> 
	            	<msh:tableColumn columnName="№" property="sn"/>
	            	<msh:tableColumn columnName="Вид проверки" property="2"/>
	            	<msh:tableColumn columnName="Количество" property="3"/>
	            	<msh:tableColumn columnName="Тип" property="4"/>
	            </msh:table>  
            </msh:section>
            
            <msh:section title='По типу проверки'>
	            <ecom:webQuery name='test' 
	              hql='select m.check.checkType, count(*) from Message m where importTime=${param.id} 
	              	group by m.check.checkType' />
	            <msh:table name="test" action="" idField="1">
	            	<msh:tableColumn columnName="№" property="sn"/>
	            	<msh:tableColumn columnName="Тип проверки" property="1"/>
	            	<msh:tableColumn columnName="Количество" property="2"/>
	            </msh:table>  
            </msh:section>
            
    </tiles:put>

<tiles:put name="style" type="string">
    <style type="text/css">
        .checkType2 {
        	color: green ;
        }
        .checkType3 {
        	color: red ;
        }
    </style>
</tiles:put>

</tiles:insert>