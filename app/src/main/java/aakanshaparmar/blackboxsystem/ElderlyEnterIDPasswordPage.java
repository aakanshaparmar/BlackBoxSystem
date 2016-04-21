package aakanshaparmar.blackboxsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ElderlyEnterIDPasswordPage extends ActionBarActivity {

    EditText password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_enter_idpassword_page);

        password = (EditText) findViewById(R.id.idPassword);
        submit = (Button) findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String pass = String.valueOf(password.getText());
                if(pass.equals("1234"))
                {
                    Intent intent = new Intent(v.getContext(), ElderlyIDPage.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Access Denied, Wrong Password", Toast.LENGTH_LONG).show();


            }
        });
    }
}
