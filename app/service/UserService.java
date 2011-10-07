package service;

import models.user.User;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class UserService {

    public User save(User user) {
        user.save();
        return user;
    }

    public User getById(long id) {
        User user = User.findById(id);
        User.em().detach(user);
        return user;
    }

    public User getByEmail(String email) {
        User user = User.find("byEmail", email).first();
        User.em().detach(user);
        return user;
    }
}
