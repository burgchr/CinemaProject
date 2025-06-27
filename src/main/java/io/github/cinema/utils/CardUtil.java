package io.github.cinema.utils;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class CardUtil {
    private static String CARDS_XML = "src/main/resources/cards.xml";

    // Add this method (package-private for test access)
    public static void setCardsXmlPath(String path) {
        CARDS_XML = path;
    }

    /**
     * Retrieves the balance of a card given its code.
     *
     * @param code The unique code of the card.
     * @return The balance of the card, or -1 if the card is not found.
     */
    public static double getCardBalance(String code) {
        try {
            File xmlFile = new File(CARDS_XML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
            NodeList cards = doc.getElementsByTagName("card");
            for (int i = 0; i < cards.getLength(); i++) {
                Element card = (Element) cards.item(i);
                if (card.getAttribute("code").equals(code)) {
                    return Double.parseDouble(card.getAttribute("balance"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * Updates the balance of a card given its code. The cards codes are saved in an XML file.
     *
     * @param code The unique code of the card.
     * @param newBalance The new balance to set for the card.
     */
    public static void updateCardBalance(String code, double newBalance) {
        try {
            File xmlFile = new File(CARDS_XML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
            NodeList cards = doc.getElementsByTagName("card");
            for (int i = 0; i < cards.getLength(); i++) {
                Element card = (Element) cards.item(i);
                if (card.getAttribute("code").equals(code)) {
                    card.setAttribute("balance", String.format("%.2f", newBalance));
                    break;
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}