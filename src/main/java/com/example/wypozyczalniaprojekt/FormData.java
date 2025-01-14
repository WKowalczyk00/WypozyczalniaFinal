package com.example.wypozyczalniaprojekt;

public class FormData {
        private String imie;
        private String nazwisko;
        private Integer iloscDni;
        private Integer nrPrawaJazdy;
        private String dataPrawaJazdy;
        private String klasaAuta;
        private String rodzajSilnika;
        private String rodzajDokumentu;

        public FormData(String imie, String nazwisko, Integer iloscDni, Integer nrPrawaJazdy, String dataPrawaJazdy, String klasaAuta, String rodzajSilnika, String rodzajDokumentu) {
            this.imie = imie;
            this.nazwisko = nazwisko;
            this.iloscDni = iloscDni;
            this.nrPrawaJazdy = nrPrawaJazdy;
            this.dataPrawaJazdy = dataPrawaJazdy;
            this.klasaAuta = klasaAuta;
            this.rodzajSilnika = rodzajSilnika;
            this.rodzajDokumentu = rodzajDokumentu;
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

        public Integer getNrPrawaJazdy() {
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

        @Override
        public String toString() {
            return String.format(
                    "imie: %s\nnazwisko: %s\nilosc dni: %s\nnr prawa jazdy: %s\ndata prawo jazdy:%s\n klasa auta: %s\nrodzaj silnika: %s\nrodzaj dokumentu: %s",
                    imie, nazwisko, iloscDni, nrPrawaJazdy, dataPrawaJazdy, klasaAuta, rodzajSilnika, rodzajDokumentu
            );
        }
    }

