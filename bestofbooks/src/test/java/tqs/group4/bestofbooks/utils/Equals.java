package tqs.group4.bestofbooks.utils;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class Equals {
    public static <T> void verifyEquals(Class<T> tClass) {
        EqualsVerifier.forClass(tClass).usingGetClass().suppress(Warning.NONFINAL_FIELDS).verify();
    }
}
