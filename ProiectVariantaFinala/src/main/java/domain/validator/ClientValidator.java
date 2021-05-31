package domain.validator;

import domain.Client;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidationException {
        if(entity.getNume().equals(" ")) throw new ValidationException("Acest client are numele gol!");
        if(entity.getPrenume().equals(" ")) throw new ValidationException("Acest client are prenumele gol!");
        if(entity.getAdresa().equals(" ")) throw new ValidationException("Acest client are adresa goala!");
        if(entity.getNrTel() < 0) throw new ValidationException("Acest client are nr de tel negativ!");
        if(entity.getIdAgent() < 0) throw new ValidationException("Acest client are idAgent negativ!");
    }
}
