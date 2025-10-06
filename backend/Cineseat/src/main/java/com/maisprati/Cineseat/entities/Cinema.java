package com.maisprati.Cineseat.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table (name = "cinemas")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String site;

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String state;
    private String uf;

    @Column(nullable = false)
    private String cityName;
    private String cityId;

    private String address;
    private String addressComplement;
    private String neighborhood;
    private String number;

    @Column(columnDefinition = "TEXT")
    private String imagesJson;

    private Boolean hasBomboniere;
    private Boolean hasSession;
    private Boolean hasSeatDistancePolicy;
    private Boolean hasSeatDistancePolicyArena;

    @ElementCollection
    @CollectionTable(name = "cinema_delivery_types", joinColumns = @JoinColumn(name = "cinema_id"))
    @Column(name = "delivery_type")
    private List<String> deliveryType = new ArrayList<>();

    private String corporation;
    private String corporationId;

    @Column(columnDefinition = "TEXT")
    private String operationPoliciesJson;

    @Column(name = "total_rooms")
    private Integer totalRooms;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @Column(nullable = false)
    private Boolean enable;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sala> salas;

//    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CinemaAvaliacao> reviews;

    public Cinema(){}

    public Cinema(Long id, String name, String site, String cnpj, String state, String uf,
                  String cityName, String cityId, String address, String addressComplement,
                  String neighborhood, String number, String imagesJson,
                  Boolean hasBomboniere, Boolean hasSession, Boolean hasSeatDistancePolicy,
                  Boolean hasSeatDistancePolicyArena, List<String> deliveryType,
                  String corporation, String corporationId, String operationPoliciesJson,
                  Integer totalRooms, Boolean enable) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.cnpj = cnpj;
        this.state = state;
        this.uf = uf;
        this.cityName = cityName;
        this.cityId = cityId;
        this.address = address;
        this.addressComplement = addressComplement;
        this.neighborhood = neighborhood;
        this.number = number;
        this.imagesJson = imagesJson;
        this.hasBomboniere = hasBomboniere;
        this.hasSession = hasSession;
        this.hasSeatDistancePolicy = hasSeatDistancePolicy;
        this.hasSeatDistancePolicyArena = hasSeatDistancePolicyArena;
        this.deliveryType = deliveryType;
        this.corporation = corporation;
        this.corporationId = corporationId;
        this.operationPoliciesJson = operationPoliciesJson;
        this.totalRooms = totalRooms;
        this.enable = enable;
    }


    @PrePersist
    public void prePersist() {
        this.dateCreation = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.enable = true;
    }

    @PreUpdate
    public void preUpdate() {this.updateDate = LocalDateTime.now();}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getSite() {return site;}
    public void setSite(String site) {this.site = site;}

    public String getCnpj() {return cnpj;}
    public void setCnpj(String cnpj) {this.cnpj = cnpj;}

    public String getState() {return state;}
    public void setState(String state) {this.state = state;}

    public String getUf() {return uf;}
    public void setUf(String uf) {this.uf = uf;}

    public String getCityName() {return cityName;}
    public void setCityName(String cityName) {this.cityName = cityName;}

    public String getCityId() {return cityId;}
    public void setCityId(String cityId) {this.cityId = cityId;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String getAddressComplement() {return addressComplement;}
    public void setAddressComplement(String addressComplement) {this.addressComplement = addressComplement;}

    public String getNeighborhood() {return neighborhood;}
    public void setNeighborhood(String neighborhood) {this.neighborhood = neighborhood;}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public String getImagesJson() {return imagesJson;}
    public void setImagesJson(String imagesJson) {this.imagesJson = imagesJson;}

    public Boolean getHasBomboniere() {return hasBomboniere;}
    public void setHasBomboniere(Boolean hasBomboniere) {this.hasBomboniere = hasBomboniere;}

    public Boolean getHasSession() {return hasSession;}
    public void setHasSession(Boolean hasSession) {this.hasSession = hasSession;}

    public Boolean getHasSeatDistancePolicy() {return hasSeatDistancePolicy;}
    public void setHasSeatDistancePolicy(Boolean hasSeatDistancePolicy) {this.hasSeatDistancePolicy = hasSeatDistancePolicy;}

    public Boolean getHasSeatDistancePolicyArena() {return hasSeatDistancePolicyArena;}
    public void setHasSeatDistancePolicyArena(Boolean hasSeatDistancePolicyArena) {this.hasSeatDistancePolicyArena = hasSeatDistancePolicyArena;}

    public List<String> getDeliveryType() {return deliveryType;}
    public void setDeliveryType(List<String> deliveryType) {this.deliveryType = deliveryType;}

    public String getCorporation() {return corporation;}
    public void setCorporation(String corporation) {this.corporation = corporation;}

    public String getCorporationId() {return corporationId;}
    public void setCorporationId(String corporationId) {this.corporationId = corporationId;}

    public String getOperationPoliciesJson() {return operationPoliciesJson;}
    public void setOperationPoliciesJson(String operationPoliciesJson) {this.operationPoliciesJson = operationPoliciesJson;}

    public Integer getTotalRooms() {return totalRooms;}
    public void setTotalRooms(Integer totalRooms) {this.totalRooms = totalRooms;}

    public LocalDateTime getDateCreation() {return dateCreation;}
    private void setDateCreation(LocalDateTime dateCreation) {this.dateCreation = dateCreation;}

    public LocalDateTime getUpdateDate() {return updateDate;}
    public void setUpdateDate(LocalDateTime updateDate) {this.updateDate = updateDate;}

    public Boolean getEnable() {return enable;}
    public void setEnable(Boolean enable) {this.enable = enable;}

    public List<Sala> getSalas() {return salas;}
    public void setSalas(List<Sala> salas) {this.salas = salas;}

//    public List<CinemaAvaliacao> getReviews() {
//        return reviews;
//    }
//    public void setReviews(List<CinemaAvaliacao> reviews) {
//        this.reviews = reviews;
//    }
}
