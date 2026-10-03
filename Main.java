import abstraction.*;
import implementor.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !"--demo".equals(args[0])) {
            System.out.println("Usage: java -cp out Main --demo");
            return;
        }

        runDemo();
    }

    private static void runDemo() {
        String reminderMessage = "Submit the assignment by Sunday.";
        String urgentMessage = "The server is unavailable.";

        String reminderEmail = "Email Envelope [Subject: Reminder | Body: "
                + reminderMessage + "]";
        String reminderSms = "SMS [ Reminder: " + reminderMessage + " ]";
        String urgentEmail = "Email Envelope [Subject: Urgent Alert | Body: URGENT: "
                + urgentMessage + "]";
        String urgentSms = "SMS [ Urgent Alert: URGENT: " + urgentMessage + " ]";
        String reminderPush = "Push Notification Envelope [ App Title: Reminder | Payload: "
                + reminderMessage + " ]";
        String urgentPush = "Push Notification Envelope [ App Title: Urgent Alert | Payload: URGENT: "
                + urgentMessage + " ]";

        int passed = 0;
        passed += checkResult("T1", new Reminder("reminder-1", new EmailChannel(),
                reminderMessage), reminderEmail);
        passed += checkResult("T2", new Reminder("reminder-1", new SMSChannel(),
                reminderMessage), reminderSms);
        passed += checkResult("T3", new UrgentAlert("alert-1", new EmailChannel(),
                urgentMessage), urgentEmail);
        passed += checkResult("T4", new UrgentAlert("alert-1", new SMSChannel(),
                urgentMessage), urgentSms);
        passed += checkRuntimeSwitch(reminderMessage, reminderEmail, reminderSms);
        passed += checkResult("T6", new Reminder("reminder-1", new PushChannel(),
                reminderMessage), reminderPush);
        passed += checkResult("T7", new UrgentAlert("alert-1", new PushChannel(),
                urgentMessage), urgentPush);

        System.out.println("SUMMARY: " + passed + "/7 PASS");
        if (passed != 7) {
            System.exit(1);
        }
    }

    private static int checkResult(String testId, Notification notification, String expected) {
        String actual = notification.execute();
        String participants = notification.getClass().getSimpleName() + " + "
                + notification.getChannel().getClass().getSimpleName();
        return printCheck(testId, expected.equals(actual), participants,
                "result=" + actual, "result=" + expected);
    }

    private static int checkRuntimeSwitch(String message, String expectedBefore,
                                          String expectedAfter) {
        Reminder original = new Reminder("reminder-switch", new EmailChannel(), message);
        String originalId = original.getId();
        String originalMessage = original.getMessage();
        String before = original.execute();

        original.setImplementation(new SMSChannel());
        Reminder afterSwitch = original;
        String after = afterSwitch.execute();

        boolean sameObject = original == afterSwitch;
        boolean stateUnchanged = originalId.equals(afterSwitch.getId())
                && originalMessage.equals(afterSwitch.getMessage());
        boolean passed = sameObject && stateUnchanged
                && expectedBefore.equals(before) && expectedAfter.equals(after);

        String actual = "sameObject=" + sameObject + " | stateUnchanged=" + stateUnchanged
                + " | id=" + afterSwitch.getId() + " | message=" + afterSwitch.getMessage()
                + "\n  before=" + before + " | after=" + after;
        String expected = "sameObject=true | stateUnchanged=true | id=" + originalId
                + " | message=" + originalMessage
                + "\n  before=" + expectedBefore + " | after=" + expectedAfter;
        return printCheck("T5", passed, "Reminder + EmailChannel -> SMSChannel", actual, expected);
    }

    private static int printCheck(String testId, boolean passed, String participants,
                                  String actual, String expected) {
        System.out.println(testId + " " + (passed ? "PASS" : "FAIL")
                + " | " + participants + " | " + actual);
        if (!passed) {
            System.out.println("  expected=" + expected);
        }
        return passed ? 1 : 0;
    }
}
