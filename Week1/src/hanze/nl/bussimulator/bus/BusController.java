package hanze.nl.bussimulator.bus;

import com.thoughtworks.xstream.XStream;
import hanze.nl.bussimulator.*;

public class BusController {

    private boolean bijHalte;
    private String busID;
    private Lijnen lijn;
    private Bedrijven bedrijf;
    private int richting;

    BusController(BusInfo busInfo) {
        this.bijHalte = false;

        this.busID = busInfo.getBusID();
        this.lijn = busInfo.getLijn();
        this.bedrijf = busInfo.getBedrijf();
        this.richting = busInfo.getRichting();
    }

    void setHalteBereikt(boolean halteBereikt) {
        this.bijHalte = halteBereikt;
    }

    public void setbusID(int starttijd){
        this.busID = starttijd+ lijn.name() + richting;
    }

    public void sendETAs(int nu, int halteNummer, int totVolgendeHalte) {
        int i=0;
        Bericht bericht = new Bericht(lijn.name(), bedrijf.name(), busID, nu);
        if (bijHalte) {
            ETA eta = new ETA(lijn.getHalte(halteNummer).name(),lijn.getRichting(halteNummer),0);
            bericht.getETAs().add(eta);
        }
        Halte.Positie eerstVolgende=lijn.getHalte(halteNummer+richting).getPositie();
        int tijdNaarHalte = totVolgendeHalte+nu;

        for (i = halteNummer+richting; !(i>=lijn.getLengte()) && !(i < 0); i=i+richting){
            tijdNaarHalte+= lijn.getHalte(i).afstand(eerstVolgende);
            eerstVolgende = getEerstVolgende(i, tijdNaarHalte, bericht);
        }

        bericht.setEindpunt(lijn.getHalte(i - richting).name());
        sendBericht(bericht);
    }

    public void sendLastETA(int nu, int halteNummer){
        Bericht bericht = new Bericht(lijn.name(),bedrijf.name(),busID,nu);
        String eindpunt = lijn.getHalte(halteNummer).name();
        ETA eta = new ETA(eindpunt,lijn.getRichting(halteNummer),0);
        bericht.getETAs().add(eta);
        bericht.setEindpunt(eindpunt);
        sendBericht(bericht);
    }

    private Halte.Positie getEerstVolgende(int i, int tijdNaarHalte, Bericht bericht) {
        ETA eta = new ETA(lijn.getHalte(i).name(), lijn.getRichting(i), tijdNaarHalte);
        bericht.getETAs().add(eta);
        return lijn.getHalte(i).getPositie();
    }

    private void sendBericht(Bericht bericht){
        XStream xstream = new XStream();
        xstream.alias("Bericht", Bericht.class);
        xstream.alias("ETA", ETA.class);
        String xml = xstream.toXML(bericht);
        Producer producer = new Producer(xml);
        producer.verstuur();
    }
}
