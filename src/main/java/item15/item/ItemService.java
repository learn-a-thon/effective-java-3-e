package item15.item;

import item15.member.MemberService;

public class ItemService {
    // private field
    private MemberService memberService;

    // package-private field
    boolean onSale;

    // protected field
    protected int saleRate;

    // public method
    public ItemService(MemberService memberService) {
        if (memberService == null) {
            throw new IllegalArgumentException("MemberService should not be null.");
        }
        this.memberService = memberService;
    }

    // 테스트를 위해서 필드를 외부에 노출(public)하는 것은 지양한다.
    // ex) public 필드, public 메소드
    // package-private으로 제한하기
    MemberService getMemberService() {
        return memberService;
    }
}
