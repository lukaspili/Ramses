package notifiers;

import models.user.User;
import play.i18n.Messages;
import play.mvc.Mailer;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Mails extends Mailer {

    private static final String FROM = "Eunomie SUPINFO Paris <eunomie@supinfo.com>";

    public static void register(User user) {
        setSubject(Messages.get("mail.register.subject"));
        addRecipient(user.getEmail());
        setFrom(FROM);
        send(user);
    }

    public static void forgotPasswordRequest(User user) {
        setSubject(Messages.get("mail.resetPasswordRequest.subject"));
        addRecipient(user.getEmail());
        setFrom(FROM);
        send(user);
    }
}
