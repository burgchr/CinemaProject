import io.github.cinema.models.OrderItem;
import io.github.cinema.models.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void testOrderItemTotal() {
        Product p = new Product("Popcorn", 2.5);
        OrderItem item = new OrderItem(p, 3);
        assertEquals(7.5, item.getTotal(), 0.001);
    }

    @Test
    void testSetQuantity() {
        Product p = new Product("Soda", 1.5);
        OrderItem item = new OrderItem(p, 2);
        item.setQuantity(5);
        assertEquals(5, item.getQuantity());
        assertEquals(7.5, item.getTotal(), 0.001);
    }
}