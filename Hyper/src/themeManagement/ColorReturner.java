package themeManagement;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 22-03-2022
 * @version 1.0
 *
 * ************************************
 */
public class ColorReturner {

    Element eTheme;
    
    public ColorReturner() {
        try {
            File file = new File("src/themes.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.parse(file);
            documento.getDocumentElement().normalize();
            NodeList nodoTema = documento.getElementsByTagName(getThemeSelected(documento));
            Node nodoTheme = nodoTema.item(0);
            eTheme = (Element) nodoTheme;
        } catch (IOException e) {
            System.out.println(e);
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(ColorReturner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getThemeSelected(Document documento) {
        NodeList nSelected = documento.getElementsByTagName("selected");
        Node nNode = nSelected.item(0);
        Element eElement = (Element) nNode;
        return eElement.getElementsByTagName("tema").item(0).getTextContent();
    }
    
    private Color transformador(String codigo){
        String[] codigos = codigo.split(",");
        Color color = new Color(Integer.parseInt(codigos[0]), Integer.parseInt(codigos[1]), Integer.parseInt(codigos[2]));
        return color;
    }
    
    public Color getBackground(){
        String codigo = eTheme.getElementsByTagName("background").item(0).getTextContent();
        return transformador(codigo);
    }
    
    public Color getPrincipal(){
        String codigo = eTheme.getElementsByTagName("principal").item(0).getTextContent();
        return transformador(codigo);
    }
    
    public Color getTexto(){
        String codigo = eTheme.getElementsByTagName("texto").item(0).getTextContent();
        return transformador(codigo);
    }
    
    public Color getTexto2(){
        String codigo = eTheme.getElementsByTagName("texto2").item(0).getTextContent();
        return transformador(codigo);
    }
    
    public Color getClose(){
        String codigo = eTheme.getElementsByTagName("close").item(0).getTextContent();
        return transformador(codigo);
    }
    
    public String getIcon(){
        String codigo = eTheme.getElementsByTagName("icon").item(0).getTextContent();
        return codigo;
    }
    
    public String getIcons(){
        String codigo = eTheme.getElementsByTagName("icons").item(0).getTextContent();
        return codigo;
    }
    
    public Color getAbsoluto(){
        String codigo = eTheme.getElementsByTagName("absoluto").item(0).getTextContent();
        return transformador(codigo);
    }
}
