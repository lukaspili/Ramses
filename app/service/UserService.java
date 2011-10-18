package service;

import exceptions.CoreException;
import models.user.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;
import play.db.jpa.Model;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class UserService extends AbstractService<User> {

    public User save(User user) throws CoreException {

        if (null != getByIdBooster(user.idBooster)) {
            throw new CoreException().type(CoreException.Type.UNIQUE_CONSTRAINT_VIOLATION);
        }

        user.password = RandomStringUtils.randomAlphanumeric(9);
        user.save();

        return detach(user);
    }

    public User getById(long id) {
        User user = User.findById(id);
        return detach(user);
    }

    public User getByIdBooster(String idBooser) {

        checkNotNull(idBooser, "ID booster is required");

        User user = User.find("byIdBooster", idBooser).first();
        return detach(user);
    }

    public List<User> getUsers() {
        List<User> users = User.findAll();
        return detach(users);
    }

    public User getFromLogin(String idBooster, String password) {

        checkNotNull(idBooster, "ID booster is required");
        checkNotNull(password, "Password is required");

        User user = User.find("byIdBooster", idBooster).first();

        if (null == user) {
            return null;
        }

        boolean isPasswordValid;
        if (user.active) {
            isPasswordValid = new StrongPasswordEncryptor().checkPassword(password, user.password);
        } else {
            isPasswordValid = password.equals(user.password);
        }

        if (!isPasswordValid) {
            return null;
        }

        return detach(user);
    }
}
