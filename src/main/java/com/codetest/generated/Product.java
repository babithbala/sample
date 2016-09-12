
package com.codetest.generated;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.codetest.utils.ValidateString;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "option",
    "productType",
    "counterparty",
    "tradeInfo"
})
public class Product {

    @JsonProperty("option")
    private String option;
    @JsonProperty("productType")
    @NotNull(message = "ProductType is required")
    private String productType;
    
    @JsonProperty("counterparty")
    @NotNull(message = "Counterparty is required")
    private String counterparty;
    /**
     * 
     */
    @JsonProperty("tradeInfo")
    @Valid
    private TradeInfo tradeInfo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The option
     */
    @JsonProperty("option")
    public String getOption() {
        return option;
    }

    /**
     * 
     * @param option
     *     The option
     */
    @JsonProperty("option")
    public void setOption(String option) {
        this.option = option;
    }

    /**
     * 
     * @return
     *     The productType
     */
    @JsonProperty("productType")
    public String getProductType() {
        return productType;
    }

    /**
     * 
     * @param productType
     *     The productType
     */
    @JsonProperty("productType")
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * 
     * @return
     *     The counterparty
     */
    @JsonProperty("counterparty")
    public String getCounterparty() {
        return counterparty;
    }

    /**
     * 
     * @param counterparty
     *     The counterparty
     */
    @JsonProperty("counterparty")
    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    /**
     * 
     * @return
     *     The tradeInfo
     */
    @JsonProperty("tradeInfo")
    public TradeInfo getTradeInfo() {
        return tradeInfo;
    }

    /**
     * 
     * @param tradeInfo
     *     The tradeInfo
     */
    @JsonProperty("tradeInfo")
    public void setTradeInfo(TradeInfo tradeInfo) {
        this.tradeInfo = tradeInfo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Generated("org.jsonschema2pojo")
    public enum Counterparty {

        PLUTO_1("PLUTO1"),
        PLUTO_2("PLUTO2");
        private final String value;
        private final static Map<String, Product.Counterparty> CONSTANTS = new HashMap<String, Product.Counterparty>();

        static {
            for (Product.Counterparty c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Counterparty(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Product.Counterparty fromValue(String value) {
            Product.Counterparty constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("org.jsonschema2pojo")
    public enum Option {

        BUY("BUY"),
        SELL("SELL");
        private final String value;
        private final static Map<String, Product.Option> CONSTANTS = new HashMap<String, Product.Option>();

        static {
            for (Product.Option c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Option(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Product.Option fromValue(String value) {
            Product.Option constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("org.jsonschema2pojo")
    public enum ProductType {

        CURRENCY("CURRENCY"),
        TRAVEL_CARD("TRAVEL_CARD"),
        TRANSFER("TRANSFER");
        private final String value;
        private final static Map<String, Product.ProductType> CONSTANTS = new HashMap<String, Product.ProductType>();

        static {
            for (Product.ProductType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ProductType(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static Product.ProductType fromValue(String value) {
            Product.ProductType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
