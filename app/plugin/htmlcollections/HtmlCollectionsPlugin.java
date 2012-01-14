package plugin.htmlcollections;

import play.Logger;
import play.PlayPlugin;
import play.db.jpa.Model;
import plugin.htmlcollections.annotations.IsHtmlCollection;
import plugin.htmlcollections.exceptions.HtmlCollectionPluginException;

import javax.persistence.Query;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class HtmlCollectionsPlugin extends PlayPlugin {

    private static final String PLUGIN = "HtmlCollectionPlugin";

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
                    throw new HtmlCollectionPluginException("The collection has no type : Collection<???>");
                }

                String[] joins = ((IsHtmlCollection) annotation).joins();

                populateCollection(collection, collectionType, params.get(name), joins);

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

    private void populateCollection(Collection collection, Class collectionType, String[] params, String[] joins) {

        if (null == params) {
            return;
        }
        
//        String join = "";
//        for(int i = 0; i < joins.length; i++) {
//            join += " join m" + i + ;
//        }

        for (String param : params) {

            long id = 0;
            try {
                id = Long.valueOf(param);
            } catch (NumberFormatException e) {
                Logger.debug(PLUGIN + " : Invalid id from select : " + id);
            }

            String name = collectionType.getName().substring(collectionType.getName().lastIndexOf(".") + 1);
            Query query = Model.em().createQuery("SELECT m FROM " + name + " m WHERE m.id = :id");
            query.setParameter("id", id);

            Object o = query.getSingleResult();

            if (null != o) {
                collection.add(o);
                Logger.debug(PLUGIN + " : Add entity to collection : " + o);
            } else {
                Logger.debug(PLUGIN + " : Entity dosen't exists from select with id " + id);
            }
        }
    }
}
