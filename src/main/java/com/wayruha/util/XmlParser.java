package com.wayruha.util;


import com.wayruha.model.ConfigFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public final class XmlParser {
    public static ArrayList<ConfigFile> loadAllPatterns(){
        ArrayList<ConfigFile> patternsList=new ArrayList<>();
        try {
            File f = new File("src/main/resources/xml/patterns.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(f);
            Element root = document.getDocumentElement();
            // для простоты сразу берем message
            Node pattern;
            ConfigFile configFile;
            for(int i=0;i<root.getChildNodes().getLength();i++){
                pattern=root.getChildNodes().item(i);
                String name=pattern.getAttributes().getNamedItem("filePath").getNodeValue();
                String filePath=pattern.getAttributes().getNamedItem("filePath").getNodeValue();
                int priceCol=Integer.valueOf(pattern.getAttributes().getNamedItem("priceCol").getNodeValue());
                int modelCol=Integer.valueOf(pattern.getAttributes().getNamedItem("modelCol").getNodeValue());
                int manufacturerCol=Integer.valueOf(pattern.getAttributes().getNamedItem("manufacturerCol").getNodeValue());
                int appendCol=Integer.valueOf(pattern.getAttributes().getNamedItem("appendCol").getNodeValue());
                configFile=new ConfigFile(name,filePath,priceCol,modelCol,manufacturerCol,appendCol);
                patternsList.add(configFile);
            }

        } catch(Exception e){
            System.out.println(e);
        }
        return patternsList;
    }

    public static void writeAnXml(ConfigFile configFile) throws Exception {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement=doc.createElement("patterns");
            Element nameElement=doc.createElement(configFile.getName());

            nameElement.setAttribute("appendCol",String.valueOf(configFile.getAppendCol()));
            nameElement.setAttribute("manufacturerCol",String.valueOf(configFile.getManufacturerCol()));
            nameElement.setAttribute("modelCol",String.valueOf(configFile.getModelCol()));
            nameElement.setAttribute("priceCol",String.valueOf(configFile.getPriceCol()));
            nameElement.setAttribute("filePath",configFile.getFilePath());
            rootElement.appendChild(nameElement);
            doc.appendChild(rootElement);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("src/main/resources/xml/patterns.xml"));
            transformer.transform(source, result);
            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);


    }
}
