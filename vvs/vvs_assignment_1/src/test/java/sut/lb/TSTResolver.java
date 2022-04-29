package sut.lb;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class TSTResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		
		Parameter parameter = parameterContext.getParameter();
		return Objects.equals(parameter.getParameterizedType().getTypeName(), 
				              "java.util.Map<java.lang.String, java.lang.Integer>");
		
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		
		Scanner sc;
		
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			sc = new Scanner(new File("data/someWords.txt"));
			
			int i=0;
			while(sc.hasNextLine()) {
				String[] keys = sc.nextLine().split(" ");
				for(String key : keys)
					map.put(key, ++i);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}

}
