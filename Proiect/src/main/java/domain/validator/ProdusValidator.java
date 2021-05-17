package domain.validator;

import domain.Produs;

public class ProdusValidator implements Validator<Produs>{
    @Override
    public void validate(Produs entity) throws ValidationException {
        if(entity.getNume().equals(" ")) throw new ValidationException("Acest produs are numele gol!");
        if(entity.getPret() < 0) throw new ValidationException("Acest produs are pretul negativ!");
        if(entity.getCantitate() < 0) throw new ValidationException("Acest produs are cantitatea negativa!");
    }
}
