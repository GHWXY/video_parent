package com.wxy.services_video.entity.category;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class LevelCategory {
    private String id;
    private String title;
    private List<LevelCategory> children = new ArrayList<>();
}
