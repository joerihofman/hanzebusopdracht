package hanze.nl.bussimulator;

import java.util.ArrayList;

public class Bericht {
    private String lijnNaam;
    private String eindpunt;
    private String bedrijf;
    private String busID;
    private int tijd;
    private ArrayList<ETA> ETAs;

    public String getLijnNaam() {
        return lijnNaam;
    }

    public String getEindpunt() {
        return eindpunt;
    }

    public String getBedrijf() {
        return bedrijf;
    }

    public String getBusID() {
        return busID;
    }

    public int getTijd() {
        return tijd;
    }

    public ArrayList<ETA> getETAs() {
        return ETAs;
    }
    public void setLijnNaam(String lijnNaam) {
        this.lijnNaam = lijnNaam;
    }

    public void setEindpunt(String eindpunt) {
        this.eindpunt = eindpunt;
    }

    public void setBedrijf(String bedrijf) {
        this.bedrijf = bedrijf;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public void setTijd(int tijd) {
        this.tijd = tijd;
    }

    public void setETAs(ArrayList<ETA> ETAs) {
        this.ETAs = ETAs;
    }

    public Bericht(String lijnNaam, String bedrijf, String busID, int tijd) {
        this.lijnNaam = lijnNaam;
        this.bedrijf = bedrijf;
        this.eindpunt = "";
        this.busID = busID;
        this.tijd = tijd;
        this.ETAs = new ArrayList<ETA>();
    }

    @Override
    public String toString() {
        return "Bericht{" +
                "lijnNaam='" + lijnNaam + '\'' +
                ", eindpunt='" + eindpunt + '\'' +
                ", bedrijf='" + bedrijf + '\'' +
                ", busID='" + busID + '\'' +
                ", tijd=" + tijd +
                ", ETAs=" + ETAs +
                '}';
    }

}
