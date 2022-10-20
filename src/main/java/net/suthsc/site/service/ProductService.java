package net.suthsc.site.service;

import net.suthsc.site.config.GiantBomConfiguration;
import net.suthsc.constants.ApiConstants;
import net.suthsc.value.Product;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements ApiConstants {

    private final RequestService requestService;
    private final String apiKey;

    @Autowired
    public ProductService(RequestService service,
                          GiantBomConfiguration configuration) {
        requestService = service;
        apiKey = configuration.getAPIKey();
    }

    public Product detailFor(String guid) {
        String url = buildQueryString("/game/" + guid + "/", new HashMap<>());
        var response = requestService.get(url);
        return buildProductDetail(response.getJSONObject("results"));
    }

    public List<Product> list(int page, int pageSize) {
        int offset = page * pageSize;
        Map<String, String> params = new HashMap<>();
        params.put(OFFSET_FIELD, String.valueOf(offset));
        params.put(LIMIT_FIELD, String.valueOf(pageSize));
        String url = buildQueryString("/games", params);

        var results = requestService.get(url).getJSONArray("results");
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            products.add(buildProduct((JSONObject) results.get(i)));
        }
        return products;
    }

    public List<Product> search(String query, String type) {
        Map<String, String> params = new HashMap<>();
        params.put(RESOURCES_PARAM, type);
        params.put(QUERY_PARAM, query);
        String url = buildQueryString("/search", params);

        List<Product> products = new ArrayList<>();
        JSONArray results = requestService.get(url).getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            products.add(buildProduct((JSONObject) results.get(i)));
        }

        return products;
    }

    private Product buildProductDetail(JSONObject json) {
        return new Product(json.getString("guid"),
                json.getString("name"),
                nullableString(json, "deck"),
                nullableString(json, "description"),
                buildAliasList(nullableString(json, "aliases")),
                buildImageList(json.getJSONObject("image"), "medium_url"),
                nullableString(json, "api_detail_url"));
    }

    private Product buildProduct(JSONObject jsonObject) {
        return new Product(jsonObject.getString("guid"),
                jsonObject.getString("name"),
                nullableString(jsonObject, "deck"),
                nullableString(jsonObject, "description"),
                buildAliasList(nullableString(jsonObject, "aliases")),
                buildImageList(jsonObject.getJSONObject("image")),
                nullableString(jsonObject, "api_detail_url"));
    }

    private String nullableString(JSONObject object, String key) {
        return (object.has(key) && !object.isNull(key)) ? object.getString(key) : null;
    }

    private String buildImageList(JSONObject imageJson) {
        if (imageJson == null) {
            return null;
        }

        return imageJson.getString("icon_url");
    }

    private String buildImageList(JSONObject imageJson, String key) {
        if (imageJson == null) {
            return null;
        }

        return imageJson.getString(key);
    }

    private List<String> buildAliasList(String aliasString) {
        List<String> aliases = new ArrayList<>();
        if (StringUtils.isNotBlank(aliasString)) {
            int index = 0;
            while (aliasString.indexOf('\n', index) >= 0) {
                aliases.add(aliasString.substring(index, aliasString.indexOf('\n', index)));
                index = aliasString.indexOf('\n', index) + 1;
            }
            aliases.add(aliasString.substring(index));
        }
        return aliases;
    }

    private String buildQueryString(String resourcePath, Map<String, String> queryParams) {
        if (!queryParams.containsKey(API_KEY_PARAM)) {
            queryParams.put(API_KEY_PARAM, apiKey);
        }
        if (!queryParams.containsKey(FORMAT_PARAM)) {
            queryParams.put(FORMAT_PARAM, "json");
        }

        String params = queryParams.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(HTTP_QUERY_DELIMITER));

        return API_ENDPOINT + resourcePath + HTTP_QUERY_SEPARATOR + params;
    }

}
