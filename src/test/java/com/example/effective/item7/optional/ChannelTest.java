package com.example.effective.item7.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ChannelTest {

    @Test
    void npe() {
        Channel channel = new Channel();
        Optional<MemberShip> memberShip = channel.defaultMemberShip();
        memberShip.ifPresent(MemberShip::hello); // npe 발생하지 않음

    }
}