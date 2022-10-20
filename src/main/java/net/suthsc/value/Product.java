package net.suthsc.value;

import java.io.Serializable;
import java.util.List;

public record Product(String guid,
                      String name,
                      String deck,
                      String description,
                      List<String> aliases,
                      String image,
                      String detailUrl) implements Serializable {

}
