<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ attribute name="name" description="название" %>
<%@ attribute name="property" description="свойство, куда записывать данные" %>
<%@ attribute name="version" description="версия ПО Ticket,Visit" %>
<%@ attribute name="idSmo" description="индентификатор случая" %>
<%@ attribute name='voc' description='protocolTicketByPatient,protocolVisitByPatient' %>
<style type="text/css">
    #textProtocol {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
    li.liTemp {
    	cursor: pointer;
    	list-style:none outside none;
    	border-top: 1px solid ; 
    }
    li.liTemp:HOVER {
    	cursor: pointer;
    	background-color: #06C ;
    	color: white ;
	}
</style>

<div id='${name}templateProtocolDialog' class='dialog'>
    <h2>Выбор шаблона протокола</h2>
    <div class='rootPane'>
    <form name="frm${name}TemplateProt">
    <table>
		<tr>
			<td colspan="3">
				<a href="javascript:void(0)" onclick="load${name}ListProtocols('temp')" id="temp${name}A">Шаблоны все</a>
				<a href="javascript:void(0)" onclick="load${name}ListProtocols('my')" id="my${name}A">Свои шаблоны</a>
				<a href="javascript:void(0)" onclick="load${name}ListProtocols('mydiary')" id="mydiary${name}A">Список своих заключений по пациенту</a>
				<a href="javascript:void(0)" onclick="load${name}ListProtocols('polyc')" id="polyc${name}A">Список заключения по пациенту</a>
				<a href="javascript:void(0)" onclick="load${name}ListProtocols('hospit')" id="hospit${name}A">Госпитализация (осмотры врачей)</a>
				<a href="javascript:void(0)" onclick="load${name}ListProtocols('disch')" id="disch${name}A">Госпитализация (выписки)</a>
				
			</td>
		</tr>
		<msh:row styleId="noswod">
	        <td class="label" title="Поиск по показаниям поступления (${name}typeReestr)" colspan="1"><label for="${name}typeReestrName" id="${name}typeReestrLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';load${name}ListProtocols(theIs${name}TempProtLastFunction)">
	        	<input type="radio" name="${name}typeReestr" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';load${name}ListProtocols(theIs${name}TempProtLastFunction)">
	        	<input type="radio" name="${name}typeReestr" value="2">  свод
	        </td>
        </msh:row>
		
		<tr>
		<td valign="top" style="width:300px">
			<div id="${name}divListCategs"></div>
		</td>
		<td valign="top" style="width:300px">
			<div id="${name}divListProtocols"></div>
		</td>
		<td valign="top">
		    <msh:panel>
		    <tr><td colspan="3">
		    <msh:panel>
		    		        <msh:row>
		            <msh:checkBox property="${name}IsClose" label="Закрывать окно после выбора заключения" />
		        </msh:row>
		        <msh:row>
		            <td colspan="3">
		                <input type="button" name="buttonTempProtOk" id='buttonTempProtOk' value='Добавить в протокол' onclick='save${name}TemplateProtocol()'/>
		                <input type="button" value='ЗАКРЫТЬ' onclick='cancel${name}TemplateProtocol()'/>
		                
		            </td>
		        </msh:row>
		    
		    </msh:panel></td>
		    </tr>
		        <msh:row>
		            <msh:textArea property="${name}textTemplProtocol" rows="25" hideLabel="true"/>
		        </msh:row>

		        </msh:panel>
		    
		</td>
		
		</tr>
		</table>
		</form>
		</div>
		
