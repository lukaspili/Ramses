package notifiers;

import models.user.User;
import play.i18n.Messages;
import play.mvc.Mailer;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Mails extends Mailer {

    public static void register(User user) {
        setSubject(Messages.get("mail.register.subject"));
        addRecipient(user.getEmail());
        setFrom("Eunomie SUPINFO Paris <eunomie@supinfo.com>");
        send(user);
    }
}
