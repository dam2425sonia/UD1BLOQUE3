
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeerXMLDOM {

    public static void main(String[] args) {
        try{
            //Crea una instancia de DocumentBuilderFactory, que es la clase responsable de la construcción del documento XML.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Se crea un DocumentBuilder, que será utilizado para analizar el archivo XML.
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Define un objeto File para el archivo XML llamado books.xml, que se encuentra en la carpeta ficheros.
            File xmlFile = new File("books.xml");
            //El método parse() se utiliza para analizar el archivo XML y 
            //devolver un objeto Document que representa la estructura del documento XML en memoria.
            Document document = builder.parse(xmlFile);
            //Este paso asegura que el documento esté en una forma consistente, 
            //eliminando nodos vacíos o innecesarios. Es un paso de "limpieza" del XML.
            document.getDocumentElement().normalize();
    
            //Obtener todos los elementos "Book". 
            NodeList bookList = document.getElementsByTagName("Book");
    
            // 1. Muestra por pantalla los diferentes IDs de cada libro utilizando la libreria DOM
            mostrar(bookList,1);
            // 2. Muestra por pantalla una lista de autores y los títulos de sus libros
            mostrar(bookList,2);
            // 3. Muestra por pantalla los títulos de los libros y sus precios. Ordena de más económico a más caro.
            mostrar(bookList,3);
            // 4. Muestra los libros por su genero
            mostrar(bookList,4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    // Función que muestra los diferentes campos de cada libro según el tipo que se le pase
    // 1 -> muestra ids
    // 2 -> muestra autores y sus títulos
    // 3 -> muestra titulos y sus precios ordenados
    // 4 -> muestra titulos ordenados por género
    public static void mostrar(NodeList bookList, int type) {
        try {

           // Crear una lista para almacenar los libros y poderlos ordenar según criterios
           List<Libro> libros = new ArrayList<>();

            //Iterar sobre la lista de libros y obtener el atributo "id"
            for (int i = 0; i < bookList.getLength(); i++) {
                //Recupera el nodo Book actual.
                Node bookNode = bookList.item(i);

                //Verifica si el nodo actual es un nodo de tipo ELEMENT_NODE, 
                //lo cual significa que es un nodo que contiene un elemento (en este caso, Book).
                if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) bookNode;
                    // Obtener el atributo "id" de cada libro
                    String bookId = bookElement.getAttribute("id");
                    // Obtener el autor del libro
                    String autor = bookElement.getElementsByTagName("Author").item(0).getTextContent();
                    // Obtener el título del libro
                    String titulo = bookElement.getElementsByTagName("Title").item(0).getTextContent();
                    // Obtener el precio del libro
                    double precio = Double.parseDouble(bookElement.getElementsByTagName("Price").item(0).getTextContent());
                    // Obtener el genero del libro
                    String genero = bookElement.getElementsByTagName("Genre").item(0).getTextContent();
                     
                    // Añadir el libro a la lista
                    libros.add(new Libro(bookId, titulo, autor, precio, genero));
                }
            }
            
            switch (type) {
                case 1:
                    // Mostrar por pantalla el id del libro
                    System.out.println("1. IDs de los libros:");
                    for (Libro libro : libros) {
                        System.out.println("Id: " + libro.getId() );
                    }
                    break;
                case 2:
                    // Mostrar por pantalla el autor y su libro
                    System.out.println("2. Autor de los libros y sus títulos:");
                    for (Libro libro : libros) {
                        System.out.println("Autor: " + libro.getAutor() + " -> Título: " + libro.getTitulo());
                    }
                    break;
                case 3:
                    // Ordenar la lista de libros por precio de menor a mayor
                    libros.sort(Comparator.comparingDouble(Libro::getPrecio));
                    // Mostrar por pantalla los libros ordenados por precio
                    System.out.println("3. Libros ordenados por precio (de más económico a más caro):");
                    for (Libro libro : libros) {
                        System.out.println("Título: " + libro.getTitulo() + " - Precio: " + libro.getPrecio());
                    }
                    break;
                case 4:
                    // Mostrar por pantalla libros y su género
                    System.out.println("4. Libros y su género:");
                    
                    // Ordenar los libros por género
                    Collections.sort(libros, new Comparator<Libro>() {
                        @Override
                        public int compare(Libro l1, Libro l2) {
                            return l1.getGenero().compareTo(l2.getGenero());
                        }
                    });
                    
                    for (Libro libro : libros) {
                        System.out.println("Genéro:" + libro.getGenero()+ " -> Título: " + libro.getTitulo() );
                    }
                    break;
                default:
                    break;
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


