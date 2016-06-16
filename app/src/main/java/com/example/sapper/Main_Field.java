package com.example.sapper;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main_Field extends AppCompatActivity implements View.OnClickListener {

    TableLayout table;
    Button reset;
    ImageView[][] block;
    Random random;
    ImageView flagMode;
    int countX, countY;
    Game game;
    ImageView status;
    final String fPATH = "/sapper";


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__field);


        table = (TableLayout) findViewById(R.id.Table);

        flagMode = (ImageView) findViewById(R.id.ivSetFlag);
        if (flagMode != null) {
            flagMode.setOnClickListener(this);
        }
        reset = (Button) findViewById(R.id.bRandom);
        if (reset != null) {
            reset.setOnClickListener(this);
        }
        status = (ImageView) findViewById(R.id.ivSmile);

        generate();
        viewBlock();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRandom:
                generate();
                viewBlock();
                break;
            case R.id.ivSetFlag:
                game.FlagMode = !game.FlagMode;
                ImageView view = (ImageView) v;
                if (game.FlagMode)
                    view.setImageResource(R.drawable.flag);

                else
                    view.setImageResource(R.drawable.unflag);

                viewBlock();
                break;

            default:

                ImageView temp = (ImageView) v;
                int Y = temp.getId() % 100;
                int X = (temp.getId() - Y) / 100;
                if (game.FlagMode) {
                    if (!game.field[X][Y].isChecked) {
                        if (!game.field[X][Y].isFlag && game.FlagCount > 0) {
                            game.FlagCount--;
                            game.field[X][Y].isFlag = true;
                        }
                        else if(game.field[X][Y].isFlag){
                            game.FlagCount++;
                            game.field[X][Y].isFlag = false;
                        }
                    }
                } else if (!game.field[X][Y].isChecked && !game.field[X][Y].isFlag) {
                    game.field[X][Y].isChecked = true;
                    switch (game.field[X][Y].cage) {
                        case Empty:
                            game.Chain(X,Y);
                            break;
                        case Bomb:
                            game.field[X][Y].cage = Block.Cage.BOOM;
                            game.Finish();
                            status.setImageResource(R.drawable.l);
                            break;
                        default:
                            break;

                    }
                }
                viewBlock();
                isFinish();
                break;
        }
    }

    void viewBlock() // Visible Zone
    {
        for (int i = 0; i < countX; i++) {
            for (int j = 0; j < countY; j++) {

                if (!game.field[i][j].isChecked) {
                    block[i][j].setImageResource(R.drawable.a);
                    if (game.field[i][j].isFlag)
                        block[i][j].setImageResource(R.drawable.flag);
                } else
                    switch (game.field[i][j].cage) {
                        case Empty:
                            block[i][j].setImageResource(R.drawable.space);
                            break;
                        case Bomb:
                            block[i][j].setImageResource(R.drawable.b);
                            break;
                        case BOOM:
                            block[i][j].setImageResource(R.drawable.explosion);
                            break;
                        case N1:
                            block[i][j].setImageResource(R.drawable.n1);
                            break;
                        case N2:
                            block[i][j].setImageResource(R.drawable.n2);
                            break;
                        case N3:
                            block[i][j].setImageResource(R.drawable.n3);
                            break;
                        case N4:
                            block[i][j].setImageResource(R.drawable.n4);
                            break;
                        case N5:
                            block[i][j].setImageResource(R.drawable.n5);
                            break;
                        case N6:
                            block[i][j].setImageResource(R.drawable.n6);
                            break;
                        case N7:
                            block[i][j].setImageResource(R.drawable.n7);
                            break;
                        case N8:
                            block[i][j].setImageResource(R.drawable.n8);
                            break;
                        case unFlag:
                            block[i][j].setImageResource(R.drawable.unflag);
                            break;
                        case Flag:
                            block[i][j].setImageResource(R.drawable.flag);
                            break;

                    }
            }
        }
    }

    void generate() {
        ImageView flag = (ImageView) findViewById(R.id.ivSetFlag);
        if (flag != null) {
            flag.setImageResource(R.drawable.unflag);
        }
        status.setImageResource(R.drawable.i);
        table.removeAllViews();
        switch (Menu_Difficult.dif)
        {
            case 1:
                game = new Game(Game.Difficult.Easy);
                break;
            case  2:
                game = new Game(Game.Difficult.Normal);
                break;
            case  3:
                game = new Game(Game.Difficult.Hard);
                break;

        }

        countX = game.field.length;
        countY = game.field[0].length;
        random = new Random();
        block = new ImageView[countX][countY];

        Display display;
        Point p;
        display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        p = new Point();
       // display.getSize(p);
        int sizeScreen = p.x < p.y ? p.x : p.y;
        int blockSize = sizeScreen / (p.x < p.y ? countY : countX);

        for (int i = 0; i < countX; i++) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < countY; j++) {
                block[i][j] = new ImageView(this);
                block[i][j].setId((i * 100) + j);
                block[i][j].setOnClickListener(this);
                row.addView(block[i][j], blockSize, blockSize);
            }
            table.addView(row);

        }




        table.invalidate();
        table.requestLayout();
    }
    void isFinish() {
        int count = 0;
        for (int i = 0; i < game.field.length; i++)
            for (int j = 0; j < game.field[i].length; j++) {
                if (game.field[i][j].cage == Block.Cage.Bomb && game.field[i][j].isFlag  )
                    count++;

            }
        if (count == game.BombCount) {
            status.setImageResource(R.drawable.v);
            game.Finish();
        }
    }




    boolean writeFile(String fName, String content) {

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            return false;

        File path = Environment.getExternalStorageDirectory();

        path = new File(path.getAbsolutePath() + fPATH);

        path.mkdirs();
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path, fName)));
            writer.write(content);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    String[] readFile(String fName) {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return null;

        File path = Environment.getExternalStorageDirectory();
        path = new File(path.getAbsolutePath() + fPATH);
        File file = new File(path, fName);
        try {
            BufferedReader rStream = new BufferedReader(new FileReader(file));
            String rText = "";
            for (String text; (text = rStream.readLine()) != null; rText += text + "_");
            return rText.split("_") ;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}