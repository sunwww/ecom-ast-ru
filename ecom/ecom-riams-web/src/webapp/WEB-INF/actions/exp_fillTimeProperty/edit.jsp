<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
    	<!-- 
    	  - Свойство заполнения
    	  -->
        <msh:form action="/entityParentSaveGoParentView-exp_fillTimeProperty.do" defaultField="propertyName">
            <msh:hidden property="id"/>
            <msh:hidden property="fillTime"/>
            <msh:hidden property="documentId"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="transformText"/>

            <msh:panel>

                <msh:row>
                    <msh:autoComplete 
                    		property = "property" 
                    		   label = "Свойство" 
                      horizontalFill = "true" 
                             vocName = "importDocumentProperties"
                              showId = 'true' 
                            parentId = 'exp_fillTimePropertyForm.documentId'
                     />
                </msh:row>
				<msh:row>
					<td style='text-align: right'><a id='testInvokeLink' href='#' onfocus='testInvoke()' onclick='testInvoke()'>Проверить преобразование</a></td>
					<td><div id='fillTimeResult'></div></td>
				</msh:row>
                <msh:row>
                    <msh:textArea 
                              property = "transformText" 
                                 label = "Выражение преобразования" 
                        horizontalFill = "true"
                		syntaxLanguage = "javascript"
                				  size = "100"
                				  rows = "20"
                      />
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>
        </msh:form>
  </tiles:put>
          
    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Document" beginForm="exp_fillTimePropertyForm" />
    </tiles:put>



    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        	<msh:ifFormTypeIsNotView formName="exp_fillTimePropertyForm">
	            <msh:sideLink key = "ALT+DEL" 
	                      confirm = "Удалить свойство заполнения?" 
	            		   params = "id" 
	            		   action = "/entityParentDeleteGoParentView-exp_fillTimeProperty" 
	            		     name = "Удалить" 
	            		    roles = "/Policy/Exp/FillTimeProperty/Delete"
	            		    />
	        	</msh:ifFormTypeIsNotView>
	     </msh:sideMenu> 		    
        
    </tiles:put>
    
     <tiles:put name="javascript" type="string">
        <msh:javascriptSrc src='./dwr/interface/FillTimeService.js'/>
        
        <script src="/codepress/codepress.js" type="text/javascript"></script>
        
	    <script type="text/javascript">
	    
	    	    	
	    	var fillTimeId = $('fillTime').value ;
			var fillTimeResultDiv = $('fillTimeResult') ;
			
			function getAreaValue() {
				try {
					return transformText.getCode() ;
				} catch (e) {
					return $('transformText').value ;
				}	
			}
			
	    	function testInvoke() {
	    	
	    		fillTimeResultDiv.style.color = "blue" ;
	    		fillTimeResultDiv.innerHTML = "Вычисление ..." ;
	    		
	    		FillTimeService.testInvoke(fillTimeId
	    			, $('property').value
	    			, getAreaValue(), {
                        callback: function(aResult) {
				    		fillTimeResultDiv.style.color = "black" ;
                            fillTimeResultDiv.innerHTML = aResult;
                        }
                        , errorHandler: function(aError) {
				    		fillTimeResultDiv.style.color = "red" ;
                            fillTimeResultDiv.innerHTML = aError;
                        }
                    });
	    	}
	    	
	    	function onKey() {
	    		var newCode = getAreaValue() ;
	    		if(newCode!=null && newCode!=lastCode) {
	    			lastCode = newCode ;
	    			testInvoke() ;
	    		}
	    	}
	    	
	    	function onSubmit() {
				$('submitButton').value = "Сохранение кода..." ;
				$('transformText').value = getAreaValue() ;
				$('submitButton').value='Сохранение ...' ;
				$('mainForm').submit() ;
	    	}

	    	testInvoke() ;
	    	//propertyAutocomplete.addOnChangeCallback(testInvoke) ;
	    	$('submitButton').onclick =	onSubmit ;
	    	
        </script>
    </tiles:put>
    
<tiles:put name="style" type="string">
    <style type="text/css">
        #transformText {
            width: 50em;
            height: 25em;
        }
        .codepress, .javascript {
        	border: 1px solid red ;
        }
        
        #testInvokeLink {
        	background-color: #ddd ;
        	border: 1px solid black ;
        	padding: .5em;
        }

    </style>
</tiles:put>
    
    
</tiles:insert>