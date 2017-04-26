package com.blogspot.sontx.bottle.server.model.bean;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import lombok.Data;

import java.io.Serializable;

@Data
public class PublicProfile implements Serializable {
    @Exclude
    private String id;
    @PropertyName("avatar_url")
    private String avatarUrl;
    @PropertyName("display_name")
    private String displayName;
}
