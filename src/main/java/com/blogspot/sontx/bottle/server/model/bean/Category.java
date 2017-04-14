package com.blogspot.sontx.bottle.server.model.bean;

import lombok.Data;

@Data
public class Category {
    private int id;
    private String name;
    private String description;
    private String photoUrl;
}
