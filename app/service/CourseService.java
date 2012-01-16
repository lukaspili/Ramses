package service;

import exceptions.UniqueConstraintException;
import models.school.Course;
import models.school.YearCourse;
import models.user.User;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CourseService extends AbstractService<Course> {

    public List<Course> getAllCourses() {

        Query query = Course.em().createQuery("select c from Course c " +
                "order by c.promotion, c.code");

        return query.getResultList();
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

    public List<Course> getAvailableCoursesByUserAndYear(User user, int year) {

        if(null == user.skills || user.skills.isEmpty()) {
            return new ArrayList<Course>();
        }

        Query query = Course.em().createQuery("select distinct c from Course c " +
                "join c.yearCourses yc " +
                "where c in :skills and yc.year = :year " +
                "order by c.promotion, c.code");

        query.setParameter("skills", user.skills)
                .setParameter("year", year);

        return query.getResultList();
    }

    public void candidate(Course course, User user) {
        course.candidates.add(user);
        course.save();
    }

    public void cancelCandidature(Course course, User user) {
        course.candidates.remove(user);
        course.save();
    }
}
