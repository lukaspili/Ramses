package service;

import models.user.Profile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class ProfileService {

    public List<Profile> getProfilesList() {
        return Arrays.asList(Profile.values());
    }
}
