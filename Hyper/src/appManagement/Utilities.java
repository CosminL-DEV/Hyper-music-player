package appManagement;

import playlist.Playlist;
import interfaz.Interfaz;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import components.ItemPlaylist;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Utilities {

    public static BufferedImage transformarLink(String picture) {
        URL url;
        BufferedImage c = null;
        try {
            url = new URL(picture);
            c = ImageIO.read(url);
        } catch (IOException ex) {
            Logger.getLogger(ItemPlaylist.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public static BufferedImage redondearImagen(BufferedImage image, int cornerRadius, ColorReturner CReturner) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(CReturner.getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

    public static String getIconUser(String username) {
        Statement sentencia;
        String picture = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT profile_pic FROM users WHERE username='" + username + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    resul.next();
                    picture = resul.getString("profile_pic");
                    if (picture == null) {
                        picture = "http://localhost/hyper/wp-content/uploads/2022/04/user.png";
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        return picture;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Font cargarCoolvetica() {
        Font coolvetica = null;
        InputStream is = Interfaz.class.getResourceAsStream("/fonts/coolvetica rg.otf");
        try {
            coolvetica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coolvetica;
    }

    public static void cambiarTema(String nuevoTema) {
        nuevoTema = nuevoTema.toLowerCase();
        Document documento;
        File archivo;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            archivo = new File("src/themes.xml");
            documento = db.parse(archivo);
            documento.getDocumentElement().normalize();
            NodeList nSelected = documento.getElementsByTagName("selected");
            Node nNode = nSelected.item(0);
            Element eElement = (Element) nNode;
            eElement.getElementsByTagName("tema").item(0).setTextContent(nuevoTema);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(archivo);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerConfigurationException e) {
        } catch (TransformerException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void escribirRuta(String nuevaRuta) {
        Document documento;
        File archivo;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            archivo = new File("src/themes.xml");
            documento = db.parse(archivo);
            documento.getDocumentElement().normalize();
            NodeList nSelected = documento.getElementsByTagName("ubicacion");
            Node nNode = nSelected.item(0);
            Element eElement = (Element) nNode;
            eElement.getElementsByTagName("ruta").item(0).setTextContent(nuevaRuta);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(archivo);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerConfigurationException e) {
        } catch (TransformerException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void descargarCanciones(String elem) {
        Statement sentencia;
        ColorReturner CReturner = new ColorReturner();
        String linkCancionActual = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT song.url "
                        + "FROM song "
                        + "WHERE song.song_id = '" + elem + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    if (resul.next()) {
                        linkCancionActual = resul.getString("url");
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        }
        AudioInputStream stream;
        File archivo;
        try {
            stream = AudioSystem.getAudioInputStream(new URL(linkCancionActual));
            archivo = new File(CReturner.getFolderPath().toString() + "/" + elem + ".wav");
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, archivo);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
