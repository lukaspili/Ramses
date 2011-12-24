package plugin.htmlcollections;

import play.Logger;
import play.PlayPlugin;
import play.data.binding.Binder;
import plugin.htmlcollections.annotations.IsHtmlCollection;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class HtmlCollectionsPlugin extends PlayPlugin {

    @Override
    public Object bind(String name, Class clazz, Type type, Annotation[] annotations, Map<String, String[]> params) {

        if (null == annotations) {
            return null;
        }

        for (Annotation annotation : annotations) {

            if (annotation.annotationType().equals(IsHtmlCollection.class)) {

                Collection collection = getCollection(clazz);

                Class collectionType;
                if (type instanceof ParameterizedType) {
                    collectionType = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
                } else {
                    collectionType = String.class;
                }

                populateCollection(collection, collectionType, params.get(name));

                return collection;
            }
        }

        return null;
    }

    private Collection getCollection(Class clazz) {

        if (!Collection.class.isAssignableFrom(clazz)) {
            throw new UnsupportedOperationException();
        }

        if (clazz.isInterface()) {
            if (clazz.equals(List.class)) {
                clazz = ArrayList.class;
            } else if (clazz.equals(Set.class)) {
                clazz = HashSet.class;
            } else if (clazz.equals(SortedSet.class)) {
                clazz = TreeSet.class;
            } else {
                clazz = ArrayList.class;
            }
        }

        try {
            return (Collection) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void populateCollection(Collection collection, Class collectionType, String[] params) {

        if (null == params) {
            return;
        }

        for (String param : params) {
            try {
                collection.add(Binder.directBind(param, collectionType));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
