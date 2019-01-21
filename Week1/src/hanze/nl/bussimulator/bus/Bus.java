package hanze.nl.bussimulator.bus;

import hanze.nl.bussimulator.Bedrijven;
import hanze.nl.bussimulator.Halte.Positie;
import hanze.nl.bussimulator.Lijnen;

public class Bus {

    private int halteNummer;
    private int totVolgendeHalte;

    private BusController busController;
    private BusInfo busInfo;

    public Bus(Lijnen lijn, Bedrijven bedrijf, int richting){
        this.halteNummer = -1;
        this.totVolgendeHalte = 0;

        this.busInfo = new BusInfo(bedrijf, lijn, richting);
        this.busController = new BusController(busInfo);
    }

    public BusInfo getBusInfo() {
        return this.busInfo;
    }

    public BusController getBusController() {
        return this.busController;
    }

    public int getHalteNummer() {
        return this.halteNummer;
    }

    public int getTotVolgendeHalte() {
        return this.totVolgendeHalte;
    }

    public boolean move(){
        boolean eindpuntBereikt = false;
        busController.setHalteBereikt(false);
        if (halteNummer == -1) {
            start();
        }
        else {
            this.totVolgendeHalte--;
            if (this.totVolgendeHalte==0){
                eindpuntBereikt=halteBereikt();
            }
        }
        return eindpuntBereikt;
    }

    private boolean halteBereikt(){
        halteNummer+=busInfo.getRichting();
        Lijnen lijn = busInfo.getLijn();

        busController.setHalteBereikt(true);

        if ((halteNummer>=lijn.getLengte()-1) || (halteNummer == 0)) {
            System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n",
                    lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
            return true;
        } else {
            System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n",
                    lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
            naarVolgendeHalte(lijn, busInfo.getRichting());
        }
        return false;
    }

    private void start() {
        int richting = busInfo.getRichting();
        Lijnen lijn = busInfo.getLijn();

        halteNummer = (richting==1) ? 0 : lijn.getLengte()-1;

        System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n",
                lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));

        naarVolgendeHalte(lijn, richting);
    }

    private void naarVolgendeHalte(Lijnen lijn, int richting){
        Positie volgendeHalte = lijn.getHalte(halteNummer+richting).getPositie();
        this.totVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
    }

}
