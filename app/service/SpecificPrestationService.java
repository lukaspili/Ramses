package service;

import models.contracts.SpecificPrestation;
import models.school.Prestation;
import models.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class SpecificPrestationService {

    public SpecificPrestation create(SpecificPrestation specificPrestation) {

        SpecificPrestation specificPrestationToSave = new SpecificPrestation();
        specificPrestationToSave.hours = specificPrestation.hours;
        specificPrestationToSave.price = specificPrestation.price;
        specificPrestationToSave.title = specificPrestation.title;
        specificPrestationToSave.date = specificPrestation.date;
        specificPrestationToSave.user = specificPrestation.user;
        specificPrestationToSave.yearCourse = specificPrestation.yearCourse;
        specificPrestationToSave.save();

        return specificPrestationToSave;
    }

    public List<SpecificPrestation> getSpecificPrestationsWithUserAndYearCourse() {

        return SpecificPrestation.em().createQuery("select sp from SpecificPrestation sp " +
                "join sp.user u join sp.yearCourse yc " +
                "order by sp.date desc, u.idBooster")
                .getResultList();
    }

    public List<SpecificPrestation> getNotOrderedSpecificPrestationsByUser(User user) {

        return Prestation.em().createQuery("select sp from SpecificPrestation sp " +
                "join sp.yearCourse yc " +
                "where sp.jobOrder = null and sp.user = :user")
                .setParameter("user", user)
                .getResultList();
    }

    public List<SpecificPrestation> getNotOrderedSpecificPrestationsByUserAndIds(User user, List<Long> ids) {

        if (null == ids || ids.isEmpty()) {
            return new ArrayList<SpecificPrestation>();
        }

        return Prestation.em().createQuery("select sp from SpecificPrestation sp " +
                "join sp.yearCourse yc " +
                "where sp.jobOrder = null and sp.user = :user and sp.id in (:ids)")
                .setParameter("user", user)
                .setParameter("ids", ids)
                .getResultList();
    }
}
