package com.gbr.nyan.web;

import com.gbr.nyan.appdirect.SubscriptionResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import static com.gbr.nyan.appdirect.SubscriptionResponse.failure;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SubscriptionController {

    @ResponseBody
    @RequestMapping(value = "/subscription/create/notification", method = GET, consumes = "*", produces = "application/json")
    public SubscriptionResponse create() throws IOException {
        return failure().withErrorCode("INVALID_RESPONSE");
    }
}
