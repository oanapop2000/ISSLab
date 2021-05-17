package domain;

public class Agent extends Entity<Integer>{
    String nume;
    String parola;

    public Agent() {
    }

    public Agent(String nume, String parola) {
        this.nume = nume;
        this.parola = parola;
    }

    public String getNume() {
        return nume;
    }

    public String getParola() {
        return parola;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
