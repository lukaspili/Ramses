package models;

import org.joda.time.LocalDate;
import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class Build extends Model {

    public String changelog;

    public LocalDate date;

    public String getFormattedNumber() {
        StringBuilder builder = new StringBuilder();

        if (id < 10) {
            builder.append("00");
        } else if (id < 100) {
            builder.append("0");
        }

        return builder.append(id).toString();
    }
}
