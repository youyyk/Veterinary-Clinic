package main.VeterinaryClinic.Service.Construct;

import lombok.Data;
import main.VeterinaryClinic.Model.Tool;

@Data
public class ToolAmt {
    private Tool tool;
    private int amount;
    private double totalPrice;

    public ToolAmt(Tool tool, int amount) {
        this.tool = tool;
        this.amount = amount;
        this.totalPrice = amount * tool.getPrice();
    }
}
