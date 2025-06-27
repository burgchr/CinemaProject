import io.github.cinema.models.OrderItem;
import io.github.cinema.models.Product;
import io.github.cinema.utils.PdfUtil;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PdfUtilTest {

    @Test
    void testGenerateReceiptAndLogPayment() {
        OrderItem item = new OrderItem(new Product("Popcorn", 2.5), 2);
        List<OrderItem> items = List.of(item);
        assertDoesNotThrow(() -> PdfUtil.generateReceipt(items, 5.0, 10.0, 5.0));
        assertDoesNotThrow(() -> PdfUtil.logPayment(items, 5.0, 10.0, 5.0));
    }

    @Test
    void testGetAllPayments() {
        var payments = PdfUtil.getAllPayments();
        assertNotNull(payments);
    }
}