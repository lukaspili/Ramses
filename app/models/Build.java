package models;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Entity
public class Build extends Model {

    @Column(name = "changelog", columnDefinition = "TEXT")
    public String changelog;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
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
