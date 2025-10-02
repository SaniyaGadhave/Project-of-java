import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ContactBookGUI {
    private ContactManager manager;
    private DefaultListModel<String> listModel;
    private JFrame frame;
    private JList<String> contactList;

    public ContactBookGUI(String storageFile) {
        manager = new ContactManager(storageFile);
        createAndShow();
    }

    private void createAndShow() {
        frame = new JFrame("Contact Book GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        JPanel left = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);
        refreshList();
        left.add(new JScrollPane(contactList), BorderLayout.CENTER);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        JTextField nameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField addrField = new JTextField(20);
        JButton addBtn = new JButton("Add Contact");

        right.add(new JLabel("Name:")); right.add(nameField);
        right.add(new JLabel("Phone:")); right.add(phoneField);
        right.add(new JLabel("Email:")); right.add(emailField);
        right.add(new JLabel("Address:")); right.add(addrField);
        right.add(Box.createRigidArea(new Dimension(0,10)));
        right.add(addBtn);

        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String addr = addrField.getText();
            if (name.isBlank()) {
                JOptionPane.showMessageDialog(frame, "Name required");
                return;
            }
            manager.addContact(name, phone, email, addr);
            nameField.setText(""); phoneField.setText(""); emailField.setText(""); addrField.setText("");
            refreshList();
        });

        frame.getContentPane().add(left, BorderLayout.CENTER);
        frame.getContentPane().add(right, BorderLayout.EAST);
        frame.setVisible(true);
    }

    private void refreshList() {
        listModel.clear();
        List<Contact> all = manager.listAll();
        for (Contact c : all) {
            listModel.addElement(String.format("%s - %s (%s)", c.getId(), c.getName(), c.getPhone()));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactBookGUI("contacts.csv"));
    }
}
