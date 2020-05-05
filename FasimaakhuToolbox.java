import javax.swing.*;
import java.awt.*;
import java.io.*;

//Main class
public class FasimaakhuToolbox extends JFrame
{
    public FasimaakhuToolbox()
    {
        super("Fasim\u0101\u0137u Toolbox");
        setLayout(new GridLayout(0,1));
        setVisible (true);
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        ScriptConverter script=new ScriptConverter();
        Dictionary dict=new Dictionary();
        add(dict);
        add(script);
        add(new JTextArea("HELP\n\nThe fields are from left-right, up-down: Fasim\u0101\u0137u, English (short), Part of Speech, Explanation (full info), Subunit Modification.\nSeparate English meanings, parts of speech, and derivations by /.\nBroad search searches the explanation field and the fas/eng field for substring content. Narrow search only searches fas/eng for full match."));
        pack();
    }
    
    public static void main(String[]args)
    {
        new FasimaakhuToolbox();
    }
}