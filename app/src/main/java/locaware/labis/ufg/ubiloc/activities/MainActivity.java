package locaware.labis.ufg.ubiloc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import locaware.labis.ufg.ubiloc.Database.FbDatabase;
import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.Buffer;

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
            Buffer.setUsernameFlag(username);
            Buffer.loadBufferFromUsername(username,new Buffer.bufferLoadedCallback() {
                @Override
                public void callback() {
                    Intent intent = new Intent(context, trackingActivity.class);
                    startActivity(intent);
                }
            });
        }
    };

}
