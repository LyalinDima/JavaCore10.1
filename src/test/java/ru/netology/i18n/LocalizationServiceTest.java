package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceTest {
    @ParameterizedTest
    @MethodSource("testLocalizationServiceSource")
    public void testLocalizationService(Country country, String expected) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String result = localizationService.locale(country);
        Assertions.assertEquals(result, expected);
    }

    private static Stream<Arguments> testLocalizationServiceSource() {
        return Stream.of(Arguments.of("172.173.174.175", "Добро пожаловать"),
                Arguments.of("96.97.98.99", "Welcome"));
    }
}
