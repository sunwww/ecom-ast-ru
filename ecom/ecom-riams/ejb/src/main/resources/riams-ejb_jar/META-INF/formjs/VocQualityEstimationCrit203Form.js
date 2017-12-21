function onPreCreate(aForm, aCtx) {
    aForm.setShortName("");
    var maxid = +aCtx.manager.createNativeQuery("select max(id) from vocqualityestimationcrit").getResultList().get(0) +1;
    aForm.setCode("0"+maxid);
    aForm.setKind(new java.lang.Long(aCtx.manager.createNativeQuery("select id from vocqualityestimationkind where code='PR203'").getResultList().get(0)));
    aForm.setIsBoolean(true);
}