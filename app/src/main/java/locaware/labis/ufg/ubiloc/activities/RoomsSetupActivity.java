package locaware.labis.ufg.ubiloc.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.HouseBuffer;
import locaware.labis.ufg.ubiloc.innerDatabase.UsernameBuffer;

public class RoomsSetupActivity extends AppCompatActivity {

    //Constants
    private final String TAG = "Debug";

    //Activity elements
    EditText mRoomNameEditText;
    TextView mRoomNumberTextView;
    Button mConfirmButton;

    //Vars
    int roomNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_setup);

        //Context
        final Context context = this;

        //Setting up the activity elements
        mRoomNameEditText   = findViewById(R.id.roomNameEditText);
        mConfirmButton      = findViewById(R.id.confirmButtonRoom);
        mRoomNumberTextView = findViewById(R.id.roomNumber);

        mRoomNumberTextView.setText("Quarto " + roomNumber);

        //Listener
        //Confirm button
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if all fields are filled
                if(!Utils.isTextFieldEmpty(mRoomNameEditText)){

                    House workingHouse = HouseBuffer.getHouseBuffer();



                }else{
                    Toast.makeText(context,"Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
