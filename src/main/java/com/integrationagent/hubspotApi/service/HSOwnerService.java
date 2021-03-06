package com.integrationagent.hubspotApi.service;

import com.mashape.unirest.http.JsonNode;
import com.integrationagent.hubspotApi.domain.HSOwner;
import com.integrationagent.hubspotApi.utils.HubSpotException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HSOwnerService {

    private HttpService httpService;

    public HSOwnerService(HttpService httpService) {
        this.httpService = httpService;
    }

    public List<HSOwner> getOwners() throws HubSpotException{
        String url = "/owners/v2/owners/";

        try {
            return parseOwnerData((JsonNode) httpService.getRequest(url));
        } catch (HubSpotException e) {
            if (e.getCode() == 404) {
                return null;
            } else {
                throw new HubSpotException("Cannot get owners. Reason: " + e.getMessage());
            }
        }
    }

    private List<HSOwner> parseOwnerData(JsonNode jsonBody) {
        List<HSOwner> owners = new ArrayList<HSOwner>();

        JSONArray jsonArray = jsonBody.getArray();
        for (Object o : jsonArray) {
            if (o instanceof JSONObject) {
                HSOwner owner = parseOwner((JSONObject) o);
                owners.add(owner);
            }
        }
        return owners;
    }
    private HSOwner parseOwner(JSONObject jsonProperties) {

        HSOwner owner = new HSOwner();

        Set<String> keys = jsonProperties.keySet();

        keys.stream().forEach(key ->
                owner.setProperty(key,
                        jsonProperties.get(key) instanceof JSONObject ?
                                ((JSONObject) jsonProperties.get(key)).getString("value") :
                                jsonProperties.get(key).toString()
                )
        );
        return owner;
    }
}
