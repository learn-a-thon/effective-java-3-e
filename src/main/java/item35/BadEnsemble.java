package item35;

// 인스턴스 필드에 정수 데이터를 저장하는 열거 타입 (222쪽)
public enum BadEnsemble {
    SOLO, DUET, TRIO, QUARTET, QUINTET,
    SEXTET, SEPTET, OCTET, DOUBLE_QUARTET,
    NONET, DECTET, TRIPLE_QUARTET;

    public int numberOfMusicians() { return ordinal() + 1; }
}
