package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import javax.inject.Inject;
import javax.inject.Named;
import lambda.service.ProcessingService;

@Named("aws")
public class AWSLambda implements RequestHandler<Long, Long> {

    @Inject
    protected transient ProcessingService service;

    @Override
    public Long handleRequest(Long id, Context context) {
        return service.process(id, context);
    }
}
