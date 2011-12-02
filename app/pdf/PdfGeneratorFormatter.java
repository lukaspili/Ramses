package pdf;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class PdfGeneratorFormatter {

    /**
     * Add 0 before if the user id is in 1 -> 9, in case to have id number like 01, 02, 03, etc...
     */
    public static String getFormattedUserId(long userId) {

        String id = null;

        if (userId < 10) {
            id = "0" + userId;
        } else {
            id = String.valueOf(userId);
        }

        return id;
    }

    /**
     * Add 0 before if the order id is in 1 -> 99, in case to have id number like 001, 002, 010, etc...
     */
    public static String getFormattedOrderId(long orderId) {

        String id = null;

        if (orderId < 10) {
            id = "00" + orderId;
        } else if (orderId < 100) {
            id = "0" + orderId;
        } else {
            id = String.valueOf(orderId);
        }

        return id;
    }
}
