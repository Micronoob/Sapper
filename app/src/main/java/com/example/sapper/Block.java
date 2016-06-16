package com.example.sapper;

/**
 * Created by Андрей on 12.05.2016.
 */
public class Block {
    public enum Cage{Empty, Bomb, BOOM, N1, N2, N3, N4, N5, N6, N7, N8, unFlag,Flag}
    public Cage cage;
    public boolean isChecked;
    public boolean isFlag;

    public Block()
    {
        isChecked = false;
        isFlag = false;
        cage = Cage.Empty;
    }
}
