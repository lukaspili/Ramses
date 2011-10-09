package validation.check;

import org.apache.commons.lang3.StringUtils;
import play.data.validation.Check;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class NumericCheck extends Check {

    public boolean isSatisfied(Object user, Object value) {
        setMessage("validation.numeric");
        return StringUtils.isNumeric(String.valueOf(value));
    }
}
