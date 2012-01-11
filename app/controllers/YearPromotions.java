package controllers;

import controllers.abstracts.AppController;
import controllers.helper.PageHelper;
import controllers.security.LoggedAccess;
import helpers.YearCourseHelper;
import models.school.Promotion;
import models.school.YearCourse;
import models.school.YearPromotion;
import models.user.Profile;
import play.mvc.Before;
import plugin.htmlcollections.annotations.IsHtmlCollection;
import service.YearCourseService;
import service.YearPromotionService;
import validation.EnhancedValidator;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@LoggedAccess(Profile.ADMIN)
public class YearPromotions extends AppController {

    @Inject
    private static YearPromotionService yearPromotionService;

    @Inject
    private static YearCourseService yearCourseService;

    private static PageHelper pageHelper;

    @Before
    public static void before() {
        pageHelper = new PageHelper("yearPromotions", renderArgs);
    }

    public static void index() {
        List<YearPromotion> yearPromotions = yearPromotionService.getYearPromotionsForYear(YearCourseHelper.getCurrentYear());
        render(yearPromotions);
    }

    public static void newYearPromotion() {
        List<Promotion> promotions = Arrays.asList(Promotion.values());
        collectionHelper.renderCollection("yearCourses", yearCourseService.getYearCoursesForYear(YearCourseHelper.getCurrentYear()));
        render(promotions);
    }

    public static void create(YearPromotion yearPromotion, @IsHtmlCollection Set<YearCourse> yearCourses) {

        EnhancedValidator validator = validator();

        validator.validate(yearPromotion).require("name", "promotion");

        if (yearPromotion.studentsNumber == 0) {
            validator.addError("studentsNumber", "validation.required");
        }

//        Set<YearCourse> yearCoursesFromSelect = collectionHelper.getFromIds(YearCourse.class, yearCoursesIds, "course");

        for (YearCourse yc : yearCourses) {
            if (yc.course.promotion != yearPromotion.promotion) {
                validator.addError("yearCoursesIds", "yearPromotions.error.course_not_in_promotion", true);
            }
        }

        if (validator.hasErrors()) {
            List<Promotion> promotions = Arrays.asList(Promotion.values());
            collectionHelper.renderCollection("yearCourses", yearCourseService.getYearCoursesForYear(YearCourseHelper.getCurrentYear()), yearCourses);
            render("YearPromotions/newYearPromotion.html", promotions, yearPromotion);
        }

        yearPromotionService.create(yearPromotion, yearCourses, YearCourseHelper.getCurrentYear());

        flashSuccess("yearPromotions.create.success");
        index();
    }

    public static void edit(long id) {

    }

    public static void update(YearPromotion yearPromotion) {

    }
}
