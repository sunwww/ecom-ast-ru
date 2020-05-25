package ru.ecom.api.entity;

import com.google.gson.Gson;

public class JsonEntity {
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, this.getClass());
    }
}
