function onCreate(aForm, aEntity, aContext) {
    if (aEntity.getCareCard() == null) {
        var careCard = aEntity.getLpuAreaPsychCareCard() != null ? aEntity.getLpuAreaPsychCareCard().getCareCard() : null;
        aEntity.setCareCard(careCard);
    }
    aContext.manager.persist(aEntity);
}