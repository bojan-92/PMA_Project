package com.example.bojan.projekatpma.category;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bojan.projekatpma.Constants;
import com.example.bojan.projekatpma.R;
import com.example.bojan.projekatpma.db.CategoryDataSource;
import com.example.bojan.projekatpma.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bojan on 1/12/2017.
 */

public class ListAllCategories extends AppCompatActivity {

    Button btnAddNewCategory;
    CategoryDataSource sqlHelperCategories;
    LinearLayout parentLayout;
    LinearLayout layoutDisplayCategories;
    TextView tvNoRecordsFound;
    private String rowID = null;
    private ArrayList<HashMap<String, String>> tableData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        getAllWidgets();
        sqlHelperCategories = new CategoryDataSource(ListAllCategories.this);
        bindWidgetsWithEvent();
        displayAllRecords();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra(Constants.TITLE);
            Category category = new Category();
            category.setTitle(title);

            if (requestCode == Constants.ADD_RECORD) {
                sqlHelperCategories.addCategory(category);
            } else if (requestCode == Constants.UPDATE_RECORD) {
                category.setId(rowID);
                sqlHelperCategories.updateCategory(category);
            }
            displayAllRecords();
        }
    }

    private void getAllWidgets() {
        btnAddNewCategory = (Button) findViewById(R.id.btnAddNewRecord);
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        layoutDisplayCategories = (LinearLayout) findViewById(R.id.layoutDisplayCategories);
        tvNoRecordsFound = (TextView) findViewById(R.id.tvNoRecordsFound);
    }

    private void bindWidgetsWithEvent() {
        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddRecord();
            }
        });
    }

    private void onAddRecord() {
        Intent intent = new Intent(ListAllCategories.this, TableManipulationActivity.class);
        intent.putExtra(Constants.DML_TYPE, Constants.INSERT);
        startActivityForResult(intent, Constants.ADD_RECORD);
    }

    private void onUpdateRecord(String title) {
        Intent intent = new Intent(ListAllCategories.this, TableManipulationActivity.class);
        intent.putExtra(Constants.TITLE, title);
        intent.putExtra(Constants.DML_TYPE, Constants.UPDATE);
        startActivityForResult(intent, Constants.UPDATE_RECORD);
    }

    private void displayAllRecords() {
        LinearLayout inflateParentView;
        parentLayout.removeAllViews();
        List<Category> categories = sqlHelperCategories.getAllCategories();

        if (categories.size() > 0) {
            tvNoRecordsFound.setVisibility(View.GONE);
            Category category;
            for (int i = 0; i < categories.size(); i++) {
                category = categories.get(i);

                final Holder holder = new Holder();
                final View view = LayoutInflater.from(this).inflate(R.layout.inflate_record, null);
                inflateParentView = (LinearLayout) view.findViewById(R.id.inflateParentView);
                holder.tvCategoryTitle = (TextView) view.findViewById(R.id.tvCategoryTitle);

                view.setTag(category.getId());
                holder.categoryTitle = category.getTitle();
                holder.tvCategoryTitle.setText(holder.categoryTitle);

                final CharSequence[] items = {Constants.UPDATE, Constants.DELETE};
                inflateParentView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListAllCategories.this);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                if (which == 0) {
                                    rowID = view.getTag().toString();
                                    onUpdateRecord(holder.categoryTitle.toString());
                                } else {
                                    AlertDialog.Builder deleteDialogOk = new AlertDialog.Builder(ListAllCategories.this);
                                    deleteDialogOk.setTitle("Delete Category?");
                                    deleteDialogOk.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Category category1 = new Category();
                                            category1.setId(view.getTag().toString());
                                            sqlHelperCategories.deleteCategory(category1);
                                            displayAllRecords();
                                        }
                                    });
                                    deleteDialogOk.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    deleteDialogOk.show();
                                }
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return true;
                    }
                });
                parentLayout.addView(view);
            }
        } else {
            tvNoRecordsFound.setVisibility(View.VISIBLE);
        }
    }

    private class Holder {
        TextView tvCategoryTitle;
        String categoryTitle;
    }

}
