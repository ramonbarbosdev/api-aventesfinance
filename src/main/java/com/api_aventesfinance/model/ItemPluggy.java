package com.api_aventesfinance.model;

import com.api_aventesfinance.enums.TipoCategoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "item_pluggy")
public class ItemPluggy {

    @Id
    private String id_item;

    private Long connectorId;

    private String name;

    private String institutionUrl;

    private String type;

    @Column(name = "fl_main")
    private Boolean flMain;


    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public Long getConnectorId() {
        return connectorId;
    }
    public void setConnectorId(Long connectorId) {
        this.connectorId = connectorId;
    }

    public String getInstitutionUrl() {
        return institutionUrl;
    }
    public void setInstitutionUrl(String institutionUrl) {
        this.institutionUrl = institutionUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

   public Boolean getFlMain() {
       return flMain;
   }
   public void setFlMain(Boolean flMain) {
       this.flMain = flMain;
   }
    
  
}
