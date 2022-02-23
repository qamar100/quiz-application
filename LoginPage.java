import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;  
                                               //here we are implement action listener interface and this interface has only one method actionperformed which we have to define/override in over class(login) otherwise it will not ru     
public class LoginPage extends JFrame implements ActionListener {
    
   JFrame frame = new JFrame();
   JTextField  textField = new JTextField();
   JTextField textField2 = new JTextField();
   JTextField  textField3= new JTextField();

   JLabel label =new JLabel();  //for quiz time heading label
   JLabel nameLabel= new JLabel(); //for name label
   JLabel StudentId = new JLabel();
   JLabel ExamId = new JLabel();

   JButton rulesButton = new JButton();
 //  JButton startQuiz = new JButton();
  
    LoginPage(){  //CONSTRUCTOR
     
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650,650);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(null); //for setting every thing manually
        frame.setResizable(false);
        
      
        label.setBounds(150,0,400,100);
        label.setForeground(new Color(25,255,0)); 
        label.setFont(new Font("Mv Boli",Font.PLAIN,45));
        label.setText("QUIZ TIME!!!");

        nameLabel.setBounds(180,100,400,100);
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(new Font("Mv Boli",Font.PLAIN,20));
        nameLabel.setText("ENTER YOUR NAME");
        //for entering our name
        textField.setBounds(100,180,400,30);
        textField.setForeground(Color.black);
        textField.setFont(new Font("Times New Roman",Font.BOLD,20));
        
        StudentId.setBounds(180,200,400,100);
        StudentId.setForeground(Color.white);
        StudentId.setFont(new Font("Mv Boli",Font.PLAIN,20));
        StudentId.setText("ENTER YOUR STUDENT ID");
        
        textField2.setBounds(100,280,400,30);
        textField2.setForeground(Color.black);
        textField2.setFont(new Font("Times New Roman",Font.BOLD,20));
       
        ExamId.setBounds(180,300,400,100);
        ExamId.setForeground(Color.white);
        ExamId.setFont(new Font("Mv Boli",Font.PLAIN,20));
        ExamId.setText("EXAM ID");
        
        textField3.setBounds(100,380,400,30);
        textField3.setForeground(Color.black);
        textField3.setFont(new Font("Times New Roman",Font.BOLD,20));

  
        rulesButton.setBounds(230,450,120,30);
        rulesButton.setText("RULES");
        rulesButton.setBackground(new Color(30,123,234));
        rulesButton.setForeground(Color.WHITE);
        rulesButton.addActionListener(this);

    /*    startQuiz.setBounds(380,440,120,30);
        startQuiz.setText("START QUIZ");
        startQuiz.setBackground(new Color(30,123,234));
        startQuiz.setForeground(Color.WHITE);
        startQuiz.addActionListener(this); // is button ky click py khuch hona cahea(this is the refrence to the current object)
        */

      //  frame.add(startQuiz);
        frame.add(rulesButton);
        frame.add(textField3);
        frame.add(ExamId); //for displaying exam id
        frame.add(textField2); //for entering student id
        frame.add(StudentId);   
        frame.add(textField); // for entering student name
        frame.add(nameLabel);  // label for displaying name
        frame.add(label);  // label for displaying quiz time
        frame.setVisible(true);

         

    }

  public void  actionPerformed(ActionEvent e){

   
    
    if(e.getSource()==rulesButton){
    
      String Username =textField.getText();  // gettextfuntion returns string so our textfield in which we stored our username will be saved into our username variable  
     String examCode = textField3.getText();
      frame.setVisible(false);  // Closing the our current frame (loginframe) 
      Rules rules = new Rules(Username,examCode); //CALLING A PAREMTERIZED CONSTRUCTOR
      }
  }

}
