package service;

import models.school.Course;

import javax.persistence.Query;
import java.util.List;

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
        return detach(courses);
    }

    public List<Course> getCoursesWithUsers() {

        Query query = Course.em().createQuery("select distinct c from Course c " +
                "left join fetch c.users u");

        List<Course> courses = query.getResultList();

        return detach(courses);
    }
}
