package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.index.UniqueId;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Deliverer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonCategory;
import seedu.address.model.person.Phone;

/**
 * Jackson-friendly version of {@link Deliverer}.
 */
class JsonAdaptedDeliverer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deliverer 's %s field is missing!";

    private final String personCategory;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String location;
    private final List<String> ids = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDeliverer} with the given Deliverer details.
     */
    @JsonCreator
    public JsonAdaptedDeliverer(@JsonProperty("personCategory") String personCategory,
                                @JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                @JsonProperty("email") String email, @JsonProperty("address") String address,
                                @JsonProperty("location") String location,
                                @JsonProperty("ids") List<String> ids) {
        this.personCategory = personCategory;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.location = location;
        if (ids != null) {
            this.ids.addAll(ids);
        }
    }

    /**
     * Converts a given {@code Deliverer} into this class for Jackson use.
     */
    public JsonAdaptedDeliverer(Deliverer source) {
        personCategory = source.getPersonCategory().toString();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        location = source.getLocation().location;
        ids.addAll(source.getOrders().stream()
                .map(UniqueId::getIdToString)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Deliverer  object into the model's {@code Deliverer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Deliverer .
     */
    public Deliverer toModelType() throws IllegalValueException {
        final ArrayList<UniqueId> modelIds = new ArrayList<>();
        for (String id : ids) {
            if (id == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        UniqueId.class.getSimpleName()));
            }
            modelIds.add(new UniqueId(id));
        }

        if (personCategory == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PersonCategory.class.getSimpleName()));
        }
        if (!PersonCategory.isValidPersonCategory(personCategory)) {
            throw new IllegalValueException(PersonCategory.MESSAGE_CONSTRAINTS);
        }
        final PersonCategory modelPersonCategory = PersonCategory.getFromString(personCategory);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        final Location modelLocation = new Location(location);

        return new Deliverer(modelName, modelPhone, modelEmail, modelAddress, modelLocation, modelIds);
    }
}
