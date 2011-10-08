package models.user;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public enum Profile {
    STA, EXTERNE, ADMIN;

    public String getValue() {
        return super.toString();
    }

    public String getLabel() {
        return "profile." + super.toString().toLowerCase();
    }
}
