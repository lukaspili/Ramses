package service;

import models.contracts.JobOrder;
import models.school.SoeExam;
import models.school.YearCourse;
import models.user.User;
import org.joda.time.LocalDate;
import play.jobs.Job;

import javax.persistence.Query;
import java.util.List;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class JobOrderService {

    public List<JobOrder> getOrdersForUser(User user) {

        Query query = JobOrder.em().createQuery("select o from JobOrder as o where o.user = :user");
        query.setParameter("user", user);

        return query.getResultList();
    }

    public void createOrder(Set<YearCourse> courses, Set<SoeExam> soeExams, User user) {

        JobOrder order = new JobOrder();
        order.creationDate = new LocalDate();
        order.user = user;
        order.contract = user.contract;
        order.soeExams = soeExams;
        order.courses = courses;

        order.total = 0;

        for (YearCourse course : courses) {
            order.total += course.getTotal();
        }

        for (SoeExam soe : soeExams) {
            order.total += SoeExam.PRICE;
        }

        order.save();
    }
}
