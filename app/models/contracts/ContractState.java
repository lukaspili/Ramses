package models.contracts;

import play.i18n.Messages;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public enum ContractState {
    CREATED, SIGNED_BY_STA, SIGNED_BY_SUPINFO;

    public String getValue() {
        return super.toString();
    }

    public String getLabel() {
        return Messages.get("contractState." + super.toString().toLowerCase());
    }
}
