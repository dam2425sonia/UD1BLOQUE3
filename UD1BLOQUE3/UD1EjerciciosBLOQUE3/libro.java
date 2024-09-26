class Libro {
    private String idLibro;
    private String titulo;
    private String autor;
    private double precio;
    private String genero;

    // Constructor
    public Libro(String idLibro, String titulo, String autor, double precio, String genero) {
        this.idLibro= idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
        this.genero=genero;
    }

    // Getters
    public String getId() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public double getPrecio() {
        return precio;
    }

    public String getGenero() {
        return genero;
    }
}