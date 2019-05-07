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
import locaware.labis.ufg.ubiloc.classes.User;
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.UsernameBuffer;

public class userConfigActivity extends AppCompatActivity {

    //Variables
    House house;
    User user;

    //Constants
    private final String TAG = "Debug";

    //Activity elements
    EditText mNewUserEditText;
    EditText mNewHouseEditText;
    EditText mQtdRoomsEditText;
    Button mConfirmButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_config);

        //The context of this activity
        final Context context = this;

        //Setting up the activity elements
        mNewHouseEditText = findViewById(R.id.newHouseEditText);
        mNewUserEditText  = findViewById(R.id.newUserNameEditText);
        mQtdRoomsEditText = findViewById(R.id.qtdRoomsEditText);
        mConfirmButton    = findViewById(R.id.confirmButtonNewUser);

        //LISTENERS
        //Confirm Button

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking if all field are filled
                if(
                    !Utils.isTextFieldEmpty(mNewHouseEditText) &&
                    !Utils.isTextFieldEmpty(mNewUserEditText) &&
                    !Utils.isTextFieldEmpty(mQtdRoomsEditText)
                ){ // In this case all fields are filled
                    house = new House();
                    user  = new User();

                    user.setName(mNewUserEditText.getText().toString());
                    user.setPosition(new Position(0,0));

                    //Setting up the house
                    house.setQtdRooms(Integer.valueOf(mQtdRoomsEditText.getText().toString()));
                    house.setName(mNewHouseEditText.getText().toString());
                    house.setUserAtArrayPosition(0,user);

                    UsernameBuffer.setHouseBuffer(house);

                    Log.d(TAG, "Nova casa adicionada ao buffer");

                    //Start a new Activity
                    Intent intent = new Intent(context,RoomsSetupActivity.class);
                    startActivity(intent);
                    Log.d(TAG, "onClick: ~ Tela de configuração dos quartos iniciada");
                }else{ //If some field is wrong or not filled
                    Toast.makeText(context,"Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
