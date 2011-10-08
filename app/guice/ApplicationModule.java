package guice;

import com.google.inject.AbstractModule;
import models.user.User;
import service.ProfileService;
import service.UserService;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(UserService.class);
        bind(ProfileService.class);
    }
}
