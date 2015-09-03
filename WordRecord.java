import java.util.*;

//Stores a word and related info
public class WordRecord
{
    private String fasWord;
    private String engWord;
    private String partOfSpeech;
    private String explanation;
    private ArrayList<String> derivations;
    
    public WordRecord (String fasWord, String engWord, String partOfSpeech, String explanation, ArrayList<String> derivations)
    {
        setFasWord(fasWord);
        setEngWord(engWord);
        setPartOfSpeech(partOfSpeech);
        setExplanation(explanation);
        setDerivations(derivations);
    }
    
    public WordRecord (String fasWord, String engWord, String partOfSpeech, String explanation)
    {
        setFasWord(fasWord);
        setEngWord(engWord);
        setPartOfSpeech(partOfSpeech);
        setExplanation(explanation);
        derivations=new ArrayList<String>();
    }

    public void setFasWord (String newFasWord)
    {
        fasWord = newFasWord;
    }
    
    public void setEngWord (String newEngWord)
    {
        engWord = newEngWord;
    }

    public void setPartOfSpeech (String newPartOfSpeech)
    {        
        partOfSpeech = newPartOfSpeech;
    }

    public void setExplanation (String newExplanation)
    {
        explanation = newExplanation;
    }
    
    public void setDerivations (ArrayList<String> newDerivations)
    {
        derivations = newDerivations;
    }
    
    public void addDerivation (String newDerivation)
    {
        derivations.add(newDerivation);
    }
    
    public void removeDerivation (String newDerivation)
    {
        derivations.remove(newDerivation);
    }

    public String getFasWord ( )
    {
        return fasWord;
    }

    public String getEngWord ( )
    {
        return engWord;
    }

    public String getPartOfSpeech ( )
    {
        return partOfSpeech;
    }

    public String getExplanation ( )
    {
        return explanation;
    }
    
    public ArrayList<String> getDerivations ( )
    {
        return derivations;
    }
}