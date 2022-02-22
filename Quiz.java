
import java.awt.*; // for action listener
import java.awt.event.*;// for action listener
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.google.gson.*;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.LinkedList;

class Exam {
    String[] Questions;
    String[][] options;
    int[] answers;

    Exam(String examCode) {
        getExam(examCode);
    }

    public void getExam(String param) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:5000/getExam"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString("{\"examCode\":\"" + param + "\"}"))
                    .build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseString = (String) response.body();
            Gson gson = new Gson();
            var exam = gson.fromJson(responseString, Exam.class);
            this.Questions = exam.Questions;
            this.options = exam.options;
            this.answers = exam.answers;
            System.out.println(exam);
        } catch (Exception e) {
            System.err.println("");
            System.exit(1);
        }
    }
}

public class Quiz extends JFrame implements ActionListener {

    Exam exam;
    LinkedList<JButton> buttons = new LinkedList<>();
    LinkedList<JLabel> answer_labels = new LinkedList<>();
    char guess;
    int answer;
    int index;
    int correctGuess;
    int totalQuestion;// Adjusts to Question length
    int results;
    int Seconds = 10;

    JFrame frame = new JFrame();
    JTextField textfield = new JTextField();// this will hold the current text field(this allow to enter single text
    JTextArea textArea = new JTextArea();
    JLabel time_label = new JLabel();
    JLabel seconds_left = new JLabel(); // display for the count down timer
    // -----X-----
    JTextField number_right = new JTextField();
    JTextField percentage = new JTextField();
    Timer timerForQuestion = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) { // for every one second subtract one from the variable second
            Seconds--;
            seconds_left.setText(String.valueOf(Seconds)); // changing our seconds left display
            if (Seconds <= 0) { // if time reaches zero, display the current correct answer
                displayAnswer();
            }
        }
    }); // closing time instantiation

    public Quiz(String param) // constructor
    {
        try {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// to close the window
            frame.setSize(650, 650);
            frame.getContentPane().setBackground(new Color(50, 50, 50));// to change the background color
            frame.setLayout(null); // BECAUSE WE WANT TO ARRANGE THE COMPONENT MANUALLY
            frame.setResizable(false); // if you dont want to resize your window
            
            exam = new Exam(param);

            if(exam.Questions == null ) throw new Exception("exam Not Found");
            
            
            generateButton(exam.options);
            generateLables(exam.options);
            totalQuestion = exam.Questions.length;// Adjusts to Question length
            textfield.setBounds(0, 0, 650, 50);
            textfield.setBackground(new Color(25, 25, 25));
            textfield.setForeground(new Color(25, 255, 0)); // the color in which text is shown
            textfield.setFont(new Font("Ink free", Font.BOLD, 30)); // TAKES THREE ARGS TYPE, FONT TYPE, SIZE
            textfield.setBorder(BorderFactory.createBevelBorder(1)); // border type
            textfield.setHorizontalAlignment(JTextField.CENTER); // our text alligment
            textfield.setEditable(false); // our text field will not be editable
            textArea.setBounds(0, 50, 650, 50);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setBackground(new Color(25, 25, 25));
            textArea.setForeground(new Color(25, 255, 0)); // the color in which text is shown
            textArea.setFont(new Font("MV Boli", Font.BOLD, 25)); // TAKES THREE ARGS TYPE, FONT TYPE, SIZE
            textArea.setBorder(BorderFactory.createBevelBorder(1)); // border type
            textArea.setEditable(false); // our text area will not be editable
            int i = 1;
            for (JButton item : buttons) {
                item.setBounds(0, i * 100, 100, 100);// this decides where its going to be placed
                item.setFont(new Font("MV Boli", Font.BOLD, 35));
                item.setFocusable(false);
                item.addActionListener(this); // this is reference to the current object
                item.setText(Integer.toString(i));
                i++;
            }
    
            int jlableIdx = 1;
            for (JLabel jLabel : answer_labels) {
                jLabel.setBounds(125, jlableIdx * 100, 500, 100);
                jLabel.setBackground(new Color(50, 50, 50));
                jLabel.setForeground(new Color(25, 255, 0)); // Color of the text
                jLabel.setFont(new Font("Mv Boli", Font.PLAIN, 35));
                jlableIdx++;
            }
            seconds_left.setBounds(535, 510, 100, 100);
            seconds_left.setBackground(new Color(25, 25, 25)); // dark black color
            seconds_left.setForeground(Color.white);
            seconds_left.setFont(new Font("Ink Free", Font.BOLD, 60));
            seconds_left.setBorder(BorderFactory.createBevelBorder(1));
            seconds_left.setOpaque(true);
            seconds_left.setHorizontalAlignment(JTextField.CENTER); // SETS THE ALIGNMENT OF THE LABELS CONTENT ALONG X AXIS
            seconds_left.setText(String.valueOf(Seconds)); // WE CONVERTED INTEGER INTO STRING
    
            // FOR TIMER ABOVE SECONDS LEFT
            time_label.setBounds(535, 475, 100, 25);
            time_label.setBackground(new Color(50, 50, 50));
            time_label.setForeground(Color.white);
            time_label.setFont(new Font("MV Boli", Font.PLAIN, 16));
            time_label.setHorizontalAlignment(JTextField.CENTER);
            time_label.setText("TIMER > :D");
    
            // FOR RIGHT NUMBER OF exam.answers at the end of the quiz
            number_right.setBounds(225, 225, 200, 100);
            number_right.setBackground(new Color(25, 25, 25));
            number_right.setForeground(new Color(25, 255, 0));
            number_right.setFont(new Font("Ink Free", Font.BOLD, 50));
            number_right.setBorder(BorderFactory.createBevelBorder(1));
            number_right.setHorizontalAlignment(JTextField.CENTER);
            number_right.setEditable(false); // user can not edit the right numbers
            // for showing percentage at the end of the quiz
            percentage.setBounds(225, 325, 200, 100);
            percentage.setBackground(new Color(25, 25, 25));
            percentage.setForeground(new Color(25, 255, 0));
            percentage.setFont(new Font("Ink Free", Font.BOLD, 50));
            percentage.setBorder(BorderFactory.createBevelBorder(1));
            percentage.setHorizontalAlignment(JTextField.CENTER);
            percentage.setEditable(false);
            frame.add(time_label);
            frame.add(seconds_left);
    
            for (JLabel item : answer_labels) {
                frame.add(item);
            }
    
            for (JButton item : buttons) {
                frame.add(item);
            }
            frame.add(textArea);
            frame.add(textfield);
            frame.setVisible(true); // this must be at last
    
            nextQusetion(); // displaying the first question and then Calling the next question   
        } catch (Exception e) {
            JTextArea  errLable = new JTextArea();
            errLable.setText("Exam Not Found");;
            errLable.setEditable(false);
            errLable.setBounds(200,300,200,300);
            errLable.setLineWrap(true);
            errLable.setWrapStyleWord(true);
            errLable.setBackground(new Color(25, 25, 25));
            errLable.setForeground(new Color(25, 255, 0)); // the color in which text is shown
            errLable.setFont(new Font("MV Boli", Font.BOLD, 25)); // TAKES THREE ARGS TYPE, FONT TYPE, SIZE
            errLable.setBorder(BorderFactory.createBevelBorder(1)); // border type
            errLable.setEditable(false); // our text area will not be editable
            frame.add(errLable);
            frame.setVisible(true); // this must be at last
            Timer pause = new Timer(5000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(1);
                }
            });
            pause.start();
        }
    }

    public void nextQusetion() //// displaying the first question and then Calling the next question
    {
        if (index >= totalQuestion) {
            results();
        } else {
            textfield.setText("Question " + (index + 1)); // For displaying question number
            textArea.setText(exam.Questions[index]); // THIS WILL DISPLAY THE QUESTION BEING ASKED
            int indexans = 0;
            for (JLabel jLabel : answer_labels) {
                jLabel.setText(exam.options[index][indexans]); // options[row][columns]
                indexans++;
            }

            timerForQuestion.start(); // using the start method of our timer oject(timerforQuestion)
        }

    }

    JButton getButton(String text) {
        JButton ret = null;
        for (JButton item : buttons) {
            if (item.getText().equals(text))
                ret = item;
        }
        return ret;
    }

    // this method will be triggered when user clicks the button
    public void actionPerformed(ActionEvent e) { // anything related to button clicking will go into this method
        for (JButton item : buttons) {
            item.setEnabled(false);
        }

        if (e.getSource() == getButton("1")) { // IF USER CLICK BUTTON A
            answer = 0;
            if (answer == exam.answers[index]) { // IF OUR ANSWER IS EQAUL TO ARRAY OF CORRECT exam.answers
                correctGuess++;
            }
        }

        if (e.getSource() == getButton("2")) { // IF USER CLICK BUTTON A
            answer = 1;
            if (answer == exam.answers[index]) { // IF OUR ANSWER IS EQAUL TO ARRAY OF CORRECT exam.answers
                correctGuess++;
            }
        }
        if (e.getSource() == getButton("3")) {
            answer = 2;
            if (answer == exam.answers[index]) {
                correctGuess++;
            }
            if (e.getSource() == getButton("4")) {
                answer = 3;
                if (answer == exam.answers[index]) {
                    correctGuess++;
                }
            }
        }
        displayAnswer(); // displays the correct answer after the click on the option label.

    }

    public void displayAnswer() {

        timerForQuestion.stop(); // stoping the timer after the question being answered
        for (JButton i : buttons) {
            i.setEnabled(false);
        }
        Timer pause = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                for (JLabel item : answer_labels) {
                    item.setForeground(new Color(25, 255, 0));
                }

                answer = ' '; // reseting our answer for our next question
                Seconds = 10; // ressetting the count to 10 after each index (our question)
                seconds_left.setText(String.valueOf(Seconds));

                for (JButton item : buttons) {
                    item.setEnabled(true);
                }
                index++;
                nextQusetion();
            }
        }); // closing time instantiation

        pause.start();
        pause.setRepeats(false);

    }

    public void results() {

        for (JButton item : buttons) {
            frame.remove(item);
        }
        frame.revalidate();
        frame.repaint();
        for (JButton item : buttons) {
            item.setEnabled(true);
            ;
        }

        results = (int) ((correctGuess / (double) totalQuestion) * 100); // storing percentage into result

        textfield.setText("RESULTS!");
        textArea.setText(" ");
        for (JLabel item : answer_labels) {
            item.setText(" ");
        }

        number_right.setText(correctGuess + "/" + totalQuestion);
        percentage.setText(results + "%");

        frame.add(percentage);
        frame.add(number_right);

    }

    void generateButton(String[][] options) {
        for (int i = 0; i < options.length; i++) {
            buttons.add(new JButton());
        }
    }

    void generateLables(String[][] options) {
        for (int i = 0; i < options.length; i++) {
            answer_labels.add(new JLabel());
        }
    }

}
