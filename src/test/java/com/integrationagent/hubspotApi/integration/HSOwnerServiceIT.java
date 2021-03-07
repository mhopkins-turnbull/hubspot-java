package com.integrationagent.hubspotApi.integration;

import com.integrationagent.hubspotApi.domain.HSOwner;
import com.integrationagent.hubspotApi.service.HubSpot;
import com.integrationagent.hubspotApi.utils.Helper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

public class HSOwnerServiceIT {

    private HubSpot hubSpot = new HubSpot(Helper.getProperty("hubspot.apikey"));

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void listOwners_Test() throws Exception {
        List<HSOwner> owners = hubSpot.owner().getOwners();
        assertNotEquals(0L, owners.size());
    }

    @Test
    public void getOwner_Id_Test() throws Exception {
        List<HSOwner> owners = hubSpot.owner().getOwners();
        assertNotEquals(0L, owners.size());
        Long ownerId = owners.get(0).getOwnerId();
        assertNotNull(ownerId);
        HSOwner owner = hubSpot.owner().getByID(ownerId);
        assertNotNull(owner);
        assertEquals((Long) owner.getOwnerId(), ownerId);
    }

    @Test
    public void getOwner_Id_Not_Found_Test() throws Exception {
        long id = -777;
        assertNull(hubSpot.owner().getByID(id));
    }
}
