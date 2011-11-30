package controllers;

import controllers.abstracts.AppController;
import controllers.security.Auth;
import controllers.security.LoggedAccess;
import models.contracts.JobOrder;
import models.school.SoeExam;
import models.school.YearCourse;
import models.user.User;
import service.JobOrderService;
import service.SoeExamService;
import service.YearCourseService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess
public class Orders extends AppController {

    @Inject
    private static JobOrderService jobOrderService;

    @Inject
    private static YearCourseService yearCourseService;

    @Inject
    private static SoeExamService soeExamService;

    public static void view() {

        User user = Auth.getCurrentUser();

        // list orders
        List<JobOrder> orders = jobOrderService.getOrdersForUser(user);

        // list courses not in orders
        List<YearCourse> courses = yearCourseService.getNotOrderedCoursesForUser(user);
        List<Long> availableCourses = new ArrayList<Long>();

        render(orders, courses, availableCourses);
    }

    public static void create(List<Long> availableCourses, List<Long> availableSoeExams) {

        if (!((null != availableCourses && !availableCourses.isEmpty()) ||
                (null != availableSoeExams && !availableSoeExams.isEmpty()))) {

            flashError("jobOrders.create.error.empty");
            view();
        }

        Set<YearCourse> courses = new HashSet<YearCourse>();

        for (long id : availableCourses) {

            YearCourse course = yearCourseService.getNotOrderedCourse(id);

            if (null == course) {
                flashError("jobOrders.create.error.already_ordered_course");
                view();
            }

            courses.add(course);
        }

        Set<SoeExam> soeExams = new HashSet<SoeExam>();

        jobOrderService.createOrder(courses, soeExams, Auth.getCurrentUser());

        flashSuccess("jobOrders.create.success");
        view();
    }

    public static void show(long orderId) {

        JobOrder order = JobOrder.findById(orderId);

        notFoundIfNull(order);

        render(order);
    }
}
