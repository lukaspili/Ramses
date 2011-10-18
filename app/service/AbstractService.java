package service;

import models.user.User;
import play.db.jpa.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractService<T> {

    protected T detach(T t) {
        Model.em().detach(t);
        return t;
    }

    protected List<T> detach(List<T> list) {

        List<T> detachedList = new ArrayList<T>();

        for (T t : list) {
            Model.em().detach(t);
            detachedList.add(t);
        }

        return detachedList;
    }
}
