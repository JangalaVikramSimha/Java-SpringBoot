# Design Notes

## Why ArrayList instead of an array?
Arrays have a fixed size, so we would have to guess how many students, courses or enrollments there will be, or copy data into a bigger array manually. `ArrayList` grows and shrinks automatically and provides `add`, `remove`, `size` and `isEmpty`. This suits a system where records are added at runtime.

## Where static members are used and why
- `IdGenerator` holds static counters (`studentIdCounter`, `courseIdCounter`, ...) and static methods (`getNextStudentId()`, ...). IDs must be unique across all objects, so the counter belongs to the class, not to any single instance.
- `InputValidator` contains only static helper methods because it needs no state.
- `Main` uses static service fields since the console app has one shared set of services.

## Where inheritance is used and what we gained
`Student` and `Trainer` both extend `Person`, which holds `id`, `firstName`, `lastName`, `email`.
- Shared fields and getters/setters are written once.
- Subclasses call `super(...)` in their constructors.
- `getDisplayName()` is overridden in each subclass, which demonstrates polymorphism: code can call `person.getDisplayName()` and get the right text for the actual type.

## Other decisions
- **Separation of concerns:** `entity` (data), `service` (logic), `ui` (input/output), `exception`, `util`.
- **Soft delete:** students are deactivated (`active = false`) instead of removed.
- **Enrollment status** uses an enum (`ACTIVE`, `COMPLETED`, `CANCELLED`) to avoid typos from raw strings.
- **Custom exceptions:** `EntityNotFoundException` and `InvalidInputException` produce friendly messages instead of crashes.
