package ru.netology.geo;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

public class GeoServiceTest {
    @ParameterizedTest
    @MethodSource("testGeoServiceSource")
    public void testGeoServiceIp(String ip, Location expected) {
        GeoService geoService = new GeoServiceImpl();
        Location result = geoService.byIp(ip);
        MatcherAssert.assertThat(result, Matchers.samePropertyValuesAs(expected));
    }

    @Test
    public void testGeoServiceCoordinates() {
        GeoService geoService = new GeoServiceImpl();
        Assertions.assertThrows(RuntimeException.class, () -> geoService.byCoordinates(1d, 1d));
    }

    private static Stream<Arguments> testGeoServiceSource() {
        return Stream.of(Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32))
        );
    }

}
