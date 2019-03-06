package locaware.labis.ufg.ubiloc.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import locaware.labis.ufg.ubiloc.Database.FbDatabase;
import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.User;
import locaware.labis.ufg.ubiloc.classes.Utils;

public class MainActivity extends AppCompatActivity {

    //Constants
    private final String TAG = "Debug";

    //Elements of the activity
    Button mConfigButton;
    Button mTrackingButton;
    EditText mUserNameEditText;

    //Intents for start a new activity
    Intent userConfigIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Context
        final Context context = this;

        //Setting the Intents for start a new activity
        userConfigIntent = new Intent(this,userConfigActivity.class);

        //Setting up the elements of activity
        mConfigButton     = findViewById(R.id.configButton);
        mTrackingButton   = findViewById(R.id.trackingButton);
        mUserNameEditText = findViewById(R.id.userNameEditText);

        //LISTENERS

        //Config button
        mConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicia a activity de configuração de um novo usuário
                Log.d(TAG, "~ Botão de configurar pressionado, iniciando nova activity");
                startActivity(userConfigIntent);
                Log.d(TAG, "~ Nova activity iniciada");
            }
        });

        //Tracking button
        mTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check the username field
                //TODO Check if the user tipped a existing username
                if(!Utils.isTextFieldEmpty(mUserNameEditText)){
                    Log.d(TAG, "onClick: ~ Iniciando tracking activity");
                    Intent intent = new Intent(context,trackingActivity.class);
                    startActivity(intent);
                    Log.d(TAG, "onClick: ~ Tracking activity iniciada");
                }else{
                    Toast.makeText(context,"Por favor, digite um usuário válido",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
