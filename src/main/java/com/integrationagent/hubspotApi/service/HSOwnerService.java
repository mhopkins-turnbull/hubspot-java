package com.integrationagent.hubspotApi.service;

import com.integrationagent.hubspotApi.domain.HSContact;
import com.integrationagent.hubspotApi.domain.HSOwner;
import com.integrationagent.hubspotApi.utils.HubSpotException;
import com.mashape.unirest.http.JsonNode;
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
            return parseOwnerListData((JSONArray) httpService.getRequest(url));
        } catch (HubSpotException e) {
            if (e.getCode() == 404) {
                return null;
            } else {
                throw new HubSpotException("Cannot get owners. Reason: " + e.getMessage());
            }
        }
    }

    public HSOwner getByID(long id) throws HubSpotException{
        String url = "/owners/v2/owners/" + id;
        return getOwner(url);
    }

    private HSOwner getOwner(String url) throws HubSpotException {
        try {
            return parseOwnerData((JSONObject) httpService.getRequest(url));
        } catch (HubSpotException e) {
            if (e.getMessage().equals("Not Found")) {
                return null;
            } else {
                throw e;
            }
        }
    }

    public HSOwner parseOwnerData(JSONObject jsonObject) {
        HSOwner HSOwner = new HSOwner();
        HSOwner.setId(jsonObject.getLong("ownerId"));
//        JSONObject jsonProperties = jsonObject.getJSONObject("properties");
        Set<String> keys = jsonObject.keySet();
        keys.stream().forEach( key ->
                HSOwner.setProperty(key,
                        jsonObject.get(key) instanceof JSONObject ?
                                ((JSONObject) jsonObject.get(key)).getString("value") :
                                jsonObject.get(key).toString()
                )
        );
        return HSOwner;
    }

    public List<HSOwner> parseOwnerListData(JSONArray jsonArray) {
        List<HSOwner> owners = new ArrayList<HSOwner>();

        //JSONArray jsonArray = jsonBody.to;
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
