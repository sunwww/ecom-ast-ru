function onPreDelete(id, ctx) {
    ctx.manager.createNativeQuery("delete from entrymedservicemedimplant where medService_id=" + id).executeUpdate();
}