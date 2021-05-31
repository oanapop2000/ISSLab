package domain;

public class Comanda extends Entity<Integer> {
    private int idAgent;
    private int idClient;
    private int idProdus;
    private int cantitate;
    private String status;

    public Comanda(int idAgent, int idClient, int idProdus, int cantitate, String status) {
        this.idAgent = idAgent;
        this.idClient = idClient;
        this.idProdus = idProdus;
        this.cantitate = cantitate;
        this.status = status;
    }

    public Comanda() {
    }

    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
