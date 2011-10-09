package service;

import exceptions.CoreException;
import models.user.User;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class UserService {

    public User save(User user) throws CoreException {

        if (null != getByIdBooster(user.idBooster)) {
            throw new CoreException().type(CoreException.Type.UNIQUE_CONSTRAINT_VIOLATION);
        }

        user.save();
        return user;
    }

    public User getById(long id) {
        User user = User.findById(id);
        User.em().detach(user);
        return user;
    }

    public User getByIdBooster(String idBooser) {
        User user = User.find("byIdBooster", idBooser).first();
        User.em().detach(user);
        return user;
    }
}
