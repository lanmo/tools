package com.yn.tools.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;

import java.util.Map;

/**
 * Created by yangnan on 2016/12/9.
 */
public class MapElement {

    @ElementMap(entry = "property", key = "key", inline = true, attribute = true)
    private Map<String, String> map;

    @Attribute(name = "name", required = true)
    private String name;

    public Map<String, String> getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
