package controllers.helper;

import play.classloading.enhancers.LocalvariablesNamesEnhancer;
import play.db.jpa.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CollectionHelper {

    public <T extends Model> List<T> getFromIds(List<Long> ids) {

        List<T> list = new ArrayList<T>();

        for (Long id : ids) {

            T t = T.findById(id);

            if (null != t) {
                list.add(t);
            }
        }

        return list;
    }
}
