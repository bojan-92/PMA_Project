package com.example.bojan.projekatpma.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bojan.projekatpma.Constants;
import com.example.bojan.projekatpma.R;

/**
 * Created by Bojan on 1/15/2017.
 */

public class TableManipulationActivity extends Activity {
    EditText etCategoryTitle;
    Button btnDML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_manipulation);
        getAllWidgets();
        bindWidgetsWithEvent();
        checkForRequest();
    }

    private void checkForRequest() {
        String request = getIntent().getExtras().get(Constants.DML_TYPE).toString();
        if (request.equals(Constants.UPDATE)) {
            btnDML.setText(Constants.UPDATE);
            etCategoryTitle.setText(getIntent().getExtras().get(Constants.TITLE).toString());
        } else {
            btnDML.setText(Constants.INSERT);
        }
    }

    private void bindWidgetsWithEvent() {
        btnDML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });
    }

    private void getAllWidgets() {
        etCategoryTitle = (EditText) findViewById(R.id.etCategoryTitle);

        btnDML = (Button) findViewById(R.id.btnDML);
    }

    private void onButtonClick() {
        if (etCategoryTitle.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Add Title", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.TITLE, etCategoryTitle.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
