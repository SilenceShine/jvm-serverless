import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        List<String> options = new ArrayList<>();
        options.add("-classpath");
        options.add("D:\\workspace\\GitHub\\SilenceShine\\jvm-serverless\\lib\\hutool-all-5.8.12.jar;D:\\workspace\\GitHub\\SilenceShine\\jvm-serverless\\lib\\commons-lang3-3.12.0.jar");
        options.add("D:\\workspace\\GitHub\\SilenceShine\\jvm-serverless\\serverless-demo\\src\\main\\java\\serverless\\HelloWorld.java");
        int result = compiler.run(null, null, null, options.toArray(new String[0]));
        System.out.println("Compile result: " + result);
    }
}