import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private final String filename;

    public FileStorage(String filename) {
        this.filename = filename;
    }

    // Save contacts as CSV with quoted fields
    public void save(List<Contact> contacts) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Contact c : contacts) {
                // wrap each field in quotes and double any existing quotes
                String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                    c.getId().replace("\"", "\"\""),
                    c.getName().replace("\"", "\"\""),
                    c.getPhone().replace("\"", "\"\""),
                    c.getEmail().replace("\"", "\"\""),
                    c.getAddress().replace("\"", "\"\"")
                );
                bw.write(line);
                bw.newLine();
            }
        }
    }

    public List<Contact> load() throws IOException {
        List<Contact> list = new ArrayList<>();
        File f = new File(filename);
        if (!f.exists()) {
            return list; // empty list
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                Contact c = Contact.fromCSV(line);
                if (c != null) list.add(c);
            }
        }
        return list;
    }
}
