package main.VeterinaryClinic.Service;

import lombok.Data;
import main.VeterinaryClinic.Model.Tool;

@Data
public class ToolAmt {
    private Tool tool;
    private int amount;
    private double price;

    public ToolAmt(Tool tool, int amount) {
        this.tool = tool;
        this.amount = amount;
        this.price = amount * tool.getPrice();
    }
}
