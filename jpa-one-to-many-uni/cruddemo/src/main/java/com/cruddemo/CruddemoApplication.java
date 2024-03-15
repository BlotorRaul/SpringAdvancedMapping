package com.cruddemo;

import com.cruddemo.dao.AppDAO;
import com.cruddemo.entity.Course;
import com.cruddemo.entity.Instructor;
import com.cruddemo.entity.InstructorDetail;
import com.cruddemo.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	//executed after the Spring Beans have been loaded
	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO)
	{
		return runner ->{
			//createCourseAndReviews(appDAO);

			//retrieveCourseAndReviews(appDAO);

			deleteCourseAndReviews(appDAO);

		};
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {

		int theId = 10;
		System.out.println("Deleting course id: " + theId);
		appDAO.deleteCourseById(theId);

		System.out.println("Done!");

	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {

		//get the course and reviews
		int theId=10;
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);

		//print the course
		System.out.println(tempCourse);

		//print the reviews
		System.out.println(tempCourse.getReviews());

		System.out.println("Done!");
	}

	private void createCourseAndReviews(AppDAO appDAO) {
		//create a course
		Course tempCourse = new Course("Pacman");

		//add some reviews
		tempCourse.addReview(new Review("Greate course... loved it!"));
		tempCourse.addReview(new Review("Cool course"));
		tempCourse.addReview(new Review("What a dumb course"));

		//save the course ...and leverage the cascade all
		System.out.println("Saving the course");
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());

		appDAO.save(tempCourse);

		System.out.println("Done");

	}

	private void deleteCourse(AppDAO appDAO) {
		int theId=10;
		System.out.println("Deleting course id: " + theId);

		appDAO.deleteCourseById(theId);

		System.out.println("Done!");
	}

	private void updateCourse(AppDAO appDAO) {
		int theId = 10;

		//find the course
		System.out.println("Finding course id: " + theId);
		Course tempCourse = appDAO.findCourseById(theId);

		//update the course
		System.out.println("Updating course id: " + theId);
		tempCourse.setTitle("Enjoy the Simple Things");

		appDAO.update(tempCourse);

		System.out.println("Done!");
	}

	private void updateInstructor(AppDAO appDAO) {
		int theId = 1;

		//find the instructor
		System.out.println("Finding instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		//update the instructor
		System.out.println("Updating instructor id: "+theId);

		tempInstructor.setLastName("TESTER");

		appDAO.update(tempInstructor);

		System.out.println("Done!");
	}

	private void findInstructorWithCourseJoinFetch(AppDAO appDAO) {

		int theId = 1;

		//find the instructor
		System.out.println("Finding instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);

		System.out.println("tempInstructor: " + tempInstructor);
		System.out.println("the associated courses: "+ tempInstructor.getCourses());

		System.out.println("Done!");
	}

	private void finCoursesForInstructor(AppDAO appDAO) {
		int theId =1;
		//find instructor
		System.out.println("Finding instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor: " + tempInstructor);

		//find courses for instructor
		System.out.println("Find courses for instructor id: " + theId);
		List<Course> courses = appDAO.findCoursesByInstructorId(theId);

		//associate the objects
		tempInstructor.setCourses(courses);

		System.out.println("the associated courses: "+tempInstructor.getCourses());

		System.out.println("Done!");
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		int theId =1;
		System.out.println("Finding instructor id: " + theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor: " + tempInstructor);
		System.out.println("the associated courses: " + tempInstructor.getCourses());

		System.out.println("Done!");
	}

	private void creatInstructorWithCourses(AppDAO appDAO) {

		//create the instructor
		Instructor tempInstructor = new Instructor("Susan", "Public", "susan@luv2code.com");

		//create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.youtube.com","Video Games");


		//associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		//-----------------------------------new code
		//create some courses
		Course tempCourse1 = new Course("Air Guitar - The Ultimate Guide");
		Course tempCourse2 = new Course("The Pinball Masterclass");

		//add courses to instructor
		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);

		//save the instructor

		//NOTE: this will also save the courses
		//because of CascadeType.Persist
		System.out.println("Saving instructor: " + tempInstructor);
		System.out.println("The courses: " + tempInstructor.getCourses());
		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}

	private void deleteInstructor(AppDAO appDAO) {
		int theId=1;
		System.out.println("Deleting instructor id: "+ theId);
		appDAO.deleteInstructorById(theId);
		System.out.println("Done!");
	}

	private void findInstructor(AppDAO appDAO) {
		int theId=2;

		System.out.println("Finding instructor id: " + theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor: "+ tempInstructor);
		System.out.println("the associate instructorDetail only: " + tempInstructor.getInstructorDetail());
	}

	/**
	 *
	 * Insert the associated entity first: InstructorDetail then insert Instructor
	 * nu putem schimba ordinea deoarece la Instructor trebuie sa stim id ptr InstructorDetail!!!
	 */
	private void createInstructor(AppDAO appDAO) {
		/*
		//create the instructor
		Instructor tempInstructor = new Instructor("Chad", "Darby", "darby@luv2code.com");

		//create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.luv2code","Luv 2 code!");
		*/

		//create the instructor
		Instructor tempInstructor = new Instructor("Madhu", "Patel", "madhu@luv2code.com");

		//create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.luv2code","Guidar");


		//associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		//save the instructor
		//
		//NOTE: this will also save the details object
		//because of CascadeType.All
		//
		System.out.println("Saving instructor: "+ tempInstructor);
		appDAO.save(tempInstructor);
		System.out.println("Done!");
	}
}
