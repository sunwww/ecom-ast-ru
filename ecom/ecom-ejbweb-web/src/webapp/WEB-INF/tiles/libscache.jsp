<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<msh:javascriptSrc src='/skin/js/ac.js' />
<msh:javascriptSrc src='/skin/js/engine.js' />
<msh:javascriptSrc src='./dwr/interface/VocService.js' />
<msh:javascriptSrc src='./dwr/interface/VocEditService.js' />
<msh:javascriptSrc src='./dwr/interface/RemoteMonitorService.js' />
<%--<msh:javascriptSrc src='./dwr/interface/AddressService.js' /> --%>