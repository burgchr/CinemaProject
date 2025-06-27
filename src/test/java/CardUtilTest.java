import io.github.cinema.utils.CardUtil;
import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class CardUtilTest {

    private static final String TEST_XML = "src/test/resources/cards_test.xml";

    @BeforeAll
    static void setup() throws IOException {
        String xml = """
        <cards>
            <card code="1234" balance="50.00"/>
            <card code="5678" balance="20.00"/>
        </cards>
        """;
        new File("src/test/resources").mkdirs();
        try (FileWriter fw = new FileWriter(TEST_XML)) {
            fw.write(xml);
        }

        CardUtil.setCardsXmlPath(TEST_XML);
    }

    @Test
    void testGetCardBalance() {
        // You may need to temporarily change CardUtil.CARDS_XML to TEST_XML for this test
        assertEquals(50.00, CardUtil.getCardBalance("1234"), 0.01);
        assertEquals(20.00, CardUtil.getCardBalance("5678"), 0.01);
        assertEquals(-1, CardUtil.getCardBalance("9999"), 0.01);
    }

    @Test
    void testUpdateCardBalance() {
        CardUtil.updateCardBalance("1234", 42.42);
        assertEquals(42.42, CardUtil.getCardBalance("1234"), 0.01);
    }
}