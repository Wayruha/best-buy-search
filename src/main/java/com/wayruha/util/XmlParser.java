package com.wayruha.util;


import com.wayruha.model.ConfigFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public final class XmlParser {
    public static ArrayList<ConfigFile> loadAllPatterns() {
        ArrayList<ConfigFile> patternsList = new ArrayList<>();
        try {
            Document doc=readXMLInDOM("src/main/resources/xml/patterns.xml");
            Element root = doc.getDocumentElement();
            // для простоты сразу берем message
            Node pattern;
            ConfigFile configFile;
            for (int i = 0; i < root.getChildNodes().getLength(); i++) {
                pattern = root.getChildNodes().item(i);
                if (pattern.getNodeValue()!=null) continue;   //У мене всі мають Нуль значення. А якщо значення існує  - це проста стрічка
                String name = pattern.getNodeName();
                String filePath = pattern.getAttributes().getNamedItem("filePath").getNodeValue();
                int priceCol = Integer.valueOf(pattern.getAttributes().getNamedItem("priceCol").getNodeValue());
                int modelCol = Integer.valueOf(pattern.getAttributes().getNamedItem("modelCol").getNodeValue());
                int manufacturerCol = Integer.valueOf(pattern.getAttributes().getNamedItem("manufacturerCol").getNodeValue());
                int appendCol = Integer.valueOf(pattern.getAttributes().getNamedItem("appendCol").getNodeValue());
                configFile = new ConfigFile(name, filePath, priceCol, modelCol, manufacturerCol, appendCol);
                patternsList.add(configFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return patternsList;
    }

    public static void writeAnXml(ConfigFile configFile) throws Exception {
        Document doc = readXMLInDOM("src/main/resources/xml/patterns.xml");
        Element root;
        if (!doc.hasChildNodes()) {root = doc.createElement("patterns");  doc.appendChild(root);}//Ставимо рутНод ящко документ пустий
        else root = doc.getDocumentElement();
        Element nameElement = doc.createElement(configFile.getName());

        nameElement.setAttribute("appendCol", String.valueOf(configFile.getAppendCol()));
        nameElement.setAttribute("manufacturerCol", String.valueOf(configFile.getManufacturerCol()));
        nameElement.setAttribute("modelCol", String.valueOf(configFile.getModelCol()));
        nameElement.setAttribute("priceCol", String.valueOf(configFile.getPriceCol()));
        nameElement.setAttribute("filePath", configFile.getFilePath());

        Node searchedElement=root.getElementsByTagName(configFile.getName()).item(0);
        if(searchedElement!=null) root.replaceChild(nameElement,searchedElement);
        else root.appendChild(nameElement);
        writeContentIntoXML(doc,"src/main/resources/xml/patterns.xml");
    }

    private static Document readXMLInDOM(String filePath) throws Exception {
        File f = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document;
        try {
            document = builder.parse(f);
        } catch (SAXParseException e){
            document=builder.newDocument();
        }
        return document;

        // root.getElementsByTagName()
    }
    private static void writeContentIntoXML(Document doc, String filePath) throws TransformerException {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
        // Output to console for testing
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }
}
