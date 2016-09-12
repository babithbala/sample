
package com.codetest.generated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "year",
    "validDates"
})
public class USD {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("year")
    private String year;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("validDates")
    private List<ValidDate> validDates = new ArrayList<ValidDate>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The year
     */
    @JsonProperty("year")
    public String getYear() {
        return year;
    }

    /**
     * 
     * (Required)
     * 
     * @param year
     *     The year
     */
    @JsonProperty("year")
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The validDates
     */
    @JsonProperty("validDates")
    public List<ValidDate> getValidDates() {
        return validDates;
    }

    /**
     * 
     * (Required)
     * 
     * @param validDates
     *     The validDates
     */
    @JsonProperty("validDates")
    public void setValidDates(List<ValidDate> validDates) {
        this.validDates = validDates;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
