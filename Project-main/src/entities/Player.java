package entities;

import java.util.Random;

// Makes use of class-based singleton
// https://www.baeldung.com/java-singleton 
public class Player {
    private static Player instance; // Singleton instance
    private String name;
    private int age;
    private int lifeExpectancy;
    private boolean isAlive = true;
    private int monthCounter = 0;  // Tracks the passage of months

    // Private constructor to prevent instantiation from outside
    private Player() {
        this.age = 0;
        this.lifeExpectancy = new Random().nextInt(31) + 70;  // Life expectancy between 70 and 100
    }

    public void increaseMonth(){
        monthCounter++;
    }

    public void decreaseMonth(){
        monthCounter++;
    }

    public int getMonth(){
        return monthCounter;
    }

    // Static method to get instance
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void incrementAge() {
        age++;
        checkLifeStatus();
    }

    private void checkLifeStatus() {
        if (age >= lifeExpectancy) {
            isAlive = false;
        }
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public int getLifeExpectancy() {
        return lifeExpectancy;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String getName() {
        return name;
    }
}
