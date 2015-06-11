package org.wordcloud.rest;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Configuration class for dropwisard
 */
public class WordCloudConfiguration extends Configuration {


    @NotEmpty
    private String springFile;

    @NotEmpty
    private String defaultName = "wordcloud.engine";

    @JsonProperty
    public String getSpringFile() {
        return springFile;
    }

    @JsonProperty
    public void setSpringFile(String file) {
        this.springFile = file;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

}
