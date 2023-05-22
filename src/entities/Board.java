package entities;

public class Board {
    String[][] positions = new String[8][8];

    public Board(){
        for (int i = 0; i < 8; i++){
            for(int j=0; j < 8; j++){
                positions[i][j] = null;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("            ___ ___ ___ ___ ___ ___ ___ ___\n");

        for (int i = 7; i >= 0; i--){
            output.append(" ".repeat(i+1)).append(i+1).append(" /");
            for(int j=0; j < 8; j++){
                //if row number + col number is even, tile is white
                char tileColor = (i+j+2) % 2 == 0 ? '_' : '#';

                if(positions[i][j] != null){
                    output.append(tileColor).append(positions[i][j]).append(tileColor).append('/');
                }
                else {
                    output.append(tileColor).append(tileColor).append(tileColor).append('/');
                }

            }
            output.append("\n");
        }
        output.append("    a   b   c   d   e   f   g   h");
        return output.toString();
    }
}
