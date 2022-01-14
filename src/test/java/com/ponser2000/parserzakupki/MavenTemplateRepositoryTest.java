package com.ponser2000.parserzakupki;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit-level testing for {@link com.ponser2000.parserzakupki.ParserZakupkiApplication} object.
 */
public class MavenTemplateRepositoryTest {

    @Test
    public void shouldCreateJavaRepositoryTemplateMain() {
        ParserZakupkiApplication main = new ParserZakupkiApplication();
        Assertions.assertNotNull(main);
    }

}
