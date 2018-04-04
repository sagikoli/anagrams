/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {
    private List<String> wordlist=new ArrayList<>();
    private HashSet<String> wordset=new HashSet<>();
    private HashMap<String,ArrayList<String>> letterstowords=new HashMap<>();
    private HashMap<Integer,ArrayList> sizetowords=new HashMap<>();
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private int word_length=DEFAULT_WORD_LENGTH;
    public String sortLetters(String input)
    {
        //  String result=new String();
        char temp[]=input.toCharArray();
        Arrays.sort(temp);
        return String.valueOf(temp);
    }

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordlist.add(word);
            wordset.add(word);
            int len=word.length();
            if(sizetowords.containsKey(len))
            {
                ArrayList<String> temparr=new ArrayList<>();
                temparr=sizetowords.get(len);
                temparr.add(word);
                sizetowords.put(len,temparr);
            }
            else
            {
                ArrayList<String> temparr=new ArrayList<>();
                temparr.add(word);
                sizetowords.put(len,temparr);
            }
            String temp=sortLetters(word);
            if(letterstowords.containsKey(temp))
            {
                ArrayList<String> temparr=letterstowords.get(temp);
                temparr.add(word);
                letterstowords.put(temp,temparr);
            }
            else
            {
                ArrayList<String> temparr=new ArrayList<>();
                temparr.add(word);
                letterstowords.put(temp,temparr);
            }
        }
    }

    public boolean isGoodWord(String word, String base)
    {
        if (!wordset.contains(word))
        return false;
        if(word.contains(base))
            return false;
        return true;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String tmp=sortLetters(targetWord);
        for(String temp : wordlist)
        {
            if(tmp.equals(sortLetters(temp)))
                result.add(temp);
        }
      /*  if (!letterstowords.containsKey(tmp))
            letterstowords.put(tmp,result);*/
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String tmp=sortLetters(word);
        ArrayList<String> chr=new ArrayList<>();
        for(int i=97;i<123;i++)
        {
            chr.add(String.valueOf((char)i));
        }
        for(String ch : chr)
        {
            String t="";
           t=tmp;
            t+=ch;
           t=sortLetters(t);
            /*for(String temp : wordlist)
            {
                if(t.equals(sortLetters(temp)))
                    result.add(temp);
            }*/
          //  result.addAll(letterstowords.get(t));
            Log.i("Shashi", String.valueOf(letterstowords.get(t)));
            if (letterstowords.get(t)==null)
                continue;;
            Iterator it=letterstowords.get(t).iterator();

            while (it.hasNext())
            {
                result.add((String) it.next());
            }

        }
        return result;
    }

    public String pickGoodStarterWord() {
        String s="stop";
        while(true) {
            ArrayList<String> temp=sizetowords.get(word_length);
            s = temp.get((random.nextInt(temp.size())));
            List<String> arr=getAnagramsWithOneMoreLetter(s);
                if (arr.size()>=MIN_NUM_ANAGRAMS)
                    break;

        }
        word_length++;
            return s;
    }
}
