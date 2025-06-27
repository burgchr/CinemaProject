import io.github.cinema.models.TicketItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketItemTest {

    @Test
    void testTicketItemFields() {
        TicketItem t = new TicketItem("A1", "F1", 10.0);
        assertEquals("A1", t.getSeat());
        assertEquals("F1", t.getFilm());
        assertEquals(10.0, t.getPrice(), 0.01);
    }
}