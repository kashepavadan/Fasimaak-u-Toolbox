import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

//Fasimaak'u Dictionary
public class Dictionary extends JPanel implements ActionListener
{
    JTextField search;
    JTextField fasWord;
    JTextField engWord;
    JTextField partOfSpeech;
    JTextField explanation;
    JLabel current; //displays current word being viewed
    JTextField addDerivs; //
    ArrayList<WordRecord> words; //words being display
    ArrayList<WordRecord> storage; //all words
    int currentRecord;
    String filename="dictionary.txt";
    boolean mustUpdate; //whether a new & unsaved word
    JPanel buttonPanel; //displays derivations as buttons
    JCheckBox searchForFas;
    JCheckBox broadSearch;
    
    public Dictionary()
    {
        setLayout(new GridLayout(1,3));
        JPanel searchPanel=new JPanel();
        JPanel displayPanel=new JPanel();
        buttonPanel=new JPanel();
        searchPanel.setLayout(new GridLayout(2,2));
        displayPanel.setLayout(new GridLayout(3,3));
        search=new JTextField("");
        JButton submit=new JButton("Search the Dictionary!");
        submit.addActionListener(this);
        searchForFas=new JCheckBox("Search for Fas. words instead?", false);
        broadSearch=new JCheckBox("Broad search?", false);
        searchPanel.add(search);
        searchPanel.add(searchForFas);
        searchPanel.add(submit);
        searchPanel.add(broadSearch);
        fasWord=new JTextField("");
        engWord=new JTextField("");
        partOfSpeech=new JTextField("");
        addDerivs=new JTextField("");
        explanation=new JTextField("");
        displayPanel.add(fasWord);
        displayPanel.add(engWord);
        displayPanel.add(partOfSpeech);
        displayPanel.add(explanation);
        displayPanel.add(addDerivs);
        JToolBar bar=new JToolBar();
        bar.setFloatable(false);
        JButton home=makeNavigationButton("Home24","home","Return to Full List");
        bar.add(home);
        JButton addWord=makeNavigationButton("create_new_document","new","New Word");
        bar.add(addWord);
        current=new JLabel("");
        bar.add(current);
        JButton previousWord=makeNavigationButton("Back24","back","Previous Word");
        bar.add(previousWord);
        JButton saveWord=makeNavigationButton("Down24","enter","Enter");
        bar.add(saveWord);
        JButton nextWord=makeNavigationButton("Forward24","next","Next Word");
        bar.add(nextWord);
        JButton deleteWord=makeNavigationButton("Delete24","delete","Delete Word");
        bar.add(deleteWord);
        displayPanel.add(bar);
        add(searchPanel);
        add(displayPanel);
        super.add(buttonPanel);
        open();
    }
    
