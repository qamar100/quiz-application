
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

// import com.google.gson.Gson;

class Exam {
    String[] Questions;
    String[][] options;
    int[] answers;
}

public class Quiz extends JFrame implements ActionListener {

    Exam exam = new Exam();

    LinkedList<JButton> buttons  =  new LinkedList<>();
    LinkedList<JLabel> answer_labels = new LinkedList<>();
    char guess;
    int answer;
    int index;
    int correctGuess;
    int totalQuestion;// Adjusts to Question length
    int results;
    int Seconds = 10;

    // Initializing GUI components
    JFrame frame = new JFrame();
    // text field displays text or image which a user can directly change or edit
    JTextField textfield = new JTextField();// this will hold the current text field(this allow to enter single text
                                            // line)
    JTextArea textArea = new JTextArea(); // to hold the current question(this allows entering multiple line)

    // CREATING LABELS TO HOLD ALL OF THE DIFFERENT exam.answers
    // Label displays text or image which a user cannot directly change or edit
    // JLabel answer_labelA = new JLabel();
    // JLabel answer_labelB = new JLabel();
    // JLabel answer_labelC = new JLabel();
    // JLabel answer_labelD = new JLabel();

    // CREATING LABELS FOR TIMERS
    JLabel time_label = new JLabel();
    JLabel seconds_left = new JLabel(); // display for the count down timer
    // -----X-----
    JTextField number_right = new JTextField();
    JTextField percentage = new JTextField();

    // ABOVE ALL ARE ACTING AS GLOBAL VARIABLES SO WE CAN ACCESS THESE ANYWHERE
    // WITHIN OUR PROGRAM

