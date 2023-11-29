package pl.shonsu.jsonbusage.model;

import java.io.Serializable;

public record Address(String city, String street, Integer number) implements Serializable {
}
