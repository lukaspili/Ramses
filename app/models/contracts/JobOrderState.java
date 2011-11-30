package models.contracts;

import play.i18n.Messages;

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
}
