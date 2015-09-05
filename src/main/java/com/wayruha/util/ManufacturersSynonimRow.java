package com.wayruha.util;

import java.util.ArrayList;

public class ManufacturersSynonimRow {

    ArrayList<String> wordList=new ArrayList<>();

    public ManufacturersSynonimRow(String ... synonims){
        for (String synonimWord:synonims)
            wordList.add(synonimWord);
    }



    public ArrayList<String> getSynonimsForWord(String word){
        if(wordList.contains(word)){
            ArrayList<String> synonimRow= (ArrayList<String>) wordList.clone();
            synonimRow.remove(word);
            return synonimRow;
        }
        return null;
    }
    public ArrayList<String> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        for (String string:wordList){
            sb.append(string);
            sb.append(", ");
        }
        sb.delete(sb.length()-2,sb.length()-1);
        return sb.toString();

    }
}
