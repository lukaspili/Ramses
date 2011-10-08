package validation;

import exceptions.CoreException;
import org.apache.commons.lang3.StringUtils;
import play.classloading.enhancers.LocalvariablesNamesEnhancer;
import play.data.validation.Validation;
import play.mvc.Scope;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class EnhancedValidator {

    private Validation validation;
    private Scope.Params params;

    private Object object;
    private String objectName;

    public EnhancedValidator(Validation validation, Scope.Params params) {
        this.validation = checkNotNull(validation);
        this.params = checkNotNull(params);
    }

    public EnhancedValidator validate(Object object) {

        this.object = object;
        this.objectName = getLocalName(object);

        checkArguments();

        validation.valid(object);

        return this;
    }

    public EnhancedValidator require(String... attributes) {

        checkArguments();

        for (String attribute : attributes) {

            Object value = null;
            try {
                value = object.getClass().getField(attribute).get(object);
            } catch (Exception e) {
                throw new CoreException(e);
            }

            validation.required(objectName + "." + attribute, value);
        }

        return this;
    }

    public boolean hasErrors() {

        checkArguments();

        if (validation.errorsMap().isEmpty()) {
            return false;
        }

        params.flash();
        validation.keep();

        return true;
    }


//    public void validate(Object o) {
//
//        checkNotNull(o);
//
//        for (Field field : o.getClass().getFields()) {
//
//            for (Annotation fieldAnnotation : field.getAnnotations()) {
//
//                if (!fieldAnnotation.getClass().isAnnotationPresent(GroupValidation.class)) {
//                    validate(o, field);
//                }
//            }
//        }
//    }
//
//    public void validate(Object o, Class annotation) {
//
//        checkNotNull(o);
//
//        if (annotation.isAnnotationPresent(GroupValidation.class)) {
//
//            for (Field field : o.getClass().getFields()) {
//
//                if (field.isAnnotationPresent(annotation)) {
//                    validate(o, field);
//                }
//            }
//        }
//    }

//    public void validate(Object o, Class validationGroup) {
//
//        checkNotNull(o);
//        checkNotNull(validationGroup);
//
//        for (Field field : o.getClass().getDeclaredFields()) {
//
//            if (field.isAnnotationPresent(validationGroup)) {
//                validate(o, field);
//            }
//        }
//    }

//    public void validate(Object o, Field field) {
//
//        checkNotNull(o);
//        checkNotNull(field);
//
//        String objectName = getLocalName(o);
//
//        if (StringUtils.isBlank(objectName)) {
//            throw new CoreException("Unable to get local name for object to validate : " + o);
//        }
//
//        String name = objectName + "." + field.getName();
//
//        try {
//            validation.valid(name, field.get(o));
//        } catch (IllegalAccessException e) {
//            throw new CoreException(e);
//        }
//    }

    private String getLocalName(Object o) {

        List<String> names = LocalvariablesNamesEnhancer.LocalVariablesNamesTracer.getAllLocalVariableNames(o);
        if (names.size() > 0) {
            return names.get(0);
        }
        return "";
    }

    private void checkArguments() {
        checkNotNull(object);
        checkArgument(StringUtils.isNotBlank(objectName), "Object name for %s is required", object);
    }
}
