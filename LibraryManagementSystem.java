import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        new LibraryGUI();
    }
}

class LibraryGUI {
    private JFrame frame;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private ArrayList<Book> books;

    public LibraryGUI() {
        books = new ArrayList<>();
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Panel for Book Table
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        String[] columns = {"ID", "Title", "Author", "Genre", "Availability"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for Actions
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(1, 3));
        JButton addButton = new JButton("Add Book");
        JButton deleteButton = new JButton("Delete Book");
        JButton issueButton = new JButton("Issue/Return Book");

        addButton.addActionListener(new AddBookAction());
        deleteButton.addActionListener(new DeleteBookAction());
        issueButton.addActionListener(new IssueReturnBookAction());

        actionPanel.add(addButton);
        actionPanel.add(deleteButton);
        actionPanel.add(issueButton);

        // Adding Panels to Frame
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.isAvailable() ? "Available" : "Issued"
            });
        }
    }

    private class AddBookAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField idField = new JTextField();
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField genreField = new JTextField();

            Object[] fields = {
                    "ID:", idField,
                    "Title:", titleField,
                    "Author:", authorField,
                    "Genre:", genreField
            };

            int option = JOptionPane.showConfirmDialog(frame, fields, "Add New Book", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String genre = genreField.getText();

                    books.add(new Book(id, title, author, genre));
                    refreshTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class DeleteBookAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {
                books.remove(selectedRow);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "No book selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class IssueReturnBookAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {
                Book book = books.get(selectedRow);
                book.setAvailable(!book.isAvailable());
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "No book selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private boolean available;

    public Book(int id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}