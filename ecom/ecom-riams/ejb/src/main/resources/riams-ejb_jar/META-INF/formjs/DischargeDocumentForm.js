function onCreate(aForm, aEntity, aCtx) {
    var bean = new Packages.ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean();
    bean.sendProtocolToExternalResource(null,aEntity.getMedCase().getId(),aEntity.getHistory(),aCtx.manager);
}