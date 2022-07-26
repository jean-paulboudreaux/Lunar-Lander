package com.company;

public class LunarLander<trajectory> {
    private static final double THRUST_ACCEL = +3.5;
    private static final double GRAVITY_ACCEL = -1.62;
    private static final int NO_LANDING = 0;
    private static final int SAFE_LANDING = +1;
    private static final int HARD_LANDING = -1;
    private static final int CRASH_LANDING = -2;

    private int[] fuelPellets = {45, 45, 45, 45, 45, 45};
    private int[] indexArray = {0, 3, 1, 4, 2, 5};
    private int indexCounter = 0;
    private double[] trajectory = new double[3];
    private double thrustTimeLimit;
    private double thrust;
    private double gravity;
    private double fuelLoadTime;
    private int safeLandingThreshold;
    private int hardLandingThreshold;


    public LunarLander() {
        trajectory[0] = 500;
        trajectory[1] = -1 * (Math.random() * 10 + 20);
        trajectory[2] = 0;
    }

    public boolean consumeFuelPellet() {
        if(indexCounter >= fuelPellets.length)
            indexCounter = 0;
        if (indexCounter < 0 || indexCounter >= fuelPellets.length)
            return false;
        else if (fuelPellets[indexArray[indexCounter]] == 0) {
            indexCounter = indexCounter % indexArray.length;
            return false;
        }
        else {
            fuelPellets[indexArray[indexCounter]]--;
            indexCounter++;
            return true;
        }
    }

    public int checkLanding() {
        if (trajectory[0] > 0) {
            return NO_LANDING;
        }
        else {
            if (trajectory[1] >= safeLandingThreshold)
            return SAFE_LANDING;
            else if (trajectory[1] >= hardLandingThreshold)
                return HARD_LANDING;
            else
                return CRASH_LANDING;
        }
    }
    public void setThrustTimeLimit(double t){
        if (t > 0)
            thrustTimeLimit = t;
    }
    public void setThrust(double t){
        if (t > Math.abs(gravity))
            thrust = t;
    }
    public void setGravity(double g){
        if (gravity < thrust)
            gravity = g;
        }

    public void setFuelLoadTime (double time){
        for (int f : fuelPellets){
            if (f > 0)
                fuelLoadTime = time;
        }
    }


    public void setSafeLandingThreshold(int threshold) {
        if (threshold < 0)
            this.safeLandingThreshold = threshold;
    }

    public void setHardLandingThreshold(int threshold) {
        if (threshold < safeLandingThreshold)
            this.hardLandingThreshold = threshold;
    }
    public double getThrustTimeLimit(){
        return thrustTimeLimit;
    }
    public double getThrust(){
        return thrust;
    }
    public double getGravity(){
        return gravity;
    }
    public double getFuelLoadTime(){
        return fuelLoadTime;
    }

    public int getSafeLandingThreshold() {
        return safeLandingThreshold;
    }

    public int getHardLandingThreshold() {
        return hardLandingThreshold;
    }

    public void updateTrajectory(double timeAmount, boolean thrustFlag) {
        if (checkLanding() != NO_LANDING)
            trajectory[0] = 0;
        double STEP = 0.01;
        double t;
        for (t = 0.0; t < timeAmount; t += STEP) {
            if (checkLanding() != NO_LANDING) {
                trajectory[0] = 0;
                trajectory[1] = trajectory[1] + (gravity + (thrustFlag ? thrust : 0)) * STEP;
            } else {
                trajectory[0] = trajectory[0] + trajectory[1] * STEP;
                trajectory[1] = trajectory[1] + (gravity + (thrustFlag ? thrust: 0)) * STEP;
            }
        }
        System.out.println(gravity);
        trajectory[2] += timeAmount;
    }
    public int getNumFuelVessels(){
        return fuelPellets.length;
    }
    public int getNumPellets(int index){
        return fuelPellets[index];
    }
    public double getPosition(){
        return trajectory[0];
    }
    public double getVelocity(){
        return trajectory[1];
    }
    public double getSimulationTime(){
        return trajectory[2];
    }

}
