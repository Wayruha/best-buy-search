package com.wayruha.util;


import com.wayruha.exception.CantLoadPattern;
import com.wayruha.exception.ErrorWindow;
import com.wayruha.model.ConfigFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.*;
import java.util.ArrayList;


public final class Parser {
    private static ArrayList<ConfigFile> configList = new ArrayList<>();
    private static ArrayList<ConfigFile> notLoadedconfigList = new ArrayList<>();

    public static ArrayList<ConfigFile> getNotLoadedconfigList() {
        return notLoadedconfigList;
    }

    public static ArrayList<ConfigFile> getConfigList() {
        return configList;
    }

    public static ConfigFile readSettingsFromName(String patternName) {
        Document doc = null;
        ConfigFile configFile = null;
        try {
            doc = readXMLInDOM("src/main/resources/xml/patterns.xml");
            Element root = doc.getDocumentElement();
            Node patternElement = root.getElementsByTagName(patternName).item(0);
            configFile = nodeToConfigObject(patternElement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return configFile;
    }

    public static void loadAllPatterns() {
        configList.clear();
        ArrayList<String> patternArr = new ArrayList<>();
        try {
            Document doc = readXMLInDOM("src/main/resources/xml/patterns.xml");
            Element root = doc.getDocumentElement();

            Node pattern;
            ConfigFile configFile;
            for (int i = 0; i < root.getChildNodes().getLength(); i++) {
                pattern = root.getChildNodes().item(i);
                if (pattern.getNodeValue() != null) continue;
                //У мене всі мають Нуль значення. А якщо значення існує  - це проста стрічка
                configFile = nodeToConfigObject(pattern);
                try (FileInputStream fis = new FileInputStream(configFile.getFilePath())) {
                    if (configFile.getFilePath().toLowerCase().endsWith("xlsx")) {
                        new XSSFWorkbook(fis);
                    } else if (configFile.getFilePath().toLowerCase().endsWith("xls")) {
                        new HSSFWorkbook(fis);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    patternArr.add(configFile.getName());
                    notLoadedconfigList.add(configFile);
                    continue;
                }
                configList.add(configFile);
            }
            if (patternArr.size() > 0) throw new CantLoadPattern(patternArr);
        } catch (CantLoadPattern cantLoadPattern) {
            new ErrorWindow(cantLoadPattern);
            System.out.println("не вийшло загрузити шаблони:" + patternArr);
        } catch (Exception e) {
            new ErrorWindow(e);
            e.printStackTrace();
        }
    }

    public static void writeAnXml(ConfigFile configFile, String oldName) throws Exception {
        Document doc = readXMLInDOM("src/main/resources/xml/patterns.xml");
        Element root;
        if (!doc.hasChildNodes()) {
            root = doc.createElement("patterns");
            doc.appendChild(root);
        }//Ставимо рутНод ящко документ пустий
        else root = doc.getDocumentElement();

        Element nameElement = doc.createElement(configFile.getName().replaceAll(" ", "_"));

        nameElement.setAttribute("appendCol", String.valueOf(configFile.getAppendCol()));
        nameElement.setAttribute("manufacturerCol", String.valueOf(configFile.getManufacturerCol()));
        nameElement.setAttribute("modelCol", String.valueOf(configFile.getModelCol()));
        nameElement.setAttribute("priceCol", String.valueOf(configFile.getPriceCol()));
        nameElement.setAttribute("filePath", configFile.getFilePath());

        if (oldName != null) {
            Node searchedElement = root.getElementsByTagName(oldName).item(0);
            if (searchedElement != null) root.replaceChild(nameElement, searchedElement);
        } else root.appendChild(nameElement);
        writeContentIntoXML(doc, "src/main/resources/xml/patterns.xml");
        loadAllPatterns();
    }

    private static Document readXMLInDOM(String filePath) throws Exception {
        File f = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document;
        try {
            document = builder.parse(f);
        } catch (SAXParseException e) {
            document = builder.newDocument();
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

    private static ConfigFile nodeToConfigObject(Node pattern) {
        String name = pattern.getNodeName().replaceAll("_", " ");
        String filePath = pattern.getAttributes().getNamedItem("filePath").getNodeValue();
        int priceCol = Integer.valueOf(pattern.getAttributes().getNamedItem("priceCol").getNodeValue());
        int modelCol = Integer.valueOf(pattern.getAttributes().getNamedItem("modelCol").getNodeValue());
        int manufacturerCol = Integer.valueOf(pattern.getAttributes().getNamedItem("manufacturerCol").getNodeValue());
        int appendCol = Integer.valueOf(pattern.getAttributes().getNamedItem("appendCol").getNodeValue());
        return new ConfigFile(name, filePath, priceCol, modelCol, manufacturerCol, appendCol);
    }


    public static void writeSynonims(){
        ManufacturersSynonimRow row1 = new ManufacturersSynonimRow("greta", "грета");
        ManufacturersSynonimRow row2 = new ManufacturersSynonimRow("dnepr", "днепр");
        ArrayList<ManufacturersSynonimRow> list = new ArrayList<>();
        list.add(row1);
        list.add(row2);
        StringBuilder outSb = new StringBuilder("{");
        for (ManufacturersSynonimRow row : list) {
            outSb.append("[");
            for (String word : row.getWordList())
                outSb.append(word + ",");
            outSb.append("]");
            outSb.append(";");
        }
        outSb.append("}");
    }

    public static ArrayList<ManufacturersSynonimRow> getSynonimListFromJSON() {

        File f = new File("src/main/resources/json/synonims.txt");

        if (!f.exists()) try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<ManufacturersSynonimRow> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(f.getAbsoluteFile()));) {
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
            String str = sb.toString();
            int startPos = 0, endPos = 0;

            while (str.indexOf(']', endPos + 1) != -1) {
                startPos = str.indexOf('[', startPos + 1);
                endPos = str.indexOf(']', endPos + 1);
                String[] arr = str.substring(startPos + 1, endPos).split(",");
                ManufacturersSynonimRow row = new ManufacturersSynonimRow(arr);
                list.add(row);
            }
        } catch (IOException e) {
            new ErrorWindow(e,"Не вдалося опрацювати словник брендів");
        }

        return list;

    }
}
