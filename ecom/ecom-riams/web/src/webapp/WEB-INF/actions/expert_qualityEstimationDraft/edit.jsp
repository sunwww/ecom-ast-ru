<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.mis.web.dwr.medcase.QualityEstimationServiceJs"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Экспертная карта
    	  -->
    <msh:form  action="/entityParentSaveGoView-expert_qualityEstimationDraft.do" defaultField="criterion1Name">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:hidden guid="hiddenSavefgdfs" property="expertType" />
      <msh:hidden guid="hiddenSavegfdsgfd" property="card" />
      <msh:hidden guid="hiddenSavefd" property="criterions" />
      <msh:panel guid="panel" colsWidth="5%,5%,10%">
        <msh:row>
	        <td colspan="6">
	        	<div id='loadCriterion'>
	        	</div>
	        </td>
        </msh:row>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/EditExpert">
	        <msh:row>
	        	<msh:autoComplete  property="expert" label="Специалист" vocName="workFunction" fieldColSpan="3" horizontalFill="true" viewOnlyField="true"/>
	        </msh:row>
        </msh:ifNotInRole>
        <msh:ifInRole roles="/Policy/Mis/MedCase/QualityEstimationCard/QualityEstimation/EditExpert">
	        <msh:row>
	        	<msh:autoComplete  property="expert" label="Специалист" vocName="workFunction" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
        </msh:ifInRole>
        <msh:row>
        	<msh:textField property="createUsername" viewOnlyField="true" label="Пользователь"/>
        	<msh:textField property="createDate" viewOnlyField="true" label="Дата создания"/>
        </msh:row>
          <msh:row>
              <msh:checkBox property="isDraft" viewOnlyField="true" label="Черновик?"/>
          </msh:row>
        <msh:row>
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel"   colSpan="3" />
      </msh:panel>
    </msh:form>


  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:expert_enterCritComment name="Enter"/>
      <tags:expert_commentYesNo name="YesNo"/>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <div id='loadJavascriptCriterion'></div>
  <script type="text/javascript" src="./dwr/interface/QualityEstimationService.js" >
  /*   */
  </script>
  <msh:ifFormTypeIsCreate formName="expert_qualityEstimationDraftForm">
  		<script type="text/javascript">
  			if ($('expertType').value=="") $('expertType').value='${param.type}' ;
  		</script>
  </msh:ifFormTypeIsCreate>
  		<script type="text/javascript">
            var voc;
            var medCaseId;
           //alert($F('card'));
            QualityEstimationService.IsQECardKindBoolean($F('card'), {
                callback: function (k) {
                   if (+k==1) {
                       voc="vocQualityEstimationMarkYesNo";
                   }
                   else if (+k==-1) {
                       voc="vocQualityEstimationMarkKMP";
                   }
                   else
                       voc="vocQualityEstimationMark";
                    //alert(voc);
                }

            });
            QualityEstimationService. getDepMedcaseFromDraftEK($F('card'), {
                callback: function (res) {
                    medCaseId=res;
                    if ((''+window.location).indexOf('entityParentView-expert_qualityEstimationDraft')!=-1)
                        window.location="entityParentView-stac_slo.do?id="+ medCaseId;
                }

            });
  		var cntCrit = 0;
  			function loadDataCriterion() {
  				//alert($('criterions').value) ;
	  			QualityEstimationService.getRowEdit($('criterions').value,$('card').value,$('expertType').value,false
	  				,{
						 callback: function(aRow) {
						     //alert(aRow.substring(1000));
						     	if (aRow!=null) {
						     		$('loadCriterion').innerHTML = aRow ;
						     		cntCrit=+$('criterionSize').value ;
			     					updateVoc();
			     					updateCriterions() ;
						     				
						     		
						     	}
						  	}
					}
	  				
	  			);}
	  			loadDataCriterion() ;
	  			function updateVoc() {
	  				firstFld = "" ;
	  				for (var i=0;i<cntCrit;i++) {
	  					var ii=i+1;
	  					if ($('criterion'+ii+'Name')) {
	  						
	  						if (firstFld="") firstFld='criterion'+ii+'Name';
	  							
							//		 class ru.nuzmsh.web.tags.AutoCompleteTag
							
							//if (ii==1) {
							//	eventutil.addEnterSupport('workFunctionName','criterion'+ii+'Name');
							//}
							if (ii==cntCrit) {
								eventutil.addEnterSupport('criterion'+ii+'Name', 'expertName');
							} else {
								eventutil.addEnterSupport('criterion'+ii+'Name', 'criterion'+(ii+1)+'Name');
							}
							//		 class ru.nuzmsh.web.tags.AutoCompleteTag
							eval("var criterion"+ii+"Autocomplete = new msh_autocomplete.Autocomplete()") ;
							eval("criterion"+ii+"Autocomplete.setUrl('simpleVocAutocomplete/"+voc+"') ");
							eval("criterion"+ii+"Autocomplete.setIdFieldId('criterion"+ii+"') ");
							eval("criterion"+ii+"Autocomplete.setNameFieldId('criterion"+ii+"Name') ");
							eval("criterion"+ii+"Autocomplete.setDivId('criterion"+ii+"Div') ");
							eval("criterion"+ii+"Autocomplete.setVocKey('"+voc+"') ");
							eval("criterion"+ii+"Autocomplete.setVocTitle('Оценка зав.отд.')") ;
							eval("criterion"+ii+"Autocomplete.build() ");
							eval("criterion"+ii+"Autocomplete.setParentId('"+$('criterion'+ii+'P').value+"')") ;
							eval("criterion"+ii+"Autocomplete.addOnChangeCallback(function() { checkCommentNeeded('criterion"+ii+"',"+ii+"); })") ;
	  					}
					}
					if (firstFld!="") {
						
						$(firstFld).select() ;
						$(firstFld).focus() ;
						
					}
					
	  			}
					
					
	  			function checkCommentNeeded(aCriterion,ii,val) {
	  				var aMarkId = $(aCriterion).value;
                    if (voc=="vocQualityEstimationMarkYesNo") {
                        aMarkId=val;
                        if ($('radio'+ii+'1') && $('radio'+ii+'1').checked)
                            $('criterion'+ii).value=val;
                        else if ($('radio'+ii+'0') && $('radio'+ii+'0').checked)
                            $('criterion'+ii).value=val;
                    }
	  				if (+aMarkId>0) {
                        if (voc == "vocQualityEstimationMarkYesNo") {
                            var createEdit=null; //всегда
                           /* var action = ''+window.location;
                            if (action.indexOf('entityParentPrepareCreate')!=-1) createEdit=false; else createEdit=true;*/
                            QualityEstimationService.GetIfCommentYesNoNeeded($('expertType').value,aMarkId,${param.id},createEdit, {
                                callback: function (res) {
                                    //alert(res);
                                    if (res=='true') {
                                        var total=$('criterion'+ii+'CommentYesNo').value;
                                        //alert(total);
                                        var tmp=total.split(';');
                                        var comment;
                                        if ($('expertType').value=='BranchManager') {
                                            if (tmp[0]!=null) comment=tmp[0]; else comment='';
                                        }
                                        if ($('expertType').value=='Expert') {
                                            if (tmp[1]!=null) comment=tmp[1]; else comment='';
                                        }
                                        if ($('expertType').value=='Coeur') {
                                            if (tmp[2]!=null) comment=tmp[2]; else comment='';
                                        }
                                        showYesNoCommentYesNo(comment,aMarkId,ii,$('expertType').value);
                                    }
                                }
                                });
                        }
                            QualityEstimationService.checkIsCommentNeed(aMarkId, {
                                callback: function (res) {
                                    var a = +res.split("@")[0];
                                    if (+a == 1) {
                                        showEnterCritComment(res.split("@")[1], aCriterion);

                                    } else {
                                        $(aCriterion + 'Comment').value = "";
                                        updateCriterions();
                                    }
                                }
                            });
                    }
	  				
	  			}
	  			function updateCriterions() {
	  				var rez ="" ;

	  				for (var i=0;i<cntCrit; i++) {
	  					if ($("criterion"+(i+1)) && $("criterion"+(i+1)).value!="") rez=rez+"#"+$("criterion"+(i+1)+"P").value+":"+$("criterion"+(i+1)).value +":"+($("criterion"+(i+1)+"Comment").value);
	  					if ($('criterion'+(i+1)+'CommentYesNo').value!=null) {
                            var total=$('criterion'+(i+1)+'CommentYesNo').value;
                            //alert(total);
                            var tmp=total.split(';');
                            var comment;
                            if ($('expertType').value=='BranchManager') {
                                if (tmp[0]!=null) comment=tmp[0]; else comment='';
                            }
                            if ($('expertType').value=='Expert') {
                                if (tmp[1]!=null) comment=tmp[1]; else comment='';
                            }
                            if ($('expertType').value=='Coeur') {
                                if (tmp[2]!=null) comment=tmp[2]; else comment='';
                            }
	  					    rez=rez+":"+comment;
                            //$('criterion'+(i+1)+'CommentYesNo').value=document.getElementById('reasonYesNo').value;
                        }
	  					else  rez=rez+":";
	  				}
	  				$('criterions').value=rez.length>0?rez.substring(1) :"";
                   // alert($F('criterions'));
	  			}
            //Milamesher просмотр комментария по кнопке 04062018 с сервисбина
            function showYesNoCommentFromBean(ii) {
                var aMarkId = $('criterion'+ii).value;
                if (voc=="vocQualityEstimationMarkYesNo") {
                    if ($('radio'+ii+'1') && $('radio'+ii+'1').checked)
                        aMarkId=$('radio'+ii+'1').value;
                    else if ($('radio'+ii+'0') && $('radio'+ii+'0').checked)
                        aMarkId=$('radio'+ii+'0').value;
                }
                if (+aMarkId>0 && voc == "vocQualityEstimationMarkYesNo") {
                    var total = $('criterion' + ii + 'CommentYesNo').value;
                    //alert(total);
                    var tmp = total.split(';');
                    var comment;
                    if ($('expertType').value == 'BranchManager') {
                        if (tmp[0] != null) comment = tmp[0]; else comment = '';
                    }
                    if ($('expertType').value == 'Expert') {
                        if (tmp[1] != null) comment = tmp[1]; else comment = '';
                    }
                    if ($('expertType').value == 'Coeur') {
                        if (tmp[2] != null) comment = tmp[2]; else comment = '';
                    }
                    showYesNoCommentYesNo(comment, aMarkId, ii, $('expertType').value,false);
                }
            }
  		</script>

  		
  </tiles:put>
</tiles:insert>