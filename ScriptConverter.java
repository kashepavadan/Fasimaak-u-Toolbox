import javax.swing.*;
import java.awt.event.*;

//Converts from ASCII to special Latin script
public class ScriptConverter extends JPanel implements ActionListener
{
    JTextField conv;
    JTextField out;
    
    public ScriptConverter()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        conv=new JTextField("Text to be converted goes here.");
        JButton submit=new JButton("Convert!");
        submit.addActionListener(this);
        out=new JTextField("Converted text will be here.");
        add(conv);
        add(submit);
        add(out);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getActionCommand ().equals ("Convert!"))
        {
            try
            {
                out.setText(new String(convertText(conv.getText())));
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Unsupported encoding!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public String convertText(String input)
    {
        input=input.replaceAll("nh","\u00F1");
        input=input.replaceAll("t'","\u0163");
        input=input.replaceAll("k'","\u0137");
        input=input.replaceAll("th","\u00F0");
        input=input.replaceAll("sh","\u0161");
        input=input.replaceAll("ii","\u012B");
        input=input.replaceAll("aa","\u0101");
        input=input.replaceAll("ee","\u0113");
        input=input.replaceAll("uu","\u016B");
        input=input.replaceAll("NH","\u00D1");
        input=input.replaceAll("T'","\u0162");
        input=input.replaceAll("K'","\u0136");
        input=input.replaceAll("TH","\u00D0");
        input=input.replaceAll("SH","\u0160");
        input=input.replaceAll("II","\u012A");
        input=input.replaceAll("AA","\u0100");
        input=input.replaceAll("EE","\u0112");
        input=input.replaceAll("UU","\u016A");
        input=input.replaceAll("^\"","«");
        input=input.replaceAll(" \""," «");
        input=input.replaceAll("\"$","»");
        input=input.replaceAll("\" ","» ");
        return input;
    }
}