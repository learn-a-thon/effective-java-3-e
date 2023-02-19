package item15.item;

import item15.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    MemberService memberService;

    @Test
    void itemService() {
        /* MemberService만을 외부에 공개하기로 했고 DefaultMemberService.class는 내부 공개로서
         * package-private으로 선언했기 때문에 사용하지 못한다.
         * 이를 사용하기 위해서는 MemberService를 필드로 선언하고 @Mock 객체를 생성하여 사용할 수 있다.
         */
        //ItemService itemService = new ItemService(new DefaultMemberService());

        ItemService itemService = new ItemService(memberService);

        assertNotNull(itemService);
        // memberService가 private field로 선언되어 있어 접근 불가능
        //assertNotNull(itemService.memberService);
        assertNotNull(itemService.getMemberService());
    }
}
