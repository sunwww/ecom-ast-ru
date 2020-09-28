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
<style type="text/css">
    #${name}IntakeInfoDialog {
   visibility: hidden ;
        display: none ;
        position: absolute ;
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
        <msh:row>
			<msh:textField property="${name}ProtocolDate" label="Дата дневника"/>
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

<div id='the${name}IntakeInfoDialog' class='dialog'>
    <h2 id='${name}IntakeInfoTitle'>ВВОД ДАННЫХ</h2>
    <input type='hidden' name='${name}List' id='${name}List'/>
    <div id="${name}IntakeRootPane" class='rootPane'>
    	
	</div>
</div>
<script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
<script type='text/javascript' src='./dwr/interface/PrescriptionService.js'></script>


<script type="text/javascript">
function trim(aStr) {
	return aStr.replace(/|\s+|\s+$/gm,'') ;
    }
var fldJson = null ;

     var theIs${name}TempProtLastFunction = "temp" ;
     var theIs${name}TempProtDialogInitialized = false ;
     var the${name}TempProtDialog = new msh.widget.Dialog($('${name}templateProtocolDialog')) ;
     var the${name}IntakeInfoDialog = new msh.widget.Dialog($('the${name}IntakeInfoDialog')) ;
     var the${name}IntakeInfoDialogInit = false ;
     // Показать
     
    function save${name}Result() {
    var rows = $('tblParamProtocol').childNodes[0].childNodes;
    var text = '';

    for (var ind=0;ind<fldJson.params.length;ind++) {
    	var par = fldJson.params[ind] ;
    	text += par.name +": ";
    	if (+par.type==2|| +par.type==6||+par.type==7) {
			text += par.valueVoc ;
		} else {
			text += par.value ;
		} 
    	if (+par.type==7) {
			text += " "+par.addValue ;
		}
    	
    	text+='\n';
    }
    $('record').value=text;
	the${name}IntakeInfoDialog.hide();
	the${name}IntakeInfoDialogInit=false ;
    $('record').disabled=false;
    isEditable();
   }
    function save${name}IntakeInfo() {
 	   $('btnEditProt1').style.display='inline' ;
	   $('btnEditProt2').style.display='inline' ;

    	var regexp = /^[\d+.]+$/;
		var isError = false ;
		for (var ind=0;ind<fldJson.params.length;ind++) {
			
			var val ;
			
			var par = fldJson.params[ind] ;
			par.valueVoc = "" ;
			if (par.type==6) {
				val="" ;
			} else {
				val = $('param'+fldJson.params[ind].id).value ;
				
			}
			var err ="";
			try{
			errorutil.HideError($('param'+par.idEnter)) ;
			}catch(e){}

			
			if (+par.type==2) {
				if (+val<1) {val='0' ;par.valueVoc="";} else {
					par.valueVoc = $('param'+par.id+'Name').value ;
				}
			} else if (+par.type==6) {
				for (var indVal=0;indVal<par.voc.length;indVal++) {
					var v = par.voc[indVal] ;
					if ($('param'+par.id+"_"+v.id).checked) {
						val = val + ","+$('param'+par.id+"_"+v.id).value ;
						par.valueVoc += trim(", "+$('param'+par.id+"_"+v.id+'Span').innerHTML) ;
						v.checked='1';
					} else {
						v.checked='0';
					}
				}
				if (val.length>0) {val+="," ;par.valueVoc=par.valueVoc.substring(2);}
			} else if (+par.type==7) {
				if (+val<1) {val='0' ;} else {
					par.valueVoc = $('param'+par.id+'Name').value ;
				}
				par.addValue= $('param'+par.id+'AddValue').value ;
			} else if (par.type=='4') {
				val = val.replace(",",".") ;
				val = val.replace("-",".") ;
				var v = val.split(".") ;
				var cntdecimal = +par.cntdecimal
				
				
				if (val!="") {
					if (!regexp.test(val)){
						err+="Допускается ввод только чисел! " ;						
						isError=true;
					}
					if (v.length==2 && v[1].length!=cntdecimal) {
						errorutil.ShowFieldError($('param'+par.idEnter),err+"Необходимо ввести "+cntdecimal+" знаков после запятой") ;
						isError= true ;
					}
					
	  					if (cntdecimal>0 ) {
	  						if (v.length==1) {
	  							errorutil.ShowFieldError($('param'+par.idEnter),err+"Должна быть 1 точка или запятая") ;
	  							isError= true ;
	  						} else if (v.length>1 && !isNaN(v[2])) {
	 							errorutil.ShowFieldError($('param'+par.idEnter),err+"Должна быть 1 точка или запятая") ;
	 							isError= true ;
							}
	  					} else {
	  						
	  					}
					}
			} else if (par.type==1) {
				if (val!='' &&!regexp.test(val)){
					
					errorutil.ShowFieldError($('param'+par.idEnter),"Допускается ввод только чисел!") ;	
					isError=true;
				}
			}
			
			par.value = val ;
			
			
		}
		if (+fldJson.isdoctoredit==0) {
			if (+$('paramWF').value==0) {
				isError=true ;
				alert("paramWF") ;
				errorutil.ShowFieldError($('paramWFName'),"Обязательное поле") ;
			} else {
				fldJson.workFunction=$('paramWF').value
			}
			
		}
		var str = JSON.stringify(fldJson);
		$('params').value = str;
		if (!isError) {
			save${name}Result();
			
		}
	} 
    
    function isEditable () {
    	TemplateProtocolService.getTemplateDisableEdit($('templateProtocol').value,{
    		callback: function (a) {
    			if (+a==1) {
    				eventutil.addEventListener($('record'),eventutil.EVENT_CLICK, 
    	    	  		  	function() {
    	    					$('record').disabled=true;
    	    					if ($('id').value!='') {
    	    						//alert ('Редактирование данного протокола возможно только через форму!'); 
    	    					}
    	    					showTemplateForm($('templateProtocol').value);
    	    				//	$('record').disabled=false;
    	    	  		  	}) ;
    	    	} else {
    	    		try {
    	    			$('record').removeEventListener('click');
    	    			$('record').removeEventListener('keypress');
    	    		} catch (e) {
    	    		//	alert("E "+e.name);
    	    		}
    	    		
    	    	}
    	    }
    		
    	});
    	
    }
    
     function showTemplateForm(aTemplateId) {
    	 the${name}IntakeInfoDialogInit = true ;
    	 the${name}TempProtDialog.hide() ;
    	    	 	//alert(aTempId) ;
    	    	 	    	 the${name}IntakeInfoDialog.hide();
    		the${name}IntakeInfoDialog.show() ;
		       $('${name}IntakeRootPane').innerHTML ="Загрузка..." ;

    				TemplateProtocolService.getParameterAndPermissionByTemplate($('id').value,aTemplateId,{
    					callback: function (aResults) {
    						var editable = true;
    					
    						$('${name}IntakeInfoTitle').innerHTML = "ВВОД ДАННЫХ" ;
    					      if ($('params') && $('params').value!='' && $('templateProtocol').value == aTemplateId) {
    					    	fldJson = JSON.parse($('params').value);  
    					      }  else {
    					      fldJson = JSON.parse(aResults);
								  editable = fldJson.disableEditProtocol=="0";
    					      }
    					      $('templateProtocol').value = +aTemplateId;
    					        var cnt = +fldJson.params.length ;
    					    	
    					        if (cnt<=0) {
									the${name}IntakeInfoDialog.hide();
									if (!editable) {
										alert("Отсутствуют параметры для редактирования и стоит запрет на ручное изменение дневника, снимите запрет в шаблоне для редактирования дневника!");
									}
    					        	return;
								}
    					        var txt = "<form><table id='tblParamProtocol'>" ;
    					        if (+fldJson.isdoctoredit==0) {
    					        	
    					        	var p = "WF" ;var pid=fldJson.workFunction;var pn =fldJson.workFunctionName ;
    					        	var param = {"id":p,"idEnter":p+"Name","valueVoc":pn,"value":pid
    					        			,"vocname":"workFunction","vocid":""
    					        			,"shortname":"Специалист","name":"Специалист","type":"2" ,"unitname":""
    					        	};
    					        	txt += ${name}getFieldTxtByParam(param) ;
    					        	
    					        }
    					        for (var ind=0;ind<cnt;ind++) {
    					        	var param = fldJson.params[ind] ;
    					        	txt += ${name}getFieldTxtByParam(param) ;
    					        }
    					        txt += "</table></form>" ;
    							
    					       $('${name}IntakeRootPane').innerHTML =txt 
    					       	+ "<br><input type=\"button\" id=\"paramOK\" name=\"paramOK\" value=\"Сохранить\" onclick=\"save${name}IntakeInfo()\">"
    					       	+ "<input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}IntakeInfo()\">";
    					    	 the${name}IntakeInfoDialog.hide();
    					    		the${name}IntakeInfoDialog.show() ;

    					       	cancel${name}TemplateProtocol();
    					       
    					   //    	the${name}IntakeInfoDialog.show() ;
    			             	
    			             if (fldJson.errors.length==0) {
    			            	 if (+fldJson.isdoctoredit==0) {
    			            		 var p = "WF" ;var pid=fldJson.workFunction;var pn =fldJson.workFunctionName ; 
    					        		eval("param"+p+"Autocomlete = new msh_autocomplete.Autocomplete() ;");
    					        		eval("param"+p+"Autocomlete.setUrl('simpleVocAutocomplete/workFunction') ;");
    					        		eval("param"+p+"Autocomlete.setIdFieldId('param"+p+"') ;");
    					        		eval("param"+p+"Autocomlete.setNameFieldId('param"+p+"Name') ;");
    					        		eval("param"+p+"Autocomlete.setDivId('param"+p+"Div') ;");
    					        		eval("param"+p+"Autocomlete.setVocKey('workFunction') ;");
    					        		eval("param"+p+"Autocomlete.setVocTitle('"+pn+"') ;");
    					        		eval("param"+p+"Autocomlete.build() ;");
    					        		//eval("param"+p+"Autocomlete.setParentId('"+pid+"') ;");
    					        		$("param"+p+"Name").select() ;
    					        		$("param"+p+"Name").focus() ;
    					        		eventutil.addEnterSupport("param"+p+"Name","param"+fldJson.params[0].idEnter) ;
    					        		
    			            	 } else {
    			            		 $("param"+fldJson.params[0].idEnter).select() ;
 					        		$("param"+fldJson.params[0].idEnter).focus() ;
    			            	 }
    					        for (var ind=0;ind<fldJson.params.length;ind++) {
    					        	 
    					        	var param1 = fldJson.params[ind] ;
    					        	idEnter = param1.idEnter ;
					        		if (+param1.type==7) {
					        			eventutil.addEnterSupport("param"+param1.idEnter,"param"+param1.idEnter.substring(0,param1.idEnter.length-4)+"AddValue") ;
					        			param1.idEnter=param1.idEnter.substring(0,param1.idEnter.length-4)+"AddValue";
					        		} else if (+param1.type==6) {
					        			for (var iparam=1;iparam<param1.voc.length-0;iparam++) {
					        				var next=param1.idEnter.substring(0,param1.idEnter.indexOf("_"))+"_"+param1.voc[iparam].id ;
					        				eventutil.addEnterSupport("param"+param1.idEnter,"param"+next) ;
					        				param1.idEnter=next ;
					        			}
					        		} 

    					        	if (ind<cnt-1) {
    					        		var param2 = fldJson.params[ind+1] ;
    					        		
    					        		eventutil.addEnterSupport("param"+param1.idEnter,"param"+param2.idEnter) ;
    					        	} else {
    					        		eventutil.addEnterSupport("param"+param1.idEnter,"paramOK") ;
    					        		
    					        	}
    					        	param1.idEnter=idEnter ;
    					        	if (+param1.type==2||+param1.type==7) {
    					        		eval("param"+param1.id+"Autocomlete = new msh_autocomplete.Autocomplete() ;");
    					        		eval("param"+param1.id+"Autocomlete.setUrl('simpleVocAutocomplete/userValue') ;");
    					        		eval("param"+param1.id+"Autocomlete.setIdFieldId('param"+param1.id+"') ;");
    					        		eval("param"+param1.id+"Autocomlete.setNameFieldId('param"+param1.idEnter+"') ;");
    					        		eval("param"+param1.id+"Autocomlete.setDivId('param"+param1.id+"Div') ;");
    					        		eval("param"+param1.id+"Autocomlete.setVocKey('userValue') ;");
    					        		eval("param"+param1.id+"Autocomlete.setVocTitle('"+param1.vocname+"') ;");
    					        		eval("param"+param1.id+"Autocomlete.build() ;");
    					        		eval("param"+param1.id+"Autocomlete.setParentId('"+param1.vocid+"') ;");
    					        	}
    					        }
								 if (eval${name}String) {
									 eval(eval${name}String);
								 }
    			             } else {
    			            	var txt ="<form name='frmTemplate' id='frmTemplate'>" ;
    			 				txt += "<a target='_blank' href='diary_templateView.do?id="+fldJson.template+"'>НЕПРАВИЛЬНО ЗАПОЛНЕНЫ ДАННЫЕ ПАРАМЕНТОВ ШАБЛОНА</a>" ;
    			 				txt +="</form>" ;
    			 				$('${name}IntakeRootPane').innerHTML =txt 
    					       	+ "<input type=\"button\" value=\"ОК\" onclick=\"cancelBioIntakeInfo()\">";
    					     }
    					}
    				}) ;
    			
     }
     
  
     
     function cancel${name}IntakeInfo() {
         the${name}IntakeInfoDialog.hide() ;
         the${name}IntakeInfoDialogInit=false;
       //  the${name}TempProtDialog.show() ;
       $('record').disabled=false;
         msh.effect.FadeEffect.pushFadeAll();
     }
