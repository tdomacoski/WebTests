package arca.ci;

import arca.controllers.network.RequestModel;
import arca.controllers.network.RequestModelImpl;

public class RequestModelIntegration {

    public static RequestModel getRequestModel(){
        return new RequestModelImpl();
    }
}
