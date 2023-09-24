package lab.jrs.application.forms;

import java.time.LocalDate;
import java.util.List;

public class PessoaForm {
    private String nome;
    private String apelido;
    private String nascimento;
    private List<String> stack;

    public PessoaForm() {}

    public PessoaForm(String nome, String apelido, String nascimento, List<String> stack) {
        this.nome = nome;
        this.apelido = apelido;
        this.nascimento = nascimento;
        this.stack = stack;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }
}
