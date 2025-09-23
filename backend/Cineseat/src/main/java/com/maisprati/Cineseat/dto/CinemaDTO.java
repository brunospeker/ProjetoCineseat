package com.maisprati.Cineseat.dto;

import com.maisprati.Cineseat.entities.Cinema;

import java.time.LocalDateTime;
import java.util.List;

public class CinemaDTO {
    private Long id;
    private String name;
    private String site;
    private String cnpj;
    private String state;
    private String uf;
    private String cityName;
    private String cityId;
    private String address;
    private String addressComplement;
    private String neighborhood;
    private String number;

    private String imagesJson;

    private Boolean hasBomboniere;
    private Boolean hasSession;
    private Boolean hasSeatDistancePolicy;
    private Boolean hasSeatDistancePolicyArena;

    private List<String> deliveryType;

    private String corporation;
    private String corporationId;

    private String operationPoliciesJson;

    private Integer totalRooms;

    private LocalDateTime dateCreation;
    private LocalDateTime updateDate;

    private Boolean enable;

    public CinemaDTO(){
    }

    public CinemaDTO(Cinema entity){
        id = entity.getId();
        name = entity.getName();
        site = entity.getSite();
        cnpj = entity.getCnpj();
        state = entity.getState();
        uf = entity.getUf();
        cityName = entity.getCityName();
        cityId = entity.getCityId();
        address = entity.getAddress();
        addressComplement = entity.getAddressComplement();
        neighborhood = entity.getNeighborhood();
        number = entity.getNumber();
        imagesJson = entity.getImagesJson();
        hasBomboniere = entity.getHasBomboniere();
        hasSession = entity.getHasSession();
        hasSeatDistancePolicy = entity.getHasSeatDistancePolicy();
        hasSeatDistancePolicyArena = entity.getHasSeatDistancePolicyArena();
        deliveryType = entity.getDeliveryType();
        corporation = entity.getCorporation();
        corporationId = entity.getCorporationId();
        operationPoliciesJson = entity.getOperationPoliciesJson();
        totalRooms = entity.getTotalRooms();
        dateCreation = entity.getDateCreation();
        updateDate = entity.getUpdateDate();
        enable = entity.getEnable();
    }

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
    public void setDateCreation(LocalDateTime dateCreation) {this.dateCreation = dateCreation;}

    public LocalDateTime getUpdateDate() {return updateDate;}
    public void setUpdateDate(LocalDateTime updateDate) {this.updateDate = updateDate;}

    public Boolean getEnable() {return enable;}
    public void setEnable(Boolean enable) {this.enable = enable;}
}