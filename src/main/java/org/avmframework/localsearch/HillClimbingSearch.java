package org.avmframework.localsearch;

import org.avmframework.TerminationException;
import org.avmframework.objective.ObjectiveValue;
import org.avmframework.variable.AtomicVariable;


import java.util.ArrayList;

public class HillClimbingSearch extends LocalSearch {

    protected ObjectiveValue currentValue;
    protected ObjectiveValue neighborValue;
    protected int num;

    protected int Max_Generate_time = 150;

    @Override
    protected void performSearch() throws TerminationException {
        //implement the steepest hill climbing which is used in the TSP project.
        boolean is_climb = true;
        int optimalNumber = var.getValue();
        while (is_climb) {
            ArrayList<Integer> neighborNumbers = getNeighborNumbers(var.getValue());
            is_climb = false;

            currentValue = objFun.evaluate(vector);
            for (Integer neighborNumber : neighborNumbers) {
                var.setValue(neighborNumber);

                neighborValue = objFun.evaluate(vector);

                if (neighborValue.betterThan(currentValue)) {
                    currentValue = neighborValue;
                    is_climb = true;
                    optimalNumber = neighborNumber;
                }
            }
            if (!is_climb) {
                var.setValue(optimalNumber);
            }
        }
    }

    public ArrayList<Integer> getNeighborNumbers(int currentNum) {
        ArrayList<Integer> neighborNumbers = new ArrayList<>();
        for (int i = 0; i < Max_Generate_time; i++) {
            int randomNeighbor = (int) ((currentNum / 2) + (currentNum * Math.random()));
            //int randomNeighbor = (int) (currentNum + (Math.random() * currentNum - currentNum / 2));
            neighborNumbers.add(randomNeighbor);
        }
        return neighborNumbers;
    }
}
