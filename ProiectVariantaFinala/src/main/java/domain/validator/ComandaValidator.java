package domain.validator;

import domain.Comanda;

public class ComandaValidator implements Validator<Comanda> {
    @Override
    public void validate(Comanda entity) throws ValidationException {
        if (entity.getIdAgent()<0) throw new ValidationException("Aceasta comanda are idAgent negativ");
        if (entity.getIdClient()<0) throw new ValidationException("Aceasta comanda are idClient negativ");
        if (entity.getIdProdus()<0) throw new ValidationException("Aceasta comanda are idProdus negativ");
        if (entity.getCantitate()<0) throw new ValidationException("Aceasta comanda are cantitatea negativa");
        if (entity.getStatus().equals(" ")) throw new ValidationException("Aceasta comanda are statusul gol!");
    }
}
