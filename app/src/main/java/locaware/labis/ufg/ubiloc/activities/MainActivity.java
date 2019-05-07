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
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.HouseBuffer;
import locaware.labis.ufg.ubiloc.innerDatabase.UsernameBuffer;

public class MainActivity extends AppCompatActivity {

    //Constants
    private final String TAG = "Debug";

    //Elements of the activity
    Button mConfigButton;
    Button mTrackingButton;
    EditText mUserNameEditText;
    public Context context;

    //Intents for start a new activity
    Intent userConfigIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Context
        context = this;

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
                FbDatabase.hasTheUsername(mUserNameEditText.getText().toString(),callback,context);

                if(Utils.isTextFieldEmpty(mUserNameEditText)){
                    Toast.makeText(context,"Por favor, digite um usuário válido",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    FbDatabase.HasTheUserCallback callback = new FbDatabase.HasTheUserCallback() {
        @Override
        public void callback(String username) {
            UsernameBuffer.setUsernameFlag(username);
            HouseBuffer.loadHouseBufferFromUsername(username,new HouseBuffer.bufferLoadedCallback() {
                @Override
                public void callback() {
                    Intent intent = new Intent(context, trackingActivity.class);
                    startActivity(intent);
                }
            });
        }
    };

}
