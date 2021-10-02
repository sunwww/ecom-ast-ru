package ru.ecom.rabbit;

import ru.ecom.api.form.PromedPolyclinicTapForm;

public interface IRabbitService {

    void sendPromedPolycMessage(PromedPolyclinicTapForm someObject);

    void sendPromedHospitalMessage(Object someObject);
}
