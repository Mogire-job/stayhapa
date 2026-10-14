package com.stayhapa.dto;

import com.stayhapa.entity.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PropertyResponseTest {
    @Test
    void publicMapLocationIsApproximateAndDoesNotExposeExactCoordinates() {
        Property property = new Property();
        property.setLatitude(-1.286391);
        property.setLongitude(36.817223);

        PropertyResponse response = PropertyResponse.fromPublicEntity(property);

        assertEquals(-1.29, response.getMapLatitude());
        assertEquals(36.82, response.getMapLongitude());
        assertNull(response.getLatitude());
        assertNull(response.getLongitude());
    }

    @Test
    void publicPropertyWithoutCoordinatesHasNoMapLocation() {
        PropertyResponse response = PropertyResponse.fromPublicEntity(new Property());

        assertNull(response.getMapLatitude());
        assertNull(response.getMapLongitude());
    }
}
