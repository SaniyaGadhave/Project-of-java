import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String STORAGE_FILE = "contacts.csv";

    public static void main(String[] args) {
        ContactManager manager = new ContactManager(STORAGE_FILE);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Contact Book ===");
            System.out.println("1. Add contact");
            System.out.println("2. Edit contact");
            System.out.println("3. Delete contact");
            System.out.println("4. Search by name");
            System.out.println("5. Search by phone");
            System.out.println("6. List all contacts");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            String opt = sc.nextLine().trim();

            switch (opt) {
                case "1":
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Phone: "); String phone = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Address: "); String addr = sc.nextLine();
                    Contact added = manager.addContact(name, phone, email, addr);
                    System.out.println("Added contact with ID: " + added.getId());
                    break;
                case "2":
                    System.out.print("Enter contact ID to edit: ");
                    String editId = sc.nextLine().trim();
                    System.out.println("Leave field empty to keep current value.");
                    System.out.print("New name: "); String nname = sc.nextLine();
                    System.out.print("New phone: "); String nphone = sc.nextLine();
                    System.out.print("New email: "); String nemail = sc.nextLine();
                    System.out.print("New address: "); String naddr = sc.nextLine();
                    boolean edited = manager.editContact(editId, nname, nphone, nemail, naddr);
                    System.out.println(edited ? "Contact updated." : "Contact not found.");
                    break;
                case "3":
                    System.out.print("Enter contact ID to delete: ");
                    String delId = sc.nextLine().trim();
                    boolean deleted = manager.deleteContact(delId);
                    System.out.println(deleted ? "Contact deleted." : "Contact not found.");
                    break;
                case "4":
                    System.out.print("Enter name to search: ");
                    String q = sc.nextLine();
                    List<Contact> found = manager.searchByName(q);
                    if (found.isEmpty()) System.out.println("No contacts found.");
                    else {
                        for (Contact c : found) System.out.println(c);
                    }
                    break;
                case "5":
                    System.out.print("Enter phone (or part) to search: ");
                    String p = sc.nextLine();
                    List<Contact> foundp = manager.searchByPhone(p);
                    if (foundp.isEmpty()) System.out.println("No contacts found.");
                    else {
                        for (Contact c : foundp) System.out.println(c);
                    }
                    break;
                case "6":
                    List<Contact> all = manager.listAll();
                    if (all.isEmpty()) System.out.println("No contacts yet.");
                    else {
                        for (Contact c : all) System.out.println(c);
                    }
                    break;
                case "7":
                    System.out.println("Goodbye!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
