package lab.jrs.core.web.types;

public class Response {
    private int status;
    private Object body;

    public Response(int status, Object body){
        this.status = status;
        this.body   = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
