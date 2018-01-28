/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson5;

import com.sg.mod2.lesson34.UserIOImpl;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ben Norman
 */
public class StudentQuizGrades {

    private static final String INPUT_DELIMITER = "\"";
    private static final String SCORE_DELIMITER = " ";
    private static final String WHITE_SPACE = "(\\s+)";
    private static final String DOUBLE_QUOTE_STRING = "("+INPUT_DELIMITER+".*?"+INPUT_DELIMITER+")";

    private UserIOImpl io = new UserIOImpl(new Scanner(System.in));
    private Gradebook gradebook = new Gradebook();

    public static void main(String[] args) {
        new StudentQuizGrades().start();
    }

    void start() {
        io.print("--------------------------------------------\n");
        io.print("WELCOME TO THE STUDENT QUIZ DATABASE SYSTEM\n");
        io.print("--------------------------------------------\n");
        help();
        while (true) {
            String input = io.readString("> ");
            if (input.matches("(add)" + WHITE_SPACE + DOUBLE_QUOTE_STRING + WHITE_SPACE + DOUBLE_QUOTE_STRING)) {
                // we have 4 quotes, one set for name and another set for quiz scores
                int quote1 = input.indexOf(INPUT_DELIMITER);
                int quote2 = input.indexOf(INPUT_DELIMITER, quote1 + 1);
                int quote3 = input.indexOf(INPUT_DELIMITER, quote2 + 1);
                int quote4 = input.indexOf(INPUT_DELIMITER, quote3 + 1);
                // get name
                String name = input.substring(quote1 + 1, quote2);
                // get numbers, strip white spaces, and split into array for parsing
                String[] numbers = input.substring(quote3 + 1, quote4).trim().split(SCORE_DELIMITER);
                ArrayList<Integer> scores = new ArrayList<>();
                for (String s : numbers) {
                    // break if we get any errors parsing the scores
                    try {
                        scores.add(Integer.parseInt(s));
                    } catch (NumberFormatException e) {
                        io.print("malformed grade @ " + scores.indexOf(s) + "\n");
                        io.print("Type --help for a list of commands\n");
                        break;
                    }
                }
                add(name, scores);
            } else if (input.matches("(add)" + WHITE_SPACE + DOUBLE_QUOTE_STRING)) {
                add(getNameFromInput(input));
            } else if (input.matches("(remove)" + WHITE_SPACE + DOUBLE_QUOTE_STRING)) {
                remove(getNameFromInput(input));
            } else if (input.matches("(grades)" + WHITE_SPACE + DOUBLE_QUOTE_STRING)) {
                grades(getNameFromInput(input));
            } else if (input.matches("(gradesAvg)" + WHITE_SPACE + DOUBLE_QUOTE_STRING)) {
                gradesAvg(getNameFromInput(input));
            } else if (input.matches("(gradesAvg)")) {
                gradesAvgAll();
            } else if (input.matches("(print)")) {
                print();
            } else if (input.matches("(--help)")) {
                help();
            } else if (input.matches("(exit)")) {
                System.exit(0);
            } else {
                io.print("Unknown or Malformed Command\n");
                io.print("Type --help for a list of commands\n");
            }
        }
    }

    private String getNameFromInput(String command) {
        return command.substring(command.indexOf("\"") + 1).replaceAll("\"", "");
    }

    private void help() {
        io.print("--------------------------------------------\n");
        io.print("<name> == student name ex: "+INPUT_DELIMITER+"John Smith"+INPUT_DELIMITER+" \n");
        io.print("<grades> == space separated list of grades ex: "+INPUT_DELIMITER+"1 2 3 4 5"+INPUT_DELIMITER+" \n");
        io.print("\n");
        io.print("add <name> \t\tadds a student to the gradebook\n");
        io.print("add <name> <grades> \tadds a student to the gradebook with the given grades \n");
        io.print("remove <name> \t\tremove a student from the gradebook\n");
        io.print("grades <name> \t\tto get a list of grades for a specifc student \n");
        io.print("gradesAvg <name> \tto get the average grades for a specifc student \n");
        io.print("gradesAvg \t\tto get the average grades for the entire class \n");
        io.print("print \t\t\tlists all students in the gradebook\n");
        io.print("exit \t\t\t exit application \n");
        io.print("type --help to access this menu\n");
        io.print("--------------------------------------------\n");
    }

    private void add(String name) {
        if (gradebook.getStudents().contains(name)) {
            io.print(doubleQuoteName(name) + "already exits in gradebook\n");
            return;
        }
        gradebook.addStudent(name);
        io.print("added " + doubleQuoteName(name) + " Total entries: " + gradebook.getStudents().size() + "\n");
    }

    private void add(String name, ArrayList<Integer> grades) {
        if (gradebook.getStudents().contains(name)) {
            io.print(doubleQuoteName(name) + "already exits in gradebook\n");
            return;
        }
        gradebook.addStudent(name, grades);
        io.print("added " + doubleQuoteName(name) + " Total entries: " + gradebook.getStudents().size() + "\n");
    }

    private void remove(String name) {
        if (emptyGradebookOrStudentNotFound(name)) {
            return;
        }
        gradebook.removeStudent(name);
        io.print("removed " + doubleQuoteName(name) + " Total entries: " + gradebook.getStudents().size() + "\n");
    }

    private void grades(String name) {
        if (emptyGradebookOrStudentNotFound(name)) {
            return;
        }
        io.print("Quiz Scores for " + doubleQuoteName(name) + ":\n");
        ArrayList<Integer> scores = gradebook.getQuizScores(name);
        io.print("\t" + ((scores == null) ? "none" : scores) + "\n");
    }

    private void gradesAvg(String name) {
        if (emptyGradebookOrStudentNotFound(name)) {
            return;
        }
        io.print("Average score for " + doubleQuoteName(name) + ": " + gradebook.getAverageQuizScore(name) + "\n");
    }

    private void gradesAvgAll() {
        if (isGradebookEmpty()) {
            return;
        }
        io.print("Average score for the class: " + gradebook.getAverageAll() + "\n");
    }

    /**
     * wraps the given name in double quotes. ex: John Smith = "John Smith" *
     */
    private String doubleQuoteName(String name) {
        return "\"" + name + "\"";
    }

    private boolean emptyGradebookOrStudentNotFound(String name) {
        return isGradebookEmpty() || gradebookHasStudent(name) == false;
    }

    /**
     * if gradebook is empty prints a msg and return true else returns false*
     */
    private boolean isGradebookEmpty() {
        if (gradebook.getStudents().isEmpty()) {
            io.print("No students in gradebook\n");
            return true;
        }
        return false;
    }

    /**
     * if student exists in gradebook return true else print message and return false*
     */
    private boolean gradebookHasStudent(String name) {
        if (gradebook.hasStudent(name)) {
            return true;
        }
        io.print(doubleQuoteName(name) + " not found\n");
        return false;
    }

    private void print() {
        if(isGradebookEmpty()){
            return;
        }
        io.print("--------------------------------------------\n");
        for (String s : gradebook.getStudents()) {
            io.print(s + "\n");
        }
        io.print("--------------------------------------------\n");
    }

    private void highestScore() {
        throw new UnsupportedOperationException("not implemented");
    }

    private void lowestScore() {
        throw new UnsupportedOperationException("not implemented");
    }
}
