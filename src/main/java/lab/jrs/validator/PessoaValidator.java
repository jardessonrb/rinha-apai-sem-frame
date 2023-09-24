package lab.jrs.validator;

import lab.jrs.application.forms.PessoaForm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class PessoaValidator {

    public static void validateForm(PessoaForm pessoaForm) throws Exception {
        if(Objects.isNull(pessoaForm.getApelido()) || Objects.isNull(pessoaForm.getNome()) || Objects.isNull(pessoaForm.getNascimento())){
            throw new IllegalArgumentException("Parâmetros inválidos.");
        }

        if(pessoaForm.getNome().isEmpty() || pessoaForm.getNome().isEmpty() || pessoaForm.getNome().matches("\\d+")){
            throw new Exception("Parâmetros inválidos.");
        }

        if(pessoaForm.getApelido().length() > 32 || pessoaForm.getNome().length() > 100){
            throw new IllegalArgumentException("Parâmetros inválidos.");
        }

        LocalDate.parse(pessoaForm.getNascimento(), DateTimeFormatter.ISO_DATE);

        if(pessoaForm.getStack() != null){
            for (int i = 0; i < pessoaForm.getStack().size(); i++) {
                String stack = pessoaForm.getStack().get(i);
                if(stack.isEmpty() || stack.length() > 32 || stack.matches("\\d+")) {
                    throw new Exception("Parâmetros inválidos.");
                }
            }
        }
    }
}
