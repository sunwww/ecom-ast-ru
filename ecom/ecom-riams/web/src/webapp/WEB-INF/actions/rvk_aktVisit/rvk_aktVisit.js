function actView(aForm,aCtx) {
    var id=aCtx.request.getParameter("id") ;
    var list =id.split("!") ;
    return list[1]=='Visit'? aCtx.createForwardRedirect(
        "/entityView-rvk_aktVisit.do?id="+list[0]) :
        aCtx.createForwardRedirect(
            "/entityView-rvk_aktSlo.do?id="+list[0]) ;
}