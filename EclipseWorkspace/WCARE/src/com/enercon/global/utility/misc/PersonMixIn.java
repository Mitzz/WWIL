package com.enercon.global.utility.misc;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class PersonMixIn {
    @JsonIgnore public String validatorResults;
    @JsonIgnore public String servletWrapper;
    @JsonIgnore public String multipartRequestHandler;
    @JsonIgnore public String page;
    @JsonIgnore public String resultValueMap;
    
}
