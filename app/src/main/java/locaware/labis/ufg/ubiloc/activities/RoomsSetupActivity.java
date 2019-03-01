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
import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.classes.Room;
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.Buffer;

public class RoomsSetupActivity extends AppCompatActivity {

    //Constants
    private final String TAG = "Debug";

    //Activity elements
    EditText mRoomWidthEditText;
    EditText mRoomHeightEditText;
    EditText mRoomNameEditText;
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
        mRoomNameEditText   = findViewById(R.id.roomNameEditText);
        mConfirmButton      = findViewById(R.id.confirmButtonRoom);

        //Listener
        //Confirm button
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if all fields are filled
                if(!Utils.isTextFieldEmpty(mRoomHeightEditText) &&
                   !Utils.isTextFieldEmpty(mRoomWidthEditText) &&
                   !Utils.isTextFieldEmpty(mRoomNameEditText)){

                    House workingHouse = Buffer.getLastHouse();
                    Room theNewRoom;
                    int width   = Integer.valueOf(mRoomWidthEditText.getText().toString());
                    int height  = Integer.valueOf(mRoomHeightEditText.getText().toString());
                    String name = mRoomNameEditText.getText().toString();

                    theNewRoom = new Room(width,height,name);
                    Log.d(TAG, "~ Quarto criado: ");
                    Log.d(TAG, "~ WIDTH: " + theNewRoom.getWidth());
                    Log.d(TAG, "~ HEIGHT: " + theNewRoom.getHeight());
                    Log.d(TAG, "~ NAME: " + theNewRoom.getName());
                    //Adding the new room to the house
                    workingHouse.addRoomAtLastIndex(theNewRoom);
                    Log.d(TAG, "~ Atual situação da casa:");
                    Log.d(TAG, "~ NOME: " + Buffer.getLastHouse().getName());
                    Log.d(TAG, "~ ÚLTIMO QUARTO: " + Buffer.getLastHouse().getLastRoom().getName());


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
