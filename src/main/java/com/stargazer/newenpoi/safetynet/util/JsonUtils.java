package com.stargazer.newenpoi.safetynet.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	private static final String path = "classpath:data/data.json";
	private static final ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	private static JsonUtils instance = null;
	private String data;
	
	/*
	 * On n'initialise pas le constructeur d'un singleton directement, on appelle getInstance() à la place.
	 */
    private JsonUtils() { if (instance != null) throw new IllegalStateException("Utilisez la méthode getInstance() !"); }

    public static synchronized JsonUtils getInstance() {
    	if (instance == null) instance = new JsonUtils();
    	return instance;
    }
    
    /*
     * Récupère le flux json s'il n'a pas déjà été chargé.
     */
	private synchronized String getJsonData() throws IOException {
        
		if (data == null) {
			Resource resource = resourceLoader.getResource(path);
	        byte[] jsonBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
	        
	        data = new String(jsonBytes, StandardCharsets.UTF_8);
        }
        
        return data;
	}
    
    /*
     * Cette méthode générique convertie les objets json en objets java.
     */
    public <T> List<T> retrieve(String field, Class<T> type) throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	
    	JsonNode root = mapper.readTree(getJsonData());
    	JsonNode node = root.get(field);
    	
    	if (node == null) throw new IllegalArgumentException("Le champ %s n'existe pas.".formatted(field));
    	
    	return mapper.readValue(node.traverse(), mapper.getTypeFactory().constructCollectionType(List.class, type));
    }
    
    public <T> List<T> retrieve(String field, String key, String expectedValue, Class<T> type) throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	List<T> filtered = new ArrayList<>();
    	
    	JsonNode root = mapper.readTree(getJsonData());
    	JsonNode nodes = root.get(field);
    	
    	if (nodes == null) throw new IllegalArgumentException("Le champ %s n'existe pas.".formatted(field));
    	
    	for (JsonNode node : nodes) {
    		JsonNode value = node.get(key);
    		if (value != null && value.asText().equals(expectedValue)) filtered.add(mapper.treeToValue(node, type));
    	}
    	
    	return filtered;
    }
}