var eval${name}String = "";
     function ${name}getFieldTxtByParam(aParam) {
			var txt = "<tr>" ;
			var type=+aParam.type;
	        txt += "<td class=\"label\"><label id=\"param"+aParam.id+"Label\" for=\"param"+aParam.idEnter+"\" title='"+aParam.name ;
	        if (type==1) {
	        	txt += '(число)' ;
	        } else if (type==3) {
	        	txt += '(текст)' ;
	        } else if (type==4) {
	        	txt += '(число зн. '+aParam.cntdecimal+')' ;
	        } else if (type==5) {
	        	txt += '(текст с огр. '+aParam.cntdecimal+')' ;
	        	
	        } 
	        txt += "'>" ;
	        txt += aParam.shortname ;
	        txt += "</label></td>" ;
	        txt += "<td>" ;
	        if (type==2) {
		        txt += "<input id=\"param"+aParam.id+"\" name=\"param"+aParam.id+"\" type=\"hidden\" value=\""+aParam.value+"\" title=\"\" autocomplete=\"\">";
	        	txt += "<div>";
		        txt += "<input id=\"param"+aParam.idEnter+"\" name=\"param"+aParam.idEnter+"\" type=\"text\" value=\""+aParam.valueVoc+"\" title=\""+aParam.vocname+"\" class=\"autocomplete horizontalFill\" autocomplete=\"on\">";
	        	txt += "<div id=\"param"+aParam.id+"Div\">";
	        	txt += "<span></span>";
		        //txt += ;
		        txt += "</div>";
		        txt += "</div>";
	        } else if (type==7) {
		        txt += "<input id=\"param"+aParam.id+"\" name=\"param"+aParam.id+"\" type=\"hidden\" value=\""+aParam.value+"\" title=\"\" autocomplete=\"\">";
	        	txt += "<div>";
		        txt += "<input id=\"param"+aParam.idEnter+"\" name=\"param"+aParam.idEnter+"\" type=\"text\" value=\""+aParam.valueVoc+"\" title=\""+aParam.vocname+"\" class=\"autocomplete horizontalFill\" autocomplete=\"on\">";
	        	txt += "<div id=\"param"+aParam.id+"Div\">";
	        	txt += "<span></span>";
		        //txt += ;
		        txt += "</div>";
		        txt += "</div>";
		        txt += "<input id=\"param"+aParam.id+"AddValue\" name=\"param"+aParam.id+"AddValue\" type=\"text\" value=\""+aParam.addValue+"\" title=\""+aParam.name+"\" autocomplete=\"off\">";
		        
	        } else if (type==6) {
	        	for (var iparam=0;iparam<aParam.voc.length-0;iparam++) {
	        		var v = aParam.voc[iparam] ;
	        		var idEnterParam=aParam.idEnter.substring(0,aParam.idEnter.indexOf("_"))+"_"+v.id ;
    				txt += "<div><input id=\"param"+idEnterParam+"\" name=\"param"+idEnterParam+"\" type=\"checkbox\" value=\""+v.id+"\" "+((+v.checked)>0?"checked":"")+" title=\"\" autocomplete=\"\">";
    				txt += "<span id=\"param"+idEnterParam+"Span\" name=\"param"+idEnterParam+"Span\" onclick=\"$('param"+idEnterParam+"').click()\">"+v.name+" </span></div>";
	        	}
	        } else {
		        txt += "<input size=\"80\" id=\"param"+aParam.id+"\" name=\"param"+aParam.id+"\" type=\"text\" value=\""+aParam.value+"\" title=\""+aParam.name+"\" autocomplete=\"off\">";
				if (type==8) {
					eval${name}String +=" new dateutil.DateField($('param"+aParam.id+"'));"
				}
		        
	        }
	        txt += "</td>" ;
	        txt += "<td>" ;
	        if (+aParam.type==4) {txt += " ("+aParam.cntdecimal+") ";}
	        txt += aParam.unitname ;
	        txt += "</td>" ;
	        txt += "</tr>" ;
			return txt ;
		}
     
     function show${name}TemplateProtocol() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}TempProtDialogInitialized) {
			init${name}TemplateProtocol() ;
			document.forms['frm${name}TemplateProt'].${name}typeReestr[1].checked='checked' ;
			if ($('${name}ProtocolDate')) { //при изменении даты дневника - перезагружаем протоколы
				new dateutil.DateField($('${name}ProtocolDate')) ;
				var valu = $('${name}ProtocolDate').value;
				eventutil.addEventListener($('${name}ProtocolDate'),'blue',function(){
					if(valu==""||valu.length==10) load${name}ListProtocols('mydiary');
				}) ;
			}
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
         //Milamesher localStorage
         if (document.getElementsByName("stac_sslDischargeForm") !=null) {
             try {
                 localStorage.setItem("stac_sslDischargeForm"+";"+medCaseId.value+";"+document.getElementById('current_username_li').innerHTML, $('dischargeEpicrisis').value);
             }
             catch (e) {}
   			}
   			if (document.getElementsByName("smo_visitProtocolForm") !=null) {
             try {
                 localStorage.setItem("smo_visitProtocolForm"+";"+medCaseId.value+";"+document.getElementById('current_username_li').innerHTML, $('record').value);
             }
             catch(e) {}
         }
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
		 var diaryDate = $('${name}ProtocolDate').value;
    	 edit${name}StyleA(aType) ;
    	 theIs${name}TempProtLastFunction=aType;
    	 var idSmo = '${idSmo}' ;
    	 var pos = idSmo.indexOf('.') ;
    	 if (+aParent>0|| +aParent<0) {} else {aParent=0}
    	 if (pos>0) {
    		 idSmo= $(idSmo.substring(pos+1)).value ;
    	 }
    		 $('${name}divListCategs').innerHTML = "Загрузка..." ;
		 var diaryDate = $('${name}ProtocolDate').value;
        	 TemplateProtocolService.listCategProtocolsByUsername( aParent, aType
        			 , 'load${name}ListProtocols',diaryDate,{
                 callback: function(aString) {
                     $('${name}divListCategs').innerHTML = aString ;
            		 $('${name}divListProtocols').innerHTML = "Загрузка..." ;
            	 	 TemplateProtocolService.listProtocolsByUsername( idSmo,aParent, aType
        			 , 'get${name}TextProtocolById','get${name}TextDiaryById',aSearchText,diaryDate, {
                 		callback: function(aString) {
                     		$('${name}divListProtocols').innerHTML = aString ;
                  		}
              		} ) ;
                  
              }} ) ;
     }
     function load${name}ListProtocols(aType,aParent,aSearchText) {
    	 edit${name}StyleA(aType) ;
    	 theIs${name}TempProtLastFunction=aType;
    	 var diaryDate = $('${name}ProtocolDate').value;
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
    			 , 'get${name}TextProtocolById','get${name}TextDiaryById',aSearchText,diaryDate, {
             callback: function(aString) {
                 $('${name}divListProtocols').innerHTML = aString ;
                 if (document.forms['frm${name}TemplateProt'].${name}typeReestr[0].checked) {
         			// $('${name}divListCategs').innerHTML = "Для отображения категорий перейдите в режим свод" ;
         		 }
              }
          } ) ;
    	 }else{
    		 $('${name}divListCategs').innerHTML = "Загрузка..." ;
			 var diaryDate = $('${name}ProtocolDate').value;
        	 TemplateProtocolService.listCategProtocolsByUsername( idSmo, aType
        			 , 'load${name}ListProtocols',diaryDate,{
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
                 if ($('templateProtocol')) {$('templateProtocol').value = aId;}
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
