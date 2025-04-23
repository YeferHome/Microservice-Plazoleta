package retoPragma.MicroPlazoleta.domain.model;


public class Plato {

    private Long idPlato;
    private String nombrePlato;
    private String descripcionPlato;
    private Long precioPlato;
    private String urlPlato;
    private String categoriaPlato;
    private Boolean activoPlato;
    private Long idUsuario;
    private Long idRestaurante;

    public Plato() {
    }

    public Plato(Long idPlato, String nombrePlato, String descripcionPlato, Long precioPlato, String urlPlato, String categoriaPlato, Boolean activoPlato, Long idUsuario, Long idRestaurante) {
        this.idPlato = idPlato;
        this.nombrePlato = nombrePlato;
        this.descripcionPlato = descripcionPlato;
        this.precioPlato = precioPlato;
        this.urlPlato = urlPlato;
        this.categoriaPlato = categoriaPlato;
        this.activoPlato = activoPlato;
        this.idUsuario = idUsuario;
        this.idRestaurante = idRestaurante;
    }

    public Long getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Long idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public String getDescripcionPlato() {
        return descripcionPlato;
    }

    public void setDescripcionPlato(String descripcionPlato) {
        this.descripcionPlato = descripcionPlato;
    }

    public Long getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(Long precioPlato) {
        this.precioPlato = precioPlato;
    }

    public String getUrlPlato() {
        return urlPlato;
    }

    public void setUrlPlato(String urlPlato) {
        this.urlPlato = urlPlato;
    }

    public String getCategoriaPlato() {
        return categoriaPlato;
    }

    public void setCategoriaPlato(String categoriaPlato) {
        this.categoriaPlato = categoriaPlato;
    }

    public Boolean getActivoPlato() {
        return activoPlato;
    }

    public void setActivoPlato(Boolean activoPlato) {
        this.activoPlato = activoPlato;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
}
