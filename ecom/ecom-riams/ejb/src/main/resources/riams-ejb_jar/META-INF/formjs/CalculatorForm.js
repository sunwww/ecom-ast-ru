function onPreCreate(aForm, aCtx) {
    aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
}