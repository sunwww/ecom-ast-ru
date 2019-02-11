package ru.ecom.api.hospital;

import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/hospitalMedCase")
public class HospitalMedCaseResource {

    @GET
    public String hello() {
        return "hello";
    }

    @POST
    @Path("/listClosedHospitals")
    /**Список закрытых СЛС за определенные период*/
    public JSONObject getClosedMedcases (@QueryParam("finishDateFrom") String aFinishDateFrom
        ,@QueryParam("finishDateTo") String aFinishDateTo
    ) {
        JSONObject ret = new JSONObject();


        return ret;
    }

    private JSONObject getOkJson() {
        return new JSONObject().put("status","ok");
    }
    private JSONObject getErrorJson(String aErrorCode, String aErrorName) {
        return new JSONObject().put("status","error").put("errorNama",aErrorName!=null ? aErrorName : "").put("errorCode",aErrorCode!=null ? aErrorCode : "");
    }
}
