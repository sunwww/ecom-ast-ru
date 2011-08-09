<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<!-- CSS  -->
<msh:stylesheet src='/skin/css/css/main/main.css'/>
<style type="text/css">
	@import url("/skin/css/css/main/main_fo<%=ru.nuzmsh.web.filter.caching.CacheUniqueUtil.getUniqueId()%>.css") all;
</style>
<!--[if IE]>
	<msh:stylesheet src='/skin/css/css/main/main_ie.css'/>
<![endif]-->

<script type="text/javascript">
try {
  switch(screen.width) {
      case 800: document.write("<msh:stylesheet src='/skin/css/css/main/main_800.css'/>") ; break ;
      case 1024: document.write("<msh:stylesheet src='/skin/css/css/main/main_1024.css'/>") ; break ;
  }
} catch (e) {
}
</script>
<!-- JavaScript -->

<msh:javascriptSrc src='/skin/js/js/prototype/prototype-1.3.1.js' />
<msh:javascriptSrc src='/skin/js/js/tablearrow/tablearrow.js' />
<msh:javascriptSrc src='/skin/js/js/json/json.js' />
<msh:javascriptSrc src='/skin/js/js/main/cookie.js' />
<msh:javascriptSrc src='/skin/js/js/main/msh.js' />
<msh:javascriptSrc src='/skin/js/js/main/effect/FadeEffect.js' />
<msh:javascriptSrc src='/skin/js/js/main/widget/Dialog.js' />
<msh:javascriptSrc src='/skin/js/js/main/widget/TreeTableDialog.js' />
<msh:javascriptSrc src='/skin/js/js/main/widget/AutocompleteTableView.js' />
<msh:javascriptSrc src='/skin/js/js/main/widget/TreeAutocompleteTableView.js' />
<msh:javascriptSrc src='/skin/js/js/main/widget/TreeTable.js' />
<msh:javascriptSrc src='/skin/js/js/main/util/FormData.js' />
<msh:javascriptSrc src='/skin/js/js/main/errorutil.js' />
<msh:javascriptSrc src='/skin/js/js/main/eventUtil.js' />
<msh:javascriptSrc src='/skin/js/js/main/accessKeyUtil.js' />
<msh:javascriptSrc src='/skin/js/js/main/dateutil.js' />
<msh:javascriptSrc src='/skin/js/js/main/timeutil.js' />
<msh:javascriptSrc src='/skin/js/js/main/keysupport.js' />
<msh:javascriptSrc src='/skin/js/js/main/tabbedpane.js' />
<msh:javascriptSrc src='/skin/js/js/main/autocomplete.js' />
<msh:javascriptSrc src='/skin/js/js/main/widget/VocValueEdit.js' />
<msh:javascriptSrc src='/skin/js/js/main/form.js' />
<msh:javascriptSrc src='/skin/js/js/main/widget/OneToManyAutocompletes.js' />


<script type='text/javascript' src='./dwr/engine.js'> </script>
<msh:javascriptSrc src='./dwr/interface/VocService.js' />
<msh:javascriptSrc src='./dwr/interface/VocEditService.js' />
<msh:javascriptSrc src='./dwr/interface/RemoteMonitorService.js' />
