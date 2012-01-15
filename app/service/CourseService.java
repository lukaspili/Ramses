package service;

import exceptions.UniqueConstraintException;
import models.school.Course;

import javax.persistence.Query;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CourseService extends AbstractService<Course> {

    public List<Course> getClasses() {
        List<Course> courses = Course.findAll();
        return detach(courses);
    }

    public List<Course> getCourses() {

        List<Course> courses = Course.findAll();
        return courses;
    }

    public List<Course> getCoursesWithUsers() {

        Query query = Course.em().createQuery("select distinct c from Course c " +
                "left join fetch c.users u");

        List<Course> courses = query.getResultList();

        return courses;
    }

    public Course create(Course course) throws UniqueConstraintException {

        checkNotNull(course);
        checkNotNull(course.name);
        checkNotNull(course.code);
        checkNotNull(course.promotion);

        if (null != Course.find("byCodeAndPromotion", course.code, course.promotion).first()) {
            throw new UniqueConstraintException("Code already exists");
        }

        Course courseToSave = new Course();
        courseToSave.name = course.name;
        courseToSave.code = course.code;
        courseToSave.promotion = course.promotion;

        courseToSave.save();

        return courseToSave;
    }
}
