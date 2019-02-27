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

import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.Utils;

public class RoomsSetupActivity extends AppCompatActivity {

    //Constants
    private final String TAG = "Debug";

    //Activity elements
    EditText mRoomWidthEditText;
    EditText mRoomHeightEditText;
    Button mConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_setup);

        //Context
        final Context context = this;

        //Setting up the activity elements
        mRoomHeightEditText = findViewById(R.id.roomHeightEditText);
        mRoomWidthEditText  = findViewById(R.id.roomWidthEditText);
        mConfirmButton      = findViewById(R.id.confirmButtonRoom);

        //Listener
        //Confirm button
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if all fields are filled
                if(!Utils.isTextFieldEmpty(mRoomHeightEditText) && !Utils.isTextFieldEmpty(mRoomWidthEditText)){
                    //TODO set the room dimensions on the created house

                    Log.d(TAG, "onClick: ~ Iniciando atividade de coleta de amostras");
                    Intent intent = new Intent(context,collectActivity.class);
                    startActivity(intent);
                    Log.d(TAG, "onClick: ~ Atividade de coleta de amostras iniciada");

                }else{
                    Toast.makeText(context,"Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
