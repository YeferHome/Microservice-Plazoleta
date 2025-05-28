package retoPragma.MicroPlazoleta.domain.model;


public class Restaurant {


    private Long idRestaurant;
    private String nameRestaurant;
    private Long nit;
    private String address;
    private String phoneRestaurant;
    private String urlLogo;
    private Long idUser;


    public Restaurant() {
    }

    public Restaurant(Long idRestaurant, String nameRestaurant, Long nit, String address, String phoneRestaurant, String urlLogo, Long idUser) {
        this.idRestaurant = idRestaurant;
        this.nameRestaurant = nameRestaurant;
        this.nit = nit;
        this.address = address;
        this.phoneRestaurant = phoneRestaurant;
        this.urlLogo = urlLogo;
        this.idUser = idUser;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        this.nameRestaurant = nameRestaurant;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneRestaurant() {
        return phoneRestaurant;
    }

    public void setPhoneRestaurant(String phoneRestaurant) {
        this.phoneRestaurant = phoneRestaurant;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
