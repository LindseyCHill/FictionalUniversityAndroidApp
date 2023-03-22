package student.lindseychill.mobileapplicationprojectwgu.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import student.lindseychill.mobileapplicationprojectwgu.Data.UniversityDB;
import student.lindseychill.mobileapplicationprojectwgu.R;
import student.lindseychill.mobileapplicationprojectwgu.Utility.CurrentUserInfo;

public class LoginController extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginClicked(View v){
        UniversityDB db;
        EditText usernameText = findViewById(R.id.usernametext);
        EditText passwordText = findViewById(R.id.passwordText);
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        db = UniversityDB.getInstance(this);
        if(username.isEmpty() || password.isEmpty()){popUp("Please fill in all fields");}
        else if(db.LoginDAO().validate(username, password)==0){popUp("Incorrect login or password");}
        else{
            CurrentUserInfo.setUser(username);
            Intent intent = new Intent(this,HomeMenuController.class);
            startActivity(intent);}
    }
    private void popUp(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
