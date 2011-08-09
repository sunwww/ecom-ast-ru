<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/exp" prefix="exp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
    	<!-- 
    	  - Перебор
    	  -->
        <msh:form action="/entitySaveGoView-exp_iterate.do" defaultField="name">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>

            <msh:panel>

                <msh:row>
                    <msh:textField property="name" label="Наименование" size="50"/>
                </msh:row>

                <msh:row>
                    <msh:textArea property="hqlString" label="Запрос" horizontalFill="true"
	                    rows="5"
                    />
                </msh:row>
 
               <msh:row>
                    <msh:textArea property="iterateScript" label="Скрипт для перебора" horizontalFill="true"
                    	size='60' rows='20'
	                    
                    />
                </msh:row>

                <msh:row>
                    <msh:textArea property="initScript" label="Инициализация" horizontalFill="true"
                    	size='60' rows='5'
	                    
                    />
                </msh:row>

 
                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>
        </msh:form>
        
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_iterateForm" />
    </tiles:put>



    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-exp_iterate" name="Изменить" roles="/Policy/Exp/FillTime/Edit"/>
            <msh:sideLink key="ALT+3" params="id" action="/exp_iterateExecute" name="Запустить" roles="/Policy/Exp/FillTimeProperty/Edit"/>
            <msh:sideLink key="ALT+DEL" confirm="Удалить перебор?" params="id" action="/entityDelete-exp_fillTime" name="Удалить" roles="/Policy/Exp/FillTime/Delete"/>
        </msh:sideMenu>
    </tiles:put>
    
  <tiles:put name="javascript" type="string">
	 <script type="text/javascript">
	
	 </script>  
   </tiles:put>
   
 <tiles:put name="style" type="string">
    <style type="text/css">
        .hqlString, .iterateScript, .initScript {
            white-space: pre;
          
        }

    </style>
</tiles:put>        
</tiles:insert>