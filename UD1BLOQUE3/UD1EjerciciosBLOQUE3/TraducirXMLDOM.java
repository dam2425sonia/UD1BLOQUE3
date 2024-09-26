import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TraducirXMLDOM {
    //Contiene las traducciones de las etiquetas del inglés al español.
    private static Map<String, String> translations = new HashMap<>();

    // Bloque estático que inicializa el mapa de traducciones
    static {
        translations.put("Catalog", "Catalogo");
        translations.put("Book", "Libro");
        translations.put("Title", "Título");
        translations.put("Author", "Autor");
        translations.put("Genre", "Año");
        translations.put("price", "Precio");
        translations.put("Public_date", "Fecha_publicacion");
        translations.put("Description", "Descripcion");
    }

    public static void main(String[] args) {
        try {
            // Cargar el archivo XML de entrada
            File inputFile = new File("books.xml");
            // Crear un DocumentBuilderFactory para procesar el XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // Crear un DocumentBuilder a partir del factory
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Parsear el archivo XML a un objeto Document
            Document doc = dBuilder.parse(inputFile);
            // Normalizar el documento (reorganiza el árbol DOM)
            doc.getDocumentElement().normalize();

            // Traducir las etiquetas del documento
            traducirElements(doc.getDocumentElement());

            // Escribir el resultado en libros.xml
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            // Configurar el transformador para que la salida esté indentada
            // Específicamente, esta línea configura el transformador para que indente (o formatee con tabulaciones y nuevas líneas) el XML de salida, haciéndolo más legible
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Crear un DOMSource a partir del documento
            DOMSource source = new DOMSource(doc);
            // Crear un StreamResult para especificar el archivo de salida
            StreamResult result = new StreamResult(new File("libros.xml"));
            // Ejecutar la transformación y guardar el archivo
            transformer.transform(source, result);

            System.out.println("Archivo traducido y guardado como libros.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método recursivo para traducir los nombres de las etiquetas
    // Si encuentra una etiqueta que tiene una traducción, renombra el nodo usando renameNode.
    // También itera sobre los nodos hijos, aplicando la traducción recursivamente.
    private static void traducirElements(Element element) {
         // Si la etiqueta actual tiene una traducción, renombrarla
        if (translations.containsKey(element.getTagName())) {
            String translatedTag = translations.get(element.getTagName());
             // Cambiar el nombre de la etiqueta en el documento
            element.getOwnerDocument().renameNode(element, element.getNamespaceURI(), translatedTag);
        }
        // Obtener todos los nodos hijos de la etiqueta actual
        NodeList childNodes = element.getChildNodes();
        // Recorrer todos los nodos hijos
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            // Si el nodo es un elemento, aplicar la traducción recursivamente
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                traducirElements((Element) node);
            }
        }
    }
}