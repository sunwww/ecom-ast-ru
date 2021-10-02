package ru.ecom.rabbit;

public interface IRabbitService {

    void sendPromedPolycMessage(Object someObject);

    void sendPromedHospitalMessage(Object someObject);
}
