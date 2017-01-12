package com.example.bojan.projekatpma.category;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bojan.projekatpma.R;
import com.example.bojan.projekatpma.db.CategoryDataSource;
import com.example.bojan.projekatpma.db.MarkerDataSource;
import com.example.bojan.projekatpma.model.Category;

/**
 * Created by Bojan on 1/9/2017.
 */

public class AddCategory extends Activity {

    Context context = this;
    EditText txtCategoryTitle;
    Button submitButton;
    CategoryDataSource data;
    Category c = new Category();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category);

        data = new CategoryDataSource(context);
        try {
            data.open();
        } catch (Exception e) {
            Log.i("Connection unsuccessful", "Connection unsuccessful");
        }

       // TLUCategoryTitle = (TextInputLayout)findViewById(R.id.userName);

        submitButton = (Button)findViewById(R.id.id_submit_button);
        txtCategoryTitle = (EditText)findViewById(R.id.txtCategoryTitle);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setTitle(String.valueOf(txtCategoryTitle.getText().toString()));
                data.addCategory(c);
            }
        });

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
