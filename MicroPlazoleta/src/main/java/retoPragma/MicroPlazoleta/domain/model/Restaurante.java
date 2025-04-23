package retoPragma.MicroPlazoleta.domain.model;


public class Restaurante {


    private Long idRestaurante;
    private String nombreRestaurante;
    private Long nit;
    private String direccion;
    private String telefonoRestaurante;
    private String urlLogo;
    private Long idUsuario;


    public Restaurante() {
    }

    public Restaurante(Long idRestaurante, String nombreRestaurante, Long nit, String direccion, String telefonoRestaurante, String urlLogo, Long idUsuario) {
        this.idRestaurante = idRestaurante;
        this.nombreRestaurante = nombreRestaurante;
        this.nit = nit;
        this.direccion = direccion;
        this.telefonoRestaurante = telefonoRestaurante;
        this.urlLogo = urlLogo;
        this.idUsuario = idUsuario;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNombreRestaurante() {
        return nombreRestaurante;
    }

    public void setNombreRestaurante(String nombreRestaurante) {
        this.nombreRestaurante = nombreRestaurante;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoRestaurante() {
        return telefonoRestaurante;
    }

    public void setTelefonoRestaurante(String telefonoRestaurante) {
        this.telefonoRestaurante = telefonoRestaurante;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
