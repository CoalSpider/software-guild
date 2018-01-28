/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class Main {

    public static final String DELIMITER = "::";

    public static void main(String[] args) {
        Main m = new Main();

        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Fido", "Lab", 70, 4));
        dogs.add(new Dog("Beast", "Yellow Lab", 120, 10));
        dogs.add(new Dog("Sadie", "Mutt", 30, 7));

        m.saveFile(dogs);

        List<Dog> dogsAgian = m.loadFile("dogs.txt");
        for(Dog d : dogsAgian){
            System.out.println(d);
        }
    }

    private List<Dog> loadFile(String fileName) throws NumberFormatException {
        List<Dog> dogsAgian = new ArrayList<>();
        try {
            // buffered reader is a reader, so we go right to construction
            Scanner s2 = new Scanner(new BufferedReader(new FileReader(fileName)));
            // makes a FileInputStream and then creates a new more generic Reader class the first version is probably more efficent
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] lineParts = line.split(DELIMITER);

                Dog dog = new Dog();
                dog.setName(lineParts[0]);
                dog.setBreed(lineParts[1]);
                dog.setWeight(Integer.parseInt(lineParts[2]));
                dog.setAge(Integer.parseInt(lineParts[3]));

                dogsAgian.add(dog);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found " + ex.getMessage());
        }

        return dogsAgian;
    }

    private void saveFile(List<Dog> dogs) {
        PrintWriter pw = getPrintWriter("dogs.txt");

        for (Dog d : dogs) {
            pw.println(d.getName() + DELIMITER + d.getBreed() + DELIMITER + d.getWeight() + DELIMITER + d.getAge());
        }

        pw.close();
    }

    public PrintWriter getPrintWriter(String filename) {
        try {
            return new PrintWriter(filename);
        } catch (FileNotFoundException ex) {
            System.out.println("Cant't find file: " + ex.getMessage());
            return null;
        }
    }
}

// TODO: own file
class Dog {

    String name;
    String breed;
    int weight;
    int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Dog() {
        // TODO: default values
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public Dog(String name, String breed, int weight, int age) {
        this.name = name;
        this.breed = breed;
        this.weight = weight;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" + "name=" + name + ", breed=" + breed + ", weight=" + weight + ", age=" + age + '}';
    }
}