    // when the timer reaches 1 seconds we gonna
    // performed wheter is in this action performed
    // method
    Timer timerForQuestion = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) { // for every one second subtract one from the variable second
            Seconds--;
            seconds_left.setText(String.valueOf(Seconds)); // changing our seconds left display
            if (Seconds <= 0) { // if time reaches zero, display the current correct answer
                displayAnswer();
            }
        }
    }); // closing time instantiation





    public void getExam(String param) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:5000/getExam"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString("{\"examCode\":\""+param+"\"}"))
                    .build();
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseString = (String) response.body();
            Gson gson = new Gson();  
            exam = gson.fromJson(responseString, Exam.class);
            System.out.println(exam);
        } catch (Exception e) {
            System.err.println("");
        }
    }

    public Quiz(String param) // constructor
    {
        getExam(param);
        generateButton(exam.options);
        generateLables(exam.options);
        totalQuestion = exam.Questions.length;// Adjusts to Question length
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// to close the window
        frame.setSize(650, 650);
        frame.getContentPane().setBackground(new Color(50, 50, 50));// to change the background color
        frame.setLayout(null); // BECAUSE WE WANT TO ARRANGE THE COMPONENT MANUALLY
        frame.setResizable(false); // if you dont want to resize your window

        // (x cor, y cor,length,heigth ) //determines where the text field is gonna
        // be placed as well as the heigth and the width
        textfield.setBounds(0, 0, 650, 50);
        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0)); // the color in which text is shown
        textfield.setFont(new Font("Ink free", Font.BOLD, 30)); // TAKES THREE ARGS TYPE, FONT TYPE, SIZE
        textfield.setBorder(BorderFactory.createBevelBorder(1)); // border type
        textfield.setHorizontalAlignment(JTextField.CENTER); // our text alligment
        textfield.setEditable(false); // our text field will not be editable
        // textfield.setText("testing");

        // FOR ACTUAL QUESTION(TEXT AREA)
        // (x cor, y cor,length,heigth ) //determines where the text field is gonna
        // be placed as well as the heigth and the width
        textArea.setBounds(0, 50, 650, 50);
        // IN CASE IF TEXT GOES OF THE SCREEN OR TEXT AREA ITS GOING TO AROUND TO
        // NEXTLINE
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(25, 25, 25));
        textArea.setForeground(new Color(25, 255, 0)); // the color in which text is shown
        textArea.setFont(new Font("MV Boli", Font.BOLD, 25)); // TAKES THREE ARGS TYPE, FONT TYPE, SIZE
        textArea.setBorder(BorderFactory.createBevelBorder(1)); // border type
        textArea.setEditable(false); // our text area will not be editable
        // textArea.setText("exam.Questions");

        // FOR BUTTONS
        int i = 1;
        
        for (JButton item : buttons) {
            item.setBounds(0, i*100, 100, 100);// this decides where its going to be placed
            item.setFont(new Font("MV Boli", Font.BOLD, 35));
            item.setFocusable(false);
            item.addActionListener(this); // this is reference to the current object
            item.setText(Integer.toString(i));
            i++;
        }


        // buttonA.setBounds(0, 100, 100, 100);// this decides where its going to be placed
        // buttonA.setFont(new Font("MV Boli", Font.BOLD, 35));
        // // FOCUSES THE TEXT COMPONENT WITHIN OUR BUTTON THIS IS TRUE BY DEFAULT IF WE
        // // DONT WANT IT THAN WE SET IT FALSE
        // buttonA.setFocusable(false);
        // buttonA.addActionListener(this); // this is reference to the current object
        // buttonA.setText("A");

        // buttonB.setBounds(0, 200, 100, 100);
        // buttonB.setFont(new Font("MV Boli", Font.BOLD, 35));
        // buttonB.setFocusable(false);
        // buttonB.addActionListener(this);
        // buttonB.setText("B");

        // buttonC.setBounds(0, 300, 100, 100);
        // buttonC.setFont(new Font("MV Boli", Font.BOLD, 35));
        // buttonC.setFocusable(false);
        // buttonC.addActionListener(this);
        // buttonC.setText("C");

        // buttonD.setBounds(0, 400, 100, 100);
        // buttonD.setFont(new Font("MV Boli", Font.BOLD, 35));
        // buttonD.setFocusable(false);
        // buttonD.addActionListener(this);
        // buttonD.setText("D");


        int jlableIdx=1;
        for (JLabel jLabel : answer_labels) {
            jLabel.setBounds(125, jlableIdx*100, 500, 100);
            jLabel.setBackground(new Color(50, 50, 50));
            jLabel.setForeground(new Color(25, 255, 0)); // Color of the text
            jLabel.setFont(new Font("Mv Boli", Font.PLAIN, 35));
            jlableIdx++;
        }

        // FOR ANSWER LABEL(a component for placing text in the container)
        // answer_labelA.setBounds(125, 100, 500, 100);
        // // Color.grey for default color for your personalized color
        // // use(new Color(R,G,B))
        // answer_labelA.setBackground(new Color(50, 50, 50));
        // answer_labelA.setForeground(new Color(25, 255, 0)); // Color of the text
        // answer_labelA.setFont(new Font("Mv Boli", Font.PLAIN, 35));
        // answer_labelA.setText("TESTING LABEL A");

        // answer_labelB.setBounds(125, 200, 500, 100); // Move y corodinates by 100
        // answer_labelB.setBackground(new Color(50, 50, 50));
        // answer_labelB.setForeground(new Color(25, 255, 0));
        // answer_labelB.setFont(new Font("Mv Boli", Font.PLAIN, 35));
        // // answer_labelB.setText("TESTING LABEL B");

        // answer_labelC.setBounds(125, 300, 500, 100);
        // answer_labelC.setBackground(new Color(50, 50, 50));
        // answer_labelC.setForeground(new Color(25, 255, 0));
        // answer_labelC.setFont(new Font("Mv Boli", Font.PLAIN, 35));
        // // answer_labelC.setText("TESTING LABEL C");

        // answer_labelD.setBounds(125, 400, 500, 100);
        // answer_labelD.setBackground(new Color(50, 50, 50));
        // answer_labelD.setForeground(new Color(25, 255, 0));
        // answer_labelD.setFont(new Font("Mv Boli", Font.PLAIN, 35));
        // answer_labelD.setText("TESTING LABEL D");

        // label for our count down timer
        seconds_left.setBounds(535, 510, 100, 100);
        seconds_left.setBackground(new Color(25, 25, 25)); // dark black color
        seconds_left.setForeground(Color.white);
        seconds_left.setFont(new Font("Ink Free", Font.BOLD, 60));
        seconds_left.setBorder(BorderFactory.createBevelBorder(1)); // setting border from border factory
                                                                    // createbevelborder is our border type and 1 is
                                                                    // bevelborder.Raised and 0 is bevelborder.LOWER
        seconds_left.setOpaque(true); // not being able to see throug
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

        // frame.add(number_right);
        // frame.add(percentage);
        frame.add(time_label);
        frame.add(seconds_left);
        // frame.add(answer_labelA);
        // frame.add(answer_labelB);
        // frame.add(answer_labelC);
        // frame.add(answer_labelD);
        for (JLabel item : answer_labels) {
            frame.add(item);
        }
        // frame.add(buttonA);
        // frame.add(buttonB);
        // frame.add(buttonC);
        // frame.add(buttonD);
        for (JButton item : buttons) {
            frame.add(item);
        }
        frame.add(textArea);
        frame.add(textfield);
        frame.setVisible(true); // this must be at last

        nextQusetion(); // displaying the first question and then Calling the next question

    }

    public void addQuestion() // desing it yourself
    {

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

    JButton getButton(String text){
        JButton ret = null;
        for (JButton item : buttons) {
            if(item.getText().equals(text)) ret = item; 
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
        
        

        // if (exam.answers[index] != 0) { 
        //     answer_labelA.setForeground(new Color(255, 0, 0));
        // }

        // if (exam.answers[index] != 1) {
        //     answer_labelB.setForeground(new Color(255, 0, 0));
        // }

        // if (exam.answers[index] != 2) {
        //     answer_labelC.setForeground(new Color(255, 0, 0));
        // }

        // if (exam.answers[index] != 3) {
        //     answer_labelD.setForeground(new Color(255, 0, 0));
        // }

        // now adding a 2 second delay after changing color into red changing it back to
        // green

        Timer pause = new Timer(1000, new ActionListener() { // when the timer reaches 2 seconds we gonna performed
                                                             // wheter is in this action performed method
            // @Override ??///(Declaring a method in sub class which is already present in
            // parent class is known as method overriding. Overriding is done so that a
            // child class can give its own implementation to a method which is already
            // provided by the parent class)
            // action performed hamrari action listener ki calss me already hy we are using
            // this agian for custamized implemntation
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
                index++; // increasing the index by one so that we can go to the next question
                nextQusetion();
            }
        }); // closing time instantiation

        pause.start();
        pause.setRepeats(false); // (our timer) will only execute once (what ever is in our action performed
                                 // method)

    } // display answer method closed

    public void results() {

        for (JButton item : buttons) {
            frame.remove(item);
        }
        frame.revalidate();
        frame.repaint();
        for (JButton item : buttons) {
            item.setEnabled(true);;
        }

        results = (int) ((correctGuess / (double) totalQuestion) * 100); // storing percentage into result

        textfield.setText("RESULTS!"); // changing our question number heading to results
        textArea.setText(" "); // removing exam.Questions since the quiz ended
        for (JLabel item : answer_labels) {
            item.setText(" ");
        }

        number_right.setText(correctGuess + "/" + totalQuestion);
        percentage.setText(results + "%");

        frame.add(percentage);
        frame.add(number_right);

    }

    void generateButton(String[][] options){
        // buttons = new JButton[options.length];
        for (int i = 0; i<options.length; i++) {
            buttons.add(new JButton()); // FOUR BUTTONS FOuR CHOICES
        }
    }
    
    void generateLables(String[][] options){
        // buttons = new JButton[options.length];
        for (int i = 0; i<options.length; i++) {
            answer_labels.add(new JLabel()); // FOUR BUTTONS FOuR CHOICES
        }
    }



}
