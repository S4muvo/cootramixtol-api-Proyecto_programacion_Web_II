package edu.cecar.cootramixtol.cootramixtol_api.service.impl;

import edu.cecar.cootramixtol.cootramixtol_api.service.StringSanitizer;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class HtmlStringSanitizer implements StringSanitizer {

    @Override
    public String sanitize(String value) {
        if (value == null) {
            return null;
        }
        return HtmlUtils.htmlEscape(value.trim());
    }
}
