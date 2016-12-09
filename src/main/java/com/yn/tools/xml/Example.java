package com.yn.tools.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangnan on 2016/12/9.
 */
@Root(name = "example")
public class Example {

    @Element(name = "list", required = true)
    private ListElement listElements;

    @Element(name = "map", required = true)
    private MapElement mapElement;

    private Map<String, Integer> listMap;

    @Commit
    public void commit() {
        listMap = new HashMap<String, Integer>();
        List<ListTestElement> es = listElements.getListTestElements();
        for (ListTestElement el : es) {
            listMap.put(el.getName(), el.getValue());
        }
    }

    public ListElement getListElements() {
        return listElements;
    }

    public void setListElements(ListElement listElements) {
        this.listElements = listElements;
    }

    public MapElement getMapElement() {
        return mapElement;
    }

    public void setMapElement(MapElement mapElement) {
        this.mapElement = mapElement;
    }

    public Map<String, Integer> getListMap() {
        return listMap;
    }

    public void setListMap(Map<String, Integer> listMap) {
        this.listMap = listMap;
    }
}
