//package helpers;
//
//import exceptions.CoreException;
//import models.school.CourseType;
//import models.school.Prestation;
//import models.school.YearPromotion;
//
///**
// * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
// */
//public final class PriceHelper {
//
//    private PriceHelper() {
//
//    }
//
//    public static float getHourPrice(Prestation rcp) {
//        return getHourPriceForCourseTypeAndYearPromotion(rcp.realCourse.yearCourse.type, rcp.realCourse.yearPromotion);
//    }
//
//    public static float getHourPriceForCourseTypeAndYearPromotion(CourseType courseType, YearPromotion yearPromotion) {
//
//        float hourPrice;
//
//        if(yearPromotion.studentsNumber > 75) {
//            hourPrice = courseType.
//        }
//
//        if (courseType.level == CourseType.Level.LEVEL1) {
//            hourPrice = 20;
//
//        } else if (courseType.level == CourseType.Level.LEVEL2) {
//
//            if (yearPromotion.studentsNumber > 75) {
//                hourPrice = 30;
//            } else {
//                hourPrice = 20;
//            }
//
//        } else if (courseType.level == CourseType.Level.LEVEL3) {
//
//            if (yearPromotion.studentsNumber > 75) {
//                hourPrice = 37.5F;
//            } else {
//                hourPrice = 25;
//            }
//        } else {
//            throw new CoreException("CourseType has no price level");
//        }
//
//        return hourPrice;
//    }
//}
