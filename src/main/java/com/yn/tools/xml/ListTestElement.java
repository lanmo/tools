package com.yn.tools.xml;

import org.simpleframework.xml.Attribute;

/**
 * Created by yangnan on 2016/12/9.
 */
public class ListTestElement {

    @Attribute(name = "name", required = true)
    private String name;

    @Attribute(name = "value", required = true)
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
