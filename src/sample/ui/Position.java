package sample.ui;

public enum Position {

    A(0, 0), B(0, 1), C(1, 0), D(1, 1);

    private int columnIndex;
    private int rowIndex;

    Position(int columnIndex, int rowIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }
}
