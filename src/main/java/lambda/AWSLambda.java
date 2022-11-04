package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.quarkus.logging.Log;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;
import lambda.service.ProcessingService;

@Named("aws")
public class AWSLambda implements RequestHandler<Integer, Integer> {

    @Inject
    protected transient ProcessingService service;

    @Override
    public Integer handleRequest(Integer id, Context context) {
        try {
            return service.process(id, context);
        } catch (Exception exception) {
            Log.error(Status.INTERNAL_SERVER_ERROR, exception);
            return -1;
        }
    }
}
