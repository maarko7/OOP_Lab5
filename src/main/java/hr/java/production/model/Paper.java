package hr.java.production.model;

import hr.java.production.exception.IllegalPaperFormatException;

public abstract class Paper {

    private Float width;
    private Float height;

    public Paper(Float width, Float height) {
        if (width < 1 || height < 1) {
            throw new IllegalPaperFormatException("Format papira nije po propisima.");
        }
        this.width = width;
        this.height = height;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }
}
