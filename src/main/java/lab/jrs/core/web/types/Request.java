package lab.jrs.core.web.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private Map<String, String> paramsQuery = new HashMap<>();
    private List<String> paramsPaths = new ArrayList<>();
    private Object body = null;
    private RequestMethod method;

    public Map<String, String> getParamsQuery() {
        return paramsQuery;
    }

    public void setParamsQuery(Map<String, String> paramsQuery) {
        this.paramsQuery = paramsQuery;
    }

    public List<String> getParamsPaths() {
        return paramsPaths;
    }

    public void setParamsPaths(List<String> paramsPaths) {
        this.paramsPaths = paramsPaths;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public void print() {
        System.out.println("Method: "+this.method);
        if(this.paramsQuery != null &&  this.paramsQuery.size() > 0){
            System.out.println("Querys: ");
            this.paramsQuery.entrySet().stream().forEach(pair -> System.out.println(pair.getKey()+" = "+pair.getValue()));
        }
        if(this.paramsPaths != null && this.paramsPaths.size() > 0){
            System.out.println("Paths: ");
            this.paramsPaths.stream().forEach(value -> System.out.println(value));
        }
        if(this.getBody() != null){
            System.out.println("Body: ");
            System.out.println(this.getBody().toString());
        }
    }
}
