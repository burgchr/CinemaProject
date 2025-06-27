package io.github.cinema.utils;
import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import io.github.cinema.models.OrderItem;
import io.github.cinema.models.TicketItem;
import javafx.scene.control.Alert;
import javafx.stage.Window;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PdfUtil {
    private static final String RECEIPTS_DIR = "src/main/resources/receipts";
    private static final String PAYMENTS_XML = RECEIPTS_DIR + "/payments.xml";
    private static final String TICKETS_DIR = "src/main/resources/tickets";

    /**
     * Generates a PDF receipt for the given order items, total amount, paid amount, and change.
     *
     * @param items  List of order items
     * @param total  Total amount of the order
     * @param paid   Amount paid by the customer
     * @param change Change to be returned to the customer
     */
    public static void generateReceipt(List<OrderItem> items, double total, double paid, double change) {
        try {
            Files.createDirectories(Paths.get(RECEIPTS_DIR));
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = RECEIPTS_DIR + "/receipt_" + timestamp + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph("Receipt - " + timestamp));
            document.add(new Paragraph(" "));
            for (OrderItem item : items) {
                document.add(new Paragraph(item.getProduct().name() + " x" + item.getQuantity() + " - " + String.format("%.2f€", item.getTotal())));
            }
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: " + String.format("%.2f€", total)));
            document.add(new Paragraph("Paid: " + String.format("%.2f€", paid)));
            document.add(new Paragraph("Change: " + String.format("%.2f€", change)));
            document.close();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate receipt PDF: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Logs the payment details into an XML file.
     *
     * @param items  List of order items
     * @param total  Total amount of the order
     * @param paid   Amount paid by the customer
     * @param change Change to be returned to the customer
     */
    public static void logPayment(List<OrderItem> items, double total, double paid, double change) {
        try {
            Files.createDirectories(Paths.get(RECEIPTS_DIR));
            File xmlFile = new File(PAYMENTS_XML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc;
            org.w3c.dom.Element root;
            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
                root = doc.getDocumentElement();
            } else {
                doc = dBuilder.newDocument();
                root = doc.createElement("payments");
                doc.appendChild(root);
            }
            org.w3c.dom.Element payment = doc.createElement("payment");
            payment.setAttribute("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            payment.setAttribute("total", String.format("%.2f", total));
            payment.setAttribute("paid", String.format("%.2f", paid));
            payment.setAttribute("change", String.format("%.2f", change));
            org.w3c.dom.Element itemsElem = doc.createElement("items");
            for (OrderItem item : items) {
                org.w3c.dom.Element itemElem = doc.createElement("item");
                itemElem.setAttribute("name", item.getProduct().name());
                itemElem.setAttribute("quantity", String.valueOf(item.getQuantity()));
                itemElem.setAttribute("price", String.format("%.2f", item.getProduct().price()));
                itemsElem.appendChild(itemElem);
            }
            payment.appendChild(itemsElem);
            root.appendChild(payment);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to log payment: " + e.getMessage()).showAndWait();
        }
    }

    /**
     * Retrieves all payments from the XML file and returns them as a list of maps.
     *
     * @return List of maps containing payment details
     */
    public static List<Map<String, String>> getAllPayments() {
        List<Map<String, String>> payments = new ArrayList<>();
        File xmlFile = new File(PAYMENTS_XML);
        if (!xmlFile.exists()) return payments;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
            org.w3c.dom.Element root = doc.getDocumentElement();
            org.w3c.dom.NodeList paymentNodes = root.getElementsByTagName("payment");
            for (int i = 0; i < paymentNodes.getLength(); i++) {
                org.w3c.dom.Element paymentElem = (org.w3c.dom.Element) paymentNodes.item(i);
                Map<String, String> map = new HashMap<>();
                map.put("timestamp", paymentElem.getAttribute("timestamp"));
                map.put("total", paymentElem.getAttribute("total"));
                map.put("paid", paymentElem.getAttribute("paid"));
                map.put("change", paymentElem.getAttribute("change"));
                payments.add(map);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to read payments: " + e.getMessage()).showAndWait();
        }
        return payments;
    }

    /**
     * Generates an end-of-day PDF report summarizing all payments and saves it to the receipts directory.
     *
     * @param owner The owner window for the alert dialog
     */
    public static void generateEndOfDayPdf(Window owner) {
        try {
            List<Map<String, String>> payments = getAllPayments();
            double totalCollected = 0, totalChange = 0, totalPaid = 0;
            for (Map<String, String> p : payments) {
                totalCollected += Double.parseDouble(p.get("total"));
                totalPaid += Double.parseDouble(p.get("paid"));
                totalChange += Double.parseDouble(p.get("change"));
            }
            String filename = RECEIPTS_DIR + "/end_of_day_" +
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph("End of the Day Report\n\n"));
            document.add(new Paragraph("Total receipts: " + payments.size()));
            document.add(new Paragraph("Total collected: " + String.format("%.2f€", totalCollected)));
            document.add(new Paragraph("Total paid: " + String.format("%.2f€", totalPaid)));
            document.add(new Paragraph("Total change given: " + String.format("%.2f€", totalChange)));
            document.add(new Paragraph(" "));
            for (Map<String, String> p : payments) {
                document.add(new Paragraph(
                        p.get("timestamp") + " | Total: " + p.get("total") +
                                " | Paid: " + p.get("paid") + " | Change: " + p.get("change")));
            }
            document.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "End of day PDF generated: " + filename);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to generate PDF: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Generates a PDF ticket for the given list of ticket items and saves it to the tickets directory.
     *
     * @param ticketItems List of ticket items to include in the PDF
     */
    public static void generateTicketPdf(List<TicketItem> ticketItems) {
        try {
            Files.createDirectories(Paths.get(TICKETS_DIR));
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = TICKETS_DIR + "/tickets_" + timestamp + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph("Tickets - " + timestamp));
            document.add(new Paragraph(" "));
            for (TicketItem item : ticketItems) {
                String line = "Seat: " + item.getSeat() + ", Film: " + item.getFilm() + ", Price: " + String.format("%.2f€", item.getPrice());
                document.add(new Paragraph(line));
            }
            document.close();
            new Alert(Alert.AlertType.INFORMATION, "Ticket PDF generated: " + filename).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate ticket PDF: " + e.getMessage()).showAndWait();
        }
    }
}