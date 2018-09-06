package ru.ecom.api;

/** Created by rkurbanov on 16.05.2018. */

public interface IApiService {
    void persistEntity(Object object);
    Object persistEntityObj(Object object);
}
