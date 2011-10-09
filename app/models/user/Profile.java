package models.user;

import play.i18n.Messages;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public enum Profile {
    STA, EXTERNE, ADMIN;

    public String getValue() {
        return super.toString();
    }

    public String getLabel() {
        return Messages.get("profile." + super.toString().toLowerCase());
    }
}
