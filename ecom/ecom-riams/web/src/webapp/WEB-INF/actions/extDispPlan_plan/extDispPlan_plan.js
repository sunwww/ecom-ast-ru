    function view(aForm,aCtx) {

        return aCtx.createForward("/WEB-INF/actions/extDispPlan_plan/view.jsp?id="+aCtx.request.getParameter("id")) ;
    }
    function add(aForm,aCtx) {

        return aCtx.createForward("/WEB-INF/actions/extDispPlan_plan/add.jsp?id="+aCtx.request.getParameter("id")) ;
    }