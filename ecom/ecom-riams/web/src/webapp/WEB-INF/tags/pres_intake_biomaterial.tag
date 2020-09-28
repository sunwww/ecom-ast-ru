<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="role" required="true" description="Роль" %>

<msh:ifInRole roles="${role}">

<style type="text/css">
    #${name}IntakeInfoDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}IntakeInfoDialog' class='dialog'>
    <h2 id='${name}IntakeInfoTitle'>ПРИЕМ БИОМАТЕРИЛА</h2>
    <input type='hidden' name='${name}List' id='${name}List'/>
    <div id="${name}IntakeRootPane" class='rootPane'>
    	
	</div>
</div>

<script type="text/javascript">
    var theIs${name}IntakeInfoDialogInitialized = false ;
    var the${name}IntakeInfoDialog = new msh.widget.Dialog($('${name}IntakeInfoDialog')) ;
     // Показать
     function show${name}IntakeCancel(aPrescipt,aBiomatType) {
         $('${name}IntakeInfoTitle').innerHTML = "ВЫБОР ДЕФЕКТА" ;
         $('${name}List').value=aPrescipt;
         PrescriptionService.getDefectByBiomaterial(aPrescipt, aBiomatType,'${name}', { 
             callback: function(aResult) {
             	$('${name}IntakeRootPane').innerHTML = aResult +"<br><input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}IntakeInfo()\">";
             	the${name}IntakeInfoDialog.show() ;
             }
 		}); 
         
     }
     function save${name}Result(aSmoId,aPrescriptId,aProtocolId, aTemplateId) {
    	 save${name}Result(null, aSmoId,aPrescriptId,aProtocolId, aTemplateId) 
     }
     function save${name}Result(aButton, aSmoId,aPrescriptId,aProtocolId, aTemplateId) {
    	 if (aButton ) aButton.disabled=true;
			var isError = false ;
			for (var ind=0;ind<fldJson.params.length;ind++) {
				var val = $('param'+fldJson.params[ind].id).value ;
				var par = fldJson.params[ind] ; 
				errorutil.HideError($('param'+par.idEnter)) ;
				if (+par.type==2) {
					if (+val<1) {val='0' ;} else {
						par.valueVoc = $('param'+fldJson.params[ind].id+'Name').value ;
					}
				}
				if (par.type=='4') {
					val = val.replace(",",".") ;
					val = val.replace("-",".") ;
					var v = val.split(".") ;
					var decimalRegexp=/^(-?\d+(\.?\d+)?|\d+)$/g;
					var cntdecimal = +par.cntdecimal
					if (val!="") {
                        if (decimalRegexp.test(val)) {
                            if (v.length==2 && v[1].length!=cntdecimal) {
                                errorutil.ShowFieldError($('param'+par.idEnter),"Необходимо ввести "+cntdecimal+" знаков после запятой") ;
                                isError= true ;
                            }
                            if (cntdecimal>0 ) {
                                if (v.length==1) {
                                    errorutil.ShowFieldError($('param'+par.idEnter),"Должна быть 1 точка или запятая") ;
                                    isError= true ;
                                } else if (v.length>1 && !isNaN(v[2])) {
                                    errorutil.ShowFieldError($('param'+par.idEnter),"Должна быть 1 точка или запятая") ;
                                    isError= true ;
                                }
                            }
                        } else {
                            errorutil.ShowFieldError($('param' + par.idEnter), "Указанное значение не является числом!");
                            isError = true;
                        }
                    }
				}
				par.value = val ;
			}
			if (+fldJson.isdoctoredit==0) {
				if (+$('paramWF').value==0) {
					isError=true ;
					errorutil.ShowFieldError($('paramWFName'),"Обязательное поле") ;
				} else {
					fldJson.workFunction=$('paramWF').value
				}
			}
			var str = JSON.stringify(fldJson);
			if (!isError) {
			    PrescriptionService.saveParameterByProtocol(aSmoId,aPrescriptId,aProtocolId,str, aTemplateId, {
				callback: function () {
					cancel${name}IntakeInfo();// убираем reload страницы. //window.document.location.reload();
				}}) ;
			} else {
				 if (aButton ) aButton.disabled=false;
			}
		}
	    
     function get${name}Reason(aReason) {
			if (+aReason>0) {
				reason=prompt('Введите причину брака:', '') ;
				if (reason==null) {
					return null ;
				} else if (reason.trim()=="") {
					return getReason(aReason) ;
				} else {
					return reason ;
				}
				
			} else {
				return "" ;
			}
		}
	    function cancel${name}InLab(aId,aReasonId,aReason) {
            $('BioIntakeRootPane').setAttribute("hidden",true);
	    	aReason = get${name}Reason(aReason) ;
	    	if (aReason!=null) {
	        	PrescriptionService.cancelService(aId,aReasonId,aReason, {
		            callback: function() {
		            	window.document.location.reload();
		            }
				});
	    	} else {
                $('BioIntakeRootPane').removeAttribute("hidden");
	    		cancelBioIntakeInfo();
	    	}	
		}
	    function uncancel${name}Intake(aId) {
        	PrescriptionService.uncancelService(aId, { 
	            callback: function() {
	            	window.document.location.reload();
	            }
			});
		}
     function show${name}IntakeCabinet(aListPrescript,aDepartment,aPrescriptType) {
    	 $('${name}IntakeInfoTitle').innerHTML = "ВЫБОР КАБИНЕТА" ;
         // устанавливается инициализация для диалогового окна
         $('${name}List').value=aListPrescript;
         PrescriptionService.getLabCabinetByPres(aListPrescript, { 
             callback: function(aResult) {
             	$('${name}IntakeRootPane').innerHTML = aResult +"<br><input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}IntakeInfo()\">";
             	the${name}IntakeInfoDialog.show() ;
             }
 		}); 
     }
     
     function go${name}Service(aSmoId,aPrescriptId,aServiceId,aFunctionSave) {
			PrescriptionService.getTemplateByService(aSmoId,aPrescriptId,aServiceId,"enter${name}Result","save${name}Result", {
					callback: function(aResult) {
						//alert(aResult) ;
						var n = +aResult.substring(0,1) ; 
						if (n==0) {
							$('${name}IntakeInfoTitle').innerHTML = "ВЫБОР ШАБЛОНА" ;
					        $('${name}List').value=aSmoId;
					       $('${name}IntakeRootPane').innerHTML = aResult.substring(2) +"<br><input type=\"button\" value=\"Отмена\" onclick=\"cancel${name}IntakeInfo(); this.disabled='true';\">";
			             	theBioIntakeInfoDialog.show() ;
						} else if (n==1) {
							var s =  aResult.substring(2).split("##") ;
							enter${name}Result(aSmoId,aPrescriptId,aServiceId,s[1],0,s[0],aFunctionSave) ;
						} else if (n==2) {
							var s =  aResult.substring(2).split("##") ;
							enter${name}Result(aSmoId,aPrescriptId,aServiceId,"",s[0],0,aFunctionSave) ;
						}
						
					}
				}) ;
			
		}
     function enter${name}Result(aSmoId,aPrescriptId,aServiceId,aServiceName,aProtocolId,aTempId,aFunctionSave) {
    	 	//alert(aTempId) ; 
			PrescriptionService.getParameterByTemplate(aSmoId,aPrescriptId,aServiceId,aProtocolId,aTempId, {
				callback: function (aResult) {
					$('BioIntakeInfoTitle').innerHTML = "ВВОД РЕЗУЛЬТАТА" ;
				        $('BioList').value=aSmoId;
				        fldJson = JSON.parse(aResult) ;
				        var cnt = +fldJson.params.length ;
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
						
				       $('BioIntakeRootPane').innerHTML =txt 
				       	+ "<br><input type=\"button\" id=\"paramOK\" name=\"paramOK\" value=\"Сохранить\" onclick=\" "+aFunctionSave+"(this,"+(+aSmoId)+","+(+aPrescriptId)+","+(+aProtocolId)+","+(+aTempId)+")\">"
				       	+ "<input type=\"button\" value=\"Отмена\" onclick=\"cancelBioIntakeInfo()\">";
		             	theBioIntakeInfoDialog.show() ;
		             	//alert(aResult) ;
		             	
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
				        		
		            	 }
				        for (var ind=0;ind<fldJson.params.length;ind++) {
				        	var param1 = fldJson.params[ind] ;
				        	
				        	if (ind<cnt-1) {
				        		var param2 = fldJson.params[ind+1] ;
				        		eventutil.addEnterSupport("param"+param1.idEnter,"param"+param2.idEnter) ;
				        	} else {
				        		eventutil.addEnterSupport("param"+param1.idEnter,"paramOK") ;
				        		
				        	}
				        	if (+param1.type==2) {
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
		             } else {
		            	var txt ="<form name='frmTemplate' id='frmTemplate'>" ;
		 				txt += "<a target='_blank' href='diary_templateView.do?id="+fldJson.template+"'>НЕПРАВИЛЬНО ЗАПОЛНЕНЫ ДАННЫЕ ПАРАМЕНТОВ ШАБЛОНА</a>" ;
		 				txt +="</form>" ;
		 				$('BioIntakeRootPane').innerHTML =txt 
				       	+ "<input type=\"button\" value=\"ОК\" onclick=\"cancelBioIntakeInfo()\">";
				     }
		             
		             if (+fldJson.isdoctoredit>0) {

	            		 $("param"+fldJson.params[0].idEnter).select() ;
			        	 $("param"+fldJson.params[0].idEnter).focus() ;
	            	 } else {
	            		 $("param"+p+"Name").select() ;
			        		$("param"+p+"Name").focus() ;
			        		$('param'+p+'Name').className="autocomplete horizontalFill required" ;
			        		eventutil.addEnterSupport("param"+p+"Name","param"+fldJson.params[0].idEnter) ;
			        		
	            	 }
				}
			}) ;
		}
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
	        } else {
		        txt += "<input onblur='checkFieldValue(this, "+aParam.id+")' id=\"param"+aParam.id+"\" name=\"param"+aParam.id+"\" type=\"text\" value=\""+aParam.value+"\" title=\""+aParam.name+"\" autocomplete=\"off\">";
		        
	        }
	        txt += "</td>" ;
	        txt += "<td>" ;
	        if (+aParam.type==4) {txt += " ("+aParam.cntdecimal+") ";}
	        txt += aParam.unitname ;
	        txt += "</td>" ;
	        txt += "</tr>" ;
			return txt ;
		}


		/*Проверяем на минимальные / максимальные значения*/
		function checkFieldValue(txtField, id) {
		    var val = +jQuery(txtField).val().replace(",",".").trim();
		    if (val){
                var el = getParameterById(id);
                if (el.nmin || el.nmax) {
                    if (val < el.min || val > el.max) { //выходит вообще за всякие рамки
                        if (! confirm ("Значение "+val +(val <el.nmin ? " меньше минимального значения "+(+el.min) : " превышает максимальное значение "+(+el.max))+", вы уверены? ")) {
                            jQuery(txtField).val("");
                            return;
                        }
                    }
                    if (val < el.nmin || val > el.nmax) { //выход за пределы реф. значений
                        jQuery(jQuery(txtField).parent().parent().children()[2]).html((val < el.nmin ? "<img src='/skin/images/arrow/green-down.png'>" : "<img src='/skin/images/arrow/red-up.png'>")+"" +
                            " (норм "+(+el.nmin)+" - "+(+el.nmax)+")");
                    } else { //значение в норме
                        jQuery(jQuery(txtField).parent().parent().children()[2]).html("("+el.cntdecimal+") "+el.unitname);
                    }
                }
            }
        }

        function getParameterById(id) {
		    var ret = jQuery.grep(fldJson.params, function(el) {return el.id==id;});
		    return ret ? ret[0] : {} ;
        }

     // Отмена
     function cancel${name}IntakeInfo() {
         the${name}IntakeInfoDialog.hide() ;
         msh.effect.FadeEffect.pushFadeAll();
     }

     function annul${name}Result(aPrescriptionId) {
    	 var reason = prompt('Введите причину аннулирования результата');
    	 if (reason!=null&&reason!='') {
    		 PrescriptionService.annulPrescription(aPrescriptionId, reason 
    	    			, { callback:function(a) {
    	    				 alert(a);
    	    			}
    	    			 });	 
    	 }
    	 
     }
     // Сохранение данных
     function save${name}IntakeInfo() {
     	PrescriptionService.intakeService($('${name}List').value,$('${name}Date').value, $('${name}Time').value, { 
            callback: function(aResult) {
            	if (aResult=="1") {
            		window.document.location.reload();
            	} else {
            		alert ("Ошибка: "+aResult);
            	}
            	
            }
		}); 
     }
     
     function save${name}Intake() {
    	
 		PrescriptionService.intakeService($('${name}List').value, textDay+'.'+textMonth+'.'+textYear
      			,(textHour<10?'0'+textHour:textHour)+':'+(textMinute<10?'0'+textMinute:textMinute)
  				, { 
	            callback: function() {
	            	window.document.location.reload();
	            }
			});
    	 
     }
     // инициализация диалогового окна
     function init${name}IntakeInfoDialog() {
     	
     	theIs${name}IntakeInfoDialogInitialized = true ;
     	
     }
</script>
</msh:ifInRole>