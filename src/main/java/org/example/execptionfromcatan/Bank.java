package org.example.execptionfromcatan;

import lombok.Getter;
import org.example.execptionfromcatan.resources.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Bank extends Player {

    private final List<Resource> wood  = new ArrayList<>();
    private final List<Resource> sheep = new ArrayList<>();
    private final List<Resource> wheat = new ArrayList<>();
    private final List<Resource> brick = new ArrayList<>();
    private final List<Resource> stone = new ArrayList<>();

    public Bank(int amountResources) {
        for (int i = 0; i < amountResources; i++) {
            this.wood.add(new Wood());
            this.sheep.add(new Sheep());
            this.wheat.add(new Wheat());
            this.brick.add(new Brick());
            this.stone.add(new Stone());
        }
    }

    public int getAmountOfResources(String type) {
        List<Resource> source = getResourceType(type);
        if (Objects.isNull(source)) {
            return 0;
        } else {
            return source.size();
        }
    }
}