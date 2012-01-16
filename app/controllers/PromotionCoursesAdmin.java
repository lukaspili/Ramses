package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import models.school.Prestation;
import models.school.PromotionCourse;
import models.user.Profile;
import models.user.User;
import org.joda.time.LocalDate;
import play.data.validation.Required;
import service.PrestationService;
import service.PromotionCourseService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
@LoggedAccess(Profile.ADMIN)
public class PromotionCoursesAdmin extends AppController {

    @Inject
    private static PromotionCourseService promotionCourseService;

    @Inject
    private static PrestationService prestationService;

    public static void edit(long promotionCourseId) {

        PromotionCourse promotionCourse = PromotionCourse.findById(promotionCourseId);
        notFoundIfNull(promotionCourse);

        LocalDate startDate = promotionCourse.startDate;
        LocalDate endDate = promotionCourse.endDate;

        List<Prestation> prestations = prestationService.getPrestationsByPromotionCourse(promotionCourse);

        render(promotionCourse, startDate, endDate, prestations);
    }

    public static void update(PromotionCourse promotionCourse, @Required Date startDate, @Required Date endDate) {

        EnhancedValidator validator = validator().validate(promotionCourse);

        if (!validator.hasErrors()) {
            promotionCourse.startDate = new LocalDate(startDate.getTime());
            promotionCourse.endDate = new LocalDate(endDate.getTime());

            if (promotionCourse.startDate.isAfter(promotionCourse.endDate)) {
                validator.addError("endDate", "validation.invalidDate", true);
            }
        }

        promotionCourse = PromotionCourse.findById(promotionCourse.id);
        notFoundIfNull(promotionCourse);

        if (validator.hasErrors()) {
            List<Prestation> prestations = prestationService.getPrestationsByPromotionCourse(promotionCourse);
            render("PromotionCoursesAdmin/edit.html", promotionCourse, startDate, endDate, prestations);
        }

        promotionCourseService.update(promotionCourse);

        flashSuccess("promotionscoursesadmin.update.success");
        edit(promotionCourse.id);
    }

    public static void updateOrAddProfessor(long userId, long promotionId, long hours) {

        if (hours < 0) {
            hours = 0;
        }

        User user = User.findById(userId);
        notFoundIfNull(user);

        PromotionCourse promotionCourse = PromotionCourse.findById(promotionId);
        notFoundIfNull(promotionCourse);

        if (hours > promotionCourse.getNotPlannifiedHours()) {
            flashError("promotionscoursesadmin.updateOrAddProfessor.error.hours_superior_than_remaining");
            edit(promotionCourse.id);
        }

        if (hours == 0 && null == prestationService.getPrestationByProfessorAndPromotion(user, promotionCourse)) {
            flashError("promotionscoursesadmin.updateOrAddProfessor.error.cannot_assign_0_hours_to_candidate");
            edit(promotionCourse.id);
        }

        prestationService.create(user, promotionCourse, hours);

        flashSuccess("promotionscoursesadmin.updateOrAddProfessor.success");
        edit(promotionCourse.id);
    }
}
