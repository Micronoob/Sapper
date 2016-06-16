package com.example.sapper;

import java.util.Random;

/**
 * Created by Андрей on 05.05.2016.
 */

public class Game {

    public enum Difficult{Easy, Normal, Hard}

    public Block[][] field;
    public boolean FlagMode;
    public int FlagCount;
    public int BombCount;


    private Random rand;

    public Game(Difficult difficult)
    {
        rand = new Random();
        switch (difficult){
            case Easy:
                field = new Block[10][10];
                break;

            case Normal:
                field = new Block[16][16];
                break;

            case  Hard:
                field = new Block[24][24];
                break;
        }

        BombCount = field.length + field.length/2;
                FlagCount = BombCount;
        FlagMode = false;

        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                field[i][j] = new Block ();

        placementOfBombs();
        placementOfNum();
    }

   public void placementOfBombs(){

       for (int i = 0; i < BombCount ; i++) {
           for ( int x=rand.nextInt(field.length),y=rand.nextInt(field[x].length);
               ;x=rand.nextInt(field.length),y=rand.nextInt(field[x].length))
               if (!field[x][y].cage.equals(Block.Cage.Bomb))
               {
                   field[x][y].cage = Block.Cage.Bomb;
                   break;
               }
       }
   }
    public void placementOfNum()
    {
        for (int i = 0; i < field.length; i++)
            for (int j = 0,count=0; j < field[0].length; j++,count=0) {
                if (field[i][j].cage.equals(Block.Cage.Bomb))
                    continue;
                if (i - 1 >= 0 && field[i - 1][j].cage.equals(Block.Cage.Bomb))
                    count++;
                if (i + 1 < field.length && field[i + 1][j].cage.equals(Block.Cage.Bomb))
                    count++;
                if (j - 1 >= 0 && field[i][j - 1].cage.equals(Block.Cage.Bomb))
                    count++;
                if (j + 1 < field.length && field[i][j + 1].cage.equals(Block.Cage.Bomb))
                    count++;

                if (i - 1 >= 0 && j - 1 >= 0 && field[i - 1][j - 1].cage.equals(Block.Cage.Bomb))
                    count++;
                if (i + 1 < field.length && j + 1 < field[0].length && field[i + 1][j + 1].cage.equals(Block.Cage.Bomb))
                    count++;
                if (i + 1 < field.length && j - 1 >= 0 && field[i + 1][j - 1].cage.equals(Block.Cage.Bomb))
                    count++;
                if (i - 1 >= 0 && j + 1 < field[0].length && field[i - 1][j + 1].cage.equals(Block.Cage.Bomb))
                    count++;

                switch (count)
                {
                    case 0:field[i][j].cage= Block.Cage.Empty;
                        break;
                    case 1:field[i][j].cage= Block.Cage.N1;
                        break;
                    case 2:field[i][j].cage= Block.Cage.N2;
                        break;
                    case 3:field[i][j].cage= Block.Cage.N3;
                        break;
                    case 4:field[i][j].cage= Block.Cage.N4;
                        break;
                    case 5:field[i][j].cage= Block.Cage.N5;
                        break;
                    case 6:field[i][j].cage= Block.Cage.N6;
                        break;
                    case 7:field[i][j].cage= Block.Cage.N7;
                        break;
                    case 8:field[i][j].cage= Block.Cage.N8;
                        break;
                }
            }

    }
    public void Finish()
    {
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].isFlag && !field[i][j].cage.equals(Block.Cage.Bomb))
                    field[i][j].cage = Block.Cage.unFlag;
                else if (field[i][j].isFlag && field[i][j].cage.equals(Block.Cage.Bomb))
                    field[i][j].cage = Block.Cage.Flag;
                field[i][j].isChecked = true;


            }
    }
    public  void Chain(int X, int Y)
    {
        try {


            if (X - 1 >= 0 && !field[X - 1][Y].cage.equals(Block.Cage.Bomb)&& !field[X - 1][Y].isChecked) {
                field[X - 1][Y].isChecked = true;
                if (field[X - 1][Y].cage.equals(Block.Cage.Empty))
                Chain(X - 1, Y);
            }
            if (Y - 1 >= 0 && !field[X][Y - 1].cage.equals(Block.Cage.Bomb) &&!field[X][Y - 1].isChecked) {
                field[X][Y - 1].isChecked = true;
                if ( field[X][Y - 1].cage.equals(Block.Cage.Empty))
                Chain(X, Y - 1);
            }
            if (X + 1 < field.length && !field[X + 1][Y].cage.equals(Block.Cage.Bomb) && !field[X + 1][Y].isChecked) {
                field[X + 1][Y].isChecked = true;
              if (field[X + 1][Y].cage.equals(Block.Cage.Empty))
                Chain(X + 1, Y);
            }
            if (Y + 1 < field.length && !field[X][Y + 1].cage.equals(Block.Cage.Bomb) && !field[X][Y + 1].isChecked) {
                field[X][Y + 1].isChecked = true;
                if (field[X][Y + 1].cage.equals(Block.Cage.Empty))
                Chain(X, Y + 1);
            }


            if (X - 1 >= 0 && Y - 1 >= 0 && !field[X - 1][Y - 1].cage.equals(Block.Cage.Bomb) && !field[X - 1][Y - 1].isChecked) {
                field[X - 1][Y - 1].isChecked = true;
                if (field[X - 1][Y - 1].cage.equals(Block.Cage.Empty))
                Chain(X - 1, Y - 1);
            }
            if (X + 1 >= 0 && Y - 1 >= 0 && !field[X + 1][Y - 1].cage.equals(Block.Cage.Bomb) && !field[X + 1][Y - 1].isChecked) {
                field[X + 1][Y - 1].isChecked = true;
                if (field[X + 1][Y - 1].cage.equals(Block.Cage.Empty))
                Chain(X + 1, Y - 1);
            }
            if (X - 1 < field.length && Y + 1 < field.length && !field[X + 1][Y + 1].cage.equals(Block.Cage.Bomb) && !field[X + 1][Y + 1].isChecked) {
                field[X - 1][Y + 1].isChecked = true;
               if (field[X + 1][Y + 1].cage.equals(Block.Cage.Empty))
                Chain(X - 1, Y + 1);
            }
            if (X + 1 < field.length && Y + 1 < field.length && !field[X + 1][Y + 1].cage.equals(Block.Cage.Bomb) && !field[X + 1][Y + 1].isChecked) {
                field[X + 1][Y + 1].isChecked = true;
                if (field[X + 1][Y + 1].cage.equals(Block.Cage.Empty))
                Chain(X + 1, Y + 1);
            }

        }  catch (Exception e)
        {
            e.printStackTrace();
        }
     }


}
