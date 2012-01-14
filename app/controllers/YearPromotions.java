package controllers;

import controllers.abstracts.AppController;
import controllers.security.LoggedAccess;
import helpers.YearCourseHelper;
import models.school.Promotion;
import models.school.YearCourse;
import models.school.YearPromotion;
import models.user.Profile;
import service.YearCourseService;
import service.YearPromotionService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class YearPromotions extends AppController {

    @Inject
    private static YearPromotionService yearPromotionService;

    @Inject
    private static YearCourseService yearCourseService;

    public static void index() {
        List<YearPromotion> yearPromotions = yearPromotionService.getYearPromotionsForYear(YearCourseHelper.getCurrentYear());
        render(yearPromotions);
    }

    public static void newYearPromotion() {
        List<Promotion> promotions = Arrays.asList(Promotion.values());
        List<YearCourse> yearCoursesModel = yearCourseService.getYearCoursesForYear(YearCourseHelper.getCurrentYear());
        List<Long> yearCourses = new ArrayList<Long>();
        render(promotions, yearCourses, yearCoursesModel);
    }

    public static void create(YearPromotion yearPromotion, List<Long> yearCourses) {

        if (null == yearCourses) {
            yearCourses = new ArrayList<Long>();
        }

        EnhancedValidator validator = validator();

        validator.validate(yearPromotion).require("name", "promotion");

        if (yearPromotion.studentsNumber == 0) {
            validator.addError("studentsNumber", "validation.required");
        }

        if (!validator.hasErrors() && null != yearPromotionService.getByNamePromotionAndYear(
                yearPromotion.name, yearPromotion.promotion, YearCourseHelper.getCurrentYear())) {

            validator.addError("name", "yearPromotions.create.error.already_exists");
        }

        if (validator.hasErrors()) {
            List<Promotion> promotions = Arrays.asList(Promotion.values());
            List<YearCourse> yearCoursesModel = yearCourseService.getYearCoursesForYear(YearCourseHelper.getCurrentYear());
            render("YearPromotions/newYearPromotion.html", promotions, yearPromotion, yearCourses, yearCoursesModel);
        }

        List<YearCourse> yearCoursesFromSelect = yearCourseService.getYearCoursesByIdsAndPromotion(yearCourses, yearPromotion.promotion);

        yearPromotionService.create(yearPromotion, yearCoursesFromSelect, YearCourseHelper.getCurrentYear());

        flashSuccess("yearPromotions.create.success");
        index();
    }

    public static void edit(long id) {

        YearPromotion yearPromotion = YearPromotion.findById(id);
        notFoundIfNull(yearPromotion);

        List<YearCourse> yearCoursesModel = yearCourseService.getYearCoursesForYear(YearCourseHelper.getCurrentYear());
        List<Long> yearCourses = collectionHelper.getIdsFromModel(yearPromotion.yearCourses);
        render(yearPromotion, yearCourses, yearCoursesModel);
    }

    public static void update(YearPromotion yearPromotion, List<Long> yearCourses) {

        notFoundIfNull(yearPromotion);
        notFoundIfNull(yearPromotion.id);

        YearPromotion yearPromotionModel = YearPromotion.findById(yearPromotion.id);
        notFoundIfNull(yearPromotionModel);

        if (null == yearCourses) {
            yearCourses = new ArrayList<Long>();
        }

        EnhancedValidator validator = validator().validate(yearPromotion);

        if (yearPromotion.studentsNumber == 0) {
            validator.addError("studentsNumber", "validation.required");
        }

        if (validator.hasErrors()) {
            List<YearCourse> yearCoursesModel = yearCourseService.getYearCoursesForYear(YearCourseHelper.getCurrentYear());
            render("YearPromotions/edit.html", yearPromotion, yearCourses, yearCoursesModel);
        }

        List<YearCourse> yearCoursesFromSelect = yearCourseService.getYearCoursesByIdsAndPromotion(yearCourses, yearPromotion.promotion);

        yearPromotionService.update(yearPromotionModel, yearPromotion, yearCoursesFromSelect);

        flashSuccess("yearPromotions.update.success");
        index();
    }
}
