package main.VeterinaryClinic.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Tool")
@Data
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tool_id")
    private Long ToolID;
    @Column(name = "tool_name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "description")
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
}

