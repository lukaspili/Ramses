package controllers.helper;

import play.classloading.enhancers.LocalvariablesNamesEnhancer;
import play.db.jpa.Model;

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

    public <T extends Model> List<Long> getIdsFromModel(Collection<T> models) {

        List<Long> ids = new ArrayList<Long>();

        for (T model : models) {
            ids.add(model.getId());
        }

        return ids;
    }
}
