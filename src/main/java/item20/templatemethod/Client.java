package item20.templatemethod;

public class Client {

    public static void main(String[] args) {
        String packageName = Client.class.getPackageName();
        String path = "src/main/java/" + packageName.replaceAll("\\.", "/") + "/number.txt";

        FileProcessor fileProcessor = new Plus(path);
        System.out.println(fileProcessor.process());

        // 템플릿 콜백 패턴(상속 없이 기능 확장하기)
        AdvanceFileProcessor advanceFileProcessor = new AdvancePlus(path);
        System.out.println(advanceFileProcessor.process(Integer::sum));
    }
}
