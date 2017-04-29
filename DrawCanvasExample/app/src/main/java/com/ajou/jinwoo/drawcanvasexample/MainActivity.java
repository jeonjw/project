package com.ajou.jinwoo.drawcanvasexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private CanvasView customCanvas;
    private Button clearButton;
    private Button undoButton;
    private Button blackButton;
    private Button pinkButton;
    private Button greenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        clearButton = (Button) findViewById(R.id.clear_button);
        undoButton = (Button) findViewById(R.id.undo_button);
        blackButton = (Button) findViewById(R.id.black_button);
        pinkButton = (Button) findViewById(R.id.pink_button);
        greenButton = (Button) findViewById(R.id.green_button);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCanvas(v);
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoPath();
            }
        });

        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(0);
            }
        });
        pinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(1);
            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(2);
            }
        });
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

    public void changeColor(int color){
        customCanvas.SetPenColor(color);
    }
    public void undoPath(){customCanvas.undoPath();}


    /*
    * Todo : Button�� OnClick Event 諛쒖깮 �� �몄텧�� �� �덈뒗 �곸젅�� 硫붿냼�� �앹꽦
    *
    * ex ) public void SetColorPink(View v) { ... }
    * */
}
