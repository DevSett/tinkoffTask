package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import web.dto.ResultDTO;

import web.jaxb.result.ServiceRequest;
import web.jaxb.result.ServiceResponse;
import web.service.ResultService;

import java.util.stream.Collectors;

@Endpoint
public class ResultEndpoint {
    private static final String namespaceUri = "http://jaxb.web/result";

    @Autowired
    ResultService resultService;


    /**
     * Метод для соап запроса
     * @param request
     * @return
     */
    @PayloadRoot(namespace = "http://jaxb.web/result", localPart = "ServiceRequest")
    @ResponsePayload
    public ServiceResponse getResult(@RequestPayload ServiceRequest request) {
        ServiceResponse response = new ServiceResponse();

        Integer number = request.getNumber().intValue();
        ResultDTO resultDTO = resultService.findNumber(number);
        response.setCode(resultDTO.getCode());
        response.setError(resultDTO.getError());
        response.setFileNames(resultDTO.getFileName().stream().collect(Collectors.joining(",")));

        return response;
    }
}
