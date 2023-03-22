package student.lindseychill.mobileapplicationprojectwgu.Controllers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import student.lindseychill.mobileapplicationprojectwgu.Adapters.TermAdapter;
import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.Model.Term;
import student.lindseychill.mobileapplicationprojectwgu.R;

public class AllTermsController extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    TermAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<Term> termList = new ArrayList<>();
    UniversityDB db;
    public static final String EXTRA_MESSAGE = "student.lindseychill.mobileapplicationprojectwgu.TERM_ID";
//    private static final String TAG = "POPCORN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);
        //sets up action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        //set up views
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        db = UniversityDB.getInstance(this);
        termList = db.TermDAO().getAll();
        adapter = new TermAdapter(AllTermsController.this,termList);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                List<Term> filteredList = new ArrayList<>();
                for(Term term : termList){
                    if (term.getName().toLowerCase().contains(searchQuery.toLowerCase())) filteredList.add(term);
                }

                if(filteredList.isEmpty()){
                    popUp("No search results");}
                else {
                    adapter.showFilteredList(filteredList);
                }
                return true;
            }
        });
    }

    public void menuSelection(View v){
        View backBTN = findViewById(R.id.back_btn);
        View homeBTN = findViewById(R.id.home_btn);
        Intent intent;
        if(v.getId() == backBTN.getId() || v.getId() == homeBTN.getId()){
            //go back a page to the home menu
            intent = new Intent(this, HomeMenuController.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please try again!", Toast.LENGTH_SHORT).show();}
    }
    public void displayFocusedTerm(Long id){
        Intent intent = new Intent(this, FocusTermController.class);
        String message = String.valueOf(id);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void newTerm(View view){
        Intent intent = new Intent(this, FocusTermController.class);
        String message = "-1";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    private void popUp(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}