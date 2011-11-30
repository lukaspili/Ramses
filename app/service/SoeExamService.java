package service;

import models.school.SoeExam;
import models.school.SoeExamState;
import models.user.Profile;
import models.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class SoeExamService {

    public List<SoeExamState> getStatesList() {
        return Arrays.asList(SoeExamState.values());
    }

    public void update(SoeExam soe, Set<User> users) {
        soe.examinators = users;
        soe.save();
    }
}
