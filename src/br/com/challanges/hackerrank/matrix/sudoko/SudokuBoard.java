package br.com.challanges.hackerrank.matrix.sudoko;

import java.util.*;

public class SudokuBoard extends Matrix {

    public SudokuBoard() {
        super(9);
    }

    public boolean isValid() {
        return isValidBoard();
    }

    private boolean isValidBoard() {
        var rows = new HashSet<String>();
        var cols = new HashSet<String>();
        HashSet<String>[][] blocks = new HashSet[3][3];
        HashSet block;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (!isValidDigit(matrix[i][j])) {
                    return false;
                }
                int iBloco = getBlockIndex(i);
                int jBloco = getBlockIndex(j);
                block = blocks[iBloco][jBloco];
                if (block == null) {
                    block = new HashSet();
                    blocks[iBloco][jBloco] = block;
                }
                if (block.contains(matrix[i][j])) {
                    return false;
                } else if (!"0".equals(matrix[i][j])) {
                    block.add(matrix[i][j]);
                }

                if (!matrix[i][j].trim().isEmpty()) {
                    if (rows.contains(matrix[i][j])) {
                        return false;
                    }
                    if (!"0".equals(matrix[i][j]))
                        rows.add(matrix[i][j]);
                }
                if (!matrix[j][i].trim().isEmpty()) {
                    if (cols.contains(matrix[j][i])) {
                        return false;
                    }
                    if (!"0".equals(matrix[j][i]))
                        cols.add(matrix[j][i]);
                }
            }
            rows.clear();
            cols.clear();
        }
        return true;
    }

    private int getBlockIndex(int i) {
        if (i >= 0 && i <= 2) {
            return 0;
        }
        if (i >= 3 && i <= 5) {
            return 1;
        }
        return 2;
    }

    private boolean isValidDigit(String digit) {
        try {
            var val = Integer.parseInt(digit);
            return val >= 0 && val <= 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
