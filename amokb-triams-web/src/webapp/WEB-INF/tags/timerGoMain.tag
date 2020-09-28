<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ attribute name="interval" required="true" description="" %>

<script type="text/javascript">
	
	function goMainPage() {
		window.location='start.do' ;
	}
	setTimeout("goMainPage()",${interval}) ;
</script>