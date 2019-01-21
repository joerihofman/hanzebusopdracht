package hanze.nl.bussimulator.bus;

import hanze.nl.bussimulator.Bedrijven;
import hanze.nl.bussimulator.Lijnen;

public class BusInfo {

    private Bedrijven bedrijf;
    private Lijnen lijn;
    private int richting;
    private String busID;

    BusInfo(Bedrijven bedrijf, Lijnen lijn, int richting) {
        this.bedrijf = bedrijf;
        this.lijn = lijn;
        this.richting = richting;
        this.busID = "Niet gestart";
    }

    Bedrijven getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijven bedrijf) {
        this.bedrijf = bedrijf;
    }

    Lijnen getLijn() {
        return lijn;
    }

    public void setLijn(Lijnen lijn) {
        this.lijn = lijn;
    }

    int getRichting() {
        return richting;
    }

    public void setRichting(int richting) {
        this.richting = richting;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }
}
