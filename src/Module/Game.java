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
    private int id, position;
    
    public Game(){}
    public Game(int id, String choosenShape, int position){
        this.choosenShape = choosenShape;
        this.position = position;
    }
    
    public void setId(int id){ this.id = id;}
    public void setPosition(int position){ this.position = position;}
    public void setChoosenShape(String ChoosenShape){ this.choosenShape = ChoosenShape;}
    
    public int getId(){return id;}
    public int getPosition(){return position;}
    public String getChoosenShape(){return choosenShape;}
}
