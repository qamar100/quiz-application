import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Rules extends JFrame implements ActionListener {
  
     JFrame frame= new JFrame(); //instantiating obj of jframe
     JLabel welcomLabel = new JLabel();
     JLabel rules= new JLabel();
     JLabel rules1= new JLabel();
     JLabel rules2 = new JLabel();
     JLabel rules3 = new JLabel();
     JLabel rules4 = new JLabel();
     JButton backButton= new JButton();
     JButton startQuiz = new JButton();
     String examcode = "";

    Rules(String Username,String examcode){  //parametarized constructor, passing our username as a constructor

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750,650);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(null); //for setting every thing manually
        frame.setResizable(false);
        
        welcomLabel.setBounds(80,0,400,100);
        welcomLabel.setForeground(new Color(25,255,0)); 
        welcomLabel.setFont(new Font("Mv Boli",Font.PLAIN,30));
        welcomLabel.setText("WELCOME "+Username);
      
        

        rules.setBounds(0,70,750,100);
        rules.setForeground(Color.red); 
        rules.setFont(new Font("Mv Boli",Font.PLAIN,17));
        rules.setText("1.Your Are Required To Answer All The Questions");
        
        rules1.setBounds(0,120,750,100);
        rules1.setForeground(Color.red); 
        rules1.setFont(new Font("Mv Boli",Font.PLAIN,17));
        rules1.setText("2.You Have 15 Seconds To Answer Each Question");

        rules2.setBounds(0,170,750,100);
        rules2.setForeground(Color.red); 
        rules2.setFont(new Font("Mv Boli",Font.PLAIN,17));
        rules2.setText("3.You Have To Answer Each Question Within Time Otherwise It Will Be Marked As Wrong");

        rules3.setBounds(0,220,750,100);
        rules3.setForeground(Color.red); 
        rules3.setFont(new Font("Mv Boli",Font.PLAIN,17));
        rules3.setText("4.Crying Is Allowd But Please Do So Quietly");
        
        rules4.setBounds(0,270,750,100);
        rules4.setForeground(Color.red); 
        rules4.setFont(new Font("Mv Boli",Font.PLAIN,17));
        rules4.setText("5.May You Know More Than What Jhon Snow Knows, Good Luck!");
        
        backButton.setBounds(200,440,120,30);
        backButton.setText("BACK");
        backButton.setBackground(new Color(30,123,234));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);

      startQuiz.setBounds(350,440,120,30);
      startQuiz.setText("START QUIZ");
      startQuiz.setBackground(new Color(30,123,234));
      startQuiz.setForeground(Color.WHITE);
      startQuiz.addActionListener(this); // is button ky click py khuch hona cahea(this is the refrence to the current object)
       
        frame.add(startQuiz);
        frame.add(backButton);
        frame.add(rules4);
        frame.add(rules3); 
        frame.add(rules2);        
        frame.add(rules1);
        frame.add(rules);
        frame.add(welcomLabel);
        frame.setVisible(true);
        this.examcode = examcode;

    }
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==backButton){
             frame.setVisible(false);  //closing our current frame
             LoginPage loginPage = new LoginPage();
        }
        if(e.getSource()==startQuiz){
            frame.setVisible(false);
            Quiz quiz = new Quiz(examcode);
        }

    }
  
}
