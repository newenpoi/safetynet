package com.stargazer.newenpoi.safetynet.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
	
    private JsonUtils() { throw new IllegalStateException("Cette classe utilitaire ne doit pas être instanciée."); }

    // Méthode générique pour récupérer les objets json.
    public static <T> List<T> retrieve(String field, Class<T> type) throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	
    	JsonNode root = mapper.readTree(getJsonData());
    	JsonNode node = root.get(field);
    	
    	// TODO: Gérer les exceptions.
    	
    	return mapper.readValue(node.traverse(), mapper.getTypeFactory().constructCollectionType(List.class, type));
    }
    
	public static String getJsonData() throws IOException {
        Resource resource = resourceLoader.getResource(path);
        byte[] jsonBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        
        return new String(jsonBytes, StandardCharsets.UTF_8);
	}
}
