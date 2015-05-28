package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.usParlament.data.DataController;
import es.upc.fib.prop.usParlament.data.DataControllerImpl;
import es.upc.fib.prop.usParlament.misc.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by miquel on 16/05/15.
 *
 * All the TEST INSTRUCTIONS (i.e. printing staff) are commented by //.
 */

//TODO: not finished
public class DomainController
{
    /*
        Needs to contain:
        - 1 Graph (congress)
        - 2 Partition (one current and one for comparing purposes)
        Needs to give the ability to consult the graphs

     */
    private Congress currentCongress;
    private ArrayList<Set<MP>> currentPartition;
    private ArrayList<Set<MP>> partition1;
    private ArrayList<Set<MP>> partition2;
    private DataController dataController;

    
    public DomainController()
    {
        currentCongress = new Congress();
        currentPartition = new ArrayList<>();
        partition1 = new ArrayList<>();
        partition2 = new ArrayList<>();
        dataController = new DataControllerImpl("congresses");
    }

    /**
     * @return It returns an String with the State and District values for each MP at the current congress.
     */
    public String getShortMPList() {
        JSONObject jList = new JSONObject();
        JSONArray a = new JSONArray();
        for (MP mp : currentCongress.getMPs()) {
            JSONObject jMP = new JSONObject();
            JSONString key = new JSONString("State");
            JSONString value = new JSONString(mp.getState().toString());
            jMP.addPair(key, value);
            key.setValue("District");
            value.setValue(String.valueOf(mp.getDistrict()));
            jMP.addPair(key, value);
            a.addElement(jMP);
        }
        JSONString key = new JSONString("MPList");
        JSONString value = new JSONString(a.toString());
        jList.addPair(key, value);
        return jList.stringify();
    }

    /**
     * @return It returns an String with the State, District and attributes values for each MP at the current congress.
     */
    public String getMPList() {
        JSONObject ret = new JSONObject();
        JSONString n = new JSONString("MPList");
        JSONArray mps = new JSONArray();
        for (MP mp : currentCongress.getMPs()) {
            JSONObject dip = new JSONObject();
            JSONString attrib = new JSONString(mp.getState().toString());
            dip.addPair(new JSONString("State"), attrib);
            attrib = new JSONString(Integer.toString(mp.getDistrict()));
            dip.addPair(new JSONString("District"), attrib);
            attrib = new JSONString(mp.getFullname());
            dip.addPair(new JSONString("Name"), attrib);
            for (Attribute a : mp.getAttributes()) {
                attrib = new JSONString(a.getValue().toString());
                dip.addPair(new JSONString(a.getDefinition().getName()), attrib);
            }
            mps.addElement(dip);
        }
        ret.addPair(n, mps);

        return ret.stringify();
    }

    /**
     *
     * @param state State of the MP
     * @param district District of the MP
     * @return Returns all the saved information about the MP (state,district).
     */
    public String getMPInfo(State state, int district) {
        MP mp = currentCongress.getMP(state,district);
        JSONObject mi = new JSONObject();
        mi.addPair(new JSONString("State"),new JSONString(mp.getState().toString()));
        mi.addPair(new JSONString("District"),new JSONString(Integer.toString(mp.getDistrict())));

        JSONArray ja = new JSONArray();
        for(Attribute a:mp.getAttributes()){
            String attrname = a.getDefinition().getName();
            String attrvalue = a.getValue().toString();
            JSONObject el = new JSONObject();
            el.addPair(new JSONString("AttrDefName"),new JSONString(attrname));
            el.addPair(new JSONString("AttrValue"),new JSONString(attrvalue));
            ja.addElement(el);
        }
        mi.addPair(new JSONString("Attributes"),ja);
        return mi.stringify();
    }

