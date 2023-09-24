package lab.jrs.service;

import com.google.gson.Gson;
import lab.jrs.application.dtos.PessoaDto;
import lab.jrs.application.forms.PessoaForm;
import lab.jrs.core.web.types.Request;
import lab.jrs.core.web.types.RequestMethod;
import lab.jrs.core.web.types.Response;
import lab.jrs.db.DataSource;
import lab.jrs.repository.PessoaRepository;
import lab.jrs.validator.PessoaValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

public class PessoaService {

    private Gson gson;

    private PessoaRepository pessoaRepository;

    public PessoaService() throws SQLException, IllegalAccessException {
        this.pessoaRepository = new PessoaRepository(DataSource.getConnection());
        this.gson = new Gson();
    }

    public Response resolve(Request request){
        if(request.getMethod().equals(RequestMethod.POST)){
            return create(request);
        }

        if(request.getMethod().equals(RequestMethod.GET)){
            if(request.getParamsPaths().size() == 2){
                return findById(request.getParamsPaths().get(1));
            }
            if(request.getParamsPaths().size() == 1 && request.getParamsQuery() != null && request.getParamsQuery().containsKey("t")){
                return findByTermo(request.getParamsQuery().get("t"));
            }
            if(request.getParamsPaths().size() == 1 && request.getParamsPaths().get(0).equals("contagem-pessoas")){
                return countPessoas();
            }
        }

        return (new Response(404, "Request not found"));
    }

    private Response create(Request request){
        try {
            PessoaForm pessoaForm = gson.fromJson((String)request.getBody(), PessoaForm.class);
            PessoaValidator.validateForm(pessoaForm);
            UUID id = pessoaRepository.save(pessoaForm);
            return (new Response(201, id));
        } catch (IllegalArgumentException | SQLIntegrityConstraintViolationException | DateTimeParseException exception){
            return (new Response(422, "Unprocessable Entity/Content"));
        } catch (Exception exception){
            exception.printStackTrace();
            return (new Response(400, "Bad Request"));
        }
    }

    private Response findByTermo(String termo){
        try {
            List<PessoaDto> pessoaDtos = pessoaRepository.findByTermo(termo);
            return (new Response(200, pessoaDtos));
        }catch (Exception exception){
            exception.printStackTrace();
            return (new Response(400, "Bad Request"));
        }
    }

    private Response findById(String id){
        try{
            PessoaDto pessoaDto = pessoaRepository.findById(UUID.fromString(id));
            if(pessoaDto == null){
                return (new Response(404, "Not Found"));
            }

            return (new Response(200, pessoaDto));
        }catch (IllegalArgumentException exception){
            return (new Response(422, "Unprocessable Entity/Content"));
        } catch (Exception exception){
            return (new Response(400, "Bad Request"));
        }
    }
    private Response countPessoas(){
        try {
           Long countPessoas = pessoaRepository.countPessoas();
           return (new Response(200, countPessoas));
        }catch (Exception exception){
            return (new Response(400, "Bad Request"));
        }
    }
}
