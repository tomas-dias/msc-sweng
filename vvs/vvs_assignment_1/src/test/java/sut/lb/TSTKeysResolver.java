package sut.lb;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class TSTKeysResolver implements ParameterResolver {
	
	private String[] keys;
	
	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		
		Parameter parameter = parameterContext.getParameter();
		return Objects.equals(parameter.getParameterizedType().getTypeName(), 
				              "java.lang.String[]");
		
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		
		try {
			
			Scanner sc = new Scanner(new File("data/someWords.txt"));
			List<String> keysList = new ArrayList<>();
			
			while(sc.hasNextLine()) {
				String[] tmp = sc.nextLine().split(" ");
				for(int i = 0; i < tmp.length; i++)
					keysList.add(tmp[i]);
			}
			
			keys = keysList.toArray(new String[keysList.size()]);
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return keys;
	}
}
