package domain;

public class Client extends Entity<Integer> {
    String nume;
    String prenume;
    String adresa;
    int nrTel;
    int idAgent;

    public Client(String nume, String prenume, String adresa, int nrTel, int idAgent) {
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.nrTel = nrTel;
        this.idAgent = idAgent;
    }

    public Client() {
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getNrTel() {
        return nrTel;
    }

    public void setNrTel(int nrTel) {
        this.nrTel = nrTel;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }
}
