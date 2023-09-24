package lab.jrs.core;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lab.jrs.core.adapters.GsonLocalDateAdapter;
import lab.jrs.core.resolvers.RequestResolverCallable;
import lab.jrs.core.web.types.Request;
import lab.jrs.core.web.types.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import lab.jrs.core.web.types.Response;
import lab.jrs.service.PessoaService;

public class HandlerRequestResolver implements HttpHandler {

    private PessoaService pessoaService;
    private Gson gson;

    PriorityBlockingQueue<HttpExchange> requests;

    public HandlerRequestResolver(PessoaService pessoaService){
        this.requests = new PriorityBlockingQueue<>();
        this.pessoaService = pessoaService;
        this.gson =  new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new GsonLocalDateAdapter())
                .setLenient()
                .create();
    }
    public HandlerRequestResolver(){
        this.requests = new PriorityBlockingQueue<>();
        this.pessoaService = null;
        this.gson =  new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new GsonLocalDateAdapter())
                .setLenient()
                .create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            Request request = parseRequest(exchange);
            Response response =  resolve(request);

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            if(response.getStatus() == 201){
                exchange.getResponseHeaders().set("Location", "/pessoas/"+response.getBody().toString());
            }

            String json = gson.toJson(response.getBody());
            byte[] body = json.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(response.getStatus(), body.length);
            exchange.getResponseBody().write(body, 0, body.length);
            exchange.close();
        } catch (Exception e) {
            System.out.println("Erro na thread de request ...");
            e.printStackTrace();
        }
    }


//    private void init() {
//        try {
//            List<RequestResolverCallable> requestResolvers = new ArrayList<>();
//            int quantidadeDeNucleosDisponiveis = Runtime.getRuntime().availableProcessors();
//            for (int i = 0; i < quantidadeDeNucleosDisponiveis; i++) {
//                requestResolvers.add(new RequestResolverCallable(this.requests, new PessoaService()));
//            }
//
//            ExecutorService executorService = Executors.newFixedThreadPool(quantidadeDeNucleosDisponiveis);
//            ExecutorCompletionService<Void> completionService = new ExecutorCompletionService<>(executorService);
//            for (int i = 0; i < quantidadeDeNucleosDisponiveis; i++) {
//                completionService.submit(requestResolvers.get(1));
//            }
//
//            executorService.shutdown();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private Request parseRequest(HttpExchange exchange) throws IOException {
        Request request = new Request();
        request.setMethod(RequestMethod.valueOf(exchange.getRequestMethod()));
        request.setBody(parseBody(exchange));
        request.setParamsPaths(parseParamsPath(exchange));
        request.setParamsQuery(parseParamsQuery(exchange));
        return request;
    }

    private Map<String, String> parseParamsQuery(HttpExchange exchange) {
        String uri = exchange.getRequestURI().toString();
        System.out.println(uri);
        Pattern pattern = Pattern.compile("[?&]([^&]+)");
        Matcher matcher = pattern.matcher(uri);
        Map<String, String> querys = new HashMap<>();
        while (matcher.find()) {
            String[] query = matcher.group(1).split("=");
            querys.put(query[0], query[1]);
        }
        return  querys;
    }

    private List<String> parseParamsPath(HttpExchange exchange) {
        String uri = exchange.getRequestURI().toString();
        Pattern pattern = Pattern.compile("^[^?]+");
        Matcher matcher = pattern.matcher(uri);
        String path = "";
        if (matcher.find()) {
            path = matcher.group();
        }
        String paths[] = path.split("/");

        return Arrays.asList(paths).stream().filter(pathUri -> !pathUri.isEmpty()).toList();
    }

    private Object parseBody(HttpExchange exchange) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    public Response resolve(Request request){
        return this.pessoaService.resolve(request);
    }
}
