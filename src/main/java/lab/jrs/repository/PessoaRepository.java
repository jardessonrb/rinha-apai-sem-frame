package lab.jrs.repository;

import lab.jrs.application.dtos.PessoaDto;
import lab.jrs.application.forms.PessoaForm;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PessoaRepository {

    private Connection connection;

    public PessoaRepository(Connection connection){
        this.connection = connection;
    }

    public UUID save(PessoaForm pessoaForm) throws SQLException {
        if(isContainsPessoaWithApelido(pessoaForm.getApelido())){
            throw new SQLIntegrityConstraintViolationException("Pessoa já existente com o apelido");
        }

        String queryCreate = """                        
                insert into pessoa(apelido, nascimento, nome, termo_busca, uuid)
                values (?, ?, ?, ? ,?);
                              """;

        this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        PreparedStatement preparedStatementCreate = this.connection.prepareStatement(queryCreate);

        UUID id = UUID.randomUUID();
        preparedStatementCreate.setString(1, pessoaForm.getApelido());
        preparedStatementCreate.setObject(2, LocalDate.parse(pessoaForm.getNascimento(), (DateTimeFormatter.ISO_DATE)));
        preparedStatementCreate.setString(3, pessoaForm.getNome());
        preparedStatementCreate.setString(4, pessoaForm.getNome()+pessoaForm.getApelido());
        preparedStatementCreate.setObject(5, id);

        preparedStatementCreate.execute();
        return id;
    }

    public Boolean isContainsPessoaWithApelido(String apelido) throws SQLException {
        String queryCount = "select count(*) as exist from pessoa where apelido = ?";

        this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        PreparedStatement preparedStatementCount = this.connection.prepareStatement(queryCount);
        preparedStatementCount.setString(1, apelido);

        ResultSet resultSet = preparedStatementCount.executeQuery();
        resultSet.next();
        Long quantidade = resultSet.getLong(1);

        return quantidade > 0L;
    }
    public Long countPessoas() throws SQLException {
        String queryCount = "select count(*) from pessoa";

        this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        PreparedStatement preparedStatementCount = this.connection.prepareStatement(queryCount);

        ResultSet resultSet = preparedStatementCount.executeQuery();
        resultSet.next();
        Long quantidade = resultSet.getLong(1);

        return quantidade;
    }

    public List<PessoaDto> findByTermo(String termo) throws SQLException {
        List<PessoaDto> pessoas = new ArrayList<>();

        String queryBuscaTermo = "SELECT * FROM pessoa where termo_busca like '%'|| ? ||'%' limit 50";

        this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        PreparedStatement preparedStatementBuscaPorTermo = this.connection.prepareStatement(queryBuscaTermo);
        preparedStatementBuscaPorTermo.setString(1, termo);

        ResultSet resultSet = preparedStatementBuscaPorTermo.executeQuery();

        while(resultSet.next()){
            PessoaDto pessoaDto = new PessoaDto();
            pessoaDto.setApelido(resultSet.getString("apelido"));
            pessoaDto.setId(resultSet.getString("uuid") != null ? UUID.fromString(resultSet.getString("uuid")) : null);
            pessoaDto.setNome(resultSet.getString("nome"));
            pessoaDto.setStack(new ArrayList<>());

            pessoas.add(pessoaDto);
        }
        return pessoas;
    }

    public PessoaDto findById(UUID id) throws SQLException {
        String queryBuscaTermo = "SELECT * FROM pessoa where uuid = ?";

        this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        PreparedStatement preparedStatementBuscaPorTermo = this.connection.prepareStatement(queryBuscaTermo);
        preparedStatementBuscaPorTermo.setObject(1, id);

        ResultSet resultSet = preparedStatementBuscaPorTermo.executeQuery();

        while(resultSet.next()){
            PessoaDto pessoaDto = new PessoaDto();
            pessoaDto.setApelido(resultSet.getString("apelido"));
            pessoaDto.setId(resultSet.getString("uuid") != null ? UUID.fromString(resultSet.getString("uuid")) : null);
            pessoaDto.setNome(resultSet.getString("nome"));
            pessoaDto.setStack(null);
            return pessoaDto;
        }
        return null;
    }

}
