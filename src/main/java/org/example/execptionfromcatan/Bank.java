package org.example.execptionfromcatan;

import lombok.Getter;
import org.example.execptionfromcatan.resources.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Bank {

    private final List<Resource> resources = new ArrayList<>();

    public Bank(int amountResources) {
        this.resources.add(new Wood(amountResources));
        this.resources.add(new Sheep(amountResources));
        this.resources.add(new Wheat(amountResources));
        this.resources.add(new Brick(amountResources));
        this.resources.add(new Stone(amountResources));
    }

    public void addResources(String type, int amount) {
        for (Resource res : resources) {
            if (res.getName().equalsIgnoreCase(type)) {
                res.addAmount(amount);
                return;
            }
        }
    }

    public void removeResources(String type, int amount, Player target) {
        for (Resource res : resources) {
            if (res.getName().equalsIgnoreCase(type)) {
                res.removeAmount(amount);
                target.addResources(type, amount);
                return;
            }
        }
    }
}
