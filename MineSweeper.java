/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;
import java.util.*;

public class MineSweeper{
public boolean m=false;
  private final int[][] cases;
  private final int x, y;
private int freeCases;
private int a;
  private MineSweeper(int x, int y, int bombs) {
    this.x = x;
    this.y = y;
    this.a=bombs;
    cases = new int[y][x];
    generate( a, new Random());
  }
  private void generate(int bombs, Random random){
    freeCases = x * y - bombs;
    while(bombs != 0){
      int rx = random.nextInt(x);
      int ry = random.nextInt(y);
      if(isBomb(rx, ry)) continue;
      cases[rx][ry] = -1;
      for(int x = -1; x < 2; x++){
        for(int y = -1; y < 2; y++){
          if(isValid(rx+x, ry+y) && !isBomb(rx+x, ry+y))
            cases[rx+x][ry+y]++;
        }
      }
      bombs--;
    }
  }
  private boolean isBomb(int x, int y){
return isValid(x, y) && (cases[x][y] == -1 || cases[x][y] == 9 || cases[x][y] == 19);  }

  private boolean isValid(int x, int y){
    return x >= 0 && y >=0 && x < this.x && y < this.y;
  }

  private boolean hasFlag(int x,int y){
    return isValid(x, y) && cases[x][y] > 18;
  }
  public boolean isOpen(int x, int y) {
    return isValid(x, y) && cases[x][y] > 8 && cases[x][y] < 19;
  }
  private void print() {
    StringBuilder builder = new StringBuilder();

    for(int x = 0; x < this.x; x++){
      for(int y = 0; y < this.y ; y++){
        builder.append(y==-1 ? x+1
                
          : hasFlag(x, y)&&!m ? "F"
          : isOpen(x, y) ? isBomb(x, y) ? "X"
            : cases[x][y] == 10 ? "-"
            : cases[x][y] - 10
          :"o");
        
      }
      builder.append("\n");
    
    }
    
    System.out.println(builder.toString());
  }
  private void setFlag(int x, int y){
    if(!isValid(x, y) || isOpen(x, y)) 
        return;
    cases[x][y] += hasFlag(x, y) ? -20 : 20;
  }

  private void open(int x, int y){
    if(!hasFlag(x, y) && isBomb(x, y)) {
      freeCases = -1;
      for(int xx = 0; xx < this.x; xx++){
        for(int yy = 0; yy < this.y; yy++){
          if(isBomb(xx, yy)) cases[xx][yy] = 9;
        }
      }
    }
    else clear(x, y);
  }

  private void clear(int x, int y) {
    if(!isValid(x, y) || isOpen(x, y) || hasFlag(x, y))
        return;
    cases[x][y] += 10;
    freeCases--;
    if(cases[x][y] > 10) 
        return;
    clear(x-1, y);
    clear(x+1, y);
    clear(x, y-1);
    clear(x, y+1);
  }

  private void play(){
    Scanner scanner = new Scanner(System.in);

    while(freeCases > 0){
      print();
      System.out.print("Please make a move:");
      String[] line = scanner.nextLine().split(" ");
      
      boolean flag=false;
        int x = Integer.parseInt(line[0]) - 1;
        int y = Integer.parseInt(line[1]) - 1;
        if(line.length>2 && line[2].equals("F"))
            flag=true;
        if(flag==true&&line[2].equals("U")){
          
            flag=false;
            m=true;
        }
        if(flag) setFlag(x, y);
        else open(x, y);
       
      }
    

    print();
    System.out.println(freeCases == 0 ? "Kazandınız." : "You lost, better luck next time .");
    scanner.close();
  }

  public static void main(String []args){
      System.out.println("Please enter the sizes of the board (m x n) :");
      Scanner sc=new Scanner(System.in);
      int a=sc.nextInt();
      int b=sc.nextInt();
     
      sc.nextLine(); 
      System.out.println("Please select the difficulty (E,M,H) :");
      String dif=sc.nextLine();
       int size=a*b;
       int totalmine=0;
        if (dif.equals("M"))
            totalmine=(int)(size/4);
        if (dif.equals("E"))
            totalmine= (int)((15*size)/100);
        if (dif.equals("H"))
            totalmine=(int)((2*size)/5);
  
     new MineSweeper(a, b, totalmine).play();
  }
}