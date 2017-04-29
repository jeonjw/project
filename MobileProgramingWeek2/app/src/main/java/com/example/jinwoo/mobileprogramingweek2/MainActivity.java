package com.example.jinwoo.mobileprogramingweek2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ShipItem shipItem;
    private EditText editText;
    private TextView baseCostTextView;
    private TextView addCostTextView;
    private TextView totalCostTextView;
    private TextView bookCountTextView;
    private TextView pencilCountTextView;
    private TextView eraserCountTextView;
    private Button bookAddButton;
    private Button bookSubButton;
    private Button pencilAddButton;
    private Button pencilSubButton;
    private Button eraserAddButton;
    private Button eraserSubButton;
    private int totalWeight;
    private int bookCount;
    private int pencilCount;
    private int eraserCount;
    private static final int BOOK_OUNCES = 14;
    private static final double PENCIL_OUNCES = 1.5;
    private static final int ERASER_OUNCES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shipItem = new ShipItem();

        editText = (EditText) findViewById(R.id.edit_text);
        baseCostTextView = (TextView) findViewById(R.id.base_cost_text_view);
        addCostTextView = (TextView) findViewById(R.id.add_cost_text_view);
        totalCostTextView = (TextView) findViewById(R.id.total_cost_text_view);
        bookCountTextView = (TextView) findViewById(R.id.book_count_text_view);
        pencilCountTextView = (TextView) findViewById(R.id.pencil_count_text_view);
        eraserCountTextView = (TextView) findViewById(R.id.eraser_count_text_view);

        bookAddButton = (Button) findViewById(R.id.book_add_button);
        bookSubButton = (Button) findViewById(R.id.book_sub_button);
        pencilAddButton = (Button) findViewById(R.id.pencil_add_button);
        pencilSubButton = (Button) findViewById(R.id.pencil_sub_button);
        eraserAddButton = (Button) findViewById(R.id.eraser_add_button);
        eraserSubButton = (Button) findViewById(R.id.eraser_sub_button);

        bookAddButton.setOnClickListener(onClickListener);
        bookSubButton.setOnClickListener(onClickListener);
        pencilAddButton.setOnClickListener(onClickListener);
        pencilSubButton.setOnClickListener(onClickListener);
        eraserAddButton.setOnClickListener(onClickListener);
        eraserSubButton.setOnClickListener(onClickListener);

        bookSubButton.setEnabled(false);
        pencilSubButton.setEnabled(false);
        eraserSubButton.setEnabled(false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    totalWeight = (int) Double.parseDouble(s.toString());
                    shipItem.setWeight(totalWeight);
                } catch (NumberFormatException e) {
                    shipItem.setWeight(0);
                }
                displayShipping();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void displayShipping() {
        baseCostTextView.setText("$" + String.format("%.2f", shipItem.getBaseCost()));
        addCostTextView.setText("$" + String.format("%.2f", shipItem.getAddedCost()));
        totalCostTextView.setText("$" + String.format("%.2f", shipItem.getTotalCost()));
    }

    Button.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.book_add_button:
                    bookCount++;
                    break;
                case R.id.book_sub_button:
                    bookCount--;
                    break;
                case R.id.pencil_add_button:
                    pencilCount++;
                    break;
                case R.id.pencil_sub_button:
                    pencilCount--;
                    break;
                case R.id.eraser_add_button:
                    eraserCount++;
                    break;
                case R.id.eraser_sub_button:
                    eraserCount--;
                    break;
            }
            if (bookCount > 0)
                bookSubButton.setEnabled(true);
            else
                bookSubButton.setEnabled(false);
            if (pencilCount > 0)
                pencilSubButton.setEnabled(true);
            else
                pencilSubButton.setEnabled(false);
            if (eraserCount > 0)
                eraserSubButton.setEnabled(true);
            else
                eraserSubButton.setEnabled(false);

            bookCountTextView.setText(String.valueOf(bookCount));
            pencilCountTextView.setText(String.valueOf(pencilCount));
            eraserCountTextView.setText(String.valueOf(eraserCount));
            editText.setText(String.valueOf((bookCount * BOOK_OUNCES) + (pencilCount * PENCIL_OUNCES) + (eraserCount * ERASER_OUNCES)));
        }
    };

}
