# Assignment 3 | Bridge Pattern

- **Name:** Zhibek Slambek
- **Group:** SE-2530
- **Topic letter:** B — Notifications
- **Repository URL:** https://github.com/scp-682-joke/sdp-3
- **Base commit hash:** `7b49bf00dbe8f8dcf4316ff85cb050738b18dfb2`

## Role map

| Role | Class or interface | Source path |
| --- | --- | --- |
| Abstraction | `Notification` | [src/src.abstraction/Notification.java](src/abstraction/Notification.java) |
| A1 | `Reminder` | [src/src.abstraction/Reminder.java](src/abstraction/Reminder.java) |
| A2 | `UrgentAlert` | [src/src.abstraction/UrgentAlert.java](src/abstraction/UrgentAlert.java) |
| Implementor | `Channel` | [src/src.implementor/Channel.java](src/implementor/Channel.java) |
| I1 | `EmailChannel` | [src/src.implementor/EmailChannel.java](src/implementor/EmailChannel.java) |
| I2 | `SMSChannel` | [src/src.implementor/SMSChannel.java](src/implementor/SMSChannel.java) |
| I3 | `PushChannel` | [src/src.implementor/PushChannel.java](src/implementor/PushChannel.java) |
| Client | `src.Main` | [src/src.Main.java](src/Main.java) |

The two independent dimensions are notification behavior and delivery channel. `Reminder` preserves a normal message, while `UrgentAlert` prefixes its message with `URGENT: `. Each delegates delivery formatting through `Channel.send(topic, message)`. Email, SMS, and push delivery are simulated locally as returned strings.

## Code review pointers

- **Bridge field:** `private Channel channel` in `src/src.abstraction/Notification.java`. The constructor receives the interface reference and stores it alongside the notification ID.
- **execute():** Declared as `public abstract String execute()` in `Notification`; implemented in `Reminder` and `UrgentAlert`. Both implementations call `getChannel().send(...)` through the interface.
- **setImplementation(...):** `Notification.setImplementation(Channel channel)` replaces the stored channel reference without changing the notification ID or message.
- **T5:** `src.Main.checkRuntimeSwitch(...)` in `src/src.Main.java`. It executes one `Reminder` with `EmailChannel`, calls `setImplementation(new SMSChannel())`, and executes it again. It checks `original == afterSwitch`, compares the original ID and message with their values after switching, and compares both actual results with the expected strings.

## Build and run

Use JDK 17 or newer. Run these commands from the project root, where `sources.txt` is located. No external libraries or interactive input are required.

```sh
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out src.Main --demo
```

## Expected results for T1–T7

The fixed reminder message is `Submit the assignment by Sunday.` The fixed urgent message is `The server is unavailable.` T1 and T2 use the same reminder data; T3 and T4 use the same urgent-alert data. T6 and T7 demonstrate the additional `PushChannel` implementation.

Expected console output:

```text
T1 PASS | Reminder + EmailChannel | result=Email Envelope [Subject: Reminder | Body: Submit the assignment by Sunday.]
T2 PASS | Reminder + SMSChannel | result=SMS [ Reminder: Submit the assignment by Sunday. ]
T3 PASS | UrgentAlert + EmailChannel | result=Email Envelope [Subject: Urgent Alert | Body: URGENT: The server is unavailable.]
T4 PASS | UrgentAlert + SMSChannel | result=SMS [ Urgent Alert: URGENT: The server is unavailable. ]
T5 PASS | Reminder + EmailChannel -> SMSChannel | sameObject=true | stateUnchanged=true | id=reminder-switch | message=Submit the assignment by Sunday.
  before=Email Envelope [Subject: Reminder | Body: Submit the assignment by Sunday.] | after=SMS [ Reminder: Submit the assignment by Sunday. ]
T6 PASS | Reminder + PushChannel | result=Push Notification Envelope [ App Title: Reminder | Payload: Submit the assignment by Sunday. ]
T7 PASS | UrgentAlert + PushChannel | result=Push Notification Envelope [ App Title: Urgent Alert | Payload: URGENT: The server is unavailable. ]
SUMMARY: 7/7 PASS
```

Each PASS or FAIL is calculated from actual execution results. A failed check also prints the expected result. The summary counts passing checks; the program exits with status 1 if any check fails.
