package com.example.wypozyczalniaprojekt;

public class FormData {
        private String imie;
        private String nazwisko;
        private Integer iloscDni;
        private String nrPrawaJazdy;
        private String dataPrawaJazdy;
        private String klasaAuta;
        private String rodzajSilnika;
        private String rodzajDokumentu;
        private String nrKarty;
        private String dataWaznosci;
        private String cvc;
        private int startint;

        public FormData(String imie, String nazwisko, Integer iloscDni, String nrPrawaJazdy, String dataPrawaJazdy, String klasaAuta, String rodzajSilnika, String rodzajDokumentu, String nrKarty, String dataWaznosci,String cvc,int startint) {
            this.imie = imie;
            this.nazwisko = nazwisko;
            this.iloscDni = iloscDni;
            this.nrPrawaJazdy = nrPrawaJazdy;
            this.dataPrawaJazdy = dataPrawaJazdy;
            this.klasaAuta = klasaAuta;
            this.rodzajSilnika = rodzajSilnika;
            this.rodzajDokumentu = rodzajDokumentu;
            this.nrKarty = nrKarty;
            this.dataWaznosci = dataWaznosci;
            this.cvc = cvc;
            this.startint = startint;
        }

        // Gettery
        public String getImie() {
            return imie;
        }

        public String getNazwisko() {
            return nazwisko;
        }

        public Integer getIloscDni() {
            return iloscDni;
        }

        public String getNrPrawaJazdy() {
            return nrPrawaJazdy;
        }

        public String getdataPrawaJazdy() {
            return dataPrawaJazdy;
        }

        public String getKlasaAuta() {
            return klasaAuta;
        }

        public String getRodzajSilnika() {
            return rodzajSilnika;
        }

        public String getRodzajDokumentu() {
            return rodzajDokumentu;
        }

        public String getNrKarty() {return nrKarty;}

        public String getDataWaznosci() {return dataWaznosci;}

        public String getCvc() {return cvc;}

        public int getStartint() {return startint;}

        @Override
        public String toString() {
            return "FormData{" +
                    "imie='" + imie + '\'' +
                    ", nazwisko='" + nazwisko + '\'' +
                    ", iloscDni=" + iloscDni +
                    ", nrPrawaJazdy=" + nrPrawaJazdy +
                    ", dataPrawaJazdy='" + dataPrawaJazdy + '\'' +
                    ", klasaAuta='" + klasaAuta + '\'' +
                    ", rodzajSilnika='" + rodzajSilnika + '\'' +
                    ", rodzajDokumentu='" + rodzajDokumentu + '\'' +
                    ", nrKarty=" + nrKarty +
                    ", dataWaznosci='" + dataWaznosci + '\'' +
                    ", cvc=" + cvc +
                    '}';
        }

}

