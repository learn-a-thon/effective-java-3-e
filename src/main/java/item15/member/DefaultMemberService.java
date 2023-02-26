package item15.member;

import java.util.Arrays;

class DefaultMemberService implements MemberService {

    private String name;

    private static class PrivateStaticMember {
        // 외부 클래스의 참조값을 가지지 않는다.
    }

    private class PrivateMember {
        void doPrint() {
            System.out.println(name);
        }
    }

    public static void main(String[] args) {
        // DefaultMemberService의 참조값을 가지고 있다.
        Arrays.stream(PrivateMember.class.getDeclaredFields()).forEach(System.out::println);
    }
}
