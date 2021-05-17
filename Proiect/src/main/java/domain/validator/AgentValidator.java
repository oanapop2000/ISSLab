package domain.validator;

import domain.Agent;

public class AgentValidator implements Validator<Agent>{
    @Override
    public void validate(Agent entity) throws ValidationException {
        if(entity.getNume().equals(" ")) throw new ValidationException("Acest agent are numele gol!");
        if(entity.getParola().equals(" ")) throw new ValidationException("Acest agent are parola goala!");
    }
}
