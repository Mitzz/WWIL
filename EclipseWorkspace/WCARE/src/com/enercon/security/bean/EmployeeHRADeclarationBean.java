package com.enercon.security.bean;

public class EmployeeHRADeclarationBean {

    public EmployeeHRADeclarationBean() {
    }

    private String Adress;
    private String LandLord;
    private double Amount;
    

    public void setSAddress(String Adress) {
        this.Adress = Adress;
    }

    public String getSAddress() {
        return Adress;
    }

    public void setSLandLord(String LandLord) {
        this.LandLord = LandLord;
    }

    public String getSLandLord() {
        return LandLord;
    }
    
    public void setNAmount(double nAmount) {
        this.Amount = nAmount;
    }

    public double getnAmount() {
        return Amount;
    }
}
