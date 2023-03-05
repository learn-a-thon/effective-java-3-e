package item20.templatemethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiFunction;

// 템플릿 콜백 패턴 적용
public class AdvanceFileProcessor {

    private String path;

    public AdvanceFileProcessor(String path) {
        this.path = path;
    }

    public final int process(BiFunction<Integer, Integer, Integer> operator) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int result = 0;
            String line = null;
            while ((line = reader.readLine()) != null) {
                result = operator.apply(result, Integer.parseInt(line));
            }
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.", e);
        }
    }
}
