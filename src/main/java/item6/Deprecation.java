package item6;

public class Deprecation {
    private String name;
    // todo 애노테이션 프로세서 학습

    /**
     * @deprecated in favor of
     * {@link #Deprecation(String)}
     */
    @Deprecated(forRemoval = true, since = "1.7")
    public Deprecation() {
    }

    public Deprecation(String name) {
        this.name = name;
    }
}
