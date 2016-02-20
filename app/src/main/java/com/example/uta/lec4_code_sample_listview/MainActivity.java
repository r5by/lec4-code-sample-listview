package com.example.uta.lec4_code_sample_listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * CSE5324 Spring 2016
 * Code Samples for Android Training Session Lecture 4: ListView
 * <p/>
 * (Please check blackboard for the lecture video by Stanford)
 */
public class MainActivity extends AppCompatActivity {

    /* Dictionary that has key-value pair of: word -> definition */
    HashMap<String, String> dictionary = new HashMap<>();

    /* Keep reference to the selected words and their definitions */
    ArrayList<String> selectedWordsList = new ArrayList<>();
    ArrayList<String> definitions = new ArrayList<>();

    /* Keep references to widgets that get interacted with  */
    TextView textViewGuessWord;
    ListView listViewWordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Auto generated code section */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewGuessWord = (TextView) findViewById(R.id.textview_guess_word);
        listViewWordsList = (ListView) findViewById(R.id.listview_word_definition_list);

        initNewGuessRound();
        populateListView();

    }

    /**
     * Populate the listview with the definitions of 5 random words, set its adapter and listener
     */
    private void populateListView() {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.definitionlist_items, R.id.list_item_definition, definitions);

        listViewWordsList.setAdapter(adapter);
        listViewWordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (definitions.get(position).equals(dictionary.get(textViewGuessWord.getText().toString()))) {
                    Toast.makeText(MainActivity.this, "You got it!", Toast.LENGTH_SHORT).show();

                    //If guess is right, start new round and update the listview
                    initNewGuessRound();
                    adapter.notifyDataSetChanged();

                } else
                    Toast.makeText(MainActivity.this, "Try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Read word-definitions from raw text file
     */
    private void initDictionary() {
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.dummy_dictionary));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.split(":");
            dictionary.put(pieces[0], pieces[1]);
        }
    }

    /**
     * Pick up 5 random words from dictionary
     */
    private void pickFiveRandomWords() {
        if (dictionary.isEmpty()) {
            initDictionary();
        }

        ArrayList<String> allWords = new ArrayList<>(dictionary.keySet());
        Collections.shuffle(allWords);

        if (!selectedWordsList.isEmpty()) {
            selectedWordsList.clear();
        }

        if (!definitions.isEmpty())
            definitions.clear();

        for (int i = 0; i < 5; i++) {
            String selectedWord = allWords.get(i);
            selectedWordsList.add(selectedWord);
            definitions.add(dictionary.get(selectedWord));
        }

    }

    /**
     * Init new round by firstly picking up 5 random word from dictionary, then update the guessword
     * and shuffle the definition list
     */
    private void initNewGuessRound() {
        pickFiveRandomWords();

        textViewGuessWord.setText(selectedWordsList.get(0));
        Collections.shuffle(definitions);
    }
//-------------------------------------------------------
// Auto-generated Code Section
// -------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
