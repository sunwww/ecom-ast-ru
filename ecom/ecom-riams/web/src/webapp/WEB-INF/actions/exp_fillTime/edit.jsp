<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/exp" prefix="exp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
    	<!-- 
    	  - Заполнение
    	  -->
        <msh:form action="/entitySaveGoView-exp_fillTime.do" defaultField="name">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>

            <msh:panel>

                <msh:row>
                    <msh:textField property="name" label="Наименование" size="50"/>
                </msh:row>

                <msh:row>
                    <msh:autoComplete property = "outputDocument" 
                    				     label = "Выходной документ"
                                horizontalFill = "true" 
                                       vocName = "importDocument"
                    />
                </msh:row>

                <msh:row>
                    <msh:autoComplete property = "format" 
                    				     label = "Формат"
                                horizontalFill = "true" 
                                       vocName = "formatByDocument"
                                      parentId = "${exp_fillTimeForm.outputDocument}"
                    />
                </msh:row>

                <msh:row>
                    <msh:textArea property="queryString" label="Запрос" horizontalFill="true"
	                    
                    />
                </msh:row>

                <msh:row>
                    <msh:textArea property="initText" label="Инициализация" horizontalFill="true"
                    	size='60' rows='20'
	                    
                    />
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>
        </msh:form>
        
     <msh:ifFormTypeIsView formName="exp_fillTimeForm">
			
			<exp:fillTimePropertiesByFormat />
     		<msh:tableNotEmpty name="fillTimeProperties">
     			<msh:section title="Свойства по формату">
     				<msh:table idField="id" name="fillTimeProperties" action="exp_fillTimePropertyByFormat.do">
	                    <msh:tableColumn columnName="Поле" property="field"/>
	                    <msh:tableColumn columnName="Свойство" property="property"/>
	                    <msh:tableColumn columnName="Комментарий" property="comment"/>
	                    <msh:tableColumn columnName="Преобразование" property="transformText"/>
     				</msh:table>
     			</msh:section>
     		</msh:tableNotEmpty>
            <msh:section title='Список свойств'>
                <ecom:parentEntityListAll formName="exp_fillTimePropertyForm" attribute="properties"/>
                <msh:table name="properties" action="entityParentEdit-exp_fillTimeProperty.do" idField="id">
                    <msh:tableColumn columnName="Свойство" property="property"/>
                    <msh:tableColumn columnName="Преобразование" property="transformText"/>
                </msh:table>
            </msh:section>
        </msh:ifFormTypeIsView>        
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_fillTimeForm" />
    </tiles:put>



    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-exp_fillTime" name="Изменить" roles="/Policy/Exp/FillTime/Edit"/>
            <msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-exp_fillTimeProperty" name="Добавить свойство" roles="/Policy/Exp/FillTimeProperty/Create"/>
            <msh:sideLink key="ALT+3" params="id" action="/exp_fillTimeExecute" name="Запустить" roles="/Policy/Exp/FillTimeProperty/Edit"/>
            <msh:sideLink key="ALT+DEL" confirm="Удалить заполнение?" params="id" action="/entityDelete-exp_fillTime" name="Удалить" roles="/Policy/Exp/FillTime/Delete"/>
        </msh:sideMenu>
    </tiles:put>
    
  <tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsNotView formName="exp_fillTimeForm">
	 <script type="text/javascript">
		formatAutocomplete.setParent(outputDocumentAutocomplete) ;
	 </script>  
  	</msh:ifFormTypeIsNotView>
   </tiles:put>      
</tiles:insert>