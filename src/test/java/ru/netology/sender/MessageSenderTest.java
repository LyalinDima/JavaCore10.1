package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderTest {
    @ParameterizedTest
    @MethodSource("testMessageSenderSource")
    public void testMessageSender(String ip, String expected) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("96"))).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(geoService.byIp(Mockito.startsWith("172"))).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Mockito.any())).thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = messageSender.send(headers);
        Assertions.assertEquals(result, expected);
    }

    private static Stream<Arguments> testMessageSenderSource() {
        return Stream.of(Arguments.of("172.173.174.175", "Добро пожаловать"),
                Arguments.of("96.97.98.99", "Welcome"));
    }
}
