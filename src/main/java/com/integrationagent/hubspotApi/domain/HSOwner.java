package com.integrationagent.hubspotApi.domain;

import com.google.common.base.Strings;
import com.integrationagent.hubspotApi.utils.HubSpotHelper;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HSOwner {

    private Map<String, String> properties = new HashMap<>();

    public HSOwner() {
    }

    public long getOwnerId() {
        return !Strings.isNullOrEmpty(this.properties.get("ownerId")) ? Long.parseLong(this.properties.get("ownerId")) : 0;
    }

    public HSOwner setId(long id) {
        this.properties.put("ownerId", Long.toString(id));
        return this;
    }

    public String getEmail() {
        return this.properties.get("email");
    }

    public HSOwner setEmail(String email) {
        this.properties.put("email", email);
        return this;
    }

    public String getFirstname() {
        return this.properties.get("firstName");
    }

    public HSOwner setFirstname(String firstname) {
        this.properties.put("firstname", firstname);
        return this;
    }

    public String getLastname() {
        return this.properties.get("lastName");
    }

    public HSOwner setLastname(String lastname) {
        this.properties.put("lastname", lastname);
        return this;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public HSOwner setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public HSOwner setProperty(String property, String value) {
        this.properties.put(property, value);
        return this;
    }

    public String getProperty(String property) {
        return this.properties.get(property);
    }

    public String toJsonString() {
        Map<String, String> properties = this.properties.entrySet().stream()
                .filter(p -> !p.getKey().equals("ownerId"))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        return HubSpotHelper.mapToJsonString(properties);
    }

    public JSONObject toJson() {

        Map<String, String> properties = new HashMap<>(getProperties());
        properties.remove("ownerId");

        return HubSpotHelper.mapToJson(properties);
    }

}