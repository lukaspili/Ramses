//package controllers.helper;
//
//import play.classloading.enhancers.LocalvariablesNamesEnhancer;
//import play.data.validation.Validation;
//
//import java.util.List;
//
///**
// * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
// */
//public class ValidationBuilder {
//
//    private Validation validation;
//
//    public ValidationBuilder(Validation validation) {
//        this.validation = validation;
//    }
//
//    public ValidationBuilder required(Object o) {
//
//        System.out.println(getLocalName(o));
//        return this;
//    }
//
//    public ValidationBuilder required(Object o, String... attributes) {
//
////        Class clazz = o.getClass();
////
////        for (String attribute : attributes) {
////
////            Object value;
////            try {
////                value = clazz.getDeclaredField(attribute).get(object);
////            } catch (Exception e) {
////                throw new CoreException(e);
////            }
////
////            validation.required(objectName + "." + attribute, value);
//        return this;
//    }
//
//
//}
