package pl.kbtest;

import pl.kbtest.contract.SetFact;
import pl.kbtest.contract.SetPremise;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetFactUtils {

    public static boolean compareFactValue(SetFact f1, SetFact f2) {
        if (f1.getHead().equals(f2.getHead())) {
            if (f1.getSet().size() == f2.getSet().size()) {
                if (f1.getSet().containsAll(f2.getSet())) {
                    return f1.isConjunction() == f2.isConjunction();
                }
            }
        }
        return false;
    }

    public static boolean isSetFactSubset(SetPremise subset, SetFact of) {
        if (subset.equals(of)) {
            return true;
        }
        if (subset.getSet().equals(of.getSet())) {
            return true;
        }
        if (of.getHead().equals(subset.getHead())) {
            if (subset.getSet().isEmpty() && of.getSet().isEmpty()) {
                return true;
            }
            if (of.isConjunction() && subset.isConjunction()) {
                if (of.getSet().containsAll(subset.getSet())) {
                    return true;
                }
            }
            if ((!subset.isConjunction() | subset.getSet().size() == 1) && (!of.isConjunction() | of.getSet().size() == 1)) {
                Set<String> copiedSubset = new HashSet<>(subset.getSet());
                Set<String> copiedOf = new HashSet<>(of.getSet());
                copiedSubset.removeAll(new HashSet<>(of.getSet()));
                copiedOf.removeAll(new HashSet<>(subset.getSet()));
                return copiedSubset.isEmpty() || copiedOf.isEmpty();
            }
        }
        return false;
    }


    public static boolean isMatch(SetPremise presmie, SetFact fact) {
        //no match when non equals heads
        if (!presmie.getHead().equals(fact.getHead())) {
            return false;
        }
        //if both same type and sets are equal
        if (presmie.isConjunction() == fact.isConjunction() && presmie.getSet().equals(fact.getSet())) {
            return true;
        }
        //both conjunction and one is subset of another
        if (fact.isConjunction() && presmie.isConjunction()) {
            if (fact.getSet().containsAll(presmie.getSet())) {
                return true;
            }
        }
        //if disjunction with conjunction and sets are equal and there is at least one element in common
        if (!presmie.isConjunction() && fact.isConjunction()) {
            if (presmie.getSet().equals(fact.getSet())) {
                return true;
            }
            if (!Collections.disjoint(presmie.getSet(), fact.getSet())) {
                return true;
            }
        }
        if (!presmie.isConjunction() && !fact.isConjunction()) {
            return presmie.getSet().containsAll(fact.getSet());
        }
        return false;
    }

}
