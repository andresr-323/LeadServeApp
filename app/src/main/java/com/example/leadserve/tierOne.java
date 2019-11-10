package com.example.leadserve;

public class tierOne extends tier {
    private int coachingProgram;
    private int LEAD1000;
    private int Showcase;

    tierOne(){
        super();
    }

    public int getCoachingProgram() {
        return coachingProgram;
    }

    public void setCoachingProgram(int coachingProgram) {
        this.coachingProgram = coachingProgram;
    }

    public int getLEAD1000() {
        return LEAD1000;
    }

    public void setLEAD1000(int LEAD1000) {
        this.LEAD1000 = LEAD1000;
    }

    public int getShowcase() {
        return Showcase;
    }

    public void setShowcase(int showcase) {
        Showcase = showcase;
    }
}
