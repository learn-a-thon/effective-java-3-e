package item7.optional;

import java.util.Optional;

public class Channel {
    private int numOfSubscribers;

    // npe 발생 예시
//    public MemberShip defaultMemberShip() {
//        if (this.numOfSubscribers < 2000) {
//            return null;
//        } else {
//            return new MemberShip();
//        }
//    }

    // npe 방어 1) exception 발생
//    public MemberShip defaultMemberShip() {
//        if (this.numOfSubscribers < 2000) {
//            throw new IllegalArgumentException();
//        } else {
//            return new MemberShip();
//        }
//    }

    // npe 방어 2) optional
    public Optional<MemberShip> defaultMemberShip() {
        if (this.numOfSubscribers < 2000) {
            return Optional.empty();
        } else {
            return Optional.of(new MemberShip());
        }
    }
}
