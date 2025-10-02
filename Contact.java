import java.util.UUID;

public class Contact {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;

    public Contact(String name, String phone, String email, String address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // Constructor used when loading with known id
    public Contact(String id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }

    // CSV-safe representation (escape commas)
    public String toCSV() {
        return escape(id) + "," + escape(name) + "," + escape(phone) + "," + escape(email) + "," + escape(address);
    }

    public static Contact fromCSV(String csvLine) {
        String[] parts = splitCSVLine(csvLine);
        if (parts.length < 5) return null;
        return new Contact(unescape(parts[0]), unescape(parts[1]), unescape(parts[2]), unescape(parts[3]), unescape(parts[4]));
    }

    private static String escape(String s) {
        if (s == null) s = "";
        return s.replace("\"", "\"\""); // we will wrap fields in quotes when writing
    }

    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace("\"\"", "\"");
    }

    private static String[] splitCSVLine(String line) {
        // Very simple CSV parser for quoted fields. Assumes fields are wrapped in double quotes.
        // e.g. "id","name","phone","email","address"
        java.util.List<String> cols = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (i + 1 < line.length() && line.charAt(i + 1) == '"') { // escaped quote
                    cur.append('"');
                    i++; // skip next
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                cols.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        cols.add(cur.toString());
        return cols.toArray(new String[0]);
    }

    @Override
    public String toString() {
        return String.format("ID: %s\nName: %s\nPhone: %s\nEmail: %s\nAddress: %s\n", id, name, phone, email, address);
    }
}