</div>
<script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
<script type="text/javascript">
     var theIs${name}TempProtLastFunction = "temp" ;
     var theIs${name}TempProtDialogInitialized = false ;
     var the${name}TempProtDialog = new msh.widget.Dialog($('${name}templateProtocolDialog')) ;

     // Показать
     function show${name}TemplateProtocol() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}TempProtDialogInitialized) {
			init${name}TemplateProtocol() ;
			document.forms['frm${name}TemplateProt'].${name}typeReestr[1].checked='checked' ;
         }
         the${name}TempProtDialog.show() ;

     }

     // Отмена
     function cancel${name}TemplateProtocol() {
         the${name}TempProtDialog.hide() ;
     }

     // Сохранение данных
     function save${name}TemplateProtocol() {
			var prop ;
     		if ("${property}"=="") {
     			prop = "record" ;
     		} else {
     			prop = "${property}" ;
     		}
   			$(prop).value += $('${name}textTemplProtocol').value ;
   			if ($("${name}IsClose").checked) {
		         the${name}TempProtDialog.hide() ;
		         $(prop).focus() ;
   			} else {
   				alert('Добавлено в заключение') ;
   			}
	         //$(prop).select() ;
     }
     // Предварительный просмотр
     function preView${name}TemplateProtocol() {
			var prop ;
     		if ("${property}"=="") {
     			prop = "record" ;
     		} else {
     			prop = "${property}" ;
     		}
   			$('${name}textTemplProtocol').value = $(prop).value;
     }
     function edit${name}StyleA(aTemp) {
    	 var a = ["temp","my","mydiary","hospit","disch","polyc"] ;
    	 for (var i=0;i<a.length;i++) {
    		 if (aTemp==a[i]) {
    			 $(a[i]+"${name}A").style.background="#C3D9FF" ;
    			 $(a[i]+"${name}A").style.backgroundImage="url('/skin/images/main/sideSelected.gif') no-repeat" ;
    		 } else {
    			 $(a[i]+"${name}A").style.background="" ;
    			 $(a[i]+"${name}A").style.backgroundImage="" ;
    		 }
    		 
    	 }
     } 
     // инициализация диалогового окна выбора шаблона протокола
     function init${name}TemplateProtocol() {
             $('${name}textTemplProtocol').readOnly=true ;

             theIs${name}TempProtDialogInitialized = true ;
             $("${name}IsClose").checked=true ;
             //load${name}ListProtocolsByUsername() ;
             /*setFocusOnField('${name}tempProtCategoryName') ;*/
     }
     function ${name}showRow(aRowId, aCanShow, aField ) {
 			try {
 				$(aRowId).style.display = aCanShow ? 'table-row' : 'none' ;
 			} catch (e) {
 				$(aRowId).style.display = aCanShow ? 'block' : 'none' ;
 			}	
 		}
    
     function get${name}TextDiaryByIdSearch(aType,aParent) {
    	 	load${name}ListProtocols(aType,aParent,$('fldSearchget${name}TextDiaryById').value) ;
    	 
     }
     
     function load${name}ListProtocolsAll(aType,aParent,aSearchText) {
    	 edit${name}StyleA(aType) ;
    	 theIs${name}TempProtLastFunction=aType;
    	 var idSmo = '${idSmo}' ;
    	 var pos = idSmo.indexOf('.') ;
    	 if (+aParent>0|| +aParent<0) {} else {aParent=0}
    	 if (pos>0) {
    		 idSmo= $(idSmo.substring(pos+1)).value ;
    	 }
    		 $('${name}divListCategs').innerHTML = "Загрузка..." ;
        	 TemplateProtocolService.listCategProtocolsByUsername( aParent, aType
        			 , 'load${name}ListProtocols',{
                 callback: function(aString) {
                     $('${name}divListCategs').innerHTML = aString ;
            		 $('${name}divListProtocols').innerHTML = "Загрузка..." ;
            	 	 TemplateProtocolService.listProtocolsByUsername( idSmo,aParent, aType
        			 , 'get${name}TextProtocolById','get${name}TextDiaryById',aSearchText,{
                 		callback: function(aString) {
                     		$('${name}divListProtocols').innerHTML = aString ;
                  		}
              		} ) ;
                  
              }} ) ;
     }
     function load${name}ListProtocols(aType,aParent,aSearchText) {
    	 edit${name}StyleA(aType) ;
    	 theIs${name}TempProtLastFunction=aType;
    	 var idSmo = '${idSmo}' ;
    	 var pos = idSmo.indexOf('.') ;
    	 if (aParent==null||+aParent==0) {aParent=0}
    	 if (pos>0) {
    		 idSmo= $(idSmo.substring(pos+1)).value ;
    	 }
    	 if (aType=='temp') idSmo=0;
    	 var isR = true ;
    	 if (+aParent>0 || (+aParent==-1)|| aParent.length>0) {
    		 if (+aParent==-1 && (aType=='temp') && (aSearchText==null || aSearchText.length==0)) {
    			 isR=false;
    	    		 
    		 }
    	 } else {
    		 if (aType=='temp') {
    			 aParent=-1 ;
    			 if (aSearchText!=null && aSearchText.length>1) {isR=true;} else {isR=false;}
    		 $('${name}divListProtocols').innerHTML = "Выберите категорию"
    		 	+"<form action='javascript:get${name}TextDiaryByIdSearch(\""+aType+"\",\"\")'><input type='text' id='fldSearchget${name}TextDiaryById' name='fldSearchget${name}TextDiaryById' value=''>" 
				+"<input type='submit' value='Поиск' onclick='get${name}TextDiaryByIdSearch(\""+aType+"\",\"-1\")'></form>" ; ;
    		 } else {isR=document.forms['frm${name}TemplateProt'].${name}typeReestr[0].checked;}
    	}
    	 
    	 if (isR) {
    		  
    		 $('${name}divListProtocols').innerHTML = "Загрузка..." ;
        	 	 TemplateProtocolService.listProtocolsByUsername( idSmo,aParent, aType
    			 , 'get${name}TextProtocolById','get${name}TextDiaryById',aSearchText,{
             callback: function(aString) {
                 $('${name}divListProtocols').innerHTML = aString ;
                 if (document.forms['frm${name}TemplateProt'].${name}typeReestr[0].checked) {
         			// $('${name}divListCategs').innerHTML = "Для отображения категорий перейдите в режим свод" ;
         		 }
              }
          } ) ;
    	 }else{
    		 $('${name}divListCategs').innerHTML = "Загрузка..." ;
        	 TemplateProtocolService.listCategProtocolsByUsername( idSmo, aType
        			 , 'load${name}ListProtocols',{
                 callback: function(aString) {
                     $('${name}divListCategs').innerHTML = aString ;
                     if ((aType=='temp'||aType=="my")) {
            			 $('${name}divListProtocols').innerHTML = ""
            				 +"<form action='javascript:get${name}TextDiaryByIdSearch(\""+aType+"\",\"\")'><input type='text' id='fldSearchget${name}TextDiaryById' name='fldSearchget${name}TextDiaryById' value=''>" 
            					+"<input type='submit' value='Поиск' onclick='get${name}TextDiaryByIdSearch(\""+aType+"\",\"-1\")'></form>" ; ;
                     } else {
                    	 $('${name}divListProtocols').innerHTML = "" ;
                     }
                     
                  }
              } ) ;
    	 }
     }
     function get${name}TextProtocolById(aId,aOk) {
         TemplateProtocolService.getText(aId, {
             callback: function(aString) {
                 $('${name}textTemplProtocol').value = aString ;
                 if (+aOk==1) {
                	 save${name}TemplateProtocol() ;
                	 //$('${name}tempProtocol').value='' ;
                	 //$('${name}tempProtocolName').value='' ;
                 }
                 
            	 //$('${name}PrevProtocol').value='' ;
            	 //$('${name}PrevProtocolName').value='' ;
              }
          } ) ;
    	 
     }
     function get${name}TextDiaryByIdDischarge(aId,aOk) {
         TemplateProtocolService.getTextDischarge(aId, {
             callback: function(aString) {
                 $('${name}textTemplProtocol').value = aString ;
                 if (+aOk==1) {
                	 save${name}TemplateProtocol() ;
                	 //$('${name}tempProtocol').value='' ;
                	 //$('${name}tempProtocolName').value='' ;
                 }
            	 //$('${name}PrevProtocol').value='' ;
            	 //$('${name}PrevProtocolName').value='' ;
              }
          } ) ;
    	 
     }
     function get${name}TextDiaryByIdExternal(aId,aOk) {
         TemplateProtocolService.getTextExternal(aId, {
             callback: function(aString) {
                 $('${name}textTemplProtocol').value = aString ;
                 if (+aOk==1) {
                	 save${name}TemplateProtocol() ;
                	 //$('${name}tempProtocol').value='' ;
                	 //$('${name}tempProtocolName').value='' ;
                 }
            	 //$('${name}PrevProtocol').value='' ;
            	 //$('${name}PrevProtocolName').value='' ;
              }
          } ) ;
    	 
     }
     function get${name}TextDiaryById(aId,aOk) {
         TemplateProtocolService.getPreviousText(aId, {
             callback: function(aString) {
                 $('${name}textTemplProtocol').value = aString ;
                 if (+aOk==1) {
                	 save${name}TemplateProtocol() ;
                	 //$('${name}PrevProtocol').value='' ;
                	 //$('${name}PrevProtocolName').value='' ;
                 } 
            	 //$('${name}tempProtocol').value='' ;
            	 //$('${name}tempProtocolName').value='' ;
              }
          } ) ;
    	 
     }
     
</script>
