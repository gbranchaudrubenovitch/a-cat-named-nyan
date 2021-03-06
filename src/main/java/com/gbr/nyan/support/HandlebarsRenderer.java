package com.gbr.nyan.support;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.util.Map;

public class HandlebarsRenderer {
    private final Handlebars handlebars;

    public static HandlebarsRenderer aRenderer() {
        return new HandlebarsRenderer(new Handlebars());
    }

    HandlebarsRenderer(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    public String render(String templateName, Map<String, Object> context) throws IOException {
        Template template = handlebars.compile(templateName);
        return template.apply(context);
    }
}
