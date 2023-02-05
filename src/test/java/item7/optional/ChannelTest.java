package item7.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

class ChannelTest {

    @Test
    void npe() {
        Channel channel = new Channel();
        Optional<MemberShip> memberShipOptional = channel.defaultMemberShip();
        memberShipOptional.ifPresent(MemberShip::hello);
    }
}
