package org.avmframework.localsearch;

import org.avmframework.TerminationException;
import org.avmframework.objective.ObjectiveValue;

public class SimulatedAnnealingSearch extends LocalSearch {
    public static final double CoolingRate = 0.999;
    public static final double InitTemperature = 100;
    public static final double MIN_TEMP = 0.01;
    public static final int MAX_ITERATION = 1000;
    private double x2 = 1.001;
    private String coolingSystemMode = "Exponential";

    protected ObjectiveValue currentValue;
    protected ObjectiveValue neighborValue;

    @Override
    protected void performSearch() throws TerminationException {
        double temperature = InitTemperature;
        int currentNum = var.getValue();
        int NUM_ITERATIION = 0;
        while (true) {
            if (temperature < MIN_TEMP || NUM_ITERATIION > MAX_ITERATION) {
                break;
            }
            int neighborNum = getANeighborNum(currentNum);
            //get the current value
            currentValue = objFun.evaluate(vector);
            //set the neighbor num to the vector and get the neighbor value
            var.setValue(neighborNum);
            neighborValue = objFun.evaluate(vector);
            //System.out.println(currentNum);
            double acceptProbability = annealingProbability(temperature);
            if (acceptProbability >= (Math.random())) {
                currentNum = neighborNum;
            } else {
                var.setValue(currentNum);
            }
            if (coolingSystemMode.equals("Exponential")) {
                temperature *= CoolingRate;
            } else if (coolingSystemMode.equals("Liner")) {
                temperature -= 0.1;
            } else {
                temperature -= Math.log(x2);
                x2 += 0.001;
            }
            NUM_ITERATIION++;
        }
    }

    public double annealingProbability(Double temperature) throws TerminationException {
        double currentApproachLevel = currentValue.getApproachLevel();
        double currentBranchDistance = currentValue.getBranchDistance() / (1.0 + currentValue.getBranchDistance());
        double neighborApproachLevel = neighborValue.getApproachLevel();
        double neighborBranchDistance = neighborValue.getBranchDistance() / (1.0 + neighborValue.getBranchDistance());

        double distanceChange = (neighborApproachLevel + neighborBranchDistance) - (currentApproachLevel + currentBranchDistance);
        //System.out.println(currentApproachLevel + " " + currentBranchDistance + " " + neighborApproachLevel + " " + neighborBranchDistance + " " + distanceChange);
        if (neighborValue.betterThan(currentValue)) {
            return 1.00;
        } else {
            return Math.exp(-distanceChange / temperature);
        }
    }

    private int getANeighborNum(int currentNum) {
        double x = Math.random();
        int randomNeighbor = (int) ((currentNum / 2) + (currentNum * x));
        return randomNeighbor;
    }
}
