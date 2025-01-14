package Wypozyczalnia;

import java.sql.Connection;
import java.util.List;

public class Tests {
    public static void main(String[] args){
        Main main = new Main();
        Car chosenSamochod = main.wybierzSamochod("B","Automatic");
        int ilosc_dni = 3;
        String dane_klienta = "Fredi Krzemionka";
        String numer_p_jazdy = String.valueOf(293021930);
        main.generateInvoice(chosenSamochod,dane_klienta,ilosc_dni,numer_p_jazdy);
//        System.out.println(main.getCars().getLast().getModel());
    }
}
