package com.example.bojan.projekatpma.menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bojan.projekatpma.R;

/**
 * Created by Bojan on 1/9/2017.
 */

public class AddCategory extends Activity {

//    TextInputLayout TLUCategoryTitle;
    EditText txtCategoryTitle;
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category);

       // TLUCategoryTitle = (TextInputLayout)findViewById(R.id.userName);

        submitButton = (Button)findViewById(R.id.id_submit_button);

        txtCategoryTitle = (EditText)findViewById(R.id.txtCategoryTitle);

       // TLUCategoryTitle.setHint("Enter Title"); //setting hint.
       /* submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String passcode = Pwd.getText().toString();

                if(!passcode.equals("testing"))
                {
                    TLPassword.setError("Password is wrong, please try again");
                }
                else
                {
                    TLPassword.setError(null);
                }
            }
        });*/


    }
}
