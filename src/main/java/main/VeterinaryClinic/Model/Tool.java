package main.VeterinaryClinic.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Tool")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tool_id")
    private Long ToolID;
    @Column(name="tool_name")
    private String name;
    @Column(name="price")
    private double price;
    @Column(name="description")
    private String description;
    @Column(name="soft_deleted_dated")
    private String softDeletedDate;
    @Column(name="soft_deleted")
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
        this.softDeletedDate = "";
        this.softDeleted = false;
    }

    public Long getToolID() {
        return ToolID;
    }

    public void setToolID(Long toolID) {
        ToolID = toolID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSoftDeletedDate() {
        return softDeletedDate;
    }

    public void setSoftDeletedDate(String softDeletedDate) {
        this.softDeletedDate = softDeletedDate;
    }

    public boolean isSoftDeleted() {
        return softDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        this.softDeleted = softDeleted;
    }

    public List<WareHouse> getItems() {
        return items;
    }

    public void setItems(List<WareHouse> items) {
        this.items = items;
    }
}
