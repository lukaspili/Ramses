package models.school;

import play.i18n.Messages;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public enum SoeExamState {

    WAITING, PLANNIFIED, COMPLETED;

    public String getValue() {
        return super.toString();
    }

    public String getLabel() {
        return Messages.get("soeExam." + super.toString().toLowerCase());
    }
}
