/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Ben Norman
 */
public class Gradebook {

    private final Map<String, ArrayList<Integer>> book = new HashMap<>();
    private final Random random = new Random();

    public Set<String> getStudents() {
        return book.keySet();
    }

    public void addStudent(String name) {
        book.put(name, generateRandomGrades());
    }
    
    public void addStudent(String name, ArrayList<Integer> grades){
        book.put(name, grades);
    }

    public void removeStudent(String name) {
        book.remove(name);
    }

    public ArrayList<Integer> getQuizScores(String name) {
        return book.get(name);
    }

    public int getAverageQuizScore(String name) {
        ArrayList<Integer> quizScores = book.get(name);
        int total = 0;
        //total = quizScores.stream().map((i) -> i).reduce(total, Integer::sum);
        for (Integer i : quizScores) {
            total += i;
        }
        return total / quizScores.size();
    }

    public int getAverageAll() {
        int total = 0;
        //total = book.keySet().stream().map((s) -> getAverageQuizScore(s)).reduce(total, Integer::sum);
        for (String s : book.keySet()) {
            total += getAverageQuizScore(s);
        }
        return total / book.keySet().size();
    }
    
    public boolean hasStudent(String name){
        return book.containsKey(name);
    }

    private ArrayList<Integer> generateRandomGrades() {
        ArrayList<Integer> spl = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            spl.add(random.nextInt(100));
        }
        return spl;
    }
}
