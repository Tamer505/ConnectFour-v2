import javax.swing.*;

public class GameFrame extends JFrame{
    private boolean running = true;

    public void setBoard(Board b) {
        Board.running = true;
        this.add(b);
        System.out.println(b);
        this.setSize(1000,800);
        this.setTitle("Connect4");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}