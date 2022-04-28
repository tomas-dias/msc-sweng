package sut;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class TSTArgumentsProvider implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		
		Stream<String> words = Files.lines(Paths.get("data/someWords.txt"))
			    .map(line -> line.split(" "))
			    .flatMap(Arrays::stream);
		
		return Stream.of(words).map(Arguments::of);
	}
}
