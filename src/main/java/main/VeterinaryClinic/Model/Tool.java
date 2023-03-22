package main.VeterinaryClinic.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="tools")
@Data
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tool_id")
    private Long toolID;
    @Column(name = "tool_name", columnDefinition = "CHAR(30)")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "soft_deleted_date")
    private Date softDeletedDate;
    @Column(name = "soft_deleted")
    private boolean softDeleted;

    @OneToMany(mappedBy = "tool", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WareHouse> items;

    public Tool() {
        super();
    }

    public Tool(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.softDeletedDate = null;
        this.softDeleted = false;
    }

    public String getNameForShow(){
        String name =  this.name;
        if (!description.isEmpty() && !description.isBlank()){
            name += " (" + description + ")";
        }
        return name;
    }

    public void updateFieldForEdit(String name, double price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public boolean isCanDelete(){
        return items.size()==0?true:false;
    }
}

