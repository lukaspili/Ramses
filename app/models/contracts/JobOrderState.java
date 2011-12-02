package models.contracts;

import models.user.Profile;
import play.i18n.Messages;

import java.util.Arrays;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public enum JobOrderState {
    CREATED, SIGNED;

    public String getValue() {
        return super.toString();
    }

    public String getLabel() {
        return Messages.get("jobOrderState." + super.toString().toLowerCase());
    }

    public static List<JobOrderState> getList() {
        return Arrays.asList(JobOrderState.values());
    }
}
