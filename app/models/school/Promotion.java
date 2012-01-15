package models.school;

import play.i18n.Messages;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public enum Promotion {
    B1(1), B2(2), B3(3), M1(4), M2(5);

    Promotion(int level) {
        this.level = level;
    }

    public int level;

    public String getValue() {
        return super.toString();
    }

    public String getLabel() {
        return Messages.get("promotion." + super.toString().toLowerCase());
    }
}