    /**
     * @return Returns an String with all the information about the AttrDefinitions defined in the current congress.
     */
    public String getAttrDefs() {
        JSONObject defs = new JSONObject();
        JSONString js = new JSONString("Attribute Definitions");
        JSONArray ja = new JSONArray();
        for (AttrDefinition def : currentCongress.getAttrDef()) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("AttrDefName"), new JSONString(def.getName()));
            jo.addPair(new JSONString("AttrDefImportance"), new JSONString(Integer.toString(def.getImportance())));
            ja.addElement(jo);
        }
        defs.addPair(js, ja);
        return defs.stringify();
    }

    public boolean existsAttrDef(String name)
    {
        return currentCongress.hasAttrDef(new AttrDefinition(name,1));
    }

    /**
     * @return Returns the current partition number of communities.
     */
    public String getCurrentPartitionNumber() { return String.valueOf(currentPartition.size()); }

    /**
     * @param comnumber
     * @return
     */
    public String getMPsCurrentPartition(String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Current partition Community numer " + comnumber);
        JSONArray ja = new JSONArray();
        for (MP mp : currentPartition.get(Integer.parseInt(comnumber))) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("State"), new JSONString(mp.getState().toString()));
            jo.addPair(new JSONString("District"), new JSONString(Integer.toString(mp.getDistrict())));
            ja.addElement(jo);
        }
        mps.addPair(js, ja);
        return mps.stringify();
    }

    /**
     * @return Returns the partition 1 number of communities.
     */
    public String getP1CommunityNumber() { return String.valueOf(partition1.size()); }

    public String getMPsPartition1 (String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Parition1 Community numer " + comnumber);
        JSONArray ja = new JSONArray();
        for (MP mp : partition1.get(Integer.parseInt(comnumber))) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("State"), new JSONString(mp.getState().toString()));
            jo.addPair(new JSONString("District"), new JSONString(Integer.toString(mp.getDistrict())));
            ja.addElement(jo);
        }
        mps.addPair(js, ja);
        return mps.stringify();
    }

    /**
     * @return Returns the partition 2 number of communities.
     */
    public String getP2CommunityNumber() {
        return Integer.toString(partition2.size());
    }

    public String getMPsPartiton2(String comnumber) {
        JSONObject mps = new JSONObject();
        JSONString js = new JSONString("Parition 2 Community numer "+comnumber);
        JSONArray ja = new JSONArray();
        for (MP mp : partition2.get(Integer.parseInt(comnumber))) {
            JSONObject jo = new JSONObject();
            jo.addPair(new JSONString("State"), new JSONString(mp.getState().toString()));
            jo.addPair(new JSONString("District"), new JSONString(Integer.toString(mp.getDistrict())));
            ja.addElement(jo);
        }
        mps.addPair(js, ja);
        return mps.stringify();
    }

    /**
     * @pre jMP doesn't belong to the current congress.
     * @post jMP belongs to the current congress.
     * @param jMP JSON Object defining the new MP.
     */
    public void addMP(JSONObject jMP) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("Name");
        JSONString jName = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        MP m = new MP(jName.getValue(), State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        currentCongress.addNode(m);
    }

    public void deleteMP(State state, int district)
    {
        currentCongress.removeNode(new MP("INVALID_VALUE",state,district));
    }

    /**
     * @pre jMP belongs to the current congress.
     * @post jMP doesn't belong to the current congress.
     * @param jMP JSON Object defining the MP to delete.
     */
    public void deleteMP(JSONObject jMP) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        key.setValue("Name");
        JSONString jName = new JSONString(((JSONString)jMP.getJSONByKey(key)).getValue());
        MP m = currentCongress.getMP(State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        currentCongress.removeNode(m);
    }

    /**
     * Adds the attribute to the MP specified. If the MP already has that attribute it is modified instead of added.
     * @param jAttr JSON Object defining the relatives MP and attribute.
     */
    public void addOrModifyAttribute(JSONObject jAttr) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        MP m = currentCongress.getMP(State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        key.setValue("AttrDef");
        JSONString jAttrD = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        key.setValue("AttrValue");
        JSONString jAttrV = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        Attribute a = new Attribute(currentCongress.getAttrDef(jAttrD.getValue()), jAttrV.getValue());
        m.addAttribute(a);
    }

    public void addOrModifyAttribute(JSONObject jmp,JSONArray jattrs)
    {
        Map<String,String> mpmss = jmp.basicJSONObjectGetInfo();

        MP mp = currentCongress.getMP(State.valueOf(mpmss.get("State")),Integer.parseInt(mpmss.get("District")));

        for(JSON j:jattrs.getArray()){
            Map<String,String> att = ((JSONObject)j).basicJSONObjectGetInfo();

            AttrDefinition atd = currentCongress.getAttrDef(att.get("AttrDefName"));
            if(atd == null) throw new IllegalStateException("NO EXISTEIX LATRIBUT");

            String value = att.get("AttrValue");
            mp.addAttribute(new Attribute(atd,value));
        }


    }


    /**
     * @pre The specified MP has a value defined for the specified attribute.
     * @post The specified MP has not any value defined for the (@pre) specified attribute.
     * @param jAttr JSON Object defining the relatives MP and attribute.
     */
    public void deleteAttribute(JSONObject jAttr) {
        JSONString key = new JSONString("State");
        JSONString jState = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        key.setValue("District");
        JSONString jDistr = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        MP m = currentCongress.getMP(State.valueOf(jState.getValue()), Integer.valueOf(jDistr.getValue()));
        key.setValue("AttrDef");
        JSONString jAttrD = new JSONString(((JSONString)jAttr.getJSONByKey(key)).getValue());
        m.removeAttribute(currentCongress.getAttrDef(jAttrD.getValue()));
    }

    /**
     * @pre true.
     * @post The AttrDefinition defined by jAttrDef is added/modified to/from the current congress.
     * @param jAttrDef JSON Object defining the relative AttrDefinition.
     */
    public void addOrModifyAttrDef(JSONObject jAttrDef) {
        JSONString key = new JSONString("AttrDefName");
        JSONString jAttrD = new JSONString(((JSONString)jAttrDef.getJSONByKey(key)).getValue());
        key.setValue("Importance");
        JSONString jImp = new JSONString(((JSONString)jAttrDef.getJSONByKey(key)).getValue());
        String imp = jImp.getValue();
        int importancia = 0;
        if(imp == "Low") importancia = 1;
        else if(imp == "Medium") importancia = 4;
        else if(imp == "High") importancia = 16;

        if(importancia == 0) throw new IllegalStateException("IMPORTANCIA NO RECONEGUDA");

        AttrDefinition ad = new AttrDefinition(jAttrD.getValue(), importancia);
        if (currentCongress.hasAttrDef(ad)) {
            currentCongress.getAttrDef(jAttrD.getValue()).setImportance(Integer.valueOf(jImp.getValue()));
        } else {
            currentCongress.addAttrDef(ad);
        }
    }

    /**
     * Deletes the AttrDefinition jAttrDef from the current congress.
     * @param jAttrDef JSON Object defining the jAttrDef we want to delete.
     */
    public void deleteAttrDef(JSONObject jAttrDef) {
        JSONString key = new JSONString("AttrDefName");
        JSONString jAttrD = new JSONString(((JSONString)jAttrDef.getJSONByKey(key)).getValue());
        currentCongress.removeAttrDef(currentCongress.getAttrDef(jAttrD.getValue()));
    }
}
