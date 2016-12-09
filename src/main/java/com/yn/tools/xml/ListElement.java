package com.yn.tools.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by yangnan on 2016/12/9.
 */
public class ListElement {

    @ElementList(entry = "test", inline = true, required = true)
    private List<ListTestElement> listTestElements;

    @Attribute(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public List<ListTestElement> getListTestElements() {
        return listTestElements;
    }

    public void setListTestElements(List<ListTestElement> listTestElements) {
        this.listTestElements = listTestElements;
    }

    public void setName(String name) {
        this.name = name;
    }
}
