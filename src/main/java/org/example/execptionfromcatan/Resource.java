package org.example.execptionfromcatan;

import lombok.Getter;

@Getter
public class Resource {
    public String name;

    public Resource(String name) {
        this.name = name;
    }
}