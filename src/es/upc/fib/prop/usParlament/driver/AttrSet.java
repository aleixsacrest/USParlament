package es.upc.fib.prop.usParlament.driver;

import es.upc.fib.prop.usParlament.domain.AttrDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by alexmirocat on 29/4/15.
 */
public class AttrSet {

    private Map<String, AttrDefinition> attributes;

    public AttrSet() {
        attributes = new LinkedHashMap<>();
    };

    public void addAttributeDef(AttrDefinition ad) { attributes.put(ad.getName(), ad); }

    public AttrDefinition getAttributeDef(String name) { return attributes.get(name); }

    public void removeAttributeDef(AttrDefinition name) { attributes.remove(name); }

    public String toString()
    {
        String ret = "Attribute definiton list\n";
        for (AttrDefinition ad : attributes.values()) {
            ret += ad.toString();
        }
        return ret;
    }

}
