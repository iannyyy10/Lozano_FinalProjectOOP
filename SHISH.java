import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class Task {
    String name;
    String priority;
    String deadline;

    Task(String name, String priority, String deadline) {
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return name + " (Priority: " + priority + ", Deadline: " + deadline + ")";
    }
}

public class SHISH {
    private JFrame frame;
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private Timer reminderTimer;

    public SHISH() {
        frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField taskNameField = new JTextField();
        JTextField priorityField = new JTextField();
        JTextField deadlineField = new JTextField();

        inputPanel.add(new JLabel("Task Name:"));
        inputPanel.add(taskNameField);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityField);
        inputPanel.add(new JLabel("Deadline:"));
        inputPanel.add(deadlineField);

        JButton addButton = new JButton("Add Task");
        JButton deleteButton = new JButton("Delete Task");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = taskNameField.getText();
                String priority = priorityField.getText();
                String deadline = deadlineField.getText();

                if (!taskName.isEmpty() && !priority.isEmpty() && !deadline.isEmpty()) {
                    taskListModel.addElement(new Task(taskName, priority, deadline));
                    taskNameField.setText("");
                    priorityField.setText("");
                    deadlineField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a task to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        initializeReminder();

        frame.setVisible(true);
    }

    private void initializeReminder() {
        reminderTimer = new Timer(true);
        reminderTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!taskListModel.isEmpty()) {
                    Task task = taskListModel.get(0);
                    JOptionPane.showMessageDialog(frame, "Reminder: " + task.toString(), "Task Reminder", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }, 0, 60000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SHISH());
    }
}