    //read from file
    public void open()
    {
        String line;
        BufferedReader in;
        try
        {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            storage=new ArrayList<WordRecord>();
            while(true)
            {
                line = in.readLine ();
                if(line==null)
                    break;
                WordRecord tempWord=new WordRecord("","","","");
                tempWord.setFasWord(line.substring(0,line.indexOf("¶")));
                line=line.substring(line.indexOf("¶")+1);
                tempWord.setEngWord(line.substring(0,line.indexOf("¶")));
                line=line.substring(line.indexOf("¶")+1);
                tempWord.setPartOfSpeech(line.substring(0,line.indexOf("¶")));
                line=line.substring(line.indexOf("¶")+1);
                tempWord.setExplanation(line.substring(0,line.indexOf("¶")));
                if(line.indexOf("¶") < line.length()-1)
                {
                    while(line.contains("¶"))
                    {
                        line=line.substring(line.indexOf("¶")+1);
                        if(line.indexOf("¶") ==-1)
                        {
                            break;
                        }
                        tempWord.addDerivation(line.substring(0,line.indexOf("¶")));
                    }
                }
                storage.add(tempWord);
            }
            in.close();
            words=storage;
            changeRecord(0);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (this, "Error from reading: "+filename+"!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //view another word
    public void changeRecord(int recNum)
    {
        if(recNum>=words.size())
        {
            recNum=0;
        }
        if(recNum<0)
        {
            recNum=words.size()-1;
        }
        currentRecord=recNum;
        current.setText((currentRecord+1)+" of "+(words.size()));
        fasWord.setText(words.get(currentRecord).getFasWord());
        engWord.setText(words.get(currentRecord).getEngWord());
        partOfSpeech.setText(words.get(currentRecord).getPartOfSpeech());
        explanation.setText(words.get(currentRecord).getExplanation());
        buttonPanel.removeAll();
        addDerivs.setText("");
        for(int j=0;j<words.get(currentRecord).getDerivations().size();j++)
        {
            String derivText=words.get(currentRecord).getDerivations().get(j);
            String engDerivText="";
            if(addDerivs.getText().equals(""))
            {
                addDerivs.setText(derivText);
            }
            else
            {
                addDerivs.setText(addDerivs.getText()+"/"+derivText);
            }
            for(int k=0;k<storage.size();k++)
            {
                if(derivText.equals(storage.get(k).getFasWord()))
                {
                    engDerivText=storage.get(k).getEngWord();
                    break;
                }
            }
            JButton middleButt=new JButton(derivText+": "+engDerivText);
            middleButt.addActionListener(this);
            buttonPanel.add(middleButt);
        }
        revalidate();
        repaint();
        mustUpdate=false;
    }
    
    //saves info
    public void save()
    {
        PrintWriter out;
        try
        {
          //if updating existing word, save all
            if(!mustUpdate)
            {
                out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF8"));
                for(int i=0;i<storage.size();i++)
                {
                    WordRecord tempWord=storage.get(i);
                    out.print(tempWord.getFasWord()+"¶"+tempWord.getEngWord()+"¶"+tempWord.getPartOfSpeech()+"¶"+tempWord.getExplanation()+"¶");
                    String derivs="";
                    for(int j=0;j<tempWord.getDerivations().size();j++)
                    {
                        derivs=derivs+tempWord.getDerivations().get(j)+"¶";
                    }
                    out.println(derivs);
                }
            }
            else //if new word, append to file
            {
                out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename, true), "UTF8"));
                WordRecord tempWord=words.get(words.size()-1);
                out.print(tempWord.getFasWord()+"¶"+tempWord.getEngWord()+"¶"+tempWord.getPartOfSpeech()+"¶"+tempWord.getExplanation()+"¶");
                String derivs="";
                for(int j=0;j<tempWord.getDerivations().size();j++)
                {
                    derivs=derivs+tempWord.getDerivations().get(j)+"¶";
                }
                out.println(derivs);
            }
            out.close();
            mustUpdate=false;
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error from writing to: "+filename+"!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void actionPerformed(ActionEvent ae)
    {
      //word deletion
        if (ae.getActionCommand ().equals ("delete"))
        {
            int result=JOptionPane.showConfirmDialog(this, "Are you sure you wish to delete this word and all of its derived terms?", "ERROR", JOptionPane.ERROR_MESSAGE);
            if(result==JOptionPane.YES_OPTION)
            {
                String deletedFas=words.get(currentRecord).getFasWord();
                words.remove(currentRecord);
                for(int i=0;i<storage.size();i++)
                {
                        ArrayList<String> ders=storage.get(i).getDerivations();
                        for(int j=0;j<ders.size();j++)
                        {
                            if(deletedFas.equals(ders.get(j)))
                            {
                                storage.remove(i);
                            }
                        }
                }
                words=storage;
                changeRecord(0);
                save();
            }
        }
        //updating a word
        if (ae.getActionCommand ().equals ("enter"))
        {
            String fasTest;
            String engTest;
            String partTest;
            String expTest;
            String derTest;
            try
            {
                fasTest=fasWord.getText();
            }
            catch(NullPointerException e)
            {
                fasTest="";
            }
            try
            {
                engTest=engWord.getText();
            }
            catch(NullPointerException e)
            {
                engTest="";
            }
            try
            {
                partTest=partOfSpeech.getText();
            }
            catch(NullPointerException e)
            {
                partTest="";
            }
            try
            {
                expTest=explanation.getText();
            }
            catch(NullPointerException e)
            {
                expTest="";
            }
            try
            {
                derTest=addDerivs.getText();
            }
            catch(NullPointerException e)
            {
                derTest="";
            }
            int result=-1;
            boolean troo=true;
            if(partTest.equals("")||expTest.equals("")||engTest.equals("")||fasTest.equals(""))
            {
                JOptionPane.showMessageDialog(null, "All basic fields must have data!", "ERROR", JOptionPane.ERROR_MESSAGE);
                troo=false;
            }
            else if(!formatWord(fasTest,true).equals(words.get(currentRecord).getFasWord())) //if FasWord is changed, check if it conflicts with an older one
            {
                for(int n=0;n<storage.size();n++)
                {
                    if(storage.get(n).getFasWord().equals(formatWord(fasTest,true)))
                    {
                        JOptionPane.showMessageDialog(null, "This word already exists!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        result=0;
                        break;
                    }
                }
            }
            if (troo && result==-1) //if all fields entered and no word duplication
            {
                ArrayList<String> testung=new ArrayList<String>(Arrays.asList(formatWord(derTest,true).split("/")));
                WordRecord storTest;
                boolean searchAgain=false;
                
                //if words is being used for a search, make sure to search for the same term again
                if(words.size()<storage.size())
                {
                  searchAgain=true;
                }
                
                //if a new word is being saved
                if(mustUpdate)
                {
                  words.remove(words.size()-1); //remove temporary word in words
                  storage.add(new WordRecord(formatWord(fasTest,true),formatWord(engTest,false),partTest.toUpperCase(),expTest));
                  if(!testung.get(0).equals(""))
                  {
                    storage.get(storage.size()-1).setDerivations(new ArrayList<String>(testung));
                  }
                }
                else //if an existing word is being modfied
                {
                    int indeks=storage.indexOf(words.get(currentRecord));
                    storTest=storage.get(indeks);
                    storTest.setFasWord(formatWord(fasTest,true));
                    storTest.setEngWord(formatWord(engTest,false));
                    storTest.setExplanation(expTest);
                    storTest.setPartOfSpeech(partTest.toUpperCase());
                    
                    //checks whether there are derivations to add
                    if(!testung.get(0).equals(""))
                    {
                        storTest.setDerivations(new ArrayList<String>(testung));
                    }
                    else
                    {
                        storTest.setDerivations(new ArrayList<String>());
                    }
                }
                words=storage;
                if(searchAgain)
                {
                  searchDict(formatWord(search.getText().toLowerCase(), searchForFas.isSelected()));
                }
                save();
                changeRecord(currentRecord);
            }
        }
        else if(mustUpdate) //prevent anything other than deletion or saving if a new word is created but not saved
        {
            JOptionPane.showMessageDialog(null, "You must enter data into this record before proceeding!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          //adds an empty new word
            if (ae.getActionCommand ().equals ("new"))
            {
                words=storage;
                words.add(new WordRecord("","","",""));
                changeRecord(words.size()-1);
                mustUpdate=true;
            }
            
            //views next word
            if (ae.getActionCommand ().equals ("next"))
            {
                changeRecord(currentRecord+1);
            }
            
            //views previous word
            if (ae.getActionCommand ().equals ("back"))
            {
                changeRecord(currentRecord-1);
            }
            
            //navigates to derivation word if a derivation button is clicked
            String derivButts=ae.getActionCommand ();
            if(derivButts.contains (":"))
            {
                for(int i=0;i<storage.size();i++)
                {
                    if(storage.get(i).getFasWord().equals(derivButts.substring(0,derivButts.indexOf(":"))))
                    {
                        words=storage;
                        changeRecord(i);
                        break;
                    }
                }
            }
            
            //searches dictionary
            if (ae.getActionCommand ().equals ("Search the Dictionary!"))
            {
                searchDict(formatWord(search.getText().toLowerCase(), searchForFas.isSelected()));
                changeRecord(0);
            }
            
            //returns to list of all words
            if (ae.getActionCommand ().equals ("home"))
            {
                words=storage;
                changeRecord(0);
            }
        }
    }
    
    //searches dictionary
    public void searchDict(String term)
    {
      term=term.toLowerCase();
        words=new ArrayList<WordRecord>();
        for(int i=0;i<storage.size();i++)
        {
          //english search
            if(!searchForFas.isSelected())
            {
              ArrayList<String> engwords=new ArrayList<String>(Arrays.asList(storage.get(i).getEngWord().split("/"))); //checks each of multiple english meanings
              if(engwords.contains(term) || (broadSearch.isSelected() && storage.get(i).getExplanation().toLowerCase().contains(term)))
              {
                words.add(storage.get(i));
              }
            }
            else //fasimaak'u search
            {
              if(term.equals(storage.get(i).getFasWord()) || (broadSearch.isSelected() && storage.get(i).getFasWord().contains(term)))
              {
                words.add(storage.get(i));
              }
            }
        }
        if(words.size()==0)
        {
            JOptionPane.showMessageDialog(null, "No results for your search!", "ERROR", JOptionPane.ERROR_MESSAGE);
            words=storage;
        }
    }
    
    //formats words for consistency
    public String formatWord(String input, boolean isFas)
    {
        input=input.toLowerCase();
        if(isFas)
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
        }
        return input;
    }
    
    //makes a button with a gif image
    protected JButton makeNavigationButton (String imageName, String actionCommand, String toolTipText)
    {
        Image imageGIF = Toolkit.getDefaultToolkit ().getImage (imageName + ".gif");
        JButton button = new JButton ();
        button.setActionCommand (actionCommand);
        button.setToolTipText (toolTipText);
        button.addActionListener (this);
        button.setIcon (new ImageIcon (imageGIF, toolTipText));
        return button;
    }
}