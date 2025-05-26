package org.example.execptionfromcatan;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {
    private String name;

    private final List<Resource> wood  = new ArrayList<>();
    private final List<Resource> sheep = new ArrayList<>();
    private final List<Resource> wheat = new ArrayList<>();
    private final List<Resource> brick = new ArrayList<>();
    private final List<Resource> stone = new ArrayList<>();

    List<Resource> getResourceType(String type) {
        switch (type) {
            case "wood":
                return this.wood;
            case "sheep":
                return this.sheep;
            case "wheat":
                return this.wheat;
            case "brick":
                return this.brick;
            case "stone":
                return this.stone;
            default:
                return null;
        }
    }

    public void addResources(List<Resource> resources) {
        List<Resource> resource = this.getResourceType(resources.getFirst().name);
        resource.addAll(resources);
    }

    public void removeResources(int amount, String type, Player target) {
        List<Resource> resources = this.getResourceType(type);

        List<Resource> removed = new ArrayList<>(resources.subList(0, amount));
        resources.subList(0, amount).clear();

        target.addResources(removed);
    }
}
