package retoPragma.MicroPlazoleta.domain.model;

public class Dish {

    private Long idDish;
    private String nameDish;
    private String descriptionDish;
    private Long priceDish;
    private String urlDish;
    private String categoryDish;
    private Boolean estate;
    private Long idUser;
    private Long idRestaurant;

    public Dish() {
    }

    public Dish(Long idDish, String nameDish, String descriptionDish, Long priceDish, String urlDish, String categoryDish, Boolean estate, Long idUser, Long idRestaurant) {
        this.idDish = idDish;
        this.nameDish = nameDish;
        this.descriptionDish = descriptionDish;
        this.priceDish = priceDish;
        this.urlDish = urlDish;
        this.categoryDish = categoryDish;
        this.estate = estate;
        this.idUser = idUser;
        this.idRestaurant = idRestaurant;
    }

    public Long getIdDish() {
        return idDish;
    }

    public void setIdDish(Long idDish) {
        this.idDish = idDish;
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public String getDescriptionDish() {
        return descriptionDish;
    }

    public void setDescriptionDish(String descriptionDish) {
        this.descriptionDish = descriptionDish;
    }

    public Long getPriceDrish() {
        return priceDish;
    }

    public void setPriceDish(Long priceDish) {
        this.priceDish = priceDish;
    }

    public String getUrlDish() {
        return urlDish;
    }

    public void setUrlDish(String urlDish) {
        this.urlDish = urlDish;
    }

    public String getCategoryDish() {
        return categoryDish;
    }

    public void setCategoryDish(String categoryDish) {
        this.categoryDish = categoryDish;
    }

    public Boolean getEstate() {
        return estate;
    }

    public void setEstate(Boolean estate) {
        this.estate = estate;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
}