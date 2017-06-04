package sample;

import javafx.beans.property.SimpleDoubleProperty;

public class PutNumbersTable {
    private SimpleDoubleProperty r1;
    private SimpleDoubleProperty r2;

    public PutNumbersTable(double r,double rr){
        this.r1 = new SimpleDoubleProperty(r);
        this.r2 = new SimpleDoubleProperty(rr);
    }


    public double getR1() {
        return r1.get();
    }

    public SimpleDoubleProperty r1Property() {
        return r1;
    }

    public void setR1(double r1) {
        this.r1.set(r1);
    }

    public double getR2() {
        return r2.get();
    }

    public SimpleDoubleProperty r2Property() {
        return r2;
    }

    public void setR2(double r2) {
        this.r2.set(r2);
    }
}
