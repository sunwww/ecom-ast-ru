<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ attribute name="nameAutocompleteClassif" required="true" description="Наименование поля, к которому подключается обработчик ключевых слов" %>
<%@ attribute name="nameFieldCategory" required="true" description="Наименование поля, к которому подключается обработчик ключевых слов" %>
<%@ attribute name="label" required="true" description="Заголовок поля" %>
<msh:autoComplete property="${nameFieldCategory}TreeCategory" vocName="Mkb" fieldColSpan="5" size="60" horizontalFill="true" label="${label}" />
<script type='text/javascript' src='./dwr/interface/CategoryTreeService.js'></script>
<script type="text/javascript">
//    var theTreeCategory = ${nameFieldCategory}TreeCategory ;
//    var theClassif = eval("${nameAutocompleteClassif}"+"Autocomplete");
        var vocabulary ;

    var changeClassif = function() {
        vocabulary = '' ;

        CategoryTreeService.getVocabulary(${nameAutocompleteClassif}Autocomplete.getVocId(), {
                callback:function(aString) {
                	//console.warn(aString) ;
                    if (aString!=null && aString!="") {
                       // alert(aString) ;
                        try {
                            $('${nameFieldCategory}').value = '';
                            ${nameFieldCategory}TreeCategoryAutocomplete.setUrl('simpleVocAutocomplete/'+aString) ;
                            ${nameFieldCategory}TreeCategoryAutocomplete.setVocKey(aString) ;
                            ${nameFieldCategory}TreeCategoryAutocomplete.setVocId('') ;
                        } catch (e) {
                        	
                            // alert("fasfdasf") ;
                            ${nameFieldCategory}TreeCategoryAutocomplete.setVocId('') ;
                            $('${nameFieldCategory}').value = '';
                        }
                    } else {
                        alert("Нет справочника");
                    }
                }
        })
    }
    var changeValueTree= function() {
        $('${nameFieldCategory}').value = ${nameFieldCategory}TreeCategoryAutocomplete.getVocId() ;
    }
    function initCategoryTree() {


        ${nameAutocompleteClassif}Autocomplete.addOnChangeCallback(changeClassif) ;

        ${nameFieldCategory}TreeCategoryAutocomplete.addOnChangeCallback(changeValueTree) ;
        CategoryTreeService.getVocabulary(${nameAutocompleteClassif}Autocomplete.getVocId(), {
                callback:function(aString) {
                    if (aString!=null && aString!="") {
                        ${nameFieldCategory}TreeCategoryAutocomplete.setUrl('simpleVocAutocomplete/'+aString) ;
                        ${nameFieldCategory}TreeCategoryAutocomplete.setIdFieldId('${nameFieldCategory}TreeCategory') ;
                      //${nameFieldCategory}TreeCategoryAutocomplete.setNameFieldId('${nameFieldCategory}TreeCategoryName') ;
                      //${nameFieldCategory}TreeCategoryAutocomplete.setDivId('${nameFieldCategory}TreeCategoryDiv') ;
                        ${nameFieldCategory}TreeCategoryAutocomplete.setVocKey(aString) ;
                      //${nameFieldCategory}TreeCategoryAutocomplete.setVocTitle('') ;
                        ${nameFieldCategory}TreeCategoryAutocomplete.setVocId($('${nameFieldCategory}').value) ;
                        //${nameFieldCategory}TreeCategoryAutocomplete.build() ;


                    } else {
//                        alert("Нет справочника");
                    }
                }
        })
//        if (theClassif.getVocKey() == null || theClassif.getVocKey()== "") {
//            alert(1111)
//
//        } else {
//            alert(theClassif.getVocKey()) ;
//
//        }
//        eval("${nameFieldCategory}"+"Autocomplete").setUrl('simpleVocAutocomplete/TemplateClassif') ;
//        eval("${nameFieldCategory}"+"Autocomplete").setIdFieldId('classif') ;
//        eval("${nameFieldCategory}"+"Autocomplete").setNameFieldId('classifName') ;
//        eval("${nameFieldCategory}"+"Autocomplete").setDivId('classifDiv') ;
//        eval("${nameFieldCategory}"+"TreeCategoryAutocomplete").setVocKey('TemplateClassif') ;
//        eval("${nameFieldCategory}"+"TreeCategoryAutocomplete").setVocId('2') ;
//
//        eval("${nameFieldCategory}"+"TreeCategoryAutocomplete").setVocTitle('') ;


    }
</script>


