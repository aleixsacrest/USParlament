package es.upc.fib.prop.usParlament.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alex on 14/4/15.
 * contributions of ondrej.
 */

public class WeightAlgorithm {

    private Congress c;

    /**
     * @param c
     * Default constructor
     */
    public WeightAlgorithm(Congress c) {
        this.c = c;
    }

    /**
     * @pre m1 and m2 are two MPs of the congress C
     * @post Returns the list of common attributes between m1 and m2
     * @param m1
     * @param m2
     * @return set of common attributes between m1 and m2
     */
    public Set<Attribute> getCommonAttributes(MP m1, MP m2) {
        Set<Attribute> ret = new HashSet<>();
        ret = m1.getAttributes();
        ret.retainAll(m2.getAttributes());
        return ret;
    }

    /**
     * @pre m1 and m2 are two MPs of the congress C, CA is the set of common attributes between them
     * @post The weight between m1 and m2 is added to C.
     * @param m1
     * @param m2
     * @param CA
     * The weight between m1 and m2 is added to C.
     */
    public void computeWeight(MP m1, MP m2, Set<Attribute> CA) {
        int w = 0; //0 = no relationship
        Relationship r = (Relationship) c.getEdge(m1, m2);
        if (r.isValid()) {
            for (Attribute a : CA) {
                w += a.getDefinition().getImportance();
            }
        }
        r.setWeight(w);
    }

    /**
     * @pre True
     * @post The weights of all the graph are computed, and the edges added
     * The weights of all the graph are computed, and the edges added
     */
    public void computeAllWeights() {
        Set<MP> mps = c.getNodes();
        for (MP m1 : mps) {
            for (MP m2 : mps) {
                if (m1 != m2 && !c.hasEdge(m1, m2)) {
                    Set<Attribute> ca = getCommonAttributes(m1, m2);
                    if (!ca.isEmpty()) {
                        Relationship r = new Relationship(m1, m2, 0);
                        computeWeight(m1, m2, ca);
                    }
                }
            }
        }
    }


}
