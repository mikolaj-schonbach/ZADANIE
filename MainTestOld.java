import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    void shouldCreateMap() {
        //given
        String[] args = {"lista.txt"};
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("#{APLIKACJA}", "ZADANIE");
        map1.put("#{APLIKACJA_OPIS}", "Program do przetwarzania szablonów plików");
        map1.put("#{WERSJA}", "1.00");
        map1.put("#{DATAWYDANIA}", "2022-07-07");


        //when
        Map<String, String> map2 = Main.createMap(args);

        //then
        assertEquals(map1, map2);
    }

    @Test
    void shouldLoadTemplate() {
        //given
        String[] args = {"lista.txt", "szablon.txt"};
        String text1 = "W dniu #{DATAWYDANIA} przygotowana została nowa wersji aplikacji #{APLIKACJA}\n" +
                "Najnowszy plik aplikacji #{APLIKACJA}.jar jest do pobrania z\n" +
                "Htpp://127.0.0.1:8080/download/#{APLIKACJA}/#{WERSJA}/#{APLIKACJA}.install.zip\n";

        //when
        String text2 = Main.loadTemplate(args);

        //then
        assertEquals(text1,text2);
    }
}
