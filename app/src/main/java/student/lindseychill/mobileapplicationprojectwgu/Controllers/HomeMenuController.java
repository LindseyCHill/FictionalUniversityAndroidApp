package student.lindseychill.mobileapplicationprojectwgu.Controllers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import student.lindseychill.mobileapplicationprojectwgu.R;

public class HomeMenuController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
        //sets up action bar
//        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
//        View backBTN = findViewById(R.id.back_btn);
//        View homeBTN = findViewById(R.id.home_btn);
//        backBTN.setVisibility(View.GONE);
//        homeBTN.setVisibility(View.GONE);
    }

    public void nextPage(View v) {
        //launch next activity with intent
        Button btn = (Button) v;
        String btnText = (String) btn.getText();
        Intent intent;
        switch (btnText) {
            case "Terms":
                intent = new Intent(this, AllTermsController.class);
                startActivity(intent);
                break;
            case "Courses":
                intent = new Intent(this, AllCoursesController.class);
                startActivity(intent);
                break;
            case "Assessments":
                intent = new Intent(this, AllAssessmentsController.class);
                startActivity(intent);
                break;
            case "Notifications":
                intent = new Intent(this, NotificationController.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Please try again!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}