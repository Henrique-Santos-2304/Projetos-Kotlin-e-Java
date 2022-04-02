package app.modelo.meusclientes.model;

public class Client {
    private int id;
    private String name;
    private String telephone;
    private String email;
    private int cep;
    private String publicPlace;
    private String number;
    private String district;
    private String state;
    private boolean termsOfUse;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getCep() { return cep; }
    public void setCep(int cep) { this.cep = cep; }

    public String getPublicPlace() { return publicPlace; }
    public void setPublicPlace(String publicPlace) { this.publicPlace = publicPlace; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public boolean isTermsOfUse() { return termsOfUse; }
    public void setTermsOfUse(boolean termsOfUse) { this.termsOfUse = termsOfUse; }
}
