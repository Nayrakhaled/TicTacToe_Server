/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

/**
 *
 * @author AM STORE
 */
public class Game {

    private String choosenShape;
    private int id, row, col;

    public Game() {
    }

    public Game(int id, String choosenShape, int row, int col) {
        this.choosenShape = choosenShape;
        this.row = row;
        this.col = col;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRow(int position) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setChoosenShape(String ChoosenShape) {
        this.choosenShape = ChoosenShape;
    }

    public int getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getChoosenShape() {
        return choosenShape;
    }
}
