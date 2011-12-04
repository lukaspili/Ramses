package service;

import models.Build;
import org.joda.time.LocalDate;
import play.Logger;

import javax.persistence.Query;
import java.util.List;


/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class BuildService {

    public Build getLatestBuild() {

        Query query = Build.em().createQuery("select b from Build b order by b.id desc");
        query.setMaxResults(1);

        try {
            return (Build) query.getSingleResult();
        } catch (Exception e) {

            Logger.error("Cannot get latest build : " + e.getMessage());

            Build build = new Build();
            build.changelog = "Initial build.";
            build.date = new LocalDate();
            build.save();

            Logger.info("Create new initial build with number : " + build.getFormattedNumber());

            return build;
        }
    }

    public List<Build> getBuildsShortHistory() {

        Query query = Build.em().createQuery("select b from Build b order by b.id desc");
        query.setMaxResults(15);

        return query.getResultList();
    }

    public void save(Build build) {
        build.date = new LocalDate();
        build.save();
    }
}
