// Jean-Paul Boudreaux
// C00416940
// CMPS 260
// Programming Assignment : 05
// Due Date : April 1, 2022
// Program Description: In this assignment i am constructing 3 lunar lander objects (runs while lander is in air) that gives the
//                      user the option to fire the thruster, let the lander free fall, or repeat the last option with
//                      the same amount of time. Data is calculated depending on which object is chosen.
// Certificate of Authenticity:
// I certify that the code in the method function main of this project
// is entirely my own work.
package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner in = new Scanner(System.in);
        LunarLander[] lander = new LunarLander[3];
        for (int i = 0; i < lander.length; i++){
            lander[i] = new LunarLander();
            printTrajectory(lander[i]);
            printFuelStatus(lander[i]);}

        lander[0].setThrust(3.5);
        lander[0].setSafeLandingThreshold(-10);
        lander[0].setHardLandingThreshold(-15);
        lander[0].setFuelLoadTime(0.3);
        lander[0].setGravity(-1.62);

        lander[1].setThrust(4.25);
        lander[1].setSafeLandingThreshold(-5);
        lander[1].setHardLandingThreshold(-12);
        lander[1].setFuelLoadTime(0.1);
        lander[1].setGravity(-1.62);

        lander[2].setThrust(3.5);
        lander[2].setSafeLandingThreshold(-5);
        lander[2].setHardLandingThreshold(-12);
        lander[2].setFuelLoadTime(0.3);
        lander[2].setGravity(-1.62);

        System.out.println();

        String choice;

        boolean previousThrust = false;
        String historyChoice = "";
        double historyTime = 0;
        for (int i = 0; i < lander.length; i++)
            lander[i].checkLanding();

        while (lander[0].checkLanding() == 0 || lander[1].checkLanding() == 0 || lander[2].checkLanding() == 0) {
            System.out.println("Which Lander would you like to control? ");
            int landerChoice = in.nextInt();


            if (landerChoice == 0) {
                if (lander[0].checkLanding() != 0) {
                    System.out.println("Sorry, you cannot control a lander that has crashed or landed.");
                    continue;
                }

                in.nextLine();
                System.out.println("Fire (T)hruster, (F)ree Fall, or (R)epeat: ");
                choice = in.nextLine();

                switch (choice) {
                    case "T":
                    case "t":
                        System.out.print("Enter time for thruster (max 3): ");
                        lander[0].setThrustTimeLimit(3);
                        double thrustTime = in.nextDouble();
                        in.nextLine();
                        if (thrustTime > lander[0].getThrustTimeLimit())
                            thrustTime = 3;
                        lander[1].updateTrajectory(thrustTime, false);
                        lander[2].updateTrajectory(thrustTime, false);
                        if (previousThrust) {
                            thrustTime += lander[0].getFuelLoadTime();
                        }
                        boolean thrustFlag = lander[0].consumeFuelPellet();
                        System.out.println(thrustFlag);
                        lander[0].updateTrajectory(thrustTime, thrustFlag);

                        previousThrust = true; //or fuelResult?
                        historyChoice = "T";
                        historyTime = thrustTime;
                        break;
                    case "F":
                    case "f":
                        System.out.print("Enter time for free fall: ");
                        double freeFallTime = Math.max(0, in.nextDouble());
                        in.nextLine();//dummy read
                        lander[0].updateTrajectory(freeFallTime, false);
                        lander[1].updateTrajectory(freeFallTime, false);
                        lander[2].updateTrajectory(freeFallTime, false);
                        previousThrust = false;
                        historyChoice = "F";
                        historyTime = freeFallTime;

                        break;
                    case "R":
                    case "r":
                        if (historyChoice.length() > 0) { // a history has been set
                            if (historyChoice.equalsIgnoreCase("T")) {
                                if (previousThrust) {
                                    thrustFlag = lander[landerChoice].consumeFuelPellet();
                                    lander[landerChoice].updateTrajectory(historyTime + lander[0].getFuelLoadTime(), thrustFlag);
                                    lander[1].updateTrajectory(historyTime + lander[0].getFuelLoadTime(), false);
                                    lander[2].updateTrajectory(historyTime + lander[0].getFuelLoadTime(), false);
                                }
                                previousThrust = true; //or fuelResult?
                            } else if (historyChoice.equalsIgnoreCase("F")) {
                                lander[landerChoice].updateTrajectory(historyTime, false);
                                lander[1].updateTrajectory(historyTime, false);
                                lander[2].updateTrajectory(historyTime, false);
                                previousThrust = false;
                            }
                        }

                }
            }
            if (landerChoice == 1) {
                if (lander[1].checkLanding() != 0) {
                    System.out.println("Sorry, you cannot control a lander that has crashed or landed.");
                    continue;
                }
                in.nextLine();
                System.out.println("Fire (T)hruster, (F)ree Fall, or (R)epeat: ");
                choice = in.nextLine();
                switch (choice) {
                    case "T":
                    case "t":
                        System.out.print("Enter time for thruster (max 3): ");
                        lander[1].setThrustTimeLimit(3);
                        double thrustTime = in.nextDouble();

                        in.nextLine();
                        if (thrustTime > lander[1].getThrustTimeLimit())
                            thrustTime = 3;
                        lander[0].updateTrajectory(thrustTime, false);
                        lander[2].updateTrajectory(thrustTime, false);
                        if (previousThrust) {
                            thrustTime += lander[1].getFuelLoadTime();
                        }
                        boolean thrustFlag = lander[1].consumeFuelPellet();
                        lander[1].updateTrajectory(thrustTime, thrustFlag);

                        previousThrust = true; //or fuelResult?
                        historyChoice = "T";
                        historyTime = thrustTime;
                        break;
                    case "F":
                    case "f":
                        System.out.print("Enter time for free fall: ");
                        double freeFallTime = Math.max(0, in.nextDouble());
                        in.nextLine();//dummy read
                        lander[0].updateTrajectory(freeFallTime, false);
                        lander[1].updateTrajectory(freeFallTime, false);
                        lander[2].updateTrajectory(freeFallTime, false);
                        previousThrust = false;
                        historyChoice = "F";
                        historyTime = freeFallTime;

                        break;
                    case "R":
                    case "r":
                        if (historyChoice.length() > 0) { // a history has been set
                            if (historyChoice.equalsIgnoreCase("T")) {
                                if (previousThrust) {
                                    thrustFlag = lander[landerChoice].consumeFuelPellet();
                                    lander[landerChoice].updateTrajectory(historyTime + lander[1].getFuelLoadTime(), thrustFlag);
                                    lander[0].updateTrajectory(historyTime + +lander[1].getFuelLoadTime(), false);
                                    lander[2].updateTrajectory(historyTime + +lander[1].getFuelLoadTime(), false);
                                }

                                previousThrust = true; //or fuelResult?
                            } else if (historyChoice.equalsIgnoreCase("F")) {
                                lander[landerChoice].updateTrajectory(historyTime, false);
                                lander[0].updateTrajectory(historyTime, false);
                                lander[2].updateTrajectory(historyTime, false);
                                previousThrust = false;
                            }
                        }

                }
            }
            if (landerChoice == 2) {
                if (lander[2].checkLanding() != 0) {
                    System.out.println("Sorry, you cannot control a lander that has crashed or landed.");
                    continue;
                }
                in.nextLine();
                System.out.println("Fire (T)hruster, (F)ree Fall, or (R)epeat: ");
                choice = in.nextLine();
                switch (choice) {
                    case "T":
                    case "t":
                        System.out.print("Enter time for thruster (max 3): ");
                        lander[2].setThrustTimeLimit(3);
                        double thrustTime = in.nextDouble();

                        in.nextLine();
                        if (thrustTime > lander[2].getThrustTimeLimit())
                            thrustTime = 3;
                        lander[0].updateTrajectory(thrustTime, false);
                        lander[1].updateTrajectory(thrustTime, false);
                        if (previousThrust) {
                            thrustTime += lander[2].getFuelLoadTime();
                        }
                        boolean thrustFlag = lander[2].consumeFuelPellet();
                        lander[2].updateTrajectory(thrustTime, thrustFlag);

                        previousThrust = true; //or fuelResult?
                        historyChoice = "T";
                        historyTime = thrustTime;
                        break;
                    case "F":
                    case "f":
                        System.out.print("Enter time for free fall: ");
                        double freeFallTime = Math.max(0, in.nextDouble());
                        in.nextLine();//dummy read
                        lander[0].updateTrajectory(freeFallTime, false);
                        lander[1].updateTrajectory(freeFallTime, false);
                        lander[2].updateTrajectory(freeFallTime, false);
                        previousThrust = false;
                        historyChoice = "F";
                        historyTime = freeFallTime;

                        break;
                    case "R":
                    case "r":
                        if (historyChoice.length() > 0) { // a history has been set
                            if (historyChoice.equalsIgnoreCase("T")) {
                                if (previousThrust) {
                                    thrustFlag = lander[landerChoice].consumeFuelPellet();
                                    lander[landerChoice].updateTrajectory(historyTime + +lander[2].getFuelLoadTime(), thrustFlag);
                                    lander[0].updateTrajectory(historyTime + +lander[2].getFuelLoadTime(), false);
                                    lander[1].updateTrajectory(historyTime + +lander[2].getFuelLoadTime(), false);
                                }
                                previousThrust = true; //or fuelResult?
                            } else if (historyChoice.equalsIgnoreCase("F")) {
                                lander[landerChoice].updateTrajectory(historyTime, false);
                                lander[0].updateTrajectory(historyTime, false);
                                lander[1].updateTrajectory(historyTime, false);

                                previousThrust = false;
                            }
                        }

                }
            }
            for (int i = 0; i < lander.length; i++) {
                System.out.println("Fuel Status for lander " + i + ": ");
                printFuelStatus(lander[i]);
                System.out.println();
                System.out.println("Trajectory for lander " + i + ": ");
                printTrajectory(lander[i]);
                lander[i].checkLanding();
                System.out.println();

            }

        }
        System.out.println("Summary of fuel and trajectory:");
        for (int i = 0; i < lander.length; i++) {
            System.out.println("Fuel Status for lander " + i + ": ");
            printFuelStatus(lander[i]);
            System.out.println();
            System.out.println("Trajectory for lander " + i + ": ");
            printTrajectory(lander[i]);
            lander[i].checkLanding();
            System.out.println();

        }
    }

    public static void printFuelStatus(LunarLander lander) {
        int sum = 0;
        for (int idx = 0; idx < lander.getNumFuelVessels(); idx++) {
            System.out.print(idx + ": ");
            sum+= lander.getNumPellets(idx);
            for (int i = 0; i < lander.getNumPellets(idx); i++)
                System.out.print("|");
            System.out.println();
        }
        System.out.println("Total Fuel: " + sum);
    }

    public static void printTrajectory(LunarLander lander) {
        System.out.println("POS: " + String.format("%.2f", lander.getPosition()) +
                "\tVEL: " + String.format("%.2f", lander.getVelocity()));


        if (lander.getVelocity() >= lander.getSafeLandingThreshold())
            System.out.println("SAFE LANDING VELOCITY");
        else if (lander.getVelocity() >= lander.getHardLandingThreshold())
            System.out.println("HARD LANDING VELOCITY");
        else
            System.out.println("CRASH LANDING VELOCITY");
        System.out.println("\tSIMULATION TIME: " + String.format("%.2f", lander.getSimulationTime()));

    }



}
