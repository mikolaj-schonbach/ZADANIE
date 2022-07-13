import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainMethodTests {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void shouldCreateOutputFile() {
        //given
        String[] args = {"lista.txt", "szablon.txt", "plik_wyjsciowy.txt"};


        //when
        Main.main(args);

        //then
        assertEquals("Utworzono plik \"plik_wyjsciowy.txt\" \n" +
                "Ścieżka do pliku: C:\\Users\\mekej\\Desktop\\ZADANIE REKRUTACYJNE\\plik_wyjsciowy.txt", outputStreamCaptor.toString().trim());
    }

    @Test
    void shouldInformAboutParameter3Missing() {
        //given
        String[] args = {"lista.txt", "szablon.txt",};


        //when
        Main.main(args);

        //then
        assertEquals("Nie podano parametru 3\nProgram zakończył działanie", outputStreamCaptor.toString());
    }




}
