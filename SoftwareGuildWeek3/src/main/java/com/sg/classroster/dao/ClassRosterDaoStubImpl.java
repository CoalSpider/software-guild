/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.dao;

import com.sg.classroster.dto.Student;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ben Norman
 */
public class ClassRosterDaoStubImpl implements ClassRosterDao {
   
    private Student onlyStudent;
    private List<Student> studentList = new ArrayList<>();
   
    public ClassRosterDaoStubImpl() {
        onlyStudent = new Student("0001");
        onlyStudent.setFirstName("John");
        onlyStudent.setLastName("Doe");
        onlyStudent.setCohort("Java-Jan-2015");
       
        studentList.add(onlyStudent);
    }
 
    @Override
    public Student addStudent(String studentId, Student student) throws ClassRosterPersistenceException {
        if (studentId.equals(onlyStudent.getStudentID())) {
            return onlyStudent;
        } else {
            return null;
        }
    }
 
    @Override
    public List<Student> getAllStudents() throws ClassRosterPersistenceException {
        return studentList;
    }
 
    @Override
    public Student getStudent(String studentId) throws ClassRosterPersistenceException {
        if (studentId.equals(onlyStudent.getStudentID())) {
            return onlyStudent;
        } else {
            return null;
        }       
    }
 
    @Override
    public Student removeStudent(String studentId) throws ClassRosterPersistenceException {
        if (studentId.equals(onlyStudent.getStudentID())) {
            return onlyStudent;
        } else {
            return null;
        }
    }   
}
