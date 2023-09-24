package lab.jrs.application.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PessoaDto {
    private UUID id;
    private String nome;
    private String apelido;
    private LocalDate nascimento;
    private List<String> stack;

    public PessoaDto() {}

    public PessoaDto(String nome, String apelido, LocalDate nascimento, List<String> stack, UUID id) {
        this.id = id;
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

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
