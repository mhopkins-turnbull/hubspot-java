package com.integrationagent.hubspotApi.unit;

import com.integrationagent.hubspotApi.domain.HSOwner;
import com.integrationagent.hubspotApi.service.HSOwnerService;
import com.integrationagent.hubspotApi.service.HttpService;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HSOwnerJSONTest {

    HSOwnerService service = new HSOwnerService(new HttpService("key", "apiBase"));

    @Test
    public void parseOwnerData_Test() throws Exception {
        String inputData = "{test:1,ownerId:71}";
        JSONObject jsonObject = new JSONObject(inputData);

        HSOwner owner = service.parseOwnerData(jsonObject);
        assertEquals(owner.getOwnerId(),71);
        assertEquals(owner.getProperty("test"),"1");
    }

    @Test
    public void toJson_Test() throws Exception {
        String inputData = "{test:1,ownerId:71}";
        JSONObject jsonObject = new JSONObject(inputData);

        HSOwner owner = service.parseOwnerData(jsonObject);
        String result = owner.toJson().toString();
        assertEquals("{\"properties\":[{\"property\":\"test\",\"value\":\"1\"}]}", result);
    }
}
