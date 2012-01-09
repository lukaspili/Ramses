package controllers.helper;

import play.classloading.enhancers.LocalvariablesNamesEnhancer;
import play.db.jpa.Model;

import javax.persistence.Query;
import java.util.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CollectionHelper {

    public <T extends Model> Set<T> getFromIds(Class<T> model, List<Long> ids) {

        Set<T> set = new HashSet<T>();

        if (null == ids) {
            return set;
        }

        for (Long id : ids) {

            T t = Model.em().find(model, id);

            if (null != t) {
                set.add(t);
            }
        }

        return set;
    }

    public <T extends Model> Set<T> getFromIds(Class<T> model, List<Long> ids, String... joins) {

        Set<T> set = new HashSet<T>();

        if (null == ids) {
            return set;
        }

        for (Long id : ids) {

            StringBuilder queryBuilder = new StringBuilder("select m from " + model.getSimpleName() + " m ");

            int i = 1;
            for (String join : joins) {
                queryBuilder.append("join m.");
                queryBuilder.append(join);
                queryBuilder.append(" m");
                queryBuilder.append(i);
                queryBuilder.append(" ");
            }

            queryBuilder.append("where m.id = :id");

            Query query = Model.em().createQuery(queryBuilder.toString());
            query.setParameter("id", id);

            Object o = query.getSingleResult();

            if (null != o) {
                set.add(model.cast(o));
            }
        }

        return set;
    }

    public <T extends Model> List<Long> getIdsFromModel(Collection<T> models) {

        List<Long> ids = new ArrayList<Long>();

        for (T model : models) {
            ids.add(model.getId());
        }

        return ids;
    }
}
