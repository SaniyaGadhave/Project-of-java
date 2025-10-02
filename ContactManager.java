import java.io.IOException;
import java.util.*;

public class ContactManager {
    private final Map<String, Contact> contactsById = new HashMap<>();
    private final FileStorage storage;

    public ContactManager(String storageFile) {
        this.storage = new FileStorage(storageFile);
        try {
            List<Contact> loaded = storage.load();
            for (Contact c : loaded) contactsById.put(c.getId(), c);
        } catch (IOException e) {
            System.out.println("Warning: could not load contacts (" + e.getMessage() + "). Starting fresh.");
        }
    }

    public Contact addContact(String name, String phone, String email, String address) {
        Contact c = new Contact(name, phone, email, address);
        contactsById.put(c.getId(), c);
        save();
        return c;
    }

    public boolean deleteContact(String id) {
        if (contactsById.remove(id) != null) {
            save();
            return true;
        }
        return false;
    }

    public boolean editContact(String id, String name, String phone, String email, String address) {
        Contact c = contactsById.get(id);
        if (c == null) return false;
        if (name != null && !name.isBlank()) c.setName(name);
        if (phone != null && !phone.isBlank()) c.setPhone(phone);
        if (email != null && !email.isBlank()) c.setEmail(email);
        if (address != null && !address.isBlank()) c.setAddress(address);
        save();
        return true;
    }

    public List<Contact> listAll() {
        return new ArrayList<>(contactsById.values());
    }

    public List<Contact> searchByName(String query) {
        query = query.toLowerCase();
        List<Contact> res = new ArrayList<>();
        for (Contact c : contactsById.values()) {
            if (c.getName().toLowerCase().contains(query)) res.add(c);
        }
        return res;
    }

    public List<Contact> searchByPhone(String phoneQuery) {
        phoneQuery = phoneQuery.toLowerCase();
        List<Contact> res = new ArrayList<>();
        for (Contact c : contactsById.values()) {
            if (c.getPhone().toLowerCase().contains(phoneQuery)) res.add(c);
        }
        return res;
    }

    private void save() {
        try {
            storage.save(listAll());
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }
}
