import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * @author Tamer Gabal
 * @version 1.4
 */
public class Board extends JPanel implements ActionListener{
    //empty space indicated by 0, red piece is 1, yellow is 2
    //row1 is bottom row, row6 is top row
    private static int[][] board = {new int[7], new int[7], new int[7], new int[7], new int[7], new int[7]};
    private int circleX = 375;
    //private final int circleY = 50;
    private final int height=820;
    private final int width=700;
    private int pieceColumn = 4;
//    private final static int connect = 4;
    private int currentPiece;
    private String pieceColor;
    private final Timer timer;
    private boolean gameStarted;
    private boolean buttonVisible = true;
    private static boolean gameEnded;
    private final JButton startButton;
    private final JButton restartButton;
    private final JButton[] buttons = new JButton[2];
    private final JLabel label;
    private String winner;
    public static boolean running;

    public Board(){

        startButton = new JButton("Start");
        label = new JLabel("Click to Start Game");
        restartButton = new JButton("Restart");

        this.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        this.setLayout(new GridLayout(0,1));
        this.setPreferredSize(new Dimension(height,width));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new MyKeyAdapter());

        timer = new Timer(100, this);
    }

    private int startGame() {
        timer.start();
        return currentPiece;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        if(!gameEnded) {
            if (!gameStarted) {
                currentPiece = 1;
                pieceColor = "red";
                createButton(startButton);
            } else {
                startButton.setVisible(false);
                label.setVisible(false);
                buttonVisible = false;
                drawBackground(g);
                startGame();
                drawBoard(g);
                drawPiece(g, pieceColor, circleX, 50);
                winner = checkGameOver();
            }
        }
        else{
            createButton(restartButton);
            restartButton.setVisible(true);
            if(winner.equals("tie")){
                g2D.setPaint(Color.BLACK);
                g2D.setFont(new Font("Times New Roman", Font.BOLD, 50));
                g2D.drawString("NOBODY WINS!", width/2-110, 100);
            }
            else if(winner.equals("red")){
                g2D.setPaint(Color.RED);
                g2D.setFont(new Font("Times New Roman",Font.BOLD,50));
                g2D.drawString("RED WINS!", width/2-110,100);
            }
            else if(winner.equals("yellow")) {
                g2D.setPaint(Color.YELLOW);
                g2D.setFont(new Font("Times New Roman", Font.BOLD, 50));
                g2D.drawString("YELLOW WINS!", width/2-110, 100);
            }
        }

    }
    public void createButton(JButton b){
        b.setBounds((width/2)-130,(height/2)-250,400,400);
        b.setBackground(Color.RED);
        this.add(b);
        this.add(label);
        b.addActionListener(this);
        if(label.getText().equals("Game Started!")){
            gameStarted = true;
        }
    }

    /**
     * @param color - color of piece
     * @param x - int value of x position to draw piece at
     * @param y - int value of y position to draw piece at
     */
    public void drawPiece(Graphics g, String color, int x, int y){
        Graphics2D g2D = (Graphics2D) g;
        if(color.equals("red"))
            g2D.setPaint(Color.red);
        else if(color.equals("yellow"))
            g2D.setPaint(Color.yellow);
        else
            g2D.setPaint(Color.white);
        g2D.fillOval(x,y,63,63);
    }
    public void drawBackground(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
//        g2D.drawImage(background, 0, 200, null);
        g2D.setPaint(Color.blue);
        g2D.fillRect(100,width/4,height,width*3/4);
    }

    public void drawBoard(Graphics g){
        //Graphics2D g2D = (Graphics2D) g;
        int x=0;
        while(x<board.length){
            for(int i=0;i<board[x].length;i++){
                if(board[5-x][i]==1){
                    drawPiece(g,"red",165 + 70*i,595-70*x);
                }
                else if(board[5-x][i]==2){
                    drawPiece(g,"yellow",165 + 70*i,595-70*x);
                }
                else{
                    drawPiece(g,"white",165 + 70*i,595-70*x);
                }
            }
            x++;
        }
    }

    /**
     * @return String value of who winner is: red, yellow, or neither
     */
    public static String checkGameOver(){
        //Check horizontally
        for(int[]row:board){
            for(int i=0;i<row.length-3;i++){
                if(row[i]==row[i+1] && row[i]==row[i+2] && row[i]==row[i+3] && row[i]!=0){
                    gameEnded = true;
                    if(row[i]==1)
                        return "red";
                    else
                        return "yellow";
                }
            }
        }
        //Check vertically
        for(int x=0;x<board[0].length;x++){
            for(int i=0;i<board.length-3;i++){
                if(board[i][x]==board[i+1][x] && board[i][x]==board[i+2][x] && board[i][x]==board[i+3][x] && board[i][x]!=0){
                    gameEnded = true;
                    if(board[i][x]==1)
                        return "red";
                    else
                        return "yellow";
                    }

            }
        }
        //Check diagonally up
        for(int x=0;x<board[0].length-3;x++){
            for(int i=0;i<board.length-3;i++){
                //for(int y=connect;)
                if(board[i][x]==board[i+1][x+1] && board[i][x]==board[i+2][x+2] && board[i][x]==board[i+3][x+3] && board[i][x]!=0){
                    gameEnded = true;
                    if(board[i][x]==1)
                        return "red";
                    else
                        return "yellow";
                    }
            }
        }
        //Check diagonally down
        for(int x=0;x<board[0].length-3;x++){
            for(int i=3;i<board.length;i++){
                if(board[i][x]==board[i-1][x+1] && board[i][x]==board[i-2][x+2] && board[i][x]==board[i-3][x+3] && board[i][x]!=0){
                    gameEnded = true;
                    if(board[i][x]==1)
                        return "red";
                    else
                        return "yellow";
                    }

            }
        }
        //check if board is full
        int count = 0;
        for(int[]row:board){
            for(int i:row){
                if(i==0)
                    count++;
            }
        }
        if(count==0){
            gameEnded = true;
            return "tie";
        }
        return "";
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==(startButton))
            label.setText("Game Started!");
        else if(e.getSource()==(restartButton)){
            for(int i=0;i<board.length;i++){
                board[i] = new int[7];
            }
            gameStarted = true;
            gameEnded = false;
            restartButton.setVisible(false);
            winner = "";
        }
        repaint();

    }

    //accessor and mutator methods
    public int getCircleX() {
        return circleX;
    }

    public int getCurrentPiece() {
        return currentPiece;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getPieceColumn() {
        return pieceColumn;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public static int[][] getBoard() {
        return board;
    }

    public String getWinner() {
        return winner;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getRestartButton() {
        return restartButton;
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public JLabel getLabel() {
        return label;
    }

    public Timer getTimer() {
        return timer;
    }

    public static void setBoard(int[][] board) {
        Board.board = board;
    }

    public void setButtonVisible(boolean buttonVisible) {
        this.buttonVisible = buttonVisible;
    }

    public void setCircleX(int circleX) {
        this.circleX = circleX;
    }

    public void setCurrentPiece(int currentPiece) {
        this.currentPiece = currentPiece;
    }

    public static void setGameEnded(boolean gameEnded) {
        Board.gameEnded = gameEnded;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void setPieceColumn(int pieceColumn) {
        this.pieceColumn = pieceColumn;
    }

    public void setPieceColor(String pieceColor) {
        this.pieceColor = pieceColor;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Board{" +
                "circleX=" + circleX +
                ", height=" + height +
                ", width=" + width +
                ", pieceColumn=" + pieceColumn +
                ", currentPiece=" + currentPiece +
                ", pieceColor='" + pieceColor + '\'' +
                ", timer=" + timer +
                ", gameStarted=" + gameStarted +
                ", buttonVisible=" + buttonVisible +
                ", startButton=" + startButton +
                ", restartButton=" + restartButton +
                ", buttons=" + Arrays.toString(buttons) +
                ", label=" + label +
                ", winner='" + winner + '\'' +
                '}';
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
                if (!buttonVisible) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    if(pieceColumn<7) {
                        circleX += 70;
                        pieceColumn++;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    if(pieceColumn>1) {
                        circleX -= 70;
                        pieceColumn--;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                    //for(int[] row:board){
                    for(int i=5;i>=0;i--){
                        if(board[i][pieceColumn-1]==0){
                            board[i][pieceColumn-1] = currentPiece;
                            circleX = 375;
                            pieceColumn = 4;
                            if(currentPiece==1) {
                                currentPiece = 2;
                                pieceColor = "yellow";
                            }
                            else {
                                currentPiece = 1;
                                pieceColor = "red";
                            }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }