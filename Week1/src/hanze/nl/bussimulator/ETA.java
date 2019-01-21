package hanze.nl.bussimulator;

public class ETA {
    String halteNaam;
    int richting;
    int aankomsttijd;

    public ETA(String halteNaam, int richting, int aankomsttijd) {
        this.halteNaam = halteNaam;
        this.richting = richting;
        this.aankomsttijd = aankomsttijd;
    }

    @Override
    public String toString() {
        return "ETA{" +
                "halteNaam='" + halteNaam + '\'' +
                ", richting=" + richting +
                ", aankomsttijd=" + aankomsttijd +
                '}';
    }
}
