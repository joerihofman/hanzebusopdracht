package hanze.nl.bussimulator;

import hanze.nl.bussimulator.bus.Bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Runner {

    /**
    * IN DB: een tabel met twee columns, id_buslog & logrecord
    * op anypoint: vul de naam van het schema in ipv de naam van de db
    * in foreach voor de insert in db is het application/java om een record op te bouwen voor in de db
    * odoo: publish starten; odoopublisher; dan kan je naar de url gaan met er achter ?wsdl dan krijg je de
    * volledige wsdl van je soap server te zien met een verwijzing naar een xsd schema aan het begin.
    * in anypoint: webseriveconsumer wsdl url opgeven van de odoo dinges in het global elements scherm
    **/


    private static HashMap<Integer, ArrayList<Bus>> busStart = new HashMap<>();
    private static ArrayList<Bus> actieveBussen = new ArrayList<>();
    private static int interval = 1000;

    private static void addBus(int starttijd, Bus bus) {
        ArrayList<Bus> bussen = new ArrayList<>();
        if (busStart.containsKey(starttijd)) {
            bussen = busStart.get(starttijd);
        }
        bussen.add(bus);
        busStart.put(starttijd, bussen);
        bus.getBusInfo().setbusID(starttijd);
    }

    private static int startBussen(int tijd) {
        actieveBussen.addAll(busStart.get(tijd));

        busStart.remove(tijd);

        return (!busStart.isEmpty()) ? Collections.min(busStart.keySet()) : -1;
    }

    private static void moveBussen(int nu) {
        Iterator<Bus> itr = actieveBussen.iterator();
        while (itr.hasNext()) {
            Bus bus = itr.next();
            boolean eindpuntBereikt = bus.move();
            if (eindpuntBereikt) {
                bus.getBusController().sendLastETA(nu, bus.getHalteNummer());
                itr.remove();
            }
        }
    }

    private static void sendETAs(int nu) {
        Iterator<Bus> itr = actieveBussen.iterator();

        while (itr.hasNext()) {
            Bus bus = itr.next();
            bus.getBusController().sendETAs(nu, bus.getHalteNummer(), bus.getTotVolgendeHalte());
        }
    }

    private static int initBussen() {
        Bus bus1 = new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1);
        Bus bus2 = new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, 1);
        Bus bus3 = new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, 1);
        Bus bus4 = new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1);
        Bus bus5 = new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1);
        Bus bus6 = new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, 1);
        Bus bus7 = new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, 1);
        Bus bus8 = new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1);
        Bus bus9 = new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1);
        Bus bus10 = new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1);
        addBus(3, bus1);
        addBus(5, bus2);
        addBus(4, bus3);
        addBus(6, bus4);
        addBus(3, bus5);
        addBus(5, bus6);
        addBus(4, bus7);
        addBus(6, bus8);
        addBus(12, bus9);
        addBus(10, bus10);
        Bus bus11 = new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1);
        Bus bus12 = new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, -1);
        Bus bus13 = new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, -1);
        Bus bus14 = new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1);
        Bus bus15 = new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1);
        Bus bus16 = new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, -1);
        Bus bus17 = new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, -1);
        Bus bus18 = new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1);
        Bus bus19 = new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1);
        Bus bus20 = new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1);
        addBus(3, bus11);
        addBus(5, bus12);
        addBus(4, bus13);
        addBus(6, bus14);
        addBus(3, bus15);
        addBus(5, bus16);
        addBus(4, bus17);
        addBus(6, bus18);
        addBus(12, bus19);
        addBus(10, bus20);
        return Collections.min(busStart.keySet());
    }

    public static void main(String[] args) throws InterruptedException {
        int tijd = 0;
        int volgende = initBussen();
        while ((volgende >= 0) || !actieveBussen.isEmpty()) {
            System.out.println("De tijd is:" + tijd);
            volgende = (tijd == volgende) ? startBussen(tijd) : volgende;
            moveBussen(tijd);
            sendETAs(tijd);
            Thread.sleep(interval);
            tijd++;
        }
    }
}
