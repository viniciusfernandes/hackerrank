package br.com.challanges.hackerrank.matrix.sudoko;

public abstract class Matrix {
    final int size;
    final String matrix[][];

    public Matrix(int size) {
        this.matrix = new String[size][size];
        this.size = size;
    }

    public void setLine(String[] line, int index) {
        matrix[index] = line;
    }

    public void set(String val, int row, int col) {
        matrix[row][col] = val;
    }

    public abstract boolean isValid();

    public Integer parseInt(String x) {
        return x == null || x.trim().length() == 0 ? null : Integer.parseInt(x);
    }
}
