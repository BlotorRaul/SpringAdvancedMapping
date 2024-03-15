package com.cruddemo.dao;

import com.cruddemo.entity.Course;
import com.cruddemo.entity.Instructor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {
    //define field for entity manager
    private EntityManager entityManager;

    //inject entity manager using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional //pentru update/ save
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {

        //retrieve the instructor
        Instructor tempInstructor = entityManager.find(Instructor.class, theId);

        //get the courses
        List<Course> courses = tempInstructor.getCourses();

        //break association of all courses for the instructor
        for(Course tempCourse: courses)
        {
            tempCourse.setInstructor(null); //daca nu fac asta aparea eroarea de la seql cu foreign key
        }

        //delete the instructor
        entityManager.remove(tempInstructor);

    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {

        //create query
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id= :data", Course.class);

        query.setParameter("data", theId);

        //execute query
        List<Course> courses = query.getResultList();

        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {

        //create query
        TypedQuery<Instructor> query = entityManager.createQuery(
                "select i from Instructor i  JOIN FETCH  i.courses " +
                        "join fetch i.instructorDetail where i.id =: data", Instructor.class);

        query.setParameter("data", theId);

        //execute query
        Instructor instructor = query.getSingleResult();

        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
        entityManager.merge(tempInstructor);

    }

    @Override
    @Transactional
    public void update(Course tempCourse) {
        entityManager.merge(tempCourse);
    }

    @Override
    public Course findCourseById(int theId) {
        return entityManager.find(Course.class,theId);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {

        //retrieve the course

        Course tempCourse = entityManager.find(Course.class,theId);

        //delete the course

        entityManager.remove(tempCourse);
    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        entityManager.persist(theCourse);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {
        //create query
        TypedQuery<Course>query = entityManager.createQuery(
                "select c from Course c Join fetch c.reviews where c.id= :data",
                Course.class);

        query.setParameter("data", theId);

        //execute query
        Course course = query.getSingleResult();

        return course;
    }
}
